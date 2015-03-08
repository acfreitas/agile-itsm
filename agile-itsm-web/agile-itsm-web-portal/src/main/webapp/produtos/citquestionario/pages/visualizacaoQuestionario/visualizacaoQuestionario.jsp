<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%
	response.setCharacterEncoding("ISO-8859-1");

	String tabela100 = request.getParameter("tabela100");
	if (tabela100 == null || tabela100.equalsIgnoreCase("")){
		tabela100 = "870px";
	}else{
		if (tabela100.equalsIgnoreCase("true")){
			tabela100 = "100%";
		}else{
			tabela100 = "800px";
		}
	}
	String subForm = request.getParameter("subForm");
	if (subForm == null || subForm.equalsIgnoreCase("")){
		subForm = "N";
	}

	String idPessoaVisualizacaoHistCampos = (String)request.getAttribute("idPessoaVisualizacaoHistCampos");
	if (idPessoaVisualizacaoHistCampos == null){
		idPessoaVisualizacaoHistCampos = "";
	}

	String bufferAposLoad = (String)request.getAttribute("bufferAposLoad");
	if (bufferAposLoad == null){
		bufferAposLoad = "";
	}
	pageContext.setAttribute("bufferAposLoad",bufferAposLoad);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citquestionario.bean.LinhaSpoolQuestionario"%>
<%@page import="java.util.ArrayList"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>


	<%@include file="/include/header.jsp"%>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>

	<script type="text/javascript" src="${ctx}/js/boxover.js"></script>
	<script type="text/javascript" src="${ctx}/js/CollectionUtils.js"></script>

	<script>
		var oldLink = null;
		var ctx = "${ctx}";
		var bufferAposLoad = "${bufferAposLoad}";
	</script>

	<link href="${ctx}/produtos/citquestionario/pages/visualizacaoQuestionario/css/visualizacaoQuestionario.css" rel="stylesheet" />
	
	<script type="text/javascript" src="${ctx}/produtos/citquestionario/pages/visualizacaoQuestionario/js/visualizacaoQuestionario.js"></script>


	<%@include file="/produtos/citquestionario/include/includeHeadCITQuestionario.jsp"%>
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>



<body>
	<%@include file="/produtos/citquestionario/include/includeTOPCITQuestionario.jsp"%>

	<form name='formQuestionario' id='formQuestionario' method="post" action="${ctx}/pages/contratoQuestionarios/contratoQuestionarios" accept-charset="UTF-8" onsubmit="document.charset = 'UTF-8'">
		<input type='hidden' name='idContrato'/>
		<input type='hidden' name='idQuestionario'/>
		<input type='hidden' name='aba'/>
		<input type='hidden' name='idItem'/>
		<input type='hidden' name='idContratoQuestionario'/>
		<input type='hidden' name='dataQuestionario'/>
		<input type='hidden' name='dataAtual' value='<%=UtilDatas.dateToSTR(UtilDatas.getDataAtual())%>'/>
		<input type='hidden' name='situacao'/>
		<input type='hidden' name='idServicoContrato' id='idServicoContratoQuest' />
    	<input type='hidden' name='idServico' id='idServicoQuest'  />
    	<input type='hidden' name='obrigatorio' id='obrigatorio'  />


		<input type='hidden' name='subForm' value="<%=subForm%>"/>

		<input type='hidden' name='produtos'/>

        <select style='display:none' name='idQuestoesCalculadas' id='idQuestoesCalculadas' >
        </select>
	<div style="background-color: white !important; width:100%" >
		<table name="tblQuestoes" border="0" id="tblQuestoes" width="50%" style="background-color: white !important; margin: 0px 10px 0px 10px">
			<%
			Collection colLinhas = (Collection)request.getAttribute("linhasQuestionario");
			if (colLinhas != null){
				for(Iterator it = colLinhas.iterator(); it.hasNext();){
					LinhaSpoolQuestionario linhaQuestionario = (LinhaSpoolQuestionario)it.next();
					if (linhaQuestionario.isGenerateTR()){
						//out.println("<tr><td>");
					}

					out.println(linhaQuestionario.getLinha());

					if (linhaQuestionario.isGenerateTR()){
						//out.println("</td></tr>");
					}
				}
			}
			%>
		</table>
		</div>
	</form>

	<%
	String modo = request.getParameter("modo");
	if ("somenteleitura".equalsIgnoreCase(modo)){
	%>
	<script>
		HTMLUtils.lockForm(document.formQuestionario);
	</script>
	<%
	}
	%>
<script>
</script>

<%@include file="/produtos/citquestionario/include/includeBottomCITQuestionario.jsp"%>
</body>
</html>
