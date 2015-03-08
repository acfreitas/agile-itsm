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

public class GerenciamentoProblemaSelenium {


	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuilder verificationErrors = new StringBuilder();
	  LoginSelenium login;

	  @Before
	  public void setUp() throws Exception {
//	    driver = new FirefoxDriver();
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
		  	 driver.findElement(By.cssSelector("a[id=itemMM56]")).click();
		   	driver.findElement(By.cssSelector("div[id=mmSUB121]")).click();
			js.executeScript("chamaItemMenu('/citsmart/pages/gerenciamentoProblemas/gerenciamentoProblemas.load')");
		   	Thread.sleep(2000L);
		   	js.executeScript("abrirSolicitacao()");
			Thread.sleep(2000L);
			WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/problema/problema.load']"));
			driver.switchTo().frame(ifr);
		    driver.findElement(By.id("idContrato")).click();
		    new Select(driver.findElement(By.id("idContrato"))).selectByVisibleText("001 - CENTRAL IT de 01/01/2012 (CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA - CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA)");
		    driver.findElement(By.cssSelector("option[value=\"1\"]")).click();
		    new Select(driver.findElement(By.id("idOrigemAtendimento"))).selectByVisibleText("3º Nivel");
		    new Select(driver.findElement(By.id("idGrupo"))).selectByVisibleText("2º NÍVEL");
		    driver.findElement(By.id("addSolicitante")).click();
		    driver.findElement(By.id("pesqLockupLOOKUP_SOL_CONTRATO_NOME")).clear();
		    driver.findElement(By.id("pesqLockupLOOKUP_SOL_CONTRATO_NOME")).sendKeys("layanne");
		    driver.findElement(By.name("btnLOOKUP_SOL_CONTRATO")).click();
		    driver.findElement(By.xpath("(//input[@name='sel'])[2]")).click();
		    driver.findElement(By.id("titulo")).clear();
		    driver.findElement(By.id("titulo")).sendKeys("Teste");
		    driver.findElement(By.id("descricao")).clear();
		    driver.findElement(By.id("descricao")).sendKeys("Teste");
		    js.executeScript("gravar()");
		    Thread.sleep(2000L);
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