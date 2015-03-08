var pageCadastro = "/pages/requisicaoLiberacao/requisicaoLiberacao.load";

$(document).ready(function() {
	$('li.dropdown').click(function() {
		if ($(this).is('.open')) {
			$(this).removeClass('open');
		} else {
			$(this).addClass('open');
		}
	});
});

var popup;
popup = new PopupManager(1000, 900, ctx + "/pages/");
popup2 = new PopupManager(1084, 1084, ctx + "/pages/");
popup2.titulo = i18n_message('citcorpore.comum.pesquisarapida');

function GrupoQtde(){
	this.id = '';
	this.qtde = 0;
}

abrePopupPesquisa = function( ) {
	$("#popupCadastroRapido" ).dialog({
		title: i18n_message("citcorpore.comum.cadastrorapido"),
		width: 1230,
		height: 600,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
	});

	document.getElementById('popupCadastroRapido').style.overflow = "hidden";
	document.getElementById('tdAvisosSol').innerHTML = '';

	popup2.abrePopup('pesquisaRequisicaoLiberacao','onClosePopUp');
}

AddBotoesTarefa = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var solicitacaoDto = tarefaDto.solicitacaoDto;
	if (solicitacaoDto != null && solicitacaoDto.status == situacaoLiberacaoSuspensa)
		return value;

	var str = "<table cellpadding='0' cellspacing='0'><tr>";
	if (tarefaDto.executar == 'S') {
		var idResponsavel = Number(tarefaDto.idResponsavelAtual);
		if (idResponsavel != idUsuario) {
			str += '<td>';
			str += "<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title='" + i18n_message('gerenciaservico.capturartarefa') + "' onclick='capturarTarefa("+tarefaDto.responsavelAtual+","+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idRequisicaoLiberacao +")'>" + i18n_message('citcorpore.comum.capturar') + "</span>";
			str += '</td>';
		}

		str += '<td>';
		str += "<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title='" + i18n_message('gerenciaservico.iniciarexecutartarefa') + "' onclick='prepararExecucaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idRequisicaoLiberacao+", 'E')'>" + i18n_message('citcorpore.comum.executar') + "</span>";
		str += '</td>';
	}
	str += '<td>' + value + '</td>';
	str += '</tr></table>';
	return str ;
};

AddLinkRequisicao = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var solicitacaoDto = tarefaDto.solicitacaoDto;
	if (solicitacaoDto != null) {
		var str = "";
		str += "<span style='cursor:pointer;margin-top: 5px!important;' class='btn btn-mini btn-primary titulo' title='" + i18n_message('requisicaoMudanca.visualizarCadastroMudanca') + "' onclick='visualizarSolicitacao("+solicitacaoDto.idRequisicaoLiberacao+")'>" + i18n_message('citcorpore.comum.visualizar') + "</span>";
		str += "&nbsp;&nbsp;"+solicitacaoDto.idRequisicaoLiberacao;
		return str;
	}
	return "";
};

addLinkAprovarLiberacao = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var solicitacaoDto = tarefaDto.solicitacaoDto;
	var dataHora = i18n_message("gerenciaservico.delegarcompartilhartarefa");

	if (tarefaDto.delegar == 'S') {
		if (solicitacaoDto != null) {
			return "<a href='#' class='btn-action glyphicons share btn-default titulo' title='" + i18n_message('requisicaoLiberacao.encaminhar') + "' onclick='exibirDelegacaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idRequisicaoLiberacao+", " + tarefaDto.elementoFluxoDto.documentacao +")'><i></i></a>&nbsp;"+datahora;
		}
		return "";
	}
	return "";
};

function aprovarLiberacao(idRequisicao){
	if (!confirm(i18n_message("requisicaoLiberacao.confirmaAprovacao"))) {
		return;
	}
	JANELA_AGUARDE_MENU.show();
	document.form.idRequisicao.value = idRequisicao;
	document.form.fireEvent('aprovarLiberacao');
}

