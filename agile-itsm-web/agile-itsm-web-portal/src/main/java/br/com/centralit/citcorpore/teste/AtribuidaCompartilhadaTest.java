package br.com.centralit.citcorpore.teste;

import java.util.ArrayList;
import java.util.List;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class AtribuidaCompartilhadaTest {
	public String testAtribuirCompartilhar() {
		ExecucaoSolicitacaoService execucaoSolicitacaoService;
		try {
			execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
			//GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO) document.getBean();
			GerenciamentoServicosDTO gerenciamentoBean = new GerenciamentoServicosDTO();
			gerenciamentoBean.setAcaoFluxo("true");
			gerenciamentoBean.setIdFluxo(1);
			gerenciamentoBean.setIdSolicitacao(613);
			gerenciamentoBean.setIdSolicitacaoSel("231");
			gerenciamentoBean.setIdTarefa(1);
			gerenciamentoBean.setNomeCampoOrdenacao("teste");
			gerenciamentoBean.setNumeroContratoSel("001");
			gerenciamentoBean.setOrdenacaoAsc("true");
			
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setIdUsuario(430);
			usuario.setLogin("abc");
			
			SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
			solicitacaoServicoDto.setContrato("015");
			solicitacaoServicoDto.setIdSolicitacaoServicoPesquisa(14);
			
			
			List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList();
			TarefaFluxoDTO tarefaDto = new TarefaFluxoDTO();
			colTarefasFiltradas.add(tarefaDto);
			
			boolean bFiltroPorContrato = gerenciamentoBean.getNumeroContratoSel() != null && gerenciamentoBean.getNumeroContratoSel().length() > 0;
			boolean bFiltroPorSolicitacao = gerenciamentoBean.getIdSolicitacaoSel() != null && gerenciamentoBean.getIdSolicitacaoSel().length() > 0;
			return new UtilTest().testNotNull(gerenciamentoBean);
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}