package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author thiago.monteiro
 */
public interface OrigemOcorrenciaService extends CrudService {
	/**
	 * Exclui a origem caso n�o exista uma ocorr�ncia associada.
	 * 
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */	
	public void deletarOrigemOcorrencia(IDto model, DocumentHTML document) throws ServiceException, Exception;	
	
	/**
	 * Consulta por origem da ocorr�ncia que estejam ativas (dataFim n�o nula).
	 * 
	 * @param model
	 * @param document
	 * @return
	 * @throws Exception 
	 */
	public boolean consultarOrigemOcorrenciaAtiva(OrigemOcorrenciaDTO origemOcorrencia) throws Exception;

	/**
	 * Metodo responsavel por retornar todos os dados da Origem de uma ocorr�ncia
	 * 
	 * @param idOrigem
	 * @return
	 *@author Ezequiel
	 */
	public OrigemOcorrenciaDTO restoreAll(Integer idOrigem) throws Exception;
	
}
