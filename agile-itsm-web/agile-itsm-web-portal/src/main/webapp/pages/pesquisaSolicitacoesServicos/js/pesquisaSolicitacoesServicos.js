/**
 * 
 */
var temporizador;
var completeUnidade;

/**Monta os parametros para a buscas do autocomplete**/ 
function montaParametrosAutocompleteUnidade(){
	$( "#unidadeDes" ).autocomplete({
		  source: function (request, response) {
		  $.ajax({
	          	  url: "pages/autoCompleteUnidade/autoCompleteUnidade.load",
	          	  dataType: "json",
	          		data: {
	        			query: request.term, colection : 1, idContrato : document.form.idContrato.value, consideraHierarquia : 'S'
	         		},
	          		success: function(data) {
                	response($.map(data, function(item) {
                          return {
                              		label: item.nome,
                              		value: item.nome.replace(/-*/, ""),
                              		id: item.idUnidade,
                              	 };
                    	}));
                	}
		        });
		  },
		  minLength: 3,
		  select: function(e, ui) {
			  $('#idUnidade').val(ui.item.id);
			  document.form.fireEvent("preencherComboLocalidade");
		  }
	});
}

function preencherComboUnidade(){
	document.form.fireEvent("preencherComboUnidade");
}

function onkeypressUnidadeDes(){
	document.getElementById("idUnidade").value= "0";
}

$(document).ready(function(){
	$("#POPUP_SOLICITANTE").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	
	$("#POPUP_RESPONSAVEL").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});		

	$("#POPUP_RESPONSAVEL_ATUAL").dialog({
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
	
	$("#POPUP_OCORRENCIAS").dialog({
		autoOpen : false,
		width : 800,
		height : 600,	
		modal : true
	});
	
	$("#POPUP_LOCALIDADE").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	
	$("#POPUP_menuAnexos").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	
	$("#POPUP_SERVICO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
});

function LOOKUP_LOCALIDADE_SOLICITACAO_select(id, desc) {
	document.form.idLocalidade.value = id;
	document.form.nomeLocalidade.value = desc.split(" - ")[0];
	$("#POPUP_LOCALIDADE").dialog("close");		
}
function LOOKUP_SOLICITANTE_select(id, desc){
	document.getElementById("idSolicitante").value = id;
	document.getElementById("nomeSolicitante").value = desc;
	$("#POPUP_SOLICITANTE").dialog("close");
}
function LOOKUP_SERVICO_select(id, desc){
	document.getElementById("idServico").value = id;
	document.getElementById("servico").value = desc;
	$("#POPUP_SERVICO").dialog("close");
}

function LOOKUP_RESPONSAVEL_select(id, desc){
	document.getElementById("idResponsavel").value = id;
	document.getElementById("nomeResponsavel").value = desc;
	$("#POPUP_RESPONSAVEL").dialog("close");
}

function LOOKUP_RESPONSAVEL_ATUAL_select(id, desc){
	document.getElementById("idUsuarioResponsavelAtual").value = id;
	document.getElementById("nomeUsuarioResponsavelAtual").value = desc;
	$("#POPUP_RESPONSAVEL_ATUAL").dialog("close");
}

function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
	document.getElementById("idItemConfiguracao").value = id;
	document.getElementById("nomeItemConfiguracao").value = desc;
	$("#POPUP_ITEMCONFIG").dialog("close");		
}

function abrePopupResponsavel(){
	$("#POPUP_RESPONSAVEL").dialog("open");
}

function abrePopupResponsavelAtual(){
	$("#POPUP_RESPONSAVEL_ATUAL").dialog("open");
}

function abrePopupUsuario(){
	$("#POPUP_SOLICITANTE").dialog("open");
}
function abrePopupIC(){
	$("#POPUP_ITEMCONFIG").dialog("open");
}

function abrePopupServico(){
	$("#POPUP_SERVICO").dialog("open");
}
function inicializarTemporizador(){
	if(temporizador == null){
		temporizador = new Temporizador("imgAtivaTimer");
	} else {
		temporizador = null;
		try{
			temporizador.listaTimersAtivos = [];
		}catch(e){}
		try{
			temporizador.listaTimersAtivos.length = 0;
		}catch(e){}
		temporizador = new Temporizador("imgAtivaTimer");
	}
}

function paginacao(paginacao){
	
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;
	
	document.form.flag.value = "comPag";
	document.form.paginacao.value = paginacao;
	
	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;	
			}
		
		}
		
		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;					
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;					
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;	
					}
				}
				
				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;					
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;	
					}
				}
				
				
				
			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;					
				}
				
			}
	}
		
	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("preencheSolicitacoesRelacionadas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("preencheSolicitacoesRelacionadas");
		}
		
	}
	
}
		
