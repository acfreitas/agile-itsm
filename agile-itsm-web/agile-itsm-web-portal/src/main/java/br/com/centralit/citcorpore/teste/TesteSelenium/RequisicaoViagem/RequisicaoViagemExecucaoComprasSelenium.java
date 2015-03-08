package br.com.centralit.citcorpore.teste.TesteSelenium.RequisicaoViagem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RequisicaoViagemExecucaoComprasSelenium {
	
	private RequisicaoViagemUtilSelenium utilSelenium = new RequisicaoViagemUtilSelenium();
	
	@Before
	public void setUp(){
		utilSelenium.iniciarNavegador();
	}

	@Test
	public void testCaseCompras() throws Exception {
		utilSelenium.login("david", "1");
		utilSelenium.pesquisarSolicitacaoInterno();
		utilSelenium.aprovarCompra();
	}
	
	@After
	public void tearDown() throws Exception {
		utilSelenium.fechar();
	}
}
