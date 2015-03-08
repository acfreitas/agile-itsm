package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.BaseItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.BaseItemConfiguracaoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class SoftwareInsDesTest {
	public String testSoftwareInsDes() {
		BaseItemConfiguracaoService baseItemConfiguracaoService;
		try {
			baseItemConfiguracaoService = (BaseItemConfiguracaoService) ServiceLocator.getInstance().getService(BaseItemConfiguracaoService.class, null);
			BaseItemConfiguracaoDTO baseItemConfiguracaoDto = new BaseItemConfiguracaoDTO();
			baseItemConfiguracaoDto.setComando("Teste Layanne");
			baseItemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());
			baseItemConfiguracaoDto.setDataInicio(UtilDatas.getDataAtual());
			baseItemConfiguracaoDto.setExecutavel("Teste.exe");
		//	baseItemConfiguracaoDto.setIdBaseItemConfiguracaoPai(2);
			baseItemConfiguracaoDto.setIdTipoItemConfiguracao(15);
			baseItemConfiguracaoDto.setNome("Teste");
			baseItemConfiguracaoDto.setTipoexecucao("I");
			baseItemConfiguracaoService.create(baseItemConfiguracaoDto);
			return new UtilTest().testNotNull(baseItemConfiguracaoDto.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
