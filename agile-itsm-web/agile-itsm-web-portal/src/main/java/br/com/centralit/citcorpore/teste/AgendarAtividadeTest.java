package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class AgendarAtividadeTest {
	public String testAgendarAtividade() {
		OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService;
		AtividadePeriodicaService atividadePeriodicaService;
		UsuarioService usuarioService;
		try {
			ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
			OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = new OcorrenciaSolicitacaoDTO();
			ocorrenciaSolicitacaoDTO.setCategoria("Teste");
			ocorrenciaSolicitacaoDTO.setComplementoJustificativa("Teste");
			ocorrenciaSolicitacaoDTO.setDadosSolicitacao("Teste");
			ocorrenciaSolicitacaoDTO.setDataFim(UtilDatas.getDataAtual());
			ocorrenciaSolicitacaoDTO.setDataInicio(new java.sql.Date(05, 02, 2012));
			ocorrenciaSolicitacaoDTO.setDataregistro(UtilDatas.getDataAtual());
			ocorrenciaSolicitacaoDTO.setDescricao("Teste");
			ocorrenciaSolicitacaoDTO.setHoraregistro("18:30");
			ocorrenciaSolicitacaoDTO.setIdItemTrabalho(34);
			ocorrenciaSolicitacaoDTO.setIdJustificativa(96);
			ocorrenciaSolicitacaoDTO.setIdSolicitacaoOcorrencia(65);
			ocorrenciaSolicitacaoDTO.setIdSolicitacaoServico(31);
			ocorrenciaSolicitacaoDTO.setInformacoesContato("Teste");
			ocorrenciaSolicitacaoDTO.setOcorrencia("Teste");
			ocorrenciaSolicitacaoDTO.setOrigem("E");
			ocorrenciaSolicitacaoDTO.setRegistradopor("Teste");
			ocorrenciaSolicitacaoDTO.setTempoGasto(8);
			ocorrenciaSolicitacaoService.create(ocorrenciaSolicitacaoDTO);
			usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setIdUsuario(21);
			usuarioDTO.setNomeUsuario("Teste");
			usuarioService.create(usuarioDTO);
			atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
			AtividadePeriodicaDTO atividadeperiodicaDTO = new AtividadePeriodicaDTO();
			atividadeperiodicaDTO.setAlteradoPor("Teste");
			atividadeperiodicaDTO.setDataInicio(UtilDatas.getDataAtual());
			atividadeperiodicaDTO.setDataUltAlteracao(UtilDatas.getDataAtual());
			atividadeperiodicaDTO.setDuracaoEstimada(8);
			atividadeperiodicaDTO.setHoraInicio("08:00");
			atividadeperiodicaDTO.setIdGrupoAtvPeriodica(14);
			atividadeperiodicaDTO.setIdProcedimentoTecnico(35);
			atividadeperiodicaDTO.setIdSolicitacaoServico(36);
			atividadeperiodicaDTO.setOrientacaoTecnica("Teste");
			atividadeperiodicaDTO.setTituloAtividade("Teste");
			atividadeperiodicaDTO.setIdRequisicaoMudanca(1);
			atividadePeriodicaService.create(atividadeperiodicaDTO);
			return new UtilTest().testNotNull(ocorrenciaSolicitacaoDTO.getIdOcorrencia());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
