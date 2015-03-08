package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.RequisicaoFuncaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RequisicaoFuncaoDao extends CrudDaoDefaultImpl {

    public RequisicaoFuncaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<RequisicaoFuncaoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", true, false, false, false));
        listFields.add(new Field("fase", "fase", false, false, false, false));
        listFields.add(new Field("nomeFuncao", "nomeFuncao", false, false, false, false));
        listFields.add(new Field("numeroPessoas", "numeroPessoas", false, false, false, false));
        listFields.add(new Field("possuiSubordinados", "possuiSubordinados", false, false, false, false));
        listFields.add(new Field("justificativaFuncao", "justificativaFuncao", false, false, false, false));
        listFields.add(new Field("resumoAtividades", "resumoAtividades", false, false, false, false));
        listFields.add(new Field("requisicaoValida", "requisicaoValida", false, false, false, false));
        listFields.add(new Field("justificativaValidacao", "justificativaValidacao", false, false, false, false));
        listFields.add(new Field("complementoJustificativaValidacao", "complementoJustificativaValidacao", false, false, false, false));
        listFields.add(new Field("idCargo", "idCargo", false, false, false, false));
        listFields.add(new Field("funcao", "funcao", false, false, false, false));
        listFields.add(new Field("resumoFuncao", "resumoFuncao", false, false, false, false));
        listFields.add(new Field("descricaoValida", "descricaoValida", false, false, false, false));
        listFields.add(new Field("justificativaDescricaoFuncao", "justificativaDescricaoFuncao", false, false, false, false));
        listFields.add(new Field("complementoJustificativaDescricaoFuncao", "complementoJustificativaDescricaoFuncao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_RequisicaoFuncao";
    }

    @Override
    public Class<RequisicaoFuncaoDTO> getBean() {
        return RequisicaoFuncaoDTO.class;
    }

    @Override
    public Collection<RequisicaoFuncaoDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("idSolicitacaoServico"));
        return super.list(list);
    }

    @Override
    public void updateNotNull(final IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }

    public Collection<RequisicaoFuncaoDTO> retornaFuncoesAprovadas() throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();

        condicao.add(new Condition("fase", "=", "finalizado"));
        ordenacao.add(new Order("nomeFuncao"));

        return super.findByCondition(condicao, ordenacao);
    }

    /**
     * Retorna RequisicaoFuncaoDTO com nomeCargo, de acordo com o idSolicitacaoServico.
     *
     * @param requisicaoFuncaoDto
     * @return RequisicaoFuncaoDTO
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public RequisicaoFuncaoDTO restoreWithNomeCargo(final RequisicaoFuncaoDTO requisicaoFuncaoDto) throws PersistenceException {
        final List<Integer> parametros = new ArrayList<>();

        final List<String> listRetorno = new ArrayList<>(this.getListNamesFieldClass());
        listRetorno.add("nomeCargo");

        final StringBuilder sql = new StringBuilder("SELECT " + this.getNamesFieldsStr("req") + ",cargo.nomecargo ");
        sql.append(" FROM " + this.getTableName() + " req");
        sql.append(" INNER JOIN  cargos cargo ");
        sql.append(" ON req.idcargo = cargo.idcargo ");
        sql.append(" WHERE req.idSolicitacaoServico = ? ");
        parametros.add(requisicaoFuncaoDto.getIdSolicitacaoServico());

        final List retorno = super.execSQL(sql.toString(), parametros.toArray());

        return (RequisicaoFuncaoDTO) this.listConvertion(this.getBean(), retorno, listRetorno).get(0);
    }

    /**
     * @param parm
     * @return
     * @throws Exception
     * @author euler.ramos
     */
    public Collection<RequisicaoFuncaoDTO> findByIdSolicitacao(final Integer idSolicitacaoServico) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        ordenacao.add(new Order("nomeFuncao"));
        return super.findByCondition(condicao, ordenacao);
    }

}
