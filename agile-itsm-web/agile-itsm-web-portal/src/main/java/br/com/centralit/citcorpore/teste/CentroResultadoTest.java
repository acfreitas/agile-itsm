package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class CentroResultadoTest {
	public String testCentroResultado() {
		CentroResultadoService centroResultadoService;
		try {
			centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, null);
			CentroResultadoDTO centroResultadoDto = new CentroResultadoDTO();
			centroResultadoDto.setCodigoCentroResultado("20");
			centroResultadoDto.setIdCentroResultadoPai(15);
			centroResultadoDto.setImagem("");
			centroResultadoDto.setNivel(2);
			centroResultadoDto.setNomeCentroResultado("Teste Layanne");
			centroResultadoDto.setNomeCentroResultadoPai("Teste");
			centroResultadoDto.setPermiteRequisicaoProduto("S");
			centroResultadoDto.setSituacao("A");
			centroResultadoService.create(centroResultadoDto);
			return new UtilTest().testNotNull(centroResultadoDto.getIdCentroResultado());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
