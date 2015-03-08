package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class MudarSLATest {
	public String testMudarSLA() {
		SolicitacaoServicoService solicitacaoServicoService;
		try {
			solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setIdUsuario(430);
			solicitacaoServicoDto.setIdSolicitacaoServico(213);
			solicitacaoServicoDto.setIdSolicitante(15);
			solicitacaoServicoDto.setDataInicio(UtilDatas.getDataAtual());
			solicitacaoServicoDto.setDataHoraSolicitacao(UtilDatas.getDataHoraAtual());
			solicitacaoServicoDto.setUsuarioDto(usuario);
			solicitacaoServicoDto.setRegistradoPor("Layanne Cristine Batista");	
			solicitacaoServicoDto.setHouveMudanca("S");
			solicitacaoServicoDto.setSituacao("EmAndamento");
			solicitacaoServicoDto.setPrazohhAnterior(9);
			solicitacaoServicoDto.setPrazommAnterior(6);
			solicitacaoServicoDto.setSlaACombinar("S");
			solicitacaoServicoService.updateSLA(solicitacaoServicoDto);
			return new UtilTest().testNotNull(solicitacaoServicoDto.getIdSolicitacaoServico());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}