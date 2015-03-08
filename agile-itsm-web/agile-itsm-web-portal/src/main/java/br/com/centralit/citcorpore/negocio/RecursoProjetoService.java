package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface RecursoProjetoService extends CrudService {
	public Collection findByIdProjeto(Integer parm) throws Exception;
	public void deleteByIdProjeto(Integer parm) throws Exception;
	public Collection findByIdEmpregado(Integer parm) throws Exception;
	public void deleteByIdEmpregado(Integer parm) throws Exception;
}
