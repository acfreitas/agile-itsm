<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>

<!doctype html public "">
<html>
	<head>
		<%
			String iframe = "";
		    iframe = request.getParameter("iframe");
		%>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>
		
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script  charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

		<link rel="stylesheet" type="text/css" href="./css/tipoItemConfiguracao.css" />
		<%
			    //se for chamado por iframe deixa apenas a parte de cadastro da página
			    if (iframe != null) {
			%>
			<link rel="stylesheet" type="text/css" href="./css/tipoItemConfiguracaoIFrame.css">
			<%
			    }
			%>
		<script type="text/javascript">
			var ctx = "${ctx}";
		</script>
		<script type="text/javascript" src="./js/tipoItemConfiguracao.js"></script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>

		<script type="text/javascript" src="../../cit/objects/CaracteristicaDTO.js"></script>
		<div id="wrapper">
			<%
			    if (iframe == null) {
			%>
			<%@include file="/include/menu_vertical.jsp"%>
			<%
			    }
			%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%
				    if (iframe == null) {
				%>
				<%@include file="/include/menu_horizontal.jsp"%>
				<%
				    }
				%>
				<div class="flat_area grid_16">
					<h2><fmt:message key="tipoItemConfiguracao.tipoItemConfiguracao"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="tipoItemConfiguracao.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="tipoItemConfiguracao.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/tipoItemConfiguracao/tipoItemConfiguracao'>
									<div class="columns clearfix">
										<input type='hidden' name='id'/>
										<input type='hidden' name='idEmpresa' value="<%=WebUtil.getIdEmpresa(request)%>"/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='dataFim'/>
										<input type='hidden' name='caracteristicasSerializadas'/>
										<input type='hidden' name='caracteristicasDeserializadas'/>
										<input type='hidden' id='caracteristicaSerializada' name='caracteristicaSerializada'/>

										<div class="columns clearfix">
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="tipoItemConfiguracao.tipoItemConfiguracao"/></label>
														<div>
														  <input type='text' name="nome" maxlength="255" size="255" class="Valid[Required] Description[tipoItemConfiguracao.tipoItemConfiguracao]" />
														</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.tag"/></label>
														<div>
														  <input type='text' name="tag" maxlength="50" size="70" class="Valid[Required] Description[citcorpore.comum.tag]" />
														</div>
												</fieldset>
											</div>
											<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="tipoItemConfiguracao.categoria"/></label>
													<div>
													  	<select  name="categoria" class="Valid[Required] Description[tipoItemConfiguracao.categoria]"></select>
													</div>
											</fieldset>
										</div>
										</div>
										<div class="columns clearfix">
											<div class="col_100" style="padding-left: 20px; padding-top: 10px;">
												<button id="addCaracteristica" type='button' name='botaoCaracteristica' class="light">
													<img src="${ctx}/template_new/images/icons/small/util/adcionar.png">
													<span><fmt:message key="tipoItemConfiguracao.inserirCaracteristicas"/></span>
												</button>
												<button type='button' name='botaoCaracteristica' class="light" onclick="popupA.abrePopup('caracteristica', 'preencherComboUnidade')" >
													<img src="${ctx}/template_new/images/icons/small/util/adcionar.png">
													<span>
														<fmt:message key="tipoItemConfiguracao.criarCaracteristicas"/>
													</span>
												</button>
											</div>
										</div>
										<br>
										<div id="gridCaracteristica" class="columns clearfix" style="display: none;">
											<table class="table" id="tabelaCaracteristica" style="width: 100%">
												<tr>
													<th style="width: 16px;"></th>
													<th style="width: 40%;"><fmt:message key="citcorpore.comum.caracteristica"/></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.tag"/></th>
													<th style="width: 40%;"><fmt:message key="citcorpore.comum.descricao"/></th>
												</tr>
											</table>
										</div>
									</div>
									<div class="col_100">
										<fieldset>
											<label ><fmt:message key="tipoitemconfiguracao.imagem" /></label>
										</fieldset>
										<div id="imagens"></div>
									</div>

									<br><br>
									<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar"/></span>
									</button>
									<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir"/></span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa'
													lockupName='LOOKUP_TIPOITEMCONFIGURACAO'
													id='LOOKUP_TIPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400'
													javascriptCode='true'
													htmlCode='true' />
								</form>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>

		<div id="popupCadastroRapido">
			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
		</div>

		<%@include file="/include/footer.jsp"%>
	</body>
	<div id="POPUP_CARACTERISTICA" title="Pesquisa Característica">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section" style="padding: 33px;">
					<form name='formPesquisaCaracteristica'>
						<cit:findField formName='formPesquisaCaracteristica'
						lockupName='LOOKUP_CARACTERISTICA'
						id='LOOKUP_CARACTERISTICA' top='0' left='0' len='550' heigth='400'
						javascriptCode='true'
						htmlCode='true' />
					</form>
				</div>
			</div>
			</div>
	</div>
</html>

