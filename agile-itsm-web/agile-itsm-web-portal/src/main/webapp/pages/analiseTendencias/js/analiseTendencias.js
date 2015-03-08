$(window).load(function(){
	$('#conteudoiframeCadastrarProblema').html('<iframe id="iframeCadastrarProblema"  src="about:blank" width="100%"  class="iframeSemBorda"></iframe>'); 
});
		

$("#POPUP_SERVICO").dialog({
	autoOpen : false,
	width : 600,
	height : 400,
	modal : true
});

$("#POPUP_SOLICITANTE").dialog({
	autoOpen : false,
	width : 600,
	height : 400,
	modal : true
});

$("#POPUP_ITEMCONFIG").dialog({
	autoOpen : false,
	width : 600,
	height : 400,
	modal : true
});

function abrePopupServico(){
	$("#POPUP_SERVICO").dialog("open");
}

function abrePopupSolicitante(){
	$("#POPUP_SOLICITANTE").dialog("open");
}

function abrePopupIC(){
	$("#POPUP_ITEMCONFIG").dialog("open");
}

function LOOKUP_SERVICO_select(id, desc){
	document.getElementById("idServico").value = id;
	document.getElementById("servico").value = desc;
	$("#POPUP_SERVICO").dialog("close");
}

function LOOKUP_SOLICITANTE_select(id, desc){
	document.getElementById("idEmpregado").value = id;
	document.getElementById("nomeSolicitante").value = desc;
	$("#POPUP_SOLICITANTE").dialog("close");
}

function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
	document.getElementById("idItemConfiguracao").value = id;
	document.getElementById("nomeItemConfiguracao").value = desc;
	$("#POPUP_ITEMCONFIG").dialog("close");		
}

/**
* @author rodrigo.oliveira
*/
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

function valida(){
	if (document.getElementById('dataInicio').value==''){
           alert(i18n_message("citcorpore.comum.validacao.datainicio"));
           return false;
       }
       if (document.getElementById('dataFim').value==''){
           alert(i18n_message("citcorpore.comum.validacao.datafim"));
           return false;
       }
       if (!DateTimeUtil.isValidDate(document.getElementById('dataInicio').value)){
           alert(i18n_message("citcorpore.comum.datainvalida"));
           return false;
       }
       if (!DateTimeUtil.isValidDate(document.getElementById('dataFim').value)){
           alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
           return false;
       }
       if(!validaData(document.getElementById('dataInicio').value,document.getElementById('dataFim').value)){
           return false;
       }
       if ((document.getElementById('idContrato').value=='')||(document.getElementById('idContrato').value==' ')||( document.getElementById('idContrato').value=='0')){
    	   alert(i18n_message("contrato.alerta.informe_contrato"));
    	   return false;
       }
       if ((document.getElementById('qtdeCritica').value=='')||(document.getElementById('qtdeCritica').value==' ')){
    	   alert(i18n_message("problema.analiseTendencias.informeAqtdeCritica"));
    	   return false;
       }
       return true;
}

function pesquisarTendencia() {
	if (valida()){
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("buscarTendencia");		
	}
}

function showResult() {
	$('.result').show();
}

function cadastrarProblema(idContrato, id, tipo ){	
	var url = URL_SISTEMA +"/pages/problema/problema.load?iframe=true&tendenciaProblema=S&id="+id+"&tipo="+tipo+"&idContrato="+idContrato; 
	document.getElementById('iframeCadastrarProblema').src = url;
	$("#modal_cadastrarProblema").modal("show");
}

function fecharProblema(){
	$("#modal_cadastrarProblema").modal("hide");
}

//Gerar relatório
function gerarRelatorio (id, tipo) {
	JANELA_AGUARDE_MENU.show();
	document.getElementById("idRelatorio").value = id;
	document.getElementById("tipoRelatorio").value = tipo;
	document.form.fireEvent("gerarRelatorio");
}

exibeTendenciaServico = function(row, obj){
	var id = obj.id;
	var idContrato = $("#idContrato").val();
    obj.sequenciaOS = row.rowIndex; 
    row.cells[3].innerHTML = "<td><button class='btn btn-default' type='button' onclick='gerarRelatorio("+id+", \"servico\"); return false;' id='btnGerarTendenciaServico' name='btnGerarTendenciaServico'>"+i18n_message("citcorpore.comum.gerarGrafico")+"</button>&nbsp;<button class='btn btn-default' type='button' onclick='cadastrarProblema(\""+idContrato+"\",\""+id+"\",\"servico\");' id='btnGerarTendenciaServico' name='btnGerarTendenciaServico'>"+i18n_message("tipoDemandaServico.criarProblema")+"</button></td>";
}

exibeTendenciaCausa = function(row, obj){
	var id = obj.id;
	var idContrato = $("#idContrato").val();
    obj.sequenciaOS = row.rowIndex; 
    row.cells[3].innerHTML = "<td><button class='btn btn-default' type='button' onclick='gerarRelatorio("+id+", \"causa\"); return false;' id='btnGerarTendenciaServico' name='btnGerarTendenciaServico'>"+i18n_message("citcorpore.comum.gerarGrafico")+"</button>&nbsp;<button class='btn btn-default' type='button' onclick='cadastrarProblema(\""+idContrato+"\",\""+id+"\",\"causa\");' id='btnGerarTendenciaCausa' name='btnGerarTendenciaCausa'>"+i18n_message("tipoDemandaServico.criarProblema")+"</button></td>";
}

exibeTendenciaItemConfig = function(row, obj){
	var id = obj.id;
	var idContrato = $("#idContrato").val();
    obj.sequenciaOS = row.rowIndex; 
    row.cells[3].innerHTML = "<td><button class='btn btn-default' type='button' onclick='gerarRelatorio("+id+", \"itemConfiguracao\"); return false;' id='btnGerarTendenciaServico' name='btnGerarTendenciaServico'>"+i18n_message("citcorpore.comum.gerarGrafico")+"</button>&nbsp;<button class='btn btn-default' type='button' onclick='cadastrarProblema(\""+idContrato+"\",\""+id+"\",\"itemConfiguracao\");' id='btnGerarTendenciaItemConfig' name='btnGerarTendenciaItemConfig'>"+i18n_message("tipoDemandaServico.criarProblema")+"</button></td>";
}

$(document).on('click', '.limpar', function(){
	var input = $(this).attr('data-input');
	var id = $(this).attr('data-id');
	
	$('#' + id).attr('value', '');
	$('#' + input).attr('value', '');
	
});
