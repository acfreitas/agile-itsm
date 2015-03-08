package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class PastaTest {
	public String testPasta() {
		PastaService pastaService;
		try {
			pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
			PastaDTO pastaDto = new PastaDTO();
			pastaDto.setNome("Teste Layanne");
			pastaDto.setDataFim(UtilDatas.getDataAtual());
			pastaDto.setDataInicio(UtilDatas.getDataAtual());
			pastaDto.setIdPastaPai(1);
			pastaDto.setHerdaPermissoes("N");
			pastaDto.setIdNotificacao(1);
			pastaService.create(pastaDto);
			return new UtilTest().testNotNull(pastaDto.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
