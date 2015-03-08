
	    var popup;
	    addEvent(window, "load", load, false);
	    function load(){
		popup = new PopupManager(1000, 900, ctx+"/pages/");

	    }
	    $(function() {
			$("#POPUP_ACAO").dialog({
				autoOpen : false,
				width : 300,
				height : 200,
				modal : true
			});
		});
		var tabela = "";
		acao = function(valor){
			$("#POPUP_ACAO").dialog("open");
			tabela = valor;
		}
		acaoTabela = function(tipo){
			document.form.tipoAcao.value = tipo;
			document.form.tabela.value = tabela;
			document.form.fireEvent("executaMontaSQL");
		}
		executaScript = function(){
			document.form.tipoAcao.value = "";
			document.form.fireEvent("executaSQL");
		}
		$(function() {
			$(".ui-widget-overlay").click(function() {
				$("#POPUP_ACAO").dialog("close");
			});
		});

		acaoDrop = function(){
			if(confirm(i18n_message("tooldatabase.alerta.ExclusaoTabela")))
				acaoTabela('drop');
			else
				$("#POPUP_ACAO").dialog("close");
		}

		createTable = function(){
			document.form.tipoAcao.value = "createTable";
			document.form.tabela.value = "";
			document.form.fireEvent("executaMontaSQL");
		}

    
