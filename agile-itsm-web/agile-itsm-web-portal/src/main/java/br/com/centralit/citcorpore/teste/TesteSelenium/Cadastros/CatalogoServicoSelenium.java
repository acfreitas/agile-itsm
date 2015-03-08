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

public class CatalogoServicoSelenium {
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
   	driver.findElement(By.cssSelector("div[id=mmSUB65]")).click();
	js.executeScript("chamaItemMenu('/citsmart/pages/dinamicViews/dinamicViews.load?identificacao=CadastroServico')");
	driver.findElement(By.cssSelector("span.combo-arrow.combo-arrow-hover")).click();
    driver.findElement(By.xpath("//tr[@id='datagrid-row-r4-2-27']/td[2]/div")).click();
    new Select(driver.findElement(By.id("IDTIPOSERVICO"))).selectByVisibleText("ROTINEIRAS");
    new Select(driver.findElement(By.id("IDIMPORTANCIANEGOCIO"))).selectByVisibleText("Alta");
    new Select(driver.findElement(By.id("IDTIPOEVENTOSERVICO"))).selectByVisibleText("DIÁRIO");
    new Select(driver.findElement(By.id("IDTIPODEMANDASERVICO"))).selectByVisibleText("O.S. (Ordem de Serviço)");
    new Select(driver.findElement(By.id("IDLOCALEXECUCAOSERVICO"))).selectByVisibleText("Interno/Externo");
    driver.findElement(By.id("DETALHESERVICO")).clear();
    driver.findElement(By.id("DETALHESERVICO")).sendKeys("Conectividade, continuidade e disponibilidade.");
    driver.findElement(By.id("SIGLAABREV")).clear();
    driver.findElement(By.id("SIGLAABREV")).sendKeys("R- 014");
    driver.findElement(By.id("NOMESERVICO")).clear();
    driver.findElement(By.id("NOMESERVICO")).sendKeys("ORDEM DE SERVIÇO Nº R0298");
    new Select(driver.findElement(By.id("IDSITUACAOSERVICO"))).selectByVisibleText("Ativo");
    driver.findElement(By.id("DATAINICIO")).click();
    driver.findElement(By.id("DATAINICIO")).clear();
    driver.findElement(By.id("DATAINICIO")).sendKeys("15/07/2012");
    driver.findElement(By.id("divMainContent")).click();
    driver.findElement(By.xpath("(//input[@id='DISPPORTAL'])[2]")).click();
    driver.findElement(By.id("btnGravar")).click();
    driver.switchTo().alert().getText().endsWith("Registro gravado com sucesso!");
    Thread.sleep(2000L); 
    driver.switchTo().alert().accept();
//    driver.findElement(By.linkText("Listagem")).click();
//    driver.findElement(By.id("termo_pesq_TABLESEARCH_13")).clear();
//    driver.findElement(By.id("termo_pesq_TABLESEARCH_13")).sendKeys("ordem");
//    driver.findElement(By.id("btn_REFRESH_VIEW")).click();
//    driver.findElement(By.xpath("//tr[@id='datagrid-row-r9-2-0']/td[2]")).click();
//    driver.findElement(By.id("btnLimpar")).click();
//    driver.findElement(By.linkText("Listagem")).click();
//    driver.findElement(By.id("termo_pesq_TABLESEARCH_13")).clear();
//    driver.findElement(By.id("termo_pesq_TABLESEARCH_13")).sendKeys("ordem");
//    driver.findElement(By.id("btn_REFRESH_VIEW")).click();
//    driver.findElement(By.xpath("//tr[@id='datagrid-row-r9-2-0']/td[2]")).click();
//    driver.findElement(By.id("btnExcluir")).click();
//    driver.switchTo().alert().getText().endsWith("Confirma a exclusão do registro ?");
//    Thread.sleep(2000L); 
//    driver.switchTo().alert().accept(); 
//    driver.switchTo().alert().getText().endsWith("Registro excluido com sucesso!");
//    driver.switchTo().alert().accept(); 
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