
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		/* Desenvolvedor: Pedro Lino - Data: 30/10/2013 - Horário: 11:00 - ID Citsmart: 120948 -
		* Motivo/Comentário: Lookup pequena/ Alterado width e height */
		$("#POPUP_FORNECEDOR").dialog({
			autoOpen : false,
			width : 720,
			height : 600,
			modal : true
		});

		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_MARCA_select(id, desc) {
		document.form.restore({
			idMarca : id
		});
	}

	function excluir() {
		if (document.getElementById("idMarca").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	function abrePopupFornecedor(){
		$("#POPUP_FORNECEDOR").dialog("open");
	}

	function LOOKUP_FORNECEDOR_select(id, desc){
		document.getElementById("idFabricante").value = id;
		document.getElementById("nomeFabricante").value = desc;
		$("#POPUP_FORNECEDOR").dialog("close");
	}



