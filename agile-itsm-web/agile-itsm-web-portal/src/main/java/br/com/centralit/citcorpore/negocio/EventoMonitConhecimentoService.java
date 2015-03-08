/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

/**
 * @author Vadoilo Damasceno
 * 
 */
public interface EventoMonitConhecimentoService extends CrudService {

	/**
	 * Exclui EventoMonitConhecimento por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void deleteByIdConhecimento(Integer idBaseConhecimento, TransactionControler transactionControler) throws Exception;

	/**
	 * Cria novo EventoMonitConhecimento.
	 * 
	 * @param eventoMonitConhecimentoDto
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void create(EventoMonitConhecimentoDTO eventoMonitConhecimentoDto, TransactionControler transactionControler) throws Exception;

	/**
	 * Lista EventoMonitConhecimentoDTO por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<EventoMonitConhecimentoDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<EventoMonitConhecimentoDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws Exception;
	
	/**
	 * Retorna true ou false caso evento Monitoramento tenha algum relacionamento com base de conhecimento
	 * 
	 * @param idEventoMonitoramento
	 * @return boolena
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarEventoMonitoramentoComConhecimento(Integer idEventoMonitoramento) throws Exception;
	
	public Collection<EventoMonitConhecimentoDTO> listByIdEventoMonitoramento(Integer idEventoMonitoramento) throws Exception;

}
