package br.com.centralit.citcorpore.negocio;

import br.com.citframework.service.CrudService;

public interface InventarioXMLService  extends CrudService {
	public boolean inventarioAtualizado (String ip, java.util.Date dataExpiracao) throws Exception;
}