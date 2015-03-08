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
		<link rel="stylesheet" type="text/css" href="./css/exportacaoContratos.css" />
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
									<h4 class="heading"><fmt:message key="exportacaoContratos.titulo"/></h4>
								</div>
								<div class="widget-body collapse in">
									<form name='form' id='form' method='post' target="_blank" action='${ctx}/pages/exportacaoContratos/exportacaoContratos.load'>
										<input type="hidden" name="export" id="export" value="n" />
										<div class='row-fluid'>
											<div class='span12'>
												<div class='row-fluid'>
													<div class='span4'>
														<label><fmt:message key="citcorpore.ui.rotulo.Cadastro" /></label>
														<select id="idContrato" name='idContrato' class="span12 Description[Contrato]"></select>
													</div>
												</div>
											</div>
										</div>
										<br/>
										<div class='row-fluid'>
											<div class='span12'>
												<div class='row-fluid'>
													<div>
														<label><fmt:message key="exportacaoContratos.grupos" /></label>
														<div id="grupos"></div>
													</div>
												</div>
											</div>
										</div>
										<br/>
										<div class='row-fluid'>
											<div class='span12'>
												<div class='row-fluid'>
													<label><fmt:message key="exportacaoContratos.exportarUnidades" /></label>
													<div class="exportar_unidades">
														<input type="radio" name="exportarUnidades" id="exportarUnidadesSim" value="y">&nbsp;<fmt:message key="citcorpore.comum.sim"/>
														<input type="radio" name="exportarUnidades" id="exportarUnidadesNao" value="n" checked="checked">&nbsp;<fmt:message key="citcorpore.comum.nao"/>
													</div>
												</div>
											</div>
										</div>
										<br/>
										<div class='row-fluid'>
											<div class='span12'>
												<div class='row-fluid'>
													<label><fmt:message key="exportacaoContratos.exportarAcordoNivelServico" /></label>
													<div class="exportar_acordo_nivel_servico">
														<input type="radio" name="exportarAcordoNivelServico" id="exportarAcordoNivelServicoSim" value="y">&nbsp;<fmt:message key="citcorpore.comum.sim"/>
														<input type="radio" name="exportarAcordoNivelServico" id="exportarAcordoNivelServicoNao" value="n" checked="checked">&nbsp;<fmt:message key="citcorpore.comum.nao"/>
													</div>
												</div>
											</div>
										</div>
										<br/>
										<div class='row-fluid'>
											<div class='span12'>
												<div class='row-fluid'>
													<label><fmt:message key="exportacaoContratos.exportarCatalogoServico" /></label>
													<div class="exportar_catalogo_servico">
														<input type="radio" name="exportarCatalogoServico" id="exportarCatalogoServicoSim" value="y">&nbsp;<fmt:message key="citcorpore.comum.sim"/>
														<input type="radio" name="exportarCatalogoServico" id="exportarCatalogoServicoNao" value="n" checked="checked">&nbsp;<fmt:message key="citcorpore.comum.nao"/>
													</div>
												</div>
											</div>
										</div>

										<br/>
										<div class='row-fluid'>
											<div class='span12'>
												<button type='button' name='btnExportar' class="lFloat btn btn-icon btn-primary" onclick='javascript:exportar();return false;'>
													<i></i><fmt:message key="citcorpore.comum.exportar" />
												</button>
												<!-- button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='document.form.clear();'>
													<i></i><fmt:message key="citcorpore.comum.limpar" />
												</button-->
											</div>
									    </div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
		<script type="text/javascript" src="js/exportacaoContratos.js"></script>
	</body>
</html>
