package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.citframework.util.Reflexao;

public class PersistenceUtil {

    private String nomeTabela;
    private Class<?> nomeClasse;
    private Collection campos;
    private List camposCreate;
    private List<Field> camposChave;
    private List camposUpdate;
    private List camposSequencial;
    private List<Field> uniqueFields;

    public PersistenceUtil(final String nomeTabela, final Class<?> nomeClasse, final Collection campos) {
        this.nomeTabela = nomeTabela;
        this.nomeClasse = nomeClasse;
        this.campos = campos;
        this.configuraCampos();
    }

    protected List<Field> getUniqueFields() {
        return uniqueFields;
    }

    protected void setUniqueFields(final List<Field> uniqueFields) {
        this.uniqueFields = uniqueFields;
    }

    protected List<Field> getCamposChave() {
        return camposChave;
    }

    protected void setCamposChave(final List<Field> camposChave) {
        this.camposChave = camposChave;
    }

    protected List getCamposCreate() {
        return camposCreate;
    }

    protected void setCamposCreate(final List camposCreate) {
        this.camposCreate = camposCreate;
    }

    protected List getCampoSequencial() {
        return camposSequencial;
    }

    protected void setCampoSequencial(final List campoSequencial) {
        camposSequencial = campoSequencial;
    }

    protected List getCamposUpdate() {
        return camposUpdate;
    }

    protected void setCamposUpdate(final List camposUpdate) {
        this.camposUpdate = camposUpdate;
    }

    protected Collection getCampos() {
        return campos;
    }

    protected void setCampos(final Collection campos) {
        this.campos = campos;
    }

    protected Class<?> getNomeClasse() {
        return nomeClasse;
    }

    protected void setNomeClasse(final Class<?> nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    protected String getNomeTabela() {
        return nomeTabela;
    }

    protected void setNomeTabela(final String nomeTabela) {
        this.nomeTabela = nomeTabela;
    }

    // Metodos utilitario
    public SqlConfiguration getConfigurationCreate(final Object obj) throws Exception {
        String sql = "INSERT INTO " + this.getNomeTabela() + " ";
        String campos = "";
        String valores = "";
        // Object[] parametros = new Object[camposCreate.size()];
        final List listaParametros = new ArrayList();
        for (int i = 0; i < camposCreate.size(); i++) {
            final Field cmp = (Field) camposCreate.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());

            if (i > 0) {
                campos += ",";
                valores += ",";
            }
            campos += cmp.getFieldDB();
            if (valor != null) {
                valores += "?";
                listaParametros.add(valor);
            } else {

                valores += "NULL";
            }

        }
        sql += "(" + campos + ") VALUES ";
        sql += "(" + valores + ")";
        return new SqlConfiguration(sql, listaParametros.toArray());
    }

    public SqlConfiguration getConfigurationUpdateAll(final Object obj) throws Exception {
        String sql = "update " + this.getNomeTabela() + " set ";
        String campos = "";
        String chaves = "";
        camposUpdate.size();
        camposChave.size();
        // Object[] parametros = new Object[qtCampos];
        final List listaParametros = new ArrayList();
        for (int i = 0; i < camposUpdate.size(); i++) {
            final Field cmp = (Field) camposUpdate.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
            if (i > 0) {
                campos += ",";
            }
            if (valor != null) {
                campos += cmp.getFieldDB() + " = ? ";
                listaParametros.add(valor);
            } else {
                campos += cmp.getFieldDB() + " = NULL ";
            }

        }

        for (int i = 0; i < camposChave.size(); i++) {
            final Field cmp = camposChave.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
            if (valor == null) {
                throw new Exception("PK value is mandatory");
            }
            if (i > 0) {
                chaves += " and ";
            }
            chaves += cmp.getFieldDB() + " = ? ";
            listaParametros.add(valor);
        }
        sql += campos + " where " + chaves;
        return new SqlConfiguration(sql, listaParametros.toArray());
    }

    public SqlConfiguration getConfigurationUpdateNotNull(final Object obj) throws Exception {
        String sql = "update " + this.getNomeTabela() + " set ";
        String campos = "";
        String chaves = "";
        camposUpdate.size();
        camposChave.size();
        // Object[] parametros = new Object[qtCampos];
        final List listaParametros = new ArrayList();
        for (int i = 0; i < camposUpdate.size(); i++) {
            final Field cmp = (Field) camposUpdate.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());

            if (valor != null) {
                if (listaParametros.size() > 0) {
                    campos += ",";
                }
                campos += cmp.getFieldDB() + " = ? ";
                listaParametros.add(valor);
            }
        }

