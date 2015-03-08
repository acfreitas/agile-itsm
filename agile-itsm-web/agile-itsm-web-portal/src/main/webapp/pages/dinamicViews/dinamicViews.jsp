<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>
<%@page import="br.com.citframework.util.Constantes" %>
<%@page import="java.util.HashMap" %>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO" %>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.BotaoAcaoVisaoDTO" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Collection" %>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO" %>
<%@page import="br.com.citframework.util.UtilStrings" %>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO" %>
<%@page import="br.com.centralit.citcorpore.metainfo.util.MetaUtil" %>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO" %>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO" %>
<%@page import="java.util.Iterator" %>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoDTO" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@page import="br.com.citframework.dto.Usuario" %>
<%@page import="br.com.citframework.util.UtilI18N" %>

<!DOCTYPE html>
<html>
<head>
<%
	String scriptToLOad = "";
	boolean temAbaFilha = false;

	VisaoDTO visaoDto = (VisaoDTO) request.getAttribute("visao");
	Collection colBotoes = (Collection) request.getAttribute("botoes");
	String titulo = "";
	String idVisao = "";
	if (visaoDto != null) {
		titulo = visaoDto.getDescricao();
		idVisao = "" + visaoDto.getIdVisao();
	} else {
		visaoDto = new VisaoDTO();
		visaoDto.setMapHtmlCodes(new HashMap());
		visaoDto.setMapScripts(new HashMap());
		visaoDto.setDescricao("");
		visaoDto.setTipoVisao(VisaoDTO.EDIT);
	}
	String strArrayNamesTablesVinc = "";
	String strArrayTablesVincMatriz = "";
	String strArrayControleRenderVinc = "";

	String scriptsIniciais = "";
	String modoExibicao = (String) request.getParameter("modoExibicao");
	if (modoExibicao == null || modoExibicao.trim().length() == 0)
		modoExibicao = "N";

	if (modoExibicao.equals("J")) {
		request.setAttribute("menustyle", "SHORT");
	}

	String idFluxo = UtilStrings.nullToVazio((String) request.getParameter("idFluxo"));
	String idTarefa = UtilStrings.nullToVazio((String) request.getParameter("idTarefa"));
	String acaoFluxo = UtilStrings.nullToVazio((String) request.getParameter("acaoFluxo"));

	String descrvisao = visaoDto.getDescricao();
	descrvisao = UtilStrings.nullToVazio(descrvisao).trim();
	if (descrvisao.startsWith("$")){
		descrvisao = UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, descrvisao));
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=descrvisao%></title>
<style type="text/css">
.tab_header ul, .tabs-wrap ul {
	height: auto !important;
	border: 0 !important;
}
</style>
<%@include file="/include/header.jsp" %>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.position.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.autocomplete.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/novoLayout/common/include/js/barra-rolagem/perfect-scrollbar.css">
<script type="text/javascript" src="${ctx}/novoLayout/common/include/js/barra-rolagem/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/include/js/barra-rolagem/perfect-scrollbar.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-easy.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.ui.datepicker.css">

<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/formparams/jquery.formparams.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.parser.js"></script>

