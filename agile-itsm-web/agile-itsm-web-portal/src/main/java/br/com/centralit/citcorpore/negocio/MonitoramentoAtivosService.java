package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.MonitoramentoAtivosDTO;
import br.com.citframework.service.CrudService;

/**
 * @author euler.ramos
 *
 */
public interface MonitoramentoAtivosService extends CrudService {
	public MonitoramentoAtivosDTO obterMonitorametoAtivoDaCaracteristica(Integer idTipoItemConfiguracao, Integer idCaracteristica) throws Exception;
	public MonitoramentoAtivosDTO obterMonitorametoAtivoDoTipoItemConfiguracao(Integer idTipoItemConfiguracao) throws Exception;
}
