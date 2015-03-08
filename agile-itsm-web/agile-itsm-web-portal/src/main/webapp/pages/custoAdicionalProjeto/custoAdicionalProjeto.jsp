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

	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			document.getElementById('tabTela').tabber.tabShow(0);
		}
	}
</script>

<!-- Area de JavaScripts -->
<script>
	function LOOKUP_CUSTOSADICIONAIS_select(id,desc){
		document.form.restore({idCustoAdicional:id});
	}
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
										 	<form name='form' action='${ctx}/pages/custoAdicionalProjeto/custoAdicionalProjeto'>
										 		<input type='hidden' name='idCustoAdicional'/>
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
											            	<select name='idProjeto' id='idProjeto' class="Valid[Required] Description[Projeto]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Tipo*:</td>
											            <td>
											            	<select name='tipoCusto' class="Valid[Required] Description[Tipo]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Data*:</td>
											            <td>
											            	<input type='text' name='dataCusto' size="10" maxlength="10" class="Valid[Required,Date] Format[Data] Description[Data]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Valor*:</td>
											            <td>
											            	<input type='text' name='valor' size="15" maxlength="15" class="Valid[Required] Format[Moeda] Description[Valor de Custo Adicional]"/>
											            </td>
											         </tr>
										         <tr>
										            <td class="campoEsquerda">Detalhamento*:</td>
										            <td>
										            	<textarea name="detalhamento" cols='70' rows='5' class="Valid[Required] Description[Detalhamento]"></textarea>
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
						    	<div class="tabbertab" id="tabPesquisa">
									<h2>Pesquisa</h2>
									<form name='formPesquisa'>
										<cit:findField formName='formPesquisa' lockupName='LOOKUP_CUSTOSADICIONAIS' id='LOOKUP_CUSTOSADICIONAIS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
