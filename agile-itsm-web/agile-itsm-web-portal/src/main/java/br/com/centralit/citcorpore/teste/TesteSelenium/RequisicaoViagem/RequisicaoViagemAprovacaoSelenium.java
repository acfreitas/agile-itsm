package br.com.centralit.citcorpore.teste.TesteSelenium.RequisicaoViagem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RequisicaoViagemAprovacaoSelenium {
	
	private RequisicaoViagemUtilSelenium utilSelenium = new RequisicaoViagemUtilSelenium();

	@Before
	public void setUp() throws Exception {
		utilSelenium.iniciarNavegador();
	}

	@Test
	public void testCaseAprovacao() throws Exception {
		utilSelenium.login("vania.oberger", "1");
		utilSelenium.pesquisarSolicitacaoInterno();
		utilSelenium.aprovarSolicitacao();		
//		utilSelenium.naoAprovarSolicitacao();		
	}
	
	@After
	public void tearDown() throws Exception {
		utilSelenium.fechar();
	}
}
