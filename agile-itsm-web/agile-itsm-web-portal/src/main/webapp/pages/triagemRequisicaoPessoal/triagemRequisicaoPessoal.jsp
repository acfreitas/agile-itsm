<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String id = request.getParameter("id");
%>
<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
<link type="text/css" rel="stylesheet"
	href="../../novoLayout/common/include/css/template.css" />

<style type="text/css">
#modal_sugestaoCurriculos {
	width: 950px; /*!important;*/
	margin-left: -49%;
	top: 1% !important;
	margin-top: 0px !important;
}

#modal_sugestaoCurriculos .modal-body {
	max-height: 633px !important;
	overflow: auto !important;
}
/* 		#modal_listaNegra{
			width: 850px!important;
		} */
#modal_listaNegra .modal-body {
	max-height: 500px;
	overflow: auto !important;
}

#modal_historicoCandidato {
	width: 99% !important;
	margin-left: -49%;
	top: 1% !important;
	margin-top: 0px !important;
}

#modal_historicoCandidato .modal-body {
	max-height: 705px !important;
	overflow: auto !important;
}

#modal_curriculo {
	width: 99% !important;
	margin-left: -49%;
	top: 1% !important;
	margin-top: 0px !important;
}

#modal_curriculo .modal-body {
	max-height: 705px !important;
	overflow: auto !important;
}
</style>
<script>
        <%-- function limpar(){
	    	window.location = '<%=Constantes.getValue("SERVER_ADDRESS")%>${ctx}/requisicaoPessoal/requisicaoPessoal.load';
	    }  --%>

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

	    $(function() {
		        $("#POPUP_TRIAGEM_MANUAL").dialog({
		            autoOpen : false,
		            width : 1000,
		            height : 400,
		            modal : true
		        });
		});

	    function LOOKUP_CURRICULO_select(id, desc){
	          var curriculos = [];
	          var curriculoDto = new CIT_CurriculoDTO();
	          curriculoDto.idCurriculo = id;
	          curriculos.push(curriculoDto);
	          document.form.curriculos_serialize.value = ObjectUtils.serializeObjects(curriculos);
	          document.form.fireEvent("adicionaCurriculos");
	    }

        function gerarSelecaoCurriculo(row, obj){
	        obj.selecionado = 'N';
	        row.cells[0].innerHTML = "<input type='checkbox' name='chkSel_"+obj.idCurriculo+"' id='chkSel_"+obj.idCurriculo+"' onclick='marcarDesmarcar(this,"+row.rowIndex+",\"tblCurriculos\")' />";
        }
        function gerarColecao(row, obj){
	        obj.selecionado = 'N';
	        row.cells[0].innerHTML = "<input type='checkbox' name='chkSel_"+obj.idCurriculo+"' id='chkSel_"+obj.idCurriculo+"' onclick='marcarDesmarcar(this,"+row.rowIndex+",\"tblCurriculosManuais\")' />";
        }

	    addEvent(window, "load", load, false);
	    function load(){
	       document.form.afterLoad = function () {
	       parent.escondeJanelaAguarde();
	       }
	    }

	    function retornaIdCurriculosTriados() {
	        var curriculos = HTMLUtils.getObjectsByTableId('tblCurriculosTriados');
	        if (curriculos == null)
	            return '0';
	        var idsCurriculos = '';
	        for(i=0;i<curriculos.length;i++){
	            var obj = curriculos[i];
	            if (idsCurriculos == ''){
	            	idsCurriculos=obj.idCurriculo;
	            } else {
	            	idsCurriculos+=','+obj.idCurriculo;
	            }
	        }
	        if (idsCurriculos == ''){
	        	return '0';
	        }
	        return idsCurriculos;
	    }

	    function abrirSugestaoCurriculos() {
	    	var id = document.form.idSolicitacaoServico.value
	    	var URL_SISTEMA = '${ctx}/';
	    	document.formSugestaoCurriculos.idSolicitacaoServico.value = document.form.idSolicitacaoServico.value;
	    	document.form.idsCurTriados.value = retornaIdCurriculosTriados();
	    	//document.getElementById("framePesquisaCurriculo").src =  URL_SISTEMA+'pages/templatePesquisaCurriculo/templatePesquisaCurriculo.load?iframe=true&idSolicitacaoServico=' + id;
	    	document.getElementById("framePesquisaCurriculo").src =  URL_SISTEMA+'pages/pesquisaCurriculo/pesquisaCurriculo.load?iframe=true&idSolicitacaoServico=' + id;
	    	$("#modal_sugestaoCurriculos").modal("show");
	    }

	    function abrePopupHistoricoCandidato(idCurriculo) {
	    	  /* document.formSugestaoCurriculos.idSolicitacaoServico.value = document.form.idSolicitacaoServico.value; */
	    	  var URL_SISTEMA = '${ctx}/';
	    	  document.getElementById("frameHistoricoCandidato").src =  URL_SISTEMA+'pages/historicoCandidato/historicoCandidato.load?iframe=true&idCurriculo=' + idCurriculo;
	    	  $("#modal_historicoCandidato").modal("show");

	    }

	    function abrirTriagemManual() {
		    	document.form.pesquisa_chave.value = window.frames["framePesquisaCurriculo"].document.formSugestaoCurriculos.chave.value;
		    	document.form.pesquisa_formacao.value = window.frames["framePesquisaCurriculo"].document.formSugestaoCurriculos.formacao.value;
		    	document.form.pesquisa_certificacao.value = window.frames["framePesquisaCurriculo"].document.formSugestaoCurriculos.certificacao.value;
		    	document.form.pesquisa_cidade.value = window.frames["framePesquisaCurriculo"].document.formSugestaoCurriculos.cidade.value;
		    	document.form.pesquisa_idiomas.value = window.frames["framePesquisaCurriculo"].document.formSugestaoCurriculos.idiomas.value;
			    document.form.fireEvent('triagemManual');
		}

		function marcarDesmarcar(chk, indice, tbl) {
	        var obj = HTMLUtils.getObjectByTableIndex(tbl, indice);
	        if (chk.checked)
	            obj.selecionado = 'S';
	        else
	            obj.selecionado = 'N';
	    }


		$(function() {
	        $("#POPUP_CURRICULO").dialog({
	            autoOpen : false,
	            width : 1024,
	            height : 600,
	            modal : true
	        });
	    });

	    function marcarDesmarcarTodosCurriculos(chk,tabela) {
	        var curriculos = HTMLUtils.getObjectsByTableId(tabela);
	        if (curriculos == null)
	            return;
	        for(i=0;i<curriculos.length;i++){
	            var obj = curriculos[i];
	            if (chk.checked)
	                obj.selecionado = 'S';
	            else
	                obj.selecionado = 'N';
	            document.getElementById('chkSel_'+obj.idCurriculo).checked = chk.checked;
	        }
	    }

	    function gerarImgExclusaoCurriculo(row, obj) {

    		row.cells[3].innerHTML = '<a href="#" class="btn-action glyphicons nameplate btn-success titulo" title="'+i18n_message("triagemRequisicaoPessoal.mostrarCurriculo")+'" onclick="abrePopupCurriculo('+obj.idCurriculo+')"><i></i></a> ';
    		row.cells[3].innerHTML += '<a href="#" class="btn-action glyphicons history btn-success titulo" title="'+i18n_message("triagemRequisicaoPessoal.mostrarHistorico")+'" onclick="abrePopupHistoricoCandidato('+obj.idCurriculo+')"><i></i></a> ';
    		row.cells[3].innerHTML += '<a href="#" class="btn-action glyphicons thumbs_down btn-warning titulo" title="'+i18n_message("triagemRequisicaoPessoal.listaNegra")+'" onclick="abrirModalListanegra('+ row.rowIndex + ','+obj.idCurriculo+')"><i></i></a>  |  ';
    		row.cells[3].innerHTML += '<a href="#" class="btn-action glyphicons remove_2 btn-danger titulo" title="'+i18n_message("triagemRequisicaoPessoal.removerCurriculo")+'" onclick="removerLinhaTabela(this.parentNode.parentNode.rowIndex)"><i></i></a>';
             if (obj.caminhoFoto != "")
            	row.cells[0].innerHTML = '<div class="col_100"><img src="' + obj.caminhoFoto + '" border=0 width="128px" /></div>';
            else
            	row.cells[0].innerHTML = '<div class="col_100"><img src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/images/avatar.jpg" border=0  /></div>';
	    }

	    function incluirCurriculo(curriculoStr) {

	    	var curriculo = new CIT_CurriculoDTO();

	    	curriculo = ObjectUtils.deserializeObject(curriculoStr);

	    	if (isCurriculoJaInserido(curriculo.idCurriculo)) {

	    		return;
	    	}

            var triagem = new CIT_TriagemRequisicaoPessoalDTO();
            triagem.idCurriculo = curriculo.idCurriculo;
            triagem.strDetalhamento = '<div class="row-fluid">';
            triagem.strDetalhamento += '<h3>'+curriculo.nome+'</h3>';
            triagem.strDetalhamento += '<label><b>CPF:</b>&nbsp;' +curriculo.cpfFormatado+ '</label>';
            triagem.strDetalhamento += '<label><b>'+i18n_message("citcorpore.comum.dataNascimento")+':</b>&nbsp;' +curriculo.dataNascimentoStr+ '</label>';
            triagem.strDetalhamento += '<label><b>'+i18n_message("citcorpore.comum.estadoCivil")+':</b>&nbsp;' +curriculo.estadoCivilExtenso+ '</label>';
            triagem.strDetalhamento += '</div>';
            triagem.caminhoFoto = curriculo.caminhoFoto;

            HTMLUtils.addRow('tblCurriculosTriados', null, '', triagem,
                       ["","strDetalhamento","",""], ["idCurriculo"], '', [gerarImgExclusaoCurriculo], null, null, false);

            document.form.idsCurTriados.value = retornaIdCurriculosTriados();
	    }

	    function isCurriculoJaInserido(idCurriculo) {

	    	var curriculoRows = HTMLUtils.getObjectsByTableId('tblCurriculosTriados'),
	    	    i = 0;

	    	for (i = 0; i < curriculoRows.length; i++) {

	    		if (curriculoRows[i].idCurriculo == idCurriculo) {

	    			return true;
	    		}
	    	}

	    	return false;
	    }


	    function candidatoListaNegra(rowIndex){
	    	var itensListaNegra = document.form.listaNegra;
		    for(var i = 0; i < itensListaNegra.length; i++){
		    	var row = i+1;
				if(itensListaNegra[i].checked == true){

					   $('#HTMLUtils_tblCurriculosTriados_row_'+row).addClass('ui-state-highlight');
					}else{
						$('#HTMLUtils_tblCurriculosTriados_row_'+row).removeClass('ui-state-highlight');
						}
			    }

	    	/* $(".listaNegra").each(
	    	    	function(i) {
	    		   if (document.form.listaNegra[i].checked == true) {
	    			   $('#HTMLUtils_tblCurriculosTriados_row_'+rowIndex).addClass('ui-state-highlight');
	    		   }else{
				    	$('#HTMLUtils_tblCurriculosTriados_row_'+rowIndex).removeClass('ui-state-highlight');
	    		   }
	    		}); */

	    }


	    /**
	    	Recebe a coleção de templatePesquisaCurriculo.java
	    	Metodo: adicionarCurriculosPorColecao
	    	- deserializa e acessa a função "adicionaDadosTable" da pagina de templatePesquisaCurriculo.jsp
	    		passando a coleção por parametro.
	    **/

	    function incluirColecaoTable(curriculoStr) {
	    	var curriculo = new CIT_CurriculoDTO();
	    	curriculo = ObjectUtils.deserializeObject(curriculoStr);
	    	window.frames["framePesquisaCurriculo"].adicionaDadosTable(curriculo);
	    }

	    function limparDadostableCurriculo(){
	    	window.frames["framePesquisaCurriculo"].limparDadostableCurriculo();
		    }

	    function incluirCurriculos() {
	        var curriculos = HTMLUtils.getObjectsByTableId('tblCurriculos');
	        if (curriculos == null)
	            return;
	        var curriculosSelecionados = [];
	        for(i=0;i<curriculos.length;i++){
	            var obj = curriculos[i];
	            if (obj.selecionado == 'S') {
	                curriculosSelecionados.push(obj);
		            var triagem = new CIT_TriagemRequisicaoPessoalDTO();
		            triagem.idCurriculo = obj.idCurriculo;
		            triagem.strDetalhamento = '<table width="100%"><tr><td class="celulaGrid"><b>'+obj.nome+'</td></tr>';
		            triagem.strDetalhamento = triagem.strDetalhamento + '<tr><td class="celulaGrid">' +obj.estadoCivilExtenso+ '</td></tr>';
		            triagem.strDetalhamento = triagem.strDetalhamento + '</table>';

	                HTMLUtils.addRow('tblCurriculosTriados', null, '', triagem,
	                        ["","","","strDetalhamento"], null, '', [gerarImgExclusaoCurriculo], null, null, false);
	            }
	        }
	        if (curriculosSelecionados.length == 0) {
	            alert(i18n_message("citcorpore.comum.nenhumaSelecao"));
	            return;
	        }
	        $("#POPUP_SUGESTAO_CURRICULOS").dialog("close");
	    }

	    $(function() {
	        $("#addCurriculo").click(function() {
	            $("#POPUP_CURRICULO").dialog("open");
	        });
	    });

	    function validar() {
	    	return document.form.validate();
        }

	 	function abrePopupCurriculo(idCurriculo){
		  	window.open(URL_SISTEMA+'modalCurriculo/modalCurriculo.load?iframe=true&idCurriculo='+idCurriculo, "_blank");
		}

	    function getObjetoSerializado() {
            var obj = new CIT_RequisicaoPessoalDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var triagens = HTMLUtils.getObjectsByTableId('tblCurriculosTriados');
            obj.serializeTriagem = ObjectUtils.serializeObjects(triagens);
            return ObjectUtils.serializeObject(obj);
     	}

	    function removerLinhaTabela(indice) {
			 HTMLUtils.deleteRow('tblCurriculosTriados', indice);
	    }

	    function abrirModalListanegra(row, idCurriculo){
		    document.getElementById('frameListaNegra').src = URL_SISTEMA+'historicoAcaoCurriculo/historicoAcaoCurriculo.load?iframe=true&tela=triagemRequisicaoPessoal&rowIndexTriagem='+row+'&idCurriculoPesquisa='+idCurriculo;
		    $('#modal_listaNegra').modal('show');
		}

	    function atualizarGridCurriculoListaNegra(indice){
	    	HTMLUtils.deleteRow("tblCurriculosTriados",indice);
		}

	    function tooltip() {
			$(document).ready(function() {$('.titulo').tooltip()});
		}

	    function fecharModal(){
	    	$("#modal_listaNegra").modal('hide');
	    }

	    function configuraJustificativaRejeicao(){
			if(document.form.rejeitada.checked)
					document.getElementById('divJustificativaRejeicao').style.display = 'block';
				else
					document.getElementById('divJustificativaRejeicao').style.display = 'none';
	   }

