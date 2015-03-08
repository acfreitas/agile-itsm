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
		<link type="text/css" rel="stylesheet" href="css/historicoFuncional.css"/>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/jqueryautocomplete.css"/>
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
									<h4 class="heading"><fmt:message key="rh.historicoFuncional"/></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/historicoFuncional/historicoFuncional'>
												<input type='hidden' name='idHistoricoFuncional' id='idHistoricoFuncional' />
												<input type='hidden' name='idEmpregado' id="idEmpregado"/>
												<input type='hidden' name='btnPesquisar' id="btnPesquisar"/>
												<input type='hidden' name='paginaSelecionada' id='paginaSelecionada'/>
												<input type='hidden' name='tipo' id='tipo'/>
												<input type='hidden' name='chkListaNegra' id='chkListaNegra'/>

												<div class="innerLR">
													<div class="row-fluid">
														<div class="span12 filtro filtrobar main">
															<div id="divEmpregado" class="span3">
																<label for="nome" class='strong'><fmt:message key="citcorpore.comum.nome"/></label>
																<input id="nome" type="text" name="nome" maxlength="150" class="span10 text" />
															</div>
															<div class="span3">
																<label for="cpf" class="strong"><fmt:message key="colaborador.cpf"/></label>
																<input id="cpf" type="text" name="cpf" maxlength="14" class="span10 text" />
															</div>
															<div class="span1 innerTB">
																<button class="btn btn-icon btn-primary" type="button" name="btnPesquisar" id="btnPesquisar" onclick="pesquisar();"><i></i><fmt:message key="citcorpore.comum.pesquisar" /></button>
															</div>
															<div class='span4 innerTB'>
																<ul>
																<li id='acoes' class='btn-group btn-block span4'>
																	<div class='leadcontainer'>
																		<button type='button' class='btn dropdown-lead btn-default' onclick="maisFiltros();"><fmt:message key="citcorpore.comum.maisfiltros"/></button>
																	</div>
																	<a class='btn btn-default dropdown-toggle filtro-toogle' href='#' data-toggle='dropdown' rel='dropdownFiltro' ><span class='caret'></span></a>
																	<ul class="dropdownFiltro dropdown-menu pull-right">
																		<li class="dropdownFiltroPesquisa">
																			<div class="span12">
														                       <input type='checkbox' name='chkColaborador' id="chkColaborador" onclick="checarSituacao()"/><fmt:message key="rh.exibirSomente"/> <fmt:message key="colaborador.colaborador"/>
													                 	 	</div>
																		</li>
																		<li class="dropdownFiltroPesquisa">
																			<div class="span12">
														                       <input type='checkbox' name='chkExColaborador' id="chkExColaborador" onclick="checarSituacao()"/><fmt:message key="rh.exibirSomente"/> <fmt:message key="candidato.exColaborador"/>
													                 	 	</div>
																		</li>
																		<li class="dropdownFiltroPesquisa">
																			<div class="span12">
														                       <input type='checkbox' name='chkColaboradorAfastado' id="chkColaboradorAfastado" onclick="checarSituacao()"/><fmt:message key="rh.exibirSomente"/> <fmt:message key="candidato.colaboradorAfastado"/>
													                 	 	</div>
																		</li>
																		<li class="dropdownFiltroPesquisa">
																			<div class="span12">
														                       <input type='checkbox' name='chkCandidatoExterno' id="chkCandidatoExterno" onclick="checarSituacao()"/><fmt:message key="rh.exibirSomente"/> <fmt:message key="candidato.candidatoExterno"/>
													                 	 	</div>
																		</li>
																		<li class="dropdownFiltroPesquisa">
																			<div class="span12">
														                       <input type='checkbox' name='chkBlackList' id="chkBlackList" onclick="checarSituacao()"/><fmt:message key="rh.listarBlackList"/>
													                 	 	</div>
																		</li>
																	</ul>
																</li>
																</ul>
															</div>
															<div class="span1 innerTB ">
																<button class="btn btn-icon btn-primary alinharEsquerda" type="button" name="btnLimpar" id="btnLimpar" onclick="limpar();"><i></i>Limpar</button>
															</div>
														</div>
													</div>
													<div class="widget">
														<div class="row-fluid">
															<div class="widget-head">
																<h4 class="heading"><fmt:message key="rh.pesquisaCanditado"/></h4>
															</div>
														</div>

														<div id="resulPesquisa" class="innerTB">
															<div class="row-fluid">
																<table id="tblPesquisa" class="table table-condensed" style="overflow: auto">
																	<tr>
																		<th style="width: 5%"><fmt:message key="rh.foto"/></th>
																		<th style="width: 55%"><fmt:message key="logs.dados"/></th>
																		<th style="width: 20%"><fmt:message key="sla.situacao"/></th>
																		<th style="width: 15%"><fmt:message key="rh.acoes"/></th>
																	</tr>
																</table>
															</div>
														</div>
														<div id="paginas" class="col_50" align="center" style="height: 50px;" ></div>
														<!-- MODAL -->
														<!--MODAL VISUALIZAR HISTORICO -->
														<div class="modal hide fade in" id="modal_visualizarHistorico" aria-hidden="false">
															<!-- Modal heading -->
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
															</div>
															<!-- // Modal heading END -->
															<!-- Modal body -->
															<div class="modal-body">
																<div id="conteudoVisualizarHistorico">
																</div>
															</div>
															<!-- // Modal body END -->
														</div>
														<!--MODAL ADICIONAR ITEM HISTORICO -->
														<div class="modal hide fade in" id="modal_itemHistorico" aria-hidden="false">
															<!-- Modal heading -->
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
																<h3><fmt:message key="rh.novoItemHistorico"/></h3>
															</div>
															<!-- // Modal heading END -->
															<!-- Modal body -->
															<div class="modal-body">
																<div id="conteudoItemHistorico">
																</div>
															</div>
															<!-- // Modal body END -->
														</div>
														<!--MODAL ADICIONAR BLACK LIST -->
														<div class="modal hide fade in" id="modal_blackList" aria-hidden="false">
															<!-- Modal heading -->
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
																<h3><fmt:message key="rh.cadastroBlackList"/></h3>
															</div>
															<!-- // Modal heading END -->
															<!-- Modal body -->
															<div class="modal-body">
																<div id="conteudoBlackList">
																</div>
															</div>
															<!-- // Modal body END -->
														</div>
														<!-- FIM MODAL -->
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
				<script src="${ctx}/pages/historicoFuncional/js/historicoFuncional.js"></script>
				<script src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>
				<script src="${ctx}/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
			</div>
		</div>
	</body>
</html>