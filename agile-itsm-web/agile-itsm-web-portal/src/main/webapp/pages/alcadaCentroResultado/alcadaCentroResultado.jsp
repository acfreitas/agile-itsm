<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO" %>

<!doctype html public "">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");
			
			String caminho = CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/";
		%>
		<%@include file="/include/header.jsp"%>

		<%@ include file="/include/security/security.jsp" %>

		<title>
			<fmt:message key="citcorpore.comum.title" />
		</title>

		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

		<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
		
		<script type="text/javascript">
			var caminho = "${caminho}";
		
		</script>

		<script type="text/javascript" src="js/alcadaCentroResultado.js"></script>

	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>
		<link type="text/css" rel="stylesheet" href="css/alcadaCentroResultado.css"/>
	<%
		}
	%>
	</head>
	<body>
		<div id="wrapper">
		<%
			if (iframe == null) {
		%>
			<%@ include file = "/include/menu_vertical.jsp" %>
		<%
			}
		%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null) {
			%>
				<%@ include file = "/include/menu_horizontal.jsp" %>
			<%
				}
			%>
				<div class="flat_area grid_16">
					<h2>
						<fmt:message key="alcadaCentroResultado" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<fmt:message key="alcadaCentroResultado.cadastro" />
							</a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top">
								<fmt:message key="alcadaCentroResultado.pesquisa" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/alcadaCentroResultado/alcadaCentroResultado">
									<div class="columns clearfix">
										<input type="hidden" id="idAlcadaCentroResultado" name="idAlcadaCentroResultado" />
										<input type="hidden" id="idCentroResultado" name="idCentroResultado" />
										<input type="hidden" id="idEmpregado" name="idEmpregado" />
										<input type="hidden" id="idAlcada" name="idAlcada" />

										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="colaborador.colaborador" />
												</label>
												<div>
													<div>
														<input type="text" id="nomeEmpregado" name="nomeEmpregado" readonly="readonly" style="width: 90% !important;"
															class="Valid[Required] Description[<ftm:message key='alcadaCentroResultado.nomeEmpregado' />]"
															maxlength="70" size="70" onclick="pesquisarColaborador();" />
														<img style="vertical-align: middle;"
															src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"
															onclick="pesquisarColaborador();" />
													</div>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="centroResultado" />
													<img style="vertical-align: middle;" src="${ctx}/imagens/add.png"
														onclick="abrirPopupCadastroCentroResultado();" />
												</label>
												<div>
													<div>
														<input type="text" id="nomeCentroResultado" name="nomeCentroResultado" readonly="readonly"
															class="Valid[Required] Description[<ftm:message key='alcadaCentroResultado.nomeCentroResultado' />]"
															style="width: 90% !important;" maxlength="70" size="70" onclick="pesquisarCentroResultado();" />
														<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png"
															style="vertical-align: middle;" onclick="pesquisarCentroResultado();" />
													</div>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<fmt:message key="alcada" />
													<img style="vertical-align: middle;" src="${ctx}/imagens/add.png"
														onclick="abrirPopupCadastroAlcada();" />
												</label>
												<div>
													<div>
														<input type="text" id="nomeAlcada" name="nomeAlcada" onclick="pesquisarAlcada();" readonly="readonly"
															class="Valid[Required] Description<ftm:message key='alcadaCentroResultado.nomeAlcada' />]"
															style="width: 90% !important;" maxlength="70" size="70" />
														<img onclick="pesquisarAlcada();" style="vertical-align: middle;"
															src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
													</div>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio" style="height: 30px;">
													<fmt:message key="centroResultado.situacao" />
												</label>
												<div>
													<input type="radio" id="situacaoAtivo" name="situacao" value="A" checked="checked" /><fmt:message key="citcorpore.comum.ativo" />
													<input type="radio" id="situacaoInativo" name="situacao" value="I" /><fmt:message key="citcorpore.comum.inativo" />
												</div>
											</fieldset>
										</div>
									</div>
									<br />
									<br />
									<button type="button" name="btnGravar" class="light" onclick="document.form.save();">
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
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<fmt:message key="citcorpore.comum.pesquisa" />
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa"
										lockupName="LOOKUP_ALCADACENTRORESULTADO"
										id="LOOKUP_ALCADACENTRORESULTADO"
										top="0"
										left="0"
										len="550"
										heigth="400"
										javascriptCode="true"
										htmlCode="true" />
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>
		<!-- JANELAS DE PESQUISA -->
		<div id="POPUP_PESQUISA_COLABORADOR" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<form name="formPesquisaColaborador">
							<cit:findField formName="formPesquisaColaborador"
							lockupName="LOOKUP_EMPREGADO"
							id="LOOKUP_PESQUISA_COLABORADOR"
							top="0" left="0" len="550" heigth="400"
							javascriptCode="true"
							htmlCode="true" />
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_PESQUISA_CENTRORESULTADO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<form name="formPesquisaCentroResultado">
							<cit:findField formName="formPesquisaCentroResultado"
							lockupName="LOOKUP_CENTRORESULTADO"
							id="LOOKUP_PESQUISA_CENTRORESULTADO"
							top="0" left="0" len="550" heigth="400"
							javascriptCode="true"
							htmlCode="true" />
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_PESQUISA_ALCADA" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<form name="formPesquisaAlcada">
							<cit:findField formName="formPesquisaAlcada"
								lockupName="LOOKUP_ALCADA"
								id="LOOKUP_PESQUISA_ALCADA"
								top="0" left="0" len="550" heigth="400"
								javascriptCode="true"
								htmlCode="true" />
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="popupCadastroRapido">
			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>