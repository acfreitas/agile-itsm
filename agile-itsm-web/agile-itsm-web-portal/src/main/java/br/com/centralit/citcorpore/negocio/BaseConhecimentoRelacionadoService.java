/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.BaseConhecimentoRelacionadoDTO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

/**
 * @author Vadoilo Damasceno
 * 
 */
public interface BaseConhecimentoRelacionadoService extends CrudService {

	/**
	 * Exclui ImportanciaConhecimentoGrupo por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void deleteByIdConhecimento(Integer idBaseConhecimento, TransactionControler transactionControler) throws Exception;

	/**
	 * Cria nova BaseConhecimentoRelacionado.
	 * 
	 * @param importanciaConhecimentoGrupo
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void create(BaseConhecimentoRelacionadoDTO baseConhecimentoRelacionadoDto, TransactionControler transactionControler) throws Exception;

	/**
	 * Lista BaseConhecimentoRelacionado por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<ImportanciaConhecimentoGrupoDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<BaseConhecimentoRelacionadoDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws Exception;
	
	public Collection<BaseConhecimentoRelacionadoDTO> listByIdBaseConhecimentoRelacionado(Integer idBaseConhecimento) throws Exception;

}
