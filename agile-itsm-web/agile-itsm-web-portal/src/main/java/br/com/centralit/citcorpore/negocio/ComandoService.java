package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ComandoDTO;
import br.com.citframework.service.CrudService;

/**
 * @author ygor.magalhaes
 *
 */
public interface ComandoService extends CrudService {
	
	public ComandoDTO listItemCadastrado(String descricao) throws Exception;

}
