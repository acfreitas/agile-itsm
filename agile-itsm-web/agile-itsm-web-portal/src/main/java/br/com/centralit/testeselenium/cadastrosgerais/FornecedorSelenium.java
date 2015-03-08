package br.com.centralit.testeselenium.cadastrosgerais;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import br.com.centralit.testeselenium.helper.MetodosGenericos;
import br.com.centralit.testeselenium.helper.TesteCitsmart;
import br.com.centralit.testeselenium.helper.UtilTeste;
import br.com.centralit.testeselenium.helper.UtilWebSelenium;

public class FornecedorSelenium extends TesteCitsmart implements MetodosGenericos
{

	private final String page = "/pages/fornecedor/fornecedor.load";
	private String nomeCadastrado;
	
	@Before
	public void setUp() throws Exception
	{

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		login(driver, baseUrl, usuario, senha);

		UtilWebSelenium.acessarPagina(driver, baseUrl + page);
		
			maximizarTela();
		


	}

	public String cadastrarRegistro() throws Exception{
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();

		nomeCadastrado = UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "razaoSocial");
		
		driver.findElement(By.id("razaoSocial")).sendKeys(nomeCadastrado);
		driver.findElement(By.id("nomeFantasia")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nomeFantasia"));
		new Select(driver.findElement(By.id("comboTiposPessoa"))).selectByVisibleText("Jurídica");
		
		driver.findElement(By.id("cnpj")).sendKeys(UtilTeste.geraCNPJSemMascara());
		
		driver.findElement(By.id("inscricaoMunicipal")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "inscricaoMunicipal"));
		driver.findElement(By.id("inscricaoEstadual")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "inscricaoEstadual"));
		
		driver.findElement(By.id("telefone")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "telefone"));
		driver.findElement(By.id("fax")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "fax"));
		
		driver.findElement(By.id("email")).sendKeys("teste@automatizado.com.br");
		driver.findElement(By.id("nomeContato")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nomeContato"));
		driver.findElement(By.id("logradouro")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "logradouro"));
		driver.findElement(By.id("numero")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "numero"));
		driver.findElement(By.id("complemento")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "complemento"));
		driver.findElement(By.id("bairro")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "bairro"));
		
		driver.findElement(By.id("cep")).sendKeys(UtilWebSelenium.geraNumeroAleatoriaDoCampo(driver, "cep"));
		
		new Select(driver.findElement(By.id("comboPaises"))).selectByVisibleText("Brasil");
		new Select(driver.findElement(By.id("comboUfs"))).selectByVisibleText("Acre");
		new Select(driver.findElement(By.id("comboCidades"))).selectByVisibleText("Porto Acre");
		
		driver.findElement(By.id("observacao")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "observacao"));
		driver.findElement(By.id("responsabilidades")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "responsabilidades"));
	
		new Select(driver.findElement(By.id("idTipoRegistro"))).selectByVisibleText("Atas de Reunião");
		new Select(driver.findElement(By.id("idFrequencia"))).selectByVisibleText("Mensal");
		new Select(driver.findElement(By.id("idFormaContato"))).selectByVisibleText("Solicitação via Citsmart ou e-mail");
		
		driver.findElement(By.id("ativ_responsabilidades")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "ativ_responsabilidades"));
		driver.findElement(By.id("gerenciamentodesacordo")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "gerenciamentodesacordo"));
	
		
		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 10000);
		
		assertEquals("Registro inserido com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		return nomeCadastrado;
		
	}
	
	@Test
	public void cit_403_CadastrarFornecedor() throws Exception
	{

		Assert.assertTrue(
				pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), 
						cadastrarRegistro()));

	}
	
	@Test
	public void cit_697_AlterarFornecedor() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		nomeCadastrado = nomeCadastrado.substring(0, 10) + "editado";
		
		driver.findElement(By.id("razaoSocial")).clear();
		driver.findElement(By.id("razaoSocial")).sendKeys(nomeCadastrado);
		
		new Select(driver.findElement(By.id("comboTiposPessoa"))).selectByVisibleText("Física");
		
		String cpf = UtilTeste.geraCPFSemMascara();
		driver.findElement(By.id("cnpj")).sendKeys(cpf);

		driver.findElement(By.id(getNomeDoBotaoDeSalvar())).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
		assertEquals("Registro alterado com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));
		
		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		assertEquals(nomeCadastrado, driver.findElement(By.id("razaoSocial")).getAttribute("value"));
		assertEquals("Física", (new Select(driver.findElement(By.id("comboTiposPessoa")))).getFirstSelectedOption().getText().trim());
		assertEquals(cpf, driver.findElement(By.id("cnpj")).getAttribute("value").replace(".", "").replace("-", ""));
		
	}
	
	@Test
	public void cit_698_ExcluirFornecedor() throws Exception
	{

		nomeCadastrado = cadastrarRegistro();

		pesquisarRegistro(getNomeDoBotaoDeLimparPesquisa(), getNomeDaAbaDePesquisa(), getNomeDoCampoDePesquisa(), getNomeDoBotaoDePesquisa(), getXpathDoPrimeiroItemDaListaDePesquisa(), nomeCadastrado);

		driver.findElement(By.id(getNomeDoBotaoDeExcluir())).click();

		UtilWebSelenium.waitForAlert(driver, 3000);
		
	    UtilWebSelenium.closeAlertAndGetItsText(driver).matches("^Deseja realmente excluir[\\s\\S]$");

	    UtilWebSelenium.waitForAlert(driver, 3000);
	    
	    assertEquals("Registro excluído com sucesso", UtilWebSelenium.closeAlertAndGetItsText(driver));

	}
	
	@Test
	public void cit_692_NomeRazaoSocialObrigatorio() throws Exception
	{
	
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome / Razão social: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_693_NomeFantasiaObrigatorio() throws Exception
	{
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("razaoSocial")).sendKeys("Teste do campo obrigatorio!!!");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Nome fantasia: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_694_PaisObrigatorio() throws Exception
	{
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("razaoSocial")).sendKeys("Teste do campo obrigatorio!!!");
		driver.findElement(By.id("nomeFantasia")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nomeFantasia"));
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("País: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_695_UfObrigatorio() throws Exception
	{
		
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("razaoSocial")).sendKeys("Teste do campo obrigatorio!!!");
		driver.findElement(By.id("nomeFantasia")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nomeFantasia"));
		new Select(driver.findElement(By.id("comboPaises"))).selectByVisibleText("Brasil");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Uf: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
	}
	
	@Test
	public void cit_696_CidadeObrigatorio() throws Exception
	{
		driver.findElement(By.id(getNomeDoBotaoDeLimpar())).click();
		
		driver.findElement(By.id("razaoSocial")).sendKeys("Teste do campo obrigatorio!!!");
		driver.findElement(By.id("nomeFantasia")).sendKeys(UtilWebSelenium.geraStringAleatoriaDoCampo(driver, "nomeFantasia"));
		new Select(driver.findElement(By.id("comboPaises"))).selectByVisibleText("Brasil");
		new Select(driver.findElement(By.id("comboUfs"))).selectByVisibleText("Acre");
		
		driver.findElement(By.id("btnGravar")).click();
		
		UtilWebSelenium.waitForAlert(driver, 5000);
		
	    assertEquals("Cidade: Campo obrigatório", UtilWebSelenium.closeAlertAndGetItsText(driver));
	    
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
		return  "pesqLockupLOOKUP_FORNECEDOR_razaosocial";
	}

	@Override
	public String getXpathDoPrimeiroItemDaListaDePesquisa()
	{
		return "//table[@id='topoRetorno']/tbody/tr[2]/td/input";
	}

	@Override
	public String getNomeDaAbaDePesquisa()
	{
		return "Pesquisa de Fornecedores";
	}

	@Override
	public String getNomeDoBotaoDeLimpar()
	{
		return "btnLimpar";
	}

	@Override
	public String getNomeDoBotaoDeLimparPesquisa()
	{
		return "btnLimparLOOKUP_FORNECEDOR";
	}

}