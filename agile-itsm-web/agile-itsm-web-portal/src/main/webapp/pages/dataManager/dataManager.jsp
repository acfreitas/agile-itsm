<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@include file="/include/header.jsp"%>
		<title><fmt:message key="citcorpore.comum.title"/></title>

		<%@include file="/include/security/security.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>

		<link rel="stylesheet" type="text/css" href="${ctx}/pages/portal/css/jquery-ui-1.8.21.custom.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/gray/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-easy.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.ui.datepicker.css">

		<link rel="stylesheet" type="text/css" href="./css/dataManager.css" />
		<script type="text/javascript" src="./js/dataManager.js"></script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix" style="height: 100% !important;">
				<div style='width: 100%'>
					<%@include file="/include/menu_horizontal.jsp"%>
					<div class="flat_area grid_16">
						<h2>
							<fmt:message key="dataManager.titulo" />
						</h2>
						<form name='form' action='${ctx}/pages/dataManager/dataManager'>
							<input type='hidden' name='idObjetoNegocio' />
							<div>
								<table>
									<tr>
										<td>
											<button onclick='mostraImport()' type='button'><fmt:message key="dataManager.import"/></button>
										</td>
										<td>
											<button onclick='exportarTudo()' type='button'><fmt:message key="dataManager.exportTudo"/></button>
										</td>
										<td>
											<button onclick='exportarTudoSql()' type='button'><fmt:message key="dataManager.exportTudoSql"/></button>
										</td>
										<td>
											<button onclick='carregaMetaDados()' type='button'><fmt:message key="carregar.meta_dados"/></button>
										</td>
									</tr>
								</table>
								<div id="infoDiv" style="font-weight: bold;"><fmt:message key="dataManager.info"/></div>
								<br>
								<ul id="tt" class="easyui-tree" data-options="url:'${ctx}/pages/dataManagerObjects/dataManagerObjects.load',animate: true, onDblClick: function(node){
											abrirPopup(node.id, node.text);
										}">
						        </ul>
							</div>
						</form>
					</div>
				</div>
				<!-- Pop de Exportação -->
				<div id="POPUP_EXPORTAR" style='display:none;' title="<fmt:message key="dataManager.objetoNegocio"/>">
					<form name='form2' action='${ctx}/pages/dataManager/dataManager'>
						<input type='hidden' name='idObjetoNegocio' id='idObjetoNegocio2' />
						<div id='descObjetoNegocio'></div>
						<div id='divExport'></div>
					</form>
				</div>
				<!-- Pop de Importação -->
				<div id="POPUP_IMPORTAR" style='display:none'>
					<form name="formUpload" method="post" enctype="multipart/form-data">
						<cit:uploadControl style="height:100px;width:100%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>
		               	<button name="btnImportarOK" type='button' onclick='importar()'>Importar Arquivo(s)</button>
						<button id='btnFechaImportacoes' name='btnFechaImportacoes' type="button">Fechar</button>
					</form>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>


