package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.negocio.CalendarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class CalendarioTest {
	public String testCalendario() {
		CalendarioService calendarioService;
		try {
			calendarioService = (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
			CalendarioDTO calendarioDto = new CalendarioDTO();
			calendarioDto.setDescricao("Teste");
			calendarioDto.setConsideraFeriados("S");
			calendarioDto.setIdJornadaSeg(1);
			calendarioDto.setIdJornadaTer(1);
			calendarioDto.setIdJornadaQua(1);
			calendarioDto.setIdJornadaQui(1);
			calendarioDto.setIdJornadaSex(1);
			//calendarioDto.setIdJornadaSab(1);
			//calendarioDto.setIdJornadaDom(1);
			calendarioService.create(calendarioDto);
			return new UtilTest().testNotNull(calendarioDto.getIdCalendario());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