AddSituacao = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var solicitacaoDto = tarefaDto.solicitacaoDto;
	if (solicitacaoDto != null) {
		var str = solicitacaoDto.descrSituacao;

		if (solicitacaoDto.status != situacaoLiberacaoSuspensa && tarefaDto.suspender == 'S')
			str = "<a href='#' class='btn-action glyphicons pause btn-default titulo' title='" + i18n_message('requisicaoLiberacao.suspenderLiberacao') + "' onclick='prepararSuspensao(" + solicitacaoDto.idRequisicaoLiberacao + ")'><i></i></a> " + str;
		if (solicitacaoDto.status == situacaoLiberacaoSuspensa && tarefaDto.reativar == 'S')
			str = "<a href='#' class='btn-action glyphicons play btn-default titulo' title='" + i18n_message('requisicaoMudanca.reativarMudanca') + "' onclick='reativarSolicitacao(" + solicitacaoDto.idRequisicaoLiberacao + ")'><i></i></a> " + str;
		str = "<a href='#' class='btn-action glyphicons book btn-default titulo' title='" + i18n_message('gerenciamentoMudanca.agendarLiberacao') + "' onclick='agendaAtividade(" + solicitacaoDto.idRequisicaoLiberacao + ")'><i></i></a> " + str;
		return str;
	}
	return "";
};

AddAtraso = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var solicitacaoDto = tarefaDto.solicitacaoDto;
	var result = "";
	if (solicitacaoDto != null && parseFloat(solicitacaoDto.atraso) > parseFloat("0,00") && solicitacaoDto.status != situacaoLiberacaoSuspensa)
		result = '<font color="red">' + solicitacaoDto.atrasoStr + '</font>';
	return result;
};

AddSelTarefa = function(row, cell, value, columnDef, dataContext) {
	return "<input type='radio' name='selTarefa' value='S'/>";
};

AddImgPrioridade = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var solicitacaoDto = tarefaDto.solicitacaoDto;
	if (solicitacaoDto.prioridade == '1'){
		return value + " <img src='" + ctx + "imagens/b.gif' style='cursor:pointer;' title='" + i18n_message('gerenciaservico.prioridadealta') + "'/> ";
	}
	return value;
};

var arrayTarefas = [],
	gridTarefa = {},
	tarefas = [],
	colunasTarefa = [
		{ id: "idRequisicao" , name: i18n_message('citcorpore.comum.numero'), field: "idRequisicao", width: 160, formatter: AddLinkRequisicao, resizable:true },
		{ id: "solicitante", name: i18n_message('solicitacaoServico.solicitante'), field: "solicitante", width: 250, resizable:true },
		{ id: "dataHoraSolicitacao", name: i18n_message('solicitacaoServico.dataHoraCriacao'), field: "dataHoraSolicitacao", width: 110 },
		{ id: "prioridade", name: i18n_message('gerenciaservico.pri'), field: "prioridade", width: 24, formatter: AddImgPrioridade, resizable:true },
		{ id: "dataHoraLimite", name: i18n_message('solicitacaoServico.prazoLimite'), field: "dataHoraLimite", width: 110 },
		{ id: "atraso", name: i18n_message('tarefa.atraso'), field: "atraso", width: 60, formatter: AddAtraso, resizable:false },
		{ id: "status", name: i18n_message('solicitacaoServico.situacao'), field: "status", width: 150,	formatter: AddSituacao, resizable:false },
		{ id: "descricao", name: i18n_message('tarefa.tarefa_atual'), field: "descricao", width: 260, formatter: AddBotoesTarefa },
		{ id: "assinatura", name: i18n_message('gerenciaservico.delegarcompartilhartarefa'),field: "liberacao", width: 155, formatter: addLinkAprovarLiberacao },
		{ id: "nomeGrupoAtual", name: i18n_message('citcorpore.comum.grupoExecutor'), field: "nomeGrupoAtual", width: 150 },
		{ id: "responsavelAtual", name: i18n_message('tarefa.responsavelatual'), field: "responsavelAtual", width: 200 },
		{ id: "compartilhamento", name: i18n_message('tarefa.compartilhadacom'), field: "compartilhamento", width: 120 }
	],
	gridOptions = {
		editable: false,
		asyncEditorLoading: false,
		enableAddRow: false,
		enableCellNavigation: true,
		enableColumnReorder: true,
		rowHeight: 40
	};

