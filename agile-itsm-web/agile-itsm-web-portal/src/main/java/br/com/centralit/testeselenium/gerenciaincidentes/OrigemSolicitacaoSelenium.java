package br.com.centralit.testeselenium.gerenciaincidentes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class OrigemSolicitacaoSelenium extends TesteCitsmart implements MetodosGenericos {

    private final String page = "/pages/origemAtendimento/origemAtendimento.load";
    private String descricao;

    @Before
    public void setUp() throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        login(driver, baseUrl, usuario, senha);

        UtilWebSelenium.acessarPagina(driver, baseUrl + page);

        driver.manage().window().maximize();
    }

    @Ignore
    public String cadastrarRegistro() throws Exception {
        descricao = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "descricao");

        driver.findElement(By.xpath(getNomeDoBotaoDeLimpar())).click();

        driver.findElement(By.id("descricao")).sendKeys(descricao);

        driver.findElement(By.xpath(getNomeDoBotaoDeSalvar())).click();

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
    public void cit_432_CadastrarOrigemAtendimento() throws Exception {
        assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), cadastrarRegistro()));
    }

    @Test
    public void cit_673_NomeObrigatorio() throws Exception {
        driver.findElement(By.xpath(getNomeDoBotaoDeLimpar())).click();

        driver.findElement(By.xpath(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 5000);

        assertEquals("Nome: Campo obrigatório ", UtilWebSelenium.closeAlertAndGetItsText(driver));
    }

    @Test
    public void cit_670_AlterarOrigemAtendimento() throws Exception {
        descricao = cadastrarRegistro();

        assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), descricao));

        descricao = descricao.substring(0, 10) + "editado";
        driver.findElement(By.id("descricao")).clear();
        driver.findElement(By.id("descricao")).sendKeys(descricao);

        driver.findElement(By.xpath(getNomeDoBotaoDeSalvar())).click();

        UtilWebSelenium.waitForAlert(driver, 5000);

        assertEquals(REGISTRO_ALTERADO_SUCESSO, UtilWebSelenium.closeAlertAndGetItsText(driver));

        assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), descricao));

        assertEquals(descricao, driver.findElement(By.id("descricao")).getAttribute("value"));
    }

    @Test
    public void cit_672_ExcluirOrigemAtendimento() throws Exception {
        descricao = cadastrarRegistro();

        assertTrue(pesquisarRegistro(getNomeDoCampoDePesquisa(), descricao));

        driver.findElement(By.xpath(getNomeDoBotaoDeExcluir())).click();

        UtilWebSelenium.waitForAlert(driver, 3000);

        UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir??[\\s\\S]$");

        UtilWebSelenium.waitForAlert(driver, 3000);

        assertEquals(REGISTRO_EXCLUIDO_SUCESSO, UtilWebSelenium.closeAlertAndGetItsText(driver));
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
        return "//div[2]/button";
    }

    @Override
    public String getNomeDoBotaoDeExcluir() {
        return "(//button[@id=''])[3]";
    }

    @Override
    public String getNomeDoCampoDePesquisa() {
        return "pesqLockupLOOKUP_ORIGEMATENDIMENTO_descricao";
    }

    @Override
    public String getXpathDoPrimeiroItemDaListaDePesquisa() {
        return "//table[@id='topoRetorno']/tbody/tr[2]/td/input";
    }

    @Override
    public String getNomeDaAbaDePesquisa() {
        return "Pesquisar Origem";
    }

    @Override
    public String getNomeDoBotaoDeLimpar() {
        return "(//button[@id=''])[2]";
    }

    @Override
    public String getNomeDoBotaoDeLimparPesquisa() {
        return "btnLimpar";
    }

}
