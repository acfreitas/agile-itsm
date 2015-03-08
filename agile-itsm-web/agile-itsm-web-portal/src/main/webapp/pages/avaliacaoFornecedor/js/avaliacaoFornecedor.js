	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	    document.form.afterLoad = function() {
	        document.form.dataAvaliacao.value = dataAvaliacao;
	        document.form.idResponsavel.value = idResponsavel;
	        document.form.nomeResponsavel.value = nomeResponsavel;
	    }
	}
	
	
	$(function() {

		// popup = new PopupManager(1100, 800, ctx+"/pages/"); 

		$("#POPUP_FORNECEDOR").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

		$("#POPUP_CRITERIOAVALIACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

		$("#POPUP_CRITERIO").dialog({
			autoOpen : false,
			width : 600,
			height : 350,
			modal : true
		});

		$("#POPUP_EMPREGADOS").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

		$("#POPUP_APROVACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 300,
			modal : true
		});

		$("#POPUP_RESPONSAVEL").dialog({
			title: 'Pesquisar Empregados',
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});


		$("#btnAddAprovacao").click(function() {
			document.formAprovacao.clear();
			$("#POPUP_APROVACAO").dialog("open");
		});

		$("#razaoSocial").click(function() {
			$("#POPUP_FORNECEDOR").dialog("open");
		});
	});

	function LOOKUP_AVALIACAOFORNECEDOR_select(id, desc) {
		document.form.restore({
			idAvaliacaoFornecedor : id
		});
	}



	function LOOKUP_FORNECEDOR_select(id, desc){
		document.form.idFornecedor.value = id;
		document.form.razaoSocial.value  = desc;
		document.form.fireEvent("preencheFornecedor");
	}

	function LOOKUP_EMPREGADO_select(id, desc){
		document.formAprovacao.idEmpregado.value = id;
		document.formAprovacao.fireEvent("preencheEmpregado");
	}

	function LOOKUP_CRITERIOAVALIACAO_select(id, desc){
		document.formCriterio.idCriterio.value = id;
		document.formCriterio.descricao.value  = desc;
		$("#POPUP_CRITERIOAVALIACAO").dialog("close");
	}

	function adicionarCriterio(){
		document.formCriterio.clear();
		$("#POPUP_CRITERIO").dialog("open");
	}

	function adicionarNome(){
		$("#POPUP_EMPREGADOS").dialog("open");
	}

	function adicionarDescricao(){
		$("#POPUP_CRITERIOAVALIACAO").dialog("open");
	}

	function fechaCriterio(){
		$("#POPUP_CRITERIO").dialog("close");
	}


	exibeIconesCriterio = function(row, obj) {
		obj.sequencia = row.rowIndex;

	    	row.cells[0].innerHTML = '<img src="'+caminho+'/imagens/edit.png" border="0" onclick="exibeCriterio('
	    			+ row.rowIndex + ')" style="cursor:pointer" />';
	    	row.cells[1].innerHTML = '<img src="'+caminho+'/imagens/excluirPeq.gif" border="0" onclick="excluiCriterio('
	    			+ row.rowIndex + ', this)" style="cursor:pointer" />';

	};

	exibeCriterio = function(indice) {
		document.formCriterio.clear();
		var obj = HTMLUtils.getObjectByTableIndex('tblCriterio', indice);
		var idCriterio = obj.idCriterio;
		HTMLUtils.setForm(document.formCriterio);
		HTMLUtils.setValues(document.formCriterio, null, obj);
		HTMLUtils.setForm(document.formCriterio);
		$("#POPUP_CRITERIO").dialog("open");
	};

	excluiCriterio = function(indice) {
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao") ) ) {
			HTMLUtils.deleteRow('tblCriterio', indice);
		}
	};

	function fechaAprovacao(){
		$("#POPUP_APROVACAO").dialog("close");
	}

	function fechaEmpregado(){
		$("#POPUP_EMPREGADOS").dialog("close");
	}

	exibeIconesAprovacao = function(row, obj) {
		obj.sequencia = row.rowIndex;

	    	row.cells[0].innerHTML = '<img src="'+caminho+'/imagens/edit.png" border="0" onclick="exibeAprovacao('
	    			+ row.rowIndex + ')" style="cursor:pointer" />';
	    	row.cells[1].innerHTML = '<img src="'+caminho+'/imagens/excluirPeq.gif" border="0" onclick="excluiAprovacao('
	    			+ row.rowIndex + ', this)" style="cursor:pointer" />';

	};

	exibeAprovacao = function(indice) {
		document.formAprovacao.clear();
		var obj = HTMLUtils.getObjectByTableIndex('tblAprovacao', indice);
		var idEmpregado = obj.idEmpregado;
		HTMLUtils.setForm(document.formAprovacao);
		HTMLUtils.setValues(document.formAprovacao, null, obj);
		HTMLUtils.setForm(document.formAprovacao);
		document.formAprovacao.idEmpregado.value = idEmpregado;
		$("#POPUP_APROVACAO").dialog("open");
	};

	excluiAprovacao = function(indice) {
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao") ) ) {
			HTMLUtils.deleteRow('tblAprovacao', indice);
		}
	};

	function gravar(){

		var dataAvaliacao = document.getElementById("dataAvaliacao").value;
		if(validaData){
			var objsCriterios = HTMLUtils.getObjectsByTableId('tblCriterio');
			document.form.listCriteriosQualidadeSerializado.value = ObjectUtils.serializeObjects(objsCriterios);

			var objsAprovacao = HTMLUtils.getObjectsByTableId('tblAprovacao');
			document.form.listAprovacaoReferenciaSerializado.value = ObjectUtils.serializeObjects(objsAprovacao);

			document.form.save();
		}



	}

	function adicionarResponsavel() {
		$("#POPUP_RESPONSAVEL").dialog("open");
	}

	function LOOKUP_RESPONSAVEL_select(id, desc) {
		document.getElementById("idResponsavel").value = id;
		document.getElementById("nomeResponsavel").value = desc;
		$("#POPUP_RESPONSAVEL").dialog("close");
	}

	function excluir() {
		if (document.getElementById("idAvaliacaoFornecedor").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	function limpar(){
		document.form.fireEvent("limpar");
		deleteAllRowsEscopo();
		document.form.clear();
		document.form.dataAvaliacao.value = dataAvaliacao;
        document.form.idResponsavel.value = idResponsavel;
        document.form.nomeResponsavel.value = nomeResponsavel;
	}

	function deleteAllRowsEscopo() {
		var tabela = document.getElementById('tblEscopo');
		try {
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			}
		}catch(e){}

	}

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
	
	