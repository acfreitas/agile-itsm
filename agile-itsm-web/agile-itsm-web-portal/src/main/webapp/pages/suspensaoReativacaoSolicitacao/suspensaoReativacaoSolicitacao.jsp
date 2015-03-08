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
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String iframe = "";
	iframe = request.getParameter("iframe");

	String idSolicitacaoServico = UtilStrings.nullToVazio((String) request.getParameter("idSolicitacaoServico"));
	String nomeTarefa = UtilStrings.nullToVazio((String) request.getAttribute("nomeTarefa"));
%>
<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
<link type="text/css" rel="stylesheet"
	href="../../novoLayout/common/include/css/template.css" />
	
<link type="text/css" rel="stylesheet" href="./css/suspensaoReativacaoSolicitacao.css"/>
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
</head>



<body>
	<div class="<%=(iframe == null) ? "container-fluid fixed" : ""%>">

		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<div
			class="navbar <%=(iframe == null) ? "main" : "nomain"%> hidden-print">

			<%
				if (iframe == null) {
			%>
			<%@include file="/novoLayout/common/include/cabecalho.jsp"%>
			<%@include file="/novoLayout/common/include/menuPadrao.jsp"%>
			<%
				}
			%>

		</div>

		<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper"%>">

			<!-- Inicio conteudo -->
			<div id="content">
				<div class="box-generic">
					<div>
							<select id="idSelectTipo">
								<option value="Suspender"><fmt:message key="suspensaoReativacaoSolicitacao.SuspenderSolicitacao" /></option>
								<option value="Reativar"><fmt:message key="suspensaoReativacaoSolicitacao.ReativarSolicitacao" /></option>
							</select>
						</div>
					<form class="form-horizontal" name='form' id='form'	action='${ctx}/pages/suspensaoReativacaoSolicitacao/suspensaoReativacaoSolicitacao.load'>

						<input type="hidden" id="tipoAcao" name="tipoAcao">
						<div>
							<div class="row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><fmt:message key="suspensaoReativacaoSolicitacao.Contrato" /></label>
									<select id="idContrato" name="idContrato" class="span10 Valid[Required]" required="required">
								</select>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span12">
									<div id="solicitanteSuspensao" style="display: block"><label class="strong campoObrigatorio"><fmt:message key="suspensaoReativacaoSolicitacao.solicitanteSuspensao" /></label></div>
									<div id="solicitanteReativacao" style="display: none"><label class="strong campoObrigatorio"><fmt:message key="suspensaoReativacaoSolicitacao.solicitanteReativacao" /></label></div>
									<div class=" input-append">
										<input type="text" class="span6" name="solicitante" id="solicitante" onfocus="montaParametrosAutocompleteServico();" required="required" placeholder="" value="" disabled="disabled">
										<span class="add-on"><i class="icon-search"></i></span>
										<button type='button' class="btn btn-mini btn-primary btn-icon glyphicons search alteraTamanho"  href="#modal_lookupSolicitante" data-toggle="modal" data-target="#modal_lookupSolicitante" id='btnPesqAvancada' disabled="disabled">
																		<i></i> <fmt:message key="citcorpore.comum.pesquisaAvancada" />
										</button>
									</div>
								</div>

							</div>

							<div class="row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><fmt:message key="suspensaoReativacaoSolicitacao.Grupo" /></label>
									<select id="idGrupo" name="idGrupo" class="span10 Valid[Required]" required="required"></select>
								</div>
							</div>

						</div>

						<div id="idSuspensao">
							<div class="control-group row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.justificativa" /></label>
									<select placeholder="" class="span10 Valid[Required]" id="idJustificativa" required="required" type="text" name="idJustificativa"></select>
								</div>
								<div class="span12">
									<label  class="strong campoObrigatorio"><fmt:message key="gerenciaservico.mudarsla.complementojustificativa"/></label>
									<textarea rows="5" class="span10 Valid[Required]" required="required" type="text" name="justificativa" id="justificativa"></textarea>
								</div>
							</div>
							<br>
							<span class="btn btn-icon btn-primary" onclick="suspenderSolicitacoes();"><i></i><fmt:message key="suspensaoReativacaoSolicitacao.Suspender" /></span>
							<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick="document.form.clear();" id=""><i></i><fmt:message key="suspensaoReativacaoSolicitacao.limparDados" /></button>
						</div>

						<div id="idReativacao" style="display: none;">
						<br>
							<span class="btn btn-icon btn-primary" onclick="reativarSolicitacoes();"><i></i><fmt:message key="suspensaoReativacaoSolicitacao.Reativar" /></span>
							<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick="document.form.clear();" id=""><i></i><fmt:message key="suspensaoReativacaoSolicitacao.limparDados" /></button>
						</div>
					</form>
				</div>
			</div>

			<div class="modal hide fade in" id="modal_lookupSolicitante" aria-hidden="false">
							<!-- Modal heading -->
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
									<h3><fmt:message key="colaborador.pesquisacolaborador" /></h3>
								</div>
								<!-- // Modal heading END -->
								<!-- Modal body -->
								<div class="modal-body">
									<form name='formPesquisaColaborador' style="width: 540px">
										<cit:findField formName='formPesquisaColaborador'
										lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOLICITANTE'
										top='0' left='0' len='550' heigth='200' javascriptCode='true'
										htmlCode='true' />
									</form>
								</div>
								<!-- // Modal body END -->
								<!-- Modal footer -->
								<div class="modal-footer">
									<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
								</div>
								<!-- // Modal footer END -->
						</div>
			<!--  Fim conteudo-->
			<%@include file="/novoLayout/common/include/rodape.jsp"%>


		</div>
	</div>

	<script type="text/javascript" src="js/suspensaoReativacaoSolicitacao.js"></script>
</body>
</html>
</compress:html>
