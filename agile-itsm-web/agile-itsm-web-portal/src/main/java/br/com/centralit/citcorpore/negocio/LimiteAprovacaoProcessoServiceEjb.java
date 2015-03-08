package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.LimiteAprovacaoProcessoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class LimiteAprovacaoProcessoServiceEjb extends CrudServiceImpl implements LimiteAprovacaoProcessoService {

    private LimiteAprovacaoProcessoDao dao;

    @Override
    protected LimiteAprovacaoProcessoDao getDao() {
        if (dao == null) {
            dao = new LimiteAprovacaoProcessoDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        this.validaUpdate(arg0);
    }

    @Override
    public Collection findByIdLimiteAprovacao(final Integer parm) throws ServiceException, LogicException {
        try {
            return this.getDao().findByIdLimiteAprovacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdLimiteAprovacao(final Integer parm) throws ServiceException, LogicException {
        try {
            this.getDao().deleteByIdLimiteAprovacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
