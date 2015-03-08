/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.integracao.ContratosGruposDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;

/**
 * @author Centralit
 *
 */
@SuppressWarnings({"unchecked"})
public class ContratosGruposServiceEjb extends CrudServiceImpl implements ContratosGruposService {

    private ContratosGruposDAO dao;

    @Override
    protected ContratosGruposDAO getDao() {
        if (dao == null) {
            dao = new ContratosGruposDAO();
        }
        return dao;
    }

    @Override
    public Collection<ContratosGruposDTO> findByIdGrupo(final Integer idGrupo) throws Exception {
        try {
            return this.getDao().findByIdGrupo(idGrupo);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ContratosGruposDTO> findByIdEmpregado(final Integer idEmpregado) throws Exception {
        final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

        try {
            final Collection<GrupoDTO> gruposEmpregado = grupoService.getGruposByPessoa(idEmpregado);
            return this.getDao().findByGrupos(gruposEmpregado);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdGrupo(final Integer idGrupo) throws Exception {
        try {
            this.getDao().deleteByIdGrupo(idGrupo);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ContratosGruposDTO> findByGrupos(final Collection<GrupoDTO> gruposEmpregado) throws Exception {
        try {
            return this.getDao().findByGrupos(gruposEmpregado);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<ContratosGruposDTO> findByIdContrato(final Integer idContrato) throws Exception {
        try {
            return this.getDao().findByIdContrato(idContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdContrato(final Integer idContrato) throws Exception {
        try {
            this.getDao().deleteByIdContrato(idContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean hasContrato(final Collection<GrupoEmpregadoDTO> gruposEmpregado, final ContratoDTO contrato) throws Exception {
        try {
            return this.getDao().hasContrato(gruposEmpregado, contrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
