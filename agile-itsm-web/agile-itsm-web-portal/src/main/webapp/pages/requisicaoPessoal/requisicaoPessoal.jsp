<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    <%
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        String id = request.getParameter("id");
    %>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
	    <script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
	    <%@include file="/novoLayout/common/include/libRodape.jsp" %>

    <script>
    function MascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal, e){
        var sep = 0;
        var key = '';
        var i = j = 0;
        var len = len2 = 0;
        var strCheck = '0123456789';
        var aux = aux2 = '';
        var whichCode = (window.Event) ? e.which : e.keyCode;
        if (whichCode == 13 || whichCode == 8) return true;
        key = String.fromCharCode(whichCode); // Valor para o código da Chave
        if (strCheck.indexOf(key) == -1) return false; // Chave inválida
        len = objTextBox.value.length;
        for(i = 0; i < len; i++)
            if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal)) break;
        aux = '';
        for(; i < len; i++)
            if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) aux += objTextBox.value.charAt(i);
        aux += key;
        len = aux.length;
        if (len == 0) objTextBox.value = '';
        if (len == 1) objTextBox.value = '0'+ SeparadorDecimal + '0' + aux;
        if (len == 2) objTextBox.value = '0'+ SeparadorDecimal + aux;
        if (len > 2) {
            aux2 = '';
            for (j = 0, i = len - 3; i >= 0; i--) {
                if (j == 3) {
                    aux2 += SeparadorMilesimo;
                    j = 0;
                }
                aux2 += aux.charAt(i);
                j++;
            }
            objTextBox.value = '';
            len2 = aux2.length;
            for (i = len2 - 1; i >= 0; i--)
            objTextBox.value += aux2.charAt(i);
            objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
        }
        return false;
    }

	    function limpar(){
	   		window.location = '${ctx}/requisicaoPessoal/requisicaoPessoal.load';
	   	}

	    var contSelecao = 0;
		function exibirSelecao(objeto) {
			contSelecao = 0;
			HTMLUtils.deleteAllRows('tblSelecao');
			var nome = objeto.toUpperCase().substring(0,1) + objeto.substring(1,objeto.length);
			document.formSelecao.objeto.value = nome;
			document.formSelecao.idFuncao.value = document.getElementById("idFuncao").value;
			document.formSelecao.action = '${ctx}/' + objeto + '/' + objeto;
	 		document.formSelecao.fireEvent('exibeSelecao');
			$("#modal_selecao").modal("show");
		}

		function incluirOpcaoSelecao(id, desc, detalhe) {
			contSelecao ++;
			var tbl = document.getElementById('tblSelecao');
			var ultimaLinha = tbl.rows.length;
			var linha = tbl.insertRow(ultimaLinha);

			var coluna = linha.insertCell(0);
			coluna.className = "celulaEsquerda";
		    coluna.innerHTML = '<input style="cursor: pointer" type="checkbox" name="id" id="id' + contSelecao + '" value="'+id+'">';

			coluna = linha.insertCell(1);
			coluna.className = "celulaEsquerda";
		    coluna.innerHTML = '<b>' +desc + '</b><input type="hidden" id="desc' + contSelecao + '" name="desc" value="' + desc + '" />';

		    coluna = linha.insertCell(2);
			coluna.className = "celulaEsquerda";
			coluna.innerHTML = detalhe +'<input type="hidden" id="detalhe' + contSelecao + '" name="detalhe" value="' + detalhe + '" />';
		}

		function atribuirSelecao() {
			if (document.formSelecao.id.length) {
				var tbl = document.getElementById('tblSelecao');
				var ultimaLinha = tbl.rows.length;

	            for(var i = 1; i < document.formSelecao.id.length; i++){
	            	if (document.formSelecao.id[i].checked) {
	            		var idSelecionado = document.getElementById('id'+i).value;
	            		var desc = document.getElementById('desc'+i).value;
						if (!validarLinhaSelecionadaPeloId(document.formSelecao.objeto.value, ultimaLinha, document.formSelecao.id[i].value, desc))
							return;
					}
				}
	            for(var i = 1; i < document.formSelecao.id.length; i++){
	            	if (document.formSelecao.id[i].checked) {
	            		var desc = document.getElementById('desc'+i).value;
	            		var detalhe = document.getElementById('detalhe'+i).value;
	            		var idSelecionado = document.getElementById('id'+i).value;
	            		adicionarLinhaSelecionada(document.formSelecao.objeto.value, document.formSelecao.id[i].value, desc, "N", detalhe, idSelecionado);
					}
				}
			}
			$("#modal_selecao").modal("hide");
		}

		function inicializaContLinha() {
			document.getElementById('contFormacaoAcademica').value = '0';
			document.getElementById('contCertificacao').value = '0';
			document.getElementById('contCurso').value = '0';
			document.getElementById('contExperienciaInformatica').value = '0';
			document.getElementById('contIdioma').value = '0';
			document.getElementById('contExperienciaAnterior').value = '0';
			document.getElementById('contConhecimento').value = '0';
			document.getElementById('contHabilidade').value = '0';
			document.getElementById('contAtitudeIndividual').value = '0';
		}

		function adicionarLinhaSelecionada(objeto, id, desc, obrigatorio, detalhe){
			var contLinha = parseInt(document.getElementById('cont'+objeto).value);
			var checked = "";
			if (obrigatorio == "S")
				checked = "checked='true'";
			contLinha ++;
			eval("document.getElementById('cont"+objeto+"').value = '"+contLinha+"'");
			var nomeTabela = 'tbl'+objeto;
			var tbl = document.getElementById(nomeTabela);
			tbl.style.display = 'block';
			var ultimaLinha = tbl.rows.length;
			var linha = tbl.insertRow(ultimaLinha);
			var coluna = linha.insertCell(0);
			/* coluna.className = "celulaEsquerda"; */
			if(obrigatorio != "S") {
			  coluna.innerHTML = '<img id="imgDel' + contLinha + '" style="cursor: pointer;" title="Clique para excluir" src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removerLinhaTabela(\''+nomeTabela+'\', this.parentNode.parentNode.rowIndex);">';
			} else {
				coluna.innerHTML = "&nbsp;";
			}
			coluna = linha.insertCell(1);
			/* coluna.className = "celulaEsquerda"; */
			coluna.innerHTML = desc + '<input type="hidden" id="id' + objeto + contLinha + '" name="id'+objeto+'" value="' + id + '" />';
			coluna = linha.insertCell(2);
			/* coluna.className = "celulaEsquerda"; */
			coluna.innerHTML = detalhe;
			coluna = linha.insertCell(3);
			/* coluna.className = "celulaCentralizada"; */
			if(obrigatorio != "S") {
				coluna.innerHTML = '<input style="cursor: pointer" type="checkbox" id="obrig' + objeto + contLinha + '" name="obrig'+objeto+'" value="S" ' + checked + ' >';
			}else{
				coluna.innerHTML = '<input style="cursor: pointer" type="checkbox" id="obrig' + objeto + contLinha + '" name="obrig'+objeto+'" value="S" ' + checked + ' disabled>';
			}
		}

		function validarLinhaSelecionada(objeto, ultimaLinha, id, desc){
			if (ultimaLinha > 1){
				var arrayId = eval('document.form.id'+objeto);
				for (var i = 1; i < arrayId.length; i++){
					if (arrayId[i].value == id){
						alert(desc+" - " + i18n_message("citcorpore.comum.registroJaCadastrado"));
						return false;
					}
				}
			}
			return true;
		}

		/**
		 * Desenvolvedor: David Rodrigues - Data: 20/06/2014 - Horário: 15:30 - ID Citsmart: 0
		 *
		 * Motivo/Comentário: Feito outro metodo para atender a validação atraves da Descrição (String),
		 *					  pois tera que ser feita uma mudança no fluxo de Requisição de Função para voltar a validação anterior.
		 *
		 **/
		function validarLinhaSelecionadaPeloNome(objeto, ultimaLinha, id, desc){

			if (ultimaLinha > 1){
				var arrayId = eval('document.form.id'+objeto);

				for (var i = 1; i < arrayId.length; i++){
					if (arrayId[i].value.toUpperCase() == desc.toUpperCase()){
						alert(desc+" - " + i18n_message("citcorpore.comum.registroJaCadastrado"));
						return false;
					}
				}
			}
			return true;
		}

		/*
			objeto: Referencia form selecionado Ex: FormacaoAcademica, Certificacao e etc

		*/

		function validarLinhaSelecionadaPeloId(objeto, ultimaLinha, id, desc){

			if (ultimaLinha > 1){
				var arrayId = eval('document.form.id'+objeto);

				for (var i = 1; i < arrayId.length; i++){
					if (arrayId[i].value.toUpperCase() == id.toUpperCase()){
						alert(desc+" - " + i18n_message("citcorpore.comum.registroJaCadastrado"));
						return false;
					}
				}
			}
			return true;
		}

		function removerLinhaTabela(idTabela, rowIndex) {
			if (confirm('Deseja realmente excluir')) {
				HTMLUtils.deleteRow(idTabela, rowIndex);
			}
		}


		/**
		 * Desenvolvedor: David Rodrigues - Data: 28/11/2013 - Horário: 15:30 - ID Citsmart: 0
		 *
		 * Motivo/Comentário: Tratamento de indice não existente (objeto excluido)
		 *
		 **/

	 	function serializaObjetos(objeto, tipo){
	 		var tabela = document.getElementById('tbl'+objeto);
	 		var lista = [];
			var arrayId = eval('document.form.id'+objeto);
			for (var i = 1; i < arrayId.length; i++){
	 			var id = arrayId[i].value;
	 			var chk = document.getElementById('obrig' + objeto + i);
	 			var obr = 'N';
	 			if(chk){
		 			if (chk.checked)
						obr = 'S';
	 			}
	 			var obj = eval('new '+tipo+'(' + id + ',"' + obr + '")');
	 			lista.push(obj);
	 		}
	 		var ser = ObjectUtils.serializeObjects(lista);
			document.getElementById('serialize'+objeto).value = ser;
	 	}

	 	function RequisicaoFormacaoAcademicaDTO(_id, _obrigatorio){
	 		this.idFormacaoAcademica = _id;
	 		this.obrigatorio = _obrigatorio;
	 	}

	 	function RequisicaoCertificacaoDTO(_id, _obrigatorio){
	 		this.idCertificacao = _id;
	 		this.obrigatorio = _obrigatorio;
	 	}

	 	function RequisicaoCursoDTO(_id, _obrigatorio){
	 		this.idCurso = _id;
	 		this.obrigatorio = _obrigatorio;
	 	}

	 	function RequisicaoExperienciaInformaticaDTO(_id, _obrigatorio){
	 		this.idExperienciaInformatica = _id;
	 		this.obrigatorio = _obrigatorio;
	 	}
	 	function RequisicaoIdiomaDTO(_id, _obrigatorio){
	 		this.idIdioma = _id;
	 		this.obrigatorio = _obrigatorio;
	 	}

	 	function RequisicaoExperienciaAnteriorDTO(_id, _obrigatorio){
	 		this.idConhecimento = _id;
	 		this.obrigatorio = _obrigatorio;
	 	}

	 	function RequisicaoConhecimentoDTO(_id, _obrigatorio){
	 		this.idConhecimento = _id;
	 		this.obrigatorio = _obrigatorio;
	 	}

	 	function RequisicaoHabilidadeDTO(_id, _obrigatorio){
	 		this.idHabilidade = _id;
	 		this.obrigatorio = _obrigatorio;
	 	}
	 	function RequisicaoAtitudeIndividualDTO(_id, _obrigatorio){
	 		this.idAtitudeIndividual = _id;
	 		this.obrigatorio = _obrigatorio;
	 	}

		   function configuraPainelAcesso(){
		       if (document.formDescricao.acessoAAlteracoes[0].checked) {
		           $('divPainelAcesso').style.display='block';
		       }else{
		           $('divPainelAcesso').style.display='none';
		       }
		}

		function configuraPerfilFuncao(perfil){
		       if (perfil == 1) {
		          /*  document.getElementById('divPerfilCargo').style.display = 'block';
		           document.getElementById('divDown').style.display = 'none';
		           document.getElementById('divUp').style.display = 'block'; */
		    		$("#collapse1").find(".widget-body").each(function() {
		    			$(this).addClass("in");
		    			$("#collapse1").attr('data-collapse-closed',false);
		    			$(this).css("height", "auto");
		    		});
		       }else{
		    	   if (perfil == 2) {
			           /* document.getElementById('divPerfilCargo').style.display = 'none';
			           document.getElementById('divDown').style.display = 'block';
			           document.getElementById('divUp').style.display = 'none'; */
		    		   $("#collapse1").find(".widget-body").each(function() {
		    				$(this).removeClass("in");
		    				$(this).css("height", 0);
		    				$("#collapse1").attr('data-collapse-closed',true)
		    			});
		    	   }
		    	   else {
		    		   /* document.getElementById('divPerfilCargo').style.display = 'none';
			           document.getElementById('divDown').style.display = 'none';
			           document.getElementById('divUp').style.display = 'none'; */
		    		   $("#collapse1").find(".widget-body").each(function() {
		    				$(this).removeClass("in");
		    				$(this).css("height", 0);
		    				$("#collapse1").attr('data-collapse-closed',true)
		    			});
		    	   }
		       }
		}

	   function configuraPainelSalario(chk){
	       if (chk.checked)
	           document.getElementById('divPainelSalario').style.display = 'none';
	       else
	       	document.getElementById('divPainelSalario').style.display = 'block';
		}

	    function desabilitarTela() {
	        var f = document.form;
	        for(i=0;i<f.length;i++){
	            var el =  f.elements[i];
	            if (el.type != 'hidden') {
	                if (el.disabled != null && el.disabled != undefined) {
	                    el.disabled = 'disabled';
	                }
	            }
	        }
	    }
	    addEvent(window, "load", load, false);
	    function load(){
	        document.form.afterLoad = function () {
	        	configuraPerfilFuncao(0);
	        	if (document.form.idSolicitacaoServico != '') {
	        		configuraPainelSalario(document.form.salarioACombinar);
	        		configuraPerfilFuncao(2);
	        	}
	            if (document.form.editar.value != '' && document.form.editar.value != 'S')
	                desabilitarTela();
	            parent.escondeJanelaAguarde();
	        }
	    }

	    function validar() {
	        return document.form.validate();
	    }

		function restoreFuncao() {
		   document.form.fireEvent("restoreFuncao");
		}

		function getObjetoSerializado() {
	        	serializaObjetos('FormacaoAcademica','RequisicaoFormacaoAcademicaDTO');
				serializaObjetos('Certificacao','RequisicaoCertificacaoDTO');
				serializaObjetos('Curso','RequisicaoCursoDTO');
				serializaObjetos('ExperienciaInformatica','RequisicaoExperienciaInformaticaDTO');
				serializaObjetos('Idioma','RequisicaoIdiomaDTO');
				serializaObjetos('Conhecimento','RequisicaoConhecimentoDTO');
				serializaObjetos('ExperienciaAnterior','RequisicaoExperienciaAnteriorDTO');
				serializaObjetos('Habilidade','RequisicaoHabilidadeDTO');
				serializaObjetos('AtitudeIndividual','RequisicaoAtitudeIndividualDTO');

	            var obj = new CIT_RequisicaoPessoalDTO();
	            HTMLUtils.setValuesObject(document.form, obj);
	    		obj.serializeFormacaoAcademica = document.form.serializeFormacaoAcademica.value;
				obj.serializeCertificacao = document.form.serializeCertificacao.value;
				obj.serializeCurso = document.form.serializeCurso.value;
				obj.serializeExperienciaInformatica = document.form.serializeExperienciaInformatica.value;
				obj.serializeIdioma = document.form.serializeIdioma.value;
				obj.serializeExperienciaAnterior = document.form.serializeExperienciaAnterior.value;
				obj.serializeConhecimento = document.form.serializeConhecimento.value;
				obj.serializeHabilidade = document.form.serializeHabilidade.value;
				obj.serializeAtitudeIndividual = document.form.serializeAtitudeIndividual.value;
	            return ObjectUtils.serializeObject(obj);
	     }

		function abilitarCampoSalario() {
	    	if (document.getElementById('salarioACombinar').checked == true) {
	    		document.getElementById('salario').value = '';
	    		document.getElementById('salario').disabled = true;
	    	}else{
	    		document.getElementById('salario').disabled = false;
			}
	    }

	</script>
