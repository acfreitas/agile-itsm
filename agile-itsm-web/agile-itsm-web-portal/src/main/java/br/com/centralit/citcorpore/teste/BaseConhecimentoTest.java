package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class BaseConhecimentoTest {
	public String testCreateBaseConhecimento() {
		BaseConhecimentoService baseConhecimentoService;
		try {
			baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
			BaseConhecimentoDTO baseConhecimentoDTO = new BaseConhecimentoDTO();
			baseConhecimentoDTO.setAcessado("S");
			baseConhecimentoDTO.setConteudo("Teste de Criação");
			baseConhecimentoDTO.setDataExpiracao(UtilDatas.getDataAtual());
			baseConhecimentoDTO.setDataFim(UtilDatas.getDataAtual());
			baseConhecimentoDTO.setDataInicio(new java.sql.Date(05, 02, 2012));
			baseConhecimentoDTO.setGerenciamentoDisponibilidade("");
			baseConhecimentoDTO.setStatus("S");
			baseConhecimentoDTO.setTitulo("Teste Criação");
			baseConhecimentoDTO.setVersao("1.5");
			baseConhecimentoDTO.setDataPublicacao(UtilDatas.getDataAtual());
			baseConhecimentoDTO.setFaq("S");
			baseConhecimentoDTO.setFonteReferencia("teste");
			baseConhecimentoDTO.setIdBaseConhecimentoPai(121);
			baseConhecimentoDTO.setIdHistoricoBaseConhecimento(3);
			baseConhecimentoDTO.setIdNotificacao(9);
			baseConhecimentoDTO.setIdPasta(1);
			baseConhecimentoDTO.setIdRequisicaoMudanca(1);
			baseConhecimentoDTO.setIdSolicitacaoServico(1);
			baseConhecimentoDTO.setUltimoAcesso("");
			baseConhecimentoDTO.setTituloNotificacao("teste");
			baseConhecimentoDTO.setSituacao("DS");
			baseConhecimentoDTO.setPrivacidade("C");
			baseConhecimentoDTO.setTipoNotificacao("");
			baseConhecimentoDTO.setOrigem("1");
			baseConhecimentoDTO.setLegislacao("");
			//baseConhecimentoDTO.setDireitoAutoral("Layanne Batista");
			baseConhecimentoDTO.setArquivado("S");
			baseConhecimentoDTO.setOcultarConteudo("N");
			baseConhecimentoDTO.setNomeUsuarioAcesso("teste");
			baseConhecimentoDTO.setJustificativaObservacao("teste");
			baseConhecimentoDTO.setIdUsuarioAprovador(2);
			baseConhecimentoDTO.setIdUsuarioAutor(2);
			baseConhecimentoService.create(baseConhecimentoDTO);
			return new UtilTest().testNotNull(baseConhecimentoDTO.getIdBaseConhecimento());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
