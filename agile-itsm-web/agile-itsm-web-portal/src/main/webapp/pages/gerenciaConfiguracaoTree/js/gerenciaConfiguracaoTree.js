
addEvent(window, "load", load, false);

$(function() {

	/** POPUPS **/
	$(".dialog").dialog({
		autoOpen : false,
		resizable : false,
		modal : true
	});

	$("#POPUP_PESQUISA").dialog({
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

	$("#POPUP_LEGENDA").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});

	$("#FRAME_ATIVOS").dialog({
		autoOpen : false,
		width : 1000,
		height : 600,
		modal : true
	});


	$("#btnTodos").css('display','none');
	$("#btnLimpar").css('display','none');

});

function load() {
	/* carregarCombosFiltro = function(){
		document.formTree.fireEvent('carregaCombosFiltro');
	} */

	/* CRIANDO UMA INSTANCIA DO JSTREE */
	/* Seleciona a arvore container usando JQuery */
	$("#jsTreeIC").jstree({

			/* a lista 'plugins' permite configurar os plugins ativos nesta instancia */
			"plugins" : ["themes","html_data","ui","crrm","hotkeys","dnd","types","contextmenu", "search"],
			"hotkeys" : {
				"f2" : false,
			},
			/* tratamento dos eventos de drag 'n drop. O que pode ser movido e para onde pode ser movido. */
			"crrm" : {
		        "move" : {
		          "check_move" : function (m) {
		        	  /*Receptor*/
		        	  receptor = m.r.attr("id");
		        	  receptorRel = m.r.attr("rel");
		        	  receptorPai = m.np.attr("id");

		        	  splitReceptor = receptor.split("_");
		        	  nomeReceptor = splitReceptor[0];

		        	  splitReceptorPai = receptorPai.split("_");
		        	  nomeReceptorPai = splitReceptorPai[0];

		        	  /*Emissor*/
		        	  emissorRel = m.o.attr("rel");
		        	  emissor = m.o[0].id;
		        	  splitEmissor = emissor.split("_");
		        	  nomeEmissor = splitEmissor[0];

		        	  if (m.op.attr("id") == m.np.attr("id")) return false;
		        	  else if(nomeEmissor == 'grupo' && emissorRel == 'grupo') return false;
		        	  else if(nomeEmissor == 'item' && emissorRel == 'itemRelacionado') return false;
		        	  else if(nomeEmissor == 'item' && receptorRel == 'grupoRelacionado' ||  receptorRel == 'grupo') return true;
		        	  else if(nomeEmissor == 'grupo' && emissorRel == 'grupoRelacionado' && receptorRel == 'grupo') return true;
		        	  else if(nomeEmissor == 'grupo' && emissorRel == 'grupoRelacionado' && receptorRel == 'grupoRelacionado') return true;
		        	  else if(receptorPai=='jsTreeIC') return false;
		        	  else return false;

					/*
		        	  if(nomeEmissor == 'grupo'){
		        		  return false;
		        	  } else {
						  if(nomeReceptor == 'grupo'){
							  if(nomeReceptorPai == 'grupo'){
								  return true;
							  } else {
								  return false;
							  }
			        	  } else {
							return false;
			        	  }
		        	  } */
		          }
		        }
		      },

		      /* Altera o contextMenu dependendo do tipo de node da arvore. */
		      "contextmenu" : {
		    	    "items" : function ($node) {
				        if ($node.attr('rel') == 'grupoRelacionado'){
			    	        return {
			    	            "gNovoGrupo" : {
			    	                "label" : i18n_message("gerenciaConfiguracaoTree.criarNovoGrupo"),
			    	                "action" : function (obj) {
						    	        			if (acessoGravar == 'N'){
						    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
						    	        				return;
						    	        			}
													this.create(obj,"first", {"data" : {"title" : i18n_message("gerenciaConfiguracaoTree.novoGrupo")}, "attr" : {"rel" : "grupoRelacionado", "id" : "grupo_novo"} } );
												},
			    	                "icon" : "images/group_menu_new.png"
			    	            },
			    	            "gRenomearGrupo" : {
			    	                "label" : i18n_message("gerenciaConfiguracaoTree.renomearGrupo"),
			    	                "action" : function (obj) {
				    	        			if (acessoGravar == 'N'){
				    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
				    	        				return;
				    	        			}
			    	                		this.rename(obj);
			    	                		},
			    	            	"icon" : "images/group_menu_edit.png"
			    	            },
			    	            "gApagarGrupo" : {
			    	                "label" : i18n_message("gerenciaConfiguracaoTree.apagarGrupo"),
			    	                "action" : function (obj) {
						    	        			if (acessoGravar == 'N'){
						    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
						    	        				return;
						    	        			}
						    	        			if (acessoDeletar == 'N'){
						    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoRemocao"));
						    	        				return;
						    	        			}
						    	                	if (confirm(i18n_message("gerenciaConfiguracaoTree.desejaExcluir") + " " + trim(obj.children("a").text()) + "")) {
						    	                		this.remove(obj);
						    						}
			    	                			},
			    	            	"icon" : "images/group_menu_remove.png"
			    	            },
			    	            "gNovoItem" : {
			    	                "label" : i18n_message("gerenciaConfiguracaoTree.criarNovoItem"),
			    	                "action" : function (obj) {
			    	        			if (acessoGravar == 'N'){
			    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
			    	        				return;
			    	        			}
			    	                	this.create(obj, "first", {"data" : {"title" : i18n_message("gerenciaConfiguracaoTree.novoItem")}, "attr" : {"rel" : "item", "id" : "item_novo"} } ); },
			    	                "icon" : "images/item_menu_new.png"
			    	            }
			    	        };
						} else if ($node.attr('rel') == 'item' || $node.attr('rel') == 'itemRelacionado') {
							return {
			    	           /*  "iListarAtivos" : {
			    	                "label" : "Listar ativos",
			    	                "action" : function (obj) { abrirInventario(obj.attr('id')); },
			    	                "icon" : "images/item_menu_assets.png"
			    	            }, */
			    	            "iVisualizarJanela" : {
			    	                "label" : i18n_message("gerenciaConfiguracaoTree.visualizarJanela"),
			    	                "action" : function (obj) { visualizarJanela(obj.attr('id')); },
			    	                "icon" : "images/viewCadastro.png"
			    	            },
			    	            "iRelacionados" : {
			    	                "label" : i18n_message("gerenciaConfiguracaoTree.verItensRelacionados"),
			    	                "action" : function (obj) { listarRelacionados(obj.attr('id')); },
			    	                "icon" : "images/item_menu_relation.png"
			    	            },
			    	            "iPesquisaRelacionados" : {
			    	                "label" : i18n_message("gerenciaConfiguracaoTree.pesquisarRelacionados"),
			    	                "action" : function (obj) { pesquisarRelacionados(obj.attr('id')); },
			    	                "icon" : "images/item_menu_relation.png"
			    	            },
			    	            "iCriarRelacionado" : {
			    	                "label" : i18n_message("gerenciaConfiguracaoTree.criarItemRelacionado"),
			    	                "action" : function (obj) {
			    	        			if (acessoGravar == 'N'){
			    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
			    	        				return;
			    	        			}
			    	                	this.create(obj, "first", {"data" : {"title" : i18n_message("gerenciaConfiguracaoTree.novoItemRelacionado")}, "attr" : {"rel" : "itemRelacionado", "id" : "itemRel_novo"} } ); },
			    	                "icon" : "images/item_menu_new.png"
			    	            },
			    	            /* "iApagarItem" : {
			    	                "label" : "Apagar item",
			    	                "action" : function (obj) {
			    	                	if (confirm("Deseja excluir " + trim(obj.children("a").text()) + "")) {
			    	                		this.remove(obj);
			    						}
    	                			},
			    	                "icon" : "images/item_menu_remove.png"
			    	            } */
			    	        };
						} else if ($node.attr('rel') == 'grupo' || $node.attr('rel') == 'database') {
							return {
								"nNovoGrupo" : {
										"label" : i18n_message("gerenciaConfiguracaoTree.criarNovoGrupo"),
				    	                "action" : function (obj) {
						    	        			if (acessoGravar == 'N'){
						    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
						    	        				return;
						    	        			}
														this.create(obj,"last", {"data" : {"title" : i18n_message("gerenciaConfiguracaoTree.novoGrupo")}, "attr" : {"rel" : "grupoRelacionado", "id" : "grupo_novo"} } );
													},
				    	                "icon" : "images/group_menu_new.png"
								},
								/*
								"nNovoItem" : {
				    	                "label" : "Criar novo item",
				    	                "action" : function (obj) {this.create(obj, "first", {"data" : {"title" : "Novo item"}, "attr" : {"rel" : "item", "id" : "item_novo"} } ); },
				    	                "icon" : "images/item_menu_new.png"
								},*/
								"nExportCMDB" : {
			    	                "label" : i18n_message("gerenciaConfiguracaoTree.exportCMDB"),
			    	                "action" : function (obj) {exportarCMDB(obj.attr('id'));},
			    	                "icon" : "images/database-upload-icon.png"
								}
							};
						} /* else if ($node.attr('rel') == 'itemRelacionado') {
							return {
								"nNovoGrupo" : {
										"label" : "Criar novo grupo",
				    	                "action" : function (obj) {
														this.create(null,"after", {"data" : {"title" : "Novo grupo"}, "attr" : {"rel" : "grupoRelacionado", "id" : "grupo_novo"} } );
													},
				    	                "icon" : "images/group_menu_new.png"
								},
								"nNovoItem" : {
				    	                "label" : "Criar novo item",
				    	                "action" : function (obj) {this.create(obj, "first", {"data" : {"title" : "Novo item"}, "attr" : {"rel" : "item", "id" : ""} } ); },
				    	                "icon" : "images/item_menu_new.png"
								},
								"nProcurarItem" : {
			    	                "label" : "Procurar itens na rede",
			    	                "action" : function (obj) {this.create(obj, "first", {"data" : {"title" : "Novo item"}, "attr" : {"rel" : "item"} } ); },
			    	                "icon" : "images/item_menu_search.png"
								}
							};
						} */ else {
							return false;
						}
		    	    }
		    	},

		      /* Altera o padrao de imagens dos icones. */
		      'types': {
		          'types' : {
		        	  /* este e' o mesmo nome que esta' no atributo 'rel' das tags <li> */
		              'grupo' : {
		            	  'text' : 'red',
		                  'icon' : {
		                      'image' : 'images/group_default.png'
		                  },
		                  'valid_children' : 'default'
		              },
		              'database' : {
		            	  'text' : 'red',
		                  'icon' : {
		                      'image' : 'images/database-icon.png'
		                  },
		                  'valid_children' : 'default'
		              },
		        	  'item' : {
		                  'icon' : {
		                      'image' : 'images/item_default.png'
		                  }
		              },
		              'grupoRelacionado' : {
		                  'icon' : {
		                      'image' : 'images/group_default.png'
		                  },
		                  'valid_children' : 'default'
		              },
		              'itemRelacionado' : {
		                  'icon' : {
		                      'image' : 'images/item_relation.png'
		                  },
		                  'valid_children' : 'default'
		              }
		          }

		      },

			/* Cada plugin incluido pode ter seus proprios objetos de configuracao. */
			"core" : { }

		})

		/* Tratamento dos eventos de movimento dos nodes. */
		.bind("move_node.jstree", function (event, data){
			idGrupo = data.rslt.np.attr("id");
			data.rslt.o.each(function () {
				 var i = $(this).attr("id");
				 iReceptor = i.split("_");
	        	 if(iReceptor[0]=='grupo' || iReceptor[0]=='database')
					mudarGrupoDeGrupo($(this).attr("id"), idGrupo);
	        	 else if(iReceptor[0]=='item')
	        		mudarItemDeGrupo ($(this).attr("id"), idGrupo);
	        	 else if(idGrupo=='jsTreeIC')
	        		 return false;
            });

		})


		/* Tratamento dos eventos de renomear nodes da arvore. */
		/* Tambem trata os nodes novos pois ao criar um node ele ja renomeia. */
		/* Padrao de IDs para novos: grupo_novo, item_novo, onde x e' o ID do grupo. */
		.bind('rename_node.jstree', function (event, data){
			var id = data.rslt.obj.attr("id");
			var idNumerico = id.replace(/\D/g, "");

			var nome = data.rslt.name;
			var nomeTratado = $.trim(nome)
			if (nomeTratado == ''){
				alert(i18n_message("gerenciaConfiguracaoTree.nomeInvalidoGrupo"));
				$.jstree.rollback(data.rlbk);
			}

			/* Array do ID. Ex.: grupo_15, item_novo, grupo_0, etc. */
			listId = id.split("_");

			/* Se for um item ele devera' ter um grupo. */
			if ( listId[0] == 'item' || listId[0] == 'itemRel' || listId[0] == 'grupo' || listId[0] == 'database') {
				var idPai = data.inst._get_parent(data.rslt.obj).attr("id");
				var idNumPai = idPai.replace(/\D/g, "");
			}


			/* Verifica se e' grupo, item, recem criado ou renomeado. */
			if(listId[1] == 'novo') {
				if ( listId[0] == 'grupo' || listId[0] == 'database') {
					if (nomeTratado.length > 100) {
						alert(i18n_message("gerenciaConfiguracaoTree.nomeMaior100"));
						$("#jsTreeIC").jstree("remove","#grupo_novo");
					} else if (nomeTratado == '' || nomeTratado == i18n_message("gerenciaConfiguracaoTree.novoGrupo")) {
						alert(i18n_message("gerenciaConfiguracaoTree.nomeInvalidoGrupo"));
						$("#jsTreeIC").jstree("remove","#grupo_novo");
					} else {
						atualizarDadosRenomear(idNumerico, nomeTratado, idNumPai);
						criarGrupo(idNumPai, nomeTratado);
						return true;
					}
				} else if (listId[0] == 'item') {
					if (nomeTratado.length > 100) {
						alert(i18n_message("gerenciaConfiguracaoTree.nomeMaior100"));
						$("#jsTreeIC").jstree("remove","#item_novo");
					} else if (nomeTratado == '' || nomeTratado.toLowerCase() == i18n_message("gerenciaConfiguracaoTree.novoItem").toLowerCase()) {
						alert(i18n_message("gerenciaConfiguracaoTree.nomeInvalidoItem"));
						$("#jsTreeIC").jstree("remove","#item_novo");
					} else {
						if(nomeTratado.toLowerCase().indexOf("win") !=-1){
							$("#item_novo").find("a > ins").removeClass("jstree-icon").addClass("wind");
						}else if(nomeTratado.toLowerCase().indexOf("linux") !=-1){
							$("#item_novo").find("a > ins").removeClass("jstree-icon").addClass("linux");
						}
						criarItem(nomeTratado, idNumPai);
						return true;
					}
				} else if (listId[0] == 'itemRel') {
					if (nomeTratado.length > 100) {
						alert(i18n_message("gerenciaConfiguracaoTree.nomeMaior100"));
						$("#jsTreeIC").jstree("remove","#itemRel_novo");
					} else if (nomeTratado == '' || nomeTratado == i18n_message("gerenciaConfiguracaoTree.novoItemRelacionado")) {
						alert(i18n_message("gerenciaConfiguracaoTree.nomeInvalidoItemRelacionado"));
						$("#jsTreeIC").jstree("remove","#itemRel_novo");
					} else {
						criarItemRelacionado(nomeTratado, idNumPai);
						return true;
					}
				}
				return false;
			} else if ( listId[0] == 'grupo' || listId[0] == 'database') {
				if (nomeTratado.length > 100) {
					alert(i18n_message("gerenciaConfiguracaoTree.nomeMaior100"));
					$.jstree.rollback(data.rlbk);
				} else {
					atualizarDadosRenomear(idNumerico, nomeTratado, idNumPai);
					document.formTree.fireEvent('renomearGrupo');
				}
			} else if ( listId[0] == 'item' ) {
				/* Ao implementar retirar o 'return false;'. */
				return false;
			}

		})


		/* Tratamento dos eventos de remover nodes da arvore. */
		 .bind("remove.jstree", function (event, data){
			var id = data.rslt.obj.attr("id");
			var idNumerico = id.replace(/\D/g, "");

			var nome = data.rslt.obj.children("a").text();
			var nomeTratado = trim(nome);

			/* Array do ID. Ex.: grupo_15, item_novo, grupo_0, etc. */
			listId = id.split("_");

			/* Se a remocao for de um item novo nao precisa ser tratado pois nao esta salvo em base de dados. */
			if ( listId[1] == 'novo' ) {
				return true;
			}

			if ( listId[0] == 'grupo' ) {
				var quantidadeFilhos = 0;

				$(data.rslt.obj).find("li").each( function( idx, listItem ) {
					quantidadeFilhos ++;
                })

                if(quantidadeFilhos > 0){
                	alert(i18n_message("gerenciaConfiguracaoTree.GrupoNaoPodeSerApagado"));
                	$.jstree.rollback(data.rlbk);
                } else {
                	$("#idGrupoItemConfiguracao").val(idNumerico);
                	$("#nomeGrupoItemConfiguracao").val(nomeTratado);
                	document.formTree.fireEvent('apagarGrupo');
                }
			} else if ( listId[0] == 'item' ) {
				$("#idItemConfiguracao").val(idNumerico);

				/* document.formTree.fireEvent('apagarItem'); */
			}
		})


		/* Tratamento dos eventos de duplo clique. */
		/* Se for 'grupos' o duplo clique deve expandi-lo. */
		.bind("dblclick.jstree", function (event) {
			var node = $(event.target).closest("li");

			if(node.attr('rel') == 'grupo' || node.attr('rel') == 'database' || node.attr('rel') == 'grupoRelacionado'){
				$("#jsTreeIC").jstree("toggle_node", node);
			} else if ( node.attr('rel') == 'item' ) {
				var id = node.attr('id');
				id = Number(id.slice('item-'.length));
				abrirEditarItem(id);
			}
		})


		/* Tratamento dos eventos de selecao dos nodes. */
		.bind("select_node.jstree", function (event, data) {
			id = data.rslt.obj.attr("id");

			listId = id.split("_");

			if(listId[0] == 'grupo' || listId[0] == 'database') {
				$("#jsTreeIC").jstree("deselect_all");
				$("#jsTreeIC").jstree("toggle_node", "#"+id);
				$("#jsTreeIC").jstree("hover_node", "#"+id);
			} else if ( listId[0] == 'item' ) {
				/**
				* Motivo: Definir o valor do idGrupoItemConfiguracao
				* Logo apos selecionar o ic o codigo abaixo verifica o primeiro grupo acima do mesmo e seta o seu valor dentro do input idGrupoItemConfiguracao
				* Autor: flavio.santana
				* Data/Hora: 20/11/2013
				*/
				$('#idGrupoItemConfiguracao').val($($('#'+id).parents("li[rel=grupoRelacionado]:first")).attr("id").slice('grupo_'.length));
				abrirEditarItem(id);
			}

		})
		  .bind("loaded.jstree", function (event, data) {
            $(this).jstree("open_all");
        })

		var altura = ($(window).height() - 50);
		var largura = ($(window).width() - ($("#jsTreeIC").width() + 55));
		//$("#jsTreeIC").css("height", altura);
		$("#FRAME_OPCOES").css("height", altura);
		$("#FRAME_OPCOES").css("width", largura);

		$(window).resize(function () {
			$("#FRAME_OPCOES").css("width", $(window).width() - ($("#jsTreeIC").width() + 55));
		});



}
function legenda() {
	$("#POPUP_LEGENDA").dialog("open");
}

