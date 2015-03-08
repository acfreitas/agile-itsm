
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);

		}

	}

	$(function() {
		$("#addGrupoExecucao").click(function() {
			$("#POPUP_GRUPO_EXECUCAO").dialog("open");
		});
	});

	$(function() {
		$("#addGrupoRelatorio").click(function() {
			$("#POPUP_GRUPO_RELATORIO").dialog("open");
		});
	});

	$(function() {
		$("#addGrupo").click(function() {
			$("#POPUP_GRUPO").dialog("open");
		});
	});

	function load() {

		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}


		$("#POPUP_GRUPO_EXECUCAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

		$("#POPUP_GRUPO_RELATORIO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

		$("#POPUP_GRUPO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

	}

	function LOOKUP_GRUPO_RENDIMENTO_EXECUCAO_select(id, desc) {
		document.form.idGrupoExecucao.value = id;
		document.form.fireEvent("restoreNomeGrupoExecucao");
		$("#POPUP_GRUPO_EXECUCAO").dialog("close");
	}

	function LOOKUP_GRUPO_RENDIMENTO_select(id, desc) {
		document.form.idGrupo.value = id;
		document.form.fireEvent("restoreNomeGrupo");
		$("#POPUP_GRUPO").dialog("close");
	}

	function LOOKUP_GRUPO_RELATORIO_select(id, desc) {
		document.form.idGrupoRelatorio.value = id;
		document.form.fireEvent("restoreNomeGrupoRelatorio");
		$("#POPUP_GRUPO_RELATORIO").dialog("close");
	}


	function filtrarExecucao() {
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var idGrupo = document.getElementById("idGrupoExecucao").value;
		var idPessoa = document.getElementById("comboPessoa").value;

		document.getElementById("idPessoa").value = idPessoa;

		if(idGrupo == ""){
			alert(i18n_message("controle.grupoObrigatorio"));
			return false;
		}

		if (dataInicio != "") {
			if (DateTimeUtil.isValidDate(dataInicio) == false) {
				alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				document.getElementById("dataInicio").value = '';
				return false;
			}
		}

		if(dataInicio == ""){
			alert(i18n_message("citcorpore.comum.validacao.datainicio"));
			return false;
		}
		if (dataFim != "") {
			if (DateTimeUtil.isValidDate(dataFim) == false) {
				alert(i18n_message("citcorpore.comum.validacao.datafim"));
				document.getElementById("dataFim").value = '';
				return false;
			}
		}

		if(dataFim == ""){
			alert(i18n_message("citcorpore.comum.validacao.datafim"));
			return false;
		}

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("carregaTabelasExecucao");

	}

	function filtrar() {
		var ano = document.getElementById("comboAno").value;
		var mes = document.getElementById("comboMes").value;
		var idGrupo = document.getElementById("idGrupo").value;

		document.form.variavelAuxiliarParaFecharMes.value = "true";
		document.form.ano.value = ano;
		document.form.mes.value = mes;

		if(idGrupo == ""){
			alert(i18n_message("controle.grupoObrigatorio"));
			return false;
		}

		JANELA_AGUARDE_MENU.show();
 		document.form.fireEvent("carregaTabelas");

	}

	function fecharMes() {
		var ano = document.getElementById("comboAno").value;
		var mes = document.getElementById("comboMes").value;
		var idGrupo = document.getElementById("idGrupo").value;

		document.form.ano.value = ano;
		document.form.mes.value = mes;

		if(variavelAuxiliarParaFecharMes.value != "true"){
			alert(i18n_message("controle.pesquiseAntes"));
			return false;
		}

		if(idGrupo == ""){
			alert(i18n_message("controle.grupoObrigatorio"));
			return false;
		}

 		document.form.fireEvent("fecharMes");

	}

	function imprimirRelatorioFuncionarioMaisEficiente(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;

		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioFuncionarioMaisEficiente");
	}

	function imprimirRelatorioFuncionarioMenosEficiente(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;

		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioFuncionarioMenosEficiente");
	}

	function imprimirRelatorioMelhoresFuncionarios(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;

		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioMelhoresFuncionarios");
	}

	function imprimirRelatorioPorGrupo(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;

		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioPorGrupo");
	}

	function imprimirRelatorioPorPessoa(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;

		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioPorPessoa");
	}

	function imprimirRelatorioMediaAtraso(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;

		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioMediaAtraso");
	}

