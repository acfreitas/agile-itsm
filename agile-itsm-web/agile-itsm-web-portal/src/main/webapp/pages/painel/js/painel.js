

		var posPainel;

		function atualizaPainel(fileName, objTd)
		{
			if(fileName != null){
				document.formPainel.fireEvent('changeCombo');
			}
			//emiteAguarde();
			if (fileName != null){
				document.formPainel.fileName.value = fileName;
				document.formPainel.fileNameItem.value = '';
				document.formPainel.parametersPreenchidos.value = 'false';

			}
			document.formPainel.fireEvent('geraPainel');

			setTimeout(function()
				{
					if (objTd != null){
 						var	tbl = document.getElementById(objTd);
						tbl.className += ' tASNx';
					}
				},200);
		}

		function emiteAguarde(){
			posPainel = HTMLUtils.getPosElement('divPainel');

			document.getElementById('divAguarde').style.top = posPainel.y + 'px';
			document.getElementById('divAguarde').style.left = posPainel.x + 'px';
			document.getElementById('divAguarde').style.width = document.getElementById('divPainel').style.width;
			document.getElementById('divAguarde').style.height = document.getElementById('divPainel').style.height;

			document.getElementById('divAguarde').style.display = 'block';
		}

		function hideAguarde(){
			document.getElementById('divAguarde').style.display = 'none';
		}

		function getFileExcel(pathFileExcel, nomeCurtoFileExcel){
			document.formGetExcel.fileNameExcel.value = pathFileExcel;
			document.formGetExcel.fileNameExcelShort.value = nomeCurtoFileExcel;

			document.formGetExcel.action= ctx + '/pages/painel/painel';

			document.formGetExcel.parmCount.value = '3';
			document.formGetExcel.parm1.value = DEFINEALLPAGES_getFacadeName(document.formGetExcel.action);
			document.formGetExcel.parm2.value = '';
			document.formGetExcel.parm3.value = 'getFileExcel';

			document.formGetExcel.setAttribute("target",'frameGetExcel');

			document.formGetExcel.action= ctx + '/pages/painel/painel.event';
			document.formGetExcel.submit();
		}

		function geraPDF(fileNameGerar, tipoSaidaApresentada){
			JANELA_AGUARDE_MENU.show();
			document.formPainel.fileNameItem.value = fileNameGerar;
			document.formPainel.tipoSaidaApresentada.value = tipoSaidaApresentada;
			document.formPainel.fireEvent('geraPDF');
		}

		function geraExcel(fileNameGerar, tipoSaidaApresentada){
			JANELA_AGUARDE_MENU.show();
			document.formPainel.fileNameItem.value = fileNameGerar;
			document.formPainel.tipoSaidaApresentada.value = tipoSaidaApresentada;
			document.formPainel.fireEvent('geraExcel');
		}

		function submitGrupo(){
			if (document.formPainel.fileNameGrupo.value == '')
			{
				document.getElementById('divPainel').innerHTML = '';
				document.getElementById('divLista').innerHTML = '';
				document.getElementById('parametros').innerHTML = '';
			}
			else{
				document.formPainel.fireEvent('changeCombo');
			}
		}

		var ajaxObj = new AjaxAction();
		function submitJSP(pathJSP){
			ajaxObj = new AjaxAction();
			copiaParametros();
			ajaxObj.submitForm(document.formPainel,pathJSP,retornoSubmitJSP);
		}
		function retornoSubmitJSP(){
			if (ajaxObj.req.readyState == 4){
				if (ajaxObj.req.status == 200){
					document.getElementById('interna0').innerHTML = ajaxObj.req.responseText;
					document.getElementById('divAguarde').style.display = 'none';
				}
			}
		}

		function gerarPainel(){

			copiaParametros();

			var dataInicio = document.getElementById("PARAM.dataInicial").value;
			var dataFim = document.getElementById("PARAM.dataFinal").value;

			if(validaData(dataInicio, dataFim)){
				if (document.formParametros.validate())
				{
					JANELA_AGUARDE_MENU.show();
					document.getElementById('Throbber').style.visibility ='visible';
					//Coloca os atributos digitados apenas para fazer um fireEvent nos dados para o Servidor.
					document.getElementById('divInfTransfPainel').innerHTML = document.getElementById('divParametros').innerHTML;
					copiaParametros();
					setTimeout(function(){$("#POPUP_PARAM").dialog('close');document.getElementById('Throbber').style.visibility ='hidden';},2000);
					document.formPainel.parametersPreenchidos.value = 'true';
					atualizaPainel(null, null);

				}

			}else{
				$("#PARAM\\.dataInicial").val(null);
			}

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

		function copiaParametros(){
			for(var i = 0; i < document.formParametros.length - 1; i++){
				var elem = document.formParametros[i];
				if (elem == null || elem == undefined){
					continue;
				}
				if (elem.name == undefined || elem.name == ''){
					continue;
				}
				try{
					//alert('document.formPainel["' + elem.name + '"].value = "' + elem.value + '"');
					eval('document.formPainel["' + elem.name + '"].value = "' + elem.value + '"');
				}catch(e){
				}
			}
		}

		function setaTipoSaida(fileName, tipoSaida){
			//emiteAguarde();
			JANELA_AGUARDE_MENU.show();
			document.getElementById('Throbber_2').style.visibility ='visible';

			document.formPainel.fileNameItem.value = fileName;
			document.formPainel.parametersPreenchidos.value = 'true';
			document.formPainel.tipoSaida.value = tipoSaida;

			document.getElementById('divInfTransfPainel').innerHTML = document.getElementById('divParametros').innerHTML;
			copiaParametros();
			document.formPainel.fireEvent('atualizaTipoSaida');
		}

		function geraSaidaImpHTML(fileName, tipoSaida){
			$('#interna0').printElement({
				printMode : 'popup',
				leaveOpen : true,
				 pageTitle : i18n_message("citcorpore.comum.agenda") ,
				iframeElementOptions:{classNameToAdd : 'ui-corner-all resBusca'},
				 printBodyOptions : {
		            styleToAdd : 'position:absolute;width:100%;height:100%; top: 1px;bottom:0px;padding: 1px; margin: 1px;  ' ,
		            classNameToAdd  :  '' },
		            overrideElementCSS: null//['../../css/pesquisaLig.css']
				});
		}

		function fecharTelaParm(){
			$("#POPUP_PARAM").dialog('close');
			document.getElementById('Throbber').style.visibility ='hidden';
		}

		function setaTipoGrafico(fileName, tipoSaida, graficosTipos, titulo){
			document.getElementById('Throbber_2').style.visibility ='hidden';

			var tabela = '<table width="100%">';
			tabela += '<tr>';
				tabela += '<td>';
					tabela += '<b>' + i18n_message("painel.selecioneGraficoAbaixo") + '</b>';
				tabela += '</td>';
			tabela += '</tr>';
			if (graficosTipos != null && graficosTipos != undefined){
				var tipos = graficosTipos.split(';');
				if (tipos.length){
					for(var i = 0; i < tipos.length; i++){
						tabela += '<tr onmouseover="HTMLUtil_TrowOn(this,HTMLUtil_colorOn)" onmouseout="HTMLUtil_TrowOn(this,HTMLUtil_colorOff)">';
							tabela += '<td style=\"cursor:pointer\" onclick=\'setaTipoSaida("' + fileName + '", "G:' + tipos[i] + '")\'>';
								tabela += tipos[i];
							tabela += '</td>';
						tabela += '</tr>';
					}
				}
			}
			tabela += '</table>';
			document.getElementById('divOpcoesGraficos').innerHTML = tabela;

			$("#POPUP_GRAFICO_OPC").attr('title','Gráfico: ' + titulo);

			$("#POPUP_GRAFICO_OPC").dialog('open');
		}

		function recarrega(obj){
			document.getElementById('divInfTransfPainel').innerHTML = document.getElementById('divParametros').innerHTML;
			copiaParametros();
			document.formPainel.parametersPreenchidos.value = 'false';

			for(var i = 0; i < document.formParametros.length - 1; i++){
				var elem = document.formParametros[i];
				if (elem == null || elem == undefined){
					continue;
				}
				if (elem.name == undefined || elem.name == ''){
					continue;
				}
				if (elem.name == obj.name){
					i++;
					if (i < document.formParametros.length - 1){
						var elem = document.formParametros[i];
						document.formPainel.campo.value = elem.name;
						break;
					}
				}
			}
			document.formPainel.fireEvent('reloadParameters');
		}

		function voltar(){
			window.location = ctx + '/pages/index/index.load';
		}


		function limparForm(){

			$("input[type='text']").val(null);
		}

		$(function() {
			$("#POPUP_PARAM").dialog( {
				autoOpen : false,
				width : 800,
				height : 650,
				modal : true
			});

		});

		$(function() {
			$("#POPUP_GRAFICO_OPC").dialog( {
				autoOpen : false,
				width : 400,
				height : 280,
				modal : true
			});
		});

		$(function()
		{
			 $('#parametros').click(function()
			 {
				 $('#POPUP_PARAM').dialog('open');
			 });

		});
		function pageLoad()
		{
			$(function()
			{
				$('input.datepicker').datepicker();
			});
		}


	
