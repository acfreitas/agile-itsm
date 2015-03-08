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
import org.openqa.selenium.support.ui.Select;

import br.com.centralit.citcorpore.teste.TesteSelenium.LoginSelenium;

public class ContratoSelenium {
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
	driver.findElement(By.cssSelector("a[id=itemMM29]")).click();
	js.executeScript("chamaItemMenu('/citsmart/pages/dinamicViews/dinamicViews.load?identificacao=Contratos')");
    driver.findElement(By.cssSelector("span.combo-arrow.combo-arrow-hover")).click();
    driver.findElement(By.cssSelector("div.datagrid-cell.datagrid-cell-c5-NOMERAZAOSOCIAL")).click();
    driver.findElement(By.cssSelector("span.combo-arrow.combo-arrow-hover")).click();
    driver.findElement(By.cssSelector("div.datagrid-cell.datagrid-cell-c6-RAZAOSOCIAL")).click();
    driver.findElement(By.xpath("(//input[@id='TIPO'])[2]")).click();
    driver.findElement(By.id("NUMERO")).clear();
    driver.findElement(By.id("NUMERO")).sendKeys("072");
    driver.findElement(By.id("OBJETO")).clear();
    driver.findElement(By.id("OBJETO")).sendKeys("teste");
    driver.findElement(By.id("DATACONTRATO")).click();
    driver.findElement(By.linkText("1")).click();
    driver.findElement(By.id("DATAFIMCONTRATO")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
    driver.findElement(By.linkText("29")).click();
    new Select(driver.findElement(By.id("IDMOEDA"))).selectByVisibleText("UST");
    driver.findElement(By.id("VALORESTIMADO")).clear();
    driver.findElement(By.id("VALORESTIMADO")).sendKeys("12000");
    driver.findElement(By.id("COTACAOMOEDA")).clear();
    driver.findElement(By.id("COTACAOMOEDA")).sendKeys("30,47");
    driver.findElement(By.xpath("(//input[@id='CADASTROMANUALUSUARIO'])[2]")).click();
    js.executeScript("salvar();");
    Thread.sleep(2000L); 
    driver.switchTo().alert().getText().endsWith("Registro gravado com sucesso!");
    Thread.sleep(2000L); 
    driver.switchTo().alert().accept(); 
    driver.findElement(By.xpath("//div[@id='tabs']/div/div[3]/ul/li[2]/a/span")).click();
    driver.findElement(By.id("termo_pesq_TABLESEARCH_7")).clear();
    driver.findElement(By.id("termo_pesq_TABLESEARCH_7")).sendKeys("072");
    driver.findElement(By.id("btn_REFRESH_VIEW")).click();
    driver.findElement(By.cssSelector("div.datagrid-cell.datagrid-cell-c16-IDCLIENTE")).click();
    driver.findElement(By.id("btnLimpar")).click();
    driver.findElement(By.xpath("//div[@id='tabs']/div/div[3]/ul/li[2]/a/span")).click();
    driver.findElement(By.xpath("//tr[@id='datagrid-row-r16-2-0']/td[3]")).click();
    driver.findElement(By.id("btnExcluir")).click();
    driver.switchTo().alert().getText().endsWith("Confirma a exclusão do registro?");
    Thread.sleep(2000L); 
    driver.switchTo().alert().accept(); 
    driver.switchTo().alert().getText().endsWith("Registro excluido com sucesso!");
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

