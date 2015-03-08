package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.citframework.service.CrudService;

public interface LocalidadeService extends CrudService {

	/**
	 * Retonar true ou false caso localidade ja existente
	 * 
	 * @param obj
	 * @return true - false
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarLocalidadeAtiva(LocalidadeDTO obj) throws Exception;

	/**
	 * Retorna lista de localidades
	 * 
	 * @param
	 * @return Collection
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<LocalidadeDTO> listLocalidade() throws Exception;

}
