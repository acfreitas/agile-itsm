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

public class CadastroTipoEventoServicoSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/dinamicViews/dinamicViews.load?identificacao=CadastroTipoEventoServico";
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
		
		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "NOMETIPOEVENTOSERVICO");

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
			
		driver.findElement(By.id("NOMETIPOEVENTOSERVICO")).sendKeys(nomeCadastrado);
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));
			
		return nomeCadastrado;
		
	}

	
	@Test
	public void cit_468_CadastrarTipoEventoServico() throws Exception
	{
		
		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));
		
	}
	

	@Test
	public void cit_674_EditarTipoEventoServico() throws Exception
	{
		
		nomeCadastrado = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";
		
		driver.findElement(By.id("NOMETIPOEVENTOSERVICO")).clear();
		driver.findElement(By.id("NOMETIPOEVENTOSERVICO")).sendKeys(nomeCadastrado);
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		assertEquals(nomeCadastrado, driver.findElement(By.id("NOMETIPOEVENTOSERVICO")).getAttribute("value"));
		
	}
	
	@Test
	public void cit_675_ExcluirTipoEventoServico() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Confirma a exclus�o do registro [\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro exclu�do com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
			
	
	@Test
	public void cit_189_DescricaoCampoObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Descri��o: Campo obrigat�rio", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
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
		return "btn_REFRESH_VIEW";
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
	public String getNomeDoCampoDePesquisa()
	{
		return  "termo_pesq_TABLESEARCH_31";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//tr[@id='datagrid-row-r5-2-0']/td[2]/div";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Listagem";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}