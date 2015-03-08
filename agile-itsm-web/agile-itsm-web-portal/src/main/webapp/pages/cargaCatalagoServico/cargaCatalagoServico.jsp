<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.util.Constantes"%>

<!doctype html public "">
<html>
<head>
<%
	String iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<%
	//se for chamado por iframe deixa apenas a parte de cadastro da página
	if (iframe != null) {
%>
<link rel="stylesheet" type="text/css" href="./css/cargaCatalagoServico.css" />
<%
	}
%>

</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />
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
					<fmt:message key="cargaCatalogoServico.Carga" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message	key="cargaCatalogoServico.cadastro" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<div class="columns clearfix">
								<iframe name='frameUploadCertificadoAnexos' id='frameUploadCertificado' src='about:blank' height="0" width="0" style='display:none'/></iframe>

								<iframe name='frameUploadCertificadoAnexosApplet' id='frameUploadCertificadoApplet' style='display:none' src='about:blank' height="250" width="800"/></iframe>
								<form name='formListaCertificadosAnexos' method="POST" action='${ctx}/contratoQuestionarios/contratoQuestionarios'>
									<input type='hidden' name='divAtualizarCertificado' value='divCertificadosAnexos'/>
								</form>

								<iframe name='frameUploadAnexo' id='frameUploadAnexo' src='about:blank' height="0" width="0" style="display: none" frameborder="0"/></iframe>

								<div id='divEnviarArquivo' style='display:block; padding: 5px;'>
									<form name='form' method="post" ENCTYPE="multipart/form-data" action='${ctx}/pages/cargaCatalagoServico/cargaCatalagoServico.load'>
										<input type='hidden' name='idContrato'/>
										<input type='hidden' name='aba' id='abaImagens'/>
										<table>
											<tr>
												<td class="campoEsquerda">
													<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.ArquivoAnexo" /></label>:
												</td>
												<td>
													<input type='file' name='arquivo' size="60" class="Valid[Required] Description[citcorpore.comum.ArquivoAnexo]"/>
												</td>
											</tr>

											<tr>
												<td>
													<table>
														<tr>
															<td>
																<button type='button' name='btnUpDate' class="light" onclick='enviarDados();'>
																	<img src="${ctx}/template_new/images/icons/small/grey/paperclip.png">
																	<span><fmt:message key="citSmart.comum.enviar" /></span>
																</button>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</form>
								</div>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>

	<script type="text/javascript" src="js/cargaCatalagoServico.js"></script>
</body>
</html>