var dadosGrafico;
var dadosGrafico2;
var dadosGerais;
var scriptTemposSLA = '';
var temporizador;
exibirTarefas = function(json_tarefas) {
	try{
		var tarefas = [];
		var qtdeAtrasadas = 0;
		var qtdeSuspensas = 0;
		var qtdeEmAndamento = 0;
		var qtdePri1 = 0;
		var qtdePri2 = 0;
		var qtdePri3 = 0;
		var qtdePri4 = 0;
		var qtdePri5 = 0;
		var qtdeItens = 0;
		var colGrupoSol = new HashMap();
		scriptTemposSLA = "";

		arrayTarefas = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(json_tarefas);
		for(var i = 0; i < arrayTarefas.length; i++){
			var tarefaDto = arrayTarefas[i];
			tarefaDto.solicitacaoDto = ObjectUtils.deserializeObject(tarefaDto.solicitacao_serialize);
			tarefaDto.elementoFluxoDto = ObjectUtils.deserializeObject(tarefaDto.elementoFluxo_serialize);
		}

		var strTableTemposSLA = '';
		strTableTemposSLA += "<img width='20' height='20' ";
		strTableTemposSLA += "alt='" +  i18n_message('ativaotemporizador')+"' id='imgAtivaTimer' style='opacity:0.5;display:none' ";
		strTableTemposSLA += "title='"+ i18n_message('citcorpore.comum.ativadestemporizador') +"' ";
		strTableTemposSLA += "src='" + ctx + "/template_new/images/cronometro.png' />";
		strTableTemposSLA += "<table class='table' cellpadding='0' cellspacing='0'>";
		strTableTemposSLA = strTableTemposSLA + "<tr><td><b>" + i18n_message('gerenciaservico.slasandamento') + "</b></td></tr>";

		for(var i = 0; i < arrayTarefas.length; i++){
			var idRequisicao = "";
			var solicitante = "";
			var prioridade = "";
			var situacao = "";
			var prazo = "";
			var dataHoraSolicitacao = "";
			var dataHoraLimite = "";
			var nomeGrupoAtual = "";

			var tarefaDto = arrayTarefas[i];
			var solicitacaoDto = tarefaDto.solicitacaoDto;
			if (solicitacaoDto != null) {
				if (solicitacaoDto.prioridade == '1'){
					qtdePri1++;
				}
				if (solicitacaoDto.prioridade == '2'){
					qtdePri2++;
				}
				if (solicitacaoDto.prioridade == '3'){
					qtdePri3++;
				}
				if (solicitacaoDto.prioridade == '4'){
					qtdePri4++;
				}
				if (solicitacaoDto.prioridade == '5'){
					qtdePri5++;
				}
				var grupoNome = solicitacaoDto.nomeGrupoAtual;
				if (grupoNome == null){
					grupoNome = ' --'+ i18n_message("citcorpore.comum.aclassificar")+ '--';
				}
				var auxGrp = colGrupoSol.get(grupoNome);
				if (auxGrp != undefined){
					auxGrp.qtde++;
				}else{
					var grupoQtde = new GrupoQtde();
					grupoQtde.id = grupoNome;
					grupoQtde.qtde = 1;
					colGrupoSol.set(grupoNome, grupoQtde);
				}

				idRequisicao = ""+solicitacaoDto.idRequisicaoLiberacao;
				solicitante = ""+solicitacaoDto.nomeSolicitante;
				if (solicitacaoDto.prazoHH < 10)
					prazo = "0";
				prazo += solicitacaoDto.prazoHH + ":";
				if (solicitacaoDto.prazoMM < 10)
					prazo += "0";
				prazo += solicitacaoDto.prazoMM;
				prioridade = ""+solicitacaoDto.prioridade;
				dataHoraSolicitacao = solicitacaoDto.dataHoraInicioStr;
				if (solicitacaoDto.status != situacaoLiberacaoSuspensa) {
					dataHoraLimite = solicitacaoDto.dataHoraTerminoStr;
				}
				nomeGrupoAtual = solicitacaoDto.nomeGrupoAtual;

				if (solicitacaoDto.atraso > 0.0 && solicitacaoDto.status != situacaoLiberacaoSuspensa) {
					qtdeAtrasadas++;
				} else if (solicitacaoDto.status == situacaoLiberacaoSuspensa && tarefaDto.reativar == 'S') {
					qtdeSuspensas++;
				} else {
					qtdeEmAndamento++;
					if (qtdeItens < 15){
						scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + solicitacaoDto.idRequisicaoLiberacao + "', " + "'barraProgresso" + solicitacaoDto.idRequisicaoLiberacao + "', "
							+ "'" + solicitacaoDto.dataHoraSolicitacaoToString + "', '" + solicitacaoDto.dataHoraLimiteToString + "'));";
						strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idRequisicaoLiberacao + "</b>: <label id='tempoRestante" + solicitacaoDto.idRequisicaoLiberacao + "'></label>";
						strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + solicitacaoDto.idRequisicaoLiberacao + "'></div></td></tr>";
					}
					qtdeItens++;
				}
			}

			tarefas[i] = {
				iniciar: tarefaDto.executar,
				executar: tarefaDto.executar,
				delegar: tarefaDto.delegar,
				idRequisicao: idRequisicao,
				solicitante: solicitante,
				prioridade: prioridade,
				dataHoraSolicitacao: dataHoraSolicitacao,
				descricao: tarefaDto.elementoFluxoDto.documentacao,
				situacao: "",
				atraso: "",
				dataHoraLimite: dataHoraLimite,
				responsavelAtual: tarefaDto.responsavelAtual,
				nomeGrupoAtual: nomeGrupoAtual,
				compartilhamento: tarefaDto.compartilhamento
			}
		}
		strTableTemposSLA = strTableTemposSLA + '</table>';
		if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){reativarSolicitacao
			var info = '';
			if (qtdeAtrasadas > 0){
				info += ' <font color="red"><b>' + qtdeAtrasadas + ' </b>  ' + i18n_message("requisicaoLiberacao.requisicoes_liberacao_atrasado") + '</font><br>';
			}
			if (qtdeSuspensas > 0){
				info += ' <b>' + qtdeSuspensas + ' </b>' + i18n_message("requisicaoLiberacao.requisicoes_liberacao_suspenso");
			}

			if (document.getElementById('fraRequisicaoLiberacao').src == "about:blank"){
				info = '<table cellpadding="0" cellspacing="0" style="overflow: auto"><tr><td style="width:20px">&nbsp;</td><td style="vertical-align: top;">' + info + '</td><td><div id="divGrafico" style="height: 250px; min-width: 340px;"></div></td><td><div id="divGrafico2" style="height: 250px; min-width: 250px;"></div></td><td><div id="divGrafico3" style="height: 250px; min-width: 270px;overflow:auto;"></div></td></tr></table>';
				document.getElementById('tdAvisosSol').innerHTML = info;
				dadosGrafico = [[i18n_message("gerenciaservico.emandamento"),qtdeEmAndamento],[i18n_message("gerenciaservico.suspensas"),qtdeSuspensas],[i18n_message("gerenciaservico.atrasadas"),qtdeAtrasadas]];
				dadosGrafico2 = [[' 1 ',qtdePri1],[' 2 ',qtdePri2],[' 3 ',qtdePri3],[' 4 ',qtdePri4],[' 5 ',qtdePri5]];
				window.setTimeout(atualizaGrafico, 1000);
				window.setTimeout(atualizaGrafico2, 1000);

				var colArray = colGrupoSol.toArray();
				dadosGerais = new Array();
				if (colArray){
					for (var iAux = 0; iAux < colArray.length; iAux++){
						dadosGerais[iAux] = [colArray[iAux].id, colArray[iAux].qtde];
					}
				}
				window.setTimeout(atualizaGrafico3, 1000);
			}
		}

		document.getElementById("divConteudoLista").innerHTML = "<div id=\"divConteudoListaInterior\" style=\"height: 100%; width: 100%\"></div>";
		gridTarefa = new Slick.Grid($("#divConteudoListaInterior"), tarefas,  colunasTarefa, gridOptions );
	}catch(e){
		alert(i18n_message("gerenciamentoLiberacao.ErroRenderizarGrid"));
		document.form.erroGrid.value = e;
		document.fireEvent("imprimeErroGrid");
	}
};

