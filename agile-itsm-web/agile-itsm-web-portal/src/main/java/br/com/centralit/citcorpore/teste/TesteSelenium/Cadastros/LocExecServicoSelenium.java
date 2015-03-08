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

import br.com.centralit.citcorpore.teste.TesteSelenium.LoginSelenium;

public class LocExecServicoSelenium {
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
	   	js.executeScript("chamaItemMenu('/citsmart/pages/dinamicViews/dinamicViews.load?identificacao=LocalExecucaoServicos')");
	    driver.findElement(By.id("NOMELOCALEXECUCAOSERVICO")).clear();
	    driver.findElement(By.id("NOMELOCALEXECUCAOSERVICO")).sendKeys("teste");
	    driver.findElement(By.id("btnGravar")).click();
	    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
	    Thread.sleep(2000L); 
	    driver.switchTo().alert().accept();
	    driver.findElement(By.partialLinkText("Pesquisa Local Execução Serviço")).click();
	    driver.findElement(By.id("termo_pesq_TABLESEARCH_12")).clear();
	    driver.findElement(By.id("termo_pesq_TABLESEARCH_12")).sendKeys("teste");
	    driver.findElement(By.id("btn_REFRESH_VIEW")).click();
	    driver.findElement(By.cssSelector("div.datagrid-cell.datagrid-cell-c5-NOMELOCALEXECUCAOSERVICO")).click();
	    driver.findElement(By.id("btnLimpar")).click();
	    driver.findElement(By.partialLinkText("Pesquisa Local Execução Serviço")).click();
	    driver.findElement(By.cssSelector("div.datagrid-cell.datagrid-cell-c5-NOMELOCALEXECUCAOSERVICO")).click();
	    js.executeScript("excluir();");
	    driver.switchTo().alert().getText().endsWith("Deseja realmente excluir?");
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
