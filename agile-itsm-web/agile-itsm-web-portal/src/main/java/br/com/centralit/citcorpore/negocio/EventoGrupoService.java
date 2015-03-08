package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.citframework.service.CrudService;

public interface EventoGrupoService extends CrudService {
	
	Collection<EventoGrupoDTO> listByEvento(Integer idEvento) throws Exception;	
}
