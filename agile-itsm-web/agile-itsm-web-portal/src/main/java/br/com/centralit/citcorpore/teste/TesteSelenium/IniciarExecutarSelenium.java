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

public class IniciarExecutarSelenium {
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
	    driver.findElement(By.cssSelector("a[id=itemMM52]")).click();
	   	driver.findElement(By.cssSelector("div[id=mmSUB53]")).click();
	   	js.executeScript("chamaItemMenu('/citsmart/pages/gerenciamentoServicos/gerenciamentoServicos.load')");
	   	Thread.sleep(2000L);
	    js.executeScript("prepararExecucaoTarefa(52015,43941,'E')");
	    WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?escalar=S&alterarSituacao=S&idSolicitacaoServico=43941&idTarefa=52015&acaoFluxo=E']"));
		driver.switchTo().frame(ifr); 
		
		  WebElement ifm = driver.findElement(By.xpath("//form[@action='/citsmart/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos']"));
			driver.switchTo().frame(ifm); 
		
	    Thread.sleep(2000L);
	    driver.findElement(By.id("btnGravarEFinalizar")).click();
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