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
<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>

<title><fmt:message key="citcorpore.comum.title"/></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
    <link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />


<script>
	var iframe = "${iframe}";

</script>


<script type="text/javascript" src="js/produto.js"></script>


<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<link href="css/produto.css" rel="stylesheet" />
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
					<fmt:message key="produto.produto" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="produto.cadastro" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="produto.pesquisa" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/produto/produto'>
  								<input type='hidden' name='idCategoria' />
    							<input type='hidden' name='idUnidadeMedida' />
    							<input type='hidden' name='idMarca' />
								<input type='hidden' name='idTipoProduto' />
								<input type='hidden' name='idProduto' />

								<div class="columns clearfix">
								<div class="col_100">
									<div class="col_20">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.codigo" /></label>
											<div>
												<input type='text' name="codigoProduto" maxlength="25" class="Description[produto.codigoProduto]" readonly="readonly"/>
											</div>
										</fieldset>
									</div>
									<div class="col_60">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="tipoProduto" /></label>
											<div>
												<input type='text' id="nomeProduto" name="nomeProduto" readonly="readonly" maxlength="256"	class="Valid[Required] Description[tipoProduto] col_100" />
												<%-- <img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" onclick='$("#POPUP_TIPOPRODUTO").dialog("open")'>
												<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('tipoProduto', '')"> --%>
											</div>

										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
										<label >&nbsp;</label>
											<div>
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" onclick='$("#POPUP_TIPOPRODUTO").dialog("open")'>
												<img src="${ctx}/imagens/add.png" onclick="popup.abrePopup('tipoProduto', '')">
											</div>

										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<div class="col_60">
										<fieldset>
											<label><fmt:message key="produto.complemento" /></label>
											<div>
												<input type='text' id="complemento" name="complemento" maxlength="100" />
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<div class="col_70">
											<fieldset>
												<label ><fmt:message key="marca" /></label>
												<div>
													<input type='text' id="nomeMarca" name="nomeMarca" readonly="readonly"  maxlength="256"	/>
												</div>
											</fieldset>
										</div>
										<div class="col_30">
												<div style='margin-top:35px !important; padding:0px 0px 0px 0px !important'>
													<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" onclick='$("#POPUP_MARCA").dialog("open")'>
													<img src="${ctx}/imagens/add.png" 	onclick="popup.abrePopup('marca', '')">
												</div>
										</div>
									</div>
								</div>
								<div class="col_100">
									<div class="col_60">
										<fieldset>
											<label ><fmt:message key="produto.modelo" /></label>
											<div>
												<input type='text' name="modelo" maxlength="25" 	/>
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset>
											<label ><fmt:message key="citcorpore.comum.valorAproximado" /></label>
											<div>
												<input type='text' name="precoMercado" maxlength="6" class="Format[Moeda] " />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<div class="col_60">
										<fieldset>
											<label><fmt:message key="produto.detalhes" /></label>
											<div>
												<textarea name="detalhes" id="detalhes" rows="5" cols="4"> </textarea>
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.situacao" /></label>
											<div class="inline" >
												<label > <input type="radio" name="situacao" value="A" 	class="Valid[Required] Description[citcorpore.comum.situacao]" /><fmt:message key="citcorpore.comum.ativo" />  </label>
												<label ><input type='radio' name="situacao" value="I"	class="Valid[Required] Description[citcorpore.comum.situacao]" /><fmt:message key="citcorpore.comum.inativo" /> </label>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<!-- <div class="col_66"> -->
										<!-- <div class="col_100"> -->
												<fieldset>
												<label ><fmt:message key="baseConhecimento.anexos"/></label>
													<cit:uploadControl  style="height:50%;width:100%; border-bottom:1px solid #DDDDDD ; border-top:1px solid #DDDDDD"  title="<fmt:message key='citcorpore.comum.anexos' />" id="uploadAnexos" form="document.form" action="/pages/upload/upload.load" disabled="false" />
												</fieldset>
											<!-- </div>	 -->
									<!-- </div>	 -->
								</div>
								<div>
									<div class='col_100'>
										<label>&nbsp;</label>
									</div>
								</div>
								<div class="col_100">
									<fieldset>
										<button type='button' name='btnGravar' class="light"
											onclick='document.form.save();'>
											<img
												src="${ctx}/template_new/images/icons/small/grey/pencil.png">
											<span><fmt:message key="citcorpore.comum.gravar" />
											</span>
										</button>
										<button type='button' name='btnLimpar' class="light"
											onclick='limparForm();'>
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
									</fieldset>
							</div>
							</div>
							<div id="popupCadastroRapido" >
			                	<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
			            	</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_PRODUTO' id='LOOKUP_PRODUTO' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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


	<div id="POPUP_MARCA" title="<fmt:message key="marca" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaMarca' style="width: 440px">
							<cit:findField formName='formPesquisaMarca'
								lockupName='LOOKUP_MARCA' id='LOOKUP_MARCA' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_TIPOPRODUTO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaTipoProduto' style="width: 440px">
							<cit:findField formName='formPesquisaTipoProduto'
								lockupName='LOOKUP_TIPOPRODUTO' id='LOOKUP_TIPOPRODUTO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>




</body>
</html>