<script type="text/javascript">
	var objTab = null;
	var trObj = null;
	var NAME_REMOVE = 'Excluído';
	var tabsInf = new Array();
	var tabsVincLado = new Array();
	var limpo = true;

	var arrayNamesTablesVinc = null;
	var arrayTablesVincMatriz = null;
	var arrayControleRenderVinc = null;

	function salvar(){
		var jsonAux = '';
		document.form.jsonMatriz.value = '';
		<%if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {%>
			$('#<%=VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoDto.getIdVisao()%>').datagrid('acceptChanges');
			$('#<%=VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoDto.getIdVisao()%>').datagrid('rejectChanges');
			var rowsMatriz = $('#<%=VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoDto.getIdVisao()%>').datagrid('getRows');
			var dadosStrMatriz = '';
			var jsonAuxMatriz = '';
			for (var j = 0; j < rowsMatriz.length; j++){
				var json_data = JSON.stringify(rowsMatriz[j]);
				if (dadosStrMatriz != ''){
					dadosStrMatriz = dadosStrMatriz + ',';
				}
				dadosStrMatriz = dadosStrMatriz + json_data;
			}
			if (dadosStrMatriz != ''){
				jsonAuxMatriz = jsonAuxMatriz + '{"MATRIZ": [' + dadosStrMatriz + ']}';
			}
			document.form.jsonMatriz.value = jsonAuxMatriz;
		<%}%>
		document.form.dinamicViewsDadosAdicionais.value = '';
		if (arrayNamesTablesVinc != null){
			for(var i = 1; i < arrayNamesTablesVinc.length; i++){
				$('#' + arrayNamesTablesVinc[i]).datagrid('acceptChanges');
			}
			for(var i = 1; i < arrayNamesTablesVinc.length; i++){
				if (i == 1){
					jsonAux = jsonAux + '{';
				}
				var dadosStr = '';
				var rows = $('#' + arrayNamesTablesVinc[i]).datagrid('getRows');
				for (var j = 0; j < rows.length; j++){
					var json_data = JSON.stringify(rows[j]);
					if (dadosStr != ''){
						dadosStr = dadosStr + ',';
					}
					dadosStr = dadosStr + json_data;
				}
				if (dadosStr != ''){
					if (jsonAux != '' && jsonAux != '{'){
						jsonAux = jsonAux + ',';
					}
					if (arrayNamesTablesVinc[i] != undefined){
						jsonAux = jsonAux + '"' + arrayNamesTablesVinc[i] + '": [' + dadosStr + ']';
					}
				}
			}
			if (jsonAux != ''){
				jsonAux = jsonAux + '}';
			}
		}
		document.form.dinamicViewsDadosAdicionais.value = jsonAux;
		var retValid = valid_scripts();
		if (retValid != undefined && !retValid){
			return;
		}
		if (!document.form.validate()){
			return;
		}
		JANELA_AGUARDE_MENU.show();
		document.form.save();
	}

	function carregaVinculacoes(){
		try{
			if (arrayNamesTablesVinc != null){
				var jsonDataFormEdit = $('#' + document.form.name).formParams(false);
				for(var i = 0; i < arrayNamesTablesVinc.length; i++){
					if (arrayNamesTablesVinc[i] != ''){
						try{
							$('#' + arrayNamesTablesVinc[i]).datagrid('load',jsonDataFormEdit);
						}catch(e){alert(arrayNamesTablesVinc[i] + ':' + e.message);}
						limpo = false;
					}
				}
				try{
					$('#body').layout('expand', 'south');
				}catch(e){}
			}
			try{
				$('#body').layout();
			}catch(e){}
		}catch(e0){}
		callExternalClasses();
	}
	function limpaVinculacoes(){
		try{
			$( '#tabsCompl' ).tabs('select', 0);
		}catch(e){}
		if (arrayNamesTablesVinc != null){
			for(var i = 0; i < arrayNamesTablesVinc.length; i++){
				if (arrayNamesTablesVinc[i] != ''){
					$('#' + arrayNamesTablesVinc[i]).datagrid('load',{});
				}
				arrayControleRenderVinc[i] = false;
			}
			arrayControleRenderVinc[0] = true;
			arrayControleRenderVinc[1] = true;
		}
	}
	function limpar(){
		var idVisaoTmp = document.form.dinamicViewsIdVisao.value;
		var idFluxo = null;
		var idTarefa = null;
		var acaoFluxo = null;
		try{
			idFluxo = document.form.idFluxo.value;
			idTarefa = document.form.idTarefa.value;
			acaoFluxo = document.form.acaoFluxo.value;
		}catch(e){}
		var modoExibicao = document.form.modoExibicao.value;
		document.form.clear();
		$('#form').form('clear');
		//Faz o tratamento de campos especificos que possuem metodo especifico de limpeza.
		try{
			for(var i = 0; i < document.form.length; i++){
				var elem = document.form.elements[i];
				if (elem.name == null) continue;
				try{
					eval(elem.name + '_limparField()');
				}catch(e){}
			}
		}catch(e){}
		document.form.dinamicViewsIdVisao.value = idVisaoTmp;
		document.form.idFluxo.value = idFluxo;
		document.form.idTarefa.value = idTarefa;
		document.form.acaoFluxo.value = acaoFluxo;
		document.form.modoExibicao.value = modoExibicao;
		limpo = true;
		limpaVinculacoes();
	}

	function callExternalClasses(){
		for(var i = 1; i < tabsInf.length; i++){
			if (tabsInf[i] != ''){
				enviaDados(tabsInf[i], 'tabInfs-' + i, document.form);
			}
		}
	}

	$(document).ready(function () {
		load_scripts();
		try{
			$('#tabsCompl').tabs({
				onSelect: function(title,index){
					if (arrayControleRenderVinc != null){
						if (!arrayControleRenderVinc[index + 1]){
							var jsonDataFormEdit = $('#' + document.form.name).formParams(false);
							try{
								$('#' + arrayNamesTablesVinc[index + 1]).datagrid('load',{});
							}catch(e){
							}
							if (!limpo){
								try{
									$('#' + arrayNamesTablesVinc[index + 1]).datagrid('load',jsonDataFormEdit);
								}catch(e){
								}
							}
							arrayControleRenderVinc[index + 1] = true;
						}
					}
				}
			});
		}catch(e){}
		callExternalClasses();
	});
