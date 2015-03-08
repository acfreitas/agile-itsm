	var objTab = null;

	
	$(window).load( function() {
		
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);

		}
		
	});
	

	function LOOKUP_EMPRESA_select(id, desc) {
		document.form.restore({
			idEmpresa : id
		});
	}

	function atualizaData() {
		var idEmpresa = document.getElementById("idEmpresa");

		if (idEmpresa != null && idEmpresa.value == 0) {
			alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
			return false;
		}
		if (confirm(i18n_message("citcorpore.comum.deleta")))
			document.form.fireEvent("atualizaData");
	}