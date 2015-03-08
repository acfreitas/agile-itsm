package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class ItemConfiguracaoTest {
	public String testItemConfiguracao() {
		ItemConfiguracaoService itemConfiguracaoService;
		try {
			itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();
			itemConfiguracaoDto.setIdentificacao("Teste Layanne");
			itemConfiguracaoDto.setIdItemConfiguracaoPai(1);
			itemConfiguracaoDto.setIdTipoItemConfiguracao(2);
			itemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());
			itemConfiguracaoDto.setDataInicio(UtilDatas.getDataAtual());
			itemConfiguracaoDto.setIdGrupoItemConfiguracao(1);
			itemConfiguracaoService.create(itemConfiguracaoDto);
			return new UtilTest().testNotNull(itemConfiguracaoDto.getIdItemConfiguracao());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
