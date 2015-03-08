package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ImportanciaNegocioDTO;
import br.com.centralit.citcorpore.negocio.ImportanciaNegocioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class ImportanciaNegocioTest {
	public String testImportanciaNegocio() {
		ImportanciaNegocioService importanciaNegocioService;
		try {
			importanciaNegocioService = (ImportanciaNegocioService) ServiceLocator.getInstance().getService(ImportanciaNegocioService.class, null);
			ImportanciaNegocioDTO importanciaNegocioDto = new ImportanciaNegocioDTO();
			importanciaNegocioDto.setIdEmpresa(1);
			importanciaNegocioDto.setNomeImportanciaNegocio("Layanne Cristine Batista");
			importanciaNegocioDto.setSituacao("A");
			importanciaNegocioService.create(importanciaNegocioDto);
			return new UtilTest().testNotNull(importanciaNegocioDto.getIdImportanciaNegocio());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
