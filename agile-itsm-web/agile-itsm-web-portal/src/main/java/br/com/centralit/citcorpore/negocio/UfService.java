package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.citframework.service.CrudService;

public interface UfService extends CrudService {

	public Collection<UfDTO> listByIdRegioes(UfDTO obj) throws Exception;

	/**
	 * Retorna uma lista de UFs de acordo com o idPais
	 * 
	 * @param obj
	 * @return Collection
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<UfDTO> listByIdPais(UfDTO obj) throws Exception;

}
