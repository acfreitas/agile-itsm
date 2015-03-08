package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class DelegacaoTarefaTest {

	public String testDelegaTarefa() {
		ExecucaoSolicitacaoService execucaoFluxoService;
		try {
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setLogin("layanne.batista");

			SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
			solicitacaoServicoDto.setIdTarefa(19173);
			solicitacaoServicoDto.setIdUsuarioDestino(430);
			solicitacaoServicoDto.setIdGrupoDestino(null);

			String usuarioDestino = null;
			String grupoDestino = null;
			
			if (solicitacaoServicoDto.getIdUsuarioDestino() != null) {
				UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
				UsuarioDTO usuarioDestinoDto = new UsuarioDTO();
				usuarioDestinoDto.setIdUsuario(solicitacaoServicoDto.getIdUsuarioDestino());
				usuarioDestinoDto = (UsuarioDTO) usuarioService.restore(usuarioDestinoDto);
				if (usuarioDestinoDto != null)
					usuarioDestino = usuarioDestinoDto.getLogin();
			}
			
			GrupoDTO grupoDestinoDto = null;
			if (solicitacaoServicoDto.getIdGrupoDestino() != null) {
				GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
				grupoDestinoDto = new GrupoDTO();
				grupoDestinoDto.setIdGrupo(solicitacaoServicoDto.getIdGrupoDestino());
				grupoDestinoDto = (GrupoDTO) grupoService.restore(grupoDestinoDto);
				if (grupoDestinoDto != null)
					grupoDestino = grupoDestinoDto.getSigla();
			}
			
			execucaoFluxoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
			execucaoFluxoService.delegaTarefa(usuario.getLogin(), solicitacaoServicoDto.getIdTarefa(), usuarioDestino, grupoDestino);
			return new UtilTest().testObj(usuario.getLogin(), solicitacaoServicoDto.getIdTarefa());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}