package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.TipoEventoServicoDTO;
import br.com.centralit.citcorpore.negocio.TipoEventoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class TipoEventoServicoTest {
	public String testTipoEventoServico() {
		TipoEventoServicoService tipoEventoServicoService;
		try {
			tipoEventoServicoService = (TipoEventoServicoService) ServiceLocator.getInstance().getService(TipoEventoServicoService.class, null);
			TipoEventoServicoDTO tipoEventoServicoDto = new TipoEventoServicoDTO();
			tipoEventoServicoDto.setNomeTipoEventoServico("Teste Layanne");
			tipoEventoServicoService.create(tipoEventoServicoDto);
			return new UtilTest().testNotNull(tipoEventoServicoDto.getIdTipoEventoServico());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
