package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface CategoriaProblemaService extends CrudService {

	public Collection findByIdCategoriaProblema(Integer parm) throws Exception;

	public void deleteByIdCategoriaProblema(Integer parm) throws Exception;

	public Collection findByNomeCategoria(String parm) throws Exception;

	/**
	 * Deleta por nome categoria.
	 * 
	 * @param parm
	 * @throws Exception
	 */
	public void deleteByNomeCategoria(String parm) throws Exception;

	public Collection listHierarquia() throws Exception;
	
	public Collection findByNomeCategoriaProblema(CategoriaProblemaDTO categoriaProblemaDto) throws Exception;
	
	/**
	 * Retorna uma lista de categoria problema ativas
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection getAtivos()throws Exception;
	
	/**
	 * Retorna verdadeiro ou falso caso a categoriaProblema esteje cadastrada
	 * @param obj
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean consultarCategoriasAtivas(CategoriaProblemaDTO obj) throws Exception;

	/**
	 * Retorna todas as CategoriaProblema relacionadas a um determinado template
	 * @throws Exception
	 * @author murilo.rodrigues
	 */
	public Collection<CategoriaProblemaDTO> findByIdTemplate(Integer idTemplate) throws Exception;

	/**
	 * Desvincula CategoriaProblemas relacionadas a um determinado Template
	 * @throws Exception
	 * @author murilo.rodrigues
	 */
	public void desvincularCategoriaProblemasRelacionadasTemplate(Integer idTemplate) throws Exception;
	
	
}
