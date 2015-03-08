package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.JustificacaoFalhasDTO;
import br.com.centralit.citcorpore.negocio.JustificacaoFalhasService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class JustificativaFalhasTest {
	public String testJustificativaFalhas() {
		JustificacaoFalhasService justificacaoFalhasService;
		try {
			justificacaoFalhasService = (JustificacaoFalhasService) ServiceLocator.getInstance().getService(JustificacaoFalhasService.class, null);
			JustificacaoFalhasDTO justificacaoFalhasDto = new JustificacaoFalhasDTO();
			justificacaoFalhasDto.setIdItemConfiguracao(1);
			justificacaoFalhasDto.setIdBaseItemConfiguracao(1);
			justificacaoFalhasDto.setIdEvento(1);
			justificacaoFalhasDto.setIdEmpregado(1);
			justificacaoFalhasDto.setIdHistoricoTentativa(1);
			justificacaoFalhasDto.setDescricao("Teste");
			justificacaoFalhasDto.setData(UtilDatas.getDataAtual());
			justificacaoFalhasDto.setHora("14:30");
			justificacaoFalhasService.create(justificacaoFalhasDto);
			return new UtilTest().testNotNull(justificacaoFalhasDto.getIdJustificacaoFalha());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
