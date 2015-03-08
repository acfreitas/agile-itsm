<%@page import="br.com.centralit.citcorpore.bean.EmpresaDTO"%>
<%@page import="br.com.centralit.citcorpore.versao.Versao" %>
<%@page import="br.com.citframework.util.Constantes" %>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<div class="clearfix"></div>
<!-- Inicio Rodape  -->
<% if(request.getParameter("iframe") == null) { %>
<div id="footer" class="hidden-print">

	<!--  Copyright Line -->
	<div class="copy">© 2014 - <a target="_blank" href="http://www.citsmart.com.br"><fmt:message key="citcorpore.comum.title"/></a> - <fmt:message key="citcorpore.todosDireitosReservados"/>  - <fmt:message key="login.versao"/> <b><%=Versao.getDataAndVersao()%> </div>
	<!--  End Copyright Line -->

</div>
<% } %>


<%@include file="/novoLayout/common/include/libRodape.jsp" %>
<!-- Fim Rodape  -->

<script type="text/javascript">
	$(document).ready(function() {
		<% if(request.getAttribute("Script") != null) { out.print(request.getAttribute("Script").toString()); }%>
	});
	function buscaHistoricoPorVersao() {
		document.formSobre.fireEvent("buscaHistoricoPorVersao");
	}
</script>

<% if(request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE") != null){ %>
<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
<%} %>
<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
<%@include file="/pages/ctrlAsterisk/ctrlAsterisk.jsp" %>
	<% if(request.getSession(true).getAttribute("permissaoBotao") != null) { out.print(request.getSession(true).getAttribute("permissaoBotao").toString()); }%>