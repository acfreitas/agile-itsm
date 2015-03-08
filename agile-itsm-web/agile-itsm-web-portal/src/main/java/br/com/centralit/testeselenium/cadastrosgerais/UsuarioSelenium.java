package br.com.centralit.testeselenium.cadastrosgerais;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class UsuarioSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/usuario/usuario.load";
	private final String pageColaborador = "/pages/empregado/empregado.load";
	private String nomeCadastro;
	
	@Before
	public void setUp() throws Exception
	{

		maximizarTela();
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		login(driver, baseUrl, usuario, senha);

		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
	}

	@Ignore
	public String cadastrarRegistro() throws Exception{
		
		UtilWebSelenium.acessarPagina(driver, baseUrl + pageColaborador);
		String empregado = ColaboradorSelenium.cadastrarRegistro(driver);
		
		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("addEmpregado")).click();
		
		String myWindowHandle = driver.getWindowHandle();

		driver.switchTo().window(myWindowHandle);
		driver.findElement(By.id("pesqLockupLOOKUP_EMPREGADO_USUARIO_NOME")).sendKeys(empregado);
		driver.findElement(By.name("btnLOOKUP_EMPREGADO_USUARIO")).click();
		driver.findElement(By.xpath("//div[2]/table/tbody/tr[2]/td/input")).click();
			
		driver.switchTo().defaultContent();

		driver.findElement(By.id("login")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "login"));
		new Select(driver.findElement(By.id("idPerfilAcessoUsuario"))).selectByVisibleText("Desenvolvimento");
		driver.findElement(By.id("senha")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "senha"));
		
		driver.findElement(By.id("senhaNovamente")).sendKeys(driver.findElement(By.id("senha")).getAttribute("value"));
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nomeCadastro = empregado;
		
	}
	
	@Test
	public void cit_167_CadastrarUsuario() throws Exception
	{
		
		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));
		
	}
	
	@Test
	public void cit_717_ExcluirUsuario() throws Exception
	{

		nomeCadastro = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nomeCadastro);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir? [\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
			
	
	@Test
	public void cit_718_ColaboradorObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Colaborador: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_719_LoginObrigatorio() throws Exception
	{
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("addEmpregado")).click();
		
		String myWindowHandle = driver.getWindowHandle();

		driver.switchTo().window(myWindowHandle);
		
		driver.findElement(By.name("btnLOOKUP_EMPREGADO_USUARIO")).click();
		driver.findElement(By.xpath("//div[2]/table/tbody/tr[2]/td/input")).click();
		
		driver.switchTo().defaultContent();
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Login: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_720_PerfilAcessoObrigatorio() throws Exception
	{
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("addEmpregado")).click();
		
		String myWindowHandle = driver.getWindowHandle();

		driver.switchTo().window(myWindowHandle);
		
		driver.findElement(By.name("btnLOOKUP_EMPREGADO_USUARIO")).click();
		driver.findElement(By.xpath("//div[2]/table/tbody/tr[2]/td/input")).click();
		
		driver.switchTo().defaultContent();
		
		driver.findElement(By.id("login")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "login"));
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
	    assertEquals("Perfil de Acesso: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_716_AlterarUsuario() throws Exception
	{

		nomeCadastro = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nomeCadastro);
		
		String addEmpregado = driver.findElement(By.id("addEmpregado")).getAttribute("value").trim();
		
		driver.findElement(By.id("login")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "login"));
		new Select(driver.findElement(By.id("idPerfilAcessoUsuario"))).selectByVisibleText("Atendimento");
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro alterado com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				addEmpregado);
		
		assertEquals(addEmpregado, driver.findElement(By.id("addEmpregado")).getAttribute("value"));
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
		return  "pesqLockupLOOKUP_USUARIO_NOME";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//table[@id='topoRetorno']/tbody/tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa de Usuário";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return "btnLimparLOOKUP_USUARIO";
	}
	
}