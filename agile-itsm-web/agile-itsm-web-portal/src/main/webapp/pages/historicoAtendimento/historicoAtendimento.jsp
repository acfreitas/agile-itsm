<%@ page import="br.com.centralit.citcorpore.util.Enumerados" %>
<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>

<%
	Integer searchMaxDays = ParametroUtil.getValorParametro(Enumerados.ParametroSistema.PERIODO_MAXIMO_DIAS_LISTAGEM, "30");
	String googleApiKey = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GOOGLE_API_KEY_WEB, "undefined");
	String googleClientIDForWork = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GOOGLE_API_KEY_WEB_FOR_WORK, "undefined");
	pageContext.setAttribute("searchMaxDays", searchMaxDays);
	pageContext.setAttribute("googleApiKey", googleApiKey);
	pageContext.setAttribute("googleClientIDForWork", googleClientIDForWork);
%>

<!DOCTYPE html>
<html>
<head>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
	<%@ include file="/novoLayout/common/include/titulo.jsp" %>
	<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css" />
</head>

<cit:janelaAguarde id="waitWindow" title="<fmt:message key='citcorpore.comum.aguardeProcessando'/>"
	style="display: none; top: 325px; width: 300px; left: 500px; height: 50px; position: absolute;" />

<body>
	<div class="container-fluid fixed">
		<div class="navbar main hidden-print">
		<%@ include file="/novoLayout/common/include/cabecalho.jsp" %>
		<%@ include file="/novoLayout/common/include/menuPadrao.jsp" %>
		</div>

		<div id="wrapper">
			<div id="content">
				<div id="alerts"></div>
				<div class="row-fluid">
					<div class="widget">
						<div class="widget-head">
							<h4 class="heading"><fmt:message key="menu.gestao.forca.atendimento.historico.atendimento" /></h4>
						</div>
					</div>
					<div class="widget-body collapse in">
						<form name="form" action="${ctx}/pages/historicoAtendimento/historicoAtendimento">
							<div class="row-fluid">
								<div class="row-fluid">
									<div class="span2">
										<label class="campoObrigatorio strong"><fmt:message key="localidade.estado" />:</label>
										<select id="idUF" name="idUF" class="Valid[Required] Description[localidade.estado]" style="width: 180px;" disabled="disabled"></select>
									</div>
									<div class="span2">
										<label class="campoObrigatorio strong"><fmt:message key="localidade.cidade" />:</label>
										<select id="idCidade" name="idCidade" class="Valid[Required] Description[localidade.cidade] span12" disabled="disabled"></select>
									</div>
									<div class="span3">
										<label class="campoObrigatorio strong"><fmt:message key="contrato.contrato" />:</label>
										<select id="idContrato" name="idContrato" class="Valid[Required] Description[contrato.contrato] span12" disabled="disabled"></select>
									</div>
									<div class="span3" style="padding-right: 2%;">
										<label class="strong"><fmt:message key="unidade.unidade" />:</label>
										<div class="input-append span12" style="margin-left: 0;">
											<input id="unidade" name="unidade" type="text" class="span12" style="margin-left: 0;" disabled="disabled" />
											<span class="add-on"><i class="icon-search"></i></span>
											<input id="idUnidade" name="idUnidade" type="hidden" value="0" />
										</div>
									</div>
									<div class="span2">
										<label class="strong"><fmt:message key="citcorpore.comum.grupoExecutor" />:</label>
										<select id="idGrupo" name="idGrupo" class="span12" disabled="disabled"></select>
									</div>
								</div>
								<div class="row-fluid">
									<div class="span4" style="padding-right: 2%;">
										<div class="input-append span12">
											<label class="strong"><fmt:message key="citcorpore.comum.tecnico" />:</label>
											<input id="atendente" name="atendente" type="text" class="span12" style="margin-left: 0;" disabled="disabled" />
											<span class="add-on"><i class="icon-search"></i></span>
											<input id="idAtendente" name="idAtendente" type="hidden" value="0"></input>
										</div>
									</div>
									<div class="span2">
										<label class="campoObrigatorio strong"><fmt:message key="citcorpore.comum.datainicio" />:</label>
										<input id="dataInicio" name="dataInicio" type="text" size="10" maxlength="10" disabled="disabled" class="Valid[Required] Description[citcorpore.comum.datainicio] Format[Date] Valid[Date]" />
									</div>
									<div class="span2">
										<label class="campoObrigatorio strong"><fmt:message key="citcorpore.comum.datafim" />:</label>
										<input id="dataFim" name="dataFim" type="text" size="10" maxlength="10" disabled="disabled" class="Valid[Required] Description[citcorpore.comum.datafim] Format[Date] Valid[Date]" />
									</div>
									<div class="span2">
										<label class="strong"><fmt:message key="citcorpore.comum.situacaoSolicitacao" />:</label>
										<select id="idSituacao" name="idSituacao" class="Description[Situacao]" disabled="disabled"></select>
									</div>
									<div class="span1">
										<div class="pull-right">
											<label>&nbsp;</label>
											<button id="btnSearch" type="button" class="btn btn-primary disabled" disabled="disabled">
												<fmt:message key="citcorpore.ui.botao.rotulo.Pesquisar" />
											</button>
										</div>
									</div>
									<div class="span1">
										<div class="pull-right">
											<label>&nbsp;</label>
											<button id="btnLimparFiltro" type="button" class="btn btn-primary disabled" disabled="disabled">
												<fmt:message key="citcorpore.ui.botao.rotulo.Limpar" />&nbsp;<fmt:message key="citcorpore.comum.filtro" />
											</button>
										</div>
									</div>
								</div>
							</div>
						</form>

						<hr>

						<div class="row-fluid">
							<label class="label-box-div"><fmt:message key="menu.gestao.forca.atendimento.rotas" /></label>
							<div id="map-box-div" class="box-div">
								<div id="map-area" class="map-area"></div>
							</div>
						</div>
						<div class="row-fluid" style="padding-top: 1%;">
							<div class="span3">
								<button id="btnLimpar" type="button" class="btn btn-primary disabled" disabled="disabled">
									<fmt:message key="citcorpore.ui.botao.rotulo.Limpar" />
								</button>
							</div>
							<ul class="inline pull-right" style="padding-top: 10px;">
								<li><b><fmt:message key="gestao.forca.atendimento.status.atendimento" />:</b></li>
								<li><span class="badge badge-success"><fmt:message key="gestao.forca.atendimento.status.atendimento.atendido.finalizado" /></span></li>
								<li><span class="badge badge-important"><fmt:message key="gestao.forca.atendimento.status.atendimento.nao.atendido" /></span></li>
								<li><span class="badge badge-warning"><fmt:message key="gestao.forca.atendimento.status.atendimento.atendendo" /></span></li>
								<li><span class="badge"><fmt:message key="gestao.forca.atendimento.status.atendimento.pendencia" /></span></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<%@include file="/novoLayout/common/include/rodape.jsp" %>
		</div>
	</div>

	<link rel="stylesheet" type="text/css" href="${ctx}/css/custom-maps.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/novoLayout/common/include/css/jqueryautocomplete.css" />

	<c:choose>
		<c:when test="${not empty googleClientIDForWork and 'undefined' ne googleClientIDForWork}">
			<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?client=${googleClientIDForWork}&v=3.17"></script>
		</c:when>
		<c:when test="${not empty googleApiKey and 'undefined' ne googleApiKey}">
			<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=${googleApiKey}"></script>
		</c:when>
		<c:otherwise>
			<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
		</c:otherwise>
	</c:choose>

	<script type="text/javascript">
		var properties = { key : "${googleApiKey}", clientID: "${googleClientIDForWork}", searchMaxDays : ${searchMaxDays - 1}, context : "${ctx}" };
	</script>

	<script type="text/javascript" src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="${ctx}/js/alert-override.js"></script>
	<script type="text/javascript" src="${ctx}/js/DateTimeUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/NumberUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/StringUtils.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/geo-utils.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/geo-utils-jquery.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/geolocation.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/google-maps-v3.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/oms.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/gestaoForcaAtendimento.js"></script>
	<script type="text/javascript" src="js/historicoAtendimento.js"></script>
</body>
</html>
