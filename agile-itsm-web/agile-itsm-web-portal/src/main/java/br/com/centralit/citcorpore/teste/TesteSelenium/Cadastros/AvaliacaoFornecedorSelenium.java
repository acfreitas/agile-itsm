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

public class AvaliacaoFornecedorSelenium {
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
   	driver.findElement(By.cssSelector("div[id=mmSUB81]")).click();
   	js.executeScript("chamaItemMenu('/citsmart/pages/avaliacaoFornecedor/avaliacaoFornecedor.load')");
    driver.findElement(By.id("razaoSocial")).click();
    driver.findElement(By.id("pesqLockupLOOKUP_FORNECEDOR_razaosocial")).click();
    driver.findElement(By.id("pesqLockupLOOKUP_FORNECEDOR_razaosocial")).sendKeys("central");
    js.executeScript("setRetorno_LOOKUP_FORNECEDOR('1','CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA');");
    driver.findElement(By.id("btnAddCriterio")).click();
    driver.findElement(By.id("descricao")).click();
    driver.findElement(By.id("pesqLockupLOOKUP_CRITERIOAVALIACAO_descricao")).clear();
    driver.findElement(By.id("pesqLockupLOOKUP_CRITERIOAVALIACAO_descricao")).sendKeys("teste");
    js.executeScript("setRetorno_LOOKUP_CRITERIOAVALIACAO('1','teste');");
    js.executeScript("document.formCriterio.fireEvent('atualizaGridCriterio');");
    Thread.sleep(2000L); 
    driver.findElement(By.id("btnAddAprovacao")).click();
    driver.findElement(By.id("nome")).click();
    driver.findElement(By.id("pesqLockupLOOKUP_EMPREGADO_NOME")).clear();
    driver.findElement(By.id("pesqLockupLOOKUP_EMPREGADO_NOME")).sendKeys("testando");
    js.executeScript("pesq_LOOKUP_EMPREGADO()");
    driver.findElement(By.name("sel")).click();
    js.executeScript("document.formAprovacao.fireEvent('atualizaGridAvaliacao');");
    Thread.sleep(2000L); 
    js.executeScript("gravar();");
    Thread.sleep(2000L); 
    driver.switchTo().alert().getText().endsWith("Registro gravado com sucesso!");
    driver.switchTo().alert().accept();
    driver.findElement(By.partialLinkText("Pesquisa Qualificação de Fornecedor")).click();
    driver.findElement(By.id("pesqLockupLOOKUP_AVALIACAOFORNECEDOR_nomefantasia")).clear();
    driver.findElement(By.id("pesqLockupLOOKUP_AVALIACAOFORNECEDOR_nomefantasia")).sendKeys("teste");
    driver.findElement(By.id("btnPesquisar")).click();
    driver.findElement(By.name("sel")).click();
    driver.findElement(By.id("btnLimpar")).click();
    driver.findElement(By.partialLinkText("Pesquisa Qualificação de Fornecedor")).click();
    driver.findElement(By.id("pesqLockupLOOKUP_AVALIACAOFORNECEDOR_nomefantasia")).clear();
    driver.findElement(By.id("pesqLockupLOOKUP_AVALIACAOFORNECEDOR_nomefantasia")).sendKeys("teste");
    driver.findElement(By.id("btnPesquisar")).click();
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