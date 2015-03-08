<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%
	response.setCharacterEncoding("ISO-8859-1");
	String idFaturaParm = request.getParameter("idFatura");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/titleComum/titleComum.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/text.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/grid.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css">
<link rel="stylesheet" type="text/css" href="./css/fatura.css">

<script type="text/javascript" src="../../cit/objects/FaturaApuracaoANSDTO.js"></script>

</head>

<body>
	<!-- Definicoes Comuns -->
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
		title="Aguarde... Processando..."
		style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
	</cit:janelaAguarde>

	<div id="paginaTotal">
		<div id="areautil">
			<div id="formularioIndex">
				<div id=conteudo>
					<table width="100%">
						<tr>
							<td width="100%">
								<h2>
									<b><fmt:message key="contrato.fatura" /></b>
								</h2> <!-- ## AREA DA APLICACAO ## -->
								<form name='form'
									action='${ctx}/pages/fatura/fatura'>
									<input type='hidden' name='idFatura' /> <input type='hidden'
										name='idContrato' /> <input type='hidden' name='idANS' /> <input
										type='hidden' name='seqANS' /> <input type='hidden'
										name='fieldANS' /> <input type='hidden'
										name='colItens_Serialize' />
									<table id="tabFormulario" cellpadding="0" cellspacing="0">
										<tr>
											<td class="campoEsquerda"><fmt:message
													key="citcorpore.comum.descricao" />*:</td>
											<td><input type='text' name='descricaoFatura' size="100"
												maxlength="150"
												class="Valid[Required] Description[Descrição da fatura] text" <%=(idFaturaParm != null ? "readonly" : "") %>/>
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda"><fmt:message
													key="citcorpore.comum.datainicio" />*:</td>
											<td><input type='text' name='dataInicial' size="10"
												maxlength="10" style="width: 100px !important;"
												class="Format[Date] Valid[Required,Date] Description[Data Início] text datepicker" />
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda"><fmt:message
													key="citcorpore.comum.datafim" />*:</td>
											<td><input type='text' name='dataFinal' size="10"
												maxlength="10" style="width: 100px !important;"
												class="Format[Date] Valid[Required,Date] Description[Data Fim] text datepicker" />
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda" style="vertical-align: middle;"><fmt:message
													key="visao.observacao" />:</td>
											<td><textarea  name="observacao" cols='120' rows='5'
													maxlength="1024" style="border: 1px solid black;"
													class="text" <%=(idFaturaParm != null ? "readonly" : "") %>></textarea></td>
										</tr>
										<tr>
											<td class="campoEsquerda"><fmt:message
													key="citcorpore.comum.situacao" />*:</td>
											<td><select name='situacaoFatura' id='situacaoFatura'
												class="Valid[Required] Description[Situação]"></select></td>
										</tr>
										<tr>
											<td></td>
											<td colspan="2">
												<button type='button' id="btnAddListaOSFaturamento"
													name='btnAddListaOSFaturamento' style="margin-top: 5px;"
													class="light img_icon has_text"
													onclick="mostrarOSParaFaturamento()">
													<img src="${ctx}/template_new/images/icons/small/grey/document.png">
													<span><fmt:message
															key="citcorpore.ui.janela.popup.titulo.Adicionar_OS" /></span>
												</button>
										</tr>
										<tr>
											<td colspan="2">
												<div id='divOsSelecionadas'></div>
											</td>
										</tr>
										<tr>
											<td colspan="2"><cit:grid id="GRID_ITENS"
													columnHeaders='citcorpore.comum.cabecalhoGridItens'
													styleCells="linhaGrid">
													<cit:column idGrid="GRID_ITENS" number="001">
														<input type='hidden'
															name='idAcordoNivelServicoContrato#SEQ#' value='' />
														<input type='text' name="descricaoAcordo#SEQ#" size='40'
															maxlength="200" readonly="readonly" />
														<br>
														<input type='text' name="complemento#SEQ#" size='40'
															maxlength="200" readonly="readonly" />
														<div id='inform#SEQ#'></div>
													</cit:column>
													<cit:column idGrid="GRID_ITENS" number="002">
														<textarea name="detalhamento#SEQ#" id="detalhamento#SEQ#"
															maxlength="300" cols='35' rows='5'></textarea>
													</cit:column>
													<cit:column idGrid="GRID_ITENS" number="003">
														<input type='text' name='valorApurado#SEQ#'
															id='valorApurado#SEQ#' maxlength="15" size='12'
															maxlength='14' class='Format[Money]' />
													</cit:column>
													<cit:column idGrid="GRID_ITENS" number="004">
														<input type='text' name='percentualGlosa#SEQ#'
															id='percentualGlosa#SEQ#' maxlength="15" size='5'
															maxlength='5' class='Format[Money]'
															onblur='calculaFormulaANS(#SEQ#, "percentualGlosa")'
															value="0,00" />
													</cit:column>
													<cit:column idGrid="GRID_ITENS" number="005">
														<input type='text' name='valorGlosa#SEQ#'
															id='valorGlosa#SEQ#' maxlength="15" size='12'
															maxlength='14' class='Format[Money]'
															onblur='calculaFormulaANS(#SEQ#, "valorGlosa")'
															value="0,00" />
													</cit:column>
												</cit:grid></td>
										</tr>
										<tr>
											<td colspan="2">
												<div id="valorTotal"
													style='border: 1px solid black; background-color: white'>
													<table>
														<tr>
															<td colspan="5">&nbsp;</td>
														</tr>
														<tr>
															<td colspan="5"><span style='color: red'>&nbsp;<fmt:message
																		key="citcorpore.comum.atencaoValoresAtualizadosAposGravacao" /></span></td>
														</tr>
														<tr>
															<td class="campoEsquerdaSemTamanho"><b><fmt:message
																		key="citcorpore.comum.valorTotalFatura" />:</b></td>
															<td><input type='text' class="text"
																name='valorPrevistoSomaOS' size="15" maxlength="15"
																readonly="readonly" /></td>
															<td class="campoEsquerdaSemTamanho"><b><fmt:message
																		key="contrato.valorExecutado" />:</b></td>
															<td><input type='text' class="text"
																name='valorExecutadoSomaOS' size="15" maxlength="15"
																readonly="readonly" /></td>
															<td class="campoEsquerdaSemTamanho"><b><fmt:message
																		key="citcorpore.comum.valorTotalGlosasFatura" />: </b></td>
															<td><input type='text' class="text"
																name='valorSomaGlosasOS' size="15" maxlength="15"
																readonly="readonly" /></td>
															<td class="campoEsquerdaSemTamanho"><b><fmt:message
																		key="citcorpore.comum.valorReceber" />:</b></td>
															<td><input type='text' class="text"
																name='valorReceberOS' size="15" maxlength="15"
																readonly="readonly" /></td>
														</tr>
														<tr>
															<td colspan="5">&nbsp;</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<div id='pareceres' style='display: none'>
													<table>
														<tr>
															<th><fmt:message
																	key="citcorpore.comum.parecerAprovacaoGestor" />:</th>
														</tr>
														<tr>
															<td><textarea name="aprovacaoGestor" cols='120'
																	rows='5' style="border: 1px solid black" <%=(idFaturaParm != null ? "readonly" : "") %>></textarea></td>
														</tr>
														<tr>
															<th><fmt:message
																	key="citcorpore.comum.parecerAprovacaoFiscal" />:</th>
														</tr>
														<tr>
															<td><textarea name="aprovacaoFiscal" cols='120'
																	rows='5' style="border: 1px solid black" <%=(idFaturaParm != null ? "readonly" : "") %>></textarea></td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="campoObrigatorio"><fmt:message
													key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio" /></td>
										</tr>
										<tr>
											<td colspan='2'>
												<table>
													<tr>
														<td>
															<div id='divBotaoGravar' style='display: block'>
																<!-- <button type='button' name='btnGravar' onclick='gravarForm();'>Gravar</button> -->
																<button type='button' id="btnAdicionar"
																	name='btnAdicionar'
																	style="margin-top: 5px; margin-left: 3px"
																	class="light img_icon has_text" onclick="gravarForm()">
																	<img
																		src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
																	<span><fmt:message
																			key="dinamicview.gravardados" /></span>
																</button>
															</div>
														</td>
														<td>
															<div id='divBotaoGravarSituacao'
																style='display: none; margin-left: 2px'>
																<!-- <button type='button' name='btnGravarSituacao' onclick='gravarSituacao();'>Atualizar Situação da Fatura</button> -->
																<button type='button' id="btnGravarSituacao"
																	name='btnGravarSituacao' style="margin-top: 5px;"
																	class="light img_icon has_text"
																	onclick="gravarSituacao()">
																	<img
																		src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
																	<span><fmt:message
																			key="citcorpore.comum.atualizarSituacaoFatura" /></span>
																</button>
															</div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="modal hide fade in" id="POPUP_LISTA_OS_FATURAMENTO"
		aria-hidden="false" data-width='1000px'>
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3><fmt:message key='citcorpore.comum.listagemOSFinalizadasNaoAssociadasFatura'/></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<form name='formAssociar'
				action='${ctx}/pages/fatura/fatura'>
				<input type='hidden' name='idOSExcluir' /> <input type='hidden'
					name='idFatura' id='idFaturaAssociar' />
				<div id='divOsSelecao'></div>
				<table>
					<tr>
						<td><button type="button" name='btnAssociarOS' onclick='associarOS()'><fmt:message key='fatura.associar'/></button></td>
					</tr>
				</table>
			</form>

		</div>

		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<a href="#" class="btn " data-dismiss="modal"><fmt:message
					key="citcorpore.comum.fechar" /></a>
		</div>
		<!-- // Modal footer END -->
	</div>
	<script type="text/javascript" src="./js/fatura.js"></script>

<%@include file="/include/footer.jsp"%>
</body>
</html>
