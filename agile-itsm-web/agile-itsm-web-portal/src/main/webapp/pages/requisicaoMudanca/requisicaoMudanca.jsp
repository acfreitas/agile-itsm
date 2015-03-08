<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="br.com.centralit.citajax.html.DocumentHTML" %>
<%@page import="br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO" %>
<%@page import="br.com.centralit.bpm.util.Enumerados" %>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@page import="br.com.citframework.dto.Usuario" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico" %>
<%@page import="br.com.centralit.bpm.util.Enumerados" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@page import="br.com.centralit.citcorpore.bean.HistoricoMudancaDTO" %>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>

<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Mudar a maneira de chamar a poupup de cadastro para novo colaborador e poder fechala pelo o seu id. Da linha 2047 á 2065 ,2969, e da linha 3118 á 3122. -->
<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Setar mascar no campo telefoneContato, linha 424. -->
<%
	String id = request.getParameter("idBaseConhecimento");
	int idRequisicaoMudanca = 0;
	if (request.getParameter("idRequisicaoMudanca") != null) {
		idRequisicaoMudanca = (Integer) request.getAttribute("idRequisicaoMudanca");
	}

	String strRegistrosExecucao = (String) request.getAttribute("strRegistrosExecucao");
	if (strRegistrosExecucao == null) {
		strRegistrosExecucao = "";
	}
	/*Só vai aparecer se for feito a pesquisa, se já esta aprovado ou não, caso contrario não aparecera nenhum dos dois.*/

	String faseMudancaRequisicao = "";
	if (request.getAttribute("faseMudancaRequisicao") != null) {
		faseMudancaRequisicao = (String) request.getAttribute("faseMudancaRequisicao");
	}

	String acaoIniciar = Enumerados.ACAO_INICIAR;
	String acaoExecutar = Enumerados.ACAO_EXECUTAR;
	pageContext.setAttribute("acaoIniciar", acaoIniciar);
	pageContext.setAttribute("acaoExecutar", acaoExecutar);

	/**
	* Motivo: Verifica se a tela é de visualização
	* Autor: luiz.borges
	* Data/Hora: 10/12/2013 15:41
	*/
	String display = "";
	String editar = "false";
	if(request.getParameter("editar") != null && request.getParameter("editar").toString().equalsIgnoreCase("N")){
		display = "display:none";
		editar = "true";
		request.getSession().setAttribute("disable", "true");
	}else{
		request.getSession().setAttribute("disable", "false");
	}
	pageContext.setAttribute("display", display);
%>

