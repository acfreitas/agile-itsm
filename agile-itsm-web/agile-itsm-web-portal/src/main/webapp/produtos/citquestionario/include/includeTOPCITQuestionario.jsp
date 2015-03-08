<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.citframework.util.Constantes"%>
<script>    
limpaCamposCalculados = function(origem){
	if ($('idQuestoesCalculadas') == null || $('idQuestoesCalculadas') == undefined)
		return;
	
    for(var i = 0; i < $('idQuestoesCalculadas').length; i++){
        var idCampo = $('idQuestoesCalculadas').options[i].text;
        var nomeCampo = retornaNomeCampo(idCampo,'');
        if ($(nomeCampo) != null) {
            $(nomeCampo).value = ''; 
        }    
    }
}; 

trataComplementoOpcao = function(campo, idQuestao, idOpcao){
    var objs = document.getElementsByTagName('div');
    if (objs != null && campo.type != 'checkbox'){
       for(var i = 0; i < objs.length; i++){
           var div = objs[i]; 
           var id = div.id;
           if (id.indexOf('divOpcao'+idQuestao+'_') >= 0) {
               div.style.display = 'none';
           }
       }  
    }
    var divComplemento = null;
    var selecionado = false;
    if (campo.type == 'select-one') {
        selecionado = (campo.value == campo.options[campo.selectedIndex].value);
        divComplemento = $('divOpcao'+idQuestao+'_'+campo.value);
    }    
    if (campo.type != 'select-one') {
        divComplemento = $('divOpcao'+idQuestao+'_'+idOpcao);
        selecionado = (campo.checked && campo.value == idOpcao);
    }    
    if (divComplemento != null) {
        if (selecionado) {
            divComplemento.style.display = 'block';
        }else{
            divComplemento.style.display = 'none';
        }                    
    }            
};

    function retornaElementoPelaSigla(sigla) {
        var idCampo = mapQuestoesComSigla.get(sigla);
        if (idCampo != null && idCampo != undefined){
        	return document.formQuestionario[retornaNomeCampo(idCampo,'')];
        }else
            return null;
    }

    function desabilitaElemento(elem) {
        if (elem != null && elem != undefined) {
            if (elem.length) {
                for(var i = 0; i < elem.length; i++){
                	elem[i].disabled = true;   
                }
            }else{
                elem.disabled = true;  
            }
        }
    }

    function habilitaElemento(elem) {
        if (elem != null && elem != undefined) {
            if (elem.length) {
                for(var i = 0; i < elem.length; i++){
                    elem[i].disabled = false;   
                }
            }else{
                elem.disabled = false;  
            }
        }
    }

    function limpaQuestao(sigla) {
        limpaElemento(retornaElementoPelaSigla(sigla));
    }
    
    function limpaElemento(elem) {
        if (elem != null && elem != undefined) {
            if (elem.length) {
                for(var i = 0; i < elem.length; i++){
                    if (elem[i].type != "radio" && elem[i].type != "checkbox" && elem[i].type != "check-box") {
                    	elem[i].value = '';  
                    }else{
                        elem[i].checked = false;
                    }  
                }
            }else{
                if (elem.type != "radio" && elem.type != "checkbox" && elem.type != "check-box") {
                    elem.value = '';
                }else{
                    elem.checked = false;
                }
            }
        }
    }

    function escondeQuestao(sigla) {
        var idCampo = mapQuestoesComSigla.get(sigla);
        if (idCampo != null && idCampo != undefined){
            escondeElemento(document.getElementById('divHist_' + idCampo));   
            escondeElemento(document.getElementById('divHistGraf_' + idCampo));   
            escondeElemento(document.getElementById('divTitulo_' + idCampo));   
        	escondeElemento(document.getElementById('divOpcoes_' + idCampo));    
        }
    }

    function mostraQuestao(sigla) {
        var idCampo = mapQuestoesComSigla.get(sigla);
        if (idCampo != null && idCampo != undefined){
        	mostraElemento(document.getElementById('divHist_' + idCampo));   
            mostraElemento(document.getElementById('divHistGraf_' + idCampo));   
            mostraElemento(document.getElementById('divTitulo_' + idCampo));   
            mostraElemento(document.getElementById('divOpcoes_' + idCampo));  
        }
    }
    
    function escondeElemento(elem) {
        if (elem != null && elem != undefined) {
            if (elem.length) {
                for(var i = 0; i < elem.length; i++){
                	elem[i].style.display = 'none';
                }
            }else{
                elem.style.display = 'none';
            }
        }
    }

    function mostraElemento(elem) {
        if (elem != null && elem != undefined) {
            if (elem.length) {
                for(var i = 0; i < elem.length; i++){
                    elem[i].style.display = 'block';
                }
            }else{
                elem.style.display = 'block';
            }
        }
    }
    
    function associaOnclick(elem, func) {
        if (elem != null && elem != undefined) {
            if (elem.length) {
                for(var i = 0; i < elem.length; i++){
                    elem[i].onclick = func;
                }
            }else{
                elem.onclick = func;
            }
        }
    }

    function associaOnblur(elem, func) {
        if (elem != null && elem != undefined) {
            if (elem.length) {
                for(var i = 0; i < elem.length; i++){
                    elem[i].onblur = func;
                }
            }else{
                elem.onblur = func;
            }
        }
    }
    
	function retornaNomeCampo(idCampo,sequencial){
		var result = null;
		if (sequencial == '') {
			result = 'campoDyn_' + idCampo;
		}else{
			result = 'campoDyn_'+sequencial+'#'+idCampo;
		}
		return result;	   			   		   		
	}
	    		
	function calculaIMC(idCampo,sequencial){
		var elemento = document.formQuestionario[retornaNomeCampo(idCampo,sequencial)];
		if (elemento != null){
			if (elemento.length){
				mostraIMC(elemento[0], elemento[1], elemento[2], elemento[3]);
			}
		}
	}

    carregouIFrame = function() {    
    	HTMLUtils.removeEvent(document.getElementById("frameUploadAnexo"),"load", carregouIFrame);
		
		mostraUploadArquivosMultimidia();
		
    	POPUP_UPLOAD_MULTIMIDIA.hide();
    };
    
	submitFormAnexo = function(){
		addEvent(document.getElementById("frameUploadAnexo"),"load", carregouIFrame);
	    document.formAnexo.setAttribute("target","frameUploadAnexo");
	    document.formAnexo.setAttribute("method","post"); 
	    document.formAnexo.setAttribute("enctype","multipart/form-data"); 
	    document.formAnexo.setAttribute("encoding","multipart/form-data"); 
	    //submetendo 
	    document.formAnexo.submit();
	};	
	
	chamaTelaUploadArquivoMultimidia = function(idDiv, idQuestaoQuestParm){
		document.formArquivosMultimidia.idDIV.value = idDiv;
		document.formAnexo.idQuestaoQuest.value = idQuestaoQuestParm;
		POPUP_UPLOAD_MULTIMIDIA.show();
	};
	
	mostraUploadArquivosMultimidia = function(){
		document.formArquivosMultimidia.fireEvent('listarUploads');
	};

	apresentaListaItensGED = function(){
		window.setTimeout('mostraUploadArquivosMultimidia()', 500);
	}

	excluirRespostaTabela = function(idTabela, objImg){
        if (!confirm('Deseja realmente excluir a linha da tabela')) return;
        
        var indice = objImg.parentNode.parentNode.rowIndex;
        
        HTMLUtils.deleteRow('tblRespostasTabela_'+idTabela, indice);
        HTMLUtils.deleteRow(document.getElementById('tblRespostasTabela_'+idTabela).value, indice);
    };
	
    adicionarRespostaTabela = function(idTabela){
    	var nomeTabela = 'tblQuestoesTabela_'+idTabela; 
        var tblOrigem = document.getElementById(nomeTabela);
        if (tblOrigem == null) {
            alert(nomeTabela + " não existe.");
            return false;
        }
        
        var nomeTabela = 'tblRespostasTabela_'+idTabela; 
        var tblDestino = document.getElementById(nomeTabela);
        if (tblDestino == null) {
            alert(nomeTabela + " não existe.");
            return false;
        }
        
        var lastRow = tblDestino.rows.length;
        var linhaDestino = tblDestino.insertRow(lastRow); 
        var colunaDestino = linhaDestino.insertCell(0);
        colunaDestino.innerHTML = '<img src="${ctx}/produtos/citsaude/imagens/btnExcluirRegistro.gif" title="Clique aqui para excluir esta linha" border="0" onclick="excluirRespostaTabela('+idTabela+',this)"/>';

        var ultimoSequencial = parseInt(document.getElementById('ultimoSequencial_'+idTabela).value) + 1;
        document.getElementById('ultimoSequencial_'+idTabela).value = ultimoSequencial;       

        var cel = 1;
        for(var r = 0; r < tblOrigem.rows.length; r++){
            var row = tblOrigem.rows[r];
        	for(var c = 0; c < row.cells.length; c++){ 
                var col = row.cells[c];	
                if (col.innerHTML != null && col.innerHTML.indexOf('campoTbl_') != -1) { 
                    var s = col.innerHTML;
                    s = StringUtils.replaceAll(s, 'campoTbl_', 'campoDyn_'+ultimoSequencial+'#');
                    s = StringUtils.replaceAll(s, '#seq#', ultimoSequencial);
                    s = StringUtils.replaceAll(s, '#class#', 'class');
                	var colunaDestino = linhaDestino.insertCell(cel);
                	colunaDestino.innerHTML = s;
                	cel = cel + 1;
                }    
        	}	   
        }    
        for(var j = 0; j <  document.formQuestionario.length; j++){
            DEFINEALLPAGES_geraConfiguracao(document.formQuestionario.elements[j], document.formQuestionario.elements[j].className);
        }
    };

    limpaCamposCalculados = function(origem){
        for(var i = 0; i < document.getElementById('idQuestoesCalculadas').length; i++){
            var idCampo = document.getElementById('idQuestoesCalculadas').options[i].text;
            var nomeCampo = retornaNomeCampo(idCampo,'');
            if (document.getElementById(nomeCampo) != null) {
            	document.getElementById(nomeCampo).value = ''; 
            }    
        }
    };  
    
	function validarDataQuestionario(field){
		if (field.value != ""){
			if(DateTimeUtil.isValidDate(field.value) == false){
				alert("Data de análise inválida");
			 	field.value = '';
				return false;	
			}
		}
	};

    chamaTelaModelosTextuaisQuest = function(campo, tipoCampo){
        document.getElementById('listagemModelosTextuais' + campo).innerHTML = 'Aguarde... Carregando a lista...';
        document.getElementById('listagemModelosTextuais' + campo).style.display = 'block'; 
        document.formModeloTextual.campoSelecaoModeloTextual.value = campo;
        document.formModeloTextual.tipoCampo.value = tipoCampo;
        document.formModeloTextual.fireEvent('listModelos');
    };  
    
    associarModelo = function(campo, idModelo, tipoCampo){
        document.formModeloTextual.campoSelecaoModeloTextual.value = campo;
        document.formModeloTextual.idModeloTextual.value = idModelo;
        document.formModeloTextual.tipoCampo.value = tipoCampo;
    	document.formModeloTextual.fireEvent('copiaModeloParaTexto');
    };

    trataComplementoOpcao = function(campo, idQuestao, idOpcao){
        var objs = document.getElementsByTagName('div');
        if (objs != null && campo.type != 'checkbox'){
           for(var i = 0; i < objs.length; i++){
               var div = objs[i]; 
               var id = div.id;
               if (id.indexOf('divOpcao'+idQuestao+'_') >= 0) {
                   div.style.display = 'none';
               }
           }  
        }
        var divComplemento = null;
        var selecionado = false;
        if (campo.type == 'select-one') {
            selecionado = (campo.value == campo.options[campo.selectedIndex].value);
            divComplemento = document.getElementById('divOpcao'+idQuestao+'_'+campo.value);
        }    
        if (campo.type != 'select-one') {
        	divComplemento = document.getElementById('divOpcao'+idQuestao+'_'+idOpcao);
            selecionado = (campo.checked && campo.value == idOpcao);
        }    
        if (divComplemento != null) {
            if (selecionado) {
                divComplemento.style.display = 'block';
            }else{
                divComplemento.style.display = 'none';
            }                    
        }            
    };

	/* Funcao para validar faixa de valores */
	validaFaixaValores = function(obj, val1, val2, comFoco){
		if (obj.name.indexOf("campoDyn_")==-1){ //Nao valida campos ocultos, de controle.
			return true;
		}
		var v = 0;
		if (obj.value != '' && obj.value != ' ' && obj.value != undefined && obj.value != null){
			v = NumberUtil.toDouble(obj.value);
		}
		var v1 = NumberUtil.toDouble(val1);
		var v2 = NumberUtil.toDouble(val2);
		if (v < v1){
			alert('Valor fora da faixa permitida! \n\n Faixa Permitida: ' + NumberUtil.format(v1, 3, ",", ".") + ' a ' + NumberUtil.format(v2, 3, ",", "."));
			obj.value = '';
			if (comFoco){
				obj.focus();
			}
			return false;
		}
		if (v > v2){
			alert('Valor fora da faixa permitida! \n\n Faixa Permitida: ' + NumberUtil.format(v1, 3, ",", ".") + ' a ' + NumberUtil.format(v2, 3, ",", "."));
			obj.value = '';
			if (comFoco){
				obj.focus();
			}			
			return false;
		}
		return true;
	};   

	validacaoGeral = function(){
		<%
		String strValidaFaixaValoresGeral = (String)request.getAttribute("strValidaFaixaValoresGeral");
		if (strValidaFaixaValoresGeral == null){
			strValidaFaixaValoresGeral = "";
		}
		out.println(strValidaFaixaValoresGeral);
		%>
		return true;
	}; 

	chamaEditorImagens = function(idQuestaoParm, idImagemParm, caminhoImagemFundo, campoValue){
		document.formGaleriaImagens.idQuestaoParm.value = idQuestaoParm;
		document.formGaleriaImagens.idImagemParm.value = idImagemParm;		
		document.formGaleriaImagens.campoValue.value = campoValue;		
		document.formGaleriaImagens.funcaoRecebeConteudo.value = 'recebeConteudo';		
		document.formGaleriaImagens.frame.value = 'ifrImagem_' + idQuestaoParm;		
		document.formGaleriaImagens.imagemFundo.value = caminhoImagemFundo;	

		var campo = document.getElementById(document.formGaleriaImagens.campoValue.value);
			
		document.formGaleriaImagens.conteudoImagem.value = campo.value;	

		POPUP_GALERIA_IMAGENS.hide();
		var acao = document.formGaleriaImagens.action;
		//window.open('${ctx}/svg-edit-2.4/svg-editor.jspfuncaoRecebeConteudo=recebeConteudo&frame=ifrImagem_' + idQuestaoParm + '&imagemFundo=' + caminhoImagemFundo);
		document.formGaleriaImagens.action = '${ctx}/svg-edit-2.4/svg-editor.jsp';

		document.formGaleriaImagens.target = '_blank';
		document.formGaleriaImagens.submit();
		
		document.formGaleriaImagens.action = acao;
	};

	recebeConteudo = function (conteudo, conteudoBase64, frame){
	   document.getElementById(frame).src = conteudoBase64;
	   document.getElementById(frame).height="480";
	   document.getElementById(frame).width="640";

	   document.getElementById('campoDyn_' + document.formGaleriaImagens.idQuestaoParm.value).value = conteudo;
    };	

    selecionaCategoriaGaleriaImagem = function (){
		document.formGaleriaImagens.fireEvent('listaImagens');
	};

	chamaGaleriaImagens = function(idQuestaoParm, idImagemParm, campoValue){
		document.formGaleriaImagens.idQuestaoParm.value = idQuestaoParm;
		document.formGaleriaImagens.idImagemParm.value = idImagemParm;
		document.formGaleriaImagens.campoValue.value = campoValue;	

		var campo = document.getElementById(document.formGaleriaImagens.campoValue.value);
		if (campo.value != undefined && campo.value != null && campo.value != '' && campo.value != 'undefined' && campo.value != 'null'){
			chamaEditorImagens(idQuestaoParm, idImagemParm, null, campoValue);
		}else{
			document.formGaleriaImagens.fireEvent('load');
			window.scroll(0, 0);
			POPUP_GALERIA_IMAGENS.show();
		}
	};

	setImagem = function(strImg){
		document.formGaleriaImagens.caminhoImagemParm.value = strImg;
	};

	var hashMapAjaxFormsDinamicos = new HashMap(); 
    adicionarRespostaTabelaFormDinamico = function(nomeFormDinamico, iQtdeColunas, classe, funcaoCallBackAddItem){
        var nomeTabela = 'tbl_'+nomeFormDinamico; 
        var tblDestino = document.getElementById(nomeTabela);
        if (tblDestino == null) {
            alert(nomeTabela + " não existe.");
            return false;
        }

        var ajaxObj = hashMapAjaxFormsDinamicos.get(nomeFormDinamico);
        if (ajaxObj == null || ajaxObj == undefined){
        	ajaxObj = new AjaxAction();
        	hashMapAjaxFormsDinamicos.set(nomeFormDinamico, ajaxObj);
        }
        ajaxObj.fireEventToAjaxFormWithCallBack(document.formQuestionario, URL_INITIAL + classe, 'addItem', 
                function(){
					if (ajaxObj.req.readyState == 4){
						if (ajaxObj.req.status == 200){
							if (funcaoCallBackAddItem != null){
								funcaoCallBackAddItem(nomeFormDinamico, iQtdeColunas, classe, ajaxObj.req.responseText);
							}
						}	
					}				
				});
    };
    
    adicionarLinhaRespostaTabelaFormDinamico = function(nomeFormDinamico, iQtdeColunas, classe){
        var nomeTabela = 'tbl_'+nomeFormDinamico; 
        var tblDestino = document.getElementById(nomeTabela);
        if (tblDestino == null) {
            alert(nomeTabela + " não existe.");
            return false;
        }  
        var ultSeq = 0;
		eval(nomeFormDinamico + '_ULTIMO_SEQUENCIAL = ' + nomeFormDinamico + '_ULTIMO_SEQUENCIAL + 1');
		eval('ultSeq = ' + nomeFormDinamico + '_ULTIMO_SEQUENCIAL;');
		document.getElementById(nomeFormDinamico + '_ULTIMO_SEQUENCIAL_HIDDEN').value = ultSeq;
              
        var lastRow = tblDestino.rows.length;
        var linhaDestino = tblDestino.insertRow(lastRow); 
        var colunaDestino = linhaDestino.insertCell(0);
        colunaDestino.innerHTML = '<img src="' + URL_INITIAL + 'produtos/citsaude/imagens/btnExcluirRegistro.gif" title="Clique aqui para excluir esta linha" border="0" onclick="excluirRespostaTabelaFormDinamico(\''+nomeFormDinamico+'\',this)"/>';

        var cel = 1;
       	for(var c = 1; c <= iQtdeColunas; c++){
           var div = 'div' + nomeFormDinamico + 'Field_' + c;
           if (document.getElementById(div)){
	           if (document.getElementById(div).innerHTML != null) { 
	               	var s = document.getElementById(div).innerHTML;
	               	s = StringUtils.replaceAll(s, '#class#', 'class');
	               	s = StringUtils.replaceAll(s, 'CAMPOFORMDYN_', '');
	               	s = StringUtils.replaceAll(s, 'SUFIXO', ultSeq);
	           		var colunaDestino = linhaDestino.insertCell(cel);
	           		colunaDestino.innerHTML = s;
	           		cel = cel + 1;
	           }  
           }  
       	}	   
        for(var j = 0; j <  document.formQuestionario.length; j++){
            DEFINEALLPAGES_geraConfiguracao(document.formQuestionario.elements[j], document.formQuestionario.elements[j].className);
        }
    };	

    excluirRespostaTabelaFormDinamico = function(nomeFormDinamico, objImg){
        if (!confirm('Deseja realmente excluir a linha da tabela')) return;
        
        var indice = objImg.parentNode.parentNode.rowIndex;
        
        HTMLUtils.deleteRow('tbl_'+nomeFormDinamico, indice);
    };

    abreMaisMenosGrp = function(obj, divNameGRP){
        	<%-- if (obj.src == '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/produtos/citsaude/imagens/im_mais1.jpg'){
    	    obj.src = '${ctx}/produtos/citsaude/imagens/im_menos1.jpg';
    	    document.getElementById(divNameGRP).style.display='block';
    	}else{
    	
    	    obj.src = '${ctx}/imagens/im_mais1.jpg';
    	    document.getElementById(divNameGRP).style.display = 'none';
    	} --%>
    	if (document.getElementById(divNameGRP).style.display == 'none'){
    	    obj.src = '${ctx}/imagens/menos1.png';
    	    document.getElementById(divNameGRP).style.display='block';
    	}else{
    	    obj.src = '${ctx}/imagens/mais1.png';
    	    document.getElementById(divNameGRP).style.display='none';
    	}
    };

    var ajaxActionSubQuestionario = null;
    var divInfoSubQuestionario = null;
    function callBackQuestionarioInterno(){
		if (ajaxActionSubQuestionario.req.readyState == 4){
			if (ajaxActionSubQuestionario.req.status == 200){
				document.getElementById(divInfoSubQuestionario).innerHTML = ajaxActionSubQuestionario.req.responseText;
				document.getElementById(divInfoSubQuestionario).style.display = 'block';
			}
		}        
    }

