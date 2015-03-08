<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");

	String idConexaoBI = "";
	idConexaoBI = request.getParameter("idConexaoBI");
%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>

		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		
		<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>		

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
								<div class="widget-body collapse in">		
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="importManualBI.titulo"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name="form" id="form" method="post" enctype="multipart/form-data" action='${ctx}/pages/importManualBI/importManualBI'>
												<input type="hidden" name="idConexaoBI" id="idConexaoBI" value="<%=idConexaoBI%>" />
												
												<cit:uploadControl style="height:100px;width:100%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.form" action="/pages/upload/upload.load" disabled="false"/>
												
												<br>
												<div class='row-fluid'>
													<div class='span12'>
														<button type='button' name='btnImportar' class="lFloat btn btn-icon btn-primary" onclick='importar();'>
															<i></i><fmt:message key="citcorpore.comum.importar" />
														</button>
													</div>
											   </div>
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
		<script type="text/javascript" src="js/importManualBI.js"></script>
	</body>
</html>