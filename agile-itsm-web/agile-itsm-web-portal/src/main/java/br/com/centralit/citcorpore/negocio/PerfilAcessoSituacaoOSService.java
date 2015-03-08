package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.service.CrudService;
public interface PerfilAcessoSituacaoOSService extends CrudService {
	
	public Collection findByIdPerfil(Integer parm) throws Exception;
	public void deleteByIdPerfil(Integer parm) throws Exception;
	public Collection findBySituacaoOs(Integer parm) throws Exception;
	public void deleteBySituacaoOs(Integer parm) throws Exception;
	public Collection getSituacoesOSPermitidasByUsuario(UsuarioDTO usuario) throws Exception;
	public Collection getSituacoesOSPermitidasByGrupo(PerfilAcessoGrupoDTO perfilAcessoGrupoDTO) throws Exception;
	
}
