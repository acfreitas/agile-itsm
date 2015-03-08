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
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class BaseConhecimentoSelenium {
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
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    login = new LoginSelenium(driver, baseUrl, acceptNextAlert, verificationErrors);
  }

  @Test
  public void testUntitled() throws Exception {
	  	login.testUntitled();   
	  	JavascriptExecutor js = (JavascriptExecutor) driver;
	    driver.findElement(By.cssSelector("a[id=itemMM50]")).click();
	    driver.findElement(By.cssSelector("div[id=mmSUB96]")).click();
	    js.executeScript("chamaItemMenu('/citsmart/pages/baseConhecimento/baseConhecimento.load')");
	    driver.findElement(By.id("dataExpiracao")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.linkText("14")).click();
	    driver.findElement(By.id("titulo")).clear();
	    driver.findElement(By.id("titulo")).sendKeys("testando");
	    driver.findElement(By.id("fonteReferencia")).clear();
	    driver.findElement(By.id("fonteReferencia")).sendKeys("selenium");
	    new Select(driver.findElement(By.id("comboPasta"))).selectByVisibleText("....3.4 Procedimentos Técnicos");
	    driver.findElement(By.id("justificativaObservacao")).clear();
	    driver.findElement(By.id("justificativaObservacao")).sendKeys("testes");
	    driver.findElement(By.id("faq")).click();
	    driver.findElement(By.id("gerenciamentoDisponibilidade")).click();
	    driver.findElement(By.id("direitoAutoral")).click();
	    driver.findElement(By.id("legislacao")).click();
	    driver.findElement(By.id("faq")).click();
	    driver.findElement(By.id("btnGravar")).click();
	    driver.switchTo().alert().getText().endsWith("Registro gravado com sucesso!");
	    Thread.sleep(2000L); 
	    driver.switchTo().alert().accept(); 
//	    driver.findElement(By.partialLinkText("Pesquisa de Base de Conhecimento")).click();
//	    driver.findElement(By.id("pesqLockupLOOKUP_BASECONHECIMENTO_titulo")).clear();
//	    driver.findElement(By.id("pesqLockupLOOKUP_BASECONHECIMENTO_titulo")).sendKeys("testando");
//	    driver.findElement(By.id("btnPesquisar")).click();
//	    driver.findElement(By.name("sel")).click();;
//	    driver.findElement(By.id("btnLimpar")).click();
//	    driver.findElement(By.partialLinkText("Pesquisa de Base de Conhecimento")).click();
//	    driver.findElement(By.id("pesqLockupLOOKUP_BASECONHECIMENTO_titulo")).clear();
//	    driver.findElement(By.id("pesqLockupLOOKUP_BASECONHECIMENTO_titulo")).sendKeys("testando");
//	    driver.findElement(By.id("btnPesquisar")).click();
//	    driver.findElement(By.name("sel")).click();
//	    js.executeScript("excluir();");
//	    driver.switchTo().alert().getText().endsWith("Deseja realmente excluir?");
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

