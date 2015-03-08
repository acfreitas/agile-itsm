package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO;
import br.com.citframework.service.CrudService;

public interface NotificacaoGrupoService  extends CrudService {
	
	public Collection<NotificacaoGrupoDTO> listaIdGrupo(Integer idNotificacao) throws Exception;

}
