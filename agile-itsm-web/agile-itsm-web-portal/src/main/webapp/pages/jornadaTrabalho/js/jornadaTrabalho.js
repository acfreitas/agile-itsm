
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);

		}
	}
	function excluir() {
		var idJornadaTrabalho = document.getElementById("idJornada");
		if (idJornadaTrabalho != null && idJornadaTrabalho.value == 0) {
			alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
			return false;
		}
		if (confirm(i18n_message("citcorpore.comum.deleta")))
			document.form.fireEvent("delete");
	}
	function LOOKUP_JORNADATRABALHO_select(id, desc) {
		document.form.restore({
			idJornada : id
		});
	}

	isValidTime = function(objeto){
		 var hora = objeto.value;
	     if(hora == null || hora.length == 0){
	         return 1;
	     }
	     if(hora.length != 5){
	    	 alert(i18n_message("jornadaTrabalho.formatoHoraInvalido"));
	    	 objeto.focus();
	         return -1;
	     }
	     var h  = hora.substring(0,2);
	     var m  = hora.substring(3,5);
	     if(h > 23 || h < 0){
	    	 alert(i18n_message("jornadaTrabalho.horaInvalida"));
	    	 objeto.focus();
	    	 return -2;
	     }
	     if(m>59 || m<0){
	    	 alert(i18n_message("jornadaTrabalho.minutoInvalido"));
	    	 objeto.focus();
	    	 return -3;
	     }
	     return 1;
	}

	function gravar(){
		inicio1 = document.getElementById("inicio1");

		var ok =isValidTime(inicio1);
		if (ok > 0){
			termino1 = document.getElementById("termino1");
			ok=isValidTime(termino1);
		}
		if (ok>0){
			inicio2 = document.getElementById("inicio2");
			ok=isValidTime(inicio2);
		}
		if (ok>0){
			termino2 = document.getElementById("termino2");
			ok=isValidTime(termino2);
		}
		if (ok>0){
			inicio3 = document.getElementById("inicio3");
			ok=isValidTime(inicio3);
		}
		if (ok>0){
			termino3 = document.getElementById("termino3");
			ok=isValidTime(termino3);
		}
		if (ok>0){
			inicio4 = document.getElementById("inicio4");
			ok=isValidTime(inicio4);
		}
		if (ok>0){
			termino4 = document.getElementById("termino4");
			ok=isValidTime(termino4);
		}
		if (ok>0){
			inicio5 = document.getElementById("inicio5");
			ok=isValidTime(inicio5);
		}
		if (ok>0){
			termino5 = document.getElementById("termino5");
			ok=isValidTime(termino5);
		}
		if (ok>0){
			document.form.save();
		}
	}


