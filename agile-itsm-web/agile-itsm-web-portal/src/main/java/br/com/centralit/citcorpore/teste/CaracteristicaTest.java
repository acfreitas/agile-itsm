package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.negocio.CaracteristicaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class CaracteristicaTest {
	public String testCaracteristica() {
		CaracteristicaService caracteristicaService;
		try {
			caracteristicaService = (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);
			CaracteristicaDTO caracteristicaDto = new CaracteristicaDTO();
			caracteristicaDto.setDataFim(UtilDatas.getDataAtual());
			caracteristicaDto.setDataInicio(UtilDatas.getDataAtual());
			caracteristicaDto.setDescricao("");
			caracteristicaDto.setIdEmpresa(1);
			caracteristicaDto.setNome("Teste Layanne");
			caracteristicaDto.setSistema("1");
			caracteristicaDto.setTag("TES");
			caracteristicaDto.setTipo("A");
			caracteristicaService.create(caracteristicaDto);
			return new UtilTest().testNotNull(caracteristicaDto.getIdCaracteristica());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
