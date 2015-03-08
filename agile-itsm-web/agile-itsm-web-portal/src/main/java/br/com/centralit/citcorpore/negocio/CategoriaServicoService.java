/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface CategoriaServicoService extends CrudService {

	/**
	 * Retorna Lista de Categorias Ativas.
	 * 
	 * @throws Exception
	 * @return <code>Collection</code>
	 */
	public Collection listCategoriasAtivas() throws Exception;

	/**
	 * Verifica se Categoria Serviço possui subcategoria ou serviço associado.
	 * 
	 * @param categoriaServico
	 * @return - <b>True:</b> Possui. - <b>False: </b>Não possui.
	 * @throws PersistenceException
	 * @throws ServiceException
	 */
	public boolean verificarSeCategoriaPossuiServicoOuSubCategoria(CategoriaServicoDTO categoriaServicoDto) throws PersistenceException, br.com.citframework.excecao.ServiceException;

	/**
	 * Verifica se categoria informada já existe.
	 * 
	 * @param categoriaServicoDTO
	 * @throws PersistenceException
	 * @throws ServiceException
	 * @return true - existe; false - não existe;
	 */
	public boolean verificarSeCategoriaExiste(CategoriaServicoDTO categoriaServicoDTO) throws PersistenceException, ServiceException;
	public Collection listHierarquia() throws Exception;
	
	 public List<CategoriaServicoDTO> listCategoriaHierarquia (CategoriaServicoDTO categoriaServicoDTO,  List<CategoriaServicoDTO> listCategoriaHierarquia ) throws Exception;

	 public String verificaIdCategoriaServico(HashMap mapFields) throws Exception;
}
