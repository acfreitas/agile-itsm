package br.com.centralit.testeselenium.gerenciadecatalogosdeservico;

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
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class TipoServicoSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/tipoServico/tipoServico.load";
	private String nomeCadastrado;
	
	@Before
	public void setUp() throws Exception
	{

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		login(driver, baseUrl, usuario, senha);

		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		maximizarTela();
	}

	@Ignore
	public String cadastrarRegistro() throws Exception{
		
		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nomeTipoServico");

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		UtilWebSelenium.aguardar(2000);
		
		driver.findElement(By.id("nomeTipoServico")).sendKeys(nomeCadastrado);
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
				
		return nomeCadastrado;
		
	}

	
	@Test
	public void cit_472_CadastrarTipoServico() throws Exception
	{
		
		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));

	}
	

	@Test
	public void cit_699_EditarTipoServico() throws Exception
	{		
		nomeCadastrado = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);
		
		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";
		
		driver.findElement(By.id("nomeTipoServico")).clear();
		driver.findElement(By.id("nomeTipoServico")).sendKeys(nomeCadastrado);
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro alterado com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);
		
		assertEquals(nomeCadastrado, driver.findElement(By.id("nomeTipoServico")).getAttribute("value"));
		
	}
	
	@Test
	public void cit_700_ExcluirTipoServico() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);
		
		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir? [\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
			
	
	@Test
	public void cit_193_NomeCampoObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome do Tipo Serviço: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@After
	public void tearDown() throws Exception
	{
		driver.quit();
		driver = null;
	}


	@Override
	public String testarQuantidadeCaracteres() throws Exception
	{
		return null;
	}

	@Override
	public String getNomeDoBotaoDePesquisa()
	{
		return "btnPesquisar";
	}

	@Override
	public String getNomeDoBotaoDeSalvar()
	{
		return "btnGravar";
	}

	@Override
	public String getNomeDoBotaoDeExcluir()
	{
		return "btnExcluir";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoCampoDePesquisa()
	{
		return  "pesqLockupLOOKUP_TIPOSERVICO_NOMETIPOSERVICO";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa de Tipo Serviço";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		// TODO Auto-generated method stub
		return "btnLimparLOOKUP_TIPOSERVICO";
	}
	
}