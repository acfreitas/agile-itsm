<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

<%
	String iframe = request.getParameter("iframe");
%>

<!DOCTYPE html>
<compress:html
	enabled="true"
	jsCompressor="closure"
	compressCss="true"
	compressJavaScript="true"
	removeComments="true"
	removeMultiSpaces="true">
<html>
<head>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
	<%@include file="/novoLayout/common/include/titulo.jsp" %>
	<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/pages/index/css/index.css"/>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">
		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">

			<% if(iframe == null) { %>
				<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
				<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
			<% } %>

		</div>

		<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">
			<!-- Inicio conteudo -->
			<div id="content">
				<mr:menuRapido/>
			</div>
			<!--  Fim conteudo-->
			<%@include file="/novoLayout/common/include/rodape.jsp" %>
		</div>
	</div>
</body>
</html>
</compress:html>
