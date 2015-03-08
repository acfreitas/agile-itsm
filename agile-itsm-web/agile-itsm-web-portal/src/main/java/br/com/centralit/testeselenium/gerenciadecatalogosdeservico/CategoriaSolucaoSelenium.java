package br.com.centralit.testeselenium.gerenciadecatalogosdeservico;

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

public class CategoriaSolucaoSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/dinamicViews/dinamicViews.load?identificacao=categoriaSolucao";
	private String nomeCadastrado = "Teste";
	
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
		
		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "DESCRICAOCATEGORIASOLUCAO");

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("DESCRICAOCATEGORIASOLUCAO")).sendKeys(nomeCadastrado);
		driver.findElement(By.id("DATAINICIO")).sendKeys(UtilTeste.tempoAgoraMaisUmtempo(1));
		new Select(driver.findElement(By.id("IDCATEGORIASOLUCAOPAI"))).selectByVisibleText("Backup e Restore");
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nomeCadastrado;
		
	}

	
	@Test
	public void cit_466_CadastrarCategoriaSolucao() throws Exception
	{
		
		Assert.assertTrue( pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));
		
	}
	

	@Test
	public void cit_664_EditarCategoriaSolucao() throws Exception
	{
		
		nomeCadastrado = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";
		
		driver.findElement(By.id("DESCRICAOCATEGORIASOLUCAO")).clear();
		driver.findElement(By.id("DESCRICAOCATEGORIASOLUCAO")).sendKeys(nomeCadastrado);
		driver.findElement(By.id("DATAINICIO")).clear();
		driver.findElement(By.id("DATAINICIO")).sendKeys("01/01/2014");
		new Select(driver.findElement(By.id("IDCATEGORIASOLUCAOPAI"))).selectByVisibleText("FS-Criação");
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		assertEquals(nomeCadastrado, driver.findElement(By.id("DESCRICAOCATEGORIASOLUCAO")).getAttribute("value"));
		assertEquals("01/01/2014", driver.findElement(By.id("DATAINICIO")).getAttribute("value"));
		assertEquals("FS-Criação", (new Select(driver.findElement(By.id("IDCATEGORIASOLUCAOPAI")))).getFirstSelectedOption().getText());
		
	}
	
	@Test
	public void cit_665_ExcluirCategoriaSolucao() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);


		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Confirma a exclusão do registro [\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
			
	
	@Test
	public void cit_186_CategoriaSolucaoCampoObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
	

		driver.findElement(By.id("DATAINICIO")).sendKeys("01/01/2014");
		new Select(driver.findElement(By.id("IDCATEGORIASOLUCAOPAI"))).selectByVisibleText("FS-Criação");
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Categoria Solução: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_661_DataInicioCampoObrigatorio() throws Exception
	{
	
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("DESCRICAOCATEGORIASOLUCAO")).sendKeys(nomeCadastrado);
		new Select(driver.findElement(By.id("IDCATEGORIASOLUCAOPAI"))).selectByVisibleText("FS-Criação");
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Data Início:: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
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
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoCampoDePesquisa()
	{
		return  "termo_pesq_TABLESEARCH_41";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//tr[@id='datagrid-row-r5-2-0']/td[2]/div";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa Categoria Solução";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}