<%@page import="br.com.citframework.util.UtilStrings"%>
<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>
<%@ page import="br.com.centralit.citcorpore.negocio.ParametroCorporeService" %>
<%@ page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO" %>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@ page import="br.com.centralit.citcorpore.free.Free"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.TipoOrigemLeituraEmail"%>

<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

<!DOCTYPE html>
<compress:html
	enabled="${param.compress != 'false'}"
	jsCompressor="closure"
	compressCss="true"
	compressJavaScript="true"
	removeComments="true"
	removeMultiSpaces="true">
<html>
<head>
<%
	String id = request.getParameter("id");
	String strRegistrosExecucao = (String) request.getAttribute("strRegistrosExecucao");
	if (strRegistrosExecucao == null) {
		strRegistrosExecucao = "";
	}

	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO,"");

	String qtdeAnexo = UtilStrings.nullToVazio( (String) request.getAttribute("quantidadeAnexo") );

	String tarefaAssociada = (String) request.getAttribute("tarefaAssociada");
	if (tarefaAssociada == null) {
		tarefaAssociada = "N";
	}
	String iframe = "", parametroAdicionalAsterisk = "";
	iframe = request.getParameter("iframe");

	String editar = request.getParameter("editar");

	/**
	* Motivo: Recebendo o parametro modalAsterisk
	* Autor: flavio.santana
	* Data/Hora: 11/12/2013 10:15
	*/
	parametroAdicionalAsterisk = (request.getParameter("modalAsterisk") == null ? "false" : request.getParameter("modalAsterisk"));
	pageContext.setAttribute("parametroAdicionalAsterisk", parametroAdicionalAsterisk);
	String URL_SISTEMA = CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+'/';
	String mostraGravarBaseConhec = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MOSTRAR_GRAVAR_BASE_CONHECIMENTO, "S").trim();
	pageContext.setAttribute("mostraGravarBaseConhec", mostraGravarBaseConhec);

	boolean isVersionFree = br.com.citframework.util.Util.isVersionFree(request);
	pageContext.setAttribute("isVersionFree", isVersionFree);

	String acaoIniciar = Enumerados.ACAO_INICIAR;
	String acaoExecutar = Enumerados.ACAO_EXECUTAR;
	pageContext.setAttribute("acaoIniciar", acaoIniciar);
	pageContext.setAttribute("acaoExecutar", acaoExecutar);
	pageContext.setAttribute("iframe", iframe);
%>

<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>

