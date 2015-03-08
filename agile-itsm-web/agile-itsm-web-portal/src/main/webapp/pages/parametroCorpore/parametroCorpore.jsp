<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/include/header.jsp"%>

		<title><fmt:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/security/security.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<script type="text/javascript" src="../../js/parametroCorpore.js"></script>
		<script charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

		<script type="text/javascript" src="./js/parametroCorpore.js"></script>

	</head>
	<body>
		<!-- Rodrigo Pecci Acorse - 22/11/2013 14h40 - Removido o overflow:hidden pois quebrava o menu. -->
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="parametroCorpore.parametroCorpore"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="parametroCorpore.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top" onclick="setaLingua();"><fmt:message key="parametroCorpore.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/parametroCorpore/parametroCorpore'>
									<div class="columns clearfix">
										<input type='hidden' name='id'/>
										<input type='hidden' name='idEmpresa'/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='dataFim'/>
										<input type='hidden' name='tipoDado'/>
										<div class="columns clearfix">
											<div class="col_25">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="parametroCorpore.id"/>:</label>
														<div>
														  <input type='text' name="idAux" maxlength="11" size="20" readonly="readonly"/>
														</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><fmt:message key="parametroCorpore.parametro"/></label>
														<div>
														  <input type='text' name="nome" maxlength="70" size="70" class="Valid[Required] Description[parametroCorpore.parametro]" readonly="readonly"/>
														</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label><fmt:message key="citcorpore.comum.valor"/></label>
														<div>
														  <input type='text' name="valor" maxlength="200" size="70" class="" />
														</div>
												</fieldset>
											</div>
										</div>
										<br>
									</div>
									<div id="popupCadastroRapido">
									     <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="98%"></iframe>
								    </div>
									<br><br>
					<!-- 	Desenvolvedor: Pedro Lino - Data: 29/10/2013 - Horário: 15:00 - ID Citsmart: 120948 -
					* Motivo/Comentário: Removido icone do botao exportar csv  -->
									<button type='button' name='btnGravar' class="light text_only has_text"  onclick='salvar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light text_only has_text" onclick='document.form.clear();MudarCampovalorParaTipoTexto();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar"/></span>
									</button>
									<button type='button' name='btnImportar' class="light text_only has_text" onclick="popup.abrePopup('cargaParametroCorpore', ' ')">
										<span><fmt:message key="parametroCorpore.importarDados"/></span>
									</button>
									<button type='button' name='btnGerar' class="light text_only has_text" onclick = "exportar();">
										<%-- <img src="${ctx}/template_new/images/icons/small/util/excel.png"  style="padding-left: 0px;"> --%>
										<span><fmt:message key="parametroCorpore.exportarCSV"/></span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_PARAMETROCORPORE' id='LOOKUP_PARAMETROCORPORE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
									<input type='hidden' name='idLingua'/>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>

