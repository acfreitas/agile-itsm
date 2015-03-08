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
	String nomeTarefa = UtilStrings.nullToVazio((String)request.getAttribute("nomeTarefa"));
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
					<h3><fmt:message key="solicitacaoServico.solicitacao"/>&nbsp;N&deg; &nbsp;<%=idSolicitacaoServico%>&nbsp;-&nbsp;<fmt:message key="tarefa.tarefa"/>&nbsp;<%=nomeTarefa%></h3>
					<div class="box-generic">
						<form class="form-horizontal" name='form' id='form' action='${ctx}/pages/mudarSLA/mudarSLA'>
						<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/>
						<input type='hidden' name='idTarefa'/>
							<div class="row-fluid">
								<div class="span5">
									<label  class="strong"><fmt:message key="gerenciaservico.mudarsla.tiposla"/></label>
									  	<select placeholder="" class="span10" id="slaACombinar" required="required"  type="text" name="slaACombinar"  onchange='mudarTipoSLA(this)'></select>
								</div>
							</div>
							<div class="row-fluid" id="tempo">
								<div class="span7">
									<label  class="strong"><fmt:message key="gerenciaservico.mudarsla.tempo"/></label>
									  	<input type='text' class='span4' id='prazoHH' name='prazoHH' onkeypress='return somenteNumero(event)' size='3' maxlength="3" />
									  	<input type='text' class='span4' ID='prazoMM' name='prazoMM' onkeypress='return somenteNumero(event)' size='3' maxlength="2" />
								</div>
							<!-- </div>
							<div class="row-fluid" > -->
								<div class="span5" id="calendario">
									<label  class="strong"><fmt:message key="gerenciaservico.mudarsla.calendario"/></label>
										<select id="idCalendario" name="idCalendario"></select>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span5">
									<label  class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.justificativa"/></label>
									  	<select placeholder="" class="span10" id="idJustificativa" required="required"  type="text" name="idJustificativa"></select>
								</div>
							</div>
							<div class="control-group row-fluid">
							  	<div class="span12">
									<label  class="strong"><fmt:message key="gerenciaservico.mudarsla.complementojustificativa" /></label>
									<textarea id="mustHaveId" class=" span12" rows="5" name="complementoJustificativa" id="complementoJustificativa"></textarea>
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

				<script src="js/mudarSLA.js"></script>

			</div>
		</div>
	</body>
</html>
</compress:html>
