package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AtividadesServicoContratoDao extends CrudDaoDefaultImpl {

    public AtividadesServicoContratoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> fields = new ArrayList<>();
        fields.add(new Field("idAtividadeServicoContrato", "idAtividadeServicoContrato", true, true, false, false));
        fields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
        fields.add(new Field("descricaoAtividade", "descricaoAtividade", false, false, false, false));
        fields.add(new Field("obsAtividade", "obsAtividade", false, false, false, false));
        fields.add(new Field("custoAtividade", "custoAtividade", false, false, false, false));
        fields.add(new Field("complexidade", "complexidade", false, false, false, false));
        fields.add(new Field("hora", "hora", false, false, false, false));
        fields.add(new Field("quantidade", "quantidade", false, false, false, false));
        fields.add(new Field("periodo", "periodo", false, false, false, false));
        fields.add(new Field("formula", "formula", false, false, false, false));
        fields.add(new Field("contabilizar", "contabilizar", false, false, false, false));
        fields.add(new Field("idServicoContratoContabil", "idServicoContratoContabil", false, false, false, false));
        fields.add(new Field("deleted", "deleted", false, false, false, false));
        fields.add(new Field("tipoCusto", "tipoCusto", false, false, false, false));
        fields.add(new Field("estruturaFormulaOs", "estruturaFormulaOs", false, false, false, false));
        fields.add(new Field("formulaCalculoFinal", "formulaCalculoFinal", false, false, false, false));
        return fields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "AtividadesServicoContrato";
    }

    @Override
    public Collection<AtividadesServicoContratoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<AtividadesServicoContratoDTO> getBean() {
        return AtividadesServicoContratoDTO.class;
    }

    @Override
    public Collection<AtividadesServicoContratoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<AtividadesServicoContratoDTO> findByIdServicoContrato(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idServicoContrato", "=", parm));
        ordenacao.add(new Order("idAtividadeServicoContrato"));
        return super.findByCondition(condicao, ordenacao);
    }

    /**
     * Retorna Lita de Atividades Servico Contrato Ativas pelo
     * idServicoContrato.
     *
     * @param idServicoContrato
     * @return atividadesServicoContrato
     * @throws Exception
     */
    public Collection<AtividadesServicoContratoDTO> obterAtividadesAtivasPorIdServicoContrato(final Integer idServicoContrato) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idServicoContrato", "=", idServicoContrato));
        ordenacao.add(new Order("idAtividadeServicoContrato"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdServicoContrato(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idServicoContrato", "=", parm));
        super.deleteByCondition(condicao);
    }

    /**
     * @param idServicoContrato
     * @throws PersistenceException
     * @author cledson.junior
     */
    public void updateAtividadesServicoContrato(final Integer idServicoContrato) throws PersistenceException {
        final List<Object> parametros = new ArrayList<>();
        parametros.add("y");
        parametros.add(idServicoContrato);
        final String sql = "UPDATE " + this.getTableName() + " SET deleted = ? WHERE idServicoContrato = ?";
        this.execUpdate(sql, parametros.toArray());
    }

    public Collection<AtividadesServicoContratoDTO> listarPorFormula() throws PersistenceException {
        final StringBuilder sql = new StringBuilder();

        sql.append("SELECT idatividadeservicocontrato, ");
        sql.append("       idservicocontrato, ");
        sql.append("       custoatividade, ");
        sql.append("       complexidade, ");
        sql.append("       hora, ");
        sql.append("       quantidade, ");
        sql.append("       periodo, ");
        sql.append("       deleted, ");
        sql.append("       formula, ");
        sql.append("       estruturaformulaos, ");
        sql.append("       formulacalculofinal ");
        sql.append("FROM   atividadesservicocontrato ");
        sql.append("WHERE  tipocusto = 'F' ");
        sql.append("       AND estruturaformulaos IS NULL");

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idAtividadeServicoContrato");
        listRetorno.add("idServicoContrato");
        listRetorno.add("custoAtividade");
        listRetorno.add("complexidade");
        listRetorno.add("hora");
        listRetorno.add("quantidade");
        listRetorno.add("periodo");
        listRetorno.add("deleted");
        listRetorno.add("formula");
        listRetorno.add("estruturaFormulaOs");
        listRetorno.add("formulaCalculoFinal");

        final List<?> lista = this.execSQL(sql.toString(), null);

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(AtividadesServicoContratoDTO.class, lista, listRetorno);
        }
        return null;

    }
}
