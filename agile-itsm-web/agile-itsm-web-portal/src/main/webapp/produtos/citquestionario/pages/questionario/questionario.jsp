<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%
    response.setCharacterEncoding("ISO-8859-1");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/include/header.jsp"%>

    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

    <%@include file="/include/cssComuns/cssComuns.jsp" %>

<link rel="stylesheet" type="text/css" href="${ctx}/produtos/citquestionario/pages/questionario/css/questionario.css">

<!-- FCKEditor -->
<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
<script type="text/javascript">

</script>
<!-- /FCKEditor -->

<%--     <%@include file="/produtos/citquestionario/include/menu/menuConfig.jsp" %> --%>

    <script type="text/javascript" src="${ctx}/cit/objects/GrupoQuestionarioDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/QuestaoQuestionarioDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/OpcaoRespostaQuestionarioDTO.js"></script>

<%--     <%@include file="/produtos/citquestionario/include/cssComuns/cssComuns.jsp" %> --%>

</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>


<!-- Area de JavaScripts -->
<script>
    
</script>
	<%if(request.getParameter("iframe")!=null && request.getParameter("iframe").equals(true)){%>
	<div id="divLogo" style="overflow: hidden!important;">
			<table cellpadding='0' cellspacing='0'>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<img border="0" src="${ctx}/imagens/logo/logo.png" />
					</td>
				</tr>

			</table>

		</div>
	<%}%>
<div>
    <form name='formAuxiliar'>
		<fieldset style="width: 100">
			<legend><b><fmt:message key="questionario.cadastroQuestionario" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></legend>
				<div class='row-fluid' id='btnVoltar'>
					<a class='btnQuest' href='${ctx}/pages/index/index.load'><fmt:message key="questionario.voltar" /></a>
				</div>
	        <table cellpadding="0" cellspacing="0" >
				<tr>
					<td style="width: 180px">
						<span id='spanTitulo'></span>&nbsp;
					</td>
					<td>
						<%
						String enderecoServidor = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS");
						if (enderecoServidor == null || enderecoServidor.trim().equalsIgnoreCase("")){
							enderecoServidor = "";
						}
						if (enderecoServidor.equalsIgnoreCase("")){
							enderecoServidor = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS");
						}
						%>
						<div>
							&nbsp;
						</div>
							<a type='button' name='btnExportarQuest' class='btnQuest'  onclick='gravarQuestionario()'><fmt:message key="citcorpore.comum.gravar" /></a>&nbsp;
							<a type='button' name='btnExportarQuest' class='btnQuest'  onclick='listagemQuestionario()'><fmt:message key="questionario.abrir" /></a>&nbsp;
							<a type='button' name='btnExportarQuest' class='btnQuest'  onclick='novoQuestionario()'><fmt:message key="questionario.novo" /></a>&nbsp;
					</td>
				</tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
	            <tr>
	                <td style='width: 180px' class="campoObrigatorioQuestionario">
	                <fmt:message key="questionario.nomeQuestionario" />:
	                </td>
	                <td>
	                    <input type='text' name='nomeQuestionario' id='nomeQuestionario' size="50" maxlength="50"/>
	                </td>
	            </tr>
	            <tr>
                    <td class="campoObrigatorioQuestionario">
                        <fmt:message key="questionario.categoriaQuestionario" />:
                    </td>
                    <td>
                        <select name='idCategoriaQuestionarioAux' id='idCategoriaQuestionarioAux' onclick='selecionaCategoriaQuest(this)'>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                    <fmt:message key="questionario.javascriptQuestionario" />:
                    </td>
                    <td colspan='3'>
                        <textarea rows="8" cols="80" name="javaScript" id="javaScriptAux"></textarea>
                    </td>
	            </tr>
	        </table>
		</fieldset>
    </form>
</div>

<table width="100%">
    <tr>
        <td width="100%"><br/>
            <!-- ## AREA DA APLICACAO ## -->
            <form name='form' id='form' action='${ctx}/pages/questionario/questionario'>
                <input type='hidden' name='acao'/>
                <input type='hidden' name='nomeQuestionario'/>
                <input type='hidden' name='idQuestionario' id='idQuestionario'/>
                <input type='hidden' name='idCategoriaQuestionario' id='idCategoriaQuestionario' value=''/>
                <input type='hidden' name='idQuestionarioOrigem' id='idQuestionarioOrigem'/>
                <input type='hidden' name='ativo' id='ativo'/>
                <input type='hidden' name='colQuestionariosSerialize'/>
                <input type='hidden' name='reload' value='false'/>
                <input type='hidden' name='idControleQuestao' id='idControleQuestao' />
       			<input type='hidden' name='idIndex' id='idIndex' />
				<input type='hidden' name='idIndexGrupo' />
				<input type='hidden' name='idControleQuestaoGrupo' id='idControleQuestaoGrupo' />

                <input type='hidden' name='tabelaOrdenar'/>
                <input type='hidden' name='tabelaExcluir'/>
                <input type='hidden' name='javaScript'/>

                <div class='row-fluid'>
                            <button type='button' name='btnExportarQuest' class='btnQuest span2'  onclick='mostrarAdicionarNovoGrupo()'><fmt:message key="questionario.adicionarNovoGrupo" /></button>
                            <button type='button' id='btnCapGrupoQuest' name='btnExportarQuest' class='btnQuest span2'  onclick='document.formCopiarGrupo.fireEvent("listarQuestionarios"); document.formCopiarGrupo.idQuestionarioCopiar.value = document.form.idQuestionario.value; abriPopUpGrupoCopiar()'><fmt:message key="questionario.copiarOutroQuestionario" /></button>
                            <button type='button' id='btnOrdenarGrupos' name='btnExportarQuest' class='btnQuest span1'  onclick='ordenarGrupos()'><fmt:message key="questionario.ordenarGrupos" /></button>
                            <button type='button' id='btnExcluirGrupo' name='btnExportarQuest' class='btnQuest span1'  onclick='excluirGrupo()'><fmt:message key="questionario.excluirGrupos" /></button>
                            <button type='button' id='btnVisualiza' name='btnExportarQuest' class='btnQuest span2'  onclick='visualizarQuestionario()'><fmt:message key="questionario.visualizarQuestionario" /></button>
                            <button type='button' name='btnExportarQuest' class='btnQuest span2'  onclick='exportarQuestionario()'><fmt:message key="questionario.exportarQuestionario" /></button>
                            <button type='button' name='btnExportarQuest' class='btnQuest span2'  onclick='abrirPopUPImportar()'><fmt:message key="questionario.importarQuestionario" /></button>
                </div>
                <table id='tblConfigQuestionario' class="tableLess" cellpadding="0" cellspacing="0" width="100%" >
                     <tr>
                        <td class="linhaSubtituloGrid" width="15%">
                        <fmt:message key="questionario.acoes" />
                        </td>
                        <td class="linhaSubtituloGrid">
                        <fmt:message key="questionario.conteudoQuestionario" />
                        </td>
                     </tr>
                </table>

            </form>
            <!-- ## FIM - AREA DA APLICACAO ## -->
        </td>
    </tr>
