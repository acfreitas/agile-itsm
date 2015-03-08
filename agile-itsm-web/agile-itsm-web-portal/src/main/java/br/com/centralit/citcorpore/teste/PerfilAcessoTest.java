package br.com.centralit.citcorpore.teste;

import java.util.ArrayList;
import java.util.List;

import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.negocio.PerfilAcessoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class PerfilAcessoTest {
	public String testPerfilAcesso() {
		PerfilAcessoService perfilAcessoService;
		try {
			perfilAcessoService = (PerfilAcessoService) ServiceLocator.getInstance().getService(PerfilAcessoService.class, null);
			PerfilAcessoDTO perfilAcessoDto = new PerfilAcessoDTO();
			PerfilAcessoMenuDTO perfilAcessoMenuDto = new PerfilAcessoMenuDTO();
			List<PerfilAcessoMenuDTO> colPerfilAcesso = new ArrayList();
			perfilAcessoMenuDto.setDataFim(UtilDatas.getDataAtual());
			perfilAcessoMenuDto.setDataInicio(UtilDatas.getDataAtual());
			perfilAcessoMenuDto.setDeleta("S");
			perfilAcessoMenuDto.setGrava("S");
			perfilAcessoMenuDto.setIdMenu(1);
			perfilAcessoMenuDto.setPesquisa("");
			colPerfilAcesso.add(perfilAcessoMenuDto);
			perfilAcessoDto.setAcessoMenus(colPerfilAcesso);
			perfilAcessoDto.setDataFim(UtilDatas.getDataAtual());
			perfilAcessoDto.setDataInicio(UtilDatas.getDataAtual());
			perfilAcessoDto.setNomePerfilAcesso("Teste Layanne");
			perfilAcessoDto.setAcessoMenuSerializados("S");
			perfilAcessoDto.setAprovaBaseConhecimento("S");
			perfilAcessoDto.setPermiteLeitura("S");
			perfilAcessoDto.setPermiteLeituraGravacao("S");
			perfilAcessoService.create(perfilAcessoDto);
			return new UtilTest().testNotNull(perfilAcessoDto.getIdPerfilAcesso());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
