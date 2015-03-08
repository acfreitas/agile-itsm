package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface NagiosNDOObjectService extends CrudService {
	public Collection findByName1(String parm) throws Exception;
	public void deleteByName1(String parm) throws Exception;
	public Collection findByName2(String parm) throws Exception;
	public void deleteByName2(String parm) throws Exception;
}
