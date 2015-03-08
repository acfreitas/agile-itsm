package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CausaIncidenteDTO;
import br.com.centralit.citcorpore.negocio.CausaIncidenteService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class CausaIncidenteTest {
	public String testCausaIncidente() {
		CausaIncidenteService causaIncidenteService;
		try {
			causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);
			CausaIncidenteDTO causaIncidenteDto = new CausaIncidenteDTO();
			causaIncidenteDto.setDataFim(UtilDatas.getDataAtual());
			causaIncidenteDto.setDataInicio(UtilDatas.getDataAtual());
			causaIncidenteDto.setDescricaoCausa("Teste Layanne");
			causaIncidenteDto.setIdCausaIncidentePai(1);
			causaIncidenteService.create(causaIncidenteDto);
			return new UtilTest().testNotNull(causaIncidenteDto.getIdCausaIncidente());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
