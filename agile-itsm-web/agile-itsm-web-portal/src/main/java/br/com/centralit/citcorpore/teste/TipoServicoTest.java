package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.centralit.citcorpore.negocio.TipoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class TipoServicoTest {
	public String testTipoServico() {
		TipoServicoService tipoServicoService;
		try {
			tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);
			TipoServicoDTO tipoServicoDto = new TipoServicoDTO();
			tipoServicoDto.setIdEmpresa(1);
			tipoServicoDto.setNomeTipoServico("Teste Layanne");
			tipoServicoDto.setSituacao("A");
			tipoServicoService.create(tipoServicoDto);
			return new UtilTest().testNotNull(tipoServicoDto.getIdTipoServico());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