/* Funcao TRIM. Remove espacos no inicio e no fim de uma string. */
function trim(str) {
	return str.replace(/^\s+|\s+$/g,"");
}

/* Funcao que cria grupos de itens de configuracao. */
function criarGrupo(id, nome){
	$("#idGrupoItemConfiguracaoPai").val(id);
	$("#nomeGrupoItemConfiguracao").val(nome);

	document.formTree.fireEvent('CriarGrupo');
}

/* Funcao que cria itens de configuracao. */
function criarItem(nome, idGrupo){
	$("#identificacao").val(nome);
	$("#idGrupoItemConfiguracao").val(idGrupo);

	document.formTree.fireEvent('CriarItem');
}

/* Funcao que cria itens de configuracao. */
function criarItemRelacionado(nome, idConfiguracaoPai){
	$("#identificacao").val(nome);
	$("#idItemConfiguracaoPai").val(idConfiguracaoPai);
	document.formTree.fireEvent('CriarItemRelacionado');
}

/* Funcao que abre o iframe de edicao de itens. */
function abrirEditarItem(id){
	/**
	 * Chama o load da página
	 * @author flavio.santana
	 * 25/10/2013 12:00
	 */
	JANELA_AGUARDE_MENU.show();
	var idItem = id.replace(/\D/g, "");
	if(idItem>"") {
		document.getElementById('iframeOpcoes').src = ctx + '/pages/itemConfiguracaoTree/itemConfiguracaoTree.load?idItemConfiguracao='+ idItem;
		$("#FRAME_OPCOES").dialog("open");
	}
}

