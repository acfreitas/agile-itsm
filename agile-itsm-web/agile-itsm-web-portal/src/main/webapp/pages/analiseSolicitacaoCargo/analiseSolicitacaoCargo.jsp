<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    <%
        String id = request.getParameter("id");
    %>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
    <title><fmt:message key="citcorpore.comum.title"/></title>

		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
	    <script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/DescricaoCargoDTO.js"></script>
	    <%@include file="/novoLayout/common/include/libRodape.jsp" %>

    <style type="text/css">
        .destacado {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
            font-size: 14px;
        }
        .table {
            border-left:1px solid #ddd;
            width: 90%;
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

        .table1 {
        }

        .table1 th {
            border:1px solid #ddd;
            padding:4px 10px;
            background:#eee;
        }

        .table1 td {
        }

         div#main_container {
            margin: 0px 0px 0px 0px;
        }

        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;

            letter-spacing: -4px;
        }
    </style>

    <script>


	function configuraSituacao()
	{
		if(document.form.situacao[1].checked)
			document.getElementById('divSituacao').style.display = 'block';
		else
			document.getElementById('divSituacao').style.display = 'none';
	}

	$(function() {
            $("#POPUP_SELECAO").dialog({
                autoOpen : false,
                width : 350,
                height : 270,
                modal : true
            });
        });

        function limpar(){
    	  window.location = '${ctx}/analiseSolicitacaoCargo/analiseSolicitacaoCargo.load';
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
            document.getElementById('divAdicionarItem').style.display = 'none';
        }

        addEvent(window, "load", load, false);
        function load(){
            document.form.afterLoad = function () {
                configuraSituacao();
                parent.escondeJanelaAguarde();
            }
        }

        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
        	serializaObjetos('FormacaoAcademica','CargoFormacaoAcademicaDTO');
			serializaObjetos('Certificacao','CargoCertificacaoDTO');
			serializaObjetos('Curso','CargoCursoDTO');
			serializaObjetos('ExperienciaInformatica','CargoExperienciaInformaticaDTO');
			serializaObjetos('Idioma','CargoIdiomaDTO');
			serializaObjetos('Conhecimento','CargoConhecimentoDTO');
			serializaObjetos('ExperienciaAnterior','CargoExperienciaAnteriorDTO');
			serializaObjetos('Habilidade','CargoHabilidadeDTO');
			serializaObjetos('AtitudeIndividual','CargoAtitudeIndividualDTO');

            var obj = new CIT_DescricaoCargoDTO();
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


	function select(id){
		document.form.idDescricaoCargo.value = id;
		document.form.fireEvent("restore");
	}

	var contSelecao = 0;
	function exibirSelecao(objeto) {
		contSelecao = 0;
		HTMLUtils.deleteAllRows('tblSelecao');
		var nome = objeto.toUpperCase().substring(0,1) + objeto.substring(1,objeto.length);
		document.formSelecao.objeto.value = nome;
		document.formSelecao.action = '${ctx}/' + objeto + '/' + objeto;
 		document.formSelecao.fireEvent('exibeSelecao');
		$("#POPUP_SELECAO").dialog("open");
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
		coluna.innerHTML = detalhe.substring(0,11) +'<span style="cursor: pointer;" title = "header=[Detalhe Completo] body=['+ detalhe +']"> ... </span> <input type="hidden" id="detalhe' + contSelecao + '" name="detalhe" value="' + detalhe + '" />';
	}

	function atribuirSelecao() {
		if (document.formSelecao.id.length) {
			var tbl = document.getElementById('tblSelecao');
			var ultimaLinha = tbl.rows.length;
            for(var i = 1; i < document.formSelecao.id.length; i++){
            	if (document.formSelecao.id[i].checked) {
            		var desc = document.getElementById('desc'+i).value;
					if (!validarLinhaSelecionada(document.formSelecao.objeto.value, ultimaLinha, document.formSelecao.id[i].value, desc))
						return;
				}
			}
            for(var i = 1; i < document.formSelecao.id.length; i++){
            	if (document.formSelecao.id[i].checked) {
            		var desc = document.getElementById('desc'+i).value;
            		var detalhe = document.getElementById('detalhe'+i).value;
            		adicionarLinhaSelecionada(document.formSelecao.objeto.value, document.formSelecao.id[i].value, desc, "N", detalhe);
				}
			}
		}
		$("#POPUP_SELECAO").dialog("close");
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
		coluna.className = "celulaEsquerda";
		coluna.innerHTML = '<img id="imgDel' + contLinha + '" style="cursor: pointer;" title="Clique para excluir" src="${ctx}/imagens/delete.png" onclick="removerLinhaTabela(\''+nomeTabela+'\', this.parentNode.parentNode.rowIndex);">';
		coluna = linha.insertCell(1);
		coluna.className = "celulaEsquerda";
		coluna.innerHTML = desc + '<input type="hidden" id="id' + objeto + contLinha + '" name="id'+objeto+'" value="' + id + '" />';
		coluna = linha.insertCell(2);
		coluna.className = "celulaEsquerda";
		coluna.innerHTML = detalhe.substring(0,11) +'<span style="cursor: pointer;" title = "header=[Detalhe Completo] body=['+ detalhe +']"> ... </span>';
		coluna = linha.insertCell(3);
		coluna.className = "celulaCentralizada";
		coluna.innerHTML = '<input style="cursor: pointer" type="checkbox" id="obrig' + objeto + contLinha + '" name="obrig'+objeto+'" value="S" ' + checked + ' >';
	}

	function validarLinhaSelecionada(objeto, ultimaLinha, id, desc){
		if (ultimaLinha > 1){
			var arrayId = eval('document.form.id'+objeto);
			for (var i = 1; i < arrayId.length; i++){
				if (arrayId[i].value == id){
					alert(i18n_message("analiseRequisicaoPessoal.formacaoAcademica") + " "+ desc +" " + i18n_message("analiseRequisicaoPessoal.jaAdicionada"));
					return false;
				}
			}
		}
		return true;
	}

	function removerLinhaTabela(idTabela, rowIndex) {
		if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			HTMLUtils.deleteRow(idTabela, rowIndex);
		}
	}

 	function serializaObjetos(objeto, tipo){
 		var tabela = document.getElementById('tbl'+objeto);
 		var lista = [];
		var arrayId = eval('document.form.id'+objeto);
		for (var i = 1; i < arrayId.length; i++){
 			var id = arrayId[i].value;
 			var chk = document.getElementById('obrig' + objeto + i);
 			var obrig = 'N';
 			if (chk.checked)
				obrig = 'S';
 			var obj = eval('new '+tipo+'(' + id + ',"' + obrig + '")');
 			lista.push(obj);
 		}
 		var ser = ObjectUtils.serializeObjects(lista);
		document.getElementById('serialize'+objeto).value = ser;
 	}

 	function CargoFormacaoAcademicaDTO(_id, _obrigatorio){
 		this.idFormacaoAcademica = _id;
 		this.obrigatorio = _obrigatorio;
 	}

 	function CargoCertificacaoDTO(_id, _obrigatorio){
 		this.idCertificacao = _id;
 		this.obrigatorio = _obrigatorio;
 	}

 	function CargoCursoDTO(_id, _obrigatorio){
 		this.idCurso = _id;
 		this.obrigatorio = _obrigatorio;
 	}

 	function CargoExperienciaInformaticaDTO(_id, _obrigatorio){
 		this.idExperienciaInformatica = _id;
 		this.obrigatorio = _obrigatorio;
 	}
 	function CargoIdiomaDTO(_id, _obrigatorio){
 		this.idIdioma = _id;
 		this.obrigatorio = _obrigatorio;
 	}

 	function CargoExperienciaAnteriorDTO(_id, _obrigatorio){
 		this.idConhecimento = _id;
 		this.obrigatorio = _obrigatorio;
 	}

 	function CargoConhecimentoDTO(_id, _obrigatorio){
 		this.idConhecimento = _id;
 		this.obrigatorio = _obrigatorio;
 	}

 	function CargoHabilidadeDTO(_id, _obrigatorio){
 		this.idHabilidade = _id;
 		this.obrigatorio = _obrigatorio;
 	}
 	function CargoAtitudeIndividualDTO(_id, _obrigatorio){
 		this.idAtitudeIndividual = _id;
 		this.obrigatorio = _obrigatorio;
 	}
    </script>
