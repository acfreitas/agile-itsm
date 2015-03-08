<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<%@include file="/include/header.jsp" %>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>

	<%
		response.setCharacterEncoding("ISO-8859-1");

		String permiteValorZeroAtv = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.OS_VALOR_ZERO, "N");
		if (permiteValorZeroAtv == null){
			permiteValorZeroAtv = "N";
		}

		pageContext.setAttribute("permiteValorZeroAtv", permiteValorZeroAtv);
	%>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/text.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/grid.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css">
	<link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="./css/osSetSituacao.css">

	<script type="text/javascript" src="../../cit/objects/DemandaDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/GlosaOSDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/VinculaOsIncidenteDTO.js"></script>

	<script type="text/javascript">
		var properties = { permiteValorZeroAtv: "${permiteValorZeroAtv}"};
	</script>
	<script type="text/javascript" src="./js/osSetSituacao.js"></script>

</head>
<body>
	<!-- Definicoes Comuns -->
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>
	<div id="paginaTotal">
		<div id="areautil">
			<div id="formularioIndex">
	       		<div id=conteudo>
					<table width="100%">
						<tr>
							<td width="100%">
									<h2><b>Gerar R.A.</b></h2>
									<!-- ## AREA DA APLICACAO ## -->
									 	<form name='form' action='${ctx}/pages/osSetSituacao/osSetSituacao'>
									 		<input type='hidden' name='idOS'/>
									 		<input type='hidden' name='idOSPai'/>
									 		<input type='hidden' name='idContrato'/>
									 		<input type='hidden' name='seqANS'/>
									 		<input type='hidden' name='fieldANS'/>
									 		<input type='hidden' name='colItens_Serialize'/>
									 		<input type='hidden' name='colQtdExec_Serialize'/>
									 		<input type='hidden' name='colGlosas_Serialize'/>
									 		<input type='hidden' name='flagGlosa'/>
									 		<input type='hidden' name='idServicoContratoContabil'/>
										  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
										         <tr>
										           <!--  <td class="campoEsquerda">Serviço*:</td> -->
										            <td>
										            <div style="display: none" >
										            	<select name='idServicoContrato'  onchange="selecionaServicoContrato()">
										            	</select>
										            	</div>
										            </td>
										         </tr>
										         <tr>
										           <!--  <td class="campoEsquerda">Número*:</td> -->
										            <td>
										            <div style="display: none" >
										            	<input type='text' name='numero' size="20" maxlength="20" />
										            </div>
										            </td>
										         </tr>
										         <tr>
										            <!-- <td class="campoEsquerda">Área requisitante*:</td> -->
										            <td>
										            <div style="display: none" >
										            	<input type='text' name='nomeAreaRequisitante' size="80" maxlength="150" style="width: 500px !important;"/>
										           	</div>
										            </td>
										         </tr>
										         <tr>
										           <!--  <td class="campoEsquerda">Ano*:</td> -->
										            <td>
										            <div style="display: none" >
										            	<input type='text' name='ano' size="4" maxlength="4" style="width: 80px !important;" />
										            </div>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda">Data Início*:</td>
										            <td>
										            	<input type='text' name='dataInicio' id='dataInicio' size="10" maxlength="10" style="width: 100px !important;" class="Format[Date] Valid[Required,Date] Description[Data Início] text datepicker"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda">Data Fim*:</td>
										            <td>
										            	<input type='text' name='dataFim' id='dataFim' size="10" maxlength="10" style="width: 100px !important;" class="Format[Date] Valid[Required,Date] Description[Data Fim] text datepicker"/>
										            </td>
										         </tr>
	        									<tr>
										            <td class="campoEsquerda">Quantidade*:</td>
										            <td>
										            	<input type='text' name='quantidade' id='quantidade' size="10" maxlength="4" style="width: 40px !important;" onblur="alterarValorAtiviade();" class="Format[Numero] Valid[Required] Description[Quantidade]"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda" style='vertical-align: middle;'>Tarefa/Demanda:</td>
										            <td>
										            	<textarea name="demanda" cols='120' rows='5' style="border: 1px solid black"></textarea>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda" style='vertical-align: middle;'>Objetivo:</td>
										            <td>
										            	<textarea name="objetivo" cols='120' rows='5' style="border: 1px solid black"></textarea>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda">Situação:*</td>
										            <td>
										            	<select name='situacaoOS' id='situacaoOS' class="Valid[Required] Description[Situação]" ></select>
										            </td>
										         </tr>
										         <%
										         	String ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS, "N");
										         	if ((ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS!=null)&&(ATIVAR_ASSINATURA_PERSONALIZADA_REL_OS.equalsIgnoreCase("S"))){
										         %>
											         <tr>
											            <td class="campoEsquerda"></td>
											            <td>
											            	<div style="display: none" >
											            	<select name='idGrupoAssinatura' id='idGrupoAssinatura' class="Valid[Required] Description[grupoAssinatura.label]"></select>
											            	</div>
											            </td>
											         </tr>
										         <%
										         	}
										         %>
										         <tr>
										         	<td></td>
										         	<td>

													</td>
										         </tr>
										         <tr>
										         	<td colspan="2">
										         		<cit:grid id="GRID_ITENS" columnHeaders="Item;Complexidade;Atividade;Custo Total;Qtde executada;Glosa" styleCells="linhaGrid">
										         			<cit:column idGrid="GRID_ITENS" number="001">
									         					<span id='item#SEQ#' style="border: none; backtext-align: center; font-weight: bold; background-color: white;"></span>
									         				</cit:column>
										         			<cit:column idGrid="GRID_ITENS" number="002">
										         				<input type='hidden' name='idAtividadesOS#SEQ#' id='idAtividadesOS#SEQ#' size='12' maxlength='14' />
										         				<select name='complexidade#SEQ#' id='complexidade#SEQ#'>
										         					<option value='B'>Baixa</option>
										         					<option value='I'>Intermediária</option>
										         					<option value='M'>Mediana</option>
										         					<option value='A'>Alta</option>
										         					<option value='E'>Especialista</option>
										         				</select>
										         			</cit:column>
										         			<cit:column idGrid="GRID_ITENS" number="003" >
										         				<div style="width: 100%; height: 234px;">
										         					<div style="border-bottom:1px solid black;">
											         					<div id="divDemanda#SEQ#" style="width: 600px; height: 74px; overflow: auto;" ></div>
																		<div id="divPopUpAssociacaoInc#SEQ#" style="display: none;"></div>
											         					<input type="hidden" name="demanda#SEQ#" />
											         					<input type="hidden" name="formula#SEQ#" />
											         				</div>
											         				<input type='hidden' name='idServicoContratoContabil#SEQ#' id='idServicoContratoContabil#SEQ#'/>
											         				<div id="divAssociacaoInc#SEQ#" style="border-bottom:1px solid black; display: none;"></div>
										         					<div style="overflow: auto;">
											         					<div style="width: 10px; border: 5px; padding: 5px; font-weight: bold;">
											         						 Observações:
											         					</div>
											         						<textarea style="width: 600px; height:auto;border: none;" name="obs#SEQ#" cols='45' rows='5'></textarea>
											         					</div>
										         					</div>
										         			</cit:column>
										         			<cit:column idGrid="GRID_ITENS" number="004">
										         				<input type='text' name='quantidade#SEQ#' style="border: none; text-align: center; font-weight: bold;" size='12' maxlength='14' class='Format[Moeda]'/>
										         			</cit:column>
										         			<cit:column idGrid="GRID_ITENS" number="005">
										         				<input type='text' name='qtdeExecutada#SEQ#' onchange="serealizaCustoExecutado();" style="border: none; text-align: center; font-weight: bold;" size='12' maxlength='14' class='Format[Moeda]'/>
										         			</cit:column>
										         			<cit:column idGrid="GRID_ITENS" number="006">
										         				<input type='text' name='glosa#SEQ#' style="border: none; text-align: center; font-weight: bold;" size='12' maxlength='14' class='Format[Moeda]' value="0,00"/>
										         			</cit:column>
										         		</cit:grid>
										         	</td>
										         </tr>
										         <tr>
											     	<td colspan="2" style='text-align: right; border:2px solid black; '>
											     		<table width="99%" >
											     			<tr>
											     				<td style='text-align: right; width: 90%; padding-bottom: 10px;'><b>Custo Total Previsto: </b></td>
											     				<td style='text-align: right;'>
											     					<span id='custoTotalPrevisto'><b>0,00</b></span>
											     				</td>
											     			</tr>
											     			<tr>
											     				<td style='text-align: right; width: 90%;'><b>Custo Total Executado: </b></td>
											     				<td style='text-align: right;'>
											     					<input style="color: black; text-align:right; font-weight: bold; background: none; border: none;" name="executadoOS" id="executadoOS" value="0,00"/>
											     				</td>
											     			</tr>
											     		</table>
											     	</td>
											     </tr>
										         <tr>
										         	<td colspan="2" style='text-align: right;'>
										         		<div id='divBotaoGlosaOS' style='display:none; text-align: right; width: 100%'>
										         		<!-- 	<input type='button' name='btnAddGlosaOS' id='btnAddGlosaOS' value='Adicionar Glosa na O.S.' onclick="GRID_GLOSAS.addRow();" /> -->
										         		<button type='button' id="btnAddGlosaOS" name='btnAddGlosaOS' style="margin-top: 5px; margin-left: 3px; display: none;" class="light img_icon has_text"  onclick="carregaGlosas();">
															<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
															<span style="font-size: 12px !important;">Adicionar Glosa na O.S.</span>
														</button>
										         		</div>
										         	</td>
										         </tr>
										         <tr>
										         	<td colspan="2">
										         		<div id='divGlosas' style='display:none'>
											         		<cit:grid id="GRID_GLOSAS" columnHeaders="Descrição da Glosa aplicada na O.S.;Número de Ocorrências;Detalhamento da Ocorrência;% aplicado;Custo da Glosa" styleCells="linhaGrid">
											         			<cit:column idGrid="GRID_GLOSAS" number="001">
											         				<input type='hidden' name='idGlosaOS#SEQ#' id='idGlosaOS#SEQ#'/><textarea name="descricaoGlosa#SEQ#" id="descricaoGlosa#SEQ#" cols='45' rows='5' maxlength='500'></textarea>
											         			</cit:column>
											         			<cit:column idGrid="GRID_GLOSAS" number="002">
											         				<input type='text' name='numeroOcorrencias#SEQ#' id='numeroOcorrencias#SEQ#' size='12' maxlength='8' class='Format[Numero]'/>
											         			</cit:column>
											         			<cit:column idGrid="GRID_GLOSAS" number="003">
											         				<textarea name="ocorrencias#SEQ#" id="ocorrencias#SEQ#" cols='45' rows='5' maxlength='500'></textarea>
											         			</cit:column>
											         			<cit:column idGrid="GRID_GLOSAS" number="004">
											         				<input type='text' name='percAplicado#SEQ#' id='percAplicado#SEQ#' size='12' maxlength='8' class='Format[Moeda]' value="0,00" />
											         			</cit:column>
											         			<cit:column idGrid="GRID_GLOSAS" number="005">
											         				<input type='text' name='custoGlosa#SEQ#' id='custoGlosa#SEQ#' size='12' maxlength='8' class='Format[Moeda]' value="0,00" />
											         			</cit:column>
											         		</cit:grid>
											         		<span style='color: red'>&nbsp;Atenção: Os valores relativos ao custo da Glosa serão atualizados somente após a gravação da OS.</span>
										         		</div>
										         	</td>
										         </tr>
										         <tr>
											     	<td colspan="2" style='text-align: right; border:2px solid black; '>
											     		<table width="99%" >
											     			<tr>
											     				<td style='text-align: right; width: 90%;'>
											     					<b>Custo Total da Glosa:</b>
											     				</td>
											     				<td style='text-align: right;'>
											     					<span id='custoTotalGlosa'><b>0,00</b></span>
											     				</td>
											     			</tr>
											     		</table>
											     	</td>
											     </tr>
										         <tr>
										            <th style='vertical-align: middle;'>Observações finais (será apresentada no RA):</th>
										            <td>
										            	<textarea name="obsFinalizacao" id="obsFinalizacao" cols='120' rows='5'style="border: 1px solid black; margin-top: 10px;" maxlength="1500"></textarea>
										            </td>
										         </tr>
											 <tr>
									            <td colspan="2" class="campoObrigatorio"> Campos com preenchimento obrigat&oacute;rio</td>
									         </tr>
									         <tr>
									         	<td colspan='2'>
									         	<!-- 	<button type='button' name='btnGravar' id='btnGravar' onclick='gravarForm();'>Gravar</button> -->
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

	<div id='POPUP_LISTA_INCIDENTES' style='display:none;'>
		<form name='formAssociar' action='${ctx}/pages/osSetSituacao/osSetSituacao'>
			<div id='conteudoPopUp'></div>
		</form>
	</div>
</body>
</html>
