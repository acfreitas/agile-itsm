package br.com.centralit.testeselenium.gerenciadeconfiguracoes;

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

public class CaracteristicaSelenium extends TesteCitsmart implements MetodosGenericos {

	private final String page = "/pages/caracteristica/caracteristica.load";
	private String nomeCadastrado;

	@Before
	public void setUp() throws Exception {

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		login(driver, baseUrl, usuario, senha);

		UtilWebSelenium.acessarPagina(driver, baseUrl + page);

		maximizarTela();
	}

	@Ignore
	public String cadastrarRegistro() throws Exception {

		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver,
				"nome");

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

		driver.findElement(By.id("nome")).sendKeys(nomeCadastrado);
		driver.findElement(By.id("tag")).sendKeys(
				UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "tag"));
		driver.findElement(By.id("descricao"))
				.sendKeys(
						UtilWebSelenium.geraStringAleatoriaDoCampo(driver,
								"descricao"));

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		assertEquals("Registro inserido com sucesso",
				UtilWebSelenium.closeAlertAndGetItsText(driver));

		return nomeCadastrado;

	}

	@Test
	public void cit_468_CadastrarTipoEventoServico() throws Exception {

		Assert.assertTrue(pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(),
				getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(),
				getNomeDoBotaoDePesquisa(),
				getXpathDoPrimeiroItemDaListaDePesquisa(), cadastrarRegistro()));

	}

	@Test
	public void cit_674_EditarTipoEventoServico() throws Exception {

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(),
				getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(),
				getNomeDoBotaoDePesquisa(),
				getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";

		driver.findElement(By.id("nome")).clear();
		driver.findElement(By.id("nome")).sendKeys(nomeCadastrado);

		driver.findElement(By.id("descricao")).clear();
		driver.findElement(By.id("descricao")).sendKeys("editado");

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		assertEquals("Registro alterado com sucesso",
				UtilWebSelenium.closeAlertAndGetItsText(driver));

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(),
				getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(),
				getNomeDoBotaoDePesquisa(),
				getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		assertEquals(nomeCadastrado, driver.findElement(By.id("nome"))
				.getAttribute("value"));
		assertEquals("editado", driver.findElement(By.id("descricao"))
				.getAttribute("value"));

	}

	@Test
	public void cit_675_ExcluirTipoEventoServico() throws Exception {

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(),
				getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(),
				getNomeDoBotaoDePesquisa(),
				getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.closeAlertAndGetItsText(driver).matches(
				"^Deseja realmente excluir? [\\s\\S]$");

		assertEquals("Registro excluído com sucesso",
				UtilWebSelenium.closeAlertAndGetItsText(driver));

	}

	@Test
	public void cit_189_NomeCampoObrigatorio() throws Exception {

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

		UtilWebSelenium.aguardar(3000);

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		assertEquals("Característica: Campo obrigatório",
				UtilWebSelenium.closeAlertAndGetItsText(driver));

	}

	@Test
	public void cit_189_DescricaoCampoObrigatorio() throws Exception {

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

		driver.findElement(By.id("nome")).clear();
		driver.findElement(By.id("nome")).sendKeys(
				UtilTeste.retornaStringValida(nomeCadastrado));

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		assertEquals("Tag: Campo obrigatório",
				UtilWebSelenium.closeAlertAndGetItsText(driver));

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
		return "pesqLockupLOOKUP_CARACTERISTICA_nomecaracteristica";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa() {
		return "//tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa() {
		return "Pesquisa de Características";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa() {
		return "btnLimparLOOKUP_CARACTERISTICA";
	}

}