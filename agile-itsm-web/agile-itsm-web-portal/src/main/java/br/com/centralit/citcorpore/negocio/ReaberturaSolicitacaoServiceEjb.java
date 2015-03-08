package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ReaberturaSolicitacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ReaberturaSolicitacaoServiceEjb extends CrudServiceImpl implements ReaberturaSolicitacaoService {

    private ReaberturaSolicitacaoDao dao;

    @Override
    protected ReaberturaSolicitacaoDao getDao() {
        if (dao == null) {
            dao = new ReaberturaSolicitacaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdSolicitacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
