<%@page import="br.com.centralit.bpm.util.Enumerados"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.citframework.util.UtilHTML"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.HistoricoLiberacaoDTO"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.Aba"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
    String iframe = "";
    iframe = request.getParameter("iframe");

    String descStatus;
    String tarefaAssociada = (String)request.getAttribute("tarefaAssociada");
    if (tarefaAssociada == null){
    	tarefaAssociada = "N";
    }
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include  file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	<script type="text/javascript" src="${ctx}/cit/objects/RequisicaoMudancaDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/HistoricoLiberacaoDTO.js"></script>
	<script type="text/javascript" src="${ctx}/cit/objects/ProblemaDTO.js"></script>
	<script type="text/javascript" src="${ctx}/cit/objects/RequisicaoLiberacaoResponsavelDTO.js"></script>
	<script type="text/javascript" src="${ctx}/cit/objects/RequisicaoLiberacaoRequisicaoComprasDTO.js"></script>
	<script type="text/javascript" src="${ctx}/cit/objects/RequisicaoLiberacaoMidiaDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/HistoricoMudancaDTO.js"></script>

<style type="text/css">
	.table {
		border-left:1px solid #ddd;
	}

	.table th {
		border:1px solid #ddd;
		padding:4px 10px;
		border-left:none;
		background:#eee;
	}

	.table td {
		border:1px solid #ddd;
		padding:4px 10px;
		border-top:none;
		border-left:none;
	}

	.tableLess {
	  font-family: arial, helvetica !important;
	  font-size: 10pt !important;
	  cursor: default !important;
	  margin: auto !important;
	  background: white !important;
	  border-spacing: 0  !important;
	  width: 95%  !important;
	  min-height:100px !important;
	  background: #f5f5f5;
	  border: solid 1px #eee;
	}

	.tableLess tbody {
	  background: transparent  !important;
	}

	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}

	.tableLess thead th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}

	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}

	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9 ;
	  cursor: pointer;
	}

	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}

	.tableLess td{
	  border: 1px solid #BBB  !important;
	  padding: 6px 10px  !important;
	}

	.sel {
		display: none;
		background: none  !important;;
		cursor:auto;
		padding: 0;
		margin: 0;
	}
	.sel td {
		padding: 0;
		margin: 0;
	}

	.table {
		border-left:1px solid #ddd;
	}

	.table th {
		border:1px solid #ddd;
		padding:4px 10px;
		border-left:none;
		background:#eee;
	}

	.table td {
		border:1px solid #ddd;
		padding:4px 10px;
		border-top:none;
		border-left:none;
		text-align: center;
	}

	.sel-s {
	 	padding-left:20px !important;
		border: 0px !important;
		width: 50% !important;
	}
	.form{
		width: 100%;
		float: right;
	}

	.formHead{
		float: left;
		width: 99%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.formBody{
		float: left;
		width: 99%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.formRelacionamentos div{
		float: left;
		width: 99%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.formFooter{
		float: left;
		width: 99%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.divEsquerda{
		float: left;
		width: 47%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.divDireita{
		float: right;
		width: 47%;
		border: 1px solid #ccc;
		padding: 5px;
	}

	.ui-tabs .ui-tabs-nav li a{
		background-color: #fff !important;
	}

	.ui-state-active{
		background-color: #aaa ;
	}

	#tabs div{
		background-color: #fff;
	}


	.ui-state-hover{
		background-color: #ccc !important;
	}
	#contentBaseline {
		padding: 0 !important;
		margin: 0 !important;
		border: 0 !important;
	}
	.padd10 {
		padding: 0 10px;
	}
	.lFloat{
		float: left;
	}
	#divGrupoItemConfiguracao {
		display: none;
	}
	/* Desenvolvedor: Pedro Lino - Data: 25/10/2013 - Horário: 16:58 - ID Citsmart: 120948 -
	* Motivo/Comentário: box(botão) base de conhecimento pequeno/ Alterado altura do box */
	#barraFerramentasLiberacao ul li.li_menu {
		height: 65px!important;
	}

</style>
<script>
var acaoBotao = false;
var tabelaRelacionamentoICs;
var popup3;

	addEvent(window, "load", load, false);
	function load() {
		popup3 = new PopupManager(900, 450, "${ctx}/pages/");
		popupMidia = new PopupManager("98%" , $(window).height()-100, "${ctx}/pages/");
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
			initTabelasRelacionamentos();
		}
	}

	   function mostraMensagemInsercao(msg){
			document.getElementById('divMensagemInsercao').innerHTML = msg;
			$("#POPUP_INFO_INSERCAO").dialog("open");
		}
	   function mostraMensagemRestaurarBaseline(msg){
			document.getElementById('divMensagemInsercaoBaseline').innerHTML = msg;
			$("#POPUP_INFO_BASELINE").dialog("open");
		}

	//Adiciona os dados vindos da lookup, o ultimo parâmetro ele pega do fluxo , no caso o status de requisicaoliberacao
    function LOOKUP_MUDANCA_CONCLUIDA_SITUACAO_select(id, desc){
        var str = desc.split('-');
        addMudanca(id, str[0],str[1], $("#status option:selected").text());
        addICMudanca(id);
    }

    function LOOKUP_PROBLEMA_select(id, desc){
        var str = desc.split('-');
        addProblema(id, str[1], str[2]);
    }


    function LOOKUP_REQUISICAOCOMPRAS_select(id, desc){
    	document.getElementById("idItemRequisicaProduto").value = id;
    	document.form.fireEvent("addDadosItemProdutos");


/*         var str = desc.split('-');
		var banco = document.getElementById("SGBD").value.toLowerCase();
		if(banco != "sqlserver")
        	addRequisicaoCompras(id, str[1], str[2]);
		else
			addRequisicaoCompras(id, str[0], str[1]); */
    }

    function LOOKUP_EMPREGADO_select(id, desc){
    	document.form.idSolicitante.value = id;
        document.form.nomeSolicitante.value = desc;
        $("#POPUP_EMPREGADO").dialog("close");
        document.form.fireEvent("carregaDadosContato");
        document.form.fireEvent("preencherComboGrupoAprovador");

	}

    function escondeJanelaAguarde() {
        JANELA_AGUARDE_MENU.hide();
    }

	function LOOKUP_LIBERACAO_select(id, desc) {
		document.form.restore({idLiberacao : id});
	}

	function gravar(){
		var dataInicio = document.getElementById("dataInicial").value;
		var dataFim = document.getElementById("dataFinal").value;
		var dataLiberacao = document.getElementById("dataLiberacao").value;
		var mudancas_serialize = document.getElementById('mudancas_serialize').value;
		if (dataInicio != ""){
			if(DateTimeUtil.isValidDate(dataInicio) == false){
				alert(i18n_message("citcorpore.comum.validacao.datainicio"));
			 	document.getElementById("dataInicial").value = '';
				return false;
			}
		}

		if (dataFim != "") {
			if (DateTimeUtil.isValidDate(dataFim) == false) {
				alert(i18n_message("citcorpore.comum.validacao.datafim"));
				document.getElementById("dataFinal").value = '';
				return false;
			}
		}
		if (dataLiberacao != ""){
			if(DateTimeUtil.isValidDate(dataLiberacao) == false){
				alert(i18n_message("MSG03"));
			 	document.getElementById("dataLiberacao").value = '';
				return false;
			}
		}

		if (document.getElementById('dataInicial').value != '' && document.getElementById('horaAgendamentoInicial').value == '') {
			alert(i18n_message("requisicaoLiberacao.informacaoHoraInicialPlanejada"));
			document.getElementById('horaAgendamentoInicial').focus();
			return;
		}

		if (document.getElementById('dataFinal').value != '' && document.getElementById('horaAgendamentoFinal').value == '') {
			alert(i18n_message("requisicaoLiberacao.informacaoHoraFinalPlanejada"));
			document.getElementById('horaAgendamentoFinal').focus();
			return;
		}

		serializa();

		var informacoesComplementares_serialize = '';
		try{
			informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
		}catch(e){}
		document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;


		if(validaDataParametrizado(dataInicio,dataFim)){
			serializa();
 			var mudancas = HTMLUtils.getObjectsByTableId('tblMudancas');
 			 if(mudancas == ""){
  				alert("<fmt:message key="requisicaoLiberacao.vincularMudanca" />");
 				$('.tabs').tabs('select', 0);
 				document.getElementById("relacionarMudancas").focus();
 				return;
			}else{
				document.form.save();
 			 }
		}


	}

	$(function() {
		   $('.datepicker').datepicker();
		   $('#telefoneContato').mask('(999) 9999-9999');
	  });

	fechar = function(){
			parent.fecharVisao();
		}

	//Atividades  do Fluxo
    function gravarEFinalizar() {
		document.form.acaoFluxo.value = '<%=Enumerados.ACAO_EXECUTAR%>';
		gravar();
    }
	//Atividades  do Fluxo
 	function gravarEContinuar() {
		document.form.acaoFluxo.value = '<%=Enumerados.ACAO_INICIAR%>';
		gravar();
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

	function addProblema(id, desc, stat){
        var obj = new CIT_ProblemaDTO();
        document.getElementById('problema#idProblema').value = id;
        document.getElementById('problema#titulo').value = desc;
        document.getElementById('problema#status').value = stat;
        HTMLUtils.addRow('tblProblema', document.form, 'problema', obj,
                ["","idProblema","titulo","status"], ["idProblema"], "Problema já adicionado", [gerarImgDelProblema], null, null, false);
		$("#POPUP_PROBLEMA").dialog("close");
	}

	//geber.costa
	var teste = '';
	function alterarFechamentoMudanca(id){
		$("#POPUP_MUDANCA_FECHAMENTO_CATEGORIA").dialog("open");
		if(acaoBotao == false){
			 //document.form.fireEvent("divFinalizacaoCategoria");
			 $('#linha').val(id);
	        $("#POPUP_MUDANCA").dialog("close");
		}
	}

	/* function deleteLinha(table, index){
		if (confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
		teste = 'true';
		HTMLUtils.deleteRow(table, index);
		return;
		}
		teste = '';
	} */
	function deleteLinha(table, index){
		acaoBotao = true;
		if (confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
			teste = 'true';
			HTMLUtils.deleteRow(table, index);
			return;
		}
		teste = '';
	}

	 function gerarImgDelProblema(row, obj){
	        row.cells[0].innerHTML = '<img src="${ctx}/imagens/delete.png" style="cursor: pointer;" onclick="excluirProblema('+row.rowIndex+')"/>';
	    };

    function gerarImgDel(row, obj){
        row.cells[0].innerHTML = '<img src="${ctx}/imagens/delete.png" style="cursor: pointer;" onclick="deleteLinha(\'tblMudancas\', this.parentNode.parentNode.rowIndex);"/>';
    };

    function gerarImgDelIc(row, obj){
        row.cells[0].innerHTML = '<img src="${ctx}/imagens/delete.png" style="cursor: pointer;" onclick="deleteLinha(\'tblICs\', this.parentNode.parentNode.rowIndex);"/>';
    };

    function gerarImgDelResponsavel(row, obj){
        row.cells[0].innerHTML = '<img src="${ctx}/imagens/delete.png" style="cursor: pointer;" onclick="deleteLinha(\'tblResponsavel\', this.parentNode.parentNode.rowIndex);"/>';
    };

    function excluirMudanca(linha) {
        if (linha > 0 && confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
            HTMLUtils.deleteRow('tblMudancas', linha);
        }
    }

    function excluirProblema(linha) {
        if (linha > 0 && confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
            HTMLUtils.deleteRow('tblProblema', linha);
        }
    }

 	function serializa(){
        var mudancas = HTMLUtils.getObjectsByTableId('tblMudancas');
		document.form.mudancas_serialize.value =  ObjectUtils.serializeObjects(mudancas);
		var problemas = HTMLUtils.getObjectsByTableId('tblProblema');
		document.form.problemas_serialize.value =  ObjectUtils.serializeObjects(problemas);

		var midias = HTMLUtils.getObjectsByTableId('tblMidia');
		document.form.midias_serialize.value =  ObjectUtils.serializeObjects(midias);

		var responsavel = HTMLUtils.getObjectsByTableId('tblResponsavel');
		document.form.responsavel_serialize.value =  ObjectUtils.serializeObjects(responsavel);

		var requisicaoCompra = HTMLUtils.getObjectsByTableId('tblRequisicaoCompra');
		document.form.requisicaoCompras_serialize.value =  ObjectUtils.serializeObjects(requisicaoCompra);

		var objIc = HTMLUtils.getObjectsByTableId('tblICs');
		document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(objIc);
 	}


	$(function() {
		$("#POPUP_EMPREGADO").dialog({
			autoOpen : false,
			width : 600,
			height : 530,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

	$(function() {
		$("#POPUP_MUDANCA").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});



		$("#POPUP_PROBLEMA").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});

		$("#POPUPITEMCONFIGURACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});

		$("#POPUP_MIDIA").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});

		//geber.costa
		$("#POPUP_MUDANCA_FECHAMENTO_CATEGORIA").dialog({
			autoOpen : false,
			width : 320,
			height : 200,
			modal : true,
			show: "fade",
			hide: "fade"
		});

		//geber.costa
		$("#POPUP_MUDANCA_CADASTRAR_SITUACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});


		$("#POPUP_RESPONSAVEL").dialog({
			autoOpen : false,
			width : 850,
			height : 500,
			modal : true,
			show: "fade",
			hide: "fade"
		});

		$("#POPUP_INFO_INSERCAO").dialog({
			autoOpen : false,
			width : 400,
			height : 280,
			modal : true,
			close: function(event, ui) {
				fechar();
			}
		});
		$("#POPUP_INFO_BASELINE").dialog({
			autoOpen : false,
			width : 400,
			height : 280,
			modal : true,
			close: function(event, ui) {
			}
		});

			$("#POPUP_REQUISICAOCOMPRAS").dialog({
			autoOpen : false,
			width : 910,
			height : 600,
			modal : true,
			show: "fade",
			hide: "fade"
		});

			$("#POPUP_PLANOSREVERSAO").dialog({
			autoOpen : false,
			width : 910,
			height : 600,
			modal : true,
			show: "fade",
			hide: "fade",
			buttons: {
				<fmt:message key="citcorpore.comum.gravar" />: function() {
		          gravarAnexosPlanosReversao();
		          $( this ).dialog( "close" );
			        }
				}
		});

		$("#POPUP_PESQUISAITEMCONFIGURACAO").dialog({
				autoOpen : false,
				width : 1200,
				height : 650,
				modal : true
		});

		$("#nomeSolicitante").click(function() {
			if (document.form.idContrato.value == ''){
				alert('Informe o contrato!');
				return;
			}

			//var v = document.getElementsByName("btnTodosLOOKUP_EMPREGADO");
			//v[0].style.display = 'none';
			//var y = document.getElementsByName("btnLimparLOOKUP_EMPREGADO");
			//y[0].style.display = 'none';

			$("#POPUP_EMPREGADO").dialog("open");
		});
		initTabelasRelacionamentos();
	});

    function adicionarMudanca() {
		$("#POPUP_MUDANCA").dialog("open");
	};

	function limpar() {
        HTMLUtils.deleteAllRows('tblMudancas');
		document.form.clear();
		//fechar();
	}

	function adicionarMudanca(){
		$("#POPUP_MUDANCA").dialog("open");
	}

	function adicionarProblema(){
		$("#POPUP_PROBLEMA").dialog("open");
	}

    function excluir() {
        if (confirm(i18n_message("citcorpore.comum.confirmaExclusao")))
            document.form.fireEvent('delete');
    }
    function mostrarEscondeRegExec(){
		if (document.getElementById('divMostraRegistroExecucao').style.display == 'none'){
			document.getElementById('divMostraRegistroExecucao').style.display = 'block';
			document.getElementById('lblMsgregistroexecucao').style.display = 'block';
			document.getElementById('btnAddRegExec').innerHTML = '<fmt:message key="solicitacaoServico.addregistroexecucao_menos" />';
		}else{
			document.getElementById('divMostraRegistroExecucao').style.display = 'none';
			document.getElementById('lblMsgregistroexecucao').style.display = 'none';
			document.getElementById('btnAddRegExec').innerHTML = '<fmt:message key="solicitacaoServico.addregistroexecucao_mais" />';
		}
	}

	/** INFLUENCIA PRIORIDADE */
	function atualizaPrioridade(){

		var impacto = document.getElementById('nivelImpacto').value;
		var urgencia = document.getElementById('nivelUrgencia').value;

		if (urgencia == "B"){
			if (impacto == "B"){
				document.form.prioridade.value = 5;
			}else if (impacto == "M"){
				document.form.prioridade.value = 4;
			}else if (impacto == "A"){
				document.form.prioridade.value = 3;
			}
		}


		if (urgencia == "M"){
			if (impacto == "B"){
				document.form.prioridade.value = 4;
			}else if (impacto == "M"){
				document.form.prioridade.value = 3;
			}else if (impacto == "A"){
				document.form.prioridade.value = 2;
			}
		}

		if (urgencia == "A"){
			if (impacto == "B"){
				document.form.prioridade.value = 3;
			}else if (impacto == "M"){
				document.form.prioridade.value = 2;
			}else if (impacto == "A"){
				document.form.prioridade.value = 1;
			}
		}
	}

	/** INFLUENCIA PRIORIDADE */
	function atualizaPrioridade(){

		var impacto = document.getElementById('nivelImpacto').value;
		var urgencia = document.getElementById('nivelUrgencia').value;

		if (urgencia == "B"){
			if (impacto == "B"){
				document.form.prioridade.value = 5;
			}else if (impacto == "M"){
				document.form.prioridade.value = 4;
			}else if (impacto == "A"){
				document.form.prioridade.value = 3;
			}
		}


		if (urgencia == "M"){
			if (impacto == "B"){
				document.form.prioridade.value = 4;
			}else if (impacto == "M"){
				document.form.prioridade.value = 3;
			}else if (impacto == "A"){
				document.form.prioridade.value = 2;
			}
		}

		if (urgencia == "A"){
			if (impacto == "B"){
				document.form.prioridade.value = 3;
			}else if (impacto == "M"){
				document.form.prioridade.value = 2;
			}else if (impacto == "A"){
				document.form.prioridade.value = 1;
			}
		}
	}
/*  function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {

		 var camposLookupServico = desc.split("-");
		addLinhaTabelaItemConfiguracao(id, desc, true);

	} */

    function adicionarIC(){
		abrePopupIcs();
	}

    function abrePopupIcs(){
		$("#POPUPITEMCONFIGURACAO").dialog("open");
	}

/*     function addLinhaTabelaItemConfiguracao(id, desc, valida){
		var tbl = document.getElementById('tblICs');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
		if (valida){
			if (!validaAddLinhaTabelaItemConfiguracao(lastRow, id)){
				return;
			}
		}

		tabelaRelacionamentoICs.addObject([id, desc, prompt(i18n_message("requisicaoLiberacao.descricaoItem"))]);

		document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());

		$("#POPUPITEMCONFIGURACAO").dialog("close");

	} */

/*     function serializaItenIc(tabelaRelacionamentoICs){

    	 var objIc = HTMLUtils.getObjectsByTableId('tblICs');
		document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(objIc)

		$("#POPUPITEMCONFIGURACAO").dialog("close");
    } */

  /*   function validaAddLinhaTabelaItemConfiguracao(lastRow, id){
		var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);

		if (lastRow > 1){
			for(var i = 0; i < listaICs.length; i++){
				if (listaICs[i].idItemConfiguracao == id){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}
			}
		}
		return true;
	} */

	function initTabelasRelacionamentos(){
		//ICs
		<%-- tabelaRelacionamentoICs = new CITTable("tblICs",["idItemConfiguracao", "nomeItemConfiguracao", "descricao", "nomeStatusIc"],[]);
		tabelaRelacionamentoICs.setInsereBotaoExcluir(true, "${ctx}/imagens/delete.png"); --%>

		//Solicitacaoservico
		tabelaRelacionamentoSolicitacaoServico = new CITTable("tblSolicitacaoServico",["idSolicitacaoServico", "nomeServico"],[]);
		tabelaRelacionamentoSolicitacaoServico.setInsereBotaoExcluir(true, "${ctx}/imagens/delete.png");

		//servicos
		tabelaRelacionamentoServicos = new CITTable("tblServicos",["idServico", "Nome", "Mapa", "Descrição"],[]);
		tabelaProblema = new CITTable("tblProblema",["idProblema", "titulo", "status"],[]);
		tabelaRelacionamentoServicos.setInsereBotaoExcluir(true, "${ctx}/imagens/delete.png");
		tabelaProblema.setInsereBotaoExcluir(true, "${ctx}/imagens/delete.png");

		//Papeis e Responsabilidades
		tabelaRelacionamentoServicos = new CITTable("tblResponsavel", ["idResponsavel", "nomeResponsavel",  "papelResponsavel", "telResponsavel", "emailResponsavel"], []);
		tabelaRelacionamentoServicos.setInsereBotaoExcluir(true, "${ctx}/imagens/delete.png");



		//Requisicao de Compras
		tabelaRelacionamentoServicos = new CITTable("tblRequisicaoCompra", ["idSolicitacaoServico",  "nomeServico", "situacaoServicos"], []);
		tabelaRelacionamentoSolicitacaoServico.setInsereBotaoExcluir(true, "${ctx}/imagens/delete.png");

	}

	function limpaListasRelacionamentos(){
		tabelaRelacionamentoICs.limpaLista();
		tabelaRelacionamentoServicos.limpaLista();
		tabelaProblema.limpaLista();
		tabelaRelacionamentoSolicitacaoServico.limpaLista();

	}

/* 	addGridIcs = function(id, desc) {
   	 if(StringUtils.isBlank(document.form.nomeVersaoText.value) || document.form.nomeVersaoText.value == null){
   		alert(i18n_message("citcorpore.controleContrato.versao"));
   		document.form.nomeVersao.focus();
   		return;
   	}

       if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
           var obj = new CIT_RequisicaoLiberacaoItemConfiguracaoDTO();
           obj.idItemConfiguracao = id;
           obj.nomeItemConfiguracao = desc;

           HTMLUtils.addRow('tblICs', document.form, null, obj, ['idItemConfiguracao','nomeCcVersao', ''], null, '', [gerarButtonDeleteVersao], funcaoClickRowVersao, null, false);
           document.form.nomeVersaoText.value = "";
           novoItem();
       }
       HTMLUtils.applyStyleClassInAllCells('tblICs', 'celulaGrid');
	} */

	 /**
	  * Renderiza tabela a partir de lista.
	  * @param _idCITTable id da tabela a ser tratada
	  * @param _fields Lista de campos correspondentes ao banco de dados
	  * @param _tableObjects Lista de itens. Deve corresponder aos campos de _fields
	  */
	 function CITTable(_idCITTable, _fields, _tableObjects){
			var self = this;
			var idCITTable = _idCITTable;
			var fields = _fields;
			var tableObjects = _tableObjects;
			var tabela = null;

			var insereBtExcluir = true;
			var imgBotaoExcluir;

			this.onDeleteRow = function(deletedItem){};

			this.getTableList = function(){
				return tableObjects;
			}

			/**
			 * Transforma a lista da tabela em uma lista de objetos
			 * de acordo com o 'fields' passado.
			 */
			this.getTableObjects = function(){
				var objects = [];
				var object = {};

				for(var j = 0; j < tableObjects.length; j++){
					for(var i = 0 ; i < fields.length; i++){
						eval("object." + fields[i] + " = '" + tableObjects[j][i] + "'");
					}
					objects.push(object);
					object = {};
				}

				return objects;
			}

			this.setTableObjects = function(objects){
				tableObjects = objects;
				this.montaTabela();
			}

			this.addObject = function(object){
				tableObjects.push(object);
				this.montaTabela();
			}

			this.limpaLista = function(){
				tableObjects.length = 0;
				tableObjects = null;
				tableObjects = [];
				limpaTabela();
			}

			var limpaTabela = function(){
				while (getTabela().rows.length > 1){
					getTabela().deleteRow(1);
				}
			}

			this.montaTabela = function(){
				var linha;
				var celula;

				limpaTabela();

				for(var i = tableObjects.length - 1; i >= 0; i--){

					var j = 0;
					linha = getTabela().insertRow(1);

					for(j = 0; j < fields.length; j++){
						celula = linha.insertCell(j);

						//tratamento caso seja um componente ao invés de texto
						try{
							celula.appendChild(tableObjects[i][j]);
						}catch(e){
							celula.innerHTML = tableObjects[i][j];
						}
					}

					if(insereBtExcluir){
						var btAux = getCopiaBotaoExcluir();
						var celExcluir = linha.insertCell(j);

						btAux.setAttribute("id", i);
						btAux.addEventListener("click", function(evt){
							//ao disparar o evento, considerará o id do botão
							self.removeObject(this.id);
							this.onDeleteRow(this);

						}, false);
						celExcluir.appendChild(btAux);
					}
				}
			}

			this.removeObject = function(indice){
				removeObjectDaLista(indice);
				this.montaTabela();
			}

			/**
			 * Remove item e organiza lista
			 */
			var removeObjectDaLista = function(indice){
				tableObjects[indice] = null;
				var novaLista = [];
				for(var i = 0 ; i < tableObjects.length; i++){
					if(tableObjects[i] != null){
						novaLista.push(tableObjects[i]);
					}
				}
				tableObjects = novaLista;
			}

			var getCopiaBotaoExcluir = function(){
				var novoBotao = new Image();
				novoBotao.setAttribute("style", "cursor: pointer;");
				novoBotao.src = imgBotaoExcluir;
				return novoBotao;
			}

			var setImgPathBotaoExcluir = function(src){
				imgBotaoExcluir = src;
			}

			var getTabela = function(){
				if(tabela == null){
					tabela = document.getElementById(idCITTable);
				}
				return tabela;
			}

			this.setInsereBotaoExcluir = function(bool, imgSrc){
				insereBtExcluir = bool;
				setImgPathBotaoExcluir(imgSrc);
			}
		}

	 /*Gravar baseline*/
		function gravarBaseline() {
			var tabela = document.getElementById('tblBaselines');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var baselines = new Array();

			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idHistoricoMudanca' + i);
				if (!trObj) {
					continue;
				}
				baselines[contadorAux] = getbaseline(i);
				contadorAux = contadorAux + 1;
			}
			serializaBaseline();
			document.form.fireEvent("saveBaseline");
		}

		var seqBaseline = '';
		var aux = '';
		serializaBaseline = function() {
			var tabela = document.getElementById('tblBaselines');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var baselines = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idHistoricoMudanca' + i);
				if (!trObj) {
					continue;
				}else if(trObj.checked){
					baselines[contadorAux] = getbaseline(i);
					contadorAux = contadorAux + 1;
					continue;
				}

			}
		var baselinesSerializadas = ObjectUtils.serializeObjects(baselines);
			document.form.baselinesSerializadas.value = baselinesSerializadas;
			return true;
		}

		getbaseline = function(seq) {
			var HistoricoMudancaDTO = new CIT_HistoricoMudancaDTO();
			HistoricoMudancaDTO.sequencia = seq;
			HistoricoMudancaDTO.idHistoricoMudanca = eval('document.form.idHistoricoMudanca' + seq + '.value');
			return HistoricoMudancaDTO;
		}
		function marcarCheckbox(elementos){
			var arrayIds = new Array();
			arrayIds = elementos;
			for ( var i = 0; i <= arrayIds.length; i++) {
				var posicao = arrayIds[i];
				$("#posicao").attr("checked",true);
			}

		}

		function restaurarHistorico(id){
			document.form.idHistoricoMudanca.value = id;
			if(confirm(i18n_message("itemConfiguracaoTree.restaurarVersao")))
				document.form.fireEvent("restaurarBaseline");
		}

		function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {
			document.form.nomeItemConfig.value = desc;
			document.form.idItemConfig.value = id;
			document.form.idItemConfig.disabled=false;
			document.form.fireEvent('addICs');
		}

		exibeIconesIC = function(row, obj) {
			var id = obj.idItemConfiguracao;
			//if(obj.idItemConfiguracaoPai == ""){
				row.cells[4].innerHTML = '<img src="${ctx}/template_new/images/icons/small/grey/graph.png" border="0" onclick="popupAtivos( '+ id + ')" style="cursor:pointer"/>';
			//}
		}

		function inventario() {
			document.getElementById('iframeAtivos').src ='${ctx}/pages/inventarioNew/inventarioNew.load?iframe=' + true;
			$("#POPUP_ATIVOS").dialog("open");
		}

		function popupAtivos(idItem){
			document.getElementById('iframeAtivos').src ='${ctx}/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
			$("#POPUP_ATIVOS").dialog("open");
		}

		function fechaPopupAtivos(){

			setTimeout($('#POPUP_ATIVOS').dialog('close'),1000);
		}

		$(function() {
			$("#POPUP_ATIVOS").dialog({
				autoOpen : false,
				width : 1005,
				height : 565,
				modal : true
			});
		});