<!doctype html public "">
<html>
<head>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

	<script type="text/javascript" src="../../cit/objects/RequisicaoMudancaResponsavelDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/HistoricoMudancaDTO.js"></script>

	<%
		String tarefaAssociada = (String) request.getAttribute("tarefaAssociada");
		if (tarefaAssociada == null) {
			tarefaAssociada = "N";
		}
	%>

	<link type="text/css" rel="stylesheet" class="include" href="${ctx}/pages/graficos/jquery.jqplot.min.css" />

	<link type="text/css" rel="stylesheet" href="${ctx}/pages/requisicaoMudanca/requisicaoMudanca.css"/>

	<title><fmt:message key="citcorpore.comum.title"/></title>

	<!-- SlickGrid and its dependancies (not sure what they're for) --->
	<script type="text/javascript" src="${ctx}/js/jquery.rule-1.0.1.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.event.drag.custom.js"></script>
	<script type="text/javascript" src="${ctx}/js/slick.core.js"></script>
	<script type="text/javascript" src="${ctx}/js/slick.editors.js"></script>
	<script type="text/javascript" src="${ctx}/js/slick.grid.js"></script>
	<script type="text/javascript" src="${ctx}/js/CollectionUtils.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-ui-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.layout-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/debug.js"></script>
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
	<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
	<script type="text/javascript" src="${ctx}/js/json2.js"></script>
	<script class="include" type="text/javascript" src="${ctx}/pages/graficos/jquery.jqplot.min.js"></script>
	<script class="include" type="text/javascript" src="${ctx}/pages/graficos/plugins/jqplot.pieRenderer.min.js"></script>

	<script type="text/javascript">
		var display = "${display}";
		var basePath = '${ctx}/fckeditor/';
		var id = "${id}";
		var botaoVisualizarMapa_src = '${ctx}/template_new/images/icons/small/grey/magnifying_glass.png';
		var botaoVisualizarProblemas_src = '${ctx}/imagens/viewCadastro.png';
		var acaoIniciar = "${acaoIniciar}";
		var acaoExecutar = "${acaoExecutar}";
	</script>
	<script type="text/javascript" src="${ctx}/pages/requisicaoMudanca/requisicaoMudanca.js"></script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />

<body>
	<div id="divBarraFerramentas" style='position:absolute; top: 0px; left: 500px; z-index: 1000'>
		<jsp:include page="/pages/barraFerramentasMudancas/barraFerramentasMudancas.jsp"></jsp:include>
	</div>
	<div id="wrapper" class="wrapper">
		<div id="main_container" class="main_container container_16 clearfix" style='margin: 10px 10px 10px 10px'>
			<div id='divTituloSolicitacao' class="flat_area grid_16" style="text-align:right;">
				<%
					if (idRequisicaoMudanca > 0) {
				%>
				<h2><fmt:message key="requisicaoMudanca.requisicaoMudancaNumero" /> <%=idRequisicaoMudanca%></h2>
				<%
					} else {
				%>
				<h2><fmt:message key="requisicaoMudanca.requisicaoMudanca" /></h2>
				<%
					}
				%>
			</div>
			<%
				if (faseMudancaRequisicao != null && faseMudancaRequisicao != "") {
			%>
			<div id='divStatusRequisicao' class="flat_area grid_16" style="text-align:right;">
				<h3><fmt:message key="<%=faseMudancaRequisicao%>" /></h3>
			</div>
			<%} %>

			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div class="section">
						<form id="form" name='form' action='${ctx}/pages/requisicaoMudanca/requisicaoMudanca'>
							<input type="hidden" id="serializados" name="serializados" />
							<input type="hidden" id="idBaseConhecimento" name="idBaseConhecimento" />
							<input type="hidden" id="itensConfiguracaoRelacionadosSerializado" name="itensConfiguracaoRelacionadosSerializado" />
							<input type="hidden" id="servicosRelacionadosSerializado" name="servicosRelacionadosSerializado" />
							<input type="hidden" id="problemaSerializado" name="problemaSerializado" />
							<input type="hidden" id="riscoSerializado" name="riscoSerializado" />
							<input type="hidden" id="idRequisicaoMudanca" name="idRequisicaoMudanca" />
							<input type="hidden" id="idSolicitante" name="idSolicitante" />
							<input type="hidden" id="grupoMudancaSerializado" name="grupoMudancaSerializado" />
							<input type="hidden" id="idProprietario" name="idProprietario" />
							<input type="hidden" id="solicitacaoServicoSerializado" name="solicitacaoServicoSerializado" />
							<input type='hidden' name='escalar' id='escalar' />
							<input type='hidden' name='alterarSituacao' id='alterarSituacao' />
							<input type='hidden' name='idTarefa' id='idTarefa' />
							<input type='hidden' name='acaoFluxo' id='acaoFluxo' />
							<input type='hidden' name='fase' id='fase' />
							<input type="hidden" id="liberacoesRelacionadosSerializado" name="liberacoesRelacionadosSerializado" />
							<input type='hidden' name='faseAtual' id='faseAtual' />
							<input type='hidden' id='fecharItensRelacionados' name="fecharItensRelacionados">
							<input type="hidden" id="iframeSolicitacao" name="iframeSolicitacao" />

							<input type="hidden" id="aprovacaoMudancaServicoSerializado" name="aprovacaoMudancaServicoSerializado" />
							<input type="hidden" id="aprovacaoPropostaServicoSerializado" name="aprovacaoPropostaServicoSerializado" />
							<input type="hidden" id="hiddenDescricaoItemConfiguracao" name="hiddenDescricaoItemConfiguracao" />
							<input type="hidden" id="hiddenIdItemConfiguracao" name="hiddenIdItemConfiguracao" />
							<input type="hidden" id="situacaoLiberacao" name="situacaoLiberacao" />
							<input type="hidden" id="liberacao#idRequisicaoLiberacao" name="liberacao#idRequisicaoLiberacao" />

							<input type='hidden' name='responsavel#idResponsavel' id='responsavel#idResponsavel' />
							<input type='hidden' name='responsavel#nomeResponsavel' id='responsavel#nomeResponsavel' />
							<input type='hidden' name='responsavel#papelResponsavel' id='responsavel#papelResponsavel' />
							<input type='hidden' name='responsavel#telResponsavel' id='responsavel#telResponsavel' />
							<input type='hidden' name='responsavel#nomeCargo' id='responsavel#nomeCargo' />
							<input type='hidden' name='responsavel#emailResponsavel' id='responsavel#emailResponsavel' />
							<input type="hidden" name="responsavel_serialize" id="responsavel_serialize" />

							<input type="hidden" name="idTipoRequisicao" id="idTipoRequisicao" />
							<input type="hidden" id="colAllLOOKUP_PROBLEMA" name="colAllLOOKUP_PROBLEMA"/>

							<div class="col_100">
								<fieldset>
									<label class="" style="font-family: Arial; font-weight: bold;">&nbsp;</label>
								</fieldset>
							</div>

							<div class="col_100">
								<fieldset>
									<label class="campoObrigatorio" style="font-family: Arial; font-weight: bold;"><fmt:message key="contrato.contrato" /></label>
									<div>
										<select id="idContrato" name='idContrato' class=" Valid[Required] Description[<fmt:message key='contrato.contrato' />]"
											onchange="setaValorLookup(this);">
										</select>
									</div>
								</fieldset>
							</div>

							<div class="col_100">
								<div class="col_50">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.solicitante" /></label>
										<div>
											<input class="Valid[Required] Description[solicitacaoServico.solicitante]" id="addSolicitante" name="nomeSolicitante" type="text" readonly="readonly"/>
										</div>
									</fieldset>
								</div>

								<div class= "col_25">
									<fieldset style="height: 55px">
										<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.tipo"/>
											<img src="${ctx}/imagens/add.png" onclick="abrirPopupNovoTipoRequisicaoMudanca()">
										</label>
										<div>
										<select class="Valid[Required] Description[citcorpore.comum.tipo]" id="idTipoMudanca" name='idTipoMudanca' onchange="mostrarCategoria();restoreImpactoUrgenciaPorTipoMudanca();"></select>
										</div>
									</fieldset>
								</div>
								<div class="col_25" id="div_categoria" name="div_categoria" style="display:none">
									<fieldset style="height: 55px">
										<label><fmt:message key="citcorpore.comum.categoria"/></label>
										<div>
											<select id="nomeCategoriaMudanca" name="nomeCategoriaMudanca">
												<option value=''>--<fmt:message key="citcorpore.comum.selecione" />--</option>
												<option value="Importante"><fmt:message key="pesquisaRequisicaoMudanca.categoriaMudancaImportante" /></option>
												<option value="Significativa"><fmt:message key="pesquisaRequisicaoMudanca.categoriaMudancaSignificativa" /></option>
												<option value="Pequena"><fmt:message key="pesquisaRequisicaoMudanca.categoriaMudancaPequena" /></option>
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
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.nomeDoContato" /></label>
												<div>
													<input id="contato" type='text' name="nomeContato" maxlength="70"
													class="Valid[Required] Description[solicitacaoServico.nomeDoContato]" />
												</div>
											</fieldset>
										</div>

										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="requisicaoMudanca.email" /></label>
												<div>
													<input id="emailSolicitante" type='text' name="emailSolicitante" maxlength="120" class="Valid[Required, Email] Description[requisicaoMudanca.email]" />
												</div>
											</fieldset>
										</div>

										<div class="col_25">
											<fieldset>
												<label><fmt:message key="requisicaoMudanca.telefone" /></label>
												<div>
													<input id="telefoneContato" type='text' name="telefoneContato" maxlength="13" class="" />
												</div>
											</fieldset>
										</div>

										<div class="col_25">
											<fieldset>
												<label><fmt:message key="citcorpore.comum.ramal" /></label>
												<div>
													<input id="ramal" type='text' name="ramal" maxlength="4" class="Format[Numero]" />
												</div>
											</fieldset>
										</div>

										<div class="col_25">
											<fieldset style="height: 55px">
												<label class="tooltip_bottom campoObrigatorio" title="<fmt:message key="colaborador.cadastroUnidade"/>"><fmt:message key="unidade.unidade"/>
													<img src="${ctx}/imagens/add.png" onclick="abrirPopupNovaUnidade()">
												</label>
												<div id="divUnidade">

												</div>
											</fieldset>
										</div>

										<div class="col_25">
											<fieldset style="height: 55px">
												<label class="tooltip_bottom " title="<fmt:message key="colaborador.cadastroUnidade"/>">
													<fmt:message key="solicitacaoServico.localidadeFisica"/>
													<img src="${ctx}/imagens/add.png" onclick="abrirPopupNovaLocalidade()">
												</label>
												<div>
													<select name='idLocalidade' id = 'idLocalidade'></select>
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_50">
										<fieldset style="height: 112px">
											<label><fmt:message key="requisicaoMudanca.observacao"/></label>
											<div>
												<textarea id="observacao" class="col_98" name="observacao" maxlength="2000" style="height: 90px; float: right;"></textarea>
											</div>
										</fieldset>
									</div>
								</div>
							</div> <!-- FIM_divInfContato -->

							<div class="col_100">
								<div>
									<h2 class="section"><fmt:message key="requisicaoMudanca.informacaoRequisicao" /></h2>
								</div>

								<div class="col_50"> <!-- Lado Esquerdo da Tela -->
									<div class="col_100" id="div_ehProposta">
										<fieldset>
											<label style='cursor:pointer' id="labelEhProposta"><input type='checkbox' value='S' name='ehPropostaAux'/><fmt:message key="solicitacaoServico.ehProposta"/></label>
										</fieldset>
									</div>
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="baseConhecimento.titulo" /></label>
											<div>
												<input class="Valid[Required] Description[baseConhecimento.titulo]" id="titulo" name="titulo" maxlength="100" type="text"/>
											</div>
										</fieldset>
									</div>

									<div style="width: 100%; float: left;">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.descricao" /></label>
												<div>
													<textarea class="Valid[Required] Description[citcorpore.comum.descricao]" id="descricao" class="col_100" name="descricao" rows="4" maxlength="255" style="height: 250px;"></textarea>
												</div>
												<div style="visibility: none" id="DescricaoAuxliar"></div>
										</fieldset>
									</div>
									<div id="divNotificacaoEmail" class="col_50">
										<fieldset>
											<label><fmt:message key="requisicaoMudanca.notificacaoporEmail"/></label>
											<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailCriacao' checked="checked"/><fmt:message key="requisicaoMudanca.enviaEmailCriacao"/></label><br>
											<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailFinalizacao' checked="checked"/><fmt:message key="requisicaoMudanca.enviaEmailFinalizacao"/></label><br>
											<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailGrupoComite' checked="checked"/><fmt:message key="requisicaoMudanca.enviarEmailComiteConsultivoMudanca"/></label><br>
											<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailAcoes'/><fmt:message key="requisicaoMudanca.enviaEmailAcoes"/></label>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset style="FONT-SIZE: xx-small;">
											<label style="cursor: pointer;"><fmt:message key="citcorpore.comum.categoriaSolucao"/></label>
											<div>
												<select name='idCategoriaSolucao'></select>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="col_50"><!-- Lado Direito da Tela -->
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label class='campoObrigatorio'> <fmt:message key="grupo.execucaoRequisicaoMudanca" />
													<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
													<img src="${ctx}/imagens/add.png" onclick="abrirPopupNovoGrupoExecutor()">
												</label>
												<div>
													<select name='idGrupoAtual' id='idGrupoAtual'>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="grupo.comiteConsultivoMudanca" />
													<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
													<img src="${ctx}/imagens/add.png" onclick="abrirPopupNovoGrupoExecutor()">
												</label>
												<div>
													<select name='idGrupoComite' id='idGrupoComite'>
													</select>
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_100">
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.impacto" /></label>
												<div>
													<select onchange="atualizaPrioridade()" id="nivelImpacto" name="nivelImpacto">
														<option value="B"><fmt:message key="citcorpore.comum.baixa"/></option>
														<option value="M"><fmt:message key="citcorpore.comum.media"/></option>
														<option value="A"><fmt:message key="citcorpore.comum.alta"/></option>
													</select>
												</div>
											</fieldset>
										</div>

										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.urgencia"/></label>
												<div>
													<select onchange="atualizaPrioridade()" id="nivelUrgencia" name="nivelUrgencia">
														<option value="B"><fmt:message key="citcorpore.comum.baixa"/></option>
														<option value="M"><fmt:message key="citcorpore.comum.media"/></option>
														<option value="A"><fmt:message key="citcorpore.comum.alta"/></option>
													</select>
												</div>
											</fieldset>
										</div>

										<div class="col_33">
											<fieldset>
												<label><fmt:message key="prioridade.prioridade" /></label>
												<div>
													<input id="prioridade" name="prioridade" type="text" readonly="readonly" value="5" />
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_100">
										<div class="col_25">
											<fieldset>
												<label><fmt:message key="requisicaoMudanca.inicioAgendada"/></label>
												<div>
													<input id="dataHoraInicioAgendada" name="dataHoraInicioAgendada" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" />
												</div>
											</fieldset>
										</div>

										<div class="col_25">
											<fieldset>
												<label><fmt:message key="requisicaoMudanca.horaAgendamentoInicial"/></label>
												<div>
													<input type='text' name="horaAgendamentoInicial" id="horaAgendamentoInicial" maxlength="5" size="5" maxlength="5" class="Valid[Hora] Format[Hora]" />
												</div>
											</fieldset>
										</div>

										<div class="col_25">
											<fieldset>
												<label><fmt:message key="requisicaoMudanca.terminoAgendada"/></label>
												<div>
													<input id="dataHoraTerminoAgendada" name="dataHoraTerminoAgendada" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" />
												</div>
											</fieldset>
										</div>

										<div class="col_25">
											<fieldset>
												<label><fmt:message key="requisicaoMudanca.horaAgendamentoFinal"/></label>
												<div>
													<input type='text' name="horaAgendamentoFinal" id="horaAgendamentoFinal" maxlength="5" size="5" maxlength="5" class="Valid[Hora] Format[Hora]" />
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_100">
										<div class="col_33">
											<fieldset>
												<label><fmt:message key="requisicaoMudanca.aceitacao"/></label>
												<div>
													<input id="dataAceitacao" name="dataAceitacao" type="text" size="10" maxlength="10" class="Format[Date] Valid[Date] text datepicker" />
												</div>
											</fieldset>
										</div>

										<div class="col_33">
											<fieldset>
												<label><fmt:message key="requisicaoMudanca.votacao"/></label>
												<div>
													<input id="dataVotacao" name="dataVotacao" type="text" size="10" maxlength="10" class="Format[Date] Valid[Date] text datepicker" />
												</div>
											</fieldset>
										</div>

										<div class="col_33">
											<fieldset>
												<label><fmt:message key="requisicaoMudanca.conclusao"/></label>
												<div>
													<input id="dataHoraConclusao" name="dataHoraConclusao" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" />
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_50">
										<fieldset>
											<label style="cursor: pointer;"><fmt:message key='gerenciaservico.agendaratividade.crupoatividades' /></label>
											<div>
												<select name='idGrupoAtvPeriodica' id='idGrupoAtvPeriodica'></select>
											</div>
										</fieldset>
									</div>
								</div>
							</div>
							<div id="requisicaMudancaStatus" class="col_100">
								<hr/>
								<%
									if (idRequisicaoMudanca > 0) {
								%>
								<label style="font-family: Arial; font-weight: bold; font-size: 13px;">
									<fmt:message key="requisicaMudanca.status" />
								</label>
								<label id="statusSetado" style="cursor: pointer;"></label>

								<label id="statusCancelado" style="cursor: pointer;">
									<input type="radio" id="status" name="status" value="Cancelada" /><fmt:message key="citcorpore.comum.cancelada" />
								</label>
								<%
									}
								%>
							</div>
							<div class="col_100">
								<fieldset>
									<label><fmt:message key="solicitacaoServico.fechamento" /></label>
									<div>
										<textarea id="fechamento" name="fechamento" cols='70' rows='3' class="Description[Resposta]"></textarea>
									</div>
								</fieldset>
							</div>
							<div>
								<div class="col_100" id='divBotaoAddRegExecucao'>
									<fieldset style="FONT-SIZE: xx-small;">
										<button type='button' name='btnAddRegExec' id='btnAddRegExec' onclick='mostrarEscondeRegExec()'><fmt:message key="solicitacaoServico.addregistroexecucao_mais" /></button>
									</fieldset>
								</div>
								<div class="col_100">
									<fieldset style="FONT-SIZE: xx-small;">
										<label id='lblMsgregistroexecucao' style='display:none'><font color='red'><b><fmt:message key="solicitacaoServico.msgregistroexecucao" /></b></font></label>
										<div id='divMostraRegistroExecucao' style='display:none'>
											<textarea id="registroexecucao" name="registroexecucao" cols='70' rows='5' maxlength="4000" class="Description[citcorpore.comum.resposta]"></textarea>
										</div>
									</fieldset>
								</div>
								<div class="col_100" style="overflow : auto; max-height: 400px">
									<fieldset>
										<div id="tblOcorrencias"></div>
									</fieldset>
								</div>
							</div>
							<div id="btRelatorio">
								<%
									if (idRequisicaoMudanca > 0) {
										if(request.getParameter("editar") != null && request.getParameter("editar").toString().equalsIgnoreCase("N")) {
								%>
								<div style="margin: 10px !important; text-align: right;float: right;"><b><fmt:message key="requisicaMudanca.relatorio" /></b>
									<br>
									<button id="btnRelatorio" type='button' name='btnRelatorio' class="light" onclick="gerarRelatorioPDF();" style="margin: 10px !important; text-align: right;float: right;">
										<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
										<span><fmt:message key="citcorpore.comum.gerarRelatorioRegistroExecucao"/></span>
									</button>
								</div>
								<%
										} else {
								%>
								<button id="btnRelatorio" type='button' name='btnRelatorio' class="light" onclick="gerarRelatorioPDF();" style="margin: 10px !important; text-align: right;float: right;">
									<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
										<span><fmt:message key="citcorpore.comum.gerarRelatorioRegistroExecucao"/></span>
									</button>
								<%
										}
									}
								%>
							</div>

							<div id="abas" class="formRelacionamentos">
								<div id="tabs" class="block">
									<ul class="tab_header">
										<li id="abaRelacionarAprovacoesProposta"><a href="#relacionarAprovacoesProposta"><fmt:message key="requisicaoMudanca.relacionarAprovacoesProposta"/></a></li>
										<li id="abaRelacionarAprovacoesMudanca"><a href="#relacionarAprovacoesMudanca"><fmt:message key="requisicaoMudanca.relacionarAprovacoesMudanca"/></a></li>
										<li><a href="#relacionaIcs"><fmt:message key="requisicaoMudanca.relacionarICs"/></a></li>
										<li><a href="#relacionaServicos"><fmt:message key="requisicaoMudanca.relacionarServicos"/></a></li>
										<li><a href="#relacionarProblemas"><fmt:message key="requisicaoMudanca.relacionarProblemas"/></a></li>
										<li><a href="#relacionarIncidentes"><fmt:message key="requisicaoMudanca.relacionarIncidentes"/></a></li>
										<li><a href="#relacionarRisco"><fmt:message key="requisicaoMudanca.relacionarRiscos"/></a></li>
										<li><a href="#relacionarLiberacaoMudanca"><fmt:message key="requisicaoMudanca.relacionarLiberacaoMudanca"/></a></li>
										<li><a href="#responsavels"><fmt:message key="requisicaoLiberacao.papeisResponsabilidades"/></a></li>
										<li><a href="#checklist"><fmt:message key="requisicaMudanca.checklist"/></a></li>
										<li><a href="#relacionarHistoricoMudanca"><fmt:message key="requisicaoMudanca.historicoMudanca"/></a></li>
										<li><a href="#adicionarPlanoDeReversao"><fmt:message key="requisicaoMudanca.adicionarPlanoDeReversao"/></a></li>
										<li><a href="#relacionarGrupo"><fmt:message key="requisicaoMudanca.relacionarGrupo"/></a></li>
									</ul>

									<div id="relacionaIcs">
										<div style="width: 15%; float: left; <%=display%>" align="center">
											<label style="cursor: pointer;" onclick='abrirModalPesquisaItemConfiguracao();'>
												<fmt:message key="requisicaoMudanca.adicionarItemConfiguracao"/>
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
											</label>
										</div>

										<div style="width: 99%; height : 30px; float: left;"></div>

										<div class="formBody">
											<table id="tblICs" class="table table-bordered table-striped">
												<tr>
													<th width="15%"><fmt:message key="parametroCorpore.id"/></th>
													<th width="35%"><fmt:message key="citcorpore.comum.nome"/></th>
													<th width="50%"><fmt:message key="citcorpore.comum.descricao"/></th>
													<th width="50%"><fmt:message key="start.instalacao.informacoesGerais"/></th>
													<th width="50%" style="<%=display%>"><fmt:message key="botaoacaovisao.excluir"/></th>
												</tr>
											</table>
										</div>
									</div>

									<div id="relacionaServicos">
										<div style="width: 15%; float: left; <%=display%>" align="center">
											<label style="cursor: pointer;" onclick='adicionarServico();'>
												<fmt:message key="requisicaoMudanca.adicionarServico"/>
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
											</label>
										</div>
										<div style="width: 99%; height : 30px; float: left;"></div>
										<div class="formBody">
											<table id="tblServicos" class="table table-bordered table-striped">
												<tr>
													<th height="10px" width="15%"><fmt:message key="parametroCorpore.id"/></th>
													<th height="10px" width="35%"><fmt:message key="citcorpore.comum.nome"/></th>
													<th height="10px"width="50%"><fmt:message key="citcorpore.comum.descricao"/></th>
													<th height="10px"width="50%"><fmt:message key="citcorpore.comum.mapa"/></th>
												</tr>
											</table>
										</div>
									</div>

									<div id="relacionarProblemas">
										<div style="width: 15%; float: left; <%=display%>" align="center">
											<label style="cursor: pointer;" onclick='adicionarProblema();'>
												<fmt:message key="requisicaoMudanca.adicionarProblema"/>
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
											</label>
										</div>
										<div style="width: 99%; height : 30px; float: left;"></div>
										<div class="formBody">
											<table id="tblProblema" class="table table-bordered table-striped">
												<tr>
													<th height="10px" width="15%"><fmt:message key="parametroCorpore.id"/></th>
													<th height="10px" width="35%"><fmt:message key="problema.titulo"/></th>
													<th height="10px"width="50%"><fmt:message key="problema.status"/></th>
													<th height="10px"width="50%"></th>
													<th height="10px"width="50%"></th>
												</tr>
											</table>
										</div>
									</div>

									<div id="relacionarIncidentes">
										<div style="width: 15%; float: left; <%=display%>" align="center">
											<label id="addSolicitacaoServico" style="cursor: pointer;">
												<fmt:message key="requisicaMudanca.adicionar_incidente"/>
												<img id="addImgSolicitacaoServico" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
											</label>
										</div>
										<div style="width: 15%; float: left; height: 27px;<%=display%>" align="center">
											<label style="cursor: pointer;" onclick="abrirPopupNovaSolicitacaoServico();">
												<fmt:message key="requisicaMudanca.criar_novo_incidente"/>
												<img src="${ctx}/imagens/add.png" />
											</label>
										</div>

										<div style="width: 99%; height : 30px; float: left;"></div>
										<div class="formBody">
											<table id="tblSolicitacaoServico" class="table table-bordered table-striped">
												<tr>
													<th height="10px" width="20%"><fmt:message key="requisicaMudanca.numero_solicitacao"/></th>
													<th height="10px" width="850%"><fmt:message key="citcorpore.comum.descricao"/></th>
													<th height="10px" width="15%"></th>
												</tr>
											</table>
										</div>
									</div><!-- FIM_relacionarIncidentes -->

									<div id="relacionarRisco">
										<!-- ANALISES DE IMPACTO, RISCOS E ROLLBACK -->
										<div style="width: 15%; float: left; <%=display%>" align="center">
											<label style="cursor: pointer;" onclick='adicionarRisco();'>
												<fmt:message key="requisicaoMudanca.adicionarRisco"/>
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
											</label>
										</div>
										<div style="width: 15%; float: left; height: 27px; <%=display%>" align="center">
											<label style="cursor: pointer;" onclick="chamaPopupCadastroRisco();">
												<fmt:message key="requisicaMudanca.criar_novo_risco"/>
												<img src="${ctx}/imagens/add.png" />
											</label>
										</div>
										<div style="width: 99%; height : 30px; float: left;"></div>
										<div class="formBody">
											<table id="tblRisco" class="table table-bordered table-striped">
												<tr>
													<th height="10px" width="15%"><fmt:message key="parametroCorpore.id"/></th>
													<th height="10px" width="35%"><fmt:message key="risco.risco"/></th>
													<th height="10px"width="50%"><fmt:message key="risco.detalhamento"/></th>
												</tr>
											</table>
										</div>
										<fieldset>
											<label><fmt:message key="requisicaoMudanca.razaoMudanca"/></label>
											<textarea id="razaoMudanca" name="razaoMudanca" maxlength="200" style="height: 100px; width: 99%;"></textarea>

											<label><fmt:message key="requisicaoMudanca.analiseImpacto"/></label>
											<textarea id="analiseImpacto" name="analiseImpacto" maxlength="255" style="height: 100px; width: 99%;"></textarea>

											<label><fmt:message key="requisicaoMudanca.analiseRiscos"/></label>
											<textarea id="risco" name="risco" maxlength="255" style="height: 100px; width: 99%;"></textarea>
										</fieldset>
									</div><!-- FIM_relacionarRisco-->

									<div id="relacionarAprovacoesProposta">
										<div id="gridAprovacoesProposta">
											<table class="table table-bordered table-striped" id="tabelaAprovacoesProposta">
												 <tr>
													<th style="width: 1%;"></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.nome" /></th>
													<th style="width: 50%;"><fmt:message key="requisicaoMudanca.votacao" /></th>
													<th style="width: 49,5%;"><fmt:message key="comentarios.comentarios" /></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.datahora" /></th>
												</tr>
											</table>
										</div>
										<div class="col_50" id="quantidadePorVotoAprovadaProposta"></div>
										<div class="col_50" id="quantidadePorVotoRejeitadaProposta"></div>
									</div>
									<div id="relacionarAprovacoesMudanca">
										<div id="gridAprovacoesMudanca">
											<table class="table table-bordered table-striped" id="tabelaAprovacoesMudanca">
												 <tr>
													<th style="width: 1%;"></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.nome" /></th>
													<th style="width: 50%;"><fmt:message key="requisicaoMudanca.votacao" /></th>
													<th style="width: 49,5%;"><fmt:message key="comentarios.comentarios" /></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.datahora" /></th>
												</tr>
											</table>
										</div>
										<div class="col_50" id="quantidadePorVotoAprovadaMudanca"></div>
										<div class="col_50" id="quantidadePorVotoRejeitadaMudanca"></div>
									</div>
									<div id="relacionarLiberacaoMudanca">
										<div style="width: 15%; float: left; <%=display%>" align="center">
											<label style="cursor: pointer;" onclick='adicionarLiberacao();'>
												<fmt:message key="requisicaoMudanca.adicionarliberacao"/>
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
											</label>
										</div>
										<div style="width: 99%; height : 30px; float: left;"></div>
										<div class="formBody">
											<table id="tblLiberacao" class="table table-bordered table-striped">
												<tr>
													<th height="10px" width="15%"><fmt:message key="parametroCorpore.id"/></th>
													<th height="10px" width="30%"><fmt:message key="citcorpore.comum.titulo"/></th>
													<th height="10px"width="20%"><fmt:message key="citcorpore.comum.descricao"/></th>
													<th height="10px"width="20%"><fmt:message key="situacaoLiberacaoMudanca.status"/></th>
												</tr>
											</table>
										</div>
									</div>

									<div id="relacionarGrupo">
										<div style="width: 15%; float: left; <%=display%>" align="center">
											<label style="cursor: pointer;" onclick='adicionarGrupo();'>
												<fmt:message key="requisicaoMudanca.adicionarGrupo"/>
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
											</label>
										</div>
										<div style="width: 99%; height : 30px; float: left;"></div>
										<div class="formBody">
											<table id="tblGrupo" class="table table-bordered table-striped">
												<tr>
													<th height="10px" width="15%"><fmt:message key="parametroCorpore.id"/></th>
													<th height="10px" width="85%"><fmt:message key="pesquisa.grupo"/></th>
												</tr>
											</table>
										</div>
									</div>
									<div id="responsavels">
										<div style="width: 20%; float: left; <%=display%>" align="center">
											<label style="cursor: pointer;" onclick='adicionarResponsavel();'> <fmt:message key="liberacao.adicionarPapeisResponsabilidades" />
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
											</label>
										</div>
										<div style="width: 99%; height: 30px; float: left;"></div>
											<div class="formBody">
												<table id="tblResponsavel" class="table table-bordered table-striped">
													<tr>
														<th height="10px" width="1%"></th>
														<th height="10px" width="5%"><fmt:message key="parametroCorpore.id" /></th>
														<th height="10px" width="25%"><fmt:message key="citcorpore.comum.nome" /></th>
														<th height="10px" width="15%"><fmt:message key="citcorpore.comum.cargo" /></th>
														<th height="10px" width="20%"><fmt:message key="citcorpore.comum.telefone" /></th>
														<th height="10px" width="20%"><fmt:message key="citcorpore.comum.email" /></th>
														<th height="10px"><fmt:message key="citcorpore.comum.papel"/></th>
													</tr>
												</table>
											</div>
									</div>
									<div id="relacionarHistoricoMudanca">
										<div class="formBody">
											<input type='hidden' id='idHistoricoMudanca' name='idHistoricoMudanca'/>
											<input type='hidden' id='idItemConfiguracao' name='idItemConfiguracao'/>
											<input type='hidden' id="baselinesSerializadas" name='baselinesSerializadas'/>
											<div id="contentBaseline">

											</div>
										</div>
									</div>
									<div id="adicionarPlanoDeReversao">
										<cit:uploadPlanoDeReversaoControl id="uploadPlanoDeReversao" title="Anexos"	style="height: 100px; width: 98%; border: 1px solid black;" form="form" action="/pages/uploadPlanoDeReversao/uploadPlanoDeReversao.load" disabled="<%=editar%>" />
									</div>

									<!-- INICIO CHECKLIST -->
									<div id="checklist">
										<div class="col_100" id='divInformacoesComplementares' style='display: block; height: 1024px'>
											<iframe id='fraInformacoesComplementares' name='fraInformacoesComplementares' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border: none;'></iframe>
										</div>
									</div>
								</div><!-- FIM_tabs -->
							</div>
							<div id="divBotoes" class="formFooter">
								<%
									if (tarefaAssociada.equalsIgnoreCase("N")) {
								%>
								<button type='button' name='btnGravar' class="light" onclick='gravar()'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" /></span>
								</button>
								<%
									} else {
								%>
								<button type='button' name='btnGravarEContinuar' class="light" onclick='gravarEContinuar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravarEContinuar" /></span>
								</button>
								<button type='button' name='btnGravarEFinalizar' class="light" onclick='gravarEFinalizar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/cog_2.png">
									<span><fmt:message key="citcorpore.comum.gravarEFinalizar" /></span>
								</button>
								<%
									}
								%>

								<button type='button' name='btnLimpar' class="light" onclick='limpar(document.form);'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span>
										<fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
							</div><!-- FIM_formFooter -->
						</form><!-- FIM_form -->
					</div>
				</div>
			</div>
		</div><!-- FIM_classForm -->
	</div>

	<!-- MAPA DESENHO SERVIÇO -->
	<div id="popupCadastroRapido">
		<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
	</div>

	<!-- LOOKUPS -->
	<div id="POPUPITEMCONFIGURACAO" title="<fmt:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP_ICS">
 		<table>
			<tr>
				<td>
					<h3><fmt:message key="eventoItemConfiguracao.itensConfiguracao" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaItem' style="width: 100%;">
			<cit:findField formName='formPesquisaItem' lockupName='LOOKUP_ITEMCONFIGURACAO_ATIVO' id='LOOKUP_ITEMCONFIGURACAO'
						top='0' left='0' len='550' heigth='480' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<div id="POPUPDESCRICAOITEMCONFIGURACAO" title="<fmt:message key="requisicaoMudanca.oQueSeraMudadoNesteItem" />" class="POPUP_LOOKUP_ITEMCONFIGURACAO">
		<div class="row-fluid">
			<div class="span12 innerAll">
				<div class="row-fluid">
					<div class="span12">
						<input type="text" id="descricaoItemConfiguracao" maxlength="100" name="descricaoItemConfiguracao" />
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<button type='button' name="btOk" class="light img_icon has_text rFloat" onclick="fecharPopupDescricaoItemConf();">
							<span><fmt:message key="requisicaMudanca.botaoOk" /></span>
						</button>
					</div>
				</div>
			</div>
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
									<img id='botaoSolicitante' src="${ctx}/imagens/add.png" onclick="addSolicitante()">
									<img id="btHistoricoSolicitante" style="cursor: pointer; display: none;" src="${ctx}/template_new/images/icons/small/grey/month_calendar.png">
							</label>
						</div>
						<form name='formPesquisaColaborador' style="width: 500px">
							<cit:findField formName='formPesquisaColaborador'
								lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOL_CONTRATO' top='0'
								left='0' len='550' heigth='370' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_SERVICO" title="<fmt:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP_SERVICO">
		<table>
			<tr>
				<td>
					<h3 align="center"><fmt:message key="servico.servico" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisa' style="width: 100%;">
			<cit:findField formName='formPesquisa' lockupName='LOOKUP_SERVICO' id='LOOKUP_SERVICO' top='0' left='0' len='550' heigth='490' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<div id="POPUP_RISCO" title="<fmt:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP_RISCO">
		<table>
			<tr>
				<td>
					<h3 align="center"><fmt:message key="risco.risco" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaRisco' style="width: 100%;">
			<cit:findField formName='formPesquisaRisco' lockupName='LOOKUP_RISCO' id='LOOKUP_RISCO' top='0' left='0' len='550' heigth='430' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<div id="POPUP_LIBERACAO" title="<fmt:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP_LIBERACAO">
		<table>
			<tr>
				<td>
					<h3 align="center"><fmt:message key="liberacao" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaLiberacao' style="width: 100%;">
			<cit:findField formName='formPesquisaLiberacao' lockupName='LOOKUP_LIBERACAO_MUDANCA' id='LOOKUP_LIBERACAO_MUDANCA' top='0' left='0' len='550' heigth='440' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<div id="POPUP_GRUPO" title="<fmt:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP">
		<table>
			<tr>
				<td>
					<h3 align="center"><fmt:message key="grupo.grupo" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaGrupo' style="width: 95%;">
			<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<div id="POPUP_PROBLEMA" title="<fmt:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP">
		<table>
			<tr>
				<td>
					<h3 align="center"><fmt:message key="problema.problema" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaProblema' style="width: 100%;">
			<cit:findField formName='formPesquisaProblema' lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true'/>
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

	<div id="POPUP_INFO_BASELINE" title="" style="overflow: hidden;">
		<div class="toggle_container">
			<div id='divMensagemInsercaoBaseline' class="section" style="overflow: hidden; font-size: 24px;">

			</div>
			<button type="button" onclick='$("#POPUP_INFO_BASELINE").dialog("close");'>
				<fmt:message key="citcorpore.comum.fechar" />
			</button>
		</div>
	</div>

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
					<form name='formPesquisaSolicitacaoServico' style="width: 790px">
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

	<div id="POPUP_EDITARPROBLEMA" style="overflow: hidden;" title="<fmt:message key="problema.problema"/>">
		<iframe id='iframeEditarProblema' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()"></iframe>
	</div>

	<div id="POPUP_RESPONSAVEL" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaResponsavel' style="width: 790px">
							<cit:findField formName='formPesquisaResponsavel' lockupName='LOOKUP_RESPONSAVEL' id='LOOKUP_RESPONSAVEL' top='0' left='0' len='800' heigth='410' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_INFORMACOESITEMCONFIGURACAO">
		<iframe id='fraInfosItemConfig' src='about:blank' width="100%" height="99%"></iframe>
	</div>

	<div id="POPUP_NOVOCOLABORADOR" style="overflow: hidden;" title="<fmt:message key="colaborador.colaborador"/>">
		<iframe id='iframeNovoColaborador' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()"></iframe>
	</div>

	<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Div popup cadastro nova solicitação serviço. -->
	<div id="POPUP_NOVASOLICITACAOSERVICO" style="overflow: hidden;" title="<fmt:message key="solicitacaoServico.solicitacao"/>">
		<iframe id='iframeNovaSolicitacao' src='about:blank' width="100%" height="100%" onload="resize_iframe()"></iframe>
	</div>

	<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
	<div id="POPUP_NOVOTIPOREQUISICAOMUDANCA" style="overflow: hidden;" title="<fmt:message key="tipoMudanca.tipoDeMudanca"/>">
		<iframe id='iframeNovoTipoRequisicaoMudanca' src='about:blank' width="100%" height="100%" onload="resize_iframe()"></iframe>
	</div>

	<div id="POPUP_NOVAUNIDADE" style="overflow: hidden;" title="<fmt:message key="unidade.unidade"/>">
		<iframe id='iframeNovaUnidade' src='about:blank' width="100%" height="100%" onload="resize_iframe()"></iframe>
	</div>

	<div id="POPUP_NOVALOCALIDADE" style="overflow: hidden;" title="<fmt:message key="localidadeFisica.localidadeFisica"/>">
		<iframe id='iframeNovaLocalidade' src='about:blank' width="100%" height="100%" onload="resize_iframe()"></iframe>
	</div>

	<div id="POPUP_NOVOGRUPOEXECUTOR" style="overflow: hidden;" title="<fmt:message key="grupo.grupo"/>">
		<iframe id='iframeNovoGrupoExecutor' src='about:blank' width="100%" height="100%" onload="resize_iframe()"></iframe>
	</div>

	<div id="POPUP_PESQUISAITEMCONFIGURACAO" title="<fmt:message key="itemConfiguracao.pesquisa" />" class="POPUP_LOOKUP_SERVICO">
		<iframe id='framePesquisaItemConfiguracao' src='about:blank' width="100%" height="99%" onload="resize_iframe()"></iframe>
	</div>
</body>
</html>
