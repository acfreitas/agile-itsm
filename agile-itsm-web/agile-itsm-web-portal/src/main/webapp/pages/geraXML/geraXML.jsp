<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citgerencial.bean.GerencialPainelDTO"%>
<%
	String fileNameGrupo = request.getParameter("fileNameGrupo");
	if (fileNameGrupo == null) fileNameGrupo = "";

	String groupName = request.getParameter("groupName");
	if (groupName == null) groupName = "";

	String showOptions = request.getParameter("showOptions");
	if (showOptions == null) showOptions = "";
	if (showOptions.equalsIgnoreCase("")) showOptions = "true";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
		<title>CITSMart</title>
	 <%@include file="/include/header.jsp"%>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>


	<style>
		#geral #body {
			height: 640px;
		}
		body {
			font-size: 12px !important;
		}
		#tabs {
			height: 600px;
		}
		#geral{
			height:820px;
		}
		#labelGrupoInf{
		color: black;
		margin-left: 18px
		}
		#divListan
		{
			height: 600px;
			width: 200px;
			border: 2px solid black;
			border-color: #CBCDCE;
			overflow: auto;
			-moz-border-radius: 4px; /* firefox */
			-webkit-border-radius: 4px; /* chrome */
		}
		#divPainel{
		height: 600px;
		/*width: 1500px;
		overflow: auto;*/
		}
		#btnFecharOptGrf{
		margin-left: 3px;
		}
		#btnOKParms{
		margin-left: 3px;
		}
		.ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only{
		text-align: left;
		}
		.linhaSubtituloGrid
		{

		    font-size		:12px;
		    color			:#000000;
		    font-family		:Arial;
		    background-color: #d3d3d3;
		    BORDER-RIGHT: thin outset;
		    BORDER-TOP: thin outset;
		    BORDER-LEFT: thin outset;
		    BORDER-BOTTOM: thin outset;

		}
		.linhaSubtitulo
		{

		    font-size		:12px;
		    color			:#000000;
		    font-family		:Arial;
		    background-color: #008b8b;

		}
		.linhaSubtitulo2
		{

		    font-size		:12px;
		    color			:#000000;
		    font-family		:Arial;
		    background-color: #99AFDC;
		    width: 101px;
		    text-align: center;

		}
		TD.celulaGrid
		{
			FONT-SIZE: 10px;
		    BORDER-RIGHT: 1px solid;
		    PADDING-RIGHT: 0px;
		    BORDER-TOP: 1px solid;
		    PADDING-LEFT: 0px;
		    PADDING-BOTTOM: 0px;
		    MARGIN: 0px;
		    BORDER-LEFT: 1px solid;
		    PADDING-TOP: 0px;
		    BORDER-BOTTOM: 1px solid
		}
		.throbber {
			background: url('/citsmart/imagens/ajax-loader.gif');
			display: inline-block;
			height: 16px;
			width: 16px;
		}
		#Throbber {
			margin: 4px 10px;
			vertical-align: middle;
			visibility: hidden;
		}
		#painelcontrole
		{
			width: 100%;
			position: relative;
		}
		#parametros
		{
			position: absolute;
			top: 0;
			right: 10px;
			cursor: pointer;
		}
		#parametros h2
		{
			padding: 10px 20px 10px 0px;
		}
		#parametrosIcon
		{
			top: 18px;
			right: 10px;
			position: absolute;
			height: 10px;
			width: 10px;
			background:url("/citsmart/imagens/icon-n.gif") no-repeat;
		}
		.w100p{width: 100%;}

		.w200 {
		    background-color: #F9F9F9;
		    min-width: 194px;
		    vertical-align: top;
		    width: 200px;
		     box-shadow: 3px 0px 3px 0px rgba(0, 0, 0, 0.2);
		}
		.ug {
		    padding: 10px 0 10px 0;
		}
