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

public class FaturaSelenium {
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
		driver.findElement(By.cssSelector("a[id=itemMM31]")).click();
		js.executeScript("chamaItemMenu('/citsmart/pages/informacoesContrato/informacoesContrato.load')");
		driver.findElement(By.id("txtDESCLOOKUP_CONTRATOS")).click();
		js.executeScript("setRetorno_LOOKUP_CONTRATOS('4','072 - ANAC - CENTRAL IT TECNOLOGIA DA INFORMA��O LTDA');");
		js.executeScript("showServicosContrato();");
		Thread.sleep(2000L); 
	    js.executeScript("showFaturas();");
	    Thread.sleep(2000L);
	    js.executeScript("adicionaFatura();");
	    WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/fatura/fatura.load?idContrato=14']"));
		driver.switchTo().frame(ifr);
	    driver.findElement(By.id("descricaoFatura")).clear();
	    driver.findElement(By.id("descricaoFatura")).sendKeys("Testando");
	    driver.findElement(By.name("dataInicial")).clear();
	    driver.findElement(By.name("dataInicial")).sendKeys("15/07/2012");
	    driver.findElement(By.name("dataFinal")).clear();
	    driver.findElement(By.name("dataFinal")).sendKeys("15/07/2013");
	    new Select(driver.findElement(By.id("situacaoFatura"))).selectByVisibleText("Em Cria��o");
	    driver.findElement(By.id("btnAddListaOSFaturamento")).click();
	    driver.findElement(By.name("idOSFatura")).click();
	    driver.findElement(By.id("btnAssociarOS")).click();
	    driver.findElement(By.id("btnAdicionar")).click();
	    driver.switchTo().alert().getText().endsWith("Registro gravado com sucesso!");
	    Thread.sleep(2000L); 
	    driver.switchTo().alert().accept();   
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