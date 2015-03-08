package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.ContatoSolicitacaoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class ContatoSolicitacaoTest {
	public String testCreateContato() {
		ContatoSolicitacaoServicoService contatoSolicitacaoServicoService;
		try {
			contatoSolicitacaoServicoService = (ContatoSolicitacaoServicoService) ServiceLocator.getInstance().getService(ContatoSolicitacaoServicoService.class, null);
			ContatoSolicitacaoServicoDTO contatoSolicitacaoServicoDTO = new ContatoSolicitacaoServicoDTO();
			contatoSolicitacaoServicoDTO.setEmailcontato("layannebatista.centralit@gmail.com");
			contatoSolicitacaoServicoDTO.setNomecontato("Layanne Cristine Batista");
			contatoSolicitacaoServicoDTO.setObservacao("Teste");
			contatoSolicitacaoServicoDTO.setTelefonecontato("6293411573");
			contatoSolicitacaoServicoDTO.setRamal("20");
			contatoSolicitacaoServicoService.create(contatoSolicitacaoServicoDTO);
			return new UtilTest().testNotNull(contatoSolicitacaoServicoDTO.getIdcontatosolicitacaoservico());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}