<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<%UsuarioDTO usuario = WebUtil.getUsuario(request);%>
<%if (usuario != null){%>
<title>CentralIT - CitCorpore (<%=usuario.getNomeUsuario() == null ? "" : usuario.getNomeUsuario()%>)</title>
<%}else{%>
<title>Citsmart</title>
<%}%>