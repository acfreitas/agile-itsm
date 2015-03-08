package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ComandoDTO;
import br.com.centralit.citcorpore.negocio.ComandoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class ComandoTest {
	public String testComando() {
		ComandoService comandoService;
		try {
			comandoService = (ComandoService) ServiceLocator.getInstance().getService(ComandoService.class, null);
			ComandoDTO comandoDto = new ComandoDTO();
			comandoDto.setDescricao("Teste Layanne");
			comandoService.create(comandoDto);
			return new UtilTest().testNotNull(comandoDto.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
