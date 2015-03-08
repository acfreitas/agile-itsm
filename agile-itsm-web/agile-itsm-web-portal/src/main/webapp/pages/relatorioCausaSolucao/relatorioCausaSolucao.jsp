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
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
<link type="text/css" rel="stylesheet"
	href="../../novoLayout/common/include/css/template.css" />
	<link rel="stylesheet" type="text/css" href="./css/relatorioCausaSolucao.css" />
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
									<fmt:message key="relatorioCausaSolucao.titulo" />
								</h4>
							</div>
							<div class="widget-body collapse in">
								<form name='form' action='${ctx}/pages/relatorioCausaSolucao/relatorioCausaSolucao'>
									<input type="hidden" id='idSolicitante' name='idSolicitante'/>
									<input type="hidden" id='generationType' name='generationType'/>

									<!--Datas-->
									<div class='row-fluid'>
										<div class='span4'>
											<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
											<input style="cursor: pointer; background: #FFF;" type="text" class=" span5 datepicker" id="dataInicio" name="dataInicio" maxlength="10" required="required" >
											&nbsp;<fmt:message key='citcorpore.comum.a' />&nbsp;
											<input style="cursor: pointer; background: #FFF;" type="text" class=" span5 datepicker" id="dataFim" name="dataFim" maxlength="10" required="required" >
										</div>
									</div>

									<!--Contrato-->
									<div class='row-fluid'>
										<div class='span6'>
											<label class="strong campoObrigatorio"><fmt:message key="contrato.contrato" /></label>
											<select id="idContrato" name="idContrato" class="span12 Description[contrato.contrato]" onchange="preencherGrupo(this);limparServico();"></select>
										</div>
									</div>

									<!--Tipo demanda serviço-->
									<div class='row-fluid'>
										<div class='span6'>
											<label class="strong"><fmt:message key="tipoServico.tipoServico" /></label>
											<select id="idTipoDemandaServico" name="idTipoDemandaServico" class="span12 Description[tipoServico.tipoServico]"></select>
										</div>
									</div>

									<!--Situação-->
									<div class='row-fluid'>
										<div class='span6'>
											<label class="strong"><fmt:message key="citcorpore.comum.situacao" /></label>
											<select id="situacao" name="situacao" class="span12 Description[citcorpore.comum.situacao]"></select>
										</div>
									</div>

									<!--Serviço-->
									<div class="row-fluid">
										<div class="span8">
											<div class="input-append span8" id='divNomeDoServico'>
												<label class="strong">
													<fmt:message key="servico.nome" />&nbsp;&nbsp;<i>* <fmt:message key="relatorioCausaSolucao.servicoEspecifico" />.</i>
												</label>

												<input class="span12" type="text" name="servicoBusca" id="servicoBusca" required="required" placeholder="Digite o nome do Serviço" onfocus="verificaContratoETipoServico();">
												<span class="add-on">
													<i class="icon-search"></i>
												</span>
											</div>
										</div>
									</div>
									<div class='row-fluid' style="display: show;" id="servicos">
										<div class='span3'>
												<label class="strong"><fmt:message key="notificacao.servicos" /></label>
												<select size="8" id="idServicos" name="idServicos" style="width: 200%;" multiple="multiple"></select>
										</div>
									</div>
									<div class='row-fluid'>
										<div class='span2'>
											<span class="btn btn-block btn-primary" onclick="RetirarDaListaSimples('idServicos', true);">
												<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
											</span>
										</div>
										<div class='span2'>
											<span class="btn btn-block btn-primary" onclick="retirarTodosDaListaSimples('idServicos', true);">
												<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
											</span>
										</div>
									</div>

									<div class="separator top"></div>

									<div class='row-fluid' style="display: show;" id="Grupo">
										<div class='span10'>
												<div class='span3'>
													<label class="strong"><fmt:message key="relatorioKpi.SelecionarGrupo" /></label>
													<select size="8" id="primeiraListaGrupo" name="primeiraListaGrupo" style="width: 85%;">
													</select>
												</div>
												<div class='row-fluid span3'>
												<div class='span10'>
													<div class="separator top"></div>
													<span class="btn btn-block btn-primary" onclick="inserirNaLista('primeiraListaGrupo','idGrupos',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionar" />
													</span>
													<span class="btn btn-block btn-primary" onclick="RetirarDaLista('primeiraListaGrupo','idGrupos',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
													</span>
													<span class="btn btn-block btn-primary" onclick="inserirTodosDaLista('primeiraListaGrupo','idGrupos',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionarTodos" />
													</span>
													<span class="btn btn-block btn-primary" onclick="retirarTodosDaLista('primeiraListaGrupo','idGrupos',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
													</span>
												</div>
												</div>
												<div class='span3'>
													<label class="strong campoObrigatorio"><fmt:message key="relatorioKpi.Grupos" /></label>
													<select size="8" id="idGrupos" name="idGrupos" style="width: 85%;" multiple="multiple"></select>
												</div>
										</div>
									</div>

									<div class="separator top"></div>

									<div class='row-fluid' style="display: show;" id="Grupo">
										<div class='span10'>
												<div class='span3'>
													<label class="strong"><fmt:message key="relatorioCausaSolucao.selecionarCausa" /></label>
													<select size="8" id="primeiraListaCausa" name="primeiraListaCausa" style="width: 85%;"></select>
												</div>
												<div class='row-fluid span3'>
												<div class='span10'>
													<div class="separator top"></div>
													<span class="btn btn-block btn-primary" onclick="inserirNaLista('primeiraListaCausa','idCausas',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionar" />
													</span>
													<span class="btn btn-block btn-primary" onclick="RetirarDaLista('primeiraListaCausa','idCausas',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
													</span>
													<span class="btn btn-block btn-primary" onclick="inserirTodosDaLista('primeiraListaCausa','idCausas',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionarTodos" />
													</span>
													<span class="btn btn-block btn-primary" onclick="retirarTodosDaLista('primeiraListaCausa','idCausas',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
													</span>
												</div>
												</div>
												<div class='span3'>
													<label class="strong campoObrigatorio"><fmt:message key="problema.causa" /></label>
													<select size="8" id="idCausas" name="idCausas" style="width: 85%;" multiple="multiple"></select>
												</div>
										</div>
									</div>
									<div class='row-fluid'>
										<div class='span3'>
											<input type="checkbox" name="exibeSemCausa" id="exibeSemCausa" value="s"/>&nbsp;<fmt:message key="relatorioCausaSolucao.exibeSemCausa" />
										</div>
									</div>

									<div class="separator top"></div>

									<div class='row-fluid' style="display: show;" id="Grupo">
										<div class='span10'>
												<div class='span3'>
													<label class="strong"><fmt:message key="relatorioCausaSolucao.selecionarSolucao" /></label>
													<select size="8" id="primeiraListaSolucao" name="primeiraListaSolucao" style="width: 85%;">
													</select>
												</div>
												<div class='row-fluid span3'>
												<div class='span10'>
													<div class="separator top"></div>
													<span class="btn btn-block btn-primary" onclick="inserirNaLista('primeiraListaSolucao','idSolucoes',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionar" />
													</span>
													<span class="btn btn-block btn-primary" onclick="RetirarDaLista('primeiraListaSolucao','idSolucoes',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
													</span>
													<span class="btn btn-block btn-primary" onclick="inserirTodosDaLista('primeiraListaSolucao','idSolucoes',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.adcionarTodos" />
													</span>
													<span class="btn btn-block btn-primary" onclick="retirarTodosDaLista('primeiraListaSolucao','idSolucoes',false);">
														<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
													</span>
												</div>
												</div>
												<div class='span3'>
													<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.solucao" /></label>
													<select size="8" id="idSolucoes" name="idSolucoes" style="width: 85%;" multiple="multiple"></select>
												</div>
										</div>
									</div>
									<div class='row-fluid'>
										<div class='span3'>
											<input type="checkbox" name="exibeSemSolucao" id="exibeSemSolucao" value="s"/>&nbsp;<fmt:message key="relatorioCausaSolucao.exibeSemSolucao" />
										</div>
									</div>

									<div class="separator top"></div>

									<div class='row-fluid' >
										<div class='span12'>
											<div class='row-fluid'>
												<button type='button' name='btnRelatorio' class="btn btn-default btn-primary" onclick='gerarCausaSolucao("grafico", "pdf");' style="margin: 20px !important;">
													<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png" style="padding-left: 8px;">
													<span><fmt:message key="relatorioCausaSolucao.gerarGrafico" /></span>
												</button>
												<button type='button' name='btnRelatorio' class="btn btn-default btn-primary" onclick='gerarCausaSolucao("grafico", "xls");' style="margin: 20px !important;">
													<img src="${ctx}/template_new/images/icons/small/util/excel.png" style="padding-left: 8px;">
													<span><fmt:message key="relatorioCausaSolucao.gerarGrafico" /></span>
												</button>
												<button type='button' name='btnRelatorio' class="btn btn-default btn-primary" onclick='gerarCausaSolucao("analitico", "pdf");' style="margin: 20px !important;">
													<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png" style="padding-left: 8px;">
													<span><fmt:message key="relatorioCausaSolucao.gerarAnalitico" /></span>
												</button>
												<button type='button' name='btnRelatorioXls' class="btn btn-default btn-primary" onclick='gerarCausaSolucao("analitico", "xls")' style="margin: 20px !important;">
													<img src="${ctx}/template_new/images/icons/small/util/excel.png">
													<span><fmt:message key="relatorioCausaSolucao.gerarAnalitico" /></span>
												</button>
												<button type='button' name='btnLimpar' class="btn btn-default btn-primary" onclick='limpar()' style="margin: 20px !important;">
													<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
													<span><fmt:message key="citcorpore.comum.limpar" /></span>
													</button>
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
	<script type="text/javascript" src="./js/relatorioCausaSolucao.js"></script>
</body>
</html>

