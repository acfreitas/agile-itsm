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

public class CategoriaServicoSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/categoriaServico/categoriaServico.load";
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
	public String cadastrarRegistro(boolean todosOsCampos) throws Exception{

		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nomeCategoriaServico");

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("nomeCategoriaServico")).sendKeys(nomeCadastrado);
		
		if(todosOsCampos){
			
			driver.findElement(By.id("nomeCategoriaServicoPai")).click();
			
			String myWindowHandle = driver.getWindowHandle();

			driver.switchTo().window(myWindowHandle);
			
			driver.findElement(By.name("btnLOOKUP_CATEGORIASERVICO_SUPERIOR")).click();
			driver.findElement(By.xpath("//table[@id='topoRetorno']/tbody/tr[2]/td/input")).click();
			
			driver.switchTo().defaultContent();
		} 
				
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nomeCadastrado;
		
	}
	
	@Test
	public void cit_473_CadastrarCategoriaDeServico() throws Exception
	{
		
		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro(false)));
		
	}
	

	@Test
	public void cit_194_CadastrarCategoriaServicoComHierarquia() throws Exception
	{

		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro(true)));
		
	}
	
	@Test
	public void cit_706_ExcluirCategoriaServico() throws Exception
	{

		nomeCadastrado = cadastrarRegistro(true);
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nomeCadastrado);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir? [\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
			
	
	@Test
	public void cit_705_NomeCampoObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_704_AlterarCategoriaServico() throws Exception
	{

		nomeCadastrado = cadastrarRegistro(true);

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nomeCadastrado);
		
		String servicoPai = driver.findElement(By.id("nomeCategoriaServicoPai")).getAttribute("value").trim();
		
		//Consultar hierarquia
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				servicoPai);
		
		String hierarquia = driver.findElement(By.id("nomeCategoriaServicoConcatenado")).getAttribute("value").trim();

		//Consulta do registo normal 
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nomeCadastrado);
		
		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";
		
		driver.findElement(By.id("nomeCategoriaServico")).clear();
		driver.findElement(By.id("nomeCategoriaServico")).sendKeys(nomeCadastrado);
		
		/*
		 
		driver.findElement(By.id("nomeCategoriaServicoPai")).click();
		String myWindowHandle = driver.getWindowHandle();
		
		driver.switchTo().window(myWindowHandle);
		
		driver.findElement(By.name("btnLOOKUP_CATEGORIASERVICO_SUPERIOR")).click();
		driver.findElement(By.xpath("//table[@id='topoRetorno']/tbody/tr[2]/td/input")).click();
		
		driver.switchTo().defaultContent();
		*/
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro alterado com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nomeCadastrado);
		
		assertEquals(nomeCadastrado, driver.findElement(By.id("nomeCategoriaServico")).getAttribute("value"));
		assertEquals(hierarquia + "  - " + nomeCadastrado, driver.findElement(By.id("nomeCategoriaServicoConcatenado")).getAttribute("value"));

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
	public String getNomeDoCampoDePesquisa()
	{
		return  "pesqLockupLOOKUP_CATEGORIASERVICO_NOMECATEGORIASERVICO";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//table[@id='topoRetorno']/tbody/tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa Categoria Serviço";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return "btnLimparLOOKUP_CATEGORIASERVICO";
	}
	
}