package br.com.centralit.testeselenium.cadastrosgerais;

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
import br.com.centralit.testeselenium.helper.UtilTeste;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class ClientesSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/dinamicViews/dinamicViews.load?identificacao=Clientes";
	private String nomeRazaoSocial;
	
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

		
		nomeRazaoSocial = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "NOMERAZAOSOCIAL");

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("NOMERAZAOSOCIAL")).sendKeys(nomeRazaoSocial);
		driver.findElement(By.id("NOMEFANTASIA")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "NOMEFANTASIA"));
		driver.findElement(By.id("CPFCNPJ")).sendKeys(UtilTeste.geraCNPJSemMascara());
		driver.findElement(By.id("OBSERVACOES")).sendKeys(UtilTeste.geraTextArea());
		driver.findElement(By.xpath("//tr[5]/td[2]/input")).click();
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nomeRazaoSocial;
		
	}
	
	@Test
	public void cit_402_CadastrarCliente() throws Exception
	{
		
		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));
		
	}
	
	@Test
	public void cit_681_Nome_RazaoSocial_Obrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
				
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome/Razão Social: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_682_SituacaoObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
			
		driver.findElement(By.id("NOMERAZAOSOCIAL")).sendKeys("Teste");
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Situação: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_683_AlterarCliente() throws Exception
	{
		
		nomeRazaoSocial = cadastrarRegistro();
		
			pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeRazaoSocial);

		nomeRazaoSocial = nomeRazaoSocial.substring(0, 10) + "editado";
		
		driver.findElement(By.id("NOMERAZAOSOCIAL")).clear();
		driver.findElement(By.id("NOMERAZAOSOCIAL")).sendKeys(nomeRazaoSocial);

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));

			pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeRazaoSocial);
		
		assertEquals(nomeRazaoSocial, driver.findElement(By.id("NOMERAZAOSOCIAL")).getAttribute("value"));
		
	}
	
	@Test
	public void cit_684_ExcluirCliente() throws Exception
	{

		nomeRazaoSocial = cadastrarRegistro();

			pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeRazaoSocial);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("Confirma a exclusão do registro??[\\s\\S]$");

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
	public String getNomeDoCampoDePesquisa()
	{
		return  "termo_pesq_TABLESEARCH_22";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//div[2]/table/tbody/tr/td[2]/div";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Listagem";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return null;
	}
	
}