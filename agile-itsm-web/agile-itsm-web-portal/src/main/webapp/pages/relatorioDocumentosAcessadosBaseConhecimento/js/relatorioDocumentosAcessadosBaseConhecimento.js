/**Autocomplete **/
var completeUsuario;
var nomeUsuario;
var idUsuario;

function montaParametrosAutocompleteUsuario(){
	idUsuario =  $("#idUsuario").val();
	nomeUsuario = $("#nomeUsuario").val();
	completeUsuario.setOptions({params: {idUsuario: idUsuario, nomeUsuario: nomeUsuario} });
}

$(document).ready(function() {
	$('#nomeUsuario').on('click', function(){
		montaParametrosAutocompleteUsuario();
	});
	completeUsuario = $('#nomeUsuario').autocomplete({ 
		serviceUrl:'pages/autoCompleteUsuario/autoCompleteUsuario.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idUsuario').val(data);
			$('#nomeUsuario').val(value);
		}
	});
});

function limpar(){
	document.form.clear();
	document.getElementById("Resumida").checked=true;
}

function alimentaVisualizacao(){
	if (document.getElementById("Resumida").checked){
		document.getElementById("visualizacao").value = "R";
	} else {
		document.getElementById("visualizacao").value = "A";
	}
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
	if (document.getElementById('dataInicial').value==''){
		alert(i18n_message("citcorpore.comum.validacao.datainicio"));
		return false;
	}
	if (document.getElementById('dataFinal').value==''){
		alert(i18n_message("citcorpore.comum.validacao.datafim"));
		return false;
	}
	if ((document.getElementById('nomeUsuario').value=='')||(document.getElementById('nomeUsuario').value==' ')){
		document.getElementById('idUsuario').value='0';
	}
	
	if (!DateTimeUtil.isValidDate(document.getElementById('dataInicial').value)){
		alert(i18n_message("citcorpore.comum.datainvalida"));
		return false;
	}

  	if (!DateTimeUtil.isValidDate(document.getElementById('dataFinal').value)){
		alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
		return false;
	}
  	
  	if(!validaData(document.getElementById('dataInicial').value,document.getElementById('dataFinal').value)){
  		return false;
  	}
	return true;
}

function gerarRelatorio(formato){
	if (valida()){
		alimentaVisualizacao();
		document.getElementById('formato').value=formato;
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("geraRelatorioDocumentosAcessadosBaseConhecimento");
	}
}