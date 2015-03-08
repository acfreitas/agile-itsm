package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.RequisicaoCertificacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RequisicaoCertificacaoDao extends CrudDaoDefaultImpl {

    public RequisicaoCertificacaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", true, false, false, false));
        listFields.add(new Field("idCertificacao", "idCertificacao", true, false, false, false));
        listFields.add(new Field("obrigatorio", "obrigatorio", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "RH_RequisicaoCertificacao";
    }

    @Override
    public Collection<RequisicaoCertificacaoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<RequisicaoCertificacaoDTO> getBean() {
        return RequisicaoCertificacaoDTO.class;
    }

    @Override
    public Collection<RequisicaoCertificacaoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<RequisicaoCertificacaoDTO> findByIdSolicitacaoServico(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idCertificacao"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdSolicitacaoServico(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

    public Collection<RequisicaoCertificacaoDTO> findByCertificacaoObrigatoria(final Integer idSolicitacaoServico) throws PersistenceException {
        final List<Condition> lstCondicao = new ArrayList<>();
        final List<Order> lstOrder = new ArrayList<>();

        lstCondicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        lstCondicao.add(new Condition("obrigatorio", "=", "S"));

        lstOrder.add(new Order("idCertificacao"));

        return super.findByCondition(lstCondicao, lstOrder);
    }

}
