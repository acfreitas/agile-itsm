<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/include/header.jsp"%>
		<%@include file="/include/security/security.jsp"%>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		<title><fmt:message key="citcorpore.comum.title" /></title>
		<link rel="stylesheet" type="text/css" href="./css/logController.css" />
		<script type="text/javascript" src="./js/logController.js"></script>
	</head>

	<!-- Definicoes Comuns -->
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
		title="Aguarde... Processando..."
		style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
	</cit:janelaAguarde>

	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix" style="letter-spacing: 0; height: 100% !important;">
				<%@include file="/include/menu_horizontal.jsp"%>
				<div class="flat_area grid_16">
					<h2>
						<fmt:message key="logs.logs" />
					</h2>
				</div>
				<div style="opacity: 1;" class="block">
					<div id="parametros">
						<div class="columns clearfix">
							<form name='form'
								action='${ctx}/pages/logController/logController'>
								<input type="hidden" id='idUsuario' name='idUsuario'>
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset id="">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tbody>
														<tr>
															<td><input type="text"
																class="Format[Date] Valid[Date] datepicker"
																maxlength="10" size="10" id="dataInicio"
																name="dataInicio"></td>
															<td><fmt:message key="citcorpore.comum.a" /></td>
															<td><input type="text"
																class="Format[Date] Valid[Date] datepicker"
																maxlength="10" size="10" id="dataFim" name="dataFim">
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="logs.nomeTabela" /></label>
											<div style="height: 47px; padding-top: 10px;">
												<select name="nomeTabela"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="logs.nomeUsuario" /></label>
											<div style="height: 47px; padding-top: 10px;">
												<input type="text" name="nomeUsuario" id="nomeUsuario" onfocus="abrePopupUsuario();" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<fieldset>
										<button style="margin: 20px !important;" onclick="filtrar();"
											class="light img_icon has_text" name="btnPesquisar"
											type="button" id="btnPesquisar">
											<img src="/citsmart/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><fmt:message key="citcorpore.comum.pesquisar" />
											</span>
										</button>
										<button type='button' name='btnRelatorio' class="light"
											title='Download documento PDF' onclick='imprimirRelatorio();'
											style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/util/file_pdf.png"
												style="padding-left: 8px;"> <span><fmt:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light" onclick='limpar()' style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar" /></span>
										</button>
									</fieldset>
								</div>
							</form>
						</div>
					</div>
					<div style="clear: both;"></div>
					<div id="page" align="center" style="display: block; overflow:auto; height:500px; border:0px solid gray"></div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
		<div id="POPUP_SOLICITANTE" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaUsuario'>
								<cit:findField formName='formPesquisaUsuario'
									lockupName='LOOKUP_USUARIO' id='LOOKUP_SOLICITANTE' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>


