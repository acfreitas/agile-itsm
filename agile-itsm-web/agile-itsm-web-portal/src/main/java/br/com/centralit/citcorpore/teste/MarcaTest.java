package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.MarcaDTO;
import br.com.centralit.citcorpore.negocio.MarcaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class MarcaTest {
	public String testMarca() {
		MarcaService marcaService;
		try {
			marcaService = (MarcaService) ServiceLocator.getInstance().getService(MarcaService.class, null);
			MarcaDTO marcaDto = new MarcaDTO();
			marcaDto.setIdFabricante(3);
			marcaDto.setNomeMarca("Teste");
			marcaDto.setSituacao("A");
			marcaDto.setNomeFabricante("Teste Layanne");
			marcaService.create(marcaDto);
			return new UtilTest().testNotNull(marcaDto.getIdMarca());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
