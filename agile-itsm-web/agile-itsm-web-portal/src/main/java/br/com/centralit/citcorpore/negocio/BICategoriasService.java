package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface BICategoriasService extends CrudService {
	public Collection findByIdCategoriaPai(Integer parm) throws Exception;
	public Collection findSemPai() throws Exception;	
}
