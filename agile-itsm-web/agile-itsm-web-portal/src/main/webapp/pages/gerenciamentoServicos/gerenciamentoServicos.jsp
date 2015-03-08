<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA"%>
<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.free.Free"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>

<%
	String id = request.getParameter("id");
	String strRegistrosExecucao = (String) request.getAttribute("strRegistrosExecucao");
	if (strRegistrosExecucao == null) {
		strRegistrosExecucao = "";
	}

	String idSolicitacaoServico = UtilStrings.nullToVazio((String)request.getParameter("idSolicitacaoServico"));
	String nomeTarefa = UtilStrings.nullToVazio((String)request.getAttribute("nomeTarefa"));

	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO,"");

	String tarefaAssociada = (String) request.getAttribute("tarefaAssociada");
	if (tarefaAssociada == null) {
		tarefaAssociada = "N";
	}
	String iframe = "";
	iframe = request.getParameter("iframe");

	String nomeUsuario = "";
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario != null)
		nomeUsuario = usuario.getNomeUsuario();

	pageContext.setAttribute("nomeUsuario", nomeUsuario);

	String URL_SISTEMA = CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+'/';

	boolean isVersionFree = br.com.citframework.util.Util.isVersionFree(request);
	String contextoAplicacao = br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO");
	pageContext.setAttribute("isVersionFree", isVersionFree);
	pageContext.setAttribute("contextoAplicacao", contextoAplicacao);

	SituacaoSLA situacaoSLA_A = SituacaoSLA.A;
	SituacaoSolicitacaoServico situacaoSolicitacaoServico_Suspensa = SituacaoSolicitacaoServico.Suspensa;
	SituacaoSolicitacaoServico situacaoSolicitacaoServico_Cancelada = SituacaoSolicitacaoServico.Cancelada;
	String acaoIniciar = Enumerados.ACAO_INICIAR;
	String acaoExecutar = Enumerados.ACAO_EXECUTAR;
	pageContext.setAttribute("situacaoSLA_A", situacaoSLA_A);
	pageContext.setAttribute("situacaoSolicitacaoServico_Suspensa", situacaoSolicitacaoServico_Suspensa);
	pageContext.setAttribute("situacaoSolicitacaoServico_Cancelada", situacaoSolicitacaoServico_Cancelada);
	pageContext.setAttribute("acaoIniciar", acaoIniciar);
	pageContext.setAttribute("acaoExecutar", acaoExecutar);
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
	<meta name="viewport" content="width=device-width" />
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
	<title><fmt:message key="citcorpore.comum.title" /></title>
	<link type="text/css" rel="stylesheet" href="css/gerenciamentoServicos.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/jqueryautocomplete.css"/>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />
