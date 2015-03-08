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
		%>

		<style title="" type="text/css">
		.campoObrigatorio:after {
			color: #FF0000;
			content: "*";
		}
		</style>

		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
		<%@include file="/novoLayout/common/include/rodape.jsp" %>

		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css">

		<script src="${ctx}/template_new/js/validation/jquery.validate.min.js" type="text/javascript"></script>
		<script src="${ctx}/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
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
				<h3><fmt:message key="gerenciaservico.agendaratividade" /></h3>
				<div class="box-generic">

						<!-- Tabs Heading -->
						<div class="tabsbar">
							<ul>
								<li class="active"><a href="#tab1" data-toggle="tab"><fmt:message key='gerenciaservico.agendaratividade.historicoagendamentos' /></a></li>
								<li  class=""><a href="#tab2" data-toggle="tab"><fmt:message key='gerenciaservico.agendaratividade.criaragendamento' /></a></li>
							</ul>
						</div>
						<!-- // Tabs Heading END -->
						<form name='form' id='form' action='${ctx}/pages/agendarAtividade/agendarAtividade'>
							<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/>
							<input type='hidden' name='idTarefa'/>
								<div class="tab-content">

									<!-- Tab content -->
									<div class="tab-pane active" id="tab1">
										<div id='divAgendamentos' ></div>
									</div>
									<div class="tab-pane" id="tab2">
										<div class='row-fluid'>
											<label class="strong campoObrigatorio"><fmt:message key="gerenciaservico.agendaratividade.crupoatividades" /></label>
											<select  class=" span12" id="idGrupoAtvPeriodica" name="idGrupoAtvPeriodica" required="required" ></select>
										</div>
										<div class="input-append">
											<label  class="strong"><fmt:message key="gerenciaservico.agendaratividade.orientacaotecnica" /></label>
										  	<div class="controls">
												<textarea class="wysihtml5 span12" rows="5" name="orientacaoTecnica" id="orientacaoTecnica"></textarea>
											</div>
										</div>
										<div class='row-fluid'>
											<div class='span6'>
												<label class="strong campoObrigatorio"><fmt:message key="gerenciaservico.agendaratividade.agendarpara" /></label>
												<input type="text" class=" span5 datepicker" id="dataInicio" name="dataInicio" maxlength="10" required="required" >
												&nbsp;<fmt:message key='citcorpore.comum.as' />&nbsp;
												<input type="text" class=" span5 " id="horaInicio" name="horaInicio"  required="required" >
											</div>
										</div>
										<div class="separator"></div>
										<div class='row-fluid'>
											<label class="strong campoObrigatorio"><fmt:message key='gerenciaservico.agendaratividade.duracaoestimada' /></label>
											<input type="text" class=" span1" id="duracaoEstimada" maxlength="8" name="duracaoEstimada" onkeypress='return somenteNumero(event)' required="required" >&nbsp;<fmt:message key='gerenciaservico.agendaratividade.minuto' />
										</div>
										<div style="margin: 0;" class="form-actions">
												<button class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick='gravar();'><i></i><fmt:message key="citcorpore.comum.gravar" /></button>
												<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick='limpar();'><i></i><fmt:message key="citcorpore.comum.limpar" /></button>
											</div>


									</div>
									<!-- // Tab content END -->


								</div>
						</form>
				</div>

				</div>
				<!--  Fim conteudo-->

			</div>
		</div>

		<script type="text/javascript" src="js/agendarAtividade.js"></script>
	</body>
</html>
</compress:html>
