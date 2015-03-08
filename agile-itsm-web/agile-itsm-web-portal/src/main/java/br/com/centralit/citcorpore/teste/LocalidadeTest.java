package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class LocalidadeTest {
	public String testLocalidade() {
		LocalidadeService localidadeService;
		try {
			localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);
			LocalidadeDTO localidadeDto = new LocalidadeDTO();
			localidadeDto.setDataFim(UtilDatas.getDataAtual());
			localidadeDto.setDataInicio(UtilDatas.getDataAtual());
			localidadeDto.setNomeLocalidade("Teste Layanne");
			localidadeService.create(localidadeDto);
			return new UtilTest().testNotNull(localidadeDto.getIdLocalidade());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
