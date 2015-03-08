	addEvent(window, "load", load, false);
			
			/*ERRO com a tela nova de login
			 * 
			 * popupAlteracaoSenha = new PopupManager(1200, 300, '../../pages/');*/
			
	var BrowserDetect = {
			init: function () {
				this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
				this.version = this.searchVersion(navigator.userAgent)
					|| this.searchVersion(navigator.appVersion)
					|| "an unknown version";
				this.OS = this.searchString(this.dataOS) || "an unknown OS";
			},
			searchString: function (data) {
				for (var i=0;i<data.length;i++)	{
					var dataString = data[i].string;
					var dataProp = data[i].prop;
					this.versionSearchString = data[i].versionSearch || data[i].identity;
					if (dataString) {
						if (dataString.indexOf(data[i].subString) != -1)
							return data[i].identity;
					}
					else if (dataProp)
						return data[i].identity;
				}
			},
			searchVersion: function (dataString) {
				var index = dataString.indexOf(this.versionSearchString);
				if (index == -1) return;
				return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
			},
			dataBrowser: [
				{
					string: navigator.userAgent,
					subString: "Chrome",
					identity: "Chrome"
				},
				{ 	string: navigator.userAgent,
					subString: "OmniWeb",
					versionSearch: "OmniWeb/",
					identity: "OmniWeb"
				},
				{
					string: navigator.vendor,
					subString: "Apple",
					identity: "Safari",
					versionSearch: "Version"
				},
				{
					prop: window.opera,
					identity: "Opera",
					versionSearch: "Version"
				},
				{
					string: navigator.vendor,
					subString: "iCab",
					identity: "iCab"
				},
				{
					string: navigator.vendor,
					subString: "KDE",
					identity: "Konqueror"
				},
				{
					string: navigator.userAgent,
					subString: "Firefox",
					identity: "Firefox"
				},
				{
					string: navigator.vendor,
					subString: "Camino",
					identity: "Camino"
				},
				{		// for newer Netscapes (6+)
					string: navigator.userAgent,
					subString: "Netscape",
					identity: "Netscape"
				},
				{
					string: navigator.userAgent,
					subString: "MSIE",
					identity: "Explorer",
					versionSearch: "MSIE"
				},
				{
					string: navigator.userAgent,
					subString: "Gecko",
					identity: "Mozilla",
					versionSearch: "rv"
				},
				{ 		// for older Netscapes (4-)
					string: navigator.userAgent,
					subString: "Mozilla",
					identity: "Netscape",
					versionSearch: "Mozilla"
				}
			],
			dataOS : [
				{
					string: navigator.platform,
					subString: "Win",
					identity: "Windows"
				},
				{
					string: navigator.platform,
					subString: "Mac",
					identity: "Mac"
				},
				{
					   string: navigator.userAgent,
					   subString: "iPhone",
					   identity: "iPhone/iPod"
			    },
				{
					string: navigator.platform,
					subString: "Linux",
					identity: "Linux"
				}
			]

		};
	    var versao = 0;
	
	function validaBrowSer(){
		if (BrowserDetect.browser=="Explorer") {
			if (versao<10){
				$("#mensagemNavegadorIe").dialog('open');
				/*alert("Navegador desatualizado. Por favor utilize Internet Explorer 10");*/
				fechar_aguarde();
				return false;
			}
		} 
		/*else {
			if (BrowserDetect.browser=="Firefox") {
				if (versao<25){
					alert("Navegador desatualizado. Por favor utilize Firefox 25");
					fechar_aguarde();
					return false;
				}
			} else {
				if (BrowserDetect.browser=="Chrome") {
					if (versao<30){
						alert("Navegador desatualizado. Por favor utilize Google Chrome 30");
						fechar_aguarde();
						return false;
					}
				}
			}
		}*/
		return true;
	}
	
	
	function load() {
				
				if (window.location != window.top.location) {
			    	window.top.location = window.location;
				}
				
				/*Validando versão do Browser*/
				BrowserDetect.init();
				versao =  parseInt(BrowserDetect.version, 10);
				
				validaBrowSer();
				
				document.form.user.focus();				
				
				document.getElementById("user").focus();				
				
				$("#user").focus();
			
	}
			
			/*ERRO com a tela nova de login
			 * 
			 * function abrirPopupAlteracaoSenha() {
				popupAlteracaoSenha.abrePopupParms("alteracaoSenha", "");
			}
			*/
			validar = function() {
			
				JANELA_AGUARDE_MENU.show();
			
				document.form.save();
			
				$("#user").focus();
			}
		
			aguarde = function() {
				JANELA_AGUARDE_MENU.show();
			}
			
			fechar_aguarde = function() {
		    	JANELA_AGUARDE_MENU.hide();
			}
		
			$(document).ready(function() {
				
				$('.abalinguas').click(function() {
					
					if ($("#lang").is('.hide')) {
						$("#lang").removeClass('hide').addClass('show');
					} else {
						$("#lang").removeClass('show').addClass('hide');
					}
				});
				
				$('#gbg4').click(function() {
					if ($("#gbd4").is('.visibilityFalse')) {						
						$('#gbd4').removeClass('visibilityFalse').addClass('visibilityTrue');
					} else { 
						$('#gbd4').removeClass('visibilityTrue').addClass('visibilityFalse');
					}
				});
				
				function hidden(){
					$('#gbd4').removeClass('visibilityTrue').addClass('visibilityFalse')
				}
				
				$('body').click(function(e){
				   if(!$(e.target).hasClass('TRUE')) {
					   $('#gbd4').removeClass('visibilityTrue').addClass('visibilityFalse');
				   }
				});
			
				var altura = $(window).height() - 140;
				$("#main_container").css("height", altura);
				
			});
		
			function internacionalizar(parametro) {
		  		document.getElementById('locale').value = parametro;
			  	document.formInternacionaliza.fireEvent('internacionaliza');
			  	window.location.reload();
		 	}	
			
			function logar() {
				if (validaBrowSer()){
					document.form.submit();
					window.location = URL_SISTEMA+"pages/index/index.load";
				}
			}

			function encaminhaAosErrosDeScript() {
				document.form.submit();
				window.location = URL_SISTEMA+"pages/scripts/scripts.load?upgrade=sim";
			}

			function alterarSenha() {
				document.form.submit();
				window.location = URL_SISTEMA+"pages/alterarSenha/alterarSenha.load";
			}

		/*	function alterarSenha() {
				document.form.submit();
				window.location = URL_SISTEMA+"pages/alterarSenha/alterarSenha.load";
			}
			*/

			
			$(function() {
				$('#popupAlteracaoSenha').dialog({
					autoOpen: false,
					width: 705,
					height: 350,
					modal: true
				});
				
				$("#mensagemNavegadorIe").dialog({
					height: 155,
					modal: true,
					autoOpen: false,
					width: 350,
					resizable: false,
					show: "fade",
					hide: "fade",
					position: "center"
				});
				
				$("#user").focus();
			});
			
			function abrirPopupAlteracaoSenha() {
				$('#popupAlteracaoSenha').dialog('open');
			}
			