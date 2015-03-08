

	$(window).load( function() {
		
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			};		
		
	});
				
				
				

	function LOOKUP_COMANDOSISTEMAOPEARCIONAL_select(idSO, desc) {
		document.form.restore({id: idSO});
	}

	function gravar() {
		document.form.save();
	}

	//funcoes de tratamento da popup de cadastro rápido

	/*
	*configurações da popup
	*layout: <div id="popupCadastroRapido">
	*			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%">
	*			</iframe>
	*		</div>
	*/
	$(document).ready(function() {
		$( "#popupCadastroRapido" ).dialog({
			title: 'Cadastro Rápido',
			width: 900,
			height: 500,
			modal: true,
			autoOpen: false,
			resizable: true,
			show: "fade",
			hide: "fade"
			});

		$("#popupCadastroRapido").dialog('close');

	});



	/*
	*funcao chamada no onclick para abrir a popup passando como parâmetro
	*a página que deseja abrir e a fireEvent que será executada
	*ao fechar a popup (que poder ser uma função do action para
	*recarregar a combo). Exemplo: abrePopup('unidade', 'preencheLista');
	*/
	function abrePopup(pagina, callbackBeforeClose) {
		document.getElementById('frameCadastroRapido').src =ctx+'/pages/' + pagina + '/' + pagina + '.load?iframe=true';

		$("#popupCadastroRapido").dialog('open');

		//quando fechar a popup ele executa um evento
		$("#popupCadastroRapido").dialog({
			beforeClose: function(event, ui) {
	   			//aqui o evento disparado ao fechar
	   			document.form.fireEvent(callbackBeforeClose);
	   		}
		});
	}