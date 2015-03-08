<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String idRequisicaoLiberacao = UtilStrings.nullToVazio((String)request.getParameter("idRequisicaoLiberacao"));
	String nomeTarefa = UtilStrings.nullToVazio((String)request.getAttribute("nomeTarefa"));

%>
	<%@include file="/include/header.jsp"%>
    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>

</head>

<body>

<script>

	fechar = function(){
		parent.fecharVisao();
	}

	gravar = function() {
		if (StringUtils.isBlank(document.form.idGrupoDestino.value) && StringUtils.isBlank(document.form.idUsuarioDestino.value)){
			alert(i18n_message("gerenciaservico.delegartarefa.validacao.informeprazo"));
			document.formIdGrupoDestino.focus();
			return;
		}
		if (confirm(i18n_message("gerenciaservico.delegartarefa.confirm.delegaratividade")))
			document.form.save();
	}
</script>

<div id="wrapper">
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">



		 <div class="box grid_16 tabs">
		 <br><br>
<h4><fmt:message key="requisicaoLiberacao.requisicaoLiberacao"/>&nbsp;Nº&nbsp;<%=idRequisicaoLiberacao%>&nbsp;-&nbsp;<fmt:message key="tarefa.tarefa"/>&nbsp;<%=nomeTarefa%></h4>
	 <div class="toggle_container">
						<div class="">
							<form name='form' action='${ctx}/pages/delegacaoLiberacao/delegacaoLiberacao'>
								<div class="columns clearfix">
									<input type='hidden' name='idRequisicaoLiberacao' id='idRequisicaoLiberacao'/>
									<input type='hidden' name='idTarefa'/>
									<input type='hidden' name='acaoFluxo'/>

									<div class="col_66">
										<fieldset>
											<label style="cursor: pointer;"><fmt:message key="solicitacaoServico.atribuicaoGrupo"/></label>
											<div>
												<select name='idGrupoDestino' onchange="document.form.fireEvent('carregaUsuarios')"></select>
											</div>
										</fieldset>
										<fieldset>
											<label style="text-align:center"><fmt:message key="citSmart.comum.ou"/></label>
										</fieldset>
										<fieldset>
											<label style="cursor: pointer;"><fmt:message key="solicitacaoServico.atribuicaoUsuario"/></label>
											<div>
												<select name='idUsuarioDestino'></select>
											</div>
										</fieldset>
									</div>
								</div>

								<div>
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name='btnCancelar' class="light" onclick='fechar();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/bended_arrow_left.png">
										<span><fmt:message key="citcorpore.comum.cancelar" />
										</span>
									</button>
							    </div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
	 </div>
  </div>
 </div>
<!-- Fim da Pagina de Conteudo -->

</body>

</html>
