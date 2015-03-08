/**Monta os parametros para a buscas do autocomplete**/
function montaParametrosAutocompleteServico(){
	idConexaoBI =  $("#idConexaoBI").val() ;
 	completeServicoBI.setOptions({params: {idConexaoBI: idConexaoBI} });
}

/**Autocomplete **/
var completeServicoBI;
var completeServicoCorporeBI

$(document).ready(function() {
	
	$('#servicoDe').on('click', function(){
		montaParametrosAutocompleteServico();
	});
	
	completeServicoBI = $('#servicoDe').autocomplete({ 
		serviceUrl:'pages/autoCompleteServicoBI/autoCompleteServicoBI.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idServicoDe').val(data);
			$('#servicoDe').val(value);
		}
	});

	completeServicoCorporeBI = $('#servicoPara').autocomplete({ 
		serviceUrl:'pages/autoCompleteServicoCorporeBI/autoCompleteServicoCorporeBI.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idServicoPara').val(data);
			$('#servicoPara').val(value);
		}
	});
	
});

function relacionar() {
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent("relacionar");
}

function listar() {
	document.form.fireEvent("carregarListaDePara");
}

function excluirDePara(conexao,idde,idpara){
	if (confirm(i18n_message("deParaCatalogoServicos.removerDePara"))) {
		JANELA_AGUARDE_MENU.show();
		document.form.idConexaoBI.value = conexao;
        document.form.idServicoDe.value = idde;
        document.form.idServicoPara.value = idpara;
        document.form.fireEvent('excluirDePara');
    }
}

function onkeyDownIdDe(evt){
	if (!FormatUtils.bloqueiaNaoNumerico(evt)){
		document.getElementById("servicoDe").value="";
	};	
}

function onkeyDownIdPara(evt){
	if (!FormatUtils.bloqueiaNaoNumerico(evt)){
		document.getElementById("servicoPara").value="";
	};	
}

function enterOnIdDe(evt){
	var kc = evt.keyCode || evt.which;
	if (kc == 13) {
		document.getElementById("idServicoPara").focus();
	}
}

function enterOnIdPara(evt){
	var kc = evt.keyCode || evt.which;
	if (kc == 13) {
		relacionar();
	}
}

$("#idServicoDe").on("blur", function(e){
	document.form.fireEvent("onExitIdDe"); 
});

$("#idServicoPara").on("blur", function(e){
	document.form.fireEvent("onExitIdPara"); 
});