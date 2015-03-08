<%
	String parm = request.getParameter("parm");
	if (parm == null){
		parm = "";
	}
	String subForm = request.getParameter("subForm");
	if (subForm == null || subForm.equalsIgnoreCase("")){
		subForm = "N";
	}
	String aba = request.getParameter("aba");
	if (aba == null || aba.equalsIgnoreCase("")){
		aba = "";
	}
%>