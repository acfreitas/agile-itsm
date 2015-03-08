package br.com.centralit.citcorpore.teste;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.negocio.AtividadePeriodicaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class AtividadePeriodicaTest {
	public String testAtividadePeriodica() {
		try {
			AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
			AtividadePeriodicaDTO atividadeperiodicaDTO = new AtividadePeriodicaDTO();
			atividadeperiodicaDTO.setDataInicio(UtilDatas.getDataAtual());
			atividadeperiodicaDTO.setDataCriacao(UtilDatas.getDataAtual());
			atividadeperiodicaDTO.setDescricao("Teste");
			atividadeperiodicaDTO.setIdContrato(17);
			atividadeperiodicaDTO.setCriadoPor("Layanne Cristine Batista");			
			atividadeperiodicaDTO.setDuracaoEstimada(8);
			atividadeperiodicaDTO.setHoraInicio("08:00");
			atividadeperiodicaDTO.setIdGrupoAtvPeriodica(14);
			atividadeperiodicaDTO.setIdProcedimentoTecnico(35);
			atividadeperiodicaDTO.setIdSolicitacaoServico(36);
			atividadeperiodicaDTO.setTituloAtividade("Solicitação/incidente");
			Collection colItens = new ArrayList();
			ProgramacaoAtividadeDTO programacaoAtividadeDTO = new ProgramacaoAtividadeDTO();
			programacaoAtividadeDTO.setTipoAgendamento("U");
			programacaoAtividadeDTO.setDataInicio(UtilDatas.getDataAtual());
			programacaoAtividadeDTO.setHoraInicio("08:00");
			programacaoAtividadeDTO.setHoraFim("09:00");
			programacaoAtividadeDTO.setRepeticao("N");
			colItens.add(programacaoAtividadeDTO);
			atividadeperiodicaDTO.setColItens(colItens);
			atividadePeriodicaService.create(atividadeperiodicaDTO);
			return new UtilTest().testNotNull(atividadeperiodicaDTO.getIdAtividadePeriodica());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