function filtrar(){	
	
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = NumberUtil.apenasNumeros($.trim(document.getElementById("idSolicitacaoServicoPesquisa").value));
	document.getElementById("idSolicitacaoServicoPesquisa").value = numero;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;
	
	document.form.flag.value = "semPag";
	
	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;	
			}
		
		}
		
		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;					
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;					
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;	
					}
				}
				
				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;					
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;	
					}
				}
				
			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;					
				}
				
			}
	}
		
	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "pesquisa";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "pesquisa";
			document.form.fireEvent("setNumeroPaginas");
		}
		
	}
	
}

function imprimirRelatorio(){	
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;
	
	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;	
			}
		
		}
		
		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;					
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;					
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;	
					}
				}
				
				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;					
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;	
					}
				}
				
				
				
			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;					
				}
				
			}
	}
		
	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "pdf";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "pdf";
			document.form.fireEvent("setNumeroPaginas");
		}
		
	}
}

function imprimirRelatorioXls(){	
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;
	
	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;	
			}
		
		}
		
		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;					
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;					
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;	
					}
				}
				
				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;					
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;	
					}
				}
				
				
				
			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;					
				}
				
			}
	}
		
	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "xls";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "xls";
			document.form.fireEvent("setNumeroPaginas");
		}
		
	}
}

function imprimirRelatorioDetalhado(){	
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;
	
	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;	
			}
		
		}
		
		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;					
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;					
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;	
					}
				}
				
				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;					
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;	
					}
				}
				
				
				
			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;					
				}
				
			}
	}
		
	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "pdfdetalhado";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "pdfdetalhado";
			document.form.fireEvent("setNumeroPaginas");
		}
		
	}
}

function imprimirRelatorioXlsDetalhado(){	
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;
	
	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;	
			}
		
		}
		
		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;					
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;					
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;	
					}
				}
				
				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;					
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;	
					}
				}
				
				
				
			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;					
				}
				
			}
	}
		
	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "xlsdetalhado";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "xlsdetalhado";
			document.form.fireEvent("setNumeroPaginas");
		}
		
	}
}

 function anexos(idSolicitacao){

	 document.form.idSolicitacaoServico.value = idSolicitacao; 
	 document.form.fireEvent("restoreUpload");
 }
function reabrir(id){
	if (!confirm(i18n_message("solicitacaoservico.confirme.reabertura"))){
		return;
	}
	document.form.idSolicitacaoServico.value = id; 
	document.form.fireEvent("verificaServicoAtivoSolicitacao");
}

function verificaServico(id){
	if (!confirm(i18n_message("solicitacaoservico.servicoRemovidoDesejaReabrir"))){
		return;
	}
	document.form.idSolicitacaoServico.value = id; 
	document.form.fireEvent("reabre");
}

function consultarOcorrencias(idSolicitacao){
	document.formOcorrencias.idSolicitacaoOcorrencia.value = idSolicitacao;
	document.formOcorrencias.fireEvent("recuperaIdSolicitacaoOcorrencia");
	$("#POPUP_OCORRENCIAS").dialog("open");
}