</head>

<body>


	<div class="container-fluid fixed" '>
        <div class="wrapper">
            <div id="tabs-2" class="box-generic" style="overflow: hidden;">
                        <form name='form' action='${ctx}/pages/requisicaoPessoal/requisicaoPessoal'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
                                <input type='hidden' name='idSolicitante' id='idSolicitante' value="<%=request.getAttribute("idSolicitante") %>" />
                                <input type='hidden' name='editar' id='editar' />
                                <input type='hidden' name='acao' id='acao'/>

                                <input type='hidden' name='idFormacaoAcademica'/>
                                <input type='hidden' name='idCertificacao'/>
                                <input type='hidden' name='idCurso'/>
                                <input type='hidden' name='idExperienciaInformatica'/>
                                <input type='hidden' name='idExperienciaAnterior'/>
                                <input type='hidden' name='idIdioma'/>
                                <input type='hidden' name='idConhecimento'/>
                                <input type='hidden' name='idHabilidade'/>
                                <input type='hidden' name='idAtitudeIndividual'/>

                                <input type='hidden' name='contFormacaoAcademica' id='contFormacaoAcademica' value='0'/>
                                <input type='hidden' name='contCertificacao' id='contCertificacao' value='0'/>
                                <input type='hidden' name='contCurso' id='contCurso' value='0'/>
                                <input type='hidden' name='contExperienciaInformatica' id='contExperienciaInformatica' value='0'/>
                                <input type='hidden' name='contExperienciaAnterior' id='contExperienciaAnterior' value='0'/>
                                <input type='hidden' name='contIdioma' id='contIdioma' value='0'/>
                                <input type='hidden' name='contConhecimento' id='contConhecimento' value='0'/>
                                <input type='hidden' name='contHabilidade' id='contHabilidade' value='0'/>
                                <input type='hidden' name='contAtitudeIndividual' id='contAtitudeIndividual' value='0'/>

                                <input type='hidden' name='serializeFormacaoAcademica' id='serializeFormacaoAcademica'/>
                                <input type='hidden' name='serializeCertificacao' id='serializeCertificacao'/>
                                <input type='hidden' name='serializeCurso' id='serializeCurso'/>
                                <input type='hidden' name='serializeExperienciaInformatica' id='serializeExperienciaInformatica'/>
                                <input type='hidden' name='serializeIdioma' id='serializeIdioma'/>
                                <input type='hidden' name='serializeExperienciaAnterior' id='serializeExperienciaAnterior'/>
                                <input type='hidden' name='serializeConhecimento' id='serializeConhecimento'/>
                                <input type='hidden' name='serializeHabilidade' id='serializeHabilidade'/>
                                <input type='hidden' name='serializeAtitudeIndividual' id='serializeAtitudeIndividual'/>

							<div class="row-fluid">
								<div class='span4'>
									<label for='idFuncao' class='strong campoObrigatorio'><fmt:message key="rh.funcao" /></label>
									<select name='idFuncao' id='idFuncao' class="Valid[Required] Description[rh.funcao] span10"  onchange="document.form.fireEvent('restoreFuncao');"></select>
								</div>
								<div class='span4'>
									<label for='vagas' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.numeroVagas" /></label>
									<input type='text' name='vagas' class="Valid[Required] Description[requisicaoPessoal.numeroVagas] Format[Numero] span10"   maxlength="9"  class='span6'/>
								</div>
								<div class='span4'>
									<label for='confidencial' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.vagaConfidencial" /></label>
									<select name='confidencial' class="Valid[Required] Description[requisicaoPessoal.vagaConfidencial]" >
	                                   <option value=" "><fmt:message key="citcorpore.comum.selecione" /></option>
	                                   <option value="S"><fmt:message key="citcorpore.comum.sim"/></option>
	                                   <option value="N"><fmt:message key="citcorpore.comum.nao"/></option>
	                                </select>
								</div>
							</div>
						<div class="row-fluid">
							<div class='span4'>
								<label for='tipoContratacao' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.tipoContratacao" /></label>
								<select name='tipoContratacao' class="Valid[Required] Description[requisicaoPessoal.tipoContratacao]" ></select>
							</div>
							<div class='span4'>
								<label for='motivoContratacao' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.motivoContratacao" /></label>
								<select name='motivoContratacao' class="Valid[Required] Description[requisicaoPessoal.motivoContratacao]" >
                               	      <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
                               	      <option value='A'><fmt:message key="rh.aumentoQuadroPessoal" /></option>
                               	      <option value='D'><fmt:message key="rh.substituicaoPessoal" /></option>
                                </select>
							</div>
							<div class='span4'>
							<label class='strong'><fmt:message key="requisicaoPessoal.salario" /></label>
								<div class='row-fluid'>
									<div class='span6'>
										<input type="checkbox" onclick="abilitarCampoSalario();" name="salarioACombinar" value="S" >
		                                 <fmt:message key="requisicaoPessoal.salarioACombinar" />
	                                 </div>
	                                 <div class='span6'>
		                                 <div id='divPainelSalario' style='display:block'>
		                                      <div class="input-prepend input-append">
													<span id="spanSlario" class="add-on"><fmt:message key="citcorpore.comum.simboloMonetario" /></span>
													<input class="Format[Moeda] span10" id="salario" name='salario' size='10' maxlength="100"  onKeyPress="return(MascaraMoeda(this,'.',',',event))">
											  </div>
			                         	</div>
		                         	</div>
	                         	</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class='span4'>
								<label for='idPais' class='strong campoObrigatorio'><fmt:message key="unidade.pais" /></label>
								<select name='idPais' id="idPais"  onchange="document.form.fireEvent('preencherComboUfs');" class="Valid[Required] Description[unidade.pais]"  ></select>
							</div>
							<div class='span4'>
								<label for='idUf' class='strong campoObrigatorio'><fmt:message key="rh.UF" /></label>
								<select name='idUf' id="idUf" onchange="document.form.fireEvent('preencherComboCidade');" class="Valid[Required] Description[uf]"  ></select>
							</div>
							<div class='span4'>
								<label for='idCidade' class='strong campoObrigatorio'><fmt:message key="rh.cidade" /></label>
								<select id="idCidade" name='idCidade'  class="Valid[Required] Description[Cidade]" ></select>
							</div>
						</div>

						<div class="row-fluid">
							<!--
							<div class='span4'>
								<label for='idUnidade' class='strong campoObrigatorio'><fmt:message key="citcorporeRelatorio.comum.lotacao" /></label>
								<select name='idUnidade' class="Valid[Required] Description[citcorporeRelatorio.comum.lotacao]" ></select>
							</div> -->
							<div class='span4'>
								<label for='idLotacao' class='strong campoObrigatorio'><fmt:message key="citcorporeRelatorio.comum.lotacao" /></label>
								<select name='idLotacao' class="Valid[Required] Description[citcorporeRelatorio.comum.lotacao]" ></select>
							</div>
							<div class='span4'>
								<label for='idJornada' class='strong'><fmt:message key="citcorporeRelatorio.comum.horarioTrabalho" /></label>
								<select name='idJornada' id='idJornada'  ></select>
							</div>
							<div class='span4'>
								<label for='beneficios' class='strong'><fmt:message key="requisicaoPessoal.beneficios" /></label>
								<textarea name='beneficios' rows="4"  class='span8' maxlength="1000"></textarea>
							</div>
						</div>
						<div class="row-fluid">
							<div class='span4'>
								<label for='idCentroCusto' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.centroCusto" /></label>
								<select name='idCentroCusto' class="Valid[Required] Description[requisicaoPessoal.centroCusto]" ></select>
							</div>
							<div class='span4'>
								<label for='idProjeto' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.projeto" /></label>
								<select name='idProjeto' class="Valid[Required] Description[requisicaoPessoal.projeto]" ></select>
							</div>
						</div>

                                <div class=''>
                                <div class="widget row-fluid" data-toggle="collapse-widget" id='collapse1'>

									<!-- Widget heading -->
									<div class="widget-head">
										<h4 class="heading"><fmt:message key="requisicaoPessoal.perfilCargo"/></h4>
										<!-- <span class="collapse-toggle"></span> -->
									</div>
									<!-- // Widget heading END -->

									<div class="widget-body collapse in">
                                <div id='divPerfilCargo' style='display:block!important' class='span12'>
                                <div class="span12">
										<label for='atividades' class="campoObrigatorio"><fmt:message key="solicitacaoCargo.atividades"/></label>
										<div>
											<textarea rows="5" cols="122" class='span12 Valid[Required] Description[solicitacaoCargo.atividades]' name='atividades'></textarea>
										</div>
								</div>

								<div class="innerTB">
									<div class="span12">
										<h4 id="perfilProfissional" class=""><fmt:message key="solicitacaoCargo.perfilProfissional"/></h4>
									</div>
								</div>
								<div class="row-fluid">


											<div class="span6">
											<label class="campoObrigatorio strong"><fmt:message key="solicitacaoCargo.formacaoAcademica"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("formacaoAcademica")' style="cursor: pointer;" title="Clique para adicionar uma formação acadêmica"></label>

												<div  id="gridFormacaoAcademica">
													<table id="tblFormacaoAcademica" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none">
														<tr>
															<th style="width: 2%;font-size:10px; " ><fmt:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
														</tr>
													</table>
												</div>

										</div>

										<div class="span6">

											<label class=' strong'><fmt:message key="solicitacaoCargo.certificacoes"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("certificacao")' style="cursor: pointer;" title="Clique para adicionar uma certificação"></label>
											<div  id="gridCertificacao">
												<table id="tblCertificacao" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
															<th style="width: 2%;font-size:10px; " ><fmt:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
														</tr>
												</table>
											</div>

										</div>
								</div>
								<div class="row-fluid">
									 <div class='span6'>

										<label class=' strong'><fmt:message key="solicitacaoCargo.treinamentos"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("curso")' style="cursor: pointer;" title="Clique para adicionar um curso"></label>
											<div  id="gridCurso">
												<table id="tblCurso" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
															<th style="width: 2%;font-size:10px; " ><fmt:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
														</tr>
												</table>
											</div>

									  </div>
									  <div class='span6'>

												<label class=' strong'><fmt:message key="solicitacaoCargo.experienciaInformatica"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("experienciaInformatica")' style="cursor: pointer;" title="Clique para adicionar uma experência em informática"></label>
												<div  id="gridExperienciaInformatica">
													<table id="tblExperienciaInformatica" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
														<tr>
															<th style="width: 2%;font-size:10px; " ><fmt:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
														</tr>
													</table>
												</div>

									  </div>
								</div>
								<div class="row-fluid">
									  <div class='span6'>

										<label class=' strong'><fmt:message key="solicitacaoCargo.idiomas"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("idioma")' style="cursor: pointer;" title="Clique para adicionar um idioma"></label>
											<div  id="gridIdioma">
												<table id="tblIdioma" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
															<th style="width: 2%;font-size:10px; " ><fmt:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
														</tr>
												</table>
											</div>

									  </div>
									  <div class='span6'>

												<label class=' strong'><fmt:message key="solicitacaoCargo.experienciaAnterior"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("experienciaAnterior")' style="cursor: pointer;" title="Clique para adicionar uma experiência anterior"></label>
												<div  id="gridExperienciaAnterior">
													<table id="tblExperienciaAnterior" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
														<tr>
															<th style="width: 2%;font-size:10px; " ><fmt:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
														</tr>
													</table>
												</div>

									  </div>
								</div>
								 <div class='innerTB'>
									<div class="span12">
									 	<h4 id="perfilCompetencia" class="section"><fmt:message key="solicitacaoCargo.perfilCompetencia"/></h4>
									</div>
								</div>
								  <div class='row-fluid'>
									  <div class='span6'>

												<label class="strong"><fmt:message key="solicitacaoCargo.conhecimentos"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("conhecimento")' style="cursor: pointer;" title="Clique para adicionar um conhecimento"></label>
												<div  id="gridConhecimento">
													<table id="tblConhecimento" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
														<tr>
															<th style="width: 2%;font-size:10px; " ><fmt:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
														</tr>
													</table>
												</div>

									  </div>
									  <div class='span6'>

										<label class="strong"><fmt:message key="solicitacaoCargo.habilidades"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("habilidade")' style="cursor: pointer;" title="Clique para adicionar uma habilidade"></label>
											<div  id="gridHabilidade">
												<table id="tblHabilidade" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
															<th style="width: 2%;font-size:10px; " ><fmt:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
														</tr>
												</table>
											</div>

									  </div>
									</div>
								<div class="row-fluid">
								  <div class='span12'>

											<label class="strong campoObrigatorio"><fmt:message key="solicitacaoCargo.atitudes"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("atitudeIndividual")' style="cursor: pointer;" title="Clique para adicionar uma atitude individual"></label>
											<div  id="gridAtitudeIndividual">
												<table id="tblAtitudeIndividual" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
														<th style="width: 2%;font-size:10px; " ><fmt:message key="rh.acao"/></th>
														<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
														<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
														<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
													</tr>
												</table>
											</div>

								  	</div>
								</div>
							</div>
						</div>
					</div>
					</div>
							<div class="row-fluid">
								  <div class="span12">
								  <label for='observacoes' id="informacoesComplementares" class="section"><fmt:message key="solicitacaoCargo.complementos"/></label>
								  </div>
							</div>
							<div class="row-fluid">
								  <div class="span12">

											<div>
												<textarea rows="5" cols="122" id='observacoes' name='observacoes' maxlength="500" class='span12'></textarea>
											</div>

								</div>
							</div>
								</form>
							</div>
							</div>

                    </div>
            </div>
        </div>
       </div>
			<div class="modal hide fade in" id="modal_selecao" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
					 <form name='formSelecao' method="POST" action=''>
						<input type='hidden' name='id'/>
						<input type='hidden' name='sel'/>
						<input type='hidden' name='objeto' id='objetoSel'/>
						<input type='hidden' name='idFuncao' id='idFuncao' />
						<div id='divSelecao' style='height:180px;overflow: auto;'>
							<table id="tblSelecao" class="table">
								<tr>
									<th style="width: 1%;" ><fmt:message key="rh.acao"/></th>
									<th ><fmt:message key="rh.descricao"/></th>
									<th style="width: 40%;" ><fmt:message key="rh.detalhes"/></th>
								</tr>
							</table>
						</div>
		  			 </form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<div style="margin: 0;" class="form-actions">
							<input type='button' class='btn btn-primary' name='btnSelecionar' value='<fmt:message key="rh.selecionar" />' onclick='atribuirSelecao()'/>
				            <a href="#" class="btn "  data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
						</div>
					<!-- // Modal footer END -->
				</div>
			</div>

</body>

</html>
