package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.GrupoAtvPeriodicaDTO;
import br.com.centralit.citcorpore.negocio.GrupoAtvPeriodicaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class GrupoAtvPeriodicaTest {
	public String testGrupoAtvPeriodica() {
		GrupoAtvPeriodicaService grupoAtvPeriodicaService;
		try {
			grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
			GrupoAtvPeriodicaDTO grupoAtvPeriodicaDto = new GrupoAtvPeriodicaDTO();
			grupoAtvPeriodicaDto.setDataFim(UtilDatas.getDataAtual());
			grupoAtvPeriodicaDto.setDataInicio(UtilDatas.getDataAtual());
			grupoAtvPeriodicaDto.setDescGrupoAtvPeriodica("Teste");
			grupoAtvPeriodicaDto.setNomeGrupoAtvPeriodica("Teste Layanne");
			grupoAtvPeriodicaService.create(grupoAtvPeriodicaDto);
			return new UtilTest().testNotNull(grupoAtvPeriodicaDto.getIdGrupoAtvPeriodica());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
