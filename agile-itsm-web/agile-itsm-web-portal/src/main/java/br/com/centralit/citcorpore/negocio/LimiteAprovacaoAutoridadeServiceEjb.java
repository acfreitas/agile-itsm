package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.LimiteAprovacaoAutoridadeDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class LimiteAprovacaoAutoridadeServiceEjb extends CrudServiceImpl implements LimiteAprovacaoAutoridadeService {

    private LimiteAprovacaoAutoridadeDao dao;

    @Override
    protected LimiteAprovacaoAutoridadeDao getDao() {
        if (dao == null) {
            dao = new LimiteAprovacaoAutoridadeDao();
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
