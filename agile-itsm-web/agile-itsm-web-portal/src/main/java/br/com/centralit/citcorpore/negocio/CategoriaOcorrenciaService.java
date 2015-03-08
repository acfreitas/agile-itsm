package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Classe da camada de negócio da aplicação para provimento de serviços.
 * Por meio de sua herança de CrudService operações CRUD são providas.
 * Nessa classe podem ser definidos serviços complementares.
 * 
 * @author thiago.monteiro
 *
 */
public interface CategoriaOcorrenciaService extends CrudService {
	/**
	 * Exclui a categoria caso não exista uma ocorrência associada.
	 * 
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */	
	public void deletarCategoriaOcorrencia(IDto model, DocumentHTML document) throws ServiceException, Exception;	
	
	/**
	 * Consulta por categorias de ocorrência que estejam ativas (dataFim não nula).
	 * 
	 * @param model
	 * @param document
	 * @return
	 * @throws Exception 
	 */
	public boolean consultarCategoriaOcorrenciaAtiva(CategoriaOcorrenciaDTO categoriaOcorrencia) throws Exception;	
	
	
	public CategoriaOcorrenciaDTO restoreAll(Integer idCategoriaOcorrencia) throws Exception;
	
}
