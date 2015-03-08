/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoPastaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoPastaDAO;
import br.com.centralit.citcorpore.util.Enumerados.PermissaoAcessoPasta;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;

/**
 * EJB de PerfilAcessoPasta.
 *
 * @author valdoilo.damasceno
 */
public class PerfilAcessoPastaServiceEjb extends CrudServiceImpl implements PerfilAcessoPastaService {

    private PerfilAcessoPastaDAO dao;

    @Override
    protected PerfilAcessoPastaDAO getDao() {
        if (dao == null) {
            dao = new PerfilAcessoPastaDAO();
        }
        return dao;
    }

    private PastaService pastaService;

    private PastaService getPastaService() throws Exception {
        if (pastaService == null) {
            pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
        }
        return pastaService;
    }

    @Override
    public boolean verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(final UsuarioDTO usuario, final Integer idPasta) throws Exception {
        return this.getDao().usuarioAprovaBaseConhecimentoParaPastaSelecionada(usuario, idPasta);
    }

    @Override
    public List<PerfilAcessoPastaDTO> validaPasta(final UsuarioDTO usuario) throws Exception {
        return this.getDao().validaPasta(usuario);
    }

    @Override
    public Collection<PerfilAcessoPastaDTO> findByIdPasta(final Integer idPasta) throws Exception {
        return this.getDao().findByIdPasta(idPasta);
    }

    @Override
    public Collection<PerfilAcessoPastaDTO> listByIdPasta(final Integer idPasta) throws Exception {
        return this.getDao().listByIdPasta(idPasta);
    }

    @Override
    public PermissaoAcessoPasta verificarPermissaoDeAcessoPasta(final UsuarioDTO usuario, PastaDTO pastaDto) throws Exception {
        pastaDto = this.getPastaService().obterHerancaDePermissao(pastaDto);
        PermissaoAcessoPasta permissao = null;
        if (pastaDto != null) {
            permissao = this.getDao().verificarPermissaoDeAcessoPasta(usuario, pastaDto.getId());
        }

        return permissao;
    }

}
