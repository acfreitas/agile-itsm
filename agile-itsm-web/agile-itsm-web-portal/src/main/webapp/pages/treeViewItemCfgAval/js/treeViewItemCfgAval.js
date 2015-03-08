
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
	}

	function submeter(){
		document.getElementById('divInfo').innerHTML = i18n_message("citcorpore.comum.aguarde");
		//if (document.form.idGrupoItemConfiguracao.value == ''){
		//	document.getElementById('divInfo').innerHTML = '';
		//	alert('Selecione o Grupo!');
		//	return;
		//}
		//document.form.fireEvent('mostraInfo');
		document.form.action = ctx + '/pages/treeViewItemCfgAval/treeViewItemCfgAval.load';
		document.form.submit();
	}
	function visualizaSofts(id){
		document.form.idItemConfiguracao.value = id;
		$('#POPUP_SOFTS').dialog('open');
		document.getElementById('divInfoSoftware').innerHTML = i18n_message("citcorpore.comum.aguarde");
		document.form.fireEvent('viewSoftwares');
	}
	function visualizaNet(id){
		document.form.idItemConfiguracao.value = id;
		$('#POPUP_SOFTS').dialog('open');
		document.getElementById('divInfoSoftware').innerHTML = i18n_message("citcorpore.comum.aguarde");
		document.form.fireEvent('viewNetwork');
	}
	$(function() {
		$("#POPUP_SOFTS").dialog({
			autoOpen : false,
			width : 1000,
			height : 700,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

	function imprimir(){
		$('#divInfo').printElement({
			printMode : 'popup',
			leaveOpen : true,
			 pageTitle : 'CITSMART Report',
			iframeElementOptions:{classNameToAdd : 'ui-corner-all resBusca'}
			});
	}

	function setaProcessar(v) {
		$('input[name="processar"]').attr('value', v);
	}

