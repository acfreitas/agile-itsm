package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.citframework.service.CrudService;

public interface AlcadaCentroResultadoService extends CrudService {
	
	/**
	 * Retorna true ou falso
	 * 
	 * @param obj
	 * @return boolean
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarVinculoCentroResultado(Integer obj) throws Exception;
	public Collection<AlcadaCentroResultadoDTO> findByIdCentroResultado(Integer idCentroResultado) throws Exception;
}
