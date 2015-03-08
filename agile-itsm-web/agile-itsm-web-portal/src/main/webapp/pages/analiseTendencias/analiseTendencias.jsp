<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
<head>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
	<%@include file="/novoLayout/common/include/titulo.jsp"%>
	<link type="text/css" rel="stylesheet"
		href="../../novoLayout/common/include/css/template.css" />
			<link href="../../novoLayout/common/theme/scripts/plugins/tables/DataTables/media/css/DT_bootstrap.css" rel="stylesheet" />

</head>

	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title=""
		style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
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
				<div class="separator top"></div>
				<div class="row-fluid">
					<div class="innerLR">
						<div class="widget">
							<div class="widget-head">
								<h4 class="heading">
									<fmt:message key="problema.analiseTendencias.titulo"/>
								</h4>
							</div>

						<form name='form' action='${ctx}/pages/analiseTendencias/analiseTendencias'>
							<input type="hidden" id='idServico' name='idServico'>
							<input type="hidden" id='idEmpregado' name='idEmpregado'>
							<input type="hidden" id='idItemConfiguracao' name='idItemConfiguracao'>

							<input type="hidden" id='idRelatorio' name='idRelatorio' />
							<input type="hidden" id='tipoRelatorio' name='tipoRelatorio' />

							<!--Datas-->
							<div class='row-fluid'>
								<div class="separator top"></div>
								<div class='span3'>
									<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
									<input style="cursor: pointer; background: #FFF;" type="text" class="span5 citdatepicker" id="dataInicio" name="dataInicio" maxlength="10" required="required" >
									&nbsp;<fmt:message key='citcorpore.comum.a' />&nbsp;
									<input style="cursor: pointer; background: #FFF;" type="text" class="span5 citdatepicker" id="dataFim" name="dataFim" maxlength="10" required="required" >
								</div>
								<!--Contrato-->
								<div class='span8'>
									<label class="strong campoObrigatorio"><fmt:message key="contrato.contrato" /></label>
									<select id="idContrato" name="idContrato" class="span12 Description[citcorpore.comum.nome]"></select>
								</div>
							</div>

							<!--Linha 1-->
							<div class='row-fluid'>
								<div class="separator top"></div>
									<div class="span2">
										<label  class="strong "><fmt:message key="solicitacaoServico.tipo" /></label>
										<select class="span12" name="idTipoDemandaServico" id="idTipoDemandaServico"  onchange="montaParametrosAutocompleteServico();limparServico()"></select>
									</div>

									<div class="span4" id='divNomeDoServico'>
										<label class="strong">
											<fmt:message key="servico.nome" />&nbsp;<span class="limpar" data-input="servico" data-id="idServico"><img src="${ctx}/imagens/borracha.png" /></span>
										</label>
										<input class="span12" type="text" onfocus='abrePopupServico();' name="servico" id="servico" required="required" placeholder="<fmt:message key="servico.digiteNomeServico"/>">
									</div>

									<div class="span4" id='divNomeDoGrupoExecutor'>
										<label class="strong">
											<fmt:message key="citcorpore.comum.grupoExecutor"/>
										</label>
										<select id="idGrupoExecutor" name="idGrupoExecutor" class="span12 Description[citcorpore.comum.nome]">
										</select>
									</div>
							</div>

							<!--Linha 2-->
							<div class='row-fluid'>
								<div class="separator top"></div>
								<!--Solicitante-->
								<div class="span4" id='divNomeDousuarioSolicitante'>
									<label class="strong">
										<fmt:message key="solicitacaoServico.solicitante"/>&nbsp;<span class="limpar" data-input="nomeSolicitante" data-id="idEmpregado"><img src="${ctx}/imagens/borracha.png" /></span>
									</label>
									<input class="span12" type="text" onfocus='abrePopupSolicitante();' id="nomeSolicitante" name="nomeSolicitante" placeholder="<fmt:message key="solicitacaoServico.digiteNomeSolicitante"/>"/>
								</div>

								<!--Urgência-->
								<div class='span2'>
									<label class="strong "><fmt:message key="solicitacaoServico.urgencia"/></label>
									<select id="urgencia" name="urgencia" class="span12 Description[citcorpore.comum.nome]">
									</select>
								</div>

								<!--Impacto-->
								<div class="span2">
									<label class="strong "><fmt:message key="solicitacaoServico.impacto"/></label>
									<select id="impacto" name="impacto" class="span12 Description[citcorpore.comum.nome]">
									</select>
								</div>

								<!--Item de Configuração-->
								<div class="span2">
									<div class="span12" id='divNomeDoGrupoExecutor'>
										<label class="strong">
											<fmt:message key="solicitacaoServico.itemConfiguracao"/>&nbsp;<span class="limpar" data-input="nomeItemConfiguracao" data-id="idItemConfiguracao"><img src="${ctx}/imagens/borracha.png" /></span>
										</label>
										<input class="span12" type="text" onfocus='abrePopupIC();' id="nomeItemConfiguracao" name="nomeItemConfiguracao" />
									</div>
								</div>
							</div>

							<div class='row-fluid'>
								<div class="separator top"></div>
								<!--Causa Incidente-->
								<div class="span2">
									<label class="strong "><fmt:message key="solicitacaoServico.causa"/></label>
									<select id="idCausaIncidente" name="idCausaIncidente" class="span12 Description[citcorpore.comum.nome]">
									</select>
								</div>

								<!--Qtd critica-->
								<div class="span2">
									<label class="strong campoObrigatorio"><fmt:message key="problema.analiseTendencias.qtdeCritica"/></label>
									<input class="span12 Format[Numero]" type="numeric" name="qtdeCritica" id="qtdeCritica" required="required" maxlength="9">
								</div>
							</div>

							<div class='row-fluid'>
								<div class='span12'>
									<div class="separator top"></div>
									<!--Botão-->
									<div class="span2">
										<button class="btn btn-default btn-primary" type="button" onclick="pesquisarTendencia();" id="btnSelecionar" name='"btnSelecionar"'>
												<span>
													<fmt:message key="citcorpore.comum.selecionar"/>
												</span>
										</button>
										<div class="separator top"></div>
										<div class="separator top"></div>
									</div>
								</div>
							</div>

							<div class="widget-body result hide">

								<div class="tabsbar">
									<ul>
										<li class="active idTab" id='servico'><a href="#tab1-3" data-toggle="tab"><span><fmt:message key="citcorpore.comum.servico"/></span></a></li>
										<li class="idTab" id='causa'><a href="#tab2-3" data-toggle="tab"><span><fmt:message key="solicitacaoServico.causa"/></span></a></li>
										<li class="idTab" id='itemConfig'><a href="#tab3-3" data-toggle="tab"><span><fmt:message key="solicitacaoServico.itemConfiguracao"/></span></a></li>
									</ul>
								</div>

								<div class="tab-content">
									<div class="tab-pane active" id="tab1-3">
										<div class='row-fluid'>
											<div class='span12'>
												<div class='row-fluid'>
													<div class='span12'>
														<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
															<table class="table table-hover table-bordered span12" id="tblServicos">
																<tr>
																	<th style="width: 5% !important;"><fmt:message key="lookup.id"/></th>
																	<th style="width: 45%;"><fmt:message key="citcorpore.comum.servico"/></th>
																	<th style="width: 7%;"><fmt:message key="problema.analiseTendencias.qtdeCritica"/></th>
																	<th style="width: 12%;"><fmt:message key="plano.melhoria.acoes"/></th>
																</tr>
															</table>
														</fieldset>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="tab-pane" id="tab2-3">
										<div class='row-fluid'>
											<div class='span12'>
												<div class='row-fluid'>
													<div class='span12'>
														<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
															<table class="table table-hover table-bordered span12" id="tblCausa">
																<tr>
																	<th style="width: 5% !important;"><fmt:message key="lookup.id"/></th>
																	<th style="width: 45%;"><fmt:message key="problema.causa"/></th>
																	<th style="width: 7%;"><fmt:message key="problema.analiseTendencias.qtdeCritica"/></th>
																	<th style="width: 12%;"><fmt:message key="plano.melhoria.acoes"/></th>
																</tr>
															</table>
														</fieldset>
													</div>
												</div>
											</div>
										</div>
									</div>

									<div class="tab-pane" id="tab3-3">
										<div class='row-fluid'>
											<div class='span12'>
												<div class='row-fluid'>
													<div class='span12'>
														<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
															<table class="table table-hover table-bordered span12" id="tblItemConfiguracao">
																<tr>
																	<th style="width: 5% !important;"><fmt:message key="lookup.id"/></th>
																	<th style="width: 45%;"><fmt:message key="solicitacaoServico.itemConfiguracao"/></th>
																	<th style="width: 7%;"><fmt:message key="problema.analiseTendencias.qtdeCritica"/></th>
																	<th style="width: 12%;"><fmt:message key="plano.melhoria.acoes"/></th>
																</tr>
															</table>
														</fieldset>
													</div>
												</div>
											</div>
										</div>
									</div>


								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_SERVICO" class="hide" title="<fmt:message key="servico.servico" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formServico' style="width: 540px">
							<cit:findField formName='formServico'
								lockupName='LOOKUP_SERVICO' id='LOOKUP_SERVICO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_SOLICITANTE" class="hide" title="<fmt:message key="citcorpore.comum.pesquisacolaborador" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<cit:findField formName='formPesquisaUsuario'
								lockupName='LOOKUP_SOLICITANTE' id='LOOKUP_SOLICITANTE' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_ITEMCONFIG" class="hide" title="<fmt:message key="citcorpore.comum.identificacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisa' style="width: 540px">
							<cit:findField formName='formPesquisa'
	 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal hide fade in" id="modal_cadastrarProblema" tabindex="-1"  data-backdrop="static" data-keyboard="false">
			<!-- Modal heading -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3><fmt:message key="problema.problema" /></h3>
			</div>
			<!-- // Modal heading END -->
			<!-- Modal body -->
			<div class="modal-body">
				<div id='conteudoiframeCadastrarProblema'>
				</div>
			</div>
			<!-- // Modal body END -->
			<!-- Modal footer -->
			<!-- // Modal footer END -->
	</div>

	<!--  Fim conteudo-->
	<%@include file="/novoLayout/common/include/rodape.jsp"%>
	<script  charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	<script src="${ctx}/novoLayout/common/theme/scripts/plugins/tables/DataTables/media/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/novoLayout/common/theme/scripts/plugins/tables/DataTables/media/js/DT_bootstrap.js"></script>
	<script type="text/javascript" src="js/analiseTendencias.js"></script>
	<link type="text/css" rel="stylesheet" href="css/analiseTendencias.css"/>
</body>

</html>