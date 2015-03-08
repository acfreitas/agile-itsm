<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.CentroResultadoDTO" %>
<%@page import="br.com.centralit.citcorpore.bean.ProcessoNegocioDTO"%>
<%@page import="java.util.Collection"%>

<%
	String iframe = request.getParameter("iframe");
	Collection<ProcessoNegocioDTO> colProcessosNegocio = (Collection)request.getAttribute("colProcessosNegocio");
%>

<!DOCTYPE html>
<html>
<head>
	<%@include file="/include/header.jsp"%>
	<title><fmt:message key="citcorpore.comum.title" /></title>

	<%@ include file="/include/menu/menuConfig.jsp" %>

	<script type="text/javascript" src="../../cit/objects/ResponsavelCentroResultadoDTO.js"></script>

	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>
	<style>
		div#main_container {
			margin: 10px 10px 10px 10px;
		}
	</style>
	<%
		}
	%>
</head>
<body>
	<div id="wrapper">
	<%
		if (iframe == null) {
	%>
		<%@ include file="/include/menu_vertical.jsp" %>
	<%
		}
	%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
		<%
			if (iframe == null) {
		%>
			<%@ include file="/include/menu_horizontal.jsp" %>
		<%
			}
		%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="centroResultado" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1">
							<fmt:message key="centroResultado.cadastro" />
						</a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top">
							<fmt:message key="centroResultado.pesquisa" />
						</a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name="form" action="${ctx}>/pages/centroResultado/centroResultado">
								<div class="columns clearfix">
									<input type="hidden" id="idCentroResultado" name="idCentroResultado" />
									<input type="hidden" id="idCentroResultadoPai" name="idCentroResultadoPai" />
									<input type='hidden' name='colResponsaveis_Serialize' />

									<div class="col_60">
										<fieldset>
											<label class="campoObrigatorio">
												<fmt:message key="citcorpore.comum.nome" />
											</label>
											<div>
												<input type="text" id="nomeCentroResultado" name="nomeCentroResultado" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_40">
										<fieldset>
											<label>
												<fmt:message key="centroResultado.superior" />
											</label>
											<div>
												<div>
													<input type="text" id="nomeCentroResultadoPai" name="nomeCentroResultadoPai" onclick="consultarCentroResultadoPai();" readonly="readonly" style="width: 90% !important;" maxlength="70" size="70" />
													<img onclick="consultarCentroResultadoPai();" style="vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
												</div>
											</div>
										</fieldset>
									</div>
									<div class="col_60">
										<fieldset>
											<label class = "campoObrigatorio">
												<fmt:message key="centroResultado.codigo" />
											</label>
											<div>
												<input type="text" id="codigoCentroResultado" name="codigoCentroResultado" maxlength="25" class="Valid[Required] Description[centroResultado.codigo]" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio">
												<fmt:message key="centroResultado.permitirRequisicaoProduto" />
											</label>
											<div>
												<input type="radio" id="permiteRequisicaoProdutoSim" name="permiteRequisicaoProduto" value="S" checked="checked" /><fmt:message key="citcorpore.comum.sim" />
												<input type="radio" id="permiteRequisicaoProdutoNao" name="permiteRequisicaoProduto" value="N" /><fmt:message key="citcorpore.comum.nao" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio">
												<fmt:message key="centroResultado.situacao" />
											</label>
											<div>
												<input type="radio" id="situacaoAtivo" name="situacao" value="A" checked="checked" /><fmt:message key="citcorpore.comum.ativo" />
												<input type="radio" id="situacaoInativo" name="situacao" value="I" /><fmt:message key="citcorpore.comum.inativo" />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<div class="col_50">
											<h2 class="section">
												 <fmt:message key="centroResultado.responsaveis" />
											</h2>
											<div id='divNovoCriterio' class="col_100">
												<div class="col_33">
													<button type='button' class='light img_icon has_text' onclick='incluirResponsavel();'>
														<fmt:message key="centroResultado.incluirResponsavel" />
													</button>
												</div>
											</div>
											<div class="col_100">
												<fieldset>
													<div style='width:500px;overflow:auto;'>
													<cit:grid id="GRID_RESPONSAVEIS" columnHeaders="centroResultado.cabecalhoGridAlcadas" styleCells="linhaGrid">
														<cit:column idGrid="GRID_RESPONSAVEIS" number="001">
														<%
														if (colProcessosNegocio != null){
															for (ProcessoNegocioDTO obj : colProcessosNegocio) {
																out.println("<input type='checkbox' name='idProcessoNegocio#SEQ#' id='idProcessoNegocio#SEQ#' value='"+obj.getIdProcessoNegocio()+"'/>"+obj.getNomeProcessoNegocio()+"<br><br>");
															}
														}
														%>
														</cit:column>
														<cit:column idGrid="GRID_RESPONSAVEIS" number="002">
															<input type="hidden" id="idResponsavel#SEQ#" name="idResponsavel#SEQ#" />
															<input onclick='adicionarEmpregado("#SEQ#");' type="text" name="nomeEmpregado#SEQ#" id="nomeEmpregado#SEQ#" />
														</cit:column>
													</cit:grid>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="col_50">
											<h2 class="section">
												<fmt:message key="citcorpore.comum.historico" />&nbsp;<fmt:message key="centroResultado.responsaveis" />
											</h2>
											<div class="col_100" style="overflow:auto;">
												<table id="tblHistorico" class="table">
													<tr>
														<th ><fmt:message key="citcorpore.comum.responsavel" /></th>
														<th width="15%"><fmt:message key="pesquisa.datainicio" /></th>
														<th width="15%"><fmt:message key="pesquisa.datafim" /></th>
													</tr>
												</table>
											</div>
										</div>
									 </div>
								</div>
								<br>
								<br>
								<button type="button" name="btnGravar" class="light" onclick="gravar();">
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png" />
									<span>
										<fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type="button" name="btnLimpar" class="light" onclick='document.form.clear();document.form.fireEvent("load");'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png" />
									<span>
										<fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type="button" name="btnExcluir" class="light" onclick="excluir();">
									<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png" />
									<span>
										<fmt:message key="citcorpore.comum.excluir" />
									</span>
								</button>
								<button type="button" name="btnVisualizarHierarquia" class="light" onclick="visualizarHierarquiaCentrosResultado();">
									<img src="${ctx}/template_new/images/icons/small/grey/preview.png" />
									<span>
										<fmt:message key="centroResultado.visualizar" />
									</span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name="formPesquisa">
								<cit:findField formName="formPesquisa"
									lockupName="LOOKUP_CENTRORESULTADO" id="LOOKUP_CENTRORESULTADO" top="0" left="0"
									len="550" heigth="400" javascriptCode="true" htmlCode="true" />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_CENTRORESULTADOPAI" class="POPUP_CENTRORESULTADOPAI" title="<fmt:message key="citcorpore.comum.pesquisa" />" >
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section" style="padding: 33px;">
					<div >
						<form id="formCentroResultadoPai" name="formCentroResultadoPai" method="post"
							action="${ctx}>/pages/centroResultado/centroResultado">
							<cit:findField id="LOOKUP_CENTRORESULTADOPAI" formName="formCentroResultadoPai" lockupName="LOOKUP_CENTRORESULTADOPAI"
								top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_HIERARQUIA_CENTRORESULTADO" title="<fmt:message key="centroResultado.hierarquia" />">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section" style="padding: 33px;">
					<div id="divApresentacaoHierarquiaCentroResultado" title="<fmt:message key="centroResultado.hierarquia" />"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_EMPREGADO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaEmpregado' style="width: 540px">
							<input type="hidden" id="isNotificacao" name="isNotificacao">
							<cit:findField formName='formPesquisaEmpregado' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>

	<script type="text/javascript" src="${ctx}/pages/centroResultado/js/centroResultado.js"></script>
</body>
</html>
