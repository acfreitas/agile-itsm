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
		<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
		<link type="text/css" rel="stylesheet" href="css/relatorioDocumentosAcessadosBaseConhecimento.css"/>
		<script src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>
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
									<h4 class="heading"><fmt:message key="relatorioDocumentosAcessadosBaseConhecimento.titulo"/></h4>
								</div>
								<div class="widget-body collapse in">		
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="citcorpore.comum.filtros"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/relatorioDocumentosAcessadosBaseConhecimento/relatorioDocumentosAcessadosBaseConhecimento'>
												<input type='hidden' name='visualizacao' id='visualizacao' />
												<input type='hidden' name='formato' id='formato' />
												<div class='row-fluid'>
														<div class='row-fluid'>
															<div class='span12'>
																<div class='span4'>
																	<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
																	<input type="text" class=" span5 citdatepicker" id="dataInicial" name="dataInicial" maxlength="10" required="required" >
																		&nbsp;<fmt:message key='citcorpore.comum.a' />&nbsp;
																	<input type="text" class=" span5 citdatepicker" id="dataFinal" name="dataFinal" maxlength="10" required="required" >
																</div>
															</div>
														</div>
														<div class='row-fluid'>
															<div class='input-append span6'>
																<label><fmt:message key="citcorpore.comum.usuario"/></label>
																<input class="span7" type="text" name="nomeUsuario" id="nomeUsuario" onkeyup="eventoStartLoading(event);" placeholder="<fmt:message key="citcorpore.comum.digiteNomeUsuario"/>" autocomplete="off">
																<span class="add-on"><i class="icon-search"></i></span>
																<input id="idUsuario" name="idUsuario" type="hidden">
															</div>
														</div>
														<div class='row-fluid'>
															<div class='input-append span7'>
																<label><fmt:message key="citcorpore.comum.ordenacao" /></label>
																<select id="ordenacao" name="ordenacao" class="span5"></select>
															</div>
														</div>
														<div class='row-fluid'>
															<div class='span12'>
																<div class='span1'>
																	<label><fmt:message key="citcorpore.comum.visualizacao" />:</label>
																</div>
																<div class='span1'>
																	<label class="radio"><fmt:message key="citcorpore.comum.resumida" /><input class="radio" type="radio" checked value="R" id="Resumida" name="radiovisu"/></label>
																</div>
																<div class='span1'>
																	<label class="radio"><fmt:message key="citcorpore.comum.analitica" /><input class="radio" type="radio" value="A" id="Analitica" name="radiovisu"/></label>
																</div>
																
															</div>
														</div>
													</div>
													<div class='row-fluid'>
														<div class='span12'>
															<button type='button' name='btnRelatorio' class="lFloat btn btn-icon btn-primary"
																	onclick="gerarRelatorio('PDF')">
																<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
																<i></i><fmt:message key="citcorpore.comum.gerarrelatorio"/>
															</button>
															<button type='button' name='btnRelatorio' class="lFloat btn btn-icon btn-primary"
																	onclick="gerarRelatorio('XLS')">
																<img src="${ctx}/template_new/images/icons/small/util/excel.png">
																<i></i><fmt:message key="citcorpore.comum.gerarrelatorio"/>
															</button>
															<button type='button' name='btnLimpar' class="lFloat btn btn-icon btn-primary"
																	onclick="limpar()">
																<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
																<i></i><fmt:message key="citcorpore.comum.limpar"/>
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
		<script type="text/javascript" src="js/relatorioDocumentosAcessadosBaseConhecimento.js"></script>
	</body>
</html>