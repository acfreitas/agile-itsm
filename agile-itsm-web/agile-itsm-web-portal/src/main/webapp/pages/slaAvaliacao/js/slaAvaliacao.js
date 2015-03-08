

var objTab = null;
addEvent(window, "load", load, false);
function load(){
	document.form.afterRestore = function () {
		$('.tabs').tabs('select', 0);
	}
}

function LOOKUP_SISTEMAOPERACIONAL_select(id,desc){
	document.form.restore({id:id});
}

	function gerarInformacoes(){
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		if(DateTimeUtil.isValidDate(dataInicio) == false){
			alert(i18n_message("citcorpore.comum.datainvalida"));
		 	document.getElementById("dataInicio").value = '';
		 	return false;
		}
		if(DateTimeUtil.isValidDate(dataFim) == false){
			alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
			 document.getElementById("dataFim").value = '';
			return false;
		}
		if(validaData(dataInicio,dataFim)){
			document.getElementById('divInfo').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';
			document.form.fireEvent('avalia');
		}
	}

	function validaData(dataInicio, dataFim) {
		if (typeof(locale) === "undefined") locale = '';

		var dtInicio = new Date();
		var dtFim = new Date();

		var dtInicioConvert = '';
		var dtFimConvert = '';
		var dtInicioSplit = dataInicio.split("/");
		var dtFimSplit = dataFim.split("/");

		if (locale == 'en') {
			dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
			dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
		} else {
			dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
			dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
		}

		dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
		dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

		if (dtInicio > dtFim){
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			return false;
		}else
			return true;
	}

