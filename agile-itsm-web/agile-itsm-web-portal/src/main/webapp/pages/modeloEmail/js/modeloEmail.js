

			var objTab = null;

			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}

			function LOOKUP_MODELOEMAIL_select(id, desc) {
				document.form.restore( {
					idModeloEmail:id
				});
			}

			var oFCKeditor = new FCKeditor( 'texto' ) ;
	        function onInitQuestionario(){
		        oFCKeditor.BasePath = ctx + '/fckeditor/';
		        //oFCKeditor.Config['ToolbarStartExpanded'] = false ;

		        oFCKeditor.ToolbarSet   = 'Default' ;
		        oFCKeditor.Width = '100%' ;
	            oFCKeditor.Height = '350' ;
		        oFCKeditor.ReplaceTextarea() ;
	        }
	        HTMLUtils.addEvent(window, "load", onInitQuestionario, false);

			function setDataEditor(){
				var oEditor = FCKeditorAPI.GetInstance( 'texto' ) ;
			    oEditor.SetData(document.form.texto.value);
			}

			function limpar(){
				document.form.clear();
		        var oEditor = FCKeditorAPI.GetInstance( 'texto' ) ;
		        oEditor.SetData('');
			}

			function salvar(){
				var oEditor = FCKeditorAPI.GetInstance( 'texto' ) ;
				document.form.texto.value = oEditor.GetXHTML();
				document.form.save();
			}

		
