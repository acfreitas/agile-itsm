<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%
	String iframe = request.getParameter("iframe");
	String idServicoContrato = request.getParameter("idServicoContrato").toString();
	
%>
<%@include file="/include/security/security.jsp"%>
<%@include file="/include/header.jsp"%>
<title><fmt:message key="citcorpore.comum.title"/></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<%//se for chamado por iframe deixa apenas a parte de cadastro da página
	if (iframe != null) {%>
	<link type="text/css" rel="stylesheet" href="css/valorServicoContrato.css"/>
<%}%>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
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
				<h2><fmt:message key="contrato.valorServicoContrato" /></h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1">
					<fmt:message key="contrato.cadastroValorServicoContrato" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/valoresServicoContrato/valoresServicoContrato'>
								<input type='hidden' name='idServicoContrato' value="<%=request.getParameter("idServicoContrato").toString()%>"/>
								<input type='hidden' name='idValorServicoContrato' value="<%=request.getParameter("idValorServico").toString()%>" />
								<div class="columns clearfix">
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="contrato.valorServico" /></label>
											<div>
												<input type="text" class="Valid[Required] Format[Money] Description[Valor do Serviço]" size="9" maxlength="9" id="valorServico" name="valorServico">
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="contrato.dataInicioServicoContrato" /></label>
											<div>
												<input type="text" class="Valid[Date,Required] Description[Data Início] Format[Date] datepicker" size="10"
												maxlength="10" id="dataInicio" name="dataInicio">
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><fmt:message key="contrato.dataFimServicoContrato" /></label>
											<div>
												<input type="text" class="Valid[Date] Description[Data Fim] Format[Date] datepicker" size="10"
												maxlength="10" id="dataFim" name="dataFim">
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light" onclick='salvar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" /></span>
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
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
	
	<script type="text/javascript"
		var idServicoContrato = "${idServicoContrato}";
	</script>
	
	<script type="text/javascript" src="js/valorServicoContrato.js"></script>
</body>
</html>
