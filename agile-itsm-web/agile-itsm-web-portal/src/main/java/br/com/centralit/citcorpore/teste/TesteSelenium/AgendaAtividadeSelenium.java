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
import org.openqa.selenium.support.ui.Select;

public class AgendaAtividadeSelenium {
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
	   	driver.findElement(By.cssSelector("div[id=mmSUB51]")).click();
	   	js.executeScript("chamaItemMenu('/citsmart/pages/gerenciamentoServicos/gerenciamentoServicos.load')");
	   	Thread.sleep(2000L);
	   	js.executeScript("agendaAtividade(1090)");
	    WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/agendarAtividade/agendarAtividade.load?idSolicitacaoServico=1090']"));
		driver.switchTo().frame(ifr);
	    driver.findElement(By.partialLinkText("Criar Agendamento")).click();
	    new Select(driver.findElement(By.id("idGrupoAtvPeriodica"))).selectByVisibleText("Atividades NÍVEL 1");
	    driver.findElement(By.id("dataInicio")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.cssSelector("span.ui-icon.ui-icon-circle-triangle-e")).click();
	    driver.findElement(By.linkText("2")).click();
	    driver.findElement(By.id("horaInicio")).clear();
	    driver.findElement(By.id("horaInicio")).sendKeys("14:00");
	    driver.findElement(By.id("duracaoEstimada")).clear();
	    driver.findElement(By.id("duracaoEstimada")).sendKeys("2");
	    js.executeScript("gravar();");
	    driver.switchTo().alert().getText().endsWith("Confirma o agendamento?");
	    Thread.sleep(2000L); 
	    driver.switchTo().alert().accept();
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