/* Funcao que lista os itens relacionados ao item escolhido. */
function listarRelacionados(id){
	/* Bruno.Aquino: Se estiver sendo usado o mozzila como navegador,
	será setado no hidden correspondente, se não será setado a String 'outro'
	para ser feito a validação no action */
	if($.browser.mozilla){
		var browser = "Mozzila";
	} else
		var browser = "outro";
	$("#idBrowserName").val(browser);

	JANELA_AGUARDE_MENU.show();
	var idItem = id.replace(/\D/g, "");
	$("#idItemConfiguracao").val(idItem);
	document.formTree.fireEvent('listarRelacionados');
}

/* Funcao que lista os itens relacionados ao item escolhido. */
function pesquisarRelacionados(id){
	var idItem = id.replace(/\D/g, "");
	document.getElementById("pesqLockupLOOKUP_ITENSCONFIGURACAORELACIONADOS_iditemconfiguracaopai").value = idItem;
	$('#POPUP_PESQUISA').dialog('open');
}

function visualizarJanela(id){
	id = id.split('item_').join('');
	document.getElementById('iframeAtivos').src = ctx +'/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id='+ id;
	$("#FRAME_ATIVOS").dialog("open");
}

function LOOKUP_ITENSCONFIGURACAORELACIONADOS_select(id, desc) {
	document.getElementById('iframeOpcoes').src =ctx +'/pages/itemConfiguracaoTree/itemConfiguracaoTree.load?idItemConfiguracao='+ id;
	limpar_LOOKUP_ITENSCONFIGURACAORELACIONADOS();
	$('#POPUP_PESQUISA').dialog('close');
	$("#FRAME_OPCOES").dialog("open");
}

