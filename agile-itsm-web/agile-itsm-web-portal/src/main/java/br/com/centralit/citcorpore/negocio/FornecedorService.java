package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.citframework.service.CrudService;

public interface FornecedorService extends CrudService {

	/**
	 * Retorna uma lista de escopo de fornecimento de acordo com o fornecedor passado
	 * 
	 * @param fornecedorProdutoDto
	 * @return Collection<FornecedorProdutoDTO>
	 * @throws Exception
	 * @author Thays.araujo
	 * 
	 */
	public Collection<FornecedorDTO> listEscopoFornecimento(FornecedorDTO fornecedorDtoF) throws Exception;
	
	public boolean consultarCargosAtivos(FornecedorDTO fornecedor) throws Exception;
	
	public boolean excluirFornecedor(FornecedorDTO fornecedor) throws Exception;

	/**
	 * Consulta os fornecedores pelo campo razaoSocial
	 * @param razaoSocial
	 * @return
	 * @throws Exception
	 */
	public Collection<FornecedorDTO> consultarFornecedorPorRazaoSocialAutoComplete(String razaoSocial) throws Exception;
	
	/**
	 * @param idFornecedor
	 * @return
	 * @throws Exception
	 * @author mario.haysaki
	 */
	public boolean existeFornecedorAssociadoContrato(Integer idFornecedor) throws Exception;
	
}