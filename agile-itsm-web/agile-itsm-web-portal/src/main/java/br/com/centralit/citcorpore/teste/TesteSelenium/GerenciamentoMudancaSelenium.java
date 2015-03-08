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

public class GerenciamentoMudancaSelenium {

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
		  	 driver.findElement(By.cssSelector("a[id=itemMM50]")).click();
		   	driver.findElement(By.cssSelector("div[id=mmSUB109]")).click();
			js.executeScript("chamaItemMenu('/citsmart/pages/gerenciamentoMudancas/gerenciamentoMudancas.load')");
		   	Thread.sleep(2000L);
		   	js.executeScript("abrirSolicitacao()");
			Thread.sleep(2000L);
			WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/requisicaoMudanca/requisicaoMudanca.load']"));
			driver.switchTo().frame(ifr);
		    driver.findElement(By.id("idContrato")).click();
		    new Select(driver.findElement(By.id("idContrato"))).selectByVisibleText("46/2012 de 26/11/2012 (ANAC - AGÊNCIA NACIONAL DE AVIAÇÃO CIVIL - CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA)");
		    driver.findElement(By.cssSelector("option[value=\"1\"]")).click();
		    Thread.sleep(2000L);
		    driver.findElement(By.id("addSolicitante")).click();
		    driver.findElement(By.id("pesqLockupLOOKUP_SOL_CONTRATO_NOME")).clear();
		    driver.findElement(By.id("pesqLockupLOOKUP_SOL_CONTRATO_NOME")).sendKeys("layanne");
		    driver.findElement(By.name("btnLOOKUP_SOL_CONTRATO")).click();
		    driver.findElement(By.name("sel")).click();
		    new Select(driver.findElement(By.id("idGrupoAtual"))).selectByVisibleText("Testes");
		    new Select(driver.findElement(By.id("idComite"))).selectByVisibleText("Testes");
		    new Select(driver.findElement(By.id("idUnidade"))).selectByVisibleText("Central IT Goiânia");
		    driver.findElement(By.id("titulo")).clear();
		    driver.findElement(By.id("titulo")).sendKeys("teste");
		    driver.findElement(By.id("descricao")).clear();
		    driver.findElement(By.id("descricao")).sendKeys("teste");
		    new Select(driver.findElement(By.id("idTipoMudanca"))).selectByVisibleText("Normal");
		    driver.findElement(By.cssSelector("#idTipoMudanca > option[value=\"1\"]")).click();
		    new Select(driver.findElement(By.id("nomeCategoriaMudanca"))).selectByVisibleText("Significativa");
		    driver.findElement(By.id("btnGravar")).click();
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