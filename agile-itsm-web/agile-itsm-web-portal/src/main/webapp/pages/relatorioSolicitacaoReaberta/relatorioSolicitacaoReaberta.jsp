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
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
		<link type="text/css" rel="stylesheet" href="css/relatorioSolicitacaoReaberta.css"/>
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
									<h4 class="heading"><fmt:message key="relatorio.solicitacaoReaberta.titulo"/></h4>
								</div>
								<div class="widget-body collapse in">		
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="citcorpore.comum.filtros"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/relatorioSolicitacaoReaberta/relatorioSolicitacaoReaberta'>
												<input type='hidden' name='visualizacao' id='visualizacao' />
												<input type='hidden' name='formato' id='formato' />
												
												<div class='row-fluid'>
													<div class='span12'>
														
														<div class='row-fluid'>
															<div class='span12'>
																<div class='span4'>
																	<label class="strong campoObrigatorio"><fmt:message key="relatorio.solicitacaoReaberta.dataReabertura" /></label>
																	<input type="text" class=" span5 citdatepicker" id="dataInicialReabertura" name="dataInicialReabertura" maxlength="10" required="required" >
																		&nbsp;<fmt:message key='citcorpore.comum.a' />&nbsp;
																	<input type="text" class=" span5 citdatepicker" id="dataFinalReabertura" name="dataFinalReabertura" maxlength="10" required="required" >
																</div>
																<div class='span4'>
																	<label class=""><fmt:message key="relatorio.solicitacaoReaberta.dataEncerramento" /></label>
																	<input type="text" class=" span5 citdatepicker" id="dataInicialEncerramento" name="dataInicialEncerramento" maxlength="10" >
																		&nbsp;<fmt:message key='citcorpore.comum.a' />&nbsp;
																	<input type="text" class=" span5 citdatepicker" id="dataFinalEncerramento" name="dataFinalEncerramento" maxlength="10" >
																</div>
															</div>
														</div>
														
														<div class='row-fluid'>
															<div class='span6'>
																<label><fmt:message key="contrato.contrato" /></label>
																<select id="idContrato" name="idContrato" class="span11"></select>
															</div>
															<div class='span6'>
																<label><fmt:message key="relatorio.solicitacaoReaberta.grupo" /></label>
																<select id="idGrupo" name="idGrupo" class="span11" ></select>
															</div>
														</div>
														
														<div class='row-fluid'>
															<div class='span12'>
																<div class='span6'>
																	<label><fmt:message key="citcorpore.comum.situacao" /></label>
																	<select id="situacao" name="situacao" class="span11"></select>
																</div>
																<div class='span6'>
																	<label><fmt:message key="relatorio.solicitacaoReaberta.tipoDemandaServico" /></label>
																	<select id="idTipoDemandaServico" name="idTipoDemandaServico" class="span11"></select>
																</div>
															</div>
														</div>
														<div class='row-fluid'>
															<div class='span12'>
																<div class='span2'>
																	<label><fmt:message key="citcorpore.comum.topList" /></label>
																	<select name="topList" class="span11"></select>
																</div>
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
		<script type="text/javascript" src="js/relatorioSolicitacaoReaberta.js"></script>
	</body>
</html>