

	var objTab = null;

	addEvent(window, "load", load, false);

	function load() {

		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}

		/*
		* Tratamento para escutar o evento submit
		* que é disparado quando o usuário dá enter no campo
		* e então salvar
		*/
		$('#form').submit(function (e) {

			e.preventDefault();

			document.form.save();
		});

		/*
		* Foco no primeiro campo
		*/
		document.form.descricao.focus();
	}

	function limpar() {

	   	limparForm();

	   	limpar_LOOKUP_IDIOMA();
	}

	function limparForm() {

		document.form.clear();

		document.form.descricao.focus();
	}

	function LOOKUP_IDIOMA_select(id, desc) {

		document.form.restore({
			idIdioma : id
		});
	}

	function excluir() {

		if (document.getElementById("idIdioma").value != "") {

			if (confirm(i18n_message("citcorpore.comum.deleta"))) {

				document.form.fireEvent("delete");
			}
		}
	}