<link type="text/css" rel="stylesheet" href="css/solicitacaoServicoMultiContratos.min.css"/>
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
<title>CITSMart</title>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body onload="">

	<div class="container-fluid fluid menu-right">
		<form name='form' id='form' action='${ctx}/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos'>
			<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
			<input type='hidden' name='idSolicitante' id='idSolicitante' />
			<input type='hidden' name='messageId' id='messageId' />
			<input type='hidden' name='idItemConfiguracao' id='idItemConfiguracao' />
			<input type='hidden' name='reclassificar' id='reclassificar' />
			<input type='hidden' name='escalar' id='escalar' />
			<input type='hidden' name='alterarSituacao' id='alterarSituacao' />
			<input type='hidden' name='idTarefa' id='idTarefa' />
			<input type='hidden' name='acaoFluxo' id='acaoFluxo' />
			<input type='hidden' name='filtroADPesq' id='filtroADPesq' />
			<input type='hidden' name='colItensProblema_Serialize' id='colItensProblema_Serialize' />
			<input type='hidden' name='colItensMudanca_Serialize' id='colItensMudanca_Serialize' />
			<input type='hidden' name='colItensIC_Serialize' id='colItensIC_Serialize' />
			<input type='hidden' name='colItensBaseConhecimento_Serialize' id='colItensBaseConhecimento_Serialize' />
			<input type='hidden'  name='idSolicitacaoPai' id='idSolicitacaoPai' />
			<input type='hidden' name='idSolicitacaoRelacionada' id='idSolicitacaoRelacionada' />
			<input type='hidden' name='informacoesComplementares_serialize' id='informacoesComplementares_serialize' />
			<input type='hidden' name='sequenciaProblema' id='sequenciaProblema' />
			<input type='hidden' name='idProblema' id='idProblema' />
			<input type='hidden' name='idRequisicaoMudanca' id='idRequisicaoMudanca' />
			<input type='hidden' name='colConhecimentoSolicitacao_Serialize' id='colConhecimentoSolicitacao_Serialize' />
			<input type='hidden' name='situacaoFiltroSolicitante' id='situacaoFiltroSolicitante' />
			<input type='hidden' name='buscaFiltroSolicitante' id='buscaFiltroSolicitante' />
			<input type='hidden' name='validaBaseConhecimento' id='validaBaseConhecimento' />
			<input type='hidden' name='flagGrupo' id='flagGrupo' />
			<input type='hidden' name='idServico' id='idServico' />
			<input type='hidden' name='nomecontato' id='nomecontato'  />
			<input type='hidden' name='reclassicarSolicitacao' id='reclassicarSolicitacao'  />
			<input type='hidden' name='sequenciaBaseConhecimento' id='sequenciaBaseConhecimento' />
			<input type="hidden" name="idItemBaseConhecimento" id="idItemBaseConhecimento">
			<input type="hidden" name="idBaseConhecimento" id="idBaseConhecimento"/>
			<!-- Parâmetro para checar se a tela é editável
				@author thyen.chang
				23/12/14 -->
			<input type="hidden" name="parametroEditar" id="parametroEditar" value=<%=editar %> />

			<div class="wrapper personalizado" >
				<div id="menu" class="hidden-print" >
					<span class='profile inativo' id='tituloSolicitacao'></span>
					<!-- <div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 800px;">
						<div class="slim-scroll" data-scroll-height="800px" style="overflow: hidden; width: auto; height: 800px;"> -->
						<!-- Regular Size Menu -->
						<ul class="menu-0">
							<li class="hasSubmenu glyphicons warning_sign " id='divMenuSolicitacao' >
								<a  href="#modal_listaSolicitacoesMesmoSolicitante" data-toggle="modal" data-target="#modal_listaSolicitacoesMesmoSolicitante" class="collapsed" onclick="carregaSolicitacoesAbertasParaMesmoSolicitante()"><i></i><span><fmt:message key="gerenciaservico.solicitacoes" /></span></a>
								<ul class="collapse" id="menu_components" style="height: 0px;">
								</ul>
								<span class="count" id='countSolicitacoesAbertasSolicitante'>0</span>
							</li>
							<li class="hasSubmenu glyphicons list " id='divMenuScript'>
								<a  href="#modal_script" data-toggle="modal" data-target="#modal_script" class="collapsed" onclick="carregaScript();" ><i></i><span><fmt:message key="solicitacaoServico.script" /></span></a>
								<ul class="collapse" id="menu_components" style="height: 0px;">
								</ul>
								<span class="count" id='countScript'>0</span>
							</li>
							<li class="hasSubmenu">
								<a  class="glyphicons paperclip" href="#modal_anexo" data-toggle="modal" data-target="#modal_anexo" onclick="carregaFlagGerenciamento()"><i></i><span><fmt:message key="citcorpore.comum.anexos" />(s)</span></a>
								<%-- <a  class="glyphicons paperclip" href="#modal_anexo" data-toggle="modal" data-target="#modal_anexo"><i></i><span><fmt:message key="citcorpore.comum.anexos" />(s)</span></a> --%>
								<ul class="collapse" id="menu_examples">
								</ul>
								<span class="count" id='quantidadeAnexos'></span>
							</li>
							<li class="glyphicons calendar"><a href="#modal_agenda" onclick="abrirModalAgenda()"><i></i><span><fmt:message key="citcorpore.comum.agenta" /></span></a>

							<%if (!tarefaAssociada.equalsIgnoreCase("N")) {%>
								<li class="hasSubmenu">
									<%if (editar == null || editar.equalsIgnoreCase("S")){ %> 
										<a  class="glyphicons notes" onclick="abreModalOcorrencia(true)"><i></i><span><fmt:message key="solicitacaoServico.ocorrencia" /></span></a>
									<% } else { %>
										<a  class="glyphicons notes" onclick="abreModalOcorrencia(false)"><i></i><span><fmt:message key="solicitacaoServico.ocorrencia" /></span></a> 
									<% }%>
									<ul class="collapse" id="menu_examples">
									</ul>
									 <span class="count" id="quantidadeOcorrencias">0</span>
								</li>


							<li class="hasSubmenu" id='liIncidentesRelacionados'>
								<a  class="glyphicons bomb" href="#modal_incidentesRelacionados" data-toggle="modal" data-target="#modal_incidentesRelacionados" id='btIncidentesRelacionados' onclick='restaurarIncidentesRelacionados()'><i></i><span><fmt:message key="gerenciaservico.incidentesrelacionados" /></span></a>
								<ul class="collapse" id="menu_examples">
								</ul>
							 <span class="count" id="quantidadeIncidentesRelacionados">0</span>
							</li>


							<li class="hasSubmenu inativo" id='liNovasolicitacao'>
								<a  class="glyphicons circle_plus"   onclick="modalCadastroSolicitacaoServico()"><i></i><span><fmt:message key="gerenciaservico.novasolicitacao" /></span></a>
								<ul class="collapse" id="menu_examples">
								</ul>
								 <span class="count" id="quantidadeNovasSolicitacoes">0</span>
							</li>
						<%}%>
						</ul>
					<div class="clearfix"></div>

					<ul class="menu-1">
						<li class="hasSubmenu active">
							<a class="glyphicons " href="#menu-recent-stats" data-toggle=""><i></i><span><fmt:message key="solicitacaoServico.processos" /></span></a>
						<%-- 	<%if (br.com.citframework.util.Util.isVersionFree(request)) {%> --%>
							<ul class="collapse in" id="menu-recent-stats">
							   <li id="divProblema">
							   		<a class="glyphicons circle_exclamation_mark" href="#modal_problema" data-toggle="modal" data-backdrop="static" data-target="#modal_problema"><i></i>
							   			<span><fmt:message key="itemConfiguracaoTree.problema" /></span>
							   		</a>
							   		<span class="count" id="quantidadeProblema">0</span>
							   	</li>
								<li id="divMudanca">
									<a class="glyphicons  edit" href="#modal_mudanca" data-toggle="modal" data-target="#modal_mudanca" data-backdrop="static"><i></i>
									<span><fmt:message key="requisicaMudanca.mudanca" /></span>
									</a>
									<span class="count" id="quantidadeMudanca">0</span>
								</li>
								<li id="divItemConfiguracao">
									<a class="glyphicons  display" href="#modal_itemConfiguracao" data-toggle="modal" data-backdrop="static" data-target="#modal_itemConfiguracao"><i></i>
									<span><fmt:message key="itemConfiguracaoTree.itensConfiguracao" /></span>
									</a>
									<span class="count" id="quantidadeItemConfiguracao">0</span>
								</li>
								<li>
									<a class="glyphicons  database_lock" onclick="abrirModalBaseConhecimento()"><i></i>
										<span><fmt:message key="baseConhecimento.baseConhecimento" /></span>
									</a>
									<span  class="count" id="quantidadeBaseConhecimento">0</span>
								</li>
								<li>
									<a class="glyphicons e-mail" href="#modal_leituraEmails" data-toggle="modal" data-backdrop="static" data-target="#modal_leituraEmails"><i></i>
									<span><fmt:message key="clienteEmailCentralServico.tituloLeituraEmails" /></span>
									</a>
									<!-- span class="count" id="quantidadeItemConfiguracao">0</span-->
								</li>
							</ul>
						<%-- 	<%} else{%>
								<ul class="collapse in" id="menu-recent-stats">
									<%=Free.getMsgCampoIndisponivel(request)%>
								</ul>
							<% }%> --%>
						</li>
					</ul>
					<!--
				Adicionado menu_2 para adicionar itens da template.
				*
				* @author Pedro Lino
				* @since 07/11/2013 15:00
				*-->
					<ul class="menu-1 inativo" id='menu_2'>
						<li class="hasSubmenu active">
							<a class="glyphicons " href="#menu-recent-stats" data-toggle=""><i></i><span><fmt:message key="construtorconsultas.template" /></span></a>
							<ul class="collapse in" id="menu_itens_template">

							</ul>
						</li>
					</ul>
					<div class="clearfix"></div>
					<div class="separator bottom"></div>

					<!-- </div> -->
					<div class="slimScrollBar ui-draggable" style="background-color: rgb(0, 0, 0); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; z-index: 99; right: 1px; height: 2764px; background-position: initial initial; background-repeat: initial initial;"></div>
					<div class="slimScrollRail" style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; background-color: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div>
				</div>


				<!-- <div id="wrapper"> -->
				<div id="content">

				<div id="rootwizard" class="wizard tabbable tabs-left">
					<ul id="ulWizard" class="nav nav-tabs">
					  	<li><a href="#tab1-4" data-toggle="tab" id='tab1'><fmt:message key="start.instalacao.1passo" /></a></li>
						<li><a href="#tab2-4" data-toggle="tab" id='tab2' onshow="document.form.fireEvent('carregaSegundoPasso');"><fmt:message key="start.instalacao.2passo" /></a></li>
						<li><a href="#tab3-4" data-toggle="tab" id='tab3' onshow="document.form.fireEvent('carregaTerceiroPasso');"><fmt:message key="start.instalacao.3passo" /></a></li>
						<li><a href="#tab4-4" data-toggle="tab" id='tab4' onshow="document.form.fireEvent('carregaQuartoPasso');"><fmt:message key="start.instalacao.4passo" /></a></li>
					</ul>
					<div class="tab-content">

						<!-- Step 1 -->
						<div class="tab-pane active" id="tab1-4">
							<div class='row-fluid'>
								<div class='span12'>
									 <div class="widget dentro">
										<div class="widget-head"><h4 class="heading"><fmt:message key="solicitacaoServico.informacaoContratoNovoLayout" /></h4></div>
										<div class="widget-body" >
											<div class='row-fluid'>
												<label class="strong campoObrigatorio"><fmt:message key="solicitacaoServico.selecioneContrato" /></label>
													<select  class="span12" id="idContrato" name="idContrato" required="required" onchange="chamaFuncoesContrato();montaParametrosAutocompleteServico();"></select>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- // Step 1 END -->

						<!-- Step 2 -->
						<div class="tab-pane" id="tab2-4">
							<div class='row-fluid'>
								<div class='span12'>
									<div class="widget dentro">
										<div class="widget-head"><h4 class="heading"><fmt:message key="solicitacaoServico.informacoesSolicitanteNovoLayout" /></h4></div>
										<div class="widget-body" >
											<label  class="strong"></label>
												<div class="input-append">
													<label  class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.origemNovoLayout" /></label>
													<select  id="idOrigem" required="required" name="idOrigem" ></select>
													<button type="button" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" id='btnCadastroOrigem'  onclick="chamaPopupCadastroOrigem()"><i></i> <fmt:message key="novaOrigemAtendimento.novaOrigemAtendimento" /></button>
												</div>
													<label  class="strong campoObrigatorio"><fmt:message key="solicitacaoServico.nomeDoSolicitante" /></label>
													<div class=" input-append">
													  	 <input type="text" class="span6" name="solicitante" id="solicitante" onfocus="montaParametrosAutocompleteServico();" required="required"  placeholder="" value="">
													  	<span class="add-on"><i class="icon-search"></i></span>
														<button type='button' class="btn btn-mini btn-primary btn-icon glyphicons search"  href="#modal_lookupSolicitante" data-toggle="modal" data-target="#modal_lookupSolicitante" id='btnPesqAvancada'>
															<i></i> <fmt:message key="citcorpore.comum.pesquisaAvancada" />
														</button>
														<button type='button' class="btn btn-mini btn-primary btn-icon glyphicons user_add"  href="#modal_novoUsuario" data-toggle="modal" data-target="#modal_novoUsuario" onclick="abreModalNovoColaborador()">
															<i></i> <fmt:message key="citcorpore.comum.novoUsuario" />
														</button>
														<%
															String mostraBotaoLDAP = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_MOSTRA_BOTAO, "N");
															if (mostraBotaoLDAP.equalsIgnoreCase("S")) {
														%>
														<span class="btn btn-mini btn-primary btn-icon glyphicons group"  data-target="#code-1" onclick="buscarAD();"><i></i> <fmt:message key="solicitacaoServico.sincronizarAD" /></span>
														<%}%>
													</div>
													<div class="row-fluid">
														<div class="span7">
															<label  class="strong  campoObrigatorio"><fmt:message key="citcorpore.comum.email" /></label>
														  	<input placeholder="Digite o email" class="span12" id="emailcontato" required="required" type="text" name="emailcontato">
														</div>
														<div class="span3">
														  	<label  class="strong"><fmt:message key="citcorpore.comum.telefone" /></label>
														  	<input class="span12"  type="text" name="telefonecontato" id="telefonecontato" placeholder="">

														</div>
														<div class="span2">
														  	<label  class="strong"><fmt:message key="citcorpore.comum.ramal" /></label>
														  	<input class="span12"  type="text" name="ramal" id="ramal" maxlength="5" placeholder="" onkeypress="return somenteNumero(event)">
														</div>
													</div>
													<div class="row-fluid">
														<div class="span6" id="divUnidade">
														</div>
														<div class="span6">
															<label  class="strong"><fmt:message key="citcorpore.comum.localidadeFisica" /></label>
															<select  class="span12" name="idLocalidade" id="idLocalidade"></select>
														</div>
													</div>
													<div class="row-fluid">
														<label  class="strong"><fmt:message key="solicitacaoServico.observacao" /></label>
													  	<div class="controls">
															<%=(editar == null || editar.equalsIgnoreCase("S") ? "<textarea class=\"wysihtml5 span12\" rows=\"5\" cols=\"100\" name=\"observacao\" id=\"observacao\"></textarea>" : "<div class=\"span12 faketextarea\" id=\"observacao\" name=\"observacao\"></div>" )%>
															<span id="contador_char"></span>
														</div>
													</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- // Step 2 END -->

						<!-- Step 3 -->
						<div class="tab-pane" id="tab3-4">
							<div class='row-fluid'>
								<div class='span12'>
									<div class="widget dentro">
										<div class="widget-head"><h4 class="heading"><fmt:message key="solicitacaoServico.informacoesSolicitacaoNovoLayout" /></h4><span class=""></span></div>
										<div class="widget-body" >
										<div class="row-fluid">
											<div class="span6">
												<label  class="strong  campoObrigatorio"><fmt:message key="solicitacaoServico.tipo" /></label>
												<select  class=" span6" name="idTipoDemandaServico" id="idTipoDemandaServico" required="required"  onchange="montaParametrosAutocompleteServico();document.form.fireEvent('carregaServicosMulti');limparCampoServiceBusca();stopSLAPrevisto();"></select>
											</div>
											<div class="span6" id="checkUtilizaCategoriaServico" >
												<div class="uniformjs"  id="checkUtilizaCategoriaServico">
													<label class="checkbox" id="checkUtilizaCategoriaServico" >
														<input type="checkbox" name="utilizaCategoriaServico" id="utilizaCategoriaServico" value="S" onclick="carregaCategoriaServico();" style="opacity: 0;" <%=(editar == null || editar.equalsIgnoreCase("S")? "":"disabled=\"true\"")%>>
														<fmt:message key="solicitacaoServico.utilizaCategoriaServico" />
													</label>
												</div>
												<div id="divCategoriaServico">
													<label  class="strong"><fmt:message key="servico.categoria" /></label>
													<select  class=" span6" id="idCategoriaServico" name="idCategoriaServico" onchange="montaParametrosAutocompleteServico();"></select>
												</div>
											</div>
										</div>
											<!-- 	<div class="separator"></div> -->
												<div class="row-fluid">
													<div class="span9">
														<!-- <label class="strong">Nome do Serviço</label> -->
														<!-- <div class="input-append" >
														<input class="span6" id='servicoBusca' name='servicoBusca' type="text" onblur="camposObrigatoriosSolicitacao();" placeholder="Digite o nome do Solicitante">
														<button class="btn btn-default" type="button"><i class="icon-search"></i></button>
														</div> -->
														<div class="input-append" id='divNomeDoServico'>
															<label class="strong campoObrigatorio"><fmt:message key="servico.nome" /></label>
														  	<input class="span6" type="text" name="servicoBusca" id="servicoBusca" required="required" onkeyup="eventoStartLoading(event);" placeholder="Digite o nome do Serviço" >
														  	<span class="add-on"><i class="icon-search"></i></span>
														  	<button type="button" class="btn btn-small btn-primary" onclick='limparServico()' id='btnLimparServico'><fmt:message key="citcorpore.ui.botao.rotulo.Limpar" /></button>&nbsp;
															<button type="button" class="btn btn-small btn-primary" onclick="mostrarComboServico()" data-toggle="modal" id='modals-bootbox-confirm'><fmt:message key="portal.carrinho.listagem" /></button>
														</div>
													</div>
													<div class="rFloat">
														<div id='fieldSla'>
															<label class="strong"><fmt:message key="controle.sla" /></label>
															<div class='input-append'>
																<span class='label large' id="tdResultadoSLAPrevisto"></span>
																<span id="divMini_loading" style="display: none" ><img src='<%=URL_SISTEMA%>novoLayout/common/include/imagens/mini_loading.gif'></span>
															</div>
														</div>
													</div>
													</div>
													<div id='fieldDescricao'>
														<div class="controls">
															<label  class="strong campoObrigatorio"><fmt:message key="solicitacaoServico.descricao" /></label>
															<div class="controls"><!-- Torna a area de descrição do passo 3 capaz de receber um texto pre-formatado pelo Ctrl+V em todos os tipos de navegadores -->
																<%=(editar == null || editar.equalsIgnoreCase("S") ? "<textarea class=\"wysihtml5 span12 Valid[Required] Description[solicitacaoServico.descricao]\" rows=\"5\" cols=\"100\" id=\"descricao\" name=\"descricao\" maxlength=\"65000\"></textarea>" : "<div class=\"span12 faketextarea\" id=\"descricao\" name=\"descricao\"></div>" )%>
															</div>
														</div>
														<div class="row-fluid">
															<div class="span12">
																<button type='button' class="btn btn-mini btn-icon glyphicons search"
																id='btnPesqSolucao' onclick='pesquisarSolucao()'><i></i><fmt:message key="solicitacaoServico.pesquisarSolucao" /></button>
															</div>
														</div>
													</div>
													<div class="row-fluid">
														<div id="divUrgencia" class="span6">
															<label  class="strong campoObrigatorio"><fmt:message key="solicitacaoServico.urgencia" /></label>
																<select  class=" span6" id="urgencia" name="urgencia" required="required" onchange="calcularSLA();"></select>
														</div>
														<div id="divImpacto" class="span6">
															<label  class="strong campoObrigatorio"><fmt:message key="solicitacaoServico.impacto" /></label>
																<select  class=" span6" id="impacto" name="impacto" required="required" onchange="calcularSLA();"></select>
														</div>
													</div>
													<div id="divGrupoAtual">
														<label  class="strong"><fmt:message key="solicitacaoServico.grupo" /></label>
																<select  class=" span6" name="idGrupoAtual" id="idGrupoAtual" onchange="marcarChecksEmail()"></select>
													</div>
													<div id="divNotificacaoEmail">
														<div id="privacy-settings" class="tab-pane active">
															<label  class="strong"><fmt:message key="solicitacaoServico.notificaoemail" /></label>
															<div class="uniformjs">

																<label class="checkbox" id="uniform-undefined1">
																	<input type="checkbox" name="enviaEmailCriacao" id="enviaEmailCriacao" value="S" checked style="opacity: 0;">
																	<fmt:message key="solicitacaoServico.enviaEmailCriacao" />
																</label>
																<label class="checkbox" id="uniform-undefined2">
																	<input type="checkbox" name="enviaEmailFinalizacao" id="enviaEmailFinalizacao"  value="S" checked  style="opacity: 0;">
																	<fmt:message key="solicitacaoServico.enviaEmailFinalizacao" />
																</label>
																<label class="checkbox" id="uniform-undefined3">
																	<input type="checkbox" name="enviaEmailAcoes" id="enviaEmailAcoes" value="S" checked style="opacity: 0;" >
																	<fmt:message key="solicitacaoServico.enviaEmailAcoes" />
																</label>

															</div>
														</div>
													</div>
													<div class="" id='divControleInformacaoComplementar1'>
													</div>
													<div class="" id='divControleInformacaoComplementar2'>
													</div>

													<div class="inativo" id='divInformacoesComplementares'>

													</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- // Step 3 END -->

						<!-- Step 4 -->
						<div class="tab-pane" id="tab4-4">
							<div class='row-fluid' id="col4">
								<div class='span12'>
									<div class="widget dentro">
										<div class="widget-head"><h4 class="heading"><fmt:message key="solicitacaoServico.fechamentoNovoLayout" /></h4><span class=""></span></div>
										<div class="widget-body" >
											<div id="divSituacao">
												<label  class="strong"><fmt:message key="solicitacaoServico.situacao" /></label>
												<div class="tab-pane">
													<div class="uniformjs">
														<label class="radio"><input class="radio" id="radioEmAndamento" type="radio" checked="checked" value="EmAndamento" name="situacao"/> <fmt:message key="solicitacaoServico.situacaoRegistrada" /></label>
														<label class="radio"><input class="radio" id="radioResolvida" type="radio" value="Resolvida" name="situacao"/> <fmt:message key="solicitacaoServico.situacaoResolvida" /></label>
														<label class="radio"><input class="radio" id="radioCancelada" type="radio" value="Cancelada" name="situacao"/> <fmt:message key="solicitacaoServico.situacao.Cancelada" /></label>
													</div>
												</div>
											</div>
											<div id="solucao">
												<div class="row-fluid">
													<div class="span6">
														<label  class="strong"><fmt:message key="solicitacaoServico.causa" /></label>
															<select  class=" span10" id="idCausaIncidente" name="idCausaIncidente"></select>
													</div>
													<div class="span6">
														<label  class="strong"><fmt:message key="solicitacaoServico.categoriaSolucao" /></label>
															<select  class=" span10" id="idCategoriaSolucao" name="idCategoriaSolucao"></select>
													</div>
												</div>
												<div class="controls" >
												<button type='button'class="btn btn-mini  btn-icon glyphicons circle_plus" id='btnAdicionarRegistroExecucao' onclick="adicionarRegistroExecucao()"><i></i> <fmt:message key="solicitacaoServico.addregistroexecucao_mais" /></button>
											<!-- Foi necessário deixar o style na div, pois colocando no css nao renderizou corretamente -->
												<div id='controleRegistroExecucao' style="display: none" >
													<label  class="strong"><fmt:message key="solicitacaoServico.registroExecucao" /></label>
													<div class="controls">
														<textarea  class="wysihtml5 span12" rows="5" name="registroexecucao" id="registroexecucao"></textarea>
													</div>
												</div>
												</div>
												<label  class="strong"><fmt:message key="solicitacaoServico.solucaoTemporaria" /></label>
												<div class="tab-pane">
													<div class="uniformjs">
														<label class="radio"><input type="radio" value="S" name="solucaoTemporaria"/> <fmt:message key="citcorpore.comum.sim" /></label>
														<label class="radio"><input type="radio" checked="checked" value="N" name="solucaoTemporaria"/> <fmt:message key="citcorpore.comum.nao" /></label>
													</div>
												</div>
												<div class="controls">
												<label  class="strong"><fmt:message key="solicitacaoServico.detalhamentocausa" /></label>
													<div class="controls">
														<%=(editar == null || editar.equalsIgnoreCase("S") ? "<textarea class=\"wysihtml5 span12\" rows=\"5\" id=\"detalhamentoCausa\" name=\"detalhamentoCausa\"></textarea>" : "<div class=\"span12 faketextarea\" id=\"detalhamentoCausa\" name=\"detalhamentoCausa\"></div>" )%>
													</div>
												</div>
												<div class="solucaoRespostaBaseConhecimento hide">
													<div id="privacy-settings" class="tab-pane active">
														<div class="uniformjs">
															<label class="checkbox" id="uniform-undefined">
																<input type="checkbox" name="gravaSolucaoRespostaBaseConhecimento" id="gravaSolucaoRespostaBaseConhecimento" onclick="gravarSolucaoRespostaEmBaseConhecimento()"  style="opacity: 0;">
																<fmt:message key='baseConhecimento.GravarSolucaoResposta'/>
															</label>
														</div>
													</div>
													<div class="row-fluid inativo" id="divTituloSolucaoRespostaBaseConhecimento" >
														<div class="span6">
															<label  class="strong campoObrigatorio"><fmt:message key="baseConhecimento.titulo"/></label>
																<input type="text" class="span12" id="tituloBaseConhecimento" name="tituloBaseConhecimento"/>
														</div>
													</div>
												</div>
												<div class="controls">
													<label  class="strong"><fmt:message key="solicitacaoservico.solucaoResposta"/></label>
													<div class="controls">
														<%=(editar == null || editar.equalsIgnoreCase("S") ? "<textarea  class=\"wysihtml5 span12\" rows=\"5\" id=\"resposta\" name=\"resposta\"></textarea>" : "<div class=\"span12 faketextarea\" id=\"resposta\" name=\"resposta\"></div>" )%>
													</div>
												</div>
											</div>

										 	<div class="clearfix"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- // Step 4 END -->

						<%
							if (request.getParameter("visualizarPasso") == null)  {
						%>
						<!-- Wizard pagination controls -->
						<div class="pagination margin-bottom-none pull-right">
							<ul>
								<li class="primary previous"><a href="javascript:;"><fmt:message key="citcorpore.comum.anterior"/></a></li>
								<li class="next primary"><a href="javascript:;"><fmt:message key="citcorpore.comum.proximo"/></a></li>
								<!-- <li class="next finish primary" style="display:none;"><a href="javascript:;">Finish</a></li> -->
							</ul>
						</div>
						<%
							}
						%>

						<div class="clearfix"></div>
						<!-- // Wizard pagination controls END -->

					</div>
					</div>
					<div style="margin: 1;" id="divBotoes" class="navbar navbar-fixed-bottom ">
					<%if (tarefaAssociada.equalsIgnoreCase("N")) {%>
						<%-- <button href="#" class="btn " id='modals-bootbox-confirm' onclick="cancelar()"><fmt:message key="citcorpore.comum.cancelar" /></button>  --%>

						<button type="button"  data-dismiss="modal"  class="btn btn-primary" onclick='desabilitaBotaoGravar(); gravarSemEnter(event); ' id="btnGravar"><fmt:message key="citcorpore.comum.gravar" /></button>
						<button type="button"  class="btn " onclick="cancelar()" data-dismiss="modal"><fmt:message key="citcorpore.comum.cancelar" /></button>
					<%} else {%>

						<button type="button"  id="btnGravar" data-dismiss="modal" class="btn  btn-primary " onclick='desabilitaBotaoGravar(); gravarEContinuar();'><fmt:message key="citcorpore.comum.gravarEContinuar" /></button>
						<button type="button" id="btnGravarEContinuar"  data-dismiss="modal" class="btn  btn-primary " onclick='gravarEFinalizar(); '><fmt:message key="citcorpore.comum.gravarEFinalizar" /></button>
						<button type="button" class="btn " id='modals-bootbox-confirm' onclick="cancelar()"><fmt:message key="citcorpore.comum.cancelar" /></button>
					<%}%>
				</div>
				</div>

			</div>
		</form>
	</div>
							<!--
							===================================
							===================================
							INICIO DA AREA DE JANELAS (MODAL)
							===================================
							===================================
							-->
