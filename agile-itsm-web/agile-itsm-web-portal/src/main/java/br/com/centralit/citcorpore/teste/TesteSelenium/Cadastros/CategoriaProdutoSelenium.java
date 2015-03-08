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

public class CategoriaProdutoSelenium {
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
   	driver.findElement(By.cssSelector("div[id=mmSUB65]")).click();
   	driver.findElement(By.cssSelector("div[id=mmSUB81]")).click();
   	js.executeScript("chamaItemMenu('/citsmart/pages/categoriaProduto/categoriaProduto.load')");
    driver.findElement(By.id("nomeCategoria")).clear();
    driver.findElement(By.id("nomeCategoria")).sendKeys("teste");
    driver.findElement(By.id("pesoCotacaoPreco")).clear();
    driver.findElement(By.id("pesoCotacaoPreco")).sendKeys("1");
    driver.findElement(By.id("pesoCotacaoPrazoPagto")).clear();
    driver.findElement(By.id("pesoCotacaoPrazoPagto")).sendKeys("2");
    driver.findElement(By.id("pesoCotacaoTaxaJuros")).clear();
    driver.findElement(By.id("pesoCotacaoTaxaJuros")).sendKeys("3");
    driver.findElement(By.id("pesoCotacaoPrazoEntrega")).clear();
    driver.findElement(By.id("pesoCotacaoPrazoEntrega")).sendKeys("4");
    driver.findElement(By.id("pesoCotacaoPrazoGarantia")).clear();
    driver.findElement(By.id("pesoCotacaoPrazoGarantia")).sendKeys("5");
    driver.findElement(By.cssSelector("label > img")).click();
    new Select(driver.findElement(By.id("idCriterio00001"))).selectByVisibleText("testando");
    driver.findElement(By.id("pesoCotacao00001")).clear();
    driver.findElement(By.id("pesoCotacao00001")).sendKeys("5");
    driver.findElement(By.id("btnGravar")).click();
    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
    Thread.sleep(2000L); 
    driver.switchTo().alert().accept();
    driver.findElement(By.linkText("Pesquisa de Categoria de Produto")).click();
    driver.findElement(By.cssSelector("td > table > tbody > tr > td")).click();
    driver.findElement(By.id("btnLimpar")).click();
    driver.findElement(By.linkText("Pesquisa de Categoria de Produto")).click();
    driver.findElement(By.cssSelector("td > table > tbody > tr > td")).click();
    driver.findElement(By.id("btnExcluir")).click();
    driver.switchTo().alert().getText().endsWith("Deseja realmente excluir?");
    Thread.sleep(2000L); 
    driver.switchTo().alert().accept(); 
    driver.switchTo().alert().getText().endsWith("Registro excluido com sucesso");
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
