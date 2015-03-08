<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%
	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
		}
	}
</script>

<%//se for chamado por iframe deixa apenas a parte de cadastro da página
if (iframe != null) {%>
<style>
	div#main_container {
		margin: 10px 10px 10px 10px;
		width: 100%;
	}
</style>
<%}%>
<style type="text/css">
.tableLess {
	  font-family: arial, helvetica !important;
	  font-size: 10pt !important;
	  cursor: default !important;
	  margin: 0 !important;
	  background: white !important;
	  border-spacing: 0  !important;
	  width: 100%  !important;
	  overflow: hidden;
	  margin: 10px 0 0 10px ;
	}

	.tableLess tbody {
	  background: transparent  !important;
	}

	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}

	.tableLess tr th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}

	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}

	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9 ;
	  /* cursor: pointer; */
	}

	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}

	.tableLess td{
		border: 1px solid #BBB  !important;
		padding: 6px 10px  !important;
		max-width: 250px;
		padding: 6px 10px !important;
		text-overflow: ellipsis;
	}
</style>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>