package br.com.centralit.testeselenium.gestaocontratos;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilTeste;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class FormulaSelenium extends TesteCitsmart implements MetodosGenericos {

    private final String page = "/pages/formula/formula.load";
    private String nome;

    @Before
    public void setUp() throws Exception {

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        login(driver, baseUrl, usuario, senha);

        UtilWebSelenium.acessarPagina(driver, baseUrl + page);

        UtilWebSelenium.aguardar(30000);

    }

    @Ignore
    public String cadastrarRegistro() throws Exception {

        UtilWebSelenium.aguardar(3000);

        nome = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nome");

        driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

        UtilWebSelenium.aguardar(2000);

        driver.findElement(By.id("nome")).sendKeys(nome);
        driver.findElement(By.id("identificador")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "identificador"));
        driver.findElement(By.id("conteudo")).sendKeys(UtilTeste.geraTextArea());
        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 10000);

        assertEquals(REGISTRO_INSERIDO_SUCESSO, UtilWebSelenium.closeAlertAndGetItsText(driver));

        UtilWebSelenium.aguardar(2000);

        return nome;

    }

    @Ignore
    public Boolean pesquisarRegistro(String nomeDoCampoDePesquisa, String valorObjeto) {
        try {

            driver.findElement(By.linkText(getNomeDaAbaDePesquisa())).click();
            driver.findElement(By.id(nomeDoCampoDePesquisa)).clear();
            driver.findElement(By.id(nomeDoCampoDePesquisa)).sendKeys(valorObjeto);
            driver.findElement(By.id(getNomeDoBotaoDePesquisa())).click();
            driver.findElement(By.xpath(getXpathDoPrimeiroItemDaListaDePesquisa())).click();

            UtilWebSelenium.aguardar(2000);

        } catch (Exception e) {
            return false;
        }

        UtilWebSelenium.waitForPageToLoad(driver);

        return true;
    }

    @Test
    public void cit_520_CadastrarFormula() throws Exception {
        Assert.assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), cadastrarRegistro()));
    }

    @Test
    public void cit_649_NomeFormulaObrigatorio() throws Exception {
        driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

        UtilWebSelenium.aguardar(3000);

        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 5000);

        assertEquals("Nome da Fórmula: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
    }

    @Test
    public void cit_650_IdentificadorFormulaObrigatorio() throws Exception {

        driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

        UtilWebSelenium.aguardar(3000);

        driver.findElement(By.id("nome")).sendKeys("Teste");

        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 5000);

        assertEquals("Identificador da Fórmula: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
    }

    @Test
    public void cit_651_ConteudoFormulaObrigatorio() throws Exception {
        driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

        UtilWebSelenium.aguardar(3000);

        driver.findElement(By.id("nome")).sendKeys("Teste");
        driver.findElement(By.id("identificador")).sendKeys("Teste do identificador");

        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 5000);

        assertEquals("Conteúdo da Fórmula: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
    }

    @Test
    public void cit_648_AlterarFormula() throws Exception {
        UtilWebSelenium.aguardar(2500);

        nome = cadastrarRegistro();

        Assert.assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), nome));

        nome = nome.substring(0, 10) + "editado";

        driver.findElement(By.id("nome")).clear();
        driver.findElement(By.id("nome")).sendKeys(nome);

        driver.findElement(By.id("identificador")).clear();
        driver.findElement(By.id("identificador")).sendKeys("Alterado");

        driver.findElement(By.id("conteudo")).clear();
        driver.findElement(By.id("conteudo")).sendKeys("Adulterado");

        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 5000);

        assertEquals(REGISTRO_ALTERADO_SUCESSO, UtilWebSelenium.closeAlertAndGetItsText(driver));

        Assert.assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), nome));

        UtilWebSelenium.aguardar(3000);

        assertEquals(nome, driver.findElement(By.id("nome")).getAttribute("value"));
        assertEquals("Alterado", driver.findElement(By.id("identificador")).getAttribute("value"));
        assertEquals("Adulterado", driver.findElement(By.id("conteudo")).getAttribute("value"));

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        driver = null;
    }

    @Override
    public String testarQuantidadeCaracteres() throws Exception {
        return null;
    }

    @Override
    public String getNomeDoBotaoDePesquisa() {
        return "btnPesquisar";
    }

    @Override
    public String getNomeDoBotaoDeSalvar() {
        return "btnGravar";
    }

    @Override
    public String getNomeDoBotaoDeExcluir() {
        return "btnUpDate";
    }

    @Override
    public String getNomeDoBotaoDeLimpar() {
        return "btnLimpar";
    }

    @Override
    public String getNomeDoCampoDePesquisa() {
        return "pesqLockupLOOKUP_FORMULA_nome";
    }

    @Override
    public String getXpathDoPrimeiroItemDaListaDePesquisa() {
        return "//table[@id='topoRetorno']/tbody/tr[2]/td/input";
    }

    @Override
    public String getNomeDaAbaDePesquisa() {
        return "Pesquisa de Fórmula";
    }

    @Override
    public String getNomeDoBotaoDeLimparPesquisa() {
        // TODO Auto-generated method stub
        return null;
    }

}
