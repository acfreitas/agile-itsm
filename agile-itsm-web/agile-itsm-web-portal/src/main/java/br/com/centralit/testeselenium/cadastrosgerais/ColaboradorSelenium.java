package br.com.centralit.testeselenium.cadastrosgerais;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilTeste;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class ColaboradorSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/empregado/empregado.load";
	private String nomeCadastrado;
	
	@Before
	public void setUp() throws Exception
	{

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		login(driver, baseUrl, usuario, senha);

		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		driver.manage().window().maximize();
		
	}

	public String cadastrarRegistro() throws Exception{

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nome");
		
		driver.findElement(By.id("nome")).sendKeys(nomeCadastrado);
		new Select(driver.findElement(By.id("tipo"))).selectByVisibleText("Empregado CLT");
		new Select(driver.findElement(By.id("idSituacaoFuncional"))).selectByVisibleText("Ativo");
		driver.findElement(By.id("email")).sendKeys("gilberto@email.com");
		driver.findElement(By.id("telefone")).sendKeys("12345678901");
		driver.findElement(By.id("ramal")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "ramal"));
		new Select(driver.findElement(By.id("idUnidade"))).selectByVisibleText("CentralIT Filial Goiânia");
		new Select(driver.findElement(By.id("idCargo"))).selectByVisibleText("Programador");
		driver.findElement(By.id("valorSalario")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "valorSalario"));
		driver.findElement(By.id("valorProdutividadeMedia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "valorProdutividadeMedia"));
		driver.findElement(By.id("valorPlanoSaudeMedia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "valorPlanoSaudeMedia"));
		driver.findElement(By.id("valorVTraMedia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "valorVTraMedia"));
		driver.findElement(By.id("valorVRefMedia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "valorVRefMedia"));
		driver.findElement(By.id("agencia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "agencia"));
		driver.findElement(By.id("contaSalario")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "contaSalario"));
		driver.findElement(By.id("cpf")).sendKeys(UtilTeste.geraCPF());
		driver.findElement(By.id("dataNasc")).sendKeys("01/02/1980");
		driver.findElement(By.id("sexoMasculino")).click();
		driver.findElement(By.id("rg")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "rg"));
		driver.findElement(By.id("dataEmissaoRg")).sendKeys("01/01/2010");
		driver.findElement(By.id("orgExpedidor")).sendKeys("DGPCGP");
		new Select(driver.findElement(By.id("idUFOrgExpedidor"))).selectByVisibleText("GO");
		driver.findElement(By.id("ctpsNumero")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "ctpsNumero"));
		driver.findElement(By.id("dataEm")).sendKeys("01/01/2012");
		driver.findElement(By.id("ctpsSerie")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "ctpsSerie"));
		new Select(driver.findElement(By.id("ctpsIdUf"))).selectByVisibleText("GO");
		driver.findElement(By.id("nit")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "nit"));
		driver.findElement(By.id("dataAdmissao")).sendKeys("01/01/2013");
		driver.findElement(By.id("dataDemissao")).sendKeys("01/01/2016");
		driver.findElement(By.id("conjuge")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "conjuge"));
		new Select(driver.findElement(By.id("estadoCivil"))).selectByVisibleText("Solteiro");
		driver.findElement(By.id("pai")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "pai"));
		driver.findElement(By.id("mae")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "mae"));
		driver.findElement(By.id("observacoes")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "observacoes"));
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nomeCadastrado;
		
	}
	
	
	public static String cadastrarRegistro(WebDriver driver2) throws Exception{

		driver2.findElement(By.id("btnLimpar")).click();

		String nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver2, "nome");
		
		driver2.findElement(By.id("nome")).sendKeys(nomeCadastrado);
		new Select(driver2.findElement(By.id("tipo"))).selectByVisibleText("Empregado CLT");
		new Select(driver2.findElement(By.id("idSituacaoFuncional"))).selectByVisibleText("Ativo");
		driver2.findElement(By.id("email")).sendKeys("gilberto@email.com");
		driver2.findElement(By.id("telefone")).sendKeys("12345678901");
		driver2.findElement(By.id("ramal")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "ramal"));
		new Select(driver2.findElement(By.id("idUnidade"))).selectByVisibleText("CentralIT Filial Goiânia");
		new Select(driver2.findElement(By.id("idCargo"))).selectByVisibleText("Programador");
		driver2.findElement(By.id("valorSalario")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "valorSalario"));
		driver2.findElement(By.id("valorProdutividadeMedia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "valorProdutividadeMedia"));
		driver2.findElement(By.id("valorPlanoSaudeMedia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "valorPlanoSaudeMedia"));
		driver2.findElement(By.id("valorVTraMedia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "valorVTraMedia"));
		driver2.findElement(By.id("valorVRefMedia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "valorVRefMedia"));
		driver2.findElement(By.id("agencia")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "agencia"));
		driver2.findElement(By.id("contaSalario")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "contaSalario"));
		driver2.findElement(By.id("cpf")).sendKeys(UtilTeste.geraCPF());
		driver2.findElement(By.id("dataNasc")).sendKeys("01/02/1980");
		driver2.findElement(By.id("sexoMasculino")).click();
		driver2.findElement(By.id("rg")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "rg"));
		driver2.findElement(By.id("dataEmissaoRg")).sendKeys("01/01/2010");
		driver2.findElement(By.id("orgExpedidor")).sendKeys("DGPCGP");
		new Select(driver2.findElement(By.id("idUFOrgExpedidor"))).selectByVisibleText("GO");
		driver2.findElement(By.id("ctpsNumero")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "ctpsNumero"));
		driver2.findElement(By.id("dataEm")).sendKeys("01/01/2012");
		driver2.findElement(By.id("ctpsSerie")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver2, "ctpsSerie"));
		new Select(driver2.findElement(By.id("ctpsIdUf"))).selectByVisibleText("GO");
		driver2.findElement(By.id("nit")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver2, "nit"));
		driver2.findElement(By.id("dataAdmissao")).sendKeys("01/01/2013");
		driver2.findElement(By.id("dataDemissao")).sendKeys("01/01/2016");
		driver2.findElement(By.id("conjuge")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver2, "conjuge"));
		new Select(driver2.findElement(By.id("estadoCivil"))).selectByVisibleText("Solteiro");
		driver2.findElement(By.id("pai")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver2, "pai"));
		driver2.findElement(By.id("mae")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver2, "mae"));
		driver2.findElement(By.id("observacoes")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver2, "observacoes"));
		
		driver2.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver2, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver2));
		
		return nomeCadastrado;
		
	}
	
	
	@Test
	public void cit_166_CadastrarColaborador() throws Exception
	{

		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));

	}
	
	@Test
	public void cit_707_AlterarColaborador() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";
		
		driver.findElement(By.id("nome")).clear();
		driver.findElement(By.id("nome")).sendKeys(nomeCadastrado);
		new Select(driver.findElement(By.id("tipo"))).selectByVisibleText("Estágio");
		new Select(driver.findElement(By.id("idCargo"))).selectByVisibleText("Secretaria");
		new Select(driver.findElement(By.id("idUFOrgExpedidor"))).selectByVisibleText("AC");
		driver.findElement(By.id("dataAdmissao")).clear();
		driver.findElement(By.id("dataAdmissao")).sendKeys("01/01/2014");
		driver.findElement(By.id("dataDemissao")).clear();
		driver.findElement(By.id("dataDemissao")).sendKeys("01/01/2017");

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro alterado com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);
		
		

		assertEquals(nomeCadastrado, driver.findElement(By.id("nome")).getAttribute("value"));
		assertEquals("Estágio", (new Select(driver.findElement(By.id("tipo")))).getFirstSelectedOption().getText());
		assertEquals("Secretaria", (new Select(driver.findElement(By.id("idCargo")))).getFirstSelectedOption().getText());
		assertEquals("AC", (new Select(driver.findElement(By.id("idUFOrgExpedidor")))).getFirstSelectedOption().getText());
		assertEquals("01/01/2014", driver.findElement(By.id("dataAdmissao")).getAttribute("value"));
		assertEquals("01/01/2017", driver.findElement(By.id("dataDemissao")).getAttribute("value"));
		
	}
	
	@Test
	public void cit_708_ExcluirColaborador() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir[\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
	
	@Test
	public void cit_709_NomeObrigatorio() throws Exception
	{
	
		
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_710_TipoColaboradorObrigatorio() throws Exception
	{
	
		
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nome")).sendKeys("Nome obrigatorio");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Tipo de Colaborador: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_711_SituacaoObrigatorio() throws Exception
	{
	
		
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nome")).sendKeys("Nome obrigatorio");
		new Select(driver.findElement(By.id("tipo"))).selectByVisibleText("Sócio");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Situação: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_712_EmailObrigatorio() throws Exception
	{
	
		
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nome")).sendKeys("Nome obrigatorio");
		new Select(driver.findElement(By.id("tipo"))).selectByVisibleText("Sócio");
		new Select(driver.findElement(By.id("idSituacaoFuncional"))).selectByVisibleText("Inativo");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("E-mail: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_713_TelefoneObrigatorio() throws Exception
	{
	
		
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nome")).sendKeys("Nome obrigatorio");
		new Select(driver.findElement(By.id("tipo"))).selectByVisibleText("Sócio");
		new Select(driver.findElement(By.id("idSituacaoFuncional"))).selectByVisibleText("Inativo");
		driver.findElement(By.id("email")).sendKeys("gilberto@email.com");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Telefone: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_714_UnidadeObrigatorio() throws Exception
	{
	
		
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nome")).sendKeys("Nome obrigatorio");
		new Select(driver.findElement(By.id("tipo"))).selectByVisibleText("Sócio");
		new Select(driver.findElement(By.id("idSituacaoFuncional"))).selectByVisibleText("Inativo");
		driver.findElement(By.id("email")).sendKeys("gilberto@email.com");
		driver.findElement(By.id("telefone")).sendKeys("12345678901");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Unidade: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_715_CargoObrigatorio() throws Exception
	{
	
		
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nome")).sendKeys("Nome obrigatorio");
		new Select(driver.findElement(By.id("tipo"))).selectByVisibleText("Sócio");
		new Select(driver.findElement(By.id("idSituacaoFuncional"))).selectByVisibleText("Inativo");
		driver.findElement(By.id("email")).sendKeys("gilberto@email.com");
		driver.findElement(By.id("telefone")).sendKeys("12345678901");
		new Select(driver.findElement(By.id("idUnidade"))).selectByVisibleText("CentralIT Filial Rio de Janeiro");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Cargos: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
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
		return  "pesqLockupLOOKUP_EMPREGADO_nomeprocura";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//div[2]/table/tbody/tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa de Colaborador";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return "btnLimparLOOKUP_EMPREGADO";
	}

}