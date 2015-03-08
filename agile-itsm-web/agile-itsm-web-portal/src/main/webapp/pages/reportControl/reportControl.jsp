<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="./js/reportControl.js"></script>
		<%@include file="/include/header.jsp"%>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<link rel="stylesheet" type="text/css" href="./css/reportControl.css" />

	<link rel="stylesheet" type="text/css" href="${ctx}/pages/geraInfoPivotTable/pivot.css">
	<script type="text/javascript" src="${ctx}/pages/geraInfoPivotTable/jquery-1.8.3.min.js"></script>

	<script type="text/javascript" src="${ctx}/js/jquery.printElement.js"></script>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/fonts.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/text.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/fonts.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css"/>
	<link rel="stylesheet" type="text/css"href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>

	<script type="text/javascript" src="${ctx}/pages/geraInfoPivotTable/jquery-ui-1.9.2.custom.min.js"></script>

	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.position.js"></script>

	<script type="text/javascript" src="${ctx}/pages/geraInfoPivotTable/pivot.js"></script>
</head>
	<frameset rows="5%,*">
	<frame src="${ctx}/pages/reportControl/topReportControl.jsp" name="top" scrolling="no">
	<frameset cols="15%,*">
	<frame NAME="esquerda" src="${ctx}/pages/listagemConsultas/listagemConsultas.load" borderCOLOR="#3F85B8" target="main">
	<frame NAME="direita" id="direita" src="about:blank" borderCOLOR="#4086C6" target="direita">
	</frameset>
		<noframes>
		<body>
		</body>
		</noframes>
	</frameset>
</html>