function inicializarTemporizador(){
	if(temporizador == null){
		temporizador = new Temporizador("imgAtivaTimer");
	} else {
		temporizador = null;
		try{
			temporizador.listaTimersAtivos = [];
		}catch(e){}
		try{
			temporizador.listaTimersAtivos.length = 0;
		}catch(e){}
		temporizador = new Temporizador("imgAtivaTimer");
	}
}

capturarTarefa = function(responsavelAtual, idTarefa, idRequisicao) {
	var msg = "";
	if (responsavelAtual == '')
		msg = i18n_message("gerencia.confirm.atribuicaotarefa") + " '" + login + "'  ";
	else
		msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavelAtual + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  + " '" + login + "' " + i18n_message("gerencia.confirm.atribuicaotarefa_3");

	if (!confirm(msg))
		return;
	JANELA_AGUARDE_MENU.show();
	document.form.idTarefa.value = idTarefa;
	document.form.idRequisicao.value = idRequisicao;
	document.form.fireEvent('capturaTarefa');
};

function atualizaGrafico(){
	plotaGraficoHCharts(dadosGrafico, "divGrafico", i18n_message("requisicaoMudanca.situacaoAtividades"))
}

function atualizaGrafico2(){
	plotaGraficoHCharts(dadosGrafico2, "divGrafico2", i18n_message("requisicaoMudanca.resumoPrioridade"));
}

