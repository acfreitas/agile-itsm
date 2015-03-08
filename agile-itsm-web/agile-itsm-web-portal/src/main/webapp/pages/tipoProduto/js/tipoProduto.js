	var contRelacionamento = 0;
	var objTab = null;
	var contFornecedor = 0;
	var contMarca = 0;

	addEvent(window, "load", load, false);
	function load() {


		$("#POPUP_CATEGORIAPRODUTO").dialog({
			autoOpen : false,
			width : 540,
			height : 400,
			modal : true
		});

		$("#POPUP_TIPOPRODUTORELACIONADO").dialog({
			autoOpen : false,
			width : 540,
			height : 400,
			modal : true
		});

		$("#POPUP_UNIDADEMEDIDA").dialog({
			autoOpen : false,
			width : 540,
			height : 400,
			modal : true
		});

		$("#POPUP_FORNECEDOR").dialog({
			autoOpen : false,
			width : 540,
			height : 400,
			modal : true
		});

		$("#POPUP_MARCA").dialog({
			autoOpen : false,
			width : 540,
			height : 400,
			modal : true
		});

		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);

		}

	};

	function gravar(){
		serializa();
		serializa2();
		document.form.save();
		contRelacionamento = 0;
	}


 	function serializa(){
 		var tabela = document.getElementById('tabelaTipoProdutoRelacionado');
 		var count = contRelacionamento + 1;
 		var listaDeRelacionamentos = [];
 		for(var i = 1; i < count ; i++){
 			if (document.getElementById('idTipoProdutoRelacionado' + i) != "" && document.getElementById('idTipoProdutoRelacionado' + i) != null){
 			var trObj = document.getElementById('idTipoProdutoRelacionado' + i).value;
 			var relacionamentoProduto = new RelacionamentoProdutoDTO(trObj, i);
 				listaDeRelacionamentos.push(relacionamentoProduto);
 			}
 		}
 		var ser = ObjectUtils.serializeObjects(listaDeRelacionamentos);
		document.form.relacionamentosSerializados.value = ser;
 	}

 	function serializa2(){
 		var tabela = document.getElementById('tabelaFornecedor');
 		var count = contFornecedor + 1;
 		var listaDeFornecedores = [];
 		for(var i = 1; i < count ; i++){
 			if (document.getElementById('idFornecedor' + i) != "" && document.getElementById('idFornecedor' + i) != null){
 			var trObj = document.getElementById('idFornecedor' + i).value;
 			var fornecedorProduto = new fornecedorProdutoDTO(trObj, i);
 				listaDeFornecedores.push(fornecedorProduto);
 			}
 		}
 		var ser = ObjectUtils.serializeObjects(listaDeFornecedores);
		document.form.fornecedoresSerializados.value = ser;
 	}


	function addLinhaTabelaTipoProdutoRelacionado(id, desc, valida){
		var tbl = document.getElementById('tabelaTipoProdutoRelacionado');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
		if (valida){
			if (!validaAddLinhaTabelaTipoProdutoRelacionado(lastRow, id)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contRelacionamento++;
		coluna.innerHTML = '<img id="imgDelTipoProdutoRelacionado' + contRelacionamento + '" style="cursor: pointer;" title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaTipoProdutoRelacionado\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = desc + '<input type="hidden" id="idTipoProdutoRelacionado' + contRelacionamento + '" name="idTipoProdutoRelacionado" value="' + id + '" />';
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input  type="radio" id="tipoRelacionamentoA' + contRelacionamento + '"  name="tipoRelacionamento' + contRelacionamento + '" value= "A" />'
		coluna = row.insertCell(3);
		coluna.innerHTML = '<input  type="radio" id="tipoRelacionamentoS' + contRelacionamento + '"  name="tipoRelacionamento' + contRelacionamento + '" value= "S" />';
			$("#POPUP_TIPOPRODUTORELACIONADO").dialog("close");

	}

	function addLinhaTabelaFornecedor(id, desc, valida){
		var tbl = document.getElementById('tabelaFornecedor');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
		if (valida){
			if (!validaAddLinhaTabelaFornecedor(lastRow, id)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contFornecedor++;
		coluna.innerHTML = '<img id="imgDelFornecedor' + contFornecedor + '" style="cursor: pointer;" title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaFornecedor\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = desc + '<input type="hidden" id="idFornecedor' + contFornecedor + '" name="idFornecedor" value="' + id + '" />' + '<input type="hidden" name="idFornecedorProduto' + contFornecedor + '" id="idFornecedorProduto' + contFornecedor + '" readonly="readonly"  />';
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input type="text" name="marca' + contFornecedor + '" id="marca' + contFornecedor + '" onclick="abrePopupMarca('+ contFornecedor +');" maxlength="50" size="50" />' + '<input type="hidden" name="idMarca' + contFornecedor + '" id="idMarca' + contFornecedor + '" readonly="readonly"  />'
		$("#POPUP_FORNECEDOR").dialog("close");

	}

 	function RelacionamentoProdutoDTO(_id, i){
 		if ($('#tipoRelacionamentoS' + i).is(":checked")){
 			this.tipoRelacionamento = 'S';
 		}
 		else {
 			this.tipoRelacionamento = 'A';
 		}
 		this.idTipoProdutoRelacionado = _id;
	}

 	function fornecedorProdutoDTO(_id, i){
 		this.idMarca = document.getElementById('idMarca' + i).value;
 		this.idFornecedor = _id;
	}


	function validaAddLinhaTabelaTipoProdutoRelacionado(lastRow, id){
		if (lastRow > 2){
			var arrayIdTipoProdutoRelacionado = document.form.idTipoProdutoRelacionado;
			for (var i = 0; i < arrayIdTipoProdutoRelacionado.length; i++){
				if (arrayIdTipoProdutoRelacionado[i].value == id || arrayIdTipoProdutoRelacionado[i].value == document.form.idTipoProduto){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}

			}
		} else if (lastRow == 2){
			var idTipoProdutoRelacionado = document.form.idTipoProdutoRelacionado.value;
			if (idTipoProdutoRelacionado == id || idTipoProdutoRelacionado == document.form.idTipoProduto.value){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		 else if (lastRow == 1){
				if (document.form.idTipoProduto.value == id ){
					alert(i18n_message("tipoProduto.naoRelacionar"));
					return false;
					}
			 }
		return true;
	}

	function validaAddLinhaTabelaFornecedor(lastRow, id){
		if (lastRow > 2){
			var arrayIdFornecedor = document.form.idFornecedor;
			for (var i = 0; i < arrayIdFornecedor.length; i++){
				if (arrayIdFornecedor[i].value == id){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}

			}
		} else if (lastRow == 2){
			var idFornecedor = document.form.idFornecedor.value;
			if (idFornecedor == id){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		return true;
	}

	function LOOKUP_TIPOPRODUTO_select(id, desc) {
		document.form.restore({
			idTipoProduto : id
		});
	}

	function atribuirChecked(valor, seq){
			if (valor == "S"){
				$('#tipoRelacionamentoS' + seq).attr('checked', true)
			}else{
				$('#tipoRelacionamentoA' + seq).attr('checked', true)
			}
		}

	function removeLinhaTabela(idTabela, rowIndex) {
		 if(idTabela == "tabelaTipoProdutoRelacionado"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.relacionamentoProduto.value = eval('document.form.idTipoProdutoRelacionado' + rowIndex + '.value');
					document.form.fireEvent("deleteTipoProdutoRelacionado");
					HTMLUtils.deleteRow(idTabela, rowIndex);
					document.form.relacionamentosSerializados.value = eval('document.form.idTipoProdutoRelacionado' + rowIndex + '.value');
				}
		 }
		  else{
			 if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fornecedor.value = eval('document.getElementById("idFornecedor' + rowIndex + '").value');
				document.form.fornecedorProduto.value = eval('document.getElementById("idFornecedorProduto' + rowIndex + '").value');
				document.form.fireEvent("deleteFornecedor");
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.fornecedoresSerializados.value = eval('document.getElementById("idFornecedor' + rowIndex + '").value');
				}
		 }
	}



	function deleteAllRows() {
		var tabela = document.getElementById('tabelaTipoProdutoRelacionado');
		var count = tabela.rows.length;
	 	var tabela1 = document.getElementById('tabelaFornecedor');
		var count1 = tabela1.rows.length

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		 	while (count1 > 1) {
				tabela1.deleteRow(count1 - 1);
				count1--;
		}
		contRelacionamento = 0;
		contFornecedor = 0;
	}

	function LOOKUP_CATEGORIAPRODUTO_select(id,desc){
		document.getElementById("idCategoria").value = id;
		document.getElementById("categoriaProduto").value = desc;
		$("#POPUP_CATEGORIAPRODUTO").dialog("close");
	};

	function LOOKUP_UNIDADEMEDIDA_select(id,desc){
		document.getElementById("idUnidadeMedida").value = id;
		document.getElementById("unidadeMedida").value = desc;
		$("#POPUP_UNIDADEMEDIDA").dialog("close");
	};

	function LOOKUP_MARCA_select(id,desc){
		document.getElementById("idMarca"+contMarca).value = id;
		document.getElementById("marca"+contMarca).value = desc;
		$("#POPUP_MARCA").dialog("close");
	};

	function setarMarca(id, desc, cont){
		document.getElementById("idMarca"+cont).value = id;
		document.getElementById("marca"+cont).value = desc;
	}

	function setarIdFornecedorProduto(id, cont){
		document.getElementById("idFornecedorProduto"+cont).value = id;
	}

	function abrePopupCategoriaProduto(){
		$("#POPUP_CATEGORIAPRODUTO").dialog("open");
	};

	function abrePopupMarca(cont){
		$("#POPUP_MARCA").dialog("open");
		contMarca = cont;
	};

	function abrePopupUnidadeMedida(){
		$("#POPUP_UNIDADEMEDIDA").dialog("open");
	};

	function excluir() {
		if (document.getElementById("idTipoProduto").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	function LOOKUP_TIPOPRODUTORELACIONADO_select(id, desc){
		addLinhaTabelaTipoProdutoRelacionado(id, desc, true);
		atribuirChecked('A', contRelacionamento);
	}

	function LOOKUP_FORNECEDOR_select(id, desc){
		addLinhaTabelaFornecedor(id, desc, true);
	}

	$(function() {
		$("#addTipoProdutoRelacionado").click(function() {
			$("#POPUP_TIPOPRODUTORELACIONADO").dialog("open");
		});
	});

	$(function() {
		$("#addFornecedor").click(function() {
			$("#POPUP_FORNECEDOR").dialog("open");
		});
	});