<body>
	<div class="container-fluid fixed ">
		<!-- Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 15:54 - ID Citsmart: 120948 -
		* Motivo/Comentário: Verificação para abrir com iframe -->
		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<% if(iframe == null) { %>
		<div class="navbar main hidden-print">
				<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
				<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
		</div>
		<% } %>
		<div id="wrapper">
			<!-- Inicio conteudo -->
			<div id="content">
				<div class="separator top"></div>
				<div class="row-fluid">
					<div class="innerLR">
						<div class="widget">
							<div class="widget-head">
								<h4 class="heading"><fmt:message key="gerenciaservico.solicitacoes"/></h4>
							</div>
							<div class="widget-body collapse in">
								<div class="tabsbar">
									<ul>
										<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="gerenciamentoservico.gerenciarSolicitacoes"/></a></li>
										<li class="" onclick="renderizarGraficos()"><a href="#tab2-3" data-toggle="tab"><fmt:message key="citcorpore.comum.graficos"/></a></li>
										<li class="" onclick="renderizarResumoSolicitacoes()"><a href="#tab3-3" data-toggle="tab"><span><fmt:message key="gerenciamentoservico.resumoSolicitacoes"/></span></a></li>
									</ul>
								</div>
								<div class="tab-content">
									<div class="tab-pane active" id="tab1-3">
										<form id='formGerenciamento' name='formGerenciamento' method='post' action="${ctx}/pages/gerenciamentoServicosImpl/gerenciamentoServicosImpl">
											<cit:gerenciamentoField classeExecutora="br.com.centralit.citcorpore.ajaxForms.GerenciamentoServicosImpl" paginacao="true" tipoLista="1"></cit:gerenciamentoField>
										</form>
										<form id='form' name='form' method='post' action="${ctx}/pages/gerenciamentoServicos/gerenciamentoServicos">
											<input type="hidden" id='idTarefa' name='idTarefa'>
											<input type="hidden" id='idSolicitacaoSel' name='idSolicitacaoSel'>
											<input type="hidden" id='acaoFluxo' name='acaoFluxo'>
										</form>
									</div>
									<div class="tab-pane" id="tab2-3">
										<span class='btn btn-primary btn-icon glyphicons refresh' id='btnAtualizarGraficos'><i></i><fmt:message key="citcorpore.comum.atualizarGraficos"/></span>

										<!-- Widget -->
										<div class="widget">

											<!-- Widget heading -->
											<div class="widget-head">
												<h4 class="heading"><fmt:message key="citcorpore.comum.solicitacaoSituacao"/></h4>
											</div>
											<!-- // Widget heading END -->

											<div class="widget-body">

												<!-- Simple Chart -->
												<div style="width: 100%!important;" class='row-fluid'>
													<div id="tdAvisosSol" style="height: 350px; border: 1px solid #D8D8D8" class="span6"></div>
													<div id="divGrafico" style="height: 350px; border: 1px solid #D8D8D8" class="span6"></div>
												</div>
												<!-- <div id="divGrafico" style="height: 350px; width: 100%!important;"></div> -->
											</div>
										</div>
										<!-- // Widget END -->

										<!-- Widget -->
										<div class="widget" style="float:left; width:49%">

											<!-- Widget heading -->
											<div class="widget-head">
												<h4 class="heading"><fmt:message key="citcorpore.comum.solicitacaoPrioridade"/></h4>
											</div>
											<!-- // Widget heading END -->

											<div class="widget-body">

												<!-- Chart with lines and fill with no points -->
												<div class='row-fluid'>
													<div id="divGrafico2" style="height: 350px; border: 1px solid #D8D8D8" class="span12"></div>
												</div>
											</div>
										</div>
										<!-- // Widget END -->

										<!-- Widget -->
										<div class="widget" style="float:right; width:49%;">

											<!-- Widget heading -->
											<div class="widget-head">
												<h4 class="heading"><fmt:message key="citcorpore.comum.solicitacaoGrupo"/></h4>
											</div>
											<!-- // Widget heading END -->

											<div class="widget-body">
												<div class='row-fluid'>
													<div id="divGrafico3" style="height: 350px; border: 1px solid #D8D8D8" class="span12"></div>
												</div>
											</div>
										</div>
										<!-- // Widget END -->
									</div>
									<div class="tab-pane" id="tab3-3">

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- Fim conteudo-->

			<%@include file="/novoLayout/common/include/rodape.jsp" %>
		</div>
	</div>

	<!-- MODAL NOVA SOLICITACAO -->
	<div class="modal hide fade in" id="modal_novaSolicitacao" tabindex="-1" data-backdrop="static" data-keyboard="false">
			<!-- Modal heading -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3><fmt:message key="citcorpore.comum.solicitacao" /></h3>
			</div>
			<!-- // Modal heading END -->
			<!-- Modal body -->
			<div class="modal-body">
				<div id='contentFrameNovaSolicitacao'>
				</div>
			</div>
			<!-- // Modal body END -->
			<!-- Modal footer -->
			<!-- // Modal footer END -->
	</div>

	<!-- MODAL RECLASSIFICAR -->
	<div class="modal hide fade in" id="modal_alterarSLA" aria-hidden="false" data-width="850">
			<!-- Modal heading -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3><fmt:message key="citcorpore.comum.solicitacao" /></h3>
			</div>
			<!-- // Modal heading END -->
			<!-- Modal body -->
			<div class="modal-body">
				<div id='contentFrameAlterarSLA'>
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

	<!-- MODAL RECLASSIFICAR -->
	<div class="modal hide fade in" id="modal_reclassificar2" aria-hidden="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.solicitacao" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id='contentReclassificarSolicitacao2'>
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

	<div class="modal hide fade in" id="modal_agendarAtividade" aria-hidden="false" data-width="850">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.solicitacao" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id='contentFrameAgendarAtividade'>
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

	<div class="modal hide fade in" id="modal_agenda" aria-hidden="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.agenta" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id='contentFrameagendaAtvPeriodicas'>
			</div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<div style="margin: 0;" class="form-actions">
				<a href="#" class="btn btn-primary" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
			</div>
		</div>
		<!-- // Modal footer END -->
	</div>

	<div class="modal hide fade in" id="modal_SuspenderReativarSolicitacao" aria-hidden="false" data-width="700">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="suspensaoReativacaoSolicitacao.tituloPopUp" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->

		<div class="modal-body">
			<div id='contentFrameSuspenderReativarSolicitacao'>
			</div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<div style="margin: 0;" class="form-actions">
				<a href="#" class="btn btn-primary" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
			</div>
		</div>
		<!-- // Modal footer END -->
	</div>


	<div class="modal hide fade in" id="modal_exibirDelegacaoTarefa" aria-hidden="false" data-width="850">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.solicitacao" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id='contentFrameExibirDelegacaoTarefa'>
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

	<div class="modal hide fade in" id="modal_suspender" aria-hidden="false" data-width="750">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.solicitacao"/></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id='contentFrameSuspender'>
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

	<div class="modal hide fade in" id="modal_reclassificarSolicitacao" aria-hidden="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.reclassificaosolicitacao"/></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div id='contentframeReclassificarSolicitacao'>
			</div>
		</div>
	</div>

	<!-- MODAL ITENS DE CONFIGURAÇÃO ... -->
	<div class="modal hide fade in" id="modal_itemConfiguracao" aria-hidden="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="solicitacaoServico.itemConfiguracao" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
			<div class="modal-body">
				<div id="divMudancaSolicitacao" style="display: block">
					<div class='widget'>
						<div class='widget-head'><h4 class='heading'><fmt:message key="solicitacaoServico.itemConfiguracaoAdcionado" /></h4></div>
							<div class='widget-body'>
								<div id='MudancaSolicitacao'>
								</div>
							</div>
					</div>
				</div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
		</div>
		<!-- // Modal footer END -->
	</div>

	<div class="modal hide fade in" id="modal_informacaoItemConfiguracao" aria-hidden="false">
		<!-- Modal heading -->
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h3><fmt:message key="solicitacaoServico.itemConfiguracao" /></h3>
			</div>
			<!-- // Modal heading END -->
			<!-- Modal body -->
			<div class="modal-body">
				<div id="conteudoiframeInformacaoItemConfiguracao">

				</div>
			</div>
			<!-- // Modal body END -->
			<!-- Modal footer -->
			<div class="modal-footer">
				<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
			</div>
			<!-- // Modal footer END -->
	</div>

	<!-- MODAL SOLICITACAOFILHAS ... -->
	<div class="modal hide fade in" id="modal_solicitacaofilha" aria-hidden="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="solicitacaoservico.solicitacaofilha" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
			<div class="modal-body">
				<div id="divSolicitacaoRelacionada" style="display: block">
					<div class='widget'>
						<div class='widget-head'><h4 class='heading'><fmt:message key="solicitacaoServico.solicitacao" /></h4></div>
							<div class='widget-body'>
								<div id='solicitacaoRelacionada'>
								</div>
							</div>
					</div>
				</div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
		</div>
		<!-- // Modal footer END -->
	</div>

	<div class="modal hide fade in" id="modal_criarSubSolicitacao" aria-hidden="false" data-width="700">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="gerenciaservico.duplicarSolicitacao"/></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<form name="formInformacoesContato" id="formInformacoesContato" action='${ctx}/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos'>
				<input type='hidden' name='idSolicitante' id='idSolicitante' />
				<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
				<input type='hidden' name='idContrato' id='idContrato' />
				<input type="hidden" name="idTipoDemandaServico" id="idTipoDemandaServico" />
				<input type='hidden' name='situacaoFiltroSolicitante' id='situacaoFiltroSolicitante' />
				<input type='hidden' name='buscaFiltroSolicitante' id='buscaFiltroSolicitante' />
				<input type='hidden' name='nomecontato' id='nomecontato' />

				<input type='hidden' name='situacao' id='situacao' />
				<input type='hidden' name='idGrupoAtual' id='idGrupoAtual' />

				<div class='row-fluid'>
					<div class="input-append span12">
						<label class="strong campoObrigatorio"><fmt:message key="origemAtendimento.origem" /></label>
						<select name='idOrigem' id='idOrigem'></select>
						<button type="button" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" id='btnCadastroOrigemAux' onclick="chamaPopupCadastroOrigemSub()"><i></i><fmt:message key="novaOrigemAtendimento.novaOrigemAtendimento" /></button>
					</div>
				</div>

				<div class='row-fluid'>
					<div class="input-append">
						<label class="strong campoObrigatorio">
							<fmt:message key="solicitacaoServico.solicitante" />
						</label>
						<input class="span12" type="text" name="nomeSolicitante" id="nomeSolicitante" required="required" placeholder="Digite o nome do solicitante">
						<span class="add-on"><i class="icon-search"></i></span>
						<span class="btn btn-mini btn-primary btn-icon glyphicons search modal_lookupSolicitante" href="#modal_lookupSolicitante" data-toggle="modal" data-target="#modal_lookupSolicitante"><i></i><fmt:message key="citcorpore.comum.pesquisaAvancada" /></span>
					</div>
				</div>
				<div class='row-fluid'>
					<div class="span6">
						<label class="strong campoObrigatorio"><fmt:message key="solicitacaoServico.emailContato" /></label>
						<input id="emailcontato" type='text' name="emailcontato" maxlength="120" class="span12" />
					</div>
					<div class="span6">
						<label class="strong campoObrigatorio"><fmt:message key="solicitacaoServico.telefoneDoContato" /></label>
						<input id="telefonecontato" type='text' name="telefonecontato" maxlength="13" />
					</div>
				</div>
				<div class='row-fluid'>
					<div class="span10" id='divUnidade'>
					</div>
				</div>
				<div class='row-fluid'>
					<div class="span6">
						<label class="strong" title="<fmt:message key="colaborador.cadastroUnidade"/>">
							<fmt:message key="solicitacaoServico.localidadeFisica"/>
						</label>
						<select name="idLocalidade" id='idLocalidade'></select>
					</div>
				</div>
				<div class='row-fluid'>
					<div class="controls">
						<label class="strong"><fmt:message key="solicitacaoServico.observacao" /></label>
						<div class="controls">
							<textarea class="span12" rows="5" cols="100" id="observacao" name="observacao"></textarea>
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<div class="form-actions">
				<a href="#" class="btn btn-primary" onclick="duplicarSolicitacao();"><fmt:message key="citcorpore.comum.gravar" /></a>
				<a href="#" class="btn btn-primary" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
			</div>
		</div>
		<!-- // Modal footer END -->
	</div>

	<div class="modal hide fade in" id="modal_exibirSubSolicitaces" aria-hidden="false">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="citcorpore.comum.solicitacaoSubSolicitacao" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<form id="formIncidentesRelacionados" name='formIncidentesRelacionados' method="post" action='${ctx}/pages/incidentesRelacionados/incidentesRelacionados'>
				<input type='hidden' name='idSolicitacaoIncRel' value=''/>
				<div id="tabelaIncidentesRelacionados"></div>
		</form>

		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<div class="form-actions">
				<a href="#" class="btn btn-primary" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
			</div>
		<!-- // Modal footer END -->
		</div>
	</div>

	<div class="modal hide fade in" id="modal_lookupSolicitante" aria-hidden="false" data-width="600">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="colaborador.pesquisacolaborador" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<form name='formPesquisaColaborador' style="width: 540px">
				<cit:findField formName='formPesquisaColaborador'
				lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOLICITANTE'
				top='0' left='0' len='550' heigth='200' javascriptCode='true'
				htmlCode='true' />
			</form>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<div class="form-actions">
				<a href="#" class="btn btn-primary" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
			</div>
		</div>
		<!-- // Modal footer END -->
	</div>

	<div class="modal hide fade in" id="modal_descricaoSolicitacao" aria-hidden="false" data-width="700">
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3><fmt:message key="gerenciaservico.descricaosolicitacao" /></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->

		<div class="modal-body">
			<div id='contentFrameDescricaoSolicitacao'>
			</div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<div style="margin: 0;" class="form-actions">
				<a href="#" class="btn btn-primary" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
			</div>
		</div>
		<!-- // Modal footer END -->
	</div>

	<div class="modal hide fade in" id="modal_origem_sub" aria-hidden="false" data-width="600">
		<!-- Modal heading -->
		<div class="modal-header">
			<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
			<h3></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<div class='slimScrollDiv'>
				<div class='slim-scroll' id='contentFrameOrigem'>
					<div id="conteudoframeExibirOrigemSub">

					</div>
				</div>
			</div>
		</div>
		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<div style="margin: 0;" class="form-actions">
				<a href="#" class="btn " onclick="preencherComboOrigem();" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
			</div>
		<!-- // Modal footer END -->
		</div>
	</div>

	<script type="text/javascript">
		var isVersionFree = "${isVersionFree}";
		var contextoAplicacao = "${contextoAplicacao}";
		var situacaoSLA_A = "${situacaoSLA_A}";
		var situacaoSolicitacaoServico_Suspensa = "${situacaoSolicitacaoServico_Suspensa};"
		var situacaoSolicitacaoServico_Cancelada = "${situacaoSolicitacaoServico_Cancelada};"
		var nomeUsuario = "${nomeUsuario}";
		var c = "${c}";
		var acaoIniciar = "${acaoIniciar}";
		var acaoExecutar = "${acaoExecutar}";
	</script>

	<script src="${ctx}/js/CollectionUtils.js" type="text/javascript"></script>
	<script src="${ctx}/js/ObjectUtils.js" type="text/javascript"></script>
	<script src="${ctx}/js/UploadUtils.js" type="text/javascript"></script>
	<script src="${ctx}/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
	<script src="js/gerenciamentoServicos.js" type="text/javascript"></script>

</body>
</html>
</compress:html>
