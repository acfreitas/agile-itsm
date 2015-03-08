package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class FluxoServicoServiceEjb extends CrudServiceImpl implements FluxoServicoService {

    private FluxoServicoDao dao;

    @Override
    protected FluxoServicoDao getDao() {
        if (dao == null) {
            dao = new FluxoServicoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdServicoContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public FluxoServicoDTO findPrincipalByIdServicoContrato(final Integer idServicoContrato) throws Exception {
        try {
            return this.getDao().findPrincipalByIdServicoContrato(idServicoContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean validarFluxoServico(final FluxoServicoDTO fluxoServicoDTO) throws Exception {
        try {
            return this.getDao().validarFluxoServico(fluxoServicoDTO);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdFluxoServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdFluxoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
