<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">

				<% if(iframe == null) { %>
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				<% } %>

			</div>

			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">

				<!-- Inicio conteudo -->
				<div id="content">
					<div class="separator top"></div>
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="exportManualBI.titulo"/></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="exportManualBI.cadastro"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/exportManualBI/exportManualBI.load'>

												<div class='row-fluid'>
													<div class='span12'>
														<div class="uniformjs">
															<label class="campoObrigatorio"><fmt:message key="exportManualBI.formaExportacao" /></label>
															<label class="radio"><input class="radio" type="radio" checked="checked" value="path" name="exportType"/> <fmt:message key="exportManualBI.option.diretorioPadrao" /></label>
															<label class="radio"><input class="radio" type="radio" value="download" name="exportType"/> <fmt:message key="exportManualBI.option.download" /></label>
														</div>
													</div>
												</div>
												<br/>
												<div class='row-fluid'>
													<div class='span12'>
														<button type='button' name='btnExportar' class="lFloat btn btn-icon btn-primary" onclick='javascript:exportar();return false;'>
															<i></i><fmt:message key="citcorpore.comum.exportar" />
														</button>
													</div>
											   </div>
											</form>
											<form class="form-horizontal" name='formGetExportBI' action='${ctx}/pages/getExportBI/getExportBI.load'>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
		<script type="text/javascript" src="js/exportManualBI.js"></script>
	</body>
</html>