addEvent(window, "load", load, false);


function renderizarGraficos() {
	JANELA_AGUARDE_MENU.show()
	document.formGerenciamento.fireEvent('renderizarGraficos');
}

function renderizarResumoSolicitacoes() {
	JANELA_AGUARDE_MENU.show()
	document.form.fireEvent('exibirResumoSolicitacoes');
}   

function GrupoQtde(){
      		this.id = '';
      		this.qtde = 0;
      }
   
   function trocaBarra(txt){
		var x = new String(txt);
		x = x.replace(/{{BARRA}}/g,'\\');
		return x;
	}

$(function(){
	
	$('#listaDetalhada').click(function(){ 
		
		$('#tipoLista').val(1);
		
		$('.listaResumida').animate({opacity:0},function(){
			$(this).removeClass('ativo');
			$(this).css('display', 'none');
		});
		
		$('.listaDetalhada').animate({opacity:1},function(){
			$(this).addClass('ativo');
			$(this).css('display', 'block');
        	$(this).show();
        	removerBotaoAtivo('#listaResumida');
        	adicionarBotaoAtivo('#listaDetalhada');   
        });
    });

    $('#listaResumida').click(function(){ 
    	
    	$('#tipoLista').val(2);
    	
    	$('.listaDetalhada').animate({opacity:0},function(){
			$(this).removeClass('ativo');
			$(this).css('display', 'none');
		});
    	$('.listaResumida').animate({opacity:1},function(){
    		$(this).addClass('ativo');
    		$(this).css('display', 'block');
        	$(this).show();
        	removerBotaoAtivo('#listaDetalhada');
        	adicionarBotaoAtivo('#listaResumida');        
    	});
    });
    
    var attendeeUrl =  URL_SISTEMA+'pages/autoCompleteSolicitante/autoCompleteSolicitante.load';
    var urlAutoCompResponsavel =  URL_SISTEMA+'pages/autoCompleteResponsavel/autoCompleteResponsavel.load';

	$('#idSolicitante').select2(
	{
	    //Does the user have to enter any data before sending the ajax request
	    minimumInputLength: 0,            
	    allowClear: true,
	    placeholder: i18n_message("citcorpore.comum.selecione"),
	    ajax: {
	        //How long the user has to pause their typing before sending the next request
	        quietMillis: 150,
	        //The url of the json service
	        url: attendeeUrl,
	        dataType: 'json',
	        //Our search term and what page we are on
	        data: function (term) {
	        	var idContratoAutoComSolicitante = $("#idContrato").val();
	        	if (idContratoAutoComSolicitante == -1) {
	        		alert(i18n_message("citcorpore.comum.selecioneContrato"));
	        		$("#idSolicitante").select2("close");
	        		$("#idContrato").focus();
	        		return;
	        	}
	            return {
	            	q : term,
	                query: term, 
	                contrato : $("#idContrato").val(),
	                colection : true
	            };
	        },
	        results: function (data, page) {
	            //Used to determine whether or not there are more results available,
	            //and if requests for more data should be sent in the infinite scrolling
	            return { results: data };
	        }
	    },
        formatResult: function(exercise) {
        	return exercise.nome;
    	},
    	formatSelection: function(exercise) {
    		 return exercise.nome;
    	},
    	id: function(exercise) {
        	return exercise.idEmpregado;
    	} 
	});
	
	$('#idResponsavelAtual').select2(
	{
		//Does the user have to enter any data before sending the ajax request
	    minimumInputLength: 0,            
	    allowClear: true,
	    placeholder: i18n_message("citcorpore.comum.selecione"),
	    ajax: {
	        //How long the user has to pause their typing before sending the next request
	        quietMillis: 150,
	        //The url of the json service
	        url: urlAutoCompResponsavel,
	        dataType: 'json',
	        //Our search term and what page we are on
	        data: function (term) {
	        	var idContratoAutoComResponsavel = $("#idContrato").val();
	        	if (idContratoAutoComResponsavel == -1) {
	        		alert(i18n_message("citcorpore.comum.selecioneContrato"));
	        		$("#idResponsavelAtual").select2("close");
	        		$("#idContrato").focus();
	        		return;
	        	}
	            return {
	            	q : term,
	                query: term, 
	                contrato : $("#idContrato").val(),
	                colection : true
	            };
	        },
	        results: function (data, page) {
	            //Used to determine whether or not there are more results available,
	            //and if requests for more data should be sent in the infinite scrolling
	            return { results: data };
	        }
	    },
        formatResult: function(exercise) {
        	return exercise.nome;
    	},
    	formatSelection: function(exercise) {
    		 return exercise.nome;
    	},
    	id: function(exercise) {
        	return exercise.idEmpregado;
    	} 
	});
	
	$('#tarefaAtual').select2(
	{
	    //Does the user have to enter any data before sending the ajax request
	    minimumInputLength: 0,            
	    allowClear: true,
	    placeholder: i18n_message("citcorpore.comum.selecione"),
	    ajax: {
	        //How long the user has to pause their typing before sending the next request
	        quietMillis: 150,
	        //The url of the json service
	        url: "../pages/autoCompleteTarefaAtual/autoCompleteTarefaAtual.load",
	        dataType: 'json',
	        //Our search term and what page we are on
	        data: function (term) {
	            return {
	                query: term, 
	            };
	        },
	        results: function (data, page) {
	            //Used to determine whether or not there are more results available,
	            //and if requests for more data should be sent in the infinite scrolling
	            return { results: data };
	        }
	    },
        formatResult: function(exercise) {
        	return exercise.documentacao;
    	},
    	formatSelection: function(exercise) {
    		 return exercise.documentacao;
    	},
    	id: function(exercise) {
        	return exercise.documentacao;
    	} 
	});
	
	$(document).on('change', '#idContrato', function() {
			$('#idSolicitante').empty()
			$('#idSolicitante').select2('data', [{}], false);
			$('#idResponsavelAtual').empty()
			$('#idResponsavelAtual').select2('data', [{}], false);
    });
});

