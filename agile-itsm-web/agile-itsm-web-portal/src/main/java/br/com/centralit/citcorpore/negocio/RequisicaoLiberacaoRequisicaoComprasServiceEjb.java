package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoRequisicaoComprasDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoRequisicaoComprasDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoLiberacaoRequisicaoComprasServiceEjb extends CrudServiceImpl implements RequisicaoLiberacaoRequisicaoComprasService {

    private RequisicaoLiberacaoRequisicaoComprasDAO dao;

    @Override
    protected RequisicaoLiberacaoRequisicaoComprasDAO getDao() {
        if (dao == null) {
            dao = new RequisicaoLiberacaoRequisicaoComprasDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdLiberacao(final Integer param) throws Exception {
        try {
            return this.getDao().findByIdLiberacao(param);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdLiberacaoAndDataFim(final Integer idRequisicaoLiberacao) throws Exception {
        try {
            return this.getDao().findByIdLiberacaoAndDataFim(idRequisicaoLiberacao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdLiberacao(final Integer param) throws Exception {
        try {
            this.getDao().deleteByIdRequisicaoLiberacao(param);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public RequisicaoLiberacaoRequisicaoComprasDTO carregaItemRequisicaoComprasByidItemRequisicaProduto(final Integer idItemrRequisicaoProduto) throws Exception {
        return this.getDao().carregaItemRequisicaoComprasByidItemRequisicaProduto(idItemrRequisicaoProduto);
    }

}
