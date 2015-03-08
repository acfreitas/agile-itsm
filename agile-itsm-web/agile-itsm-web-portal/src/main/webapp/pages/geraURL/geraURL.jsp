<%
String url = (String)request.getAttribute("url");
if (url != null){
	response.sendRedirect(url);
}
%>