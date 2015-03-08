package br.com.centralit.testeselenium.helper;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

public abstract class CitTeste
{
	public static WebDriver driver;
	public static String baseUrl;
	public static String usuario;
	public static String senha;
	public static String navegador;
	
	public CitTeste()	{
		
		try {
			
			usuario = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SELENIUM_USUARIO_TESTE, "consultor");
			senha = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SELENIUM_SENHA_TEST, "1");
			baseUrl = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SELENIUM_URL_TESTE, "localhost:8080/citsmart");
			navegador = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SELENIUM_NAVEGADOR_TESTE, "Google Chrome");
			
			if(!baseUrl.contains("http://"))
				baseUrl = "http://" + baseUrl; 

			String ipCliente = ParametroUtil.getValor(ParametroSistema.SELENIUM_URL_CLIENTE_TESTE);
				
			// Execução automatica sem a necidade da tela
			// ipCliente == null
			
			if(ipCliente != null && ipCliente.trim().length() > 0){
				
				DesiredCapabilities capability = new DesiredCapabilities();
				
				if(navegador.equalsIgnoreCase("chrome") || navegador.equalsIgnoreCase("Google Chrome")){

					capability = DesiredCapabilities.chrome();
					
				} else if(navegador.equalsIgnoreCase("ie") || navegador.equalsIgnoreCase("internetexplorer") || navegador.equalsIgnoreCase("Internet Explorer")){
					
					capability = DesiredCapabilities.internetExplorer();
					
				} else {
					
					capability = DesiredCapabilities.firefox();
					
				}
				
				ipCliente = "http://" + ipCliente + ":4444/wd/hub";
				
				driver = new RemoteWebDriver(new URL(ipCliente), capability);
				
			} else {
				
				if(navegador.equalsIgnoreCase("chrome") || navegador.equalsIgnoreCase("Google Chrome")){

	                System.setProperty("webdriver.chrome.driver", ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SELENIUM_CAMINHO_COMPLETO_DRIVER_CHROME, "Google Chrome"));
					
					driver = new ChromeDriver();
					
				} else if(navegador.equalsIgnoreCase("ie") || navegador.equalsIgnoreCase("internetexplorer") || navegador.equalsIgnoreCase("Internet Explorer")){

		            System.setProperty("webdriver.ie.driver", ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SELENIUM_CAMINHO_COMPLETO_DRIVER_IE, "Internet Explorer"));
					
					driver = new InternetExplorerDriver();
					
				} else {
					
					driver = new FirefoxDriver();
						
				}
				
			}
			
		} catch (Exception e) {
			System.out.println("Erro nas configurações de teste:");
			System.out.println(e.toString());
		}
		
		tempoDeEsperaMaximo(30);
		
		maximizarTela();
		
	}
	
	public void maximizarTela(){
		driver.manage().window().maximize();
	}
	
	public static void minimizarTela(){
		
		driver.manage().window().setPosition(new Point(-2000, 0));
	}
	
	public static void minimizarTela(Integer x, Integer y){
		
		if(x == null)
			x = -2000;
		
		if(y == null)
			y = 0;
		
		driver.manage().window().setPosition(new Point(x, y));
	}
	
	public void tempoDeEsperaMaximo(int tempoEmSegundos){
		
		driver.manage().timeouts().implicitlyWait(tempoEmSegundos, TimeUnit.SECONDS);
		
	}

}