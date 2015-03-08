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

	<script type="text/javascript" src="${ctx}/js/CollectionUtils.js"></script>

<script>
var hash = new HashMap();
var hashTitulo = new HashMap();
var hashSubst = new HashMap();
var divSelecionada = '';
var ultimaYPos = 600;
function mostraProp(divPObj){
	divSelecionada = divPObj.id;
	var obj = hash[divSelecionada];
	if (obj == null || obj == undefined || obj == ''){
		HTMLUtils.setValue('idConsulta', '');
		HTMLUtils.setValue('titulo', '');
		HTMLUtils.setValue('parmsSubst', '');
	}else{
		var varSubt = hashSubst[divSelecionada];
		if (varSubt == null || varSubt == undefined || varSubt == 'undefined'){
			varSubt = '';
		}
		HTMLUtils.setValue('idConsulta', hash[divSelecionada]);
		HTMLUtils.setValue('titulo', hashTitulo[divSelecionada]);
		HTMLUtils.setValue('parmsSubst', varSubt);
	}
	$("#POPUP_PROPRIEDADE").dialog("open");
}
addEvent(window, "load", load, false);
function load() {
	$("#POPUP_PROPRIEDADE").dialog({
		autoOpen : false,
		width : 600,
		height : 500,
		modal : true,
		buttons: {
	        "OK": function() {
	        	if (document.form.idConsulta.value == ''){
	        		alert('<fmt:message key="dashboard.selecione"/>');
	        		return;
	        	}
	        	document.getElementById(divSelecionada + '_internal').innerHTML = StringUtils.replaceAll(document.form.titulo.value, '...', '');
	        	hash[divSelecionada] = document.form.idConsulta.value;
	        	hashTitulo[divSelecionada] = document.form.titulo.value;
	        	hashSubst[divSelecionada] = document.form.parmsSubst.value;
	            $( this ).dialog( "close" );
	        },
	        "Delete": function() {
	        	document.getElementById(divSelecionada + '_internal').innerHTML = '';
	        	document.getElementById(divSelecionada + '').style.top = ultimaYPos + 'px';
	        	document.getElementById(divSelecionada + '').style.left = '5px';
	        	document.getElementById(divSelecionada + '').style.width = '20px';
	        	document.getElementById(divSelecionada + '').style.height = '20px';
	        	hash[divSelecionada] = '';
	        	hashTitulo[divSelecionada] = '';
	        	hashSubst[divSelecionada] = '';
	        	ultimaYPos = ultimaYPos + 30;
	            $( this ).dialog( "close" );
	        },
	        Cancel: function() {
	          	$( this ).dialog( "close" );
	        }
	      }
	});

	$("#POPUP_SALVAR").dialog({
		autoOpen : false,
		width : 600,
		height : 600,
		modal : true,
		buttons: {
	        "OK": function() {
	        	if (StringUtils.isBlank(document.formSalvar.nomeDashBoard.value)){
	        		alert('<fmt:message key="citcorpore.comum.campo_obrigatorio"/>:<fmt:message key="dashboard.titulo"/>');
	        		return;
	        	}
	        	if (StringUtils.isBlank(document.formSalvar.identificacao.value)){
	        		alert('<fmt:message key="citcorpore.comum.campo_obrigatorio"/>:<fmt:message key="dashboard.identificacao"/>');
	        		return;
	        	}
	        	if (!document.formSalvar.validate()){
	        		return;
	        	}
	        	document.formSalvar.fireEvent('saveDash');
	        },
	        Cancel: function() {
	          	$( this ).dialog( "close" );
	        }
	      }
	});
}
function saveDash(){
	var itens = new Array();
	var iAux = 0;
	for (var i = 1; i <= 20; i++){
		var divObj = document.getElementById('testdiv_' + i);
		if ((divObj.style.left != undefined && divObj.style.left != '') &&
				(divObj.style.top != undefined && divObj.style.top != '') &&
				(divObj.style.width != undefined && divObj.style.width != '') &&
				(divObj.style.height != undefined && divObj.style.height != '')){
			//alert('Dimensoes: ' + divObj.id + ' --> ' + divObj.style.left + ' - ' + divObj.style.top + ' - ' + divObj.style.width + ' - ' + divObj.style.height);
			var obj = hash[divObj.id];
			if (obj == null || obj == undefined){
				alert('<fmt:message key="dashboard.existeSemItemDados"/>');
				return;
			}
			if (obj != ''){
				var objTitulo = hashTitulo[divObj.id];
				var objSubst = hashSubst[divObj.id];
				if (objSubst == null || objSubst == undefined || objSubst == 'undefined'){
					objSubst = '';
				}
				itens[iAux] = createItemDash(divObj.id, divObj.style.left, divObj.style.top, divObj.style.width, divObj.style.height, obj, iAux, objTitulo, objSubst);
				iAux++;
			}
		}
	}
	var objsSerializados = ObjectUtils.serializeObjects(itens);
    document.formSalvar.colItensSerialize.value = objsSerializados;
    $("#POPUP_SALVAR").dialog("open");
}
function novoDash(){
	$("#POPUP_PROPRIEDADE").dialog("close");
	$("#POPUP_SALVAR").dialog("close");
	document.formSalvar.colItensSerialize.value = '';
	document.formSalvar.idDashBoard.value = '';
	window.location = '${ctx}/pages/dashBoardBuilderInternal/dashBoardBuilderInternal.load';
}
function createItemDash(id, left, top, width, height, idConsulta, posicao, titulo, subst){
	item=new Object();
	item.id = id;
	item.itemLeft = StringUtils.onlyNumbers(left);
	item.itemTop = StringUtils.onlyNumbers(top);
	item.itemWidth = StringUtils.onlyNumbers(width);
	item.itemHeight = StringUtils.onlyNumbers(height);
	item.idConsulta = idConsulta;
	item.posicao = posicao;
	item.titulo = titulo;
	item.parmsSubst = subst;
	return item;
}
function validaTituloItem(obj){
	var str = obj.options[obj.selectedIndex].text;
	document.form.titulo.value = StringUtils.replaceAll(str, '...', '');
}
</script>

