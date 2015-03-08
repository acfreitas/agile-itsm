/**
 * 
 */


		jQuery(function($){
			   $("#horaInicio").mask("99:99");
			   $('.datepicker').datepicker();
			});

		gravar = function() {


			hrs = (document.form.horaInicio.value.substring(0,2));
			min = (document.form.horaInicio.value.substring(3,5));
			estado = "";
			if ((hrs < 00 ) || (hrs > 23) || ( min < 00) ||( min > 59)){
				estado = "errada";
			}

			if (document.form.horaInicio.value == "") {
				estado = "errada";
			}
			if (StringUtils.isBlank(document.form.idGrupoAtvPeriodica.value)){
				alert(i18n_message("gerenciaservico.agendaratividade.valida.grupo"));
				document.form.idGrupoAtvPeriodica.focus();
				return;
			}
			if (StringUtils.isBlank(document.form.dataInicio.value)){
				alert(i18n_message("gerenciaservico.agendaratividade.valida.dataagendamento"));
				document.form.dataInicio.focus();
				return;
			}
			if (!DateTimeUtil.isValidDate(document.form.dataInicio.value)) {
				alert(i18n_message("citcorpore.validacao.dataInvalida"));
				return;
			}
			if (estado == "errada") {
				alert(i18n_message("citcorpore.validacao.horaInvalida"));
				document.form.horaInicio.focus();
				return;
			}
			if (StringUtils.isBlank(document.form.horaInicio.value)){
				alert(i18n_message("gerenciaservico.agendaratividade.valida.hora"));
				document.form.horaInicio.focus();
				return;
			}
			if (StringUtils.isBlank(document.form.duracaoEstimada.value)){
				alert(i18n_message("gerenciaservico.agendaratividade.valida.duracao"));
				document.form.duracaoEstimada.focus();
				return;
			}
			if (document.form.duracaoEstimada.value == '0' || document.form.duracaoEstimada.value == '00' || document.form.duracaoEstimada.value == '000'){
				alert(i18n_message("gerenciaservico.agendaratividade.valida.duracao"));
				document.form.duracaoEstimada.focus();
				return;
			}
			if (confirm(i18n_message("gerenciaservico.agendaratividade.confirm.agendamento")))
				document.form.save();
			}


		function somenteNumero(e){
		    var tecla=(window.event)?event.keyCode:e.which;
		    if((tecla>47 && tecla<58)) return true;
		    else{
		    	if (tecla==8 || tecla==0) return true;
			else  return false;
		    }
		}

		function limpar(){
			document.form.idGrupoAtvPeriodica.value = '';
			document.form.dataInicio.value = '';
			document.form.horaInicio.value = '';
			document.form.duracaoEstimada.value = '';
			//Limpar TextArea HTML5
			$("#orientacaoTecnica").data("wysihtml5").editor.setValue('');
		}
