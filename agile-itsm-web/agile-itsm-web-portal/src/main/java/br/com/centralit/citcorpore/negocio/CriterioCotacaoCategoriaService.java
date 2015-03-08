package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface CriterioCotacaoCategoriaService extends CrudService {
	public Collection findByIdCategoria(Integer parm) throws Exception;
	public void deleteByIdCategoria(Integer parm) throws Exception;
}
