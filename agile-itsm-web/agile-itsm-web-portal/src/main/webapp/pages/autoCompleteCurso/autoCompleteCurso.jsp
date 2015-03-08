<%
String json_response = (String)request.getAttribute("json_response");
if (json_response != null){
	out.print(json_response);
}
%>