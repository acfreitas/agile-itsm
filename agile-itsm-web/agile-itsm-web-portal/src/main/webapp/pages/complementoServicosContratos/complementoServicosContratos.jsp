<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.util.MetaUtil"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
VisaoDTO visaoDTO = (VisaoDTO)request.getAttribute("visaoComplementar");
%>
<style>
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
	.linhaGrid{
		border:1px solid black;
		background-color: #D6E7FF;
		vertical-align: middle;
	}	
</style>
<script>
	var arrayServicos = new Array();
	function mostra_POPUP_COMPL_SERVICO_CONTRATO(){
		document.formComplServicosContratos.clear();
		GRID_ITENS.deleteAllRows();
		$( "#POPUP_COMPL_SERVICO_CONTRATO" ).dialog( 'open' );
	}
	function edita_POPUP_COMPL_SERVICO_CONTRATO(num){
		document.formComplServicosContratos.clear();
		GRID_ITENS.deleteAllRows();
		var json_data = arrayServicos[num];
		var jsonObj = converteStrJSONToObj(json_data);
		HTMLUtils.setValues(document.formComplServicosContratos, null, jsonObj);
		var indice = 1;
		for (var i = 0; i < 50; i++){
			var encontrou = false;
			for (var property in jsonObj) {
				var elem = null;
				var aux01 = 'complexidade' + NumberUtil.zerosAEsquerda(indice,5);
				var aux02 = 'demanda' + NumberUtil.zerosAEsquerda(indice,5);
				var aux03 = 'obs' + NumberUtil.zerosAEsquerda(indice,5);
				var aux04 = 'quantidade' + NumberUtil.zerosAEsquerda(indice,5);
				if (property == aux01){
					GRID_ITENS.addRow();
					encontrou = true;
					try{
						eval('HTMLUtils.setValue("' + aux01 + '", "' + jsonObj[property] + '")');
					}catch(e){}
				}
				if (property == aux02){
					try{
						eval('elem = document.formComplServicosContratos.' + aux02);
					}catch(e){}
					elem.value = jsonObj[property];
				}
				if (property == aux03){
					try{
						eval('elem = document.formComplServicosContratos.' + aux03);
					}catch(e){}			
					elem.value = jsonObj[property];		
				}
				if (property == aux04){
					try{		
						eval('elem = document.formComplServicosContratos.' + aux04);
					}catch(e){}		
					elem.value = jsonObj[property];
				}												
			}
			if (encontrou){
				indice++;
			}
		}
		$( "#POPUP_COMPL_SERVICO_CONTRATO" ).dialog( 'open' );		
	}
	function fecharPopupComplServContrato(){
		$( "#POPUP_COMPL_SERVICO_CONTRATO" ).dialog( 'close' );
	}
	function okPopupComplServContrato(){
		var jsonDataFormEdit = $('#formComplServicosContratos').formParams(false);
		var nomeServico = document.formComplServicosContratos.IDSERVICO_label.value;
		arrayServicos.push(jsonDataFormEdit);
		montaTabelaServicos();
		$( "#POPUP_COMPL_SERVICO_CONTRATO" ).dialog( 'close' );
	}
	function montaTabelaServicos(){
		var str = '<table width="100%">';
		
		str = str + '<tr>';
		str = str + '	<td class="linhaSubtituloGrid">';
		str = str + '		&nbsp;';
		str = str + '	</td>';		
		str = str + '	<td class="linhaSubtituloGrid">';
		str = str + '		Serviço';
		str = str + '	</td>';
		str = str + '	<td class="linhaSubtituloGrid">';
		str = str + '		ANS';
		str = str + '	</td>';
		str = str + '</tr>';		
		
		for(var i = 0; i < arrayServicos.length; i++){
			var json_data = arrayServicos[i];
			//var jsonObj = converteStrJSONToObj(json_data);
			var jsonObj = json_data;
			str = str + '<tr>';
				str = str + '<td style="border:1px solid black; vertical-align: middle;">';
					str = str + '<img border="0" src="${ctx}/imagens/edit.png" onclick="edita_POPUP_COMPL_SERVICO_CONTRATO(' + i + ')" style="cursor:pointer"/>';
				str = str + '</td>';			
				str = str + '<td style="border:1px solid black; vertical-align: middle;">';
					str = str + '<b>' + jsonObj.IDSERVICO_label + '</b>';
				str = str + '</td>';
				str = str + '<td style="border:1px solid black; vertical-align: middle;">';
					str = str + '<img border="0" src="${ctx}/imagens/Partnership-icon.png"/>';
				str = str + '</td>';				
			str = str + '</tr>';
		}
		
		str = str + '</table>';
		
		document.getElementById('divListagemServicosContrato').innerHTML = str;
	}
	function converteStrJSONToObj(text){
		var dataObj = JSON.parse(text, function (key, value) {
		    var type;
		    if (value && typeof value === 'object') {
		        type = value.type;
		        if (typeof type === 'string' && typeof window[type] === 'function') {
		            return new (window[type])(value);
		        }
		    }
		    return value;
		});	
		return dataObj;
	}
	function serialize_TabelaServicos(){
		var json_data_geral = '';
		for(var i = 0; i < arrayServicos.length; i++){
			var json_data = JSON.stringify(arrayServicos[i], function (key, value) {
					  if (value && typeof value === 'object') {
					    var replacement = {};
					    for (var k in value) {
					      if (Object.hasOwnProperty.call(value, k)) {
					        replacement[k && k.charAt(0).toLowerCase() + k.substring(1)] = prepareStringJSON(value[k]);
					      }
					    }
					    return replacement;
					  }
					  return prepareStringJSON(value);
					});				
			json_data = '{"ITEM' + i + '": [' + json_data + ']}';	
			if (json_data_geral != ''){
				json_data_geral = json_data_geral + ',';
			}
			json_data_geral = json_data_geral + json_data;
		}
		document.form.dinamicViewsDadosAdicionais.value = '[' + json_data_geral + ']';
	}
