package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.negocio.JornadaTrabalhoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class JornadaTrabalhoTest {
	public String testJornadaTrabalho() {
		JornadaTrabalhoService jornadaTrabalhoService;
		try {
			jornadaTrabalhoService = (JornadaTrabalhoService) ServiceLocator.getInstance().getService(JornadaTrabalhoService.class, null);
			JornadaTrabalhoDTO jornadaTrabalhoDto = new JornadaTrabalhoDTO();
			jornadaTrabalhoDto.setDescricao("teste");
			jornadaTrabalhoDto.setDataFim(UtilDatas.getDataAtual());
			jornadaTrabalhoDto.setDataInicio(UtilDatas.getDataAtual());
			jornadaTrabalhoDto.setCargaHoraria("40:00");
			jornadaTrabalhoDto.setInicio1("08:00");
			jornadaTrabalhoDto.setTermino1("18:00");
			jornadaTrabalhoDto.setInicio2("08:00");
			jornadaTrabalhoDto.setTermino2("18:00");
			jornadaTrabalhoDto.setInicio3("08:00");
			jornadaTrabalhoDto.setTermino3("18:00");
			jornadaTrabalhoDto.setInicio4("08:00");
			jornadaTrabalhoDto.setTermino4("18:00");
			jornadaTrabalhoDto.setInicio5("08:00");
			jornadaTrabalhoDto.setTermino5("18:00");
			jornadaTrabalhoService.create(jornadaTrabalhoDto);
			return new UtilTest().testNotNull(jornadaTrabalhoDto.getIdJornada());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
