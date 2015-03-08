<%@ page import="br.com.citframework.util.UtilStrings" %>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados" %>
<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@ page import="br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO" %>
<%@ page import="br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO" %>
<%@ page import="br.com.citframework.util.Constantes" %>

<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

<%
	String googleApiKey = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GOOGLE_API_KEY_WEB, "undefined");
	String googleClientIDForWork = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GOOGLE_API_KEY_WEB_FOR_WORK, "undefined");
	pageContext.setAttribute("googleApiKey", googleApiKey);
	pageContext.setAttribute("googleClientIDForWork", googleClientIDForWork);
%>

<!DOCTYPE html>
<compress:html
	enabled="true"
	jsCompressor="closure"
	compressCss="true"
	compressJavaScript="true"
	removeComments="true"
	removeMultiSpaces="true">
<html>
	<head>
		<%
			String iframe = request.getParameter("iframe");

			String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");

			if(!UtilStrings.isNotVazio(controleAccUnidade)) {
				controleAccUnidade = "N";
			}
		%>

		<%@include file="/include/header.jsp"%>
		<title><fmt:message key="citcorpore.comum.title" /></title>

		<meta name="viewport" content="width=device-width, initial-scale=1.0">

		<%@ include file="/include/security/security.jsp" %>

		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

		<%
			//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {
		%>
		<style>
			div#main_container {
				margin: 10px;
			}
		</style>
		<%
			}
		%>

		<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="${waitingWindowMessage}" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	</head>

	<body>
		<script type="text/javascript" src="../../cit/objects/ServicoDTO.js"></script>
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
				<%@ include file="/include/menu_horizontal.jsp" %>
				<%
					}
				%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="unidade.unidade" /></h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li><a href="#tabs-1"><fmt:message key="unidade.cadastroUnidade" /></a></li>
						<li><a href="#tabs-2" class="round_top"><fmt:message key="unidade.pesquisaUnidade" /></a></li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div id="alerts"></div>
							<div class="section">
								<form name="form" action="${ctx}/pages/unidade/unidade">
									<input type="hidden" name="locale" value="" />
									<input type="hidden" name="dataInicio" />
									<input type="hidden" name="dataFim" />
									<input type="hidden" name="servicosSerializados" />
									<input type="hidden" name="servicosDeserializados" />
									<input type="hidden" name="servicoSerializado" id="servicoSerializado" />
									<input type="hidden" name="localidadesSerializadas" />
									<input type="hidden" name="idEndereco" />

									<div class="columns clearfix">
										<input type="hidden" name="idUnidade" />
										<input type="hidden" name="idEmpresa"/>

										<div class="col_100">
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome" />
													</label>
													<div>
														<input type="text" name="nome" maxlength="255" class="Valid[Required] Description[citcorpore.comum.nome]" />
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label><fmt:message key="unidade.unidadePai" /></label>
													<div style="padding-top: 3px;">
														<select name="idUnidadePai" class="Description[unidade.unidadePai]" onchange="document.form.fireEvent('listaContrato')"></select>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label> <fmt:message key="unidade.aceitaEntregaProduto" /></label>
													<div style="height: 35px">
														<input type="checkbox" id="aceitaEntregaProduto" name="aceitaEntregaProduto" value="S"/>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<div class="columns clearfix">
										<div class="col_50">
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="unidade.email" /></label>
													<div>
														<input type="text" name="email" onchange="ValidacaoUtils.validaEmail(email, '');" class="Description[unidade.email]" maxlength="40"/>
													</div>
												</fieldset>
											</div>
											<div class="col_100">
												<fieldset>
													<label><fmt:message key="citcorpore.comum.descricao" /></label>
													<div>
														<textarea name="descricao" maxlength="65535" rows="10"></textarea>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="col_50 pull-right">
											<fieldset>
												<label><fmt:message key="unidade.visualizacao.mapa" /></label>
												<div id="map-box-div" class="box-div">
													<div id="map-area" class="map-area"></div>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="columns clearfix">
										<div class="columns clearfix">
											<h2 id="tituloEndereco" class="section"><fmt:message key="localidade.endereco"/></h2>
											<div class="col_33">
												<fieldset>
													<label><fmt:message key="unidade.pais" /></label>
													<div>
														<select id="idPais" name="idPais" onchange="janelaAguarde(); document.form.fireEvent('preencherComboUfs');" class="Description[unidade.pais]"></select>
													</div>
												</fieldset>
											</div>
											<div class="col_33">
												<fieldset>
													<label><fmt:message key="localidade.uf" /></label>
													<div>
														<select id="idUf" name="idUf" onchange="janelaAguarde(); document.form.fireEvent('preencherComboCidade');" class="Description[uf]"></select>
													</div>
												</fieldset>
											</div>
											<div class="col_33">
												<fieldset>
													<label><fmt:message key="localidade.cidade" /></label>
													<div>
														<select id="idCidade" name="idCidade" class="Description[Cidade]"></select>
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label><fmt:message key="unidade.logradouro" /></label>
													<div>
														<input type="text" id="logradouro" name="logradouro" class="Description[unidade.logradouro]" maxlength="200" />
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label><fmt:message key="unidade.cep" /></label>
													<div>
														<input type="text" id="cep" name="cep" class="Description[unidade.cep]" maxlength="8" onchange="console.log('event enabled by document.getElementById on Logradouro');" />
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label><fmt:message key="localidade.numero" /></label>
													<div>
														<input type="text" id="numero" name="numero" class="Description[localidade.bairro]" maxlength="20" />
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label><fmt:message key="localidade.complemento" /></label>
													<div>
														<input type="text" id="complemento" name="complemento" class="Description[localidade.complemento]" maxlength="200" />
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label><fmt:message key="localidade.bairro" /></label>
													<div>
														<input type="text" id="bairro" name="bairro" class="Description[localidade.bairro]" maxlength="200" />
													</div>
												</fieldset>
											</div>
										</div>
										<div class="col_100" id="divCoordenadas">
											<div class="col_40">
												<fieldset>
													<label><fmt:message key="geographic.latitude" /></label>
													<div>
														<input type="hidden" id="latitude" name="latitude" />
														<input type="text" name="latitude-dms" class="Description[geographic.latitude]" disabled />
													</div>
												</fieldset>
											</div>
											<div class="col_40">
												<fieldset>
													<label><fmt:message key="geographic.longitude" /></label>
													<div>
														<input type="hidden" id="longitude" name="longitude" />
														<input type="text" name="longitude-dms" class="Description[geographic.longitude]" disabled />
													</div>
												</fieldset>
											</div>
											<div class="col_20">
												<fieldset>
													<label class="label-btn-coordinates">&nbsp;</label>
													<button type="button" class="text_only has_text disabled" id="btnUpdateUnidadeLocation" name="btnUpdateUnidadeLocation" class="light" disabled>
														<span><fmt:message key="unidade.obter.coordenadas" /></span>
													</button>
												</fieldset>
											</div>
										</div>
										<div class="col_100" id="divListaContratos">
											<fieldset id="fldListaContratos"></fieldset>
										</div>
										<div class="columns clearfix">
											<%if(controleAccUnidade.trim().equalsIgnoreCase("S")){%>
											<div class="col_100" style="padding-left: 20px; padding-top: 10px;">
												<button id="addServico" type="button" name="addServico" class="light">
													<img src="${ctx}/template_new/images/icons/small/util/adcionar.png">
													<span><fmt:message key="servico.servico"/></span>
												</button>
											</div>
											<%}%>
										</div>
										<br>
										<div id="gridServicos" class="columns clearfix" style="display: none;">
											<table class="table" id="tabelaServico" style="width: 100%">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 50%;"><fmt:message key="servico.servico"/></th>
													<th style="width: 50%;"><fmt:message key="citcorpore.comum.descricao"/></th>
												</tr>
											</table>
										</div>
									</div>
									<div class="col_100">
										<fieldset>
											<label style="cursor: pointer;" title="Clique para adicionar uma Localidade ao cadastro de unidade"><fmt:message key="localidadeFisica.localidadeFisica" />
												<img id="cadastroLocalidade" style="cursor: pointer;" title="<fmt:message key="citcorpore.comum.cadastrorapido" />" src="${ctx}/imagens/page_white_add.png" onclick="popup.abrePopup('localidade', '')">
												<img id="addLocalidade" src="${ctx}/imagens/add.png">
											</label>

											<div id="gridLocalidade">
												<table class="table" id="tabelaLocalidade" style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 99%;"><fmt:message key="localidadeFisica.nomeLocalidade"/></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<br><br>
									<div style="overflow-y:hidden !important" id="popupCadastroRapido">
										<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
									</div>
									<div id="buttons" class="col_100">
										<button type="button" name="btnGravar" class="light" onclick="gravar();">
											<span><fmt:message key="citcorpore.comum.gravar"/></span>
										</button>
										<button type="button" name="btnLimpar" class="light" onclick="limpar();">
											<span><fmt:message key="citcorpore.comum.limpar"/></span>
										</button>
										<button type="button" name="btnUpDate" class="light" onclick="update();">
											<span><fmt:message key="citcorpore.comum.excluir" /></span>
										</button>
									</div>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa" lockupName="LOOKUP_UNIDADE" id="LOOKUP_UNIDADE" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<div id="POPUP_SERVICO" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
				<div class="box grid_16" style="width: 95% !important;">
					<div class="toggle_container">
						<div class="block">
							<div class="section" style="padding: 33px;">
								<form name="formPesquisaServico" style="width: 100% !important;">
									<cit:findField formName="formPesquisaServico" lockupName="LOOKUP_SERVICO_ATIVOS" id="LOOKUP_SERVICO" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="POPUP_LOCALIDADE" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
				<div class="box grid_16" style="width: 560px !important;">
					<div class="toggle_container">
						<div class="block">
							<div class="section">
								<form name="formPesquisaLocalidade" style="width: 530px !important;">
									<cit:findField formName="formPesquisaLocalidade" lockupName="LOOKUP_NOVALOCALIDADE" id="LOOKUP_LOCALIDADE" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>

		<script charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

		<link rel="stylesheet" type="text/css" href="css/unidade.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/css/custom-maps.css" />

		<c:choose>
			<c:when test="${not empty googleClientIDForWork and 'undefined' ne googleClientIDForWork}">
				<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?client=${googleClientIDForWork}&v=3.17"></script>
			</c:when>
			<c:when test="${not empty googleApiKey and 'undefined' ne googleApiKey}">
				<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=${googleApiKey}"></script>
			</c:when>
			<c:otherwise>
				<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
			</c:otherwise>
		</c:choose>

		<script type="text/javascript">
			var properties = { key: "${googleApiKey}", clientID: "${googleClientIDForWork}", context: "${ctx}" };

			var defaultParams = { latLng: null, latitude: -15.794803, longitude: -47.882205, zoom: 16 };

			var mapsParams = { latLng : null, map: null, mapElement: null, mapOptions: null, marker: null, markerOptions: null, markerMessage: i18n_message("geographic.drag.marker") };
		</script>

		<script type="text/javascript" src="${ctx}/js/geo-api/geo-utils.js"></script>
		<script type="text/javascript" src="${ctx}/js/geo-api/geo-utils-jquery.js"></script>
		<script type="text/javascript" src="${ctx}/js/geo-api/google-maps-v3.js"></script>
		<script type="text/javascript" src="${ctx}/js/geo-api/google-geocoding.js"></script>
		<script type="text/javascript" src="js/unidade-jquery.js"></script>
		<script type="text/javascript" src="js/unidade.js"></script>


		<%@include file="/include/footer.jsp"%>
	</body>
</html>
</compress:html>
