package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class UnidadeTest {
	public String testUnidade() {
		UnidadeService unidadeService;
		try {
			unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
			UnidadeDTO unidadeDto = new UnidadeDTO();
			unidadeDto.setDataFim(UtilDatas.getDataAtual());
			unidadeDto.setDataInicio(UtilDatas.getDataAtual());
			unidadeDto.setDescricao("Teste Layanne");
			unidadeDto.setIdUnidadePai(3);
			unidadeDto.setIdTipoUnidade(1);
			unidadeDto.setIdEmpresa(1);
			unidadeDto.setNome("Teste");
			unidadeDto.setEmail("teste@centralt.com.br");
			unidadeDto.setIdEndereco(3);
			unidadeDto.setAceitaEntregaProduto("N");
			unidadeService.create(unidadeDto);
			return new UtilTest().testNotNull(unidadeDto.getIdUnidade());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