function atualizaGrafico3(){
	plotaGraficoHCharts(dadosGerais, "divGrafico3", i18n_message("requisicaoMudanca.resumoGrupo"));
}

var myLayout;
var popup = new PopupManager(1000, 900, ctx + "/pages/");

$(document).ready(function () {
	$("#POPUP_VISAO" ).dialog({
		title: i18n_message("citcorpore.comum.visao"),
		width: 900,
		height: 600,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_VISAO").hide();

	$("#POPUP_BUSCA" ).dialog({
		title: i18n_message("citcorpore.comum.buscarapida"),
		width: 250,
		height: 300,
		modal: false,
		autoOpen: false,
		resizable: false
	});

	$("#popupCadastroRapido" ).dialog({
		title: i18n_message("citcorpore.comum.cadastrorapido"),
		width: 900,
		height: 600,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
	});

	// create the layout - with data-table wrapper as the layout-content element
	myLayout = $('body').layout({
		west__size: 20,
		south__size: 420,
		center__minHeight: 350, //limite maximo que o panel pode subir.
		west__onresize: function (pane, $pane, state, options) {
			var $content = $pane.children('.ui-layout-content'),
			gridHdrH = $content.children('.slick-header').outerHeight(),
			paneHdrH = $pane.children(':first').outerHeight(),
			paneFtrH = $pane.children(':last').outerHeight(),
			$gridList = $content.children('.slick-viewport');
			$gridList.height( state.innerHeight - paneHdrH - paneFtrH - gridHdrH );
		},
		south__onresize: function (pane, $pane, state, options) {
		var gridHdrH = $pane.children('.slick-header').outerHeight(),
		$gridList   = $pane.children('.slick-viewport');
		$gridList.height( state.innerHeight - gridHdrH );
		document.form.fireEvent('exibeTarefas');
		},
		east__initClosed: true,
		east__size: 0,
		west: {
			onclose_end: function() {
				myLayout.close("west");
				document.getElementById("tdLeft").style.backgroundColor = 'white';
			},
			onopen_end: function(){
				myLayout.open("west");
				document.getElementById("tdLeft").style.backgroundColor = 'lightgray';
			}
		},
		south: {
			onclose_end: function(){
				myLayout.close("south");
				document.getElementById("tdDown").style.backgroundColor = 'white';
			},
			onopen_end: function(){
				myLayout.open("south");
				document.getElementById("tdDown").style.backgroundColor = 'lightgray';
				document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'>" + i18n_message('citcorpore.comum.aguardecarregando') + "</div>";
				document.form.fireEvent('exibeTarefas');
			},
			onresize_end: function(){
				document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'>" + i18n_message('citcorpore.comum.aguardecarregando') + "</div>";
				document.form.fireEvent('exibeTarefas');
			}
		}
	});

	$('body > h2.loading').hide(); // hide Loading msg
	if(idRequisicao) {
		visualizarSolicitacao(idRequisicao);
	} else {
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	}

	myLayout.hide('north');
	myLayout.hide('west');
});

voltar = function(){
	if (document.getElementById('fraRequisicaoLiberacao').src == "about:blank"){
		window.location = ctx + '/pages/index/index.load';
	}else{
		window.location = ctx + '/pages/gerenciamentoLiberacao/gerenciamentoLiberacao.load';
	}
};

editarSolicitacao = function(idRequisicao) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	document.getElementById('fraRequisicaoLiberacao').src = ctx + pageCadastro + "?idRequisicaoLiberacao=" + idRequisicao + "&escalar=S&alterarSituacao=N";
};

visualizarSolicitacao = function(idRequisicao) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	JANELA_AGUARDE_MENU.show();
	document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	document.getElementById('fraRequisicaoLiberacao').src = ctx + pageCadastro + "?idRequisicaoLiberacao=" + idRequisicao + "&escalar=N&alterarSituacao=N&editar=N";
};

