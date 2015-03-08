<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<%
	response.setCharacterEncoding("ISO-8859-1");

	String permiteValorZeroAtv = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.OS_VALOR_ZERO, "N");
	if (permiteValorZeroAtv == null){
		permiteValorZeroAtv = "N";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	 <%@include file="/include/header.jsp"%>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/text.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/grid.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css">
<!--  Desenvolvedor: Pedro Lino - Data: 30/10/2013 - Horário: 09:40 - ID Citsmart: 120948 -
* Motivo/Comentário: Inserido atualiza antigo para layout entrar na cor padrão 	 -->
	<link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />
  <link type="text/css" rel="stylesheet" href="css/os.css"/>

	<script type="text/javascript" src="../../cit/objects/DemandaDTO.js"></script>

</head>
<body>
	<!-- Definicoes Comuns -->
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="<fmt:message key='citcorpore.comum.aguardecarregando' />" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>

	<div id="paginaTotal">
		<div id="areautil">
			<div id="formularioIndex">
	       		<div id="conteudo" style="margin-left:  10px; margin-bottom: 10px;" >
					<table width="100%">
						<tr>
							<td width="100%">
									<h2><b><fmt:message key="requisitosla.ordem_servico" /></b></h2>
									<!-- ## AREA DA APLICACAO ## -->
									 	<form name='form' action='${ctx}/pages/os/os'>
									 		<input type='hidden' name='idOS'/>
									 		<input type='hidden' name='idOSPai'/>
									 		<input type='hidden' name='idContrato'/>
									 		<input type='hidden' name='quantidadeGlosasAnterior'/>
									 		<input type='hidden' name='colItens_Serialize'/>
										  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
										  		<tr>
										            <td class="campoEsquerda"><fmt:message key="pesquisa.datainicio" />*:</td>
										            <td>
										            	<input type='text' id="dataInicio" name='dataInicio' size="10" maxlength="10" onchange="calculaCusto()" style="width: 100px !important;" class="Valid[Required,Date] Description[Data Início] Format[Date] dtpicker"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><fmt:message key="pesquisa.datafim" />*:</td>
										            <td>
										            	<input type='text' id="dataFim" name='dataFim' size="10" maxlength="10" onchange="calculaCusto()" style="width: 100px !important;" class="Valid[Required,Date] Description[Data Fim] Format[Date] dtpicker"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><fmt:message key="problema.servico" />*:</td>
										            <td>
										            	<select id="idServicoContrato" name='idServicoContrato' class="Valid[Required] Description[Serviço]" onclick="selecionaServicoContrato()" onchange="restoreInfoServios()"></select>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda" style="visibility:hidden; display: none;"><fmt:message key="citcorpore.comum.ano" />*:</td>
										            <td>
										            	<input type='text' style="visibility:hidden; display: none;" name='ano' size="4" maxlength="4" style="width: 80px !important;" class="Format[Numero] Valid[Required] Description[Ano] text"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><fmt:message key="citcorpore.comum.numero" />*:</td>
										            <td>
										            	<input type='text' name='numero' size="20" maxlength="20" style="width: 250px !important;" class="Valid[Required] Description[Número] text"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><fmt:message key="citcorpore.comum.areaRequisitante" />*:</td>
										            <td>
										            	<input type='text' id='nomeAreaRequisitante' name='nomeAreaRequisitante' size="80" maxlength="80" style="width: 500px !important;" class="Valid[Required] Description[Área requisitante] text"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda" style='vertical-align: middle;'><fmt:message key="citcorpore.ui.tabela.coluna.Tarefa_Demanda" />:</td>
										            <td>
										            	<textarea name="demanda" cols='120' rows='5' style="border: 1px solid black;"></textarea>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda" style='vertical-align: middle;'><fmt:message key="planoMelhoria.objetivo" />:</td>
										            <td>
										            	<textarea name="objetivo" cols='120' rows='5' style="border: 1px solid black;"></textarea>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><fmt:message key="visao.situacao" />*:</td>
										            <td>
										            	<select name='situacaoOS' id='situacaoOS' class="Valid[Required] Description[visao.situacao]"></select>
										            </td>
										         </tr>
										         <%
										         	String ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS, "N");
										         	if ((ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS!=null)&&(ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS.equalsIgnoreCase("S"))){
										         %>
											         <tr>
											            <td class="campoEsquerda"><fmt:message key="grupoAssinatura.label" />*:</td>
											            <td>
											            	<select name='idGrupoAssinatura' id='idGrupoAssinatura' class="Valid[Required] Description[grupoAssinatura.label]"></select>
											            </td>
											         </tr>
										         <%
										         	}
										         %>
										         <tr>
										            <td class="campoEsquerda">&nbsp;</td>
										            <td>
										            	<input type='button' name='btnAtuLista' id='btnAtuLista' value='<fmt:message key="citcorpore.comum.atualizalistaatv" />' onclick="atualizaInfoServios();" />
										            </td>
										         </tr>
										         <tr>
										         	<td colspan="2" style='text-align: right; display:none'>
										         		<input type='button' name='btnAddInteg' id='btnAddInteg' value='Adicionar Item' onclick="GRID_ITENS.addRow();" />
										         	</td>
										         </tr>
										         <tr>
										         	<td colspan="2">
											         		<cit:grid id="GRID_ITENS" columnHeaders="citcorpore.comum.cabecalhoItens" styleCells="linhaGrid">
											         			<cit:column idGrid="GRID_ITENS" number="001">
											         				<span id='item#SEQ#' style="border: none; backtext-align: center; font-weight: bold; background-color: white;"></span>
											         			</cit:column>
											         			<cit:column idGrid="GRID_ITENS" number="002">
											         			<input type="hidden" id='idAtividadeServicoContrato#SEQ#' name='idAtividadeServicoContrato#SEQ#' size='12' maxlength='14' />
											         				<select id='complexidade#SEQ#' name='complexidade#SEQ#'>
											         					<option value='B'><fmt:message key="citcorpore.comum.complexidadeBaixa" /></option>
											         					<option value='I'><fmt:message key="citcorpore.comum.complexidadeIntermediaria" /></option>
											         					<option value='M'><fmt:message key="citcorpore.comum.complexidadeMediana" /></option>
											         					<option value='A'><fmt:message key="citcorpore.comum.complexidadeAlta" /></option>
											         					<option value='E'><fmt:message key="citcorpore.comum.complexidadeEspecialista" /></option>
											         				</select>
											         			</cit:column>
											         			<cit:column idGrid="GRID_ITENS" number="003" >
											         				<div style="width: 100%; height: 234px;">
											         					<div style="border-bottom:1px solid black;">
											         					    <textarea style="width: 705px; height: 100px ;border: none;" name="demanda#SEQ#" cols='45' rows='5' maxlength="1000"></textarea>
												         					<input type="hidden" name="formula#SEQ#" />
												         				</div>
												         				<input type="hidden" name="contabilizar#SEQ#" />
												         				<input type="hidden" name="idServicoContratoContabil#SEQ#" />
											         					<div style="width: 10px; border: 5px; padding: 5px; font-weight: bold;">
											         						 <fmt:message key="citcorpore.comum.observacoes" />:
											         					</div>
											         						<textarea style="width: 705px; height: 100px ;border: none;" name="obs#SEQ#" cols='45' rows='5' maxlength="1000"></textarea>
											         					</div>
											         			</cit:column>
											         			<cit:column idGrid="GRID_ITENS" number="004">
											         				<input type='text' name='quantidade#SEQ#' style="border: none; text-align: center; font-weight: bold;" size='25' maxlength='14' class='Format[Moeda]'/>
											         			</cit:column>
											         		</cit:grid>
										         	</td>
										         </tr>
										     <tr>
										     	<td colspan="2" style='text-align: right; border:2px solid black; '>
										     		<table width="99%" >
										     			<tr>
										     				<th style='text-align: right; width: 90%; vertical-align: middle !important;'>
										     					<fmt:message key="requisitosla.custo_total" />:
										     				</th>
										     				<td style='text-align: right;'>
										     					<span id='custoTotal'><b>0,00</b></span>
										     				</td>
										     			</tr>
										     		</table>
										     	</td>
										     </tr>
											 <tr>
									            <td colspan="2" class="campoObrigatorio"><fmt:message key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio" /></td>
									         </tr>
									         <tr>
									         	<td colspan='2'>
									         		<!-- <button type='button' name='btnGravar' onclick='gravarForm();'>Gravar</button> -->
									         		<button type='button' id="btnGravar" name='btnGravar' style="margin-top: 5px; margin-left: 3px" class="light img_icon has_text" onclick="gravarForm()">
															<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
														<span><fmt:message key="botaoacaovisao.gravar_dados" /></span>
													</button>
								         		</td>
								         	</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">

		var permiteValorZeroAtv = "${permiteValorZeroAtv}";
	</script>
	
	<script type="text/javascript" src="js/os.js"></script>
	
</body>
</html>