package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.citframework.service.CrudService;

public interface NotificacaoUsuarioService extends CrudService {

	/**
	 * Retorar uma lista de idusuario
	 * @param idNotificacao
	 * @return
	 * @throws Exception
	 */
	public Collection<NotificacaoUsuarioDTO> listaIdUsuario(Integer idNotificacao) throws Exception;

}
