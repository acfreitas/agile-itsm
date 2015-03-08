package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.service.CrudService;
public interface PerfilAcessoSituacaoFaturaService extends CrudService {
	public Collection findByIdPerfil(Integer parm) throws Exception;
	public void deleteByIdPerfil(Integer parm) throws Exception;
	public Collection findBySituacaoFatura(String parm) throws Exception;
	public void deleteBySituacaoFatura(String parm) throws Exception;
	public Collection getSituacoesFaturaPermitidasByUsuario(UsuarioDTO usuario) throws Exception;
	public Collection getSituacoesFaturaPermitidasByGrupo(PerfilAcessoGrupoDTO perfilAcessoGrupoDTO) throws Exception;
}