function limpar(){
	document.form.clear();
	$('#situacao').attr('disabled', false)
	
	document.getElementById("tblResumo").innerHTML="";
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

//		popup para pesquisar de localidade
	
 $(function() {
	$("#addLocalidade").click(function() {
		$("#POPUP_LOCALIDADE").dialog("open");
	});
}); 

function validarNumero() {
	 if (  isNaN(document.getElementById("idSolicitacaoServicoPesquisa").value)   ) {  
	       document.getElementById("idSolicitacaoServicoPesquisa").value = "";
	} else {
	     return true;
	}	
}

function visualizarSolicitacao(idSolicitacao){
	parent.visualizarSolicitacao(idSolicitacao);
}

function executaPesquisa(tipoPesquisa){
	switch(tipoPesquisa){
		case "pesquisa":
			document.form.fireEvent("preencheSolicitacoesRelacionadas");
			break;
		case "pdf":
			document.form.fireEvent("imprimirRelatorio");
			break;
		case "xls":
			document.form.fireEvent("imprimirRelatorioXls");
			break;
		case "pdfdetalhado":
			document.form.fireEvent("imprimirRelatorioDetalhado");
			break;
		case "xlsdetalhado":
			document.form.fireEvent("imprimirRelatorioXlsDetalhado");
			break;

	}
}


// esta parte em diante estava no JSP:

var temporizador;
var completeUnidade;

/**Monta os parametros para a buscas do autocomplete**/
function montaParametrosAutocompleteUnidade(){
	$( "#unidadeDes" ).autocomplete({
		  source: function (request, response) {
		  $.ajax({
	          	  url: "pages/autoCompleteUnidade/autoCompleteUnidade.load",
	          	  dataType: "json",
	          		data: {
	        			query: request.term, colection : 1, idContrato : document.form.idContrato.value, consideraHierarquia : 'S'
	         		},
	          		success: function(data) {
                	response($.map(data, function(item) {
                          return {
                              		label: item.nome,
                              		value: item.nome.replace(/-*/, ""),
                              		id: item.idUnidade,
                              	 };
                    	}));
                	}
		        });
		  },
		  minLength: 3,
		  select: function(e, ui) {
			  $('#idUnidade').val(ui.item.id);
			  document.form.fireEvent("preencherComboLocalidade");
		  }
	});
}

function preencherComboUnidade(){
	document.form.fireEvent("preencherComboUnidade");
}

function onkeypressUnidadeDes(){
	document.getElementById("idUnidade").value= "0";
}


function LOOKUP_LOCALIDADE_SOLICITACAO_select(id, desc) {
	document.form.idLocalidade.value = id;
	document.form.nomeLocalidade.value = desc.split(" - ")[0];
	$("#POPUP_LOCALIDADE").dialog("close");
}
function LOOKUP_SOLICITANTE_select(id, desc){
	document.getElementById("idSolicitante").value = id;
	document.getElementById("nomeSolicitante").value = desc;
	$("#POPUP_SOLICITANTE").dialog("close");
}
function LOOKUP_SERVICO_select(id, desc){
	document.getElementById("idServico").value = id;
	document.getElementById("servico").value = desc;
	$("#POPUP_SERVICO").dialog("close");
}

function LOOKUP_RESPONSAVEL_select(id, desc){
	document.getElementById("idResponsavel").value = id;
	document.getElementById("nomeResponsavel").value = desc;
	$("#POPUP_RESPONSAVEL").dialog("close");
}

function LOOKUP_RESPONSAVEL_ATUAL_select(id, desc){
	document.getElementById("idUsuarioResponsavelAtual").value = id;
	document.getElementById("nomeUsuarioResponsavelAtual").value = desc;
	$("#POPUP_RESPONSAVEL_ATUAL").dialog("close");
}

function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
	document.getElementById("idItemConfiguracao").value = id;
	document.getElementById("nomeItemConfiguracao").value = desc;
	$("#POPUP_ITEMCONFIG").dialog("close");
}

function abrePopupResponsavel(){
	$("#POPUP_RESPONSAVEL").dialog("open");
}

function abrePopupResponsavelAtual(){
	$("#POPUP_RESPONSAVEL_ATUAL").dialog("open");
}

function abrePopupUsuario(){
	$("#POPUP_SOLICITANTE").dialog("open");
}
function abrePopupIC(){
	$("#POPUP_ITEMCONFIG").dialog("open");
}

function abrePopupServico(){
	$("#POPUP_SERVICO").dialog("open");
}
function inicializarTemporizador(){
	if(temporizador == null){
		temporizador = new Temporizador("imgAtivaTimer");
	} else {
		temporizador = null;
		try{
			temporizador.listaTimersAtivos = [];
		}catch(e){}
		try{
			temporizador.listaTimersAtivos.length = 0;
		}catch(e){}
		temporizador = new Temporizador("imgAtivaTimer");
	}
}

function paginacao(paginacao){

	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;

	document.form.flag.value = "comPag";
	document.form.paginacao.value = paginacao;

	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;
			}

		}

		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}

				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;
					}
				}



			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;
				}

			}
	}

	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("preencheSolicitacoesRelacionadas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("preencheSolicitacoesRelacionadas");
		}

	}

}

function filtrar(){

	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = NumberUtil.apenasNumeros($.trim(document.getElementById("idSolicitacaoServicoPesquisa").value));
	document.getElementById("idSolicitacaoServicoPesquisa").value = numero;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;

	document.form.flag.value = "semPag";

	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;
			}

		}

		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}

				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;
					}
				}

			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;
				}

			}
	}

	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "pesquisa";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "pesquisa";
			document.form.fireEvent("setNumeroPaginas");
		}

	}

}

function imprimirRelatorio(){
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;

	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;
			}

		}

		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}

				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;
					}
				}



			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;
				}

			}
	}

	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "pdf";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "pdf";
			document.form.fireEvent("setNumeroPaginas");
		}

	}
}