//------------------------------------------ CALENDARIO ----------------------------------------------------
function setActiveStyleSheet(link, title) {
  var i, a, main;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
	  if (a.id == 'cssCalendario'){
		    if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
		      a.disabled = true;
		      if(a.getAttribute("title") == title) a.disabled = false;
		    }
	  }
  }
  if (oldLink) oldLink.style.fontWeight = 'normal';
  oldLink = link;
  //link.style.fontWeight = 'bold';
  return false;
}

function selected(cal, date) {
  cal.sel.value = date; // just update the date in the input field.
  if (cal.dateClicked && (cal.sel.id == "sel1" || cal.sel.id == "sel2"))
    cal.callCloseHandler();
  try{
	  cal.sel.focus();
	  eval('CHAMA_' + cal.sel.name + '_calculaDPP()');
  }catch (e) {
  }
}

function closeHandler(cal) {
  cal.hide();                        // hide the calendar
}

function QUESTIONARIO_showCalendar(id, format) {
  //alert("[DEBUG] inicio");
  var el = document.getElementById(id);
  //alert("[DEBUG] in 1");
  if (calendar != null) {
    //alert("[DEBUG] in 2");
    calendar.hide();                 // so we hide it first.
    //alert("[DEBUG] in 3");
  } else {
    //alert("[DEBUG] in 4");
    var cal = new Calendar(false, null, selected, closeHandler);
    //alert("[DEBUG] in 5");
    calendar = cal;                  // remember it in the global var
    //alert("[DEBUG] in 6");
    cal.setRange(1900, 2070);        // min/max year allowed.
    //alert("[DEBUG] in 7");
    cal.create();
    //alert("[DEBUG] in 8");
  }
  //alert("[DEBUG] in 9");
  calendar.setDateFormat(format);    // set the specified date format
  //alert("[DEBUG] in 10");
  calendar.parseDate(el.value);      // try to parse the text in field
  //alert("[DEBUG] in 11");
  calendar.sel = el;                 // inform it what input field we use
  //alert("[DEBUG] in 12");
  
  calendar.showAtElement(el, "Bl");        // show the calendar
  //alert("[DEBUG] fim");
  return false;
}
var MINUTE = 60 * 1000;
var HOUR = 60 * MINUTE;
var DAY = 24 * HOUR;
var WEEK = 7 * DAY;

