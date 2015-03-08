package br.com.centralit.testeselenium.cadastrosgerais;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilTeste;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class EmpresaSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/empresa/empresa.load";
	private String nomeEmpresa;
	
	@Before
	public void setUp() throws Exception
	{

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		login(driver, baseUrl, usuario, senha);

		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		driver.manage().window().maximize();
		
	}
	@Ignore
	public String cadastrarRegistro() throws Exception{

		nomeEmpresa = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nomeEmpresa");
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nomeEmpresa")).sendKeys(nomeEmpresa);
		driver.findElement(By.id("detalhamento")).sendKeys(UtilTeste.geraTextArea());

		UtilWebSelenium.aguardar(2500);
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		UtilWebSelenium.aguardar(2000);
		
		return nomeEmpresa;
		
	}
	
	@Test
	public void cit_421_CadastrarEmpresa() throws Exception
	{

		Assert.assertTrue(pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));

	}
	
	@Test
	public void cit_689_AlterarEmpresa() throws Exception
	{

		nomeEmpresa = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeEmpresa);

		nomeEmpresa = nomeEmpresa.substring(0, 10) + "editado";
		driver.findElement(By.id("nomeEmpresa")).clear();
		driver.findElement(By.id("nomeEmpresa")).sendKeys(nomeEmpresa);

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro alterado com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeEmpresa);
		
		UtilWebSelenium.aguardar(3000);

		assertEquals(nomeEmpresa, driver.findElement(By.id("nomeEmpresa")).getAttribute("value"));
		
	}
	
	@Test
	public void cit_690_ExcluirEmpresa() throws Exception
	{

		nomeEmpresa = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeEmpresa);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir?[\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
	
	@Test
	public void cit_688_NomeObrigatorio() throws Exception
	{
	
		UtilWebSelenium.aguardar(1500);
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
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
	public String getNomeDoCampoDePesquisa()
	{
		return  "pesqLockupLOOKUP_EMPRESA_NOMEEMPRESA";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//table[@id='topoRetorno']/tbody/tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa Empresa";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return "btnLimparLOOKUP_EMPRESA";
	}

}