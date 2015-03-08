<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
	<head>	
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");
			String URL_SISTEMA = "";
			URL_SISTEMA = CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+'/';
		%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/pages/modalCurriculo/css/modalCurriculo.css"/>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
		<script src="${ctx}/novoLayout/common/include/js/templatePesquisaCurriculo.js"></script>
		<script type="text/javascript" src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
    	<script type="text/javascript" src="${ctx}/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>
    	<script type="text/javascript" src="${ctx}/cit/objects/CurriculoDTO.js"></script>
	</head>
	<body>
		<div>
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<%if (iframe == null) {%>
				<div class="navbar main hidden-print">
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				</div>
			<%}%>
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<div id="content">
					<form name='formModalCurriculo' action='${ctx}/pages/modalCurriculo/modalCurriculo'>
					<input type="hidden" id='idCurriculo' name='idCurriculo'>
					<input type="hidden" id='tipo' name='tipo'>
						<div class="row-fluid">
						<!-- 1ª Parte - Dados Pessoais -->
							<div class="row-fluid">
								<div id="foto">
									<div id="divFoto"></div>
								</div>
								
								<div id="divDadosPessoaisContainer">
									<div id="divDadosPessoais"></div>
								</div>
							</div>
							<!-- 2ª Parte - Historico Profissional -->
							<div class="row-fluid">
								<div class="span12">
									<div id="divHistoricoProfissional"></div>
								</div>
							</div> 
							<!-- 3ª Parte - Formação Academica -->
							<div class="row-fluid">
								<div class="span12">
									<div id="divFormacaoAcademica"></div>
								</div>
							</div> 
							<!-- 4ª Parte - Idiomas -->
							<div class="row-fluid">
								<div class="span12">
									<div id="divIdiomas"></div>
								</div>
							</div> 
							<!-- 5ª Parte - Certificações -->
							<div class="row-fluid">
								<div class="span12">
									<div id="divCertificacao"></div>
								</div>
							</div> 
							<!-- 6ª Parte - Competencia -->
							<div class="row-fluid">
								<div class="span12">
									<div id="divCompetencia"></div>
								</div>
							</div> 
							<!-- 7ª Parte - Anexos -->
							<div class="row-fluid">
								<div class="span12">
									<div id="divAnexos"></div>
								</div>
							</div> 
							
							<% if(request.getAttribute("ultimaAtualizacao") != null) { %>
							<div class="row-fluid row-ultima-atualizacao">
								<div class="span12">
									<strong><fmt:message key="ocorrenciaProblema.dataHoraUltimaAtualizacao" /></strong> <%=request.getAttribute("ultimaAtualizacao")%>
								</div>
							</div>
							<% } %>
						</div> 
					</form>
				</div>
			</div>
				<!--  Fim conteudo-->
<%-- 				<%@include file="/novoLayout/common/include/rodape.jsp" %> --%>
				<script src="${ctx}/pages/modalCurriculo/js/modalCurriculo.js"></script>
			</div>
	</body>
</html>