function adicionarBotaoAtivo(param){
	$(param).addClass('btn-primary').each(function() {
		$(this).find('i').addClass('icon-white');
	});
}

function removerBotaoAtivo(param){
	$(param).removeClass('btn-primary').each(function() {
		$(this).find('i').removeClass('icon-white');
	});
}

function load() {
	document.formGerenciamento.afterRestore = function() {
		
	}
}

/**
 * Motivo: Criando flag de atualiza��o 
 * altera��o 
 * Autor: flavio.santana
 * thays.araujo
 * Data/Hora: 13/11/2013 15:56
 * 31/01/2014 as 15:44
 * 
 */
var flagModalAtualizacao = false;

$(function(){
	
	$('.modal').on('show', function() {
		 flagModalAtualizacao = true;
	});
	
	$('.modal').on('hidden', function () {
		 flagModalAtualizacao = false;
	});

});

function janelaAguarde(){
	JANELA_AGUARDE_MENU.show();
}
function modalNovaSolicitacaoServico(){
	document.getElementById('frameNovaSolicitacao').src =  URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load';
	$('#modal_novaSolicitacao').modal('show');
	ajustarPadraoCitsmart();
}

//M�rio J�nior -  23/10/2013 -  16:27 - Inseri idTarefa como parametro.
function visualizarSolicitacao(idSolicitacaoServico, idTarefa) {
	document.getElementById('frameNovaSolicitacao').src =  URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?idSolicitacaoServico='+idSolicitacaoServico+'&idTarefa='+idTarefa+'&escalar=N&alterarSituacao=N&editar=N&acaoFluxo=V';
	//window.frames['frameNovaSolicitacao'].bloquearForm();
	$('#modal_novaSolicitacao').modal('show');
}

function visualizarSolicitacao(idSolicitacaoServico) {
	document.getElementById('frameNovaSolicitacao').src =  URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?idSolicitacaoServico='+idSolicitacaoServico+'&escalar=N&alterarSituacao=N&editar=N&acaoFluxo=V';
	//window.frames['frameNovaSolicitacao'].bloquearForm();
	$('#modal_novaSolicitacao').modal('show');
}

function prepararMudancaSLA(idTarefa, idSolicitacao) {
	document.getElementById('frameAlterarSLA').src =  URL_SISTEMA+'pages/mudarSLA/mudarSLA.load?iframe=true&idSolicitacaoServico='+idSolicitacao;
	$('#modal_alterarSLA').modal('show');
}

function agendaAtividade(idSolicitacao) {
	document.getElementById('frameAgendarAtividade').src =  URL_SISTEMA+'pages/agendarAtividade/agendarAtividade.load?iframe=true&idSolicitacaoServico='+idSolicitacao;
	$('#modal_agendarAtividade').modal('show');
}

function exibirDelegacaoTarefa(idTarefa, idSolicitacao, nomeTarefa) {
	document.getElementById('frameExibirDelegacaoTarefa').src =  URL_SISTEMA+'pages/delegacaoTarefa/delegacaoTarefa.load?iframe=true&idSolicitacaoServico='+idSolicitacao+'&idTarefa='+idTarefa+'&nomeTarefa='+nomeTarefa;
	$('#modal_exibirDelegacaoTarefa').modal('show');
}

function prepararSuspensao(idSolicitacao) {
	document.getElementById('frameExibirSuspender').src =  URL_SISTEMA+'pages/suspensaoSolicitacao/suspensaoSolicitacao.load?iframe=true&idSolicitacaoServico='+idSolicitacao;
	$('#modal_suspender').modal('show');
}

function reativarSolicitacao(idSolicitacao) {
	if (!confirm(i18n_message("gerencia.confirm.reativacaoSolicitacao"))) 
		return;
	document.form.idSolicitacaoSel.value = idSolicitacao;
	document.form.fireEvent('reativaSolicitacao'); 
}

function prepararExecucaoTarefa(idTarefa,idSolicitacao,acao) {
	janelaAguarde();
	document.form.idSolicitacaoSel.value = idSolicitacao;
	document.form.idTarefa.value = idTarefa;
	document.form.acaoFluxo.value = acao;
	document.form.fireEvent('preparaExecucaoTarefa');
}

exibirVisao = function(titulo,idVisao,idFluxo,idTarefa,acao){
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraSolicitacaoServico').src = "about:blank";
	document.getElementById('fraSolicitacaoServico').src = URL_SISTEMA + "pages/dinamicViews/dinamicViews.load?modoExibicao=J&idVisao="+idVisao+"&idFluxo="+idFluxo+"&idTarefa="+idTarefa+"&acaoFluxo="+acao;
};

fecharVisao = function(){
	$( "#POPUP_VISAO" ).dialog( 'close' );
	document.getElementById('fraSolicitacaoServico').src = "about:blank";
	document.form.fireEvent('exibeTarefas');
	myLayout.open("south");		
	//myLayout.open("west");		
};
	

function exibirUrl(titulo, url) {
	document.getElementById('frameNovaSolicitacao').src = '../../'+url;
	$('#modal_novaSolicitacao').modal('show');
}
function reclassificarSolicitacao(idSolicitacao, idTarefa) {
	JANELA_AGUARDE_MENU.show();
	document.getElementById('frameReclassificarSolicitacao').src = URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?idSolicitacaoServico='+idSolicitacao+'&idTarefa='+idTarefa+'&reclassificar=S&visualizarPasso=C';
	$('#modal_reclassificarSolicitacao').modal('show');

};

