package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.FKReferenceException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.core.SequenceBlockCache;
import br.com.citframework.integracao.core.SequenceBlockPool;
import br.com.citframework.util.DBItemConvertion;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.XmlReadDBItemConvertion;

public class PersistenceEngine extends JdbcEngine {

    private static final Logger LOGGER = Logger.getLogger(PersistenceEngine.class);

    private final PersistenceUtil persistenceUtil;
    private final String tableName;
    private final Class<?> bean;
    private int maxRows = 0;
    private static UsuarioDTO usuarioSessao;

    /* Atributos para controle dos ids utilizando sequence block */
    private static volatile SequenceBlockCache globalCache = null;

    public static void setUsuarioSessao(final UsuarioDTO usuario) {
        usuarioSessao = usuario;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(final int maxRows) {
        this.maxRows = maxRows;
    }

    public PersistenceEngine(final String alias, final String tableName, final Class<?> bean, final Collection fields, final Usuario usuario) {
        super(alias, usuario);

        globalCache = SequenceBlockPool.getFromPool(alias);

        this.tableName = tableName;
        this.bean = bean;
        persistenceUtil = new PersistenceUtil(tableName, bean, fields);
    }

    public PersistenceEngine(final TransactionControler tc, final String tableName, final Class<?> bean, final Collection fields, final Usuario usuario) {
        super(tc, usuario);

        globalCache = SequenceBlockPool.getFromPool(tc.getDataBaseAlias());

        this.tableName = tableName;
        this.bean = bean;
        persistenceUtil = new PersistenceUtil(tableName, bean, fields);
    }

    public Object create(final Object obj) throws PersistenceException {
        try {
            this.validUniqueKey(obj);
            this.setNextKey(obj);
            final SqlConfiguration conf = persistenceUtil.getConfigurationCreate(obj);

            final Object[] params = conf.getParametros();
            this.execUpdate(conf.getSql(), params);

            if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
                new Thread(new RegistraLog(obj, "I", persistenceUtil, usuarioSessao, tableName)).start();
            }

            return obj;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    public Object createWithID(final Object obj) throws PersistenceException {
        try {
            this.validUniqueKey(obj);
            final SqlConfiguration conf = persistenceUtil.getConfigurationCreate(obj);

            final Object[] params = conf.getParametros();
            this.execUpdate(conf.getSql(), params);

            if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
                new Thread(new RegistraLog(obj, "I", persistenceUtil, usuarioSessao, tableName)).start();
            }

            return obj;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    public void update(final Object obj) throws PersistenceException {
        try {
            this.validUniqueKey(obj);
            final SqlConfiguration conf = persistenceUtil.getConfigurationUpdateAll(obj);

            final Object[] params = conf.getParametros();
            this.execUpdate(conf.getSql(), params);

            if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
                new Thread(new RegistraLog(obj, "A", persistenceUtil, usuarioSessao, tableName)).start();
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    public void updateNotNull(final Object obj) throws PersistenceException {
        try {
            final SqlConfiguration conf = persistenceUtil.getConfigurationUpdateNotNull(obj);

            final Object[] params = conf.getParametros();
            this.execUpdate(conf.getSql(), params);

            if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
                new Thread(new RegistraLog(obj, "A", persistenceUtil, usuarioSessao, tableName)).start();
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    public void delete(final Object obj) throws PersistenceException {
        try {
            final SqlConfiguration conf = persistenceUtil.getConfigurationDelete(obj);

            final Object[] params = conf.getParametros();
            this.execUpdate(conf.getSql(), params);

            if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
                new Thread(new RegistraLog(obj, "E", persistenceUtil, usuarioSessao, tableName)).start();
            }
        } catch (final PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            if (e.getMessage().indexOf(".FK") > -1) {
                throw new FKReferenceException("Operação cancelada: Registro com referência em outra(s) tabela(s)");
            }
            throw e;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    public Object restore(final Object obj) throws PersistenceException {
        try {
            final SqlConfiguration conf = persistenceUtil.getConfigurationRestore(obj);
            final Object[] params = conf.getParametros();
            final List lista = this.execSQL(conf.getSql(), params, this.getMaxRows());
            if (lista == null || lista.size() == 0) {
                return null;
            }
            final List result = this.listConvertion(this.getBean(), lista, conf.getCamposRetorno());
            return result.get(0);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    public Collection list(final List ordenacao) throws PersistenceException {
        try {
            final SqlConfiguration conf = persistenceUtil.getConfigurationList(ordenacao);
            final List lista = this.execSQL(conf.getSql(), null, maxRows);
            final List result = this.listConvertion(this.getBean(), lista, conf.getCamposRetorno());
            return result;
        } catch (final PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    public Integer getNextKey(final String nomeTabela, final String nomeCampo) throws Exception {
        try {
            final Long r = globalCache.getNextId(getRealName(nomeTabela), getRealName(nomeCampo));
            return r.intValue();
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
            return null;
        }
    }

    public static Integer getNextKey(final String databaseAlias, final String nomeTabela, final String nomeCampo) throws Exception {
        try {
            globalCache = SequenceBlockPool.getFromPool(databaseAlias);

            final Long r = globalCache.getNextId(getRealName(nomeTabela), getRealName(nomeCampo));

            return r.intValue();
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
            return null;
        }
    }

    private void setNextKey(final Object obj) throws Exception {
        if (persistenceUtil.getCampoSequencial() != null) {
            final Iterator it = persistenceUtil.getCampoSequencial().iterator();
            while (it.hasNext()) {
                final Field field = (Field) it.next();

                final Integer keyOfTable = this.getNextKey(tableName.trim().toLowerCase(), field.getFieldDB());

                Reflexao.setPropertyValueAsString(obj, field.getFieldClass(), keyOfTable + "");
            }
        }
    }

    public static String getRealName(String nome) throws Exception {
        final XmlReadDBItemConvertion xmlReadDB = XmlReadDBItemConvertion.getInstance(CITCorporeUtil.SGBD_PRINCIPAL);
        if (xmlReadDB != null) {
            final Collection col = xmlReadDB.getItens();
            if (col != null && col.size() > 0) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final DBItemConvertion dbItemConvertion = (DBItemConvertion) it.next();

                    if (nome != null && dbItemConvertion != null && nome.equalsIgnoreCase(dbItemConvertion.getNameToBeConverted())) {
                        nome = dbItemConvertion.getNameAfterConversion();
                        break;
                    }
                }
            }
        }

        return nome;
    }

    /**
     * Faz a busca dos dados atraves dos campos que estiverem preenchidos no bean, ou seja que nao estejam nulos. O segundo parametro permite ordenacao.
     *
     * ## IMPORTANTE: ## O segundo parametro eh uma lista de String com o nome dos campos na classe que devem ser ordenados. Exemplo: List lst = new ArrayList(); lst.add("nomeUf");
     * lst.add("siglaUf");
     *
     * findNotNull(obj, lst);
     *
     * @param obj
     * @param ordenacao
     * @return
     * @throws PersistenceException
     */
    public Collection findNotNull(final Object obj, final List ordenacao) throws PersistenceException {
        try {
            final SqlConfiguration conf = persistenceUtil.getConfigurationFindNotNull(obj, ordenacao);
            final Object[] params = conf.getParametros();
            final List lista = this.execSQL(conf.getSql(), params, maxRows);
            if (lista == null || lista.size() == 0) {
                return null;
            }
            final List result = this.listConvertion(this.getBean(), lista, conf.getCamposRetorno());
            return result;
        } catch (final PersistenceException e) {
            throw e;
        } catch (final Exception e) {
            throw new PersistenceException(e);
        }
    }

    public Collection findByCondition(final List condicao, final List ordenacao) throws PersistenceException {
        try {
            final SqlConfiguration conf = persistenceUtil.getConfigurationFindByCondition(condicao, ordenacao);
            final Object[] params = conf.getParametros();
            final List lista = this.execSQL(conf.getSql(), params, maxRows);
            if (lista == null || lista.size() == 0) {
                return null;
            }
            final List result = this.listConvertion(this.getBean(), lista, conf.getCamposRetorno());
            return result;
        } catch (final PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    public int updateByCondition(final Object obj, final List condicao) throws PersistenceException {
        int result = 0;

        try {
            final SqlConfiguration conf = persistenceUtil.getConfigurationUpdateAllByCondition(obj, condicao);

            final Object[] params = conf.getParametros();
            result = this.execUpdate(conf.getSql(), params);

            if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
                new Thread(new RegistraLog(obj, "A", persistenceUtil, usuarioSessao, tableName)).start();
            }
        } catch (final PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }

        return result;
    }

    public Collection getResultByConfiguration(final SqlConfiguration sqlconf) throws PersistenceException {
        try {
            final SqlConfiguration conf = sqlconf;
            final Object[] params = conf.getParametros();
            final List lista = this.execSQL(conf.getSql(), params, maxRows);
            if (lista == null || lista.size() == 0) {
                return null;
            }
            final List result = this.listConvertion(this.getBean(), lista, conf.getCamposRetorno());
            return result;
        } catch (final PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    public int updateNotNullByCondition(final Object obj, final List condicao) throws PersistenceException {
        int result = 0;

        try {
            final SqlConfiguration conf = persistenceUtil.getConfigurationUpdateNotNullByCondition(obj, condicao);

            final Object[] params = conf.getParametros();
            result = this.execUpdate(conf.getSql(), params);

            if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
                new Thread(new RegistraLog(obj, "A", persistenceUtil, usuarioSessao, tableName)).start();
            }
        } catch (final PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
        return result;
    }

    public int deleteByCondition(final List condicao) throws PersistenceException {
        int result = 0;

        try {
            final SqlConfiguration conf = persistenceUtil.getConfigurationDeleteByCondition(condicao);

            final Object[] params = conf.getParametros();
            result = this.execUpdate(conf.getSql(), params);
        } catch (final PersistenceException e) {
            LOGGER.error(e.getMessage(), e);
            if (e.getMessage().indexOf(".FK") > -1) {
                throw new FKReferenceException("Operação cancelada: Registro com referência em outra(s) tabela(s)");
            }

            throw e;
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }

        return result;
    }

    private void validUniqueKey(final Object obj) throws Exception {
        final List<Field> uniqueFields = persistenceUtil.getUniqueFields();
        if (uniqueFields != null && uniqueFields.size() > 0) {
            final List<Object> valoresChave = this.getValoresChave(obj);

            for (final Field cmp : uniqueFields) {
                this.validUniqueKey(persistenceUtil.getNomeTabela(), cmp.getFieldDB(), cmp.getMsgReturn(), Reflexao.getPropertyValue(obj, cmp.getFieldClass()),
                        persistenceUtil.getCamposChave(), valoresChave);
            }
        }
    }

    private List<Object> getValoresChave(final Object obj) throws Exception {
        final List<Object> valores = new ArrayList<>();
        final List<Field> it = persistenceUtil.getCamposChave();
        for (final Field field : it) {
            valores.add(Reflexao.getPropertyValue(obj, field.getFieldClass()));
        }
        return valores;
    }

    public String getTableName() {
        return tableName;
    }

    public Class<?> getBean() {
        return bean;
    }

}
