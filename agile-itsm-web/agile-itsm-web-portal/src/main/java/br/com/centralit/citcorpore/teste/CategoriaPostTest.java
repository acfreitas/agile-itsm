package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CategoriaPostDTO;
import br.com.centralit.citcorpore.negocio.CategoriaPostService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class CategoriaPostTest {
	public String testCategoriaPost() {
		CategoriaPostService categoriaPostService;
		try {
			categoriaPostService = (CategoriaPostService) ServiceLocator.getInstance().getService(CategoriaPostService.class, null);
			CategoriaPostDTO categoriaPostDTO = new CategoriaPostDTO();
			categoriaPostDTO.setDataFim(UtilDatas.getDataAtual());
			categoriaPostDTO.setDataInicio(UtilDatas.getDataAtual());
			categoriaPostDTO.setIdCategoriaPostPai(1);
			categoriaPostDTO.setNomeCategoria("Teste Layanne");
			categoriaPostService.create(categoriaPostDTO);
			return new UtilTest().testNotNull(categoriaPostDTO.getIdCategoriaPost());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
