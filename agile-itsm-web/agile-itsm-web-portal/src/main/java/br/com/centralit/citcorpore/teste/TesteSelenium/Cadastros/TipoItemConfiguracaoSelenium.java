package br.com.centralit.citcorpore.teste.TesteSelenium.Cadastros;

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
//import org.openqa.selenium.support.ui.Select;

import br.com.centralit.citcorpore.teste.TesteSelenium.LoginSelenium;

public class TipoItemConfiguracaoSelenium{
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
	    driver.findElement(By.cssSelector("a[id=itemMM50]")).click();
	   	driver.findElement(By.cssSelector("div[id=mmSUB56]")).click();
	   	js.executeScript("chamaItemMenu('/citsmart/pages/tipoItemConfiguracao/tipoItemConfiguracao.load')");
		Thread.sleep(2000L);
	    driver.findElement(By.id("nome")).clear();
	    driver.findElement(By.id("nome")).sendKeys("teste");
	    driver.findElement(By.id("tag")).clear();
	    driver.findElement(By.id("tag")).sendKeys("tes");
	    driver.findElement(By.id("botaoCaracteristica")).click();
	    driver.findElement(By.xpath("//div[28]/div/a/span")).click();
	    driver.findElement(By.id("addCaracteristica")).click();
	    driver.findElement(By.xpath("(//input[@name='sel'])[26]")).click();
	    driver.findElement(By.id("btnGravar")).click();
	    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
	    driver.switchTo().alert().accept();
	    driver.findElement(By.linkText("Pesquisa Tipo Item Configura��o")).click();
	    driver.findElement(By.id("pesqLockupLOOKUP_TIPOITEMCONFIGURACAO_nometipoitemconfiguracao")).clear();
	    driver.findElement(By.id("pesqLockupLOOKUP_TIPOITEMCONFIGURACAO_nometipoitemconfiguracao")).sendKeys("teste");
	    driver.findElement(By.id("btnPesquisar")).click();
	    driver.findElement(By.name("sel")).click();
	    driver.findElement(By.id("btnLimpar")).click();
	    driver.findElement(By.linkText("Pesquisa Tipo Item Configura��o")).click();
	    driver.findElement(By.name("sel")).click();
	    driver.findElement(By.id("btnUpDate")).click();
	    driver.switchTo().alert().getText().endsWith("Registro excluido com sucesso");
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