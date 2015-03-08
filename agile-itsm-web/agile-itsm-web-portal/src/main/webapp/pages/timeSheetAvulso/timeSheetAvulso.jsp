<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	response.setCharacterEncoding("ISO-8859-1");
%>
<html>

<head>
	<%@include file="/include/titleComum/titleComum.jsp" %>

	<%@include file="/include/menu/menuConfig.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<%@include file="/include/menu/menu.jsp"%>

<script>
	var objTab = null;
</script>

<!-- Area de JavaScripts -->

<script>
	<cit:Menu/>
	writeMenus();
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
							  	<div class="tabber">
							    	<div class="tabbertab" id="tabCadastro">
										<h2>Time Sheet</h2>
										 	<form name='form' action='${ctx}/pages/timesheet/timeSheetAvulso'>
										 		<input type='hidden' name='idTimeSheet'/>
											  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
											         <tr>
											            <td class="campoEsquerda">Cliente*:</td>
											            <td>
											            	<select name='idCliente' class="Valid[Required] Description[Cliente]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Projeto*:</td>
											            <td>
											            	<select name='idProjeto' class="Valid[Required] Description[Projeto]">
											            	</select>
											            </td>
											         </tr>
													<tr>
														<td class="campoEsquerda">
															Data*:
														</td>
														<td>
															<input type='text' name='data' size='10' maxlength="10" class="Valid[Required] Format[Data] Description[Data]"/>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">
															Qtde Horas*:
														</td>
														<td>
															<input type='text' name='qtdeHoras' size='10' class="Valid[Required] Format[MOEDA] Description[Quantidade de horas]"/>
														</td>
													</tr>
													<tr>
														<td class="campoEsquerda">
															Detalhamento*:
														</td>
														<td>
															<textarea rows="5" cols="60" name="detalhamento" class="Valid[Required] Description[Detalhamento]"></textarea>
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
