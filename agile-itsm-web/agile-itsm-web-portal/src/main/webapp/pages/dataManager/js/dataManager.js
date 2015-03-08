
			var btnProcessar = i18n_message("dataManager.processsar");
			var btnCancelar = i18n_message("dataManager.cancelar");
		
			function abrirPopup(id, text){
				document.getElementById('descObjetoNegocio').innerHTML = text;
				document.form.idObjetoNegocio.value = id;
				document.form2.idObjetoNegocio.value = id;
				document.getElementById('divExport').innerHTML = '';
				$("#POPUP_EXPORTAR").dialog({
					autoOpen : false,
					width : 800,
					height : 400,
					modal : true,
		            buttons: {
		            	btnProcessar : function() {
		                	JANELA_AGUARDE_MENU.show();
		                	document.form2.fireEvent('exportar');
		                },
		                btnCancelar : function() {
		                    $( this ).dialog( "close" );
		                }
		            }
				});
				$('#POPUP_EXPORTAR').dialog('open');
				mostraExport();
			}
			function exportarTudo(){
				if (!confirm(i18n_message("dataManager.exportTudoConfirm"))){
					return;
				}
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent('exportarTudo');
			}
			function mostraExport(){
				document.getElementById('divExport').innerHTML = i18n_message("citcorpore.comum.carregando")+"...";
				document.form2.fireEvent('trataExport');
			}

			function importar(){

				document.form.fireEvent('importar');
			}

			function mostraImport(){
				$("#POPUP_IMPORTAR").dialog({
					title: 'Importar',
					autoOpen : false,
					width : 700,
					height : 320,
					modal : true,
					show: "fade",
					hide: "fade"
				});

				$("#btnFechaImportacoes").click(function(){
					$('#POPUP_IMPORTAR').dialog('close');
				});

				$( "#POPUP_IMPORTAR" ).dialog( 'open' );
				uploadAnexos.refresh();
			}
			function getFile(pathFile, fileName){
				window.location.href = ctx+'/baixar.getFilefile=' + pathFile + '&fileName=' + fileName;
			}
			function preparaUpload(){
				var btnUpload=$('#ajxiupload2');
				var status=$('#status');

				new AjaxUpload(btnUpload, {
					action: ctx+'/pages/uploadAjax/uploadAjax.load',
					name: 'file_uploadAnexos',
					onSubmit: function(file, ext){
						status.text('Uploading...');
					},
					onComplete: function(file, response){
						//On completion clear the status
						status.text('');
						//Add uploaded file to list
						//if(response==="success"){
							$('<li></li>').appendTo('#files').html('<img src="'+ctx+'/imagens/documents.png" alt="" /><br />' + file).addClass('success');
						//} else{
						//	$('<li></li>').appendTo('#files').text(file).addClass('error');
						//}
					}
				});
			}
			function limpar_upload(){
				document.getElementById('divShowFiles').innerHTML = '';
				document.getElementById('divShowFiles').innerHTML = '<ul id="files" ></ul>';
				document.form.fireEvent('limparUpload');
			}
			function validaExcl(obj){
				if (obj.checked){
					var msg = i18n_message("dataManager.exclusaoregs");
					alert(msg);
				}
			}
			function exportarTudoSql(){
				if (!confirm(i18n_message("dataManager.exportTudoConfirm"))){
					return;
				}
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent('exportarTudoSql');
			}

			function carregaMetaDados(){
				if (!confirm(i18n_message("dataBaseMetaDados.carregaTabelas"))){
					return;
				}
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent('carregaMetaDados');
			}

		