<!-- MODAL AGENDA ... -->
			<div class="modal hide fade in" id="modal_agenda" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="citcorpore.comum.agenta" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoAgendaAtvPeriodicas">

						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<div class="modal hide fade in" id="modal_novoColaborador" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="menu.nome.colaborador" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoCadastroNovoColaborador">

						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn btn-primary" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<!-- MODAL ANEXO ... -->
			<div class="modal hide fade in" id="modal_anexo" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="fechaModalAnexo();">×</button>
						<h3><fmt:message key="citcorpore.comum.anexos" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form class="form-horizontal" name="formUpload" method="post" enctype="multipart/form-data">
								<cit:uploadControl id="uploadAnexos" title="Anexos" style="height: 100px; width: 100%; border: 1px solid black;" form="document.formUpload" action="/pages/upload/upload.load" disabled="false" />
								<font id="msgGravarDados" style="display:none" color="red"><fmt:message key="barraferramenta.validacao.solicitacao" /></font><br />
						</form>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a><button type="button" href="#" class="btn btn-primary" data-dismiss="modal" onclick="fechaModalAnexo();"><fmt:message key="citcorpore.comum.gravar" /></button></a>
						<a href="#" class="btn " data-dismiss="modal" onclick="fechaModalAnexo();"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<!-- MODAL SCRIPT ... -->
			<div class="modal hide fade in" id="modal_script" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="solicitacaoServico.script" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div class='widget'>
							<div class='widget-head'><h4 class='heading'><fmt:message key="solicitacaoServico.scriptApoio" /></h4></div>
								<div class='widget-body'>
									<div id='divScript' >
										<fmt:message key="citcorpore.comum.selecionescript" />
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

			<!-- MODAL PROBLEMA ... -->
			<div class="modal hide fade in" id="modal_problema" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="problema.problema" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="divProblema" style="display: block" class="col_50">
							<%if (br.com.citframework.util.Util.isVersionFree(request)) {%>
								<div style="width: 90%;">
									<%=Free.getMsgCampoIndisponivel(request)%>
								</div>
							<%} else {%>
							<div class="row-fluid">
								<div class="span10">
									<button class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" id="addProblema" href="#modal_lookupProblema" data-target="#modal_lookupProblema" data-toggle="modal">
										<i></i>
										<fmt:message key="solicitacaoServico.adicionarProblema" />
									</button>

									<button class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" id="addMudanca" onclick="cadastrarProblema()">
										<i></i>
										<fmt:message key="categoriaProblema.cadastro" />
									</button>
								</div>
							</div>
							<div class='widget'>
							<div class='widget-head'><h4 class='heading'><fmt:message key="solicitacaoServico.problemaRelacionado" /></h4></div>
								<div class='widget-body'>
									<div id='divProblemaSolicitacao' >
										<table id='tblProblema' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>

												<!-- <td width="10%"></td> -->
												<td width="60%" ><fmt:message key="requisicaMudanca.titulo" /></td>
												<td width="29%" ><fmt:message key="requisicaMudanca.status" /></td>
												<td style='text-align: center'  width='20px' height="15px"></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<%}%>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

				<!-- MODAL MUDANÇA ... -->
			<div class="modal hide fade in" id="modal_mudanca" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="requisicaMudanca.mudanca" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
					<div id="divMudanca" style="display: block" class="col_50">
							<%if (br.com.citframework.util.Util.isVersionFree(request)) {%>
								<div style="width: 90%;">
									<%=Free.getMsgCampoIndisponivel(request)%>
								</div>
							<%} else {%>
							<div class="row-fluid">
								<div class="span10">
									<button type="button" class="btn btn-mini btn-primary btn-icon glyphicons search"  id="pesquisaMudanca" name="pesquisaMudanca" class='span10'  href="#modal_lookupMudanca" data-toggle="modal" data-target="#modal_lookupMudanca"><i></i> <fmt:message key="gerenciarequisicao.pesquisaMudanca" /></button>
									 <button type="button" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  id="addMudanca" name="addMudanca" class='span10'  onclick="cadastrarMudanca()"><i></i><fmt:message key="citcorpore.comum.cadastrarMudanca"/></button>
								</div>
							</div>
						<div class='widget'>
							<div class='widget-head'><h4 class='heading'><fmt:message key="solicitacaoServico.mudancaRelacionada" /></h4></div>
								<div class='widget-body'>
									<div id='divMudancaSolicitacao' >
										<table id='tblMudanca' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>
												<!-- <td width="10%"></td> -->
												<td width="60%" ><fmt:message key="requisicaMudanca.titulo" /></td>
												<td width="29%" ><fmt:message key="requisicaMudanca.status" /></td>
												<td style='text-align: center'  width='20px' height="15px">&nbsp;</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<%}%>
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
							<div class="row-fluid">
								<div class="span10">
									 <button type="button" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  id="addSolicitacaoFilha" name="addSolicitacaoFilha" class='span10'  onclick="chamaPopupCadastroSolicitacaoServico()"><i></i><fmt:message key="solicitacaoServico.cadastrosolicitacao"/></button>
								</div>
							</div>

						<div class='widget'>
							<div class='widget-head'><h4 class='heading'><fmt:message key="solicitacaoServico.solicitacao" /></h4></div>
								<div class='widget-body'>
									<div id='solicitacaoRelacionada' >
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

			<!-- MODAL LEITURA DE EMAIL ... -->
			<div class="modal hide fade in" id="modal_leituraEmails" aria-hidden="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h3><fmt:message key="clienteEmailCentralServico.tituloLeituraEmails" /></h3>
				</div>
				<!-- // Modal heading END -->

				<!-- Modal body -->
				<div class="modal-body">
					<div id="divLeituraEmails" style="display: block">
						<form id="formEmail" name='formEmail' action='${ctx}/pages/clienteEmailCentralServico/clienteEmailCentralServico'>
							<input type="hidden" id="emailMessageId" name="emailMessageId" />
							<input type="hidden" id="idContrato" name="idContrato" />
							<input type="hidden" id="emailOrigem" name="emailOrigem" value="<%=TipoOrigemLeituraEmail.SOLICITACAO_SERVICO.toString()%>" />
							<div class="row-fluid">
								<div class="span10">
									<button class="btn btn-mini btn-primary btn-icon glyphicons search" id="verificarEmails" name="verificarEmails" onclick="setEmail();return false;">
										<i></i>
										<fmt:message key="citcorpore.comum.verificarEmails" />
									</button>
								</div>
							</div>
							<div id='divEmails' class='widgetEmails' style="display: none;margin-top:5px;"></div>
						</form>
					</div>
				</div>
				<!-- // Modal body END -->

				<!-- Modal footer -->
				<div class="modal-footer">
					<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
				</div>
				<!-- // Modal footer END -->
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
							<div id="divMudanca" style="display: block" class="col_50">
							<%if (br.com.citframework.util.Util.isVersionFree(request)) {%>
								<div style="width: 90%;">
									<%=Free.getMsgCampoIndisponivel(request)%>
								</div>
							<%} else {%>
							<div class="row-fluid">
								<div class="span10">
									<button type="button" class="btn btn-mini btn-primary btn-icon glyphicons search"
									id="addProblema" name="addProblema" class='span10' onclick="abrirModalPesquisaItemConfiguracao()"><i></i> <fmt:message key="solicitacaoServico.pesquisarItemConfiguracao" /></button>
								</div>
							</div>
							<div class='widget'>
							<div class='widget-head'><h4 class='heading'><fmt:message key="solicitacaoServico.itemConfiguracaoAdcionado" /></h4></div>
								<div class='widget-body'>
									<div id='divMudancaSolicitacao' >
										<table id='tblIC' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>
											<td width="20%" class='linhaSubtituloGrid'><fmt:message key="citcorpore.comum.numero" /></td>
											<td width="62%" class='linhaSubtituloGrid'><fmt:message key="citcorpore.comum.identificacao" /></td>
											<td width="10%"><fmt:message key="solicitacaoServico.informacao" /></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<%}%>
						</div>


					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<!-- MODAL BASE DE CONHECIMENTO ... -->
			<div class="modal hide fade in" id="modal_baseConhecimento" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="baseConhecimento.baseConhecimento" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
							<div class="box-generic">

								<!-- Tabs Heading -->
								<div class="tabsbar">
									<ul>
										<li class="active"><a href="#tab11-33" data-toggle="tab"><fmt:message key="baseConhecimento.vincularBaseConhecimento"/></a></li>
										<li class=""><a href="#tab22-33" data-toggle="tab" onclick="abrirModalBaseConhecimento()"><fmt:message key="baseConhecimento.pesquisabase"/> </a></li>
									</ul>
								</div>
								<!-- // Tabs Heading END -->
								<div class="tab-content">
										<div class='tab-pane active' id='tab11-33'>
											<div class="row-fluid">
												<div class="span10">
													<button type="button" class="btn btn-mini btn-primary btn-icon glyphicons search"
													id="addProblema" name="addProblema" class='span10'
													 href="#modal_lookupBaseConhecimento" data-toggle="modal" data-target="#modal_lookupBaseConhecimento"><i></i> <fmt:message key="baseConhecimento.pesquisabase" /></button>
												</div>
											</div>
											<div class='widget'>
												<div class='widget-head'><h4 class='heading'><fmt:message key="baseConhecimento.pesquisabase"/></h4></div>
													<div class='widget-body'>
														<div id='divConhecimentoSolicitacao' >
															<table id='tblBaseConhecimento' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
																<tr>
																	<td width="20%" style='font-size: 14px;' class='linhaSubtituloGrid'><fmt:message key="baseConhecimento.idBaseConhecimento" /></td>
																	<td width="75%" style='font-size: 14px;' class='linhaSubtituloGrid'><fmt:message key="baseConhecimento.titulo" /></td>
																	<td style='text-align: center' style='font-size: 14px;'  class='linhaSubtituloGrid' width='20px' height="15px;">&nbsp;</td>
																</tr>
															</table>
														</div>
													</div>
											</div>
										</div>
										<div class='tab-pane' id='tab22-33'>
											<div id="conteudoframeBaseConhecimento">

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
				<!-- MODAL OCORRENCIA -->
				<div class="modal hide fade in" id="modal_ocorrencia" data-backdrop="static" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="citcorpore.comum.ocorrencia" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoCadastroOcorrenciaSolicitacao"></div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<!-- MODAL LISTA INCIDENTES RELACIONADOS ... -->
			<div class="modal hide fade in" id="modal_incidentesRelacionados" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="gerenciaservico.incidentesrelacionados" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
					<div id='divBtIncidentesRelacionados'>
						<div class="row-fluid innerB">
							<div class="span12">
								<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"
								id="btIncidentesRelacionados"  class='span10'
								 href="#modal_listaRelacionarIncidentes" onclick="listarSolicitacoesServicoEmAndamento()" data-toggle="modal" data-target="#modal_listaRelacionarIncidentes"  ><i></i> <fmt:message key="solicitacaoServico.adicionarIncidenteNaRelacao" /></span>
							</div>
						</div>
					</div>
					<!-- Modal body -->


						<div class='widget'>
							<div class='widget-head'><h4 class='heading'><fmt:message key="solicitacaoServico.listaIncidenteRealcionado" /></h4></div>
								<div class='widget-body'>
									<div id="tabelaIncidentesRelacionados"></div>
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

			<!-- MODAL SELECIONAR INCIDENTES RELACIONADOS ... -->
			<div class="modal hide fade in" id="modal_listaRelacionarIncidentes" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="requisicaoMudanca.relacionarIncidentes" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
					<div>
						<div class="row-fluid">
						</div>
					</div>
					<!-- Modal body -->
						<div class="" id="divSolicitacoesFilhas">
								<form name="formIncidentesRelacionados" method="post" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/incidentesRelacionados/incidentesRelacionados">
									<input type="hidden" name="idSolicitacaoIncRel" value="" />
									<div id="divConteudoIncRel">
										<div class="row-fluid innerTB">
											<div class="span12">
												<fmt:message key="citcorpore.comum.aguardecarregando"/>
											</div>
										</div>
									</div>
								</form>
						</div>

					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn btn-primary" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

				<!-- MODAL SOLICITAÇÕES PARA O SOLICITANTE SELECIONADO ... -->
			<div class="modal hide fade in" id="modal_listaSolicitacoesMesmoSolicitante" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="solicitacaoServico.solicitacaoPorUsuario" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
					<div>
						<div class="row-fluid">
							<div class="span3">
								<label  class="strong"><fmt:message key="solicitacaoServico.situacao" /></label>
								<select name='situacaoTblResumo2' id='situacaoTblResumo2'>
									<option value='EmAndamento'><fmt:message key="citcorpore.comum.emandamento"/></option>
									<option value='Fechada'><fmt:message key="citcorpore.comum.fechada"/></option>
								</select>
							</div>
							<div class="input-prepend input-append">
							  	<label  class="strong"><fmt:message key="citcorpore.comum.busca" /></label>
							  	<input class="span12"  type="text" name="campoBuscaTblResumo2" id="campoBuscaTblResumo2" placeholder="" onkeydown="if ( event.keyCode == 13 ) pesquisaSolicitacoesAbertasParaMesmoSolicitante();">
							  	<span class="add-on"><i class="icon-search"></i></span>
							  	<span class="btn btn-mini btn-primary btn-icon glyphicons search" id='btnPesquisaSolUsuario' onclick="pesquisaSolicitacoesAbertasParaMesmoSolicitante()"><i></i> <fmt:message key="citcorpore.comum.pesquisar"/></span>
							</div>
						</div>
					</div>
					<!-- Modal body -->
						<div class="" id="tblResumo2"></div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<div class="modal hide fade in" id="modal_detalheSolicitacaoServico" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="solicitacaoServico.detalhamento" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
					<div id='detalheSolicitacaoServico'>
					</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<div class="modal hide fade in" id="modal_pesquisaSolucaoBaseConhecimento" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<!-- <h3>Detalhamento da Solicitação</h3> -->
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
						<div id='resultPesquisa'>
					</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

