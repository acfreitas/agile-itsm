package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.PageImpl;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.util.Constantes;

public abstract class CrudDaoDefaultImpl extends DaoTransactDefaultImpl implements CrudDAO {

    public CrudDaoDefaultImpl(final String aliasDB, final Usuario usuario) {
        super(aliasDB, usuario);
    }

    public CrudDaoDefaultImpl(final TransactionControler tc, final Usuario usuario) {
        super(tc, usuario);
    }

    @Override
    public abstract String getTableName();

    @Override
    public IDto create(final IDto obj) throws PersistenceException {
        return (IDto) engine.create(obj);
    }

    public IDto createWithID(final IDto obj) throws PersistenceException {
        return (IDto) engine.createWithID(obj);
    }

    @Override
    public void delete(final IDto obj) throws PersistenceException {
        engine.delete(obj);
    }

    @Override
    public IDto restore(final IDto obj) throws PersistenceException {
        return (IDto) engine.restore(obj);
    }

    @Override
    public void update(final IDto obj) throws PersistenceException {
        engine.update(obj);
    }

    protected int deleteByCondition(final List condicao) throws PersistenceException {
        return engine.deleteByCondition(condicao);
    }

    protected void updateNotNull(final IDto obj) throws PersistenceException {
        engine.updateNotNull(obj);
    }

    protected int updateByCondition(final IDto obj, final List condicao) throws PersistenceException {
        return engine.updateByCondition(obj, condicao);
    }

    protected int updateNotNullByCondition(final IDto obj, final List condicao) throws PersistenceException {
        return engine.updateNotNullByCondition(obj, condicao);
    }

    /**
     * ## IMPORTANTE: ## O 1.o parametro eh uma lista de String com o nome dos campos na classe que devem ser ordenados.
     * Exemplo: List lst = new ArrayList(); lst.add("nomeUf"); lst.add("siglaUf");
     *
     * list(lst);
     *
     * @param ordenacao
     * @return
     * @throws Exception
     */
    protected Collection list(final List ordenacao) throws PersistenceException {
        return engine.list(ordenacao);
    }

    /**
     * Passa o campo na classe a qual o retorno deve ser ordenado.
     *
     * @param ordenacao
     * @return
     * @throws Exception
     */
    protected Collection list(final String ordenacao) throws PersistenceException {
        final List<Order> lstOrder = new ArrayList<>();
        lstOrder.add(new Order(ordenacao));
        return engine.list(lstOrder);
    }

    protected Collection find(final IDto obj, final List ordenacao) throws PersistenceException {
        return engine.findNotNull(obj, ordenacao);
    }

    public Collection findByCondition(final List condicao, final List ordenacao) throws PersistenceException {
        return engine.findByCondition(condicao, ordenacao);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return new ArrayList<>();
    }

    @Override
    public Collection list() throws PersistenceException {
        return new ArrayList<>();
    }

    @Override
    public abstract Collection<Field> getFields();

    public String getNamesFieldsStr() {
        final Collection<Field> col = this.getFields();
        final StringBuilder campos = new StringBuilder();
        if (col != null) {
            for (final Field field : col) {
                if (campos.length() > 0) {
                    campos.append(",");
                }
                campos.append(field.getFieldDB());
            }
        }
        return campos.toString();
    }

    public String getNamesFieldsStr(final String prefix) {
        final Collection<Field> col = this.getFields();
        final StringBuilder campos = new StringBuilder();
        if (col != null) {
            for (final Field field : col) {
                if (campos.length() > 0) {
                    campos.append(",");
                }
                campos.append(prefix);
                campos.append(".");
                campos.append(field.getFieldDB());
            }
        }
        return campos.toString();
    }

    public List<String> getListNamesFieldClass() {
        final Collection<Field> col = this.getFields();
        final List<String> lstRetorno = new ArrayList<>();
        if (col != null) {
            for (final Field field : col) {
                lstRetorno.add(field.getFieldClass());
            }
        }
        return lstRetorno;
    }

    public String getOwner() {
        String valor = Constantes.getValue("OWNER_DB_" + this.getClass().getName());
        if (valor == null) {
            // Se nao encontrar especificao para a Classe, entao pega o Geral.
            valor = Constantes.getValue("OWNER_DB", "");
        }
        return valor;
    }

    /**
     * Constrói uma página de resultado
     *
     * @param result
     *            lista de resultado da página
     * @param pageable
     *            informação da paginação
     * @param totalElements
     *            total de elementos que atendem ao critério de filtragem
     * @return
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     */
    protected <E> Page<E> makePage(final List<E> result, final Pageable pageable, final long totalElements) {
        return new PageImpl<>(result, pageable, totalElements);
    }

    /**
     * Realiza a contagem de objetos de acodro com a query passada {@code sqlCount} e os parametros a serem usado
     *
     * @param sqlCount
     *            SQL para realizar a contagem de objetos
     * @param parametrosArray
     *            parametros a serem usado na filtragem
     * @return
     * @throws PersistenceException
     *             caso ocorra algum problema na filtragem
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     */
    protected Long countElements(final String sqlCount, final Object[] parametrosArray) throws PersistenceException {
        final List<?> result = this.execSQL(sqlCount, parametrosArray);
        final Object[] objects = (Object[]) result.get(0);
        return Long.valueOf(objects[0].toString());
    }

    /**
     * Junta trecho para contagem de registro em {@code fromWherePiece}
     *
     * @param fromWherePiece
     *            trecho a ser complementado na query de contagem ({@code FROM} e {@code WHERE})
     * @return query para contagem de registros
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     */
    protected StringBuilder countQueryPiece(final StringBuilder fromWherePiece) {
        final StringBuilder count = new StringBuilder();
        count.append("SELECT count(*) ");
        count.append(fromWherePiece);
        return count;
    }

}
