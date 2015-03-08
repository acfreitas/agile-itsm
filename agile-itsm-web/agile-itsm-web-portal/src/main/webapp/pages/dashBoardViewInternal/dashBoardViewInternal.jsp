<%
String idDashBoard = request.getParameter("idDashBoard");
if (idDashBoard == null){
	idDashBoard = (String)request.getAttribute("idDashBoard");
	if (idDashBoard == null){
		idDashBoard = "";
	}
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
		<%@include file="/include/header.jsp"%>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/pages/dashBoardBuilder/css/byrei-dyndiv_0.5.css">
		<script type="text/javascript" src="${ctx}/pages/dashBoardBuilder/js/byrei-dyndiv_1.0rc1.js"></script>

		<script  type="text/javascript" src="${ctx}/js/CollectionUtils.js"></script>

	<script type="text/javascript" src="${ctx}/template_new/js/DataTables/jquery.dataTables.js"></script>

	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.datepicker.js"></script>
	<script src="${ctx}/js/jquery.ui.datepicker-pt-BR.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.position.js"></script>

<script>

</script>

<style>
#testdiv_1 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 0px;
 left: 5px;
 position: absolute;
}
#testdiv_2 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 30px;
 left: 5px;
 position: absolute;
}
#testdiv_3 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 60px;
 left: 5px;
 position: absolute;
}
#testdiv_4 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 90px;
 left: 5px;
 position: absolute;
}
#testdiv_5 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 120px;
 left: 5px;
 position: absolute;
}
#testdiv_6 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 150px;
 left: 5px;
 position: absolute;
}
#testdiv_7 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 180px;
 left: 5px;
 position: absolute;
}
#testdiv_8 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 210px;
 left: 5px;
 position: absolute;
}
#testdiv_9 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 240px;
 left: 5px;
 position: absolute;
}
#testdiv_10 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 270px;
 left: 5px;
 position: absolute;
}
#testdiv_11 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 300px;
 left: 5px;
 position: absolute;
}
#testdiv_12 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 330px;
 left: 5px;
 position: absolute;
}
#testdiv_13 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 360px;
 left: 5px;
 position: absolute;
}
#testdiv_14 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 390px;
 left: 5px;
 position: absolute;
}
#testdiv_15 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 420px;
 left: 5px;
 position: absolute;
}
#testdiv_16 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 450px;
 left: 5px;
 position: absolute;
}
#testdiv_17 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 480px;
 left: 5px;
 position: absolute;
}
#testdiv_18 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 510px;
 left: 5px;
 position: absolute;
}
#testdiv_19 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 540px;
 left: 5px;
 position: absolute;
}
#testdiv_20 {
 width: 20px;
 height: 20px;
 background: white;
 border: 1px solid #aaa;
 top: 570px;
 left: 5px;
 position: absolute;
}
<%if (idDashBoard != null && !idDashBoard.trim().equalsIgnoreCase("")){%>
#barraFerr {
 background-color: light yellow;
 border: 1px solid #aaa;
 top: 0px;
 left: 0px;
 position: absolute;
}
<%}%>
</style>

</head>
<body>
<cit:janelaAguarde id="JANELA_AGUARDE_DASH"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">...</cit:janelaAguarde>
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
    					document.formParametros.action = '${ctx}/pages/dashBoardViewInternal/dashBoardViewInternal.load';
    					document.formParametros.submit();
    				}
    			//}
    		}

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
    		function setaRefresh(tempo){
    			if (tempo == undefined || tempo == ''){
    				return;
    			}
    			window.setTimeout("refreshDash()",tempo);
    		}
    		function refreshDash(){
    			JANELA_AGUARDE_DASH.show();
				document.formParametros.action = '${ctx}/pages/dashBoardViewInternal/dashBoardViewInternal.load';
				document.formParametros.submit();
    		}
    		function showURL(){
    			prompt("URL do Dashboard:", "${ctx}/pages/dashBoardViewInternal/dashBoardViewInternal.load?idDashBoard=<%=idDashBoard%>");
    		}
    		function expandURL(){
    			window.open("${ctx}/pages/dashBoardViewInternal/dashBoardViewInternal.load?idDashBoard=<%=idDashBoard%>");
    		}
        </script>

