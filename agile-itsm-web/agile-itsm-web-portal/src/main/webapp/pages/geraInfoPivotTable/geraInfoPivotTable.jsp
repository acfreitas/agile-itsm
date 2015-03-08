<%
String json = (String)request.getAttribute("json");
if (json != null && json.equalsIgnoreCase("S")){
	String textJSON = (String)request.getAttribute("textJSON");
	out.print(textJSON);
	return;
}
	String idConsulta = request.getParameter("idConsulta");
	if (idConsulta == null){
		idConsulta = "";
	}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!DOCTYPE html>
<html>
<head>
<script>
var intervalo;
</script>
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
		    min-width: 200px;
		    vertical-align: top;
		    width: 200px;
		}
		.ug {
		    padding: 10px 0 10px 0;
		}
		.aq {
		    background: none repeat scroll 0 0 #F5F5F5;
		    border-left: 1px solid #C8C8C8;
		    border-right: 1px solid #DDDDDD;
		    box-shadow: -1px 0 3px rgba(0, 0, 0, 0.2);
		    height: 100%;
		    width: 5px;
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
		#divImpressao{
			padding: 8px!important;

		}
		.table th, .table td {
			padding: 8px;
			line-height: 20px;
			text-align: left;
			vertical-align: top!important;
			border-top: 1px solid #ddd;
	}

	</style>

        <link rel="stylesheet" type="text/css" href="${ctx}/pages/geraInfoPivotTable/pivot.css">
        <script type="text/javascript" src="${ctx}/pages/geraInfoPivotTable/jquery-1.8.3.min.js"></script>

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


	<!-- script type="text/javascript" src="${ctx}/pages/painel/jquery-1.8.0.js"></script  -->

        <script type="text/javascript" src="${ctx}/pages/geraInfoPivotTable/jquery-ui-1.9.2.custom.min.js"></script>

	<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker.js"></script>
	<script src="${ctx}/js/jquery.ui.datepicker-locale.js"></script>
	<script src="${ctx}/js/jquery.ui.datepicker-init.js"></script>

	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.position.js"></script>

        <script type="text/javascript" src="${ctx}/pages/geraInfoPivotTable/pivot.js"></script>

        <link rel="stylesheet" type="text/css" href="${ctx}/pages/portal/css/jquery-ui-1.8.21.custom.css" />
        <link href="${ctx}/novoLayout/common/theme/css/style-light.min.css" rel="stylesheet" />
</head>
    <style>
        * {font-family: Verdana;}
    </style>
<body>
        <script type="text/javascript">
			$(function() {
				$("#POPUP_PARAM").dialog( {
					autoOpen : false,
					width : 800,
					height : 500,
					modal : true
				});
			});

    		function pageLoad()
    		{
    			$(function()
    			{
    				$('input.datepicker').datepicker();
    			});
    		}
    		function fecharTelaParm(){
    			$("#POPUP_PARAM").dialog('close');
    		}
    		function gerarPainel(){
    			//var dataInicio = document.getElementById("PARAM.dataInicial").value;
    			//var dataFim = document.getElementById("PARAM.dataFinal").value;
    			var queryStr = '';
    			//if(validaData(dataInicio, dataFim)){
    				if (document.formParametros.validate())
    				{
    					queryStr = AjaxUtils.generateQueryStringByForm(document.formParametros);
    					fecharTelaParm();
    	                $.getJSON("${ctx}/pages/geraInfoPivotTable/geraInfoPivotTable.load?parmOK=true&idConsulta=<%=idConsulta%>&" + queryStr, function(mps) {
    	                    $("#output").pivotUI(mps);
    	                });
    				}
    			//}
    		}
    		function gerarPainelSemParametros(){
    			var queryStr = '';
 				queryStr = AjaxUtils.generateQueryStringByForm(document.formParametros);
 	            $.getJSON("${ctx}/pages/geraInfoPivotTable/geraInfoPivotTable.load?parmOK=true&idConsulta=<%=idConsulta%>&" + queryStr, function(mps) {
 	                $("#output").pivotUI(mps);
 	            });
    		}

    		function validaData(dataInicio, dataFim) {
    			if (typeof(locale) === "undefined") locale = '';

    			var dtInicio = new Date();
    			var dtFim = new Date();

    			var dtInicioConvert = '';
    			var dtFimConvert = '';
    			var dtInicioSplit = dataInicio.split("/");
    			var dtFimSplit = dataFim.split("/");

    			if (locale == 'en') {
    				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
    				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
    			} else {
    				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
    				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
    			}

    			dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
    			dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

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
    		function imprimir(){
    			$('#tblPivot').printElement({
    				printMode : 'popup',
    				leaveOpen : true,
    				 pageTitle : 'CITSMART Report',
    				iframeElementOptions:{classNameToAdd : 'ui-corner-all resBusca'}
    				});
    		}
    		function geraOds(){
    			document.formParametros.conteudo.value = document.getElementById('tblPivot').innerHTML;
    			document.formParametros.fireEvent('geraOds');
    		}
    		function geraExcel(){
    			document.formParametros.conteudo.value = document.getElementById('tblPivot').innerHTML;
    			document.formParametros.fireEvent('geraExcel');
    		}
        </script>
        <div id='divImpressao'>
        	<table>
        		<tr>
        			<td>
        				<img style='cursor:pointer' src='${ctx}/imagens/impressora.gif' border='0' onclick='imprimir()'/>
        			</td>
        			<td>
        				<img style='cursor:pointer' src='${ctx}/imagens/Excel-icon.png' border='0' onclick='geraExcel()'/>
        			</td>
        			<td>
        				<img style='cursor:pointer' src='${ctx}/imagens/libreoffice-writer.png' border='0' onclick='geraOds()'/>
        			</td>
        		</tr>
        	</table>
        </div><br>
        <div id="output" style="margin: 30px;"></div>

<div  style="background:white" id="POPUP_PARAM">
	<form name="formParametros" id="formParametros" action='${ctx}/pages/geraInfoPivotTable/geraInfoPivotTable'>
	<input type='hidden' name='conteudo' id='conteudo'/>
	<div id='divParametros' style='width: 98%; overflow: auto; padding: 7px;'></div>
		<table>
			<tr>
				<td align="center" valign="middle">
					<div id="Throbber" class="throbber"></div>
				</td>
				<td>

					<button type='button' name='btnOKParms' class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only light" onclick='gerarPainel();' style="margin: 20px !important;">
						<img src="${ctx}/template_new/images/icons/small/grey/chart_8.png"/>
						<span><fmt:message key="citcorpore.comum.gerar" /></span>
					</button>

					<button type='button' name='btnFecharParms' class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only light" onclick='fecharTelaParm();' style="margin: 20px !important;">
						<img src="${ctx}/template_new/images/icons/small/grey/clear.png"/>
						<span><fmt:message key="citcorpore.comum.fechar" /></span>
					</button>

					<!-- <input type='button' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only' id='btnOKParms' name='btnOKParms' value='Gerar' onclick='gerarPainel()'/>
					<input type="reset" name='btnFecharParms' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only' value='Limpar'/> -->
				</td>
			</tr>
		</table>
	</form>
</div>

</body>
</html>
