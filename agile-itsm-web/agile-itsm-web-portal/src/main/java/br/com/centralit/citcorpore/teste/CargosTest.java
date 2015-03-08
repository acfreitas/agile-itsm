package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class CargosTest {
	public String testCargos() {
		CargosService cargosService;
		try {
			cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
			CargosDTO cargosDto = new CargosDTO();
			cargosDto.setDataFim(UtilDatas.getDataAtual());
			cargosDto.setDataInicio(UtilDatas.getDataAtual());
			cargosDto.setNomeCargo("Teste");
			cargosService.create(cargosDto);
			return new UtilTest().testNotNull(cargosDto.getIdCargo());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
