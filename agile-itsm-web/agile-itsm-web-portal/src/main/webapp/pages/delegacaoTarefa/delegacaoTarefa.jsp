<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<html>
	<head>
	<%
	String iframe = "";
	iframe = request.getParameter("iframe");

	String idSolicitacaoServico = UtilStrings.nullToVazio((String)request.getParameter("idSolicitacaoServico"));
	String nomeTarefa = UtilStrings.nullToVazio((String)request.getAttribute("nomeTarefa"));


	%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="css/delegacaoTarefa.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
	</head>
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
					<h3><fmt:message key="solicitacaoServico.solicitacao"/>&nbsp;Nº&nbsp;<%=idSolicitacaoServico%>&nbsp;-&nbsp;<fmt:message key="tarefa.tarefa"/>&nbsp;<%=nomeTarefa%></h3>
					<div class="box-generic">
						<form class="form-horizontal" name='form' id='form' action='${ctx}/pages/delegacaoTarefa/delegacaoTarefa'>
							<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/>
							<input type='hidden' name='idTarefa'/>
							<input type='hidden' name='acaoFluxo'/>
							<div class="row-fluid">
								<div class="span5">
									<label  class="strong"><fmt:message key="solicitacaoServico.atribuicaoGrupo"/></label>
									  	<select placeholder="" class="span10" id="idGrupoDestino" required="required"  type="text" name="idGrupoDestino"></select>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span5">
									<label  class="strong"><fmt:message key="citSmart.comum.ou"/></label>
								</div>
							</div>
							<div class="control-group row-fluid">
								<div class="span5">
									<label  class="strong"><fmt:message key="solicitacaoServico.atribuicaoUsuario"/></label>
									  	<select placeholder="" class="span10" id="idUsuarioDestino" required="required"  type="text" name="idUsuarioDestino"></select>
								</div>
							</div>
							<div style="margin: 0;" class="form-actions">
								<button class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick='gravar();'><i></i><fmt:message key="citcorpore.comum.gravar" /></button>
								<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick='document.form.clear();'><i></i><fmt:message key="citcorpore.comum.limpar" /></button>
							</div>
						</form>
					</div>
				</div>
				<!--  Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
				<script type="text/javascript" src="js/delegacaoTarefa.js"></script>
			</div>
		</div>
	</body>
</html>