/* Funcao que adiciona os itens relacionados 'a arvore. */
function adicionarRelacionados(idPai, idItemRelacionado, nomeItemRelacionado){
	$("#jsTreeIC").jstree("remove","#"+idItemRelacionado);
	$("#jsTreeIC").jstree("create", $("#"+idPai), "inside",  { "data" : {"title" : nomeItemRelacionado}, "attr" : {"rel" : "itemRelacionado", "id" : idItemRelacionado} }, function() { }, true);
}

/* Funcao que adiciona os itens relacionados 'a arvore. */
function adicionarRelacionadosImagem(idPai, idItemRelacionado, nomeItemRelacionado, imagem){
	$("#jsTreeIC").jstree("remove","#"+idItemRelacionado);
	$("#jsTreeIC").jstree("create", $("#"+idPai), "inside", {"data" : {"title" : nomeItemRelacionado , "icon" : imagem }, "attr" : {"rel" : "itemRelacionado", "id" : idItemRelacionado}}, function() { }, true);
}

function adicionarRelacionadosJson(idPai, json) {
	$("#jsTreeIC").jstree("create", $("#"+idPai), "inside", json, function() { }, true);
}

/* Verifica itens criticos. */
function criticos(id){
	if($("#" + id).attr('rel')=="item") {
		var s = id.split('item_').join('');
		$("#idItemConfiguracao").val(s);
		document.formTree.fireEvent('verificaCriticidade');
	}
	$("#" + id).find(".jstree-leaf").each(function() {
		if($(this).attr('rel')=="itemRelacionado") {
			var s = this.id.split('item_').join('');
			$("#idItemConfiguracao").val(s);
			document.formTree.fireEvent('verificaCriticidade');
		}
	});
}
/*Add o icone de item critico*/
function addCritico(id) {
	$("#"+id).append(($("#"+id).has("img").length ? "" : "<img src='../../imagens/b.gif' />"));
}

