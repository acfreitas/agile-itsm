	 var popup;
	 var popup2;
	 var popup3;
	    addEvent(window, "load", load, false);
	    function load(){
			popup = new PopupManager(1000, 600, ctx + "/pages/");
			popup2 = new PopupManager(1000, 600, ctx + "/pages/");
			popup3 = new PopupManager(1000, 600, ctx + "/pages/");
			document.form.afterRestore = function () {
				$('.tabs').tabs('select', 0);
			}
	    }

		function verificaValor(obj){
			if (obj.options[obj.selectedIndex].value == 'N'){
				$( "#labelUnidade" ).removeClass( "campoObrigatorio" );
				$( "#labelCargos" ).removeClass( "campoObrigatorio" );
			}else{
				$( "#labelUnidade" ).addClass( "campoObrigatorio" );
				$( "#labelCargos" ).addClass( "campoObrigatorio" );
			}

			if (obj.options[obj.selectedIndex].value == 'E'){
				$('valorSalario').innerHTML = 'Valor Sal&atilde;rio CLT:';
			}else if (obj.options[obj.selectedIndex].value == 'S'){
				$('valorSalario').innerHTML = 'Valor Est&atilde;gio:';
			}else if (obj.options[obj.selectedIndex].value == 'P'){
				$('valorSalario').innerHTML = 'Valor Contratado Mensal:';
			}else if (obj.options[obj.selectedIndex].value == 'X'){
				$('valorSalario').innerHTML = 'Valor do Prolabore:';
			}else{
				$('valorSalario').innerHTML = 'Valor Mensal:';
			}
		}

		function LOOKUP_EMPREGADO_select(id,desc){
			document.form.restore({idEmpregado:id});
		}

		$(function() {
			   $('.datepicker').datepicker();
			   $('#telefone').mask('(999) 9999-9999');
		  });

		function ocultarDivGruposContrato(){
			$('#gruposContrato').hide();
		}

		function exibirDivGruposContrato(){
			$('#gruposContrato').show();
		}

	function excluir() {
		if (document.getElementById("idEmpregado").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	function gravar(){
		var validaEmail = ValidacaoUtils.validaEmail(document.getElementById('email'), '');

		if(validaEmail==false){
			return;
		}
		if(!validaDatas()){
			return;
		}

		if ($('#tipo option:selected').val() != 'N'){
			if($( "#idUnidade" ).val() == null || $( "#idUnidade" ).val() == ""){
				alert("Unidade: Campo Obrigatório");
				return;
			}
			if($( "#idCargo" ).val() == null || $( "#idCargo" ).val() == ""){
				alert("Cargo: Campo Obrigatório");
				return;
			}
		}

		document.form.save();

	}

	function validaDatas(){
		if(!nullOrEmpty(gebi("dataNasc"))){
			if(!nullOrEmpty(gebi("dataEmissaoRg"))){
				if (!DateTimeUtil.comparaDatas(document.form.dataNasc, document.form.dataEmissaoRg, i18n_message("citcorpore.comum.validacao.datargmenordatanasc"))){
					return false;
				}
			}
			if(!nullOrEmpty(gebi("dataEm"))){
				if (!DateTimeUtil.comparaDatas(document.form.dataNasc, document.form.dataEm, i18n_message("citcorpore.comum.validacao.emissaoctpsmenornasci"))){
					return false;
				}
			}
			if(!nullOrEmpty(gebi("dataAdmissao"))){
				if (!DateTimeUtil.comparaDatas(document.form.dataNasc, document.form.dataAdmissao, i18n_message("citcorpore.comum.validacao.admissaomenornascimento"))){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * getElementById
	 */
	function gebi(id){
		return document.getElementById(id);
	}

	function nullOrEmpty(comp){
		return comp.value == null || comp.value == "" ? true : false;
	}

	//<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Criação de função para não digitar numeros, para retirar bug de não poder usar setas do teclado . -->
	function naoDigitarNumeros(e){
		 var tecla= event.keyCode || e.which;
		 if((tecla>47 && tecla<58))
			 return false;
		 else{
			 if (tecla==8 || tecla==0) return true;
			 	else
			 		return true;
		 }
	}