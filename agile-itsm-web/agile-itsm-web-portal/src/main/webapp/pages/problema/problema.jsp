<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@page import="br.com.citframework.dto.Usuario" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico" %>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho" %>
<%@page import="br.com.centralit.bpm.util.Enumerados" %>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>

<%
	response.setCharacterEncoding("ISO-8859-1");

	String id = request.getParameter("idBaseConhecimento");
	pageContext.setAttribute("id", id);

	String iframe = request.getParameter("iframe");
	String relacionarProblema = request.getParameter("relacionarProblema");
	pageContext.setAttribute("relacionarProblema", relacionarProblema);

	String tarefaAssociada = (String)request.getAttribute("tarefaAssociada");
	if (tarefaAssociada == null){
		tarefaAssociada = "N";
	}

	String requestpage = request.getContextPath();
	pageContext.setAttribute("requestpage", requestpage);

	String acaoIniciar = Enumerados.ACAO_INICIAR;
	pageContext.setAttribute("acaoIniciar", acaoIniciar);

	String acaoExecutar = Enumerados.ACAO_EXECUTAR;
	pageContext.setAttribute("acaoExecutar", acaoExecutar);
%>

<!DOCTYPE html>
<html>
<head>
	<%@include file="/include/header.jsp" %>

	<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

	<title>CITSMart</title>

	<link type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css"/>

	<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery.ui.all.css"/>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/slick.grid.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/pages/graficos/jquery.jqplot.min.css" />

	<link rel="stylesheet" type="text/css" href="${ctx}/pages/problema/css/problema.css" />

	<script type="text/javascript" src="${ctx}/js/jquery-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-ui-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.layout-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/debug.js"></script>
	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/uniform/jquery.uniform.min.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/autogrow/jquery.autogrowtextarea.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/multiselect/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/selectbox/jquery.selectBox.min.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/timepicker/jquery.timepicker.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/colorpicker/js/colorpicker.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/tiptip/jquery.tipTip.minified.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/validation/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/uitotop/js/jquery.ui.totop.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/custom/ui.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/custom/forms.js"></script>
	<script type="text/javascript" src="${ctx}/pages/graficos/jquery.jqplot.min.js"></script>
	<script type="text/javascript" src="${ctx}/pages/graficos/plugins/jqplot.pieRenderer.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/json2.js"></script>

	<!-- SlickGrid and its dependancies (not sure what they're for?) -->
	<script type="text/javascript" src="${ctx}/js/jquery.rule-1.0.1.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.event.drag.custom.js"></script>
	<script type="text/javascript" src="${ctx}/js/slick.core.js"></script>
	<script type="text/javascript" src="${ctx}/js/slick.editors.js"></script>
	<script type="text/javascript" src="${ctx}/js/slick.grid.js"></script>
	<script type="text/javascript" src="${ctx}/js/CollectionUtils.js"></script>
	<script type="text/javascript" src="${ctx}/js/CITTable.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/jquery/jquery.maskedinput.js"></script>

	<script type="text/javascript">
		var properties = {
			id : "${id}",
			requestpage : "${requestpage}",
			ctx : "${ctx}",
			acaoIniciar : "${acaoIniciar}",
			acaoExecutar : "${acaoExecutar}",
			relacionarProblema : "${relacionarProblema}"
		};
	</script>
	<script type="text/javascript" src="${ctx}/pages/problema/js/problema.js"></script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />

<body>
	<div id="divBarraFerramentas" style='position:absolute; top: 0px; left: 500px; z-index: 1000'>
		<jsp:include page="/pages/barraFerramentasProblemas/barraFerramentasProblemas.jsp"></jsp:include>
	</div>
	<div id="wrapper" class="wrapper">
	<div id="main_container" class="main_container container_16 clearfix" style='margin: 10px 10px 10px 10px'>
	<div id='divTituloProblema' class="flat_area grid_16" style="text-align:right;">
		<h2><fmt:message key="problema.registro_problema"/></h2>
	</div>
	<div class="box grid_16 tabs">
	<form name='form' action='${ctx}/pages/problema/problema'>
		<input type="hidden" id="itensConfiguracaoRelacionadosSerializado" name="itensConfiguracaoRelacionadosSerializado" />
		<input type="hidden" id="servicosRelacionadosSerializado" name="servicosRelacionadosSerializado" />
		<input type="hidden" id="solicitacaoServicoSerializado" name="solicitacaoServicoSerializado" />
		<%if(id!=null){ %>
		<input type="hidden" id="idBaseConhecimento" name="idBaseConhecimento">
		<%} %>
		<input type="hidden" id="idProblema" name="idProblema" />
		<input type="hidden" id="idProprietario" name="idProprietario" />
		<input type="hidden" id="idSolicitante" name="idSolicitante" />
		<input type="hidden" id="idCriador" name="idCriador" />
		<input type="hidden" id="dataHoraInicio" name="dataHoraInicio" />
		<input type="hidden" id="dataHoraCaptura" name="dataHoraCaptura" />
		<input type="hidden" id="idFaseAtual" name="idFaseAtual" />
		<input type="hidden" id="requisicaoMudancaSerializado" name="RequisicaoMudancaSerializado" />
		<input type='hidden' name='escalar' id='escalar' />
		<input type='hidden' name='alterarSituacao' id='alterarSituacao' />
		<input type='hidden' name='idTarefa' id='idTarefa' />
		<input type='hidden' name='acaoFluxo' id='acaoFluxo' />
		<input type='hidden' name='fase' id='fase' />
		<input type='hidden' name='tituloSolucaoContorno' id='tituloSolucaoContorno' />
		<input type='hidden' name='solucaoContorno' id='solucaoContorno' maxlength="700"/>
		<input type='hidden' name='idSolucaoContorno' id='idSolucaoContorno' />
		<input type='hidden' name='tituloSolucaoDefinitiva' id='tituloSolucaoDefinitiva' />
		<input type='hidden' name='solucaoDefinitiva' id='solucaoDefinitiva' />
		<input type='hidden' name='idSolucaoDefinitiva' id='idSolucaoDefinitiva' />
		<input type='hidden' name='chamarTelaProblema' id='chamarTelaProblema' />
		<input type='hidden' name='informacoesComplementares_serialize' id='informacoesComplementares_serialize' />
		<input type='hidden' id='fecharItensRelacionados' name="fecharItensRelacionados">
		<input type='hidden' id='hiddenIdItemConfiguracao' name="hiddenIdItemConfiguracao">
		<input type="hidden" id="idProblemaRelacionado" name="idProblemaRelacionado" />
		<input type='hidden' name='colItensProblema_Serialize' id='colItensProblema_Serialize' />
		<input type='hidden' name='messageId' id='messageId' />
		<input type="hidden" id="iframeSolicitacao" name="iframeSolicitacao" />

		<div class="col_100">
					<fieldset>
						<label>&nbsp;</label>
						<div>
							&nbsp;
						</div>
					</fieldset>
				</div>

			<div class="col_100">
					<fieldset>
						<label class="campoObrigatorio" style="font-family: Arial; font-weight: bold;"><fmt:message key="contrato.contrato" /></label>
						<div id = "divContrato">
						</div>
					</fieldset>
				</div>
					<div class="col_100">
						<div class="col_50">
						<fieldset style="height: 60px;">
							<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.solicitante" /></label>
							<div>
								<input class="Valid[Required] Description[solicitacaoServico.solicitante]" id="addSolicitante" name="solicitante" type="text" readonly="readonly"/>
							</div>
						</fieldset>
						</div>
						<div class="col_25">
							<fieldset style="height: 60px;">
								<label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="origemAtendimento.origem" /></label>
								<div>
									<select id="idOrigemAtendimento" name='idOrigemAtendimento' class="Valid[Required] Description[origemAtendimento.origem]"></select>
								</div>
							</fieldset>
						</div>
						<div class="col_25">
						<fieldset style="height: 60px;">
							<label><fmt:message key="citcorpore.comum.grupoExecutor"/></label>
							<div>
								<select name='idGrupo' id='idGrupo'>
								</select>
							</div>
						</fieldset>
					</div>
				</div>
				<!-- infContato -->
				<div class="col_100">
					<div>
						<h2 class="section"><fmt:message key="solicitacaoServico.informacaoContato" /></h2>
					</div>

				<div class="col_100">
					<div class="col_50">
						<div class="col_50">
							<fieldset>
								<label class="campoObrigatorio"> <fmt:message key="solicitacaoServico.nomeDoContato" /></label>
								<div>
									<input id="contato" type='text' name="nomeContato" maxlength="70"
									class="Valid[Required] Description[<fmt:message key='solicitacaoServico.nomeDoContato' />]" />
								</div>
							</fieldset>
						</div>

						<div class="col_50">
							<fieldset>
								<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.email" /></label>
								<div>
									<input id="emailContato" type='text' name="emailContato" maxlength="120" class="Valid[Required, Email] Description[citcorpore.comum.email]" />
								</div>
							</fieldset>
						</div>

						<div class="col_50">
							<fieldset>
								<label><fmt:message key="citcorpore.comum.telefone" /></label>
								<div>
									<input id="telefoneContato" type='text' name="telefoneContato" maxlength="20" class="" />
								</div>
							</fieldset>
						</div>

						<div class="col_50">
							<fieldset>
								<label><fmt:message key="citcorpore.comum.ramal" /></label>
								<div>
									<input id="ramal" type='text' name="ramal" maxlength="5" class="Format[Numero]" />
								</div>
							</fieldset>
						</div>

						<div class="col_50">
							<fieldset style="height: 55px">
								<label class="tooltip_bottom campoObrigatorio" title="<fmt:message key="colaborador.cadastroUnidade"/>">
									<fmt:message key="unidade.unidade"/></label>
								<div id = "divUnidade">
								</div>
							</fieldset>
						</div>

						<div class="col_50">
							<fieldset style="height: 55px">
								<label class="tooltip_bottom " title="<fmt:message key="colaborador.cadastroUnidade"/>">
									<fmt:message key="solicitacaoServico.localidadeFisica"/>
								</label>
								<div>
									<select name='idLocalidade' id = 'idLocalidade'></select>
								</div>
							</fieldset>
						</div>
					</div>
						<div class="col_50">
							<fieldset style="height: 112px">
								<label><fmt:message key="colaborador.observacao"/></label>
								<div>
									<textarea id="observacao" class="col_98" name="observacao" maxlength="700" rows="4" style="height: 90px; float: right;"></textarea>
								</div>
							</fieldset>
						</div>
					</div>
				</div> <!-- FIM_divInfContato -->

				<div class="col_100">

				</div>

				<div class="col_100">
					<div>
						<h2 class="section"><fmt:message key="problema.informacao" /></h2>
					</div>

					<div class="col_50"> <!-- Lado Esquerdo da Tela -->
						<div class="col_100">
							<fieldset>
								<label class="campoObrigatorio"><fmt:message key="problema.titulo" /></label>
								<div>
									<input maxlength="255" class="Valid[Required] Description[problema.titulo]" id="titulo" name="titulo" type="text"/>
								</div>
							</fieldset>
						</div>

						<div style="width: 100%; float: left;">
							<fieldset>
								<label class="campoObrigatorio"><fmt:message key="problema.descricao" /></label>
									<div>
										<textarea class="Valid[Required] Description[problema.descricao]" id="descricao" class="col_100" name="descricao" rows="4" maxlength="65000" style="height: 250px;"></textarea>
									</div>
									<div style="visibility: none" id="DescricaoAuxliar">
									</div>
							</fieldset>
						</div>
					</div>

					<div class="col_50"><!-- Lado Direito da Tela -->
						<div class="col_33">
							<fieldset style="height: 55px">
								<label class="campoObrigatorio"><fmt:message key="problema.severidade" /></label>
								<div>
									<select class="influenciaPrioridade" onchange="atualizaPrioridade()" id="severidade" name="severidade">
										<option><fmt:message key="citcorpore.comum.baixa"/></option>
										<option><fmt:message key="citcorpore.comum.media"/></option>
										<option><fmt:message key="citcorpore.comum.alta"/></option>
									</select>
								</div>
							</fieldset>
						</div>
						<div class="col_33">
							<fieldset style="height: 55px">
								<label class="campoObrigatorio"><fmt:message key="problema.impacto"/></label>
								<div>
									<select class="influenciaPrioridade" onchange="atualizaPrioridade()" id="impacto" name="impacto">
										<option value="B"><fmt:message key="citcorpore.comum.baixo"/></option>
										<option value="M"><fmt:message key="citcorpore.comum.medio"/></option>
										<option value="A"><fmt:message key="citcorpore.comum.alto"/></option>
									</select>
								</div>
							</fieldset>
						</div>
						<div class="col_33">
							<fieldset style="height: 55px">
								<label class="campoObrigatorio"><fmt:message key="problema.urgencia"/></label>
								<div>
									<select class="influenciaPrioridade" onchange="atualizaPrioridade()" id="urgencia" name="urgencia">
										<option value="B"><fmt:message key="citcorpore.comum.baixo"/></option>
										<option value="M"><fmt:message key="citcorpore.comum.medio"/></option>
										<option value="A"><fmt:message key="citcorpore.comum.alto"/></option>
									</select>
								</div>
							</fieldset>
						</div>
						<div class="col_100">
						<div class="col_50">
							<fieldset style="height: 60px">
								<label><fmt:message key="problema.solucionar_contornar"/></label>
								<div>
									<input id="dataHoraLimiteSolucionar" name="dataHoraLimiteSolucionar" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" />
								</div>
							</fieldset>
						</div>
						<div class="col_20">
							<fieldset style="height: 60px">
								<label class="campoObrigatorio"><fmt:message key="prioridade.prioridade" /></label>
								<div>
									<input id="prioridade" name="prioridade" type="text" readonly="readonly" value="5"/>
								</div>
							</fieldset>
						</div>
						<div class="col_30">
							<fieldset style="height: 60px">
								<label><fmt:message key="problema.gerenciamentoProblema"/></label>
								<div>
									<input type="radio" id="proativoReativo" name="proativoReativo" value="Proativa" checked="checked" /><fmt:message key="problema.proativo"/>
									<input type="radio" id="proativoReativo" name="proativoReativo" value="Reativo" /><fmt:message key="problema.reativo"/>
								</div>
							</fieldset>
						</div>
						</div>

						<div id="divNotificacaoEmail" class="col_100">
								<fieldset class="Col_50">
									<label><fmt:message key="problema.notificacao_email"/></label>
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailCriacao' checked="checked"/><fmt:message key="problema.registrar_novo_problema"/></label><br>
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailFinalizacao' checked="checked"/><fmt:message key="problema.finalizar_problema"/></label><br>
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailAcoes'/><fmt:message key="problema.acoes_relacionadas_problema"/></label>
								</fieldset>
						</div>
					</div>
				</div>
				<div style="display: block;" id="categoria">
									<div class="col_33">
										<fieldset style="height: 60px">
											<label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key="categoriaProblema.categoriaProblema"/>
											<img src="${ctx}/imagens/add.png" onclick="abreCadastroCategoriaProblema()">
											</label>
											<div>
												<select name='idCategoriaProblema' onchange="carregarInformacoesComplementares();restoreImpactoUrgenciaPorTipoProblema();" onclick="verificaContrato()" class="Valid[Required] Description[categoriaProblema.categoriaProblema]"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset style="height: 60px">
											<label style="cursor: pointer;"><fmt:message key="problema.causa"/></label>
											<div>
												<select name='idCausa'></select>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset style="FONT-SIZE: xx-small; height: 60px;">
											<label style="cursor: pointer;"><fmt:message key="citcorpore.comum.categoriaSolucao"/></label>
											<div>
												<select name='idCategoriaSolucao'></select>
											</div>
										</fieldset>
									</div>
								</div>

							<div class="col_100" id='divInformacoesComplementares' style='display:none;'>
								<iframe id='fraInformacoesComplementares' name='fraInformacoesComplementares' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;'></iframe>
							</div>

				<div id="statusProblema" class="col_100">
				<fieldset>
					<label>
						<fmt:message key="problema.status"/>
					</label>

						<label id="statusSetado" style="cursor: pointer;">

						</label>
					<label id="statusCancelada" style="cursor: pointer;">
						<input type="radio" name="status" value="Cancelada" />
						<fmt:message key="problema.cancelado"/>
					</label>
					</fieldset>
				</div>
				<div class="col_100">
						<fieldset>
							<label><fmt:message key="citcorpore.comum.fechamento" /></label>
							<div>
								<textarea id="fechamento" name="fechamento" maxlength="500" cols='70' rows='5' class="Description[Resposta]"></textarea>
							</div>
						</fieldset>
				</div>

						<div class="col_100">
							<fieldset style="height: 60px">
								<label><fmt:message key="ocorrenciaProblema.dataHoraUltimaAtualizacao"/></label>
								<div id='dataHoraUltimaAtualizacao'></div>
							</fieldset>
						</div>

				<div class="col_100" id='divBotaoAddRegExecucao'>
						<fieldset style="FONT-SIZE: xx-small;">
							<button type='button' name='btnAddRegExec' id='btnAddRegExec' onclick='mostrarEscondeRegExec()'><fmt:message key="solicitacaoServico.addregistroexecucao_mais" /></button>
						</fieldset>
					</div>
					<div class="col_100">
						<fieldset style="FONT-SIZE: xx-small">
							<label id='lblMsgregistroexecucao' style='display:none'><font color='red'><b><fmt:message key="solicitacaoServico.msgregistroexecucao" /></b></font></label>
								<div id='divMostraRegistroExecucao' style='display:none'>

									<textarea id="registroexecucao" name="registroexecucao" cols='70' rows='5' class="Description[citcorpore.comum.resposta]" maxlength="700"></textarea>

								</div>
						</fieldset>
					</div>
					<div class="col_100" style="overflow : auto;max-height: 400px">
						<fieldset>
							<div id="tblOcorrencias"></div>
						</fieldset>
					</div>

		<div id="abas" class="formRelacionamentos">
			<div id="tabs" class="block">
				<ul class="tab_header clearfix">
					<li><a href="#relacionaIcs"><fmt:message key="problema.relacionar_ics"/></a></li>
					<li><a href="#relacionarMudancas" id="abaMudancas"><fmt:message key="requisicaoMudanca.mudancas"/>
					</a></li>
					<li><a href="#relacionarIncidentes"><fmt:message key="problema.incidentes"/>
					</a></li>
					<li><a href="#relacionarRevisaoProblemaGrave" id="abaRevisaoProblemaGrave"><fmt:message key="problema.revisao_problema_grave"/>
					</a></li>
					<li><a href="#relacionarDiagnostico"><fmt:message key="problema.avaliacaoDiagnostico"/>
					</a></li>
					<li><a href="#relacionarSolucao"><fmt:message key="citcorpore.comum.solucao"/>
					</a></li>
					<%if(id==null){ %>
					<li><a href="#relacionarErrosConhecidos"><fmt:message key="problema.erros_conhecidos"/></a></li>
					<%} %>
					<li><a href="#divProblemaRelacionado"><fmt:message key="problema.relacionarProblema"/></a></li>
				</ul>
				<div id="relacionaIcs">
					<div class="formHead">
						<div style="width: 15%; float: left; border: 0px!important;" align="center">
						<fieldset>
							<label>
								<fmt:message key="itemConfiguracao.itemConfiguracao"/>
								<img id="imagenIC" onclick="abrirModalPesquisaItemConfiguracao();" style="vertical-align: middle; cursor: pointer;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
								<img id='btnCadastroItemConfiguracao' style="vertical-align: middle; cursor: pointer;" src="${ctx}/imagens/add.png" onclick="gravarGestaoItemConfiguracao(document.form.idBaseConhecimento.value);">
							</label>
						</fieldset>
						</div>
						<div class="formBody" style="overflow: auto;">
							<table id="tblICs" class="table table-bordered table-striped">
								<tr>
									<th width="13%">
										<fmt:message key="parametroCorpore.id"/>
									</th>
									<th width="35%">
										<fmt:message key="citcorpore.comum.nome"/>
									</th>
									<th width="50%">
										<fmt:message key="citcorpore.comum.descricao"/>
									</th>
									<th width="2%">

									</th>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div id="relacionarMudancas">
					<div class="formHead">
						<div style="width: 15%; float: left;border: 0px!important;" align="center">
						<fieldset>
							<label>
								<fmt:message key="requisicaMudanca.mudanca"/>
								<img onclick='pesquisarRequisicaoMudanca();' style="vertical-align: middle; cursor: pointer;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
								<img onclick="popupManagerRequisicaoMudanca.abrePopup('requisicaoMudanca', '');" style="vertical-align: middle; cursor: pointer;" src="${ctx}/imagens/add.png" />
							</label>
						</fieldset>
						</div>
					<div class="formBody">
						<table id="tblRDM" class="table table-bordered table-striped">
								<tr>
									<th width="13%"><fmt:message key="parametroCorpore.id"/></th>
									<th width="35%"><fmt:message key="lookup.titulo"/></th>
									<th width="50%"><fmt:message key="lookup.status"/></th>
									<th width="2%"></th>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div id="relacionarIncidentes">
							<div class="formHead">
								<div style="width: 20%; float: left;border: 0px!important;" align="center">
								<fieldset>
									<label>
										<fmt:message key="solicitacaoServico.solicitacao"/>
										<img id="addSolicitacaoServico" style="vertical-align: middle; cursor: pointer;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
										<img onclick="abrirPopupNovaSolicitacaoServico();" style="vertical-align: middle; cursor: pointer;" src="${ctx}/imagens/add.png" />
									</label>
								</fieldset>
								</div>
								<div></div>
								<div class="formBody">
									<table id="tblSolicitacaoServico" class="table table-bordered table-striped">
										<tr>
											<th width="35%">
												<fmt:message key="problema.numero_solicitacao"/>
											</th>
											<th width="63%">
												<fmt:message key="citcorpore.comum.descricao"/>
											</th>
											<th width="2%"></th>
										</tr>
									</table>
								</div>
							</div>
					</div>
						<div id="relacionarRevisaoProblemaGrave">
							<fieldset>
								<label><fmt:message key="problema.acoes_corretas"/></label>
								<textarea id="acoesCorretas" name="acoesCorretas" maxlength="500" style="height: 100px; width: 99%;"></textarea>

								<label><fmt:message key="problema.acoes_erradas"/></label>
								<textarea id="acoesIncorretas" name="acoesIncorretas" maxlength="500" style="height: 100px; width: 99%;"></textarea>

								<label><fmt:message key="problema.possiveis_melhorias_futuras"/></label>
								<textarea id="melhoriasFuturas" name="melhoriasFuturas" maxlength="500" style="height: 100px; width: 99%;"></textarea>

								<label><fmt:message key="problema.prevencao_de_recorrencia"/></label>
								<textarea id="recorrenciaProblema" name="recorrenciaProblema" maxlength="500" style="height: 100px; width: 99%;"></textarea>

								<label style="cursor: pointer;">
									<input type="checkbox" id="acompanhamento" name="acompanhamento" value = "S" onchange="validarAcompanhamento()"/>
									<fmt:message key="problema.necessario_acompanhamento"/>
								</label>

								<label><fmt:message key="problema.responsabilidade_de_terceiros"/></label>
								<textarea id="responsabilidadeTerceiros" name="responsabilidadeTerceiros" maxlength="500" style="height: 100px; width: 99%;"></textarea>

							</fieldset>
					</div><!-- FIM_relacionarRevisaoProblemaGrave -->

						<div id="relacionarDiagnostico">
							<!-- ANALISES DE IMPACTO, RISCOS E ROLLBACK -->
							<fieldset>

							<label id="rotuloCausaRaiz" class="campoObrigatorio"><fmt:message key="problema.causa_raiz"/></label>
							<textarea id="causaRaiz" name="causaRaiz" maxlength="700" style="height: 100px; width: 99%;"></textarea>

							<label><fmt:message key="problema.mensagem_erro_associada"/></label>
							<textarea id="msgErroAssociada" name="msgErroAssociada" maxlength="500" style="height: 100px; width: 99%;"></textarea>

							<label><fmt:message key="problema.diagnostico"/></label>
							<textarea id="diagnostico" name="diagnostico" maxlength="500" style="height: 100px; width: 99%;"></textarea>

							</fieldset>
					</div>
						<div id="relacionarSolucao">
							<div class="col_100">
								<div style="width: 20%; float: left;border: 0px!important;" align="center">
								<fieldset>
									<label class="campoObrigatorio" id="rotuloSolucaoContorno" style="cursor: pointer;" onclick='abrirPopupSolContorno();'>
									<fmt:message key="problema.adicionarEditarSolucaoContorno"/>
									<img src="${ctx}/imagens/add.png" />
									</label>

								</fieldset>
								</div>
							<div class="formBody" id="divTblSolContorno" style='display: block; overflow: auto;'>
								<table id="tblSolContorno" class="table table-bordered table-striped">
									<tr>
										<th width="20%">
											<fmt:message key="problema.titulo"/>
										</th>
										<th width="10%">
											<fmt:message key="requisitosla.criadoem"/>
										</th>
										<th width="70%">
											<fmt:message key="citcorpore.comum.descricao"/>
										</th>
									</tr>
								</table>
							</div>
							</div>
							<div class="col_100">
								<fieldset>
									<div style="width: 15%; float: left;border: 0px!important;" align="center">
									<label style="cursor: pointer;" onclick='abrirPopupSolDefinitiva();'>
									<fmt:message key="problema.adicionarEditarSolucaoDefinitiva"/>
									<img src="${ctx}/imagens/add.png" />
									</label>
									</div>
								</fieldset>
							<div class="formBody" id="divTblSolDefinitiva" style='display: block;overflow:auto; '>
								<table id="tblSolDefinitiva" class="table table-bordered table-striped">
									<tr>
										<th width="20%">
											<fmt:message key="problema.titulo"/>
										</th>
										<th width="10%">
											<fmt:message key="requisitosla.criadoem"/>
										</th>
										<th width="70%">
											<fmt:message key="citcorpore.comum.descricao"/>
										</th>
									</tr>
								</table>
							</div>
							</div>
						</div>
						<%if(id==null){ %>
						<div id="relacionarErrosConhecidos">
							<div class="col_100">
								<input type="hidden" id="idBaseConhecimento" name="idBaseConhecimento">
								<div id="divBaseConhecimento" style="width: 15%; float: left; border: 0px!important;" align="center">
									<fieldset>
										<label><fmt:message key='baseConhecimento.baseConhecimento'/>
										<img onclick='gravarErroConhecido();' style="cursor: pointer;" src="${ctx}/imagens/add.png">
										</label>
									</fieldset>
								</div>
								<div id='divProblemaConhecimento'	style='height: 120px; width: 99%; overflow: auto;'>
									<table id='tblErrosConhecidos' class="table table-bordered table-striped">
										<tr>
											<th width="10%"></th>
											<th width="60%"><fmt:message key="requisicaMudanca.titulo" /></th>
											<th width="29%"><fmt:message key="requisicaMudanca.status" /></th>
											<th>&nbsp;</th>
										</tr>
									</table>
								</div>
							</div>
						</div>
					<%} %>

					<div id="divProblemaRelacionado" style="display: block; border: 0px" class="col_50">
							<div class="row-fluid">
								<div class="span10" style=" border: 0px">
									<button type='button' name='pesqProblema' class="light" onclick='addProblemaRelacionado();'>
										<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
										<span>
											<fmt:message key="solicitacaoServico.adicionarProblema" />
										</span>
									</button>

									<button type='button' name='criarProblema' class="light" onclick='cadastrarProblema();'>
										<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
										<span>
											<fmt:message key="categoriaProblema.cadastro" />
										</span>
									</button>
								</div>
							</div>
							<div class='widget'>
								<div class='widget-head'><h4 class='heading'><fmt:message key="solicitacaoServico.problemaRelacionado" /></h4></div>
								<div class='widget-body'>
									<div id='divProblemaSolicitacao'>
										<table id='tblProblema' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>
												<td width="30%"><fmt:message key="pesquisaProblema.numero" /></td>
												<td width="60%"><fmt:message key="requisicaMudanca.titulo" /></td>
												<td width="29%"><fmt:message key="requisicaMudanca.status" /></td>
												<td style='text-align: center' width='20px' height="15px"></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
					</div>
				</div>
			</div>

				<div id="divBotoes" class="formFooter">
					<%if (tarefaAssociada.equalsIgnoreCase("N")) {%>
						<button type='button' name='btnGravar' class="light" onclick='gravar()'>
							<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
							<span><fmt:message key="citcorpore.comum.gravar" /></span>
						</button>
					<%}else{%>
						<button type='button' name='btnGravarEContinuar' class="light" onclick='gravarEContinuar();'>
							<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
							<span><fmt:message key="citcorpore.comum.gravarEContinuar" /></span>
						</button>
						<button type='button' name='btnGravarEFinalizar' class="light" onclick='gravarEFinalizar();'>
							<img src="${ctx}/template_new/images/icons/small/grey/cog_2.png">
							<span><fmt:message key="citcorpore.comum.gravarEFinalizar" /></span>
						</button>
					<%}%>

					<button type='button' name='btnLimpar' class="light" onclick='limpar(document.form);'>
						<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
						<span>
							<fmt:message key="citcorpore.comum.limpar" />
						</span>
					</button>
				</div><!-- FIM_formFooter -->
				<div id="divBotaoFecharFrame" class="formFooter">
					<button type='button' name='btnGravarFecharDef' class="light" onclick='fecharFrameProblema();'>
						<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
						<span><fmt:message key="citcorpore.comum.fechar" />
						</span>
					</button>
				</div>
				<!-- Solução de Contorno -->
				<div id="POPUP_SOLUCAO_CONTORNO">
					<div class="columns clearfix">
					<div class="col_100">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="problema.titulo" /></label>
							<div>
								<input type='text' id="tituloSolCon" name="tituloSolCon" maxlength="120" />
							</div>
						</fieldset>
					</div>
					<div class="col_100">
						<fieldset>
							<label class="campoObrigatorio" id="rotuloSolucaoContornoN"><fmt:message key="citcorpore.comum.descricao"/></label>
							<div>
							<textarea id="descSolCon" name="descSolCon" maxlength="700" style="height: 150px; width: 99%;" class="Description[citcorpore.comum.descricao]"></textarea>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="col_100">
					<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
					 <button type='button' name='btnGravarNotificacao' class="light"
						onclick='gravarSolContorno();'>
						<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
						<span><fmt:message key="citcorpore.comum.gravar" />
						</span>
					</button>
					<button type='button' name='btnGravarFechar' class="light"
						onclick='fecharSolContorno();'>
						<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
						<span><fmt:message key="citcorpore.comum.fechar" />
						</span>
					</button>
				</fieldset>
				</div>
			</div>
			<!-- Solução de Definitiva -->
			<div id="POPUP_SOLUCAO_DEFINITIVA">
				<div class="columns clearfix">
					<div class="col_100">
						<fieldset>
							<label class="campoObrigatorio"><fmt:message key="problema.titulo" /></label>
							<div>
								<input type='text' id="tituloSolDefinitiva" name="tituloSolDefinitiva" maxlength="120" />
							</div>
						</fieldset>
					</div>
					<div class="col_100">
						<fieldset>
							<label class="campoObrigatorio" id="rotuloSolucaoDefinitiva"><fmt:message key="citcorpore.comum.descricao"/></label>
							<div>
								<textarea id="descSolDefinitiva" name="descSolDefinitiva" maxlength="700" style="height: 150px; width: 99%;" class="Description[citcorpore.comum.descricao]"></textarea>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="col_100">
					<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
					<button type='button' name='btnGravarSolDefinitiva' class="light" onclick='gravarSolDefinitiva();'>
						<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
						<span><fmt:message key="citcorpore.comum.gravar" /></span>
					</button>
					<button type='button' name='btnGravarFecharDef' class="light" onclick='fecharSolDefinitiva();'>
						<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
						<span><fmt:message key="citcorpore.comum.fechar" /></span>
					</button>
				</fieldset>
				</div>
			</div>

		</form>
	</div>
	</div>
	</div>

	<!-- POPUPS -->
	<div id="popupServicosRelacionados" class="popup">

	</div>

	<!-- MAPA DESENHO SERVIÇO -->
	<div id="popupCadastroRapido">
		<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
	</div>

	<!-- LOOKUPS -->
	<div id="POPUP_SOLICITACAOSERVICO" title="<fmt:message key="solicitacaoServico.solicitacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
					<div align="right">
							<label style="cursor: pointer; ">
									<fmt:message key="solicitacaoServico.solicitacao" />
									<img id='botaoSolicitante' src="${ctx}/imagens/add.png" onclick="abrirPopupNovaSolicitacaoServico();">
							</label>
						</div>
						<form name='formPesquisaSolicitacaoServico' style="width: 540px">
							<cit:findField formName='formPesquisaSolicitacaoServico'
								lockupName='LOOKUP_SOLICITACAOSERVICO' id='LOOKUP_SOLICITACAOSERVICO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUPITEMCONFIGURACAO" style="" title="Pesquisa Itens Configuração" class="POPUP_LOOKUP">
 		<table>
			<tr>
				<td>
					<h3><fmt:message key="problema.itens_configuracao"/></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaItem' style="width: 95%;">
			<cit:findField formName='formPesquisaItem' lockupName='LOOKUP_ITEMCONFIGURACAO_ATIVO' id='LOOKUP_ITEMCONFIGURACAO'
					top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<div id="POPUP_REQUISICAOMUDANCA" style="" title=<fmt:message key="requisicaMudanca.mudanca"/> class="POPUP_LOOKUP">
 		<table>
			<tr>
				<td>
					<h3><fmt:message key="requisicaMudanca.mudanca"/></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaRequisicaoMudanca' style="width: 95%;">
			<cit:findField formName='formPesquisaRequisicaoMudanca' lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA'
						top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<div id="POPUP_PESQUISARPROBLEMA" style="overflow: hidden;" title='<fmt:message key="categoriaProblema.pesquisa"/>'>
		<div class="modal-body">
			<form name='formPesquisaProblema'>
				<cit:findField formName='formPesquisaProblema'
					lockupName='LOOKUP_PROBLEMA_EMEXECUCAO' id='LOOKUP_PROBLEMA_EMEXECUCAO' top='0'
					left='0' len='802' heigth='200' javascriptCode='true'
					htmlCode='true' />
			</form>
		</div>
	</div>

	<div id="POPUP_SOLICITANTE" title="<fmt:message key="citcorpore.comum.pesquisacolaborador" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div align="right">
							<label style="cursor: pointer; ">
								<fmt:message key="solicitacaoServico.solicitante" />
								<img id='botaoSolicitante' src="${ctx}/imagens/add.png" onclick="chamaPopupCadastroSol()">
								<img id="btHistoricoSolicitante" style="cursor: pointer; display: none;" src="${ctx}/template_new/images/icons/small/grey/month_calendar.png">
							</label>
						</div>
						<form name='formPesquisaColaborador' style="width: 540px">
							<cit:findField formName='formPesquisaColaborador'
								lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOL_CONTRATO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_EMPREGADO" title="Pesquisa Colaborador" class="POPUP_LOOKUP">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaEmpregado' style="width: 540px">
							<cit:findField formName='formPesquisaEmpregado'
								lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_SERVICO" style="" title="Pesquisa Serviço" class="POPUP_LOOKUP">
			<table>
			<tr>
				<td>
					<h3 align="center">
						<fmt:message key="problema.servico" />
					</h3>
				</td>
			</tr>
		</table>

		<form name='formPesquisa' style="width: 95%;">
			<cit:findField formName='formPesquisa' lockupName='LOOKUP_SERVICO' id='LOOKUP_SERVICO'
						top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<div id="POPUP_INFO_INSERCAO" title="" style="overflow: hidden;">
		<div class="toggle_container">
			<div id='divMensagemInsercao' class="section" style="overflow: hidden; font-size: 24px;">

			</div>
			<button type="button" onclick='$("#POPUP_INFO_INSERCAO").dialog("close");'>
				<fmt:message key="citcorpore.comum.fechar" />
			</button>
		</div>
	</div>

	<div id="POPUP_BASECONHECIMENTO" style="overflow: hidden;" title="<fmt:message key="baseConhecimento.baseConhecimento"/>">
		<iframe id='iframeBaseConhecimento' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()"></iframe>
	</div>

	<div id="POPUP_ITEMCONFIGURACAO" title="<fmt:message key='itemConfiguracao.itemConfiguracao' /> ">
		<iframe id="iframeItemConfiguracao" name="iframeItemConfiguracao" width="100%" height="100%"> </iframe>
	</div>

	<div id="POPUP_IFRAMEPROBLEMARELACIONADO" title="<fmt:message key='solicitacaoServico.problemaRelacionado' /> ">
		<iframe id='iframeEditarCadastrarProblema' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;'onload="resize_iframe()"></iframe>
	</div>

	<!-- Thiago Fernandes - 01/11/2013 08:30 - Sol. 121468 - Popup para cadastro nova solicitação serviço.. -->
	<div id="POPUP_NOVASOLICITACAOSERVICO" style="overflow: hidden;" title="<fmt:message key="solicitacaoServico.solicitacao"/>">
		<iframe id='iframeNovaSolicitacao' src='about:blank' width="100%" height="100%" onload="resize_iframe()">
		</iframe>
	</div>

	<div id="POPUP_PESQUISAITEMCONFIGURACAO" style="overflow: hidden;" title="<fmt:message key="itemConfiguracao.pesquisa" />">
		<iframe id='framePesquisaItemConfiguracao' src='about:blank' width="100%" height="100%" onload="resize_iframe()">
		</iframe>
	</div>
</body>
</html>
