<%@page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.citframework.util.UtilDatas" %>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@page import="br.com.centralit.citcorpore.free.Free" %>

<%
	String iframe = request.getParameter("iframe");
	if (iframe == null) {
		iframe = "false";
	}

	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
	pageContext.setAttribute("pagina", PAGE_CADADTRO_SOLICITACAOSERVICO);
%>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/include/header.jsp" %>

	<title><fmt:message key="citcorpore.comum.title" /></title>

	<link type="text/css" rel="stylesheet" href="${ctx}/template_new/js/star-rating/jquery.rating.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery.ui.all.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/pages/baseConhecimentoView/css/baseConhecimentoViewAll.css" />

	<c:set var="iframe" value="false" />
	<c:if test="${not empty param.iframe}">
		<c:set var="iframe" value="${param.iframe}" />
	</c:if>

	<c:set var="fecharpesquisa" value="" />
	<c:if test="${not empty param.fecharpesquisa}">
		<c:set var="fecharpesquisa" value="${param.fecharpesquisa}" />
	</c:if>

	<c:choose>
		<c:when test="${iframe}">
			<link type="text/css" rel="stylesheet" href="${ctx}/pages/baseConhecimentoView/css/baseConhecimentoViewIframe.css"/>
		</c:when>
		<c:otherwise>
			<link type="text/css" rel="stylesheet" href="${ctx}/pages/baseConhecimentoView/css/baseConhecimentoView.css"/>
		</c:otherwise>
	</c:choose>

	<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/star-rating/jquery.MetaData.js"></script>

	<script type="text/javascript" src="${ctx}/js/jquery-ui-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.layout-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/debug.js"></script>

	<script type="text/javascript" src="${ctx}/js/dtree.js"></script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />

