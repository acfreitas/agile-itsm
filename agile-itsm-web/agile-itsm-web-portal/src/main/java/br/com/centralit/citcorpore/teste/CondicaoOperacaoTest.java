package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CondicaoOperacaoDTO;
import br.com.centralit.citcorpore.negocio.CondicaoOperacaoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class CondicaoOperacaoTest {
	public String testCondicaoOperacao() {
		CondicaoOperacaoService condicaoOperacaoService;
		try {
			condicaoOperacaoService = (CondicaoOperacaoService) ServiceLocator.getInstance().getService(CondicaoOperacaoService.class, null);
			CondicaoOperacaoDTO condicaoOperacaoDto = new CondicaoOperacaoDTO();
			condicaoOperacaoDto.setDataFim(UtilDatas.getDataAtual());
			condicaoOperacaoDto.setDataInicio(UtilDatas.getDataAtual());
			condicaoOperacaoDto.setIdEmpresa(1);
			condicaoOperacaoDto.setNomeCondicaoOperacao("Teste Layanne");
			condicaoOperacaoService.create(condicaoOperacaoDto);
			return new UtilTest().testNotNull(condicaoOperacaoDto.getIdCondicaoOperacao());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