</table>
<script type="text/javascript">
	var ctx = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/produtos/citquestionario/pages/questionario/js/questionario.js"></script>

<!-- SCRIPT DE CARGA DOS DADOS DE RESTORE, SE HOUVER -->
        <script>
        idControleTabela = 0;
        <c:forEach var="item" items="${grupos}">
            addicionarGrupoFunction('<c:out value="${item.nomeGrupoQuestionario}"/>', <c:out value="${item.idGrupoQuestionario}"/>);

            document.getElementById('idControleQuestao').value = '' + (idControleTabela - 1);

            <c:forEach var="itemQuestao" items="${item.colQuestoes}">
                adicionarQuestaoFunction('${itemQuestao.tipo}',
                                            '${itemQuestao.idQuestaoOrigem}',
                                            '${itemQuestao.tituloQuestaoQuestionarioNoEnter}',
                                            '${itemQuestao.tipoQuestao}',
                                            '${itemQuestao.unidade}',
                                            '${itemQuestao.valoresReferencia}',
                                            '${itemQuestao.obrigatoria}',
                                            '${itemQuestao.ponderada}',
                                            '${itemQuestao.calculada}',
                                            '${itemQuestao.infoResposta}',
                                            '${itemQuestao.textoInicialNoEnter}',
                                            '${itemQuestao.tamanho}',
                                            '${itemQuestao.decimais}',
                                            '${itemQuestao.qtdeLinhas}',
                                            '${itemQuestao.qtdeColunas}',
                                            '${itemQuestao.cabecalhoLinhas}',
                                            '${itemQuestao.cabecalhoColunas}',
                                            '${itemQuestao.nomeListagem}',
                                            '${itemQuestao.serializeOpcoesResposta}',
                                            '${itemQuestao.serializeQuestoesAgrupadas}',
                                            '${itemQuestao.serializeCabecalhosLinha}',
                                            '${itemQuestao.serializeCabecalhosColuna}',
                                            '${itemQuestao.ultimoValor}',
                                            '${itemQuestao.idSubQuestionario}',
                                            '${itemQuestao.abaResultSubForm}',
                                            '${itemQuestao.sigla}',
                                            '${itemQuestao.imprime}',
                                            '${itemQuestao.editavel}',
                                            NumberUtil.format(NumberUtil.toDouble('${itemQuestao.valorPermitido1Str}'), 3, ",", "."),
                                            NumberUtil.format(NumberUtil.toDouble('${itemQuestao.valorPermitido2Str}'), 3, ",", "."),
                                            '${itemQuestao.idImagem}',
                                            '${itemQuestao.valorDefaultNoEnter}'
                                            );
            </c:forEach>
        </c:forEach>
       </script>
</body>
<div id="POPUP_IMPORTAR" title='<fmt:message key="questionario.selecionarArquivo" />' >
	<div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formImportar' method='post' ENCTYPE="multipart/form-data"  action='${ctx}/pages/questionario/questionario.load'/>
						<input type='hidden' name='acao' id='acaoImportar' value='<fmt:message key="dataManager.import" />'/>
				        <table width="100%">
				            <tr>
				                <td>

				                  <fmt:message key="questionario.arquivoImportar" />:*
				                </td>
				                <td>
				                   <input type='file' name='fileImportar' size="50" value='<fmt:message key="questionario.selecionarArquivo" />'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' value='OK' onclick='importarQuestionario()' />
				                </td>
				            </tr>
				        </table>
				    </form>
    			</div>
			</div>
		</div>
	</div>
</div>

