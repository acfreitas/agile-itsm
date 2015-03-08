package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class SuspenderSolicitacaoTest {

	public String testSuspenderSolicitacao() {
		SolicitacaoServicoService solicitacaoServicoService;
		try {
			solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setIdUsuario(430);
			solicitacaoServicoDTO.setIdSolicitacaoServico(126);
			solicitacaoServicoService.suspende(usuarioDTO, solicitacaoServicoDTO);
			return new UtilTest().testNull (solicitacaoServicoDTO);
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}