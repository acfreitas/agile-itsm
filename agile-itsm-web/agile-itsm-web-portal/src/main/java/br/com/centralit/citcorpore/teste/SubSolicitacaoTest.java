package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class SubSolicitacaoTest {
	public String testCreateSubSolicitacao() {
		try {
			SolicitacaoServicoDTO novaSolicitacaoServicoDto = new SolicitacaoServicoDTO();
			UsuarioDTO usuarioDto = new UsuarioDTO();
			usuarioDto.setIdUsuario(430);
			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
			SolicitacaoServicoDTO solicitacaoServicoOrigem = new SolicitacaoServicoDTO();
			ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();
			solicitacaoServicoOrigem.setIdSolicitacaoServico(4);
			solicitacaoServicoOrigem = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoOrigem);
			servicoContratoDto.setIdServicoContrato(solicitacaoServicoOrigem.getIdServicoContrato());
			servicoContratoDto = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDto);
			novaSolicitacaoServicoDto.setIdSolicitacaoServico(solicitacaoServicoOrigem.getIdSolicitacaoServico());
			novaSolicitacaoServicoDto.setIdSolicitacaoPai(solicitacaoServicoOrigem.getIdSolicitacaoServico());
			novaSolicitacaoServicoDto.setIdContatoSolicitacaoServico(solicitacaoServicoOrigem.getIdContatoSolicitacaoServico());
			novaSolicitacaoServicoDto.setIdServico(servicoContratoDto.getIdServico());
			novaSolicitacaoServicoDto.setUsuarioDto(usuarioDto);
			novaSolicitacaoServicoDto.setDescricao(solicitacaoServicoOrigem.getDescricao());
			novaSolicitacaoServicoDto.setSituacao(solicitacaoServicoOrigem.getSituacao());
			novaSolicitacaoServicoDto.setIdContrato(1);
			novaSolicitacaoServicoDto.setIdServico(605);
			novaSolicitacaoServicoDto.setNomecontato("Layanne Batista");
			novaSolicitacaoServicoDto.setRegistroexecucao(solicitacaoServicoOrigem.getDescricao());
			solicitacaoServicoService.create(novaSolicitacaoServicoDto); 
			return new UtilTest().testNotNull(novaSolicitacaoServicoDto.getIdSolicitacaoServico());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}