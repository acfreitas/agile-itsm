package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CriterioAvaliacaoFornecedorDTO;
import br.com.citframework.service.CrudService;
@SuppressWarnings("rawtypes")
public interface CriterioAvaliacaoFornecedorService extends CrudService {
	
	public Collection findByIdCriterio(Integer parm) throws Exception;

	public void deleteByIdCriterio(Integer parm) throws Exception;
	
	/**
	 * Retorna uma lista de CriterioAvaliacaoFornecedorDTO de acordo com o idAvaliacaoFornecedor passado.
	 * 
	 * @param idAvaliacaoFornecedor
	 * @return Collection<CriterioAvaliacaoFornecedorDTO>
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<CriterioAvaliacaoFornecedorDTO> listByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception;
	
	public void deleteByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception;
	
}
