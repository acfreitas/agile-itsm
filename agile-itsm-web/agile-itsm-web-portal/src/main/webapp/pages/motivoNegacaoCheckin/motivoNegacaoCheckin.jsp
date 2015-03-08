<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>

<!DOCTYPE html>
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
	</head>

	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>

	<body>
		<input type="hidden" id="pesqLockupLOOKUP_MENU_idlingua"/>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">

			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">
				<% if(iframe == null) { %>
				<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
				<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				<% } %>
			</div>

			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">
				<div id="content">
					<div class="separator top"></div>
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="motivoNegacaoCheckin.motivoNegacaoCheckin"/></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tabsbar">
										<ul>
											<li class="active">
												<a href="#tab1-3" data-toggle="tab">
													<fmt:message key="motivoNegacaoCheckin.cadastrarMotivoNegacaoCheckin"/>
												</a>
											</li>
											<li>
												<a href="#tab2-3" data-toggle="tab">
													<fmt:message key="motivoNegacaoCheckin.pesquisaMotivoNegacaoCheckin"/>
												</a>
											</li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name="form" action="${ctx}/pages/motivoNegacaoCheckin/motivoNegacaoCheckin">
												<input type="hidden" name="idMotivo" id="idMotivo" />

												<div class="row-fluid">
													<div class="span12">
														<div class="row-fluid">
															<div class="span6">
																<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.descricao" /></label>
																<input id="descricao" name="descricao" type="text" maxlength="100" class="span12 Valid[Required] Description[citcorpore.comum.descricao]" />
															</div>
														</div>
													</div>
												</div>
												<div class="row-fluid">
													<div class="span12">
														<button type="button" name="btnGravar" class="lFloat btn btn-icon btn-primary" onclick="document.form.save();">
															<i></i><fmt:message key="citcorpore.comum.gravar" />
														</button>
														<button type="button" name="btnLimpar" class="lFloat btn btn-icon btn-primary" onclick="document.form.clear();">
															<i></i><fmt:message key="citcorpore.comum.limpar" />
														</button>
														<button type="button" name="btnUpDate" class="lFloat btn btn-icon btn-primary" onclick="excluir();">
															<i></i><fmt:message key="citcorpore.comum.excluir" />
														</button>
													</div>
												</div>
											</form>
										</div>
										<div class="tab-pane" id="tab2-3">
											<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
												<form name="formPesquisa">
													<cit:findField formName="formPesquisa"
					 									lockupName="LOOKUP_MOTIVONEGACAOCHECKIN" id="LOOKUP_MOTIVONEGACAOCHECKIN" top="0"
					 									left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
		<script type="text/javascript" src="${ctx}/js/motivoNegacaoCheckin.js"></script>
	</body>
</html>
