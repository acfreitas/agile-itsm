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

public class AdministracaoContratoSelenium {
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
	driver.findElement(By.cssSelector("a[id=itemMM29]")).click();
	js.executeScript("chamaItemMenu('/citsmart/pages/informacoesContrato/informacoesContrato.load')");
	driver.findElement(By.id("txtDESCLOOKUP_CONTRATOS")).click();
	js.executeScript("setRetorno_LOOKUP_CONTRATOS('4','072 - ANAC - CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA');");
	js.executeScript("showServicosContrato();");
	Thread.sleep(2000L); 
	driver.findElement(By.id("btnGravar")).click();
	Thread.sleep(2000L);
	WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/dinamicViews/dinamicViews.load?modoExibicao=J&identificacao=Servicos_Contrato']"));
	driver.switchTo().frame(ifr);
	driver.findElement(By.cssSelector("span.combo-arrow.combo-arrow-hover")).click();
    driver.findElement(By.xpath("//tr[@id='datagrid-row-r2-2-60']/td[2]/div")).click();
    new Select(driver.findElement(By.id("IDCONDICAOOPERACAO"))).selectByVisibleText("Horário comercial");
    driver.findElement(By.id("DATAINICIO")).click();
    driver.findElement(By.id("DATAINICIO")).clear();
    driver.findElement(By.id("DATAINICIO")).sendKeys("15/07/2012");
    driver.findElement(By.id("OBSERVACAO")).click();
    driver.findElement(By.id("DATAFIM")).click();
    driver.findElement(By.id("DATAFIM")).clear();
    driver.findElement(By.id("DATAFIM")).sendKeys("15/07/2013");
    driver.findElement(By.id("IDCONDICAOOPERACAO")).click();
    new Select(driver.findElement(By.id("IDMODELOEMAILCRIACAO"))).selectByVisibleText("Validade do Item configuraÃ§Ã£o - ${IDENTIFICACAO}");
    driver.findElement(By.cssSelector("option[value=\"21\"]")).click();
    new Select(driver.findElement(By.id("IDCALENDARIO"))).selectByVisibleText("Calendário Padrão");
    driver.findElement(By.xpath("//body[@id='body']/div[4]/div[2]")).click();
    driver.findElement(By.id("OBSERVACAO")).clear();
    driver.findElement(By.id("OBSERVACAO")).sendKeys("Conectividade, continuidade e disponibilidade.");
    driver.findElement(By.id("RESTRICOESPRESSUP")).clear();
    driver.findElement(By.id("RESTRICOESPRESSUP")).sendKeys("Os responsáveis pela execução das atividades 3 e 4 deverão possuir, no mínimo, certificação Cisco CCNA.");
    driver.findElement(By.id("OBJETIVO")).clear();
    driver.findElement(By.id("OBJETIVO")).sendKeys("Conectividade, continuidade e disponibilidade.");
    new Select(driver.findElement(By.id("IDMODELOEMAILCRIACAO"))).selectByVisibleText("Solicitação em atendimento - ${IDSOLICITACAOSERVICO}");
    new Select(driver.findElement(By.id("IDCALENDARIO"))).selectByVisibleText("Calendário Padrão");
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