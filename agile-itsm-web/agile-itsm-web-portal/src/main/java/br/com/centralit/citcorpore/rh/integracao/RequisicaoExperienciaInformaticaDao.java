package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.RequisicaoExperienciaInformaticaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RequisicaoExperienciaInformaticaDao extends CrudDaoDefaultImpl {

    public RequisicaoExperienciaInformaticaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", true, false, false, false));
        listFields.add(new Field("idExperienciaInformatica", "idExperienciaInformatica", true, false, false, false));
        listFields.add(new Field("obrigatorio", "obrigatorio", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "RH_RequisicaoExperienciaInformatica";
    }

    @Override
    public Collection<RequisicaoExperienciaInformaticaDTO> list() throws PersistenceException {
        return null;
    }

    public List<RequisicaoExperienciaInformaticaDTO> findByIdFuncao(final Integer IdFuncao) throws PersistenceException {
        final List<Integer> paramentros = new ArrayList<>();
        final List<String> fields = new ArrayList<>();

        final String sql = "SELECT b.idexperienciainformatica, idsolicitacaoServico, descricao, detalhe, obrigatorio FROM rh_requisicaoexperienciainformatica a inner join rh_experienciainformatica b on a.idexperienciainformatica = b.idexperienciainformatica where idsolicitacaoservico = ? order by descricao  ";

        paramentros.add(IdFuncao);

        final List dados = this.execSQL(sql, paramentros.toArray());

        fields.add("idExperienciaInformatica");
        fields.add("idSolicitacaoServico");
        fields.add("descricao");
        fields.add("detalhe");
        fields.add("obrigatorio");

        super.getListNamesFieldClass();

        return this.listConvertion(this.getBean(), dados, fields);
    }

    @Override
    public Class<RequisicaoExperienciaInformaticaDTO> getBean() {
        return RequisicaoExperienciaInformaticaDTO.class;
    }

    @Override
    public Collection<RequisicaoExperienciaInformaticaDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<RequisicaoExperienciaInformaticaDTO> findByIdSolicitacaoServico(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idExperienciaInformatica"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdSolicitacaoServico(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

}
