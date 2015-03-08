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

public class MidiaSoftwareSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/midiaSoftware/midiaSoftware.load";
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
		driver.findElement(By.id("endFisico")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "endFisico"));
		driver.findElement(By.id("endLogico")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "endLogico"));
		new Select(driver.findElement(By.id("idMidia"))).selectByVisibleText("CD");
		new Select(driver.findElement(By.id("idTipoSoftware"))).selectByVisibleText("Navegador");
		driver.findElement(By.xpath("//div[@id='tabs-1']/div/form/div/div[7]/fieldset/div/input")).sendKeys("1");
		driver.findElement(By.id("chave")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "chave"));
		driver.findElement(By.id("qtdPermissoes")).sendKeys("5");
		
		driver.findElement(By.id("buttonAddChave")).click();

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nome;
		
	}
	
		
	@Test
	public void cit_408_CadastrarMidiaDefinitiva() throws Exception
	{

		Assert.assertTrue(pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				cadastrarRegistro()));
	}
	
	@Test
	public void cit_609_EditarMidiaDefinitiva() throws Exception
	{

		nome = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nome);
		
		nome = nome.substring(0, 10) + "editado";
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
	public void cit_610_ExcluirMidiaDefinitiva() throws Exception
	{

		nome = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nome);
		
		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir Mídia definitiva?[\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
	
	@Test
	public void cit_606_NomeMidiaObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("endFisico")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "endFisico"));
		new Select(driver.findElement(By.id("idMidia"))).selectByVisibleText("CD");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_607_EnderecoFisicoObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("nome")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nome"));
		new Select(driver.findElement(By.id("idMidia"))).selectByVisibleText("CD");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Endereço Físico: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_608_TipoMidiaObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("nome")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nome"));
		driver.findElement(By.id("endFisico")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "endFisico"));
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Tipo de mídia: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
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
		return  "pesqLockupLOOKUP_MIDIASOFTWARE_nome";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//table[@id='topoRetorno']/tbody/tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa de Mídia Definitiva";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}


	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return "pesqLockupLOOKUP_MIDIASOFTWARE_nome";
	}

}