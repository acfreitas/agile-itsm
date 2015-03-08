
	$(window).load( function() {
		
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
		
		
	});


	function tree(id) {

		$(id).treeview();

	}

	function excluir() {
		if (document.getElementById("idPerfilAcesso").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.acessoMenuSerializados.value = adicionaMenus();
				document.form.fireEvent("delete");
			}
		}
	}

	function acessarSistemaCitsmart(){
		if (confirm(i18n_message("perfilAcesso.desablitarAcessoSistema"))){

			desmarcarCheckbox();

			if ($("#idPerfilAcesso").val() != null && $("#idPerfilAcesso").val() != ""){
				document.form.fireEvent("verificarGruposPerfilAcesso");
			}
		}

	}

	function desmarcarCheckbox(){

		$("#corpoInf").find("input[type='checkbox']").each(function(i, el){
			$(el).attr('checked',false)
		});

	}

	function LOOKUP_PERFILACESSO_select(id, desc) {
		JANELA_AGUARDE_MENU.show();
		document.form.restore({idPerfilAcesso : id});
	}

	function marcarTodosCheckbox(id) {

		var classe = $(id).attr("class");
		var x = classe.split(" ");
		if(x[1] != null){
			classe = x[x.length - 2];
		}

		var valor = "";
		if(!$(id).is(':checked')){

			$("." + classe).each(function() {
				$(this).attr("checked", false);
			});
		}else{
			$("." + classe).each(function() {
					$(this).attr("checked", true);
			});
		}
	}

		function checkboxPesquisar(id) {
			var idPesquisa = "pesq_" + id;
			$("#" + idPesquisa).attr("checked", true);
		}

		function checkboxIncluir(id) {
			var idInclui = "inc_" + id;
			$("#" + idInclui).attr("checked", true);
		}

		function checkboxGravar(id) {
			var idGravar = "gravar_" + id;
			$("#" + idGravar).attr("checked", true);
		}

		function checkboxDeletar(id) {
			var idDeleta = "del_" + id;
			$("#" + idDeleta).attr("checked", true);
		}

		adicionaMenus = function() {
			var id = "";
			var check = "";
			var lista = "";
			var tipo = "";
			var i = 0;
			var x = 0;
			var array = new Array();
			$("input[name='menu']").each(function() {
				id = $(this).val();
				if ($(this).is(':checked')) {
					tipo = "S";
				} else {
					tipo = "N";
				}
				check += tipo + "-";
				if (i == 2) {
					lista += id + "@" + check + ";";
					i = -1;
					check = "";
				}
				i++;
				x++;
			});
			return lista;
		}

		function gravar() {

			if (document.getElementById('acessoSistemaCitsmartN').checked){
				desmarcarCheckbox();
			}
			document.form.acessoMenuSerializados.value = adicionaMenus();
			document.form.save();
		}