<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<!doctype html public "">
<html>
	<head>
	<%
	    String iframe = "";
	    iframe = request.getParameter("iframe");
	%>

	<%@include file="/include/header.jsp"%>
	<title><fmt:message key="citcorpore.comum.title" /></title>

	<%@include file="/include/security/security.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<script charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/SLA/RequisitoSLA.js"></script>
	<script charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/SLA/RevisarSLA.js"></script>
	<script charset="ISO-8859-1"  type="text/javascript" src="${ctx}/js/SLA/PrioridadeSLA.js"></script>

	<script charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

	<script type="text/javascript" src="../../cit/objects/PrioridadeAcordoNivelServicoDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/PrioridadeServicoUsuarioDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/SlaRequisitoSlaDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/RevisarSlaDTO.js"></script>

	<link rel="stylesheet" type="text/css" href="./css/acordoNivelServico.css" />
	<script type="text/javascript">
		var ctx = "${ctx}";
	</script>
	<script type="text/javascript" src="./js/acordoNivelServico.js"></script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<!-- Conteudo -->
		<div id="wrapper" class="wrapper">
			<div id="main_container" class="main_container container_16 clearfix" style='margin: 10px 10px 10px 10px'>
				<%@include file="/include/menu_horizontal.jsp"%>
				<div class="flat_area grid_16">
					<h2>
						<fmt:message key="acordoNivelServico.AcordoNivelServicoGeral" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="acordoNivelServico.cadastroAcordoNivelServicoGeral" /></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="acordoNivelServico.pesquisaAcordoNivelServicoGeral" /></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/acordoNivelServico/acordoNivelServico">
									<input type='hidden' name='idAcordoNivelServico' id='idAcordoNivelServico'/>
									<div class="columns clearfix">
										<div class="col_60">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="visao.tituloAcordo" />
												</label>
												<div>
													<input type='text' name="tituloSLA" maxlength="500" class="Valid[Required] Description[visao.tituloAcordo]" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="visao.tipoAcordo" />
												</label>
												<div style="height: 35px;">
													<select id="tipo" onchange="alterarTipoSLA()" name="tipo" class="noClearCITAjax">
														<option value=""><fmt:message key="citcorpore.comum.selecione"/></option>
														<option value="D"><fmt:message key="requisitosla.disponibilidade"/></option>
														<option value="T"><fmt:message key="requisitosla.tempo_fases"/></option>
														<option value="V"><fmt:message key="requisitosla.informacoes_outras_fontes"/></option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="visao.impacto" />
												</label>
												<div style="height: 35px;">
													<select id="impacto" name="impacto" class="noClearCITAjax">
														<option value="A"><fmt:message key="requisitosla.alto"/></option>
														<option value="M"><fmt:message key="requisitosla.medio"/></option>
														<option value="B"><fmt:message key="requisitosla.baixo"/></option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="visao.urgencia" />
												</label>
												<div style="height: 35px;">
													<select id="urgencia" name="urgencia" class="noClearCITAjax">
														<option value="A"><fmt:message key="requisitosla.alta"/></option>
														<option value="M"><fmt:message key="requisitosla.media"/></option>
														<option value="B"><fmt:message key="requisitosla.baixa"/></option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="visao.permiteMudImpUrg" />
												</label>
												<div style="height: 35px;">
													<input type="radio" id="pertmiteMudarImpUrgN" name="permiteMudarImpUrg" checked="checked" value="N" style="margin-top: 10px" /><fmt:message key="requisitosla.nao" />
													<input type="radio" id="pertmiteMudarImpUrgS" name="permiteMudarImpUrg" value="S" style="margin-left: 30px"/><fmt:message key="requisitosla.sim" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="visao.situacao" />
												</label>
												<div style="height: 35px;">
													<input type="radio" id="situacaoAtivo" name="situacao" checked="checked" value="A" style="margin-top: 10px" /><fmt:message key="requisitosla.ativo" />
													<input type="radio" id="situacaoInativo" name="situacao" value="I" style="margin-left: 30px"/><fmt:message key="requisitosla.inativo" />
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="visao.descricaoAcordo" />
												</label>
												<div>
													<textarea id="descricaoSLA" name="descricaoSLA" maxlength="4000" cols="50" rows="5" class="Valid[Required] Description[visao.descricaoAcordo]"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="visao.escopoAcordo" />
												</label>
												<div>
													<textarea id="escopoSLA" name="escopoSLA" maxlength="4000" cols="50" rows="5" class="Description[visao.escopoAcordo]"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="visao.dataDeInicio"/>
												</label>
												<div>
													<input type='text' id="dataInicio" name="dataInicio" maxlength="10" size="10" class="Valid[Data, Required] Description[visao.dataDeInicio] Format[Data] datepicker" />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.comum.datafim"/>
												</label>
												<div>
													<input type='text' id="dataFim" name="dataFim" maxlength="10" size="10" class="Valid[Data] Description[citcorpore.comum.datafim] Format[Data] datepicker" />
												</div>
											</fieldset>
										</div>
										<div class="col_20">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="sla.avaliarem"/>
												</label>
												<div>
													<input type='text' id="avaliarEm" name="avaliarEm" maxlength="10" size="10" class="Valid[Data, Required] Description[sla.avaliarem] Format[Data] datepicker" />
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="visao.contatos" />
												</label>
												<div>
													<textarea id="contatos" name="contatos" maxlength="65535" cols="50" rows="5" class="Description[visao.contatos]"></textarea>
												</div>
											</fieldset>
										</div>
									</div>

									<!-- Início da parte dinâmica -->
									<div class="col_100">
										<div id="divByDisponibilidade" style="display: none;" >
											<div class="col_100" style="padding-bottom: 10px;">
												<b><fmt:message key="sla.acordoservicodisponibilidade" /></b>
											</div>
											<div class="col_20">
												<fieldset>
													<label style="margin-bottom: 3px; margin-top: 5px;">
														<fmt:message key="sla.indicedisponibilidade" />
													</label>
													<div style="float: left; width: 65px; padding-right: 10px;">
														<input type="text" name="disponibilidade" size="5" maxlength="5" class="Format[Money]" id="disponibilidade"/>
													</div>
													<div style="padding-top: 10px;">%</div>
												</fieldset>
											</div>
										</div>
										<div id="divByTempos" style="display: none; padding-bottom: 145px;">
											<br>
											<b><fmt:message key="sla.alvostempo"/></b><br>
											<table border="1" width="100%" style="margin-top: 5px;">
												<tr style='color: white;'>
													<td>
														&nbsp;
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 1 ---</b>
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 2 ---</b>
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 3 ---</b>
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 4 ---</b>
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 5 ---</b>
													</td>
												</tr>
												<tr>
													<td>
														<fmt:message key="sla.captura"/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-1' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-1' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-2' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-2' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-3' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-3' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-4' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-4' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-5' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-5' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
												</tr>
												<tr>
													<td>
														<fmt:message key="sla.resolucao"/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-1' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-1' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-2' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-2' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-3' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-3' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-4' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-4' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-5' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-5' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
												</tr>
											</table>
											<br>
											<b><fmt:message key="sla.automacao"/></b><br>
											<fieldset style="padding-bottom: 10px; padding-top: 10px;">
												<table border="1" width="100%">
													<tr>
														<td style="width: 100px; padding-bottom: 25px;">
															<fmt:message key="sla.actiontime"/>
														</td>
														<td>
															<div id="divTempoAuto"></div>
														</td>
													</tr>
													<tr>
														<td colspan="2">
																<label style="font-weight: bold;"><fmt:message key="sla.action"/></label>
																<table>
																	<tr>
																		<td>
																			<fmt:message key="sla.prioridade"/>
																		</td>
																		<td>
																			<select name="IDPRIORIDADEAUTO1" id="IDPRIORIDADEAUTO1"></select>
																		</td>
																		<td>
																			<fmt:message key="sla.grupo"/>
																		</td>
																		<td>
																			<select name="IDGRUPO1" id="IDGRUPO1"></select>
																		</td>

																		<td>
																			<fmt:message key="acordoNivelServico.modeloEmail"/>
																		</td>
																		<td>
																			<select name="idEmail" id="idEmail"></select>
																		</td>
																	</tr>
																</table>
														</td>
													</tr>
												</table>
											</fieldset>
											<div id="gridPrioridadeUnidade" class="col_50">
												<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
													<b><fmt:message key="acordoNivelServico.prioridadeUnidade" /></b>
												</div>
												<div class="col_100">
													 <fieldset >
														<label style="margin-bottom: 3px; margin-top: 10px; float: left;"><fmt:message key="acordoNivelServico.buscaUnidade" />*</label>
														<div style="float: left; width: 330px; padding-left: 0px;">
															<input type='hidden' id="idUnidadePrioridade" name='idUnidadePrioridade'/>
															<input id="addUnidade" type='text' readonly="readonly" name="addUnidade" maxlength="80" />
														</div>
														<button type="button" style="margin-top: 1px!important;" onclick="$('#addUnidade').val('')" class='light icon_only text_only'><fmt:message key="citcorpore.ui.botao.rotulo.Limpar" /></button>
														</fieldset>
														</div>
														<div class="col_100">
														 <fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
														<label style="margin-bottom: 3px; margin-top: 10px; float: left;"><fmt:message key="prioridade.prioridade" />*</label>
														<div style="float: left; width: 50px; padding-left: 0px;">
															<div style="height: 15px;">
																<select id="prioridadeUnidade" name="prioridadeUnidade" class="noClearCITAjax">
																	<option value="">--</option>
																	<option value="1">1</option>
																	<option value="2">2</option>
																	<option value="3">3</option>
																	<option value="4">4</option>
																	<option value="5">5</option>
																</select>
															</div>
														</div>
														<img title="Adicionar Prioridade" src="/citsmart/imagens/add.png" onclick="addUnidadeRow();" border="0" style="cursor:pointer; padding-top: 10px;">
													 </fieldset>
												</div>
												<input type='hidden' id="prioridadeUnidadeSerializados" name='prioridadeUnidadeSerializados'/>
												<table class="table" id="tabelaPrioridadeUnidade" style="width: 500px; margin-left: 15px;">
													<tr>
														<th style="width: 16px !important;"></th>
														<th style="width: 70%;"><fmt:message key="citcorpore.comum.unidade" /></th>
														<th style="width: 20%;"><fmt:message key="prioridade.prioridade" /></th>
													</tr>
												</table>
											</div>
											<div id="gridPrioridadeUsuario" class="col_50">
												<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
													<b><fmt:message key="acordoNivelServico.prioridadeUsuario" /></b>
												</div>
												<div class="col_100" style="">
													<fieldset>
														<label style="margin-bottom: 3px; margin-top: 10px; float: left;"><fmt:message key="acordoNivelServico.buscaUsuario" />*</label>
														<div style="float: left; width: 300px; padding-left: 0px;">
															<input type='hidden' id="idUsuarioPrioridade" name='idUsuarioPrioridade'/>
															<input id="addUsuario" type='text' readonly="readonly" name="addUsuario" maxlength="80" />
														</div>
														<button type="button" style="margin-top: 1px!important;" onclick="$('#addUsuario').val('')" class='light icon_only text_only'><fmt:message key="citcorpore.ui.botao.rotulo.Limpar" /></button>
														</fieldset>
														</div>
														<div class="col_100" style="">
														<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px; ">
														<label style="margin-bottom: 3px; margin-top: 10px; float: left;"><fmt:message key="prioridade.prioridade" />*</label>
														<div style="float: left; width: 50px; padding-left: 0px;">
															<div style="height: 15px;">
																<select id="prioridadeUsuario" name="prioridadeUsuario" class="noClearCITAjax">
																	<option value="">--</option>
																	<option value="1">1</option>
																	<option value="2">2</option>
																	<option value="3">3</option>
																	<option value="4">4</option>
																	<option value="5">5</option>
																</select>
															</div>
														</div>
														<img title="Adicionar Prioridade" src="/citsmart/imagens/add.png" onclick="addUsuarioRow();" border="0" style="cursor:pointer; padding-top: 10px;">
													</fieldset>
												</div>
												<input type='hidden' id="prioridadeUsuarioSerializados" name='prioridadeUsuarioSerializados'/>
												<table class="table" id="tabelaPrioridadeUsuario" style="width: 500px; margin-left: 15px;">
													<tr>
														<th style="width: 16px !important;"></th>
														<th style="width: 70%;"><fmt:message key="citcorpore.comum.usuario" /></th>
														<th style="width: 20%;"><fmt:message key="prioridade.prioridade" /></th>
													</tr>
												</table>
											</div>
										</div>

										<div id="divByDiversos" style="display: none;">
											<div class="col_100" style="padding-bottom: 10px;">
												<b><fmt:message key="sla.acordoservico" /></b>
											</div>
											<div class="col_25">
												<fieldset>
													<label style="margin-bottom: 3px;">
														<fmt:message key="visao.valorLimite" />
													</label>
													<div style="float: left; width: 150px;">
														<input type="text" name="valorLimite" size="15" maxlength="15" class="Format[Money]"/>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label style="margin-bottom: 3px;">
														<fmt:message key="sla.unidade" />
													</label>
													<div style="float: left; width: 200px;">
														<input type="text" name="unidadeValorLimite" size="150" maxlength="150"/>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label style="margin-bottom: 3px;">
														<fmt:message key="visao.glosa" />
													</label>
													<div>
														<textarea name="detalheGlosa" maxlength="2000" cols="50" rows="5" class="Description[visao.glosa]"></textarea>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label style="margin-bottom: 3px;">
														<fmt:message key="visao.limiteGlosa" />
													</label>
													<div>
														<textarea name="detalheLimiteGlosa" maxlength="2000" cols="50" rows="5" class="Description[visao.limiteGlosa]"></textarea>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<!-- Fim da parte dinâmica -->

									<div class="col_100" style="padding-top: 30px; padding-bottom: 30px;">
										<button type="button" name="btnGravar" class="light" onclick="gravar();">
											<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
											<span><fmt:message key="citcorpore.comum.gravar"/></span>
										</button>
										<button type="button" name="btnLimpar" class="light" onclick="limpar();">
											<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar"/></span>
										</button>
										<button type="button" name="btnUpDate" class="light" onclick="excluir();">
											<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
											<span><fmt:message key="citcorpore.comum.excluir" />
											</span>
										</button>
									</div>

									<!-- Início das abas inferiores de Relacionamentos -->
									<div  id="abas" class="formRelacionamentos">
										<div id="tabs" class="block" style="padding-bottom: 50px;">
											<ul class="tab_header clearfix">
												<li>
													<a href="#relacionarRequisitoSla"><fmt:message key="slarequisitosla.titulo"/></a>
												</li>
												<li>
													<a href="#relacionarContratoVincCliente"><fmt:message key="sla.listacontratosvinculadoscliente"/></a>
												</li>
												<li>
													<a href="#relacionarContratoVincAno"><fmt:message key="sla.listacontratosvinculadosano"/></a>
												</li>
												<li>
													<a href="#relacionarContratoVincTerceiro"><fmt:message key="sla.listacontratosvinculadosterceiro"/></a>
												</li>
												<li>
													<a href="#relacionarHistoricoAuditoria"><fmt:message key="sla.historicoauditoria"/></a>
												</li>
												<li>
													<a href="#relacionarRevisarSla"><fmt:message key="sla.revisar.revisar"/></a>
												</li>
											</ul>
											<div id="relacionarRequisitoSla">
												<div id="contentrequisitoSla">
													<input type='hidden' id="requisitoSlaSerializados" name='requisitoSlaSerializados'/>
													<div class="col_20" style="border: none; width: 20%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="slarequisitosla.datavinculacao" />*
															</label>
															<input type='text' id="dataVinculacaoSLA" name="dataVinculacaoSLA" maxlength="10" size="10" class="Valid[Data] Description[slarequisitosla.datavinculacao] Format[Data] datepicker" />
														</fieldset>
													</div>
													<div class="col_20" style="border: none; width: 20%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="slarequisitosla.reqslr" />*
															</label>
															<input type='hidden' id="idRequisitoSLAVinc" name='idRequisitoSLAVinc' /> <input id="addRequisitoSLA" type='text' readonly="readonly" name="addRequisitoSLA" maxlength="80" />
														</fieldset>
													</div>
													<div class="col_15" style="border: none; width: 5%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<div style="border: none; padding-top: 25px; padding-left: 5px;">
																<img title="Adicionar Requisito SLA" src="/citsmart/imagens/add.png" onclick="addRequisitoSLARow();" border="0" style="cursor: pointer">
															</div>
														</fieldset>
													</div>
													<div class="col_50" style="border: none; width: 50%; float: left; padding-left: 50px;">
														<fieldset style="border: none;">
															<table class="table" id="tabelaRequisitoSLA" style="width: 500px; margin-top: 5px;">
																<tr>
																	<th style="width: 16px !important;"></th>
																	<th style="width: 30%;"><fmt:message key="slarequisitosla.datavinculacao" /></th>
																	<th style="width: 60%;"><fmt:message key="slarequisitosla.reqslr" /></th>
																</tr>
															</table>
														</fieldset>
													</div>
												</div>
												<label style="color: red;"><fmt:message key="acordoNivelServico.avisoSalvar"/></label>
											</div>
											<div id="relacionarContratoVincCliente">
												<div style="border: none !important;" id="contratoVincCliente"></div>
											</div>
											<div id="relacionarContratoVincAno">
												<div style="border: none !important;" id="contratoVincAno"></div>
											</div>
											<div id="relacionarContratoVincTerceiro">
												<div style="border: none !important;" id="contratoVincTerceiro"></div>
											</div>
											<div id="relacionarHistoricoAuditoria">
												<div style="border: none !important;" id="historicoAuditoria"></div>
											</div>
											<div id="relacionarRevisarSla">
												<div id="contentrevisarSla">
													<input type='hidden' id="revisarSlaSerializados" name='revisarSlaSerializados'/>
													<div class="col_20" style="border: none; width: 13%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 0px; font-weight: bolder;">
																<fmt:message key="sla.revisar.data"/>*
															</label>
															<input type='text' id="dataRevisarSLA" name="dataRevisarSLA" maxlength="10" size="10" class="Valid[Data] Description[sla.revisar.data] Format[Data] datepicker" />
														</fieldset>
													</div>
													<div class="col_20" style="border: none; width: 15%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="sla.revisar.detalhes"/>*
															</label>
															<textarea id="detalhesSLA" name="detalhesSLA" maxlength="2000" cols="50" rows="3" class="Description[sla.revisar.detalhes]"></textarea>
														</fieldset>
													</div>
													<div class="col_20" style="border: none; width: 15%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="sla.revisar.observacao"/>
															</label>
															<textarea id="observacaoSLA" name="observacaoSLA" maxlength="200" cols="50" rows="3" class="Description[sla.revisar.observacao]"></textarea>
														</fieldset>
													</div>
													<div class="col_15" style="border: none; width: 3%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<div style="border: none; padding-top: 30px; padding-left: 20px;">
																<img title="Adicionar Revisar SLA" src="/citsmart/imagens/add.png" onclick="addRevisarSLARow();" border="0" style="cursor:pointer">
															</div>
														</fieldset>
													</div>
													<div class="col_50" style="border: none; width: 45%; float: left; padding-left: 50px;">
														<fieldset style="border: none;">
															<table class="table" id="tabelaRevisarSLA" style="width: 750px; margin-top: 5px;">
																<tr>
																	<th style="width: 10px !important;"></th>
																	<th style="width: 15%;"><fmt:message key="sla.revisar.data" /></th>
																	<th style="width: 45%;"><fmt:message key="sla.revisar.detalhes" /></th>
																	<th style="width: 35%;"><fmt:message key="sla.revisar.observacao" /></th>
																</tr>
															</table>
														</fieldset>
													</div>
												</div>
												<label style="color: red;"><fmt:message key="acordoNivelServico.avisoSalvar"/></label>
											</div>
										</div>
									</div>
									<!-- Fim das abas inferiores de Relacionamentos -->
								</form>
							</div>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
							<form name='formPesquisa'>
								<cit:findField formName="formPesquisa" lockupName="LOOKUP_ACORDONIVELSERVICOGLOBAL" id="LOOKUP_ACORDONIVELSERVICOGLOBAL" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<div id="POPUP_UNIDADE" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style="width: 560px !important;height: 560px !important;" >
				<div class="toggle_container">
					<div class="block">
						<div class="section"  >
							<form name="formPesquisaUnidade" style="width: 530px !important;" >
								<cit:findField formName="formPesquisaUnidade" lockupName="LOOKUP_UNIDADE" id="LOOKUP_UNIDADE" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_USUARIO" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style="width: 560px !important;height: 560px !important;" >
				<div class="toggle_container">
					<div class="block">
						<div class="section">
							<form name="formPesquisaUsuario" style="width: 530px !important;" >
								<cit:findField formName="formPesquisaUsuario" lockupName="LOOKUP_SOLICITANTE" id="LOOKUP_SOLICITANTE" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_REQUISITOSLA" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style="width: 560px !important;height: 560px !important;" >
				<div class="toggle_container">
					<div class="block">
						<div class="section">
							<form name="formRequisitoSLA" style="width: 530px !important;" >
								<cit:findField formName="formRequisitoSLA" lockupName="LOOKUP_REQUISITOSLA" id="LOOKUP_REQUISITOSLA" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>


