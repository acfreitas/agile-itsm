package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.PalavraGemeaDTO;
import br.com.centralit.citcorpore.negocio.PalavraGemeaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class PalavraGemeaTest {
	public String testPalavraGemea() {
		PalavraGemeaService palavraGemeaService;
		try {
			palavraGemeaService = (PalavraGemeaService) ServiceLocator.getInstance().getService(PalavraGemeaService.class, null);
			PalavraGemeaDTO palavraGemeaDto = new PalavraGemeaDTO();
			palavraGemeaDto.setPalavra("Teste Layanne");
			palavraGemeaDto.setPalavraCorrespondente("Teste");
			palavraGemeaService.create(palavraGemeaDto);
			return new UtilTest().testNotNull(palavraGemeaDto.getIdPalavraGemea());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
