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
//import org.openqa.selenium.support.ui.Select;

public class AtividadesContratoSelenium {
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
		driver.findElement(By.cssSelector("a[id=itemMM29]")).click();
		js.executeScript("chamaItemMenu('/citsmart/pages/informacoesContrato/informacoesContrato.load')");
		driver.findElement(By.id("txtDESCLOOKUP_CONTRATOS")).click();
		js.executeScript("setRetorno_LOOKUP_CONTRATOS('4','072 - ANAC - CENTRAL IT TECNOLOGIA DA INFORMA��O LTDA');");
		js.executeScript("showServicosContrato();");
		Thread.sleep(2000L); 
		js.executeScript("atividadesServicoContrato(173)");
		js.executeScript("adicionarAtividade(173)");
		Thread.sleep(2000L);
		WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/dinamicViews/dinamicViews.load?modoExibicao=J&identificacao=ATIVIDADESSERVICOCONTRATO&saveInfo=173']"));
		driver.switchTo().frame(ifr);
	    driver.findElement(By.id("OBSATIVIDADE")).clear();
	    driver.findElement(By.id("OBSATIVIDADE")).sendKeys("Coletar informações dos seguintes equipamentos, por ordem.");
	    driver.findElement(By.id("DESCRICAOATIVIDADE")).clear();
	    driver.findElement(By.id("DESCRICAOATIVIDADE")).sendKeys("Efetuar diariamente a consolidação de erros, alertas e desempenho dos equipamentos que compõem a infra-estrutura de rede.");
	    driver.findElement(By.id("custoatividade")).clear();
	    driver.findElement(By.id("custoatividade")).sendKeys("30,00");
	    js.executeScript("salvar();");
	    driver.switchTo().alert().getText().endsWith("Registro gravado com sucesso!");
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