package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.AnexoIncidenteDTO;
import br.com.centralit.citcorpore.negocio.AnexoIncidenteService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class AnexoTest {
	public String testCreateAnexo() {
		AnexoIncidenteService anexoIncidenteService ;
		try {
			anexoIncidenteService = (AnexoIncidenteService) ServiceLocator.getInstance().getService(AnexoIncidenteService.class, null);
			AnexoIncidenteDTO anexoIncidenteDTO = new AnexoIncidenteDTO();
			anexoIncidenteDTO.setDataFim(UtilDatas.getDataAtual());
			anexoIncidenteDTO.setDataInicio(new java.sql.Date(210, 02, 13));
			anexoIncidenteDTO.setDescricao("testes");
			anexoIncidenteDTO.setExtensao("PDF");
			anexoIncidenteDTO.setIdIncidente(1);
			anexoIncidenteDTO.setLink("");
			anexoIncidenteDTO.setNomeAnexo("Teste.pdf");
			anexoIncidenteDTO.setTextoDocumento("Testando Anexo Arquivo em Incidente");
			anexoIncidenteService.create(anexoIncidenteDTO);
			return new UtilTest().testNotNull(anexoIncidenteDTO.getIdAnexoIncidente());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}