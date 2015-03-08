package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ValorLimiteAprovacaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ValorLimiteAprovacaoServiceEjb extends CrudServiceImpl implements ValorLimiteAprovacaoService {

    private ValorLimiteAprovacaoDao dao;

    @Override
    protected ValorLimiteAprovacaoDao getDao() {
        if (dao == null) {
            dao = new ValorLimiteAprovacaoDao();
        }
        return dao;
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
