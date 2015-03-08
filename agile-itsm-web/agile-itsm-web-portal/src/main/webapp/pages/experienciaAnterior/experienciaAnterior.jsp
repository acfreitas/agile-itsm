<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%
			String iframe = "";
			iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title"/></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_CONHECIMENTO_select(id, desc) {
		document.form.restore({
			idConhecimento : id
		});
	}

	function excluir() {
		if (document.getElementById("idConhecimento").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>
<%}%>
</head>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="solicitacaoCargo.experienciaAnterior" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="solicitacaoCargo.experienciaAnterior" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="citcorpore.comum.pesquisa" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/experienciaAnterior/experienciaAnterior'>
								<input type='hidden' name='idConhecimento' id="idConhecimento" />
								<div class="columns clearfix">
									<div class="col_50">
				               			<fieldset style="height: 70px;">
				             				<label><fmt:message key="citcorpore.comum.descricao" /></label>
				             				<input type='text' name='descricao' size='50' maxlength="100"/>
				               			</fieldset>
				               	    </div>
				               	    <div class="col_100">
				               			<fieldset style="height: 130px;">
				             				<label><fmt:message key="solicitacaoCargo.detalhamento" /></label>
				             				<textarea name='detalhe' rows="5" cols="48"></textarea>
				               			</fieldset>
				             		</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='document.form.save();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir"
									class="light" onclick='excluir();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_CONHECIMENTO' id='LOOKUP_CONHECIMENTO' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>
