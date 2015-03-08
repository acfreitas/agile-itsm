package br.com.centralit.citcorpore.teste;

import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.ExecucaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class CapturaTarefaTest {
	public String testRealizaSolicitacao() {
	try {
		ExecucaoSolicitacaoDTO execucaoSolicitacaoDTO = new ExecucaoSolicitacaoDTO();
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setDataInicio(UtilDatas.getDataAtual());
		usuarioDTO.setDataFim(new java.sql.Date(05, 02, 2012));
		usuarioDTO.setLogin("Teste");
		usuarioDTO.setNomeUsuario("Layanne Cristine Batista");
		usuarioDTO.setSenha("abc123");
		usuarioDTO.setSenhaNovamente("abc123");
		usuarioDTO.setIdEmpregado(12313);
		usuarioDTO.setIdEmpresa(1);
		usuarioDTO.setSeguencia(12345);
		usuarioDTO.setIdGrupo(2);
		usuarioDTO.setIdPerfilAcessoUsuario(3);
		usuarioDTO.setIdUnidade(4);
		usuarioDTO.setStatus("A");
		usuarioDTO.setLdap("S");
		usuarioDTO.setLocale("Central IT");
		usuarioDTO.setUltimoAcessoPortal(UtilDatas.getDataHoraAtual());
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		usuarioService.create(usuarioDTO);
		GerenciamentoServicosDTO gerenciamentoBean = new GerenciamentoServicosDTO();
		gerenciamentoBean.setIdTarefa(1);
		execucaoSolicitacaoDTO.setIdFase(2);
		execucaoSolicitacaoDTO.setIdFluxo(3);
		execucaoSolicitacaoDTO.setIdInstanciaFluxo(1001);
		execucaoSolicitacaoDTO.setIdSolicitacaoServico(613);
		execucaoSolicitacaoDTO.setPrazoHH(48);
		execucaoSolicitacaoDTO.setPrazoMM(0);
		execucaoSolicitacaoDTO.setSeqReabertura(1);
		ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
		execucaoSolicitacaoService.executa(usuarioDTO, gerenciamentoBean.getIdTarefa(), Enumerados.ACAO_INICIAR);
	return new UtilTest().testNotNull(execucaoSolicitacaoDTO);
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