/*Add o icone de item critico para o Pai */
function addCriticoPai(id) {
	$("#"+id).find("a.r").append(($("#"+id).has("img").length ? "" : "<img src='../../imagens/b.gif' />"));
}

/* @Deprecated
/* Funcao que adiciona os itens relacionados em uma popup.
*/
function adicionarRelacionadosPesquisa(idPai, idItemRelacionado, nomeItemRelacionado){
	var nodeLi = createElementWithClassName('div', 'list');
	var nodeL = createElementWithClassName('div', 'listitem');

	var nodeA = createElementWithClassName('div', 'url title');

	nodeLi.id = idItemRelacionado;
	nodeLi.addEventListener('click', event);
	nodeA.innerHTML=nomeItemRelacionado;
	nodeL.appendChild(nodeA);
	nodeLi.appendChild(nodeL);

	$("#entry").append(nodeLi);
}

/* Tratamento dos eventos de selecao dos nodes a partir do popup. */
function event(e) {
  	var box = e.currentTarget;
  	var id = box.id;
	abrirEditarItem(id);
	setTimeout(function () { $('#POPUP_PESQUISA').dialog('close'); },1000);
}

/* Cria um elemento com classe */
function createElementWithClassName(type, className) {
	var elm = document.createElement(type);
	elm.className = className;
	return elm;
}

