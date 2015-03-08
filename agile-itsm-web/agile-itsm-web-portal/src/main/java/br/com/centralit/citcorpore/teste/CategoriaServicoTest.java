package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.centralit.citcorpore.negocio.CategoriaServicoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class CategoriaServicoTest {
	public String testCliente() {
		CategoriaServicoService categoriaServicoService;
		try {
			categoriaServicoService = (CategoriaServicoService) ServiceLocator.getInstance().getService(CategoriaServicoService.class, null);
			CategoriaServicoDTO categoriaServicoDto = new CategoriaServicoDTO();
			categoriaServicoDto.setIdCategoriaServicoPai(2);
			categoriaServicoDto.setIdEmpresa(1);
			categoriaServicoDto.setNomeCategoriaServico("Teste Layanne");
			categoriaServicoService.create(categoriaServicoDto);
			return new UtilTest().testNotNull(categoriaServicoDto.getIdCategoriaServico());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
