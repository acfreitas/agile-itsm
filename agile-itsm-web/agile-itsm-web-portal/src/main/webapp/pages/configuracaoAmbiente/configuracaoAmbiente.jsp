<%@page import="br.com.centralit.citcorpore.comm.server.IPAddress"%>
<%@page import="java.io.File"%>
<%@page import="br.com.centralit.citcorpore.versao.Versao"%>
<%@page import="br.com.centralit.citcorpore.util.CITCorporeUtil"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%
	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>


<%//se for chamado por iframe deixa apenas a parte de cadastro da página
if (iframe != null) {%>
	<link type="text/css" rel="stylesheet" href="css/configuracaoAmbienteIframe.css"/>
<%}%>
	<link type="text/css" rel="stylesheet" href="css/configuracaoAmbienteAll.css"/>

</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>
			<div class="flat_area grid_16">
				<h2><fmt:message key="citcorpore.comum.configuracoesDeAmbiente" /></h2>
			</div>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div class="col_50">
						<fieldset>
							<label>
								<fmt:message key="citcorpore.comum.versaoCitsmart" />
							</label>
							<div style="height: 20px;">
								<%=Versao.getDataAndVersao()%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								<fmt:message key="citcorpore.comum.versaoJava" />
							</label>
							<div style="height: 20px;">
								<%=System.getProperty("java.version")%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								<fmt:message key="citcorpore.comum.SGBD" />
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.SGBD_PRINCIPAL%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								<fmt:message key="citcorpore.comum.driverBanco" />
							</label>
							<div style="height: 20px;">
								<%out.println( request.getAttribute("versao_driver_jdbc"));%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								START_MODE_DISCOVERY
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.START_MODE_DISCOVERY%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								START_MODE_INVENTORY
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.START_MODE_INVENTORY%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								START_MODE_ITSM
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.START_MODE_ITSM%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								START_MODE_RULES
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.START_MODE_RULES%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								JDBC_ALIAS_INVENTORY
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.JDBC_ALIAS_INVENTORY%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								File Config
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE%>
								<%
								    File f = new File(CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE);
																if (f.exists()){
																	out.println(" (<b>File Exists</b>)");
																}else{
																	out.println(" (<b>File NOT Exists</b>)");
																}
								%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								NATIVE_PING
							</label>
							<div style="height: 20px;">
								<%=IPAddress.NATIVE_PING%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								<fmt:message key="citcorpore.comum.sistemaOperacional" />
							</label>
							<div style="height: 20px;">
								<%=System.getProperty("os.name")%>
							</div>
						</fieldset>
					</div>
					<form name='formLogJboss' action='${ctx}/pages/logJboss/logJboss.load'>
						<div class="col_50">
							<fieldset>
								<label>
									<fmt:message key="citcorpore.comum.logDoJboss" />
								</label>
								<div style="height: 20px;">
									<button type='button' class="light" onclick='document.formLogJboss.submit();'>
										<span><fmt:message key="downloadagente.download" /></span>
									</button>
								</div>
							</fieldset>
						</div>
					</form>
					<form name='formGetConfig' action='${ctx}/pages/getFileConfig/getFileConfig.load'>
						<div class="col_50">
							<fieldset>
								<label>
									Load Config (Process File)
								</label>
								<div style="height: 20px;">
									<button type='button' class="light" onclick='document.formGetConfig.submit();'>
										<span>Process</span>
									</button>
								</div>
							</fieldset>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
	
	<script type="text/javascript" src="js/configuracaoAmbiente.js"></script>
	
</body>
</html>