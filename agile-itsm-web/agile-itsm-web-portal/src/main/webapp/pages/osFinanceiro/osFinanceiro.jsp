<%@page import="br.com.centralit.citcorpore.bean.DemandaDTO"%>
<%@page import="br.com.citframework.util.UtilFormatacao"%>
<%@page import="br.com.centralit.citcorpore.bean.OSDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
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
	function LOOKUP_PROJETO_select(id,desc){
		document.form.restore({idProjeto:id});
	}
	function fPesquisar(){
		if (!document.form.validate()){
			return;
		}
		document.form.action='${ctx}/pages/osFinanceiro/osFinanceiro.load';
		document.form.submit();
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
								<form name='form' action='${ctx}/pages/osFinanceiro/osFinanceiro' method="POST">
								  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
								         <tr>
								            <td class="campoEsquerda">Contrato*:</td>
								            <td>
								            	<select name='idContrato' class="Valid[Required] Description[Contrato]">
								            	</select>
								            </td>
								         </tr>
										<tr>
											<td class="campoEsquerda">
												Per&iacute;odo:
											</td>
											<td>
												<input type='text' name='dataInicio' size="10" maxlength="10" class="Valid[Required] Format[Data] Description[Data Início]"/> a <input type='text' name='dataFim' size="10" maxlength="10" class="Valid[Required] Format[Data] Description[Data Fim]"/>
											</td>
										</tr>
										<tr>
											<td colspan='2'>
								            	<input type='button' name='Pesquisar' value='Pesquisar' onclick='fPesquisar()'/>
											</td>
										</tr>
								    </table>
								    <table width="98%">
								    <%
								    Collection col = (Collection)request.getAttribute("colecao");
								    Double cotacaoMoeda = (Double)request.getAttribute("cotacaoMoeda");
								    if (cotacaoMoeda == null){
								    	cotacaoMoeda = new Double(1);
								    }
								    int iAux = 0;
								    if (col != null){
								    	for(Iterator it = col.iterator(); it.hasNext();){
								    		OSDTO osDto = (OSDTO)it.next();
								    		if (iAux > 0){
								    			out.print("<tr><td>&nbsp;</td></tr>");
								    		}
								    		%>
								    		<tr>
								    			<td colspan="2">
								    				OS Número: <b><font color="blue"><%=osDto.getNumero()%>/<%=osDto.getAno()%></font></b> &nbsp;&nbsp;-&nbsp;&nbsp; Execução Início: <b><%=UtilDatas.dateToSTR(osDto.getDataInicio())%></b> - Execução Final: <b><%=UtilDatas.dateToSTR(osDto.getDataFim())%></b>
								    			</td>
								    		</tr>
								    		<tr>
								    			<td style='border:1px solid black;' width="50%">
								    				Tarefa/Demanda:<br>
								    				<%=osDto.getDemanda()%>
								    			</td>
								    			<td style='border:1px solid black;' width="50%">
								    				Objetivo:<br>
								    				<%=osDto.getObjetivo()%>
								    			</td>
								    		</tr>
								    		<tr>
								    			<td colspan="2">
								    				<%if (osDto.getColItens() != null){%>
								    					<table style='border:1px solid black' width="100%">
								    							<tr>
								    								<td width="5%">
								    									<b>Item</b>
								    								</td>
								    								<td>
								    									<b>Atividade</b>
								    								</td>
								    								<td>
								    									<b>Custo Total</b>
								    								</td>
								    								<td>
								    									<b>Glosa</b>
								    								</td>
								    							</tr>
								    					<%
								    					int i = 1;
								    					double custoTotalAcumulado = 0;
								    					double glosasAcumulado = 0;
								    					for(Iterator it2 = osDto.getColItens().iterator(); it2.hasNext();){
								    						DemandaDTO demandaDto = (DemandaDTO)it2.next();%>
								    							<tr>
								    								<td rowspan="2" style='border:1px solid black'>
								    									<%=i%>
								    								</td>
								    								<td style='border:1px solid black'>
								    									<%=demandaDto.getDetalhamento()%>
								    								</td>
								    								<td style='border:1px solid black'>
								    									<%custoTotalAcumulado = custoTotalAcumulado + demandaDto.getCustoTotal();%>
								    									<%=UtilFormatacao.formatDouble(demandaDto.getCustoTotal(),2)%>
								    								</td>
								    								<td style='border:1px solid black'>
								    									<%
								    									if (demandaDto.getGlosa() == null){
								    										demandaDto.setGlosa(new Double(0));
								    									}
								    									glosasAcumulado = glosasAcumulado + demandaDto.getGlosa();
								    									%>
								    									<%=UtilFormatacao.formatDouble(demandaDto.getGlosa(),2)%>
								    								</td>
								    							</tr>
								    							<tr>
								    								<td colspan="3" style='border:1px solid black'>
								    									Observações:
								    									<b><%=demandaDto.getObservacao()%></b>
								    								</td>
								    							</tr>
								    					<%
								    						i++;
								    					}
								    					%>

								    							<tr>
								    								<td colspan="2">
								    									TOTAIS DA O.S. (<b><%=(i-1)%></b> atividades)
								    								</td>
								    								<td style='border:1px solid black; background-color: gray;'>
								    									<b><%=UtilFormatacao.formatDouble(custoTotalAcumulado,2)%></b>
								    								</td>
								    								<td style='border:1px solid black; background-color: gray;'>
								    									<b><%=UtilFormatacao.formatDouble(glosasAcumulado,2)%></b>
								    								</td>
								    							</tr>
								    						</table>
								    						<table style='border:1px solid black' width="100%">
								    							<tr>
								    								<td colspan="4">&nbsp;</td>
								    							</tr>
								    							<tr>
								    								<td rowspan="2">
								    									<b>Faturamento:</b>
								    								</td>
								    								<td style='border:1px solid black;'>
								    									<b>Custo aprovado</b>
								    								</td>
								    								<td style='border:1px solid black;'>
								    									<b>Valor de conversão</b>
								    								</td>
								    								<td style='border:1px solid black;'>
								    									<b>Valor em R$</b>
								    								</td>
								    							</tr>
								    							<tr>
								    								<td style='border:1px solid black; background-color: gray;'>
								    									<b><%=UtilFormatacao.formatDouble(custoTotalAcumulado - glosasAcumulado,2)%></b>
								    								</td>
								    								<td style='border:1px solid black; background-color: gray;'>
								    									<b><%=UtilFormatacao.formatDouble(cotacaoMoeda,2)%></b>
								    								</td>
								    								<td style='border:1px solid black; background-color: gray;'>
								    									<b><%=UtilFormatacao.formatDouble((custoTotalAcumulado - glosasAcumulado) * cotacaoMoeda,2)%></b>
								    								</td>
								    							</tr>
								    					</table>
								    				<%}%>
								    			</td>
								    		</tr>
								    		<%
								    		iAux++;
								    	}
								    }
								    %>
								    </table>
							    </form>
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
