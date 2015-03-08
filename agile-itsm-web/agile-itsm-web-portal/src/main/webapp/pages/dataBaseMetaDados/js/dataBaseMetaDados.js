

    $(document).ready(function () {
     	layout = $('body').layout({ applyDefaultStyles: true, north: { resizable: false, slidable: false }, west: { resizable: false, slidable: false, closable: false }});

    	layout.sizePane('north', 50);

    	layout.sizePane('west', 40);

    	layout.sizePane('south', 200);

    	$( "#tabInfs" ).tabs({
 		   select: function(event, ui) {
 		 	}
 		});
    });

	function limpar(){
		document.form.clear();
		document.getElementById('divRetorno').innerHTML = '';
	}

	function carregar(){
		document.getElementById('divRetorno').innerHTML = 'Aguarde... carregando...';
		var flag = document.getElementById("carregarTodos");
		if(flag.checked){
			if(confirm(i18n_message("dataBaseMetaDados.carregaTabelas"))){
				document.form.fireEvent('carregaTodosMetaDados');
			}else{
				document.getElementById('divRetorno').innerHTML = "";
				return false;
			}
		}else{
			document.form.fireEvent('carregaMetaDados');
		}

	}

	function validaCheck(){
		var flag = document.getElementById("carregarTodos");
		if(flag.checked){
			document.getElementById("nomeTabela").value = "";
			document.getElementById("nomeTabela").disabled = true;
		}else{
			document.getElementById("nomeTabela").disabled = false;
		}
	}

	voltar = function(){
		window.location = ctx + '/pages/index/index.load';
	};


