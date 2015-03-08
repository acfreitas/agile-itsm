package br.com.centralit.testeselenium.gestaocontratos;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilTeste;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class FormulaOSSelenium extends TesteCitsmart implements MetodosGenericos {

    private final String page = "/pages/formulaOs/formulaOs.load";
    private String descricao;

    @Before
    public void setUp() throws Exception {

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        login(driver, baseUrl, usuario, senha);

        UtilWebSelenium.acessarPagina(driver, baseUrl + page);

    }

    @Ignore
    public String cadastrarRegistro() throws Exception {

        descricao = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "descricao");

        driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

        driver.findElement(By.id("descricao")).sendKeys(descricao);
        driver.findElement(By.id("formula")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "formula"));
        new Select(driver.findElement(By.id("situacao"))).selectByVisibleText("Ativo");
        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 10000);

        assertEquals(REGISTRO_INSERIDO_SUCESSO, UtilWebSelenium.closeAlertAndGetItsText(driver));

        return descricao;

    }

    @Ignore
    public Boolean pesquisarRegistro(String nomeDoCampoDePesquisa, String valorObjeto) {
        try {

            driver.findElement(By.linkText(getNomeDaAbaDePesquisa())).click();
            driver.findElement(By.id(nomeDoCampoDePesquisa)).clear();
            driver.findElement(By.id(nomeDoCampoDePesquisa)).sendKeys(valorObjeto);
            driver.findElement(By.id(getNomeDoBotaoDePesquisa())).click();
            driver.findElement(By.xpath(getXpathDoPrimeiroItemDaListaDePesquisa())).click();

        } catch (Exception e) {
            return false;
        }

        UtilWebSelenium.waitForPageToLoad(driver);

        return true;
    }

    @Test
    public void cit_654_CadastrarFormulaOS() throws Exception {
        Assert.assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), cadastrarRegistro()));
    }

    @Test
    public void cit_658_DescricaoObrigatorio() throws Exception {
        driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 5000);

        assertEquals(PREENCHA_TODOS_CAMPOS_OBRIGATORIOS, UtilWebSelenium.closeAlertAndGetItsText(driver));
    }

    @Test
    public void cit_659_FormulaOSObrigatorio() throws Exception {
        driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

        driver.findElement(By.id("descricao")).sendKeys("Teste de inclusao");

        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        assertEquals(PREENCHA_TODOS_CAMPOS_OBRIGATORIOS, UtilWebSelenium.closeAlertAndGetItsText(driver));
    }

    @Test
    public void cit_660_SituacaoObrigatorio() throws Exception {
        driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

        driver.findElement(By.id("descricao")).sendKeys("Teste");
        driver.findElement(By.id("formula")).sendKeys(UtilTeste.geraTextArea());

        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        assertEquals(PREENCHA_TODOS_CAMPOS_OBRIGATORIOS, UtilWebSelenium.closeAlertAndGetItsText(driver));
    }

    @Test
    public void cit_655_AlterarFormulaOS() throws Exception {
        descricao = cadastrarRegistro();

        Assert.assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), descricao));

        descricao = descricao.substring(0, 10) + "editado";

        driver.findElement(By.id("descricao")).clear();
        driver.findElement(By.id("descricao")).sendKeys(descricao);

        String formula = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "formula");
        driver.findElement(By.id("formula")).clear();
        driver.findElement(By.id("formula")).sendKeys(formula);

        new Select(driver.findElement(By.id("situacao"))).selectByVisibleText("Ativo");

        driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 5000);

        assertEquals(REGISTRO_ALTERADO_SUCESSO, UtilWebSelenium.closeAlertAndGetItsText(driver));

        Assert.assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), descricao));

        assertEquals(descricao, driver.findElement(By.id("descricao")).getAttribute("value"));
        assertEquals(formula, driver.findElement(By.id("formula")).getAttribute("value"));
        assertEquals("Ativo", (new Select(driver.findElement(By.id("situacao")))).getFirstSelectedOption().getText());
    }

    @Test
    public void cit_656_ExcluirFormulaOS() throws Exception {
        descricao = cadastrarRegistro();

        Assert.assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), descricao));

        driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

        UtilWebSelenium.waitForAlert(driver, 3000);

        UtilWebSelenium.closeAlertAndGetItsText(driver).matches("Deseja excluir? [\\s\\S]$");

        UtilWebSelenium.waitForAlert(driver, 3000);

        assertEquals(REGISTRO_EXCLUIDO_SUCESSO, UtilWebSelenium.closeAlertAndGetItsText(driver));

        Assert.assertFalse(pesquisarRegistro(getNomeDoCampoDePesquisa(), descricao));
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
        return "pesqLockupLOOKUP_FORMULAOS_descricao";
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
        return null;
    }

}
