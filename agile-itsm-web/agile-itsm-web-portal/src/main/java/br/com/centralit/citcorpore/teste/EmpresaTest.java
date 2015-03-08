package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.EmpresaDTO;
import br.com.centralit.citcorpore.negocio.EmpresaService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class EmpresaTest {
	public String testEmpresa() {
		EmpresaService empresaService;
		try {
			empresaService = (EmpresaService) ServiceLocator.getInstance().getService(EmpresaService.class, null);
			EmpresaDTO empresaDto = new EmpresaDTO();
			empresaDto.setNomeEmpresa("teste");
			empresaDto.setDataFim(UtilDatas.getDataAtual());
			empresaDto.setDataInicio(UtilDatas.getDataAtual());
			empresaDto.setDetalhamento("Teste Layanne");
			empresaService.create(empresaDto);
			return new UtilTest().testNotNull(empresaDto.getIdEmpresa());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
