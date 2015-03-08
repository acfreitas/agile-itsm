<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@ page import="br.com.citframework.util.Constantes" %>
<%@ page import="br.com.centralit.citcorpore.free.Free" %>

<!doctype html public "">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		<%@include file="/include/header.jsp"%>

		<%@ include file="/include/security/security.jsp" %>

		<title><fmt:message key="citcorpore.comum.title" /></title>

		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
		<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
		<link rel="stylesheet" type="text/css" href="./css/atividadePeriodica.css" />
	</head>

	<body>
		<div id="wrapper">
			<%
				if (iframe == null) {
			%>
					<%@include file="/include/menu_vertical.jsp"%>
			<%
				}
			%>
			<!-- CONTEÚDO -->
			<div id="main_container" class="main_container container_16 clearfix">
				<%
					if (iframe == null) {
				%>
						<%@include file="/include/menu_horizontal.jsp"%>
				<%
					}
				%>
				<div class="flat_area grid_16">
					<h2><fmt:message key="citcorpore.ui.pagina.atividadePeriodica.titulo" /></h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="citcorpore.ui.aba.titulo.Cadastro" /></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="citcorpore.ui.aba.titulo.Pesquisa" /></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/atividadePeriodica/atividadePeriodica">
									<input type="hidden" name="idAtividadePeriodica" />
									<input type="hidden" name="idSolicitacaoServico" />
									<input type="hidden" name="colItens_Serialize" />
									<input type="hidden" name="colItensOS_Serialize" />
									<table id="tabFormulario" cellpadding="0" cellspacing="0">
										<tr>
											<td class="campoEsquerda campoObrigatorio"><fmt:message key="citcorpore.ui.rotulo.Cadastro" />:</td>
											<td>
												<select name="idContrato" class="Valid[Required] Description[citcorpore.ui.rotulo.Cadastro]" onchange="setaValorContrato(this)"></select>
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda campoObrigatorio"><fmt:message key="citcorpore.ui.rotulo.Grupo_de_Atividades" />:</td>
											<td>
												<select name="idGrupoAtvPeriodica" class="Valid[Required] Description[citcorpore.ui.rotulo.Grupo_de_Atividades]"></select>
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda campoObrigatorio"><fmt:message key="citcorpore.ui.rotulo.Titulo_da_Atividade" />:</td>
											<td>
												<input type="text" name="tituloAtividade" maxlength="70" size="70" class="Valid[Required] Description[citcorpore.ui.rotulo.Titulo_da_Atividade] text" />
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda" style="vertical-align: middle;"><fmt:message key="citcorpore.ui.rotulo.mudanca" />:</td>
											<%
												if(br.com.citframework.util.Util.isVersionFree(request)){
											%>
											<td>
												<%=Free.getMsgCampoIndisponivel(request)%>
											</td>
											<%
												} else {
											%>
											<td>
												<table>
													<tr>
														<td>
															<input type='text' name='identMudanca' size="100" maxlength="100" readonly="readonly" onclick='$("#POPUP_MUDANCA").dialog("open")'/>
														</td>
														<td>
															<img src='<%=Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/clear.png' style='cursor:pointer' border='0' onclick='limparMudanca()'/>
														</td>
													</tr>
												</table>
												<input type='hidden' name='idRequisicaoMudanca'/>
											</td>
											<%
												}
											%>


										</tr>
										<tr>
											<td class="campoEsquerda"><fmt:message key="citcorpore.ui.rotulo.blackout" />:</td>
											<td>
												<input type="checkbox" name="blackout" value="S"/>
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda"><fmt:message key="citcorpore.ui.rotulo.Descricao_da_Atividade" />:</td>
											<td><textarea name="descricao" cols="70" rows="5" style="border: 1px solid black"></textarea></td>
										</tr>
										<tr>
											<td class="campoEsquerda"><fmt:message key="citcorpore.ui.rotulo.Orientacao_Tecnica" />:</td>
											<td><textarea name="orientacaoTecnica" cols="70" rows="5" style="border: 1px solid black"></textarea></td>
										</tr>
										<tr>
											<td colspan="2" style="text-align: left;" id="tdBotaoNovaOs">
												<button type="button"
													id="btnAddOS"
													name="btnAddOS"
													style="margin-top: 5px; margin-left: 3px; float: left;"
													class="light img_icon has_text">
													<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
													<span style="font-size: 12px !important;"><fmt:message key="citcorpore.ui.botao.rotulo.Vincular_OS" /></span>
												</button>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<div id="divOS"	style="height: 120px; width: 940px; overflow: auto; border: 1px solid black;">
													<table id="tblOS" cellpadding="0" cellspacing="0" width="100%">
														<tr>
															<td style="text-align: center" class="linhaSubtituloGrid" width="16px">&nbsp;</td>
															<td></td>
													 		<td class="linhaSubtituloGrid"><fmt:message key="citcorpore.ui.tabela.coluna.Numero" /></td>
															<td class="linhaSubtituloGrid"><fmt:message key="citcorpore.ui.tabela.coluna.Area_requisitante" /></td>
															<td class="linhaSubtituloGrid"><fmt:message key="citcorpore.ui.tabela.coluna.Tarefa_Demanda" /></td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2" style="text-align: left;" id="tdBotaoNovoAgendamento">
												<button type="button"
													id="btnAddInteg"
													name="btnAddInteg"
													style="margin-top: 5px; margin-left: 3px; float: left;"
													class="light img_icon has_text">
													<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
													<span style="font-size: 12px !important;"><fmt:message key="citcorpore.ui.botao.Novo_Agendamento" /></span>
												</button>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<div id="divAgendamentos" style="height: 120px; width: 940px; overflow: auto; border: 1px solid black;">
													<table id="tblAgendamentos" cellpadding="0" cellspacing="0" width="100%">
														<tr>
															<td style="text-align: center" class="linhaSubtituloGrid" width="16px">&nbsp;</td>
															<td style="text-align: center" class="linhaSubtituloGrid" width="16px">&nbsp;</td>
															<td class="linhaSubtituloGrid"><fmt:message key="citcorpore.ui.tabela.coluna.Tipo_de_agendamento" /></td>
															<td class="linhaSubtituloGrid"><fmt:message key="citcorpore.ui.tabela.coluna.Atividade_OS" /></td>
															<td class="linhaSubtituloGrid"><fmt:message key="citcorpore.ui.tabela.coluna.Data_inicio" /></td>
															<td class="linhaSubtituloGrid"><fmt:message key="citcorpore.ui.tabela.coluna.Detalhamento" /></td>
															<td class="linhaSubtituloGrid"><fmt:message key="citcorpore.ui.tabela.coluna.Duracao_estimada" /></td>
															<td class="linhaSubtituloGrid"><fmt:message key="citcorpore.ui.tabela.coluna.Repeticao" /></td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="campoObrigatorio"><fmt:message key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio" /></td>
										</tr>
										<tr>
											<td colspan="2">
												<div>
													<table>
														<tr>
															<td id="tdBotaoGravar">
																<button type="button"
																	id="btnGravar"
																	name="btnGravar"
																	style="margin-top: 5px; margin-left: 3px;"
																	class="light img_icon has_text"
																	onclick="grava()">
																	<img src="${ctx}/template_new/images/icons/small/grey/pencil.png" />
																	<span>
																		<fmt:message key="citcorpore.ui.botao.rotulo.Gravar" />
																	</span>
																</button>
															</td>
															<td>
																<button type="button"
																	id="btnLimpar"
																	name="btnLimpar"
																	style="margin-top: 5px; margin-left: 3px;"
																	class="light img_icon has_text"
																	onclick="limpa()">
																	<img src=${ctx}/template_new/images/icons/small/grey/clear.png />
																	<span>
																		<fmt:message key="citcorpore.ui.botao.rotulo.Limpar" />
																	</span>
																</button>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<fmt:message key="citcorpore.ui.aba.titulo.Pesquisa" />
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa"
										lockupName="LOOKUP_ATIVIDADE_PERIODICA"
										id="LOOKUP_ATIVIDADE_PERIODICA" top="0" left="0" len="550"
										heigth="400" javascriptCode="true" htmlCode="true" />
								</form>
							</div>
						</div>
					</div> <!-- FIM DA ÁREA DA APLICAÇÃO -->
				</div>
			</div>
		</div>	<!-- FIM DA PÁGINA DE CONTEÚDO -->
		<%@ include file="/include/footer.jsp" %>