/* Desenvolvedor: Pedro Lino - Data: 31/10/2013 - Horário: 10:00 - ID Citsmart: 120948 -
* Motivo/Comentário: Alterado css para esconder a td do meio */
		.aq {
		    /* background: none repeat scroll 0 0 #F5F5F5;
		    border-left: 1px solid #C8C8C8;
		    border-right: 1px solid #DDDDDD;
		    box-shadow: -1px 0 3px rgba(0, 0, 0, 0.2);
		    height: 100%;
		    width: 5px; */
		}
		.wj {
		    background: none repeat scroll 0 0 #FFFFFF;
		    clear: both;
		    margin: 0;
		    padding: 0;
		    width: 100%;
		}
		.pg {
		    background-color: #EEEEEE;
		    display: block;
		    height: 40px;
		    margin: 0;
		    padding: 0;
		}
		.He {
		    float: right;
		    font-size: 12px;
		    font-weight: normal;
		    margin: 14px 20px 0 0;
		    white-space: nowrap;
		}
		.lFloat { float: left;}
		.rFloat { float: right;}
		.clear { clear: both;}
		.n { padding: 3px;}

		.He a, .He a:visited {
		    color: #1155CC;
		}
		.He a, .He span {
		    margin-left: 15px;
		}
		.Ki {
		    text-decoration: none;
		}
		a, a:visited, .e {
		    color: #005C9C;
		}
		.XF div {
		    background: url("") no-repeat scroll 0 0 transparent;
		    height: 44px;
		    margin: 0 0 0 20px;
		    width: 213px;
		}
		 a.sJcFJd {
		  	border-left: 4px solid #FFFFFF;
		    color: #000;
		    display: block;
		    font-size: 12px;
		    line-height: 18px;
		    outline: medium none;
		    padding: 5px 0 6px 10px;
		    width: 180px;
		}
		a.sJcFJd.Co6tNc-purZT {
		    border-left-width: 2px;
		}
		a.sJcFJd.tASNx {
		    border-left-color: #464E5A;
		    color: #464E5A;
		}
		.sJcFJd:hover {
		    background-color: #EEEEEE;
		    border-left-color: #EEEEEE;
		}
		a.sJcFJd:focus {
		    text-decoration: underline;
		}
		a.sJcFJd:active, a.sJcFJd:hover {
		    text-decoration: none;
		}
		a {
		    color: #1155CC;
		    text-decoration: none;
		}
		.tf {
		    border-right: 1px solid #FFFFFF;
		    padding-right: 10px;
	     	height: 40px;
	     	width: 197px;
		}
		.tf h1 {
			padding: 6px 0 6px 10px;
		}
		#miniPainel
		{
			display: block;
			float: left;
			position: relative;
			border: 1px solid #CCCCCC;
			margin: 2px;
		}
		.interna
		{
			/*padding: 5px 10px;*/
			width: 1000px;
		}
		.internaTools
		{
			top: 0px;
			right: 0px;
			position: absolute;
		}
		.internaTitle
		{
			width: 100%;
			height: 30px;
			border-bottom: 1px solid #CCCCCC;;
			background: #F7F7F7;
		}
		.internaTitle h2
		{
			padding: 2px 0 0 10px;
			font-size: 16px;
		}
		td {
			vertical-align: top!important;
		}
		.form-horizontal .control-group {
			margin-bottom: -24px!important;
		}
		#fileNameGrupo{
			border: 1px solid #B0B0B0!important;
		}
	</style>

	<!-- Live Load (remove after development) -->
	<!-- 		<script>document.write('<script src="http://' + (location.host || 'localhost').split(':')[0] + ':35729/livereload.jssnipver=1"></' + 'script>')</script> -->


	<script type="text/javascript" src="${ctx}/js/javaScriptComuns.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.printElement.js"></script>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/fonts.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/text.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/fonts.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css"/>
	<link rel="stylesheet" type="text/css"href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>


	<script type="text/javascript" src="${ctx}/pages/painel/jquery-1.8.0.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.datepicker.js"></script>
	<script src="/citsmart/js/jquery.ui.datepicker-pt-BR.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.position.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/pages/portal/css/jquery-ui-1.8.21.custom.css" />

	<script type="text/javascript">

		var posPainel;

		function atualizaPainel(fileName, objTd)
		{
			if(fileName != null)
				document.formPainel.fireEvent('changeCombo');
			//emiteAguarde();
			if (fileName != null){
				document.formPainel.fileName.value = fileName;
				document.formPainel.fileNameItem.value = '';
				document.formPainel.parametersPreenchidos.value = 'false';
			}
			document.formPainel.fireEvent('geraPainel');

			setTimeout(function()
				{
					if (objTd != null){
 						var	tbl = document.getElementById(objTd);
						tbl.className += ' tASNx';
					}
				},200);
		}

		function emiteAguarde(){
			posPainel = HTMLUtils.getPosElement('divPainel');

			document.getElementById('divAguarde').style.top = posPainel.y + 'px';
			document.getElementById('divAguarde').style.left = posPainel.x + 'px';
			document.getElementById('divAguarde').style.width = document.getElementById('divPainel').style.width;
			document.getElementById('divAguarde').style.height = document.getElementById('divPainel').style.height;

			document.getElementById('divAguarde').style.display = 'block';
		}

		function hideAguarde(){
			document.getElementById('divAguarde').style.display = 'none';
		}

		function getFileExcel(pathFileExcel, nomeCurtoFileExcel){
			document.formGetExcel.fileNameExcel.value = pathFileExcel;
			document.formGetExcel.fileNameExcelShort.value = nomeCurtoFileExcel;

			document.formGetExcel.action='<%=Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/painel/painel';

			document.formGetExcel.parmCount.value = '3';
			document.formGetExcel.parm1.value = DEFINEALLPAGES_getFacadeName(document.formGetExcel.action);
			document.formGetExcel.parm2.value = '';
			document.formGetExcel.parm3.value = 'getFileExcel';

			document.formGetExcel.setAttribute("target",'frameGetExcel');

			document.formGetExcel.action='<%=Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/painel/painel.event';
			document.formGetExcel.submit();
		}

		function geraPDF(fileNameGerar, tipoSaidaApresentada){
			JANELA_AGUARDE_MENU.show();
			document.formPainel.fileNameItem.value = fileNameGerar;
			document.formPainel.tipoSaidaApresentada.value = tipoSaidaApresentada;
			document.formPainel.fireEvent('geraPDF');
		}

		function geraExcel(fileNameGerar, tipoSaidaApresentada){
			JANELA_AGUARDE_MENU.show();
			document.formPainel.fileNameItem.value = fileNameGerar;
			document.formPainel.tipoSaidaApresentada.value = tipoSaidaApresentada;
			document.formPainel.fireEvent('geraExcel');
		}

		function submitGrupo(){
			if (document.formPainel.fileNameGrupo.value == '')
			{
				document.getElementById('divPainel').innerHTML = '';
				document.getElementById('divLista').innerHTML = '';
				document.getElementById('parametros').innerHTML = '';
			}
			else{
				document.formPainel.fireEvent('changeCombo');
				//document.formPainel.action = '<%=Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/painel/painel.load';
				//document.formPainel.submit();
			}
		}

		var ajaxObj = new AjaxAction();
		function submitJSP(pathJSP){
			ajaxObj = new AjaxAction();
			copiaParametros();
			ajaxObj.submitForm(document.formPainel,pathJSP,retornoSubmitJSP);
		}
		function retornoSubmitJSP(){
			if (ajaxObj.req.readyState == 4){
				if (ajaxObj.req.status == 200){
					document.getElementById('interna0').innerHTML = ajaxObj.req.responseText;
					document.getElementById('divAguarde').style.display = 'none';
				}
			}
		}

		function gerarPainel(){

			var dataInicio = document.getElementById("PARAM.dataInicial").value;
			var dataFim = document.getElementById("PARAM.dataFinal").value;

			if(validaData(dataInicio, dataFim)){
				if (document.formParametros.validate())
				{
					JANELA_AGUARDE_MENU.show();
					document.getElementById('Throbber').style.visibility ='visible';
					//Coloca os atributos digitados apenas para fazer um fireEvent nos dados para o Servidor.
					document.getElementById('divInfTransfPainel').innerHTML = document.getElementById('divParametros').innerHTML;
					copiaParametros();
					setTimeout(function(){$("#POPUP_PARAM").dialog('close');document.getElementById('Throbber').style.visibility ='hidden';},2000);
					document.formPainel.parametersPreenchidos.value = 'true';
					atualizaPainel(null, null);
				}

			}


		}

		/**
		* @author rodrigo.oliveira
		*/
		function validaData(dataInicio, dataFim) {

			var dtInicio = new Date();
			var dtFim = new Date();

			dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
			dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;

			if (dtInicio > dtFim){
				alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
				return false;
			}else
				return true;
		}

		function copiaParametros(){
			for(var i = 0; i < document.formParametros.length - 1; i++){
				var elem = document.formParametros[i];
				if (elem == null || elem == undefined){
					continue;
				}
				if (elem.name == undefined || elem.name == ''){
					continue;
				}
				try{
					//alert('document.formPainel["' + elem.name + '"].value = "' + elem.value + '"');
					eval('document.formPainel["' + elem.name + '"].value = "' + elem.value + '"');
				}catch(e){
				}
			}
		}

		function setaTipoSaida(fileName, tipoSaida){
			//emiteAguarde();
			JANELA_AGUARDE_MENU.show();
			document.getElementById('Throbber_2').style.visibility ='visible';

			document.formPainel.fileNameItem.value = fileName;
			document.formPainel.parametersPreenchidos.value = 'true';
			document.formPainel.tipoSaida.value = tipoSaida;

			document.getElementById('divInfTransfPainel').innerHTML = document.getElementById('divParametros').innerHTML;
			copiaParametros();
			document.formPainel.fireEvent('atualizaTipoSaida');
		}

		function geraSaidaImpHTML(fileName, tipoSaida){
			$('#interna0').printElement({
				printMode : 'popup',
				leaveOpen : true,
				 pageTitle : i18n_message("citcorpore.comum.agenda") ,
				iframeElementOptions:{classNameToAdd : 'ui-corner-all resBusca'},
				 printBodyOptions : {
		            styleToAdd : 'position:absolute;width:100%;height:100%; top: 1px;bottom:0px;padding: 1px; margin: 1px;  ' ,
		            classNameToAdd  :  '' },
		            overrideElementCSS: null//['../../css/pesquisaLig.css']
				});
		}

		function fecharTelaParm(){
			$("#POPUP_PARAM").dialog('close');
			document.getElementById('Throbber').style.visibility ='hidden';
		}

		function setaTipoGrafico(fileName, tipoSaida, graficosTipos, titulo){
			document.getElementById('Throbber_2').style.visibility ='hidden';

			var tabela = '<table width="100%">';
			tabela += '<tr>';
				tabela += '<td>';
					tabela += '<b><fmt:message key="painel.selecioneGraficoAbaixo" />:</b>';
				tabela += '</td>';
			tabela += '</tr>';
			if (graficosTipos != null && graficosTipos != undefined){
				var tipos = graficosTipos.split(';');
				if (tipos.length){
					for(var i = 0; i < tipos.length; i++){
						tabela += '<tr onmouseover="HTMLUtil_TrowOn(this,HTMLUtil_colorOn)" onmouseout="HTMLUtil_TrowOn(this,HTMLUtil_colorOff)">';
							tabela += '<td style=\"cursor:pointer\" onclick=\'setaTipoSaida("' + fileName + '", "G:' + tipos[i] + '")\'>';
								tabela += tipos[i];
							tabela += '</td>';
						tabela += '</tr>';
					}
				}
			}
			tabela += '</table>';
			document.getElementById('divOpcoesGraficos').innerHTML = tabela;

			$("#POPUP_GRAFICO_OPC").attr('title','Gráfico: ' + titulo);

			$("#POPUP_GRAFICO_OPC").dialog('open');
		}

		function recarrega(obj){
			document.getElementById('divInfTransfPainel').innerHTML = document.getElementById('divParametros').innerHTML;
			copiaParametros();
			document.formPainel.parametersPreenchidos.value = 'false';

			for(var i = 0; i < document.formParametros.length - 1; i++){
				var elem = document.formParametros[i];
				if (elem == null || elem == undefined){
					continue;
				}
				if (elem.name == undefined || elem.name == ''){
					continue;
				}
				if (elem.name == obj.name){
					i++;
					if (i < document.formParametros.length - 1){
						var elem = document.formParametros[i];
						document.formPainel.campo.value = elem.name;
						break;
					}
				}
			}
			document.formPainel.fireEvent('reloadParameters');
		}

		function voltar(){
			window.location = '${ctx}/pages/index/index.load';
		}

		$(function() {
			$("#POPUP_PARAM").dialog( {
				autoOpen : false,
				width : 800,
				height : 650,
				modal : true
			});

		});

		$(function() {
			$("#POPUP_GRAFICO_OPC").dialog( {
				autoOpen : false,
				width : 400,
				height : 280,
				modal : true
			});
		});

		$(function()
		{
			 $('#parametros').click(function()
			 {
				 $('#POPUP_PARAM').dialog('open');
			 });

		});
		function pageLoad()
		{
			$(function()
			{
				$('input.datepicker').datepicker();
			});
		}


	</script>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body id="bodyNode" style="background-color: white;">

	<form name='formPainel' class="form-horizontal" style="margin: 0" method="post" action='<%=Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/painel/painel'>
		<input type='hidden' name='fileName'/>
		<input type='hidden' name='fileNameItem'/>
		<input type='hidden' name='tipoSaida'/>
		<input type='hidden' name='tipoSaidaApresentada'/>
		<input type='hidden' name='campo'/>
		<input type='hidden' name='parametersPreenchidos' id='parametersPreenchidos' value='false'/>
		<div id='divInfTransfPainel' style='display:none'></div>

		<table width="100%">
			<tr>
				<td valign="top" class="aq"></td>
				<td valign="top" class="w100p">
					<div id="painelcontrole">
						<h1></h1>
						<div id="parametros" title="<fmt:message key="painel.cliqueParaMudarParametros" />"></div>
						<div id="parametrosIcon"></div>
					</div>

					<div id='divPainel'></div>
				</td>
			</tr>
		</table>
	</form>


	<div id='divAguarde' style='position: absolute; display:none; CURSOR: wait; BACKGROUND-COLOR:gray; filter:alpha(opacity=20);-moz-opacity:.25;opacity:.25;'>
		<table width="100%" >
			<tr>
				<td>
					<font  size='15'><b><fmt:message key="citcorpore.comum.aguardeCarregando" /></b></font>
				</td>
			</tr>
		</table>
	</div>

		<form name='formGetExcel'>
			<input type='hidden' name='fileNameExcel'/>
			<input type='hidden' name='fileNameExcelShort'/>

		 	<input type='hidden' name='parmCount'/>
		 	<input type='hidden' name='parm1'/>
		 	<input type='hidden' name='parm2'/>
		 	<input type='hidden' name='parm3'/>
		</form>

	<div id='divGenerateExcel' style='display:none'>
		<iframe src='about:blank' name='frameGetExcel' id='frameGetExcel'></iframe>
	</div>

