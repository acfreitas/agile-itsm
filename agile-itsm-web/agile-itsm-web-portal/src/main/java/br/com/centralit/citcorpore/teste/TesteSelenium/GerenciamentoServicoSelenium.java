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

public class GerenciamentoServicoSelenium {
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
		driver.get("http://localhost/citsmart/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load");
		new Select(driver.findElement(By.id("idContrato"))).selectByVisibleText("46/2012 de 26/11/2012 (ANAC - AGÊNCIA NACIONAL DE AVIAÇÃO CIVIL - CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA)");
		driver.findElement(By.cssSelector("option[value=\"1\"]")).click();
		driver.findElement(By.id("addSolicitante")).click();
		driver.findElement(By.id("pesqLockupLOOKUP_SOLICITANTE_NOME")).clear();
		driver.findElement(By.id("pesqLockupLOOKUP_SOLICITANTE_NOME")).sendKeys("layanne");
		driver.findElement(By.id("btnPesquisar")).click();
		driver.findElement(By.name("sel")).click();
	    new Select(driver.findElement(By.id("idTipoDemandaServico"))).selectByVisibleText("Requisição");
	    driver.findElement(By.cssSelector("#idTipoDemandaServico > option[value=\"3\"]")).click();
	    new Select(driver.findElement(By.id("idServico"))).selectByVisibleText("ARMAZENAMENTO E BACKUP.RETIRAR E ARMAZENAR MÍDIA DE BACKUP EM LOCAL SEGURO...");
	    driver.findElement(By.cssSelector("option[value=\"43\"]")).click();
	    new Select(driver.findElement(By.id("idGrupoAtual"))).selectByVisibleText("testando");
	    new Select(driver.findElement(By.id("idCausaIncidente"))).selectByVisibleText("....Outros");
	    new Select(driver.findElement(By.id("idCategoriaSolucao"))).selectByVisibleText("....Ajuste de Configuração de Software");
		WebElement ifr = driver.findElement(By.xpath("//iframe[@id='descricao___Frame']"));
		driver.switchTo().frame(ifr);
		driver.findElement(By.xpath("/html/body")).sendKeys("Teste");
		driver.switchTo().defaultContent();
	    js.executeScript("gravar();");
	    Thread.sleep(2000L);
	    driver.switchTo().alert().getText().endsWith("Registro inserido com sucesso.");
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