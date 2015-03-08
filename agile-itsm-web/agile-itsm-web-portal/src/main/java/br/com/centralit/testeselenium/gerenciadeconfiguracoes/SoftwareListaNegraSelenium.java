package br.com.centralit.testeselenium.gerenciadeconfiguracoes;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class SoftwareListaNegraSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/softwaresListaNegra/softwaresListaNegra.load";
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

		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nomeSoftwaresListaNegra");

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nomeSoftwaresListaNegra")).sendKeys(nomeCadastrado);
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nomeCadastrado;
		
	}
	
	@Test
	public void cit_452_CadastrarSoftwareListaNegra() throws Exception
	{
		
		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));
		
	}
	

	@Test
	public void cit_728_AlterarSoftwareListaNegra() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado); 

		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";
		
		driver.findElement(By.id("nomeSoftwaresListaNegra")).clear();
		driver.findElement(By.id("nomeSoftwaresListaNegra")).sendKeys(nomeCadastrado);
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro alterado com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);
		
		assertEquals(nomeCadastrado, driver.findElement(By.id("nomeSoftwaresListaNegra")).getAttribute("value"));
		
	}
	
	@Test
	public void cit_726_ExcluirSoftwareListaNegra() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir? [\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
			
	
	@Test
	public void cit_730_ValidarCampoObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome Software Lista Negra: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
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
		return "btnPesquisar";
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
		return  "pesqLockupLOOKUP_SOFTWARESLISTANEGRA_nomeSoftwaresListaNegra";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa de Softwares Lista Negra";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return "btnLimparLOOKUP_SOFTWARESLISTANEGRA";
	}
	
}