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
	<link rel="stylesheet" type="text/css" href="./css/relatorioEficaciaTeste.css" />
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
									<fmt:message key="relatorioEficaciaTeste.titulo" />
								</h4>
							</div>
							<div class="widget-body collapse in">
								<form name="form" id="form"action="${ctx}/pages/relatorioEficaciaTeste/relatorioEficaciaTeste.load">
									<input type="hidden" id='formatoArquivoRelatorio'name='formatoArquivoRelatorio'>
									<input type="hidden" id='listaServicos'name='listaServicos'>
									<input type="hidden" id='listaGrupoUnidade'name=listaGrupoUnidade>
									<input type='hidden' name='idServico' id='idServico' />

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
													<select id="idContrato" name="idContrato" class="span12 Valid[Required] Description[citcorpore.comum.nome]" onchange="mudarTipoDemanda();retirarTodosDaLista('primeiraLista','listaServico',true);">
													</select>
												</div>
											</div>
										</div>
									</div>
									<%-- <!--Tipo Demanda-->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class="span6">
													<label  class="strong  campoObrigatorio"><fmt:message key="solicitacaoServico.tipo" /></label>
													<select  class=" span6" name="idTipoDemandaServico" id="idTipoDemandaServico" required="required"  onchange="montaParametrosAutocompleteServico();limparServico()"></select>
												</div>
											</div>
										</div>
									</div> --%>
									<!--auto complete serviço-->
									<div class="row-fluid">
										<div class="span7">
											<div class="input-append span2">
												<label  class="strong "><fmt:message key="solicitacaoServico.tipo" /></label>
												<select  class="span12" name="idTipoDemandaServico" id="idTipoDemandaServico"  onchange="montaParametrosAutocompleteServico();limparServico()"></select>
											</div>
											<div class="input-append span6" id='divNomeDoServico'>
												<label class="strong">
													<fmt:message key="servico.nome" />
												</label>

												<input class="span12" type="text" name="servicoBusca" id="servicoBusca" required="required" placeholder="Digite o nome do Serviço">
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
												<select size="8" id="listaServico" name="listaServico" style="width: 200%;">
												</select>
											</div>
										</div>
									</div>

									<div class='row-fluid span12'>
										<div class='span2'>
											<span class="btn btn-block btn-primary" onclick="RetirarDaLista('primeiraLista','listaServico',true);">
												<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.remover" />
											</span>
										</div>
										<div class='span2'>
											<span class="btn btn-block btn-primary" onclick="retirarTodosDaLista('primeiraLista','listaServico',true);">
												<fmt:message key="relatorioEficaciaDasNasEstimativasdeServico.removerTodos" />
											</span>
										</div>
									</div>

									<%-- <!-- situacao -->
									<div class='row-fluid'>
										<div class='span6'>
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
									</div> --%>

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
		</div>
	</div>
	<script type="text/javascript" src="./js/relatorioEficaciaTeste.js"></script>
</body>
</html>

