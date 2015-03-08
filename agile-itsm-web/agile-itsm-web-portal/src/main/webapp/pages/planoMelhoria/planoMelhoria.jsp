<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>

<%@include file="/include/header.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css">
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.core.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.widget.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.position.js"></script>										
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.autocomplete.js"></script>	
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/lookup/jquery.ui.lookup.js"></script>

<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/css/jquery-easy.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.datepicker.css">		

<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/locale/easyui-lang-pt_BR.js"></script>
					
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/formparams/jquery.formparams.js"></script>					

<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.parser.js"></script>

<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/ajaxupload.3.5.js"></script>	
					
<link rel="stylesheet" type="text/css" href="./css/planoMelhoria.css" />
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
<script type="text/javascript" src="./js/planoMelhoria.js"></script>
	
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div style='width: 100%'>
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="planoMelhoria.titulo" />
				</h2>
			</div>
				<table>
					<tr>
						<td valign="top" style='vertical-align: top;'>
							<div style='border:1px solid black; width: 300px; height: 810px; overflow: auto'>
								<table>
									<tr>
										<td>
											<button onclick='novo()' type='button'><fmt:message key="planoMelhoria.novo"/></button>
										</td>
									</tr>
								</table>
								<ul id="tt" class="easyui-tree" data-options="url:'${ctx}/pages/planoMelhoriaTreeView/planoMelhoriaTreeView.load',animate: true, 
										onDblClick: function(node){
											abrirPopup(node.id, node.text);
										},onClick: function(node){
											if (node.id.indexOf('EDITAR-') > -1){
												editaContrato(StringUtils.onlyNumbers(node.id));
											}else if (node.id.indexOf('NOVOOBJ-') > -1){
												novoObjetivo(StringUtils.onlyNumbers(node.id));
											}else if (node.id.indexOf('NOVAACT-') > -1){
												novaAcao(StringUtils.onlyNumbers(node.id));
											}else if (node.id.indexOf('NOVOMON-') > -1){
												novoMonitoramento(StringUtils.onlyNumbers(node.id));												
											}else if (node.id.indexOf('EDITAROBJ-') > -1){
												editaObjetivo(StringUtils.onlyNumbers(node.id));
											}else if (node.id.indexOf('ACTEDT-') > -1){
												editaAcao(StringUtils.onlyNumbers(node.id));
											}else if (node.id.indexOf('MONEDT-') > -1){
												editaMonitoramento(StringUtils.onlyNumbers(node.id));												
											}
										}">
						        </ul>
							</div>
						</td>
						<td valign="top" style='vertical-align: top;'>
							<div style='border:1px solid black; width: 900px; height: 810px; overflow: auto'>
								<div id='divCadastroPlano' style='width: 100%; height: 100%'>
								  <form name='form' action='${ctx}/pages/planoMelhoria/planoMelhoria'>
									<input type='hidden' name='idPlanoMelhoria' id='idPlanoMelhoria' /> 
									<table>
										<tr>
											<td style='vertical-align: middle;' colspan="2">
												<b><fmt:message key="planoMelhoria.planomelhoria"/></b>
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.fornecedor"/>
											</td>
											<td>
												<select name='idFornecedor' id='idFornecedor' onchange='carregaContratos()' onclick='carregaContratos()'>
												</select>											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.contrato"/>
											</td>
											<td>
												<select name='idContrato' id='idContrato'>
												</select>
											</td>
										</tr>
										<tr>
											<td  style='vertical-align: middle;'>
												<label class="campoObrigatorio"><fmt:message key="planoMelhoria.tituloplano"/></label>
											</td>
											<td>
												<input type='text' name='titulo' id='titulo' maxlength='100' size='100' class='Valid[Required] Description[<fmt:message key="planoMelhoria.tituloplano"/>]' />											
											</td>
										</tr>	
										<tr>
											<td style='vertical-align: middle;'>
												<label class="campoObrigatorio"><fmt:message key="planoMelhoria.datainicio"/></label>
											</td>
											<td>
												<input type='text' name='dataInicio' id='dataInicio' maxlength='10' size='10' class='datepicker Format[Date] Valid[Required,Date] Description[<fmt:message key="planoMelhoria.datainicio"/>]' />											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.datafim"/>
											</td>
											<td>
												<input type='text' name='dataFim' id='dataFim' maxlength='10' size='10' class='datepicker Format[Date] Valid[Date] Description[<fmt:message key="planoMelhoria.datafim"/>]' />											
											</td>
										</tr>	
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.objetivo"/>
											</td>
											<td>
												<textarea name='objetivo' id='objetivo' rows='5' cols='100'></textarea>											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.visaogeral"/>
											</td>
											<td>
												<textarea name='visaoGeral' id='visaoGeral' rows='5' cols='100'></textarea>											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.escopo"/>
											</td>
											<td>
												<textarea name='escopo' id='escopo' rows='5' cols='100'></textarea>											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.visao"/>
											</td>
											<td>
												<textarea name='visao' id='visao' rows='5' cols='100'></textarea>											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.missao"/>
											</td>
											<td>
												<textarea name='missao' id='missao' rows='5' cols='100'></textarea>											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.notas"/>
											</td>
											<td>
												<textarea name='notas' id='notas' rows='5' cols='100'></textarea>											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.situacao"/>
											</td>
											<td>
												<select name='situacao' id='situacao'>
													<option value='E'><fmt:message key="planoMelhoria.situacao.emdesenv" /></option>
													<option value='A'><fmt:message key="planoMelhoria.situacao.ativo" /></option>
													<option value='C'><fmt:message key="planoMelhoria.situacao.cancelado" /></option>
												</select>		
											</td>
										</tr>	
										<tr>
											<td colspan="2">
												<b><label class="campoObrigatorio"></label></b> <fmt:message key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio" />
											</td>
										</tr>																			
									</table>
									<button type='button' name='btnGravar' class="light"
										onclick='gravarPlano();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type='button' name='btnGravar' class="light"
										onclick='imprimirDocumentoPlanoMelhoria();'>
										<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
										<span><fmt:message key="citcorpore.comum.gerarrelatorio"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light"
										onclick='document.form.clear();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" />
										</span>
									</button>	
								  </form>								
								</div>
								<div id='divObjetivos' style='width: 100%; height: 100%; display: none'>
								  <form name='formObj' action='${ctx}/pages/planoMelhoria/planoMelhoria'>
									<input type='hidden' name='idPlanoMelhoriaAux1' id='idPlanoMelhoriaAux1' /> 								
									<input type='hidden' name='idObjetivoPlanoMelhoria' id='idObjetivoPlanoMelhoria' />
									<table>
										<tr>
											<td style='vertical-align: middle;' colspan="2">
												<b><fmt:message key="planoMelhoria.objetivo.objetivo"/></b>
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<label class="campoObrigatorio"><fmt:message key="planoMelhoria.objetivo.titulo"/></label>
											</td>
											<td>
												<input type='text' name='tituloObjetivo' id='tituloObjetivo' maxlength='255' size='100' class='Valid[Required] Description[<fmt:message key="planoMelhoria.objetivo.titulo"/>]' />										
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.objetivo.detalhamento"/>
											</td>
											<td>
												<textarea name='detalhamento' id='detalhamento' rows='5' cols='100'></textarea>
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.objetivo.resultado"/>
											</td>
											<td>
												<textarea name='resultadoEsperado' id='resultadoEsperado' rows='5' cols='100'></textarea>
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.objetivo.medicao"/>
											</td>
											<td>
												<input type='text' name='medicao' id='medicao' maxlength='255' size='100' />
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.objetivo.responsavel"/>
											</td>
											<td>
												<input type='text' name='responsavel' id='responsavel' maxlength='255' size='100' />
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<b></b><label class="campoObrigatorio"></label> <fmt:message key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio" />
											</td>
										</tr>																				
									</table>	
									<button type='button' name='btnGravarObj' class="light"
										onclick='gravarObjetivo();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type='button' name='btnLimparObj' class="light"
										onclick='document.formObj.clear();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" />
										</span>
									</button>										
								  </form>							
								</div>
								<div id='divAcoes' style='width: 100%; height: 100%; display: none'>
								  <form name='formAcao' action='${ctx}/pages/planoMelhoria/planoMelhoria'>
									<input type='hidden' name='idAcaoPlanoMelhoria' id='idAcaoPlanoMelhoria' />
									<input type='hidden' name='idObjetivoPlanoMelhoria' id='idObjetivoPlanoMelhoriaAcao' />
									<table>
										<tr>
											<td style='vertical-align: middle;' colspan="2">
												<b><fmt:message key="planoMelhoria.acoes.acao"/></b>
											</td>
										</tr>									
										<tr>
											<td style='vertical-align: middle;'>
												<label class="campoObrigatorio"><fmt:message key="planoMelhoria.acoes.titulo"/></label>
											</td>
											<td>
												<input type='text' name='tituloAcao' id='tituloAcao' maxlength='255' size='100' class='Valid[Required] Description[<fmt:message key="planoMelhoria.acoes.titulo"/>]' />										
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.acoes.detalhamento"/>
											</td>
											<td>
												<textarea name='detalhamentoAcao' id='detalhamentoAcao' rows='5' cols='100'></textarea>
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<label class="campoObrigatorio"><fmt:message key="planoMelhoria.acoes.datainicio"/></label>
											</td>
											<td>
												<input type='text' name='dataInicio' id='dataInicio' maxlength='10' size='10' class='datepicker Format[Date] Valid[Required,Date] Description[<fmt:message key="planoMelhoria.datainicio"/>]' />											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<label class="campoObrigatorio"><fmt:message key="planoMelhoria.acoes.datafim"/></label>
											</td>
											<td>
												<input type='text' name='dataFim' id='dataFim' maxlength='10' size='10' class='datepicker Format[Date] Valid[Date] Description[<fmt:message key="planoMelhoria.datafim"/>]' />											
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.monitoramento.responsavel"/>
											</td>
											<td>
												<input type='text' name='responsavel' id='responsavelAcao' maxlength='255' size='100' />
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<b></b><label class="campoObrigatorio"></label><fmt:message key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio" />
											</td>
										</tr>																														
									</table>
									<button type='button' name='btnGravarAcao' class="light"
										onclick='gravarAcao();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type='button' name='btnLimparAcao' class="light"
										onclick='document.formAcao.clear();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" />
										</span>
									</button>									
								  </form>								
								</div>
								<div id='divMonitoramento' style='width: 100%; height: 100%; display: none'>
								  <form name='formMonitoramento' action='${ctx}/pages/planoMelhoria/planoMelhoria'>
									<input type='hidden' name='idObjetivoMonitoramento' id='idObjetivoMonitoramento' />
									<input type='hidden' name='idObjetivoPlanoMelhoria' id='idObjetivoPlanoMelhoriaMonitoramento' />
									<table>
										<tr>
											<td style='vertical-align: middle;' colspan="2">
												<b><fmt:message key="planoMelhoria.monitoramento.monitoramento"/></b>
											</td>
										</tr>									
										<tr>
											<td style='vertical-align: middle;'>
												<label class="campoObrigatorio"><fmt:message key="planoMelhoria.monitoramento.titulo"/></label>
											</td>
											<td>
												<input type='text' name='tituloMonitoramento' id='tituloMonitoramento' maxlength='255' size='100' class='Valid[Required] Description[<fmt:message key="planoMelhoria.monitoramento.titulo"/>]' />										
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<label class="campoObrigatorio"><fmt:message key="planoMelhoria.monitoramento.fatorcriticosucesso"/></label>
											</td>
											<td>
												<input type='text' name='fatorCriticoSucesso' id='fatorCriticoSucesso' maxlength='255' size='100' class='Valid[Required] Description[<fmt:message key="planoMelhoria.monitoramento.fatorcriticosucesso"/>]' />										
											</td>
										</tr>	
										<tr>
											<td style='vertical-align: middle;'>
												<label class="campoObrigatorio"><fmt:message key="planoMelhoria.monitoramento.kpi"/></label>
											</td>
											<td>
												<input type='text' name='kpi' id='kpi' maxlength='255' size='100' class='Valid[Required] Description[<fmt:message key="planoMelhoria.monitoramento.kpi"/>]' />										
											</td>
										</tr>	
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.monitoramento.metrica"/>
											</td>
											<td>
												<textarea name='metrica' id='metrica' rows='5' cols='100'></textarea>
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.monitoramento.medicao"/>
											</td>
											<td>
												<textarea name='medicao' id='medicao' rows='5' cols='100'></textarea>
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.monitoramento.relatorios"/>
											</td>
											<td>
												<textarea name='relatorios' id='relatorios' rows='5' cols='100'></textarea>
											</td>
										</tr>
										<tr>
											<td style='vertical-align: middle;'>
												<fmt:message key="planoMelhoria.monitoramento.responsavel"/>
											</td>
											<td>
												<input type='text' name='responsavel' id='responsavelMon' maxlength='255' size='100' />
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<b></b><label class="campoObrigatorio"></label> <fmt:message key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio" />
											</td>
										</tr>										
									</table>
									<button type='button' name='btnGravarMon' class="light"
										onclick='gravarMonitoramento();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type='button' name='btnLimparMon' class="light"
										onclick='document.formMonitoramento.clear();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" />
										</span>
									</button>									
								  </form>								
								</div>
							</div>
						</td>
					</tr>
				</table>
		</div>
		
		<div id="POPUP_EXPORTAR" style='display:none;' title="<fmt:message key="dataManager.objetoNegocio"/>">
			<form name='form2' action='${ctx}/pages/dataManager/dataManager'>
				<input type='hidden' name='idObjetoNegocio' id='idObjetoNegocio2' /> 			
				<div id='descObjetoNegocio'>
				</div>
				<div id='divExport'>
				</div>
			</form>
		</div>
				
	<%@include file="/include/footer.jsp"%>
</body>
</html>


