
	var objTab = null;

	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
	}
	$(function() {
		$("#POPUP_EMPREGADO").dialog({
			autoOpen : false,
			width : 650,
			height : 500,
			modal : true,
			show: "fade",
			hide: "fade"
		});
		$("#POPUP_EMPREGADO_ASSINATURA").dialog({
			autoOpen : false,
			width : 650,
			height : 500,
			modal : true,
			show: "fade",
			hide: "fade"
		});
/* Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 10:00 - ID Citsmart: 120948 -
* Motivo/Comentário: Lookup com padrão de popup antigo/ criando popups modelo jqueryui */
		$("#POPUP_MUDANCA").dialog({
			autoOpen : false,
			width : 650,
			height : 500,
			modal : true,
			show: "fade",
			hide: "fade"
		});
		$("#POPUP_LIBERACAO").dialog({
			autoOpen : false,
			width : 650,
			height : 500,
			modal : true,
			show: "fade",
			hide: "fade"
		});
		$("#POPUP_REG_AUT_MUDANCA").dialog({
			autoOpen : false,
			width : 650,
			height : 250,
			modal : true,
			show: "fade",
			hide: "fade"
		});

		$("#addRecurso").click(function() {
			$("#POPUP_EMPREGADO").dialog("open");
		});

		$("#addAssinaturasAprovacoes").click(function() {
			$("#POPUP_EMPREGADO_ASSINATURA").dialog("open");
		});
		$("#adicionarMudanca").click(function() {
			$("#POPUP_MUDANCA").dialog("open");
		});
		$("#adicionarLiberacao").click(function() {
			$("#POPUP_LIBERACAO").dialog("open");
		});
	});

	var contRecurso = 0;
	var contRecursosAssinatura = 0;
	function LOOKUP_PROJETO_select(id,desc){
		document.form.restore({idProjeto:id});
	}
	function LOOKUP_EMPREGADO_select(id, desc){
		addLinhaTabelaRecurso(id, desc, '', true);
	}
	function LOOKUP_EMPREGADO_ASSINATURA_select(id, desc){
		addLinhaTabelaAssinaturaAprovacao(id, desc, '', '', true);
	}
	function LOOKUP_MUDANCA_select(id, desc){
		document.form.idRequisicaoMudanca.value = id;
		document.form.adicionarMudanca.value = desc;
		$("#POPUP_MUDANCA").dialog("close");
	}
	function LOOKUP_LIBERACAO_select(id, desc){
		document.form.idLiberacao.value = id;
		document.form.adicionarLiberacao.value = desc;
		$("#POPUP_LIBERACAO").dialog("close");
	}
	function addLinhaTabelaRecurso(idEmpregado, nome, valor, valida){
		var tbl = document.getElementById('tabelaRecurso');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
	 	if (valida){
			if (!validaAddLinhatabelaRecurso(lastRow, idEmpregado)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contRecurso++;
		coluna.innerHTML = '<img id="imgDelEmpregado' + contRecurso + '" style="cursor: pointer;" title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaRecurso\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = nome + '<input type="hidden" id="idEmpregado' + contRecurso + '" name="idEmpregado" value="' + idEmpregado + '" />';
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input type="text" id="custoHora' + idEmpregado + '"  name="custoHora" value="' + valor + '"/>';

		addEvent(document.getElementById('custoHora' + idEmpregado),
				"keydown",
				DEFINEALLPAGES_formataMoeda,
				false);
		addEvent(document.getElementById('custoHora' + idEmpregado),
				"blur",
				DEFINEALLPAGES_formataMoedaSaidaCampo,
				false);

		$("#POPUP_EMPREGADO").dialog("close");
	}

	function addLinhaTabelaAssinaturaAprovacao(idEmpregado, nome, papel, ordem, valida){
		var tbl = document.getElementById('tabelaAssinaturasAprovacoes');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;

		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contRecursosAssinatura++;
		coluna.innerHTML = '<img id="imgDelEmpregadoAssinatura' + contRecursosAssinatura + '" style="cursor: pointer;" title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaAssinatura(\'tabelaAssinaturasAprovacoes\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = nome + '<input type="hidden" id="idEmpregadoAssinatura' + contRecursosAssinatura + '" name="idEmpregadoAssinatura" value="' + idEmpregado + "" + contRecursosAssinatura + '" />';
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input type="text" maxlength="100" id="papel' + idEmpregado + "" + contRecursosAssinatura + '"  name="papel" value="' + papel + '"/>';
		coluna = row.insertCell(3);
		coluna.innerHTML = '<input type="text" id="ordem' + idEmpregado + "" + contRecursosAssinatura + '" class="Format[Numero]" name="ordem" value="' + ordem + '" onkeypress="return somenteNumero(event);"/>';

		$("#POPUP_EMPREGADO_ASSINATURA").dialog("close");
	}

	function trataKeyMoney(e){
		if (isMozilla){
			element = e.target;
		}else{
			element = e.srcElement;
		}
	  	FormatUtils.formataCampo(element.form, element.name, '99/99/9999', e);
	}
	function validaAddLinhatabelaRecurso(lastRow, idEmpregadoParm){
		if (lastRow > 2){
			var arrayIdEmp = document.form.idEmpregado;
			for (var i = 0; i < arrayIdEmp.length; i++){
				if (arrayIdEmp[i].value == idEmpregadoParm){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}

			}
		} else if (lastRow == 2){
			var idEmp = document.form.idEmpregado;
			if (idEmp.value == idEmpregadoParm){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		return true;
	}

	function removeLinhaTabela(idTabela, rowIndex) {
		 if (idTabela == "tabelaRecurso"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					HTMLUtils.deleteRow(idTabela, rowIndex);
				}
		 }
	}
	function removeLinhaTabelaAssinatura(idTabela, rowIndex) {
		 if (idTabela == "tabelaAssinaturasAprovacoes"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					HTMLUtils.deleteRow(idTabela, rowIndex);
				}
		 }
	}
	function deleteAllRows() {
		var tabela = document.getElementById('tabelaRecurso');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		document.getElementById('tabelaRecurso').style.display = "none";
		contRecurso = 0;

		var tabelaAssinatura = document.getElementById('tabelaAssinaturasAprovacoes');
		var countAssinatura = tabelaAssinatura.rows.length;

		while (countAssinatura > 1) {
			tabelaAssinatura.deleteRow(countAssinatura - 1);
			countAssinatura--;
		}
		document.getElementById('tabelaAssinaturasAprovacoes').style.display = "none";
		contRecursosAssinatura = 0;
	}

	function salvar(){
		document.form.colRecursosSerialize.value = '';
		document.form.colAssinaturasSerialize.value = '';
		//serialização de recursos
		var tbl = document.getElementById('tabelaRecurso');
		var lastRow = tbl.rows.length;
		var objs = new Array();
		var x = 0;
		if (lastRow > 2){
			var arrayIdEmp = document.form.idEmpregado;
			for (var i = 0; i < arrayIdEmp.length; i++){
				var custoObj = null;
				try{
					custoObj = document.getElementById('custoHora' + arrayIdEmp[i].value);
				}catch(e){}
				var custoValue = '0';
				if (custoObj != null){
					try{
						custoValue = custoObj.value;
					}catch(e){}
				}
				objs[x] = {idEmpregado:arrayIdEmp[i].value,custoHora:custoValue};
				x++;
			}
		} else if (lastRow == 2){
			var idEmp = document.form.idEmpregado;
			var custoObj = null;
			try{
				custoObj = document.getElementById('custoHora' + idEmp.value);
			}catch(e){}
			var custoValue = '0';
			if (custoObj != null){
				try{
					custoValue = custoObj.value;
				}catch(e){}
			}
			objs[x] = {idEmpregado:idEmp.value,custoHora:custoValue};
			x++;
		}
      	var objsSerializados = ObjectUtils.serializeObjects(objs);
		document.form.colRecursosSerialize.value = objsSerializados;

		//serialização de assinaturas
		var tblAssinatura = document.getElementById('tabelaAssinaturasAprovacoes');
		var lastRowAssinatura = tblAssinatura.rows.length;
		var objsAssinatura = new Array();
		var y = 0;
		if (lastRowAssinatura > 2){
			var arrayIdEmpAssinatura = document.form.idEmpregadoAssinatura;
			for (var i = 0; i < arrayIdEmpAssinatura.length; i++){
				var papelObj = null;
				var ordemObj = null;
				try{
					papelObj = document.getElementById('papel' + arrayIdEmpAssinatura[i].value);
					ordemObj = document.getElementById('ordem' + arrayIdEmpAssinatura[i].value);
				}catch(e){}
				var papelValue = ' ';
				var ordemValue = ' ';
				if (papelObj != null){
					try{
						papelValue = papelObj.value;
					}catch(e){}
				}
				if (ordemObj != null){
					try{
						ordemValue = ordemObj.value;
						var ordemInt = parseInt(ordemObj.value);
						if(isNaN(ordemInt)){
							alert(i18n_message("projeto.ordemSomenteNumeros"));
							return;
						}
					}catch(e){}
				}
				objsAssinatura[y] = {idEmpregadoAssinatura:arrayIdEmpAssinatura[i].value,papel:papelValue,ordem:ordemValue};
				y++;
			}
		} else if (lastRowAssinatura == 2){
			var idEmpAssinatura = document.form.idEmpregadoAssinatura;
			var papelObj = null;
			var ordemObj = null
			try{
				papelObj = document.getElementById('papel' + idEmpAssinatura.value);
				ordemObj = document.getElementById('ordem' + idEmpAssinatura.value);
			}catch(e){}
			var papelValue = ' ';
			var ordemValue = ' ';
			if (papelObj != null){
				try{
					papelValue = papelObj.value;
				}catch(e){}
			}
			if (ordemObj != null){
				try{
					ordemValue = ordemObj.value;
					var ordemInt = parseInt(ordemObj.value);
					if(isNaN(ordemInt)){
						alert(i18n_message("projeto.ordemSomenteNumeros"));
						return;
					}
				}catch(e){}
			}
			objsAssinatura[y] = {idEmpregadoAssinatura:idEmpAssinatura.value,papel:papelValue,ordem:ordemValue};
			y++;
		}
      	var objsAssinaturaSerializados = ObjectUtils.serializeObjects(objsAssinatura);
		document.form.colAssinaturasSerialize.value = objsAssinaturaSerializados;


		document.form.save();
	}
	function limpar(){
		deleteAllRows();
		document.form.clear();
		document.getElementById('divOS').style.display = 'none';
		document.getElementById('divLinhasBase').innerHTML = '';
	}
	function verificaVinculoOS(obj){
		if (obj.checked){
			document.getElementById('divOS').style.display = 'block';
			document.form.fireEvent('carregaInfoOS');
		}else{
			document.getElementById('divOS').style.display = 'none';
			limparVinculacoesOS();
		}
	}
	function limparMudanca(){
		document.form.idRequisicaoMudanca.value = '';
		document.form.adicionarMudanca.value = '';
		/* LOOKUP_MUDANCA.setvalue('');
		LOOKUP_MUDANCA.settext(''); */
	}
	function limparLiberacao(){
		document.form.idLiberacao.value = '';
		document.form.adicionarLiberacao.value = '';
		/* LOOKUP_LIBERACAO.setvalue('');
		LOOKUP_LIBERACAO.settext(''); */
	}
	function registrarAutorizacao(idLnBase){
		document.formAutorizacao.idLinhaBaseProjeto.value = idLnBase;
		document.formAutorizacao.idProjetoAutorizacao.value = document.form.idProjeto.value;
		document.formAutorizacao.justificativaMudanca.value = '';
		$("#POPUP_REG_AUT_MUDANCA").dialog("open");
	}
	function salvarAutMudanca(){
		document.formAutorizacao.fireEvent('gravarAutorizMudanca');
	}
	function excluir() {
		if (document.getElementById("idProjeto").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.fireEvent("delete");
			}
		}
	}

	function somenteNumero(e){
		 var tecla= (window.event) ?event.keyCode:e.which;
		 if((tecla>47 && tecla<58))
			 return true;
		 else{
			 if (tecla==8 || tecla==0) return true;
			 	else
			 		return false;
		 }
	}

	function limparVinculacoesOS(){
		$('#idServicoContrato').val("");
		$('#nomeAreaRequisitante').val("");
		$('#numero').val("");
		$('#dataEmissao').val("");
		$('#demanda').val("");
		$('#objetivo').val("");
	}


