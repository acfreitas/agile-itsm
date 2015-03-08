package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ResponsavelCentroResultadoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ResponsavelCentroResultadoServiceEjb extends CrudServiceImpl implements ResponsavelCentroResultadoService {

    private ResponsavelCentroResultadoDao dao;

    @Override
    protected ResponsavelCentroResultadoDao getDao() {
        if (dao == null) {
            dao = new ResponsavelCentroResultadoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCentroResultado(final Integer parm) throws ServiceException, LogicException {
        try {
            return this.getDao().findByIdCentroResultado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdResponsavel(final Integer parm) throws ServiceException, LogicException {
        try {
            return this.getDao().findByIdResponsavel(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdCentroResultado(final Integer parm) throws ServiceException, LogicException {
        try {
            this.getDao().deleteByIdCentroResultado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
