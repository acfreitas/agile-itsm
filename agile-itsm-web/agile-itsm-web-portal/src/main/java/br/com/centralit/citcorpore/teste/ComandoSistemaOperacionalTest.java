package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ComandoSistemaOperacionalDTO;
import br.com.centralit.citcorpore.negocio.ComandoSistemaOperacionalService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class ComandoSistemaOperacionalTest {
	public String testComandoSistemaOperacional() {
		ComandoSistemaOperacionalService comandoSistemaOperacionalService;
		try {
			comandoSistemaOperacionalService = (ComandoSistemaOperacionalService) ServiceLocator.getInstance().getService(ComandoSistemaOperacionalService.class, null);
			ComandoSistemaOperacionalDTO comandoSistemaOperacionalDto = new ComandoSistemaOperacionalDTO();
			comandoSistemaOperacionalDto.setComando("Layanne");
			comandoSistemaOperacionalDto.setIdSistemaOperacional(1);
			comandoSistemaOperacionalDto.setIdComando(3);
//			comandoSistemaOperacionalDto.setComandoSistemaOperacional();
//			comandoSistemaOperacionalDto.setSistemaOperacional();
			comandoSistemaOperacionalService.create(comandoSistemaOperacionalDto);
			return new UtilTest().testNotNull(comandoSistemaOperacionalDto.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
