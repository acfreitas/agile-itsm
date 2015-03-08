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

public class CategoriaSolucaoSelenium {
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
	   	js.executeScript("chamaItemMenu('/citsmart/pages/dinamicViews/dinamicViews.load?identificacao=categoriaSolucao')");
	    driver.findElement(By.id("DESCRICAOCATEGORIASOLUCAO")).clear();
	    driver.findElement(By.id("DESCRICAOCATEGORIASOLUCAO")).sendKeys("teste");
	    driver.findElement(By.id("DATAINICIO")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.linkText("1")).click();
	    new Select(driver.findElement(By.id("IDCATEGORIASOLUCAOPAI"))).selectByVisibleText("Software");
	    js.executeScript("salvar();");
	    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
	    Thread.sleep(2000L); 
	    driver.switchTo().alert().accept();
	    driver.findElement(By.partialLinkText("Pesquisa Categoria Solução")).click();
	    driver.findElement(By.id("termo_pesq_TABLESEARCH_15")).clear();
	    driver.findElement(By.id("termo_pesq_TABLESEARCH_15")).sendKeys("teste");
	    driver.findElement(By.id("btn_REFRESH_VIEW")).click();
	    driver.findElement(By.cssSelector("div.datagrid-cell.datagrid-cell-c5-DESCRICAOCATEGORIASOLUCAO")).click();
	    driver.findElement(By.id("btnLimpar")).click();
	    driver.findElement(By.partialLinkText("Pesquisa Categoria Solução")).click();
	    driver.findElement(By.id("btn_REFRESH_VIEW")).click();
	    driver.findElement(By.cssSelector("div.datagrid-cell.datagrid-cell-c5-DESCRICAOCATEGORIASOLUCAO")).click();
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