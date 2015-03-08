var popupPesquisaProblema = new PopupManager(1084, 1084, ctx + "/pages/");
popupPesquisaProblema.titulo = i18n_message("citcorpore.comum.pesquisarapida");

var pageCadastro = "/pages/problema/problema.load";

var myLayout;
var popup = new PopupManager(1000, 900, ctx + "/pages/");

AddBotoesTarefa = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var problemaDto = tarefaDto.problemaDto;
	if (problemaDto != null && problemaDto.status == i18n_message('citcorpore.comum.suspensa'))
		return value;

	var str = "<table cellpadding='0' cellspacing='0'><tr>";
	if (tarefaDto.executar == 'S') {
		if(problemaDto.responsavel != login){
			str += '<td>';
			str += "<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title='" + i18n_message('gerenciaservico.capturartarefa') + "' onclick=\"capturarTarefa('" + problemaDto.responsavel + "', " + tarefaDto.idItemTrabalho + "," + problemaDto.idProblema + ")\">" + i18n_message('citcorpore.comum.capturar') + "</span>";
			str += '</td>';
		}
		str += '<td>';
		str += "<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title='" + i18n_message('gerenciaservico.iniciarexecutartarefa') + "' onclick=\"prepararExecucaoTarefa(" + tarefaDto.idItemTrabalho + "," + problemaDto.idProblema + ",'E')\">" + i18n_message('citcorpore.comum.executar') + "</span>";
		str += '</td>';
	}

	str += '<td>' + value + '</td>';
	str += '</tr></table>';

	return str ;
};

AddLinkProblema = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var problemaDto = tarefaDto.problemaDto;
	if (problemaDto != null) {
		var str = "<span style='cursor:pointer;margin-top: 5px!important;' class='btn btn-mini btn-primary titulo' title='" + i18n_message('problema.vizualizarCadastroProblema') + "' onclick='visualizarSolicitacao(" + problemaDto.idProblema + ")'>" + i18n_message('citcorpore.comum.visualizar') + "</span>";
		str += "&nbsp;&nbsp;" + problemaDto.idProblema;
		return str;
	}
	return "";
};

AddStatus = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var problemaDto = tarefaDto.problemaDto;
	if (problemaDto != null) {
		var str = problemaDto.status;

		if (problemaDto.atrasoSLA > 0.0 && problemaDto.status != i18n_message('citcorpore.comum.suspensa')) {
			str += " <img src='" + ctx + "/imagens/exclamation02.gif' height='15px' title='" + i18n_message('gerenciaservico.atrasada') + " />";
		}
		if (problemaDto.status != i18n_message('citcorpore.comum.suspensa') && tarefaDto.suspender == 'S') {
			str = "<a href='#' class='btn-action glyphicons pause btn-default titulo' title='" + i18n_message('gerenciaservico.suspenderProblema') + " onclick='prepararSuspensao(" + problemaDto.idProblema + ")'><i></i></a> " + str;
		}
		if (problemaDto.status == i18n_message('citcorpore.comum.suspensa') && tarefaDto.reativar == 'S') {
			str = "<a href='#' class='btn-action glyphicons play btn-default titulo' title='" + i18n_message('gerenciaservico.reativarProblema') + " onclick='reativarProblema(" + problemaDto.idProblema + ")'><i></i></a> " + str;
		}
		str = "<a href='#' class='btn-action glyphicons book btn-default titulo' title='" + i18n_message('gerenciaservico.agendaratividade') + " onclick='agendaAtividade(" + problemaDto.idProblema + ")'><i></i></a> " + str;
		return str;
	}
	return "";
};

AddImgPrioridade = function(row, cell, value, columnDef, dataContext) {
	var tarefaDto = arrayTarefas[row];
	var problemaDto = tarefaDto.problemaDto;
	if (problemaDto.prioridade == '1'){
		return value + " <img src='" + ctx + "/imagens/b.gif' style='cursor:pointer;' title='" + i18n_message('gerenciaservico.prioridadealta') + " /> ";
	}
	return value;
};

