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
<%@include file="/novoLayout/common/include/titulo.jsp"%>

<link type="text/css" rel="stylesheet"
	href="../../novoLayout/common/include/css/template.css" />
	<link rel="stylesheet" type="text/css" href="./css/relatorioKpiProdutividade.css" />
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title=""
	style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div class="<%=(iframe == null) ? "container-fluid fixed" : ""%>">

		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<div
			class="navbar <%=(iframe == null) ? "main" : "nomain"%> hidden-print">

			<%
				if (iframe == null) {
			%>
			<%@include file="/novoLayout/common/include/cabecalho.jsp"%>
			<%@include file="/novoLayout/common/include/menuPadrao.jsp"%>
			<%
				}
			%>

		</div>

		<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper"%>">

			<!-- Inicio conteudo -->
			<div id="content">
				<div class="separator top"></div>
				<div class="row-fluid">
					<div class="innerLR">
						<div class="widget">
							<div class="widget-head">
								<h4 class="heading">
									<fmt:message key="relatorioKpi.titulo" />
								</h4>
							</div>
							<div class="widget-body collapse in">
								<form name="form" id="form"action="${ctx}/pages/relatorioKpiProdutividade/relatorioKpiProdutividade.load">
									<input type="hidden" id='formatoArquivoRelatorio'name='formatoArquivoRelatorio'>
									<input type="hidden" id='listaUsuarios'name='listaUsuarios'>
									<input type="hidden" id='listaGrupoUnidade'name=listaGrupoUnidade>

									<!--Datas-->
									<div class='row-fluid'>
											<div class='span4'>
												<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
												<input style="cursor: pointer; background: #FFF;" type="text" readonly="readonly" class=" span5 citdatepicker" id="dataInicio" name="dataInicio" maxlength="10" required="required" >
												&nbsp;<fmt:message key='citcorpore.comum.a' />&nbsp;
												<input style="cursor: pointer; background: #FFF;" type="text" class=" span5 citdatepicker" readonly="readonly" id="dataFim" name="dataFim" maxlength="10" required="required" >
											</div>
										</div>
									<!--Contrato-->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<label class="strong campoObrigatorio"><fmt:message key="contrato.contrato" /></label>
													<select id="idContrato" name="idContrato" class="span12 Valid[Required] Description[citcorpore.comum.nome]" onchange="preencherGrupoUnidade(this);">
													</select>
												</div>
											</div>
										</div>
									</div>

									<!--Mostrar Grupo ou Unidade-->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<label class="strong campoObrigatorio"><fmt:message key="relatorioKpi.FiltrarPor" /></label>
													<div class="widget-body uniformjs">
														<label class="radio">
															<input type="radio" class="radio" value="grupo" checked="checked" name="selecionarGrupoUnidade" id="selecionarGrupoUnidade1" onclick="mostrarGrupoUnidade(this);preencherGrupoUnidade(idContrato);"/>
															<fmt:message key="menu.nome.grupo" />
														</label><br/>
														<label class="radio">
															<input type="radio" class="radio" value="unidade" name="selecionarGrupoUnidade" id="selecionarGrupoUnidade" onclick="mostrarGrupoUnidade(this);preencherGrupoUnidade(idContrato);"/>
															<fmt:message key="menu.nome.unidade" />
														</label><br/>
													</div>
												</div>
											</div>
										</div>
									</div>

									<!--Grupo-->
									<div class='row-fluid' style="display: show;" id="Grupo">
										<div class='span10'>
												<div class='span3'>
													<label class="strong campoObrigatorio"><fmt:message key="relatorioKpi.SelecionarGrupo" /></label>
													<select size="8" id="primeiraListaGrupo" name="primeiraListaGrupo" style="width: 85%;">
													</select>
												</div>
												<div class='row-fluid span3'>
												<div class='span10'>
													<div class="separator top"></div>
													<span class="btn btn-block btn-primary" onclick="inserirNaLista('primeiraListaGrupo','segundaListaGrupo',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionar" />
													</span>
													<span class="btn btn-block btn-primary" onclick="RetirarDaLista('primeiraListaGrupo','segundaListaGrupo',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
													</span>
													<span class="btn btn-block btn-primary" onclick="inserirTodosDaLista('primeiraListaGrupo','segundaListaGrupo',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionarTodos" />
													</span>
													<span class="btn btn-block btn-primary" onclick="retirarTodosDaLista('primeiraListaGrupo','segundaListaGrupo',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
													</span>
												</div>
												</div>
												<div class='span3'>
													<label class="strong campoObrigatorio"><fmt:message key="relatorioKpi.Grupos" /></label>
													<select size="8" id="segundaListaGrupo" name="segundaListaGrupo" style="width: 85%;">
													</select>
												</div>
										</div>
									</div>


									<!--Unidade-->
									<div class='row-fluid' style="display: none;" id="Unidade">
										<div class='span10'>
												<div class='span3'>
													<label class="strong campoObrigatorio"><fmt:message key="relatorioKpi.SelecionarUnidade" /></label>
													<select size="8" id="primeiraListaUnidade" name="primeiraListaUnidade" style="width: 85%;">
													</select>
												</div>
												<div class='row-fluid span3'>
												<div class='span10'>
													<div class="separator top"></div>
													<span class="btn btn-block btn-primary" onclick="inserirNaLista('primeiraListaUnidade','segundaListaUnidade',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionar" />
													</span>
													<span class="btn btn-block btn-primary" onclick="RetirarDaLista(primeiraListaUnidade','segundaListaUnidade',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
													</span>
													<span class="btn btn-block btn-primary" onclick="inserirTodosDaLista('primeiraListaUnidade','segundaListaUnidade',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionarTodos" />
													</span>
													<span class="btn btn-block btn-primary" onclick="retirarTodosDaLista('primeiraListaUnidade','segundaListaUnidade',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
													</span>
												</div>
												</div>
												<div class='span3'>
													<label class="strong campoObrigatorio"><fmt:message key="relatorioKpi.Unidades" /></label>
													<select size="8" id="segundaListaUnidade" name="segundaListaUnidade" style="width: 85%;">
													</select>
												</div>
										</div>
									</div>


								<%-- 	<!--MostrarFuncionario-->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<label class="strong campoObrigatorio"><fmt:message key="relatorioKpi.SelecionarColaborador" /></label>
													<div class="widget-body uniformjs">
														<label class="radio">
															<input type="radio" class="radio" value="S" name="selecionarColaborador" id="selecionarColaborador1" onclick="mostrarFuncionario(this);"/>
															<fmt:message key="citcorpore.comum.sim" />
														</label><br/>
														<label class="radio">
															<input type="radio" class="radio" value="N" checked="checked" name="selecionarColaborador" id="selecionarColaborador2" onclick="mostrarFuncionario(this);"/>
															<fmt:message key="citcorpore.comum.nao" />
														</label><br/>
													</div>
												</div>
											</div>
										</div>
									</div> --%>

									<!--Funcionario-->
									<div class='row-fluid' style="display: show;" id="funcionario">
										<div class='span10'>
												<div class='span3'>
													<label class="strong campoObrigatorio"><fmt:message key="relatorioKpi.SelecionarColaborador" /></label>
													<select size="8" id="primeiraLista" name="primeiraLista" style="width: 85%;">
													</select>
												</div>
												<div class='row-fluid span3'>
												<div class='span10'>
													<div class="separator top"></div>
													<span class="btn btn-block btn-primary" onclick="inserirNaLista('primeiraLista','segundaLista',true);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionar" />
													</span>
													<span class="btn btn-block btn-primary" onclick="RetirarDaLista('primeiraLista','segundaLista',true);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
													</span>
													<span class="btn btn-block btn-primary" onclick="inserirTodosDaLista('primeiraLista','segundaLista',true);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionarTodos" />
													</span>
													<span class="btn btn-block btn-primary" onclick="retirarTodosDaLista('primeiraLista','segundaLista',true);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
													</span>
												</div>
												</div>
												<div class='span3'>
													<label class="strong campoObrigatorio"><fmt:message key="relatorioKpi.ColaboradoresSelecionados" /></label>
													<select size="8" id="segundaLista" name="segundaLista" style="width: 85%;">
													</select>
												</div>
										</div>
									</div>

									<!--MostrarSolicitações/Incidente-->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<label class="strong campoObrigatorio"><fmt:message key="portal.carrinho.tipoSolicitacao" /></label>
													<div class="widget-body uniformjs">
														<label class="checkbox">
															<input type="checkbox" class="radio" name="checkMostrarIncidentes" value="S" id="checkMostrarIncidentes"/>
															<fmt:message key="requisitosla.incidente" />
														</label>
														<label class="checkbox">
															<input type="checkbox" class="radio" name="checkMostrarRequisicoes" value="S" checked="checked" id="checkMostrarRequisicoes"/>
															<fmt:message key="requisicaoProduto.requisicao" />
														</label><br/>
													</div>
												</div>
											</div>
										</div>
									</div>

									<!-- situacao -->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<label class="strong campoObrigatorio"><fmt:message key="requisitosla.situacao" /></label>
													<select id="situacao" name="situacao" class="span12 Valid[Required] Description[citcorpore.comum.nome]" >
														<option value=''><fmt:message key="citcorpore.comum.todos" /></option>
														<option value='Fechada'><fmt:message key="citcorpore.comum.resolvida" /></option>
														<option value='Cancelada'><fmt:message key="solicitacaoServico.situacao.Cancelada" /></option>
													</select>
												</div>
											</div>
										</div>
									</div>



									<div class="separator top"></div>
									<div class='row-fluid' >
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<button style="display: none;" class="btn btn-default btn-primary" type="button" onclick="imprimirRelatorio(this);" id="btnRelatorio" name='btnRelatorio' value="pdf">
														<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png"style="padding-left: 8px;">
														<span>
															<fmt:message key="citcorpore.comum.gerarRelatorio" />
														</span>
													</button>

													<button class="btn btn-default btn-primary" type="button" onclick="imprimirRelatorio(this);" id="btnRelatorio" name='btnRelatorio' value="xls">
														<img src="${ctx}/template_new/images/icons/small/util/excel.png"style="padding-left: 8px;">
														<span>
															<fmt:message key="citcorpore.comum.gerarRelatorio" />
														</span>
													</button>

													<button class="btn btn-default btn-primary" type="button" id="btnRelatorio" name='btnRelatorio' onclick='limpar();'>
														<img src="${ctx}/template_new/images/icons/small/grey/clear.png"style="padding-left: 8px;">
														<span>
															<fmt:message key="citcorpore.comum.limpar" />
														</span>
													</button>
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
			<!--  Fim conteudo-->
			<%@include file="/novoLayout/common/include/rodape.jsp"%>
		</div>
	</div>
	<script type="text/javascript" src="./js/relatorioKpiProdutividade.js"></script>
</body>
</html>

