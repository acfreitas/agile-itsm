package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface FaixaValoresRecursoService extends CrudService {
	public Collection findByIdRecurso(Integer parm) throws Exception;
	public void deleteByIdRecurso(Integer parm) throws Exception;
}
