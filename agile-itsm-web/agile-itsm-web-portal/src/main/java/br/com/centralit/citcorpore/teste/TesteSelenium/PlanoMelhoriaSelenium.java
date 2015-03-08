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
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

public class PlanoMelhoriaSelenium{
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
		   	driver.findElement(By.cssSelector("div[id=mmSUB117]")).click();
		   	js.executeScript("chamaItemMenu('/citsmart/pages/planoMelhoria/planoMelhoria.load')");
			Thread.sleep(2000L);
		    driver.findElement(By.id("idFornecedor")).click();
		    driver.findElement(By.cssSelector("option[value=\"1\"]")).click();
		    new Select(driver.findElement(By.id("idContrato"))).selectByVisibleText("46/2012");
		    driver.findElement(By.id("titulo")).clear();
		    driver.findElement(By.id("titulo")).sendKeys("teste");
		    driver.findElement(By.id("dataInicio")).clear();
		    driver.findElement(By.id("dataInicio")).sendKeys("01/06/2013");
		    driver.findElement(By.id("dataFim")).clear();
		    driver.findElement(By.id("dataFim")).sendKeys("25/10/2013");
		    new Select(driver.findElement(By.id("situacao"))).selectByVisibleText("Ativo");
		    driver.findElement(By.id("btnGravar")).click();
		    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
		    Thread.sleep(2000L); 
		    driver.switchTo().alert().accept();
		    driver.findElement(By.cssSelector("span.tree-title")).click();
		    driver.findElement(By.cssSelector("button.icon_only.text_only")).click();
		    driver.findElement(By.cssSelector("span.tree-title")).click();

//		    driver.findElement(By.cssSelector("div.tree-node.tree-node-hover > span.tree-hit")).click();
//		    driver.findElement(By.cssSelector("div.tree-node.tree-node-hover > span.tree-hit")).click();
//		    driver.findElement(By.xpath("//ul[@id='tt']/li/ul/li/ul/li[2]/ul/li/div/span[6]")).click();
//		    driver.findElement(By.id("tituloObjetivo")).clear();
//		    driver.findElement(By.id("tituloObjetivo")).sendKeys("teste");
//		    driver.findElement(By.id("btnGravarObj")).click();
//////		    assertEquals("Registro inserido com sucesso", closeAlertAndGetItsText());
////		    assertEquals("CITSmart", driver.getTitle());
//		    driver.findElement(By.xpath("//ul[@id='tt']/li/ul/li/div/span[3]")).click();
//		    driver.findElement(By.xpath("//ul[@id='tt']/li/ul/li/div/span[2]")).click();
//		    driver.findElement(By.cssSelector("div.tree-node.tree-node-hover > span.tree-hit")).click();
//		    driver.findElement(By.cssSelector("div.tree-node.tree-node-hover > span.tree-hit")).click();
//		    driver.findElement(By.cssSelector("div.tree-node.tree-node-hover > span.tree-hit")).click();
//		    driver.findElement(By.cssSelector("div.tree-node.tree-node-hover > span.tree-title")).click();
//		    driver.findElement(By.id("tituloAcao")).clear();
//		    driver.findElement(By.id("tituloAcao")).sendKeys("teste");
////		    driver.findElement(By.id("btnGravarAcao")).click();
//////		    assertEquals("Data Início: Campo obrigatório", closeAlertAndGetItsText());
//		    driver.findElement(By.cssSelector("div.tree-node.tree-node-hover > span.tree-hit")).click();
//		    driver.findElement(By.cssSelector("div.tree-node.tree-node-hover > span.tree-title")).click();
//		    driver.findElement(By.id("tituloMonitoramento")).clear();
//		    driver.findElement(By.id("tituloMonitoramento")).sendKeys("teste");
//		    driver.findElement(By.id("fatorCriticoSucesso")).clear();
//		    driver.findElement(By.id("fatorCriticoSucesso")).sendKeys("teste");
//		    driver.findElement(By.id("kpi")).clear();
//		    driver.findElement(By.id("kpi")).sendKeys("teste");
//		    driver.findElement(By.id("btnGravarMon")).click();
//		    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso");
//		    driver.switchTo().alert().accept();
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