<div id="testdiv_1"  ondblclick="mostraProp(this)">
 <div id="testdiv_1_internal"></div>
</div>

<div id="testdiv_2"  ondblclick="mostraProp(this)">
 <div id="testdiv_2_internal"></div>
</div>

<div id="testdiv_3"  ondblclick="mostraProp(this)">
 <div id="testdiv_3_internal"></div>
</div>

<div id="testdiv_4"  ondblclick="mostraProp(this)">
 <div id="testdiv_4_internal"></div>
</div>

<div id="testdiv_5"  ondblclick="mostraProp(this)">
 <div id="testdiv_5_internal"></div>
</div>

<div id="testdiv_6"  ondblclick="mostraProp(this)">
 <div id="testdiv_6_internal"></div>
</div>

<div id="testdiv_7"  ondblclick="mostraProp(this)">
 <div id="testdiv_7_internal"></div>
</div>

<div id="testdiv_8"  ondblclick="mostraProp(this)">
 <div id="testdiv_8_internal"></div>
</div>

<div id="testdiv_9"  ondblclick="mostraProp(this)">
 <div id="testdiv_9_internal"></div>
</div>

<div id="testdiv_10"  ondblclick="mostraProp(this)">
 <div id="testdiv_10_internal"></div>
</div>

<div id="testdiv_11"  ondblclick="mostraProp(this)">
 <div id="testdiv_11_internal"></div>
</div>

<div id="testdiv_12"  ondblclick="mostraProp(this)">
 <div id="testdiv_12_internal"></div>
</div>

<div id="testdiv_13"  ondblclick="mostraProp(this)">
 <div id="testdiv_13_internal"></div>
</div>

<div id="testdiv_14"  ondblclick="mostraProp(this)">
 <div id="testdiv_14_internal"></div>
</div>

<div id="testdiv_15"  ondblclick="mostraProp(this)">
 <div id="testdiv_15_internal"></div>
</div>

<div id="testdiv_16"  ondblclick="mostraProp(this)">
 <div id="testdiv_16_internal"></div>
</div>

<div id="testdiv_17"  ondblclick="mostraProp(this)">
 <div id="testdiv_17_internal"></div>
</div>

<div id="testdiv_18"  ondblclick="mostraProp(this)">
 <div id="testdiv_18_internal"></div>
</div>

<div id="testdiv_19"  ondblclick="mostraProp(this)">
 <div id="testdiv_19_internal"></div>
</div>

<div id="testdiv_20"  ondblclick="mostraProp(this)">
 <div id="testdiv_20_internal"></div>
</div>

<%if (idDashBoard != null && !idDashBoard.trim().equalsIgnoreCase("")){%>
<div id='barraFerr'>
	<table>
		<tr>
			<td>
				<img border='0' onclick="showURL()" style='cursor:pointer' src='${ctx}/imagens/share2.png' title='Clique aqui para copiar a URL'/>
			</td>
		</tr>
		<tr>
			<td>
				<img border='0' onclick="expandURL()" style='cursor:pointer' src='${ctx}/imagens/expand.png' title='Clique aqui para expandir a visualização (abrir em nova janela)'/>
			</td>
		</tr>
		<tr>
			<td>
				<img border='0' onclick="refreshDash()" style='cursor:pointer' src='${ctx}/imagens/refreshpeq.png' title='Clique aqui para fazer refresh na tela'/>
			</td>
		</tr>
	</table>
</div>
<%}%>
<div  style="background:white" id="POPUP_PARAM">
	<form name="formParametros" id="formParametros">
		<input type='hidden' name='idDashBoard' id='idDashBoard' value='<%=idDashBoard%>'/>
		<input type='hidden' name='parmOK' id='parmOK' value='S'/>
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