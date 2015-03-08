package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class GrupoTest {
	public String testGrupo() {
		GrupoService grupoService;
		try {
			grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
			GrupoDTO grupoDto = new GrupoDTO();
			grupoDto.setIdEmpresa(1);
			grupoDto.setNome("teste layanne");
			grupoDto.setDataFim(UtilDatas.getDataAtual());
			grupoDto.setDataInicio(UtilDatas.getDataAtual());
			grupoDto.setDescricao("teste");
			grupoDto.setServiceDesk("S");
			grupoDto.setAbertura("S");
			grupoDto.setEncerramento("S");
			grupoDto.setAndamento("S");
			grupoDto.setSigla("TELAY");
			grupoService.create(grupoDto);
			return new UtilTest().testNotNull(grupoDto.getIdGrupo());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
