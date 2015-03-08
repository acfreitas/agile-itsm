package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class OcorrenciaSolicitacaoTest {
	public String testCreateOcorrencia() {
		OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService;
		try {
			ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
			OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = new OcorrenciaSolicitacaoDTO();
			ocorrenciaSolicitacaoDTO.setCategoria("Criação");
			ocorrenciaSolicitacaoDTO.setComplementoJustificativa("Teste");
			ocorrenciaSolicitacaoDTO.setDadosSolicitacao("Teste");
			ocorrenciaSolicitacaoDTO.setDataFim(UtilDatas.getDataAtual());
			ocorrenciaSolicitacaoDTO.setDataInicio(new java.sql.Date(05, 02, 2012));
			ocorrenciaSolicitacaoDTO.setDataregistro(UtilDatas.getDataAtual());
			ocorrenciaSolicitacaoDTO.setDescricao("Teste Criação");
			ocorrenciaSolicitacaoDTO.setHoraregistro("18:30");
			ocorrenciaSolicitacaoDTO.setIdItemTrabalho(415);
			ocorrenciaSolicitacaoDTO.setIdJustificativa(1);
			ocorrenciaSolicitacaoDTO.setIdSolicitacaoServico(355);
			ocorrenciaSolicitacaoDTO.setInformacoesContato("não se aplica");
			ocorrenciaSolicitacaoDTO.setOcorrencia("Teste Criação");
			ocorrenciaSolicitacaoDTO.setOrigem("E");
			ocorrenciaSolicitacaoDTO.setRegistradopor("Layanne Cristine Batista");
			ocorrenciaSolicitacaoDTO.setTempoGasto(0);
			ocorrenciaSolicitacaoService.create(ocorrenciaSolicitacaoDTO);
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
