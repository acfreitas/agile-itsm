package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface LiberacaoProblemaService extends CrudService {
	public Collection findByIdLiberacao(Integer parm) throws Exception;
	public void deleteByIdLiberacao(Integer parm) throws Exception;
}
