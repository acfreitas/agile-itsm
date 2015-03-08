package br.com.centralit.testeselenium.cadastrosgerais;

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

public class UnidadeSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/unidade/unidade.load";
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
		new Select(driver.findElement(By.id("idUnidadePai"))).selectByVisibleText("Central IT - Clientes");
		driver.findElement(By.id("aceitaEntregaProduto")).click();
		driver.findElement(By.id("email")).sendKeys("gilberto@email.com");
		driver.findElement(By.id("descricao")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "descricao"));
		new Select(driver.findElement(By.id("idPais"))).selectByVisibleText("Brasil");
		new Select(driver.findElement(By.id("idUf"))).selectByVisibleText("Amapá");
		new Select(driver.findElement(By.id("idCidade"))).selectByVisibleText("Mazagão");
		driver.findElement(By.id("logradouro")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "logradouro"));
		driver.findElement(By.id("complemento")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "complemento"));
		driver.findElement(By.id("bairro")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "bairro"));
		driver.findElement(By.id("numero")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "numero"));
		driver.findElement(By.id("cep")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "cep"));
			
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nome;
		
	}
	
	@Test
	public void cit_418_CadastrarUnidade() throws Exception
	{

		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));

	}
	
	@Test
	public void cit_722_AlterarUnidade() throws Exception
	{

		nome = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nome);

		nome = nome.substring(0, 10) + "editado";
		
		driver.findElement(By.id("nome")).clear();
		driver.findElement(By.id("nome")).sendKeys(nome);
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro alterado com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nome);
	
		assertEquals(nome, driver.findElement(By.id("nome")).getAttribute("value"));

		
	}
	
	@Test
	public void cit_723_ExcluirUnidade() throws Exception
	{

		nome = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nome);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir[\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
	
	@Test
	public void cit_724_NomeObrigatorio() throws Exception
	{
	
		
		
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
		return "btnUpDate";
	}

	@Override
	public String getNomeDoCampoDePesquisa()
	{
		return  "pesqLockupLOOKUP_UNIDADE_nome";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//div[2]/table/tbody/tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa de Unidade";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return "btnLimparLOOKUP_UNIDADE";
	}

}