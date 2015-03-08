package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class TriagemRequisicaoPessoalDao extends CrudDaoDefaultImpl {

    public TriagemRequisicaoPessoalDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idTriagem", "idTriagem", true, true, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));
        listFields.add(new Field("idItemTrabalhoEntrevistaRH", "idItemTrabalhoEntrevistaRH", false, false, false, false));
        listFields.add(new Field("idItemTrabalhoEntrevistaGestor", "idItemTrabalhoEntrevistaGestor", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "RH_TriagemRequisicaoPessoal";
    }

    @Override
    public Collection<TriagemRequisicaoPessoalDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("idTriagem"));
        return super.list(list);
    }

    @Override
    public Class<TriagemRequisicaoPessoalDTO> getBean() {
        return TriagemRequisicaoPessoalDTO.class;
    }

    @Override
    public Collection<TriagemRequisicaoPessoalDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<TriagemRequisicaoPessoalDTO> findByIdSolicitacaoServico(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idTriagem"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<TriagemRequisicaoPessoalDTO> findDisponiveisEntrevistaRH(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        condicao.add(new Condition("idItemTrabalhoEntrevistaRH", "is", null));
        ordenacao.add(new Order("idTriagem"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<TriagemRequisicaoPessoalDTO> findDisponiveisEntrevistaGestor(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        condicao.add(new Condition("idItemTrabalhoEntrevistaGestor", "is", null));
        ordenacao.add(new Order("idTriagem"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<TriagemRequisicaoPessoalDTO> findByIdItemTrabalhoRH(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idItemTrabalhoEntrevistaRH", "=", parm));
        ordenacao.add(new Order("idTriagem"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<TriagemRequisicaoPessoalDTO> findByIdItemTrabalhoGestor(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idItemTrabalhoEntrevistaGestor", "=", parm));
        ordenacao.add(new Order("idTriagem"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdSolicitacaoServico(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

    public boolean candidatoParticipaProcessoSelecao(final Integer idCurriculo, final Integer idSolicitacaoServico) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        sql.append("select distinct solicitacaoservico.idsolicitacaoservico ");
        sql.append("from rh_triagemrequisicaopessoal join solicitacaoservico on ");
        sql.append("solicitacaoservico.idsolicitacaoservico=rh_triagemrequisicaopessoal.idsolicitacaoservico and ");
        sql.append("(solicitacaoservico.situacao not in ('" + br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Resolvida + "','"
                + br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Fechada + "','"
                + br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Cancelada + "')) and ");
        sql.append("rh_triagemrequisicaopessoal.idcurriculo=? and solicitacaoservico.idsolicitacaoservico<>?");
        parametro.add(idCurriculo);
        parametro.add(idSolicitacaoServico);

        final List list = this.execSQL(sql.toString(), parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return list.size() > 0;
        }
        return false;
    }

}
