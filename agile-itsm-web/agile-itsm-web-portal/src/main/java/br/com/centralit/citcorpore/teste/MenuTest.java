package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class MenuTest {
	public String testMenu() {
		MenuService menuService;
		try {
			menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
			MenuDTO menuDto = new MenuDTO();
			menuDto.setIdMenuPai(32);
			menuDto.setNome("Teste");
			menuDto.setDataFim(UtilDatas.getDataAtual());
			menuDto.setDataInicio(UtilDatas.getDataAtual());
			menuDto.setDescricao("teste layanne");
			menuDto.setOrdem(0);
			menuDto.setLink("");
			menuDto.setHorizontal("N");
			menuDto.setImagem("teste.png");
			menuDto.setMenuRapido("N");
			menuService.create(menuDto);
			return new UtilTest().testNotNull(menuDto.getIdMenu());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
