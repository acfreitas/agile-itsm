<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.citframework.util.Constantes"%>

<%
	response.setCharacterEncoding("ISO-8859-1");

	String subForm = request.getParameter("subForm");
	if (subForm == null || subForm.equalsIgnoreCase("")){
		subForm = "N";
	}

	String idPessoaVisualizacaoHistCampos = (String)request.getAttribute("idPessoaVisualizacaoHistCampos");
	if (idPessoaVisualizacaoHistCampos == null){
		idPessoaVisualizacaoHistCampos = "";
	}
%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citquestionario.bean.LinhaSpoolQuestionario"%>
<%@page import="java.util.ArrayList"%>
		<table name="tblQuestoesSubQuest" border="0" id="tblQuestoesSubQuest" width="100%">
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
