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
<script type="text/javascript" src="./js/marca.js"></script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<link rel="stylesheet" type="text/css" href="./css/marca.css" />
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
					<fmt:message key="marca" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="marca.cadastro" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="marca.pesquisa" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/marca/marca'>
								<input type='hidden' name='idMarca' id = "idMarca" />
								<input type='hidden' name='idFabricante' id = "idFabricante" />
								<div class="columns clearfix">
								<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="marca.nomeMarca" /></label>
											<div>
												<input type='text' name="nomeMarca" id = "nomeMarca" maxlength="100"
													class="Valid[Required] Description[marca.nomeMarca]" />
											</div>
										</fieldset>
								</div>
								<div class="col_33">
										<fieldset>
											<label ><fmt:message key="marca.fabricante" /></label>
											<div>
												<input type='text' onfocus='abrePopupFornecedor();' name="nomeFabricante" id = "nomeFabricante" maxlength="100" />
											</div>
										</fieldset>
								</div>
								<div class="col_33">
										<fieldset style="height: 55px">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.situacao" /></label>
											<div  class="inline clearfix">
											<label><fmt:message key="citcorpore.comum.ativo" /><input type="radio"  name="situacao" value="A" checked="checked" /></label>
											<label><fmt:message key="citcorpore.comum.inativo" /><input type="radio"  name="situacao" value="I" /></label>
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
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_MARCA' id='LOOKUP_MARCA' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
<!-- 	 Desenvolvedor: Pedro Lino - Data: 30/10/2013 - Horário: 11:10 - ID Citsmart: 120948 -
		* Motivo/Comentário: form pequeno/ Alterado width para 660px  -->
<div id="POPUP_FORNECEDOR" title="<fmt:message key="marca.pesquisaFabricante" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaFabricante' style="width: 660px">
							<cit:findField formName='formPesquisaFabricante'
								lockupName='LOOKUP_FORNECEDOR' id='LOOKUP_FORNECEDOR' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</html>