<%-- 			<div class="modal hide fade in" id="modal_novaSolicitacaoFilho" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="gerenciaservico.novasolicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
						<div id="conteudoframeCadastroNovaSolicitacaoFilho">

						</div>
					<div >
					</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div> --%>

						<!-- MODAL ITEM DE CONFIGURACAO ... -->
			<div class="modal hide fade in" id="modal_pesquisaItemConfiguracao" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="solicitacaoServico.pesquisarItemConfiguracao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoPesquisaItemConfiguracao">

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

			<div class="modal hide fade in" id="modal_editarCadastrarProblema" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="problema.problema" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoiframeEditarCadastrarProblema">

						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<div class="modal hide fade in" id="modal_editarCadastrarMudanca" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="itemConfiguracaoTree.mudanca" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoiframeEditarCadastrarMudanca">

						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>


			<div class="modal hide fade in" id="modal_editarCadastrarSolicitacaoFilha" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="solicitacaoServico.solicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoframeCadastroNovaSolicitacaoFilho">

						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<!-- MODAL LOOKUP SOLICITANTE... -->
			<div class="modal hide fade in" id="modal_lookupSolicitante" aria-hidden="false">
				<!-- Modal heading -->
				<div class="modal-header">
					 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<div class='slimScrollDiv'>
						<div class='slim-scroll'>
							<div id="divPesqSolicitante">
							<form name='formPesquisaColaborador' style="width: 1117px">
									<cit:findField formName='formPesquisaColaborador'
										lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOLICITANTE'
										top='0' left='0' len='550' heigth='200' javascriptCode='true'
										htmlCode='true' />
							</form>
							</div>
						</div>
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
					<!-- // Modal footer END -->
			</div>
			<!-- MODAL LOOKUP PROBLEMA... -->
			<div class="modal hide fade in" id="modal_lookupProblema" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaProblema' style="width: 640px">
							<cit:findField formName='formPesquisaProblema'
								lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0'
								left='0' len='550' heigth='200' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<div class="modal hide fade in" id="modal_lookupMudanca" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaMudanca' style="width: 640px">
						<cit:findField formName='formPesquisaMudanca'
							lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0'
							left='0' len='550' heigth='200' javascriptCode='true'
							htmlCode='true' />
					</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<div class="modal hide fade in" id="modal_lookupBaseConhecimento" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaBaseConhecimento' style="width: 640px">
						<cit:findField formName='formPesquisaBaseConhecimento'
							lockupName='LOOKUP_BASECONHECIMENTO_PUBLICADOS' id='LOOKUP_BASECONHECIMENTO_PUBLICADOS' top='0'
							left='0' len='550' heigth='200' javascriptCode='true'
							htmlCode='true' />
					</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
					<!-- // Modal footer END -->
			</div>

			<!--
				Foi adicionado tratamento (data-backdrop="static" data-keyboard="false") no modal  para bloquear click externo.
				*
				* @author maycon.fernandes
				* @since 25/10/2013 16:32
			*-->
			<div class="modal hide fade in" id="mensagem_insercao" aria-hidden="false" data-backdrop="static" data-keyboard="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body" >
						<div id="divInsercao">

						</div>

					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a id="btFecharMensagem" href="#" class="btn " onclick="fecharModalNovaSolicitacao();" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
					</div>
			</div>

			<div class="modal hide fade in" id="modal_origem" aria-hidden="false">
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
								<div id="conteudoframeExibirOrigem">

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

			<div class="modal hide fade in" id="modal_visualizaProblemaBaseConhecimento" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div class='slimScrollDiv'>
							<div class='slim-scroll' id='contentFrameOrigem'>
								<div id="conteudovisualizaProblemaBaseConhecimento">

								</div>
							</div>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<div style="margin: 0;" class="form-actions">

						</div>
					<!-- // Modal footer END -->
				</div>
			</div>

				<div class="modal hide fade in" id="modal_infoServicos" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="portal.carrinho.listagem" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div class='slimScrollDiv'>
							<div class='slim-scroll'>
								<div id="divInfoServicos">
								<div class="filter-bar">
									<div class='row-fluid'>
										<input type='text' class='span12' id='filtroTableServicos' placeholder="<fmt:message key="portal.carrinho.filtroBusca" />" onkeyup="filtroTableJs(this, 'tblListaServicos')">
									</div>
								</div>
									<table id='tblListaServicos' class='dynamicTable table  table-bordered table-condensed dataTable'>
											<tr>
												<th></th>
												<th ><fmt:message key="citcorpore.comum.servico" /></th>
											</tr>
									</table>
								</div>
							</div>
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
			<%@include file="/novoLayout/common/include/libRodape.jsp" %>

			<script type="text/javascript">
				var mostraGravarBaseConhec = "${mostraGravarBaseConhec}";
				var isVersionFree = "${isVersionFree}";
				var tarefaAssociada = "${tarefaAssociada}";
				var acaoIniciar = "${acaoIniciar}";
				var acaoExecutar = "${acaoExecutar}";
				var iframe = "${iframe}";
				var parametroAdicionalAsterisk = "${parametroAdicionalAsterisk}";
			</script>

			<script src="js/form_wizards.js"></script>
			<script src="js/solicitacaoServicoMultiContratos.js"></script>
			<script src="${ctx}/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
			<script type="text/javascript" src='${ctx}/js/UploadUtils.js'></script>
</body>
</html>
</compress:html>