/* Funcao para mudar um item de grupo. */
function mudarItemDeGrupo(idItem, idGrupo){
	if (acessoGravar == 'N'){
		alert(i18n_message("baseconhecimento.permissao.sempermissao"));
		window.location = ctx + '/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load';
		return;
	}
	var idItemNumero = idItem.replace(/\D/g, "");
	var idGrupoNumero = idGrupo.replace(/\D/g, "");
	$("#idItemConfiguracao").val(idItemNumero);
	$("#idGrupoItemConfiguracao").val(idGrupoNumero);
	document.formTree.fireEvent('mudarItemDeGrupo');
}

/* Funcao para mudar um item de grupo. */
function mudarGrupoDeGrupo(idItem, idGrupo){
	if (acessoGravar == 'N'){
		alert(i18n_message("baseconhecimento.permissao.sempermissao"));
		window.location = ctx + '/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load';
		return; 
	}
	var idItemNumero = idItem.replace(/\D/g, "");
	var idGrupoNumero = idGrupo.replace(/\D/g, "");
	$("#idGrupoItemConfiguracao").val(idItemNumero);
	$("#idGrupoItemConfiguracaoPai").val(idGrupoNumero);
	document.formTree.fireEvent('mudarGrupoDeGrupo');
}

function recarregar(){
	window.location = ctx + '/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load';
}



