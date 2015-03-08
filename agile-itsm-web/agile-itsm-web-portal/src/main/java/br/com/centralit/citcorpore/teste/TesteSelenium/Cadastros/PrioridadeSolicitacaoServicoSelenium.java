package br.com.centralit.citcorpore.teste.TesteSelenium.Cadastros;

import static org.junit.Assert.assertTrue;
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

public class PrioridadeSolicitacaoServicoSelenium {
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
	   	js.executeScript("chamaItemMenu('/citsmart/pages/prioridadeSolicitacoes/prioridadeSolicitacoes.load')");
	    driver.findElement(By.cssSelector("img[title=\"Adicionar nível do Impacto\"]")).click();
	    driver.findElement(By.xpath("(//input[@name='NIVELIMPACTO'])[6]")).clear();
	    driver.findElement(By.xpath("(//input[@name='NIVELIMPACTO'])[6]")).sendKeys("teste");
	    driver.findElement(By.xpath("(//input[@name='SIGLAIMPACTO'])[6]")).clear();
	    driver.findElement(By.xpath("(//input[@name='SIGLAIMPACTO'])[6]")).sendKeys("te");
	    driver.findElement(By.id("btnGravar")).click();
	    assertTrue(driver.switchTo().alert().getText().equals("Registro inserido com sucesso"));
	    driver.switchTo().alert().accept();
	    driver.findElement(By.cssSelector("img[title=\"Adicionar nível da Urgência\"]")).click();
	    driver.findElement(By.xpath("(//input[@name='NIVELURGENCIA'])[5]")).click();
	    driver.findElement(By.xpath("(//input[@name='NIVELURGENCIA'])[5]")).clear();
	    driver.findElement(By.xpath("(//input[@name='NIVELURGENCIA'])[5]")).sendKeys("teste");
	    driver.findElement(By.xpath("(//input[@name='SIGLAIMPACTO'])[6]")).clear();
	    driver.findElement(By.xpath("(//input[@name='SIGLAIMPACTO'])[6]")).sendKeys("te");
	    driver.findElement(By.id("btnGravar")).click();
	    assertTrue(driver.switchTo().alert().getText().equals("Registro inserido com sucesso"));
	    driver.switchTo().alert().accept();
	    driver.findElement(By.xpath("(//input[@name='SIGLAURGENCIA'])[5]")).clear();
	    driver.findElement(By.xpath("(//input[@name='SIGLAURGENCIA'])[5]")).sendKeys("te");
	    driver.findElement(By.xpath("(//button[@id='btnGravar'])[2]")).click();
	    driver.switchTo().alert().dismiss();
	    new Select(driver.findElement(By.id("IDIMPACTOSELECT"))).selectByVisibleText("teste");
	    new Select(driver.findElement(By.id("IDURGENCIASELECT"))).selectByVisibleText("teste");
	    driver.findElement(By.id("VALORPRIORIDADE")).clear();
	    driver.findElement(By.id("VALORPRIORIDADE")).sendKeys("4");
	    driver.findElement(By.cssSelector("img[title=\"Adicionar situação na Matriz de Prioridade\"]")).click();
	    driver.findElement(By.xpath("(//button[@id='btnGravar'])[3]")).click();
	    driver.switchTo().alert().accept();
	    driver.findElement(By.id("imgExcluiMatrizPrioridade21")).click();
	    driver.switchTo().alert().getText().endsWith("Deseja realmente excluir");
	    driver.switchTo().alert().accept();
	    driver.findElement(By.cssSelector("div.columns.clearfix > div > fieldset > div > #divNivelUrgencia > #btnGravar")).click();
	    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
	    driver.switchTo().alert().accept();
	    driver.findElement(By.xpath("(//img[@title='Remover quantiade'])[4]")).click();
	    driver.findElement(By.cssSelector("#divNivelUrgencia > #btnGravar")).click();
	    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
	    driver.switchTo().alert().accept();
	    driver.findElement(By.xpath("(//img[@title='Remover Impacto'])[5]")).click();
	    driver.findElement(By.id("btnGravar")).click();
	    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
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
