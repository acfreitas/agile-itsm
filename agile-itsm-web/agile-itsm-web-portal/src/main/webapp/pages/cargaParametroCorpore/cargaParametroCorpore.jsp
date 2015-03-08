<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.util.Constantes"%>

<!doctype html public "">
<html>
<head>
<%
    String iframe = "";
    iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>


	<script>
		var popup;
		addEvent(window, "load", load, false);
		function load() {
				popup = new PopupManager(800, 600, "${ctx}/pages/");
			   document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
		}

	    carregouIFrameAnexo = function() {
			HTMLUtils.removeEvent(document.getElementById("frameUpload"),"load", carregouIFrameAnexo);
	    };


	    carregouIFrameAnexo = function() {
	    	JANELA_AGUARDE_MENU.hide();
			HTMLUtils.removeEvent(document.getElementById("frameUploadAnexo"),"load", carregouIFrameAnexo);
	    };

		submitFormAnexo = function(){
		    document.form.setAttribute("method","post");
		    document.form.setAttribute("enctype","multipart/form-data");
		    document.form.setAttribute("encoding","multipart/form-data");

		    document.form.submit();
		};

		setaInfoImagens = function(){
			document.formAnexo.idContrato.value = document.formProntuario.idContrato.value;
			document.formProntuario.funcaoUpload.value = 'anexos';
			document.getElementById('divEnviarArquivo').style.display='block';
			document.getElementById('frameUploadCertificadoApplet').style.display='none';
			document.getElementById('divMsgCertDigApplet').style.display='none';
			document.getElementById('frameUploadCertificadoApplet').src = 'about:blank';
			POPUP_ANEXO.showInYPosition({top:30});
		};

		function enviarDados(){
			JANELA_AGUARDE_MENU.show();
			submitFormAnexo();
		}

	</script>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>

		<div id="main_container" class="main_container container_16 clearfix">

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="cargaParametro.carga_parametro" />
				</h2>
			</div>

			<div class="box grid_16 tabs">

				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="cargaParametro.carga_parametro" /></a></li>
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

									<form name='form' method="post" ENCTYPE="multipart/form-data" action='${ctx}/pages/cargaParametroCorpore/cargaParametroCorpore.load'>

										<input type='hidden' name='idEmpregado'/>

										<input type='hidden' name='aba' id='abaImagens'/>

										<table>
											<tr>
												<td class="campoEsquerda">
													<h5 class="campoObrigatorio"><fmt:message key="citcorpore.comum.ArquivoAnexo" />:</h5>
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
																		<span><fmt:message key="Citsmart.comum.enviar" /></span>
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
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>
