package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoGrupoMonitDTO;
import br.com.citframework.service.CrudService;

/**
 * @author euler.ramos
 *
 */
public interface NotificacaoGrupoMonitService extends CrudService {
	public Collection<NotificacaoGrupoMonitDTO> restoreByIdMonitoramentoAtivos(Integer idMonitoramentoAtivos) throws Exception;
}
