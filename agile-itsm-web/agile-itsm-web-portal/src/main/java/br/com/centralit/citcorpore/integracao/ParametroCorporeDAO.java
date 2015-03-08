/**
 *
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author valdoilo.damasceno
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ParametroCorporeDAO extends CrudDaoDefaultImpl {

    private final String SQL_UPDATE_PARAMETRO = "update " + getTableName() + " set valor = ? where IDPARAMETROCORPORE = ?";
    private final String SQL_GET_PARAMETRO_CITCORPORE = "SELECT IDPARAMETROCORPORE, NOMEPARAMETROCORPORE, VALOR, IDEMPRESA, DATAINICIO, DATAFIM FROM parametrocorpore ";

    public ParametroCorporeDAO() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<ParametroCorporeDTO> find(IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("IDPARAMETROCORPORE", "id", true, false, false, true));
        listFields.add(new Field("NOMEPARAMETROCORPORE", "nome", false, false, false, true, "Nome Parâmetro!"));
        listFields.add(new Field("VALOR", "valor", false, false, false, false));
        listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
        listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
        listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
        listFields.add(new Field("tipodado", "tipoDado", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "PARAMETROCORPORE";
    }

    @Override
    public Collection list() throws PersistenceException {
        List<Order> list = new ArrayList<>();
        list.add(new Order("id"));
        return super.list(list);
    }

    /**
     * @param id
     *            Integer IdParamentro do Sistema
     * @param NomeParametro
     *            String Nome Paramentro do Sistema
     * @return Lista
     * @throws Exception
     * @author Maycon.Fernandes
     */
    public ParametroCorporeDTO getParamentroAtivo(Integer id) throws PersistenceException {
        List objs = new ArrayList();
        objs.add(id);

        String sql = SQL_GET_PARAMETRO_CITCORPORE;

        sql += " WHERE ";
        sql += " (IDPARAMETROCORPORE = ?) AND (DATAFIM IS NULL) ";

        sql += " ORDER BY NOMEPARAMETROCORPORE, DATAINICIO DESC, DATAFIM";
        List lista = this.execSQL(sql, objs.toArray());

        List listRetorno = this.prepararListaDeRetorno();

        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        if (result == null || result.size() == 0) {
            return null;
        }
        return (ParametroCorporeDTO) result.get(0);
    }

    public List pesquisarParamentro(Integer id, String NomeParametro) throws PersistenceException {
        List<Integer> objs = new ArrayList<>();
        objs.add(id);

        StringBuilder sql = new StringBuilder(SQL_GET_PARAMETRO_CITCORPORE);

        sql.append(" WHERE ");
        sql.append(" (IDPARAMETROCORPORE = ?) ");

        sql.append(" ORDER BY NOMEPARAMETROCORPORE");
        List lista = this.execSQL(sql.toString(), objs.toArray());

        List listRetorno = this.prepararListaDeRetorno();

        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        if (result == null || result.size() == 0) {
            return null;
        }
        return result;
    }

    @Override
    public Class<ParametroCorporeDTO> getBean() {
        return ParametroCorporeDTO.class;
    }

    @Override
    public void updateNotNull(IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

    private List prepararListaDeRetorno() {
        List<String> listRetorno = new ArrayList<>();
        listRetorno.add("id");
        listRetorno.add("nome");
        listRetorno.add("valor");
        listRetorno.add("idEmpresa");
        listRetorno.add("dataInicio");
        listRetorno.add("dataFim");
        return listRetorno;
    }

    public Collection findByID(ParametroCorporeDTO parametroCorporeDTO) throws PersistenceException {
        List<Condition> condicao = new ArrayList<>();
        List<Order> ordenacao = new ArrayList<>();

        condicao.add(new Condition("id", "=", parametroCorporeDTO.getId()));
        ordenacao.add(new Order("id"));
        condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
        return super.findByCondition(condicao, ordenacao);
    }

    /**
     * Atualiza o valor do parametro pelo id
     *
     */
    public void atualizarParametro(Integer id, String valor) {
        List<Object> parametros = new ArrayList<>();
        parametros.add(valor);
        parametros.add(id);

        try {
            super.execUpdate(SQL_UPDATE_PARAMETRO, parametros.toArray());
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

}
