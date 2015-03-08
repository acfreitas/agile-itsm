package br.com.centralit.testeselenium.gerenciadecatalogosdeservico;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class TipoSolicitacaoServicoSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/dinamicViews/dinamicViews.load?identificacao=TipoSolicitacaoServico";
	private String nomeCadastrado;
	
	@Before
	public void setUp() throws Exception
	{

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		login(driver, baseUrl, usuario, senha);

		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		maximizarTela();
		
	}

	@Ignore
	public String cadastrarRegistro() throws Exception{

		
		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "NOMETIPODEMANDASERVICO");

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("NOMETIPODEMANDASERVICO")).sendKeys(nomeCadastrado);
		new Select(driver.findElement(By.id("CLASSIFICACAO"))).selectByVisibleText("Requisição");
		driver.findElement(By.id("btnGravar")).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		
		return nomeCadastrado;
		
	}

	@Test
	public void cit_469_CadastrarTipoSolicitacaoServico() throws Exception
	{

		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));


	}
	
	@Test
	public void cit_680_NomeTipoSolicitacaoServicoObrigatorio() throws Exception
	{
	
		UtilWebSelenium.aguardar(3000);
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		new Select(driver.findElement(By.id("CLASSIFICACAO"))).selectByVisibleText("Incidente");
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome Tipo Demanda Serviço: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	
	@Test
	public void cit_190_EditarTipoSolicitacaoServico() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);
		
		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";
		
		driver.findElement(By.id("NOMETIPODEMANDASERVICO")).clear();
		driver.findElement(By.id("NOMETIPODEMANDASERVICO")).sendKeys(nomeCadastrado);
		new Select(driver.findElement(By.id("CLASSIFICACAO"))).selectByVisibleText("Ordem de Serviço");

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);
		
		UtilWebSelenium.aguardar(3000);

		assertEquals(nomeCadastrado, driver.findElement(By.id("NOMETIPODEMANDASERVICO")).getAttribute("value"));
		assertEquals("Ordem de Serviço", (new Select(driver.findElement(By.id("CLASSIFICACAO")))).getFirstSelectedOption().getText());
		
	}
	
	@Test
	public void cit_677_ExcluirTipoSolicitacaoServico() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);
		
		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
		UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Confirma a exclusão do registro [\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
			
			
	@After
	public void tearDown() throws Exception
	{
		driver.quit();
		driver = null;
	}


	@Override
	public String testarQuantidadeCaracteres() throws Exception
	{
		return null;
	}

	@Override
	public String getNomeDoBotaoDePesquisa()
	{
		return "btn_REFRESH_VIEW";
	}

	@Override
	public String getNomeDoBotaoDeSalvar()
	{
		return "btnGravar";
	}

	@Override
	public String getNomeDoBotaoDeExcluir()
	{
		return "btnExcluir";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoCampoDePesquisa()
	{
		return  "termo_pesq_TABLESEARCH_33";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//tr[@id='datagrid-row-r5-2-0']/td[2]/div";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa Solicitação Serviço";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}