</head>

<body>
		<div class="box grid_16 tabs">
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='${ctx}/pages/analiseSolicitacaoCargo/analiseSolicitacaoCargo'>
                                <input type='hidden' name='idDescricaoCargo'/>
                                <input type='hidden' name='idSolicitacaoServico'/>
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
                                <div class="columns clearfix">
                                    <div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="cargo.cargo"/></label>
											<div>
												<input type="text" onkeydown="FormatUtils.noNumbers(this)" onchange="FormatUtils.noNumbers(this)" name="nomeCargo" maxlength="80"/>

											</div>
									</fieldset>
								    </div>
								<div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="solicitacaoCargo.atividades"/></label>
											<div>
												<textarea rows="5" cols="122" name='atividades'></textarea>
											</div>
									</fieldset>
								</div>
								</div>



								<div class="col_100">
								<h2 id="tituloSituacao" class="section"><fmt:message key="Análise"/></h2>
								</div>
								<div class="col_100">
									<fieldset>
											<div>
												<label style="cursor: pointer"><input type='radio' style="cursor: pointer" name='situacao' value='A' class='Valid[Required] Description[citcorpore.comum.situacaoSolicitacao]' onclick='configuraSituacao()'/><fmt:message key="analiseSolicitacaoCargo.aprovada"/></label>
	                                            <label style="cursor: pointer"><input type='radio' style="cursor: pointer" name='situacao' value='X' class='Valid[Required] Description[citcorpore.comum.situacaoSolicitacao]' onclick='configuraSituacao()'/><fmt:message key="analiseSolicitacaoCargo.naoAprovada"/></label>
											</div>
											<div id='divSituacao' style='display:none;' class="col_100">
                                                <div class="col_50">
					                        <fieldset style="height: 90px;">
					                            <label><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
					                            <div>
					                                <select id='idJustificativaValidacao'  name='idJustificativaValidacao'></select>
					                            </div>
					                        </fieldset>
					                   </div>
					                   <div class="col_50">
					                       <fieldset style="height: 90px;">
					                           <label><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
					                           <div>
					                                <textarea id="complemJustificativaValidacao" name="complemJustificativaValidacao" cols='60' rows='2'></textarea>
					                           </div>
					                       </fieldset>
					                   </div>
                                            </div>
									</fieldset>
								</div>

								<h2 id="perfilProfissional" class="section"><fmt:message key="solicitacaoCargo.perfilProfissional"/></h2>
								<div class="col_100">
									<div class="col_50">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="solicitacaoCargo.formacaoAcademica"/><img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"  onclick='exibirSelecao("formacaoAcademica")' style="cursor: pointer;" title="Clique para adicionar uma formação acadêmica"></label>
											<div  id="gridFormacaoAcademica">
												<table class="table" id="tblFormacaoAcademica" class="table" style="display: none;">
													<tr>
														<th style="width: 1%;font-size:10px; "></th>
														<th style="font-size:10px;"><fmt:message key="citcorpore.comum.descricao"/></th>
														<th style="width: 40%;font-size:10px;"><fmt:message key="rh.detalhes"/></th>
														<th style="width: 10%;font-size:10px;"><fmt:message key="rh.obrigatoria"/></th>
													</tr>
												</table>
											</div>
									</fieldset>
									</div>
									<div class="col_50">
									<fieldset>
										<label ><fmt:message key="solicitacaoCargo.certificacoes"/><img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"  onclick='exibirSelecao("certificacao")' style="cursor: pointer;" title="Clique para adicionar uma certificação"></label>
										<div  id="gridCertificacao">
											<table class="table" id="tblCertificacao" class="table" style="display: none;">
												<tr>
													<th style="width: 1%;font-size:10px; "></th>
													<th style="font-size:10px;"><fmt:message key="citcorpore.comum.descricao"/></th>
													<th style="width: 40%;font-size:10px;"><fmt:message key="rh.detalhes"/></th>
													<th style="width: 10%;font-size:10px;"><fmt:message key="rh.obrigatoria"/></th>
												</tr>
											</table>
										</div>
									</fieldset>
									</div>
								</div>
								<div class="col_100">
								  <div class='col_50'>
								  <fieldset >
									<label><fmt:message key="solicitacaoCargo.cursos"/><img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"  onclick='exibirSelecao("curso")' style="cursor: pointer;" title="Clique para adicionar um curso"></label>
										<div  id="gridCurso">
											<table class="table" id="tblCurso" class="table" style="display: none;">
												<tr>
													<th style="width: 1%;font-size:10px; "></th>
													<th style="font-size:10px;"><fmt:message key="citcorpore.comum.descricao"/></th>
													<th style="width: 40%;font-size:10px;"><fmt:message key="rh.detalhes"/></th>
													<th style="width: 10%;font-size:10px;"><fmt:message key="rh.obrigatoria"/></th>
												</tr>
											</table>
										</div>
								    </fieldset>
								  </div>
								  <div class='col_50'>
									  <fieldset >
											<label><fmt:message key="solicitacaoCargo.experienciaInformatica"/><img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"  onclick='exibirSelecao("experienciaInformatica")' style="cursor: pointer;" title="Clique para adicionar uma experência em informática"></label>
											<div  id="gridExperienciaInformatica">
												<table class="table" id="tblExperienciaInformatica" class="table" style="display: none;">
													<tr>
														<th style="width: 1%;font-size:10px; "></th>
														<th style="font-size:10px;"><fmt:message key="citcorpore.comum.descricao"/></th>
														<th style="width: 40%;font-size:10px;"><fmt:message key="rh.detalhes"/></th>
														<th style="width: 10%;font-size:10px;"><fmt:message key="rh.obrigatoria"/></th>
													</tr>
												</table>
											</div>
									    </fieldset>
								  </div>
								</div>
								<div class="col_100">
								  <div class='col_50'>
								  <fieldset >
									<label><fmt:message key="solicitacaoCargo.idiomas"/><img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"  onclick='exibirSelecao("idioma")' style="cursor: pointer;" title="Clique para adicionar um idioma"></label>
										<div  id="gridIdioma">
											<table class="table" id="tblIdioma" class="table" style="display: none;">
												<tr>
													<th style="width: 1%;font-size:10px; "></th>
													<th style="font-size:10px;"><fmt:message key="citcorpore.comum.descricao"/></th>
													<th style="width: 40%;font-size:10px;"><fmt:message key="rh.detalhes"/></th>
													<th style="width: 10%;font-size:10px;"><fmt:message key="rh.obrigatoria"/></th>
												</tr>
											</table>
										</div>
								    </fieldset>
								  </div>
								  <div class='col_50'>
									  <fieldset >
											<label><fmt:message key="solicitacaoCargo.experienciaAnterior"/><img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"  onclick='exibirSelecao("experienciaAnterior")' style="cursor: pointer;" title="Clique para adicionar uma experiência anterior"></label>
											<div  id="gridExperienciaAnterior">
												<table class="table" id="tblExperienciaAnterior" class="table" style="display: none;">
													<tr>
														<th style="width: 1%;font-size:10px; "></th>
														<th style="font-size:10px;"><fmt:message key="citcorpore.comum.descricao"/></th>
														<th style="width: 40%;font-size:10px;"><fmt:message key="rh.detalhes"/></th>
														<th style="width: 10%;font-size:10px;"><fmt:message key="rh.obrigatoria"/></th>
													</tr>
												</table>
											</div>
									    </fieldset>
								  </div>
								</div>
								<div class="col_100">
								  <h2 id="perfilCompetencia" class="section"><fmt:message key="solicitacaoCargo.perfilCompetencia"/></h2>
								  </div>
								<div class="col_100">
								  <div class='col_50'>
									  <fieldset >
											<label class="campoObrigatorio"><fmt:message key="solicitacaoCargo.conhecimentos"/><img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"  onclick='exibirSelecao("conhecimento")' style="cursor: pointer;" title="Clique para adicionar um conhecimento"></label>
											<div  id="gridConhecimento">
												<table class="table" id="tblConhecimento" class="table" style="display: none;">
													<tr>
														<th style="width: 1%;font-size:10px; "></th>
														<th style="font-size:10px;"><fmt:message key="citcorpore.comum.descricao"/></th>
														<th style="width: 40%;font-size:10px;"><fmt:message key="rh.detalhes"/></th>
														<th style="width: 10%;font-size:10px;"><fmt:message key="rh.obrigatoria"/></th>
													</tr>
												</table>
											</div>
									    </fieldset>
								  </div>
								  <div class='col_50'>
								  <fieldset >
									<label class="campoObrigatorio"><fmt:message key="solicitacaoCargo.habilidades"/><img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"  onclick='exibirSelecao("habilidade")' style="cursor: pointer;" title="Clique para adicionar uma habilidade"></label>
										<div  id="gridHabilidade">
											<table class="table" id="tblHabilidade" class="table" style="display: none;">
												<tr>
													<th style="width: 1%;font-size:10px; "></th>
													<th style="font-size:10px;"><fmt:message key="citcorpore.comum.descricao"/></th>
													<th style="width: 40%;font-size:10px;"><fmt:message key="rh.detalhes"/></th>
													<th style="width: 10%;font-size:10px;"><fmt:message key="rh.obrigatoria"/></th>
												</tr>
											</table>
										</div>
								    </fieldset>
								  </div>
								</div>
								<div class="col_100">
									<div class='col_50'>
									  <fieldset >
											<label class="campoObrigatorio"><fmt:message key="solicitacaoCargo.atitudes"/><img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"  onclick='exibirSelecao("atitudeIndividual")' style="cursor: pointer;" title="Clique para adicionar uma atitude individual"></label>
											<div  id="gridAtitudeIndividual">
												<table class="table" id="tblAtitudeIndividual" class="table" style="display: none;">
													<tr>
														<th style="width: 1%;font-size:10px; "></th>
														<th style="font-size:10px;"><fmt:message key="citcorpore.comum.descricao"/></th>
														<th style="width: 40%;font-size:10px;"><fmt:message key="rh.detalhes"/></th>
														<th style="width: 10%;font-size:10px;"><fmt:message key="rh.obrigatoria"/></th>
													</tr>
												</table>
											</div>
									    </fieldset>
								  </div>
								 </div>
								  <div class="col_100">
								  <h2 id="complementos" class="section"><fmt:message key="solicitacaoCargo.complementos"/></h2>
								  </div>

								  <div class="col_100">
									<fieldset>
											<div>
												<textarea rows="5" cols="122" name='observacoes'></textarea>
											</div>
									</fieldset>
								</div>

                        </form>
                    </div>
            </div>
        </div>
<div id="POPUP_SELECAO" title=<fmt:message key="rh.selecao"/> style="overflow: hidden">
   <form name='formSelecao' method="POST" action=''>
		<input type='hidden' name='id'/>
		<input type='hidden' name='sel'/>
		<input type='hidden' name='objeto' id='objetoSel'/>
		<div id='divSelecao' style='height:180px;width:340px;overflow: auto;'>
			<table class="table" id="tblSelecao">
				<tr>
					<th style="width: 1%;">&nbsp;</th>
					<th style="width: 50%;"><fmt:message key="citcorpore.comum.descricao"/></th>
					<th style="width: 49%;"><fmt:message key="rh.detalhes"/></th>
				</tr>
			</table>
		</div>
     	<table cellpadding="5" cellspacing="0">
          	<tr>
           		<td>
           		     &nbsp;
               		<input type='button' class="btn btn-primary" name='btnSelecionar' value='Selecionar' onclick='atribuirSelecao()'/>
               		<input type='button' class="btn " name='btnFechar' value='Fechar' onclick='$("#POPUP_SELECAO").dialog("close");'/>
           		</td>
          	</tr>
      	</table>
   </form>
</div>
</div>
</body>

</html>
