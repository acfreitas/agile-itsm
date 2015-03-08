package br.com.centralit.citcorpore.teste;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ClienteEmailCentralServicoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class VerificaEmailTest {
	public String testVerificaEmail() {
		EmpregadoService empregadoService;
		try {
			ClienteEmailCentralServicoDTO clienteEmailCentralServicoDTO = new ClienteEmailCentralServicoDTO();
			clienteEmailCentralServicoDTO.setHost("");
			clienteEmailCentralServicoDTO.setIdContrato(13);
			clienteEmailCentralServicoDTO.setPassword("");
			clienteEmailCentralServicoDTO.setPort(8080);
			clienteEmailCentralServicoDTO.setProvider("");
			clienteEmailCentralServicoDTO.setUsername("");
			empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDto = new EmpregadoDTO();
			Collection<EmpregadoDTO> colEmpregadoDTO = new ArrayList();
			empregadoDto.setAgencia("");
			empregadoDto.setConjuge("");
			empregadoDto.setContaSalario("");
			empregadoDto.setCpf("123.323.561-78");
			empregadoDto.setCtpsDataEmissao(UtilDatas.getDataAtual());
			empregadoDto.setCtpsIdUf(1);
			empregadoDto.setCtpsNumero("");
			empregadoDto.setCtpsSerie("");
			empregadoDto.setCustoPorHora(2.3);
			empregadoDto.setCustoTotalMes(2.5);
			empregadoDto.setDataAdmissao(UtilDatas.getDataAtual());
			empregadoDto.setDataCadastro(UtilDatas.getDataAtual());
			empregadoDto.setDataDemissao(UtilDatas.getDataAtual());
			empregadoDto.setDataEmissaoRG(UtilDatas.getDataAtual());
			empregadoDto.setDataFim(UtilDatas.getDataAtual());
			empregadoDto.setDataNascimento(UtilDatas.getDataAtual());
			empregadoDto.setEmail("layanne.batista@centralit.com.br");
			empregadoDto.setEnviaEmail("S");
			empregadoDto.setEstadoCivil(1);
			empregadoDto.setFumante("N");
			empregadoDto.setIdCargo(1);
			empregadoDto.setIdContrato(1);
			empregadoDto.setIdEmpregado(1);
			empregadoDto.setIdGrupo(1);
			empregadoDto.setIdSituacaoFuncional(1);
			empregadoDto.setIdUFOrgExpedidor(1);
			empregadoDto.setIdUnidade(1);
			empregadoDto.setIframe("");
			empregadoDto.setMae("Maria");
			empregadoDto.setNit("");
			empregadoDto.setNome("Layanne");
			empregadoDto.setNomeProcura("Layanne");
			empregadoDto.setObservacoes("teste");
			empregadoDto.setOrgExpedidor("RPTG-GO");
			empregadoDto.setPai("João");
			empregadoDto.setRamal("123");
			empregadoDto.setRg("1233255");
			empregadoDto.setSexo("F");
			empregadoDto.setTelefone("(62)32567410");
			empregadoDto.setTipo("teste");
			empregadoDto.setValorPlanoSaudeMedia(2.1);
			empregadoDto.setValorProdutividadeMedia(3.4);
			empregadoDto.setValorSalario(1.2);
			empregadoDto.setValorVRefMedia(5.1);
			empregadoDto.setValorVTraMedia(2.6);
			colEmpregadoDTO.add(empregadoDto);
			colEmpregadoDTO = empregadoService.listEmailContrato(clienteEmailCentralServicoDTO.getIdContrato());
			return new UtilTest().testNotNull(clienteEmailCentralServicoDTO.getIdContrato());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}