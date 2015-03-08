<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.citframework.util.UtilFormatacao"%>

<!doctype html>
<html>
<head>
	<%
		Collection listaServicos = (Collection) request.getAttribute("listaServicos");

		//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
		String iframe = "";
		iframe = request.getParameter("iframe");

	%>
	<%@include file="/include/header.jsp"%>

	<%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<link rel="stylesheet" type="text/css" href="./css/avaliacaoPorFornecedor.css" />

	<script type="text/javascript" src="./js/avaliacaoPorFornecedor.js"></script>
	<%
	//se for chamado por iframe deixa apenas a parte de cadastro da página
	if(iframe != null){%>
		<link rel="stylesheet" type="text/css" href="./css/avaliacaoPorFornecedorIFrame.css">
	<%}%>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%if(iframe == null){%>
			<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
		<%if(iframe == null){%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%}%>
		<form name='form' action='${ctx}/pages/avaliacaoPorFornecedor/avaliacaoPorFornecedor'>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="avaliacao.fornecedor.servico"/>
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<fieldset>
					<div class="col_30" style="width: 30% !important; float: left;">
						<label><fmt:message key="avaliacao.fornecedor.pesquisa"/></label>
						<select name="comboFornecedor" id="comboFornecedor"></select>
					</div>
					<div class="col_30" style="width: 30%; float: left; margin-top: 20px;">
						<button type='button' name='btnpesquisa' class="light" onclick='pesquisa();'>
							<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
							<span><fmt:message key="citcorpore.comum.pesquisa" /></span>
						</button>
					</div>
				</fieldset>
				<div id="tableGrafico"></div>
				<div id="tableResult"></div>
			</div>
		</form>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>

