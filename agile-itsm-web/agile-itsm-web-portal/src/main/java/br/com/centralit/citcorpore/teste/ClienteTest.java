package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class ClienteTest {
	public String testCliente() {
		ClienteService clienteService;
		try {
			clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
			ClienteDTO clienteDto = new ClienteDTO();
			clienteDto.setNomeRazaoSocial("teste layanne");
			clienteDto.setNomeFantasia("teste layanne");
			clienteDto.setObservacoes("teste layanne");
			clienteDto.setCpfCnpj("123.456.789-00");
			clienteDto.setSituacao("A");
			clienteService.create(clienteDto);
			return new UtilTest().testNotNull(clienteDto.getIdCliente());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
