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

public class ItemConfiguracaoSelenium {
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
	driver.findElement(By.cssSelector("div[id=mmSUB56]")).click();
	js.executeScript("chamaItemMenu('/citsmart/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load')");
    driver.findElement(By.cssSelector("ins.jstree-icon")).click();
	driver.findElement(By.id("vakata-contextmenu")).click();
    driver.findElement(By.linkText("Criar Novo Grupo")).click();
    driver.findElement(By.cssSelector("input.jstree-rename-input")).sendKeys("testando");
	driver.findElement(By.id("vakata-contextmenu")).click();
    driver.findElement(By.linkText("Criar novo item")).click();
    driver.findElement(By.cssSelector("input.jstree-rename-input")).sendKeys("testando");
    driver.findElement(By.cssSelector("a.jstree-hovered")).click();
    WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/itemConfiguracaoTree/itemConfiguracaoTree.load?idItemConfiguracao=449']")); 
	driver.switchTo().frame(ifr);
    driver.findElement(By.id("familia")).clear();
    driver.findElement(By.id("familia")).sendKeys("teste");
    driver.findElement(By.id("classe")).clear();
    driver.findElement(By.id("classe")).sendKeys("teste");
    driver.findElement(By.id("localidade")).clear();
    driver.findElement(By.id("localidade")).sendKeys("teste");
    driver.findElement(By.id("dp1363631853527")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.linkText("21")).click();
    driver.findElement(By.name("btnLOOKUP_EMPREGADO")).click();
    driver.findElement(By.id("pesqLockupLOOKUP_EMPREGADO_NOME")).clear();
    driver.findElement(By.id("pesqLockupLOOKUP_EMPREGADO_NOME")).sendKeys("layanne");
    driver.findElement(By.name("btnLOOKUP_EMPREGADO")).click();
    driver.findElement(By.xpath("(//input[@name='sel'])[2]")).click();
    driver.findElement(By.id("versao")).clear();
    driver.findElement(By.id("versao")).sendKeys("1.0");
    driver.findElement(By.id("numeroSerie")).clear();
    driver.findElement(By.id("numeroSerie")).sendKeys("2.0");
    driver.findElement(By.id("nomeTipoItemConfiguracao")).click();
    driver.findElement(By.name("btnLOOKUP_TIPOITEMCONFIGURACAO")).click();
    driver.findElement(By.name("sel")).click();
    driver.findElement(By.id("btnGravar")).click();
    driver.switchTo().alert().accept(); 
    driver.findElement(By.id("idHistoricoIC1")).click();
    driver.findElement(By.id("btnGravarBaseLine")).click();
    driver.switchTo().alert().accept(); 
    driver.findElement(By.cssSelector("img[alt=\"Restaurar\"]")).click();
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