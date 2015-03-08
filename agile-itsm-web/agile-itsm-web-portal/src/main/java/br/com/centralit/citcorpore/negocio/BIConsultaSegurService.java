package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface BIConsultaSegurService extends CrudService {
	public Collection findByIdPerfilSeguranca(Integer parm) throws Exception;
	public void deleteByIdPerfilSeguranca(Integer parm) throws Exception;
	public Collection findByIdConsulta(Integer parm) throws Exception;
	public void deleteByIdConsulta(Integer parm) throws Exception;
}
