package br.com.centralit.citcorpore.teste;

import java.sql.Timestamp;
import java.util.Calendar;

import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class ProblemaTest {
	public String testCreateProblema() {
		ProblemaService problemaService;
		try {
			problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
			ProblemaDTO problemaDTO = new ProblemaDTO();
			Calendar cal = Calendar.getInstance();
			cal.set(2012, 05, 20, 9, 30);
			problemaDTO.setAdicionarBDCE("N");
			problemaDTO.setCausaRaiz("teste");
			problemaDTO.setDataHoraFim(UtilDatas.getDataHoraAtual());
			problemaDTO.setDataHoraInicio(new Timestamp(cal.getTimeInMillis()));
			problemaDTO.setDataHoraLimiteSolucionar(UtilDatas.getDataAtual());
			problemaDTO.setDescricao("Teste");
			problemaDTO.setIdBaseConhecimento(116);
			problemaDTO.setIdCategoriaProblema(1);
			problemaDTO.setIdCriador(251);
			problemaDTO.setIdErrosConhecidos(1);
			problemaDTO.setIdPastaBaseConhecimento(1);
			//problemaDTO.setIdServico(1);
			problemaDTO.setIdProblemaIncidente(1);
			problemaDTO.setIdProblemaItemConfiguracao(1);
			problemaDTO.setIdProblemaMudanca(1);
			problemaDTO.setIdProprietario(251);
			problemaDTO.setIdSolicitacaoServico(126);
			problemaDTO.setImpacto("Médio");
			problemaDTO.setItensConfiguracaoRelacionadosSerializado("S");
			problemaDTO.setMsgErroAssociada("Teste");
			problemaDTO.setNomeCriador("Testando");
			problemaDTO.setNomeProprietario("Testando");
			problemaDTO.setNomeServico("Teste");
			problemaDTO.setPrioridade(0);
			problemaDTO.setProativoReativo("Proativa");
			problemaDTO.setSequenciaProblema(2);
			problemaDTO.setSeveridade("Média");
			problemaDTO.setSolicitacaoServicoSerializado("S");
			problemaDTO.setSolucaoContorno("N");
			problemaDTO.setSolucaoDefinitiva("N");
			problemaDTO.setStatus("Registrado");
			problemaDTO.setStatusBaseConhecimento("");
			problemaDTO.setTitulo("TESTE 1");
			problemaDTO.setUrgencia("Alta");
			problemaService.create(problemaDTO);
			return new UtilTest().testNotNull(problemaDTO.getIdProblema());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}