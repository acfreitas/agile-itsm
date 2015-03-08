<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
String json_retorno = (String)request.getAttribute("json_retorno");
out.print(json_retorno);
%>