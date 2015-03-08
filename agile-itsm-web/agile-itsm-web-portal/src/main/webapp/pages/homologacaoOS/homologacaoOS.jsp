<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	response.setCharacterEncoding("ISO-8859-1");

	String permiteValorZeroAtv = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.OS_VALOR_ZERO, "N");
	if (permiteValorZeroAtv == null){
		permiteValorZeroAtv = "N";
	}
%>
<html>

<head>
	<%@include file="/include/titleComum/titleComum.jsp" %>

	<%@include file="/include/menu/menuConfig.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>

	<script type="text/javascript" src="../../cit/objects/DemandaDTO.js"></script>
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<%@include file="/include/menu/menu.jsp"%>

<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			document.getElementById('tabTela').tabber.tabShow(0);
		}
	}
</script>

<!-- Area de JavaScripts -->
<script>
	function LOOKUP_OS_select(id,desc){
		document.form.restore({idOS:id});
	}
	function gravarForm(){
		var count = GRID_ITENS.getMaxIndex();
		var existeErro = false;
		var contadorAux = 0;
		var objs = new Array();
		for (var i = 1; i <= count; i++){
			var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
			if (!trObj){
				continue;
			}
			var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
			var glosaObj = document.getElementById('glosa' + NumberUtil.zerosAEsquerda(i,5));
			var complexidadeObj = document.getElementById('complexidade' + NumberUtil.zerosAEsquerda(i,5));
			var demandaObj = document.getElementById('demanda' + NumberUtil.zerosAEsquerda(i,5));
			var objObj = document.getElementById('obs' + NumberUtil.zerosAEsquerda(i,5));
			trObj.bgColor = 'white';
			complexidadeObj.style.backgroundColor = 'white';
			quantidadeObj.style.backgroundColor = 'white';
			demandaObj.style.backgroundColor = 'white';
			objObj.style.backgroundColor = 'white';
			glosaObj.style.backgroundColor = 'white';
			if (complexidadeObj.value == ''){
				trObj.bgColor = 'orange';
				complexidadeObj.style.backgroundColor = 'orange';
				quantidadeObj.style.backgroundColor = 'orange';
				demandaObj.style.backgroundColor = 'orange';
				objObj.style.backgroundColor = 'orange';
				glosaObj.style.backgroundColor = 'orange';
				alert('Informe a complexidade! Linha: ' + i);
				return;
			}
			if (demandaObj.value == ''){
				trObj.bgColor = 'orange';
				complexidadeObj.style.backgroundColor = 'orange';
				quantidadeObj.style.backgroundColor = 'orange';
				demandaObj.style.backgroundColor = 'orange';
				objObj.style.backgroundColor = 'orange';
				glosaObj.style.backgroundColor = 'orange';
				alert('Informe a demanda! Linha: ' + i);
				return;
			}
			if (quantidadeObj.value == ''){
				trObj.bgColor = 'orange';
				complexidadeObj.style.backgroundColor = 'orange';
				quantidadeObj.style.backgroundColor = 'orange';
				demandaObj.style.backgroundColor = 'orange';
				objObj.style.backgroundColor = 'orange';
				glosaObj.style.backgroundColor = 'orange';
				alert('Informe o custo! Linha: ' + i);
				return;
			}
			var objItem = new CIT_DemandaDTO();
			objItem.complexidade = complexidadeObj.value;
			objItem.custoTotal = quantidadeObj.value;
			objItem.glosa = glosaObj.value;
			objItem.detalhamento = demandaObj.value;
			objItem.observacao = objObj.value;
			objs[contadorAux] = objItem;
			contadorAux = contadorAux + 1;

			<%if (!permiteValorZeroAtv.equalsIgnoreCase("S")){%>
				if (quantidadeObj.value == '' || quantidadeObj.value == '0,00' || quantidadeObj.value == '0'){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';
					objObj.style.backgroundColor = 'orange';
					glosaObj.style.backgroundColor = 'orange';
					alert('Falta definir custo da atividade ! Linha: ' + i);
					existeErro = true;
				}
			<%}%>
		}
		if (existeErro){
			return;
		}
		document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
		document.form.save();
	}
	var seqSelecionada = '';
	function setaRestoreItem(complex, det, obs, custo, glosa){
		if (seqSelecionada != ''){
			eval('HTMLUtils.setValue(\'complexidade' + seqSelecionada + '\', \'' + complex + '\')');
			eval('document.form.demanda' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
			eval('document.form.obs' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + obs + '\')');
			eval('document.form.quantidade' + seqSelecionada + '.value = "' + custo + '"');
			eval('document.form.glosa' + seqSelecionada + '.value = "' + glosa + '"');
		}
	}
</script>

<style>
	.linhaGrid{
		border:1px solid black;
		background-color: #D6E7FF;
	}
</style>

<div id="paginaTotal">
	<div id="areautil">
		<div id="formularioIndex">
       		<div id=conteudo>
				<table width="100%">
					<tr>
						<td width="100%">
							<div id='areaUtilAplicacao'>
								<b>Homologação de OS - Ordem de Serviço</b>
								<!-- ## AREA DA APLICACAO ## -->
							  	<div class="tabber" id="tabTela">
							    	<div class="tabbertab" id="tabCadastro">
										<h2>Cadastro</h2>
										 	<form name='form' action='${ctx}/pages/homologacaoOS/homologacaoOS'>
										 		<input type='hidden' name='idOS'/>
										 		<input type='hidden' name='colItens_Serialize'/>
											  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
											         <tr>
											            <td class="campoEsquerda">Contrato*:</td>
											            <td>
											            	<select name='idContrato' class="Valid[Required] Description[Contrato]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Classificação*:</td>
											            <td>
											            	<select name='idClassificacaoOS' class="Valid[Required] Description[Classificação]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Número*:</td>
											            <td>
											            	<input type='text' name='numero' size="20" maxlength="20" class="Valid[Required] Description[Número]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Ano*:</td>
											            <td>
											            	<input type='text' name='ano' size="4" maxlength="4" class="Format[Numero] Valid[Required] Description[Ano]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Data Início*:</td>
											            <td>
											            	<input type='text' name='dataInicio' size="10" maxlength="10" class="Format[Date] Valid[Required,Date] Description[Data Início]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Data Fim*:</td>
											            <td>
											            	<input type='text' name='dataFim' size="10" maxlength="10" class="Format[Date] Valid[Required,Date] Description[Data Fim]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Tarefa/Demanda:</td>
											            <td>
											            	<textarea name="demanda" cols='120' rows='5'></textarea>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Objetivo:</td>
											            <td>
											            	<textarea name="objetivo" cols='120' rows='5'></textarea>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Situação:*</td>
											            <td>
											            	<select name='situacaoOS' id='situacaoOS' class="Valid[Required] Description[Situação]">
											            		<option value='5'>Editar</option>
											            		<option value='6'>Homologar</option>
											            		<option value='2'>Rejeitar</option>
											            	</select>
											            </td>
											         </tr>
											         <tr>
											         	<td colspan="2" style='text-align: right;'>
											         		<input type='button' name='btnAddInteg' id='btnAddInteg' value='Adicionar Item' onclick="GRID_ITENS.addRow();" />
											         	</td>
											         </tr>
											         <tr>
											         	<td colspan="2">
												         		<cit:grid id="GRID_ITENS" columnHeaders="Complexidade;Atividade;Observações;Custo Total;Glosa" styleCells="linhaGrid">
												         			<cit:column idGrid="GRID_ITENS" number="001">
												         				<select name='complexidade#SEQ#' id='complexidade#SEQ#'>
												         					<option value='B'>Baixa</option>
												         					<option value='I'>Intermediária</option>
												         					<option value='M'>Mediana</option>
												         					<option value='A'>Alta</option>
												         					<option value='E'>Especialista</option>
												         				</select>
												         			</cit:column>
												         			<cit:column idGrid="GRID_ITENS" number="002">
												         				<textarea name="demanda#SEQ#" cols='58' rows='5'></textarea>
												         			</cit:column>
												         			<cit:column idGrid="GRID_ITENS" number="003">
												         				<textarea name="obs#SEQ#" cols='58' rows='5'></textarea>
												         			</cit:column>
												         			<cit:column idGrid="GRID_ITENS" number="004">
												         				<input type='text' name='quantidade#SEQ#' size='12' maxlength='14' class='Format[Moeda]'/>
												         			</cit:column>
												         			<cit:column idGrid="GRID_ITENS" number="005">
												         				<input type='text' name='glosa#SEQ#' size='12' maxlength='14' class='Format[Moeda]'/>
												         			</cit:column>
												         		</cit:grid>
											         	</td>
											         </tr>
												 <tr>
										            <td colspan="2" class="campoObrigatorio">* Campos com preenchimento obrigat&oacute;rio</td>
										         </tr>
										         <tr>
										         	<td colspan='2'>
									         		<input type='button' name='btnGravar' value='Gravar' onclick='gravarForm();'/>
									         		<input type='button' name='btnLimpar' value='Limpar' onclick='document.form.clear();'/>
									         		</td>
									         	</tr>
										</table>
									</form>
								</div>
						    	<div class="tabbertab" id="tabPesquisa">
									<h2>Pesquisa</h2>
									<form name='formPesquisa'>
										<cit:findField formName='formPesquisa' lockupName='LOOKUP_OS_HOMOLOGACAO' id='LOOKUP_OS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
									</form>
								</div>
							</div>
							<!-- ## FIM - AREA DA APLICACAO ## -->
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>

<script>
	document.form.onClear = function(){
		GRID_ITENS.deleteAllRows();
	};
</script>

</body>
</html>