<!-- </body> -->
		<div id="POPUP_OS" title='<fmt:message key="citcorpore.ui.janela.popup.titulo.Adicionar_OS" />'>
			<form name="formOS" action="${ctx}/pages/os/os">
				<input type="hidden" name="idOS" id="idOS" />
				<input type="hidden" name="sequenciaOS" id="sequenciaOS" />
				<cit:findField formName="formOS"
					lockupName="LOOKUP_OS_EXECUCAO_APROVADOEXE"
					id="LOOKUP_OS_EXECUCAO_APROVADOEXE"
					top="0"
					left="0"
					len="550"
					heigth="400"
					javascriptCode="true"
					htmlCode="true" />
			</form>
		</div>

		<div id="POPUP_MUDANCA" title='<fmt:message key="solicitacaoServico.adicionarMudanca" />'>
			<form name="formMudanca" action="${ctx}/pages/os/os">
				<cit:findField lockupName="LOOKUP_MUDANCA" heigth="400" left="150" len="500" htmlCode="true" top="60" javascriptCode="true" formName="formMudanca"></cit:findField>
			</form>
		</div>


		<div id="POPUP_AGENDAMENTO" title='<fmt:message key="citcorpore.ui.janela.popup.titulo.Agendamento" />'>
			<form name="formAgendamento" action="${ctx}/pages/programacaoAtividade/programacaoAtividade">
				<input type="hidden" name="idProgramacaoAtividade" id="idProgramacaoAtividade" />
		 		<input type="hidden" name="sequencia" id="sequencia" />
		 		<div class='row-fluid'>
	 				<div class='span12 innerAll'>
			 			<div class='row-fluid'>
			 				<div class='span12'>
								<label class="campoObrigatorio"><fmt:message key="citcorpore.ui.rotulo.Tipo_de_agendamento" /></label>
								<select name="tipoAgendamento" id="tipoAgendamento" onchange="configuraAgendamento(this);">
									<option value="D">
										<fmt:message key="citcorpore.texto.frequencia.diariamente" />
									</option>
									<option value="S">
										<fmt:message key="citcorpore.texto.frequencia.semanalmente" />
									</option>
									<option value="M">
										<fmt:message key="citcorpore.texto.frequencia.mensalmente" />
									</option>
									<option value="U">
										<fmt:message key="citcorpore.texto.frequencia.umaVez" />
									</option>
								</select>
							</div>
			 			</div>
						<div class='row-fluid'>
			 				<div class='span12'>
								<label class="campoObrigatorio"><fmt:message key="citcorpore.agendamento.Inicia_em" /></label>
								<div class='displayInBk' class='displayInBk'>
									<input type="text" name="dataInicio" id="dataInicio" size="10" maxlength="10" class="Format[Date] text datepicker" />
								</div>
								<b><fmt:message key="citcorpore.agendamento.as" /></b>
								<div class='displayInBk'>
									<input type="text" name="horaInicio" id="horaInicio" size="5" maxlength="5" class="Format[Hora] text" />
								</div>
							</div>
						</div>
						<div id="divDataFinal" class='row-fluid'>
			 				<div class='span4'>
								<label><fmt:message key="citcorpore.agendamento.Expira_em" /></label>
								<input type="text" name="dataFim" id="dataFim" size="10" maxlength="10" class="Format[Date] text datepicker" />
							</div>
						</div>
						<div class='row-fluid'>
							<div class='span12'>
								<label class="campoObrigatorio"><fmt:message key="citcorpore.agendamento.Duracao_estimada" /></label>
								<div class='displayInBk'>
									<input type="text" name="duracaoEstimada" id="duracaoEstimada" size="5" maxlength="5" class="Format[Numero] text" />
								</div>
								<b><fmt:message key="citcorpore.texto.tempo.minutoS" /></b>
							</div>
						</div>
						<div id="divConfigTarefaD" class='row-fluid'>
			 				<div class='span6'>
								<label><fmt:message key="citcorpore.agendamento.Agendar_atividade_diaria" /></label>
								<fmt:message key="citcorpore.agendamento.atividade.A_cada" />
								<div class='displayInBk'>
									<input type="text" name="periodicidadeDiaria" size="5" maxlength="5" class="Format[Numero] text" />
								</div>
								<fmt:message key="citcorpore.texto.tempo.diaS" />
							</div>
						</div>
						<div id="divConfigTarefaS" class='row-fluid hide'>
							 <div class='span6'>
								<label><fmt:message key="citcorpore.agendamento.Agendar_atividade_semanal" /></label>

								<fmt:message key="citcorpore.agendamento.atividade.A_cada" />
								<div class='displayInBk'>
									<input type="text" name="periodicidadeSemanal" size="5" maxlength="5" class="Format[Numero]" />
								</div>
								<fmt:message key="citcorpore.agendamento.atividade.semanaS_na_BARRA_no" />
							</div>
							 <div class='span6'>
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td width="70px">
											<input type="checkbox" name="seg" id="seg" value="S" /><span class="campoEsquerda"><fmt:message key="citcorpore.texto.abreviado.diaSemana.segundaFeira" /></span>
										</td>
										<td>
											<input type="checkbox" name="sex" id="sex" value="S" /><span class="campoEsquerda"><fmt:message key="citcorpore.texto.abreviado.diaSemana.sextaFeira" /></span>
										</td>
									</tr>
									<tr>
										<td>
											<input type="checkbox" name="ter" id="ter" value="S" /><span class="campoEsquerda"><fmt:message key="citcorpore.texto.abreviado.diaSemana.tercaFeira" /></span>
										</td>
										<td>
											<input type="checkbox" name="sab" id="sab" value="S" /><span class="campoEsquerda"><fmt:message key="citcorpore.texto.abreviado.diaSemana.sabado" /></span>
										</td>
									</tr>
									<tr>
										<td>
											<input type="checkbox" name="qua" id="qua" value="S" /><span class="campoEsquerda"><fmt:message key="citcorpore.texto.abreviado.diaSemana.quartaFeira" /></span>
										</td>
										<td>
											<input type="checkbox" name="dom" id="dom" value="S" /><span class="campoEsquerda"><fmt:message key="citcorpore.texto.abreviado.diaSemana.domingo" /></span>
										</td>
									</tr>
									<tr>
										<td>
											<input type="checkbox" name="qui" id="qui" value="S" /><span class="campoEsquerda"><fmt:message key="citcorpore.texto.abreviado.diaSemana.quintaFeira" /></span>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div id="divConfigTarefaM" class='row-fluid hide'>
							 <div class='span12'>
								<label><fmt:message key="citcorpore.agendamento.Agendar_atividade_mensal" /></label>
								<div class='row-fluid'>
							 		<div class='span2 uniformjs'>
										<label class="radio">
											<input type="radio" style="cursor: pointer" name="periodicidadeMensal" value="1" />
											<fmt:message key="citcorpore.texto.tempo.Dia" />
										</label>
							 		</div>
							 		<div class='span3'>
							 			<select name="dia" id="dia">
										<%
											for (int i = 1; i <= 31; i++)
												out.print("<option value='" + i + "'>" + i + "</option>");
										%>
											<option value="99">
												<fmt:message key="citcorpore.texto.adjetivo.ultimo" />
											</option>
										</select>
									</div>
									<div class='span7'>
										<fmt:message key="citcorpore.agendamento.atividade.dos_meses_selecionados" />
							 		</div>
						 		</div>
						 		<div class='row-fluid'>
							 		<div class='span2 uniformjs'>
										<label class="radio">
											<input type="radio" style="cursor: pointer" name="periodicidadeMensal" value="2" />
											<fmt:message key="citcorpore.agendamento.atividade.O" />
										</label>
							 		</div>
							 		<div class='span3'>
										<select name="diaUtil" id="diaUtil">
											<%
												for (int i = 1; i <= 23; i++) {
													out.print("<option value='" + i + "'>" + i);

													switch (i) {
														case 1:
											%>
															<fmt:message key="citcorpore.texto.sufixo.ordemNumero.primeiro" /></option>
											<%
														break;
														case 2:
											%>
															<fmt:message key="citcorpore.texto.sufixo.ordemNumero.segundo" /></option>
											<%
														break;
														case 3:
											%>
															<fmt:message key="citcorpore.texto.sufixo.ordemNumero.terceiro" /></option>
											<%
														break;
														default:
											%>
															<fmt:message key="citcorpore.texto.sufixo.ordemNumero.masculino" /></option>
											<%
													}
												}
											%>
											<option value="99">
												<fmt:message key="citcorpore.texto.adjetivo.ultimo" />
											</option>
										</select>
									</div>
									<div class='span7'>
										<fmt:message key="citcorpore.agendamento.atividade.dia_util_meses_dos_selecionados" />
							 		</div>
						 		</div>

						 		<div class='row-fluid'>
							 		<div class='span2 uniformjs'>
										<label class="radio">
											<input type="radio" style="cursor: pointer" name="periodicidadeMensal" value="3" />
											<fmt:message key="citcorpore.agendamento.atividade.A_BARRA_O" />
										</label>
							 		</div>
							 		<div class='span3'>
										<select name="seqDiaSemana" id="seqDiaSemana">
											<option value="1"><fmt:message key="citcorpore.texto.numeral.ordinal.primeraO" /></option>
											<option value='2'><fmt:message key="citcorpore.texto.numeral.ordinal.segundaO" /></option>
											<option value='3'><fmt:message key="citcorpore.texto.numeral.ordinal.terceiraO" /></option>
											<option value='4'><fmt:message key="citcorpore.texto.numeral.ordinal.quartaO" /></option>
											<option value='5'><fmt:message key="citcorpore.texto.adjetivo.ultimaO" /></option>
										</select>
										<select name='diaSemana'>
											<option value='2'>
												<fmt:message key="citcorpore.texto.diaSemana.segundaFeira" />
											</option>
											<option value='3'>
												<fmt:message key="citcorpore.texto.diaSemana.tercaFeira" />
											</option>
											<option value='4'>
												<fmt:message key="citcorpore.texto.diaSemana.quartaFeira" />
											</option>
											<option value='5'>
												<fmt:message key="citcorpore.texto.diaSemana.quintaFeira" />
											</option>
											<option value='6'>
												<fmt:message key="citcorpore.texto.diaSemana.sextaFeira" />
											</option>
											<option value='7'>
												<fmt:message key="citcorpore.texto.diaSemana.sabado" />
											</option>
											<option value='1'>
												<fmt:message key="citcorpore.texto.diaSemana.domingo" />
											</option>
										</select>
									</div>
									<div class='span7'>
										<fmt:message key="citcorpore.agendamento.atividade.dos_meses_selecionados" />
							 		</div>
						 		</div>
						 		<div class='row-fluid'>
							 		<div class='span12'>
							 			<div class='row-fluid'>
									 		<div class='span12'>
										 		<fmt:message key="citcorpore.agendamento.atividade.Selecione_meses_atividade_executada" />
									 		</div>
								 		</div>
							 			<div class='row-fluid'>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="jan" id="jan" value="S" />
													<fmt:message key="citcorpore.texto.mes.janeiro" />
												</label>
									 		</div>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="jul" id="jul" value="S" />
													<fmt:message key="citcorpore.texto.mes.julho" />
												</label>
									 		</div>
									 	</div>
									 	<div class='row-fluid'>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="fev" id="fev" value="S" />
													<fmt:message key="citcorpore.texto.mes.fevereiro" />
												</label>
									 		</div>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="ago" id="ago" value="S" />
													<fmt:message key="citcorpore.texto.mes.agosto" />
												</label>
									 		</div>
									 	</div>
									 	<div class='row-fluid'>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="mar" id="mar" value="S" />
													<fmt:message key="citcorpore.texto.mes.marco" />
												</label>
									 		</div>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="set" id="set" value="S" />
													<fmt:message key="citcorpore.texto.mes.setembro" />
												</label>
									 		</div>
									 	</div>
									 	<div class='row-fluid'>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="abr" id="abr" value="S" />
													<fmt:message key="citcorpore.texto.mes.abril" />
												</label>
									 		</div>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="out" id="out" value="S" />
													<fmt:message key="citcorpore.texto.mes.outubro" />
												</label>
									 		</div>
									 	</div>
									 	<div class='row-fluid'>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="mai" id="mai" value="S" />
													<fmt:message key="citcorpore.texto.mes.maio" />
												</label>
									 		</div>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="nov" id="nov" value="S" />
													<fmt:message key="citcorpore.texto.mes.novembro" />
												</label>
									 		</div>
									 	</div>
									 	<div class='row-fluid'>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="jun" id="jun" value="S" />
													<fmt:message key="citcorpore.texto.mes.junho" />
												</label>
									 		</div>
									 		<div class='span6'>
												<label class="radio">
													<input type="checkbox" name="dez" id="dez" value="S" />
													<fmt:message key="citcorpore.texto.mes.dezembro" />
												</label>
									 		</div>
									 	</div>

								 	</div>
							 	</div>
							</div>
						</div>
						<div class='row-fluid'>
					 		<div class='span12'>
								<label class="radio">
									<input type="checkbox" name="repeticao" id="repeticao" value="S" onclick="configuraRepeticao(this)" />
									<fmt:message key="citcorpore.agendamento.atividade.Repetir_agendamento_no_dia" />
								</label>
					 		</div>
					 	</div>
					 	<div id="divConfigRepeticao" class='row-fluid hide'>
					 		<div class='span6'>
								<%-- <label><fmt:message key="citcorpore.agendamento.Agendar_atividade_diaria" /></label> --%>
								<fmt:message key="citcorpore.agendamento.atividade.A_cada" />
								<div class='displayInBk'>
									<input type="text" name="repeticaoIntervalo" id="repeticaoIntervalo" size="5" maxlength="5" class="Format[Numero] text" />
								</div>
								<div class='displayInBk'>
									<select name="repeticaoTipoIntervalo" id="repeticaoTipoIntervalo">
										<option value="M">
											<fmt:message key="citcorpore.texto.tempo.minutos" />
										</option>
										<option value="H">
											<fmt:message key="citcorpore.texto.tempo.horas" />
										</option>
									</select>
								</div>
							</div>
							<div class='span6'>
								<fmt:message key="citcorpore.texto.Ate" />
								<div class='displayInBk'><input type="text" name="horaFim" id="horaFim" size="5" maxlength="5" class="Format[Hora] text" /></div>
							</div>
					 	</div>
					 	<div class='row-fluid'>
					 		<div class='span12'>
								<button type="button" id="btnGravarAgendamento"	name="btnGravarAgendamento"	style="margin-top: 5px; margin-left: 3px;"	class="light img_icon has_text" onclick="atualizaAgendamento();">
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span> <fmt:message key="citcorpore.ui.botao.rotulo.Gravar" /></span>
								</button>
								<button type="button" id="btnLimparAgendamento"	name="btnLimparAgendamento"	style="margin-top: 5px; margin-left: 3px;"	class="light img_icon has_text" onclick="limpaAgendamento()">
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span> <fmt:message key="citcorpore.ui.botao.rotulo.Limpar" /></span>
								</button>
								<button type="button" id="btnFecharAgendamento"	name="btnFecharAgendamento"	style="margin-top: 5px; margin-left: 3px;"	class="light img_icon has_text" onclick="fecharAgendamento()">
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span> <fmt:message key="citcorpore.ui.botao.rotulo.Fechar" />	</span>
								</button>
							</div>
					 	</div>
					</div>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript">
		var ctx = "${ctx}";
	</script>
	<script type="text/javascript" src="./js/atividadePeriodica.js"></script>
</html>

