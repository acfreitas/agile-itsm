<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!doctype html public "">
<html>
	<head>	
	<%	String iframe = "";
	iframe = request.getParameter("iframe"); %>
		<%@page import="br.com.citframework.util.UtilStrings"%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/templateCurriculo.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css" />
		
		
		    <script>var URL_INITIAL = '<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/';	
					</script>
	</head>
	<body>
		<div class="container-fluid fixed ">
			
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
					<%@include file="/novoLayout/common/include/templateCurriculoCentro.jsp" %>
				</div>
				<!--  Fim conteudo-->

				<%@include file="/novoLayout/common/include/rodape.jsp" %>
				<%@include file="/novoLayout/common/include/templateCurriculoCentroScripts.jsp" %>
			</div>
		</div>
	</body>
