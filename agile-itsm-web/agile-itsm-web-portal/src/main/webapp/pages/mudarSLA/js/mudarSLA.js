/**
 * 
 */

		gravar = function() {
			if (document.form.slaACombinar.value == 'N'){
				if (StringUtils.isBlank(document.form.prazoHH.value) && StringUtils.isBlank(document.form.prazoMM.value)){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.prazoHH.focus();
					return;
				}
				if (document.form.prazoHH.value == '0' && document.form.prazoMM.value == '0'){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.prazoHH.focus();
					return;
				}
				if (document.form.prazoHH.value == '0' && StringUtils.isBlank(document.form.prazoMM.value)){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.prazoHH.focus();
					return;
				}
				if (document.form.idJustificativa.value == '') {
					alert(i18n_message("citcorpore.comum.justificativa") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
					document.form.idJustificativa.focus();
					return;
				}
				if (StringUtils.isBlank(document.form.prazoHH.value) && document.form.prazoMM.value == '0'){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.prazoHH.focus();
					return;
				}
				if (StringUtils.isBlank(document.form.idCalendario.value)){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.idCalendario.focus();
					return;
				}
			}
			if (!document.form.validate()){
				return;
			}
			if (document.form.idJustificativa.value == '') {
				alert(i18n_message("citcorpore.comum.justificativa") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
				document.form.idJustificativa.focus();
				return;
			}
			if (confirm(i18n_message("gerenciaservico.mudarsla.confirm.mudanca")))
				document.form.save();
		}

		function mudarTipoSLA(obj){
			if (obj.value == 'S'){
			 	document.getElementById('tempo').style.display = 'none';
				document.getElementById('calendario').style.display = 'none';
				/*$('#tempo').switchClass( "inativo", "ativo", null );
				$('#calendario').switchClass( "inativo", "ativo", null );*/
			}else{
				document.getElementById('tempo').style.display = 'block';
				document.getElementById('calendario').style.display = 'block';
				/*$('#tempo').switchClass( "ativo", "inativo", null );
				$('#calendario').switchClass( "ativo", "inativo", null );*/
			}
		}
		function verificaMudarTipoSLA(){
			mudarTipoSLA(document.form.slaACombinar);
		}

		function somenteNumero(e){
		    var tecla=event.keyCode || e.which;
		    if((tecla>47 && tecla<58)) return true;
		    else{
		    	if (tecla==8 || tecla==0) return true;
			else  return false;
		    }
		}