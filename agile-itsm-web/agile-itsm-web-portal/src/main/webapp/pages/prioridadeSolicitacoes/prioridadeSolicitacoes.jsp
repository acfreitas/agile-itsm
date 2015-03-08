<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<!doctype html public "">
<html>
	<head>
	<%
	    String iframe = "";
	    iframe = request.getParameter("iframe");
	%>
	<%@include file="/include/header.jsp"%>

	<title><fmt:message key="citcorpore.comum.title" /></title>

	<%@include file="/include/security/security.jsp"%>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<script charset="ISO-8859-1"  type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

	<link rel="stylesheet" type="text/css" href="./css/prioridadeSolicitacoes.css" />

	<script type="text/javascript" src="./js/prioridadeSolicitacoes.js"></script>
	</head>
	<body>
		<script type="text/javascript" src="../../cit/objects/ImpactoDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/UrgenciaDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/MatrizPrioridadeDTO.js"></script>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
				<div class="flat_area grid_16">
					<h2>
						<fmt:message key="prioridade.titulo" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li><a href="#tabs-1"><fmt:message key="prioridade.cadastro" /></a></li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div class="block">
							<div class="parametros">
								<form name='form' action='${ctx}/pages/prioridadeSolicitacoes/prioridadeSolicitacoes'>
									<div class="columns clearfix">
										<div>
											<div class="col_50">
												<fieldset style="padding-top: 15px; border: none !important;">
													<label><fmt:message key="prioridade.impacto" /></label>
													<div>
														<br>
														<div id="divNivelImpacto" style="padding-top:10px">
															<input type='hidden' id='NIVELIMPACTOSERELIALIZADO' name='NIVELIMPACTOSERELIALIZADO'/>
															<table id="tabelaImpacto" >
																<tr>
																	<td>
																		<label style=" padding-right: 15px;width: 92%;" class="campoObrigatorio"><fmt:message key="prioridade.nivelImpacto" />  </label>
																	</td>
																	<td>
																		<input type='TEXT' name='NIVELIMPACTO' size='100' maxlength='100'/>
																	</td>
																	<td>
																		<label style="padding-left: 5px; padding-right: 15px;width: 70%;" class="campoObrigatorio"><fmt:message key="prioridade.sigla" />  </label>
																	</td>
																	<td>
																		<input type='TEXT' name='SIGLAIMPACTO' size='1' maxlength='1'/>
																	</td>
																	<td>
																		<img title="Adicionar nível do Impacto" src="/citsmart/imagens/add.png" onclick="addImpacto();" border="0" style="cursor:pointer">
																	</td>
																</tr>
															</table>
															<button type='button' name='btnGravar' class="light" onclick='gravarImpacto();'>
																<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
																<span><fmt:message key="prioridade.gravarImpacto"/></span>
															</button>
														</div>
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset style="padding-top: 15px; border: none !important;">
													<label><fmt:message key="prioridade.urgencia" /></label>
													<div>
														<br>
														<div id="divNivelUrgencia" style="padding-top:10px">
															<input type='hidden' id='NIVELURGENCIASERELIALIZADO' name='NIVELURGENCIASERELIALIZADO'/>
															<table id="tabelaUrgencia" >
																<tr>
																	<td>
																		<label style=" padding-right: 15px;width: 100%;" class="campoObrigatorio"><fmt:message key="prioridade.nivelUrgencia" />  </label>
																	</td>
																	<td>
																		<input type='TEXT' name='NIVELURGENCIA' size='100' maxlength='100'/>
																	</td>
																	<td>
																		<label style="padding-left: 5px; padding-right: 15px;width: 70%;" class="campoObrigatorio"><fmt:message key="prioridade.sigla" />  </label>
																	</td>
																	<td>
																		<input type='TEXT' name='SIGLAURGENCIA' size='1' maxlength='1'/>
																	</td>
																	<td>
																		<img title="Adicionar nível da Urgência" src="/citsmart/imagens/add.png" onclick="addUrgencia();" border="0" style="cursor:pointer">
																	</td>
																</tr>
															</table>
															<button type='button' name='btnGravar' class="light" onclick='gravarUrgencia();'>
																<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
																<span><fmt:message key="prioridade.gravarUrgencia"/></span>
															</button>
														</div>
													</div>
												</fieldset>
											</div>
										</div>
									<div class="col_100" id="divBotaoMatrizPrioridade">
										<div class="columns clearfix">
											<div class="col_100" style="padding-left: 20px; padding-top: 10px;">
													<button id="addMatrizPrioridade" type="button" name="addMatrizPrioridade" class="light" onclick="matrizPrioridade()">
														<img src="${ctx}/template_new/images/icons/small/util/adcionar.png">
														<span><fmt:message key="prioridade.matrizprioridade.cadastro"/></span>
													</button>
											</div>
										</div>
									</div>
									<div id="divAdicionarMatriz" style="left: 10px; top: 10px; padding: 7px; display: none;">
										<div class="columns clearfix">
											<div>
												<fieldset style="padding-top: 15px; border: none !important;">
													<label><fmt:message key="prioridade.matrizprioridade.info" /></label>
													<div>
														<br>
														<div id="divNivelUrgencia" style="padding-top:10px">
															<input type='hidden' id='MATRIZPRIORIDADESERELIALIZADO' name='MATRIZPRIORIDADESERELIALIZADO'/>
															<table id="tabelaCadastroMatriz" >
																<tr>
																	<td>
																		<span style="padding-right: 15px;" class="campoObrigatorio"><fmt:message key="prioridade.nivelImpacto" />  </span>
																	</td>
																	<td>
																		<div><select id="IDIMPACTOSELECT" name="IDIMPACTOSELECT"></select></div>
																	</td>
																	<td>
																		<span style="padding-right: 15px; padding-left: 15px;" class="campoObrigatorio"><fmt:message key="prioridade.nivelUrgencia" />  </span>
																	</td>
																	<td>
																		<div><select id="IDURGENCIASELECT" name="IDURGENCIASELECT"></select></div>
																	</td>
																	<td>
																		<span style="padding-right: 15px; padding-left: 15px;" class="campoObrigatorio"><fmt:message key="prioridade.matrizpriopridade.valor" />  </span>
																	</td>
																	<td>
																		<input type='TEXT' name='VALORPRIORIDADE' size='5' maxlength='5' class="Format[Numero]"/>
																	</td>
																	<td>
																		<img title="Adicionar situação na Matriz de Prioridade" src="/citsmart/imagens/add.png" onclick="addLinhaMatriz();" border="0" style="cursor:pointer;">
																	</td>
																</tr>
															</table>
															<div id="gridMatrizPrioridade" style="display: none;">
																<table class="table" id="tabelaMatrizPrioridade" style="width: 50%">
																	<tr>
																		<th style="width: 16px !important;"></th>
																		<th style="width: 30%;"><fmt:message key="prioridade.nivelImpacto"/></th>
																		<th style="width: 30%;"><fmt:message key="prioridade.nivelUrgencia"/></th>
																		<th style="width: 30%;"><fmt:message key="prioridade.matrizpriopridade.valor"/></th>
																	</tr>
																</table>
															</div>
															<button type='button' name='btnGravar' class="light" onclick='gravarMatriz()'>
																<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
																<span><fmt:message key="prioridade.matrizprioridade.gravar"/></span>
															</button>
														</div>
													</div>
												</fieldset>
											</div>
										</div>
									</div>

								</div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>

		<!-- Fim da Pagina de Conteudo -->
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>