function inicializaPopover(){
	$('.maisInfo').popover({ placement: 'top', animation: true, trigger: 'click', html: true}); 
	$('.informacoesSolicitante').popover({placement: 'top', animation: true, trigger: 'click', html: true});
}

var completeServico;
$(document).ready(function() {
	
	$('body').on('click', function (e) {
	    $('.maisInfo').each(function () {
	        //the 'is' for buttons that triggers popups
	        //the 'has' for icons within a button that triggers a popup
	        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
	            $(this).popover('hide');
	        }
	    });
	});
	
	$('body').on('click', function (e) {
	    $('.informacoesSolicitante').each(function () {
	        //the 'is' for buttons that triggers popups
	        //the 'has' for icons within a button that triggers a popup
	        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
	            $(this).popover('hide');
	        }
	    });
	});

	completeServico = $('#servicoBusca').autocomplete({ 
		serviceUrl:'pages/autoCompleteServico/autoCompleteServico.load',
		noCache: true,
		onSelect: function(value, data){
			//document.form.clear();
			$('#idServico').val(data);
			$('#servicoBusca').val(value);
			/*document.form.fireEvent('verificaImpactoUrgencia');	*/	
			document.form.fireEvent('carregaBaseConhecimentoAssoc');
			carregarInformacoesComplementares();
			calcularSLA();
			startLoading();

		} 
	});
	inicializaPopover();
	$('#btnAtualizarGraficos').click(function(){ 
		renderizarGraficos();
	})
});


/**
Adicionado para fechar o moldal  apos carregar a grid de solicita�oes.grid de solicita��o.
* 
* @author maycon.fernandes
* @since 25/10/2013 14:35
*/
function fecharModal(){
	$('#modal_novaSolicitacao').modal('hide');
	$('#modal_reclassificarSolicitacao').modal('hide');
	$('#modal_criarSubSolicitacao').modal('hide');
	$('#modal_exibirDelegacaoTarefa').modal('hide');
	$('#modal_suspender').modal('hide');
	$('#modal_alterarSLA').modal('hide');
}

function fecharModalNovaSolicitacao(){
	$('#modal_novaSolicitacao').modal('hide');
	$('#modal_reclassificarSolicitacao').modal('hide');
	carregaListaServico();
}

function carregaListaServico(){
	pesquisarItensFiltro();
}

  function fecharModalReclassificacao() {
  	$('#modal_reclassificar2').modal('hide');
	atualizarLista();
  }

/**
 * Realiza o evento de dropdown do filtro de pesquisa
 * Ao clicar dentro da area de conteudo do filtro o mesmo n�o oculta, mas se o 
 * mesmo clicar fora da area de conteudo o mesmo fica oculto
 */
$('html').off('click.dropdown.data-api');
$('html').on('click.dropdown.data-api', function(e) {
  if($(e.target).is('.search') || !$(e.target).parents('li').is('.dropdownFiltroPesquisa')) {
    $('.dropdown').removeClass('open');
    resetarPluginSelect2AutoComplete();
  }else {
	  e.stopPropagation();
  }
});

function fechaWindow() {
	$('#acoes').removeClass('open');
}


/** Autor: Pedro Lino
 * Data: 27/08/2013
 * Filtra todos os dados contidos na lista em div.content-area.ativo
 * deve ser chamada no input via onkeyup
 * campoBusca: valor digitado no campo de filtro
 * lista: Id da div onde ser� feito a busca
 **/
function filtroListaDivJs(campoBusca, lista){
			// Recupera value do campo de busca
        var term=campoBusca.value.toLowerCase();
		if( term != "")
		{
			 var searchText = term;

		        $('#' + lista + ' div.content-area.ativo ').each(function(){

		            var currentLiText = $(this).text(),
		                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) !== -1;

		            $(this).toggle(showCurrentLi);

		        });  
		}else{
			// Quando n�o h� nada digitado, mostra a tabela com todos os dados
			 $('#' + lista + ' div.content-area.ativo').each(function(){

		            var currentLiText = $(this).text(),
		                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) == -1;

		            $(this).toggle(showCurrentLi);

		        }); 
		}
	}

/** AREA DE GRAFICOS**/


function atualizaGrafico(){
	plotaGrafico(dadosGrafico, "divGrafico");
	eval(scriptTemposSLA);
	temporizador.init();
	temporizador.ativarDesativarTimer();
}
function atualizaGrafico2(){
	plotaGrafico(dadosGrafico2, "divGrafico2");
}
function atualizaGrafico3(){
	plotaGrafico(dadosGerais, "divGrafico3");
}

function plotaGrafico(dados, idDiv){
	var div = '#'+idDiv;
	$.plot(div, dados, {
	    series: {
	    	 pie: {
	    		 innerRadius: 0.0,
	             show: true,
	             highlight: {
						opacity: 0.1
					},
					radius: 1,
					stroke: {
						color: '#fff',
						width: 8
					},
					startAngle: 2,
				    combine: {
	                    color: '#EEE',
	                    threshold: 0.05
	                },
	             label: {
	                    show: true,
	                    radius: 1,
	                    formatter: function(label, series){
	                        return '<div class="label label-inverse">'+label+'&nbsp;'+Math.round(series.percent)+'%</div>';
	                    }
	         }	
	    },
	    grow: {	active: true},
	    legend: {
	        show: false
	    },
	    grid: {
            hoverable: true,
            clickable: true
           
        },
        colors: [],
	    tooltip: true,
	    tooltipOpts: {
			content: "%s : %y.1"+"%",
			shifts: {
				x: -30,
				y: -50
			},
			defaultTheme: true
		}
	  }
	});
}

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

function gravarSolicitacao() {
	document.form.save();
	if (document.getElementById('form') != null) {
		alert(document.getElementById('form'));
	}
}

