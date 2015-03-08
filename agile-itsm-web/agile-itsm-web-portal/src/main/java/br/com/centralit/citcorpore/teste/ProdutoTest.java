package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.negocio.ProdutoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class ProdutoTest {
	public String testProduto() {
		ProdutoService produtoService;
		try {
			produtoService = (ProdutoService) ServiceLocator.getInstance().getService(ProdutoService.class, null);
			ProdutoDTO produtoDto = new ProdutoDTO();
			produtoDto.setIdTipoProduto(1);
			produtoDto.setIdMarca(3);
			produtoDto.setModelo("teste");
			produtoDto.setPrecoMercado(23.00);
			produtoDto.setDetalhes("teste Layanne");
			produtoDto.setCodigoProduto("123");
			produtoDto.setComplemento("Teste Layanne");
			produtoDto.setSituacao("A");
			produtoService.create(produtoDto);
			return new UtilTest().testNotNull(produtoDto.getIdProduto());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