</script>
</head>
<body>
	<div class="container-fluid fixed ">
		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->

		<%-- 	<div class="navbar main hidden-print">
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>

			</div> --%>


		<div id="wrapper">

			<!-- Inicio conteudo -->

			<div class="box-generic">
				<form name='form'
					action='${ctx}/pages/triagemRequisicaoPessoal/triagemRequisicaoPessoal'>
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
					<input type='hidden' name='serializeTriagem' id='serializeTriagem' />
					<input type='hidden' name='editar' id='editar' />
                    <input type='hidden' name='acao' id='acao' value='T' />
                    <input type='hidden' name='curriculos_serialize' id='curriculos_serialize' />
                    <input type='hidden' name='pesquisa_chave' id='pesquisa_chave' />
					<input type='hidden' name='pesquisa_funcao' id='pesquisa_funcao' />
                    <input type='hidden' name='pesquisa_formacao' id='pesquisa_formacao' />
					<input type='hidden' name='pesquisa_certificacao' id='pesquisa_certificacao' />
					<input type='hidden' name='pesquisa_cidade' id='pesquisa_cidade' />
					<input type='hidden' name='pesquisa_idiomas' id='pesquisa_idiomas' />
					<input type='hidden' name='qtdCandidatosAprovados' id='qtdCandidatosAprovados' />
					<input type='hidden' name='indiceCurriculoListaNegra' id='indiceCurriculoListaNegra' />
					<input type="hidden" id='idsCurTriados' name='idsCurTriados'>

					<div class="row-fluid">
						<div class='span4'>
							<label class='strong'><fmt:message key="rh.funcao" /></label>
							<select name='idFuncao' id='idFuncao'
								class="Valid[Required] Description[rh.funcao] span10"
								disabled="disabled"></select>
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.numeroVagas" /></label> <input type='text'
								name='vagas' maxlength="100" readonly="readonly" class='span6' />
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.vagaConfidencial" /></label> <select
								name='confidencial'
								class="Valid[Required] Description[requisicaoPessoal.vagaConfidencial]"
								disabled="disabled">
								<option value=" ">
									<fmt:message key='citcorpore.comum.selecione' />
								</option>
								<option value="S">
									<fmt:message key='citcorpore.comum.sim' />
								</option>
								<option value="N">
									<fmt:message key='citcorpore.comum.nao' />
								</option>
							</select>
						</div>
					</div>
					<div class="row-fluid">
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.tipoContratacao" /></label> <select
								name='tipoContratacao'
								class="Valid[Required] Description[requisicaoPessoal.tipoContratacao]"
								disabled="disabled"></select>
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.motivoContratacao" /></label> <select
								name='motivoContratacao'
								class="Valid[Required] Description[requisicaoPessoal.tipoContratacao]"
								disabled="disabled">
								<option value=''>
									<fmt:message key='citcorpore.comum.selecione' />
								</option>
								<option value='N'>
									<fmt:message key='rh.novoCargo' />
								</option>
								<option value='D'>
									<fmt:message key='analiseRequisicaoPessoal.demissaoPessoal' />
								</option>
								<option value='A'>
									<fmt:message key='rh.aumentoDemanda' />
								</option>
								<option value='R'>
									<fmt:message key='rh.requisicaoCliente' />
								</option>
							</select>
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.salario" /></label>
							<div class='row-fluid'>
								<div class='span6'>
									<input type="checkbox" name="salarioACombinar" value="S"
										disabled="disabled">
									<fmt:message key="requisicaoPessoal.salarioACombinar" />
								</div>
								<div class='span6'>
									<div id='divPainelSalario' style='display: block'>
										<div class="input-prepend input-append">
											<span class="add-on"><fmt:message
													key="citcorpore.comum.simboloMonetario" /></span> <input
												class="Format[Moeda] span12" name='salario' size='10'
												maxlength="100" readonly="readonly">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class='span4'>
							<label class='strong'><fmt:message key="unidade.pais" /></label>
							<select name='idPais' id="idPais"
								onchange="document.form.fireEvent('preencherComboUfs');"
								class="Description[unidade.pais]" disabled="disabled"></select>
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message key="localidade.uf" /></label>
							<select name='idUf' id="idUf"
								onchange="document.form.fireEvent('preencherComboCidade');"
								class="Description[uf]" disabled="disabled"></select>
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message
									key="localidade.cidade" /></label> <select id="idCidade"
								name='idCidade' class="Description[lookup.cidade]"
								disabled="disabled"></select>
						</div>
					</div>

					<div class="row-fluid">
						<div class='span4'>
							<label class='strong'><fmt:message key="Lotação" /></label> <select
								name='idLotacao'
								class="Valid[Required] Description[citcorporeRelatorio.comum.lotacao]"
								disabled="disabled"></select>
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.horarios" /></label> <select name='idJornada'
								id='idJornada' disabled="disabled"></select>
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.beneficios" /></label>
							<textarea name='beneficios' rows="4" class='span8'
								readonly="readonly"></textarea>
						</div>
					</div>
					<div class="row-fluid">
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.centroCusto" /></label> <select
								name='idCentroCusto'
								class="Valid[Required] Description[lookup.centroCusto]"
								disabled="disabled"></select>
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.projeto" /></label> <select name='idProjeto'
								class="Valid[Required] Description[lookup.projeto]"
								disabled="disabled"></select>
						</div>
						<div class='span4'>
							<label class='strong'><fmt:message
									key="requisicaoPessoal.folgas" /></label> <input type='text'
								name='folgas' size='10' maxlength="100" readonly="readonly" />
						</div>
					</div>
					<div style="margin: 0;" class="form-actions">
						<button class="btn btn-icon btn-primary glyphicons search"
							type="button" onclick='abrirSugestaoCurriculos();'>
							<i></i>
							<fmt:message key="curriculo.pesquisa" />
						</button>
					</div>
					<div class="widget">
						<div class="widget-head">
							<h4 class="heading">
								<fmt:message key="triagemRequisicaoPessoal.curriculosTriados" />
							</h4>
						</div>
						<!-- Tabela de resultados -->
						<div class="widget-body">

							<table id="tblCurriculosTriados" class="table table-condensed" style="overflow: auto">
								<tr>
									<th width="130px"><fmt:message key="rh.foto" /></th>
									<th><fmt:message key="produto.detalhes" /></th>
									<th width="250px"><fmt:message key="citcorpore.comum.informacao" /></th>
									<th><fmt:message key="plano.melhoria.acoes" /></th>
								</tr>
							</table>

						</div>
					</div>
					<div class='widget'>
						<div class='box-generic'>
							<div class="row-fluid">
								<div class='span12'>
		                    		<input id="preRequisitoEntrevistaGestor" name="preRequisitoEntrevistaGestor" class="checkbox" type="checkbox" value="S" name="s" style="cursor: pointer">
		                     		<fmt:message key="analiseRequisicaoPessoal.entrevistaRHPreRequisitoEntrevistaGestor"/>
		                		</div>
		                	</div>
	                	</div>
	                </div>
					<div class='widget'>
						<div class='box-generic'>
							<div class="row-fluid">
								<div class="span12" >
									<div>
										<input id="rejeitada" name="rejeitada" class="checkbox" type="checkbox" value="S" style="cursor: pointer" onclick="configuraJustificativaRejeicao()">
	                     				<fmt:message key="requisicaoPessoal.rejeitarRequisicao"/>
									</div>
									<div id='divJustificativaRejeicao' style='display:none;' class='span12'>
					                    <label><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
					                    <div>
					                        <textarea class="span11" id="justificativaRejeicao" name="justificativaRejeicao" cols='60' rows='3'></textarea>
					                    </div>
                                    </div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="modal hide fade in" id="modal_sugestaoCurriculos"
				data-width="98%"
				aria-hidden="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<form name='formSugestaoCurriculos'
						action='${ctx}/pages/triagemRequisicaoPessoal/triagemRequisicaoPessoal'>
						<input type='hidden' name='idSolicitacaoServico'
							id='idSolicitacaoSugestao' />
						<div>
							<iframe width="99%" height="600" id='framePesquisaCurriculo'
								name='framePesquisaCurriculo' src=""></iframe>
						</div>
					</form>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
				<div class="modal-footer">
					<div style="margin: 0;" class="form-actions">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message
								key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
				</div>
			</div>
			<div class="modal hide fade in" id="modal_listaNegra"
				aria-hidden="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3>
						<fmt:message key="rh.listaNegraCurriculo" />
					</h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<iframe id='frameListaNegra' src='' height="430" border="0"
						style="width: 99%;"></iframe>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
				<div class="modal-footer">
					<div style="margin: 0;" class="form-actions">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message
								key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
				</div>
			</div>

			<div class="modal hide fade in" id="modal_historicoCandidato"
				aria-hidden="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<form name='formHistoricoCandidato'
						action='${ctx}/pages/historicoCandidato/historicoCandidato'>
						<input type='hidden' name='idSolicitacaoServico'
							id='idSolicitacaoSugestao' />
						<div>
							<iframe width="99%" height="650" id='frameHistoricoCandidato'
								name='frameHistoricoCandidato' src=""></iframe>
						</div>
					</form>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
				<div class="modal-footer">
					<div style="margin: 0;" class="form-actions">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message
								key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
				</div>
			</div>

			<div class="modal hide fade in" id="modal_curriculo"
				aria-hidden="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<iframe id='curriculo' src='' width="99%" height="600" border="0"></iframe>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
				<div class="modal-footer">
					<div style="margin: 0;" class="form-actions">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message
								key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
				</div>
			</div>

		</div>
		<!--  Fim conteudo-->

		<%@include file="/novoLayout/common/include/libRodape.jsp"%>
		<script type="text/javascript"
			src="${ctx}/js/ValidacaoUtils.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/PopupManager.js"></script>
		<script type="text/javascript"
			src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
		<script type="text/javascript"
			src="${ctx}/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>
		<script type="text/javascript"
			src="${ctx}/cit/objects/CurriculoDTO.js"></script>

	</div>

</body>
</html>