	var controleFaixas_Inclusao = true;
	var controleFaixas_Index = 0;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
		
		document.form.onClear = function () {
			HTMLUtils.deleteAllRows('tblFaixaControle');
			limparIC();
		};

		/* document.form.afterRestore = function () {
			document.getElementById('tabTela').tabber.tabShow(0);
		};	 */

		document.form.onValidate = function () {
			document.form.colFaixasSerialize.value = '';

			var objs = HTMLUtils.getObjectsByTableId('tblFaixaControle');

	      	var objsSerializados = ObjectUtils.serializeObjects(objs);

			document.form.colFaixasSerialize.value = objsSerializados;

			return true;
		};
		
		
	}
	
	

	LOOKUP_RECURSO_select = function(id,desc){
		JANELA_AGUARDE_MENU.show();
		document.form.restore({idRecurso:id});
	};

	atualizaListagem = function(){
		document.form.fireEvent('atualizaListagem');
	};

	geraRadio = function(row, obj){
		row.cells[0].innerHTML = '<input type="radio" name="idObjetivoEstrategico" value="' + obj.idPlanoEstrategico + '" onclick="restoreObj(\'' + obj.idObjetivoEstrategico + '\')"/>';
	};

	setaCor = function(cor){
		try{
			document.getElementById('divCor').style.backgroundColor = cor;
		}catch(e){
		}

		document.formFaixaControle.cor.value = cor;
		POPUP_CORES.hide();
	};

	showAdicionarFaixaControle = function(){
		controleFaixas_Inclusao = true;
		controleFaixas_Index = 0;

		document.getElementById('divCor').style.backgroundColor = 'white';
		document.formFaixaControle.clear();
		POPUP_FAIXA_CONTROLE.show();
	};

	validaFaixaControle = function(){
		if (document.formFaixaControle.validate()){
			var obj = new CIT_FaixaValoresRecursoDTO();

			obj.corInner = '<div style="width:100px; height:18px; background-color: ' + document.formFaixaControle.cor.value + '">&nbsp;</div>';

			if (controleFaixas_Inclusao){
				HTMLUtils.addRow('tblFaixaControle', document.formFaixaControle, '', obj, ['valorInicio', 'valorFim', 'corInner', ''], null, null, [geraBtnExcluir], null, null, false);
			}else{
				HTMLUtils.updateRow('tblFaixaControle', document.formFaixaControle, '', obj, ['valorInicio', 'valorFim', 'corInner', ''], null, null, [geraBtnExcluir], null, null, controleFaixas_Index, false);
			}

			POPUP_FAIXA_CONTROLE.hide();
		}
	};

	geraBtnExcluir = function(row, obj){
		row.cells[0].onclick = function(){funcaoClick(row, obj)};
		row.cells[1].onclick = function(){funcaoClick(row, obj)};
		row.cells[2].onclick = function(){funcaoClick(row, obj)};

		row.cells[3].innerHTML = '<input type="button" name="btnExcluirFaixa" value="Excluir" onclick="excluirFaixa(' + row.rowIndex + ')"/>';
	};

	funcaoClick = function(row, obj){
		controleFaixas_Inclusao = false;

		controleFaixas_Index = row.rowIndex;

		HTMLUtils.setValuesForm(document.formFaixaControle, obj);

		document.getElementById('divCor').style.backgroundColor = obj.cor;
		POPUP_FAIXA_CONTROLE.show();
	};

	excluirFaixa = function(index){
		if (!confirm(i18n_message('recurso.desejaExcluirFaixa'))){
			return;
		}
		HTMLUtils.deleteRow('tblFaixaControle', index);
	};

	function LOOKUP_SOLICITANTE_select(id, desc){
		document.form.idSolicitante.value = id;
		document.form.nomeSolicitante.value = desc;
	}

	function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
		document.form.idItemConfiguracaoPai.value = id;
		document.form.nomeItemConfiguracaoPai.value = desc;
		listaInfoRelacionadaIC();
	}

	function listaInfoRelacionadaContrato(){
		document.form.fireEvent('listaServicos');
	}

	function listaInfoRelacionadaIC(){
		document.form.fireEvent('listaICSVinc');
	}

	function limparIC(){
		document.form.nomeItemConfiguracaoPai.value = '';
		document.form.idItemConfiguracaoPai.value = '';
		HTMLUtils.removeAllOptions('idItemConfiguracao');
	}
	
	
	function corpoTabela() {
		
		var r = new Array("00","33","66","99","CC","FF");
		var g = new Array("00","33","66","99","CC","FF");
		var b = new Array("00","33","66","99","CC","FF");

		for (i=0;i<r.length;i++){
		    for (j=0;j<g.length;j++) {
		       document.write("<tr>");
		       for (k=0;k<b.length;k++) {
		          var novoc = "#" + r[i] + g[j] + b[k];
		          document.write("<td style=\"background-color:" + novoc + "\" bgcolor=\"" + novoc + "\" align=center onclick=setaCor('" + novoc + "') style='cursor:pointer'>");
		          document.write(novoc);
		          document.write("</td>");
		       }
		       document.write("</tr>");
		    }
		}
		
		
		
	}
	
	

	