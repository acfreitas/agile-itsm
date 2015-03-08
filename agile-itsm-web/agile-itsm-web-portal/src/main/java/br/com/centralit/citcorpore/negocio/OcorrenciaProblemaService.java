package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * 
 * @author geber.costa
 *
 */
@SuppressWarnings("rawtypes")
public interface OcorrenciaProblemaService extends CrudService {
	
	public Collection findByIdProblema(Integer idProblema
			) throws Exception;
}


