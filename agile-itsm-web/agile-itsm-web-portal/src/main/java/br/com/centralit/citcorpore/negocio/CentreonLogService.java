package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface CentreonLogService extends CrudService {
	public void setJndiName(String jndiNameParm);
	public Collection findByHostServiceStatus(String jndiNameParm, String hostName, String serviceName, String status, Date dataInicial, Date dataFinal) throws Exception;
	public Collection findByHostServiceStatusAndServiceNull(String jndiNameParm, String hostName, String status, Date dataInicial, Date dataFinal) throws Exception;
}
