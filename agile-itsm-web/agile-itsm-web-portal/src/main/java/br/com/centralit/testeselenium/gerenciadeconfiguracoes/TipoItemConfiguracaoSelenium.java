package br.com.centralit.testeselenium.gerenciadeconfiguracoes;

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

public class TipoItemConfiguracaoSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/tipoItemConfiguracao/tipoItemConfiguracao.load";
	private String nome;
	
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

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		nome = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nome");
		
		driver.findElement(By.id("nome")).sendKeys(nome);
		driver.findElement(By.id("tag")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "tag"));
		new Select(driver.findElement(By.id("categoria"))).selectByVisibleText("Software");
		driver.findElement(By.xpath("//div[@id='imagens']/label/input")).click();

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nome;
		
	}
	
		
	@Test
	public void cit_445_CadastrarTipoItemConfiguracao() throws Exception
	{

		Assert.assertTrue(pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				cadastrarRegistro()));
	}
	
	@Test
	public void cit_613_EditarTipoItemConfiguracao() throws Exception
	{

		nome = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nome);
		
		nome = nome.substring(0, 10) + "" + "editado";
		driver.findElement(By.id("nome")).clear();
		driver.findElement(By.id("nome")).sendKeys(nome);

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro alterado com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nome);
		
		assertEquals(nome, driver.findElement(By.id("nome")).getAttribute("value"));
		
	}
	

	
	@Test
	public void cit_614_ExcluirTipoItemConfiguracao() throws Exception
	{

		nome = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nome);
		
		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir?[\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
	
	@Test
	public void cit_611_TipoItemConfiguracaoObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("tag")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "tag"));
		new Select(driver.findElement(By.id("categoria"))).selectByVisibleText("Bios");
		driver.findElement(By.xpath("//div[@id='imagens']/label/input")).click();
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Tipo Item Configuração: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_612_TAGObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nome")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nome"));
		new Select(driver.findElement(By.id("categoria"))).selectByVisibleText("Hardware");
		driver.findElement(By.xpath("//div[@id='imagens']/label/input")).click();
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("TAG: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
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
		return "btnUpDate";
	}

	@Override
	public String getNomeDoCampoDePesquisa()
	{
		return  "pesqLockupLOOKUP_TIPOITEMCONFIGURACAO_nometipoitemconfiguracao";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//table[@id='topoRetorno']/tbody/tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa Tipo Item Configuração";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}


	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return "btnLimparLOOKUP_TIPOITEMCONFIGURACAO";
	}

}