/* Funcao para voltar ao menu principal do sistema. */
function voltar(){
	window.location = ctx + '/pages/index/index.load';
}

/* Funcao para atualizar o ID de novos nodes de acordo com a base de dados. */
function atualizarId(idAtual, idNovo){
	var node = document.getElementById(idAtual);
	node.id = idNovo;
}
/* Funcao para reload do node atualizado de acordo com a base de dados. */
function reloadItem(idItem) {
	$("#idItemConfiguracao").val(idItem);
	document.formTree.fireEvent('listarRelacionados');
}

/* Renomeia o item de configuração e move de grupo caso necessário. */
function renomearMoverItemConfiguracao(idItemConfiguracao, identificacao, idGrupo, status, classStatus, idInventario ) {
	if($('#idGrupoItemConfiguracao').val()!="" && $('#idGrupoItemConfiguracao').val()!=idGrupo){
		$('#jsTreeIC').jstree('rename_node','#item_'+idItemConfiguracao,identificacao +' - ');
		$('#item_'+idItemConfiguracao).find('span').text(status).removeClass().addClass(classStatus);
		if(idGrupo != null)
			$('#jsTreeIC').jstree('move_node','#item_'+idItemConfiguracao,'#grupo_'+idGrupo);
		else
			$('#jsTreeIC').jstree('move_node','#item_'+idItemConfiguracao,'#grupo_'+idInventario);
	} else {
		$('#jsTreeIC').jstree('rename_node','#item_'+idItemConfiguracao,identificacao +' - ');
		$('#item_'+idItemConfiguracao).find('span').text(status).removeClass().addClass(classStatus);
	}

}

