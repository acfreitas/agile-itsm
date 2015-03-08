    //Autor: Tiago Cartibani
    //Data: 23/10/2013
    //Função para funcionar botões de idioma e logout

    $(document).ready(function() {
    	$('li.dropdown').click(function() {
    		if ($(this).is('.open')) {
    		$(this).removeClass('open');
    		} else {
    		$(this).addClass('open');
    		}
    	});

    });
    // Fim da Função para funcionar botões de idioma e logout

    popup2 = new PopupManager(1084, 1084, ctx+"/pages/");
	popup2.titulo = i18n_message("citcorpore.comum.pesquisarapida");

      function GrupoQtde(){
      		this.id = '';
      		this.qtde = 0;
      }

      //Autor: Emauri
      //Data: 05/11/2013
      //Trocado o icone de executar pelo botao executar
		  AddBotoesTarefa = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null && solicitacaoDto.situacao == situacaoSolicitacaoServico_Suspensa)
	  		return value;

		var str = "";

         return str + value;
	  };

	  AddLinkSolicitacao = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null) {
	  	  var str = "";
	  	  str += "&nbsp;&nbsp;"+solicitacaoDto.idSolicitacaoServico;
          return str;
	    }else
	      return "";
	  };

	  AddSituacao = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if(solicitacaoDto.vencendo == ''){

	  		if (solicitacaoDto.atrasada == 'true' && solicitacaoDto.situacao != situacaoSolicitacaoServico_Suspensa){
				return "<img src='"+ctx+"/imagens/bolavermelha.png' title='"+i18n_message("gerenciaservico.atrasada")+"'/>";
			}else{
				if (solicitacaoDto.situacao == situacaoSolicitacaoServico_Suspensa){
					return "<img src='"+ctx+"/imagens/bolacinza2.png' title='"+i18n_message("citcorpore.comum.suspensa")+"'/>";
				}
				if (solicitacaoDto.falta1Hora == 'true'){
					return "<img src='"+ctx+"/imagens/bolaamarela.png' title='"+i18n_message("solicitacaoServico.menos1hora.desc")+"'/>";
				}else{
					return "<img src='"+ctx+"/imagens/bolaverde.png'/>";
				}
			}
	  		//trata de acordo com regras de escalonamento definidas
	  	} else {
	  		if (solicitacaoDto.atrasada == 'true' && solicitacaoDto.situacao != situacaoSolicitacaoServico_Suspensa){
				return "<img src='"+ctx+"/imagens/bolavermelha.png' title='"+i18n_message("gerenciaservico.atrasada")+"'/>";
			}else{
				if (solicitacaoDto.situacao == situacaoSolicitacaoServico_Suspensa){
					return "<img src='"+ctx+"/imagens/bolacinza2.png' title='"+i18n_message("citcorpore.comum.suspensa")+"'/>";
				}
				if(solicitacaoDto.vencendo == 'S'){
					return "<img src='"+ctx+"/imagens/bolaamarela.png' title='"+i18n_message("solicitacaoServico.vencendo")+"'/>";
				} else {
					return "<img src='"+ctx+"/imagens/bolaverde.png'/>";
				}
			}
	  	}
	  };

	  AddAtraso = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	var result = "";
	  	if (solicitacaoDto != null && parseFloat(solicitacaoDto.atrasoSLA) > 0 && solicitacaoDto.situacao != situacaoSolicitacaoServico_Suspensa)
	      	result = '<font color="red">' + solicitacaoDto.atrasoSLAStr + '</font>';
        return result;
	  };

	  AddSelTarefa = function(row, cell, value, columnDef, dataContext) {
        return "<input type='radio' name='selTarefa' value='S'/>";
	  };

	  AddBotaoMudancaSLA = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null && solicitacaoDto.situacao == situacaoSolicitacaoServico_Suspensa)
	  		return "";

	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	var aux = value;
	  	if (solicitacaoDto.slaACombinar == 'S'){
	  		aux = 'A comb.';
	  	}
        return aux;
	  };

	  AddImgPrioridade = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto.prioridade == '1'){
	    	return value + " <img src='" + URL_INITIAL + "imagens/b.gif' style='cursor:pointer;' title='"+i18n_message("gerenciaservico.prioridadealta")+"'/>";
	  	}else{
	  		return value;
	  	}
	  };

	  AddImgSolicitante = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if ((solicitacaoDto.emailcontato != '' && solicitacaoDto.emailcontato != undefined && solicitacaoDto.emailcontato != null)
	  	||  (solicitacaoDto.telefonecontato != '' && solicitacaoDto.telefonecontato != undefined && solicitacaoDto.telefonecontato != null)
	  	||  (solicitacaoDto.localizacaofisica != '' && solicitacaoDto.localizacaofisica != undefined && solicitacaoDto.localizacaofisica != null)){
	  		var strAux = '';
	  		strAux += '' + solicitacaoDto.solicitanteUnidade;
	  		if (solicitacaoDto.telefonecontato != '' && solicitacaoDto.telefonecontato != undefined && solicitacaoDto.telefonecontato != null){
	  			strAux += '\n'+i18n_message("citcorpore.comum.telefone") + ' : ' + solicitacaoDto.telefonecontato;
	  		}
	  		if (solicitacaoDto.emailcontato != '' && solicitacaoDto.emailcontato != undefined && solicitacaoDto.emailcontato != null){
	  			strAux += '\n'+i18n_message("citcorpore.comum.email") + ' : ' + solicitacaoDto.emailcontato;
	  		}
	  		if (solicitacaoDto.localizacaofisica != '' && solicitacaoDto.localizacaofisica != undefined && solicitacaoDto.localizacaofisica != null){
	  			strAux += '\n'+i18n_message("citcorpore.comum.localizacao") + ' : ' + solicitacaoDto.localizacaofisica;
	  		}
	    	return " <img src='" + URL_INITIAL + "imagens/cracha.png' style='cursor:pointer;' title='" + strAux + "'/> " + value;
	  	}else{
	  		return value;
	  	}
	  };

		if (isIE){
			 var	arrayTarefas   = []
			    ,   gridTarefa     = {}
			    ,   tarefas		   = []
			    ,   colunasTarefa = [
			           	{ id: "idSolicitacao"      	, name: i18n_message('citcorpore.comum.numero')		, field: "idSolicitacao"       	, width: 80,	formatter: AddLinkSolicitacao, resizable:true	   }
			        ,   { id: "contrato"			, name: i18n_message('contrato.contrato')		, field: "contrato"	       		, width: 100    }
			        ,   { id: "origem"				, name: i18n_message('origemAtendimento.origem')		, field: "origem"	       		, width: 80    }
			        ,   { id: "servico"				, name: i18n_message('servico.servico')		, field: "servico"	       		, width: 150    }
			        ,   { id: "solicitanteUnidade"	, name: i18n_message('solicitacaoServico.solicitante')	, field: "solicitanteUnidade"	       	, width: 180, width: 180,    formatter: AddImgSolicitante, resizable:true    }
			        ,   { id: "dataHoraSolicitacao"	, name: i18n_message('solicitacaoServico.dataHoraCriacao')		, field: "dataHoraSolicitacao"	, width: 110   }
			        ,   { id: "prioridade"			, name: i18n_message('gerenciaservico.pri')			, field: "prioridade"	       	, width: 30,    formatter: AddImgPrioridade, resizable:true    }
			        ,   { id: "sla"					, name: i18n_message('gerenciaservico.sla')			, field: "sla"					, width: 70,    formatter: AddBotaoMudancaSLA, resizable:true   }
			        ,   { id: "dataHoraLimite"		, name: i18n_message('solicitacaoServico.prazoLimite')	, field: "dataHoraLimite"		, width: 110   }
			        ,   { id: "atrasoSLA"       	, name: i18n_message('tarefa.atraso') 		, field: "atrasoSLA"           	, width: 50,   	formatter: AddAtraso, resizable:false 		}
			        ,   { id: "situacao"       		, name: i18n_message('solicitacaoServico.situacao')     	, field: "situacao"           	, width: 150,	 formatter: AddSituacao, resizable:false    	}
			        ,   { id: "descricao"			, name: i18n_message('tarefa.tarefa_atual')	, field: "descricao"    	 	, width: 250,   formatter: AddBotoesTarefa}
			        ,   { id: "grupoAtual"			, name: i18n_message('citcorpore.comum.grupoExecutor'), field: "grupoAtual"     		, width: 80    }
			        ,   { id: "responsavelAtual"	, name: i18n_message('tarefa.responsavelatual'), field: "responsavelAtual"  , width: 80    }
			        ]
			    ,   gridOptions = {
			            editable:               false
			        ,   asyncEditorLoading:     false
			        ,   enableAddRow:           false
			        ,   enableCellNavigation:   true
			        ,   enableColumnReorder:    true
			        }
			    ;
		}else{
		    var	arrayTarefas   = []
		    ,   gridTarefa     = {}
		    ,   tarefas		   = []
		    ,   colunasTarefa = [
		           	{ id: "idSolicitacao"      	, name: i18n_message('citcorpore.comum.numero')		, field: "idSolicitacao"       	, width: 80,	formatter: AddLinkSolicitacao, resizable:true	   }
		        ,   { id: "contrato"			, name: i18n_message('contrato.contrato')		, field: "contrato"	       		, width: 100    }
		        ,   { id: "origem"				, name: i18n_message('origemAtendimento.origem')		, field: "origem"	       		, width: 80    }
		        ,   { id: "servico"				, name: i18n_message('servico.servico')		, field: "servico"	       		, width: 150    }
		        ,   { id: "solicitanteUnidade"	, name: i18n_message('solicitacaoServico.solicitante')	, field: "solicitanteUnidade"	       	, width: 180,    formatter: AddImgSolicitante, resizable:true    }
		        ,   { id: "dataHoraSolicitacao"	, name: i18n_message('solicitacaoServico.dataHoraCriacao')		, field: "dataHoraSolicitacao"	, width: 110   }
		        ,   { id: "prioridade"			, name: i18n_message('gerenciaservico.pri')			, field: "prioridade"	       	, width: 30,    formatter: AddImgPrioridade, resizable:true    }
		        ,   { id: "sla"					, name: i18n_message('gerenciaservico.sla')			, field: "sla"					, width: 80,    formatter: AddBotaoMudancaSLA, resizable:true   }
		        ,   { id: "dataHoraLimite"		, name: i18n_message('solicitacaoServico.prazoLimite')	, field: "dataHoraLimite"		, width: 110   }
		        ,   { id: "atrasoSLA"       	, name: i18n_message('tarefa.atraso') 		, field: "atrasoSLA"           	, width: 50,   	formatter: AddAtraso, resizable:false 		}
		        ,   { id: "situacao"       		, name: i18n_message('solicitacaoServico.situacao')     	, field: "situacao"           	, width: 150,	formatter: AddSituacao, resizable:false    	}
		        ,   { id: "descricao"			, name: i18n_message('tarefa.tarefa_atual')	, field: "descricao"    	 	, width: 250,   formatter: AddBotoesTarefa}
		        ,   { id: "grupoAtual"			, name: i18n_message('citcorpore.comum.grupoExecutor'), field: "grupoAtual"     		, width: 120    }
		        ,   { id: "responsavelAtual"	, name: i18n_message('tarefa.responsavelatual'), field: "responsavelAtual"  , width: 120    }
		        ]
		    ,   gridOptions = {
		            editable:               false
		        ,   asyncEditorLoading:     false
		        ,   enableAddRow:           false
		        ,   enableCellNavigation:   true
		        ,   enableColumnReorder:    false
		        }
		    ;
		}

		var dadosGrafico;
		var dadosGrafico2;
		var dadosGerais;
		var scriptTemposSLA = '';
		var temporizador;
		exibirTarefas = function(json_tarefas) {
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
			strTableTemposSLA += "src='"+ctx+"/template_new/images/cronometro.png'/>";
			strTableTemposSLA += "<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\">";
			strTableTemposSLA = strTableTemposSLA + "<tr><td><b>"+i18n_message("gerenciaservico.slasandamento")+"</b></td></tr>";
			inicializarTemporizador();
			for(var i = 0; i < arrayTarefas.length; i++){
				var idSolicitacao = "";
				var contrato = "";
				var origem = "";
				var servico = "";
				var solicitante = "";
				var prioridade = "";
				var situacao = "";
				var sla = "";
				var dataHoraSolicitacao = "";
				var dataHoraLimite = "";
				var grupoAtual = "";

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
						grupoNome = ' -- '+ i18n_message("citcorpore.comum.aclassificar")+ '--';
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

					idSolicitacao = ""+solicitacaoDto.idSolicitacaoServico;
					origem = ""+solicitacaoDto.origem;
					contrato = ""+solicitacaoDto.contrato;
					servico = ""+solicitacaoDto.servico;
					solicitante = ""+solicitacaoDto.solicitanteUnidade;
					if (solicitacaoDto.prazoHH < 10)
						sla = "0";
					sla += solicitacaoDto.prazoHH + ":";
					if (solicitacaoDto.prazoMM < 10)
						sla += "0";
					sla += solicitacaoDto.prazoMM;
					prioridade = ""+solicitacaoDto.prioridade;
					dataHoraSolicitacao = solicitacaoDto.dataHoraSolicitacaoStr;
					if (solicitacaoDto.situacao != situacaoSolicitacaoServico_Suspensa) {
						dataHoraLimite = solicitacaoDto.dataHoraLimiteStr;
					}
					grupoAtual = solicitacaoDto.grupoAtual;

					if (parseFloat(solicitacaoDto.atrasoSLA) > 0.0 && solicitacaoDto.situacao != situacaoSolicitacaoServico_Suspensa){
						qtdeAtrasadas++;
					}else if (solicitacaoDto.situacao == situacaoSolicitacaoServico_Suspensa && tarefaDto.reativar == 'S'){
						qtdeSuspensas++;
					}else {
						qtdeEmAndamento++;
						if (qtdeItens < 100){
							if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar != 'S'){
								scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + solicitacaoDto.idSolicitacaoServico + "', " + "'barraProgresso" + solicitacaoDto.idSolicitacaoServico + "', "
									+ "'" + solicitacaoDto.dataHoraSolicitacaoToString + "', '" + solicitacaoDto.dataHoraLimiteToString + "'));";
							}
							if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar != 'S'){
								strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'></label>";
								strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + solicitacaoDto.idSolicitacaoServico + "'></div></td></tr>";
							}else{
								strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'>"+i18n_message("citcorpore.comum.acombinar")+"</font></label>";
							}
						}
						qtdeItens++;
					}
				}
 		        tarefas[i] = {
				        		 iniciar:			tarefaDto.executar
				        		,executar:			tarefaDto.executar
				        		,delegar:			tarefaDto.delegar
		        				,idSolicitacao:		idSolicitacao
		        			 	,contrato: 			contrato
		        			 	,origem: 			origem
		        			 	,servico: 			servico
		        			 	,solicitanteUnidade: 		solicitante
		        			 	,prioridade: 		prioridade
		        			 	,dataHoraSolicitacao: dataHoraSolicitacao
		        			 	,descricao: 		tarefaDto.elementoFluxoDto.documentacao
				        		,status:	 		""
				        		,sla:	 			sla
				        		,atrasoSLA:	 		""
		        			 	,dataHoraLimite: 	dataHoraLimite
		        			 	,responsavelAtual:  tarefaDto.responsavelAtual
		        			 	,grupoAtual:  grupoAtual
		        			}
			}
			strTableTemposSLA = strTableTemposSLA + '</table>';
			if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){
				var info = '';
				if (qtdeAtrasadas > 0){
					info += ' <font color="red"><b>' + qtdeAtrasadas + '</b>'+i18n_message("solicitacaoServico.solicitacoes_incidentes_atrasado")+'</font><br>';
				}
				if (qtdeSuspensas > 0){
					info += ' <b>' + qtdeSuspensas + '</b>' + i18n_message("solicitacaoServico.solicitacoes_incidentes_suspenso");
				}
				info = i18n_message("solicitacaoServico.existem") + info + '<br><div id="divTemposSLA" style="height:330px; overflow:auto; border:1px solid black">' + strTableTemposSLA + '</div>';

				info = '<table cellpadding="0" cellspacing="0" style="width: 100%!important"><tr><td style="width:10px">&nbsp;</td><td style="vertical-align: top;">' + info + '</td><td><div id="divGrafico" style="height: 380px; width: 350px;"></div></td><td><div id="divGrafico2" style="height: 380px; width: 350px;"></div></td><td><div id="divGrafico3" style="height: 380px; width: 350px;"></div></td></tr></table>';
				//info = '<table cellpadding="0" cellspacing="0"><tr><td style="width:15px">&nbsp;</td><td style="vertical-align: top; width: 100%; height: 250px">' + info + '</td></tr></table>';
				document.getElementById('tdAvisosSol').innerHTML = info;
				/* dadosGrafico = [['i18n_message("gerenciaservico.emandamento" />',qtdeEmAndamento],['i18n_message("gerenciaservico.suspensas" />',qtdeSuspensas],['i18n_message("gerenciaservico.atrasadas" />',qtdeAtrasadas]]; */
				dadosGrafico = [{label: i18n_message('citcorpore.comum.normal'), data: qtdeEmAndamento}, {label: i18n_message('citcorpore.comum.suspensa'), data: qtdeSuspensas},{label:  i18n_message('citcorpore.comum.vencido'), data: qtdeAtrasadas}];
// 				/* dadosGrafico2 = [[' 1 ',qtdePri1],[' 2 ',qtdePri2],[' 3 ',qtdePri3],[' 4 ',qtdePri4],[' 5 ',qtdePri5]]; */
				dadosGrafico2 = [{label:" 1 ", data: qtdePri1},{label:" 2 ", data: qtdePri2},{label: " 3 ", data: qtdePri3},{label: " 4 ", data: qtdePri4},{label: " 5 ", data: qtdePri5}];
// 				//window.setTimeout(atualizaGrafico, 1000);
// 				atualizaGrafico();
// 				//window.setTimeout(atualizaGrafico2, 1000);
// 				atualizaGrafico2();

				var colArray = colGrupoSol.toArray();
				dadosGerais = new Array();
				if (colArray){
					for (var iAux = 0; iAux < colArray.length; iAux++){
						/* dadosGerais[iAux] = [colArray[iAux].id, colArray[iAux].qtde]; */
						dadosGerais[iAux] = {label: colArray[iAux].id, data: colArray[iAux].qtde};
					}
				}
				//window.setTimeout(atualizaGrafico3, 1000);
// 				atualizaGrafico3();
				//window.setTimeout(atualizaPagina, 30000);
				atualizaPagina();
			}
	        //gridTarefa = new Slick.Grid( myLayout.contents.south,  tarefas,  colunasTarefa, gridOptions );
	        document.getElementById("divConteudoLista").innerHTML = "<div id=\"divConteudoListaInterior\" style=\"height: 100%; width: 100%\"></div>";
	        gridTarefa = new Slick.Grid($("#divConteudoListaInterior"), tarefas,  colunasTarefa, gridOptions );

	        gridTarefa.onSort.subscribe(function (e, args) {
	        	sortdir = args.sortAsc ? 1 : -1;
	            sortcol = args.sortCol.field;

	            dataView.fastSort((sortcol == "percentComplete") ? percentCompleteValueFn : sortcol, args.sortAsc);

	            dataView.sort(comparer, args.sortAsc);
	        	var cols = args.sortCols;
				var asc = cols[10].sortAsc;
				if (document.formPesquisa.ordenacaoAsc.value != ''){
					asc = document.formPesquisa.ordenacaoAsc.value;
				}
				document.formPesquisa.nomeCampoOrdenacao.value = cols[10].sortCol.id;
				document.formPesquisa.ordenacaoAsc.value = "" + asc;
				document.alert("passou por aqui!");


				/*
				* Codigo Comentado pois compromete a performance do sistema  - RETORNADO POR EMAURI - 18/05*/
				document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'>"+i18n_message('citcorpore.comum.aguardecarregando')+"</div>"
				document.formPesquisa.fireEvent("exibeTarefas");
				//



		  	 });

		};
	function setinha(){
		window.setTimeout(orgColunsSort, 500);
	}

	function orgColunsSort(){
		try{
			gridTarefa.setSortColumn(document.formPesquisa.nomeCampoOrdenacao.value, eval(10));
		}catch(e){}
	}

	function atualizaGraficoAba(){
		atualizaGrafico();
		atualizaGrafico2();
		atualizaGrafico3();
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

	capturarTarefa = function(responsavelAtual, idTarefa, idRequisicao) {
		var msg = "";
		if (responsavelAtual == '')
			msg = i18n_message("gerencia.confirm.atribuicaotarefa") + login;
		else
			msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavelAtual + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  +login+ i18n_message("gerencia.confirm.atribuicaotarefa_3");

		if (!confirm(msg))
			return;
		document.form.idTarefa.value = idTarefa;
		document.form.idRequisicao.value = idRequisicao;
		document.form.fireEvent('capturaTarefa');
	};

    var myLayout;
    var popup;
	popup = new PopupManager(1000, 900, ctx+"/pages/");

    $(document).ready(function () {
		$( "#POPUP_VISAO" ).dialog({
			title: i18n_message("citcorpore.comum.visao"),
			width: 900,
			height: 500,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			});

		$("#POPUP_VISAO").hide();

		$( "#POPUP_REUNIAO" ).dialog({
			title: i18n_message("citcorpore.comum.visao"),
			width: 900,
			height: 600,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			});

		$("#POPUP_REUNIAO").hide();

		$( "#POPUP_BUSCA" ).dialog({
			title: i18n_message("citcorpore.comum.buscarapida"),
			width: 250,
			height: 300,
			modal: false,
			autoOpen: false,
			resizable: false
			});

		$( "#popupCadastroRapido" ).dialog({
			title: i18n_message("citcorpore.comum.cadastrorapido"),
			width: 900,
			height: 600,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			});

		$("#POPUP_CONTRATO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

		$("#POPUP_GRUPO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

        // create the layout - with data-table wrapper as the layout-content element
        myLayout = $('body').layout({
        	west__size:         20
       	,	center__minHeight:	200
        ,   west__onresize:     function (pane, $pane, state, options) {
                                    var $content    = $pane.children('.ui-layout-content')
                                    ,   gridHdrH    = $content.children('.slick-header').outerHeight()
                                    ,   paneHdrH    = $pane.children(':first').outerHeight()
                                    ,   paneFtrH    = $pane.children(':last').outerHeight()
                                    ,   $gridList   = $content.children('.slick-viewport') ;
                                    $gridList.height( state.innerHeight - paneHdrH - paneFtrH - gridHdrH );
                                }
        ,   south__onresize:   function (pane, $pane, state, options) {
                                    var gridHdrH    = $pane.children('.slick-header').outerHeight()
                                    ,   $gridList   = $pane.children('.slick-viewport') ;
                                    $gridList.height( state.innerHeight - gridHdrH );
                                    document.form.fireEvent('exibeTarefas');
                                }
        ,    east__initClosed:   true
        ,	 east__size:		 0
        , west: {
        		onclose_end: function(){
					myLayout.close("west");
					document.getElementById("tdLeft").style.backgroundColor = 'white';
        		},
        		onopen_end: function(){
					myLayout.open("west");
					document.getElementById("tdLeft").style.backgroundColor = 'lightgray';
        		}
        	}
        , south: {
        		onclose_end: function(){
					myLayout.close("south");
					document.getElementById("tdDown").style.backgroundColor = 'white';
        		},
        		onopen_end: function(){
					myLayout.open("south");
					document.getElementById("tdDown").style.backgroundColor = 'lightgray';
        		},
        		onresize_end: function(){
        		}
        	}
        });

        $('body > h2.loading').hide(); // hide Loading msg
        if(!idRequisicao)	{
       		document.getElementById('fraRequisicaoMudanca').src = "about:blank";
		} else	{	
			visualizarSolicitacao(idRequisicao);
		}

	    myLayout.hide('north');
	    myLayout.hide('west');
    });


	voltar = function(){
		window.location = ctx+"/pages/index/index.load";
	};

	editarSolicitacao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = ctx+"/pages/solicitacaoServico/solicitacaoServico.load?idSolicitacaoServico="+idSolicitacao+"&escalar=S&alterarSituacao=N";
	};

	reclassificarSolicitacao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = ctx+"/pages/solicitacaoServico/solicitacaoServico.load?idSolicitacaoServico="+idSolicitacao+"&reclassificar=S";
	};

	prepararSuspensao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = ctx+"/pages/suspensaoSolicitacao/suspensaoSolicitacao.load?idSolicitacaoServico="+idSolicitacao;
		$( "#POPUP_VISAO" ).dialog({ height: 500 });
		$( "#POPUP_VISAO" ).dialog({ title: 'i18n_message("gerenciaservico.suspendersolicitacao" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	reativarSolicitacao = function(idSolicitacao) {
		if (!confirm(i18n_message("gerencia.confirm.reativacaoSolicitacao")))
			return;
		document.form.idSolicitacao.value = idSolicitacao;
		document.form.fireEvent('reativaSolicitacao');
	};

	agendaAtividade = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = ctx+"/pages/agendarAtividade/agendarAtividade.load?idSolicitacaoServico="+idSolicitacao;
		$( "#POPUP_VISAO" ).dialog({ height: 600 });
		$( "#POPUP_VISAO" ).dialog({ title: i18n_message("gerenciaservico.agendaratividade") });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	prepararExecucaoTarefa = function(idTarefa,idSolicitacao,acao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.form.idTarefa.value = idTarefa;
		document.form.acaoFluxo.value = acao;
		document.form.fireEvent('preparaExecucaoTarefa');
	};

	prepararMudancaSLA = function(idTarefa,idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = ctx+"/pages/mudarSLA/mudarSLA.load?idSolicitacaoServico="+idSolicitacao;
		$( "#POPUP_VISAO" ).dialog({ height: 550 });
		$( "#POPUP_VISAO" ).dialog({ title: i18n_message("gerenciaservico.mudarsla") });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	exibirDelegacaoTarefa = function(idTarefa,idSolicitacao,nomeTarefa) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = ctx+"/pages/delegacaoTarefa/delegacaoTarefa.load?idSolicitacaoServico="+idSolicitacao+"&idTarefa="+idTarefa+"&nomeTarefa="+nomeTarefa;
		$( "#POPUP_VISAO" ).dialog({ height: 400 });
		$( "#POPUP_VISAO" ).dialog({ title: i18n_message("gerenciaservico.delegarcompartilhartarefa") });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	exibirVisao = function(titulo,idVisao,idFluxo,idTarefa,acao){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = ctx+"/pages/dinamicViews/dinamicViews.load?modoExibicao=J&idVisao="+idVisao+"&idFluxo="+idFluxo+"&idTarefa="+idTarefa+"&acaoFluxo="+acao;
	};

	fecharVisao = function(){
		$( "#POPUP_VISAO" ).dialog( 'close' );
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.form.fireEvent('exibeTarefas');
		myLayout.open("south");
	};

	abrirSolicitacao = function(){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = ctx+"/pages/solicitacaoServico/solicitacaoServico.load";
	};

	exibirUrl = function(titulo, url){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = ctx+"/"+url;
	};

	fecharSolicitacao = function(){
		myLayout.open("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.form.fireEvent('exibeTarefas');
	};

	atualizarListaTarefas = function() {
		myLayout.open("south");
		document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'>"+i18n_message('citcorpore.comum.aguardecarregando')+"</div>";
		document.form.numeroContratoSel.value = document.formPesquisa.numeroContratoSel.value;
		document.form.idSolicitacaoSel.value = document.formPesquisa.idSolicitacaoSel.value;
		document.form.fireEvent('exibeTarefas')
	}

	abrePopup = function(obj,func) {
		popup.abrePopup('usuario','()');
	}

	function resize_iframe()
	{
		var height=window.innerWidth;//Firefox
		if (document.body.clientHeight)
		{
			height=document.body.clientHeight;//IE
		}
		document.getElementById("fraSolicitacaoServico").style.height=parseInt(height - document.getElementById("fraSolicitacaoServico").offsetTop-8)+"px";
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
			  },
			 legend: {
			        show: false
			    }
			});
		}

	 abrePopupPesquisa = function( ) {

	  		$( "#popupCadastroRapido" ).dialog({
	  			title: i18n_message("citcorpore.comum.cadastrorapido"),
	  			width: 1240,
	  			height: 600,
	  			modal: true,
	  			autoOpen: false,
	  			resizable: false,
	  			show: "fade",
	  			hide: "fade"

	  			});


	  		document.getElementById('popupCadastroRapido').style.overflow = "hidden";
	  		document.getElementById('tdAvisosSol').innerHTML = '';


	  		popup2.abrePopup('pesquisaRequisicaoMudanca','onClosePopUp');
	  	}

	    var cont = 0;
	     function atualizaPagina(){
	      	if (document.getElementById('chkAtualiza').checked && cont == 3){
// 	      		window.location.reload();
// 	      		document.form.fireEvent('exibeTarefas');
	      		pesquisaServico();
	      	}
	      	if (cont > 3) {
	      		cont = 0;
			}
	      	window.setInterval(atualizaPagina, 300000);
	      	cont++;
	      }


	 	function LOOKUP_CONTRATOS_select(id, desc) {
			document.form.idContrato.value = id;
			document.form.numeroContratoSel.value = id;
			document.formPesquisa.nomeContrato.value = desc;
			$("#POPUP_CONTRATO").dialog("close");
		}

	 	function LOOKUP_GRUPO_select(id, desc) {
			document.form.idGrupoAtual.value = id;
			document.formPesquisa.nomeGrupo.value = desc;
			$("#POPUP_GRUPO").dialog("close");
		}

		$(function() {
			$("#addContrato").click(function() {
				$("#POPUP_CONTRATO").dialog("open");
			});
		});

		$(function() {
			$("#addGrupo").click(function() {
				$("#POPUP_GRUPO").dialog("open");
			});
		});


	 	function pesquisaServico(){
	 		abreJanelaAguarde();
			 document.form.fireEvent("exibeTarefas");
		}

	 	function limparCampos(){
	 		document.formPesquisa.nomeGrupo.value = "";
	 		document.formPesquisa.nomeContrato.value = "";
	 		document.form.idContrato.value = "";
	 		document.form.idGrupoAtual.value = "";
		}

		function abreJanelaAguarde(){
			JANELA_AGUARDE_MENU.show();
		}

		function fechaJanelaAguarde(){
			JANELA_AGUARDE_MENU.hide();
		}