var arrayTarefas = [],
	gridTarefa = {},
	tarefas = [],
	colunasTarefa = [
			{ id: "idProblema" , name: i18n_message('citcorpore.comum.numero') , field: "idProblema" , width: 160 , formatter: AddLinkProblema, resizable:true },
			{ id: "titulo" , name: i18n_message('citcorpore.comum.titulo') , field: "titulo" , width: 230 },
			{ id: "contrato" , name: i18n_message('contrato.contrato') , field: "contrato" , width: 150 },
			{ id: "dataCriacao" , name: i18n_message('solicitacaoServico.dataHoraCriacao') , field: "dataHoraCapturaStr" , width: 120 },
			{ id: "prioridade" , name: i18n_message('gerenciaservico.pri') , field: "prioridade" , width: 30  ,  formatter: AddImgPrioridade },
			{ id: "dataHoraLimite" , name: i18n_message('solicitacaoServico.prazoLimite') , field: "dataHoraLimiteStr" , width: 120 },
			{ id: "solicitante" , name:  i18n_message('solicitacaoServico.solicitante') , field: "solicitante" , width: 250   },
			{ id: "status" , name: i18n_message('solicitacaoServico.situacao') , field: "status" , width: 150 , formatter: AddStatus, resizable:true },
			{ id: "descricao" , name: i18n_message('tarefa.tarefa_atual') , field: "descricao" , width: 250 ,  formatter: AddBotoesTarefa },
			{ id: "grupoAtual" , name: i18n_message('citcorpore.comum.grupoExecutor') , field: "nomeGrupoAtual" , width: 170 },
			{ id: "responsavel" , name: i18n_message('tarefa.responsavelatual')	 , field: "responsavel" , width: 120 }
		], gridOptions = {
			editable: false,
			asyncEditorLoading: false,
			enableAddRow: false,
			enableCellNavigation: true,
			enableColumnReorder: true,
			rowHeight: 40
		};

$(document).ready(function() {
	$('li.dropdown').click(function() {
		if ($(this).is('.open')) {
		$(this).removeClass('open');
		} else {
		$(this).addClass('open');
		}
	});
});

$(document).ready(function () {
	$( "#POPUP_VISAO" ).dialog({
		title: 'Visão',
		width: "80%",
		height: $(window).height()-100,
		modal: true,
		autoOpen: false,
		resizable: false,
		show: "fade",
		hide: "fade"
	});

	$("#POPUP_VISAO").hide();

	$( "#POPUP_BUSCA" ).dialog({
		title: 'Busca Rápida',
		width: 250,
		height: 300,
		modal: false,
		autoOpen: false,
		resizable: false
	});

	$( "#popupCadastroRapido" ).dialog({
		title: 'Cadastro Rápido',
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
				$gridList = $pane.children('.slick-viewport');
				$gridList.height( state.innerHeight - gridHdrH);
				document.form.fireEvent('exibeTarefas');
			},
		east__initClosed: true,
		east__size: 0,
		west: {
			onclose_end: function() {
				myLayout.close("west");
				document.getElementById("tdLeft").style.backgroundColor = 'white';
			},
			onopen_end: function() {
				myLayout.open("west");
				document.getElementById("tdLeft").style.backgroundColor = 'lightgray';
			}
		},
		south: {
			onclose_end: function() {
				myLayout.close("south");
				document.getElementById("tdDown").style.backgroundColor = 'white';
			},
			onopen_end: function() {
				myLayout.open("south");
				document.getElementById("tdDown").style.backgroundColor = 'lightgray';
				document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='width: 100%; overflow: -moz-scrollbars-vertical; overflow-y: scroll; overflow: scroll; height: 101%;'>"+i18n_message("citcorpore.comum.aguardecarregando")+"</div>";
				document.form.fireEvent('exibeTarefas');
			},
			onresize_end: function() {
				document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='width: 100%; overflow: -moz-scrollbars-vertical; overflow-y: scroll; overflow: scroll; height: 101%;'>"+i18n_message("citcorpore.comum.aguardecarregando")+"</div>";
				document.form.fireEvent('exibeTarefas');
			}
		}
	});

	$('body > h2.loading').hide(); // hide Loading msg
	if(idProblema) {
		visualizarSolicitacao(idProblema);
	} else {
		document.getElementById('fraRequisicaoProblema').src = "about:blank";
	}

	myLayout.hide('north');
	myLayout.hide('west');
});

