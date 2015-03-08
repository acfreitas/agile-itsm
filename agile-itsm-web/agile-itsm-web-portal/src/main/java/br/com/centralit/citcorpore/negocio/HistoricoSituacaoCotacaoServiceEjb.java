package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.HistoricoSituacaoCotacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class HistoricoSituacaoCotacaoServiceEjb extends CrudServiceImpl implements HistoricoSituacaoCotacaoService {

    private HistoricoSituacaoCotacaoDao dao;

    @Override
    protected HistoricoSituacaoCotacaoDao getDao() {
        if (dao == null) {
            dao = new HistoricoSituacaoCotacaoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCotacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCotacao(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdCotacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdResponsavel(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdResponsavel(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdResponsavel(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdResponsavel(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