function chamarPesquisaSolicitacoes(){
	document.getElementById('framePesquisaGeralSolicitacao').src = URL_SISTEMA+'pages/pesquisaSolicitacoesServicos/pesquisaSolicitacoesServicos.load?iframe=true';
	$('#modal_pesquisaGeralSolicitacao').modal('show');
}

function chamarAgendaGrid(){
	document.getElementById('frameAgendaAtvPeriodicas').src = URL_SISTEMA+'pages/agendaAtvPeriodicas/agendaAtvPeriodicas.load?noVoltar=true';
	$('#modal_agenda').modal('show');
}

function chamarSuspenderReativarSolicitacaoGrid(){
	document.getElementById('frameSuspenderReativarSolicitacao').src = URL_SISTEMA+'pages/suspensaoReativacaoSolicitacao/suspensaoReativacaoSolicitacao.load?noVoltar=true&iframe=true';
	$('#modal_SuspenderReativarSolicitacao').modal('show');
}

function chamarModalDescricaoSolicitacao(descricao, idSolicitacaoServico ){
	$('#contentFrameDescricaoSolicitacao').html('<h3>'+ i18n_message('solicitacaoServico.solicitacao')+'&nbsp;&nbsp;'+idSolicitacaoServico+'</h3><br><div class=\"box-generic\"><div class=\"descricaoModal\">'+descricao+'</div></div>');
	$('#modal_descricaoSolicitacao').modal('show');
}

geraPopoverInformacoesSolicitante = function(telefoneContato, emailContato) {
	
  	if ((emailContato != '' && emailContato != undefined && emailContato != null) 
  	||  (telefoneContato != '' && telefoneContato != undefined && telefoneContato != null)){
  		var strAux1 = '';
  		if (telefoneContato != '' && telefoneContato != undefined && telefoneContato != null){
  			strAux1 += i18n_message('citcorpore.comum.telefone')+":"  + telefoneContato +", \r" ;
  		}
  		var strAux2 = '';
  		if (emailContato != '' && emailContato != undefined && emailContato != null){
  			/*
  			 * Rodrigo Pecci Acorse - 20/11/2013 16h40 - #125019
  			 * Foi adicionada a classe nowrap para evitar a quebra de linha do e-mail 
  			 */
  			strAux2 += "<span class='nowrap'>"+i18n_message('citcorpore.comum.email')+"</span>: " + emailContato;
  		}

  		$('.informacoesSolicitante').attr('data-content', '<p>'+'<label>'+strAux1+'</label>'+'<label>'+strAux2+'</label>'+'</p>');

  	}
  };
  
  var completeSolicitante;
  $(document).ready(function() {
	  completeSolicitante = $('#nomeSolicitante').autocomplete({ 
  		serviceUrl:'pages/autoCompleteSolicitante/autoCompleteSolicitante.load',
  		noCache: true,
  		onSelect: function(value, data){
  			document.formInformacoesContato.idSolicitante.value = data;
  			document.formInformacoesContato.nomeSolicitante.value = value;
  			document.formInformacoesContato.nomecontato.value = data;
  			document.formInformacoesContato.fireEvent("restauraSolicitante");
  		}
  	});
  });
  
  function montaParametrosAutocompleteSol(idContrato){
	  completeSolicitante.setOptions({params: {contrato: idContrato} });
  }

  function carregarModalDuplicarSolicitacao(idSolicitacao) {	  
	    document.formInformacoesContato.clear();
		document.formInformacoesContato.idSolicitacaoServico.value = idSolicitacao;
		
		/*
		 * Rodrigo Pecci Acorse - 03/12/2013 14h40 - #126139
		 * Seta os valores da situa��o e grupo executor que foram selecionados no filtro para o form de criar subsolicita��o.
		 */
		var situacao = $('select[name="situacao"]').find(':selected').attr('value');
		if (situacao == "" || situacao == "undefined") situacao = "";
		
		var idGrupoAtual = $('select[name="idGrupoAtual"]').find(':selected').attr('value');
		if (idGrupoAtual == "" || idGrupoAtual == "undefined") idGrupoAtual = "-1";
		
	  	document.formInformacoesContato.situacao.value = situacao;
	  	document.formInformacoesContato.idGrupoAtual.value = idGrupoAtual;
		
		document.formInformacoesContato.fireEvent('carregarModalDuplicarSolicitacao');
		$('#modal_criarSubSolicitacao').modal('show');
  }
  
  function fecharModalAgendarAtividade() {
	  $('#modal_agendarAtividade').modal('hide');
  }
  