<style>
#testdiv_1 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 0px;
 left: 5px;
 position: absolute;
}
#testdiv_2 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 30px;
 left: 5px;
 position: absolute;
}
#testdiv_3 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 60px;
 left: 5px;
 position: absolute;
}
#testdiv_4 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 90px;
 left: 5px;
 position: absolute;
}
#testdiv_5 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 120px;
 left: 5px;
 position: absolute;
}
#testdiv_6 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 150px;
 left: 5px;
 position: absolute;
}
#testdiv_7 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 180px;
 left: 5px;
 position: absolute;
}
#testdiv_8 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 210px;
 left: 5px;
 position: absolute;
}
#testdiv_9 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 240px;
 left: 5px;
 position: absolute;
}
#testdiv_10 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 270px;
 left: 5px;
 position: absolute;
}
#testdiv_11 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 300px;
 left: 5px;
 position: absolute;
}
#testdiv_12 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 330px;
 left: 5px;
 position: absolute;
}
#testdiv_13 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 360px;
 left: 5px;
 position: absolute;
}
#testdiv_14 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 390px;
 left: 5px;
 position: absolute;
}
#testdiv_15 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 420px;
 left: 5px;
 position: absolute;
}
#testdiv_16 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 450px;
 left: 5px;
 position: absolute;
}
#testdiv_17 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 480px;
 left: 5px;
 position: absolute;
}
#testdiv_18 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 510px;
 left: 5px;
 position: absolute;
}
#testdiv_19 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 540px;
 left: 5px;
 position: absolute;
}
#testdiv_20 {
 width: 20px;
 height: 20px;
 background: #ccc;
 border: 1px solid #aaa;
 top: 570px;
 left: 5px;
 position: absolute;
}
</style>

</head>
<body>

<div id="testdiv_1" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_1_internal"></div>
</div>

<div id="testdiv_2" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_2_internal"></div>
</div>

<div id="testdiv_3" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_3_internal"></div>
</div>

<div id="testdiv_4" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_4_internal"></div>
</div>

<div id="testdiv_5" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_5_internal"></div>
</div>

<div id="testdiv_6" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_6_internal"></div>
</div>

<div id="testdiv_7" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_7_internal"></div>
</div>

<div id="testdiv_8" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_8_internal"></div>
</div>

<div id="testdiv_9" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_9_internal"></div>
</div>

<div id="testdiv_10" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_10_internal"></div>
</div>

<div id="testdiv_11" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_11_internal"></div>
</div>

<div id="testdiv_12" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_12_internal"></div>
</div>

<div id="testdiv_13" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_13_internal"></div>
</div>

<div id="testdiv_14" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_14_internal"></div>
</div>

<div id="testdiv_15" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_15_internal"></div>
</div>

<div id="testdiv_16" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_16_internal"></div>
</div>

<div id="testdiv_17" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_17_internal"></div>
</div>

<div id="testdiv_18" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_18_internal"></div>
</div>

<div id="testdiv_19" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_19_internal"></div>
</div>

<div id="testdiv_20" class="dynDiv_moveDiv dynDiv_bodyLimit dynDiv_showResize-active" ondblclick="mostraProp(this)">
 <div class="dynDiv_resizeDiv_tl"></div>
 <div class="dynDiv_resizeDiv_tr"></div>
 <div class="dynDiv_resizeDiv_bl"></div>
 <div class="dynDiv_resizeDiv_br"></div>
 <div id="testdiv_20_internal"></div>
