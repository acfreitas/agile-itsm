<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%
	String exibirBotaoOrdemServico = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.HABILITA_BOTAO_ORDEMSERVICO, "N");
	String exibirPrecoCarrinhoCompra = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.HABILITA_PRECO_CARRINHO_PORTAL, "S");
%>

<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

<!DOCTYPE html>
<compress:html
	enabled="true"
	jsCompressor="closure"
	compressCss="true"
	compressJavaScript="true"
	removeComments="true"
	removeMultiSpaces="true">
<html>
<head>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
	<%@include file="/novoLayout/common/include/titulo.jsp" %>
	<link type="text/css" rel="stylesheet" href="${ctx}/pages/gerenciamentoServicos/css/gerenciamentoServicos.css"></link>
	<link type="text/css" rel="stylesheet" href="css/portal.css"/>
	<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
	<script type="text/javascript" src="../../cit/objects/InfoCatalogoServicoDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/ServicoContratoDTO.js"></script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="${waitingWindowMessage}" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />

<body>
	<div class="container-fluid fixed ">
		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<div class="navbar main hidden-print">
			<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
			<input type="hidden" id="rowIndex" name="rowIndex"/>
		</div>

		<div id="wrapper">
			<div class="separator top"></div>
			<!-- Inicio conteudo -->
			<div id="content">
				<div class="row-fluid">
					<div class="innerLR">
						<div class="widget">
							<!-- inicio das tabs -->
							<!--
								Correção do layout da aba
								@autor flavio.santana
								@since 29/10/2013
							 -->
							<div class="tabsbar tabsbar-2 active-fill">
								<ul>
									<li class="glyphicons no-js show_thumbnails_with_lines active"><a href="#tabgerenc" data-toggle="tab"><i></i><fmt:message key="portal.gerenciamentoServico"/></a></li>
									<li class="glyphicons no-js notes"><a href="#tabbase" data-toggle="tab" onclick="carregaBaseConhecimento();"><i></i><fmt:message key="portal.baseConhecimento"/></a></li>
									<li class="glyphicons no-js circle_question_mark"><a href="#tabfaq" data-toggle="tab" onclick="carregaFaq();"><i></i><fmt:message key="portal.faq"/></a></li>
									<%if (exibirBotaoOrdemServico.equals("S")){ %>
									<li class="glyphicons no-js notes"><a href="${ctx}/pages/informacoesContrato/informacoesContrato.load?portal=true" target="_blank" id="tabOrdensDeServico"><i></i><fmt:message key="portal.ordemServico"/></a></li>
									<%} %>
								</ul>
							</div>
							<!-- fim das tabs -->
							<div class="tab-content">
								<div class="separator top"></div>
								<!-- inicio da tab gerenciamento de serviços -->
								<div class="tab-pane active" style="min-height:650px" id="tabgerenc">
									<div class="innerLR">
										<div class="widget" data-toggle="collapse-widget">
											<div class="widget-head">
												<h4 class="heading"><fmt:message key="portal.carrinho.servico" /></h4>
											</div>
											<div class="widget-body collapse in">
												<div class="row-fluid">
													<div class="span12">
														<div id="titulo">
															<div class="row-fluid inicio">
																<div class="span2">
																</div>
															</div>
														</div>
													</div>
													<!-- Tabs -->
													<div class="tabsbar tabsbar-2 active-fill">
														<ul class="row-fluid row-merge">
															<li class="span3 glyphicons cargo active"><a href="#tab1" data-toggle="tab"><i></i><fmt:message key="portal.carrinho.listagem"/></a></li>
															<li class="span3 glyphicons cart_in"><a href="#tab2" data-toggle="tab"><i></i> <span><fmt:message key="portal.carrinho.nomeAmigavel.servico"/></span></a></li>
														</ul>
													</div>
													<!-- Fim tabs -->
													<div class="tab-content">
														<div class="tab-pane active" id="tab1"> <!-- conteudo da tab Listagem de ServiÃ§os -->
															<div class="span12 filtro">
																<div class="row-fluid">
																	<div class="span6">
																		<div class="input-append">
																			<input class="span11" id="stringDigitada" type="text" placeholder='<fmt:message key="citcorpore.comum.buscar"/>' onkeyup="filtroListaJs(this, 'listaServicos');">
																			<button class="btn btn-default" type="button"><i class="icon-search"></i></button>
																		</div>
																	</div>
																</div>
															</div>

															<form name='formListaServicos' action='${ctx}/pages/portal/portal'>
																<input type="hidden" name="idCatalogoServico" id="idCatalogoServico" />
																<input type="hidden" name="idContratoUsuario" id="idContratoUsuario" />
																<input type='hidden' name='servicosEscolhidos' id='servicosEscolhidos' />
																<input type='hidden' name='filtroCatalogo' id='filtroCatalogo' />
																<input type='hidden' name='valorTotalServico' id='valorTotalServico' />
																<input type='hidden' name='listaServicosLancados' id='listaServicosLancados' />
																<input type='hidden' name='finalizaCompra' id='finalizaCompra'/>
																<input type="hidden" name="idInfoCatalogoServicoPrincipal" id="idInfoCatalogoServicoPrincipal" />
																<input type="hidden" name="anexarArquivos" id="anexarArquivos" />
																<input type='hidden' name='informacoesComplementares_serialize' id='informacoesComplementares_serialize' />
																<input type="hidden" name="idServicoUp" id="idServicoUp" />
																<input type="hidden" name="idQuestionarioQuest" id="idQuestionarioQuest" />
																<input type="hidden" name="idServicoContratoQuest" id="idServicoContratoQuest" />
																<input type="hidden" name="respostaObrigatoria" id="respostaObrigatoria" />
																<input type="hidden" name="paginaAtualBaseConhecimento" id="paginaAtualBaseConhecimento" />
																<input type="hidden" name="paginaAtualFaq" id="paginaAtualFaq" />
																<input type="hidden" name="tituloPesquisaBaseConhecimento" id="tituloPesquisaBaseConhecimento" />
																<input type="hidden" name="tituloPesquisaFaq" id="tituloPesquisaFaq" />


																<!-- Início listagem de itens -->
																<div class="shop-client-products list" id="listaServicos"></div>
																<!-- Fim listagem de itens -->
															</form>
														</div>
														<div class="tab-pane" id="tab2">
															<!-- Shopping cart -->
															<div class="shop-client-products cart">
																<form name='formCarrinho' action='${ctx}/pages/portal/portal'>
																	<input type='hidden' name='servicosLancados' id='servicosLancados'/>
																	<input type='hidden' name='exibirPrecoCarrinhoCompra' id='exibirPrecoCarrinhoCompra' value='<%= exibirPrecoCarrinhoCompra %>' />

																	<!-- Cart table -->
																	<table id="carrinho" class="table table-bordered table-primary table-striped table-vertical-center checkboxs js-table-sortable tabelaFixa">
																		<thead>
																			<tr>
																			<% if (exibirPrecoCarrinhoCompra.equals("S")){ %>
																				<th class="span1"></th>
																				<th class="span2"><fmt:message key="portal.carrinho.nomeTecnico.servico"/></th>
																				<th class="span3"><fmt:message key="portal.carrinho.descricao"/></th>
																				<th class="span3"><fmt:message key="portal.carrinho.observacao"/></th>
																				<th class="span1"><fmt:message key="carrinho.categoria"/></th>
																				<th class="span1"><fmt:message key="portal.carrinho.preco"/></th>
																			<%} else {%>
																				<th class="span1"><fmt:message key="questionario.questionario"/></th>
																				<th class="span2"><fmt:message key="portal.carrinho.nomeAmigavel.servico"/></th>
																				<th class="span3"><fmt:message key="portal.carrinho.descricao"/></th>
																				<th class="span3"><fmt:message key="portal.carrinho.observacao"/></th>
																				<th class="span1"><fmt:message key="citSmart.comum.anexar"/></th>
																			<%} %>
																				<th class="span1"><fmt:message key="carrinho.excluir"/></th>
																			</tr>
																		</thead>
																		<tbody>

																		</tbody>
																	</table>
																	<!-- // Cart table END -->

																	<div class="separator bottom"></div>
																	<!-- Row -->
																	<div class="row-fluid">
																	<!-- Column -->
																		<div class="span5"></div>
																		<!-- Column END -->
																		<!-- Column -->
																		<div class="span4 offset3">
																			<table class="table table-borderless table-condensed cart_total">
																				<tbody>
																					<tr>
																						<td colspan="2">
																							<div class="separator bottom"></div>
																							<span class="label center label-block large <% if (exibirPrecoCarrinhoCompra.equals("N")){ %> hidden <% } %>" id="imprimeTotal"><fmt:message key="carrinho.total"/></span>
																						</td>
																					</tr>
																					<tr>
																						<td colspan="2"><button id="btnfinalizarCarrinho" type="button" onclick="finalizarCarrinho();" class="btn btn-block btn-primary btn-icon glyphicons right_arrow"><i></i><fmt:message key="carrinho.Concluir"/></button></td>
																					</tr>
																				</tbody>
																			</table>
																		</div>
																		<!-- // Column END -->
																	</div>
																	<!-- // Row END -->
																</form>
															</div>	<!-- // Shopping cart END -->
														</div>
													</div>
												</div>
												<div class="separator top"></div>
												<div class="row-fluid">
													<div class="span12">
														<div id="titulo">

														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="widget" data-toggle="collapse-widget">
											<div class="widget-head">
												<h4 class="heading"><fmt:message key="portal.carrinho.solicitacoes"/></h4>
											</div>
											<div class="widget-body collapse in">
												<form id='formGerenciamento' name='formGerenciamento' method='post' action="${ctx}/pages/gerenciamentoServicosPortal/gerenciamentoServicosPortal">
													<cit:gerenciamentoField classeExecutora="br.com.centralit.citcorpore.ajaxForms.GerenciamentoServicosPortal" paginacao="true" tipoLista="1" />
												</form>
											</div>
										</div>
									</div>
								</div> <!-- fim da tab gerenciamento -->
								<!-- início da tab Base de Conhecimento -->
								<div class="tab-pane" style="min-height:650px" id="tabbase">
									<div class="innerLR">
										<div class="widget" data-toggle="collapse-widget">
											<div class="widget-head">
												<h4 class="heading"><fmt:message key="baseConhecimento.baseConhecimento"/></h4>
											</div>
											<div class="widget-body collapse in">
												<div class="span12 filtro">
													<div class="row-fluid">
														<div class="span6">
															<div class="input-append">
																<input class="span12" id="campoPesquisaBaseConhecimento" type="text" placeholder="<fmt:message key="citcorpore.comum.buscar"/>" >
																<button class="btn btn-default" type="button" onclick="pesquisaBaseConhecimento();"><i class="icon-search"></i></button>
															</div>
														</div>
													</div>
												</div>
												<div class="widget-body collapse in">
													<ul id="column4" class="connectedSortable" style="margin:0px"></ul>
												</div>
											</div>
										</div>
									</div>
								</div>
								<!-- fim da tab Base de Conhecimento -->
								<!-- início da tab FAQ -->
								<div class="tab-pane" style="min-height:650px" id="tabfaq">
									<div class="innerLR">
										<div class="widget" data-toggle="">
											<div class="widget-head">
												<h4 class="heading"><fmt:message key="baseconhecimento.faq"/></h4>
											</div>
											<div class="widget-body collapse in">
												<div class="span12 filtro">
													<div class="row-fluid">
														<div class="span6">
															<div class="input-append">
																<input class="span11" id="campoPesquisaFaq" type="text" placeholder="<fmt:message key="citcorpore.comum.buscar"/>" >
																<button class="btn btn-default" type="button" onclick="pesquisaFaq();"><i class="icon-search"></i></button>
															</div>
														</div>
													</div>
												</div>
												<div class="widget-body"><!-- Inicio da div class="widget-body" -->
													<div class="tab-content"><!-- Inicio da div class="tab-content" -->
														<h3><fmt:message key="faq.faq"/></h3>
														<div class="accordion accordion-2" id="tabAccountAccordion">
														</div>
															<div class="accordion accordion-2" id="accordion"><!-- Inicio da div id="accordion" -->
																<div id="faqs" class="accordion-group"><!-- Inicio da div id="grupo" -->
																</div><!-- Fim da div id="grupo" -->
															</div><!-- Fim da div id="accordion" -->
													</div><!-- Fim da div class="tab-content" -->
												</div><!-- Fim da div class="widget-body" -->
											</div>
										</div>
									</div>
								</div>
								<!-- fim da tab faq -->
							</div>
						</div>
					</div>
				</div>
			</div><!-- Fim conteudo-->

			<%@include file="/novoLayout/common/include/rodape.jsp" %>
			<script type="text/javascript" src="js/portal.js"></script>
		</div>
	</div>

	<!-- MODAL NOVA SOLICITACAO -->
	<div class="modal hide fade" id="modal_novaSolicitacao" tabindex="-1" data-backdrop="static" data-keyboard="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3><fmt:message key="citcorpore.comum.solicitacao"/></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div class='slimScrollDiv'>
				<div class='slim-scroll' id='contentFrameNovaSolicitacao'>
					<iframe id='frameNovaSolicitacao'></iframe>
				</div>
			</div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<!-- // Modal footer END -->
	</div>

	<div class="modal hide fade in" id="modal_ocorrencia" data-backdrop="static" aria-hidden="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3>Ocorrências</h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id="conteudoCadastroOcorrenciaSolicitacao"><iframe id="frameCadastroOcorrenciaSolicitacao" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe></div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">Fechar</a>
		</div>
		<!-- // Modal footer END -->
	</div>

	<div class="modal hide fade in" id="modal_pesquisaGeralSolicitacao" aria-hidden="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.solicitacao" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id='contentPesquisaGeralSolicitacao'>
			</div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<div style="margin: 0;" class="form-actions">
				<a href="#" class="btn btn-primary" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
			</div>
			<!-- // Modal footer END -->
		</div>
	</div>

	<!-- MODAL NOVA UPLOAD -->
	<div class="modal hide fade" id="modal_upload_files" tabindex="-1" data-backdrop="static" data-keyboard="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.anexos" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id='contentFrameUploadFiles'>
				<div class="col_100">
					<fieldset>
					<form class="form-horizontal" name="formUpload" method="post" enctype="multipart/form-data">
						<cit:uploadControl id="uploadAnexos" title="Anexos" style="height: 100px; width: 100%; border: 1px solid black;" form="document.formUpload" action="${ctx}/pages/upload/upload.load" disabled="false" />
						<font id="msgGravarDados" style="display:none" color="red"><fmt:message key="barraferramenta.validacao.solicitacao" /></font><br />
					</form>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<a data-handler="2" class="btn btn-default" href="javascript:;" onclick="$('#modal_upload_files').modal('hide');">Fechar</a>
		</div>
	</div>

	<div class="modal hide fade in" id="modal_questionario" data-backdrop="static" aria-hidden="false" style="width: 750px !important;">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3>Solicitação</h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id="conteudoQuestionarioServicoPortal">
			<iframe id="fraInformacoesComplementares" name="fraInformacoesComplementares" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>

			</div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<div style="text-align: left;">
				<a href="#" onclick="gravarQuestionario()" class="btn btn-primary"><fmt:message key="citcorpore.comum.gravar"/></a>
				<a href="#" class="btn" data-dismiss="modal"><fmt:message key="citcorpore.comum.cancelar"/></a>
			</div>
		</div>
		<!-- // Modal footer END -->
	</div>
</body>
</html>
</compress:html>