function duplicarSolicitacao(){
		if (document.formInformacoesContato.idOrigem.value == '') {
			alert(i18n_message("citcorpore.comum.origem") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
			document.formInformacoesContato.idOrigem.focus();
			return;
		}
		if (document.formInformacoesContato.nomeSolicitante.value == '') {
			alert(i18n_message("solicitacaoServico.nomeDoSolicitante") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
			document.formInformacoesContato.nomeSolicitante.focus();
			return;
		}
		if (document.formInformacoesContato.emailcontato.value == '') {
			alert(i18n_message("solicitacaoServico.emailContato") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
			document.formInformacoesContato.emailcontato.focus();
			return;
		}
		if (document.formInformacoesContato.idUnidade.value == '') {
			alert(i18n_message("unidade.unidade") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
			document.formInformacoesContato.idUnidade.focus();
			return;
		}
		if (document.formInformacoesContato.telefonecontato.value == '') {
				alert(i18n_message("solicitacaoServico.telefoneDoContato") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
				document.formInformacoesContato.telefonecontato.focus();
				return;
		}
		if(ValidaEmail()) {
			JANELA_AGUARDE_MENU.show();
			document.formInformacoesContato.fireEvent("duplicarSolicitacao");
		}
		
}
exibirSubSolicitacoes = function(idSolicitacaoServico){
	document.formIncidentesRelacionados.idSolicitacaoIncRel.value = idSolicitacaoServico; 
	inicializarTemporizadorRel1();
	document.formIncidentesRelacionados.fireEvent("abrirListaDeSubSolicitacoes");
	$('#modal_exibirSubSolicitaces').modal('show');
}

function LOOKUP_SOLICITANTE_select(id, desc){
	document.formInformacoesContato.idSolicitante.value = id;
	document.formInformacoesContato.fireEvent("restoreSolicitante");
	$('#modal_lookupSolicitante').modal('hide');
}
var temporizadorRel1;

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

function inicializarTemporizadorRel1(){
	if(temporizadorRel1 == null){
		temporizadorRel1 = new Temporizador("imgAtivaTimerRel1");
	} else {
		temporizadorRel1 = null;
		try{
			temporizadorRel1.listaTimersAtivos = [];
		}catch(e){}
		try{
			temporizadorRel1.listaTimersAtivos.length = 0;
		}catch(e){}
		temporizadorRel1 = new Temporizador("imgAtivaTimerRel1");
	}
}

function ValidaEmail() {
    var email = $("#emailcontato").val();
    var emailValido=/^.+@.+\..{2,}$/;

    if(!emailValido.test(email))
    {
    	alert(i18n_message("solicitacaoServico.emailContato") + ': ' + i18n_message("citcorpore.validacao.emailInvalido"));
    	document.formInformacoesContato.emailcontato.focus();
    	return false;
    }
    return true;
}

function adicionarIdContratoNaLookup(id){
	 document.getElementById('pesqLockupLOOKUP_SOLICITANTE_IDCONTRATO').value = id;
}
/**
 * Motivo: Corrigir um bug nos navegadores que n�o fecham o select2 no dropdown de filtro
 * Autor: flavio.santana
 * Data/Hora: 13/11/2013 
 */
function resetarPluginSelect2AutoComplete() {
	$("#idResponsavelAtual").select2("close");
    $("#idSolicitante").select2("close");
    $("#tarefaAtual").select2("close");
}
function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds){
            break;
        }
    }
}
var loop;
function motrarQuandoFiltroEstiverAtivo(){
	if($("#idSolicitacao").val()=='' && $("#idContrato").val()=='-1' && $("#idTipo").val()=='-1' && $("#idSolicitante").val()=='' && $("#idResponsavelAtual").val()=='' && $("#idGrupoAtual").val()=='-1' && $("#tarefaAtual").val()=='' && $("#situacao").val()=='' && $("#palavraChave").val()==''){
		$("#idbotaoBuscar").removeClass("mudarCorBotaoFiltros");
		/*clearInterval(loop);*/
	}else{
		$("#idbotaoBuscar").addClass("mudarCorBotaoFiltros");
		/*loop = setInterval(function(){
			$( "#idbotaoBuscar" ).animate({
			    opacity: 1,
			    left: "+=50",
			    color:'red'
			  }, 1000, function() {
			    // Animation complete.
			  });
			
			$( "#idbotaoBuscar" ).animate({
			    opacity: 1,
			    left: "+=50",
			    color:'black'
			  }, 1000, function() {
			    // Animation complete.
			  });
			},2000);*/
		
	}
	
}
function abrirModalPesquisaItemConfiguracao(){
	document.getElementById('framePesquisaItemConfiguracao').src = URL_SISTEMA+'pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load?iframe=true';
	$('#modal_pesquisaItemConfiguracao').modal('show');
}

/*function abrirModalItemConfiguracao(){
	 $('#conteudoiframeInformacaoItemConfiguracao').html('<iframe src="about:blank" width="99%" id="iframeInformacaoItemConfiguracao" height="530" class="iframeSemBorda"></iframe>'); 
}*/
function popupAtivos(id){
	var idItem = id;
		document.getElementById('iframeInformacaoItemConfiguracao').src = URL_SISTEMA+'pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
		$("#modal_informacaoItemConfiguracao").modal("show");
		calcularSLA();
}
function pesquisarItensDeConfiguracaoRelacionados(idSolicitacaoPai){
	document.getElementById('idSolicitacaoSel').value = idSolicitacaoPai;
	document.form.fireEvent('abrirListaDeItensDeConfiguracao');
}

function fecharModalSuspensaoReativacaoSolicitacao(){
	$('#modal_SuspenderReativarSolicitacao').modal('hide');
}

function modalSolicitacoesFilhas(idSolicitacaoPai){
	document.getElementById('idSolicitacaoSel').value = idSolicitacaoPai;
	document.form.fireEvent('abrirListaDeSubSolicitacoes');
	$('#modal_solicitacaofilha').modal('show');
}

function visualizarSolicitacaoPaiEnvio(idSolicitacaoServico, idTarefa) {
	document.getElementById('frameNovaSolicitacao').src =  URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?idSolicitacaoServico='+idSolicitacaoServico+'&idTarefa='+idTarefa+'&escalar=N&alterarSituacao=N&editar=N&acaoFluxo=V';
	$('#modal_novaSolicitacao').modal('show');
}

function visualizarSolicitacaoPaiEnvioDoAction(idSolicitacaoServico) {
	document.getElementById('idSolicitacaoSel').value = idSolicitacaoServico;
	document.form.fireEvent('buscarItemTtrabalhoSolicitacao');
}

function chamaPopupCadastroOrigemSub(){
	if (document.formInformacoesContato.idContrato.value == ''){
		alert(i18n_message("solicitacaoservico.validacao.contrato"));
		return;
	}
	var idContrato = 0;
	try{
		idContrato = document.formInformacoesContato.idContrato.value;
	}catch(e){
	}
	document.getElementById('frameExibirOrigemSub').src = URL_SISTEMA+'pages/origemAtendimento/origemAtendimento.load?iframe=true&idContrato='+idContrato+'&subSolicitacao=S';
	$('#modal_origem_sub').modal('show');
}

function preencherComboOrigem() {
	document.form.fireEvent('chamaComboOrigem');
}

function fecharModalSubSolicitacao(){
	$('#modal_origem_sub').modal('hide');
}

function ajustarTemplate(){
	
	$('#modal_novaSolicitacao').css({
		'width': '97%',
		'height': 'auto',
		'margin-top': '0% !important',
		'margin-left': '-48.5%'
	});
	
	$('#modal_novaSolicitacao .modal-body').css('max-height', '92%');
	$('#frameNovaSolicitacao').attr('style', 'min-height: 865px !important;');
}

function ajustarPadraoCitsmart(){
	
	$('#modal_novaSolicitacao').css({
		'width': '80%',
		'height': 'auto',
		'margin-top': '0% !important',
		'margin-left': '-40% '
	});
	
	$('#modal_novaSolicitacao .modal-body').css('max-height', '700px');
	$('#frameNovaSolicitacao').attr('style', 'min-height: 690px !important;');
}

var completeUnidade;

function montaParametrosAutocompleteUnidade(){
	idContrato =  document.formInformacoesContato.idContrato.value;
	completeUnidade.setOptions({params: {idContrato: idContrato} });
}

function geraAutoComplete(){
	completeUnidade = $('#unidadeDes').autocomplete({ 
		serviceUrl: 'pages/autoCompleteUnidade/autoCompleteUnidade.load', 
		noCache: true,
		onSelect: function(value, data){ 
					$('#idUnidade').val(data);
					$('#unidadeDes').val(value.replace(/-*/, ""));
					document.formInformacoesContato.fireEvent("preencherComboLocalidade");
				  } 
    });
}

function onkeypressUnidadeDes(){
	document.getElementById("idUnidade").value= "0";
}

$(document).ready(function() {
	
	$(document).on('onfocus','#unidadeDes', function(){
		montaParametrosAutocompleteUnidade();
	});
	
});

/*
 * Function de validação para preenchimento de valores apenas numerico de acordo com a iniciativa 492.
 * 
 * @author Ezequiel Bispo Nunes
 * @date 2014-12-05 
 * 
 */
function ajustarCampoNumeroSolicitacao(){
	
	jQuery("#idSolicitacao").keypress(function(el){
			
		var key = (el.charCode) ? el.charCode : ((el.which) ? el.which : el.keyCode);      
		   		  
		/*
		 * 48 a 58: campos numericos:
		 */
		if(key >= 48 && key <= 58){ 
			
			return true; 	
		}
		
		/*
		 * 8: backspace
		 * 46: delete
		 */
		if (key == 8 || key ==  46){ 
		    
			return true;
		} 
			  
		/*
		* A: 65 e 97
		* C: 99 e 67 
		* V: 86 e 118
		* X: 88 e 120
		*/
		var keysCrtl = [65,67,86,88,97,99,118,120];
		  
		if (el.ctrlKey && keysCrtl.indexOf(key) >= 0){
		
			return true;	    
		}      
		
		return false;  	    
	  
	}); 

}





/* ============================================================
ATENÇÃO

NO JSP DEVE CONTER APENAS SCRIPTS QUE NECESSITAM DE SCRIPTLETS  

================================================================*/

jQuery(document).ready(function(){


jQuery("#idbotaoBuscar").click(function(){

	ajustarCampoNumeroSolicitacao();

});

});

jQuery(function($){
$("#telefonecontato").mask('(999) 9999-9999');
});

capturarTarefa = function(responsavelAtual, idTarefa) {
var msg = "";
if (responsavelAtual == '')
	msg = i18n_message("gerencia.confirm.atribuicaotarefa") + " '"+c+"'  ?";
else 	
	msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavelAtual + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  +" nomeUsuario "+ i18n_message("gerencia.confirm.atribuicaotarefa_3");
	
bootbox.confirm(msg, function(result) {
	if(result == true){
		JANELA_AGUARDE_MENU.show();
		document.form.idTarefa.value = idTarefa;
		document.form.fireEvent('capturaTarefa');
	
		}
});
};

function gravarEContinuar() {
document.form.acaoFluxo.value = acaoIniciar;
gravarSolicitacao();
}

function gravarEFinalizar2() {
document.form.acaoFluxo.value = acaoExecutar;
gravarSolicitacao();
}

function gravarSolicitacao22() {

document.form.save(); 

	if(document.form.descricao.innerHTML == "<br />" || document.form.descricao.innerHTML == "&lt;br /&gt;"){
	alert('Informe a descrição!');
	return;
}

if (document.form.descricao.value == '' || document.form.descricao.value == '&nbsp;'
	|| document.form.descricao.value == '<p></p>'){
	alert('Informe a descrição!');
	return;
}
if (document.form.descricao.value == 'Resolvida'){
	if (document.form.resposta.value == '' || document.form.resposta.value == ' '){
		alert('Informe a resposta!');
		return;				
	}
} 

 if (!validarInformacoesComplementares())
    return; 
 
if(document.form.enviaEmailCriacao.disabled==true)
{
	document.form.enviaEmailCriacao.disabled=false;
}
if(document.form.enviaEmailFinalizacao.disabled==true)
{
	document.form.enviaEmailFinalizacao.disabled=false;
}
if(document.form.enviaEmailAcoes.disabled==true)
{
	document.form.enviaEmailAcoes.disabled=false;
}

var informacoesComplementares_serialize = '';
try{
	informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
}catch(e){}

if (!isVersionFree) {
	var objs = HTMLUtils.getObjectsByTableId('tblProblema');
	if (objs != null) {
		document.form.colItensProblema_Serialize.value = ObjectUtils.serializeObjects(objs);
	}
	
	var objsMudanca = HTMLUtils.getObjectsByTableId('tblMudanca');
	if (objsMudanca != null) {
		document.form.colItensMudanca_Serialize.value = ObjectUtils.serializeObjects(objsMudanca);
	}
	
	var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
	if (objsIC != null) {
		document.form.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);
		
	var objsBaseConhecimento = HTMLUtils.getObjectsByTableId('tblBaseConhecimento');
	document.form.colConhecimentoSolicitacao_Serialize.value = ObjectUtils.serializeObjects(objsBaseConhecimento);
	}
}		

/* JANELA_AGUARDE_MENU.show(); */
document.form.urgencia.disabled = false;
document.form.impacto.disabled = false;
document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;
if(document.getElementById('flagGrupo').value == 0){
	document.form.save();
}else{
if (document.getElementById("idGrupoAtual").value == ''){
	//var e = document.getElementById("idGrupoAtual");
	//var grupoAtual = e.options[e.selectedIndex].text;
	if (confirm(i18n_message("solicitacaoServico.grupoAtualVazio"))){
	document.form.save(); 
	}else{
		/* JANELA_AGUARDE_MENU.hide(); */
		return;	
	}
}else{
	document.form.save(); 
}
}
}

var dadosGrafico;
var dadosGrafico2;
var dadosGrafico4;
var dadosGerais;
var scriptTemposSLA = '';
var temporizador;
exibirGraficos = function(json_tarefas) {
var tarefas = [];
//json_tarefas = '';
//$("#ajaxX").text(json_tarefas);
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
strTableTemposSLA += "alt='"+  i18n_message('ativaotemporizador')+"' id='imgAtivaTimer' style='opacity:0.5;display:none' ";
strTableTemposSLA += "title='"+ i18n_message('citcorpore.comum.ativadestemporizador') +"' ";
strTableTemposSLA += "src='"+contextoAplicacao+"/template_new/images/cronometro.png'/>";	
strTableTemposSLA += "<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\">";
strTableTemposSLA = strTableTemposSLA + "<tr><td><b>" + i18n_message('gerenciaservico.slasandamento')+"</b></td></tr>";	
inicializarTemporizador();
for(var i = 0; i < arrayTarefas.length; i++){
	var idSolicitacaoServico = "";
	var contrato = "";
	var responsavel = "";
	var servico = "";
	var solicitante = "";
	var prioridade = "";
	var situacao = "";
	var sla = "";
	var dataHoraSolicitacao = "";
	var dataHoraLimite = "";
	var grupoAtual = "";
	var farolAux = "";

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
		var grupoNome = solicitacaoDto.grupoAtual;
		if (grupoNome == null){
			grupoNome = '-- '+ i18n_message("citcorpore.comum.aclassificar")+ '--';
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
		
		idSolicitacaoServico = ""+solicitacaoDto.idSolicitacaoServico;
		responsavel = ""+trocaBarra(solicitacaoDto.responsavel);
		contrato = ""+trocaBarra(solicitacaoDto.contrato);
		servico = ""+trocaBarra(solicitacaoDto.servico);
		solicitante = ""+trocaBarra(solicitacaoDto.solicitanteUnidade);
		if (solicitacaoDto.prazoHH < 10)
			sla = "0";
		sla += solicitacaoDto.prazoHH + ":";
		if (solicitacaoDto.prazoMM < 10)
			sla += "0";
		sla += solicitacaoDto.prazoMM;
		prioridade = ""+solicitacaoDto.prioridade;
		dataHoraSolicitacao = solicitacaoDto.dataHoraSolicitacaoStr;
		if (solicitacaoDto.situacaoSLA == situacaoSLA_A) { 
			dataHoraLimite = solicitacaoDto.dataHoraLimiteStr;
		}
		grupoAtual = trocaBarra(solicitacaoDto.grupoAtual);
		
		if (parseFloat(solicitacaoDto.atrasoSLA) > parseFloat("0,00") && solicitacaoDto.situacao != situacaoSolicitacaoServico_Suspensa && solicitacaoDto.situacao != situacaoSolicitacaoServico_Cancelada){
			qtdeAtrasadas++;
		}else if (solicitacaoDto.situacao == situacaoSolicitacaoServico_Suspensa){
			qtdeSuspensas++;
		}else {
			qtdeEmAndamento++;
			if (qtdeItens < 15){
				if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar != 'S' && solicitacaoDto.situacaoSLA == 'A'){
					scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + solicitacaoDto.idSolicitacaoServico + "', " + "'barraProgresso" + solicitacaoDto.idSolicitacaoServico + "', "
					    + "'" + solicitacaoDto.dataHoraInicioSLAStr + "', '" + solicitacaoDto.dataHoraLimiteToString + "'));";
					strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label class='crono' id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'></label>";
					strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + solicitacaoDto.idSolicitacaoServico + "'></div></td></tr>";
				}else if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar == 'S') {
					strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'>" + i18n_message('citcorpore.comum.acombinar') + " </font></label>";
				}else if (solicitacaoDto.situacaoSLA == 'N'){
                    strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'>" + i18n_message('citcorpore.comum.naoIniciado')+ " </font></label>";
				}else if (solicitacaoDto.situacaoSLA == 'S'){
                    strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'>" + i18n_message('citcorpore.comum.suspenso') + " </font></label>";
				}
			}
			qtdeItens++;
		}
	} 
    tarefas[i] = {
	        		 iniciar:			tarefaDto.executar
	        		,executar:			tarefaDto.executar
	        		,delegar:			tarefaDto.delegar
    				,idSolicitacaoServico:		idSolicitacaoServico
    			 	,contrato: 			contrato
    			 	,responsavel: 		responsavel
    			 	,servico: 			servico
    			 	,solicitanteUnidade: 		solicitante
    			 	,prioridade: 		prioridade
    			 	,dataHoraSolicitacao: dataHoraSolicitacao
    			 	,descricao: 		trocaBarra(tarefaDto.elementoFluxoDto.documentacao)
	        		,status:	 		""
		        	,atraso:			solicitacaoDto.atrasoSLA
	        		,sla:	 			sla
	        		,atrasoSLA:	 		""
    			 	,dataHoraLimite: 	dataHoraLimite
    			 	,responsavelAtual:  tarefaDto.responsavelAtual
    			 	,compartilhamento:  tarefaDto.compartilhamento
    			 	,grupoAtual:  grupoAtual
    			}
}
strTableTemposSLA = strTableTemposSLA + '</table>';
if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){
	var info = '';
	if (qtdeAtrasadas > 0){
		info += ' <font color="red"><b>' + qtdeAtrasadas + '</b> ' + i18n_message("solicitacaoServico.solicitacoes_incidentes_atrasado") + '</font><br>';
	}
	if (qtdeSuspensas > 0){
		info += ' <b>' + qtdeSuspensas + '</b>' + i18n_message("solicitacaoServico.solicitacoes_incidentes_suspenso");
	}
	info = i18n_message("solicitacaoServico.existem") + ' <br>' + info + '<br><div id="divTemposSLA" style="height:280px; overflow:auto; border: 1px solid #999999">' + strTableTemposSLA + '</div>';
		info = '<table cellpadding="0" cellspacing="0"><tr><td style="width:15px">&nbsp;</td><td style="vertical-align: top; width: 100%; height: 250px">' + info + '</td></tr></table>';
		 document.getElementById('tdAvisosSol').innerHTML = info;
		dadosGrafico = [{label: i18n_message('citcorpore.comum.normal'), data: qtdeEmAndamento}, {label: i18n_message('citcorpore.comum.suspensa'), data: qtdeSuspensas},{label:  i18n_message('citcorpore.comum.vencido'), data: qtdeAtrasadas}];
		dadosGrafico2 = [{label:" 1 ", data: qtdePri1},{label:" 2 ", data: qtdePri2},{label: " 3 ", data: qtdePri3},{label: " 4 ", data: qtdePri4},{label: " 5 ", data: qtdePri5}];
		window.setTimeout(atualizaGrafico, 1000);
		window.setTimeout(atualizaGrafico2, 1000);
		
		var colArray = colGrupoSol.toArray();
		dadosGerais = new Array();
		if (colArray){
			for (var iAux = 0; iAux < colArray.length; iAux++){
				dadosGerais[iAux] = {label: colArray[iAux].id, data: colArray[iAux].qtde};
			}
		}
		window.setTimeout(atualizaGrafico3, 1000);
		document.formGerenciamento.quantidadeAtrasadas.value = qtdeAtrasadas;
		document.formGerenciamento.quantidadeTotal.value = (qtdeEmAndamento + qtdeSuspensas + qtdeAtrasadas);
}

	JANELA_AGUARDE_MENU.hide()
};



