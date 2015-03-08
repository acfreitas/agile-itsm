package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AvaliacaoReferenciaFornecedorDTO;
import br.com.citframework.service.CrudService;

public interface AvaliacaoReferenciaFornecedorService extends CrudService {

	/**
	 * Lista AvaliacaoReferenciaFornecedorDTO por idAvaliacaoFornecedor.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<AvaliacaoReferenciaFornecedorDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<AvaliacaoReferenciaFornecedorDTO> listByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception;

	/**
	 * 
	 * @param idAvaliacaoFornecedor
	 * @throws Exception
	 *             Metodo para excluir de acordo com o idAvaliacaoFornecedor passado
	 */
	public void deleteByIdAvaliacaoFornecedor(Integer idAvaliacaoFornecedor) throws Exception;
}
