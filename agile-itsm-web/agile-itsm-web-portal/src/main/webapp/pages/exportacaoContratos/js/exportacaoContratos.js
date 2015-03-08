function exportar() {
	if (confirm(i18n_message("citcorpore.comum.confirmaexportacao"))) {
		$("#export").attr("value", "y");
		submitForm("form");
	}
}

function submitForm (f) {
	$("form[name=" + f + "]").submit();
	//document.f.submit();
}

//Atualiza informações da página de acordo com o contrato selecionado.
$("#idContrato").on("change", function() {
	var idContrato = $(this).attr("value");
	
	if (idContrato != "" && typeof idContrato != "undefined") {
		var v = $("input[name='exportarAcordoNivelServico']:checked").val();
		if (v == "y") {
			$("input[name='exportarCatalogoServico'][value='y']").attr("checked", true);
			$("input[name='exportarCatalogoServico']").attr("disabled", true);
		} else {
			$("input[name='exportarCatalogoServico'][value='n']").attr("checked", true);
			$("input[name='exportarCatalogoServico']").attr("disabled", false);		
		}
	} else {
		$("input[name='exportarCatalogoServico'][value='n']").attr("checked", true);
		$("input[name='exportarCatalogoServico']").attr("disabled", false);
	}
	
	document.form.fireEvent("atualizaGrupos");
});

$("input[name='exportarAcordoNivelServico']").change(function() {
	var idContrato = $("#idContrato").attr("value");
	
	if (idContrato != "" && typeof idContrato != "undefined") {
		var v = $("input[name='exportarAcordoNivelServico']:checked").val();
		if (v == "y") {
			$("input[name='exportarCatalogoServico'][value='y']").attr("checked", true);
			$("input[name='exportarCatalogoServico']").attr("disabled", true);
		} else {
			$("input[name='exportarCatalogoServico'][value='n']").attr("checked", true);
			$("input[name='exportarCatalogoServico']").attr("disabled", false);		
		}
	} else {
		return false;
	}
});

$("#selectTodosGrupos").live("change", function() {
	if ($(this).is(':checked')) {
		$("input[name='idGrupos']").attr("checked", true);
	} else {
		$("input[name='idGrupos']").attr("checked", false);
	}
});



