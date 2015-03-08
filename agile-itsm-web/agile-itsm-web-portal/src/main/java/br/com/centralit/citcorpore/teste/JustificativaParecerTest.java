package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class JustificativaParecerTest {
	public String testJustificativaParecer() {
		JustificativaParecerService justificativaParecerService;
		try {
			justificativaParecerService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, null);
			JustificativaParecerDTO justificativaParecerDto = new JustificativaParecerDTO();
			justificativaParecerDto.setAplicavelCotacao("S");
			justificativaParecerDto.setDescricaoJustificativa("Teste Layanne");
			justificativaParecerDto.setSituacao("A");
//			justificativaParecerDto.setAplicavelInspecao("S");
//			justificativaParecerDto.setAplicavelRequisicao("S");
			justificativaParecerService.create(justificativaParecerDto);
			return new UtilTest().testNotNull(justificativaParecerDto.getIdJustificativa());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
