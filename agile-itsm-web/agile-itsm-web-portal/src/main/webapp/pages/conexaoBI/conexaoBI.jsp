<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA"%>
<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.free.Free"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String id = request.getParameter("id");
	String strRegistrosExecucao = (String) request
			.getAttribute("strRegistrosExecucao");
	if (strRegistrosExecucao == null) {
		strRegistrosExecucao = "";
	}
	
	String iframe = "";
	iframe = request.getParameter("iframe"); 
	
	String nomeUsuario = "";
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario != null)
		nomeUsuario = usuario.getNomeUsuario();
	
	String URL_SISTEMA = "";
	URL_SISTEMA = CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+'/';
%>

<!DOCTYPE HTML>
<html>
	<head>	
		<meta name="viewport" content="width=device-width" />
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<title><fmt:message key="citcorpore.comum.title" /></title>
		<link type="text/css" rel="stylesheet" href="css/conexaoBI.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">	</cit:janelaAguarde>
	<body >
		<div class="container-fluid fixed ">
			<!--  Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 15:54 - ID Citsmart: 120948 - 
			* Motivo/Comentário: Verificação para abrir com iframe -->
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<% if(iframe == null) { %>
			<div class="navbar main hidden-print">		
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>		
			</div>
			<% } %>
				<div id="wrapper">					
					<!-- Inicio conteudo -->
					<div id="content">				
						<div class="separator top"></div>	
						<div class="row-fluid">
							<div class="innerLR">
								<div class="widget">
									<div class="widget-head">
										<h4 class="heading"><fmt:message key="bi.painelControle.listagemClientes.titulo"/></h4>
									</div>
									<div class="widget-body collapse in">
										<div class="tab-content">
											<div class="tab-pane active" id="tab1-3">
												<form id='formGerenciamento'  name='formGerenciamento' method='post' action="${ctx}/pages/conexaoBI_Impl/conexaoBI_Impl">
													<cit:gerenciamentoField classeExecutora="br.com.centralit.citcorpore.ajaxForms.ConexaoBI_Impl" paginacao="true" tipoLista="1"></cit:gerenciamentoField>													
												</form>												
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->		
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
   				<script type="text/javascript" src="js/conexaoBI.js" ></script>
				<script src="${ctx}/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
			</div>	
			<!-- MODAL NOVA CONEXÃO -->
			<div class="modal hide fade" id="modal_novaConexaoBI" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3><fmt:message key="bi.painelControle.conexao.novoClienteBI"/></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<div class='slimScrollDiv'>
						<div class='slim-scroll' id='contentFrameNovaConexaoBI'>
							<iframe id='frameNovaConexaoBI' width="100%" height="100%"></iframe>
						</div>
					</div>
				</div>
				<!-- // Modal body END -->
			</div>	
			<!-- Modal footer -->
			<!-- MODAL EDITAR -->
			<div class="modal hide fade" id="modal_editarConexaoBI" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3><fmt:message key="bi.painelControle.conexao.editarClienteBI"/></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<div class='slimScrollDiv'>
						<div class='slim-scroll' id='contentFrameEditarConexaoBI'>
							<iframe id='frameEditarConexaoBI' width="100%" height="100%"></iframe>
						</div>
					</div>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
			</div>
			<!-- Modal footer -->
			<!-- MODAL AGENDAMENTO EXECUCAO ESPECÍFICA-->
			<div class="modal hide fade" id="modal_AgendamentoExecucaoEspecificaBI" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<div class='slimScrollDiv'>
						<div class='slim-scroll' id='contentFrameAgendamentoExecucaoEspecificaBI'>
							<iframe id='frameAgendamentoExecucaoEspecificaBI' width="100%" height="100%"></iframe>
						</div>
					</div>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
			</div>
			<!-- Modal footer -->
			<!-- MODAL AGENDAMENTO EXECUCAO EXCECAO-->
			<div class="modal hide fade" id="modal_AgendamentoExecucaoExcecaoBI" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<div class='slimScrollDiv'>
						<div class='slim-scroll' id='contentFrameAgendamentoExecucaoExcecaoBI'>
							<iframe id='frameAgendamentoExecucaoExcecaoBI' width="100%" height="100%"></iframe>
						</div>
					</div>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
			</div>
			
			<!-- MODAL IMPORTACAO MANUAL-->
			<div class="modal hide fade" id="modal_ImportacaoManualBI" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3><fmt:message key="importManualBI.titulo"/></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<iframe id='frameImportacaoManualBI' width="100%" height="100%" style="height:400px;"></iframe>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
			</div>
			
					<!-- MODAL LOG-->
			<div class="modal hide fade" id="modal_LogImportacaoBI" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3><fmt:message key="bi.painelControle.conexao.logExecucao" /></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<div class='slimScrollDiv'>
						<div class='slim-scroll' id='contentFrameLogImportacaoBI'>
							<iframe id='frameLogImportacaoBI' width="100%" height="100%" style="height:500px;"></iframe>
						</div>
					</div>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
			</div>

	</body>
</html>