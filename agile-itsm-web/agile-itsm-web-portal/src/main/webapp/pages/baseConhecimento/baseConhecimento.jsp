<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ImportanciaConhecimentoUsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.BaseConhecimentoRelacionadoDTO"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
	String idProblema = request.getParameter("idProblema");

	if(idProblema == null){
		idProblema = "";
	}

	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/include/header.jsp"%>

	<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>

	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<link href='${ctx}/template_new/js/star-rating/jquery.rating.css' type="text/css" rel="stylesheet"/>

	<link rel="stylesheet" type="text/css" href="./css/baseConhecimento.css" />

	<script>
		var PAGE_CADADTRO_SOLICITACAOSERVICO = "${PAGE_CADADTRO_SOLICITACAOSERVICO}";
		var iframe = "${iframe}";
	</script>

	<script src='${ctx}/template_new/js/star-rating/jquery.MetaData.js' type="text/javascript" language="javascript"></script>
	<script src='${ctx}/template_new/js/star-rating/jquery.rating.js' type="text/javascript" language="javascript"></script>
	<script type="text/javascript" src="./js/baseConhecimento.js"></script>

	<script type="text/javascript" src="../../cit/objects/ComentariosDTO.js"></script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />

<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
		<%
			if (iframe == null) {
		%>
		<%@include file="/include/menu_horizontal.jsp"%>
		<% }%>

			<div class="flat_area grid_16">
				<h2><fmt:message key="baseConhecimento.baseConhecimento"/></h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1"><fmt:message key="baseConhecimento.cadastro"/></a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top" onclick="exibirAbaPesquisa();"><fmt:message key="baseConhecimento.pesquisa"/></a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/baseConhecimento/baseConhecimento' enctype="multipart/form-data">
								<div class="columns clearfix">
									<input type='hidden' id='versao' name='versao'/>
									<input type='hidden' name='idBaseConhecimentoPai'/>
									<input type='hidden' name='comentariosSerializados'/>
									<input type='hidden' name='comentariosDeserializados'/>
									<input type='hidden' id='comentarioSerializado' name='comentarioDeserializado'/>
									<input type='hidden' id='idListUsuariosGrauImportanciaSerializado' name='listImportanciaConhecimentoUsuarioSerializado'/>
									<input type='hidden' id='idListGrupoosGrauImportanciaSerializado' name='listImportanciaConhecimentoGrupoSerializado'/>
									<input type='hidden' id='idListConhecimentoRelacionadoSerializado' name='listConhecimentoRelacionadoSerializado'/>
									<input type='hidden' name='idUsuarioAutor'/>
									<input type='hidden' id='idConhecimentoRelacionado' name="idConhecimentoRelacionado"/>
									<input type='hidden' id="tituloNotificacao" name="tituloNotificacao"/>
									<input type='hidden' id='tipoNotificacao' name="tipoNotificacao"/>
									<input type="hidden" id="listUsuariosNotificacaoSerializados" name="listUsuariosNotificacaoSerializados"/>
									<input type="hidden" id="listGruposNotificacaoSerializados" name="listGruposNotificacaoSerializados" />
									<input type="hidden" id="idNotificacao" name="idNotificacao" />
									<input type="hidden" id="idHistoricoBaseConhecimento" name="idHistoricoBaseConhecimento" />
									<input type="hidden" id="arquivado" name="arquivado" />
									<input type="hidden" id="idListEventoMonitoramento" name="listEventoMonitoramentoSerializado" />
									<input type="hidden" id="idProblema" name="idProblema" />
									<input type='hidden' id='ocultaJanelaAguarde' name='ocultaJanelaAguarde'/>

									<span id="idArquivado"><label style="font-weight: bold; color: red; padding-left: 20px; padding-right: 20px; border: 1px solid red;"><fmt:message key="baseconhecimento.arquivado"/></label></span>
									<!-- Começo da div -->
									<div class="columns clearfix">
										<div class="col_20">
											<fieldset id="idFaq">
												<label><fmt:message key="baseconhecimento.tipoconhecimento"/></label>
												<div>
													<span class="publicabase">
													<label style="display: block">
															<input type="radio" id="documento" name="documento" value="S" onclick="validaDocumento()" /><fmt:message key="baseconhecimento.documento"/>
														</label>
														<label style="display: block">
															<input type="radio" id="faq" name="faq" value="S" onclick="habilitarPergunta();validaErroConhecido()"/><fmt:message key="baseconhecimento.faq"/>
														</label>
														<label style="display: block">
															<input type="radio" id="erroConhecido" name="erroConhecido" value="S" onclick="validaFaq()"/><fmt:message key="baseConhecimento.erroConhecido"/>
														</label>
													</span>
												</div>
											</fieldset>
										</div>
										<div class="col_10">
											<fieldset id="idIdentificadorBase" style="height: 80px;">
												<label><fmt:message key="citSmart.comum.identificador"/></label>
												<div>
													<input id="identificadorBase" type='text' class="outputext" name="idBaseConhecimento" readonly="readonly"/>
												</div>
											</fieldset>
										</div>

										<div class="col_10">
											<fieldset style="height: 80px; text-align: center;">
												<label><fmt:message key="baseconhecimento.notificacoes"/></label>
												<div>
													<input type="image" class='' style="cursor: pointer;" title="<fmt:message key='pasta.notificacoes'/>" onclick="abrirPopupNotificacao();return false;" src="${ctx}/template_new/images/icons/large/grey/mail_2.png"/>
												</div>
											</fieldset>
										</div>

										<div class="col_10">
											<fieldset style="height: 80px; text-align: center;">
												<label><fmt:message key="baseconhecimento.importancia"/></label>
												<div>
													<input type="image" class='' style="cursor: pointer;" title="<fmt:message key='baseConhecimento.definirImportancia'/>" onclick="abrirPopupImportanciaConhecimento();return false;" src="${ctx}/imagens/graudeimportancia.png" />
												</div>
											</fieldset>
										</div>

										<div class="col_10">
											<fieldset style="height: 80px; text-align: center;	">
												<label><fmt:message key="baseconhecimento.conhecimentorelacionado"/></label>
												<div>
													<input type="image" class='' style="cursor: pointer;" title="<fmt:message key='baseConhecimento.relacionarConhecimento'/>" onclick="abrirPopupConhecimentoRelacionado();return false;" src="${ctx}/imagens/conhecimentorelacionado.png"/>
												</div>
											</fieldset>
										</div>

										<div id="divSolicitacaoServico" class="col_10">
											<fieldset style="height: 80px; text-align: center;	">
												<label><fmt:message key="solicitacaoServico.solicitacao"/></label>
												<div>
													<input type="image" class='' style="cursor: pointer;" title="<fmt:message key='rh.solicitacaoServico'/>" onclick="gravarIncidentesRequisicao(document.form.idBaseConhecimento.value);return false;" src="${ctx}/template_new/images/icons/large/grey/cog.png"/>
												</div>
											</fieldset>
										</div>

										<div id="divMudanca" class="col_10">
											<fieldset style="height: 80px; text-align: center;	">
												<label><fmt:message key="requisicaMudanca.mudanca"/></label>
												<div>
													<input type="image" class='' style="cursor: pointer;" title="<fmt:message key='baseConhecimento.requisicaoMudanca'/>" onclick="gravarGestaoMudanca(document.form.idBaseConhecimento.value);return false;" src="${ctx}/template_new/images/icons/large/grey/alert_2.png"/>
												</div>
											</fieldset>
										</div>

										<%
											if(!br.com.citframework.util.Util.isVersionFree(request)){
										%>
										<div id="divEvento" class="col_10">
											<fieldset style="height: 80px; text-align: center;	">
												<label><fmt:message key="eventoMonitoramento.Evento"/></label>
												<div>
													<input type="image" class='' style="cursor: pointer;" title="<fmt:message key='menu.nome.eventoMonitoramento'/>" onclick="abrirPopupEventoMonitConhecimento();return false;" src="${ctx}/template_new/images/icons/large/grey/radio_signal.png"/>
												</div>
											</fieldset>
										</div>
										<%
											}
										%>
									</div>
									<!-- Fim da div -->
									<div class="columns clearfix">
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio" style="margin-top: 5px;" id="idLabelTitulo"><fmt:message key="baseConhecimento.titulo"/></label>
												<label class="campoObrigatorio" style="margin-top: 5px;" id="idLabelTituloFAQ"><fmt:message key="citcorpore.comum.pergunta"/></label>
												<div>
													<input type="text" id="titulo" name="titulo" maxlength="70" size="70" class="Valid[Required] Description[baseConhecimento.titulo]"/>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label style="margin-top: 5px;"><fmt:message key="baseconhecimento.fontereferencia"/></label>
												<div>
													<input type='text' id="fonteReferencia" name="fonteReferencia" maxlength="70" size="70" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="pasta.pasta"/></label>
												<div>
													<select id="comboPasta" name="idPasta" style="margin-bottom: 3px;" onchange='verificarPermissoesDeAcesso();' class="Valid[Required] Description[pasta.pasta]"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset>
												<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="citcorpore.comum.origem"/></label>
												<div>
													<select id="comboOrigem" name="origem" style="margin-bottom: 3px;" class="Valid[Required] Description[citcorpore.comum.origem]"></select>
												</div>
											</fieldset>
										</div>
									</div>

									<div class="columns clearfix">
										<div class="col_33">
											<fieldset style="height: 100px;">
												<label style="margin-top: 5px;"><fmt:message key="baseconhecimento.justificativaobservacao"/></label>
												<div>
													<textarea id="justificativaObservacao" name="justificativaObservacao" rows="1" cols="1" style="display: block;" onKeyDown="tamanhoCampo(this, 500);" onKeyUp="tamanhoCampo(this, 500);"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_15" style="width: 8% !important;">
											<fieldset class="tooltip_bottom " title="<fmt:message key='baseconhecimento.publicarTitulo'/>" id="publicacao" style="height: 100px;">
												<label class="campoObrigatorio" style="margin-top: 5px; padding-left: 12px;"><fmt:message key="baseconhecimento.publicar"/></label>
												<span class="publicabase">
													<label style="cursor: pointer; padding-right: 1px;">
														<input style="margin-right: 5px;" type="radio" id="aprovaBaseConhecimentoTrue" name="status" value="S"/>
														<fmt:message key="citcorpore.comum.sim"/>
													</label>
													<label style="cursor: pointer;">
														<input type="radio" id="aprovaBaseConhecimentoFalse" name="status" value="N" />
														<fmt:message key="citcorpore.comum.nao"/>
													</label>
												</span>
											</fieldset>
										</div>
										<div class="col_15" style="width: 10% !important;">
											<fieldset style="height: 100px;">
												<label style="margin-top: 5px; padding-left: 12px;"><fmt:message key="baseconhecimento.privacidade"/></label>
												<div>
													<select id="comboPrivacidade" id="privacidade" name="privacidade" style="margin-bottom: 3px;"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_15" style="width: 10% !important;">
											<fieldset style="height: 100px;">
												<label style="margin-top: 5px; padding-left: 12px;"><fmt:message key="citcorpore.comum.situacao"/></label>
												<div>
													<select id="comboSituacao" id="situacao" name="situacao" style="margin-bottom: 3px;"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset id="idautor" style="height: 100px;">
												<label style="margin-top: 5px; padding-left: 12px;"><fmt:message key="citcorpore.comum.autor"/></label>
												<input type='text' class="outputext" name="autor" readonly="readonly" style="padding-left: 9px;"/>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset id="idaprovador" style="height: 100px;">
												<label style="margin-top: 5px; padding-left: 12px;"><fmt:message key="citcorpore.comum.aprovador"/></label>
												<input type='text' class="outputext" name="aprovador" readonly="readonly" style="padding-left: 9px;"/>
											</fieldset>
										</div>
									</div>
									<div class="columns clearfix" style="margin-top: -1px;">
										<div class="col_16">
											<fieldset id="idDataInicio" style="height: 71px;">
												<label><fmt:message key="citcorpore.comum.dataCriacao"/></label>
												<input style="margin-top: 5px; padding-left: 15px;" id=dataInicio type='text' class="outputext" name="dataInicio" readonly="readonly" />
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset style="height: 71px;">
												<label class="campoObrigatorio"><fmt:message key="baseConhecimento.dataExpiracao"/></label>
												<input type='text' id="dataExpiracao" name="dataExpiracao" maxlength="10" readonly="readonly" size="8" class="Valid[Required,Data] Description[baseConhecimento.dataExpiracao] Format[Data] datepicker" />
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset id="idDataPublicacao" style="height: 71px;">
												<label><fmt:message key="citcorpore.comum.dataPublicacao"/></label>
												<input id=dataPublicacao type='text' class="outputext" name="dataPublicacao" readonly="readonly" />
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset>
												<div>
												<label style="display: block; padding-top: 15px;"><input type="checkbox" checked="checked" name="gerenciamentoDisponibilidade" id="gerenciamentoDisponibilidade" value="S" /><fmt:message key="baseconhecimento.gerenciamentoDisponibilidade"/></label>
												</div>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset>
												<div>
												<label style="display: block; padding-top: 15px;"><input type="checkbox" checked="checked" name="direitoAutoral" id="direitoAutoral" value="S" /><fmt:message key="baseconhecimento.direitoAutoral"/></label>
												</div>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset>
												<div>
												<label style="display: block; padding-top: 15px;"><input type="checkbox" checked="checked" name="legislacao" id="legislacao" value="S" /><fmt:message key="baseconhecimento.legislacao"/></label>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="columns clearfix">

									</div>
									<div class="columns clearfix">
										<div class="col_50">
											<fieldset>
												<div id="gridCont" class="columns clearfix">
													<label style="font-weight: bold" id="idLabelConteudo"><fmt:message key="baseConhecimento.conteudo"/></label>
													<label style="font-weight: bold" id="idLabelConteudoFAQ"><fmt:message key="citcorpore.comum.resposta"/></label>
												</div>
												<div>
													<textarea id="conteudoBaseConhecimento" name="conteudo" rows="3" cols="80" style="display: block;"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<div id="gridComentario" class="columns clearfix" style="display: none;">
													<label style="font-weight: bold"><fmt:message key="baseConhecimento.comentarios"/></label>
													<table class="table" id="tabelaComentarios" style="width: 95%">
														<tr style="text-align: left;">
															<th></th>
															<th style="width: 15%;"><fmt:message key="citcorpore.comum.nome"/></th>
															<th style="width: 25%;"><fmt:message key="citcorpore.comum.email"/></th>
															<th style="width: 30%;"><fmt:message key="baseConhecimento.comentario"/></th>
															<th style="width: 30%;"><fmt:message key="baseConhecimento.avaliacao"/></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>
									</div>
									<br>
								</div>

								<div id="POPUP_GRAUDEIMPORTANCIA" class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<h2><fmt:message key="citcorpore.comum.usuario" /></h2>
											<label id="addUsuario" style="cursor: pointer;">
												<img title="Adicionar Usuário" src="${ctx}/imagens/adicionarUsuario.png" onclick="adicionarUsuario(false)">
											</label>
											<div id="gridUsuario">
												<table class="table" id="tabelaUsuario"
													style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 50%;"><fmt:message key="citcorpore.comum.usuario"/></th>
														<th style="width: 49%;"><fmt:message key="baseconhecimento.graudeimportancia"/></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<h2><fmt:message key="grupo.grupo"/></h2>
											<label id="addGrupo" style="cursor: pointer;">
												<img title="Adicionar Grupo" src="${ctx}/imagens/add2.png" onclick="adicionarGrupo(false)">
											</label>
											<div id="gridGrupo">
												<table class="table" id="tabelaGrupo" style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 50%;"><fmt:message key="grupo.grupo" /></th>
														<th style="width: 49%;"><fmt:message key="baseconhecimento.graudeimportancia"/></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>

									<div class="col_100">
										<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
											<button id="btnGravarImportancia" type='button' name='btnGravarImportancia' class="light" onclick='gravarImportanciaConhecimento();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravar"/></span>
											</button>

											<button id="btnFechar" type='button' name='btnFechar' class="light" onclick='fecharPopupGrauDeImportancia();'>
												<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
												<span><fmt:message key="citcorpore.comum.fechar"/></span>
											</button>
										</fieldset>
									</div>
								</div>

								<div id="POPUP_CONHECIMENTORELACIONADO" class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<h2><fmt:message key="baseConhecimento.conhecimento"/></h2>
											<label id="addConhecimento" style="cursor: pointer;">
												<img title="Adicionar Conhecimento" src="${ctx}/imagens/adicionarConhecimento.png" onclick="adicionarConhecimentoRelacionado()">
											</label>
											<div id="gridConhecimentoRelacionado">
												<table class="table" id="tabelaConhecimentoRelacionado" style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 50%;"><fmt:message key="baseconhecimento.documento"/></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
											<button id="btnGravarConhecimentoRelacionado" type='button' name='btnGravarConhecimentoRelacionado' class="light" onclick='gravarConhecimentoRelacionado();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravar"/></span>
											</button>

											<button id="btnFechar" type='button' name='btnFechar' class="light" onclick='fecharPopupConhecimentoRelacionado();'>
												<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
												<span><fmt:message key="citcorpore.comum.fechar"/></span>
											</button>
										</fieldset>
									</div>
								</div>

								<div id="POPUP_NOTIFICACAO">
									<div class="columns clearfix">
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="notificacao.titulo" /></label>
												<div>
													<input type='text' id="notificacaoTitulo" name="notificacaoTitulo" maxlength="255" />
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset style="height: 55px">
												<label class="campoObrigatorio"><fmt:message key="notificacao.tipoNotificacao" /></label>
												<div>
													<select id="tipo" name="tipo"></select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label id="addUsuario" style="cursor: pointer;"><fmt:message key="citcorpore.comum.usuario" />
												<img src="${ctx}/imagens/adicionarUsuario.png" onclick="adicionarUsuario(true)"></label>
												<div id="gridUsuarioNotificacao">
													<table class="table" id="tabelaUsuarioNotificacao">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 98%;"><fmt:message key="citcorpore.comum.usuario" /></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label id="addGrupo" style="cursor: pointer;"><fmt:message key="grupo.grupo" />
												<img src="${ctx}/imagens/add2.png" onclick="adicionarGrupo(true)"></label>
												<div id="gridGrupoNotificacao">
													<table class="table" id="tabelaGrupoNotificacao">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 98%;"><fmt:message key="grupo.grupo" /></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
											<button type='button' name='btnGravarNotificacao' class="light" onclick='gravarNotificacao();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravar" /></span>
											</button>
											<button type='button' name='btnGravarFechar' class="light" onclick='fecharNotificacao();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.fechar" /></span>
											</button>
										</fieldset>
									</div>
								</div>

								<div id="POPUP_EVENTOMONITCONHECIMENTO" class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<h2><fmt:message key="eventoMonitoramento.Evento"/></h2>
											<label id="addEvento" style="cursor: pointer;">
												<img title="Adicionar Evento" src="${ctx}/imagens/adicionarEvento.png" onclick="adicionarEvento()">
											</label>
											<div id="gridEvento">
												<table class="table" id="tabelaEvento" style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 50%;"><fmt:message key="citcorpore.comum.nome"/></th>
														<th style="width: 49%;"><fmt:message key="citcorpore.comum.criadopor"/></th>
														<th style="width: 49%;"><fmt:message key="citcorpore.comum.dataCriacao"/></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>

									<div class="col_100">
										<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
											<button id="btnGravarEventoMonitConhecimento" type='button' name='btnGravarEventoMonitConhecimento' class="light" onclick='gravarEventoMonitConhecimento();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravar"/></span>
											</button>

											<button id="btnFechar" type='button' name='btnFechar' class="light" onclick='fecharPopupEventoMonitConhecimento();'>
												<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
												<span><fmt:message key="citcorpore.comum.fechar"/></span>
											</button>
										</fieldset>
									</div>
								</div>

								<button id="btnGravar" type='button' name='btnGravar' class="light" onclick='gravar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar"/></span>
								</button>
								<button id="btnLimpar" type='button' name='btnLimpar' class="light" onclick='limpar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar"/></span>
								</button>
								<button id="btnExcluir" type='button' name='btnUpDate' class="light" onclick='excluir();'>
									<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir"/></span>
								</button>
								<button id="btnArquivar" type='button' name='btnArquivar' class="light" onclick='arquivar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/folder.png">
									<span><fmt:message key="baseConhecimento.arquivar"/></span>
								</button>
								<button id="btnRestaurar" type='button' name='btnRestaurar' class="light" onclick='restaurar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/folder.png">
									<span><fmt:message key="itemConfiguracaoTree.restaurar"/></span>
								</button>
							</form>

							<form name="formUpload" method="post" enctype="multipart/form-data" id="formularioDeAnexos">
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset style="margin-top: 5px;">
											<label style="margin-top: 5px; margin-bottom: 5px;"><fmt:message key="baseConhecimento.anexo"/></label>
											<cit:uploadControl style="height:100px;width:50%;border:1px solid black" title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>
										</fieldset>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_BASECONHECIMENTO' id='LOOKUP_BASECONHECIMENTO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_USUARIO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs" style='width: 560px !important;'>
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<input type="hidden" id="isNotificacao" name="isNotificacao">
							<cit:findField formName='formPesquisaUsuario' lockupName='LOOKUP_USUARIO_CONHECIMENTO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_GRUPO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs" style='width: 560px !important;'>
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaGrupo' style="width: 540px">
						<input type="hidden" id="isNotificacaoGrupo" name="isNotificacaoGrupo">
							<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_CONHECIMENTO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaConhecimento' style="width: 540px">
							<cit:findField formName='formPesquisaConhecimento' lockupName='LOOKUP_BASECONHECIMENTO_PUBLICADOS' id='LOOKUP_CONHECIMENTO_RELACIONADO' top='0' left='0' len='550' heigth='700' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_LOOKUPEVENTOMONITORAMENTO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaEventoMonitoramento' style="width: 540px">
							<cit:findField formName='formPesquisaEventoMonitoramento' lockupName='LOOKUP_EVENTO_MONITORAMENTO' id='LOOKUP_EVENTO_MONITORAMENTO' top='0' left='0' len='550' heigth='700' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_INCIDENTE" title="<fmt:message key='solicitacaoServico.solicitacao' /> ">
		<iframe id="iframeSolicitacaoServico" name="iframeSolicitacaoServico" width="100%" height="100%"> </iframe>
	</div>

	<div id="POPUP_MUDANCA" title="<fmt:message key='requisicaMudanca.mudanca' /> ">
		<iframe id="iframeMudanca" name="iframeMudanca" width="100%" height="100%"> </iframe>
	</div>

	<%@include file="/include/footer.jsp"%>
</body>
</html>
