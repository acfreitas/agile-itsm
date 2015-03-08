
		$(function() {
			$("#POPUP_MIDIASOFTWARE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			$('#duplicado').change(function() {
			  if (!$(this).is(':checked')) {
				  $('#contentChaves').hide();
				  $('#softwaresL').attr('disabled', false);
				  document.form.fireEvent("listaChavesMidiaSoftware");
			  }else{
				  $('#contentChaves').show();
				  $('#softwaresL').attr('disabled', true);
			  }
			});

		});

		function LOOKUP_MIDIASOFTWARE_select(id, desc) {
			document.form.idMidiaSoftware.value = id;
			document.getElementById("nomeMidia").value = desc;
			document.form.fireEvent("listaChavesMidiaSoftware");
			 $('#duplicados').show();
			 $('#softwares').show();
			$("#POPUP_MIDIASOFTWARE").dialog("close");
		}

		function abrePopupMidia(){
			$("#POPUP_MIDIASOFTWARE").dialog("open");
		}

		function imprimirRelatorioPacoteOffice(){
			serializa();
			if(document.form.midiaSoftwareChavesSerealizadas.value == '' && confirm(i18n_message("relatorio.office.desejaListarTodos"))){
				document.form.fireEvent("imprimirRelatorioPacoteOffice");
			}else {
				document.form.fireEvent("imprimirRelatorioPacoteOffice");
			}
		}

		function imprimirRelatorioPacoteOfficeXls(){
			serializa();
			if(document.form.midiaSoftwareChavesSerealizadas.value == '' && confirm(i18n_message("relatorio.office.desejaListarTodos"))){
				document.form.fireEvent("imprimirRelatorioPacoteOfficeXls");
			}else {
				document.form.fireEvent("imprimirRelatorioPacoteOfficeXls");
			}
		}

		serializa = function() {
			try {
				var tabela = document.getElementById('tblMidiaSoftwareChave');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var baselines = new Array();
				for ( var i = 0; i < count; i++) {
					var trObj = document.getElementById('idMidiaSoftwareChave' + i);
					if (!trObj)
						continue;
					else if(trObj.checked){
						baselines[contadorAux] = get(i);
						contadorAux = contadorAux + 1;
						continue;
					}
				}
				var objs = ObjectUtils.serializeObjects(baselines);
				document.form.midiaSoftwareChavesSerealizadas.value = objs;
				return true;
			}catch(e){

			}
		}

		get = function(seq) {
			var midiaSoftwareChaveDTO = new CIT_MidiaSoftwareChaveDTO();
			midiaSoftwareChaveDTO.sequencia = seq;
			midiaSoftwareChaveDTO.idMidiaSoftwareChave = eval('document.form.idMidiaSoftwareChave' + seq + '.value');
			midiaSoftwareChaveDTO.chave = eval('document.form.chave' + seq + '.value');
			return midiaSoftwareChaveDTO;
		}

		function limpar() {
			$("#contentChaves").text("");
			$("#nomeMidia").val("");
			$("#midiaSoftwareChavesSerealizadas").val("");
			$('#duplicados').hide();
			$('#softwares').hide();
		}

	
