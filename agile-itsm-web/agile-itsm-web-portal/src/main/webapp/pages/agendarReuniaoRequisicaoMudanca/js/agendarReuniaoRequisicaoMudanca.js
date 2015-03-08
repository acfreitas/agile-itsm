/**
 * 
 */

	fechar = function(){
		parent.fecharReuniao();
	}

	gravar = function() {
		if (StringUtils.isBlank(document.form.localReuniao.value)){
			alert(i18n_message("gerenciaservico.agendaratividade.valida.local"));
			document.form.localReuniao.focus();
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
		validarDatas();

		if (confirm(i18n_message("gerenciaservico.agendaratividade.confirm.agendamento")))

			document.form.fireEvent("validaHorarioESalva");
	}

	function excluiReuniao(idReuniao){
		if (confirm(i18n_message("gerenciaservico.agendaratividade.cancela.agendamento"))) {
			document.getElementById('idReuniaoRequisicaoMudanca').value = idReuniao;
			document.form.fireEvent("delete");
		}
	}

	function validarDatas(){
		var inputs = document.getElementsByClassName("datepicker");
		var input = null;
		var errorMsg = i18n_message("citcorpore.comum.nenhumaDataDeveSerInferiorHoje") ;

		for(var i = 0; i < inputs.length; i++){
			input = inputs[i];

			if(input == null){
				continue;
			}

			if(comparaComDataAtual(input) < 0){
				alert(errorMsg);
				input.focus();
				throw errorMsg;
			}
		}
	}

	/*Thiago Fernandes - 29/10/2013 - 17:33 - Sol. 121468 - Assim que for adicionado um agendamento a popup não deve ser fechada, ela deve ser apenas recarregada. Função para limpar campos ao cadastrar um novo agendamento*/
	function limparCampos() {
		document.getElementById('localReuniao').value = '';
		document.getElementById('descricao').value = '';
		document.getElementById('dataInicio').value = '';
		document.getElementById('horaInicio').value = '';
		document.getElementById('duracaoEstimada').value = '';
	}