reclassificarSolicitacao = function(idRequisicao) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	document.getElementById('fraRequisicaoLiberacao').src = ctx + pageCadastro + "?idRequisicaoLiberacao=" + idRequisicao + "&reclassificar=S";
};

prepararSuspensao = function(idRequisicao) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	document.getElementById('fraVisao').src = "about:blank";
	document.getElementById('fraVisao').src = ctx + "/pages/suspensaoLiberacao/suspensaoLiberacao.load?idRequisicaoLiberacao=" + idRequisicao;
	$( "#POPUP_VISAO" ).dialog({ height: 500 });
	$( "#POPUP_VISAO" ).dialog({ title: i18n_message("gerenciamentoLiberacao.suspenderLiberacao") });
	$( "#POPUP_VISAO" ).dialog( 'open' );
};

prepararSuspensao = function(idRequisicao) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	document.getElementById('fraVisao').src = "about:blank";
	document.getElementById('fraVisao').src = ctx + "/pages/suspensaoLiberacao/suspensaoLiberacao.load?idRequisicaoLiberacao=" + idRequisicao;
	$( "#POPUP_VISAO" ).dialog({ height: 500 });
	$( "#POPUP_VISAO" ).dialog({ title: i18n_message("gerenciamentoLiberacao.suspenderLiberacao") });
	$( "#POPUP_VISAO" ).dialog( 'open' );
};

reativarSolicitacao = function(idRequisicao) {
	if (!confirm(i18n_message("requisicaoLiberacao.confirmaReativacaoLiberacao")))
		return;
	JANELA_AGUARDE_MENU.show();
	document.form.idRequisicao.value = idRequisicao;
	document.form.fireEvent('reativaRequisicao');
};

agendaAtividade = function(idRequisicao) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	document.getElementById('fraVisao').src = "about:blank";
	document.getElementById('fraVisao').src = ctx + "/pages/agendarAtividadeRequisicaoLiberacao/agendarAtividadeRequisicaoLiberacao.load?idRequisicaoLiberacao="+idRequisicao;
	$( "#POPUP_VISAO" ).dialog({ height: 600 });
	$( "#POPUP_VISAO" ).dialog({ title: i18n_message("gerenciaservico.agendaratividade") });
	$( "#POPUP_VISAO" ).dialog( 'open' );
};

prepararExecucaoTarefa = function(idTarefa,idRequisicao,acao) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	document.form.idTarefa.value = idTarefa;
	document.form.acaoFluxo.value = acao;
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent('preparaExecucaoTarefa');
};

prepararLiberacaoSLA = function(idTarefa,idRequisicao) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	document.getElementById('fraVisao').src = "about:blank";
	document.getElementById('fraVisao').src = ctx + "/pages/mudarSLA/mudarSLA.load?idRequisicaoLiberacao=" + idRequisicao;
	$( "#POPUP_VISAO" ).dialog({ height: 550 });
	$( "#POPUP_VISAO" ).dialog({ title: i18n_message("gerenciaservico.mudarsla") });
	$( "#POPUP_VISAO" ).dialog( 'open' );
};

