/**
 * 
 */

	$(window).load(function(){
		popup = new PopupManager(1000, 900, ctx+"/pages/");
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	});

	function pesquisa() {
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;

		if (dataInicio != '' || dataFim != '') {
			if (DateTimeUtil.isValidDate(dataInicio) == false) {
				alert(i18n_message("citcorpore.comum.datainvalida"));
	 			document.getElementById("dataInicio").value = '';
				return false;
			}

			if (DateTimeUtil.isValidDate(dataFim) == false) {
				alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
		 		document.getElementById("dataFim").value = '';
				return false;
			}

			if (!validaData(dataInicio, dataFim) ) {
				return false;
			}
		}

		document.form.fireEvent("pesquisarItemConfiguracao");
	}

	function verificarExpiracao() {
		document.form.fireEvent('verificarExpiracao');
	}


	function LOOKUP_PESQUISAITEMCONFIGURACAO_NAO_DESATIVADOS_select(id, desc) {
		var valor = desc.split(' - ');
		document.form.ip.value = valor[0];
		document.form.idItemConfiguracao.value = id;

		document.formPesquisaFilho.pesqLockupLOOKUP_ITENSCONFIGURACAORELACIONADOS_NAO_DESATIVADOS_iditemconfiguracaopai.value = id;
		$("#POPUP_ITEMCONFIG").dialog("close");

	}

	function LOOKUP_ITENSCONFIGURACAORELACIONADOS_NAO_DESATIVADOS_select(id, desc){
		document.form.idItemConfiguracaoFilho.value = id;
		document.form.identificacaoFilho.value = desc;
		$("#POPUP_ITEMCONFIGFILHO").dialog("close");
	}

	function LOOKUP_GRUPOITEMCONFIGURACAO_select(id, desc) {
		document.form.idGrupoItemConfiguracao.value = id;
		document.form.nomeGrupoItemConfiguracao.value = desc;
		$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("close");
	}
	function popupAtivos(idItem){
		
		if(iframe != null){
			parent.selectedItemConfiguracao(idItem);
		}else{
			document.getElementById('iframeAtivos').src =ctx+'/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
			$("#POPUP_ATIVOS").dialog("open");
		}
	}

	$(function() {
		$("#POPUP_ATIVOS").dialog({
			autoOpen : false,
			width : 1005,
			height : 565,
			modal : true
		});
	});


	$(function() {
		$('.datepicker').datepicker();
	});
// 	popup para pesquisar de ip
	$(function() {
		$("#POPUP_ITEMCONFIG").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	});

	$(function() {
		$("#POPUP_ITEMCONFIGFILHO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		//document.formPesquisaFilho.btnTodosLOOKUP_ITENSCONFIGURACAORELACIONADOS_NAO_DESATIVADOS.style.display = 'none';
		//document.formPesquisaFilho.btnLimparLOOKUP_ITENSCONFIGURACAORELACIONADOS_NAO_DESATIVADOS.style.display = 'none';
	});

	$(function() {
		$("#addip").click(function() {
			$("#POPUP_ITEMCONFIG").dialog("open");
		});
	});

	$(function() {
		$("#addipFilho").click(function() {
			if (document.form.id.value == ''
				|| document.form.idItemConfiguracao.value == "") {
				alert(i18n_message("pesquisaIC.selecioneIdentificacao"));
			valor.checked = false;
			return false;
		}
			$("#POPUP_ITEMCONFIGFILHO").dialog("open");

		});

		$("#identificacaoFilho").click(function() {
			$("#POPUP_ITEMCONFIGFILHO").dialog("open");
		});

		$("#ip").click(function() {
			$("#POPUP_ITEMCONFIG").dialog("open");
		});
	});



//	popup para pesquisar de grupo
	$(function() {
		$("#POPUP_GRUPOITEMCONFIGURACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	});

	function consultarGrupoItemConfiguracao(){
		$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("open");
	}


 	/**
	* @author rodrigo.oliveira
	*/
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

	function divItemFilho(valor) {
		if (valor.checked) {
			if (document.form.id.value == ''
					|| document.form.idItemConfiguracao.value == "") {
				alert(i18n_message("pesquisaIC.selecioneIdentificacao"));
				valor.checked = false;
				return false;
			}

			document.getElementById('divItemFilho').style.display = 'block';
		} else {
			document.getElementById('divItemFilho').style.display = 'none';
		}
	}

	/*
	Metodo para limpar div de pesquisa de item configuração
	@thays.araujo 28/02/2014 #136499
	*/
	function limparGrid(){
		$( "#divPesquisaItemConfiguracao" ).empty();
	}

	function checkedTipoExecucao(){
		$('#inventario').attr('checked', true);
	}