<div  style="background:white" id="POPUP_PARAM">
	<form name="formParametros" id="formParametros">
	<div id='divParametros' style='width: 98%; overflow: auto; padding: 7px;'></div>
		<table>
			<tr>
				<td align="center" valign="middle">
					<div id="Throbber" class="throbber"></div>
				</td>
				<td>

					<button type='button' name='btnOKParms' class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only light" onclick='gerarPainel();' style="margin: 20px !important;">
						<%-- <img src="${ctx}/template_new/images/icons/small/grey/chart_8.png"/> --%>
						<span><fmt:message key="citcorpore.comum.gerar" /></span>
					</button>

					<button type='button' name='btnFecharParms' class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only light" onclick='fecharTelaParm();' style="margin: 20px !important;">
						<%-- <img src="${ctx}/template_new/images/icons/small/grey/clear.png"/> --%>
						<span><fmt:message key="citcorpore.comum.fechar" /></span>
					</button>

					<!-- <input type='button' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only' id='btnOKParms' name='btnOKParms' value='Gerar' onclick='gerarPainel()'/>
					<input type="reset" name='btnFecharParms' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only' value='Limpar'/> -->
				</td>
			</tr>
		</table>
	</form>
</div>

<div style="background:white" title="Opções de Gráficos" id="POPUP_GRAFICO_OPC">
	<table>
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<div id='divOpcoesGraficos' style='height: 150px; background-color: white; width:348px;'>

				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div style='background-color: white;text-align:right;'>

					<div id="Throbber_2" class="throbber"></div>

					<input type='button' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'
					id='btnFecharOptGrf' name='btnFecharOptGrf' value='Fechar' onclick="$('#POPUP_GRAFICO_OPC').dialog('close');"/>
				</div>
			</td>
		</tr>
	</table>
</div>

</body>


</html>