</script>
	<img src='${ctx}/imagens/plus.png' border='0' style='cursor:pointer' onclick='mostra_POPUP_COMPL_SERVICO_CONTRATO()'/>
	<div id='divListagemServicosContrato'>
		Não há serviços selecionados!
	</div>

<div id="POPUP_COMPL_SERVICO_CONTRATO" style='width: 600px; height: 600px' >
	<form name='formComplServicosContratos' id='formComplServicosContratos' action='${ctx}/pages/dinamicViews/dinamicViews'>
	<%
	//MetaUtil.renderViewEdit(visaoDTO, out);
	%>
	<table width='100%'>
         <tr>
         	<td style='text-align: right;'>
         		<input type='button' name='btnAddInteg' id='btnAddInteg' value='Adicionar Item' onclick="GRID_ITENS.addRow();" />
         	</td>
         </tr>	
         <tr>
         	<td>
	         		<cit:grid id="GRID_ITENS" columnHeaders="Complexidade;Atividade;Observações;Custo Total" styleCells="linhaGrid">
	         			<cit:column idGrid="GRID_ITENS" number="001">
	         				<select name='complexidade#SEQ#' id='complexidade#SEQ#'>
	         					<option value='B'>Baixa</option>
	         					<option value='I'>Intermediária</option>
	         					<option value='M'>Mediana</option>
	         					<option value='A'>Alta</option>
	         					<option value='E'>Especialista</option>
	         				</select>
	         			</cit:column>
	         			<cit:column idGrid="GRID_ITENS" number="002">
	         				<textarea name="demanda#SEQ#" cols='55' rows='3' style='border:1px solid black'></textarea> 
	         			</cit:column>
	         			<cit:column idGrid="GRID_ITENS" number="003">
	         				<textarea name="obs#SEQ#" cols='55' rows='3' style='border:1px solid black'></textarea> 
	         			</cit:column>
	         			<cit:column idGrid="GRID_ITENS" number="004">
	         				<input type='text' name='quantidade#SEQ#' size='12' maxlength='14' class='Format[Moeda]'/>
	         			</cit:column>
	         		</cit:grid>
         	</td>
         </tr>	
	</table>
	<table>
		<tr>
			<td>
				<input type='button' name='btnOkServicoContrato' id='btnOkServicoContrato' value='OK' onclick="okPopupComplServContrato();" />
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				<input type='button' name='btnFecharServicoContrato' id='btnFecharServicoContrato' value='Fechar' onclick="fecharPopupComplServContrato();" />
			</td>
		</tr>
	</table>
	</form>
</div>
<script>
	$( "#POPUP_COMPL_SERVICO_CONTRATO" ).dialog({
		title: 'Serviços do Contrato',
		width: 800,
		height: 500,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
		}); 
			
	$("#POPUP_COMPL_SERVICO_CONTRATO").hide();
	
	DEFINEALLPAGES_atribuiCaracteristicasCitAjax();		
</script>