<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.citframework.util.UtilHTML"%>
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
<%@include file="/include/security/security.jsp"%>
<%@include file="/include/header.jsp"%>

<title><fmt:message key="citcorpore.comum.title"/></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script charset="ISO-8859-1"  type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

<link type="text/css" rel="stylesheet" href="css/tipoProdutoAll.css"/>

<script type="text/javascript" src="js/tipoProduto.js"></script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
	<link type="text/css" rel="stylesheet" href="css/tipoProdutoIframe.css"/>
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
					<fmt:message key="tipoProduto" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="tipoProduto.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="tipoProduto.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/TipoProduto/TipoProduto'>
								<input type="hidden" id="idTipoProduto" name="idTipoProduto" />
									<input type="hidden" id="relacionamentosSerializados" name="relacionamentosSerializados" />
									<input type="hidden" id="relacionamentoProduto" name="relacionamentoProduto" />
									<input type="hidden" id="fornecedor" name="fornecedor" />
									<input type="hidden" id="fornecedorProduto" name="fornecedorProduto" />
								<input type="hidden" id="idCategoria" name="idCategoria" />
								<input type="hidden" id="idUnidadeMedida" name="idUnidadeMedida" />
								<input type="hidden" id="fornecedoresSerializados" name="fornecedoresSerializados" />

								<div class="columns clearfix">
									<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="tipoProduto.nome" /></label>
										<div>
											<input type='text' name="nomeProduto" maxlength="70" class="Valid[Required] Description[tipoProduto.nome]" />
										</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="tipoProduto.Categoria" /></label>
										<div>
										<input type='text' name="nomeCategoria" id="categoriaProduto" onclick="abrePopupCategoriaProduto();" maxlength="50" size="50" readonly="readonly" class="Valid[Required] Description[tipoProduto.Categoria]" />
										</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="tipoProduto.unidadeMedida" /></label>
										<div>
										<input type='text' name="nomeUnidadeMedida" id="unidadeMedida" onclick="abrePopupUnidadeMedida();" maxlength="50" size="50" readonly="readonly" class="Valid[Required] Description[tipoProduto.unidadeMedida]"  />
										</div>
										</fieldset>
									</div>
									</div>
									<div class="columns clearfix">
									<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="tipoProduto.aceitaRequisicao" /></label>
										<div  class="inline clearfix">
										<label><input type="radio" id="aceitaRequisicao" name="aceitaRequisicao" value="S" checked="checked" /><fmt:message key="citcorpore.comum.sim" /></label>
										<label><input type="radio" id="aceitaRequisicao" name="aceitaRequisicao" value="N" /><fmt:message key="citcorpore.comum.nao" /></label>
										</div>
										</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="tipoProduto.situacao" /></label>
										<div  class="inline clearfix">
										<label>
												<input type="radio" id="situacao" name="situacao" value="A" checked="checked" /><fmt:message key="citcorpore.comum.ativo" />
										</label>
										<label>
											<input type="radio" id="situacao" name="situacao" value="I" /><fmt:message key="citcorpore.comum.inativo" />
										</label>
										</div>
										</fieldset>
									</div>
								</div>
									<div  class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label id="addFornecedor" style="cursor: pointer;"
												title="<fmt:message  key="citcorpore.comum.cliqueParaAdicinar" />"><fmt:message key="cotacao.fornecedores" /><img
												src="${ctx}/imagens/add.png"></label>
											<div  id="gridFornecedores">
												<table class="table" id="tabelaFornecedor"
													style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 50%;"><fmt:message key="cotacao.fornecedores" /></th>
														<th style="width: 49%;"><fmt:message  key="marca" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div  class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label id="addTipoProdutoRelacionado" style="cursor: pointer;"
												title="<fmt:message  key="citcorpore.comum.cliqueParaAdicinar" />"><fmt:message key="tipoProduto.relacionamento" /><img
												src="${ctx}/imagens/add.png"></label>
											<div  id="gridTiposProdutosRelacionados">
												<table class="table" id="tabelaTipoProdutoRelacionado"
													style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 97%;"><fmt:message key="tipoProduto" /></th>
														<th style="width: 1%;"><fmt:message  key="tipoProduto.acessorio" /></th>
														<th style="width: 1%;"><fmt:message  key="tipoProduto.prodSemelhante" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear(); deleteAllRows();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' class="light"
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
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_TIPOPRODUTO' id='LOOKUP_TIPOPRODUTO' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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

<div id="POPUP_CATEGORIAPRODUTO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formCategoriaProduto' style="width: 540px">
							<cit:findField formName='formCategoriaProduto'
								lockupName='LOOKUP_CATEGORIAPRODUTO' id='LOOKUP_CATEGORIAPRODUTO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

<div id="POPUP_UNIDADEMEDIDA" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formUnidadeMedida' style="width: 540px">
							<cit:findField formName='formUnidadeMedida'
								lockupName='LOOKUP_UNIDADEMEDIDA' id='LOOKUP_UNIDADEMEDIDA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

<div id="POPUP_TIPOPRODUTORELACIONADO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formTipoProduto' style="width: 540px">
							<cit:findField formName='formTipoProduto'
								lockupName='LOOKUP_TIPOPRODUTO' id='LOOKUP_TIPOPRODUTORELACIONADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

<div id="POPUP_FORNECEDOR" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaFabricante' style="width: 520px">
							<cit:findField formName='formPesquisaFabricante'
								lockupName='LOOKUP_FORNECEDOR' id='LOOKUP_FORNECEDOR' top='0' left='0' len='500' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

<div id="POPUP_MARCA" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formMarca' style="width: 540px">
							<cit:findField formName='formMarca'
								lockupName='LOOKUP_MARCA' id='LOOKUP_MARCA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</html>
