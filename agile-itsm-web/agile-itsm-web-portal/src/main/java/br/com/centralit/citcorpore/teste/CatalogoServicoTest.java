package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.negocio.CatalogoServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class CatalogoServicoTest {
	public String testCatalogoServico() {
		CatalogoServicoService catalogoServicoService;
		try {
			catalogoServicoService = (CatalogoServicoService) ServiceLocator.getInstance().getService(CatalogoServicoService.class, null);
			CatalogoServicoDTO catalogoServicoDto = new CatalogoServicoDTO();
			catalogoServicoDto.setIdCatalogoServico(1);
			catalogoServicoDto.setTituloCatalogoServico("Teste Layanne");
			catalogoServicoDto.setIdContrato(1);
			catalogoServicoDto.setDataInicio(UtilDatas.getDataAtual());
			catalogoServicoDto.setDataFim(UtilDatas.getDataAtual());
			catalogoServicoDto.setObs("Teste");
			catalogoServicoDto.setNomeServico("Teste");
			catalogoServicoDto.setServicoSerialize("S");
			catalogoServicoDto.setInfoCatalogoServicoSerialize("S");
			catalogoServicoDto.setDescInfoCatalogoServico("Teste");
			catalogoServicoDto.setNomeInfoCatalogoServico("Teste");
			catalogoServicoDto.setIdInfoCatalogoServico(1);
			catalogoServicoDto.setIdServicoContrato(1);
			catalogoServicoDto.setRowIndex(1);
			catalogoServicoDto.setNomeContrato("Teste");
			catalogoServicoService.create(catalogoServicoDto);
			return new UtilTest().testNotNull(catalogoServicoDto.getIdCatalogoServico());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