<body>
	<c:if test="${not iframe}">
		<div class="ui-layout-north">
			<div id="divLogo" style="overflow: hidden !important;">
				<table cellpadding='0' cellspacing='0'>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td>
							<img border="0" src="${ctx}/imagens/logo/logo.png" />
						</td>
					</tr>
				</table>
			</div>

			<div id="divControleLayout" style="position: fixed;top:1%;right: 2%;z-index: 100000;float: right;display: block; ">
				<table cellpadding='0' cellspacing='0' width="100" style="width: 100%;">
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td width="100" style="display: block; float: left;">
							<button type="button" class="light img_icon has_text" style='text-align: right; margin-left: 99%; float: right; display: block;' onclick="voltar()" title="<fmt:message key="citcorpore.comum.voltarprincipal" />">
								<img border="0" title="<fmt:message key='citcorpore.comum.voltarprincipal' />" src="${ctx}/imagens/back.png" /><span style="padding-left: 0px !important;"><fmt:message key="citcorpore.comum.voltar" /></span>
							</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</c:if>

	<div class="ui-layout-west">
		<div class="pastas">
			<form name='form2' action='${ctx}/pages/baseConhecimentoView/baseConhecimentoView'>
				<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento' /> <input type='hidden' id='idPasta' name='idPasta' />
				<div>
					<div style="" id="principalBaseConhecimento"></div>
				</div>
			</form>
		</div>
	</div>
	<div class="ui-layout-center">
		<div id="baseconhecimento" class="baseconhecimento container_16 clearfix" style="padding: 0px !important;">
			<div class="flat_area grid_16" style="padding-bottom: 1px !important; margin-top: -1px;"></div>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<span class='manipulaDiv' style='cursor: pointer'><span id='spanPesq' style='cursor: pointer'><fmt:message key="baseConhecimento.esconderPesquisa" /></span> &nbsp;<img src="${ctx}/imagens/search.png" /></span>
						<div id="divpesquisa" class="section">
							<form class="labelOverflowTresPontinhos" name='formPesquisa' id='formPesquisa' action='${ctx}/pages/baseConhecimentoView/baseConhecimentoView'>
								<div class="flat_area grid_16">
									<h2>
										<fmt:message key="baseConhecimento.baseConhecimento" />
									</h2>
								</div>

								<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento' />
								<input type="hidden" id="idUsuarioAutorPesquisa" name="idUsuarioAutorPesquisa" />
								<input type="hidden" id="idUsuarioAprovadorPesquisa" name="idUsuarioAprovadorPesquisa" />
								<input type="hidden" id="idItemConfiguracao" name="idItemConfiguracao" />
								<input type="hidden" id="idProblema" name="idProblema" />
								<input type="hidden" id="idSolicitacaoServico" name="idSolicitacaoServico" />
								<input type="hidden" id="idRequisicaoMudanca" name="idRequisicaoMudanca" />
								<input type='hidden' name='colItensProblema_Serialize' id='colItensProblema_Serialize' />
								<input type='hidden' name='colItensMudanca_Serialize' id='colItensMudanca_Serialize' />
								<input type='hidden' name='colItensIC_Serialize' id='colItensIC_Serialize' />
								<input type='hidden' name='colItensINC_Serialize' id='colItensINC_Serialize' />
								<input type="hidden" id="idRequisicaoLiberacao" name="idRequisicaoLiberacao" />
								<input type='hidden' name='colItensLiberacao_Serialize' id='colItensLiberacao_Serialize' />
								<div class="columns clearfix">
									<div class="col_20">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.autor" /></label>
											<div>
												<input type="text" id="nomeUsuarioAutor" name="nomeUsuarioAutor" onclick="abrirPopupUsuario(true);" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.aprovador" /></label>
											<div>
												<input type="text" id="nomeUsuarioAprovador" name="nomeUsuarioAprovador" onclick="abrirPopupUsuario(false);" />
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.dataCriacao" /></label>
											<div>
												<input type='text' id="dataInicioPesquisa" name="dataInicioPesquisa" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataNascimento] Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.dataPublicacao" /></label>
											<div>
												<input type='text' id="dataPublicacaoPesquisa" name="dataPublicacaoPesquisa" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataNascimento] Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset>
											<label><fmt:message key="relatorioBaseConhecimento.mediaAvaliacao" /></label>
											<div>
												<select id="termoPesquisaNota" name="termoPesquisaNota">
													<option value="">-- <fmt:message key="alcada.limite.todos" /> --</option>
													<option value="0.0">0.0</option>
													<option value="1.0">1.0</option>
													<option value="2.0">2.0</option>
													<option value="3.0">3.0</option>
													<option value="4.0">4.0</option>
													<option value="5.0">5.0</option>
													<option value="S"><fmt:message key="relatorioBaseConhecimento.semAvaliacao" /></option>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_15" >
										<fieldset style="border-bottom: 0px !important;">
											<label><fmt:message key="baseconhecimento.gerenciamentoDisponibilidade"/></label>
											<div>
												<input type='checkbox' name='amDoc' value='S' />
											</div>
										</fieldset>
									</div>
								</div>

								<div class="columns clearfix">
									<div id='divBotaoFechar' style='top: 0px; left: 220px; z-index: 10000;'>
										<fieldset>
											<div style="border-right: none; left: 0px; width: 400px; padding-top: 10px;" class="col_50">
												<label style="font: bold 13px arial, serif;"><fmt:message key="baseConhecimento.pesquisar" /></label>
												<div>
													<input type='text' name='termoPesquisa' size='40' maxlength="200" />
												</div>
											</div>
											<div style="border-right: none; padding-top: 10px; left: 15px;" class="col_40">
												<button title='<fmt:message key="citcorpore.comum.pesquisar"/>' type='button' id="btnPesquisar"
													name='btnPesquisar'
													style="margin-top: 17px; margin-left: -30px;" class="light"
													onclick='pesquisarBaseConhecimento()'>
													<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
													<span><fmt:message key="baseConhecimento.pesquisar" /></span>
												</button>
												<button title='<fmt:message key="citcorpore.ui.botao.rotulo.Limpar"/>' type='button' id="btnPesquisar" name='btnPesquisar' style="margin-top: 17px;" class="light" onclick='document.formPesquisa.clear();'>
													<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
													<span><fmt:message key="citcorpore.comum.limpar" /></span>
												</button>
											</div>
										</fieldset>
										<table>
											<tr>
												<td>
													<div id='resultPesquisaPai' style='border: 1px solid black; background-color: white; height: 400px; width: 650px; overflow: auto'>
														<table>
															<tr>
																<td>
																	<div style="margin: 10px !important;" id='resultPesquisa'></div>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</form>
						</div>

						<div class="section" id="conhecimento" style="overflow: auto;">
							<form id="form" name='form' action='${ctx}/pages/baseConhecimento/baseConhecimento'>

								<div class="flat_area grid_16">
									<input type='text' id="tituloconhecimento" name="titulo" />
								</div>

								<div id="modulosPai" class="columns clearfix">
									<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento' />
									<input type='hidden' name='idBaseConhecimentoPai' />

									<div class="columns clearfix">
										<div class="col_25">
											<fieldset style="height: 58px">
												<label><fmt:message key="pasta.pasta" /></label>
												<div>
													<input type='text' id="nomePasta" name="nomePasta" readonly="readonly" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label><fmt:message key="baseConhecimento.avaliacao" /></label>
												<div id='divMostraNota'></div>
												<br>

												<div id='divMostraVotos'>
													<label><span id="votos"> </span>
													<fmt:message key="citcorpore.comum.votos" /></label>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<div id='divInfoAdicional'></div>
											</fieldset>
										</div>
									</div>

									<div class="columns clearfix">
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="baseConhecimento.conteudo" /></label>
												<div id="conteudo"></div>
											</fieldset>
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="baseConhecimento.anexos" /></label>
													<cit:uploadControl style="height:10%;width:100%; border-bottom:1px solid #DDDDDD ; border-top:1px solid #DDDDDD "
														title="<fmt:message key='citcorpore.comum.anexos' />" id="uploadAnexos" form="document.form"
														action="/pages/upload/upload.load" disabled="false" />
												</fieldset>
											</div>
										</div>
										<div class="col_50">
											<div class="col_100">
												<fieldset style="margin-bottom: -1px; padding-bottom: 21px; height: 34px">
													<div style="padding-top: 20px">
														<a style="padding: 18px;" href="#" class="light" id="exibirHistorioVersao" title="<fmt:message key='baseConhecimentoView.consultarHistoricoVersoes'/>">
															<img src="${ctx}/imagens/search.png" />
															<span style="color: black !important; font-weight: bold"><fmt:message key="pesquisaBaseConhecimento.historicoVersoes" /></span>
														</a>
													</div>
												</fieldset>
											</div>
											<div class="col_100">
												<fieldset style="margin-bottom: -1px; padding-bottom: 21px; height: 34px">
													<div style="padding-top: 20px">
														<a style="padding: 18px;" href="#" class="light" id="exibirHistorioAlteracao" title="<fmt:message key='baseConhecimentoView.consultarHistoricoAlteracao'/>">
															<img src="${ctx}/imagens/search.png" />
															<span style="color: black !important; font-weight: bold"><fmt:message key="pesquisaBaseConhecimento.historicoAlteracao" /></span>
														</a>
													</div>
												</fieldset>
											</div>
											<div class="col_100">
												<fieldset style="margin-bottom: -1px; padding-bottom: 21px; height: 34px">
													<div style="padding-top: 20px">
														<a style="padding: 18px;" href="#" class="light" id="addComentarios">
															<img src="${ctx}/imagens/add.png" />
															<span style="color: black !important; font-weight: bold"><fmt:message key="baseConhecimento.enviarComentario" /></span>
														</a>
													</div>
												</fieldset>
												<div id="quantidadeComentarioPorBaseConhecimento"></div>
											</div>

											<%
												if (iframe.equalsIgnoreCase("false")) {
											%>
											</div>
											<div>
												<fieldset>
													<div id="manipularDiv"></div>
												</fieldset>
											</div>
											<span class='manipulaDiv' style='cursor: pointer'><fmt:message key="baseConhecimento.esconderConteudo" />&nbsp;<img src="${ctx}/imagens/content2.png" /></span>
											<div class="col_100" id="modulos">
												<!-- DIV INCIDENTES -->
												<div class="col_50">
													<fieldset>
														<div class="col_100">
															<div id="quantidadeSolicitacaoPorBaseConhecimento"></div>
														</div>
														<div id="divSolicitacao" style="display: block" class="col_100">
															<h2 class="section">
																<fmt:message key="solicitacaoServico.solicitacao" />
															</h2>
															<fieldset>
																<div style="width: 90%;">
																	<input type='hidden' name='sequenciaSolicitacao' id='sequenciaSolicitacao' />
																	<input type="hidden" name="idSolicitacaoServico" id="idSolicitacaoServico">
																	<input readonly="readonly" style="width: 90% !important;" type='text' id="addMudanca" name="addMudanca" maxlength="70" size="70" />
																	<img id="imagenSolicitacao" style="vertical-align: middle; cursor: pointer;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
																	<img id='btnCadastroSolicitacao' src="${ctx}/imagens/add.png" onclick="gravarIncidentesRequisicao(document.form.idBaseConhecimento.value);">
																</div>

																<div id='divSolicitacaoConhecimento' style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
																	<table id='tblSolicitacao' cellpadding="0" cellspacing="0" width='100%'>
																		<tr>
																			<td style='text-align: center' class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																			<td width="5%"></td>
																			<td width="20%" class='linhaSubtituloGrid'><fmt:message key="citcorpore.comum.numero" /></td>
																			<td width="75%" class='linhaSubtituloGrid'><fmt:message key="servico.servico" /></td>
																		</tr>
																	</table>
																</div>

																<button type='button' name='btnGravarSolicitacaoConhecimento' class="light" onclick="gravarSolicitacoesConhecimento();" style="margin: 12px 0 12px 22px;">
																	<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
																	<span><fmt:message key="citcorpore.comum.gravar" /></span>
																</button>
															</fieldset>
														</div>
													</fieldset>
												</div>
												<!-- FIM DIV INCIDENTES -->
											<%
												}
											%>

											<%
												if(br.com.citframework.util.Util.isVersionFree(request)){
											%>
											<fieldset >
												<div style="width: 100%;">
													<%=Free.getMsgCampoIndisponivel(request)%>
												</div>
											</fieldset>
											<%
											} else
												if (iframe.equalsIgnoreCase("false")) {
											%>

											<!-- DIV PROBLEMAS -->
											<div class="col_50">
												<fieldset>
													<div class="col_100">
														<div id="quantidadeProblemaPorBaseConhecimento"></div>
													</div>

													<div id="divProblema" style="display: block" class="col_100">
														<h2 class="section">
															<fmt:message key="problema.problema" />
														</h2>

														<fieldset>
															<div style="width: 90%;">
																<input type='hidden' name='sequenciaProblema' id='sequenciaProblema' />
																<input type="hidden" name="idProblema" id="idProblema">
																<input readonly="readonly" style="width: 90% !important;" type='text' id="addProblema" name="addProblema" maxlength="70" size="70" />
																<img id="imagenProblema" style="vertical-align: middle; cursor: pointer;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
																<img id='btnCadastroProblema' src="${ctx}/imagens/add.png" onclick="gravarGestaoProblema(document.form.idBaseConhecimento.value);">
															</div>

															<div id='divProblemaConhecimento' style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
																<table id='tblProblema' cellpadding="0" cellspacing="0"
																	width='100%'>
																	<tr>
																		<td style='text-align: center' class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																		<td width="10%"></td>
																		<td width="60%" class='linhaSubtituloGrid'><fmt:message key="requisicaMudanca.titulo" /></td>
																		<td width="29%" class='linhaSubtituloGrid'><fmt:message key="requisicaMudanca.status" /></td>
																	</tr>
																</table>
															</div>

															<button type='button' name='btnGravar' class="light" onclick="gravarProblemasConhecimento();" style="margin: 12px 0 12px 22px;">
																<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
																<span><fmt:message key="citcorpore.comum.gravar" /></span>
															</button>
														</fieldset>
													</div>
												</fieldset>
											</div>
											<!-- FIM DIV PROBLEMAS -->

											<!-- DIV MUDANCA -->
											<div class="col_50">
												<fieldset>
													<div class="col_100">
														<div id="quantidadeMudancaPorBaseConhecimento"></div>
													</div>

													<div id="divMudanca" style="display: block" class="col_100">
														<h2 class="section">
															<fmt:message key="requisicaMudanca.mudanca" />
														</h2>
														<fieldset>
															<div style="width: 90%;">
																<input type='hidden' name='sequenciaMudanca' id='sequenciaMudanca' /> <input type="hidden" name="idRequisicaoMudanca" id="idRequisicaoMudanca">
																<input readonly="readonly" style="width: 90% !important;" type='text' id="addMudanca" name="addMudanca"
																	maxlength="70" size="70" /> <img id="imagenMudanca" style="vertical-align: middle; cursor: pointer;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
																<img id='btnCadastroMudanca' src="${ctx}/imagens/add.png" onclick="gravarGestaoMudanca(document.form.idBaseConhecimento.value);">
															</div>

															<div id='divMudancaConhecimento' style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
																<table id='tblMudanca' cellpadding="0" cellspacing="0"
																	width='100%'>
																	<tr>
																		<td style='text-align: center' class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																		<td width="10%"></td>
																		<td width="60%" class='linhaSubtituloGrid'><fmt:message key="requisicaMudanca.titulo" /></td>
																		<td width="29%" class='linhaSubtituloGrid'><fmt:message key="requisicaMudanca.status" /></td>
																	</tr>
																</table>
															</div>

															<button type='button' name='btnGravar' class="light" onclick="gravarMudancaConhecimento();" style="margin: 12px 0 12px 22px;">
																<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
																<span><fmt:message key="citcorpore.comum.gravar" /></span>
															</button>
														</fieldset>
													</div>
												</fieldset>
											</div>
											<!-- DIV ITEM CONFIGURAÇÃO -->
											<div class="col_50">
												<fieldset>
													<div class="col_100">
														<div id="quantidadeItemConfiguracaoPorBaseConhecimento"></div>
													</div>

													<div id="divItemConfiguracao" class="col_100" style="display: block;">
														<h2 class="section"><fmt:message key="itemConfiguracao.itemConfiguracao"/></h2>

														<fieldset>
															<label>
																<img title="<fmt:message key="solicitacaoServico.informacaoItemConfiguracao" />" id="imagem" onclick="popupAtivos();" style="vertical-align: top; cursor: pointer; display: none;" src="${ctx}/template_new/images/icons/small/grey/graph.png">
																<img id="btHistoricoIc" style="cursor: pointer; display: none;" src="${ctx}/template_new/images/icons/small/grey/month_calendar.png">
															</label>

															<div style="width: 90%;">
																<input onclick="popup.abrePopup('pesquisaItemConfiguracao','()')" readonly="readonly" style="width: 90% !important;" type='text' id="itemConfiguracao" name="itemConfiguracao" maxlength="70" size="70" />
																<img id="imagenIC" onclick="popup.abrePopup('pesquisaItemConfiguracao','()')" style="vertical-align: middle; cursor: pointer;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
																<img id='btnCadastroItemConfiguracao' src="${ctx}/imagens/add.png" onclick="gravarGestaoItemConfiguracao(document.form.idBaseConhecimento.value);">
															</div>

															<div id='divIC' style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
																<table id='tblIC' cellpadding="0" cellspacing="0" width='100%'>
																	<tr>
																		<td style='text-align: center' class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																		<td width="20%" class='linhaSubtituloGrid'><fmt:message key="citcorpore.comum.numero" /></td>
																		<td width="62%" class='linhaSubtituloGrid'><fmt:message key="citcorpore.comum.identificacao" /></td>
																		<td width="10%"><fmt:message key="solicitacaoServico.informacao"/></td>
																	</tr>
																</table>
															</div>

															<button type='button' name='btnGravar' class="light" onclick="gravarICConhecimento();" style="margin: 12px 0 12px 22px;">
																<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
																<span><fmt:message key="citcorpore.comum.gravar" /></span>
															</button>
														</fieldset>
													</div>
												</fieldset>
											</div>
											<div class="col_100">
												<div id="quantidadeLiberacaoPorBaseConhecimento"></div>
											</div>

											<div id="divLiberacao" style="display: block" class="col_50">
												<h2 class="section">
													<fmt:message key="requisicaoLiberacao.liberacao" />
												</h2>
												<fieldset>
													<div style="width: 90%;">
														<input type='hidden' name='sequenciaLiberacao' id='sequenciaLiberacao' />
														<input type="hidden" name="idRequisicaoLiberacao" id="idRequisicaoLiberacao">
														<input readonly="readonly" style="width: 90% !important;" type='text' id="addLiberacao" name="addLiberacao" maxlength="70" size="70" />
														<img id="imagenLiberacao" style="vertical-align: middle; cursor: pointer;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
														<img id='btnCadastroLiberacao' src="${ctx}/imagens/add.png" onclick="gravarGestaoLiberacao(document.form.idBaseConhecimento.value);">
													</div>

													<div id='divLiberecaoConhecimento' style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
														<table id='tblLiberacao' cellpadding="0" cellspacing="0" width='100%'>
															<tr>
																<td style='text-align: center' class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																<td width="10%"></td>
																<td width="60%" class='linhaSubtituloGrid'><fmt:message key="requisicaMudanca.titulo" /></td>
																<td width="29%" class='linhaSubtituloGrid'><fmt:message key="requisicaMudanca.status" /></td>
															</tr>
														</table>
													</div>

													<button type='button' name='btnGravar' class="light" onclick="gravarLiberacaoConhecimento();" style="margin: 12px 0 12px 22px;">
														<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
														<span><fmt:message key="citcorpore.comum.gravar" /></span>
													</button>
												</fieldset>
											</div>
											<%
											}
											%>

											<div id="POPUP_DADOSCOMENTARIO">
												<div class="col_50">
													<label style="font-weight: bold; color: #333333; display: block; font-size: 11px; font-weight: bold; margin-right: 10px; padding: 4px 20px;"><fmt:message key="baseConhecimento.comentarios" /></label>

													<div id="gridComentario" class="columns clearfix" style="display: none;">
														<table class="table" id="tabelaComentarios" style="width: 100%">
															<tr style="text-align: left;">
																<th style="width: 15%;"><fmt:message key="citcorpore.comum.nome" /></th>
																<th style="width: 25%;"><fmt:message key="citcorpore.comum.email" /></th>
																<th style="width: 30%;"><fmt:message key="baseConhecimento.comentario" /></th>
																<th style="width: 30%;"><fmt:message key="baseConhecimento.nota" /></th>
															</tr>
														</table>
													</div>
												</div>
											</div>

											<div class="col_100">
												<div id="gridConhecimentoRelacionado">
													<label style="font-weight: bold; color: #333333; display: block; font-size: 11px; font-weight: bold; margin-right: 10px; padding: 4px 20px;">
													<fmt:message key='baseConhecimentoView.conhecimentosRelacionados'/></label>
													<table class="table" id="tabelaConhecimentoRelacionado" style="display: none;">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 50%;"><fmt:message key='menu.relatorio.gerConhecimento'/></th>
														</tr>
													</table>
												</div>
											</div>
										</div>
									</div>
									<div id="popupCadastroRapido">
										<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="97%" height="97%"></iframe>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_COMENTARIOS" title="<fmt:message key='comentarios.comentarios' />">
		<form id="formPopup" name='formPopup' action='${ctx}/pages/comentarios/comentarios'>
			<div class="columns clearfix" style="padding: 15px; width: 500px;">

				<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento'
					value="<%session.getAttribute("idBaseConhecimento");%>" /> <input
					type='hidden' id='idComentario' name='idComentario' /> <input
					type='hidden' name='dataInicio' /> <input type='hidden'
					name='dataFim' />

				<h2 style="padding: 0px;">
					<fmt:message key="comentarios.nota" />
				</h2>
				<span><fmt:message key="baseConhecimento.notaconhecimento" /></span>
				<div class="Clear" id="notaEnviarComentario">
					<input id="nota" class="star required" type="radio" name="nota" value="1" title="<fmt:message key='citcorpore.comum.fraco'/>" />
					<input class="star" type="radio" name="nota" value="2" title="<fmt:message key='citcorpore.comum.regular'/>" />
					<input class="star" type="radio" name="nota" value="3" title="<fmt:message key='citcorpore.comum.bom'/>" />
					<input class="star" type="radio" name="nota" value="4" title="<fmt:message key='citcorpore.comum.otimo'/>" />
					<input class="star" type="radio" name="nota" value="5" title="<fmt:message key='citcorpore.comum.excelente'/>" />
				</div>

				<br>

				<h2 class="campoObrigatorio" style="padding: 0px;">
					<fmt:message key="comentarios.nome" />
				</h2>
				<span><fmt:message key="baseConhecimento.nomecompleto" /></span>
				<div>
					<input type='text' name="nome" onkeydown="FormatUtils.noNumbers(this)" maxlength="70" size="70" class="Valid[Required] Description[comentarios.nome]" />
				</div>

				<h2 class="campoObrigatorio" style="padding: 0px;">
					<fmt:message key="comentarios.email" />
				</h2>
				<span><fmt:message key="baseConhecimento.informemail" /> </span>
				<div>
					<input type='text' name="email" maxlength="70" size="70" onchange="ValidacaoUtils.validaEmail(email,'');" class="Valid[Required] Description[comentarios.email]" />
				</div>

				<h2 class="campoObrigatorio" style="padding: 0px;">
					<fmt:message key="comentarios.comentario" />
				</h2>
				<div>
					<textarea rows="" cols="" maxlength="200" id="comentario" name="comentario"></textarea>
				</div>

				<br>

				<button type='button' name='btnGravar' class="light" onclick='gravar();'>
					<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
					<span><fmt:message key="citcorpore.comum.gravar" /></span>
				</button>
				<button type="button" name='btnLimpar' class="light" onclick='limpar();'>
					<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
					<span><fmt:message key="citcorpore.comum.limpar" /></span>
				</button>

			</div>
		</form>
	</div>

	<div id="POPUP_INCIDENTE" title="<fmt:message key='solicitacaoServico.solicitacao' /> ">
		<iframe id="iframeSolicitacaoServico" name="iframeSolicitacaoServico" width="95%" height="95%"> </iframe>
	</div>

	<div id="POPUP_CADASTROPROBLEMA" title="<fmt:message key='problema.problema' /> ">
		<iframe id="iframeProblema" name="iframeProblema" width="97%" height="97%"> </iframe>
	</div>

	<div id="POPUP_CADASTROMUDANCA" title="<fmt:message key='requisicaMudanca.mudanca' /> ">
		<iframe id="iframeMudanca" name="iframeMudanca" width="97%" height="97%"> </iframe>
	</div>

	<div id="POPUP_CADASTROLIBERACAO" title="<fmt:message key='requisicaoLiberacao.liberacao' /> ">
		<iframe id="iframeLiberacao" name="iframeLiberacao" width="97%" height="97%"> </iframe>
	</div>

	<div id="POPUP_MUDANCA"
		title="<fmt:message key="requisicaMudanca.mudanca" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div align="right"></div>
						<form name='formPesquisaMudanca' style="width: 540px">
							<cit:findField formName='formPesquisaMudanca' lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0' left='0'
								len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_LIBERACAO"
		title="<fmt:message key="requisicaoLiberacao.liberacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div align="right"></div>
						<form name='formPesquisaLiberacao' style="width: 540px">
							<cit:findField formName='formPesquisaLiberacao' lockupName='LOOKUP_LIBERACAO' id='LOOKUP_LIBERACAO' top='0' left='0'
								len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_PROBLEMA"
		title="<fmt:message key="problema.problema" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div align="right"></div>
						<form name='formPesquisaProblema' style="width: 540px">
							<cit:findField formName='formPesquisaProblema' lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_SOLICITACAO"
		title="<fmt:message key="gerenciaservico.solicitacaoincidente" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div align="right"></div>
						<form name='formPesquisaSolicitacao' style="width: 540px">
							<cit:findField formName='formPesquisaSolicitacao' lockupName='LOOKUP_SOLICITACAOSERVICO'
								id='LOOKUP_SOLICITACAOSERVICO' top='0' left='0' len='550'
								heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_ITEMCONFIGURACAO" title="<fmt:message key='itemConfiguracao.itemConfiguracao' /> ">
		<iframe id="iframeItemConfiguracao" name="iframeItemConfiguracao" width="99%" height="90%"> </iframe>
	</div>

	<div id="POPUP_COMENTARIO" title="<fmt:message key='baseConhecimento.comentario' /> ">
		<iframe id="iframeComentario" name="iframeComentario" width="97%" height="97%"> </iframe>
	</div>

	<div id="POPUP_DADOSSOLICITCAO" title="<fmt:message key='baseConhecimento.dadosolicacao' />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formOcorrencias' method="post" action=''>
							<div id='dadosSolicitacao/Incidetes' style='width: 100%; height: 100%;'></div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_DADOSPROBLEMA" title="<fmt:message key='baseConhecimento.dadosproblema' />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formOcorrencias' method="post" action=''>
							<div id='dadosProblema' style='width: 100%; height: 100%;'></div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_DADOSMUDANCA" title="<fmt:message key='baseConhecimento.dadosmudanca' />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formOcorrencias' method="post" action=''>
							<div id='dadosMudanca' style='width: 100%; height: 100%;'></div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_DADOSITEMCONFIGURACAO" title="<fmt:message key='baseConhecimento.dadositemconfiguracao' />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formOcorrencias' method="post" action=''>
							<div id='dadosItemConfiguracao' style='width: 100%; height: 100%;'>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_USUARIO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<input type="hidden" id="isNotificacao" name="isNotificacao">
							<cit:findField formName='formPesquisaUsuario' lockupName='LOOKUP_USUARIO_CONHECIMENTO' id='LOOKUP_USUARIO'
								top='0' left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_HISTORICOVERSAO">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaHistoricoVersao' style="width: 540px">
							<div id="gridHistoricoVersoes">
								<table style="width: 100%" class="table" id="tabelaHistoricoVersoes">
									<tr>
										<th style="width: 90%;"><fmt:message key="planoMelhoria.tituloplano"/></th>
										<th style="width: 10%;"><fmt:message key="midiaSoftware.versao"/></th>
									</tr>
								</table>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_ALTERACAO">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaHistoricoAlteracao' style="width: 930px">
							<div id="gridHistoricoAlteracao">
								<table style="width: 100%;" class="table" id="tabelaHistoricoAlteracao">
									<tr>
										<th style="width: 30%;"><fmt:message key="planoMelhoria.tituloplano"/></th>
										<th style="width: 30%;"><fmt:message key="menu.nome.pasta"/></th>
										<th style="width: 2%;"><fmt:message key="midiaSoftware.versao"/></th>
										<th style="width: 2%;"><fmt:message key="lookup.origem"/></th>
										<th style="width: 2%;"><fmt:message key="pagamentoProjeto.status"/></th>
										<th style="width: 50%;"><fmt:message key="pagamentoProjeto.usuario"/></th>
										<th style="width: 25%;"><fmt:message key="baseConhecimentoView.dataHoraAlteracao"/></th>
									</tr>
								</table>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_ATIVOS"
		title="<fmt:message key="pesquisa.listaAtivosDaMaquina" />"
		style="overflow: hidden;">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block" style="overflow: hidden;">
					<div class="section" style="overflow: hidden;">
						<iframe id="iframeAtivos"
							style="display: block; margin-left: -20px;" name="iframeAtivos"
							width="97%" height="97%"> </iframe>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_EDITARPROBLEMA" style="overflow: hidden;" title="<fmt:message key="problema.problema"/>">
		<iframe id='iframeEditarProblema' src='about:blank' width="97%" height="97%" style='border:none;'></iframe>
	</div>

	<script type="text/javascript">
		var fecharpesquisa = "${fecharpesquisa}";
		var pagina = "${PAGE_CADADTRO_SOLICITACAOSERVICO}";
	</script>

	<script type="text/javascript" src="${ctx}/pages/baseConhecimentoView/js/baseConhecimentoView.js"></script>
</body>
</html>
