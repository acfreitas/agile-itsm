package br.com.centralit.citcorpore.teste.TesteSelenium;

import static org.junit.Assert.fail;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class OrdensServicoSelenium {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuilder verificationErrors = new StringBuilder();
  LoginSelenium login;

  @Before
  public void setUp() throws Exception {
//    driver = new FirefoxDriver();
	DesiredCapabilities capability = DesiredCapabilities.firefox();
	driver = new RemoteWebDriver(new URL("http://10.2.1.3:4444/wd/hub"), capability);
    baseUrl = "http://localhost/citsmart";
    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    login = new LoginSelenium(driver, baseUrl, acceptNextAlert, verificationErrors);
  }

  @Test
  public void testUntitled() throws Exception {
		login.testUntitled();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.findElement(By.cssSelector("a[id=itemMM29]")).click();
		js.executeScript("chamaItemMenu('/citsmart/pages/informacoesContrato/informacoesContrato.load')");
		driver.findElement(By.id("txtDESCLOOKUP_CONTRATOS")).click();
		js.executeScript("setRetorno_LOOKUP_CONTRATOS('4','072 - ANAC - CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA');");
		js.executeScript("showServicosContrato();");
		Thread.sleep(2000L); 
	    js.executeScript("showOS();");
	    Thread.sleep(2000L);
	    js.executeScript("adicionaOS();");
	    WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/os/os.load?idContrato=4']"));
		driver.switchTo().frame(ifr);
		
		driver.findElement(By.id("dataInicio")).clear();
		driver.findElement(By.id("dataInicio")).sendKeys("01/04/2013");
		driver.findElement(By.id("dataFim")).clear();
		driver.findElement(By.id("dataFim")).sendKeys("25/04/2013");
		driver.findElement(By.id("idServicoContrato")).click();
		new Select(driver.findElement(By.id("idServicoContrato"))).selectByVisibleText("R- 014 - ORDEM DE SERVIÇO Nº R0298");
		driver.findElement(By.cssSelector("option[value=\"173\"]")).click();
		driver.findElement(By.id("numero")).clear();
		driver.findElement(By.id("numero")).sendKeys("001");
		driver.findElement(By.id("nomeAreaRequisitante")).clear();
		driver.findElement(By.id("nomeAreaRequisitante")).sendKeys("teste");
		new Select(driver.findElement(By.id("situacaoOS"))).selectByVisibleText("Em criação");
	    driver.findElement(By.id("btnGravar")).click();
	    driver.switchTo().alert().getText().endsWith("OS gravada com sucesso!");
	    Thread.sleep(2000L); 
	    driver.switchTo().alert().accept();

//	    driver.findElement(By.id("tdEmCriacao")).click();
//	    driver.findElement(By.cssSelector("img[title=\"Editar a O.S.\"]")).click();
//	    new Select(driver.findElement(By.id("situacaoOS"))).selectByVisibleText("Aprovada");
//	    driver.findElement(By.id("btnGravar")).click();
//	    driver.switchTo().alert().getText().endsWith("OS gravada com sucesso!");
//	    driver.switchTo().alert().accept();
//	    driver.findElement(By.id("tdEmExecucao")).click();
//	    driver.findElement(By.id("tdAprovada")).click();
//	    driver.findElement(By.cssSelector("img[title=\"Gerar R.A.\"]")).click();
//	    driver.findElement(By.id("dataInicioExecucao")).clear();
//	    driver.findElement(By.id("dataInicioExecucao")).sendKeys("22/03/2013");
//	    driver.findElement(By.id("dataFimExecucao")).clear();
//	    driver.findElement(By.id("dataFimExecucao")).sendKeys("22/06/2013");
//	    driver.findElement(By.id("quantidade")).clear();
//	    driver.findElement(By.id("quantidade")).sendKeys("1");
//	    driver.findElement(By.id("btnGravaRegistroExecucao")).click();
//	    driver.switchTo().alert().getText().endsWith("R.A. gerado com sucesso!");
//	    driver.switchTo().alert().accept();
//	    driver.findElement(By.id("tdTodas")).click();
//	    driver.findElement(By.cssSelector("img[title=\"Expandir O.S.\"]")).click();
//	    new Select(driver.findElement(By.id("situacaoOS"))).selectByVisibleText("Executada");
//	    driver.findElement(By.id("numeroOcorrencias00001")).clear();
//	    driver.findElement(By.id("numeroOcorrencias00001")).sendKeys("5");
//	    driver.findElement(By.id("percAplicado00001")).clear();
//	    driver.findElement(By.id("percAplicado00001")).sendKeys("3");
//	    driver.findElement(By.id("btnGravar")).click();
//	    driver.switchTo().alert().getText().endsWith("Informe a descrição da Glosa! Linha: 1");
//	    driver.switchTo().alert().accept();
//	    driver.findElement(By.id("descricaoGlosa00001")).clear();
//	    driver.findElement(By.id("descricaoGlosa00001")).sendKeys("TESTE");
//	    driver.findElement(By.id("btnGravar")).click();
//	    driver.switchTo().alert().getText().endsWith("Registro atualizado com sucesso!");
//	    Thread.sleep(2000L); 
//	    driver.switchTo().alert().accept();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}