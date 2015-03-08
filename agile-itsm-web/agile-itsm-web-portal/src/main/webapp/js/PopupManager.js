/**
 * @author breno.guimaraes
 *
 * Mátodos responsáveis por renderizar uma popup.
 *
 * configuraçães da popup layout:
 * 	<div id="popupCadastroRapido">
 * 			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%">
 * 			</iframe>
 * 	</div>
 *
 * Para simplificar, somente os parámetros
 * extritamente necessários devem ser solicitados. Mudanáas mais complexas devem
 * ser feitas no cádigo.
 * @constructor
 * @_width
 * Largura da janela.
 * @_height
 * Altura da janela.
 * @rootPath
 * Caminho padráo para o diretário de páginas.
 * Exemplo: <%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/
 */
function PopupManager(_width, _height, rootPath) {

	var _popupManagerRootPath = rootPath;

	//configuraçães iniciais da popup
	$("#popupCadastroRapido").dialog({
		height : _height,
		modal : true,
		autoOpen : false,
		width : _width,
		maxWidth : "100%",
		resizable : true,
		show : "fade",
		hide : "fade"
	});

	$(".ui-dialog").css("width", "auto");

	this.fecharPopup = function(){
		$("#popupCadastroRapido").dialog('close');
	}

	$("#popupCadastroRapido").dialog('close');

	/**
	 * funcao chamada no onclick para abrir a popup passando como parametro
	 * a pagina que deseja abrir e a fireEvent que sera executada ao fechar
	 * a popup (que poder ser uma funcao do action(servlet) para recarregar
	 * a combo). Exemplo: abrePopup('unidade', 'preencheLista');
	 *
	 * @pagina String da pagina que devera ser aberta dentro da popup (iframe).
	 * @callbackBeforeClose String: nome da funcao que sera chamada pelo
	 *                      fireEvent.
	 */
	this.abrePopup = function(pagina, callbackBeforeClose) {
		//atribuo o título aqui novamente para que o mesmo possa ser alterado livremente
		$("#popupCadastroRapido").dialog({
			title : this.titulo
		});

		// seto para o iframe a página que deverá ser aberta
		document.getElementById('frameCadastroRapido').src = _popupManagerRootPath + pagina + '/' + pagina + '.load?iframe=true';

		// abre a popup
		$("#popupCadastroRapido").dialog('open');

		// quando fechar a popup, executa um evento
		$("#popupCadastroRapido").dialog({
			beforeClose : function(event, ui) {
				// aqui o evento disparado ao fechar
				if(callbackBeforeClose != null && callbackBeforeClose != ''){
					document.form.fireEvent(callbackBeforeClose);
				}
			}
		});
	}

	this.abrePopupParms = function(pagina, callbackBeforeClose, parms) {
		//atribuo o título aqui novamente para que o mesmo possa ser alterado livremente
		$("#popupCadastroRapido").dialog({
			title : this.titulo
		});

		var params = '';
		if (parms != undefined && parms != ''){
			params = '&' + parms;
		}

		// seto para o iframe a página que deverá ser aberta
		document.getElementById('frameCadastroRapido').src = _popupManagerRootPath + pagina + '/' + pagina + '.load?iframe=true' + params;

		// abre a popup
		$("#popupCadastroRapido").dialog('open');

		// quando fechar a popup, executa um evento
		$("#popupCadastroRapido").dialog({
			beforeClose : function(event, ui) {
				// aqui o evento disparado ao fechar
				if(callbackBeforeClose != null && callbackBeforeClose != ''){
					document.form.fireEvent(callbackBeforeClose);
				}
			}
		});
	}

}