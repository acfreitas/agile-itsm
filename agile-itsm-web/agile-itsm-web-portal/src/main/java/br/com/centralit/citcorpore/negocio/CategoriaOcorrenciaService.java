package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Classe da camada de neg�cio da aplica��o para provimento de servi�os.
 * Por meio de sua heran�a de CrudService opera��es CRUD s�o providas.
 * Nessa classe podem ser definidos servi�os complementares.
 * 
 * @author thiago.monteiro
 *
 */
public interface CategoriaOcorrenciaService extends CrudService {
	/**
	 * Exclui a categoria caso n�o exista uma ocorr�ncia associada.
	 * 
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */	
	public void deletarCategoriaOcorrencia(IDto model, DocumentHTML document) throws ServiceException, Exception;	
	
	/**
	 * Consulta por categorias de ocorr�ncia que estejam ativas (dataFim n�o nula).
	 * 
	 * @param model
	 * @param document
	 * @return
	 * @throws Exception 
	 */
	public boolean consultarCategoriaOcorrenciaAtiva(CategoriaOcorrenciaDTO categoriaOcorrencia) throws Exception;	
	
	
	public CategoriaOcorrenciaDTO restoreAll(Integer idCategoriaOcorrencia) throws Exception;
	
}