function exportarCMDB(idItem){
	$("#idItemConfiguracaoExport").val(StringUtils.onlyNumbers(idItem));
	JANELA_AGUARDE_MENU.show();
	document.formTree.fireEvent('exportarCMDB');
}

function getFile(pathFile, fileName){
	window.location.href = ctx + '/baixar.getFilefile=' + pathFile + '&fileName=' + fileName;
}

function filtar(){
	document.formTree.criticidade.value = $("#cboCriticidade").val();
	document.formTree.status.value = $("#comboStatus").val();
	document.formTree.identificacao.value = $("#identificador").val();
	JANELA_AGUARDE_MENU.show();
	document.formTree.fireEvent("load");
}
$(function() {

			var myLayout;
			myLayout = $('body').layout({
	          	north__resizable: false // OVERRIDE the pane-default of 'resizable=true'
	          , north__spacing_open: 0 // no resizer-bar when open (zero height)
	          , north__spacing_closed: 350
	          , north__minSize: 110
	          , west: {
	        	   minSize: 310
	        	  ,onclose_end: function(){
	        		  $('#FRAME_OPCOES').css('width', '98%');
	        	  }
				  ,onopen_end: function(){
					  $('#FRAME_OPCOES').css('width', '98%');
				}
	          }
    		});

			jQuery.fn.toggleText = function(a,b) {
				return this.html(this.html().replace(new RegExp("("+a+"|"+b+")"),function(x){return(x==a)?b:a;}));
				};

			/*  $('#divpesquisa').before("<span id='spanPesq' class='manipulaDiv' style='cursor: pointer'>Mostrar Pesquisa</span>"); */

			 $('#filtro').css('display', 'none')
			// $('#jsTreeIC').css('height',  $('#west').height()-240);

			 $('#spanPesq').click(function() {
				 document.formTree.fireEvent('carregaCombosFiltro');
				 $('#filtro').toggle(function(){
					 /**
					 * Adiciona o scroll na treeView
					 * @autor flavio.santana
					 */
					 $('#jsTreeIC').animate({ height: $(window).height() - $("#jsTreeIC").offset().top - 20});
				 });
			 });
			 /**
			 * Adiciona o scroll na treeView
			 * @autor flavio.santana
			 */
			 $('#jsTreeIC').animate(
			    { height: $(window).height() - $("#jsTreeIC").offset().top - 20},
			    {
			        complete : function(){
			            //alert('this alert will popup twice');
			        }
			    }
			 );
		});

var acessoGravar = 'N';
var acessoDeletar = 'N';

atualizarDadosRenomear = function(idNumerico, nomeTratado, idNumPai){
	$('#idGrupoItemConfiguracao').val(idNumerico);
	$('#nomeGrupoItemConfiguracao').val(nomeTratado);
	$('#idGrupoItemConfiguracaoPai').val(idNumPai);
}

function fecharJanelaAguarde() {
	JANELA_AGUARDE_MENU.hide();
}

function esconderBotaoVoltar() {
	document.getElementById('divControleLayout').style.display = 'none';
}