<div  id="POPUP_GRUPO" title='<fmt:message key="questionario.configuracaoQuestionario" />' >
    <div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formGrupo' action='${ctx}/pages/questionario/questionario'/>
				        <input type='hidden' name='idGrupoQuestionario' />
				        <table>
				            <tr>
				                <td class="campoObrigatorioQuestionario">
				                <fmt:message key="questionario.nomeGrupoQuestionario" />
				                </td>
				                <td>
				                    <input type='text' name='nomeGrupoQuestionario' size="80" maxlength="80"/>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <table>
				                        <tr>
				                            <td>
				                                <input type='button' name='btnAdicionarGrupo' class='ui-button'  value='<fmt:message key="questionario.adicionar" />' onclick="adicionarGrupo()"/>
				                            </td>
				                            <td>
				                                <input type='button' name='btnAtualizarGrupo'  class='ui-button' value='<fmt:message key="questionario.atualizar" />' onclick="atualizarGrupo()"/>
				                            </td>
				                        </tr>
				                    </table>
				                </td>
				            </tr>
				        </table>
				    </form>
	    		</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_LISTAGEM_QUESTIONARIOS" title='<fmt:message key="questionario.listagemQuestionario" />' >
     <div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formAbrirQuestionario'>
				        <table>
				            <tr>
				                <td>
				                    <select name="lstQuestionarios" id="lstQuestionarios" style='width: 645px; height: 350px' size="18">
				                    </select>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnAbrir' value='<fmt:message key="questionario.abrirQuestionario" />' onclick='abrirQuestionario()'/>
				                    <input type='button' class='ui-button' name='btnFecharListagem' value='<fmt:message key="questionario.fechar" />' onclick='fecharPopUPListagemQuestionario()'/>
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_GRUPO_EXCLUIR" title='<fmt:message key="questionario.excluirQuestoesGrupo" />' >
   <div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formExcluirQuestaoGrupo' action='${ctx}/pages/questionario/questionario'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><b><fmt:message key="questionario.atencaoQuestionario" />:</b></font>
				                </td>
				                <td>
				                    <font color='red'><fmt:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2">
				                   <fmt:message key="questionario.questoes" />:
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2">
				                    <div id='divQuestoesExcluir' style='width:540px;height:300px; border:1px solid black; background-color: white; overflow: auto'>
				                        <table id='tblExcluir' width="100%">
				                            <tr>
				                                <td>
				                                </td>
				                                <td>
				                                </td>
				                            </tr>
				                        </table>
				                    </div>
				                    <div id='divQuestoesExcluirAtualizar' style='display:none'>
				                        <fmt:message key="citcorpore.comum.aguardecarregando"/>
				                    </div>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnSalvarExcl'   value='<fmt:message key="questionario.salvar" />' onclick='gravarQuestionario()'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' name='btnCancelarExcl'   value='<fmt:message key="questionario.cancelar" />' onclick='abrirQuestPosOrder()'/>
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_GRUPO_ORDENAR" title='<fmt:message key="questionario.ordenarQuestionario" />'>
   <div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formOrdenarGrupo' action='${ctx}/pages/questionario/questionario'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><b><fmt:message key="questionario.atencaoQuestionario" />:</b></font>
				                </td>
				                <td>
				                    <font color='red'><fmt:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2">
				                    <fmt:message key="questionario.questoes" />:
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2">
				                    <div id='divQuestoesOrdenar' style='width:540px;height:300px; border:1px solid black; background-color: white; overflow: auto'>
				                        <table id='tblOrdenar' width="100%">
				                            <tr>
				                                <td>
				                                </td>
				                                <td>
				                                </td>
				                            </tr>
				                        </table>
				                    </div>
				                    <div id='divQuestoesOrdenarAtualizar' style='display:none'>
				                        <fmt:message key="citcorpore.comum.aguardecarregando"/>
				                    </div>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnSalvarOrd'  value='<fmt:message key="questionario.salvar" />' onclick='gravarQuestionario()'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' name='btnCancelarOrd'  value='<fmt:message key="questionario.cancelar" />' onclick='abrirQuestPosOrder()'/>
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_QUESTAO" title='<fmt:message key="questionario.configurarElemento" />'>
   <div class="box grid_16 tabs"  style="width: 840px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formQuestao' id='formQuestao'>
				        <input type='hidden' name='idQuestaoQuestionario' />
				        <div>
				            <table>
				                <tr>
				                    <td>
				                    <fmt:message key="questionario.informeElemento" />:
				                    </td>
				                    <td>
				                        <input type='radio' name='tipo' value='Q' onclick='verificaTipo()'/><fmt:message key="questionario.questoes" />
				                        <input type='radio' name='tipo' value='T' onclick='verificaTipo()'/><fmt:message key="questionario.textoFixo" />
				                        <input type='hidden' name='tipo' value='M' onclick='verificaTipo()'/><!-- Matriz  comentado por nao estar funcionando a corrigir depois  -->
				                        <input type='hidden' name='tipo' value='B' onclick='verificaTipo()'/><!-- Tabela  comentado por nao estar funcionando a corrigir depois  -->
				                        <input type='radio' name='tipo' value='P' onclick='verificaTipo()'/>HTML/Java Script

				                    </td>
				                </tr>
				            </table>
				            <div id='divQuestao' style='display: none'>
				                <table width='100%' border="0">
				                    <tr>
				                        <td colspan="2">
				                            <fmt:message key="questionario.textoIncial" /> :    <a href='#' onclick="mostrarTextoInicial()"><fmt:message key="questionario.cliqueEditarTexto" /> </a>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td colspan="2">
				                            <div id='divTextoInicialQuestao' style='height: 50px; overflow: auto; border: 1px solid black;'></div>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td style="5%" class='campoObrigatorioQuestionario campoEsquerda'>
				                        <fmt:message key="questionario.tituloQuestao" />:
				                        </td>
				                        <td>
				                            <textarea rows="5" cols="90" name="tituloQuestaoQuestionario" id="tituloQuestaoQuestionario" style='width: 100%'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td class="campoObrigatorioQuestionario">
				                           <fmt:message key="questionario.tipoQuestao" />:
				                        </td>
				                        <td>
				                            <table cellpadding="0" cellspacing="0">
				                                <tr>
				                                    <td>
				                                        <select name='tipoQuestao' onchange='verificaTipoQuestao()'>
				                                            <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
				                                            <option value='T'><fmt:message key="questionario.textoCurto" /></option>
				                                            <option value='A'><fmt:message key="questionario.textoLongoObs" /></option>
				                                            <option value='L'><fmt:message key="questionario.textoLongoEditor" /></option>
				                                            <option value='R'><fmt:message key="questionario.radioTxt" /></option>
				                                            <option value='C'><fmt:message key="questionario.checkBoxTxt" /></option>
				                                            <option value='X'><fmt:message key="questionario.comboBoxTxt" /></option>
				                                            <option value='N'><fmt:message key="questionario.numeroTxt" /></option>
				                                            <option value='V'><fmt:message key="questionario.valorDecimal" /></option>
				                                            <option value='%'><fmt:message key="questionario.percentualTxt" /></option>
				                                            <option value='*'><fmt:message key="questionario.percentualValorTxt" /></option>
				                                            <option value='1'><fmt:message key="questionario.faixaValoresNumeros" /></option>
				                                            <option value='2'><fmt:message key="questionario.faixaValoresDecimais" /></option>
				                                            <option value='D'><fmt:message key="citcorpore.comum.data" /></option>
				                                            <option value='H'><fmt:message key="citcorpore.texto.tempo.hora" /></option>
				                                            <option value='G'><fmt:message key="questionario.mesAno" /></option>
				                                            <option value='M'><fmt:message key="questionario.galeriaMultimidia" /></option>
				                                        </select>
				                                    </td>
				                                    <td>
				                                        &nbsp;
				                                    </td>
				                                    <td>
				                                        <div id='divTamanho' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <fmt:message key="questionario.tamanho" />:
				                                                    </td>
				                                                    <td>
				                                                        <input type='text' name='tamanho' size="10" maxlength="10" class='Format[Numero]'/>
				                                                    </td>
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                    <td>
				                                        <div id='divDecimais' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                       <fmt:message key="questionario.decimais" />:
				                                                    </td>
				                                                    <td>
				                                                        <input type='text' name='decimais' size="3" maxlength="3" class='Format[Numero]'/>
				                                                    </td>
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                    <td>
				                                        <div id='divPonderada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                       <fmt:message key="questionario.ponderada" />:
				                                                    </td>
				                                                    <td>
				                                                        <input type='radio' name='ponderada' value='S' onclick='verificaTipoQuestao()'/> <fmt:message key="citcorpore.comum.sim" />
				                                                        <input type='radio' name='ponderada' value='N' onclick='verificaTipoQuestao()' checked="checked"/> <fmt:message key="citcorpore.comum.nao" />
				                                                    </td>
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                    <td>
				                                        <div id='divListagem' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                    <fmt:message key="questionario.tipoListagem" />:
				                                                    </td>
				                                                    <td>
							                                            <select name='nomeListagem'>
							                                            </select>
				                                                    </td>
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td colspan="2">
				                            <table border = "0">
				                                <tr>
						                             <td>
						                              <fmt:message key="questionario.ondeAparecer" />

						                             </td>
						                             <td>
						                                 <input type='radio' value='L' name='infoResposta' checked="checked"/> <fmt:message key="questionario.ladoPergunto" />
						                             </td>
						                             <td>
						                                 <input type='radio' value='B' name='infoResposta'/><fmt:message key="questionario.baixoPergunta" />
						                             </td>
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td colspan="2">
				                            <fieldset>
				                                <legend><B><fmt:message key="questionario.informacoesEspecificasQuestao" /></B></legend>
				                                <div style='height: 420px; width: 770px; overflow: auto'>
				                                    <div id='divCheckboxRadioCombo' style='display:none'>
				                                        <fieldset><legend><b><fmt:message key="questionario.opcoes" /></b></legend>
				                                        <table width='100%' border="0">
				                                            <tr>
				                                                <td>
				                                                    <div id='divOpcaoQuestao' style='display:none'>
					                                                    <table width='100%' >
					                                                        <tr>
								                                                <td>
								                                                    <fmt:message key="questionario.opcao" />:
								                                                </td>
								                                                <td>
								                                                    <input type='text' name='txtOpcao' size="45"/>
								                                                </td>
								                                                <td>
								                                                    <fmt:message key="questionario.valor" />:
								                                                </td>
								                                                <td>
								                                                    <input type='text' name='txtValor' size="6"/>
								                                                </td>
								                                                <td>
								                                                    <div id='divPeso' style='display:none'>
								                                                        <table cellpadding="0" cellspacing="0">
								                                                            <tr>
								                                                                <td>
								                                                                    <fmt:message key="questionario.peso" />:
								                                                                </td>
								                                                                <td>
								                                                                    <input type='text' name='peso' size="3" maxlength="3" class='Format[Numero]'/>
								                                                                </td>
								                                                            </tr>
								                                                        </table>
								                                                    </div>
								                                                </td>
								                                                <td>
								                                                   <input type='checkbox' name='geraAlerta' value='S'/><span><fmt:message key="questionario.alerta" /></span>
								                                                </td>
								                                                <td>
								                                                   <input type='checkbox' name='exibeComplemento' value='S' onchange="verificaExibicaoComplementoOpcao()" /><span><fmt:message key="questionario.exibeComplemento" /></span>
								                                                </td>
					                                                        </tr>
					                                                    </table>
				                                                    </div>
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <table>
				                                                        <tr>
				                                                            <td><input type='button' class ='ui-button' name='btnComplementoOpcao' style='display:none' value='<fmt:message key="questionario.configuraComplemento" />' onclick="configurarComplementoOpcao()"/></td>
						                                                    <td><input type='button' class ='ui-button' name='btnNovaOpcao' style='display:block' value='<fmt:message key="questionario.novaOpcao" />' onclick="novaOpcaoQuestao()"/></td>
						                                                    <td><input type='button' class ='ui-button' name='btnAddOpcao' style='display:none' value='<fmt:message key="questionario.adicionar" />' onclick="adicionarOpcaoQuestao()"/></td>
						                                                    <td><input type='button' class ='ui-button' name='btnAtuOpcao' style='display:none' value='<fmt:message key="questionario.atualizar" />' onclick="atualizarOpcaoQuestao()"/></td>
				                                                        </tr>
				                                                    </table>
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <fieldset>
				                                                    <table width="100%"><tr>
				                                                    <td>
					                                                    <div style='height: 70px; width: 600px; overflow: auto; border: 1px solid black'>
						                                                    <table id='tblOpcoes' cellpadding="0" cellspacing="0" width="100%" border="0">
						                                                        <tr>
						                                                            <td class="linhaSubtituloGrid" width="5%">
						                                                                &nbsp;
						                                                            </td>
						                                                            <td class="linhaSubtituloGrid" width="60%">
						                                                                <fmt:message key="questionario.opcao" />
						                                                            </td>
					                                                                <td class="linhaSubtituloGrid" width="25%">
					                                                                    <fmt:message key="questionario.valor" />
					                                                                </td>
						                                                            <td class="linhaSubtituloGrid" width="10%">
						                                                                <fmt:message key="questionario.peso" />
						                                                            </td>
						                                                        </tr>
						                                                    </table>
					                                                    </div>
					                                                </td>
					                                                <td>
				                                                        <table cellpadding="0" cellspacing="0" >
				                                                            <tr>
				                                                                <td><input type='button' class ='ui-button' style="width: 120px" name='btnRemTodasOpcoes' value='<fmt:message key="questionario.removerTodos" />' onclick='removeTodasOpcoes()'/></td>
				                                                            </tr>
				                                                            <tr>
				        	                                                    <td><input type='button' class ='ui-button' style="width: 100px; display: none" name='btnRemOpcao' value='<fmt:message key="questionario.remover" />' onclick='removeOpcao()'/></td>
				                                                            </tr>
				                                                            <tr>
				                                                                <td><input type='button' class ='ui-button' style="width: 100px; display: none" name='btnMoveOpcaoAcima' value='<fmt:message key="questionario.moverAcima" />' onclick='moveOpcaoAcima(this)'/></td>
				                                                            </tr>
				                                                            <tr>
				                                                                <td><input type='button' class ='ui-button' style="width: 100px; display: none" name='btnMoveOpcaoAbaixo' value='<fmt:message key="questionario.moverAbaixo" />' onclick='moveOpcaoAbaixo(this)'/></td>
				                                                            </tr>
				                                                        </table>
					                                                </td>
				                                                </tr></table>
				                                                </fieldset>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <table width="100%">
				                                                        <tr>
				                                                            <td>
							                                                    <fmt:message key="questionario.opcaoPadrao" />:
							                                                </td>
							                                                <td>
							                                                    <select name='lstOpcoesDefault' id='lstOpcoesDefault' style='width: 500px'></select>
							                                                </td>
				                                                        </tr>
				                                                    </table>
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </fieldset>
				                                    </div>
				                                    <div id='divFaixaNumeros' style='display:none'>
				                                        <table>
				                                            <tr>
				                                                <td width="20%">
				                                                   <fmt:message key="questionario.faixaPermitida" />:
				                                                </td>
				                                                <td width="17%">
				                                                    <input type='text' name='val1' size="15" class='Format[Numero]'/>
				                                                </td>
				                                                <td width="5%">
				                                                   <fmt:message key="questionario.ate" />
				                                                </td>
				                                                <td>
				                                                    <input type='text' name='val2' size="15" class='Format[Numero]'/>
				                                                </td>
				                                            </tr>
				<!--
				                                            <tr>
				                                                <td>
				                                                    Texto:
				                                                </td>
				                                                <td colspan="4">
				                                                    <div style='width: 600px; height: 100px; border: 1px solid black; background-color: white'></div>
				                                                </td>
				                                            </tr>
				-->
				                                        </table>
				                                    </div>
				                                    <div id='divFaixaDecimais' style='display:none'>
				                                        <table>
				                                            <tr>
				                                                <td width="20%">
				                                                    <fmt:message key="questionario.faixaPermitida" />:
				                                                </td>
				                                                <td width="17%">
				                                                    <input type='text' name='valPerm1' size="15" class='Format[Moeda]'/>
				                                                </td>
				                                                <td width="5%">
				                                                     <fmt:message key="questionario.ate" />
				                                                </td>
				                                                <td>
				                                                    <input type='text' name='valPerm2' size="15" class='Format[Moeda]'/>
				                                                </td>
																<td>
				                                                    <font color='red'><fmt:message key="questionario.faixaValores" /></font>
				                                                </td>
				                                            </tr>
				<!--
				                                            <tr>
				                                                <td>
				                                                    Texto:
				                                                </td>
				                                                <td colspan="4">
				                                                    <div style='width: 600px; height: 100px; border: 1px solid black; background-color: white'></div>
				                                                </td>
				                                            </tr>
				 -->
				                                        </table>
				                                    </div>
				                                    <div id='divValoresReferenciaUN'>
				                                        <table width='100%'>
				                                            <tr>
				                                                <td width='180px'>
				                                                <fmt:message key="questionario.preenchimentoObrigatorio" />
				                                                </td>
				                                                <td>
				                                                    <input type='radio' name='obrigatoria' value='S'/><fmt:message key="citcorpore.comum.sim" />
				                                                    <input type='radio' name='obrigatoria' value='N' checked="checked"/><fmt:message key="citcorpore.comum.nao" />
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                     <fmt:message key="unidade.unidade" />:
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="unidade" id="unidade" size="50" />
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <fmt:message key="questionario.valorReferencia" />:
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="valoresReferencia" id="valoresReferencia" size="85" />
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                     <fmt:message key="cronograma.imprimir" />:
				                                                </td>
				                                                <td>
				                                                    <input type='radio' name='imprime' value='S' checked="checked"/><fmt:message key="citcorpore.comum.sim" /> <input type='radio' name='imprime' value='N'/><fmt:message key="citcorpore.comum.nao" />
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <font color='red'><fmt:message key="questionario.recuperarValor" /></font>
				                                                </td>
				                                                <td>
				                                                     <input type='radio' name='ultimoValor' value='N' checked="checked"/><fmt:message key="citcorpore.comum.nao" /> <input type='radio' name='ultimoValor' value='S'/><fmt:message key="questionario.atravesId" /> <input type='radio' name='ultimoValor' value='G'/><fmt:message key="questionario.atravesSigla" />
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </div>
				                                    <div id='divCalculada' style='display:none'>
				                                        <table width='100%'>
				                                           <tr>
				                                               <td width='180px'>
				                                                   <fmt:message key="questionario.calculadaSistema" />
				                                               </td>
				                                               <td>
				                                                   <input type='radio' name='calculada' value='S' onclick='verificaTipoQuestao()'/><fmt:message key="citcorpore.comum.sim" /> <input type='radio' name='calculada' value='N' onclick='verificaTipoQuestao()' checked="checked"/><fmt:message key="citcorpore.comum.nao" />
				                                               </td>
				                                           </tr>
				                                       </table>
				                                    </div>
				                                    <div id='divDesenho' style='display:none'>
				                                        <table width='100%'>
				                                           <tr>
				                                               <td width='180px'>
				                                                   <fmt:message key="questionario.utilizarDesenho" />:
				                                               </td>
				                                               <td>
																	<select name='idImagem' id='idImagem'>
																	</select>
				                                               </td>
				                                           </tr>
				                                       </table>
				                                    </div>
				                                    <div id='divEditavel' style='display:none'>
				                                        <table width='100%'>
				                                           <tr>
				                                               <td width='180px'>
				                                                <fmt:message key="questionario.alteradoUsuario" />

				                                               </td>
				                                               <td>
				                                                   <input type='radio' name='editavel' value='S' /><fmt:message key="citcorpore.comum.sim" /> <input type='radio' name='editavel' value='N' checked="checked"/><fmt:message key="citcorpore.comum.nao" />
				                                               </td>
				                                           </tr>
				                                       </table>
				                                    </div>
				                                    <div id='divSigla'>
				                                        <table width='100%'>
				                                            <tr>
				                                                <td width='180px'>
				                                                    <fmt:message key="questionario.sigla" />:
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="sigla" id="sigla" size="60" />
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </div>
				                                </div>
				                            </fieldset>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <table>
				                                <tr>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAddQuestao'  value='<fmt:message key="questionario.adicionar" />' onclick='adicionarQuestao()'/>
				                                    </td>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAtuQuestao'  value='<fmt:message key="questionario.atualizar" />' onclick='atualizarQuestao()' style='display:none'/>
				                                    </td>
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                </table>
				            </div>
				            <div id='divTextoFixo' style='display: none;'>
				                <table width="795px">
				                    <tr>
				                        <td>
				                            <textarea rows="20" class='ui-button' cols="100" name="textoFixo" id="textoFixo" style='width: 790px; height: 400px'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <input type='button' class='ui-button' name='btnAtuTextoFixo'  value='<fmt:message key="questionario.adicionar" />' onclick='adicionarTextoFixo()'/>
				                        </td>
				                    </tr>
				                </table>
				            </div>
				            <div id='divHTML' style='display: none;'>
				                <table width="795px">
				                    <tr>
				                        <td>
				                            <b>HTML/Java Script</b>
				                            <textarea rows="40" cols="100" name="textoHTML" id="textoHTML" style='width: 790px; height: 350px'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <input type='button' class='ui-button' name='btnAddHTML'  value='<fmt:message key="questionario.adicionar" />' onclick='adicionarHTML()'/>
				                            <input type='button' class='ui-button' name='btnAtuHTML'  value='<fmt:message key="questionario.atualizar" />' onclick='atualizarHTML()'/>
				                        </td>
				                    </tr>
				                </table>
				            </div>
				            </div>
				            <div id='divMatriz' style='display: none;'>
				                <fieldset>
					                <table border = "0" width="770px">
					                    <tr>
					                        <td style="5%">
					                            <fmt:message key="questionario.titulo" />:*
					                        </td>
					                        <td colspan="5">
					                            <textarea rows="5" cols="90" name="tituloQuestaoMatriz" id="tituloQuestaoMatriz"></textarea>
					                        </td>
					                    </tr>
					                </table>
				                </fieldset>
				                <fieldset>
				                    <table border = "0" cellpadding="0" cellspacing="0" border = '0'>
				                        <tr>
				                            <td>
				                                <table>
				                                    <tr>
							                            <td>
							                                <fmt:message key="questionario.imprime" />:
							                            </td>
							                            <td>
							                                <input type='radio' name='imprimeMatriz' value='S' checked="checked"/><fmt:message key="citcorpore.comum.sim" /> <input type='radio' name='imprimeMatriz' value='N'/><fmt:message key="citcorpore.comum.nao" />
							                            </td>
							                            <td>
							                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                            </td>
				                                    </tr>
				                                </table>
				                            </td>
				                        </tr>
				                        <tr>
				                            <td>
				                                <table>
				                                    <tr>
							                            <td>
							                               <fmt:message key="questionario.recuperarValor" />

							                            </td>
							                            <td>
							                                <input type='radio' name='ultimoValorMatriz' value='N' checked="checked"/><fmt:message key="citcorpore.comum.nao" /> <input type='radio' name='ultimoValorMatriz' value='G'/><fmt:message key="citcorpore.comum.sim" />
							                            </td>
				                                    </tr>
				                                </table>
				                            </td>
				                        </tr>
				                        <tr>
				                            <td>
				                                <table>
								                    <tr>
								                        <td >
								                        <fmt:message key="questionario.qtdLinhas" />:
								                        </td>
								                        <td>
								                            <input type='text' name="qtdeLinhas" id="qtdeLinhas" size="3" />
								                        </td>
				                                        <td>
				                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                                        </td>
								                        <td >
								                     <fmt:message key="questionario.qtdColunas" />:
				:
								                        </td>
								                        <td>
								                            <input type='text' name="qtdeColunas" id="qtdeColunas" size="3" />
								                        </td>
				                                        <td>
				                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                                        </td>
							                            <td>
							                                <fmt:message key="questionario.sigla" />:
							                            </td>
							                            <td>
							                                <input type='text' name="siglaMatriz" id="siglaMatriz" size="50" />
							                            </td>
								                    </tr>
				                                </table>
				                            </td>
				                        </tr>
				                        <tr>
				                            <td>
				                                <table>
				                                    <tr>
								                        <td>
								                        <fmt:message key="questionario.cabecaolhoLinhas" />:
								                        </td>
								                        <td>
								                            <input type='radio' name='cabecalhoLinhas' value='S' checked="checked"/><fmt:message key="citcorpore.comum.sim" /> <input type='radio' name='cabecalhoLinhas' value='N'/><fmt:message key="citcorpore.comum.nao" />
								                        </td>
				                                        <td>
				                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                                        </td>
								                        <td>
								                            <fmt:message key="questionario.cabecaolhoColunas" />:
								                        </td>
								                        <td>
								                            <input type='radio' name='cabecalhoColunas' value='S' checked="checked"/><fmt:message key="citcorpore.comum.sim" /> <input type='radio' name='cabecalhoColunas' value='N'/><fmt:message key="citcorpore.comum.nao" />
								                        </td>
				                                        <td>
				                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                                        </td>
				                                        <td>
								                            <input type='button' class='ui-button' name='btnMontaMatriz'  value='Montar linhas e colunas' onclick='montarMatriz()'/>
								                        </td>
				                                    </tr>
				                                </table>
				                            </td>
				                        </tr>
				                    </table>
				                </fieldset>
				                <fieldset>
				                    <legend><B><fmt:message key="questionario.questaoMatriz"/></B></legend>
				                    <div id='divQuestoesMatriz' style='width: 770px; height: 340px; overflow: auto;'>
				                    </div>
				                </fieldset>
				                <table>
				                    <tr>
				                        <td>
				                            <table>
				                                <tr>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAddMatriz' value='<fmt:message key="questionario.adicionar" />' onclick='adicionarMatriz()'/>
				                                    </td>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAtuMatriz'  value='<fmt:message key="questionario.atualizar" />' onclick='atualizarMatriz()' style='display:none'/>
				                                    </td>
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                </table>
				            </div>
				            <div id='divTabela' style='display: none; position: absolute;'>
				                <fieldset>
					                <table border = "0" width="780px">
					                    <tr>
					                        <td style="5%">
					                            <fmt:message key="questionario.titulo" />:*
					                        </td>
					                        <td colspan="5">
					                            <textarea rows="5" cols="90" name="tituloQuestaoTabela" id="tituloQuestaoTabela"></textarea>
					                        </td>
					                    </tr>
				                    </table>
				                </fieldset>
				                <fieldset>
				                    <table border = "0">
				                        <tr>
				                            <td>
				                                <fmt:message key="questionario.imprime" />:
				                            </td>
				                            <td>
				                                <input type='radio' name='imprimeTabela' value='S' checked="checked"/><fmt:message key="citcorpore.comum.sim" /> <input type='radio' name='imprimeTabela' value='N'/><fmt:message key="citcorpore.comum.nao" />
				                            </td>
				                        </tr>
				                        <tr>
				                            <td colspan="5" cellpadding="0" cellspacing="0" border = '0'>
				                                <table>
				                                    <tr>
				                                        <td>
				                                           <fmt:message key="questionario.recuperarValor" />
				                                        </td>
				                                        <td>
				                                            <input type='radio' name='ultimoValorTabela' value='N' checked="checked"/><fmt:message key="citcorpore.comum.nao" /> <input type='radio' name='ultimoValorTabela' value='G'/><fmt:message key="citcorpore.comum.sim" />
				                                        </td>
				                                    </tr>
				                                </table>
				                            </td>
				                        </tr>
				                    </table>
				                    <table width="100%" border = "0">
				                        <tr>
					                        <td style='text-align: right;' width="20%" >
					                            <fmt:message key="questionario.qtdColunas" />:
					                        </td>
					                        <td width="10%">
					                            <input type='text' name="qtdeColunasTabela" id="qtdeColunasTabela" size="3" />
					                        </td>
				                            <td>
				                                <fmt:message key="questionario.sigla" />:
				                            </td>
				                            <td>
				                                <input type='text' name="siglaTabela" id="siglaTabela" size="60" />
				                            </td>
					                        <td>
					                            <input type='button' class='ui-button' name='btnMontaTabela'  value='<fmt:message key="questionario.montarColunas" />' onclick='montarTabela(true)'/>
					                        </td>
				                        </tr>
				                    </table>
				                </fieldset>
				                <fieldset>
				                    <legend><B><fmt:message key="questionario.questoesTabela" /></B></legend>
				                    <div id='divQuestoesTabela' style='width: 750px; height: 340px; overflow: auto;'>
				                    </div>
				                </fieldset>
				                <table>
				                    <tr>
				                        <td>
				                            <table>
				                                <tr>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAddTabela' value='<fmt:message key="questionario.adicionar" />' onclick='adicionarTabela()'/>
				                                    </td>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAtuTabela' value='<fmt:message key="questionario.adicionar" />' onclick='atualizarTabela()' style='display:none'/>
				                                    </td>
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                </table>
				            </div>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_TEXTO" title='<fmt:message key="questionario.textoInicialQuestao" />'>
   <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formTextoInicial'>
				        <table width="750px">
				            <tr>
				                <td>

				                   <fmt:message key="questionario.textoInicialQuestao" />:
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <textarea rows="20" cols="100" name="textoInicialQuestaoQuestionario" id="textoInicialQuestaoQuestionario" style='width: 600px; height: 300px'></textarea>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <table>
				                        <tr>
				                            <td>
				                                <input type='button' class='ui-button' name='btnOKTexto' value='OK' onclick='addTextoInicioQuestao()'/>
				                            </td>
				                            <td>
				                                <input type='button' class='ui-button' name='btnFecharTexto' value=' <fmt:message key="questionario.fechar" />' onclick='fecharPopUPTexto()'/>
				                            </td>
				                        </tr>
				                    </table>
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div  id="POPUP_GRUPO_COPIAR" title='<fmt:message key="questionario.copiarGrupoQuestionario" />'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formCopiarGrupo' action='${ctx}/pages/questionario/questionario'/>
				        <input type='hidden' name='idQuestionarioCopiar'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><fmt:message key="questionario.atencaoQuestionario" />:</font>
				                </td>
				                <td>
				                    <font color='red'><fmt:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                <fmt:message key="questionario.questionario" />:
				                </td>
				                <td>
				                    <select name='idQuestionario' id='idQuestionarioCopiarGrupo' onchange='document.formCopiarGrupo.fireEvent("listarGruposQuestionarios")'>
				                    </select>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                 <fmt:message key="questionario.grupo" />:
				                </td>
				                <td>
				                    <select name='idGrupoQuestionario' id='idGrupoQuestionarioCopiarGrupo'>
				                    </select>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                <fmt:message key="questionario.nomeNovoGrupo" />:

				                </td>
				                <td>
				                    <input type='text' name='nomeGrupoQuestionario' size="80" maxlength="80"/>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnCopiarGrupo'  value='<fmt:message key="questionario.copiar" />' onclick='copiarGrupo()'/>
				                     <input type='button' class='ui-button' name='btnFecharGrupo'  value='<fmt:message key="questionario.fechar" />' onclick='fecharPopUPGrupoCopiar()'/>
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_GRUPO_ORDENAR_GRUPO" title='<fmt:message key="questionario.ordenarGrupos" />'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formOrdenar' action='${ctx}/pages/questionario/questionario'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><b><fmt:message key="questionario.atencaoQuestionario" />:</b></font>
				                </td>
				                <td>
				                    <font color='red'><fmt:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2">
				                <fmt:message key="questionario.grupos" />:
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2">
				                    <div id='divQuestoesOrdenarGrupo' style='width:790px;height:300px; border:1px solid black; background-color: white; overflow: auto'>
				                        <table id='tblOrdenarGrupo' width="100%">
				                            <tr>
				                                <td>
				                                </td>
				                                <td>
				                                </td>
				                            </tr>
				                        </table>
				                    </div>
				                    <div id='divQuestoesOrdenarAtualizarGrupo' style='display:none'>
				                        <fmt:message key="citcorpore.comum.aguardecarregando"/>
				                    </div>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnSalvarOrdGrupo'  value='<fmt:message key="questionario.salvar" />' onclick='gravarGruposQuestionario()'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' name='btnCancelarOrdGrupo' value='<fmt:message key="questionario.cancelar" />' onclick='abrirQuestPosOrder()'/>
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_GRUPO_EXCLUIR_GRUPO" title='<fmt:message key="questionario.excluirGrupos" />'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formExcluirGrupo' action='${ctx}/pages/questionario/questionario'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><b><fmt:message key="questionario.atencaoQuestionario" />:</b></font>
				                </td>
				                <td>
				                    <font color='red'><fmt:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2">
				                   <fmt:message key="questionario.grupos" />:
				                </td>
				            </tr>
				            <tr>
				                <td colspan="2">
				                    <div id='divQuestoesExcluirGrupo' style='width:790px;height:300px; border:1px solid black; background-color: white; overflow: auto'>
				                        <table id='tblExcluirGrupo' width="100%">
				                            <tr>
				                                <td>
				                                </td>
				                                <td>
				                                </td>
				                            </tr>
				                        </table>
				                    </div>
				                    <div id='divQuestoesExcluirAtualizarGrupo' style='display:none'>
				                        <fmt:message key="citcorpore.comum.aguardecarregando"/>
				                    </div>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnSalvarOrdGrupo'   value='<fmt:message key="questionario.salvar" />' onclick='gravarQuestionario()'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' name='btnCancelarOrdGrupo'  value='<fmt:message key="questionario.cancelar" />' onclick='abrirQuestPosOrder()'/>
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_QUESTAO_AGRUPADA" title='<fmt:message key="questionario.configuracaoMatrizTabela"/>'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formQuestaoAgrupada'>
				        <input type='hidden' name='idQuestaoQuestionario' />
				        <div>
				            <table>
				                <tr>
				                    <td>
				                         <label><fmt:message key="questionario.informeElemento" /></label>
				                    </td>
				                    <td>
				                        <input type='radio' name='tipo' value='Q' onclick='verificaTipoAgrupada()'/><fmt:message key="questionario.questoes" /><input type='radio' name='tipo' value='T' onclick='verificaTipoAgrupada()'/><span id='lblTextoFixo' name='lblTextoFixo'>Texto Fixo</span>
				                    </td>
				                </tr>
				            </table>
				            <div id='divQuestaoAgrupada' style='display: none'>
				                <table border="0" width="780px">
				                    <tr>
				                        <td colspan="2">
				                            <div id='divTextoInicialQuestaoAgrupada' style='displauy:none'></div>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td style="5%">
				                            <label><fmt:message key="questionario.tituloQuestao" /></label>
				                        </td>
				                        <td>
				                            <textarea rows="5" cols="90" name="tituloQuestaoQuestionarioAgrupada" id="tituloQuestaoQuestionarioAgrupada" style='width: 100%'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <label><fmt:message key="questionario.tipoQuestao" /></label>
				                        </td>
				                        <td>
				                            <table cellpadding="0" cellspacing="0">
				                                <tr>
				                                    <td>
				                                        <select name='tipoQuestao' onchange='verificaTipoQuestaoAgrupada()'>
				                                            <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
				                                            <option value='T'><fmt:message key="questionario.textoCurto" /></option>
				                                            <option value='A'><fmt:message key="questionario.textoLongoObs" /></option>
				                                            <option value='L'><fmt:message key="questionario.textoLongoEditor" /></option>
				                                            <option value='R'><fmt:message key="questionario.radioTxt" /></option>
				                                            <option value='C'><fmt:message key="questionario.checkBoxTxt" /></option>
				                                            <option value='X'><fmt:message key="questionario.comboBoxTxt" /></option>
				                                            <option value='N'><fmt:message key="questionario.numeroTxt" /></option>
				                                            <option value='V'><fmt:message key="questionario.valorDecimal" /></option>
				                                            <option value='%'><fmt:message key="questionario.percentualTxt" /></option>
				                                            <option value='*'><fmt:message key="questionario.percentualValorTxt" /></option>
				                                            <option value='1'><fmt:message key="questionario.faixaValoresNumeros" /></option>
				                                            <option value='2'><fmt:message key="questionario.faixaValoresDecimais" /></option>
				                                            <option value='D'><fmt:message key="citcorpore.comum.data" /></option>
				                                            <option value='H'><fmt:message key="citcorpore.texto.tempo.hora" /></option>
				                                            <option value='G'><fmt:message key="questionario.mesAno" /></option>
				                                            <option value='M'><fmt:message key="questionario.galeriaMultimidia" /></option>
				                                            <option value='8'><fmt:message key="visao.listagem" /></option>
				                                        </select>
				                                    </td>
				                                    <td>
				                                        &nbsp;
				                                    </td>
				                                    <td>
				                                        <div id='divTamanhoAgrupada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <label><fmt:message key="questionario.tamanho" /></label>
				                                                    </td>
				                                                    <td>
				                                                        <input type='text' name='tamanho' size="10" maxlength="10"/>
				                                                    </td>
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                    <td>
				                                        <div id='divDecimaisAgrupada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <label><fmt:message key="questionario.decimais" /></label>
				                                                    </td>
				                                                    <td>
				                                                        <input type='text' name='decimais' size="3" maxlength="3"/>
				                                                    </td>
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                    <td>
				                                        <div id='divPonderadaAgrupada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <label><fmt:message key="questionario.ponderada" /></label>
				                                                    </td>
				                                                    <td>
				                                                        <input type='radio' name='ponderada' value='S' onclick='verificaTipoQuestaoAgrupada()'/><label><fmt:message key="citcorpore.comum.sim" /></label>
				                                                        <input type='radio' name='ponderada' value='N' onclick='verificaTipoQuestaoAgrupada()' checked="checked"/><label><fmt:message key="citcorpore.comum.nao" /></label>
				                                                    </td>
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                    <td>
				                                        <div id='divListagemAgrupada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <label><fmt:message key="questionario.tipoDeListagem" /></label>
				                                                    </td>
				                                                    <td>
				                                                        <select name='nomeListagemAgrupada'></select>
				                                                    </td>
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td colspan="2">
				                            <table border = "0">
				                                <tr>
				                                     <td>
				                                         <label><fmt:message key="questionario.ondeAparecer" /></label>
				                                     </td>
				                                     <td>
				                                         <input type='radio' value='L' name='infoResposta' checked="checked"/><label><fmt:message key="questionario.ladoPergunto" /></label>
				                                     </td>
				                                     <td>
				                                         <input type='radio' value='B' name='infoResposta'/><label><fmt:message key="questionario.baixoPergunta" /></label>
				                                     </td>
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td colspan="2">
				                            <fieldset>
				                                <legend><b><fmt:message key="questionario.informacoesEspecificasQuestao" /></b></legend>
				                                <div style='height: 320px; overflow: auto'>
				                                    <div id='divCheckboxRadioComboAgrupada' style='display:none'>
				                                        <table border="0">
				                                            <tr>
				                                                <td>
				                                                    <label><fmt:message key="questionario.opcao" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name='txtOpcao' size="70"/>
				                                                </td>
				                                                <td>
					                                                <div id='divPesoAgrupada' style='display:none'>
					                                                    <table cellpadding="0" cellspacing="0">
					                                                        <tr>
					                                                            <td>
					                                                                <label><fmt:message key="questionario.peso" /></label>
					                                                            </td>
					                                                            <td>
					                                                                <input type='text' name='peso' size="2" maxlength="3"/>
					                                                            </td>
					                                                        </tr>
					                                                    </table>
					                                                </div>
				                                                </td>
				                                                <td>
				                                                    <input type='button' class='ui-button' name='btnAddOpcao' id='btnAddOpcaoAgrupada' value='<fmt:message key="citcorpore.comum.adicionar" />' onclick="adicionarOpcaoQuestaoAgrupada()"/>
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td colspan="2">
				                                                    <select name='lstOpcoesAgrupada' id='lstOpcoesAgrupada' size="7" style='width: 600px'></select>
				                                                </td>
				                                                <td colspan="3">
				                                                    <input type='button' class='ui-button' name='btnRemOpcao' value='<fmt:message key="questionario.remover" />' onclick='removeOpcaoAgrupada()'/>
				                                                    <br/>
				                                                    <input type='button' class='ui-button' name='btnRemOpcao' value='<fmt:message key="questionario.rmTodos" />' onclick='removeTodasOpcoesAgrupada()'/>
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <label><fmt:message key="questionario.opcaoPadrao" /></label>
				                                                </td>
				                                                <td colspan="4">
				                                                    <select name='lstOpcoesDefaultAgrupada' id='lstOpcoesDefaultAgrupada' style='width: 500px'></select>
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </div>
				                                    <div id='divFaixaNumerosAgrupada' style='display:none'>
				                                        <table>
				                                            <tr>
				                                                <td width="20%">
				                                                    <label><fmt:message key="questionario.faixaPermitida" /></label>
				                                                </td>
				                                                <td width="17%">
				                                                    <input type='text' name='val1' size="15"/>
				                                                </td>
				                                                <td width="5%">
				                                                    <label><fmt:message key="citcorpore.comum.a" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name='val2' size="15"/>
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <label><fmt:message key="questionario.texto" /></label>
				                                                </td>
				                                                <td colspan="4">
				                                                    <div style='width: 600px; height: 100px; border: 1px solid black; background-color: white'></div>
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </div>
				                                    <div id='divFaixaDecimaisAgrupada' style='display:none'>
				                                        <table>
				                                            <tr>
				                                                <td width="20%">
				                                                    <label><fmt:message key="questionario.faixaPermitida" /></label>
				                                                </td>
				                                                <td width="17%">
				                                                    <input type='text' name='valPerm1' size="15" class='Format[Moeda]'/>
				                                                </td>
				                                                <td width="5%">
				                                                    <label><fmt:message key="citcorpore.comum.a" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name='valPerm2' size="15" class='Format[Moeda]'/>
				                                                </td>
																<td>
				                                                    <font color='red'><fmt:message key="questionario.avisoNaoExisteFaixaValores" /></font>
				                                                </td>
				                                            </tr>
				<!--
				                                            <tr>
				                                                <td>
				                                                    Texto:
				                                                </td>
				                                                <td colspan="4">
				                                                    <div style='width: 600px; height: 100px; border: 1px solid black; background-color: white'></div>
				                                                </td>
				                                            </tr>
				-->
				                                        </table>
				                                    </div>
				                                    <div id='divValoresReferenciaUNAgrupada'>
				                                        <table>
				                                            <tr>
				                                                <td>
				                                                    <label><fmt:message key="questionario.preenchimentoObrigatorio" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='radio' name='obrigatoria' value='S'/><label><fmt:message key="citcorpore.comum.sim" /></label>
				                                                    <input type='radio' name='obrigatoria' value='N' checked="checked"/><label><fmt:message key="citcorpore.comum.nao" /></label>
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <label><fmt:message key="unidade.unidade" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="unidade" id="unidade" size="50" />
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <label><fmt:message key="questionario.valorReferencia" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="valoresReferencia" id="valoresReferencia" size="90" />
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </div>
				                                    <div id='divSiglaAgrupada'>
				                                        <table width='100%'>
				                                            <tr>
				                                                <td width='180px'>
				                                                	<label><fmt:message key="questionario.sigla" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="sigla" id="sigla" size="60" />
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </div>
				                                </div>
				                            </fieldset>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <table>
				                                <tr>
				                                    <td>
				                                        <input type='button' class ='ui-button' name='btnAddQuestaoAgrupada'  value='<fmt:message key="citcorpore.comum.adicionar" />' onclick='atualizarQuestaoAgrupada()'/>
				                                    </td>
				                                    <td>
				                                        <input type='button' class ='ui-button' name='btnAtuQuestaoAgrupada'  value='<fmt:message key="citcorpore.comum.atualizar" />' onclick='atualizarQuestaoAgrupada()' style='display:none'/>
				                                    </td>
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                </table>
				            </div>
				            <div id='divTextoFixoAgrupada' style='display: none; width: 780px'>
				                <table width="100%">
				                    <tr>
				                        <td>
				                            <textarea rows="20" cols="100" name="textoFixoAgrupada" id="textoFixoAgrupada" style='width: 770px; height: 400px'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <input type='button' class ='ui-button' name='btnAtuTextoFixoAgrupada'  value='<fmt:message key="citcorpore.comum.adicionar" />' onclick='atualizarTextoFixoAgrupada()'/>
				                        </td>
				                    </tr>
				                </table>
				            </div>
				        </div>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_COPIAR_QUESTAO" title='<fmt:message key="questionario.selecioneConfigurar"/>'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formCopiarQuestao'>
				        <table width="295">
				            <tr>
				                <td>
				                   <select name='lstQuestoes' id='lstQuestoes' multiple="multiple" size="7" style='width: 250px'></select>
				                </td>
				                <td>
				                    <input type='button' class ='ui-button' value='OK' onclick='copiarQuestaoFunction()' />
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>
</html>
