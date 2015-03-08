package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CategoriaSolucaoDTO;
import br.com.centralit.citcorpore.negocio.CategoriaSolucaoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class CategoriaSolucaoTest {
	public String testCategoriaSolucao() {
		CategoriaSolucaoService categoriaSolucaoService;
		try {
			categoriaSolucaoService = (CategoriaSolucaoService) ServiceLocator.getInstance().getService(CategoriaSolucaoService.class, null);
			CategoriaSolucaoDTO categoriaSolucaoDTO = new CategoriaSolucaoDTO();
			categoriaSolucaoDTO.setDataFim(UtilDatas.getDataAtual());
			categoriaSolucaoDTO.setDataInicio(UtilDatas.getDataAtual());
			categoriaSolucaoDTO.setDescricaoCategoriaSolucao("Teste Layanne");
			categoriaSolucaoDTO.setIdCategoriaSolucaoPai(1);
			categoriaSolucaoService.create(categoriaSolucaoDTO);
			return new UtilTest().testNotNull(categoriaSolucaoDTO.getIdCategoriaSolucao());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
