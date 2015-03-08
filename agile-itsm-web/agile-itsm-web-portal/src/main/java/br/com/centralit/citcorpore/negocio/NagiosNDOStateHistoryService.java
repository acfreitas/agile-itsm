package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface NagiosNDOStateHistoryService extends CrudService {
	public void setJndiName(String jndiNameParm);
	public Collection findByObject_id(Integer parm) throws Exception;
	public void deleteByObject_id(Integer parm) throws Exception;
	public Collection findByHostServiceStatus(String jndiNameParm, String hostName, String serviceName, String status, Date dataInicial, Date dataFinal) throws Exception;
	public Collection findByHostServiceStatusAndServiceNull(String jndiNameParm, String hostName, String status, Date dataInicial, Date dataFinal) throws Exception;	
}
