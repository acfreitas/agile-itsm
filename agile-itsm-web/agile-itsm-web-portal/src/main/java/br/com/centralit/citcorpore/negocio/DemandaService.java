package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface DemandaService extends CrudService {
	public Collection findByIdOS(Integer parm) throws Exception;
}
