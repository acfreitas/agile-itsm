package br.com.centralit.citcorpore.negocio;

import br.com.citframework.service.CrudService;

public interface TipoUnidadeService extends CrudService {
	boolean jaExisteUnidadeComMesmoNome(String nome); 
}