function isDisabled(date) {
  //alert("[DEBUG] .1");
  var today = new Date();
  //alert("[DEBUG] .2");
  return (Math.abs(date.getTime() - today.getTime()) / DAY) > 10;
  //alert("[DEBUG] .3");
}

function flatSelected(cal, date) {
  //alert("[DEBUG] 1.");
  var el = document.getElementById("preview");
  //alert("[DEBUG] 2.");
  el.innerHTML = date;
}

function showFlatCalendar() {
  //alert("[DEBUG] 1");
  var parent = document.getElementById("display");
  //alert("[DEBUG] 2");

  var cal = new Calendar(false, null, flatSelected);
  //alert("[DEBUG] 3");
  cal.weekNumbers = false;
  //alert("[DEBUG] 4");
  cal.setDisabledHandler(isDisabled);
  //alert("[DEBUG] 5");
  cal.setDateFormat("DD, M d");
  //alert("[DEBUG] 6");
  cal.create(parent);
  //alert("[DEBUG] 7");
  cal.show();
  //alert("[DEBUG] 8");
}

setActiveStyleSheet(document.getElementById("defaultTheme"), "win2k-cold-1");
</script>


<div id="divData" style="display:none">
	<!-- the calendar will be inserted here -->
	<div id="display"></div>
	<div id="preview"></div>
</div> 
