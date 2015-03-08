package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.NotificacaoUsuarioMonitDTO;
import br.com.citframework.service.CrudService;

/**
 * @author euler.ramos
 *
 */
public interface NotificacaoUsuarioMonitService extends CrudService {
	public Collection<NotificacaoUsuarioMonitDTO> restoreByIdMonitoramentoAtivos(Integer idMonitoramentoAtivos) throws Exception;
}
