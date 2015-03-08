package br.com.centralit.citcorpore.teste.TesteSelenium.RequisicaoViagem;

import junit.framework.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RequisicaoViagemUtilSelenium {
	
	private WebDriverWait wait;
	private WebDriver driver;
	private String baseUrl;
	private String numeroDaSolicitacao;
	
	private final String ENDERECO_CHROME = "C:\\Users\\david.silva\\Documents\\Teste Automatizados\\Driver\\chromedriver.exe";
	private final String ENDERECO_IE = "C:\\Users\\david.silva\\Documents\\Teste Automatizados\\Driver\\IEDriverServer.exe";
	private final String NAVEGADOR = "firefox";
	private final String CONTRATO = "001 - CENTRAL IT de 01/01/2012 (CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA - CENTRAL IT TECNOLOGIA DA INFORMAÇÃO LTDA)";
	
	private final String SOLICITACAO = "125116";
	
	private final String DATA_INICIO = "01022014";
	private final String DATA_FIM = "11022014";
	private final String DATA_COTACAO = "01022014";
	private final String NOME_EMPREGADO = "gilberto tavares de franco";
	private final String CIDADE_ORIGEM = "goiania";
	private final String CIDADE_DESTINO = "brasilia";
	
	public  void login (String nome, String senha) throws InterruptedException{
		driver.get(baseUrl + "/citsmart/pages/login/login.load");
		driver.findElement(By.id("user")).clear();
		driver.findElement(By.id("user")).sendKeys(nome);
		driver.findElement(By.id("senha")).clear();
		driver.findElement(By.id("senha")).sendKeys(senha);
		driver.findElement(By.xpath("//button[@id='']")).click();
		Thread.sleep(2000);
		// Maximizar tela
		driver.manage().window().maximize();
	}
	
	public  void iniciarNavegador(){
		baseUrl = "http://localhost/";
		if (NAVEGADOR.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (NAVEGADOR.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", ENDERECO_IE);
			driver = new InternetExplorerDriver();
		} else {
			System.setProperty("webdriver.chrome.driver", ENDERECO_CHROME);
			driver = new ChromeDriver();
		}
		wait = new WebDriverWait(driver, 120);
	}
	
	public void filtroBuscaSolicitacao(String solicitacao) throws InterruptedException{
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//button[@id='']")).click();
		waitForPageToLoad();
		Thread.sleep(500);
		WebElement pesquisa = driver.findElement(By.id("formGerenciamento"));
		pesquisa.findElement(By.name("idSolicitacao")).sendKeys(solicitacao);
		new Select(driver.findElement(By.id("idContrato"))).selectByVisibleText(CONTRATO);
		pesquisa.findElement(By.xpath("//li[@id='acoes']/ul/li/div/div/div[5]/div/button")).click();
		waitForPageToLoad();
		Thread.sleep(5000);
	}
		
	public void pesquisarSolicitacaoInterno() throws InterruptedException{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Registro Incidentes/Req. de Serviços")));
		driver.findElement(By.linkText("Registro Incidentes/Req. de Serviços")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formGerenciamento")));	
		filtroBuscaSolicitacao(SOLICITACAO);
		Thread.sleep(5000);
	}
	
	public void trocaDeFrame() throws InterruptedException{
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("frameNovaSolicitacao"));
		Thread.sleep(500);		
		waitForPageToLoad();
		Thread.sleep(500);	
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("fraInformacoesComplementares"));	
		waitForPageToLoad();
		Thread.sleep(500);
	}
	
	public void clicaBotaoExecutar() throws InterruptedException{
		waitForPageToLoad();
		Thread.sleep(7000);		
		driver.findElement(By.id("executar")).click();
	}
	
	public void clicaBotaoGravarEAvancar() throws InterruptedException{
		driver.switchTo().defaultContent();
		driver.switchTo().frame("frameNovaSolicitacao");
//		driver.findElement(By.id("btnGravar")).click();
		driver.findElement(By.id("btnGravarEContinuar")).click();
		waitForAlert();
	}
	
	public void aprovarCompra() throws InterruptedException{
		clicaBotaoExecutar();
		confirmarCompra();
		clicaBotaoGravarEAvancar();
	}
	
	public void confirmarCompra() throws InterruptedException{
		waitForPageToLoad();
		Thread.sleep(500);	
		trocaDeFrame();
		driver.findElement(By.id("confirmaExec")).click();
	}
	
	public void aprovarSolicitacao() throws InterruptedException{
		clicaBotaoExecutar();
		solicitacaoAprovada();
		clicaBotaoGravarEAvancar();
	}
	
	public void naoAprovarSolicitacao() throws InterruptedException{
		clicaBotaoExecutar();
		solicitacaoNaoAprovada();
		clicaBotaoGravarEAvancar();
	}
	
	public void solicitacaoAprovada() throws InterruptedException{
		waitForPageToLoad();
		Thread.sleep(2000);
		trocaDeFrame();
		driver.findElement(By.id("autorizarS")).click();
	}
	
	public void solicitacaoNaoAprovada() throws InterruptedException{
		waitForPageToLoad();
		Thread.sleep(2000);
		trocaDeFrame();
		driver.findElement(By.id("autorizarN")).click();
		new Select(driver.findElement(By.id("idJustificativaAutorizacao"))).selectByVisibleText("Autorização não reembolsável");
		driver.findElement(By.id("complemJustificativaAutorizacao")).sendKeys("Não reembolsável - Teste Selenium");		
	}
	
	public void novaRequisicaoViagem() throws Exception {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Registro Incidentes/Req. de Serviços")));
		driver.findElement(By.linkText("Registro Incidentes/Req. de Serviços")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formGerenciamento")));

		driver.findElement(By.xpath("//span[@onclick='modalNovaSolicitacaoServico()']")).click();
		Thread.sleep(2000);
		driver.switchTo().frame("frameNovaSolicitacao");
		Thread.sleep(1000);		
		new Select(driver.findElement(By.id("idContrato"))).selectByVisibleText(CONTRATO);
		Thread.sleep(2000);
		new Select(driver.findElement(By.id("idOrigem"))).selectByVisibleText("Operador");

		driver.findElement(By.id("solicitante")).sendKeys("david r");
		Thread.sleep(3000);
		driver.findElement(By.id("solicitante")).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(500);		
		driver.findElement(By.id("solicitante")).sendKeys(Keys.UP);
		Thread.sleep(500);		
		driver.findElement(By.id("solicitante")).sendKeys(Keys.RETURN);
		Thread.sleep(2000);		
		driver.findElement(By.linkText("Próximo")).click();
		Thread.sleep(2000);

		new Select(driver.findElement(By.id("idTipoDemandaServico"))).selectByVisibleText("Requisição");
		((JavascriptExecutor) driver).executeScript("$(descricao).data('wysihtml5').editor.setValue('Requisição de Teste');");
		Thread.sleep(4000);
		driver.findElement(By.id("servicoBusca")).sendKeys("VIAGEM");
		Thread.sleep(5000);
		driver.findElement(By.id("servicoBusca")).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(2000);
		driver.findElement(By.id("servicoBusca")).sendKeys(Keys.RETURN);
		Thread.sleep(500);
		
		waitForPageToLoad();
		driver.switchTo().frame("fraInformacoesComplementares");
		Thread.sleep(500);	
		waitForPageToLoad();
		Thread.sleep(500);
		driver.findElement(By.id("nomeCidadeEUfOrigem")).sendKeys(CIDADE_ORIGEM);
		Thread.sleep(2000);		
		driver.findElement(By.id("nomeCidadeEUfOrigem")).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(2000);		
		driver.findElement(By.id("nomeCidadeEUfOrigem")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("nomeCidadeEUfDestino")).sendKeys(CIDADE_DESTINO);
		Thread.sleep(3000);		
		driver.findElement(By.id("nomeCidadeEUfDestino")).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(500);		
		driver.findElement(By.id("nomeCidadeEUfDestino")).sendKeys(Keys.RETURN);	
		
		driver.findElement(By.id("dataInicioViagem")).sendKeys(DATA_INICIO);		
		driver.findElement(By.id("dataInicioViagem")).sendKeys(Keys.RETURN);		
		driver.findElement(By.id("dataFimViagem")).sendKeys(DATA_FIM);		
		driver.findElement(By.id("dataFimViagem")).sendKeys(Keys.RETURN);
		
		new Select(driver.findElement(By.id("idCentroCusto"))).selectByVisibleText(".....01.008 CIT SMART");		
		new Select(driver.findElement(By.id("idProjeto"))).selectByVisibleText(".....1001 GERAL");		
		new Select(driver.findElement(By.id("idMotivoViagem"))).selectByVisibleText("Implantação do Sistema");		
		driver.findElement(By.id("descricaoMotivo")).sendKeys("Implantação de Sistema (Selenium)");		
		driver.findElement(By.id("descricaoMotivo")).sendKeys(Keys.RETURN);		
		driver.findElement(By.id("nomeEmpregado")).sendKeys(NOME_EMPREGADO);
		Thread.sleep(7000);	
		driver.findElement(By.id("nomeEmpregado")).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(3000);		
		driver.findElement(By.id("nomeEmpregado")).sendKeys(Keys.RETURN);
		Thread.sleep(5000);				
		driver.findElement(By.id("btnAddIntegranteViagem")).sendKeys(Keys.RETURN);
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("frameNovaSolicitacao");
		driver.findElement(By.id("btnGravar")).click();
		waitForPageToLoad();
		Thread.sleep(7000);		
		
		numeroDaSolicitacao = driver.findElement(By.id("mensagem_insercao")).getText();
		numeroDaSolicitacao = numeroDaSolicitacao.substring((numeroDaSolicitacao.indexOf(":") + 2),(numeroDaSolicitacao.indexOf("criada.") - 1));

		// Valida se a requisição foi salva
		Assert.assertTrue(driver.findElement(By.id("mensagem_insercao"))
				.getText().contains("Registro inserido com sucesso."));

		driver.findElement(By.id("btFecharMensagem")).click();
		Thread.sleep(4000);
	}
	
	public void pesquisarEmExecucao() throws InterruptedException{
		waitForPageToLoad();
		Thread.sleep(8000);	
		filtroBuscaSolicitacao(numeroDaSolicitacao);
	}
	
	public void avancarFluxoEmExecucao() throws Exception {	
		pesquisarEmExecucao();		
		clicaBotaoExecutar();
		fluxoInformacaoViagem();
		fluxoItemControleFinanceiroViagem();	
		clicaBotaoGravarEAvancar();
	}
	
	public void fluxoInformacaoViagem() throws InterruptedException{
		trocaDeFrame();
		new Select(driver.findElement(By.id("idMoeda"))).selectByVisibleText("Real");		
		driver.findElement(By.id("observacoes")).sendKeys("Implantação de Sistema (Selenium)\n");		
	}
	
	public void fluxoViagemAddPassagem() throws InterruptedException{
		new Select(driver.findElement(By.id("classificacao"))).selectByVisibleText("Passagem");
		new Select(driver.findElement(By.id("idTipoMovimFinanceiraViagem"))).selectByVisibleText("Passagem Aérea");
		new Select(driver.findElement(By.id("idJustificativa"))).selectByVisibleText("Implantação do Sistema");
		new Select(driver.findElement(By.id("idFormaPagamento"))).selectByVisibleText("Dinheiro");		
		driver.findElement(By.id("nomeFornecedor")).sendKeys("tam");
		Thread.sleep(5000);
		driver.findElement(By.id("nomeFornecedor")).sendKeys(Keys.DOWN);
		driver.findElement(By.id("nomeFornecedor")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("dataCotacao")).sendKeys(DATA_COTACAO);
		driver.findElement(By.id("dataCotacao")).sendKeys(Keys.RETURN);		
		((JavascriptExecutor) driver).executeScript("$('#horaCotacao').val('20:00');");
		((JavascriptExecutor) driver).executeScript("$('#valorUnitario').val('100');");		
		driver.findElement(By.id("valorUnitario")).sendKeys(Keys.TAB);
		new Select(driver.findElement(By.id("tipoPassagem"))).selectByVisibleText("Ida");
		driver.findElement(By.id("complementoJustificativa")).sendKeys("Teste Selenium Controle Financeiro");
		driver.findElement(By.id("btnGravar")).click();
		waitForAlert();
	}
	
	public void fluxoViagemAddDiaria() throws InterruptedException{
		new Select(driver.findElement(By.id("classificacao"))).selectByVisibleText("Diária");
		new Select(driver.findElement(By.id("idTipoMovimFinanceiraViagem"))).selectByVisibleText("Diária Padrão");
		new Select(driver.findElement(By.id("idJustificativa"))).selectByVisibleText("Implantação do Sistema");
		new Select(driver.findElement(By.id("idFormaPagamento"))).selectByVisibleText("Dinheiro");		
		driver.findElement(By.id("nomeFornecedor")).sendKeys("hotel");
		Thread.sleep(5000);
		driver.findElement(By.id("nomeFornecedor")).sendKeys(Keys.DOWN);
		driver.findElement(By.id("nomeFornecedor")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("dataCotacao")).sendKeys(DATA_COTACAO);
		driver.findElement(By.id("dataCotacao")).sendKeys(Keys.RETURN);		
		((JavascriptExecutor) driver).executeScript("$('#horaCotacao').val('20:00');");
		((JavascriptExecutor) driver).executeScript("$('#valorUnitario').val('100');");		
		driver.findElement(By.id("valorUnitario")).sendKeys(Keys.TAB);
		driver.findElement(By.id("complementoJustificativa")).sendKeys("Teste Selenium Controle Financeiro");
		driver.findElement(By.id("btnGravar")).click();
		waitForAlert();
	}
	
	public void fluxoItemControleFinanceiroViagem() throws InterruptedException{
		((JavascriptExecutor) driver).executeScript("addItemIntegrante(68421)");			
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("POPUP_ITEMCONTROLEFINANCEIRO")));
		Thread.sleep(20000);	
		WebElement frameID = driver.findElement(By.id("iframeItemControleFinanceiro"));
		driver.switchTo().frame(frameID);		
		fluxoViagemAddPassagem();
		fluxoViagemAddDiaria();	
		waitForPageToLoad();
		Thread.sleep(500);
		driver.findElement(By.id("btnFecha")).click();	
		waitForPageToLoad();
		Thread.sleep(500);		
	}
	
	public void confirmarAdiantamento() throws InterruptedException{
		clicaBotaoExecutar();
		trocaDeFrame();
		waitForPageToLoad();
		Thread.sleep(2500);
		driver.findElement(By.xpath("//tr[@id='HTMLUtils_tblControleFinaceiro_row_2']/td/a")).click();		
		waitForPageToLoad();
		driver.findElement(By.xpath("//div[@id='divBtnGravar']/button")).click();	
		waitForAlert();
		Thread.sleep(500);
		clicaBotaoGravarEAvancar();
	}
	
	public void conferenciaAprovada() throws Exception{
		clicaBotaoExecutar();
		trocaDeFrame();
		waitForPageToLoad();
		Thread.sleep(2500);
		driver.findElement(By.xpath("//input[@id='aprovado']")).click();
		Thread.sleep(500);
		clicaBotaoGravarEAvancar();
	}
	
	public void conferenciaNaoAprovada() throws Exception{
		clicaBotaoExecutar();
		trocaDeFrame();
		waitForPageToLoad();
		Thread.sleep(2500);
		driver.findElement(By.xpath("//div[@id='validacao']/div/div/fieldset/label[3]/input")).click();
		Thread.sleep(500);
		new Select(driver.findElement(By.id("idJustificativaAutorizacao"))).selectByVisibleText("Compra não necessária para o negócio");
		driver.findElement(By.id("complemJustificativaAutorizacao")).sendKeys("Justificativa - Teste Selenium");
		waitForPageToLoad();
		Thread.sleep(500);
		clicaBotaoGravarEAvancar();
	}
	
	public void prestacaoContas() throws InterruptedException{
		clicaBotaoExecutar();
		trocaDeFrame();
		waitForPageToLoad();
		Thread.sleep(2000);
		driver.findElement(By.id("nomeFornecedor")).sendKeys("TAM S.A");
		driver.findElement(By.id("nomeFornecedor")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("numeroDocumento")).sendKeys("001");
		driver.findElement(By.id("numeroDocumento")).sendKeys(Keys.RETURN);
		((JavascriptExecutor) driver).executeScript("$('#data').val('25/01/2014');");
		((JavascriptExecutor) driver).executeScript("$('#valor').val('1500');");	
		driver.findElement(By.id("descricao")).sendKeys("Passagens Aereas");
		driver.findElement(By.id("descricao")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("btnGravar")).click();
		
		driver.findElement(By.id("nomeFornecedor")).sendKeys("Hotel Bsb");
		driver.findElement(By.id("nomeFornecedor")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("numeroDocumento")).sendKeys("002");
		driver.findElement(By.id("numeroDocumento")).sendKeys(Keys.RETURN);
		((JavascriptExecutor) driver).executeScript("$('#data').val('25/01/2014');");
		((JavascriptExecutor) driver).executeScript("$('#valor').val('1500');");	
		driver.findElement(By.id("descricao")).sendKeys("Hospedagem");
		driver.findElement(By.id("descricao")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("btnGravar")).click();
		clicaBotaoGravarEAvancar();
		Thread.sleep(2000);
	}
	
	public void corrigirPrestacaoContas() throws InterruptedException{
		clicaBotaoExecutar();
		waitForPageToLoad();
		trocaDeFrame();
		waitForPageToLoad();
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//a[@onclick='removeLinhaTabela(this.parentNode.parentNode.rowIndex);']")).click();
		waitForAlert();
		driver.findElement(By.xpath("//a[@onclick='removeLinhaTabela(this.parentNode.parentNode.rowIndex);']")).click();
		waitForAlert();
		
		driver.findElement(By.id("nomeFornecedor")).sendKeys("TAM S.A");
		driver.findElement(By.id("nomeFornecedor")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("numeroDocumento")).sendKeys("005");
		driver.findElement(By.id("numeroDocumento")).sendKeys(Keys.RETURN);
		((JavascriptExecutor) driver).executeScript("$('#data').val('25/01/2014');");
		((JavascriptExecutor) driver).executeScript("$('#valor').val('750');");	
		driver.findElement(By.id("descricao")).sendKeys("Passagens Aereas");
		driver.findElement(By.id("descricao")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("btnGravar")).click();
		
		driver.findElement(By.id("nomeFornecedor")).sendKeys("Hotel Bsb");
		driver.findElement(By.id("nomeFornecedor")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("numeroDocumento")).sendKeys("006");
		driver.findElement(By.id("numeroDocumento")).sendKeys(Keys.RETURN);
		((JavascriptExecutor) driver).executeScript("$('#data').val('25/01/2014');");
		((JavascriptExecutor) driver).executeScript("$('#valor').val('1250');");	
		driver.findElement(By.id("descricao")).sendKeys("Hospedagem");
		driver.findElement(By.id("descricao")).sendKeys(Keys.RETURN);
		driver.findElement(By.id("btnGravar")).click();
		clicaBotaoGravarEAvancar();
		Thread.sleep(2000);
	}
	
	public void waitForAlert(){
          while( !isAlertPresent() ){
             try{
                  Thread.sleep(500);
             }catch (InterruptedException e){ 
                  e.printStackTrace();
             }
          }

          try {
             Thread.sleep(2000); 
          }catch (InterruptedException e){ 
               e.printStackTrace();
          }
    }

	public boolean isAlertPresent() {		 
		  boolean presentFlag = false;		 
		  try {
		   Alert alert = driver.switchTo().alert();
		   presentFlag = true;
		   alert.accept();
		  } catch (NoAlertPresentException ex) {
		   ex.printStackTrace();
		  }
		  return presentFlag;
		 }
	
	public void waitForPageToLoad() {          
        while( !((JavascriptExecutor) this.driver).executeScript("return document.readyState").equals("complete") ){
           try{
          	 Thread.sleep(500) ;
           } 
           catch (InterruptedException e) { 
                e.printStackTrace();
           }
        }        
        	try {
               Thread.sleep(2000); 
        	} 
        	catch (InterruptedException e) { 
             e.printStackTrace();
        }        
	}
	
	public void fechar() throws Exception {
		driver.quit();
	}

}