/*
Rodrigo Pecci Acorse
07/11/2013 - Sol. 123390
- Os iframs que possuiam src definido aqui no load da página foram removidos e adicionados somente na ação do item. Os frames serão carregados somente quando necessário.
- A altura do iframe foi removida pois estava causando 2 barras de rolagem na página de nova solicitação.
*/
$(window).load(function(){
$('#contentFrameNovaSolicitacao').html('<iframe src="about:blank" id="frameNovaSolicitacao" width="100%" class="iframeSemBorda"></iframe>');
$('#contentFrameAlterarSLA').html('<iframe id="frameAlterarSLA" src="about:blank" width="100%" height="520" class="iframeSemBorda"></iframe>');
$('#contentReclassificarSolicitacao2').html('<iframe id="frameReclassificarSolicitacao2" src="about:blank" width="100%" height="600" class="iframeSemBorda"></iframe>');
$('#contentFrameAgendarAtividade').html('<iframe id="frameAgendarAtividade" src="about:blank" width="100%" height="460" class="iframeSemBorda"></iframe>');
$('#contentFrameagendaAtvPeriodicas').html('<iframe id="frameAgendaAtvPeriodicas" src="about:blank" width="100%" height="530" class="iframeSemBorda"></iframe>');
$('#contentFrameSuspenderReativarSolicitacao').html('<iframe id="frameSuspenderReativarSolicitacao" src="about:blank" width="100%" height="520" class="iframeSemBorda"></iframe>');
$('#contentFrameExibirDelegacaoTarefa').html('<iframe id="frameExibirDelegacaoTarefa" src="about:blank" width="100%" height="400" class="iframeSemBorda"></iframe>');
$('#contentFrameSuspender').html('<iframe id="frameExibirSuspender" src="about:blank" width="100%" height="400" class="iframeSemBorda"></iframe>');
$('#contentPesquisaGeralSolicitacao').html('<iframe id="framePesquisaGeralSolicitacao" src="about:blank" width="100%" height="600" class="iframeSemBorda"></iframe>');
$('#contentframeReclassificarSolicitacao').html('<iframe id="frameReclassificarSolicitacao" src="about:blank" width="100%" height="700" class="iframeSemBorda"></iframe>');
$('#conteudoframeExibirOrigemSub').html('<iframe id="frameExibirOrigemSub" src="about:blank" width="100%" height="350" class="iframeSemBorda"></iframe>');
$('#conteudoPesquisaItemConfiguracao').html('<iframe id="framePesquisaItemConfiguracao" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
$('#conteudoiframeInformacaoItemConfiguracao').html('<iframe id="iframeInformacaoItemConfiguracao" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>'); 
});