        for (int i = 0; i < camposChave.size(); i++) {
            final Field cmp = camposChave.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
            if (valor == null) {
                throw new Exception("PK value is mandatory");
            }
            if (i > 0) {
                chaves += " and ";
            }
            chaves += cmp.getFieldDB() + " = ? ";
            listaParametros.add(valor);
        }
        sql += campos + " where " + chaves;
        return new SqlConfiguration(sql, listaParametros.toArray());
    }

    public SqlConfiguration getConfigurationDelete(final Object obj) throws Exception {
        String sql = "delete from " + this.getNomeTabela() + " where ";
        String chaves = "";
        final Object[] parametros = new Object[camposChave.size()];
        for (int i = 0; i < camposChave.size(); i++) {

            final Field cmp = camposChave.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
            if (valor == null) {
                throw new Exception("PK value is mandatory");
            }
            if (i > 0) {
                chaves += " and ";
            }

            chaves += cmp.getFieldDB() + " = ? ";
            parametros[i] = valor;
        }
        sql += chaves;
        return new SqlConfiguration(sql, parametros);
    }

    public SqlConfiguration getConfigurationRestore(final Object obj) throws Exception {
        String sql = " select ";
        String camposSql = "";
        String chaves = "";
        final List camposRetorno = new ArrayList();

        final Iterator it = campos.iterator();

        while (it.hasNext()) {
            final Field cmp = (Field) it.next();

            camposRetorno.add(cmp.getFieldClass());
            if (camposSql.length() > 0) {
                camposSql += ",";
            }
            camposSql += cmp.getFieldDB();

        }
        final Object[] parametros = new Object[camposChave.size()];

        for (int i = 0; i < camposChave.size(); i++) {

            final Field cmp = camposChave.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
            if (valor == null) {
                throw new Exception("PK value is mandatory");
            }
            if (i > 0) {
                chaves += " and ";

            }

            chaves += cmp.getFieldDB() + " = ? ";
            parametros[i] = valor;
        }
        sql += camposSql + " from " + this.getNomeTabela() + " where " + chaves;
        return new SqlConfiguration(sql, parametros, camposRetorno);
    }

    public SqlConfiguration getConfigurationList(final List ordenacao) throws Exception {
        String sql = " select ";
        String camposSql = "";
        final List camposRetorno = new ArrayList();

        final Iterator it = campos.iterator();

        while (it.hasNext()) {
            final Field cmp = (Field) it.next();

            camposRetorno.add(cmp.getFieldClass());
            if (camposSql.length() > 0) {
                camposSql += ",";
            }
            camposSql += cmp.getFieldDB();

        }
        sql += camposSql + " from " + this.getNomeTabela();

        if (ordenacao != null) {
            sql += this.getOrdenacao(ordenacao);
        }

        return new SqlConfiguration(sql, null, camposRetorno);
    }

    public SqlConfiguration getConfigurationFindByCondition(final List condicao, final List ordenacao) throws Exception {
        final SqlConfiguration sqlConf = this.getConfigurationList(null);
        this.montaCondicaoOrdenacao(condicao, ordenacao, sqlConf);
        return sqlConf;

    }

    public SqlConfiguration getConfigurationUpdateAllByCondition(final Object obj, final List condicao) throws Exception {
        final String sql = "update " + this.getNomeTabela() + " set ";
        String campos = "";
        final List listaParametros = new ArrayList();
        for (int i = 0; i < camposUpdate.size(); i++) {
            final Field cmp = (Field) camposUpdate.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
            if (listaParametros.size() > 0) {
                campos += ",";

            }
            if (valor == null) {
                campos += cmp.getFieldDB() + " = NULL ";
            } else {
                campos += cmp.getFieldDB() + " = ? ";
                listaParametros.add(valor);
            }

        }
        final SqlConfiguration result = new SqlConfiguration(sql + campos, listaParametros.toArray());
        this.montaCondicaoOrdenacao(condicao, null, result);
        return result;

    }

    public SqlConfiguration getConfigurationUpdateNotNullByCondition(final Object obj, final List condicao) throws Exception {
        final String sql = "update " + this.getNomeTabela() + " set ";
        String campos = "";
        final List listaParametros = new ArrayList();
        for (int i = 0; i < camposUpdate.size(); i++) {
            final Field cmp = (Field) camposUpdate.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
            if (valor != null) {
                if (listaParametros.size() > 0) {
                    campos += ",";
                }
                campos += cmp.getFieldDB() + " = ? ";
                listaParametros.add(valor);

            }

        }
        final SqlConfiguration result = new SqlConfiguration(sql + campos, listaParametros.toArray());
        this.montaCondicaoOrdenacao(condicao, null, result);
        return result;

    }

    /**
     * Pega a configuracao de find. O segundo parametro eh uma lista de String
     * com o nome dos campos na classe que devem ser ordenados. Exemplo: List
     * lst = new ArrayList(); lst.add("nomeUf"); lst.add("siglaUf");
     *
     * getConfigurationFindNotNull(obj, lst);
     *
     * @param obj
     * @param ordenacao
     * @return
     * @throws Exception
     */
    public SqlConfiguration getConfigurationFindNotNull(final Object obj, final List ordenacao) throws Exception {
        String sql = " select ";
        String camposSql = "";
        String chaves = "";
        final List camposRetorno = new ArrayList();

        final Iterator it = campos.iterator();

        while (it.hasNext()) {
            final Field cmp = (Field) it.next();

            camposRetorno.add(cmp.getFieldClass());
            if (camposSql.length() > 0) {
                camposSql += ",";
            }
            camposSql += cmp.getFieldDB();

        }
        Object[] parametros = null;
        final List parms = new ArrayList();
        final List camposAux = (List) campos;
        int qtdeParms = 0;
        for (int i = 0; i < camposAux.size(); i++) {

            final Field cmp = (Field) camposAux.get(i);
            final Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
            if (valor != null && !valor.equals("")) {
                if (qtdeParms > 0) {
                    chaves += " and ";
                }
                qtdeParms++;
                chaves += cmp.getFieldDB() + " = ? ";
                parms.add(valor);
            }
        }
        if (qtdeParms > 0) {
            parametros = new Object[parms.size()];
            for (int i = 0; i < parms.size(); i++) {
                parametros[i] = parms.get(i);
            }
        }
        sql += camposSql + " from " + this.getNomeTabela() + " where " + chaves;

        if (ordenacao != null) {
            sql += this.getOrdenacao(ordenacao);
        }

        return new SqlConfiguration(sql, parametros, camposRetorno);
    }

    public SqlConfiguration getConfigurationDeleteByCondition(final List condicao) throws Exception {
        final SqlConfiguration sqlConf = new SqlConfiguration("delete from " + this.getNomeTabela(), null);
        this.montaCondicaoOrdenacao(condicao, null, sqlConf);
        return sqlConf;
    }

    private void montaCondicaoOrdenacao(final List condicao, final List ordenacao, final SqlConfiguration sqlConf) throws Exception {
        if (condicao != null) {
            String result = "";
            final List parametros = new ArrayList();
            if (sqlConf.getParametros() != null) {

                parametros.addAll(Arrays.asList(sqlConf.getParametros()));
            }
            for (int i = 0; i < condicao.size(); i++) {
                final Condition cond = (Condition) condicao.get(i);
                if (i == 0) {
                    result += " where  ";
                } else {
                    if (cond.getOperator() == Condition.OR) {
                        result += " or ";
                    } else {
                        result += " and ";
                    }
                }

                result += this.getCampoDB(cond.getFiledClass()) + " " + cond.getComparator() + " ";

                if (cond.getValue() instanceof Collection) {
                    final Iterator it = ((Collection) cond.getValue()).iterator();
                    String valores = "";
                    while (it.hasNext()) {
                        if (valores.length() > 0) {
                            valores += ",";
                        }
                        valores += "?";
                        parametros.add(it.next());
                    }
                    result += "(" + valores + ") ";
                } else {
                    if (cond.getValue() == null) {
                        result += " NULL ";
                    } else {
                        result += "? ";
                        parametros.add(cond.getValue());
                    }
                }

            }

            sqlConf.setParametros(parametros.toArray());
            sqlConf.setSql(sqlConf.getSql() + result);
        }

        if (ordenacao != null) {

            sqlConf.setSql(sqlConf.getSql() + this.getOrdenacao(ordenacao));
        }

    }

    private String getOrdenacao(final List ordenacao) throws Exception {
        if (ordenacao == null || ordenacao.size() == 0) {
            return "";
        }
        String result = " order by ";
        for (int i = 0; i < ordenacao.size(); i++) {
            if (i > 0) {
                result += ",";
            }
            final Order order = (Order) ordenacao.get(i);
            result += this.getCampoDB(order.getField()) + " " + order.getTypeOrder();
        }
        return result;
    }

    public String getCampoDB(final String campoClasse) throws Exception {
        final Iterator it = campos.iterator();
        while (it.hasNext()) {
            final Field cmp = (Field) it.next();

            if (cmp.getFieldClass().equals(campoClasse)) {
                return cmp.getFieldDB();
            }
        }
        throw new Exception("Field " + campoClasse + " não configurado!");
    }

    private void configuraCampos() {
        camposCreate = new ArrayList<>();
        camposChave = new ArrayList<>();
        camposUpdate = new ArrayList<>();
        uniqueFields = new ArrayList<>();
        camposSequencial = new ArrayList<>();

        if (this.getCampos() != null) {
            final Iterator it = this.getCampos().iterator();
            while (it.hasNext()) {
                final Field cmp = (Field) it.next();
                if (!cmp.isAuto()) {
                    camposCreate.add(cmp);
                }

                if (cmp.isPk()) {
                    camposChave.add(cmp);
                } else {
                    camposUpdate.add(cmp);
                }

                if (cmp.isSequence()) {
                    camposSequencial.add(cmp);
                }

                if (cmp.isUnique()) {
                    uniqueFields.add(cmp);
                }
            }
        }
    }

}
