package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.EventoItemConfigDTO;
import br.com.centralit.citcorpore.negocio.EventoItemConfigService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class EventoTest {
	public String testEvento() {
		EventoItemConfigService eventoItemConfigService;
		try {
			eventoItemConfigService = (EventoItemConfigService) ServiceLocator.getInstance().getService(EventoItemConfigService.class, null);
			EventoItemConfigDTO eventoItemConfigDto = new EventoItemConfigDTO();
			eventoItemConfigDto.setIdItemCfg(1);
			eventoItemConfigDto.setIdEmpresa(1);
			eventoItemConfigDto.setDataFim(UtilDatas.getDataAtual());
			eventoItemConfigDto.setDataInicio(UtilDatas.getDataAtual());
			eventoItemConfigDto.setDescricao("Teste Layanne");
			eventoItemConfigDto.setLigarCasoDesl("N");
			eventoItemConfigDto.setNomeItemCfg("Teste");
			eventoItemConfigService.create(eventoItemConfigDto);
			return new UtilTest().testNotNull(eventoItemConfigDto.getIdEvento());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