exibirDelegacaoTarefa = function(idTarefa,idRequisicao,nomeTarefa) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	document.getElementById('fraVisao').src = "about:blank";
	document.getElementById('fraVisao').src = ctx + "/pages/delegacaoLiberacao/delegacaoLiberacao.load?idRequisicaoLiberacao=" + idRequisicao+"&idTarefa=" + idTarefa + "&nomeTarefa=" + nomeTarefa;
	$( "#POPUP_VISAO" ).dialog({ height: 400 });
	$( "#POPUP_VISAO" ).dialog({ title: i18n_message("gerenciaservico.delegarcompartilhartarefa") });
	$( "#POPUP_VISAO" ).dialog( 'open' );
};

exibirVisao = function(titulo,idVisao,idFluxo,idTarefa,acao){
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	document.getElementById('fraRequisicaoLiberacao').src = ctx + "/pages/dinamicViews/dinamicViews.load?modoExibicao=J&idVisao=" + idVisao + "&idFluxo=" + idFluxo + "&idTarefa=" + idTarefa + "&acaoFluxo=" + acao;
};

fecharVisao = function(){
	$( "#POPUP_VISAO" ).dialog( 'close' );
	document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	document.form.fireEvent('exibeTarefas');
	myLayout.open("south");
	JANELA_AGUARDE_MENU.hide();
};

abrirSolicitacao = function(){
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	document.getElementById('fraRequisicaoLiberacao').src = ctx + pageCadastro;
};

exibirUrl = function(titulo, url){
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	document.getElementById('fraRequisicaoLiberacao').src = ctx + "/" + url;
};

fecharLiberacao = function(){
	myLayout.open("south");
	document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
	document.form.fireEvent('exibeTarefas');
};

atualizarListaTarefas = function() {
	myLayout.open("south");
	document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'>" + i18n_message('citcorpore.comum.aguardecarregando') + "</div>";
	document.form.idRequisicaoSel.value = document.formPesquisa.idRequisicaoSel.value;
	document.form.atribuidaCompartilhada.value = document.formPesquisa.atribuidaCompartilhada.value;
	document.form.fireEvent('exibeTarefas')
}

abrePopup = function(obj,func) {
	popup.abrePopup('usuario','()');
}

function controleArea(tdName, areaName){
	if (document.getElementById(tdName).style.backgroundColor == 'white'){
		myLayout.open(areaName);
		document.getElementById(tdName).style.backgroundColor = 'lightgray';
	}else{
		myLayout.close(areaName);
		document.getElementById(tdName).style.backgroundColor = 'white';
	}
}

function plotaGrafico(dados, componente) {
	var plot1 = jQuery.jqplot(componente, [ dados ], {
		seriesDefaults : {
			renderer : jQuery.jqplot.PieRenderer,
			rendererOptions : {
				showDataLabels : true
			}
		},
		legend : {
			show : true,
			location : 'e'
		}
	});
}

function plotaGraficoHCharts(dados, componente, title){
	$("#"+componente).highcharts({
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: true
		},
		title: {
			text: title
		},
		credits: {
			enable: false
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					color: '#000000',
					connectorColor: '#000000',
					format: '<b>{point.name}</b>: {point.percentage:.1f} %'
				},
			showInLegend: true
			}
		},
		series: [{
			type: 'pie',
			name: i18n_message("questionario.percentualTxt"),
			data: dados
		}]
	});
}

function janelaAguarde(){
	JANELA_AGUARDE_MENU.show();
}

function fechaJanelaAguarde(){
	JANELA_AGUARDE_MENU.hide();
}

function disableEnterKey(e){
	var key;

	if(window.event) {
		key = window.event.keyCode; //IE
	} else {
		key = e.which; //firefox
	}

	if(key == 13) {
		return false;
	}
	return true;
}

function atualizaPagina(){
	if ( document.getElementById('chkAtualiza').checked ) {
		atualizarListaTarefas();
	}
}

window.setInterval(atualizaPagina, 30000);