package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoItemConfigRelDTO;
import br.com.citframework.service.CrudService;

public interface EventoItemConfigRelService extends CrudService {

	public Collection<EventoItemConfigRelDTO> listByEvento(Integer idEvento) throws Exception;
	
}
