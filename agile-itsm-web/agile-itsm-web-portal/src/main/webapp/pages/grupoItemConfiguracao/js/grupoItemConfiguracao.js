
	var objTab = null;

	addEvent(window, "load", load, false);

	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function excluir() {
		if (document.getElementById("idGrupoItemConfiguracao").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.fireEvent("delete");
			}
		}

	}

	function gravar() {
		if (document.getElementById("emailGrupoItemConfiguracao").value == ''
				|| document.getElementById("nomeGrupoItemConfiguracao").value == '') {
			alert(i18n_message("citcorpore.comum.camposObrigatorios"));
			return;
		}
		if (ValidacaoUtils.validaEmail(document.getElementById("emailGrupoItemConfiguracao"), '') == false) {
			return;
		} else {
			document.form.fireEvent("save");
		}
	}

	function LOOKUP_GRUPOITEMCONFIGURACAO_select(id, desc) {
		document.form.restore({
			idGrupoItemConfiguracao : id
		});
	}

	function limpar() {
		document.form.clear();
	}

