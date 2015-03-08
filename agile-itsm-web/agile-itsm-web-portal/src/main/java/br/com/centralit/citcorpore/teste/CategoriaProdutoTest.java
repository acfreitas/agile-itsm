package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.negocio.CategoriaProdutoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class CategoriaProdutoTest {
	public String testCategoriaProduto() {
		CategoriaProdutoService categoriaProdutoService;
		try {
			categoriaProdutoService = (CategoriaProdutoService) ServiceLocator.getInstance().getService(CategoriaProdutoService.class, null);
			CategoriaProdutoDTO categoriaProdutoDto = new CategoriaProdutoDTO();
			categoriaProdutoDto.setIdCategoriaPai(2);
			//categoriaProdutoDto.setPesoCotacaoPreco(5);
			categoriaProdutoDto.setNomeCategoria("Teste");
			categoriaProdutoDto.setNomeCategoriaPai("Teste Layanne");
			categoriaProdutoDto.setSituacao("A");
			categoriaProdutoService.create(categoriaProdutoDto);
			return new UtilTest().testNotNull(categoriaProdutoDto.getIdCategoria());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
