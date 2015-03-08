package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ConhecimentoSolicitacaoDTO;
import br.com.centralit.citcorpore.integracao.ConhecimentoSolicitacaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

public class ConhecimentoSolicitacaoServiceEjb extends CrudServiceImpl implements ConhecimentoSolicitacaoService {

    private ConhecimentoSolicitacaoDao dao;

    @Override
    protected ConhecimentoSolicitacaoDao getDao() {
        if (dao == null) {
            dao = new ConhecimentoSolicitacaoDao();
        }
        return dao;
    }

    @Override
    public void rollbackTransaction(final TransactionControler tc, final Exception ex) throws ServiceException, LogicException {
        super.rollbackTransaction(tc, ex);
    }

    @Override
    public ConhecimentoSolicitacaoDTO restoreAll(final Integer idSolicitacaoServico) throws Exception {
        return null;
    }

    @Override
    public Collection findBySolictacaoServico(final ConhecimentoSolicitacaoDTO bean) throws ServiceException, LogicException {
        try {
            return this.getDao().findByidSolicitacaoServico(bean.getIdSolicitacaoServico());
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
