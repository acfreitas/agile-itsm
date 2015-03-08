package br.com.centralit.citcorpore.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.negocio.VersaoService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class FiltroSegurancaCITSmart implements Filter {

	private static final int ACESSO_NEGADO = 403;
	private static Collection colLivres = new ArrayList();
	private static Boolean haVersoesSemValidacao = null;
	private static final String INTERROGACAO = "?";
	private static final int PROBLEMAS = 999;

	public static Boolean getHaVersoesSemValidacao() {
		try {
			if (haVersoesSemValidacao == null) {
				VersaoService service = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, null);
				haVersoesSemValidacao = service.haVersoesSemValidacao();
			}
			return haVersoesSemValidacao;
		} catch (Exception e) {
			return true;
		}
	}

	public static void setHaVersoesSemValidacao(Boolean haVersoesSemValidacao) {
		FiltroSegurancaCITSmart.haVersoesSemValidacao = haVersoesSemValidacao;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String path = getRequestedPath(request);

		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (path == null) {
			path = "";
		}
		if (isFilesLivres(path)) {
			chain.doFilter(request, response);
			return;
		}
		if (isPaginaScript(path)) {
			chain.doFilter(request, response);
			return;
		}
		
		if(path.equals("/pages/portal/portal.load") && usuario == null){
			request.getSession(true).setAttribute("abrePortal", "S");
		}
	
		if (isRecursoLivre(path) && !getHaVersoesSemValidacao()) {
			chain.doFilter(request, response);
			return;
		}

		ParametroCorporeService parametroService;
		String CAMINHO_RELATIVO_PAGINA_LOGIN = Constantes.getValue("CAMINHO_RELATIVO_PAGINA_LOGIN");
		try {
			CAMINHO_RELATIVO_PAGINA_LOGIN = Constantes.getValue("CAMINHO_RELATIVO_PAGINA_LOGIN");
			if (CAMINHO_RELATIVO_PAGINA_LOGIN == null || CAMINHO_RELATIVO_PAGINA_LOGIN.trim().equalsIgnoreCase("")) {
				parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
					CAMINHO_RELATIVO_PAGINA_LOGIN = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/login/login.load";
			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (path.equalsIgnoreCase("") || path.equalsIgnoreCase("/")) {
			if (CAMINHO_RELATIVO_PAGINA_LOGIN == null || CAMINHO_RELATIVO_PAGINA_LOGIN.trim().equalsIgnoreCase("")) {
				CAMINHO_RELATIVO_PAGINA_LOGIN = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/login/login.load";
			}
			response.sendRedirect(CAMINHO_RELATIVO_PAGINA_LOGIN);
			return;
		}
		
		if (path.equals("/portal/portal.load") && usuario != null) {
			response.sendRedirect(Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/portal/portal.load");
			return;
		}

		if (usuario == null) {
			if (CAMINHO_RELATIVO_PAGINA_LOGIN == null || CAMINHO_RELATIVO_PAGINA_LOGIN.trim().equalsIgnoreCase("")) {
				CAMINHO_RELATIVO_PAGINA_LOGIN = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/login/login.load";
			}
			CharSequence s = new String(path);
			if (CAMINHO_RELATIVO_PAGINA_LOGIN.contains(s)) { // Verifica se eh a
																// // pagina
																// de// login
																// para // nao
																// ficar em //
																// redirect //
																// infinito.
				chain.doFilter(request, response);
				return;
			}
			if (path.endsWith("/login.save")) { // Deixa passar,// se nao nem//
												// loga.
				chain.doFilter(request, response);
				return;
			}
			if (path.equalsIgnoreCase("/ActionServletMarcacao")) { // Deixa //
																	// passar,//
																	// se nao//
																	// nem loga.
				chain.doFilter(request, response);
				return;
			}
			response.sendRedirect(CAMINHO_RELATIVO_PAGINA_LOGIN);
			return;
		}
		
		if(!usuario.getLogin().equalsIgnoreCase("consultor") && !usuario.getLogin().equalsIgnoreCase("admin")){
			if ("N".equalsIgnoreCase(usuario.getAcessoCitsmart())){
				
				response.sendRedirect(Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/portal/portal.load");
				return;
			}
		}

		String idPerfilAcessoAdministrador = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_PERFIL_ACESSO_ADMINISTRADOR, "1");
		boolean usuarioTemPerfilDeAdministrador = usuario.getIdPerfilAcessoUsuario() != null && usuario.getIdPerfilAcessoUsuario().toString().trim().equals(idPerfilAcessoAdministrador.trim());
		
		// O usuario "admin" ou "admin.centralit" tem acesso a tudo.
		if (usuario.getNomeUsuario().equalsIgnoreCase("administrador") 
				|| usuario.getNomeUsuario().equalsIgnoreCase("admin.centralit") 
				|| usuario.getNomeUsuario().equalsIgnoreCase("consultor")
				|| usuario.getNomeUsuario().equalsIgnoreCase("admin")
				|| usuarioTemPerfilDeAdministrador) {
			if (!getHaVersoesSemValidacao()) {
				chain.doFilter(request, response);
			} else {
				response.sendRedirect(Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/scripts/scripts.load?upgrade=sim");
			}
			return;
		}

		String SEGURANCA_HABILITADA = Constantes.getValue("SEGURANCA_HABILITADA");
		if (SEGURANCA_HABILITADA == null) {
			SEGURANCA_HABILITADA = "N";
		}
		if (!SEGURANCA_HABILITADA.equalsIgnoreCase("S")) { // Se nao estiver//
															// setado como "S"
															// -/ Sim, entao
															// esta// livre.
			chain.doFilter(request, response);
			return;
		}

		try {
			if (isRecursoLivre(path)) {
				chain.doFilter(request, response);
				return;
			}
			Collection col = (Collection) request.getSession(true).getAttribute("acessosUsuario");
			if (col == null) { // Caso ainda nao tenha carregado a colecao com//
								// as autorizações, entao carrega.
				Collection colPathsAutorizadosUsuario = new ArrayList();
				MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
				Collection<MenuDTO> listaPermissao = menuService.listaMenuByUsr(usuario);
				if (listaPermissao != null && listaPermissao.size() > 0) {
					for (MenuDTO beanMenu : listaPermissao) {
						if (!"".equalsIgnoreCase(beanMenu.getLink()))
							colPathsAutorizadosUsuario.add("/pages" + beanMenu.getLink());
					}
				}
				col = colPathsAutorizadosUsuario;
				request.getSession(true).setAttribute("acessosUsuario", col);
			}
			boolean bAutorizado = false;
			if (col != null) {
				bAutorizado = col.contains(path);
			}
			if (bAutorizado) {
				chain.doFilter(request, response);
				return;
			} else {
				if (path.startsWith("/")) {
					path = path.substring(1);
					bAutorizado = col.contains(path); // Faz mais uma
														// tentativa,// mas
														// agora sem a barra//
														// que havia.
					if (bAutorizado) {
						chain.doFilter(request, response);
						return;
					}
				}
				request.getSession(true).setAttribute("acessoSolicitado", path);
				sendError(ACESSO_NEGADO, "O usuario nao tem acesso ao recurso solicitado.", response);
			}
		} catch (Exception e) {
			System.out.println("CITSMART - Filtro de Seguranca: Problemas -> " + e.getMessage());
			e.printStackTrace();
			chain.doFilter(request, response);
		}
	}

	private String getRequestedPath(HttpServletRequest request) {
		String path = request.getRequestURI();
		path = path.substring(request.getContextPath().length());
		int index = path.indexOf(INTERROGACAO);
		if (index != -1)
			path = path.substring(0, index);
		return path;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		try {
			VersaoService service = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, null);
			setHaVersoesSemValidacao(service.haVersoesSemValidacao());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isFilesLivres(String requestedPath) {
		if (requestedPath.endsWith(".old")) {
			return true;
		}
		if (requestedPath.endsWith(".json")) {
			return true;
		}		
		if (requestedPath.endsWith(".zip")) {
			return true;
		}
		if (requestedPath.endsWith(".cab")) {
			return true;
		}
		if (requestedPath.endsWith(".smart")) {
			return true;
		}
		if (requestedPath.endsWith(".eot")) {
			return true;
		}
		if (requestedPath.endsWith(".extern")) {
			return true;
		}
		if (requestedPath.endsWith(".getFile")) {
			return true;
		}
		if (requestedPath.endsWith(".jar")) {
			return true;
		}
		if (requestedPath.endsWith(".swf")) {
			return true;
		}
		if (requestedPath.endsWith(".mp3")) {
			return true;
		}
		if (requestedPath.endsWith(".wav")) {
			return true;
		}
		if (requestedPath.endsWith(".woff")) {
			return true;
		}
		if (requestedPath.endsWith(".ico")) {
			return true;
		}
		if (requestedPath.endsWith(".manifest")) {
			return true;
		}
		if (requestedPath.endsWith("svg-editor.jsp")) {
			return true;
		}
		if (requestedPath.endsWith("autoCompletePessoa.load")) {
			return true;
		}
		requestedPath = requestedPath.toUpperCase();
		if (requestedPath.endsWith(".JS")) {
			return true;
		}
		if (requestedPath.endsWith(".CSS")) {
			return true;
		}
		if (requestedPath.endsWith(".PDF")) {
			return true;
		}
		if (requestedPath.endsWith(".XML")) {
			return true;
		}
		if (requestedPath.endsWith(".JSON")) {
			return true;
		}		
		if (requestedPath.endsWith(".XLS") || requestedPath.endsWith(".XLSX")) {
			return true;
		}
		if (requestedPath.endsWith(".DOC") || requestedPath.endsWith(".DOCX")) {
			return true;
		}
		if (requestedPath.endsWith(".GIF") || requestedPath.endsWith(".JPG") || requestedPath.endsWith(".PNG") || requestedPath.endsWith(".BMP") || requestedPath.endsWith(".DCM")
				|| requestedPath.endsWith(".DC3") || requestedPath.endsWith(".SVG")) {
			return true;
		}
		
		return false;
	}
	
	private boolean isPaginaScript(String requestedPath) {
	
		if (requestedPath.endsWith("/scripts/scripts.load") 
				|| requestedPath.endsWith("/scripts/scripts.get") 
				|| requestedPath.endsWith("/scripts/scripts.event")
				|| requestedPath.contains("/scripts_deploy/") 
				|| requestedPath.endsWith("vazio.jsp") 
				|| requestedPath.endsWith("/start/start.event") 
				|| requestedPath.endsWith("/start/start.load")
				|| requestedPath.endsWith("/start/start.get") 
				|| requestedPath.endsWith("/login/login.load") 
				|| requestedPath.endsWith("/login/login.save") 
				|| requestedPath.endsWith("/login/login.get") 
				|| requestedPath.endsWith("/start/termos.html") 
				|| requestedPath.endsWith("/start/start.load.event")
				|| requestedPath.endsWith("/start/termos_pt_BR.html") 	
				|| requestedPath.endsWith("/start/termos_en.html")	
				|| requestedPath.endsWith("/start/termos_es.html")) {

			return true;
		}
			return false;
	}
	
	private boolean isRecursoLivre(String requestedPath) {
		//System.out.println(requestedPath);
		if (requestedPath.endsWith("/pesquisaRequisicaoLiberacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/login.load")) {
			return true;
		}
		if (requestedPath.endsWith("/modalCurriculo/modalCurriculo.load")) {
			return true;
		}
		if (requestedPath.endsWith("/autoCompleteParceiro.load")) {
			return true;
		}
		if (requestedPath.endsWith("/autoCompleteFuncionario.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadAnexos/refreshuploadAnexos.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadAnexos/refreshuploadAnexosList.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadAnexosdocsLegais/refreshuploadAnexosdocsLegais.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadExcluirDocsLegais/uploadExcluirDocsLegais.get")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/refreshuploadAnexosdocsGerais/refreshuploadAnexosdocsGerais.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadExcluirDocsGerais/uploadExcluirDocsGerais.get")) {
			return true;
		}
		
		if (requestedPath.endsWith("/ActionServlet")) {
			return true;
		}
		if (requestedPath.endsWith("/CertDisplay")) {
			return true;
		}
		if (requestedPath.endsWith("/CertVerifier")) {
			return true;
		}
		if (requestedPath.indexOf("/applet/") > -1) {
			return true;
		}
		if (requestedPath.indexOf("/services/") > -1) {
			return true;
		}
		if (requestedPath.indexOf("/mobile/") > -1) {
			return true;
		}
		if (requestedPath.indexOf("/certificadodigital/") > -1) {
			return true;
		}
		if (requestedPath.endsWith("/ActionServletLogin")) {
			return true;
		}
		if (requestedPath.endsWith("dataManagerObjects.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/solicitacaoServicoQuestionario/solicitacaoServicoQuestionario.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/liberacaoQuestionario/liberacaoQuestionario.load")) {
			return true;
		}
		if (requestedPath.endsWith("vazio.jsp")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/upload/upload.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadList/uploadList.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadDocsLegais/uploadDocsLegais.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadDocsLegaisList/uploadDocsLegaisList.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadDocsGerais/uploadDocsGerais.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadDocsGeraisList/uploadDocsGeraisList.load")) {
			return true;
		}
		if (requestedPath.endsWith("cithelp/index.html")) {
			return true;
		}
		if (requestedPath.endsWith("/cithelp/toc.html")) {
			return true;
		}
		if (requestedPath.endsWith("/cithelp/1Introduo.html")) {
			return true;
		}
		if (requestedPath.endsWith("lookup.load")) {
			return true;
		}
		if (requestedPath.endsWith("tableSearch.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/index/index.load")) {
			return true;
		}
		if (requestedPath.endsWith("citHelp/index.html")) {
			return true;
		}
		if (requestedPath.endsWith("sair.load")) {
			return true;
		}
		if (requestedPath.startsWith("/fckeditor/")) {
			return true;
		}
		if (requestedPath.startsWith("/tempUpload/") || requestedPath.startsWith("tempUpload/")) {
			return true;
		}
		if (requestedPath.endsWith("pages/index/index.jsp")) {
			return true;
		}
		if (requestedPath.endsWith("topReportControl.jsp")) {
			return true;
		}		
		if (requestedPath.endsWith("eventos.load")) {
			return true;
		}
		if (requestedPath.endsWith("delegacaoTarefa.load")) {
			return true;
		}
		if (requestedPath.endsWith("dinamicViews.load")) {
			return true;
		}
		if (requestedPath.endsWith("solicitacaoServico.load")) {
			return true;
		}
		if (requestedPath.endsWith("problema.load")) {
			return true;
		}
		if (requestedPath.endsWith("internacionalizacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("eventMonitor.load")) {
			return true;
		}
		if (requestedPath.endsWith("requisicaoMudanca.load")) {
			return true;
		}
		if (requestedPath.endsWith("requisicaoLiberacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("planoMelhoriaTreeView.load")) {
			return true;
		}
		if (requestedPath.endsWith("suspensaoSolicitacao.load")) {
			return true;
		}
/*		if (requestedPath.endsWith("origemAtendimento/origemAtendimento.load")) {
			return true;
		}*/
		if (requestedPath.endsWith("/pages/baseConhecimentoView/modalBaseConhecimento.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/osSetSituacao/osSetSituacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("mudarSLA.load")) {
			return true;
		}
		if (requestedPath.endsWith("copiarSLA.load")) {
			return true;
		}
		if (requestedPath.endsWith("agendarAtividade.load")) {
			return true;
		}
		if (requestedPath.endsWith("opiniao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/listaServicosContrato/listaServicosContrato.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/visualizarNotificacoes/visualizarNotificacoes.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/contratosAnexos/contratosAnexos.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/informacoesContratoQuestionario/informacoesContratoQuestionario.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/informacoesContrato/informacoesContrato.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/listaOSContrato/listaOSContrato.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/contratoQuestionarios/respostaPadraoFechar.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/contratoQuestionarios/respostaPadraoFechar.jsp")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/contratoQuestionarios/contratoQuestionarios.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/visualizarDesempenhoServicosContrato/visualizarDesempenhoServicosContrato.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/programacaoAtividade/programacaoAtividade.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/testeCITSmart/testeCITSmart.load")) {
			return true;
		}
		if (requestedPath.endsWith("/os.load")) {
			return true;
		}
		if (requestedPath.endsWith("listaFaturasContrato.load")) {
			return true;
		}
		if (requestedPath.endsWith("fatura.load")) {
			return true;
		}
		if (requestedPath.endsWith("tableSearchVinc.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/alterarSenha/menu.html")) {
			return true;
		}
		if (requestedPath.endsWith("pages/alterarSenha/alterarSenha.load")) {
			return true;
		}
		if (requestedPath.endsWith("pesquisaSatisfacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/portal/portal.load")) {
			return true;
		}
		if (requestedPath.endsWith("/categoriaPost/categoriaPost.load")) {
			return true;
		}
		if (requestedPath.endsWith("/post/post.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pesquisa/pesquisa.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/visualizarUploadTemp/visualizarUploadTemp.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/cargaParametroCorpore/cargaParametroCorpore.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/cadastroFluxo/cadastroFluxo.load")) {
			return true;
		}
		if (requestedPath.startsWith("/pages/pesquisaSolicitacoesServicos/pesquisaSolicitacoesServicos.event") || requestedPath.startsWith("/printPDF/printPDF.jsp")) {
			return true;
		}
		if (requestedPath.startsWith("/pages/pesquisaRequisicaoLiberacao/pesquisaRequisicaoLiberacao.load") || requestedPath.startsWith("/printPDF/printPDF.jsp")) {
			return true;
		}
		if (requestedPath.startsWith("/pages/pesquisaRequisicaoMudanca/pesquisaRequisicaoMudanca.load") || requestedPath.startsWith("/printPDF/printPDF.jsp")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/pesquisaSolicitacoesServicosPortal/pesquisaSolicitacoesServicosPortal.load") || requestedPath.startsWith("/printPDF/printPDF.jsp")) {
			return true;
		}	
		
		if (requestedPath.endsWith("listagemConsultas.load")) {
			return true;
		}	
		if (requestedPath.endsWith("geraInfoPivotTable.load")) {
			return true;
		}	
		if (requestedPath.endsWith("geraTemplateReport.load")) {
			return true;
		}	
		if (requestedPath.endsWith("getFileConfig.load")) {
			return true;
		}		
		if (requestedPath.endsWith("baseConhecimentoView.load")) {
			return true;
		}
		if (requestedPath.endsWith("/agendarAtividadeProblema.load")) {
			return true;
		}
		if (requestedPath.endsWith("/suspensaoProblema.load")) {
			return true;
		}
		if (requestedPath.endsWith("/validacaoRequisicaoProblema.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadAnexosDocsGerais/refreshuploadAnexosDocsGerais.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadPlanoDeReversao/refreshuploadPlanoDeReversao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadExcluirPlanoDeReversao/uploadExcluirPlanoDeReversao.load")) {
			return true;
		}
		if (requestedPath.endsWith("agendarReuniaoRequisicaoMudanca.load")) {
			return true;
		}
		if (requestedPath.endsWith("delegacaoLiberacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("agendarAtividadeRequisicaoLiberacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("suspensaoLiberacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("agendarAtividadeRequisicaoMudanca.load")) {
			return true;
		}
		if (requestedPath.endsWith("suspensaoMudanca.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadAnexosDocsGeraisList/refreshuploadAnexosDocsGeraisList.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadAnexosdocsLegaisList/refreshuploadAnexosdocsLegaisList.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadAnexosDocsGeraisList/refreshuploadAnexosDocsGeraisList.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadAnexosdocsLegaisList/refreshuploadAnexosdocsLegaisList.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/galeriaImagens/galeriaImagens.load")) {
			return true;
		}
		if (requestedPath.endsWith("/galeriaImagens/galeriaImagens.load")) {
			return true;
		}

		if (requestedPath.endsWith("/pages/visualizacaoQuestionario/visualizacaoQuestionario.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/uploadArquivoMultimidia/uploadArquivoMultimidia.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadPlanoDeReversao/uploadPlanoDeReversao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadPlanoDeReversao/refreshuploadPlanoDeReversao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadPlanoDeReversaoLiberacao/uploadPlanoDeReversaoLiberacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadPlanoDeReversaoLiberacao/uploadPlanoDeReversaoLiberacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadPlanoDeReversaoLiberacao/uploadPlanoDeReversaoLiberacao.jsp")) {
			return true;
		}		
		if (requestedPath.endsWith("/pages/uploadExcluirPlanoDeReversaoLiberacao/uploadExcluirPlanoDeReversaoLiberacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/uploadExcluirPlanoDeReversaoLiberacao/uploadExcluirPlanoDeReversaoLiberacao.jsp")) {
			return true;
		}		
		if (requestedPath.endsWith("/pages/inventarioNew/inventarioNew.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/upload/excluirAnexo.do")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/template/template.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/alterarSenha2/alterarSenha2.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/solicitacaoServicoMultiContratosPortal2/solicitacaoServicoMultiContratosPortal2.load")) {
			return true;
		}

		/*
		 * ######################################### INICIO FILTRO DE COMPRAS
		 */

		if (requestedPath.endsWith("/requisicaoProduto/requisicaoProduto.load")) {
			return true;
		}
		if (requestedPath.endsWith("/requisicaoQuestionario/requisicaoQuestionario.load")) {
			return true;
		}
		if (requestedPath.endsWith("/validacaoRequisicaoProduto/validacaoRequisicaoProduto.load")) {
			return true;
		}
		if (requestedPath.endsWith("/autorizacaoCotacao/autorizacaoCotacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/aprovacaoCotacao/aprovacaoCotacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/itemCotacao/itemCotacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/fornecedorCotacao/fornecedorCotacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/coletaPreco/coletaPreco.load")) {
			return true;
		}
		if (requestedPath.endsWith("/inspecaoEntregaItem/inspecaoEntregaItem.load")) {
			return true;
		}
		if (requestedPath.endsWith("/acionamentoGarantia/acionamentoGarantia.load")) {
			return true;
		}
		if (requestedPath.endsWith("/resultadoCotacao/resultadoCotacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/consultaAprovacaoCotacao/consultaAprovacaoCotacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pedidoCompra/pedidoCompra.load")) {
			return true;
		}
		if (requestedPath.endsWith("/entregaPedido/entregaPedido.load")) {
			return true;
		}
		if (requestedPath.endsWith("/acompRequisicaoProduto/acompRequisicaoProduto.load")) {
			return true;
		}
		if (requestedPath.endsWith("/informacaoItemConfiguracao/informacaoItemConfiguracao.load")) {
			return true;
		}
		
		/*
		 * ############################## FIM FILTRO DE COMPRAS
		 */

		/*
		 * ######################################### INICIO FILTRO DE RH
		 */
		if (requestedPath.endsWith("/triagemRequisicaoPessoal/triagemRequisicaoPessoal.load")) {
			return true;
		}
		if (requestedPath.endsWith("/solicitacaoCargo/solicitacaoCargo.load")) {
			return true;
		}
		if (requestedPath.endsWith("/requisicaoPessoal/requisicaoPessoal.load")) {
			return true;
		}
		if (requestedPath.endsWith("/entrevistaRequisicaoPessoal/entrevistaRequisicaoPessoal.load")) {
			return true;
		}
		if (requestedPath.endsWith("/analiseSolicitacaoCargo/analiseSolicitacaoCargo.load")) {
			return true;
		}
		if (requestedPath.endsWith("/analiseRequisicaoPessoal/analiseRequisicaoPessoal.load")) {
			return true;
		}
		if (requestedPath.endsWith("/curriculo/curriculo.load")) {
			return true;
		}	
		if (requestedPath.endsWith("/entrevistaCandidato/entrevistaCandidato.load")) {
			return true;
		}	
		
		if (requestedPath.endsWith("/pages/entrevistaRequisicaoPessoal/entrevistaRequisicaoPessoal.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("pages/entrevistaCandidato/entrevistaCandidato.load")) {
			return true;
		}

		if (requestedPath.endsWith("/pages/requisicaoPessoal/requisicaoPessoal.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/classificacaoRequisicaoPessoal/classificacaoRequisicaoPessoal.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/movimentacaoColaborador/movimentacaoColaborador.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("pages/triagemRequisicaoPessoal/triagemRequisicaoPessoal.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("pages/templatePesquisaCurriculo/templatePesquisaCurriculo.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/pesquisaCurriculo/pesquisaCurriculo.load")) {
			return true;
		}
		if (requestedPath.endsWith("/historicoAcaoCurriculo/historicoAcaoCurriculo.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/historicoCandidato/historicoCandidato.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/visualizarHistoricoFuncional/visualizarHistoricoFuncional.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/blackList/blackList.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/itemHistoricoFuncional/itemHistoricoFuncional.load")) {
			return true;
		}
		
		/*
		 * ######################################### INICIO FILTRO LAYOUT NOVO
		 */
		
		if (requestedPath.endsWith("/gerenciamentoServicos/gerenciamentoServicos.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/autoCompleteServico/autoCompleteServico.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/autoCompleteSolicitante/autoCompleteSolicitante.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/autoCompleteTarefaAtual/autoCompleteTarefaAtual.load")) {
			return true;
		}

		if (requestedPath.endsWith("/autoCompleteCidade/autoCompleteCidade.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/autoCompleteEmpregado/autoCompleteEmpregado.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/autoCompleteNaoEmpregado/autoCompleteNaoEmpregado.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/autoCompleteFornecedor/autoCompleteFornecedor.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/autoCompleteFormacaoAcademica/autoCompleteFormacaoAcademica.load")) {
			return true;
		}
		
		if (requestedPath.indexOf("autoComplete") > 0) {
			return true;
		}
		
		/*
		 * ############################## FIM FILTRO DE RH
		 */
		

		// if
		// (requestedPath.endsWith("/pages/relatorioAcompanhamento/relatorioAcompanhamento.load"))
		// {
		// return true;
		// }
		if (requestedPath.endsWith("/pages/relatorioQuantitativoPorServico/relatorioQuantitativoPorServico.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/relatorioQuantitativo/relatorioQuantitativo.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/relatorioServicoAprovar/relatorioServicoAprovar.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/relatorioCargaHoraria/relatorioCargaHoraria.load")) {
			return true;
		}
		// if (requestedPath.endsWith("/pages/slaAvaliacao/slaAvaliacao.load"))
		// {
		// return true;
		// }
		// if
		// (requestedPath.endsWith("/pages/planoMelhoria/planoMelhoria.load")) {
		// return true;
		// }
		// if
		// (requestedPath.endsWith("pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load"))
		// {
		// return true;
		// }
		// if
		// (requestedPath.endsWith("pages/grupoItemConfiguracao/grupoItemConfiguracao.load"))
		// {
		// return true;
		// }
		if (requestedPath.endsWith("/pages/pesquisaFaq/pesquisaFaq.load")) {
			return true;
		}
		// if
		// (requestedPath.endsWith("/pages/eventoMonitoramento/eventoMonitoramento.load"))
		// {
		// return true;
		// }
		// if (requestedPath.endsWith("/pages/localidade/localidade.load")) {
		// return true;
		// }
		// if
		// (requestedPath.endsWith("/pages/relatorioPesquisaSatisfacao/relatorioPesquisaSatisfacao.load"))
		// {
		// return true;
		// }
		// if
		// (requestedPath.endsWith("pages/relatorioValorServicoContrato/relatorioValorServicoContrato.load"))
		// {
		// return true;
		// }
		// if
		// (requestedPath.endsWith("/pages/logController/logController.load")) {
		// return true;
		// }
		// if (requestedPath.endsWith("/pages/dicionario/dicionario.load")) {
		// return true;
		// }
		// if (requestedPath.endsWith("/pages/lingua/lingua.load")) {
		// return true;
		// }
		// if (requestedPath.endsWith("gerenciamentoMudancas.load")) {
		// return true;
		// }
		// if
		// (requestedPath.endsWith("/pages/midiaSoftware/midiaSoftware.load")) {
		// return true;
		// }
		
		if (requestedPath.endsWith("/aprovacaoSolicitacaoServico.load")) {
			return true;
		}
		if (requestedPath.endsWith("/atividadesServico/atividadesServico.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/patrimonio/patrimonio.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/valorServicoContrato/valorServicoContrato.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/notificacaoServicoContrato/notificacaoServicoContrato.load")) {
			return true;
		}
		if (requestedPath.endsWith("/solicitacaoServicoMultiContratosPortal.load")) {
			return true;
		}
		if (requestedPath.endsWith("/solicitacaoServicoPortal.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadAnexosList/refreshuploadAnexosList.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/categoriaOcorrencia/categoriaOcorrencia.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/origemOcorrencia/origemOcorrencia.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/notificacao/notificacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/cargaUsuarioAd/cargaUsuarioAd.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/cargaMensagens/cargaMensagens.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/itemConfiguracaoTree/itemConfiguracaoTree.load")) {
			return true; 
		}
		if (requestedPath.endsWith("uploadAjax.load")) {
			return true;
		}
		if (requestedPath.endsWith("/uploadExcluir/uploadExcluir.load")) {
			return true;
		}
		if (requestedPath.endsWith("/uploadExcluirDocsLegais.load")) {
			return true;
		}
		if (requestedPath.endsWith("/uploadDocsLegaisExcluir/uploadDocsLegaisExcluir.load")) {
			return true;
		}
		if (requestedPath.endsWith("recuperaFromGed/recuperaFromGed.load")) {
			return true;
		}
		if (requestedPath.endsWith("/uploadExcluirDocsGerais.load")) {
			return true;
		}
		if (requestedPath.endsWith(".save") || requestedPath.endsWith(".find") || requestedPath.endsWith(".get") || requestedPath.endsWith(".restore") || requestedPath.endsWith(".event")
				|| requestedPath.endsWith(".complete")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/portal/home.html")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/start/termos.html")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/redefinirSenha/redefinirSenha.jsp")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/pesquisaErroConhecido/pesquisaErroConhecido.load")) {
			return true;
		}
		if (requestedPath.endsWith("/ansServicoContratoRelacionado/ansServicoContratoRelacionado.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/pesquisaRequisicaoMudanca/pesquisaRequisicaoMudanca.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/servicoContrato/servicoContrato.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/checklistQuestionario/checklistQuestionario.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/situacaoLiberacaoMudanca/situacaoLiberacaoMudanca.load")) {
			return true;
		}
		if (requestedPath.contains("/exportXML/")) {
			return true;
		}
		if (requestedPath.endsWith("pages/refreshuploadPlanoDeReversaoLiberacao/refreshuploadPlanoDeReversaoLiberacao.load")) {
			return true;
		}
		if (requestedPath.endsWith("pages/servicoContratoMulti/servicoContratoMulti.load")) {
			return true;
		}
		
		// ---- Página de teste de operação Rest -> Será acionada diretamente pela URL
		if (requestedPath.endsWith("/pages/testeOperacaoRest/testeOperacaoRest.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/requisicaoViagem/requisicaoViagem.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/despesaViagem/despesaViagem.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/adiantamentoViagem/adiantamentoViagem.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/autorizacaoViagem/autorizacaoViagem.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/conferenciaViagem/conferenciaViagem.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/controleFinanceiroViagem/controleFinanceiroViagem.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/prestacaoContasViagem/prestacaoContasViagem.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/itemControleFinanceiroViagem/itemControleFinanceiroViagem.load")) {
			return true;
		}
        if (requestedPath.endsWith("/pages/compraViagem/compraViagem.load")) {
            return true;
        }
	    if (requestedPath.endsWith("/pages/corrigirPrestacaoContas/corrigirPrestacaoContas.load")) {
	    	return true;
	    }
	    if (requestedPath.endsWith("/pages/chatSmart/chatSmart.load")) {
			return true;
		}
	    if (requestedPath.endsWith("/pages/chatSmartListaContatos/chatSmartListaContatos.load")) {
			return true;
		}
		if (requestedPath.endsWith("/pages/refreshuploadRequisicaoMudanca/refreshuploadRequisicaoMudanca.load")) {
			return true;
		}

		if (requestedPath.endsWith("/pages/refreshuploadRequisicaoMudanca/refreshuploadRequisicaoMudanca.get")) {
			return true;
		}

		if (requestedPath.endsWith("/pages/uploadRequisicaoMudanca/uploadRequisicaoMudanca.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/uploadExcluirRequisicaoMudanca/uploadExcluirRequisicaoMudanca.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/motivoSuspensaoAtividade/motivoSuspensaoAtividade.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/refreshuploadRequisicaoProblema/refreshuploadRequisicaoProblema.load")) {
			return true;
		}

		if (requestedPath.endsWith("/pages/refreshuploadRequisicaoProblema/refreshuploadRequisicaoProblema.get")) {
			return true;
		}

		if (requestedPath.endsWith("/pages/uploadRequisicaoProblema/uploadRequisicaoProblema.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/uploadExcluirRequisicaoProblema/uploadExcluirRequisicaoProblema.load")) {
			return true;
		}

		if (requestedPath.endsWith("/pages/informacoesItemConfiguracaoMudanca/informacoesItemConfiguracaoMudanca.load")) {
			return true;
		}

		if (requestedPath.endsWith("/pages/origemAtendimento/origemAtendimento.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/cadastroConexaoBI/cadastroConexaoBI.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/agendamentoExecucaoBI/agendamentoExecucaoBI.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/importManualBI/importManualBI.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/logImportacaoBI/logImportacaoBI.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/importarDados/importarDados.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/externalConnection/externalConnection.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("getExportBI.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("getLogImportacaoBI.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("suspensaoReativacaoSolicitacao/suspensaoReativacaoSolicitacao.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao.load")) {
			return true;
		}
				
		if (requestedPath.startsWith("/dwr") || requestedPath.endsWith(".dwr")) {
            return true;
		}
		
		if (requestedPath.endsWith("/pages/relatorioIncidentesNaoResolvidos/relatorioIncidentesNaoResolvidos.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/relatorioEficaciaNasEstimativasDasRequisicaoDeServico/relatorioEficaciaNasEstimativasDasRequisicaoDeServico.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/relatorioKpiProdutividade/relatorioKpiProdutividade.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/relatorioEficaciaTeste/relatorioEficaciaTeste.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo/relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/requisicaoFuncao/requisicaoFuncao.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("listagemConsultas.load")) {
			return true;
		}		
		if (requestedPath.endsWith("geraInfoPivotTable.load")) {
			return true;
		}	
		if (requestedPath.endsWith("geraTemplateReport.load")) {
			return true;
		}
		if (requestedPath.endsWith("geraDataTable.load")) {
			return true;
		}
		if (requestedPath.endsWith("geraGraficoPizzaJS.load")) {
			return true;
		}
		if (requestedPath.endsWith("geraGraficoBarraJS.load")) {
			return true;
		}
		if (requestedPath.endsWith("geraJSP.load")) {
			return true;
		}
		if (requestedPath.endsWith("geraURL.load")) {
			return true;
		}
		if (requestedPath.endsWith("geraXML.load")) {
			return true;
		}
		if (requestedPath.endsWith("geraScript.load")) {
			return true;
		}
		if (requestedPath.endsWith("geraRetornoClasse.load")) {
			return true;
		}
		if (requestedPath.endsWith("listagemConsultasObjects.load")) {
			return true;
		}
		if (requestedPath.endsWith("listagemDashBoardsObjects.load")) {
			return true;
		}
		if (requestedPath.endsWith("topReportControl.jsp")) {
			return true;
		}
		if (requestedPath.endsWith("topDashboardBuilder.jsp")) {
			return true;
		}
		if (requestedPath.endsWith("topDashboarView.jsp")) {
			return true;
		}		
		if (requestedPath.endsWith("dashBoardBuilderInternal.load")) {
			return true;
		}
		if (requestedPath.endsWith("dashBoardViewInternal.load")) {
			return true;
		}
		if (requestedPath.endsWith("listagemDashBoardsObjects.load")) {
			return true;
		}
		if (requestedPath.endsWith("listagemDashBoards.load")) {
			return true;
		}		
		if (requestedPath.contains("/jspEmbedded/")) {
			return true;
		}		        


		/* Inicio dos autocompletes mapeados que não estavam no filtro de segurança. */
		
		if (requestedPath.endsWith("/pages/autoCompleteServicoBI/autoCompleteServicoBI.load")) 
			return true;
		
		if (requestedPath.endsWith("/autoCompleteServicoCorporeBI/autoCompleteServicoCorporeBI.load"))
			return true;
		
		if (requestedPath.endsWith("/pages/loginCandidato/loginCandidato.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/recuperaSenhaCandidato/recuperaSenhaCandidato.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/trabalheConosco/trabalheConosco.load") || requestedPath.startsWith("/pages/trabalheConosco/trabalheConosco.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/candidatoTrabalheConosco/candidatoTrabalheConosco.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/templateCurriculoTrabalheConosco/templateCurriculoTrabalheConosco.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/visualizarCurriculoTrabalheConosco/visualizarCurriculoTrabalheConosco.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/pages/uploadFile/uploadFile.load")) {
			return true;
		}
		
		if (requestedPath.endsWith("/autoCompleteDescricaoAtribuicaoResponsabilidade/autoCompleteDescricaoAtribuicaoResponsabilidade.load"))
			return true;
		if (requestedPath.endsWith("/autoCompleteCausa/autoCompleteCausa.load"))
			return true;
		if (requestedPath.endsWith("/autoCompleteAtitudeIndividual/autoCompleteAtitudeIndividual.load"))
			return true;
		if (requestedPath.endsWith("/autoCompleteCurso/autoCompleteCurso.load"))
			return true;
		if (requestedPath.endsWith("/autoCompleteCertificacao/autoCompleteCertificacao.load"))
			return true;
		if (requestedPath.endsWith("/autoCompleteCompetenciaTecnica/autoCompleteCompetenciaTecnica.load"))
			return true;
		
		if (requestedPath.endsWith("/autoCompleteFuncao/autoCompleteFuncao.load"))
			return true;
		if (requestedPath.endsWith("/autoCompleteIdioma/autoCompleteIdioma.load"))
			return true;
		if (requestedPath.endsWith("autoCompleteExperiencia/autoCompleteExperiencia.load"))
			return true;
		if (requestedPath.endsWith("/autoCompletePerspectivaComportamental/autoCompletePerspectivaComportamental.load"))
			return true;
		
		
		if (requestedPath.endsWith("/dropzone/"))
			return true;
		
		/* Fim dos autocompletes mapeados que não estavam no filtro de segurança. */
		
		
		
		if (colLivres != null) {
			if (requestedPath.startsWith("/")) {
				requestedPath = requestedPath.substring(1);
			}
			for (Iterator it = colLivres.iterator(); it.hasNext();) {
				String req = (String) it.next();
				if (req.equalsIgnoreCase(requestedPath)) {
					return true;
				}
			}
		}
		return false;
	}

	private void sendError(int errorCode, String errorMessage, HttpServletResponse response) {
		try {
			response.sendError(errorCode, errorMessage);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
