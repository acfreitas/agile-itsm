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

public class ProdutoSelenium {
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
   	js.executeScript("chamaItemMenu('/citsmart/pages/produto/produto.load')");
    driver.findElement(By.id("nomeProduto")).click();
    driver.findElement(By.name("btnLOOKUP_TIPOPRODUTO")).click();
    driver.findElement(By.name("sel")).click();
    driver.findElement(By.id("complemento")).clear();
    driver.findElement(By.id("complemento")).sendKeys("testes");
    driver.findElement(By.id("modelo")).clear();
    driver.findElement(By.id("modelo")).sendKeys("teste");
    driver.findElement(By.id("precoMercado")).clear();
    driver.findElement(By.id("precoMercado")).sendKeys("50");
    driver.findElement(By.id("detalhes")).clear();
    driver.findElement(By.id("detalhes")).sendKeys("teste");
    driver.findElement(By.id("situacao")).click();
    js.executeScript("document.form.save();");
    driver.findElement(By.xpath("//div[@id='main_container']/div[4]/ul/li[2]")).click();
    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
    Thread.sleep(2000L); 
    driver.switchTo().alert().accept();
    driver.findElement(By.partialLinkText("Pesquisa de Produto")).click();
    driver.findElement(By.id("pesqLockupLOOKUP_PRODUTO_codigoProduto")).clear();
    driver.findElement(By.id("pesqLockupLOOKUP_PRODUTO_codigoProduto")).sendKeys("1");
    js.executeScript("pesq_LOOKUP_PRODUTO()");
    driver.findElement(By.name("sel")).click();
    js.executeScript("limparForm();");
    driver.findElement(By.partialLinkText("Pesquisa de Produto")).click();
    js.executeScript("pesq_LOOKUP_PRODUTO()");
    driver.findElement(By.name("sel")).click();
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
