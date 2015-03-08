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
									<h4 class="heading"><fmt:message key="deParaCatalogoServicos.titulo"/></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/deParaCatalogoServicosBI/deParaCatalogoServicosBI'>

												<div class='row-fluid'>
													<div class='span12'>
														<div class='row-fluid'>
															<div class='span12'>
																<label class="campoObrigatorio"><fmt:message key="contrato.cliente" /></label>
																<select name='idConexaoBI' style='width: 250px;' class="span12 Valid[Required] Description[contrato.cliente]" onchange= "listar();"></select>
															</div>
														</div>
														<div class='row-fluid'>
 															<div class='span12'>
																<div class='span5'>
																	<div class='row-fluid'>
																		<div class='span4'>
																			<label class="strong campoObrigatorio"><fmt:message key="lookup.idServico" /></label>
																			<input class="span12" type="text" name="idServicoDe" id="idServicoDe" required="required" onkeydown="onkeyDownIdDe(event);" onkeyup="enterOnIdDe(event);">
																		</div>
																		<div class='span8'>
																			<label class="strong"><fmt:message key="servico.nome" /></label>
																			<input class="span12" type="text" name="servicoDe" id="servicoDe">
																		</div>
																	</div>
																</div>

																<div class='span5'>
																	<div class='row-fluid'>
																		<div class='span4'>
																			<label class="strong campoObrigatorio"><fmt:message key="lookup.idservicocorpore" /></label>
																			<input class="span12" type="text" name="idServicoPara" id="idServicoPara" required="required" onkeydown="onkeyDownIdPara(event);" onkeyup="enterOnIdPara(event);">
																		</div>
																		<div class='span8'>
																			<label class="strong"><fmt:message key="servicoCorporeBI.nomeServico" /></label>
																			<input class="span12" type="text" name="servicoPara" id="servicoPara">
																		</div>
																	</div>
																</div>

																<div class='span2'>
																	<button type='button' name='btnRelacionar' class="lFloat btn btn-icon btn-primary" style="position:relative;top:23px;" onclick='relacionar();'>
																		<i></i><fmt:message	key="citcorpore.comum.relacionar" />
																	</button>
																</div>

															</div>
														</div>

														<div class='row-fluid'>
															<div class='span12'>
																<table id="tblDePara" name="tblDePara" class="table  table-bordered">
																	<tr>
																		<th height="10px" width="2%"></th>
																		<th height="10px" width="9%"><fmt:message	key="lookup.idServico" /></th>
																		<th height="10px" width="40%"><fmt:message	key="servico.nome" /></th>
																		<th height="10px" width="9%"><fmt:message	key="lookup.idservicocorpore" /></th>
																		<th height="10px" width="40%"><fmt:message	key="servicoCorporeBI.nomeServico" /></th>
																	</tr>
																</table>
															</div>

														</div>
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
		<script type="text/javascript" src="js/deParaCatalogoServicosBI.js"></script>
	</body>
</html>