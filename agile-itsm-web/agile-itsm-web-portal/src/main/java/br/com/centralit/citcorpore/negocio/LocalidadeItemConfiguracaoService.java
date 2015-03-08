package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.LocalidadeItemConfiguracaoDTO;
import br.com.citframework.service.CrudService;

public interface LocalidadeItemConfiguracaoService extends CrudService {
	
	public LocalidadeItemConfiguracaoDTO listByIdRegiao(LocalidadeItemConfiguracaoDTO obj) throws Exception;
	public LocalidadeItemConfiguracaoDTO listByIdUf(LocalidadeItemConfiguracaoDTO obj) throws Exception;
	

}
