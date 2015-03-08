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

public class LancamentoRegistroSelenium {

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
		   	driver.findElement(By.cssSelector("div[id=mmSUB115]")).click();
		   	js.executeScript("chamaItemMenu('/citsmart/pages/liberacao/liberacao.load')");
		   	
		   	driver.findElement(By.id("nomeSolicitante")).click();
		    driver.findElement(By.id("pesqLockupLOOKUP_EMPREGADO_NOME")).clear();
		    driver.findElement(By.id("pesqLockupLOOKUP_EMPREGADO_NOME")).sendKeys("layanne");
		    driver.findElement(By.name("btnLOOKUP_EMPREGADO")).click();
		    driver.findElement(By.name("sel")).click();
		    new Select(driver.findElement(By.id("situacao"))).selectByVisibleText("Aceita");
		    driver.findElement(By.id("dataInicial")).click();
		    driver.findElement(By.linkText("1")).click();
		    driver.findElement(By.id("dataFinal")).click();
		    driver.findElement(By.linkText("16")).click();
		    driver.findElement(By.id("titulo")).clear();
		    driver.findElement(By.id("titulo")).sendKeys("teste");
		    driver.findElement(By.id("descricao")).clear();
		    driver.findElement(By.id("descricao")).sendKeys("teste");
		    new Select(driver.findElement(By.id("risco"))).selectByVisibleText("Baixo");
		    driver.findElement(By.id("btnGravar")).click();
		    driver.switchTo().alert().getText().endsWith("Registro atualizado com sucesso");
		    Thread.sleep(2000L); 
		    driver.switchTo().alert().accept();
		    driver.findElement(By.partialLinkText("Pesquisa de liberações")).click();
		    driver.findElement(By.id("btnTodos")).click();
		    driver.findElement(By.id("pesqLockupLOOKUP_LIBERACAO_titulo")).clear();
		    driver.findElement(By.id("pesqLockupLOOKUP_LIBERACAO_titulo")).sendKeys("teste");
		    driver.findElement(By.id("btnPesquisar")).click();
		    driver.findElement(By.name("sel")).click();
		    driver.findElement(By.id("btnLimpar")).click();
		    driver.findElement(By.partialLinkText("Pesquisa de liberações")).click();
		    driver.findElement(By.id("pesqLockupLOOKUP_LIBERACAO_titulo")).clear();
		    driver.findElement(By.id("pesqLockupLOOKUP_LIBERACAO_titulo")).sendKeys("teste");
		    driver.findElement(By.id("btnPesquisar")).click();
		    driver.findElement(By.name("sel")).click();
		    driver.findElement(By.id("btnUpDate")).click();
		    driver.switchTo().alert().getText().endsWith("Confirma a exclusão");
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