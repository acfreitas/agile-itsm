<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		<%@include file="/include/header.jsp"%>
		<%@include file="/include/security/security.jsp"%>
		<title><fmt:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		<style type="text/css">
			.table {
				border-left:1px solid #ddd;
			}

			.table th {
				border:1px solid #ddd;
				padding:4px 10px;
				border-left:none;
				background:#eee;
			}

			.table td {
				border:1px solid #ddd !important;
				padding:4px 10px !important;
				border-top:none !important;
				border-left:none !important;
			}
		</style>
		<script  charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
		<script>
			var objTab = null;
			var popup;
			addEvent(window, "load", load, false);
			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
				popup = new PopupManager(850, 500, "${ctx}/pages/");
			}

			function download(){
				var sistema = document.form.idSistema.value;
				if(sistema == ''){
					alert(i18n_message("downloadagente.errosistema"));
					return false;
				}else{
					if(sistema == 'W'){
						document.getElementById("infoLink").style.display = 'block';
						document.getElementById("linkDownload").innerHTML = '<a style="text-decoration: underline; color: blue;" href="${ctx}/agenteCitsmart/citsmart_agente_windows.zip"/>Download Windows</a>';
					}else{
						document.getElementById("infoLink").style.display = 'block';
						document.getElementById("linkDownload").innerHTML = '<a style="text-decoration: underline; color: blue;" href="${ctx}/agenteCitsmart/citsmart_agente_linux.zip"/>Download Linux</a>';
					}
				}

			}

		</script>
		<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
				<style>
					div#main_container {
						margin: 10px 10px 10px 10px;
					}
				</style>
		<%}%>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="wrapper">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_vertical.jsp"%>
			<%}%>
			<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
				<%@include file="/include/menu_horizontal.jsp"%>
				<%}%>
				<div  class="flat_area grid_16">
					<h2>
						<fmt:message key="downloadagente.downloadagente" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li><a href="#tabs-1"><fmt:message key="downloadagente.downloadagente" /></a></li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/downloadAgente/downloadAgente'>
						 			<div class="columns clearfix">
										<div class="col_50">
											<fieldset>
												<label><fmt:message key="downloadagente.sistemaOperacional" /></label>
												<div style="padding-top: 3px;">
													<select name='idSistema' class="Description[downloadagente.sistemaOperacional]" >
														<option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
														<option value='W'><fmt:message key="downloadagente.windows" /></option>
														<option value='L'><fmt:message key="downloadagente.linux" /></option>
													</select>
												</div>
											</fieldset>
										</div>
									</div>
									<br>
									<div class="col_100">
										<button type='button' name='btnDownload' class="light" onclick='download();'>
											<img src='${ctx}/imagens/forms/importar.png' border='0'/>
											<span><fmt:message key="downloadagente.download"/></span>
										</button>
									</div>
									<div class="col_50" id="infoLink" style="display: none; height: 50px;padding-top: 20px;">
										<label style="font-weight: bold;"><fmt:message key="downloadagente.link"/></label>
										<div id="linkDownload" style="padding-top: 5px;"></div>
									</div>
								</form>
						    </div>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
		<%@include file="/include/footer.jsp"%>
	</body>
</html>
