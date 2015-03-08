package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CaracteristicaMonitDTO;
import br.com.citframework.service.CrudService;

/**
 * @author euler.ramos
 *
 */
public interface CaracteristicaMonitService extends CrudService {
	public Collection<CaracteristicaMonitDTO> restoreByIdMonitoramentoAtivos(Integer idMonitoramentoAtivos) throws Exception;
}
