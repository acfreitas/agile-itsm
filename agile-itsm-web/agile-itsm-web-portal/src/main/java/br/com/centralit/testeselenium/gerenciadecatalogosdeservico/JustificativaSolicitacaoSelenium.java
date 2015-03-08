package br.com.centralit.testeselenium.gerenciadecatalogosdeservico;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class JustificativaSolicitacaoSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/dinamicViews/dinamicViews.load?identificacao=justificativaSolicitacao";
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

		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "DESCRICAOJUSTIFICATIVA");

		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("DESCRICAOJUSTIFICATIVA")).sendKeys(nomeCadastrado);
		
		driver.findElement(By.xpath("//tr[2]/td[2]/input")).click();
		driver.findElement(By.xpath("//tr[3]/td[2]/input")).click();
		driver.findElement(By.xpath("//tr[4]/td[2]/input")).click();
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();

		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nomeCadastrado;
		
	}
	
	@Test
	public void cit_471_CadastrarJustificativaSolicitacao() throws Exception
	{
		
		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));
		
	}
	

	@Test
	public void cit_687_EditarJustificativaSolicitacao() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						nomeCadastrado);
		
		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";
		
		driver.findElement(By.id("DESCRICAOJUSTIFICATIVA")).clear();
		driver.findElement(By.id("DESCRICAOJUSTIFICATIVA")).sendKeys(nomeCadastrado);
		
		driver.findElement(By.xpath("//tr[2]/td[2]/input[2]")).click();
		driver.findElement(By.xpath("//tr[3]/td[2]/input[2]")).click();
		driver.findElement(By.xpath("//tr[4]/td[2]/input[2]")).click();
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro gravado com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nomeCadastrado);

		assertEquals(nomeCadastrado, driver.findElement(By.id("DESCRICAOJUSTIFICATIVA")).getAttribute("value"));
		
		WebElement suspensao = driver.findElement(By.xpath("//tr[2]/td[2]/input[2]"));
		WebElement situacao = driver.findElement(By.xpath("//tr[3]/td[2]/input[2]"));
		WebElement viagem = driver.findElement(By.xpath("//tr[4]/td[2]/input[2]"));
		
		WebElement suspensaoF = driver.findElement(By.xpath("//tr[2]/td[2]/input"));
		WebElement situacaoF = driver.findElement(By.xpath("//tr[3]/td[2]/input"));
		WebElement viagemF = driver.findElement(By.xpath("//tr[4]/td[2]/input"));
		
		Assert.assertTrue(suspensao.isSelected());
		Assert.assertTrue(situacao.isSelected());
		Assert.assertTrue(viagem.isSelected());
		
		Assert.assertFalse(suspensaoF.isSelected());
		Assert.assertFalse(situacaoF.isSelected());
		Assert.assertFalse(viagemF.isSelected());
		
	}
	
	@Test
	public void cit_691_ExcluirJustificativaSolicitacao() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
				nomeCadastrado);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Confirma a exclusão do registro [\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso!", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
			
	
	@Test
	public void cit_192_CamposObrigatorios() throws Exception
	{

		// Descrição campo obrigatório
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
	    assertEquals("Descrição: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
		// Suspenso campo obrigatório
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("DESCRICAOJUSTIFICATIVA")).sendKeys("campo obrigatorio");
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
	    assertEquals("Suspenso:: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
		// Situação campo obrigatório
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("DESCRICAOJUSTIFICATIVA")).sendKeys("campo obrigatorio");
		driver.findElement(By.xpath("//tr[2]/td[2]/input")).click();
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		UtilWebSelenium.waitForAlert(driver, 3000);
	    assertEquals("Situação: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
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
		return  "termo_pesq_TABLESEARCH_37";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//div[2]/table/tbody/tr/td[2]/div";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa Justificativa Solicitação";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return null;
	}
	
}