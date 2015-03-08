package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContratosUnidadesDTO;
import br.com.citframework.service.CrudService;

public interface ContratosUnidadesService extends CrudService {
	
	public Collection<ContratosUnidadesDTO> findByIdUnidade(Integer idUnidade) throws Exception;

}
