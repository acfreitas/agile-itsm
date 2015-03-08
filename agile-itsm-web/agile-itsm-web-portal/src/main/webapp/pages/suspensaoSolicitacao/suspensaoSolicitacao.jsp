<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

<!DOCTYPE html>
<compress:html
	enabled="${param.compress != 'false'}"
	jsCompressor="closure"
	compressCss="true"
	compressJavaScript="true"
	removeComments="true"
	removeMultiSpaces="true">
<html>
	<head>
	<%
	String iframe = request.getParameter("iframe");

	String idSolicitacaoServico = UtilStrings.nullToVazio((String)request.getParameter("idSolicitacaoServico"));

	%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
		 <style type="text/css">
		 	.campoObrigatorio:after {
				color: #FF0000;
				content: "*";
			}

		 </style>
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
					<h3><fmt:message key="solicitacaoServico.solicitacao"/>&nbsp;Nº&nbsp;<%=idSolicitacaoServico%></h3>
					<div class="box-generic">
						<form class="form-horizontal" name='form' id='form' action='${ctx}/pages/suspensaoSolicitacao/suspensaoSolicitacao'>
							<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/>
							<div class="row-fluid">
								<div class="span12">
									<label  class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.justificativa"/></label>
									  	<select placeholder="" class="span10" id="idJustificativa" required="required"  type="text" name="idJustificativa"></select>
								</div>
							</div>
							<div class="control-group row-fluid">
								<div class="span12">
									<label  class="strong"><fmt:message key="gerenciaservico.mudarsla.complementojustificativa"/></label>
									  	<textarea placeholder="" rows="5" class="span10" id="complementoJustificativa" required="required"  type="text" name="complementoJustificativa"></textarea>
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

			</div>
		</div>
		
		<script src="js/suspensaoSolicitacao.js"></script>
	</body>
</html>
</compress:html>
