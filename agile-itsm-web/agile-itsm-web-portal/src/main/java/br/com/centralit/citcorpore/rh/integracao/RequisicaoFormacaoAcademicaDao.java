package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.RequisicaoFormacaoAcademicaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RequisicaoFormacaoAcademicaDao extends CrudDaoDefaultImpl {

    public RequisicaoFormacaoAcademicaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<RequisicaoFormacaoAcademicaDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", true, false, false, false));
        listFields.add(new Field("idFormacaoAcademica", "idFormacaoAcademica", true, false, false, false));
        listFields.add(new Field("obrigatorio", "obrigatorio", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_RequisicaoFormacaoAcademica";
    }

    @Override
    public Class<RequisicaoFormacaoAcademicaDTO> getBean() {
        return RequisicaoFormacaoAcademicaDTO.class;
    }

    @Override
    public Collection<RequisicaoFormacaoAcademicaDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("idFormacaoAcademica"));
        return super.list(list);
    }

    public Collection<RequisicaoFormacaoAcademicaDTO> findByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws PersistenceException {
        final List<Condition> lstCondicao = new ArrayList<>();
        final List<Order> lstOrder = new ArrayList<>();

        lstCondicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        lstOrder.add(new Order("idFormacaoAcademica"));

        return super.findByCondition(lstCondicao, lstOrder);
    }

    public void deleteByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws PersistenceException {
        final Condition cond = new Condition("idSolicitacaoServico", "=", idSolicitacaoServico);
        final List<Condition> lstCond = new ArrayList<>();
        lstCond.add(cond);

        super.deleteByCondition(lstCond);
    }

    public Collection<RequisicaoFormacaoAcademicaDTO> findByFormacaoAcademicaObrigatoria(final Integer idSolicitacaoServico) throws PersistenceException {
        final List<Condition> lstCondicao = new ArrayList<>();
        final List<Order> lstOrder = new ArrayList<>();

        lstCondicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        lstCondicao.add(new Condition("obrigatorio", "=", "S"));

        lstOrder.add(new Order("idFormacaoAcademica"));

        return super.findByCondition(lstCondicao, lstOrder);
    }

}
