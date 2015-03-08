<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/security/security.jsp" %>
<%@include file="/include/header.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<script type="text/javascript">
function fecharPopup(){
	window.parent.$('#popupCadastroRapido').dialog('close');
}
</script>
</head>
<body>

	<form name='form' action='${ctx}/pages/opiniao/opiniao'>

		<div class="columns clearfix">
		 <%	if(request.getParameter("idSolicitacao") != null)	{		%>
		 <input type='hidden' name='idSolicitacao' id='idSolicitacao' value='<%= request.getParameter("idSolicitacao") %>'/>
		 <% } %>


			<div class="col_50">
				<fieldset>
					<label><fmt:message key="citcorpore.comum.tipo" /></label>
						<div>
						  	<input type="radio" value="Elogio" class="Valid[Required] Description[Tipo]" id="tipo" name="tipo"><fmt:message key="portal.elogio" />
						  	<input type="radio" value="Queixa" class="Valid[Required] Description[Tipo]" id="tipo" name="tipo"><fmt:message key="portal.queixa"/>
						</div>
				</fieldset>
			</div>
		</div>
		<div class="columns clearfix">
		  <div class="col_100">
				<fieldset>
					<label><span class="campoEsquerda"><fmt:message key="citcorpore.comum.observacoes" /></span></label>
						<div>
						  <textarea name="observacoes" id="observacoes" class="Valid[Required] Description[Observações]" maxlength="200" cols='70' rows='5'></textarea>
						</div>
				</fieldset>
			</div>
		</div>

		<br><br>

	<button type='button' name='btnGravar' class="light"  onclick='document.form.save();'>
		<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
		<span><fmt:message key="portal.gravarDados"/></span>
	</button>
	<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
		<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
			<span><fmt:message key="portal.limparDados"/></span>
		</button>
	</form>

</body>
</html>
