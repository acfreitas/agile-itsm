package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * @author Flávio.santana
 *
 */
public interface PostService extends CrudService {
	
	public Collection listNotNull() throws Exception;
}
