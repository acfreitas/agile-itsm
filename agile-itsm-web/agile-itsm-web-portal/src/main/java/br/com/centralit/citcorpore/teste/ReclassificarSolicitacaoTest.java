package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class ReclassificarSolicitacaoTest {
	public String testReclassificarSolicitacao() {
		SolicitacaoServicoService solicitacaoServicoService;
		try {
			solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setIdUsuario(430);
			solicitacaoServicoDto.setReclassificar("S");
			solicitacaoServicoDto.setUsuarioDto(usuario);
			solicitacaoServicoDto.setEditar("S");
			solicitacaoServicoDto.setIdCategoriaServico(1);
			solicitacaoServicoDto.setIdContrato(1);
			solicitacaoServicoDto.setIdServico(605);
			solicitacaoServicoDto.setIdTipoDemandaServico(2);
			solicitacaoServicoDto.setUrgencia("M");
			solicitacaoServicoDto.setImpacto("B");
			solicitacaoServicoDto.setIdSolicitacaoServico(613);
			solicitacaoServicoDto.setContrato("015");
			solicitacaoServicoDto.setNomecontato("Layanne Batista");
			solicitacaoServicoDto.setIdServicoContrato(4);
			solicitacaoServicoDto.setIdContatoSolicitacaoServico(251);
			solicitacaoServicoDto.setGrupoNivel1("A");
			solicitacaoServicoDto.setIdPrioridade(5);
			solicitacaoServicoDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
			solicitacaoServicoDto.setDataHora(UtilDatas.getDataHoraAtual());
			solicitacaoServicoDto.setDataHoraFim(UtilDatas.getDataHoraAtual());
			solicitacaoServicoDto.setDataInicio(UtilDatas.getDataAtual());
			solicitacaoServicoDto.setDataFim(UtilDatas.getDataAtual());
			solicitacaoServicoDto.setDataHoraReativacao(UtilDatas.getDataHoraAtual());
			solicitacaoServicoDto.setDescricao("Teste de Reclassificação");
			solicitacaoServicoService.updateInfo(solicitacaoServicoDto);
			return new UtilTest().testNotNull(solicitacaoServicoDto.getReclassificar());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}