package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class TipoItemConfiguracaoTest {
	public String testTipoItemConfiguracao() {
		TipoItemConfiguracaoService tipoItemConfiguracaoService;
		try {
			tipoItemConfiguracaoService = (TipoItemConfiguracaoService) ServiceLocator.getInstance().getService(TipoItemConfiguracaoService.class, null);
			TipoItemConfiguracaoDTO tipoItemConfiguracaoDto = new TipoItemConfiguracaoDTO();
			tipoItemConfiguracaoDto.setCategoria(1);
			tipoItemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());
			tipoItemConfiguracaoDto.setDataInicio(UtilDatas.getDataAtual());
			tipoItemConfiguracaoDto.setIdEmpresa(1);
			tipoItemConfiguracaoDto.setNome("Teste Layanne");
			tipoItemConfiguracaoDto.setSistema("0");
			tipoItemConfiguracaoDto.setTag("TES");
			tipoItemConfiguracaoService.create(tipoItemConfiguracaoDto);
			return new UtilTest().testNotNull(tipoItemConfiguracaoDto.getId());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
