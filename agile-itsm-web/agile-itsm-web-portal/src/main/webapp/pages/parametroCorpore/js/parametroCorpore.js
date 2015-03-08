
			var popup;
		    addEvent(window, "load", load, false);
		    function load(){
				popup = new PopupManager(850, 500, "${ctx}/pages/");
		    }

			exportar =  function(){
			document.form.fireEvent('exportarParametroCsv');
			JANELA_AGUARDE_MENU.show();
			}

			salvar =  function(){
				var pass = document.getElementById("valor");
				if(pass.type == 'password'){
					removeAllEventos()
					limpaElemntoValidacao();
					$('#valor').unmask();
					$('#valor').removeClass();
					removeAllEventos();
				}
				document.form.save();
			}

			function LOOKUP_PARAMETROCORPORE_select(id, desc) {
				document.form.restore({
					id : id
				});
			}

			function limpaCaracteristica(){
				removeAllEventos()
				limpaElemntoValidacao();
				$('#valor').unmask();
				$('#valor').removeClass();
				removeAllEventos();
				 $("#valor").attr('maxlength','200');
			}
			function MudarCampovalorParaTipoSenha(){
				removeAllEventos()
				limpaElemntoValidacao();
				$('#valor').unmask();
				$('#valor').removeClass();
				removeAllEventos();
				var pass = document.getElementById("valor");
				pass.type = 'password';
				//$('#valor').attr('type', 'password');
			}
			function MudarCampovalorParaTipoTexto(){
				removeAllEventos()
				limpaElemntoValidacao();
				$('#valor').unmask();
				$('#valor').removeClass();
				removeAllEventos();
				var pass = document.getElementById("valor");
				pass.type = 'text';
			}
			function limpaElemntoValidacao(){
				var element = document.form.valor;
				var aux = element.validacao;
				if (aux == null || aux == undefined){
					element.validacao = '';
				}else{
					element.validacao = '';
				}

				var aux = element.descricao;
				if (aux == null || aux == undefined){
					element.descricao = '';
				}else{
					element.descricao = '';
				}
			}

		    function setaLingua(){
		    	document.getElementById("pesqLockupLOOKUP_PARAMETROCORPORE_d_idlingua").value = document.formPesquisa.idLingua.value;
		    }

			function mascara(tipo){

				if(tipo == "Date"){
				 	$("#valor").mask("99/99/9999");
				    $("#valor").attr('maxlength','10');

				} else if(tipo == "CPF"){
			 		$("#valor").mask("999.999.999-99");
					$("#valor").attr('maxlength','14');

				} else if(tipo == "Telefone"){
					$("#valor").mask("(99)9999-9999");
					$("#valor").attr('maxlength','13');

				} else if(tipo == "Hora"){
					 $("#valor").mask('99:99');
					$("#valor").attr('maxlength','5');

				} else if(tipo == "Numero"){
					$("#valor").mask("999999999999999999999999999999");
					$("#valor").attr('maxlength','30');

				} else if(tipo == "CNPJ"){
				 	$("#valor").mask("99.999.999/9999-99");
					$("#valor").attr('maxlength','17');
				} else if(tipo == "CEP"){
				 	$("#valor").mask("99.999-999");
					$("#valor").attr('maxlength','10');
				}
			}

			function removeAllEventos(){
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataMoedaSaidaCampo, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataDecimalSaidaCampo, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataNumero, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataData, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataHora, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataCNPJ, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataCPF, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataMoeda, false);

				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataMoedaSaidaCampo, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataDecimalSaidaCampo, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataNumero, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataData, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataHora, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataCNPJ, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataCPF, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataMoeda, false);
			}

		
