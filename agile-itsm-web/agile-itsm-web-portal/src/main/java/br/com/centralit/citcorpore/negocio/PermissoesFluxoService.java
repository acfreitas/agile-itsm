package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.service.CrudService;

public interface PermissoesFluxoService extends CrudService {

    Collection findByIdTipoFluxo(final Integer parm) throws Exception;

    void deleteByIdTipoFluxo(final Integer parm) throws Exception;

    Collection findByIdGrupo(final Integer parm) throws Exception;

    void deleteByIdGrupo(final Integer parm) throws Exception;

    Collection<FluxoDTO> findFluxosByUsuario(final UsuarioDTO usuarioDto) throws Exception;

    PermissoesFluxoDTO findByUsuarioAndFluxo(final UsuarioDTO usuarioDto, final FluxoDTO fluxoDto) throws Exception;

    boolean permissaoGrupoExecutor(final Integer idTipoMudanca, final Integer idGrupoExecutor) throws Exception;

    boolean permissaoGrupoExecutorLiberacao(final Integer idTipoMudanca, final Integer idGrupoExecutor) throws Exception;

    boolean permissaoGrupoExecutorProblema(final Integer idCategoriaProblema, final Integer idGrupoExecutor) throws Exception;

    boolean permissaoGrupoExecutorLiberacaoServico(final Integer idGrupoExecutor, final Integer idTipoFluxo) throws Exception;

    PermissoesFluxoDTO findByIdFluxoAndIdUsuario(final Integer idUsuario, final Integer idItemtrabalho);

}
