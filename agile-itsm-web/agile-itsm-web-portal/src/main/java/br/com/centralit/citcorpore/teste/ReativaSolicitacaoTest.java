package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class ReativaSolicitacaoTest {
	public String testRealizaSolicitacao() {
		SolicitacaoServicoService solicitacaoServicoService;
	try {
		solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setIdUsuario(430);
		solicitacaoServicoDto.setIdSolicitacaoServico(126);  // setar id da solicitação suspensa pra fazer teste
		solicitacaoServicoService.reativa(usuario, solicitacaoServicoDto);
		return new UtilTest().testNull(solicitacaoServicoDto);
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
