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
							<h4 class="heading"><fmt:message key="menu.gestao.forca.atendimento.rotas.configuracao" /></h4>
						</div>
					</div>
					<div class="widget-body collapse in">
						<form name="form" action="${ctx}/pages/gerenciamentoRotas/gerenciamentoRotas">
							<input type="hidden" id="page" name="page" />
							<input type="hidden" id="size" name="size" />
							<div class="row-fluid">
								<div class="span6" style="padding-right: 2%;">
									<div class="input-append span12">
										<label class="campoObrigatorio strong"><fmt:message key="citcorpore.comum.tecnico" />:</label>
										<input type="text" name="usuario" id="usuario" class="Valid[Required] Description[citcorpore.comum.tecnico] span12"
											style="margin-left: 0;" disabled="disabled" />
										<span class="add-on"><i class="icon-search"></i></span>
										<input id="idUsuario" name="idUsuario" type="hidden" value="0"></input>
									</div>
								</div>
								<div class="span6">
									<label class="campoObrigatorio strong"><fmt:message key="citcorpore.comum.periodo.abertura" />:</label>
									<input type="text" id="dataInicio" name="dataInicio" size="10" maxlength="10" disabled="disabled" class="Valid[Required] Description[citcorpore.comum.datainicio] Format[Date] Valid[Date]" />
									<span>&nbsp;<fmt:message key="citcorpore.texto.artigoDefinido.a" />&nbsp;</span>
									<input id="dataFim" name="dataFim" type="text" size="10" maxlength="10" disabled="disabled" class="Valid[Required] Description[citcorpore.comum.datafim] Format[Date] Valid[Date]" />
								</div>
							</div>
							<div class="row-fluid">
								<label class="label-box-div"><fmt:message key="citcorpore.comum.filtro" /></label>
								<div class="box-div">
									<div class="row-fluid">
										<div class="span2">
											<label class="campoObrigatorio strong"><fmt:message key="localidade.estado" />:</label>
											<select id="idUF" name="idUF" class="Valid[Required] Description[localidade.estado] span12" disabled="disabled"></select>
										</div>
										<div class="span4">
											<label class="campoObrigatorio strong"><fmt:message key="localidade.cidade" />:</label>
											<select id="idCidade" name="idCidade" class="Valid[Required] Description[localidade.cidade] span12" disabled="disabled"></select>
										</div>
										<div class="span6">
											<label class="strong"><fmt:message key="contrato.contrato" />:</label>
											<select id="idContrato" name="idContrato" class="span12" disabled="disabled"></select>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span4" style="padding-right: 2%;">
											<label class="strong"><fmt:message key="unidade.unidade" />:</label>
											<div class="input-append span12" style="margin-left: 0;">
												<input id="unidade" name="unidade" type="text" class="span12" style="margin-left: 0;" disabled="disabled" />
												<span class="add-on"><i class="icon-search"></i></span>
												<input id="idUnidade" name="idUnidade" type="hidden" value="0" />
											</div>
										</div>
										<div class="span4">
											<label class="strong"><fmt:message key="solicitacaoServico.tipo" />:</label>
											<select id="idTipoSolicitacao" name="idTipoSolicitacao" class="Description[solicitacaoServico.tipo]" disabled="disabled"></select>
										</div>
										<div class="span2">
											<div class="pull-right">
												<label>&nbsp;</label>
												<button id="btnSearch" type="button" class="btn btn-primary disabled" disabled="disabled">
													<fmt:message key="citcorpore.ui.botao.rotulo.Pesquisar" />
												</button>
											</div>
										</div>
										<div class="span2">
											<div class="pull-right">
												<label>&nbsp;</label>
												<button id="btnLimparFiltro" type="button" class="btn btn-primary disabled" disabled="disabled">
													<fmt:message key="citcorpore.ui.botao.rotulo.Limpar" />&nbsp;<fmt:message key="citcorpore.comum.filtro" />
												</button>
											</div>
										</div>
									</div>
								</div>
							</div>
						</form>

						<hr>

						<div class="row-fluid">
							<form name="formSolicitacoes" action="${ctx}/pages/gerenciamentoRotas/gerenciamentoRotas">
								<div id="rotaSolicitacoes" class="span6" style="display: none;">
									<input type="hidden" id="atribuicoes" name="atribuicoes" />
									<div class="row-fluid">
										<label class="label-box-div"><fmt:message key="menu.gestao.forca.atendimento.agendar.rota" /></label>
										<div id="boxSolicitacoes" class="box-div">
											<div class="row-fluid">
												<div id="solicitacoes" class="span12">
													<label class="campoObrigatorio strong"><fmt:message key="agenda.dataExecucao" /></label>
													<input id="idAtendente" name="idAtendente" type="hidden" value="0"></input>
													<input id="dataExecucao" name="dataExecucao" type="text" size="10" maxlength="10" class="Valid[Required] Description[agenda.dataExecucao] Format[Date] Valid[Date]" readonly/>
													<div id="tSols" class="table span12">
														<div class="table-header">
															<div class="table-row">
																<div class="table-cell" style="border-top: none"></div>
																<div class="table-cell" style="border-top: none"><label class="strong"><fmt:message key="problema.numero_solicitacao" /></label></div>
																<div class="table-cell" style="border-top: none"><label class="strong"><fmt:message key="contrato.contrato" /></label></div>
																<div class="table-cell" style="border-top: none"><label class="strong"><fmt:message key="gerenciaservico.sla" /></label></div>
																<div class="table-cell" style="border-top: none"><label class="strong"><fmt:message key="solicitacaoServico.tipo" /></label></div>
																<div class="table-cell" style="border-top: none"><label class="strong"><fmt:message key="citcorpore.comum.situacao" /></label></div>
																<div class="table-cell" style="border-top: none"><label class="strong"><fmt:message key="prioridade.prioridade" /></label></div>
															</div>
														</div>
														<div id="rowSolicitacoes" style="display: table-row-group;"></div>
														<div id="caption" class="table-caption-bottom" style="display: none;">
															<div style="padding-top: 1.5%;">
																<strong><fmt:message key="gestao.forca.atendimento.nova.consulta" /></strong>
															</div>
														</div>
													</div>
												</div>
												<div class="row-fluid">
													<div id="paginator" class="pagination pagination-centered pagination-small" style="display: none;"></div>
												</div>
											</div>
										</div>
									</div>
									<div class="row-fluid" style="padding-top: 1%;">
										<button id="btnGravar" type="button" class="btn btn-primary disabled" disabled="disabled">
											<fmt:message key="citcorpore.ui.botao.rotulo.Gravar" />
										</button>
										<button id="btnLimpar" type="button" class="btn btn-primary disabled" disabled="disabled">
											<fmt:message key="citcorpore.ui.botao.rotulo.Limpar" />
										</button>
									</div>
								</div>
								<div id="rotaMapa" class="span12 pull-right">
									<div class="row-fluid">
										<label class="label-box-div"><fmt:message key="menu.gestao.forca.atendimento.rota" /></label>
										<div id="map-box-div" class="box-div">
											<div id="map-area" class="map-area"></div>
										</div>
									</div>
									<div class="row-fluid">
										<ul class="inline pull-right" style="padding-top: 1%;">
											<li><span class="badge badge-important"><fmt:message key="gestao.forca.atendimento.status.atendimento.nao.atendido" /></span></li>
											<li><span class="badge badge-warning"><fmt:message key="gestao.forca.atendimento.status.atendimento.atendendo" /></span></li>
											<li><span class="badge"><fmt:message key="gestao.forca.atendimento.status.atendimento.pendencia" /></span></li>
										</ul>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<%@include file="/novoLayout/common/include/rodape.jsp" %>
		</div>
	</div>

	<link rel="stylesheet" type="text/css" href="css/gerenciamentoRotas.css" />
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
		var properties = { key: "${googleApiKey}", clientID: "${googleClientIDForWork}", searchMaxDays : ${searchMaxDays - 1}, context: "${ctx}" };

	</script>

	<script type="text/javascript" src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="${ctx}/js/alert-override.js"></script>
	<script type="text/javascript" src="${ctx}/js/DateTimeUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/StringUtils.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/geo-utils.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/geo-utils-jquery.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/geolocation.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/google-maps-v3.js"></script>
	<script type="text/javascript" src="${ctx}/js/geo-api/oms.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/gestaoForcaAtendimento.js"></script>
	<script type="text/javascript" src="js/gerenciamentoRotas.js"></script>

	<div id="modalSolicitacao" class="modal hide fade in" tabindex="-1" data-backdrop="static" data-keyboard="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.solicitacao" /></h3>
		</div>
		<div class="modal-body">
			<div id="contentSolicitacao">
				<iframe src="about:blank" id="frameSolicitacao" width="100%" class="bordeless-iframe"></iframe>
			</div>
		</div>
	</div>
</body>

</html>
