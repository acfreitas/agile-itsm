<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.citframework.util.Campo"%>
<%@page import="br.com.citframework.util.LookupFieldUtil"%>
<%@page import="br.com.citframework.util.UtilHTML"%>
<%@page import="br.com.citframework.util.UtilI18N"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%
Collection col = (Collection)request.getAttribute("retorno");
String nomeLookup = request.getParameter("nomeLookup");
String nomeLookupExec = nomeLookup;
String id = request.getParameter("id");
if (nomeLookup == null) nomeLookup = "";
if (id != null){
	if (!id.equalsIgnoreCase("")){
		nomeLookup = id;
	}
}

%> 
<table style="border: 1px solid" width="100%" cellspacing="0">
	<%
	
	//	
	boolean b;
	Campo cp;
	LookupFieldUtil lookUpField = new LookupFieldUtil();
	Collection colCamposRet = lookUpField.getCamposRetorno(nomeLookupExec);
	String strSeparaCampos = lookUpField.getSeparaCampos(nomeLookupExec);
	if (colCamposRet != null){
		Iterator itRet = colCamposRet.iterator();
		out.print("<tr>");	
		b = false;
		while(itRet.hasNext()){
			cp = (Campo)itRet.next();
			if (b){	
				if (!"IGNORE".equalsIgnoreCase(cp.getDescricao())){
					out.print("<td style='border: 1px solid;'>");
					out.print("<b>" + UtilHTML.encodeHTML(cp.getDescricao()) + "</b>");
					out.print("</td>");
				}
			}else{
				out.print("<td>&nbsp;</td>");
			}
			b=true;
		}	
		out.print("</tr>");
	}
	//
	
			if (col != null){
			Iterator it = col.iterator();
			Iterator it2;
			Collection colAux;
			if (!it.hasNext()){
				out.print("<tr>");
				out.print("<td>&nbsp;</td><td style='border: 1px solid;' colspan='8'>");
				out.print("<B><font color='RED'>"+UtilI18N.internacionaliza(request, "MSG04")+"</font></B>");
				out.print("</td>");		
				out.print("</tr>");
			}
			while(it.hasNext()){
		colAux = (Collection)it.next();
		it2 = colAux.iterator();
		String ret = "";
		b = false;
		while(it2.hasNext()){
			cp = (Campo)it2.next();
			if (!b){
				b=true;
			}else{
				if (strSeparaCampos.equalsIgnoreCase("S")){
					if (!ret.equalsIgnoreCase("")){
						ret = ret + ", ";
					}
					ret = ret + "'" + cp.getObjValue().toString() + "'";
				}else{
					if (!ret.equalsIgnoreCase("")){
						ret = ret + " - ";
					}
					if (cp.getObjValue() == null){
						ret = ret + "";
					}else{
						ret = ret + cp.getObjValue().toString();
					}
				}
			}
		}
		if (!strSeparaCampos.equalsIgnoreCase("S")){
			ret = "'" + ret + "'";
		}
		//
		it2 = colAux.iterator();
		out.print("<tr>");	
		b = false;
		while(it2.hasNext()){
			cp = (Campo)it2.next();
			if (!b){
				out.print("<td style='border: 1px solid;'>");
				String valor = "";
				if (cp.getObjValue() != null){
					valor = cp.getObjValue().toString();
				}
				if (valor == null) valor = "";
				out.print("<input type='radio' name='sel' value='"+cp.getObjValue()+"' onclick=\"setRetorno_"+nomeLookup.toUpperCase()+"('"+valor+"',"+ret+");\">");
				out.print("</td>");		
				b=true;
			}else{
				if (!"IGNORE".equalsIgnoreCase(cp.getDescricao())){
					out.print("<td style='border: 1px solid;'>");
					String valor = "&nbsp;";
					if (cp.getObjValue() != null){
						valor = cp.getObjValue().toString();
					}					
					out.print(valor);
					out.print("</td>");
				}
			}
		}
		out.print("</tr>");
			}
		}else{
			out.print("<tr>");
			out.print("<td>&nbsp;</td><td colspan='8'>");
			out.print("<B><font color='RED'>"+UtilI18N.internacionaliza(request, "MSG04")+"</font></B>");
			out.print("</td>");		
			out.print("</tr>");
		}
	%>
</table>