/**
 * 
 */

var objTab = null;

$(window).load(function(){
	
	document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);
	}	
	
	document.form.afterRestore = function() {
		$('.tabsbar li:eq(0) a').tab('show');
	}
	
});


function LOOKUP_ORIGEMATENDIMENTO_select(id, desc) {
	document.form.restore({
		idOrigem : id
	});
}

function excluir() {
	if (document.getElementById("idOrigem").value != "") {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			chamaFuncaoPreencherCombo();
			document.form.fireEvent("delete");
		}
	}
}


function chamaFuncaoPreencherCombo() {
	if(typeof parent.preencherComboOrigem === 'function')
		parent.preencherComboOrigem();
}

function savarDados() {
	if(document.form.descricao.value!=''){
		document.form.save();
	} else{
		document.form.descricao.focus();
		alert(i18n_message("origemAtendimento.nome.campoObrigatorio"));
	}
}



