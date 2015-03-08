package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ScriptMonitDTO;
import br.com.citframework.service.CrudService;

/**
 * @author euler.ramos
 *
 */
public interface ScriptMonitService extends CrudService {
	public Collection<ScriptMonitDTO> restoreByIdMonitoramentoAtivos(Integer idMonitoramentoAtivos) throws Exception;
}
