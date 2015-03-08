package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class ContratoTest {
	public String testCreateContrato() {
		ContratoService servicoContratoService;
		try {
			servicoContratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
			ContratoDTO contratoDTO = new ContratoDTO();
			contratoDTO.setCotacaoMoeda(1.20);
			contratoDTO.setDataContrato(UtilDatas.getDataAtual());
			contratoDTO.setDataFimContrato(new java.sql.Date(05, 12, 2012));
			contratoDTO.setDeleted("N");
			contratoDTO.setIdCliente(02);
			contratoDTO.setIdFluxo(78);
			contratoDTO.setIdFornecedor(1);
			contratoDTO.setIdGrupoSolicitante(1);
			contratoDTO.setIdMoeda(89);
			contratoDTO.setNumero("015");
			contratoDTO.setObjeto("Teste");
			contratoDTO.setSituacao("1");
			contratoDTO.setTempoEstimado(8);
			contratoDTO.setTipo("1");
			contratoDTO.setTipoTempoEstimado("1");
			contratoDTO.setValorEstimado(1.2);
			servicoContratoService.create(contratoDTO);
			return new UtilTest().testNotNull(contratoDTO.getIdContrato());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}