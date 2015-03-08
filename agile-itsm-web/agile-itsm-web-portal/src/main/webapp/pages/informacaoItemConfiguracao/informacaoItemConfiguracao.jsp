<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

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
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<%
request.setCharacterEncoding("UTF-8");
response.setHeader("Content-Language","lang");
%>
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/informacao.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css">
<script type="text/javascript" src="${ctx}/template_new/js/jqueryTreeview/jquery.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jqueryTreeview/jquery.cookie.js"></script>

</head>
	<body id="bodyInf">
		<form name='form' action='${ctx}/pages/informacaoItemConfiguracao/informacaoItemConfiguracao'>
			<input type='hidden' name='idItemConfiguracao'/>
			<div id="principalInf" style="width: 95%;">
			</div>
		</form>
		<div id="loading_overlay">
			<div class="loading_message round_bottom">
				<img src="${ctx}/template_new/images/loading.gif" alt="aguarde..." />
			</div>
	    </div>
	</body>
	<script type="text/javascript" src="./js/informacaoItemConfiguracao.js"></script>
</html>
</compress:html>
