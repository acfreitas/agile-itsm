package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.negocio.ModeloEmailService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class ModeloEmailTest {
	public String testModeloEmail() {
		ModeloEmailService modeloEmailService;
		try {
			modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, null);
			ModeloEmailDTO modeloEmailDto = new ModeloEmailDTO();
			modeloEmailDto.setTitulo("Teste Layanne");
			modeloEmailDto.setTexto("teste");
			modeloEmailDto.setIdentificador("30");
			modeloEmailDto.setSituacao("A");
			modeloEmailDto.setCampoSelecaoModeloTextual("teste");
			modeloEmailDto.setDiv("teste");
			modeloEmailDto.setMetodoExecutarVolta("S");
			modeloEmailDto.setTipoCampo("texto");
			modeloEmailService.create(modeloEmailDto);
			return new UtilTest().testNotNull(modeloEmailDto.getIdModeloEmail());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
