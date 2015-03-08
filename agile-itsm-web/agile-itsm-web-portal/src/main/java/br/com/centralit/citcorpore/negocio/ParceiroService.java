package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ParceiroDTO;
import br.com.citframework.service.CrudService;

/**
 * @author david.silva
 *
 */
public interface ParceiroService extends CrudService{
	
	public Collection<ParceiroDTO> consultarFornecedorPorRazaoSocialAutoComplete(String razaoSocial) throws Exception;

}