</div>

<div id="POPUP_PROPRIEDADE" title="<fmt:message key='dashboard.propriedade'/>">
	<div class="box grid_16 tabs" style="width: 560px !important;height: 200px !important;" >
		<div class="toggle_container">
			<div class="block">
				<div class="section">
					<form name="form" action="${ctx}/pages/dashBoardBuilderInternal/dashBoardBuilderInternal">
						<div class="columns clearfix">
							<div class="col_100">
								<fieldset>
									<label class="campoObrigatorio" style="margin-bottom: 3px;">
										<fmt:message key="dashboard.item" />
									</label>
									<div>
										<select id="idConsulta" name="idConsulta" onchange='validaTituloItem(this)'>
											<option value=""><fmt:message key="citcorpore.comum.selecione"/></option>
										</select>
									</div>
								</fieldset>
							</div>
							<div class="col_100">
								<fieldset>
									<label class="campoObrigatorio" style="margin-bottom: 3px;">
										<fmt:message key="dashboard.titulo" />
									</label>
									<div>
										<input type='text' name="titulo" id="titulo" maxlength="150" class="Valid[Required] Description[dashboard.titulo]" />
									</div>
								</fieldset>
							</div>
							<div class="col_100">
								<fieldset>
									<label style="margin-bottom: 3px;">
										<fmt:message key="dashboard.substituicaoparams" />
									</label>
									<div>
										<textarea name="parmsSubst" id="parmsSubst" cols="70" rows="8" class="Description[dashboard.substituicaoparams]"></textarea>
									</div>
								</fieldset>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_SALVAR" title="<fmt:message key='dashboard.salvar'/>">
	<div class="box grid_16 tabs" style="width: 560px !important;height: 200px !important;" >
		<div class="toggle_container">
			<div class="block">
				<div class="section">
					<form name="formSalvar" action="${ctx}/pages/dashBoardBuilder/dashBoardBuilder">
						<input type='hidden' name='idDashBoard' id='idDashBoard'/>
						<input type='hidden' name='colItensSerialize' id='colItensSerialize'/>
						<div class="columns clearfix">
							<div class="col_100">
								<fieldset>
									<label class="campoObrigatorio" style="margin-bottom: 3px;">
										<fmt:message key="dashboard.titulo" />
									</label>
									<div>
										<input type='text' name="nomeDashBoard" maxlength="150" class="Valid[Required] Description[dashboard.titulo]" />
									</div>
								</fieldset>
							</div>
							<div class="col_100">
								<fieldset>
									<label class="campoObrigatorio" style="margin-bottom: 3px;">
										<fmt:message key="dashboard.identificacao" />
									</label>
									<div>
										<input type='text' name="identificacao" maxlength="70" class="Valid[Required] Description[dashboard.identificacao]" />
									</div>
								</fieldset>
							</div>
							<div class="col_100">
								<fieldset>
									<label class="campoObrigatorio" style="margin-bottom: 3px;">
										<fmt:message key="dashboard.tempoRefresh" />
									</label>
									<div>
										<input type='text' name="tempoRefresh" maxlength="5" size="5" class="Format[Number] Valid[Required] Description[dashboard.tempoRefresh]" />
									</div>
								</fieldset>
							</div>
							<div class="col_100">
								<fieldset>
									<label style="margin-bottom: 3px;">
										<fmt:message key="dashboard.parametros" />
									</label>
									<div>
										<textarea name="parametros" rows="5" cols="80" class="Description[dashboard.parametros]"></textarea>
									</div>
								</fieldset>
							</div>
							<div class="col_100">
								<fieldset>
									<label class="campoObrigatorio" style="margin-bottom: 3px;">
										<fmt:message key="dashboard.tipo" />
									</label>
									<div>
										<select id="tipo" name="tipo">
											<option value=""><fmt:message key="citcorpore.comum.selecione"/></option>
											<option value="C"><fmt:message key="dashboard.corporativo"/></option>
											<option value="P"><fmt:message key="dashboard.meupessoal"/></option>
										</select>
									</div>
								</fieldset>
							</div>
							<div class="col_100">
								<fieldset>
									<label><fmt:message key="dashboard.naoAtualizBase"/></label>
										<div>
											<input type='checkbox' name='naoAtualizBase' id='naoAtualizBase' value='S'/>
										</div>
								</fieldset>
							</div>
							<div class="col_100">
								<fieldset>
									<label class="campoObrigatorio" style="margin-bottom: 3px;">
										<fmt:message key="dashboard.situacao" />
									</label>
									<div>
										<select id="situacao" name="situacao">
											<option value="A"><fmt:message key="dashboard.situacaoativo"/></option>
											<option value="I"><fmt:message key="dashboard.situacaoinativo"/></option>
										</select>
									</div>
								</fieldset>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>