	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_PRODUTO_select(id, desc) {
		document.form.restore({
			idProduto : id
		});
	}

	function excluir() {
		if (document.getElementById("idProduto").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	 var popup;

	$(function() {

		if (iframe == 'true') {
			popup = new PopupManager(580, 680, ctx+"/pages/");
		} else {
			popup = new PopupManager(1100, 800, ctx+"/pages/");
		}


		$("#POPUP_MARCA").dialog({
			autoOpen : false,
			width : 500,
			height : 400,
			modal : true
		});

		$("#POPUP_TIPOPRODUTO").dialog({
			autoOpen : false,
			width : 500,
			height : 400,
			modal : true
		});


		$("#nomeMarca").click(function() {
			$("#POPUP_MARCA").dialog("open");
		});

		$("#nomeProduto").click(function() {
			$("#POPUP_TIPOPRODUTO").dialog("open");
		});

	});


	function LOOKUP_MARCA_select(id, desc){
		document.form.idMarca.value = id;
		document.form.nomeMarca.value  = desc;
		fecharNomeMarca();
	}

	function LOOKUP_TIPOPRODUTO_select(id, desc){
		document.form.idTipoProduto.value = id;
		document.form.nomeProduto.value  = desc;
		fecharTipoProduto();
	}


	function fecharNomeMarca(){
		$("#POPUP_MARCA").dialog('close');
	}

	function fecharTipoProduto(){
		$("#POPUP_TIPOPRODUTO").dialog('close');
	}

	function limparForm(){
		document.getElementById('file_uploadAnexos').value = '';
		document.form.fireEvent("clear");
	}

	function chamaPopupCadastroTipoProduto(){
		popup.abrePopupParms('tipoProduto', '', '');
	}