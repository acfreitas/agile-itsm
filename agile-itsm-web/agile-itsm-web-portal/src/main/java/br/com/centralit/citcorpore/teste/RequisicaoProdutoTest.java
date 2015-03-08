package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.negocio.RequisicaoProdutoService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilTest;

public class RequisicaoProdutoTest {
	public String testRequisicaoProduto() {
		RequisicaoProdutoService requisicaoProdutoService;
		try {
			requisicaoProdutoService = (RequisicaoProdutoService) ServiceLocator.getInstance().getService(RequisicaoProdutoService.class, null);
			RequisicaoProdutoDTO requisicaoProdutoDto = new RequisicaoProdutoDTO();
			requisicaoProdutoDto.setIdProjeto(1);
			requisicaoProdutoDto.setIdCentroCusto(1);
			requisicaoProdutoDto.setFinalidade("");
			requisicaoProdutoDto.setIdEnderecoEntrega(1);
			requisicaoProdutoDto.setIdCategoriaProduto(1);
			requisicaoProdutoDto.setIdProduto(1);
			requisicaoProdutoDto.setTipoIdentificacaoItem("");
			requisicaoProdutoDto.setRejeitada("N");
			requisicaoProdutoDto.setIdFornecedorColeta(1);
			requisicaoProdutoDto.setIdItemColeta(1);
			requisicaoProdutoDto.setAcao("abrir");
			requisicaoProdutoDto.setValorAprovado(2.5);
			requisicaoProdutoDto.setIdColetaPreco(1);
			requisicaoProdutoDto.setIdItemRequisicaoProduto(1);
		    requisicaoProdutoDto.setItensRequisicao_serialize("");
		    requisicaoProdutoDto.setItensCotacao_serialize("");
		    requisicaoProdutoDto.setDemanda("");
		    requisicaoProdutoDto.setDescricao("teste");
		    requisicaoProdutoDto.setDescrSituacao("teste");
		    requisicaoProdutoDto.setDetalhamentoCausa("");
		    requisicaoProdutoDto.setEditar("S");
		    requisicaoProdutoDto.setEmailcontato("S");
		    requisicaoProdutoDto.setEnviaEmailAcoes("S");
		    requisicaoProdutoDto.setAcaoFluxo("");
		    requisicaoProdutoDto.setAlterarSituacao("S");
		    requisicaoProdutoDto.setAtendimentoPresencial("N");
		    requisicaoProdutoDto.setAtrasoSLA(1.0);
		    requisicaoProdutoDto.setAtrasoSLAStr("1.0");
		    requisicaoProdutoDto.setCaracteristica("");
		    requisicaoProdutoDto.setCentroCusto("");
		    requisicaoProdutoDto.setComplementoJustificativa("");
		    requisicaoProdutoDto.setContrato("");
			requisicaoProdutoService.create(requisicaoProdutoDto);
			return new UtilTest().testNotNull(requisicaoProdutoDto.getIdRequisicaoMudanca());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
