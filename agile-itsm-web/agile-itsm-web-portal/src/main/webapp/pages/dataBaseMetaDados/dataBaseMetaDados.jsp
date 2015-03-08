<%@page import="br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.BotaoAcaoVisaoDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.util.MetaUtil"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/security/security.jsp"%>

<title>CIT Corpore</title>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/DataTables/css/demo_table.css">

<link rel="stylesheet" type="text/css" href="./css/dataBaseMetaDados.css" />

<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/lookup/jquery.ui.lookup.js"></script>
<script type="text/javascript" language="javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/DataTables/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/layout/jquery.layout-latest.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/formparams/jquery.formparams.js"></script>

<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script type="text/javascript" src="./js/dataBaseMetaDados.js"></script>
</head>

<body>

<div id='menu'>
	<%
	request.setAttribute("menustyle", "SHORT");
	%>
	<%@include file="/include/menu_vertical.jsp"%>
</div>

<div class="ui-layout-north"></div>

<div class="ui-layout-east">
	<button type='button' name='btnGravar' class="light"
		onclick='carregar();'><img
		src="${ctx}/template_new/images/icons/small/grey/pencil.png">
	<span><fmt:message key="citcorpore.comum.carregar"/></span></button>
	<button type='button' name='btnLimpar' class="light"
		onclick='limpar();'><img
		src="${ctx}/template_new/images/icons/small/grey/clear.png">
	<span><fmt:message key="dinamicview.limpardados"/></span></button>
	<button type="button" class="light img_icon has_text " style='text-align: right;' onclick="voltar()" title="<fmt:message key="citcorpore.comum.voltar" />">
		<img border="0" title="<fmt:message key="citcorpore.comum.voltar" />" src="${ctx}/imagens/back.png" /><span ><fmt:message key="citcorpore.comum.voltar" /></span>
	</button>
</div>

<div class="ui-layout-west"></div>

<div class="ui-layout-center">
	<!-- Conteudo do Centro -->
	<div id='tabs' class="box grid_16 tabs" style='width: 97%'>
		<ul class="tab_header clearfix">
			<li>
				<a href="#tabs-1">
					<fmt:message key="carregar.meta_dados"/>
				</a>
			</li>
		</ul>
		<a href="#" class="toggle">&nbsp;</a>
		<div class="toggle_container">
			<div id="tabs-1" class="block">
				<div class="section">

					<!-- INICIO DO FORM PRINCIPAL -->
					<form name='form' id='form' onsubmit='javascript:return false;' action='${ctx}/pages/dataBaseMetaDados/dataBaseMetaDados'>
						<label><fmt:message key="carregar.meta_dados.nomeTabela"/></label>
						<input type='text' name='nomeTabela' size='40' maxlength="40"/><br>
						<div style="padding-top: 10px;"><label style="padding-top: 10px;"><input type='checkbox' name='carregarTodos' id='carregarTodos' onchange="validaCheck();"/><fmt:message key="carregar.meta_dados.todos"/><br></label></div>
						<div id='divRetorno'></div>
					</form>
					<!-- FIM DO FORM PRINCIPAL -->

				</div>
			</div>
		</div>
	</div>
	<!-- Fim Conteudo do Centro -->
</div>

<div class="ui-layout-south"></div>

<!-- Fim da Pagina de Conteudo -->

</body>
</html>


