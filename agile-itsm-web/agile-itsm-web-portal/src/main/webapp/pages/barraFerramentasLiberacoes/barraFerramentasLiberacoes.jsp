<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.citframework.util.UtilI18N"%>

<%
	String id = request.getParameter("id");
%>

<!doctype html public "">
<html>
<head>
	<%@include file="/include/header.jsp"%>

	<title><fmt:message key="citcorpore.comum.title" /></title>

	<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

	<link rel="stylesheet" type="text/css" href="${ctx}/pages/barraFerramentasMudancas/css/barraFerramentasMudancas.css">
</head>
<body>
	<input type="hidden" id="idRequisicaoLiberacao" />
	<div align="center" id="barraFerramentasLiberacoes">
		<ul class="menu_horizontal">
			<li style="cursor: pointer;" class="li_menu tooltip_bottom" id="btAnexos" title='<fmt:message key="citcorpore.comum.anexardocumentoimagens" />'>
				<img src="${ctx}/template_new/images/icons/small/grey/paperclip.png" />
				<div class="name"><fmt:message key="citcorpore.comum.anexos" /></div>
			</li>
			<li style="cursor: pointer;" class="li_menu tooltip_bottom" id="btOcorrencias" title='<fmt:message key="gerenciaservico.visualizarregistarocorrencia" />'>
				<img src="${ctx}/template_new/images/icons/small/grey/note_book.png" >
				<div class="name"><fmt:message key="citcorpore.comum.ocorrencia" /></div>
			</li>
		<% if (id == null ) { %>
			<li style="cursor: pointer;" class="li_menu tooltip_bottom" id="btBaseConhecimentoView" title='<fmt:message key="gerenciaservico.acessarbaseconhecimentos" />'>
				<img  src="${ctx}/template_new/images/icons/small/grey/user_comment.png" >
				<div class="name"><fmt:message key="baseConhecimento.baseConhecimento" /></div>
			</li>
		<% } %>
		</ul>
	</div>

	<div class="POPUP_barraFerramentasLiberacoes" id="POPUP_menuAnexos" style="display:none">
		<form name="formUpload" method="post" enctype="multipart/form-data" id='formularioDeAnexos'>
			<div  id="msgGravarDadosFalha" style="display:none">
				<h2><font color="red"><fmt:message key="baseconhecimento.pastaNaoCriada" /></font><br /></h2>
			</div>
			<cit:uploadControl id="uploadAnexos" title="Anexos"	style="height: 300px; width: 100%; border: 1px solid black;" form="document.formUpload" action="/pages/upload/upload.load" disabled="false" />
			<font id="msgGravarDados" style="display:none" color="red"><fmt:message key="barraferramenta.validacao.solicitacao" /></font><br />
			<button id="btnGravarTelaAnexos" name="btnGravarTelaAnexos" onclick="gravarAnexoMudanca();" type="button" style="display:none">
				<fmt:message key="citcorpore.comum.gravar" />
			</button>
			<button id="btnFecharTelaAnexos" name="btnFecharTelaAnexos" type="button">
				<fmt:message key="citcorpore.comum.fechar" />
			</button>
		</form>
	</div>
	<div class="POPUP_barraFerramentasLiberacoes" id="POPUP_menuOcorrencias" title="<fmt:message key='citcorpore.comum.ocorrencia' />">
		<!-- Conteudo -->
		<!-- Não usar este CSS possuido: main_container -->
		<div id="main_container" class="container_16 clearfix">
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1"><fmt:message key="gerenciaservico.relacaoocorrenciaregistradas" /></a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top" onclick="alert(document.getElementById('pesqLockupLOOKUP_OCORRENCIA_MUDANCA_idrequisicaomudanca').value);">
							<fmt:message key="gerenciaservico.cadastroocorrencia" />
						</a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<form name="formOcorrenciaLiberacao" method="post" action="${ctx}/pages/ocorrenciaLiberacao/ocorrenciaLiberacao">
						<input type="hidden" id="idOcorrencia" name="idOcorrencia" />
						<input type="hidden" id="descricao" name="descricao" />
						<input type="hidden" id="idCategoriaOcorrencia" name="idCategoriaOcorrencia" />
						<input type="hidden" id="idOrigemOcorrencia" name="idOrigemOcorrencia" />
						<input type="hidden" id="idRequisicaoLiberacao" name="idRequisicaoLiberacao" />

						<!-- Primeira aba -->
						<div id="tabs-1" class="block">
							<div id="divRelacaoOcorrencias" style="width: 100%; height: 100%; overflow: auto"></div>
						</div>
						<!-- Segunda aba -->
						<div id="tabs-2" class="block">
							<div class="section">
								<div class="columns clearfix">
									<input type="hidden" name="idOcorrencia" />
									<div class="col_50">
										<fieldset style="height: 70px;">
											<div>
												<label class="campoObrigatorio" style="cursor: pointer; float: left; width: 100px">
													<fmt:message key="citcorpore.comum.categoria" />
													<img id="btnCategoriaOcorrencia" src="${ctx}/imagens/add.png" onclick="abrirPopupCadastroCategoriaOcorrencia();" />
												</label>
												<div>
													<input id="nomeCategoriaOcorrencia" name="nomeCategoriaOcorrencia" type="text" readonly="readonly" maxlength="80" class="Valid[Required] Description['citcorpore.comum.categoria']" />
												</div>
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio" style="cursor: pointer; float: left; width: 100px">
												<fmt:message key="citcorpore.comum.origem" />
												<img id="btnOrigemOcorrencia" src="${ctx}/imagens/add.png" onclick="abrirPopupCadastroOrigemOcorrencia();" />
											</label>
											<div>
												<input id="nomeOrigemOcorrencia" name="nomeOrigemOcorrencia" type="text" readonly="readonly" maxlength="80" class="Valid[Required] Description['citcorpore.comum.origem']" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="ocorrenciaMudanca.tempoGasto" /></label>
											<div>
												<input type="text" name="tempoGasto" maxlength="4" size="4" class="Format[Numero] Valid[Required] Description[Tempo Gasto]" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="ocorrenciaMudanca.registradopor" /></label>
											<div>
												<input type="text" name="registradopor" id="registradopor" maxlength="30" size="30" readonly="readonly"
													class="Valid[Required] Description[Registrado por]" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio">
												<fmt:message key="citcorpore.comum.descricao" />
											</label>
											<div>
												<textarea cols="70" rows="5" name="descricao1" class="Valid[Required] Description[Descrição]" maxlength="200"></textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.ocorrencia" /></label>
											<div>
												<textarea cols="70" rows="5" name="ocorrencia" class="Valid[Required] Description[Ocorrência]" maxlength="5000"></textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.informacaoContato" /></label>
											<div>
												<textarea cols="70" rows="2" name="informacoesContato" class="Valid[Required] Description[Informações de Contato]" maxlength="5000"></textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<br /><br />
								<button type="button" name="btnGravar" class="light" onclick="salvar();">
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type="button" name="btnLimpar" class="light" onclick="document.formOcorrenciaLiberacao.clear();">
									<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="citcorpore.comum.limpar" /></span>
								</button>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="POPUP_barraFerramentasLiberacoes" id="POPUP_menuIncidentesRelacionados">
		<div id="tabelaIncidentesRelacionados"></div>
		<div id="menuIncidentesRelacionados" class="col_50">
			<button type="button" name="btRelacionarSolicitacao" id="btRelacionarSolicitacao" class="light" onclick="">
				<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
				<span>
					<fmt:message key="barraferramenta.relacionaroutroincidente" />
				</span>
			</button>
		</div>
		<div id="menuIncidentesRelacionados" class="col_50">
			<button type="button" name="btRelacionarSolicitacaoFechar" id="btRelacionarSolicitacaoFechar" class="light" onclick="">
				<span>
					<fmt:message key="citcorpore.comum.fechar" />
				</span>
			</button>
		</div>
		<div class="POPUP_barraFerramentasLiberacoes" id="divSolicitacoesFilhas">
			<form name="formIncidentesRelacionados" method="post"
				action="${ctx}/pages/incidentesRelacionados/incidentesRelacionados">
				<input type="hidden" name="idSolicitacaoIncRel" value="" />
				<div id="divConteudoIncRel"></div>
			</form>
		</div>
	</div>
	<div class="POPUP_barraFerramentasLiberacoes" id="POPUP_menuHistorico">
		<div id="divResultHistorico"></div>
	</div>
	<div id="popupCadastroRapido">
		<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
	</div>
	<div id="POPUP_PESQUISA_CATEGORIA_OCORRENCIA" class="POPUP_PESQUISA_CATEGORIA_OCORRENCIA" title="<fmt:message key="citcorpore.comum.pesquisaCategoriaOcorrencia" />" >
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<div align="left">
						<label style="cursor: pointer;">
							<fmt:message key="citcorpore.comum.pesquisa" />
						</label>
						<form id="formPesquisaCategoriaOcorrencia" name="formPesquisaCategoriaOcorrencia" method="post" action="${ctx}/pages/categoriaOcorrencia/categoriaOcorrencia">
							<cit:findField id="LOOKUP_CATEGORIA_OCORRENCIA" formName="formPesquisaCategoriaOcorrencia" lockupName="LOOKUP_CATEGORIA_OCORRENCIA"
								top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_PESQUISA_ORIGEM_OCORRENCIA" class="POPUP_PESQUISA_ORIGEM_OCORRENCIA" title="<fmt:message key="citcorpore.comum.pesquisaOrigemOcorrencia" />" >
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<div align="right">
						<label style="cursor: pointer;">
							<fmt:message key="citcorpore.comum.pesquisa" />
						</label>
						<form id="formPesquisaOrigemOcorrencia" name="formPesquisaOrigemOcorrencia" method="post" action="${ctx}/pages/origemOcorrencia/origemOcorrencia">
							<cit:findField id="LOOKUP_ORIGEM_OCORRENCIA" formName="formPesquisaOrigemOcorrencia" lockupName="LOOKUP_ORIGEM_OCORRENCIA"
								top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="${ctx}/pages/barraFerramentasLiberacoes/js/barraFerramentasLiberacoes.js"></script>
</body>
</html>
