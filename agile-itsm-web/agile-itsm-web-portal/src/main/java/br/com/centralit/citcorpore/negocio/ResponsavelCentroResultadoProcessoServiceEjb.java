package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ResponsavelCentroResultadoProcessoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ResponsavelCentroResultadoProcessoServiceEjb extends CrudServiceImpl implements ResponsavelCentroResultadoProcessoService {

    private ResponsavelCentroResultadoProcessoDao dao;

    @Override
    protected ResponsavelCentroResultadoProcessoDao getDao() {
        if (dao == null) {
            dao = new ResponsavelCentroResultadoProcessoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdCentroResultadoAndIdResponsavel(final Integer idCentroResultado, final Integer idResponsavel) throws Exception {
        try {
            return this.getDao().findByIdCentroResultadoAndIdResponsavel(idCentroResultado, idResponsavel);
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
