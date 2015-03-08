package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface ExecucaoAtividadePeriodicaService extends CrudService {
	public void deleteByIdAtividadePeriodica(Integer parm) throws Exception;
	public void deleteByIdEmpregado(Integer parm) throws Exception;
	public Collection findByIdAtividadePeriodica(Integer idAtividadePeriodicaParm) throws Exception;
	public Collection findBlackoutByIdMudancaAndPeriodo(Integer idMudanca, Date dataInicio, Date dataFim) throws Exception;
}
