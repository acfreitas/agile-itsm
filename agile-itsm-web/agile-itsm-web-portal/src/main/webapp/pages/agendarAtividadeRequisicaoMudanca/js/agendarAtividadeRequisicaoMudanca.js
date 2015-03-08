/**
 * 
 */

	fechar = function(){
		parent.fecharVisao();
	}

	gravar = function() {
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
		if (!document.form.validate()){
			return;
		}
		if (confirm(i18n_message("gerenciaservico.agendaratividade.confirm.agendamento")))
			document.form.save();
	}