package br.com.centralit.testeselenium.helper;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UtilWebSelenium
{

	public static boolean acceptNextAlert = true;
	
	public static boolean isElementPresent(WebDriver driver, By by)
	{
		try
		{
			driver.findElement(by);
			return true;
		}
		catch (NoSuchElementException e)
		{
			return false;
		}
	}

	public static String closeAlertAndGetItsText(WebDriver driver)
	{
		try
		{
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert)
			{
				alert.accept();
			}
			else
			{
				alert.dismiss();
			}
			return alertText;
		}
		finally
		{
			acceptNextAlert = true;
		}
	}
	
	public static void waitForPageToLoad(WebDriver driver) {          
		
	      while( !((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete") ){
	         try{
	        	 Thread.sleep(500) ;
	         } 
	         catch (InterruptedException e) { 
	              e.printStackTrace();
	         }
	      }        
	      	try {
	             Thread.sleep(2000); 
	      	} 
	      	catch (InterruptedException e) { 
	           e.printStackTrace();
	      }        
	}
	
	
	/**
	 * @author luiz.prado<br>
	 * Verifica se a pagina foi carregada
	 */
	public static boolean isLoadComplete(WebDriver driver, String page)
	{
		try
		{
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (alertText.equals("A server error has occurred."))
			{
				alert.accept();
				System.out.println("Erro normal e ignorado carregando a página: " + page + ":\n - A server error has occurred.");
			}
			System.out.println("ALERTA APRENSENTADO: "+alertText);

		}
		catch (NoAlertPresentException e)
		{
		}
		return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
	}
	
	/**
	 * Verifica se existe um alert aberto
	 */
	public static boolean isAlertPresent (WebDriver driver)
	{
		try
		{
			driver.switchTo().alert();
			return true;
		}
		catch (NoAlertPresentException e)
		{
			return false;
		}
	}

	
	/**
	 * 
	 * Espera um alert ser aberto
	 * 
	 *  Tempo verificado de segundo a segundo
	 * 
	 * @param driver
	 * @param tempoDeEspera - Qtd de SEGUNDOS máximo para espera Ex.: 15 (15 segundos)
	 * 
	 * @throws Exception - Nenhuma exceção lançada
	 * 
	 */
	public static void waitForAlert(WebDriver driver, Integer tempoDeEspera)  throws Exception
	{
		
		Integer n = 0;
		
		while (!isAlertPresent(driver)) {

			n ++;
					 
			Thread.sleep(1000);
			
			if(isAlertPresent(driver))
				break;
			
			if(n > tempoDeEspera)
				throw new Exception("Uma alerta era esperado, mas demora abrir ou falta a mensagem e a chamada pelo alert.");
			
		}
	}

	
	/**
	 * @param i - tempo em milisegundos que a rotina irá "dormir"
	 */
	public static void aguardar(int i)
	{
		
		try
		{
			Thread.sleep(i);
		}
		catch (InterruptedException e)
		{
			System.out.println("Erro ao aguardar tempo");
			e.printStackTrace();
		}
		
	}
	
	
	public static void acessarPagina(WebDriver driver, String pagina){
		
		driver.get(pagina);
		UtilWebSelenium.waitForPageToLoad(driver);
		
	}
	
	public static WebElement procuraElemento(List<WebElement> elementos, String id) {
		for(WebElement e: elementos) {
			if(e.getAttribute("id").equals(id)) {
				return e;
			}
		}
		return null;
	}
	
	public static List<WebElement> criaListaDeInputs(List<WebElement> elementos) {
		List<WebElement> inputs = new LinkedList<WebElement>();
		for(WebElement e: elementos) {
			if(e.getAttribute("maxlength") != null) { //só adiciona inputs que possuem tamanho máximo definido
				inputs.add(e);
			}
		}
		return inputs;
	}
	
	
	/**
	 * Recupera tamanho do campo
	 * 
	 * @param e
	 * @return Tamanho da propriedade maxlength se definido
	 * 		   null se não definido
	 */
	public static Integer recuperaTamanhoDoCampo(WebElement e) {
		
		if(e.getAttribute("maxlength") == null || e.getAttribute("maxlength").length() <= 0)
			return null;
		else
			return Integer.parseInt(e.getAttribute("maxlength"));
		
	}
	
	public static List<WebElement> criaListaDeTextAreas(List<WebElement> elementos) {
		List<WebElement> textareas = new LinkedList<WebElement>();
		textareas.addAll(elementos);
		return textareas;
	}
	
	
	
	/**
	 * Gera uma string aleatoria do campo passado como parametro de acordo com o tamanho do campo
	 * 
	 * @param driver
	 * @param nomeDoCampo
	 * 
	 * @return String aleatoria de acordo com o tamanho do campo ou
	 * 		   String aleatoria de tamanho = 1 caso o campo não tenha tamanho definido 
	 * 				
	 */
	public static String geraStringAleatoriaDoCampo(WebDriver driver, String nomeDoCampo) {
		
		Integer tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.name(nomeDoCampo)));
		
		if(tamCampo == null || tamCampo.toString().length() <= 0)
			return UtilTeste.geraString(1);
		else if(tamCampo > 1000)
			return UtilTeste.geraString(1000);

		return UtilTeste.geraString(tamCampo);

	}
	
	/**
	 * Gera uma string aleatoria do campo passado como parametro de acordo com o tamanho do campo, default = ID
	 * 
	 * @param driver
	 * @param opcaoDeBusca:
	 * 
	 * 					1-ID
	 * 					2-Name
	 * 					3-css
	 * 					4-xpath
	 * 
	 * @return String aleatoria de acordo com o tamanho do campo ou
	 * 		   String aleatoria de tamanho = 1 caso o campo não tenha tamanho definido 
	 * 				
	 */
	public static String geraStringAleatoriaDoCampo(WebDriver driver, Integer opcaoDeBusca, String valorDoCampo) {
		
		Integer tamCampo = 1;
		
		switch (opcaoDeBusca)
		{
			case 1:
				tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.id(valorDoCampo)));
				break;
			case 2:
				tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.name(valorDoCampo)));
				break;
			case 3:
				tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.cssSelector(valorDoCampo)));
				break;
			case 4:
				tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.xpath(valorDoCampo)));
				break;				

			default:
				break;
		}
		
		if(tamCampo == null || tamCampo.toString().length() <= 0)
			return UtilTeste.geraString(1);
		else if(tamCampo > 1000)
			return UtilTeste.geraString(1000);

		return UtilTeste.geraString(tamCampo);

	}
	
	
	/**
	 * Gera um numero aleatoria do campo passado como parametro de acordo com o tamanho do campo
	 * 
	 * @param driver
	 * @param nomeDoCampo
	 * 
	 * @return Numero aleatoria de acordo com o tamanho do campo ou
	 * 		   Numero aleatoria de tamanho = 1 caso o campo não tenha tamanho definido 
	 * 				
	 */
	public static String geraNumeroAleatoriaDoCampo(WebDriver driver, String nomeDoCampo) {
		
		Integer tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.name(nomeDoCampo)));
		
		if(tamCampo == null || tamCampo.toString().length() <= 0)
			return UtilTeste.geraNumero(1);
		else 
			return UtilTeste.geraNumero(tamCampo);

	}	
	
	/**
	 * Gera um numero aleatoria do campo passado como parametro de acordo com o tamanho do campo
	 * 
	 * @param driver
	 * @param nomeDoCampo
	 * 
	 * @return Numero aleatoria de acordo com o tamanho do campo ou
	 * 		   Numero aleatoria de tamanho = 1 caso o campo não tenha tamanho definido 
	 * 				
	 */
	public static String geraNumeroAleatoriaDoCampo(WebDriver driver, Integer opcaoDeBusca, String valorDoCampo) {
		
		Integer tamCampo = 1;
		
		switch (opcaoDeBusca)
		{
			case 1:
				tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.id(valorDoCampo)));
				break;
			case 2:
				tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.name(valorDoCampo)));
				break;
			case 3:
				tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.cssSelector(valorDoCampo)));
				break;
			case 4:
				tamCampo = UtilWebSelenium.recuperaTamanhoDoCampo(driver.findElement(By.xpath(valorDoCampo)));
				break;				

			default:
				break;
		}
		
		if(tamCampo == null || tamCampo.toString().length() <= 0)
			return UtilTeste.geraNumero(1);
		else if(tamCampo > 1000)
			return UtilTeste.geraNumero(1000);

		return UtilTeste.geraNumero(tamCampo);

	}
}