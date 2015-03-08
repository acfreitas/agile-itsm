package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.TipoUnidadeDTO;
import br.com.centralit.citcorpore.negocio.TipoUnidadeService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class TipoUnidadeTest {
	public String testTipoUnidade() {
		TipoUnidadeService tipoUnidadeService;
		try {
			tipoUnidadeService = (TipoUnidadeService) ServiceLocator.getInstance().getService(TipoUnidadeService.class, null);
			TipoUnidadeDTO tipoUnidadeDto = new TipoUnidadeDTO();
			tipoUnidadeDto.setIdEmpresa(1);
			tipoUnidadeDto.setDataFim(UtilDatas.getDataAtual());
			tipoUnidadeDto.setDataInicio(UtilDatas.getDataAtual());
			tipoUnidadeDto.setNomeTipoUnidade("Teste layanne");
			tipoUnidadeService.create(tipoUnidadeDto);
			return new UtilTest().testNotNull(tipoUnidadeDto.getIdTipoUnidade());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