</script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title='${waitingWindowMessage}' style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;" />

<body id='body' class="easyui-layout">
	<% if (!modoExibicao.equals("J")) { %>
	<div data-options="region:'north',split:false" style="height: 125px;" class="dinamic-menu">
		<%@include file="/include/menu_horizontal.jsp"%>
	</div>
	<% } %>
	<div data-options="region:'south',split:true" title="<%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.informacoescomplementares"))%>" style="height: 200px; padding: 10px; background: #efefef;">
	<%
		int i = 0;
		if (visaoDto.getColVisoesRelacionadas() != null && visaoDto.getColVisoesRelacionadas().size() > 0) {
			i = 1;
			for (Iterator it = visaoDto.getColVisoesRelacionadas().iterator(); it.hasNext();) {
				VisaoRelacionadaDTO visaoRelacionadaDto = (VisaoRelacionadaDTO) it.next();
				if (visaoRelacionadaDto.getTipoVinculacao() == null) {
					visaoRelacionadaDto.setTipoVinculacao("");
				}
				if (visaoRelacionadaDto.getTipoVinculacao().equalsIgnoreCase(VisaoRelacionadaDTO.VINC_ABA_FILHA)) {
	%>
		<script>tabsInf[<%=i%>] = '';</script>
	<%
		if (i == 1) {
	%>
		<div id="tabsCompl" class="easyui-tabs">
	<%
		}
	%>
	<%
		String tituloAba = visaoRelacionadaDto.getTitulo();
		tituloAba = UtilStrings.nullToVazio(tituloAba).trim();
		if (tituloAba.startsWith("$")){
			tituloAba = UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, tituloAba));
		}
	%>
			<div id="tabInfs-<%=i%>" data-options="tools:'#p-tools'" title="<%=tituloAba%>">
			<%
				temAbaFilha = true;
				if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.EDIT)) {
					if (visaoRelacionadaDto.getAcaoEmSelecaoPesquisa().equalsIgnoreCase(VisaoRelacionadaDTO.ACAO_RECUPERAR_REGISTROS_VINCULADOS)) {
						MetaUtil.renderViewTableEditVinc_Easy(visaoRelacionadaDto.getVisaoFilhaDto(), out, visaoRelacionadaDto, request, response);

						if (!strArrayNamesTablesVinc.equalsIgnoreCase("")) {
							strArrayNamesTablesVinc += ",";
							strArrayTablesVincMatriz += ",";
							strArrayControleRenderVinc += ",";
						}
						strArrayNamesTablesVinc += "\"" + VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoRelacionadaDto.getVisaoFilhaDto().getIdVisao() + "\"";
						strArrayTablesVincMatriz += "false";
						strArrayControleRenderVinc += "false";
					} else {
						MetaUtil.renderViewEdit(visaoRelacionadaDto.getVisaoFilhaDto(), out, request, response);
					}
				} else if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.EXTERNALCLASS)) {
					String url = MetaUtil.renderExternaClass(visaoRelacionadaDto.getVisaoFilhaDto(), out);
					out.print("<script>tabsInf[" + i + "] = '" + url + "';</script>");
				} else if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
					MetaUtil.renderViewMatriz(visaoRelacionadaDto.getVisaoFilhaDto(), out, request, response, true);
					if (!strArrayNamesTablesVinc.equalsIgnoreCase("")) {
						strArrayNamesTablesVinc += ",";
						strArrayTablesVincMatriz += ",";
						strArrayControleRenderVinc += ",";
					}
					strArrayNamesTablesVinc += "\"" + VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoRelacionadaDto.getVisaoFilhaDto().getIdVisao() + "\"";
					strArrayTablesVincMatriz += "true";
					strArrayControleRenderVinc += "false";
				} else {
					MetaUtil.renderViewTableSearch(visaoRelacionadaDto.getVisaoFilhaDto(), out, request);
				}
			%>
			</div>
		<%
					i++;
					}
				}
			}
			if (!temAbaFilha) {
				scriptToLOad += "$('#body').layout('remove', 'south');\n";
			}
			if (i > 0) {
		%>
		</div>
		<%
			}
		%>
	</div>
	<div data-options="region:'east',iconCls:'icon-reload',split:true"
		title="<%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.botoesacao"))%>" style="width: 180px;">
		<%
			String htmlCodeInitButtons = (String) visaoDto.getMapHtmlCodes().get(HtmlCodeVisaoDTO.HTMLCODE_INIT_BUTTONS.getName());
			if (htmlCodeInitButtons != null) {
				out.println(UtilI18N.internacionalizaString(htmlCodeInitButtons, request));
			}

			if (colBotoes == null) {
				colBotoes = new ArrayList();
			}

			for (Iterator it = colBotoes.iterator(); it.hasNext();) {
				BotaoAcaoVisaoDTO botaoAcaoVisaoDTO = (BotaoAcaoVisaoDTO) it.next();
				if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_SCRIPT)) {
					out.println("<script>");
					out.println("function script_button_" + botaoAcaoVisaoDTO.getIdBotaoAcaoVisao() + "(){");
					out.println(botaoAcaoVisaoDTO.getScript());
					out.println("}");
					out.println("</script>");
				}
				if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_GRAVAR)) {
		%>
		<button type='button' name='btnGravar' id='btnGravar' class="light" onclick='salvar();'>
			<span><%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.gravardados"))%></span>
		</button>
		<%
			}
			if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_EXCLUIR)) {
		%>
		<button type='button' id='btnExcluir' name='btnExcluir' class="light" onclick='excluir();'>
			<span><%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.excluirdados"))%></span>
		</button>
		<%
			}
			if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_LIMPAR)) {
		%>
		<button type='button' id='btnLimpar' name='btnLimpar' class="light" onclick='limpar();'>
			<span><%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.limpardados"))%></span>
		</button>
		<%
			}
			if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_SCRIPT)) {
		%>
		<button type='button' name='btnScript<%=botaoAcaoVisaoDTO.getIdBotaoAcaoVisao()%>' class="light" onclick='<%="script_button_" + botaoAcaoVisaoDTO.getIdBotaoAcaoVisao() + "();"%>'>
			<span><%=UtilI18N.internacionalizaString(botaoAcaoVisaoDTO.getTexto(), request)%></span>
		</button>
		<%
			}
		%>
		<br>
		<%
			}
		%>
		<%
			if (modoExibicao.equals("J")) {
		%>
		<button type='button' name='btnCancelar' id="btnCancelar" class="light" onclick='cancelar();'>
			<img src="${ctx}/template_new/images/icons/small/grey/bended_arrow_left.png">
			<span><%=UtilI18N.internacionaliza(request, "dinamicview.cancelar")%></span>
		</button>
		<%
			}
		%>

		<%
			String htmlCodeFinalButtons = (String) visaoDto.getMapHtmlCodes().get(HtmlCodeVisaoDTO.HTMLCODE_END_BUTTONS.getName());
			if (htmlCodeFinalButtons != null) {
				out.println(UtilI18N.internacionalizaString(htmlCodeFinalButtons, request));
			}
		%>
	</div>

	<div data-options="region:'center'" title="<%=descrvisao%>">
		<%
			String htmlCodeInit = (String) visaoDto.getMapHtmlCodes().get(HtmlCodeVisaoDTO.HTMLCODE_INIT.getName());
			if (htmlCodeInit != null) {
				out.println(UtilI18N.internacionalizaString(htmlCodeInit, request));
			}
		%>
		<!-- Conteudo do Centro -->
		<div id="tabs" class="easyui-tabs" data-options="tools:'#tab-tools'">
			<div id="tabs-1" data-options="tools:'#p-tools'" style="width:100% !important;" title="<%=descrvisao%>">
				<!-- INICIO DO FORM PRINCIPAL -->
				<form name='form' id='form' onsubmit='javascript:return false;' action='${ctx}/pages/dinamicViews/dinamicViews'>
					<input type='hidden' name='dinamicViewsIdVisao' value='<%=idVisao%>' />
					<input type='hidden' name='dinamicViewsIdVisaoPesquisaSelecionada' value='' />
					<input type='hidden' name='dinamicViewsAcaoPesquisaSelecionada' value='' />
					<input type='hidden' name='dinamicViewsJson_data' value='' />
					<input type='hidden' name='dinamicViewsJson_tempData' value='' />
					<input type='hidden' name='keyControl' value='' />
					<input type='hidden' name='dinamicViewsTablesVinc' value='' />
					<input type='hidden' name='dinamicViewsDadosAdicionais' value='' />
					<input type='hidden' name='modoExibicao' value='<%=modoExibicao%>' />
					<input type='hidden' name='idFluxo' value='<%=idFluxo%>' />
					<input type='hidden' name='idTarefa' value='<%=idTarefa%>' />
					<input type='hidden' name='acaoFluxo' value='<%=acaoFluxo%>' />
					<input type='hidden' name='JsonData' value='TRUE' />
					<input type='hidden' name='jsonMatriz' value='' />
					<%
						String htmlCodeInitForm = (String) visaoDto.getMapHtmlCodes().get(HtmlCodeVisaoDTO.HTMLCODE_INIT_FORM.getName());
						if (htmlCodeInitForm != null) {
							out.println(UtilI18N.internacionalizaString(htmlCodeInitForm, request));
						}
					%>
					<div id='divMainContent' class="columns clearfix">
						<%
							if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.EDIT)) {
								MetaUtil.renderViewEdit(visaoDto, out, request, response);
							} else if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.EXTERNALCLASS)) {
								String url = MetaUtil.renderExternaClass(visaoDto, out);
								scriptsIniciais = "enviaDados('" + url + "', 'divMainContent', document.form);";
							} else if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
								MetaUtil.renderViewMatriz(visaoDto, out, request, response, false);
							} else {
								MetaUtil.renderViewTableSearch(visaoDto, out, request);
							}
						%>
					</div>
					<%
						String htmlCodeFinalForm = (String) visaoDto.getMapHtmlCodes().get(HtmlCodeVisaoDTO.HTMLCODE_END_FORM.getName());
						if (htmlCodeFinalForm != null) {
							out.println(UtilI18N.internacionalizaString(htmlCodeFinalForm, request));
						}
					%>
					<br>
					<br>
				</form>
				<!-- FIM DO FORM PRINCIPAL -->
			</div>
			<%
				if (visaoDto.getColVisoesRelacionadas() != null) {
					i = 2;
					for (Iterator it = visaoDto.getColVisoesRelacionadas().iterator(); it.hasNext();) {
						VisaoRelacionadaDTO visaoRelacionadaDto = (VisaoRelacionadaDTO) it.next();
						if (visaoRelacionadaDto.getTipoVinculacao() == null) {
							visaoRelacionadaDto.setTipoVinculacao("");
						}
						if (visaoRelacionadaDto.getTipoVinculacao().equalsIgnoreCase(VisaoRelacionadaDTO.VINC_ABA_AO_LADO)) {
			%>
			<div id="tabs-<%=i%>" data-options="tools:'#p-tools'" title="<%=UtilI18N.internacionalizaString(visaoRelacionadaDto.getTitulo(),request)%>">
				<div id="tabsLado-<%=i%>">
			<%
							if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.EDIT)) {
								MetaUtil.renderViewEdit(visaoRelacionadaDto.getVisaoFilhaDto(), out, request, response);
							} else if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.EXTERNALCLASS)) {
								String url = MetaUtil.renderExternaClass(visaoRelacionadaDto.getVisaoFilhaDto(), out);
								out.print("<script>tabsVincLado[" + i + "] = '"	+ url + "';</script>");
							} else if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
								MetaUtil.renderViewMatriz(visaoRelacionadaDto.getVisaoFilhaDto(), out, request, response, false);
							} else {
								MetaUtil.renderViewTableSearch(visaoRelacionadaDto.getVisaoFilhaDto(), out, request);
							}
							i++;
			%>
				</div>
			</div>
			<%
						}
					}
				}
			%>
			<!-- ## FIM - AREA DA APLICACAO ## -->
		</div>

		<!-- Fim Conteudo do Centro -->

		<%
			String htmlCodeFinal = (String) visaoDto.getMapHtmlCodes().get(HtmlCodeVisaoDTO.HTMLCODE_END.getName());
			if (htmlCodeFinal != null) {
				out.println(UtilI18N.internacionalizaString(htmlCodeFinal, request));
			}
		%>
	</div>

	<script type="text/javascript">
		<% if (strArrayNamesTablesVinc != null && !strArrayNamesTablesVinc.equalsIgnoreCase("")) { %>
		arrayNamesTablesVinc = new Array('', <%=strArrayNamesTablesVinc%>);
		arrayTablesVincMatriz = new Array(false, <%=strArrayTablesVincMatriz%>);
		arrayControleRenderVinc = new Array(false, <%=strArrayControleRenderVinc%>);
		<% } %>
		document.form.dinamicViewsTablesVinc.value = '<%=strArrayNamesTablesVinc%>';

		function load_scripts(){
			try{
			<%= scriptsIniciais %>
			<% String strScript = (String) visaoDto.getMapScripts().get(
			ScriptsVisaoDTO.SCRIPT_EXECUTE_CLIENT + "#" + ScriptsVisaoDTO.SCRIPT_LOAD.getName());
			if (strScript != null) {
				//NAO RETIRE ISTO!!!!
				//remove coisas velhas da dinamic view anterior que estava no fonte do BD. Mantem compatibilidade.
				strScript = strScript.replaceAll("layout\\.sizePane\\(\\\"west\\\"\\, 5\\);", "");
				strScript = strScript.replaceAll("layout\\.close\\(\\\"west\\\"\\);", "");
				strScript = strScript.replaceAll("layout\\.toggle\\(\\\"south\\\"\\);", "");
				out.println(UtilI18N.internacionalizaString(strScript, request));
			}%>
			}catch(e){}
			<%=scriptToLOad%>
		}

		function restore_scripts(){
			<% strScript = (String) visaoDto.getMapScripts().get(ScriptsVisaoDTO.SCRIPT_EXECUTE_CLIENT + "#" + ScriptsVisaoDTO.SCRIPT_ONRESTORE.getName());
				if (strScript != null) {
					out.println(UtilI18N.internacionalizaString(strScript, request));
				}
			%>
		}

		function valid_scripts(){
			<%strScript = (String) visaoDto.getMapScripts().get(ScriptsVisaoDTO.SCRIPT_EXECUTE_CLIENT + "#"+ ScriptsVisaoDTO.SCRIPT_VALIDADE.getName());
				if (strScript != null && !strScript.trim().equalsIgnoreCase("")) {
					out.println(UtilI18N.internacionalizaString(strScript, request));
				}else{
					out.println("return true;");
				}
			%>
		}

		addEvent(window, "load", load, false);
		function load(){
			document.form.afterLoad = function () {
				if ('<%=idFluxo%>' != '' && '<%=idTarefa%>' != '') {
					document.form.fireEvent("recuperaVisaoFluxo");
				}
			}
		}
	</script>

	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="${ctx}/js/locale/easyui-lang-pt_BR.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
	<script type="text/javascript" src="${ctx}/dwr/util.js"></script>

	<script type="text/javascript" src="${ctx}/pages/dinamicViews/js/dinamicViews.js"></script>

	<%@include file="/pages/ctrlAsterisk/ctrlAsterisk.jsp" %>
</body>

</html>