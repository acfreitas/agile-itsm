package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.VinculaOsIncidenteDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 *
 * @author rodrigo.oliveira
 *
 */
public class VinculaOsIncidenteServiceEjb extends CrudServiceImpl implements VinculaOsIncidenteService {

    private VinculaOsIncidenteDao dao;

    @Override
    protected VinculaOsIncidenteDao getDao() {
        if (dao == null) {
            dao = new VinculaOsIncidenteDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdOS(final Integer idOS) throws Exception {
        try {
            return this.getDao().findByIdOS(idOS);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdOs(final Integer idOS) throws Exception {
        try {
            this.getDao().deleteByIdOs(idOS);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdAtividadeOS(final Integer idAtividadeOS) throws Exception {
        try {
            this.getDao().deleteByIdAtividadeOS(idAtividadeOS);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean verificaServicoSelecionado(final Integer idServicoContratoContabil) throws Exception {
        try {
            return this.getDao().verificaServicoSelecionado(idServicoContratoContabil);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean verificaServicoJaVinculado(final Integer idOS, final Integer idServicoContratoContabil) throws Exception {
        try {
            return this.getDao().verificaServicoJaVinculado(idOS, idServicoContratoContabil);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdAtividadeOS(final Integer idAtividadeOS) throws Exception {
        try {
            return this.getDao().findByIdAtividadeOS(idAtividadeOS);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
