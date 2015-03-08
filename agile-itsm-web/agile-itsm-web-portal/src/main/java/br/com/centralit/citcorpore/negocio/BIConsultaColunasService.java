package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface BIConsultaColunasService extends CrudService {
	public Collection findByIdConsulta(Integer parm) throws Exception;
	public void deleteByIdConsulta(Integer parm) throws Exception;
	public Collection findByOrdem(Integer parm) throws Exception;
	public void deleteByOrdem(Integer parm) throws Exception;
}
