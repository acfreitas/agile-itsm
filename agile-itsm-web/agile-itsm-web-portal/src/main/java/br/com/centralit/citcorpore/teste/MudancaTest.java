package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class MudancaTest {
	public String testCreateMudanca() {
		RequisicaoMudancaService requisicaoMudancaService;
		try {
			requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
			RequisicaoMudancaDTO requisicaoMudancaDto = new RequisicaoMudancaDTO();
			requisicaoMudancaDto.setIdProprietario(430);
			requisicaoMudancaDto.setIdSolicitante(23);
			requisicaoMudancaDto.setTipo("Padrão");
			requisicaoMudancaDto.setIdTipoMudanca(1);
			requisicaoMudancaDto.setIdGrupoNivel1(1);
			requisicaoMudancaDto.setIdGrupoAtual(1);
			requisicaoMudancaDto.setIdCalendario(1);
			requisicaoMudancaDto.setMotivo("Melhoria");
			requisicaoMudancaDto.setNivelImportanciaNegocio("Util");
			requisicaoMudancaDto.setClassificacao("");
			requisicaoMudancaDto.setNivelImpacto("M");
			requisicaoMudancaDto.setAnaliseImpacto("");
			requisicaoMudancaDto.setDataHoraConclusao(UtilDatas.getDataHoraAtual());
			requisicaoMudancaDto.setDataAceitacao(UtilDatas.getDataAtual());
			requisicaoMudancaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
			requisicaoMudancaDto.setDataHoraTermino(UtilDatas.getDataHoraAtual());
			requisicaoMudancaDto.setTitulo("Teste");
			requisicaoMudancaDto.setDescricao("Teste");
			requisicaoMudancaDto.setRisco("Alto");
			requisicaoMudancaDto.setEstimativaCusto(2.3);
			requisicaoMudancaDto.setPlanoReversao("teste");
			requisicaoMudancaDto.setStatus("Registrada");
			requisicaoMudancaDto.setPrioridade(2);
			requisicaoMudancaDto.setEnviaEmailCriacao("S");
			requisicaoMudancaDto.setEnviaEmailAcoes("S");
			requisicaoMudancaDto.setExibirQuadroMudancas("N");
			requisicaoMudancaDto.setSeqReabertura(1);
			requisicaoMudancaDto.setDataHoraCaptura(UtilDatas.getDataHoraAtual());
			requisicaoMudancaDto.setDataHoraReativacao(UtilDatas.getDataHoraAtual());
			requisicaoMudancaDto.setTempoDecorridoHH(5);
			requisicaoMudancaDto.setTempoDecorridoMM(6);
			requisicaoMudancaDto.setPrazoHH(1);
			requisicaoMudancaDto.setPrazoMM(1);
			requisicaoMudancaDto.setTempoAtendimentoHH(5);
			requisicaoMudancaDto.setTempoAtendimentoMM(3);
			requisicaoMudancaDto.setTempoAtrasoHH(1);
			requisicaoMudancaDto.setTempoAtrasoMM(0);
			requisicaoMudancaDto.setTempoCapturaHH(0);
			requisicaoMudancaDto.setTempoCapturaMM(0);
			requisicaoMudancaDto.setFase("Execução");
			requisicaoMudancaDto.setNivelUrgencia("B");
			requisicaoMudancaService.create(requisicaoMudancaDto);
			return new UtilTest().testNotNull(requisicaoMudancaDto.getIdRequisicaoMudanca());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}