function imprimirRelatorioXls(){
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;

	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;
			}

		}

		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}

				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;
					}
				}



			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;
				}

			}
	}

	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "xls";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "xls";
			document.form.fireEvent("setNumeroPaginas");
		}

	}
}

function imprimirRelatorioDetalhado(){
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;

	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;
			}

		}

		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}

				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;
					}
				}



			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;
				}

			}
	}

	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "pdfdetalhado";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "pdfdetalhado";
			document.form.fireEvent("setNumeroPaginas");
		}

	}
}

function imprimirRelatorioXlsDetalhado(){
	var dataInicio = document.getElementById("dataInicio").value;
	var dataFim = document.getElementById("dataFim").value;
	var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
	var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
	var dataFimFechamento = document.getElementById("dataFimFechamento").value;

	if(numero != "") {
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
			 	document.getElementById("dataInicio").value = '';
				return false;
			}

		}

		if (dataFim != ""){
			if(DateTimeUtil.isValidDate(dataFim) == false){
				alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
				 document.getElementById("dataFim").value = '';
				return false;
			}
		}
	}
	else {
			if(dataInicioFechamento != "" || dataFimFechamento != ""){
				if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
				 	document.getElementById("dataInicioFechamento").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
					document.getElementById("dataFimFechamento").value = '';
					return false;
				}
				if (dataInicio != ""){
					if(DateTimeUtil.isValidDate(dataFim) == true){
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;
					}
					}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}

				if (dataFim != ""){
					if(DateTimeUtil.isValidDate(dataInicio) == true){
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						 document.getElementById("dataFim").value = '';
						return false;
					}
				}
					else{
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 document.getElementById("dataInicio").value = '';
						return false;
					}
				}



			}
			else{
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeCampoObrigatorio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					document.getElementById("dataFim").value = '';
					return false;
				}

			}
	}

	if(dataInicioFechamento != "" || dataFimFechamento != ""){
		if(validaData(dataInicioFechamento,dataFimFechamento)){
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.origemSolicitacao.value = "xlsdetalhado";
				document.form.fireEvent("setNumeroPaginas");
			}
		}
	}
	else{
		if(validaData(dataInicio,dataFim)){
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.origemSolicitacao.value = "xlsdetalhado";
			document.form.fireEvent("setNumeroPaginas");
		}

	}
}

 function anexos(idSolicitacao){

	 document.form.idSolicitacaoServico.value = idSolicitacao;
	 document.form.fireEvent("restoreUpload");
 }
function reabrir(id){
	if (!confirm(i18n_message("solicitacaoservico.confirme.reabertura"))){
		return;
	}
	document.form.idSolicitacaoServico.value = id;
	document.form.fireEvent("verificaServicoAtivoSolicitacao");
}

function verificaServico(id){
	if (!confirm(i18n_message("solicitacaoservico.servicoRemovidoDesejaReabrir"))){
		return;
	}
	document.form.idSolicitacaoServico.value = id;
	document.form.fireEvent("reabre");
}

function consultarOcorrencias(idSolicitacao){
	document.formOcorrencias.idSolicitacaoOcorrencia.value = idSolicitacao;
	document.formOcorrencias.fireEvent("recuperaIdSolicitacaoOcorrencia");
	$("#POPUP_OCORRENCIAS").dialog("open");
}

function limpar(){
	document.form.clear();
	$('#situacao').attr('disabled', false)

	document.getElementById("tblResumo").innerHTML="";
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

//	popup para pesquisar de localidade

 $(function() {
	$("#addLocalidade").click(function() {
		$("#POPUP_LOCALIDADE").dialog("open");
	});
});

function validarNumero() {
	 if (  isNaN(document.getElementById("idSolicitacaoServicoPesquisa").value)   ) {
	       document.getElementById("idSolicitacaoServicoPesquisa").value = "";
	} else {
	     return true;
	}
}

function visualizarSolicitacao(idSolicitacao){
	parent.visualizarSolicitacao(idSolicitacao);
}

function executaPesquisa(tipoPesquisa){
	switch(tipoPesquisa){
		case "pesquisa":
			document.form.fireEvent("preencheSolicitacoesRelacionadas");
			break;
		case "pdf":
			document.form.fireEvent("imprimirRelatorio");
			break;
		case "xls":
			document.form.fireEvent("imprimirRelatorioXls");
			break;
		case "pdfdetalhado":
			document.form.fireEvent("imprimirRelatorioDetalhado");
			break;
		case "xlsdetalhado":
			document.form.fireEvent("imprimirRelatorioXlsDetalhado");
			break;

	}
}