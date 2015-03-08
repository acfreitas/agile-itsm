package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.DelegacaoCentroResultadoFluxoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class DelegacaoCentroResultadoFluxoServiceEjb extends CrudServiceImpl implements DelegacaoCentroResultadoFluxoService {

    private DelegacaoCentroResultadoFluxoDao dao;

    @Override
    protected DelegacaoCentroResultadoFluxoDao getDao() {
        if (dao == null) {
            dao = new DelegacaoCentroResultadoFluxoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdDelegacaoCentroResultado(final Integer parm) throws ServiceException, LogicException {
        try {
            return this.getDao().findByIdDelegacaoCentroResultado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdDelegacaoCentroResultado(final Integer parm) throws ServiceException, LogicException {
        try {
            this.getDao().deleteByIdDelegacaoCentroResultado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
