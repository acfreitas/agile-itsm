package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class PrioridadeTest {
	public String testPrioridade() {
		PrioridadeService prioridadeService;
		try {
			prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
			PrioridadeDTO prioridadeDto = new PrioridadeDTO();
			prioridadeDto.setGrupoPrioridade("G1");
			prioridadeDto.setIdEmpresa(1);
			prioridadeDto.setNomePrioridade("Teste Layanne");
			prioridadeDto.setSituacao("A");
			prioridadeService.create(prioridadeDto);
			return new UtilTest().testNotNull(prioridadeDto.getIdPrioridade());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
