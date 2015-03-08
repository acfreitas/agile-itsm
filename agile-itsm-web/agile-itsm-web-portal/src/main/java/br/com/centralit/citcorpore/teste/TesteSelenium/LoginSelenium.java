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

public class LoginSelenium {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuilder verificationErrors = new StringBuilder();

	public LoginSelenium(WebDriver driver, String baseUrl, boolean acceptNextAlert, StringBuilder verificationErrors) {
		super();
		this.driver = driver;
		this.baseUrl = baseUrl;
		this.acceptNextAlert = acceptNextAlert;
		this.verificationErrors = verificationErrors;
	}

	@Before
	public void setUp() throws Exception {
//		driver = new FirefoxDriver();
	    DesiredCapabilities capability = DesiredCapabilities.firefox();
	    driver = new RemoteWebDriver(new URL("http://10.2.1.3:4444/wd/hub"), capability);
		baseUrl = "http://localhost/citsmart";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testUntitled() throws Exception {
		driver.get("http://localhost/citsmart/login/login.load");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.findElement(By.id("user")).clear();
		driver.findElement(By.id("user")).sendKeys("admin");
		driver.findElement(By.id("senha")).clear();
		driver.findElement(By.id("senha")).sendKeys("adm1goi@nia");
		js.executeScript("validar();");
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
