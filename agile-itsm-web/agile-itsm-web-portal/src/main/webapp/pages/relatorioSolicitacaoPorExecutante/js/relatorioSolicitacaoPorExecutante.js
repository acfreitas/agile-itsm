
			var temporizador;
			
			$(window).load(function() {

				$("#POPUP_RESPONSAVEL").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
				
			});			


			function LOOKUP_RESPONSAVEL_select(id, desc){
				document.getElementById("idResponsavelAtual").value = id;
				document.getElementById("nomeResponsavelAtual").value = desc;
				$("#POPUP_RESPONSAVEL").dialog("close");
			}

			function abrePopupResponsavel(){
				$("#POPUP_RESPONSAVEL").dialog("open");
			}

	
