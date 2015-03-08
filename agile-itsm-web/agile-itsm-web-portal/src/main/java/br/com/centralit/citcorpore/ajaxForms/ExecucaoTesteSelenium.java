package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ExecucaoTesteSeleniumDTO;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.testeselenium.cadastrosgerais.CargosSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.ClientesSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.ColaboradorSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.EmpresaSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.FornecedorSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.UnidadeSelenium;
import br.com.centralit.testeselenium.cadastrosgerais.UsuarioSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.CadastroTipoEventoServicoSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.CategoriaServicoSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.CategoriaSolucaoSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.CausaIncidenteSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.CondicaoOperacaoSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.ImportanciaNegocioSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.JustificativaSolicitacaoSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.LocalExecucaoServicosSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.PrioridadeSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.PrioridadeSolicitacaoSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.TipoServicoSelenium;
import br.com.centralit.testeselenium.gerenciadecatalogosdeservico.TipoSolicitacaoServicoSelenium;
import br.com.centralit.testeselenium.gerenciadeconfiguracoes.CaracteristicaSelenium;
import br.com.centralit.testeselenium.gerenciadeconfiguracoes.GrupoItemConfiguracaoSelenium;
import br.com.centralit.testeselenium.gerenciadeconfiguracoes.MidiaSoftwareSelenium;
import br.com.centralit.testeselenium.gerenciadeconfiguracoes.SoftwareListaNegraSelenium;
import br.com.centralit.testeselenium.gerenciadeconfiguracoes.TipoItemConfiguracaoSelenium;
import br.com.centralit.testeselenium.gerenciadeconhecimentos.CategoriaGaleriaImagensSelenium;
import br.com.centralit.testeselenium.gerenciadeconhecimentos.PalavraGemeaSelenium;
import br.com.centralit.testeselenium.gerenciaincidentes.OrigemSolicitacaoSelenium;
import br.com.centralit.testeselenium.gestaocontratos.FormulaOSSelenium;
import br.com.centralit.testeselenium.gestaocontratos.FormulaSelenium;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class ExecucaoTesteSelenium extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		if (!WebUtil.validarSeUsuarioEstaNaSessao(request, document))
			return;

		// Popular combo Importar por
		popularComboDosPacotesDeTeste(document, request, response);

		//Popular combo com os navegadores disponiveis para execu��o de teste
		popularComboDosNavegadoresParaExecucaoDeTeste(document, request, response);

		// Limpa anexos
		limparFormulario(document, request, response);

	}

	/**
	 * Popular combo com as conexoes ativas
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 */
	public void popularComboDosPacotesDeTeste(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getSelectById("pacotesDeTeste").removeAllOptions();

		document.getSelectById("pacotesDeTeste").addOption("",UtilI18N.internacionaliza(request,"citcorpore.comum.selecione"));

		document.getSelectById("pacotesDeTeste").addOption("0",UtilI18N.internacionaliza(request,"Cadastros Gerais"));
		document.getSelectById("pacotesDeTeste").addOption("1",UtilI18N.internacionaliza(request,"Ger�ncia de Cat�logo de Servi�o"));
		document.getSelectById("pacotesDeTeste").addOption("2",UtilI18N.internacionaliza(request,"Ger�ncia de Configura��es"));
		document.getSelectById("pacotesDeTeste").addOption("3",UtilI18N.internacionaliza(request,"Ger�ncia de Conhecimentos"));
		document.getSelectById("pacotesDeTeste").addOption("4",UtilI18N.internacionaliza(request,"Ger�ncia de Incidentes"));
		document.getSelectById("pacotesDeTeste").addOption("5",UtilI18N.internacionaliza(request,"Gest�o de Contratos"));

	}

	/**
	 * Popular combo com os navegadores disponiveis
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 */
	public void popularComboDosNavegadoresParaExecucaoDeTeste(DocumentHTML document,HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getSelectById("navegadorDeExecucao").removeAllOptions();

		document.getSelectById("navegadorDeExecucao").addOption("",UtilI18N.internacionaliza(request,"citcorpore.comum.selecione"));

		document.getSelectById("navegadorDeExecucao").addOption("0",UtilI18N.internacionaliza(request,"Internet Explorer"));
		document.getSelectById("navegadorDeExecucao").addOption("1",UtilI18N.internacionaliza(request,"Google Chrome"));
		document.getSelectById("navegadorDeExecucao").addOption("2",UtilI18N.internacionaliza(request,"Mozilla Firefox"));

	}
	

	/**
	 * Limpa o formulario
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void limparFormulario(DocumentHTML document,HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Limpa grid anexos
		document.executeScript("mostrarDiv()");
		HTMLForm form = document.getForm("form");
		form.clear();
		
	}

	/**
	 * Executar rotina
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void executarRotina(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!WebUtil.validarSeUsuarioEstaNaSessao(request, document)){
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		ExecucaoTesteSeleniumDTO execucaoTesteDTO = (ExecucaoTesteSeleniumDTO) document.getBean();

		JUnitCore junit = new JUnitCore();
		Result resultado;
		
		java.util.List<String> listaDeTestesQuePassaram = new ArrayList<>();
		java.util.List<String> listaDeTestesQueFalharam = new ArrayList<>();
		java.util.List<String> listaDeFalhas = new ArrayList<>();
		Integer erros = 0;
		Integer total = 0;		
		
		StringBuilder divSucesso = new StringBuilder("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">").append("Testes executados com sucesso:").append("</h4>").append("</div></div>");
		StringBuilder divErro = new StringBuilder("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">").append("Testes executados com falha:").append("</h4>").append("</div></div>");

		
		try{
			
			if(execucaoTesteDTO == null || execucaoTesteDTO.getUsuarioDoTeste() == null || execucaoTesteDTO.getUsuarioDoTeste().trim().equals("")
					|| execucaoTesteDTO.getSenhaDoTeste() == null || execucaoTesteDTO.getSenhaDoTeste().trim().equals("")
					|| execucaoTesteDTO.getNavegadorDeExecucao() == null) { 
				
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
				
			}
			
			ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

			parametroService.atualizarParametro(ParametroSistema.SELENIUM_USUARIO_TESTE.id(), execucaoTesteDTO.getUsuarioDoTeste());
			parametroService.atualizarParametro(ParametroSistema.SELENIUM_SENHA_TEST.id(), execucaoTesteDTO.getSenhaDoTeste());
			parametroService.atualizarParametro(ParametroSistema.SELENIUM_URL_TESTE.id(), request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
			parametroService.atualizarParametro(ParametroSistema.SELENIUM_URL_CLIENTE_TESTE.id(), request.getRemoteHost());

			
			if(execucaoTesteDTO.getNavegadorDeExecucao() == 0){
				parametroService.atualizarParametro(ParametroSistema.SELENIUM_NAVEGADOR_TESTE.id(), "Internet Explorer");				
			}
			
			
			if(execucaoTesteDTO.getNavegadorDeExecucao() == 1){
				parametroService.atualizarParametro(ParametroSistema.SELENIUM_NAVEGADOR_TESTE.id(), "Google Chrome");				
			}
			
			
			if(execucaoTesteDTO.getNavegadorDeExecucao() == 2)
				parametroService.atualizarParametro(ParametroSistema.SELENIUM_NAVEGADOR_TESTE.id(), "Mozilla Firefox");

			
			//Cadastros gerais
			if(execucaoTesteDTO.getCargos() != null && execucaoTesteDTO.getCargos().equalsIgnoreCase("on")){

				resultado = junit.run(CargosSelenium.class);

				if(resultado.getFailureCount() > 0){
					
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Cargo:").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Cargo");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Cargo").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Cargo");
					
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
				
			}
			
			if(execucaoTesteDTO.getClientes() != null && execucaoTesteDTO.getClientes().equalsIgnoreCase("on")){
				
				resultado = junit.run(ClientesSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Cliente").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Cliente");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Cliente").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Cliente");
					
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
				
			}
				
			
			if(execucaoTesteDTO.getColaborador() != null && execucaoTesteDTO.getColaborador().equalsIgnoreCase("on")){
				resultado = junit.run(ColaboradorSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Colaborador:").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Colaborador");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Colaborador").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Colaborador");
					
				} 
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
			} 
			
			if(execucaoTesteDTO.getEmpresa() != null && execucaoTesteDTO.getEmpresa().equalsIgnoreCase("on")){
				resultado = junit.run(EmpresaSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Empresa").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Cargo");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Empresa").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Empresa");
					
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
			}
			
			if(execucaoTesteDTO.getFornecedor() != null && execucaoTesteDTO.getFornecedor().equalsIgnoreCase("on")){
				resultado = junit.run(FornecedorSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Fornecedor").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Fornecedor");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Fornecedor").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Fornecedor");
					
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
			}
				
			
			if(execucaoTesteDTO.getUnidade() != null && execucaoTesteDTO.getUnidade().equalsIgnoreCase("on")){
				resultado = junit.run(UnidadeSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Unidade").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Unidade");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Unidade").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Unidade");
					
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
			}
			
			if(execucaoTesteDTO.getUsuario() != null && execucaoTesteDTO.getUsuario().equalsIgnoreCase("on")){
				resultado = junit.run(UsuarioSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Usu�rio").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Usu�rio");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Usu�rio").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Usu�rio");
					
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
			}
			
			
			
			//Gerencia de Catalogo de Servico
			if(execucaoTesteDTO.getTipoEventoServico() != null && execucaoTesteDTO.getTipoEventoServico().equalsIgnoreCase("on")){
				resultado = junit.run(CadastroTipoEventoServicoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Tipo Evento Servi�o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Usu�rio");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Tipo Evento Servi�o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Tipo Evento Servi�o");
					
				} 
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
			}
				
			
			if(execucaoTesteDTO.getCategoriaServico() != null && execucaoTesteDTO.getCategoriaServico().equalsIgnoreCase("on")){
				resultado = junit.run(CategoriaServicoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Categoria Servi�o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Categoria Servi�o");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Categoria Servi�o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Categoria Servi�o");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
			}
			
			if(execucaoTesteDTO.getCategoriaSolucao() != null && execucaoTesteDTO.getCategoriaSolucao().equalsIgnoreCase("on")){
				resultado = junit.run(CategoriaSolucaoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Categoria Solu��o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Categoria Solu��o");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Categoria Solu��o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Categoria Solu��o");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
			}
				
			
			if(execucaoTesteDTO.getCausaIncidente() != null && execucaoTesteDTO.getCausaIncidente().equalsIgnoreCase("on")){
				resultado = junit.run(CausaIncidenteSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Causa Incidente").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Causa Incidente");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Causa Incidente").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Causa Incidente");
				} 
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();				
			}
			
			
			if(execucaoTesteDTO.getCondicaoOperacao() != null && execucaoTesteDTO.getCondicaoOperacao().equalsIgnoreCase("on")){
				resultado = junit.run(CondicaoOperacaoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Condi��o Opera��o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Condi��o Opera��o");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Condi��o Opera��o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Condi��o Opera��o");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();
			}
			
			if(execucaoTesteDTO.getImportanciaNegocio() != null && execucaoTesteDTO.getImportanciaNegocio().equalsIgnoreCase("on")){
				resultado = junit.run(ImportanciaNegocioSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Import�ncia Neg�cio").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Import�ncia Neg�cio");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Import�ncia Neg�cio").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Import�ncia Neg�cio");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();	
			}
				
			
			if(execucaoTesteDTO.getJustificativaSolicitacao() != null && execucaoTesteDTO.getJustificativaSolicitacao().equalsIgnoreCase("on")){
				resultado = junit.run(JustificativaSolicitacaoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Justificativa Solicita��o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Justificativa Solicita��o");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Justificativa Solicita��o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Justificativa Solicita��o");
				} 
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();			
			}
				
			
			if(execucaoTesteDTO.getLocalExecucaoServico() != null && execucaoTesteDTO.getLocalExecucaoServico().equalsIgnoreCase("on")){
				resultado = junit.run(LocalExecucaoServicosSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Local Execu��o Servi�o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Local Execu��o Servi�o");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Local Execu��o Servi�o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Local Execu��o Servi�o");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();	
			}
				
			
			if(execucaoTesteDTO.getPrioridade() != null && execucaoTesteDTO.getPrioridade().equalsIgnoreCase("on")){
				resultado = junit.run(PrioridadeSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Prioridade").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Usu�rio");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Prioridade").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Prioridade");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();			
			}
			
			
			if(execucaoTesteDTO.getSituacao() != null && execucaoTesteDTO.getSituacao().equalsIgnoreCase("on")){
				resultado = junit.run(PrioridadeSolicitacaoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Prioridade Solicitacao").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Prioridade Solicitacao");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Prioridade Solicitacao").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Prioridade Solicitacao");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();			
			}
				
			
			if(execucaoTesteDTO.getTipoServico() != null && execucaoTesteDTO.getTipoServico().equalsIgnoreCase("on")){
				resultado = junit.run(TipoServicoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Tipo Servi�o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Tipo Servi�o");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Tipo Servi�o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Tipo Servi�o");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();	
			}

			
			if(execucaoTesteDTO.getTipoSolicitacaoServico() != null && execucaoTesteDTO.getTipoSolicitacaoServico().equalsIgnoreCase("on")){
				resultado = junit.run(TipoSolicitacaoServicoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Tipo Solicita��o Servi�o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Usu�rio");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Tipo Solicita��o Servi�o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Tipo Solicita��o Servi�o");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();	
			}
			
			
			
			//Gerencia de Configura��o
			if(execucaoTesteDTO.getCaracteristica() != null && execucaoTesteDTO.getCaracteristica().equalsIgnoreCase("on")){
				resultado = junit.run(CaracteristicaSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Caracter�stica").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Caracter�stica");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Caracter�stica").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Caracter�stica");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();							
			}
			
			if(execucaoTesteDTO.getGrupoItem() != null && execucaoTesteDTO.getGrupoItem().equalsIgnoreCase("on")){
				resultado = junit.run(GrupoItemConfiguracaoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Grupo Item Configura��o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Grupo Item Configura��o");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Grupo Item Configura��o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Grupo Item Configura��o");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();						
			}
			
			if(execucaoTesteDTO.getMidiaSoftware() != null && execucaoTesteDTO.getMidiaSoftware().equalsIgnoreCase("on")){
				resultado = junit.run(MidiaSoftwareSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Midia Software").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Midia Software");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Midia Software").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Midia Software");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();				
			}
				
			
			if(execucaoTesteDTO.getSoftwareListaNegra() != null && execucaoTesteDTO.getSoftwareListaNegra().equalsIgnoreCase("on")){
				resultado = junit.run(SoftwareListaNegraSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Software Lista Negra").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Software Lista Negra");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Software Lista Negra").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Software Lista Negra");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();	
			}
				
			
			if(execucaoTesteDTO.getTipoItemConfiguracao() != null && execucaoTesteDTO.getTipoItemConfiguracao().equalsIgnoreCase("on")){
				resultado = junit.run(TipoItemConfiguracaoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Tipo Item Configura��o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Tipo Item Configura��o");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Tipo Item Configura��o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Tipo Item Configura��o");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();				
			}
			
			
			
			//Gerencia de Conhecimento
			if(execucaoTesteDTO.getCategoriaGaleria() != null && execucaoTesteDTO.getCategoriaGaleria().equalsIgnoreCase("on")){
				resultado = junit.run(CategoriaGaleriaImagensSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Categoria Galeria Imagem").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Categoria Galeria Imagem");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Categoria Galeria Imagem").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Categoria Galeria Imagem");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();	
			}
				
			
			if(execucaoTesteDTO.getPalavraGemea() != null && execucaoTesteDTO.getPalavraGemea().equalsIgnoreCase("on")){
				resultado = junit.run(PalavraGemeaSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Palavra G�mea").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Palavra G�mea");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Palavra G�mea").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Palavra G�mea");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();			
			}
			
			
			//Gerencia de Incidente
			if(execucaoTesteDTO.getOrigem() != null && execucaoTesteDTO.getOrigem().equalsIgnoreCase("on")){
				resultado = junit.run(OrigemSolicitacaoSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("Origem Solicita��o").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("Origem Solicita��o");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("Origem Solicita��o").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("Origem Solicita��o");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();	
			}
			
			
			//Gestao de Contrato
			if(execucaoTesteDTO.getFormulaOS() != null && execucaoTesteDTO.getFormulaOS().equalsIgnoreCase("on")){
				resultado = junit.run(FormulaOSSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("F�rmula OS").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("F�rmula OS");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("F�rmula OS").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("F�rmula OS");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();	
			}
				
			
			if(execucaoTesteDTO.getFormulaOS() != null && execucaoTesteDTO.getFormulaOS().equalsIgnoreCase("on")){
				resultado = junit.run(FormulaSelenium.class);
				
				if(resultado.getFailureCount() > 0){
					divErro.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divErro.append("F�rmula").append("</h4>");
					divErro.append("\"" + resultado.getFailures().toString() + "\"");
					divErro.append("</div></div>");
					
					listaDeTestesQueFalharam.add("F�rmula");
					listaDeFalhas.add(resultado.getFailures().toString());
					
				} else {
					
					divSucesso.append("<div class=\"media\"><div class=\"media-body\"><h4 class=\"media-heading\">");
					divSucesso.append("F�rmula").append("</h4>");
					divSucesso.append("</div></div>");
					
					listaDeTestesQuePassaram.add("F�rmula");
				}
				
				total += resultado.getRunCount();
				erros += resultado.getFailureCount();	
			}

			
			//Mensagens das telas executadas
			
			StringBuilder telasComSucesso = new StringBuilder();
			for(String labelSucesso: listaDeTestesQuePassaram){
				
				if(telasComSucesso.length() > 0)
					telasComSucesso.append("\n");
				
				telasComSucesso.append(" * ").append(labelSucesso);
				
			}
			if(telasComSucesso.length() <= 0)
				telasComSucesso.append("Sem dados");
			
			Integer cont = 0;
			StringBuilder telasComErro = new StringBuilder();
			for(String labelErro: listaDeTestesQueFalharam){
				
				if(telasComErro.length() > 0)
					telasComErro.append("\n");
				
				telasComErro.append(" * ").append(labelErro);
				telasComErro.append("Erros:").append(listaDeFalhas.get(cont).toString()).append("\n\n");
				
				cont++;
			}
			
			if(telasComErro.length() <= 0)
				telasComErro.append("Sem dados");
			
			StringBuilder parametros = new StringBuilder();
			parametros.append(telasComSucesso).append("','").append(telasComErro);
			
			//document.executeScript("atualizaLabel('" + parametros.toString() + "')");
			
			Integer sucesso = total-erros;
			
			Integer pctSucesso = ((100 * sucesso) / total);
			Integer pctErro = ((100 * erros) / total);
			
			parametros = new StringBuilder();
			parametros.append(total).append(",").append(sucesso).append(",").append(erros).append(",").append(100).append(",").append(pctSucesso).append(",").append(pctErro);
			
			document.executeScript("atualizarDadosGrafico("+parametros.toString()+")");
			
			document.executeScript("document.getElementById('tab2').click();");
			
			System.out.println("Total::" + total + "Certos:" + (total-erros) + "Erros:" + erros);
			System.out.println(listaDeFalhas.toString());

			document.executeScript("$('#testesExecutadosComSucesso').html('" + divSucesso.toString() + "')");
			document.executeScript("$('#testesExecutadosComFalha').html('" + divErro.toString() + "')");
			
			//document.executeScript("iniciarGrafico()");
			
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("---------------------- Erro na rotina de execu��o ----------------------------");
			System.out.println(e.toString());
		} finally {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();			
		}

	}

	@Override
	public Class<ExecucaoTesteSeleniumDTO> getBeanClass() {
		return ExecucaoTesteSeleniumDTO.class;
	}

}