/* 		function addLinhaTabelaItemConfiguracao(id, desc, valida, status){
			var tbl = document.getElementById('tblICs');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaItemConfiguracao(lastRow, id)){
					return;
				}
			}

		 	tabelaRelacionamentoICs.addObject([id, desc, prompt(i18n_message("requisicaoMudanca.oQueSeraMudadoNesteItem")), status]);

			document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());

			$("#POPUPITEMCONFIGURACAO").dialog("close");

		} */

	/* 	function validaAddLinhaTabelaItemConfiguracao(lastRow, id){
			var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaICs.length; i++){
					if (listaICs[i].idItemConfiguracao == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;
		} */

	/* 	function restaurar(){
			var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);
			limpaListasRelacionamentos();

			for(var i = 0; i < listaICs.length; i++){
				tabelaRelacionamentoICs.addObject([listaICs[i].idItemConfiguracao, listaICs[i].nomeItemConfiguracao , listaICs[i].descricao, listaICs[i].nomeStatusIc ]);
			}
// 			document.form.itensConfiguracaoRelacionadosSerializado.value = "";
		} */



		function limpaListasRelacionamentos(){
			tabelaRelacionamentoICs.limpaLista();
		}

		function setaValorLookup(obj){
//  		limpar_LOOKUP_SOLICITANTE();
// 			document.form.idSolicitante.value = '';
// 			document.form.solicitante.value = '';
// 			document.form.emailcontato.value = '';
// 			document.form.nomecontato.value = '';
// 			document.form.telefonecontato.value = '';
// 			document.form.observacao.value = '';
// 			document.form.ramal.value = '';
// 			document.getElementById('idTipoDemandaServico').options[0].selected = 'selected';
// 			document.form.servicoBusca.value = '';
			document.getElementById('pesqLockupLOOKUP_EMPREGADO_IDCONTRATO').value = '';
			document.getElementById('pesqLockupLOOKUP_EMPREGADO_IDCONTRATO').value = obj.value;
		}

		//MIDIA SOFTWARE
		function adicionarMidia(){
			$("#POPUP_MIDIA").dialog("open");
		}

	    function LOOKUP_MIDIASOFTWARE_select(id, desc){
	    	  document.getElementById('midia#idMidiaSoftware').value = id;
	    	  document.form.fireEvent("restaurarMidiaSoftware");
	    }

		exibeIconesMidia = function(row, obj){
			var id = obj.idMidiaSoftware;
	        obj.sequenciaLiberacao = row.rowIndex;
		    row.cells[0].innerHTML = '<img src="${ctx}/imagens/delete.png" border="0" onclick="excluiMidia('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
		}

		excluiMidia = function(indice) {
			if (indice > 0 && confirm('Confirma exclusão')) {
				HTMLUtils.deleteRow('tblMidia', indice);
			}
		}

		function fecharMidia(){
			$("#POPUP_MIDIA").dialog("close");
		}


	    //Questionario
		    function carregaQuestionario(idTipoAba){
		    	document.form.idTipoAba.value = idTipoAba;
		    	document.form.fireEvent("carregaQuestionario");
		    }
	    //add responsavel

	    	/**
			 *Alterado pois a rotina usando citTable nao estava funcionando.
			 *
			 * @author maycon.fernandes
			 * 31/10/2013 18:47
			 */

	     function LOOKUP_RESPONSAVEL_select(id, desc){
	    	 document.getElementById('responsavel#idResponsavel').value = id;
	    	 addResponsavel();
	     }

	    function adicionarResponsavel(){
			$("#POPUP_RESPONSAVEL").dialog("open");
		}

		exibeIconesResponsavel = function(row, obj){
			var id = obj.idResponsavel;
	        obj.sequenciaLiberacao = row.rowIndex;
		    row.cells[0].innerHTML = '<img src="${ctx}/imagens/delete.png" border="0" onclick="excluiResponsavel('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
		}

		excluiResponsavel = function(indice) {
			if (indice > 0 && confirm('Confirma exclusão')) {
				HTMLUtils.deleteRow('tblResponsavel', indice);
			}
		}

		function fecharResponsavel(){
			$("#POPUP_RESPONSAVEL").dialog("close");
		}

	    function addResponsavel(){
	        var papel =  prompt(i18n_message("citcorpore.comum.papel"),"");
	        if(papel.length <= 180){
	        	document.getElementById('responsavel#papelResponsavel').value = papel;
	        }else{
	        	alert(i18n_message("requisicaoLiberacao.limiteCaracter"));
	        	return;
	        }

	        document.form.fireEvent('restaurarResponsavel');
		}

	    function excluirResponsavel(linha) {
	        if (linha > 0 && confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
	            HTMLUtils.deleteRow('tblResponsavel', linha);
	        }
	    }

	    function adicionarResponsavel(){
			$("#POPUP_RESPONSAVEL").dialog("open");
		}



	    //add Requisicao de Compras
	   function addRequisicaoCompras(id, nomeservico, situacaoservico ){
	        var obj = new CIT_RequisicaoLiberacaoRequisicaoComprasDTO();


	        document.getElementById('requisicaoCompras#idSolicitacaoServico').value = id;
	        document.getElementById('requisicaoCompras#nomeServico').value = nomeservico;
	        document.getElementById('requisicaoCompras#situacaoServicos').value = situacaoservico;

	        HTMLUtils.addRow('tblRequisicaoCompra', document.form, 'requisicaoCompras', obj, ["","idSolicitacaoServico", "nomeServico", "situacaoServicos"], ["idSolicitacaoServico"], null, [gerarImgDelRequisicaoCompras], null, null, false);
	    	$("#POPUP_REQUISICAOCOMPRAS").dialog("close");
		}

	    function adicionarRequisicaoCompras(){
	    	$("#POPUP_REQUISICAOCOMPRAS").dialog("open");
	    }

	    function excluirRequisicaoCompras(linha) {
	        if (linha > 0 && confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
	            HTMLUtils.deleteRow('tblRequisicaoCompra', linha);
	        }
	    };

	    function gerarImgDelRequisicaoCompras(row, obj){
	        row.cells[0].innerHTML = '<img src="${ctx}/imagens/delete.png" style="cursor: pointer;"  onclick="deleteLinha(\'tblRequisicaoCompra\', this.parentNode.parentNode.rowIndex);"/>';
	    };

	  //geber.costa
	    function gerarImgpopup(row, obj){
	    row.cells[5].innerHTML = '<img src="${ctx}/imagens/edit.png" style="cursor: pointer;" onclick="alterarFechamentoMudanca('+row.rowIndex+')"/>';
	    };
	  //murilo.pacheco
	    function geraImgPlanoReversao(row, obj){
	    row.cells[6].innerHTML = '<img src="${ctx}/template_new/images/icons/small/grey/paperclip.png" style="cursor: pointer;" onclick="listarPlanosDeReversao('+obj.idRequisicaoMudanca+')"/>';
	    };


	    /**
	    	INFORMAÇÕES COMPLEMENTARES (TEMPLATE/QUESTIONARIO)
	    **/
	    function exibirInformacoesComplementares(url) {
            if (url != '') {
                /* JANELA_AGUARDE_MENU.show(); */
                //document.getElementById('divInformacoesComplementares').style.display = 'block';
                document.getElementById('fraInformacoesComplementares').src = '${ctx}'+url;

            }else{
                try{
                	escondeJanelaAguarde();
                }catch (e) {
                }
                document.getElementById('divInformacoesComplementares').style.display = 'none';
            }
        }

	    function addICMudanca(id){
	    	document.form.idICMudanca.value = id;
	    	document.form.fireEvent("relacionaICMudanca");
	    }

	    //geber.costa
	    var fechamento = "";
	    function setarFechamentoCategoria(){
	    	fechamento = document.getElementById('idFechamentoCategoria').value;
	    	if(fechamento==""){
	    		alert(i18n_message("situacaoLiberacaoMudanca.selecione"));
	    		return;
	    	}
	    	 var obj = HTMLUtils.getObjectByTableIndex('tblMudancas', document.getElementById('rowIndex').value);
		        document.getElementById('mudanca#idRequisicaoMudanca').value = obj.idRequisicaoMudanca;
	              document.getElementById('mudanca#titulo').value = obj.titulo;
	              document.getElementById('mudanca#status').value =  obj.status;
	              document.getElementById('mudanca#situacaoLiberacao').value =  fechamento;
		        HTMLUtils.updateRow('tblMudancas', document.form, 'mudanca', obj, ["", "idRequisicaoMudanca", "titulo","status","situacaoLiberacao","",""], null, '', [gerarImgDel, gerarImgpopup, geraImgPlanoReversao], funcaoClickRowMudanca, null, document.getElementById('rowIndex').value, false);

	    	$("#POPUP_MUDANCA_FECHAMENTO_CATEGORIA").dialog("close");

	    }

	     //geber.costa
	     //adiciona uma linha na tabela de mudanças
	     function funcaoClickRowMudanca(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;

		        	document.getElementById('idFechamentoCategoria').value =  obj.situacaoLiberacao;
		        	/* if(teste == ""){
		            $("#POPUP_MUDANCA_FECHAMENTO_CATEGORIA").dialog("open");
		        	}
		        	teste = '';
 */
		        }
		    }

	     addMudanca = function(id, desc, stat, fecha) {

	       /*  if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){ */
	        	 var obj = new CIT_RequisicaoMudancaDTO();
	        	  document.getElementById('mudanca#idRequisicaoMudanca').value = id;
	              document.getElementById('mudanca#titulo').value = desc;
	              document.getElementById('mudanca#status').value = stat;
	              document.getElementById('mudanca#situacaoLiberacao').value = fecha;
	            HTMLUtils.addRow('tblMudancas', document.form, 'mudanca', obj,["", "idRequisicaoMudanca", "titulo","status","situacaoLiberacao","", ""], ["idRequisicaoMudanca"], "Mudança já adicionada", [gerarImgDel, gerarImgpopup, geraImgPlanoReversao], funcaoClickRowMudanca, null, false);
	            $("#POPUP_MUDANCA").dialog("close");
	             novoItem();
	        /* } else {
		        var obj = HTMLUtils.getObjectByTableIndex('tblMudancas', document.getElementById('rowIndex').value);
		        document.getElementById('mudanca#idRequisicaoMudanca').value = obj.idRequisicaoMudanca;
	              document.getElementById('mudanca#titulo').value = obj.titulo;
	              document.getElementById('mudanca#status').value =  obj.status;
	              document.getElementById('mudanca#situacaoLiberacao').value =  fecha;
		        HTMLUtils.updateRow('tblMudancas', document.form, 'mudanca', obj, ["", "idRequisicaoMudanca", "titulo","status","situacaoLiberacao","",""], null, '', [gerarImgDel, gerarImgpopup, geraImgPlanoReversao], funcaoClickRowMudanca, null, document.getElementById('rowIndex').value, false);

	             novoItem();
	        }   */
	        HTMLUtils.applyStyleClassInAllCells('tblMudancas', 'celulaGrid');
		}



	     function novoItem(){
		 		document.form.rowIndex.value = "";
		    }
	     function informaNumeroSolicitacao(numero){
				document.getElementById('divTituloLiberacao').innerHTML =
					'<h2><fmt:message key="requisicaoLiberacao.requisicaoLiberacaoNumero"/>&nbsp;' + numero + '</h2>';
			}
	     function gravarAnexosPlanosReversao(){
		    document.form.fireEvent("gravarAnexosPlanosReversao");
		 }


	     function listarPlanosDeReversao(idMudanca){
	    	 acaoBotao = true;
	    	 document.form.idMudanca.value = idMudanca;
	    	 document.formUploadReversaoLiberacao.idMudanca.value = idMudanca;
	    	<%--  document.getElementById('iframePlanoReversao').src ='${ctx}/pages/uploadPlanoDeReversao/uploadPlanoDeReversao.load?idMudancaLiberacao=' + idMudancaLiberacao; --%>
	    	document.form.fireEvent("listarPlanos");
	    	$("#POPUP_PLANOSREVERSAO").dialog("open");
	     }

		// Manipulador de evento para o Campo dataLiberacao.
		// Se A requisição for macada como resolvida o campo dataLiberacao passa a ser obrigatorio.
	     function verificaDataLiberacao(status){
	    	 var teste = status.value;
				if( teste == "Resolvida"){
					$('#dtLiberacao').addClass('campoObrigatorio');
					$('#dataLiberacao').addClass("Format[Data] datepicker Valid[Required] Description[liberacao.dataLiberacao]");
				}else{
					$('#dtLiberacao').removeClass('campoObrigatorio');
					$('#dataLiberacao').removeClass("Format[Data] datepicker Valid[Required] Description[liberacao.dataLiberacao]");

				}
	     }
			/* Desenvolvedor: Thiago Matias - Data: 27/11/2013 - Horário: 16:00 - ID Citsmart: 125329 -
				* Motivo/Comentário: foi alterado o modo de pesquisa dos itens de configuração para vinculação*/
	     function abrirModalPesquisaItemConfiguracao(){

				var h;
				var w;
				w = parseInt($(window).width() * 0.75);
				h = parseInt($(window).height() * 0.85);

				document.getElementById('framePesquisaItemConfiguracao').src = "${ctx}/pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load?iframe=true";

				$("#POPUP_PESQUISAITEMCONFIGURACAO").dialog("open");
			}

	     function selectedItemConfiguracao(id){
				idItemConfiguracao = id;
				document.getElementById('hiddenIdItemConfiguracao').value = id;
				var tbl = document.getElementById('tblICs');
				tbl.style.display = 'block';
				var lastRow = tbl.rows.length;
				var resultado = true;
				resultado = validaAddLinhaTabelaItemConfiguracao(lastRow, id);

				document.form.idItemConfig.value = id;
				document.form.idItemConfig.disabled=false;
				document.form.fireEvent('addICs');

			/* 	if (resultado == true){
					abrePopupDescricaoIcs();
				}else{
					return;
				} */
			}

	 	function validaAddLinhaTabelaItemConfiguracao(lastRow, id){
			var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);

			if (lastRow > 1){
				for(var i = 0; i < listaICs.length; i++){
					if (listaICs[i].idItemConfiguracao == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			}
			return true;
		}

</script>

<%  //se for chamado por iframe deixa apenas a parte de cadastro da pÃ¡gina

if (iframe != null) {

%>


<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>

<%  } %>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<div id="divBarraFerramentas" style='position:absolute; top: 0px; left: 500px; z-index: 1000'>
		<jsp:include page="/pages/barraFerramentasLiberacoes/barraFerramentasLiberacoes.jsp"></jsp:include>
	</div>
<body>
	<%-- <!-- 	<script type="text/javascript" src="../../cit/objects/EmpregadoDTO.js"></script> -->
	<div id="wrapper">
		<%
		    if (iframe == null) {
		%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%
		    }
		%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%
			    if (iframe == null) {
			%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%
			    }
			%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="liberacao" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="liberacao.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="liberacao.pesquisa" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class=""> --%>

					<div 	 id="wrapper" class="wrapper">
					<div id="main_container" class="main_container container_16 clearfix" style='margin: 10px 10px 10px 10px'>
					<div id='divTituloLiberacao' class="flat_area grid_16" style="text-align:right;">
									<h2><fmt:message key="requisicaoLiberacao.requisicaoLiberacao" /></h2>
					</div>
					<div class="box grid_16 tabs">
						<div class="toggle_container">
							<div class="section">
							<form name='form' action='${ctx}/pages/requisicaoLiberacao/requisicaoLiberacao'>
								<div class="section">
                                    <input type='hidden' name='idSolicitante' id='idSolicitante' />
                                    <input type='hidden' name='idItemRequisicaProduto' id='idItemRequisicaProduto' />
                                    <input type='hidden' name='idContatoRequisicaoLiberacao' id='idContatoRequisicaoLiberacao' />
                                    <input type='hidden' name='mudanca#idRequisicaoMudanca' id='mudanca#idRequisicaoMudanca' />
                                    <input type='hidden' name='mudanca#titulo' id='mudanca#titulo' />
                                    <input type='hidden' name='mudanca#status' id='mudanca#status' />
                                    <input type='hidden' name='mudanca#situacaoLiberacao' id='mudanca#situacaoLiberacao' />
									<input type="hidden" name="mudancas_serialize" id="mudancas_serialize" />
									<input type="hidden" name="idMudanca"  id="idMudanca" />
									<input type='hidden' name='problema#idProblema' id='problema#idProblema' />
                                    <input type='hidden' name='problema#titulo' id='problema#titulo' />
                                    <input type='hidden' name='problema#status' id='problema#status' />
									<input type="hidden" name="problemas_serialize" id="problemas_serialize" />

									<input type='hidden' name='acaoFluxo' id='acaoFluxo' />
									<input type='hidden' name='idRequisicaoLiberacao' id='idRequisicaoLiberacao' />
									<input type='hidden' name='alterarSituacao' id='alterarSituacao' />
									<input type='hidden' name='idTarefa' id='idTarefa' />
									<input type='hidden' name='escalar' id='escalar' />
									<input type='hidden' name='fase' id='fase' />
									<input type="hidden" name="idProprietario" id="idProprietario" />
									<input type="hidden" id="itensConfiguracaoRelacionadosSerializado" name="itensConfiguracaoRelacionadosSerializado" />
									<input type="hidden" id="hiddenIdItemConfiguracao" name="hiddenIdItemConfiguracao" />

									<!-- MIDIA SOFTWARE -->
									<input type='hidden' name='midia#idMidiaSoftware' id='midia#idMidiaSoftware' />
                                    <input type='hidden' name='midia#nomeMidia' id='midia#nomeMidia' />
                                  	<input type="hidden" name="midias_serialize" id="midias_serialize" />

                                  	<input type='hidden' name='responsavel#idResponsavel' id='responsavel#idResponsavel' />
                                    <input type='hidden' name='responsavel#nomeResponsavel' id='responsavel#nomeResponsavel' />
                                    <input type='hidden' name='responsavel#papelResponsavel' id='responsavel#papelResponsavel' />
                                    <input type='hidden' name='responsavel#telResponsavel' id='responsavel#telResponsavel' />
                                    <input type='hidden' name='responsavel#nomeCargo' id='responsavel#nomeCargo' />
									<input type='hidden' name='responsavel#emailResponsavel' id='responsavel#emailResponsavel' />
									<input type="hidden" name="responsavel_serialize" id="responsavel_serialize" />
									<!-- FIM RESPONSÁVEL-->
									<!-- PEDIDO DE COMPRAS -->

									<input type='hidden' name='requisicaoCompras#idSolicitacaoServico' id='requisicaoCompras#idSolicitacaoServico' />
                                    <input type='hidden' name='requisicaoCompras#nomeServico' id='requisicaoCompras#nomeServico' />
                                    <input type='hidden' name='requisicaoCompras#situacaoServicos' id='requisicaoCompras#situacaoServicos' />
									<input type="hidden" name="requisicaoCompras_serialize" id="requisicaoCompras_serialize" />
									<!-- FIM PEDIDO COMPRAS -->
                                  	<input type="hidden" name="informacoesComplementares_serialize" id="informacoesComplementares_serialize" />
                                  	<input type="hidden" id="rowIndex" name="rowIndex"/>

                                  	<input type="hidden" name="idTipoAba" id="idTipoAba" />
                                  	<input type="hidden" name="idTipoRequisicao" id="idTipoRequisicao" />

                                  	<input type="hidden" name="idItemConfig" id="idItemConfig" />
                                  	<input type="hidden" name="nomeItemConfig" id="nomeItemConfig" />

                                  	<input type="hidden" name="idICMudanca" id="idICMudanca" />
                                  	<input type="hidden" name="numeroLiberacao" id="numeroLiberacao" />
                                  	<input type="hidden" name="rowIndex" id="rowIndex" />
                                  	<input type="hidden" name="SGBD" id="SGBD" />
                                  		<div class="col_100">
											<fieldset>
												<label class="" style="font-family: Arial; font-weight: bold;">&nbsp;</label>

											</fieldset>
										</div>
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio" style="font-family: Arial; font-weight: bold;"><fmt:message key="contrato.contrato" /></label>
											<div>
											<select  id="idContrato" onchange="setaValorLookup(this);" onclick= "document.form.fireEvent('carregaUnidade');"  name='idContrato' class=" Valid[Required] Description[<fmt:message key='contrato.contrato' />]" >
											<!--	onchange="setaValorLookup(this);" SETAR DPOIS carregarInformacoesComplementares(); document.form.fireEvent('preencherComboLocalidade'); -->

											</select>
											</div>
										</fieldset>
									</div>

									<div class="col_100">
                                        <%-- <div class="col_10" >
                                             <fieldset style="height: 60px;">
                                                 <label style="cursor: pointer; " >
                                                     <fmt:message key="citcorpore.comum.numero" />
                                                 </label>
                                                 <div>
                                                     <input id="numeroLiberacao" type='text' name="numeroLiberacao" readonly="readonly"/>
                                                 </div>
                                            </fieldset>
                                        </div>    --%>

                                        <div class="col_60" >
                                             <fieldset style="height: 60px;">
                                                 <label class="campoObrigatorio" style="cursor: pointer; " >
                                                     <fmt:message key="liberacao.solicitadaPor" />
                                                 </label>
                                                 <div>
                                                     <input id="nomeSolicitante" type='text' name="nomeSolicitante" readonly="readonly" class="Valid[Required] Description[liberacao.solicitadaPor]" />
                                                 </div>
                                            </fieldset>
                                        </div>

<%--                                        	<div class="col_40" style="height: 60px;">
											<fieldset style="height: 60px;">
												<label class="campoObrigatorio"> <fmt:message key="citcorpore.comum.grupoaprovador" /></label>
												<div>
													<select name='idGrupoAprovador' id='idGrupoAprovador' class="Valid[Required] Description[citcorpore.comum.grupoaprovador]"  >
													</select>
												</div>
											</fieldset>
										</div> --%>
									</div>

								<div class="col_100">
									<div class="col_15">
											<fieldset style="height: 60px;">
												<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.situacao"/>
												</label>
												<div>
	                                                <select id='status' onchange="verificaDataLiberacao(this);" name='status' class="Valid[Required] Description[citcorpore.comum.situacao]"></select>
												</div>
											</fieldset>
										</div>

										<div class="col_15">
		                                    <fieldset style="height: 60px;">
		                                        <label class="campoObrigatorio"><fmt:message key="liberacao.dataInicial" />
		                                        </label>
		                                            <div>
		                                                <input type='text' name="dataInicial" id="dataInicial" maxlength="10" size="10"
		                                                    class="Valid[Required] Description[liberacao.dataInicial] Format[Data] datepicker" />
		                                            </div>
		                                    </fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="requisicaoMudanca.horaAgendamentoInicial"/></label>
												<div>
													<input type='text' name="horaAgendamentoInicial" id="horaAgendamentoInicial" maxlength="5" size="5" maxlength="5"  class="Valid[Hora] Format[Hora]" />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
	                                        <fieldset style="height: 60px;">
	                                            <label class="campoObrigatorio"><fmt:message key="liberacao.dataFinal" />
	                                            </label>
	                                                <div>
	                                                    <input type='text' name="dataFinal" id="dataFinal" maxlength="10" size="10"
	                                                        class="Valid[Required] Description[liberacao.dataFinal] Format[Data] datepicker" />
	                                                </div>
	                                        </fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="requisicaoLiberacao.horaFinal"/></label>
												<div>
													<input type='text' name="horaAgendamentoFinal" id="horaAgendamentoFinal" maxlength="5" size="5" maxlength="5"  class="Valid[Hora] Format[Hora]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="cursor: pointer;"><fmt:message key='gerenciaservico.agendaratividade.crupoatividades' /></label>
											<div>
												<select name='idGrupoAtvPeriodica' id='idGrupoAtvPeriodica'>
												</select>
											</div>
											</fieldset>
										</div>
									</div>

							        <div class="col_100">
                                        <div class="col_40">
                                            <fieldset style="height: 120px;">
                                                <label class="campoObrigatorio"><fmt:message key="liberacao.titulo" />
                                                </label>
                                                    <div>
                                                        <input type='text' name="titulo" id="titulo" maxlength="100"
                                                            class="Valid[Required] Description[liberacao.titulo]" />
                                                    </div>
                                            </fieldset>
                                        </div>
					                    <div class="col_60">
					                         <fieldset style="height: 120px;">
					                             <label class="campoObrigatorio"><fmt:message key="liberacao.descricao" />
					                             </label>
					                             <div>
					                                 <textarea name="descricao" id="descricao" cols='200' rows='4' maxlength="65000"  class="Valid[Required] Description[liberacao.descricao]" ></textarea>
					                             </div>
					                         </fieldset>
					                     </div>
                                    </div>
                                    <div class="col_100">
									<div>
										<h2 class="section">
											<fmt:message key="solicitacaoServico.informacaoContato" />
										</h2>
									</div>

									<div class="col_100">
										<div class="col_50">
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"> <fmt:message
															key="solicitacaoServico.nomeDoContato" /></label>
													<div>
														<input id="nomeContato2" type='text' name="nomeContato2"
															maxlength="70"
															class="Valid[Required] Description[<fmt:message key='solicitacaoServico.nomeDoContato' />]" />
													</div>
												</fieldset>
											</div>

											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message
															key="requisicaoMudanca.email" /></label>
													<div>
														<input id="emailContato" type='text' name="emailContato"
															maxlength="120"
															class="Valid[Required, Email] Description[requisicaoMudanca.email]" />
													</div>
												</fieldset>
											</div>

											<div class="col_25">
												<fieldset>
													<label><fmt:message
															key="requisicaoMudanca.telefone" /></label>
													<div>
														<input id="telefoneContato" type='text'
															name="telefoneContato" maxlength="20" class="" />
													</div>
												</fieldset>
											</div>

											<div class="col_25">
												<fieldset>
													<label><fmt:message key="citcorpore.comum.ramal" /></label>
													<div>
														<input id="ramal" type='text' name="ramal" maxlength="4"
															class="Format[Numero]" />
													</div>
												</fieldset>
											</div>

											<div class="col_25">
												<fieldset style="height: 55px">
													<label class="tooltip_bottom campoObrigatorio"
														title="<fmt:message key="colaborador.cadastroUnidade"/>">
														<fmt:message key="unidade.unidade" />
													</label>
													<div>
														<select name='idUnidade' id='idUnidade'
															onchange="document.form.fireEvent('preencherComboLocalidade')"
															class="Valid[Required] Description[colaborador.cadastroUnidade]"></select>
													</div>
												</fieldset>
											</div>

											<div class="col_25">
												<fieldset style="height: 55px">
													<label class="tooltip_bottom "
														title="<fmt:message key="colaborador.cadastroUnidade"/>">
														<fmt:message key="solicitacaoServico.localidadeFisica" />
													</label>
													<div>
														<select name='idLocalidade' id='idLocalidade'></select>
													</div>
												</fieldset>
											</div>
										</div>

										<div class="col_50">
											<fieldset style="height: 112px">
												<label><fmt:message
														key="requisicaoMudanca.observacao" /></label>
												<div>
													<textarea id="observacao" class="col_98" name="observacao"
														maxlength="2000" style="height: 90px; float: right;"></textarea>
												</div>
											</fieldset>
										</div>
									</div>

								</div>
									<div>
										<h2 class="section">
											<fmt:message key="requisicaoLiberacao.informacaoRequisicao" />
										</h2>
									</div>
                                    <div class="col_100">
							        <div class="col_50">
							        <div class="col_100">
				                       <div class= "col_25">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.tipo"/></label>
												<div>
													<select class="Valid[Required] Description[citcorpore.comum.tipo]" id="idTipoLiberacao" name='idTipoLiberacao' ></select>
												</div>
											</fieldset>
										</div>
                                        <div class="col_25">
                                            <fieldset>
                                                <label id="dtLiberacao" ><fmt:message key="liberacao.dataLiberacao" />
                                                </label>
                                                    <div>
                                                        <input type='text' name="dataLiberacao" id="dataLiberacao" maxlength="10" size="10"
                                                            class="Format[Data] datepicker" />
                                                    </div>
                                            </fieldset>
                                        </div>
                                        <div class="col_25">
                                            <fieldset>
                                                <label ><fmt:message key="liberacao.versao" />
                                                </label>
                                                    <div>
                                                        <input type='text' name="versao" id="versao" maxlength="25"  />
                                                    </div>
                                            </fieldset>
                                        </div>
                                        <div class="col_25">
                                            <fieldset>
                                                <label class="campoObrigatorio"><fmt:message key="liberacao.risco"/>
                                                </label>
                                                <div>
                                                    <select id='risco' name='risco' class="Valid[Required] Description[liberacao.risco]" >
                                                        <option value=''><fmt:message key="citcorpore.comum.selecione"/></option>
                                                        <option value='B'><fmt:message key="liberacao.riscoBaixo"/></option>
                                                        <option value='M'><fmt:message key="liberacao.riscoMedio"/></option>
                                                        <option value='A'><fmt:message key="liberacao.riscoAlto"/></option>
                                                    </select>
                                                </div>
                                            </fieldset>
                                        </div>
							        </div>

							        	<div class="col_100">
					        				<div class="col_25">
												<fieldset>
													<label class="campoObrigatorio"> <fmt:message key="citcorpore.comum.grupoExecutor" /></label>
													<div>
														<select name='idGrupoAtual' id='idGrupoAtual' class="Valid[Required] Description[citcorpore.comum.grupoExecutor]" >
														</select>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.impacto" /></label>
												<div>
													<select onchange="atualizaPrioridade()" id="nivelImpacto" name="nivelImpacto">
														<option value="B"><fmt:message key="citcorpore.comum.baixa"/></option>
														<option value="M"><fmt:message key="citcorpore.comum.media"/></option>
														<option value="A"><fmt:message key="citcorpore.comum.alta"/></option>
													</select>
												</div>
											</fieldset>
										</div>

										<div class="col_25" >
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.urgencia"/></label>
												<div>
													<select onchange="atualizaPrioridade()" id="nivelUrgencia" name="nivelUrgencia">
														<option value="B"><fmt:message key="citcorpore.comum.baixa"/></option>
														<option value="M"><fmt:message key="citcorpore.comum.media"/></option>
														<option value="A"><fmt:message key="citcorpore.comum.alta"/></option>
													</select>
												</div>
											</fieldset>
										</div>

										<div class="col_25">
											<fieldset style="height: 52px">
												<label><fmt:message key="prioridade.prioridade" /></label>
												<div>
													<input id="prioridade" name="prioridade" type="text" readonly="readonly" value="5" />
												</div>
											</fieldset>
										</div>
										</div>

									</div>
									<div id="divNotificacaoEmail" class="col_50" >
										<fieldset>
											<label><fmt:message key="requisicaoMudanca.notificacaoporEmail"/></label>
											<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailCriacao' checked="checked"/><fmt:message key="requisicaoLiberacao.enviaEmailCriacao"/></label><br>
											<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailFinalizacao' checked="checked"/><fmt:message key="requisicaoLiberacao.enviaEmailFinalizacao"/></label><br>
											<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailAcoes'/><fmt:message key="requisicaoLiberacao.enviaEmailAcoes"/></label>

										</fieldset>
									</div>
	                                </div>

								<div class="col_100">
									<fieldset>
										<label><fmt:message key="citcorpore.comum.fechamento" /></label>

										<div>
											<textarea id="fechamento" name="fechamento" cols='70' rows='5' class="Description[citcorpore.comum.fechamento]"></textarea>
										</div>
									</fieldset>
								</div>
								<div class="col_50">
										<fieldset style="FONT-SIZE: xx-small;">
											<label style="cursor: pointer;"><fmt:message key="citcorpore.comum.categoriaSolucao"/></label>
											<div>
												<select name='idCategoriaSolucao' ></select>
											</div>
										</fieldset>
								</div>
                                   <!--  <div class="col_100">
                                        <label>&nbsp;</label>
                                    </div> -->
                                     <div>
										<div class="col_100" id='divBotaoAddRegExecucao'>
											<fieldset style="FONT-SIZE: xx-small;">
												<button type='button' name='btnAddRegExec' id='btnAddRegExec' onclick='mostrarEscondeRegExec()'><fmt:message key="solicitacaoServico.addregistroexecucao_mais" /></button>
											</fieldset>
										</div>
										<div class="col_100">
											<fieldset style="FONT-SIZE: xx-small;">
												<label id='lblMsgregistroexecucao' style='display:none'><font color='red'><b><fmt:message key="solicitacaoServico.msgregistroexecucao" /></b></font></label>
													<div id='divMostraRegistroExecucao' style='display:none'>
														<textarea id="registroexecucao" name="registroexecucao" cols='70' rows='5' class="Description[citcorpore.comum.resposta]"></textarea>
													</div>
											</fieldset>
										</div>
										<div class="col_100" style="overflow : auto;max-height: 400px">
											<fieldset>
												<div  id="tblOcorrencias" ></div>
											</fieldset>
										</div>
									</div>
                                   <%--  <div id="btnGravar" style="margin-top: 10px; margin-bottom: 50px;" class="col_100">

			                             <%if (tarefaAssociada.equalsIgnoreCase("N")) {%>
		                                    <button type='button' name='btnGravarEContinuar' class="light" onclick='gravarEContinuar();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravar" /></span>
											</button>
			                                <button type="button" name='btnLimpar' class="light" onclick='limpar();'>
			                                    <img src="${ctx}/template_new/images/icons/small/grey/clear.png">
			                                    <span><fmt:message key="citcorpore.comum.limpar"/></span>
			                                </button>
										<%}else{%>
		                                    <button type='button' name='btnGravarEContinuar' class="light" onclick='gravarEContinuar();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravarEContinuar" /></span>
											</button>
											<button type='button' name='btnGravarEFinalizar' class="light" onclick='gravarEFinalizar();'>
												<img src="${ctx}/template_new/images/icons/small/grey/cog_2.png">
												<span><fmt:message key="citcorpore.comum.gravarEFinalizar" /></span>
											</button>
										<%}%>


                                   </div> --%>

								</div>

								<div  id="abas" class="formRelacionamentos">
									<div id="tabs" class="block">
										<ul class="tab_header clearfix">
											<li><a href="#relacionarMudancas"><fmt:message key="requisicaoMudanca.relacionarMudancas"/></a></li>
											<li><a href="#relacionaIcs"><fmt:message key="requisicaoMudanca.relacionarICs"/></a></li>
											<li><a href="#relacionarProblemas"><fmt:message key="requisicaoMudanca.relacionarProblemas"/></a></li>
											<%-- <li><a href="#relacionarLiberacoes"><fmt:message key="requisicaoMudanca.relacionarLiberacoes"/></a></li> --%>
											<li><a href="#relacionarDocsLegais"><fmt:message key="requisicaoMudanca.documentosLegais"/></a></li>
											<li><a href="#midiaDefinitiva"><fmt:message key="requisicaoMudanca.midiaDefinitiva"/></a></li>
											<li><a href="#documentosGerais"><fmt:message key="requisicaoLiberacao.documentosGerais"/></a></li>
											<li><a href="#responsavel"><fmt:message key="requisicaoLiberacao.papeisResponsabilidades"/></a></li>
											<li><a href="#requisicaoCompras"><fmt:message key="requisicaoLiberacao.requisicaoComprasVinculadas"/></a></li>
											<%if (!tarefaAssociada.equalsIgnoreCase("N")) {%>
												<li><a href="#checklist" onclick="carregaQuestionario('<%=br.com.centralit.citcorpore.util.Enumerados.Aba.LIBERCAOTESTE.getId()%>')"><fmt:message key="requisicaoLiberacao.checklist"/></a></li>
											<%}%>

										</ul>
										<div id="relacionarMudancas">
											<div  style="width: 15%; float: left;" align="center" >
												<label style="cursor: pointer;" onclick='adicionarMudanca();'>
												<fmt:message key="liberacao.adicionarMudanca" />
												<img  src="${ctx}/imagens/add.png" />
												</label>
											</div>


											<div  style="width: 15%; float: left;" align="center" >
												<label style="cursor: pointer;" onclick="popup3.abrePopup('situacaoLiberacaoMudanca', 'tabelaMudancaSituacaoLiberacao')">
												<fmt:message key="situacaoLiberacaoMudanca.cadastroSituacao" />
												<img  src="${ctx}/imagens/add.png" />
												</label>
											</div>

				                             <div class="col_100">
				                                 	<table id="tblMudancas" class="table table-bordered table-striped" style="width: 100%;">
				                                       <tr>
			                                          	   <th width="1%">&nbsp;</th>
				                                           <th width="5%"><fmt:message key="citcorpore.comum.numero" /></th>
				                                           <th width="20%"><fmt:message key="liberacao.titulo" /></th>
				                                           <th width="10%"><fmt:message key="situacaoLiberacaoMudanca.status"/></th>
				                                           <th width="10%"><fmt:message key="situacaoLiberacaoMudanca.situacaoDaLiberacao"/></th>
				                                           <th width="5%"><fmt:message key="situacaoLiberacaoMudanca.alterar"/></th>
				                                           <th width="6%"><fmt:message key="citcorpore.comum.anexosPlanoRevisao"/></th>

				                                   </table>
				                             </div>
				                             <div class="col_100">
				                                  <label>&nbsp;</label>
				                             </div>
										</div>
										<div id="relacionaIcs">
										<%-- 	<div  style="width: 15%; float: left;" align="center" >
												<label style="cursor: pointer;" onclick='adicionarIC();'>
												<fmt:message key="requisicaoMudanca.adicionarItemConfiguracao"/>
													<img  src="${ctx}/imagens/add.png" />
												</label>
											</div> --%>
												<div  style="width: 15%; float: left;" align="center" >
													<label style="cursor: pointer;" onclick='abrirModalPesquisaItemConfiguracao();'>
													<fmt:message key="requisicaoMudanca.adicionarItemConfiguracao"/>
														<img  src="<%=br.com.citframework.util.Constantes
											.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
													</label>
												</div>
											<div  style="width: 15%; float: left;" align="center" >
												<label style="cursor: pointer;" onclick='inventario();'>
												<fmt:message key="requisicaoMudanca.inventario"/>
													<img  src="${ctx}/imagens/add.png" />
												</label>
											</div>

											<div style="width: 99%; height : 30px; float: left;"></div>

											<div class="formBody">
												<table id="tblICs" class="table table-bordered table-striped" style="width: 99%">
													<tr>
														<th width="2%">&nbsp;</th>
														<th width="5%"><fmt:message key="parametroCorpore.id"/></th>
														<th width="60%"><fmt:message key="citcorpore.comum.nome"/></th>
														<th width="30%"><fmt:message key="situacaoLiberacaoMudanca.status"/></th>
														<th width="2%">&nbsp;</th>
													</tr>
												</table>
											</div>
										</div>
										<div id="relacionarProblemas">
											<div style="width: 15%; float: left;" align="center" >
												<label  style="cursor: pointer;" onclick='adicionarProblema();'>
													<fmt:message key="requisicaoMudanca.adicionarProblema"/>
													<img  src="${ctx}/imagens/add.png" />
												</label>
											</div>
											<div style="width: 99%; height : 30px; float: left;"></div>
											<div class="formBody">
												<table id="tblProblema" class="table table-bordered table-striped">
													<tr>
														<th height="10px" width="5%"></th>
														<th height="10px" width="15%"><fmt:message key="parametroCorpore.id"/></th>
														<th height="10px" width="35%"><fmt:message key="problema.titulo"/></th>
														<th height="10px"width="15%"><fmt:message key="problema.status"/></th>
														<th height="10px"width="10%"></th>
													</tr>
												</table>
											</div>
										</div>
<%-- 										<div id="relacionarLiberacoes">
											<div class="formBody">
												<input type='hidden' id='idHistoricoLiberacao' name='idHistoricoLiberacao'/>
												<input type='hidden' id='idHistoricoMudanca' name='idHistoricoMudanca'/>
												<input type='hidden' id='idItemConfiguracao' name='idItemConfiguracao'/>
												<input type='hidden' id="baselinesSerializadas" name='baselinesSerializadas'/>
												<div id="contentBaseline">

												</div>
												<button onclick="gravarBaseline();" class="light img_icon has_text" name="btnGravarBaseLine" type="button" id="btnGravarBaseLine">
													<img src="/citsmart/template_new/images/icons/small/grey/pencil.png">
													<span><fmt:message key="itemConfiguracaoTree.gravarBaselines"/></span>
												</button>
											</div>
										</div> --%>

											<!-- 	MIDIA DEFINITIVA -->
											<div id="midiaDefinitiva">
											<div style="width: 15%; float: left;" align="center" >
												<label  style="cursor: pointer;" >
													<fmt:message key="liberacao.adicionarMidia"/>
													<img onclick='adicionarMidia();' style="vertical-align: middle; cursor: pointer;"  src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
													<img  onclick="popupMidia.abrePopup('midiaSoftware', '');" style="vertical-align: middle; cursor: pointer;" src="${ctx}/imagens/add.png" />
												</label>
											</div>
											<div style="width: 99%; height : 30px; float: left;"></div>
											<div class="formBody">
												<table id="tblMidia" class="table table-bordered table-striped">
													<tr>
														<th height="10px" width="5%"></th>
														<th height="10px" width="15%"><fmt:message key="parametroCorpore.id"/></th>
														<th height="10px" width="35%"><fmt:message key="problema.titulo"/></th>
													</tr>
												</table>
											</div>
										</div>
										<!--  INICIO DIV DOCUMENTOS LEGAIS -->

										<div id="relacionarDocsLegais" style="border:none !important;">
											<div class="formDocsLegais">
												<input type='hidden' id='idItemConfiguracao' name='idItemConfiguracao'/>
											<!-- 	<input type='hidden' id="baselinesSerializadas" name='baselinesSerializadas'/>	 -->
												 <div id="contentDocsLegais" style="border:none !important;">
													<cit:uploadControl id="uploadAnexosdocsLegais" title="Anexos" style="height: 100px; width: 98%; border:0px solid black;" form="document.form" action="/pages/uploadDocsLegais/uploadDocsLegais.load" disabled="false" />
												</div>
											</div>
										</div>

										<!-- DOCUMENTOSGERAIS -->
										<div id="documentosGerais">
												<cit:uploadControl id="uploadAnexosDocsGerais" title="Anexos"	style="height: 100px; width: 100%; border: 0px solid black;" form="form" action="/pages/uploadDocsGerais/uploadDocsGerais.load" disabled="false" />
										</div>
										<!-- inicio documentosGerais -->


										<!--  INICIO DOCUMENTOS GERAIS -->

										<!--  INICIO CHECKLIST -->
										<%if (!tarefaAssociada.equalsIgnoreCase("N")) {%>
											<div id="checklist">
												<div class="col_100" id='divInformacoesComplementares' style='display:block; height: 850px'>
	                                				<iframe id='fraInformacoesComplementares' name='fraInformacoesComplementares' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;'></iframe>
	                            				</div>
											</div>
										<%}%>
										<!-- INÍCIO PAPÉIS E RESPONSABILIDADES -->
										<div id="responsavel">
											<div style="width: 20%; float: left;" align="center" >
												<label  style="cursor: pointer;" onclick='adicionarResponsavel();'>
													<fmt:message key="liberacao.adicionarPapeisResponsabilidades"/>
													<img  src="${ctx}/imagens/add.png" />
												</label>
											</div>
											<div style="width: 99%; height : 30px; float: left;"></div>
											<div class="formBody">
												<table id="tblResponsavel" class="table table-bordered table-striped">
													<tr>
														<th height="10px" width="1%"></th>
														<th height="10px" width="5%"><fmt:message key="parametroCorpore.id"/></th>
														<th height="10px" width="25%"><fmt:message key="citcorpore.comum.nome"/></th>
														<th height="10px"width="15%"><fmt:message key="citcorpore.comum.cargo"/></th>
														<th height="10px" width="20%"><fmt:message key="citcorpore.comum.telefone"/></th>
														<th height="10px" width="20%"><fmt:message key="citcorpore.comum.email"/></th>
														<th height="10px" width="20%"><fmt:message key="citcorpore.comum.papel"/></th>
													</tr>
												</table>
											</div>
										</div>

										<!--  INICIO CHECKLIST -->
										<!-- <div id="checklist">
											<div class="col_100" id='divInformacoesComplementares' style='display:block;'>
                                				<iframe id='fraInformacoesComplementares' name='fraInformacoesComplementares' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;'></iframe>
                            				</div>
										</div> -->
											<!-- INÍCIO PEDIDO DE COMPRAS -->
										<div id="requisicaoCompras">
											<div style="width: 8%; float: left;" align="center" >
												<label  style="cursor: pointer;" onclick='adicionarRequisicaoCompras();'>
													<fmt:message key="citcorpore.comum.adicionar"/>
													<img  src="${ctx}/imagens/add.png" />
												</label>
											</div>
											<!-- <div style="width: 99%; height : 30px; float: left;"></div>	 -->
											<div class="formBody">
												<table id="tblRequisicaoCompra" class="table table-bordered table-striped">
													<tr>
														<th height="10px" width="1%"></th>
														<th height="10px" width="5%"><fmt:message key="requisicaMudanca.numero_solicitacao"/></th>
														<th height="10px" width="40%"><fmt:message key="itemRequisicaoProduto.descrProduto"/></th>
														<th height="10px" width="20%"><fmt:message key="lookup.nomeCentroResultado"/></th>
														<th height="10px" width="20%"><fmt:message key="lookup.codigoCentroResultado"/></th>
														<th height="10px" width="20%"><fmt:message key="lookup.nomeProjeto"/></th>
														<th height="10px" width="20%"><fmt:message key="citcorpore.comum.situacao"/></th>
													</tr>
												</table>
											</div>
									</div>
									 <div id="btnGravar" style="margin-top: 10px; margin-bottom: 50px;" class="col_100">

			                             <%if (tarefaAssociada.equalsIgnoreCase("N")) {%>
		                                    <button type='button' name='btnGravarEContinuar' class="light" onclick='gravarEContinuar();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravar" /></span>
											</button>
			                                <button type="button" name='btnLimpar' class="light" onclick='limpar();'>
			                                    <img src="${ctx}/template_new/images/icons/small/grey/clear.png">
			                                    <span><fmt:message key="citcorpore.comum.limpar"/></span>
			                                </button>
										<%}else{%>
		                                    <button type='button' name='btnGravarEContinuar' class="light" onclick='gravarEContinuar();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravarEContinuar" /></span>
											</button>
											<button type='button' name='btnGravarEFinalizar' class="light" onclick='gravarEFinalizar();'>
												<img src="${ctx}/template_new/images/icons/small/grey/cog_2.png">
												<span><fmt:message key="citcorpore.comum.gravarEFinalizar" /></span>
											</button>
										<%}%>


                                   </div>
									</div><!-- FIM_tabs -->
								</div><!-- FIM_formRelaciomentos -->
								<div id="POPUP_MUDANCA_FECHAMENTO_CATEGORIA" title='<fmt:message key="situacaoLiberacaoMudanca.alterar" />'>
      								 <div class="box grid_16 tabs">
       								 <div class="toggle_container">
       								 <div id="tabs-2" class="block">
        							 <div class="col_100">
										<fieldset>
										<label><fmt:message key="situacaoLiberacaoMudanca.situacao" /></label>
												<div>
												<input type="hidden" id="linha" name="linha"/>
											<select name="idFechamentoCategoria" id="idFechamentoCategoria"
													class="Valid[Required] Description[liberacaomudanca.status]">
											</select>
											<button type="button" class="light" onclick="setarFechamentoCategoria()">OK</button>
												</div>
										</fieldset>
								  </div>
     							  </div>
  								  </div>
      							  </div>
      							  </div>
   								 </div>
							</form>
						</div>
					</div>
					<%-- <div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_LIBERACAO' id='LOOKUP_LIBERACAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div> --%>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	<%@include file="/include/footer.jsp"%>
</body>

	<div id="POPUP_EMPREGADO" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaEmp' style="width: 540px">
<!-- lookup_empregado -->
				<cit:findField formName='formPesquisaEmp' lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>

    <div id="POPUP_MUDANCA" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
        <div class="box grid_16 tabs">
        <div class="toggle_container">
        <div id="tabs-2" class="block">
        <div class="section">
            <form name='formPesquisaMudanca' style="width: 540px">
                <cit:findField formName='formPesquisaMudanca' lockupName='LOOKUP_MUDANCA_CONCLUIDA_SITUACAO' id='LOOKUP_MUDANCA_CONCLUIDA_SITUACAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
            </form>
        </div>
        </div>
        </div>
        </div>
    </div>

    <div id="POPUP_PROBLEMA" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
        <div class="box grid_16 tabs">
        <div class="toggle_container">
        <div id="tabs-2" class="block">
        <div class="section">
            <form name='formPesquisaProblema' style="width: 540px">
                <cit:findField formName='formPesquisaProblema' lockupName='LOOKUP_PROBLEMA_CONCLUIDO' id='LOOKUP_PROBLEMA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
            </form>
        </div>
        </div>
        </div>
        </div>
    </div>

    <div id="POPUP_INFO_INSERCAO"
		title=""
		style="overflow: hidden;">
		<div class="toggle_container">
			<div id='divMensagemInsercao' class="section" style="overflow: hidden; font-size: 24px;">

			</div>
			<button type="button" onclick='$("#POPUP_INFO_INSERCAO").dialog("close");'>
				<fmt:message key="citcorpore.comum.fechar" />
			</button>
		</div>
	</div>
    <div id="POPUP_INFO_BASELINE"
		title=""
		style="overflow: hidden;">
		<div class="toggle_container">
			<div id='divMensagemInsercaoBaseline' class="section" style="overflow: hidden; font-size: 24px;">

			</div>
			<button type="button" onclick='$("#POPUP_INFO_BASELINE").dialog("close");'>
				<fmt:message key="citcorpore.comum.fechar" />
			</button>
		</div>
	</div>


   <div id="POPUPITEMCONFIGURACAO" style=""  title="<fmt:message key="citcorpore.comum.pesquisa" />">
 		<table >
			<tr>
				<td>
					<h3><fmt:message key="eventoItemConfiguracao.itensConfiguracao" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaItem' style="width: 95%; margin:auto;">
			<cit:findField formName='formPesquisaItem'
						   lockupName='LOOKUP_ITEMCONFIGURACAO_ATIVO_ALL'
						   id='LOOKUP_ITEMCONFIGURACAO'
						   top='0' left='0' len='550'
						   heigth='400'
						   javascriptCode='true' htmlCode='true' />
		</form>
	</div>
		<div id="POPUP_MIDIA" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
        <div class="box grid_16 tabs">
        <div class="toggle_container">
        <div id="tabs-2" class="block">
        <div class="section">
            <form name='formPesquisaMidia' style="width: 540px">
                <cit:findField formName='formPesquisaMidia' lockupName='LOOKUP_MIDIASOFTWARE' id='LOOKUP_MIDIASOFTWARE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
            </form>
        </div>
        </div>
        </div>
        </div>
		</div>
    </div>
    <div id="POPUP_RESPONSAVEL" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
        <div class="box grid_16 tabs">
        <div class="toggle_container">
        <div id="tabs-2" class="block">
       <div class="section">
            <form name='formPesquisaResponsavel' style="width: 790px">
                <cit:findField formName='formPesquisaResponsavel' lockupName='LOOKUP_RESPONSAVEL' id='LOOKUP_RESPONSAVEL' top='0' left='0' len='800' heigth='500' javascriptCode='true' htmlCode='true' />
            </form>
        </div>
        </div>
        </div>
        </div>
    </div>
    <div id="POPUP_REQUISICAOCOMPRAS" title="<fmt:message  key="citcorpore.comum.pesquisa" />">
        <div class="box grid_16 tabs">
        <div class="toggle_container">
        <div id="tabs-2" class="block">
       <div class="section">
            <form name='formPesquisaRequisicaoCompras' style="width: 800px">
                <cit:findField formName='formPesquisaRequisicaoCompras' lockupName='LOOKUP_REQUISICAOCOMPRAS' id='LOOKUP_REQUISICAOCOMPRAS' top='0' left='0' len='800' heigth='600' javascriptCode='true' htmlCode='true' />
            </form>
        </div>
        </div>
        </div>
        </div>
    </div>
	<div id="POPUP_ATIVOS" title="<fmt:message key="pesquisa.listaAtivosDaMaquina" />" style="overflow: hidden;">
		<div class="box grid_16 tabs" >
			<div class="toggle_container" >
				<div id="tabs-2" class="block" style="overflow: hidden;">
					<div class="section" style="overflow: hidden;">
						<iframe id="iframeAtivos" style="display: block; margin-left: -20px;" name="iframeAtivos" width="970" height="480" >
						</iframe>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_PLANOSREVERSAO" title="<fmt:message key="requisicaoLiberacao.planosReversao" />" style="overflow: hidden;">
		<form name='formUploadReversaoLiberacao' action='${ctx}/pages/uploadPlanoDeReversaoLiberacao/uploadPlanoDeReversaoLiberacao' enctype="multipart/form-data">
				<input type="hidden" name="idMudanca"  id="idMudanca" />
				<cit:uploadPlanoDeReversaoControl id="uploadPlanoDeReversaoLiberacao" title="Anexos" style="height: 220px; width: 98%; border: 1px solid black;" form="formUploadReversaoLiberacao" action="/pages/uploadPlanoDeReversaoLiberacao/uploadPlanoDeReversaoLiberacao.load" disabled="false" />
		</form>
	</div>
	<div id="POPUP_PESQUISAITEMCONFIGURACAO" style="" title="<fmt:message key="itemConfiguracao.pesquisa" />" class="POPUP_LOOKUP_SERVICO">
		<iframe id='framePesquisaItemConfiguracao' src='about:blank' width="100%" height="99%" onload="resize_iframe()">
		</iframe>
	</div>
</html>