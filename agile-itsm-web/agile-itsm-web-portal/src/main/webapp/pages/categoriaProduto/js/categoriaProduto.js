
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
		$("#loading_overlay .loading_message").delay(200).fadeOut(function(){});
		$("#loading_overlay").delay(500).fadeOut();
	}

	function LOOKUP_CATEGORIAPRODUTO_select(id, desc) {
		document.form.restore({
			idcategoria : id
		});
	}

	function restaurarCategoria(id) {
		document.form.idCategoria.value = id;
		document.form.fireEvent("restore");

		var index = $('#tabs a[href="#tabs-1"]').parent().index();
		$('#tabs').tabs('select', index);
	}

	function LOOKUP_CATEGORIAPRODUTOPAI_select(idTipo, desc) {
		document.form.idCategoriaPai.value = idTipo;

	    var valor = desc.split('-');
		var nomeConcatenado = "";
		for(var i = 0 ; i < valor.length; i++){
			if(i == 0){
				document.form.nomeCategoriaPai.value = valor[i];
			}
		}
		fecharPopup();
	}

	function removerCategoria() {
		var idCategoriaServico = document.getElementById("idCategoriaServico");

		if (idCategoriaServico != null && idCategoriaServico.value == 0) {
			alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
			return false;
		}
		if (confirm(i18n_message("citcorpore.comum.deleta")))
			document.form.fireEvent("remove");
	}

	$(function() {
		$("#POPUP_CATEGORIAPRODUTOPAI").dialog( {
			autoOpen : false,
			width : 705,
			height : 500,
			modal : true
		});
	});

	$(function() {
		$("#POPUP_MENUFOTOS").dialog( {
			autoOpen : false,
			width : 705,
			height : 400,
			modal : true,
			title: i18n_message("menu.imagem")
		});

		$("#btnEscolherFotos").click(function() {
			document.form.fireEvent("mostraImagem");
			$('#POPUP_MENUFOTOS').dialog('close');
		});
	});

	function consultarCategoriaServicoSuperior(){
		$("#POPUP_CATEGORIAPRODUTOPAI").dialog("open");
	}

	function fecharPopup(){
		$("#POPUP_CATEGORIAPRODUTOPAI").dialog("close");
	}

	function anexos(){
		$('#POPUP_MENUFOTOS').dialog('open');
		uploadAnexos.refresh();
	};

	function situacaoAtivo() {
		var radioSituacao = document.form.situacao;
		var radioLength = radioSituacao.length;
		for(var i = 0; i < radioLength; i++) {
			radioSituacao[i].checked = false;
			if(radioSituacao[i].value == "A") {
				radioSituacao[i].checked = true;
			}
		}
	}

    var seqCriterio = '';
    incluirCriterio = function() {
        GRID_CRITERIOS.addRow();
        seqCriterio = NumberUtil.zerosAEsquerda(GRID_CRITERIOS.getMaxIndex(),5);
        eval('document.form.idCriterio' + seqCriterio + '.focus()');
    }

    exibeCriterio = function(serializeCriterio) {
        if (seqCriterio != '') {
            if (!StringUtils.isBlank(serializeCriterio)) {
                var criterioDto = new CIT_CriterioCotacaoCategoriaDTO();
                criterioDto = ObjectUtils.deserializeObject(serializeCriterio);
                try{
                    eval('HTMLUtils.setValue("idCriterio' + seqCriterio + '",' + criterioDto.idCriterio + ')');
                }catch(e){
                }
                eval('document.form.pesoCotacao' + seqCriterio + '.value = "' + criterioDto.pesoCotacao + '"');
            }
        }
    }

    getCriterio = function(seq) {
        var criterioDto = new CIT_CriterioCotacaoCategoriaDTO();

        seqCriterio = NumberUtil.zerosAEsquerda(seq,5);
        criterioDto.sequencia = seq;
        criterioDto.idCriterio = parseInt(eval('document.form.idCriterio' + seqCriterio + '.value'));
        criterioDto.pesoCotacao = eval('document.form.pesoCotacao' + seqCriterio + '.value');
        return criterioDto;
    }

    verificarCriterio = function(seq) {
        var idCriterio = eval('document.form.idCriterio' + seq + '.value');
        var count = GRID_CRITERIOS.getMaxIndex();
        for (var i = 1; i <= count; i++){
            if (parseInt(seq) != i) {
                 var trObj = document.getElementById('GRID_CRITERIOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
                 if (!trObj){
                    continue;
                 }
                 var idAux = eval('document.form.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.value');
                 if (idAux == idCriterio) {
                      alert(i18n_message('categoria.produto.criterio_selecionado'));
                      eval('document.form.idCriterio' + seq + '.focus()');
                      return false;
                 }
            }
        }
        return true;
    }

    function tratarCriterios(){
        try{
            var count = GRID_CRITERIOS.getMaxIndex();
            var contadorAux = 0;
            var objs = new Array();

            for (var i = 1; i <= count; i++){
                var trObj = document.getElementById('GRID_CRITERIOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
                if (!trObj){
                    continue;
                }

                var criterioDto = getCriterio(i);
                if (parseInt(criterioDto.idCriterio) > 0) {
                    if  (!verificarCriterio(NumberUtil.zerosAEsquerda(i,5))) {
                        return false;
                    }
                    if (StringUtils.isBlank(criterioDto.pesoCotacao)){
                        alert(i18n_message('categoriaProduto.informePeso'));
                        eval('document.form.pesoCotacao' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
                        return false;
                    }
                    objs[contadorAux] = criterioDto;
                    contadorAux = contadorAux + 1;
                }else{
                    alert(i18n_message('categoriaProduto.selecioneCriterio'));
                    eval('document.form.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');
                    return false;
                }
            }
            document.form.colCriterios_Serialize.value = ObjectUtils.serializeObjects(objs);
            return true;
        }catch(e){

        }
    }

    function gravar() {
        if (!tratarCriterios()){
            return;
        }

        document.form.save();
    }

    $(function() {
    	var criteriosPesosCotacao = [
			'pesoCotacaoPreco',
			'pesoCotacaoPrazoEntrega',
			'pesoCotacaoPrazoPagto',
			'pesoCotacaoPrazoGarantia',
			'pesoCotacaoTaxaJuros'
		];

    	// Associa a função de validação aos critérios e pesos obrigatórios para a cotação (quando o campo perder o foco).
    	for (i in criteriosPesosCotacao) {
    		$('#' + criteriosPesosCotacao[i]).focusout(function() {
    			if ($(this).val() == '')
    				return;

    			// Referencia o elemento que precede (anterior) ao elemento pai da caixa de texto que está sendo validada.
    			var nomeCampo = $(this).parent().prev().html();

    			nomeCampo = nomeCampo.substr(0, nomeCampo.indexOf('&nbsp;') ).replace(/^\s+|\s+$/g, '').toLowerCase();

    			var msg = null;

    			// Verificando se o valor do critério ou peso de cotação é numérico e possui um ou dois algarismos.
    			if ($(this).val().match(/^\d{1,2}$/) ) {
    				if ($(this).val() < 0 || $(this).val() > 10) {
    					msg = i18n_message("categoriaProduto.valorInformado") + ' ' + nomeCampo + ' ' + i18n_message("categoriaProduto.invalidoforaIntervalo");
    					alert(msg);
        				$(this).val('');
        			}
    			} else {
    				msg = i18n_message("categoriaProduto.valorInformado") + ' ' + nomeCampo + ' ' + i18n_message("categoriaProduto.invalidoforaIntervalo");
    				alert(msg);
    				$(this).val('');
    			}
    		});
    	}
    });