capturarTarefa = function(responsavel, idTarefa, idProblema) {
	var msg = "";
	if (responsavel == '')
		msg = i18n_message("gerencia.confirm.atribuicaotarefa") + " '" + login + "' " ;
	else
		msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavel + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  + " '" + login + "' " + i18n_message("gerencia.confirm.atribuicaotarefa_3");

	if (!confirm(msg))
		return;
	document.form.idTarefa.value = idTarefa;
	document.form.idProblema.value = idProblema;
	document.form.fireEvent('capturaTarefa');
};

function atualizaGrafico(){
	plotaGraficoHCharts(dadosGrafico, "divGrafico", i18n_message("requisicaoMudanca.situacaoAtividades"));
}

function atualizaGrafico2(){
	plotaGraficoHCharts(dadosGrafico2, "divGrafico2", i18n_message("requisicaoMudanca.resumoPrioridade"));
}

function atualizaGrafico3(){
	plotaGraficoHCharts(dadosGerais, "divGrafico3", i18n_message("requisicaoMudanca.resumoGrupo"));
}

function plotaGrafico(dados, componente) {
	var plot1 = jQuery.jqplot(componente, [ dados ], {
		seriesDefaults : {
			// Make this a pie chart.
			renderer : jQuery.jqplot.PieRenderer,
			rendererOptions : {
				// Put data labels on the pie slices.
				// By default, labels show the percentage of the slice.
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

atualizarListaTarefas = function() {
	myLayout.open("south");
	document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='width: 100%; overflow: -moz-scrollbars-vertical; overflow-y: scroll; overflow: scroll; height: 101%;'>"+i18n_message("citcorpore.comum.aguardecarregando")+"</div>";
	document.form.idProblemaSel.value = document.formPesquisa.idProblemaSel.value;
	document.form.fireEvent('exibeTarefas')
}

visualizarSolicitacao = function(idProblema) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraRequisicaoProblema').src = "about:blank";
	document.getElementById('fraRequisicaoProblema').src = ctx + pageCadastro + "?idProblema="+idProblema+"&escalar=N&alterarSituacao=N&editar=N";
};

voltar = function(){
	if (document.getElementById('fraRequisicaoProblema').src == "about:blank"){
		window.location = ctx + '/pages/index/index.load';
	} else {
		window.location = ctx + '/pages/gerenciamentoProblemas/gerenciamentoProblemas.load';
	}
};

editarSolicitacao = function(idProblema) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraRequisicaoProblema').src = "about:blank";
	document.getElementById('fraRequisicaoProblema').src = ctx + pageCadastro + "?idProblema="+idProblema+"&escalar=S&alterarSituacao=N";
};

fecharProblema = function(){
	myLayout.open("south");
	document.getElementById('fraRequisicaoProblema').src = "about:blank";
	document.form.fireEvent('exibeTarefas');
};

fecharVisao = function(){
	$( "#POPUP_VISAO" ).dialog( 'close' );
	document.getElementById('fraRequisicaoProblema').src = "about:blank";
	document.form.fireEvent('exibeTarefas');
	myLayout.open("south");
};

abrirSolicitacao = function(){
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraRequisicaoProblema').src = "about:blank";
	document.getElementById('fraRequisicaoProblema').src = ctx + pageCadastro;
};

function disableEnterKey(e){
	var key;

	if(window.event)
		key = window.event.keyCode; //IE
	else
		key = e.which; //firefox

	if(key == 13)
		return false;
	else
		return true;
}

abrePopupPesquisa = function( ) {
	$( "#popupCadastroRapido" ).dialog({
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

	popupPesquisaProblema.abrePopup('pesquisaProblema','onClosePopUp');
}

function atualizaPagina(){
	if ( document.getElementById('chkAtualiza').checked ) {
		atualizarListaTarefas();
	}
}

prepararExecucaoTarefa = function(idTarefa,idProblema,acao) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	document.getElementById('fraRequisicaoProblema').src = "about:blank";
	document.form.idTarefa.value = idTarefa;
	document.form.acaoFluxo.value = acao;
	document.form.idProblema.value = idProblema;
	document.form.fireEvent('preparaExecucaoTarefa');
};

exibirUrl = function(titulo, url){
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraRequisicaoProblema').src = "about:blank";
	document.getElementById('fraRequisicaoProblema').src = ctx + "/" + url;
};

function GrupoQtde(){
	this.id = '';
	this.qtde = 0;
}

prepararSuspensao = function(idProblema) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	document.getElementById('fraVisao').src = "about:blank";
	document.getElementById('fraVisao').src = ctx + "/pages/suspensaoProblema/suspensaoProblema.load?idProblema="+idProblema;
	$( "#POPUP_VISAO" ).dialog({ height: 500 });
	$( "#POPUP_VISAO" ).dialog({ title: i18n_message("problema.suspenderProblema") });
	$( "#POPUP_VISAO" ).dialog( 'open' );
};

reativarProblema = function(idProblema) {
	if (!confirm(i18n_message("gerencia.confirm.reativacaoSolicitacao")))
		return;
	document.form.idProblema.value = idProblema;
	document.form.fireEvent('reativaProblema');
};

agendaAtividade = function(idProblema) {
	document.getElementById('tdAvisosSol').innerHTML = '';
	document.getElementById('fraVisao').src = "about:blank";
	document.getElementById('fraVisao').src = ctx + "/pages/agendarAtividadeProblema/agendarAtividadeProblema.load?idProblema="+idProblema;
	$( "#POPUP_VISAO" ).dialog({ height: $(window).height()-100 });
	$( "#POPUP_VISAO" ).dialog({ title: i18n_message("gerenciaservico.agendaratividade") });
	$( "#POPUP_VISAO" ).dialog( 'open' );
};
/* Agendador de atualizacao. */
window.setInterval(atualizaPagina, 30000);

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
			tarefaDto.problemaDto = ObjectUtils.deserializeObject(tarefaDto.problema_serialize);
			tarefaDto.elementoFluxoDto = ObjectUtils.deserializeObject(tarefaDto.elementoFluxo_serialize);
		}

		var strTableTemposSLA = '';
		strTableTemposSLA += "<img width='20' height='20' ";
		strTableTemposSLA += "alt='"+  i18n_message('ativaotemporizador')+"' id='imgAtivaTimer' style='opacity:0.5;display:none' ";
		strTableTemposSLA += "title='"+ i18n_message('citcorpore.comum.ativadestemporizador') + "' ";
		strTableTemposSLA += "src=" + ctx + "/template_new/images/cronometro.png";
		strTableTemposSLA += "<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\">";
		strTableTemposSLA = strTableTemposSLA + "<tr><td><b>" + i18n_message('gerenciaservico.slasandamento') + "</b></td></tr>";

		for(var i = 0; i < arrayTarefas.length; i++){
			var idProblema = "";
			var solicitante = "";
			var titulo = "";
			var contrato = "";
			var dataHoraCaptura = "";
			var nomeCriador = "";
			var nomeProprietario = "";
			var status = "";
			var prioridade = "";
			var dataHoraLimite = "";
			var nomeGrupoAtual = "";

			var tarefaDto = arrayTarefas[i];
			var problemaDto = tarefaDto.problemaDto;
			if (problemaDto != null) {

				if (problemaDto.prioridade == '1'){
					qtdePri1++;
				}
				if (problemaDto.prioridade == '2'){
					qtdePri2++;
				}
				if (problemaDto.prioridade == '3'){
					qtdePri3++;
				}
				if (problemaDto.prioridade == '4'){
					qtdePri4++;
				}
				if (problemaDto.prioridade == '5'){
					qtdePri5++;
				}

				var grupoNome = problemaDto.nomeGrupoAtual;
				if (grupoNome == null){
					grupoNome = ' --'+ i18n_message("citcorpore.comum.aclassificar")+ '--';
				}

				idProblema = ""+problemaDto.idProblema;
				solicitante = ""+problemaDto.solicitante;

				prioridade = ""+problemaDto.prioridade;
				dataHoraCaptura = problemaDto.dataHoraCapturaStr;

				if (problemaDto.status != i18n_message('citcorpore.comum.suspensa')) {
					dataHoraLimite = problemaDto.dataHoraLimiteStr;
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

				nomeGrupoAtual = problemaDto.nomeGrupoAtual;

				if (problemaDto.atraso > 0.0 && problemaDto.status != i18n_message('citcorpore.comum.suspensa')){
					qtdeAtrasadas++;
				}else if (problemaDto.status == i18n_message('citcorpore.comum.suspensa') && tarefaDto.reativar == 'S'){
					qtdeSuspensas++;
				}else {
					qtdeEmAndamento++;
					if (qtdeItens < 15){
						scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + problemaDto.idProblema + "', " + "'barraProgresso" + problemaDto.idProblema + "', "
							+ "'" + problemaDto.dataHoraCapturaStr + "', '" + problemaDto.dataHoraLimiteStr + "'));";
						strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + problemaDto.idProblema + "</b>: <label id='tempoRestante" + problemaDto.idProblema + "'></label>";
						strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + problemaDto.idProblema + "'></div></td></tr>";
					}
					qtdeItens++;
				}
			}

			tarefas[i] = {
				iniciar: tarefaDto.executar,
				executar: tarefaDto.executar,
				idProblema: problemaDto.idProblema,
				titulo: problemaDto.titulo,
				idContrato: problemaDto.idContrato,
				contrato: problemaDto.contrato,
				dataHoraCapturaStr: problemaDto.dataHoraCapturaStr,
				solicitante: problemaDto.solicitante,
				responsavel: problemaDto.responsavel,
				status: problemaDto.status,
				prioridade: problemaDto.prioridade,
				descricao: tarefaDto.elementoFluxoDto.documentacao,
				dataHoraLimiteStr: problemaDto.dataHoraLimiteStr,
				nomeGrupoAtual: problemaDto.nomeGrupoAtual
			}
		}

		strTableTemposSLA = strTableTemposSLA + '</table>';
		if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){
			var info = '';

			if (qtdeAtrasadas > 0){
				info += ' <font color="red"><b>' + qtdeAtrasadas + ' </b> ' + i18n_message("problema.problemas_atrasado") + '</font><br>';
			}

			if (qtdeSuspensas > 0){
				info += ' <b>' + qtdeSuspensas + ' </b>' + i18n_message("problema.problemas_suspenso");
			}

			if (document.getElementById('fraRequisicaoProblema').src == "about:blank"){
				info = '<table cellpadding="0" cellspacing="0"><tr><td style="width:20px">&nbsp;</td><td style="vertical-align: top;">' + info + '</td><td><div id="divGrafico" style="height: 250px; min-width: 340px;"></div></td><td><div id="divGrafico2" style="height: 250px; min-width: 250px;"></div></td><td><div id="divGrafico3" style="height: 250px; min-width: 270px;overflow:auto;"></div></td></tr></table>';
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

		document.getElementById("divConteudoLista").innerHTML = "<div id=\"divConteudoListaInterior\" style=\"width: 100%; overflow: -moz-scrollbars-vertical; overflow-y: scroll; overflow: scroll; height: 101%;\"></div>";
		gridTarefa = new Slick.Grid($("#divConteudoListaInterior"), tarefas,  colunasTarefa, gridOptions );
	} catch(e) {
		alert(i18n_message("gerenciamentoMudanca.ErroRenderizarGrid"));
		document.form.erroGrid.value = e;
		document.fireEvent("imprimeErroGrid");
	}
};
