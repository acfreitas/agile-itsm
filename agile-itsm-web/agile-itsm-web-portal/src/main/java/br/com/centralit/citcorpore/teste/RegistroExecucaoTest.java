package br.com.centralit.citcorpore.teste;

import net.htmlparser.jericho.Source;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class RegistroExecucaoTest {
	public String testRegistrarExecucao() {
		SolicitacaoServicoService solicitacaoServicoService;
		try {
			solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setIdUsuario(430);
			solicitacaoServicoDTO.setUsuarioDto(usuarioDTO);
			solicitacaoServicoDTO.setContrato("015");
			solicitacaoServicoDTO.setDemanda("3");
			solicitacaoServicoDTO.setDescricao("Testes");
			solicitacaoServicoDTO.setDescrSituacao("Teste");
			solicitacaoServicoDTO.setDetalhamentoCausa("Teste");
			solicitacaoServicoDTO.setEmailcontato("layanne.batista@centralit.com.br");
			solicitacaoServicoDTO.setEditar("N");
			solicitacaoServicoDTO.setGrupoNivel1("A");
			solicitacaoServicoDTO.setIdCalendario(1);
			solicitacaoServicoDTO.setIdGrupoNivel1(1);
			solicitacaoServicoDTO.setIdBaseConhecimento(8);
			solicitacaoServicoDTO.setIdCategoriaServico(739);
			solicitacaoServicoDTO.setIdCategoriaSolucao(12);
			solicitacaoServicoDTO.setIdCausaIncidente(11);
			solicitacaoServicoDTO.setIdContatoSolicitacaoServico(317);
			solicitacaoServicoDTO.setIdContrato(1);
			solicitacaoServicoDTO.setIdFaseAtual(1);
			solicitacaoServicoDTO.setIdGrupoAtual(1);
			solicitacaoServicoDTO.setIdGrupoDestino(2);
			solicitacaoServicoDTO.setIdJustificativa(9);
			solicitacaoServicoDTO.setIdLocalidade(1);
			solicitacaoServicoDTO.setIdOrigem(4);
			solicitacaoServicoDTO.setIdPrioridade(5);
			solicitacaoServicoDTO.setIdResponsavel(4);
			solicitacaoServicoDTO.setIdServico(605);
			solicitacaoServicoDTO.setIdServicoContrato(9);
			solicitacaoServicoDTO.setIdSolicitacaoPai(4);
			solicitacaoServicoDTO.setIdSolicitacaoServicoPesquisa(1);
			solicitacaoServicoDTO.setIdSolicitante(251);
			solicitacaoServicoDTO.setIdTarefa(4);
			solicitacaoServicoDTO.setIdTipoDemandaServico(1);
			solicitacaoServicoDTO.setIdTipoProblema(5);
			solicitacaoServicoDTO.setIdTipoServico(1);
			solicitacaoServicoDTO.setIdUnidade(3);
			solicitacaoServicoDTO.setIdUsuarioDestino(2);
			solicitacaoServicoDTO.setImpacto("A");
			solicitacaoServicoDTO.setNomecontato("Layanne Cristine Batista");
			solicitacaoServicoDTO.setNomeCategoriaServico("Configurar");
			solicitacaoServicoDTO.setNomeServico("MONITORAMENTO.CLIENT.INSTALAR.");
			solicitacaoServicoDTO.setNomeSolicitante("Layanne Cristine Batista");
			solicitacaoServicoDTO.setNomeTarefa("Teste");
			solicitacaoServicoDTO.setNomeTipoDemandaServico("Teste");
			solicitacaoServicoDTO.setNomeTipoServico("Teste");
			solicitacaoServicoDTO.setNomeUnidadeResponsavel("Central IT");
			solicitacaoServicoDTO.setNomeUnidadeSolicitante("Central IT");
			solicitacaoServicoDTO.setNomeUsuario("Layanne Cristine Batista");
			solicitacaoServicoDTO.setNumeroRegistros(2);
			solicitacaoServicoDTO.setOrigem("OPERADOR");
			solicitacaoServicoDTO.setObservacao("Teste");
			solicitacaoServicoDTO.setPrioridade("B");
			solicitacaoServicoDTO.setRegistradoPor("Layanne");
			solicitacaoServicoDTO.setResponsavel("Layanne Cristine Batista");
			solicitacaoServicoDTO.setServico("MONITORAMENTO.CLIENT.INSTALAR.");
			solicitacaoServicoDTO.setServicoBusca("MONITORAMENTO.CLIENT.INSTALAR.");
			solicitacaoServicoDTO.setSituacao("EmAndamento");
			solicitacaoServicoDTO.setSolicitante("Layanne Cristine Batista");
			solicitacaoServicoDTO.setSolicitanteUnidade("Central IT");
			solicitacaoServicoDTO.setTelefonecontato("(062) 8221-8365");
			solicitacaoServicoDTO.setTarefa("N");
			solicitacaoServicoDTO.setUrgencia("A");	
			Source source = new Source("Teste Layanne");
			solicitacaoServicoDTO.setRegistroexecucao(source.getTextExtractor().toString());
			solicitacaoServicoService.create(solicitacaoServicoDTO);
			return new UtilTest().testNotNull(solicitacaoServicoDTO.getRegistroexecucao());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
