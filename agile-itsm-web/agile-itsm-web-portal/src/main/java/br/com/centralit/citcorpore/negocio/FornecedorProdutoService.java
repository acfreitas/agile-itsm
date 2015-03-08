package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FornecedorProdutoDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface FornecedorProdutoService extends CrudService {

	public Collection findByIdTipoProduto(Integer parm) throws Exception;

	public FornecedorProdutoDTO findByIdTipoProdutoAndFornecedor(Integer parm, Integer parm2) throws Exception;

	
}
