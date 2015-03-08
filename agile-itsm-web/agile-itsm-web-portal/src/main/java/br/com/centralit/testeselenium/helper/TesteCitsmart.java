package br.com.centralit.testeselenium.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class TesteCitsmart extends CitTeste {

    private static String page;

    public static void login(WebDriver driver, String baseUrl, String usuario, String senha) {
        setPage("/pages/login/login.load");

        driver.get(baseUrl + getPage());
        driver.findElement(By.id("user")).clear();
        driver.findElement(By.id("user")).sendKeys(usuario);
        driver.findElement(By.id("senha")).clear();
        driver.findElement(By.id("senha")).sendKeys(senha);

        driver.findElement(By.xpath("//button[@id='']")).click();

        UtilWebSelenium.waitForPageToLoad(driver);
    }

    public static void logout(WebDriver driver) {
        driver.findElement(By.xpath("//form[@id='formInternacionaliza']/ul/li[3]/a/span")).click();

        driver.findElement(By.linkText("Sair")).click();
    }

    public static Boolean pesquisarRegistro(String nomeDoBotaoDeLimpar, String nomeDoLinkDaAbaDePesquisa, String nomeDoCampoDePesquisa, String nomeDoBotaoDePesquisa,
            String xPathDoPrimeiroItemDaLista, String valorObjeto) {
        try {

            driver.findElement(By.linkText(nomeDoLinkDaAbaDePesquisa)).click();

            UtilWebSelenium.aguardar(1000);

            if (nomeDoBotaoDeLimpar != null && !nomeDoBotaoDeLimpar.trim().equals(""))
                driver.findElement(By.name(nomeDoBotaoDeLimpar)).click();

            driver.findElement(By.id(nomeDoCampoDePesquisa)).clear();
            driver.findElement(By.id(nomeDoCampoDePesquisa)).sendKeys(valorObjeto);
            driver.findElement(By.id(nomeDoBotaoDePesquisa)).click();

            UtilWebSelenium.aguardar(3000);

            driver.findElement(By.xpath(xPathDoPrimeiroItemDaLista)).click();

        } catch (Exception e) {
            return false;
        }

        UtilWebSelenium.waitForPageToLoad(driver);

        return true;
    }

    public static String getPage() {
        return page;
    }

    public static void setPage(String pagina) {
        page = pagina;
    }

}
