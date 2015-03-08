package br.com.centralit.testeselenium.gerenciadecatalogosdeservico;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class PrioridadeSolicitacaoSelenium extends TesteCitsmart
{

	private final String page = "/pages/prioridadeSolicitacoes/prioridadeSolicitacoes.load";
	private String nomeImpacto;
	private String nomeUrgencia;
	private String enderecoUrgencia;
	private String enderecoImpacto;
	
	@Before
	public void setUp() throws Exception
	{

		minimizarTela(-2000, 0);
		
		login(driver, baseUrl, usuario, senha);

		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		maximizarTela();
		
	}

	@Ignore
	private void cadastrarImpactoEUrgencia(boolean impacto, boolean urgencia) throws Exception{
		
		Integer cont;
		
		//Cadastrar Impacto
		if(impacto){
			
			driver.findElement(By.xpath("//td[5]/img")).click();
			
			cont = driver.findElements(By.xpath("//table[@id='tabelaImpacto']/tbody/tr")).size();
			enderecoImpacto = "(//input[@name='NIVELIMPACTO'])["+cont+"]";
			nomeImpacto = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, 4, enderecoImpacto);
			
			driver.findElement(By.xpath(enderecoImpacto)).sendKeys(nomeImpacto);
			driver.findElement(By.xpath("(//input[@name='SIGLAIMPACTO'])[" + cont + "]")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, 4, "(//input[@name='SIGLAIMPACTO'])[" + cont + "]"));
			
			driver.findElement(By.id("btnGravar")).click();
			
			UtilWebSelenium.waitForAlert(driver, 5000);
			
		    assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		    
		}
	    
		
	    //Cadastrar Urgencia
		if(urgencia){
			
			driver.findElement(By.xpath("//div[2]/fieldset/div/div/table/tbody/tr/td[5]/img")).click();
			
			cont = driver.findElements(By.xpath("//table[@id='tabelaUrgencia']/tbody/tr")).size();
			enderecoUrgencia = "(//input[@name='NIVELURGENCIA'])[" + cont + "]";
			nomeUrgencia = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, 4, enderecoUrgencia);
			
			driver.findElement(By.xpath(enderecoUrgencia)).sendKeys(nomeUrgencia);
			driver.findElement(By.xpath("(//input[@name='SIGLAURGENCIA'])[" + cont + "]")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, 4, "(//input[@name='SIGLAURGENCIA'])[" + cont + "]"));
			
			driver.findElement(By.xpath("(//button[@id='btnGravar'])[2]")).click();
			
			UtilWebSelenium.waitForAlert(driver, 5000);
			
		    assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));			
		    
		}
	    
	}
	
	@Test
	public void cit_462_CadastrarImpacto()  throws Exception {

		cadastrarImpactoEUrgencia(true, false);
		
		driver.findElement(By.linkText("Início")).click();
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		assertEquals(nomeImpacto, driver.findElement(By.xpath(enderecoImpacto)).getAttribute("value"));
		
	}
	
	@Test
	public void cit_182_CadastrarUrgencia()  throws Exception {
		
		cadastrarImpactoEUrgencia(false, true);
		
		driver.findElement(By.linkText("Início")).click();
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		assertEquals(nomeUrgencia, driver.findElement(By.xpath(enderecoUrgencia)).getAttribute("value"));
	    
	}
	
	@Test
	public void cit_601_CadastrarMatrizDePrioridade()  throws Exception {
		
		cadastrarImpactoEUrgencia(true, true);
		
		driver.findElement(By.xpath("//div[2]/fieldset/div/div/table/tbody/tr/td[5]/img")).click();
		
		new Select(driver.findElement(By.id("IDIMPACTOSELECT"))).selectByVisibleText(nomeImpacto);
		new Select(driver.findElement(By.id("IDURGENCIASELECT"))).selectByVisibleText(nomeUrgencia);
		driver.findElement(By.id("VALORPRIORIDADE")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, 1, "VALORPRIORIDADE"));
		
		driver.findElement(By.xpath("//img[@title='Adicionar situação na Matriz de Prioridade']")).click();
		driver.findElement(By.xpath("(//button[@id='btnGravar'])[3]")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	
	@Test
	public void cit_615_NivelImpactoCampoObrigatorio()  throws Exception {
		
		driver.findElement(By.xpath("//td[5]/img")).click();
		
		Integer cont = driver.findElements(By.xpath("//table[@id='tabelaImpacto']/tbody/tr")).size();
		
		driver.findElement(By.xpath("(//input[@name='NIVELIMPACTO'])["+cont+"]")).sendKeys("Teste campos obrigatorios");
		
		UtilWebSelenium.aguardar(2000);
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Favor preencher todos os campos!", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_621_SiglaImpactoCampoObrigatorio()  throws Exception {
		
		driver.findElement(By.xpath("//td[5]/img")).click();
		
		Integer cont = driver.findElements(By.xpath("//table[@id='tabelaImpacto']/tbody/tr")).size();
		
		driver.findElement(By.xpath("(//input[@name='SIGLAIMPACTO'])[" + cont + "]")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, 4, "(//input[@name='SIGLAIMPACTO'])[" + cont + "]"));
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Favor preencher todos os campos!", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_622_NivelUrgenciaCampoObrigatorio()  throws Exception {
		
		driver.findElement(By.xpath("//div[2]/fieldset/div/div/table/tbody/tr/td[5]/img")).click();
		
		Integer cont = driver.findElements(By.xpath("//table[@id='tabelaUrgencia']/tbody/tr")).size();
		
		driver.findElement(By.xpath("(//input[@name='SIGLAURGENCIA'])[" + cont + "]")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, 4, "(//input[@name='SIGLAURGENCIA'])[" + cont + "]"));
		
		driver.findElement(By.xpath("(//button[@id='btnGravar'])[2]")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Favor preencher todos os campos!", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_623_SiglaUrgenciaCampoObrigatorio()  throws Exception {
		
		driver.findElement(By.xpath("//div[2]/fieldset/div/div/table/tbody/tr/td[5]/img")).click();
		
		Integer cont = driver.findElements(By.xpath("//table[@id='tabelaUrgencia']/tbody/tr")).size();
		
		driver.findElement(By.xpath("(//input[@name='NIVELURGENCIA'])[" + cont + "]")).sendKeys("Teste campo obrigatorio");
		
		driver.findElement(By.xpath("(//button[@id='btnGravar'])[2]")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Favor preencher todos os campos!", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_624_NivelImpactoDaMatrizObrigatorio()  throws Exception {
		
		driver.findElement(By.xpath("//div[2]/fieldset/div/div/table/tbody/tr/td[5]/img")).click();
		
		new Select(driver.findElement(By.id("IDURGENCIASELECT"))).selectByVisibleText("Crítica");
		driver.findElement(By.id("VALORPRIORIDADE")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, 1, "VALORPRIORIDADE"));
		
		driver.findElement(By.xpath("//img[@title='Adicionar situação na Matriz de Prioridade']")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Informe o nível de impacto, de urgência e o valor da prioridade", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_625_NivelUrgenciaDaMatrizObrigatorio()  throws Exception {
		
		driver.findElement(By.xpath("//div[2]/fieldset/div/div/table/tbody/tr/td[5]/img")).click();
		
		new Select(driver.findElement(By.id("IDIMPACTOSELECT"))).selectByVisibleText("Altíssimo");
		driver.findElement(By.id("VALORPRIORIDADE")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, 1, "VALORPRIORIDADE"));
		
		driver.findElement(By.xpath("//img[@title='Adicionar situação na Matriz de Prioridade']")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Informe o nível de impacto, de urgência e o valor da prioridade", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_626_ValorPrioridadeDaMatrizObrigatorio()  throws Exception {
		
		driver.findElement(By.xpath("//div[2]/fieldset/div/div/table/tbody/tr/td[5]/img")).click();
		
		new Select(driver.findElement(By.id("IDIMPACTOSELECT"))).selectByVisibleText("Altíssimo");
		new Select(driver.findElement(By.id("IDURGENCIASELECT"))).selectByVisibleText("Crítica");
		
		driver.findElement(By.xpath("//img[@title='Adicionar situação na Matriz de Prioridade']")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Informe o nível de impacto, de urgência e o valor da prioridade", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	
	
	@Test
	public void cit_627_628_EditarImpacto() throws Exception{

		cadastrarImpactoEUrgencia(true, false);
		
		Integer cont = driver.findElements(By.xpath("//table[@id='tabelaImpacto']/tbody/tr")).size();

		nomeImpacto = driver.findElement(By.xpath("(//input[@name='NIVELIMPACTO'])["+cont+"]")).getAttribute("value");
		
		nomeImpacto = nomeImpacto.substring(0, 5) + "editado";
		
		driver.findElement(By.xpath("(//input[@name='NIVELIMPACTO'])["+cont+"]")).clear();
		driver.findElement(By.xpath("(//input[@name='NIVELIMPACTO'])["+cont+"]")).sendKeys(nomeImpacto);
		
		driver.findElement(By.xpath("(//input[@name='SIGLAIMPACTO'])[" + cont + "]")).clear();
		driver.findElement(By.xpath("(//input[@name='SIGLAIMPACTO'])[" + cont + "]")).sendKeys("ZZ");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		    
	    driver.findElement(By.linkText("Início")).click();
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		assertEquals(nomeImpacto, driver.findElement(By.xpath("(//input[@name='NIVELIMPACTO'])["+cont+"]")).getAttribute("value"));
		assertEquals("ZZ", driver.findElement(By.xpath("(//input[@name='SIGLAIMPACTO'])[" + cont + "]")).getAttribute("value"));
		
		driver.findElement(By.xpath("(//img[@title='Remover Impacto'])[" + (cont-1) + "]")).click();
		driver.findElement(By.id("btnGravar")).click();
		
	}
	
	@Test
	public void cit_629_630_EditarUrgencia() throws Exception{
		
		cadastrarImpactoEUrgencia(false, true);
		
		Integer cont = driver.findElements(By.xpath("//table[@id='tabelaUrgencia']/tbody/tr")).size();
		
		nomeUrgencia = driver.findElement(By.xpath("(//input[@name='NIVELURGENCIA'])[" + cont + "]")).getAttribute("value");
		nomeUrgencia = nomeUrgencia.substring(0, 5) + "editado";
		
		driver.findElement(By.xpath("(//input[@name='NIVELURGENCIA'])[" + cont + "]")).clear();
		driver.findElement(By.xpath("(//input[@name='NIVELURGENCIA'])[" + cont + "]")).sendKeys(nomeUrgencia);
		
		driver.findElement(By.xpath("(//input[@name='SIGLAURGENCIA'])[" + cont + "]")).clear();
		driver.findElement(By.xpath("(//input[@name='SIGLAURGENCIA'])[" + cont + "]")).sendKeys("ZZ");
		
		driver.findElement(By.xpath("(//button[@id='btnGravar'])[2]")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
			
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));			
		    
		driver.findElement(By.linkText("Início")).click();
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		assertEquals(nomeUrgencia, driver.findElement(By.xpath("(//input[@name='NIVELURGENCIA'])[" + cont + "]")).getAttribute("value"));
		assertEquals("ZZ", driver.findElement(By.xpath("(//input[@name='SIGLAURGENCIA'])[" + cont + "]")).getAttribute("value"));
		
		driver.findElement(By.xpath("(//img[@title='Remover quantiade'])[" + (cont-1) + "]")).click();
		driver.findElement(By.xpath("(//button[@id='btnGravar'])[2]")).click();
		
	}
	
	@Test
	public void cit_631_ExcluirImpacto() throws Exception{

		cadastrarImpactoEUrgencia(true, false);
		
		Integer cont = driver.findElements(By.xpath("//table[@id='tabelaImpacto']/tbody/tr")).size();

		driver.findElement(By.xpath("(//img[@title='Remover Impacto'])[" + (cont-1) + "]")).click();
		
		driver.findElement(By.xpath("//fieldset/div/div/button")).click();

		UtilWebSelenium.aguardar(2000);
		
	    assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		    
	    driver.findElement(By.linkText("Início")).click();
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		assertEquals(new Double(cont-1), new Double(driver.findElements(By.xpath("//table[@id='tabelaImpacto']/tbody/tr")).size()));
		
	}
	
	@Test
	public void cit_731_ExcluirUrgencia() throws Exception{

		cadastrarImpactoEUrgencia(false, true);
		
		Integer cont = driver.findElements(By.xpath("//table[@id='tabelaUrgencia']/tbody/tr")).size();

		driver.findElement(By.xpath("(//img[@title='Remover quantiade'])[" + (cont-1) + "]")).click();
		
		driver.findElement(By.xpath("(//button[@id='btnGravar'])[2]")).click();
		
		UtilWebSelenium.aguardar(2000);
		
	    assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		    
	    driver.findElement(By.linkText("Início")).click();
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
		UtilWebSelenium.waitForPageToLoad(driver);
		
		assertEquals(new Double(cont-1), new Double(driver.findElements(By.xpath("//table[@id='tabelaUrgencia']/tbody/tr")).size()));
		
	}
	
	@After
	public void tearDown() throws Exception
	{
		driver.quit();
		driver = null;
	}
	
}