<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.citframework.util.Constantes"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
	response.setCharacterEncoding("ISO-8859-1");
	String idContratoQuestionario = (String)request.getParameter("idContratoQuestionario");
	String idQuestionario = (String)request.getParameter("idQuestionario");
	String idContrato = (String)request.getParameter("idContrato");
	String dataQuestionario = (String)request.getParameter("dataQuestionario");
	String subForm = (String)request.getParameter("subForm");
	String situacao = (String)request.getParameter("situacao");
	String HASH_CONTEUDO = (String)request.getParameter("HASH_CONTEUDO");
	if (subForm == null || subForm.equalsIgnoreCase("")){
		subForm = "N";
	}
	String aba = (String)request.getParameter("aba");
	if (aba == null){
		aba = "";
	}
	if (situacao == null){
		situacao = "";
	}
	if (HASH_CONTEUDO == null){
		HASH_CONTEUDO = "";
	}
%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<%@include file="/include/titleComum/titleComum.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

	<%@include file="/include/cssComuns/cssComuns.jsp" %>

	</head>

	<body>
		<script>
			function emitir(){
				document.formImprimirForm.action='${ctx}/pages/contratoQuestionarios/contratoQuestionarios';

				document.formImprimirForm.parmCount.value = '3';
				document.formImprimirForm.parm1.value = DEFINEALLPAGES_getFacadeName(document.formImprimirForm.action);
				document.formImprimirForm.parm2.value = '';
				document.formImprimirForm.parm3.value = 'imprimir';

				document.formImprimirForm.action='${ctx}/pages/contratoQuestionarios/contratoQuestionarios.event';
				document.formImprimirForm.submit();
			}
			function editar(){
				parent.chamaEdicaoQuestionario(<%=idContrato%>, <%=idQuestionario%>, 0, <%=idContratoQuestionario%>, false, 'N', '', '<%=dataQuestionario%>');
			}
		</script>
		<form name='formImprimirForm' method="POST" action="${ctx}/pages/ASO/ASO">
			<input type='hidden' name='idContratoQuestionario' value='<%=idContratoQuestionario%>'/>
			<input type='hidden' name='idQuestionario' value='<%=idQuestionario%>' />

		 	<input type='hidden' name='parmCount'/>
		 	<input type='hidden' name='parm1'/>
		 	<input type='hidden' name='parm2'/>
		 	<input type='hidden' name='parm3'/>
		 	<textarea name='HASH_CONTEUDO' id='HASH_CONTEUDO' style='display:none'><%=HASH_CONTEUDO%></textarea>
			<table>
				<tr>
					<td colspan="3">
						<b>Registro Gravado com sucesso!</b>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<input type='button' name='btnImprime' value='Clique para imprimir o contrato gravado' onclick='emitir()'/>
					</td>
					<td>
						&nbsp;
					</td>
					<td>
						<%if (!UtilStrings.nullToVazio(situacao).equalsIgnoreCase("F")){%>
						<input type='button' name='btnEditar' value='* Continuar Editando *' onclick='editar()' style='background-color: #FFFF99'/>
						<%}else{%>
						&nbsp;
						<%}%>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="3">
						Para fechar este tela, clique em fechar!
					</td>
				</tr>
			</table>
		</form>
<script>
	<%if (situacao.equalsIgnoreCase("F")){%>
		parent.chamaTelaAssinaturaDigital(document.formImprimirForm.HASH_CONTEUDO.value);
	<%}%>
</script>
	</body>
</html>

