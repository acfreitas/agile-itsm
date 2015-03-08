package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.citframework.service.CrudService;


public interface PerfilAcessoGrupoService extends CrudService {

    public PerfilAcessoGrupoDTO listByIdGrupo(PerfilAcessoGrupoDTO obj) throws Exception;

	public boolean existeGrupoVinculadoPerfil(Integer idPerfilAcesso) throws Exception;

}
