package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface TimeSheetProjetoService extends CrudService {
	public Collection findByIdRecursoTarefaLinBaseProj(Integer idRecursoTarefaLinBaseProj, Integer idEmpregado) throws Exception;
	public void deleteByIdRecursoTarefaLinBaseProj(Integer parm) throws Exception;
}
