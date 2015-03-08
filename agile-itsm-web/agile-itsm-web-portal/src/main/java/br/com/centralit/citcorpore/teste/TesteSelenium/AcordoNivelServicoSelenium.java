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

public class AcordoNivelServicoSelenium {
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
		js.executeScript("setRetorno_LOOKUP_CONTRATOS('4','072 - ANAC - CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA');");
		js.executeScript("showServicosContrato();");
		Thread.sleep(2000L); 
		js.executeScript("acordosServicoContrato(173)");
		js.executeScript("adicionarSLA(173)");
		Thread.sleep(2000L);
		WebElement ifr = driver.findElement(By.xpath("//iframe[@src='/citsmart/pages/dinamicViews/dinamicViews.load?modoExibicao=J&identificacao=Acordo_Nivel_Servico&saveInfo=173']"));
		driver.switchTo().frame(ifr);
	    new Select(driver.findElement(By.id("TIPO"))).selectByVisibleText("OS - Resultados esperados");
	    driver.findElement(By.id("TITULOSLA")).clear();
	    driver.findElement(By.id("TITULOSLA")).sendKeys("SUPERIOR TRIBUNAL DE JUSTIÇA");
	    driver.findElement(By.id("DESCRICAOSLA")).clear();
	    driver.findElement(By.id("DESCRICAOSLA")).sendKeys("Conectividade, continuidade e disponibilidade.");
	    driver.findElement(By.id("DATAINICIO")).click();
	    driver.findElement(By.id("DATAINICIO")).clear();
	    driver.findElement(By.id("DATAINICIO")).sendKeys("01/04/2013");
	    driver.findElement(By.id("DATAFIM")).click();
	    driver.findElement(By.id("DATAFIM")).clear();
	    driver.findElement(By.id("DATAFIM")).sendKeys("17/04/2013");
	    driver.findElement(By.id("AVALIAREM")).click();
	    driver.findElement(By.id("AVALIAREM")).clear();
	    driver.findElement(By.id("AVALIAREM")).sendKeys("30/04/2013");
	    driver.findElement(By.id("SITUACAO")).click();
	    driver.findElement(By.cssSelector("img[title=\"Adicionar nova linha\"]")).click();
	    driver.findElement(By.cssSelector("img[title=\"Adicionar nova linha\"]")).click();
	    driver.findElement(By.cssSelector("img[title=\"Adicionar nova linha\"]")).click();
	    driver.findElement(By.cssSelector("img[title=\"Adicionar nova linha\"]")).click();
	    driver.findElement(By.xpath("(//textarea[@name='DESCRICAORESULTADOS'])[5]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='DESCRICAORESULTADOS'])[5]")).sendKeys("Acionar o plano de comunicação");
	    driver.findElement(By.xpath("(//textarea[@name='DESCRICAORESULTADOS'])[4]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='DESCRICAORESULTADOS'])[4]")).sendKeys("Manter a documentação técnica");
	    driver.findElement(By.xpath("(//textarea[@name='DESCRICAORESULTADOS'])[3]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='DESCRICAORESULTADOS'])[3]")).sendKeys("Entregar o produto dentro do prazo e horário definido na Ordem de Serviço/Requisição");
	    driver.findElement(By.xpath("(//textarea[@name='DESCRICAORESULTADOS'])[2]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='DESCRICAORESULTADOS'])[2]")).sendKeys("Executar as atividades em conformidade com as rotinas padronizadas por documentação, devidamente autorizadas pela área responsável");
	    driver.findElement(By.id("DESCRICAORESULTADOS")).clear();
	    driver.findElement(By.id("DESCRICAORESULTADOS")).sendKeys("Manutenção de disponibilidade dos equipamentos");
	    driver.findElement(By.xpath("(//textarea[@name='LIMITES'])[2]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='LIMITES'])[2]")).sendKeys("100%");
	    driver.findElement(By.xpath("(//textarea[@name='LIMITES'])[4]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='LIMITES'])[4]")).sendKeys("100%");
	    driver.findElement(By.xpath("(//textarea[@name='LIMITES'])[5]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='LIMITES'])[5]")).sendKeys("100%");
	    driver.findElement(By.xpath("(//textarea[@name='LIMITES'])[3]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='LIMITES'])[3]")).sendKeys("1 hora de atraso");
	    driver.findElement(By.id("LIMITES")).clear();
	    driver.findElement(By.id("LIMITES")).sendKeys("99,7%");
	    driver.findElement(By.xpath("(//textarea[@name='GLOSA'])[5]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='GLOSA'])[5]")).sendKeys("2% por cada falha no procedimento deste item limitado a 8");
	    driver.findElement(By.xpath("(//textarea[@name='GLOSA'])[4]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='GLOSA'])[4]")).sendKeys("2% por cada documento incompleto limitados a 10");
	    driver.findElement(By.xpath("(//textarea[@name='GLOSA'])[3]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='GLOSA'])[3]")).sendKeys("2% até o limite de 10 horas");
	    driver.findElement(By.xpath("(//textarea[@name='GLOSA'])[2]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='GLOSA'])[2]")).sendKeys("5% até o limite de 4 por rotina não documentada ou não aprovada");
	    driver.findElement(By.id("GLOSA")).clear();
	    driver.findElement(By.id("GLOSA")).sendKeys("2% para cada décimo inferior ao limite até 98,7%");
	    driver.findElement(By.xpath("(//textarea[@name='LIMITEGLOSA'])[5]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='LIMITEGLOSA'])[5]")).sendKeys("30% se ultrapassar o limite de 8 não conformidades");
	    driver.findElement(By.xpath("(//textarea[@name='LIMITEGLOSA'])[4]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='LIMITEGLOSA'])[4]")).sendKeys("30% se ultrapassar o limite");
	    driver.findElement(By.xpath("(//textarea[@name='LIMITEGLOSA'])[3]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='LIMITEGLOSA'])[3]")).sendKeys("30% para tempo superior a 10 hs");
	    driver.findElement(By.xpath("(//textarea[@name='LIMITEGLOSA'])[2]")).clear();
	    driver.findElement(By.xpath("(//textarea[@name='LIMITEGLOSA'])[2]")).sendKeys("30% se mais de 4");
	    driver.findElement(By.id("LIMITEGLOSA")).clear();
	    driver.findElement(By.id("LIMITEGLOSA")).sendKeys("30% se ultrapassar o limite");
	    driver.findElement(By.xpath("(//input[@id='PERMITEMUDARIMPURG'])[2]")).click();
	    js.executeScript("salvar();");
	    driver.switchTo().alert().getText().endsWith("Registro gravado com sucesso!");
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