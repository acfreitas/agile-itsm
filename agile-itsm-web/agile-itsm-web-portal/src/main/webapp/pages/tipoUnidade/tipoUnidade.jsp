<%@page import="com.lowagie.text.Document"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%
    //identifica se a p�gina foi aberta a partir de um iframe (popup de cadastro r�pido)
			String iframe = "";
			iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title"/></title>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
			<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
<script type="text/javascript" src="./js/tipoUnidade.js"></script>
<%
    //se for chamado por iframe deixa apenas a parte de cadastro da p�gina
			if (iframe != null) {
%>
<link rel="stylesheet" type="text/css" href="./css/tipoUnidade.css" />
<%
    }
%>
</head>
<body>
	<div id="wrapper">
		<%
		    if (iframe == null) {
		%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%
		    }
		%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%
			    if (iframe == null) {
			%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%
			    }
			%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="unidade.tipoUnidade" />
				</h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="tipoUnidade.cadastroTipoUnidade" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="tipoUnidade.pesquisaTipoUnidade" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/tipoUnidade/tipoUnidade'>
								<div class="columns clearfix">
									<div class="col_66">
										<input type='hidden' name='idTipoUnidade'  id="idTipoUnidade"/>
										<input type='hidden' name='idEmpresa' value="<%=WebUtil.getIdEmpresa(request)%>" />
										<input type='hidden' name='dataInicio' /> <input type='hidden' name='dataFim' />
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="tipoUnidade.nomeTipo" />
											</label>
											<div>
												<input type='text' name="nomeTipoUnidade" maxlength="80"
													class="Valid[Required] Description[tipoUnidade.nomeTipo]" />
											</div>
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
								<button type='button' name='btnUpDate' class="light"
									onclick='excluir();'>
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
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_TIPOUNIDADE' id='LOOKUP_TIPOUNIDADE' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
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


