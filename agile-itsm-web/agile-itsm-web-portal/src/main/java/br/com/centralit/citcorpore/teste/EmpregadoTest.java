package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class EmpregadoTest {
	public String testCreateEmpregado() {
		EmpregadoService empregadoService;
		try {
			empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDTO = new EmpregadoDTO();
			empregadoDTO.setAgencia("2367");
			empregadoDTO.setConjuge("741123");
			empregadoDTO.setContaSalario("S");
			empregadoDTO.setCpf("278.412.632-01");
			empregadoDTO.setCtpsDataEmissao(new java.sql.Date(05, 02, 2010));
			empregadoDTO.setCtpsIdUf(25);
			empregadoDTO.setCtpsNumero("3665");
			empregadoDTO.setCtpsSerie("365");
			empregadoDTO.setCustoPorHora(1.20);
			empregadoDTO.setCustoTotalMes(40.00);
			empregadoDTO.setDataAdmissao(new java.sql.Date(20, 10, 2011));
			empregadoDTO.setDataDemissao(UtilDatas.getDataAtual());
			empregadoDTO.setDataEmissaoRG(new java.sql.Date(25, 11, 2009));
			empregadoDTO.setDataFim(UtilDatas.getDataAtual());
			empregadoDTO.setDataNascimento(new java.sql.Date(28, 04, 1991));
			empregadoDTO.setEmail("layanne.batista@centralit.com.br");
			empregadoDTO.setEstadoCivil(1);
			empregadoDTO.setFumante("N");
			empregadoDTO.setIdCargo(2);
			empregadoDTO.setIdContrato(1);
			empregadoDTO.setIdGrupo(1);
			empregadoDTO.setIdSituacaoFuncional(1);
			empregadoDTO.setIdUFOrgExpedidor(2);
			empregadoDTO.setIdUnidade(1);
			empregadoDTO.setIframe("tests");
			empregadoDTO.setMae("teste");
			empregadoDTO.setNit("teste");
			empregadoDTO.setNome("Testando");
			empregadoDTO.setNomeProcura("Testando");
			empregadoDTO.setObservacoes("Test");
			empregadoDTO.setOrgExpedidor("SPTC");
			empregadoDTO.setPai("testes");
			empregadoDTO.setRg("5478963");
			empregadoDTO.setSexo("F");
			empregadoDTO.setTelefone("6292871452");
			empregadoDTO.setTipo("E");
			empregadoDTO.setValorPlanoSaudeMedia(2.65);
			empregadoDTO.setValorProdutividadeMedia(4.23);
			empregadoDTO.setValorSalario(1000.00);
			empregadoDTO.setValorVRefMedia(250.00);
			empregadoDTO.setValorVTraMedia(100.00);
			empregadoService.create(empregadoDTO);
			return new UtilTest().testNotNull(empregadoDTO.getIdEmpregado());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}