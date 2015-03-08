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
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css" />
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
	<link rel="stylesheet" type="text/css" href="./css/relatorioQI03QI04ProgramacaoDefensivaEficacia.css" />
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
									<fmt:message key="relatorioQI03QI04ProgramacaoDefensivaEficacia" />
								</h4>
							</div>
							<div class="widget-body collapse in">
								<form name="form" id="form"action="${ctx}/pages/relatorioQI03QI04ProgramacaoDefensivaEficacia/relatorioQI03QI04ProgramacaoDefensivaEficacia.load">
									<input type="hidden" id='formatoArquivoRelatorio'name='formatoArquivoRelatorio'>
									<input type="hidden" id='listaUsuarios'name='listaUsuarios'>
									<input type="hidden" id='listaGrupoUnidade'name=listaGrupoUnidade>
									<input type='hidden' name='idServico' id='idServico' />
									<input type='hidden' name='idCausaIncidente' id='idCausaIncidente' />
									<input type='hidden' name='listaServicosString' id='listaServicosString' />
									<input type='hidden' name='listaCausaString' id='listaCausaString' />


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
													<select id="idContrato" name="idContrato" class="span12 Valid[Required] Description[citcorpore.comum.nome]" onchange="preencherGrupoUnidade(this);preencherComboTipoDemanda();montaParametrosAutocompleteCausa();">
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
													<span class="btn btn-block btn-primary" onclick="retirarDaLista('primeiraListaGrupo','segundaListaGrupo',false);">
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
													<span class="btn btn-block btn-primary" onclick="retirarDaLista(primeiraListaUnidade','segundaListaUnidade',false);">
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
													<span class="btn btn-block btn-primary" onclick="retirarDaLista('primeiraLista','segundaLista',true);">
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
									<div class="row-fluid">
										<div class="span7">
											<div class=" span2">
												<label  class="strong  "><fmt:message key="solicitacaoServico.tipo" /></label>
												<select  class="span12" name="idTipoDemandaServico" id="idTipoDemandaServico"  onchange="montaParametrosAutocompleteServico();limparServico();validacaoTipoDemanda();"></select>
											</div>
											<div class="input-append span6" id='divNomeDoServico'>
												<label  class="strong ">
													<fmt:message key="servico.nome" />
												</label>

												<input class="span12" type="text" name="servicoBusca" id="servicoBusca" disabled="disabled" required="required" placeholder="Digite o nome do Serviço">
												<span class="add-on">
													<i class="icon-search"></i>
												</span>
											</div>
										</div>
									</div>


									<!--Serviço-->
									<div class='row-fluid' style="display: show;" id="servicos">
										<div class='span10'>
											<div class='span3'>
												<label class="strong campoObrigatorio"><fmt:message key="notificacao.servicos" /></label>
												<select size="8" id="idListaServicosString" name="idListaServicosString" style="width: 200%;">
												</select>
											</div>
										</div>
									</div>

									<div class='row-fluid span12'>
										<div class='span2'>
											<span class="btn btn-block btn-primary" onclick="retirarDaListaGeral('idListaServicosString',true);">
												<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
											</span>
										</div>
										<div class='span2'>
											<span class="btn btn-block btn-primary" onclick="retirarTodosDaListaGeral('idListaServicosString',true);">
												<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
											</span>
										</div>
									</div>
									<div class="separator top"></div>
									<div class="separator top"></div>
									<div class="row-fluid">
										<div class="span7">
											<div class="input-append span6" id='divCausa'>
												<label class="strong">
													<fmt:message key="solicitacaoServico.causa" />
												</label>

												<input class="span12" type="text" name="causaBusca" id="causaBusca" required="required" placeholder="Digite o nome da causa">
												<span class="add-on">
													<i class="icon-search"></i>
												</span>
											</div>
										</div>
									</div>



									<!--Serviço-->
									<div class='row-fluid' style="display: show;" >
										<div class='span10'>
											<div class='span3'>

												<select size="8" id="idListaCausaString" name="idListaCausaString" style="width: 200%;">
												</select>
											</div>
										</div>
									</div>
									<div class='row-fluid span12'>
										<div class='span2'>
											<span class="btn btn-block btn-primary" onclick="retirarDaListaGeral('idListaCausaString',true);">
												<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
											</span>
										</div>
										<div class='span2'>
											<span class="btn btn-block btn-primary" onclick="retirarTodosDaListaGeral('idListaCausaString',true);">
												<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
											</span>
										</div>
									</div>


									<div class="separator top"></div>
									<div class="separator top"></div>
									<div class="separator top"></div>
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
			<script type="text/javascript" src="js/relatorioQI03QI04ProgramacaoDefensivaEficacia.js"></script>
		</div>
	</div>

</body>
</html>
