
	var contador = 60;
	addEvent(window, "load", load, false);
	function load() {
		window.setTimeout('geraInformacoesMonitoramento()',1000);
	}
	function mostraHostsGrupo(id, text){
		document.form.idGrupoRecurso.value = id;
		document.form.nomeGrupoRecurso.value = text;
		document.getElementById('divDetalhamento').innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
		document.form.fireEvent('mostraHostsGrupo');
	}
	function geraInformacoesMonitoramento(){
		if (contador < 0){
			contador = 61;
			document.form.fireEvent('geraInformacoesMonitoramento');
			document.getElementById('divDetalhamento').innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
			document.form.fireEvent('mostraHostsGrupo');
		}else{
			document.getElementById('timeRefresh').innerHTML = contador;
		}
		contador--;
		window.setTimeout('geraInformacoesMonitoramento()',1000);
	}

