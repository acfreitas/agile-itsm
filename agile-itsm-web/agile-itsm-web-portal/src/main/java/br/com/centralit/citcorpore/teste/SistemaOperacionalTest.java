package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.SistemaOperacionalDTO;
import br.com.centralit.citcorpore.negocio.SistemaOperacionalService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class SistemaOperacionalTest {
	public String testSistemaOperacional() {
		SistemaOperacionalService sistemaOperacionalService;
		try {
			sistemaOperacionalService = (SistemaOperacionalService) ServiceLocator.getInstance().getService(SistemaOperacionalService.class, null);
			SistemaOperacionalDTO sistemaOperacionalDto = new SistemaOperacionalDTO();
			sistemaOperacionalDto.setNome("Teste Layanne");
			sistemaOperacionalService.create(sistemaOperacionalDto);
			return new UtilTest().testNotNull(sistemaOperacionalDto.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
