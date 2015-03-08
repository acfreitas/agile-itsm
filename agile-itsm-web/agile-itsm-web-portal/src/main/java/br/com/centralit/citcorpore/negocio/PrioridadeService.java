package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.citframework.service.CrudService;

/**
 * @author leandro.viana
 * 
 */
public interface PrioridadeService extends CrudService {
	public Collection<PrioridadeDTO> prioridadesAtivasPorNome(String nome);

	public Collection prioridadesAtivas();
}
