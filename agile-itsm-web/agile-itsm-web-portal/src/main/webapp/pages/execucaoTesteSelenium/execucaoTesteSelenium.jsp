<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>

		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>

		<script src="js/execucaoTesteSelenium.js"></script>
	
		<!-- MiniColors Plugin -->
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/color/jquery-miniColors/jquery.miniColors.js"></script>
	
		
		<!-- Easy-pie Plugin -->
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/easy-pie/jquery.easy-pie-chart.js"></script>
		
		<!--  Flot Charts Plugin -->
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/system/jquery-ui/js/jquery-ui-1.9.2.custom.min.js"></script>
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/color/jquery-miniColors/jquery.miniColors.js"></script>
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.selection.js"></script>
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.orderBars.js"></script>
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.tooltip.js"></script>
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.resize.js"></script>
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.pie.js"></script>
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.js"></script>
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/system/jquery.min.js"></script>
		<script src="${ctx}/novoLayout/common/theme/scripts/plugins/system/modernizr.js"></script>
		<script src="${ctx}/novoLayout/common/bootstrap/js/bootstrap.min.js"></script>
	
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
										<h4 class="heading"><fmt:message key="execucaoTesteSelenium"/></h4>
									</div>
												
									<div class="widget-body collapse in">	
										
										<div class="tabsbar">
											<ul>
												<li class="active"><a href="#tab1-2" data-toggle="tab"><fmt:message key="execucaoTesteSelenium.executarTestes"/></a></li>
												<li><a href="#tab2-2"id="tab2"  data-toggle="tab" ><fmt:message key="execucaoTesteSelenium.resultadoTeste"/></a></li>
											</ul>
										</div>
										
										<div class="tab-content">
										
											<div class="tab-pane active" id="tab1-2">
									
												<form name='form' action='${ctx}/pages/execucaoTesteSelenium/execucaoTesteSelenium'>
													
													<div class="span3">
													
														<label class="strong campoObrigatorio " ><fmt:message key='usuario.usuario'/></label>
														<div>													
															<input type="text" id="usuarioDoTeste" name="usuarioDoTeste" />
														</div>
																
														<label class="strong campoObrigatorio " ><fmt:message key='usuario.senha'/></label>
														<div>													
															<input type="password" id="senhaDoTeste" name="senhaDoTeste" />
														</div>
																
														<label class="strong campoObrigatorio " ><fmt:message key='execucaoTesteSelenium.navegador'/></label>
														<div>													
															<select name="navegadorDeExecucao" id ="navegadorDeExecucao" class="span12"></select>
														</div>
																				
													</div>
															
													<div class="span1" ></div>
															
													<div class="span6">
															
														<label class="strong campoObrigatorio " ><fmt:message key='execucaoTesteSelenium.pacoteDeTeste'/></label>
														<div>													
															<select name="pacotesDeTeste" id ="pacotesDeTeste" class="span12" onchange="mostrarDiv()"></select>
														</div>
																	
														<div id="divCadastrosGerais" >
															<div>
																<table>
																	<tr><td><input type='checkbox' checked="checked"  name='cargos' id='cargos'/></td><td><fmt:message key="cargo.cargo" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='clientes' id='clientes'/></td><td><fmt:message key="lookup.cliente" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='colaborador' id='colaborador'/></td><td><fmt:message key="menu.nome.colaborador" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='empresa' id='empresa'/></td><td><fmt:message key="menu.nome.empresa" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='fornecedor' id='fornecedor'/></td><td><fmt:message key="contrato.fornecedor" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='unidade' id='unidade'/></td><td><fmt:message key="menu.nome.unidade" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='usuario' id='usuario'/></td><td><fmt:message key="menu.nome.usuario" /></td></tr>
																</table>
															</div>
														</div>
																					                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
														<div id="divGerenciaDeCatalogoDeServico">
															<div>
																<table>
																	<tr><td><input type='checkbox' checked="checked"  name='tipoEventoServico' id='tipoEventoServico'/></td><td><fmt:message key="menu.nome.tipoEventoServico" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='categoriaServico' id='categoriaServico'/></td><td><fmt:message key="menu.nome.categoriaServico" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='categoriaSolucao' id='categoriaSolucao'/></td><td><fmt:message key="citcorpore.comum.categoriaSolucao" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='causaIncidente' id='causaIncidente'/></td><td><fmt:message key="visao.causaIncidentes" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='condicaoOperacao' id='condicaoOperacao'/></td><td><fmt:message key="menu.nome.condicaoOperacao" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='importanciaNegocio' id='importanciaNegocio'/></td><td><fmt:message key="importanciaNegocio.importanciaNegocio" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='justificativaSolicitacao' id='justificativaSolicitacao'/></td><td><fmt:message key="visao.justificativaSolicitacao" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='localExecucaoServico' id='localExecucaoServico'/></td><td><fmt:message key="visao.localExecucaoServico" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='prioridade' id='prioridade'/></td><td><fmt:message key="solicitacaoServico.prioridade" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='situacao' id='situacao'/></td><td><fmt:message key="servico.situacao" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='tipoServico' id='tipoServico'/></td><td><fmt:message key="menu.nome.tipoServico" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='tipoSolicitacaoServico' id='tipoSolicitacaoServico'/></td><td><fmt:message key="menu.nome.tipoSolicitacaoServico" /></td></tr>
																</table>
															</div>
														</div>
														
														<div id="divGerenciaDeConfiguracao">
															<div>
																<table>
																	<tr><td><input type='checkbox' checked="checked"  name='caracteristica' id='caracteristica'/></td><td><fmt:message key="citcorpore.comum.caracteristica" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='grupoItem' id='grupoItem'/></td><td><fmt:message key="grupoItemConfiguracao.grupo" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='midiaSoftware' id='midiaSoftware'/></td><td><fmt:message key="menu.midiaSoftware" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='softwareListaNegra' id='softwareListaNegra'/></td><td><fmt:message key="menu.nome.softwareListaNegra" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='tipoItemConfiguracao' id='tipoItemConfiguracao'/></td><td><fmt:message key="menu.nome.tipoItemConfiguracao" /></td></tr>
																</table>
															</div>
														</div>		
														
														<div id="divGerenciaDeConhecimento">
															<div>
																<table>
																	<tr><td><input type='checkbox' checked="checked"  name='categoriaGaleria' id='categoriaGaleria'/></td><td><fmt:message key="menu.nome.categoriaGaleriaImagens" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='palavraGemea' id='palavraGemea'/></td><td><fmt:message key="palavraGemea.palavraGemea" /></td></tr>
																</table>
															</div>
														</div>											
														
														<div id="divGerenciaDeIncidente">
															<div>
																<table>
																	<tr><td><input type='checkbox' checked="checked"  name='origem' id='origem'/></td><td><fmt:message key="menu.nome.origemSolicitacoes" /></td></tr>
																</table>
															</div>
														</div>						
																				
														<div id="divGestaoContrato">
															<div>
																<table>
																	<tr><td><input type='checkbox' checked="checked"  name='formulaOS' id='formulaOS'/></td><td><fmt:message key="menu.nome.formulaOs" /></td></tr>
																	<tr><td><input type='checkbox' checked="checked"  name='formula' id='formula'/></td><td><fmt:message key="menu.nome.formula" /></td></tr>
																</table>
															</div>
														</div>		
													</div>
														
													<div class="separator top"></div>
												
													<div class="row-fluid">
														<div class="span12">
															<button type='button' name='btnLimpar' class="light" onclick='document.form.fireEvent("load");'>
																<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
																<span><fmt:message key="citcorpore.comum.limpar" /></span>
															</button>
															<button type='button' name='btnExecutarRotina' class="light" onclick='executarRotina();'>
																<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
																<span><fmt:message key="execucaoTesteSelenium.executarTestes" /></span>
															</button>
														</div>
													</div>
												</form>
											</div>
											
											<div class="tab-pane" id="tab2-2">
											
												<!-- 
													<div id="chart_donut" style="height: 250px;"></div>
												 -->
												 
												<div id="resultados">
												
													<table class="table table-pricing table-pricing-2 table-bordered">
														<thead>
															<tr>
																<th class="shortRight" ><fmt:message key="citcorpore.comum.resultados" /></th>
																<th></th>
																<th>Quantidade</th>
															</tr>
														</thead>
														<tbody>
														<tr>
															<td class="shortRight"><fmt:message key="pagamentoProjeto.total" /></td>
																<td>
																	<div class="progress">
																		<div class="bar totalPct" style="width: 0%;"></div>
																	</div>
																</td>
																<td class="totalQtd"></td>
															</tr>
															<tr>
																<td class="shortRight"><fmt:message key="execucaoTesteSelenium.sucesso" /></td>
																<td>
																	<div class="progress progress-success">
																		<div class="bar sucessoPct" style="width: 0%;"></div>
																	</div>
																</td>
																<td class="sucessoQtd"></td>
															</tr>
															<tr>
																<td class="shortRight"><fmt:message key="execucaoTesteSelenium.falha" /></td>
																<td>
																	<div class="progress progress-danger">
																		<div class="bar falhaPct" style="width: 0%;"></div>
																	</div>
																</td>
																<td class="falhaQtd"></td>
															</tr>
														</tbody>
													</table>
												
													<div class="row-fluid">
													
														<div class="span6">
														
															<div class="box-generic">															
																<div id="testesExecutadosComSucesso" name="testesExecutadosComSucesso"></div>
															</div>
			
														</div>
														<div class="span6">
														
															<div class="box-generic">
																<div id="testesExecutadosComFalha" name="testesExecutadosComFalha">
															</div>
															
														</div>
													</div>
												
												</div>
												
											</div>
											
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
		</div>

		<!--  Fim conteudo-->
		<%@include file="/novoLayout/common/include/rodape.jsp" %>
	</body>
</html>