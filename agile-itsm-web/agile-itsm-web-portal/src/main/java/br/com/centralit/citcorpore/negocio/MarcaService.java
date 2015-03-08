package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.MarcaDTO;
import br.com.citframework.service.CrudService;


@SuppressWarnings("rawtypes")
public interface MarcaService extends CrudService {
	
	
	public Collection findByIdFabricante(Integer parm) throws Exception;
	
	public void deleteByIdFabricante(Integer parm) throws Exception;
	
	public boolean consultarMarcas(MarcaDTO marca) throws Exception;
}
