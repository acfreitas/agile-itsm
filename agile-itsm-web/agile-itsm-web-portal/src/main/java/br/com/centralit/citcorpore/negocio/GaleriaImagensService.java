package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface GaleriaImagensService extends CrudService {
	public Collection findByCategoria(Integer idCategoria) throws Exception;
	public Collection listOrderByCategoria() throws Exception;
}