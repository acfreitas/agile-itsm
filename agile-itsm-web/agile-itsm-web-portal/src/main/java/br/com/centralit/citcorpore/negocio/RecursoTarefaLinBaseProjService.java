package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface RecursoTarefaLinBaseProjService extends CrudService {
	public Collection findByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception;
	public void deleteByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception;
	public Collection findByIdEmpregado(Integer parm) throws Exception;
	public void deleteByIdEmpregado(Integer parm) throws Exception;
}
