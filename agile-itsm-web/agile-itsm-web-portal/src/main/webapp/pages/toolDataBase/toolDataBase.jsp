<%@page import="java.util.HashMap"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%
	String sgbd = "";
	String banco = "";
	String schema = "";
	HashMap<Integer, String> dadosSGBD = new HashMap<Integer, String>();
	dadosSGBD = (HashMap)request.getSession().getAttribute("dadosSGBD");
	if(dadosSGBD != null){
		sgbd = dadosSGBD.get(1);
		banco = dadosSGBD.get(2);
		schema = dadosSGBD.get(3);
	}

%>
<!doctype html public "">
<html>
	<head>
	<%
		String iframe = "";
		iframe = request.getParameter("iframe");
	%>
	<%@include file="/include/header.jsp"%>

	<%@include file="/include/security/security.jsp"%>
	<title><fmt:message key="citcorpore.comum.title" /></title>
	<%@include file="/include/menu/menuConfig.jsp"%>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/pages/toolDataBase/toolDataBase.css" />
	<script type="text/javascript" src="./js/toolDataBase.js"></script>
	<%//se for chamado por iframe deixa apenas a parte de cadastro da página
	if (iframe != null) {%>
		<link rel="stylesheet" type="text/css" href="./css/toolDataBase.css" />
	<%}%>
	</head>
	<body >
		<div id="wrapper" >
			<%if (iframe == null) {%>
			<%@include file="/include/menu_vertical.jsp"%>
			<%}%>

			<!-- Conteudo -->
			<div id="main_container" class="main_container" >
				<%if (iframe == null) {%>
				<%@include file="/include/menu_horizontal.jsp"%>
				<%}%>
				<form name='form' action='${ctx}/pages/toolDataBase/toolDataBase'>
					<input type="hidden" name="tabela" id="tabela">
					<input type="hidden" name="tipoAcao" id="tipoAcao">
					<div id="corpoPrincipal" >
						<label class="infoBanco"><b><fmt:message key="tooldatabase.sgbd"/>: <%=sgbd%></b></label>
						<label class="infoBanco"><b><fmt:message key="tooldatabase.banco"/>: <%=banco%></b></label>
						<label class="infoBanco"><b><fmt:message key="tooldatabase.url"/>: <%=schema%></b></label>
						<div id="estTabelas"  >

						</div>
						<div id="corpoExec" >
							<div id="inputSQL" >
								<label><b><fmt:message key="tooldatabase.consoleScriptsSQL"/></b></label>
								<textarea id="strExec" name="strExec" >

								</textarea>
								<br />
								<label><input type="button" name="executar" id="executar" onclick="executaScript()" value="<fmt:message key="tooldatabase.Executar"/>" /></label>
								<!-- <label><input type="button" name="commit" id="commit" value="COMMIT" /></label> -->
							</div>
							<div id="outputSQL" >
								<label><b><fmt:message key="tooldatabase.resultadosScriptSQL"/></b></label><label><b><fmt:message key="tooldatabase.quantRows"/>: <input type="text" id="quantRows" name="quantRows" value="1000" /> </b></label>
							</div>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
					<div id="POPUP_ACAO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
						<div class="box grid_16 tabs" >
							<div class="toggle_container" >
								<br />
								<a class="linkAcao" href="#" onclick="acaoDrop()" ><fmt:message key="tooldatabase.deletarTabela"/></a>
								<br />
								<a class="linkAcao" href="#" onclick="acaoTabela('addColumn');" ><fmt:message key="tooldatabase.adicionarCampos"/></a>
								<br />
								<a class="linkAcao" href="#" onclick="acaoTabela('list');" ><fmt:message key="tooldatabase.listarCampos"/></a>
								<br />
								<a class="linkAcao" href="#" onclick="acaoTabela('insert');" ><fmt:message key="tooldatabase.inserirDados"/></a>
								<br />
								<a class="linkAcao" href="#" onclick="acaoTabela('update');" ><fmt:message key="tooldatabase.atualizarDados"/></a>
								<br />
								<a class="linkAcao" href="#" onclick="acaoTabela('del');" ><fmt:message key="tooldatabase.deletarDados"/></a>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->

		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>

