package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class FornecedorTest {
	public String testFornecedor() {
		FornecedorService fornecedorService;
		try {
			fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
			FornecedorDTO fornecedorDto = new FornecedorDTO();
			fornecedorDto.setRazaoSocial("teste layanne");
			fornecedorDto.setNomeFantasia("teste");
			fornecedorDto.setCnpj("1021452369877");
		//	fornecedorDto.setNome("");
		//	fornecedorDto.setCnpjcpf("");
			fornecedorDto.setEmail("teste@centralit.com.br");
			fornecedorDto.setObservacao("teste");
		//	fornecedorDto.setTipoPessoa("");
			fornecedorDto.setIdEndereco(25);
			fornecedorDto.setTelefone("(62)82741596");
			fornecedorDto.setFax("32147852");
			fornecedorDto.setNomeContato("Layanne Cristine Batista");
			fornecedorDto.setInscricaoEstadual("78965412");
			fornecedorDto.setInscricaoMunicipal("78965412");
			fornecedorService.create(fornecedorDto);
			return new UtilTest().testNotNull(fornecedorDto.getIdFornecedor());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
