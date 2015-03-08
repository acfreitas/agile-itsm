
	var objTab = null;
	var descricaoOuQueryAlterada = false;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}

		$("#POPUP_RESULTADO_CONSULTA").dialog({
			closeOnEscape: false,
			title: i18n_message('scripts.resultadoConsulta'),
			autoOpen : false,
			width : 900,
			height : 600,
			modal : true
		});

		$("#POPUP_MENSAGEM_FALTA_PERMISSAO").dialog({
			title: i18n_message('mensagem'),
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

	}

	function LOOKUP_SCRIPTS_select(id, desc) {
		document.form.restore({
			idScript :id});
	}

	function excluir() {
		if (document.getElementById("idScript").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	function validaAtualizacao() {
		if(confirm(i18n_message("scripts.validaAtualizacao"))){
			document.form.fireEvent("validaAtualizacao");
		}
	}

	function encaminhaParaIndex() {
		document.form.submit();
		window.location = ctx+"/pages/index/index.load?mensagem=citcorpore.comum.validacaoSucesso";
	}

	function orientacaoTecnicaPopUp() {
		$('#POPUP_ORIENTACAO_TECNICA').dialog('open');
	}

	function executar() {
		if (document.getElementById("tipo").value != "consulta"
				&& (document.getElementById("idScript").value == ""
					|| descricaoOuQueryAlterada)) {
			alert(i18n_message("scripts.favorGravarScript"));
		} else if (document.getElementById("tipo").value == "consulta"
				|| document.getElementById("historico").value == ""
				|| confirm(i18n_message("scripts.scriptJaFoiExecutado"))) {
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("executar");
		}
	}

	function downloadDocumento(sel) {
		var value = sel.options[sel.selectedIndex].value;
		window.open(value, '_blank')
	}

