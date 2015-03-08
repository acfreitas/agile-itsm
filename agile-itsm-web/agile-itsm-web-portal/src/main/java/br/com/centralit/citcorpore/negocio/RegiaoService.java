package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.RegiaoDTO;
import br.com.citframework.service.CrudService;

public interface RegiaoService  extends CrudService{
	
	public  RegiaoDTO listByIdRegiao(RegiaoDTO obj) throws Exception;

}
