<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%
	response.setCharacterEncoding("ISO-8859-1");
%>
<html>

<head>
	<%@include file="/include/titleComum/titleComum.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
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
	function LOOKUP_DEMANDA_select(id,desc){
		document.form.restore({idEmpregado:id});
	}
</script>

<script>
</script>

<div id="paginaTotal">
	<div id="areautil">
		<div id="formularioIndex">
       		<div id=conteudo>
				<table width="100%">
					<tr>
						<td width="100%">
							<div id='areaUtilAplicacao'>
								<!-- ## AREA DA APLICACAO ## -->
							  	<div class="tabber" id="tabTela">
							    	<div class="tabbertab" id="tabCadastro">
										<h2>Cadastro</h2>
										 	<form name='form' action='${ctx}/pages/demanda/demanda'>
										 		<input type='hidden' name='idDemanda'/>
											  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
											         <tr>
											            <td class="campoEsquerda">Fluxo de Tratamento:*</td>
											            <td>
											            	<select name='idFluxo' class="Valid[Required] Description[Fluxo]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Cliente:*</td>
											            <td>
											            	<select name='idCliente' class="Valid[Required] Description[Clente]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Projeto:*</td>
											            <td>
											            	<select name='idProjeto' class="Valid[Required] Description[Projeto]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Tipo de Demanda:*</td>
											            <td>
											            	<select name='idTipoDemanda' class="Valid[Required] Description[Tipo de Demanda]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Detalhamento:*</td>
											            <td>
											            	<textarea name="detalhamento" cols='70' rows='10' class="Valid[Required] Description[Nome]"></textarea>
											            </td>
											         </tr>

											         <tr>
											            <td class="campoEsquerda">Expectativa de Finalização:*</td>
											            <td>
											            	<input type='text' name="expectativaFim" maxlength="10" size="10" class="Valid[Required,Data] Description[Expectativa de Finalização] Format[Data]" />
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Prioridade:*</td>
											            <td>
											            	<input type='radio' name="prioridade" value="E" class="Valid[Required] Description[Prioridade]" />Emergencial
											            	<input type='radio' name="prioridade" value="A" class="Valid[Required] Description[Prioridade]" />Alta
											            	<input type='radio' name="prioridade" value="M" class="Valid[Required] Description[Prioridade]" />Média
											            	<input type='radio' name="prioridade" value="B" class="Valid[Required] Description[Prioridade]" />Baixa
											            </td>
											         </tr>
												 <tr>
										            <td colspan="2" class="campoObrigatorio">* Campos com preenchimento obrigat&oacute;rio</td>
										         </tr>
										         <tr>
										         	<td colspan='2'>
									         		<input type='button' name='btnGravar' value='Gravar' onclick='document.form.save();'/>
									         		<input type='button' name='btnLimpar' value='Limpar' onclick='document.form.clear();'/>
									         	</td>
									         </tr>
										</table>
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
	<%@include file="../../include/rodape.jsp"%>
</div>

</body>
</html>
