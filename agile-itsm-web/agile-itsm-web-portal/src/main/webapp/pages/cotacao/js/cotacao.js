
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
	        document.getElementById('divEncerramento').style.display = 'block';
			carregarFrames();
		}
	}

	function selecionarAbaColetaPrecos() {
        JANELA_AGUARDE_MENU.hide();
		carregarFrames();
        //$('tabsFrame').tabs('select', 3);
	}

	var contFrame = 0;
	function carregarFrames() {
		contFrame = 3;
        exibeItensCotacao();
        exibeFornecedoresCotacao();
        exibeColetasPrecos();
    }

	function LOOKUP_COTACAO_select(id, desc) {
		document.form.restore({
			idCotacao :id});
	}

	function LOOKUP_COTACAO_ENCERRADAS_select(id, desc) {
		document.form.restore({
			idCotacao :id});
	}

    function exibeColetasPrecos() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraColetaPreco').src = ctx + '/pages/coletaPreco/coletaPreco.load?'+
            'idCotacao='+idCotacao;
        //window.frames["fraColetaPreco"].atualiza();
    }

    function atualizaColetaPrecos() {
    	JANELA_AGUARDE_MENU.show();
    	window.frames["fraColetaPreco"].atualiza();
    }

    function exibeItensCotacao() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraItemCotacao').src = ctx + '/pages/itemCotacao/itemCotacao.load?'+
            'idCotacao='+idCotacao;
    }

    function exibeFornecedoresCotacao() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraFornecedorCotacao').src = ctx + '/pages/fornecedorCotacao/fornecedorCotacao.load?'+
            'idCotacao='+idCotacao;
    }

    function exibeResultadoCotacao() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraResultadoCotacao').src = ctx + '/pages/resultadoCotacao/resultadoCotacao.load?'+
            'idCotacao='+idCotacao;
    }

    function exibeAprovacao() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraAprovacao').src = ctx + '/pages/consultaAprovacaoCotacao/consultaAprovacaoCotacao.load?'+
            'idCotacao='+idCotacao;
    }

    function exibePedidos() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraPedido').src = ctx + '/pages/pedidoCompra/pedidoCompra.load?'+
            'idCotacao='+idCotacao;
    }

    function exibeEntregas() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraEntrega').src = ctx + '/pages/entregaPedido/entregaPedido.load?'+
            'idCotacao='+idCotacao;
    }

    function escondeJanelaAguarde() {
        contFrame = contFrame - 1;
        if (contFrame <= 0)
            JANELA_AGUARDE_MENU.hide();
    }

    function limpar() {
        JANELA_AGUARDE_MENU.show();
    	document.form.clear();
    	document.getElementById('fraColetaPreco').src = 'about:blank';
    	document.getElementById('fraItemCotacao').src = 'about:blank';
    	document.getElementById('fraFornecedorCotacao').src = 'about:blank';
    	document.getElementById('fraResultadoCotacao').src = 'about:blank';
        document.getElementById('fraPedido').src = 'about:blank';
        document.getElementById('fraEntrega').src = 'about:blank';
        $('.tabs').tabs('select', 0);
        document.getElementById('divEncerramento').style.display = 'none';
        document.getElementById('retPesqLOOKUP_COTACAO').innerHTML = '';
        document.getElementById('retPesqLOOKUP_COTACAO_ENCERRADAS').innerHTML = '';
        JANELA_AGUARDE_MENU.hide();
    }

    function atualiza() {
    	JANELA_AGUARDE_MENU.hide();
        document.form.fireEvent('restore');
        carregarFrames();
    }

    function pesquisarRequisicoes() {
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		if(DateTimeUtil.isValidDate(dataInicio) == false){
			alert(i18n_message("citcorpore.comum.validacao.datainicio"));
		 	document.getElementById("dataInicio").value = '';
			return false;
		}
		if(DateTimeUtil.isValidDate(dataFim) == false){
			alert(i18n_message("citcorpore.comum.validacao.datafim"));
			document.getElementById("dataFim").value = '';
			return false;
		}

		if(validaData(dataInicio,dataFim)){
        	document.formItensRequisicao.fireEvent('pesquisaItensParaCotacao');
		}
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

    function gerarSelecaoItemRequisicao(row, obj){
        obj.selecionado = 'N';
        var detalhes = "<table>";
        detalhes += "<tr><td style='padding:4px 4px;'><b>"+i18n_message('calendario.descricao')+":<b></td><td>"+obj.descricaoItem+"</td></tr>";
        if (obj.idProduto != '' && obj.codigoProduto != '')
            detalhes += "<tr><td style='padding:4px 4px;'><b>"+i18n_message('centroResultado.codigo')+":<b></td><td>"+obj.codigoProduto+"</td></tr>";
        if (obj.idProduto == '' && obj.marcaPreferencial != '')
           detalhes += "<tr><td style='padding:4px 4px;'><b>"+i18n_message('itemRequisicaoProduto.marcaPreferencial')+":<b></td><td>"+obj.marcaPreferencial+"</td></tr>";
        if (obj.especificacoes != '')
               detalhes += "<tr><td style='padding:4px 4px;'><b>"+i18n_message('itemRequisicaoProduto.especificacoes')+":<b></td><td>"+obj.especificacoes+"</td></tr>";
        detalhes += "</table>";
        row.cells[0].innerHTML = "<img style='cursor: pointer;' src=ctx + '/imagens/viewCadastro.png' title=\"header=["+i18n_message('cotacao.detalhesDoItem')+"] body=[" + detalhes + "]\" />";
    }

    function encerrarCotacao() {
        if (confirm(i18n_message("cotacao.mensagemEncerramento")))
            document.form.fireEvent('encerra');
    }

