package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.negocio.AlcadaCentroResultadoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class AlcadaCentroResultadoTest {
	public String testAlcadaCentroResultado() {
		AlcadaCentroResultadoService alcadaCentroResultadoService;
		try {
			alcadaCentroResultadoService = (AlcadaCentroResultadoService) ServiceLocator.getInstance().getService(AlcadaCentroResultadoService.class, null);
			AlcadaCentroResultadoDTO alcadaCentroResultadoDTO = new AlcadaCentroResultadoDTO();
			alcadaCentroResultadoDTO.setDataFim(UtilDatas.getDataAtual());
			alcadaCentroResultadoDTO.setDataInicio(UtilDatas.getDataAtual());
			alcadaCentroResultadoDTO.setIdAlcada(2);
			alcadaCentroResultadoDTO.setIdCentroResultado(12);
			alcadaCentroResultadoDTO.setIdEmpregado(1);
			alcadaCentroResultadoDTO.setNomeAlcada("Teste Layanne");
			alcadaCentroResultadoDTO.setNomeCentroResultado("Teste");
			alcadaCentroResultadoDTO.setNomeEmpregado("Layanne Cristine Batista");
			alcadaCentroResultadoService.create(alcadaCentroResultadoDTO);
			return new UtilTest().testNotNull(alcadaCentroResultadoDTO.getIdAlcadaCentroResultado());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
