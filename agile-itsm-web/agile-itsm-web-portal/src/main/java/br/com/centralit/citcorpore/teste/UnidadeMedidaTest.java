package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.centralit.citcorpore.negocio.UnidadeMedidaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class UnidadeMedidaTest {
	public String testUnidadeMedida() {
		UnidadeMedidaService unidadeMedidaService;
		try {
			unidadeMedidaService = (UnidadeMedidaService) ServiceLocator.getInstance().getService(UnidadeMedidaService.class, null);
			UnidadeMedidaDTO unidadeMedidaDto = new UnidadeMedidaDTO();
			unidadeMedidaDto.setNomeUnidadeMedida("Teste Layanne");
			unidadeMedidaDto.setSiglaUnidadeMedida("Teslay");
			unidadeMedidaDto.setSituacao("A");
			unidadeMedidaService.create(unidadeMedidaDto);
			return new UtilTest().testNotNull(unidadeMedidaDto.getIdUnidadeMedida());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
