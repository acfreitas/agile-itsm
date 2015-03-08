<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
<%@page import="br.com.centralit.citcorpore.free.Free" %>
<%@page import="java.util.Collection"%>

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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%
    String iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

<link type="text/css" rel="stylesheet" href="css/pesquisaSolicitacoesServicosPortal.css"/>

<%if (iframe != null) {%>
	<link type="text/css" rel="stylesheet" href="css/pesquisaSolicitacoesServicosPortalIframe.css"/>
<%}%>

</style>

</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="<fmt:message key='citcorpore.comum.aguardeProcessando'/>" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;" />

<body>
	<div id="wrapper">
		<%
			if (iframe == null) {
		%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%
			}
		%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null) {
			%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%
				}
			%>
			<div class="flat_area grid_16">
				<h2><fmt:message key="pesquisasolicitacao.pesquisasolicitacao"/></h2>
			</div>
			<div class="box grid_16 tabs ">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="pesquisasolicitacao.pesquisasolicitacao"/></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div class="block" style="overflow: hidden;">
						<div id="parametros">
							<div class='inner'>
								<form class='labelOverflowTresPontinhos' name='form' action='${ctx}/pages/pesquisaSolicitacoesServicosPortal/pesquisaSolicitacoesServicosPortal'>
									<input type="hidden" id='idSolicitante' name='idSolicitante'>
									<input type="hidden" id='idResponsavel' name='idResponsavel'>
									<input type="hidden" id='idUsuarioResponsavelAtual' name='idUsuarioResponsavelAtual'>
									<input type="hidden" id='idItemConfiguracao' name='idItemConfiguracao'>
									<input type="hidden" id='idSolicitacaoServico' name='idSolicitacaoServico'>
									<input type="hidden" id='idUnidade' name='idUnidade'>
									<input type="hidden" id='idLocalidade' name='idLocalidade'>
									<input type="hidden" id='idServico' name='idServico'>
									<input type="hidden" id='paginacao' name='paginacao'>
									<input type='hidden' id='totalItens' name='totalItens' value =''>
									<input type='hidden' id='totalPagina' name='totalPagina' value =''>
									<input type='hidden' id='pagAtual' name='pagAtual' value =''>
									<input type='hidden' id='pagAtualAux' name='pagAtualAux' value =''>
									<input type='hidden' id='flag' name='flag'>
									<input type='hidden' id='origemSolicitacao' name='origemSolicitacao'>

									<!--
										Motivo: Otimizando o html para o novo padrão de layout
										Autor: flavio.santana
										Data: 30/10/2013 10:50
									 -->
									<div class="row-fluid">
										<div class="span12">
											<div class="row-fluid">
												<div class="span3">
													<label class="campoObrigatorio strong"><fmt:message key="pesquisasolicitacao.periodoAbertura" /></label>
													<div class="row-fluid">
														<div class="span5">
															<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</div>
														<div class="span2"><fmt:message key="citcorpore.comum.a" /></div>
														<div class="span5">
															<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</div>
													</div>
												</div>
												<div class="span3">
													<label><fmt:message key="pesquisasolicitacao.periodoEncerramento"/></label>
													<div class="row-fluid">
														<div class="span5">
															<input type='text' name='dataInicioFechamento' id='dataInicioFechamento' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</div>
														<div class="span2"><fmt:message key="citcorpore.comum.a" /></div>
														<div class="span5">
															<input type='text' name='dataFimFechamento' id='dataFimFechamento' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											<div class="row-fluid">
												<div class="span3">
													<label><fmt:message key="servico.servico" /> </label>
													<input type="text" onfocus='abrePopupServico();' id="servico" name="servico" />
												</div>
												<div class="span3">
													<label><fmt:message key="solicitacaoServico.situacao" /></label>
													<select id="situacao" name='situacao'>
														<option value=''>--<fmt:message key="citcorpore.comum.todos" /> --</option>
														<option value='Cancelada'><fmt:message key="citcorpore.comum.cancelada" /> </option>
														<option value='EmAndamento'><fmt:message key="citcorpore.comum.emandamento" /> </option>
														<option value='Fechada'><fmt:message key="citcorpore.comum.fechada" /> </option>
														<option value='Reaberta'><fmt:message key="citcorpore.comum.reaberta" /> </option>
														<option value='Resolvida'><fmt:message key="citcorpore.comum.resolvida" /></option>
														<option value='Suspensa'><fmt:message key="citcorpore.comum.suspensa" /> </option>
													</select>
												</div>
												<div class="span3">
													<label><fmt:message key="citcorpore.comum.tipo" /></label>
													<select name='idTipoDemandaServico'></select>
												</div>
											</div>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											<label>
												<input type="checkbox" name="exibirCampoDescricao" id="exibirCampoDescricao" value="S" />
												<fmt:message key="pesquisasolicitacao.exibirCampoDescricaoRelatorios"/>
											</label>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											<button type='button' name='btnPesquisar' class="light" onclick='filtrar();' style="margin: 20px !important;">
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
												<span><fmt:message key="citcorpore.comum.pesquisar" /></span>
											</button>
											<button type='button' name='btnLimpar' class="light" onclick='limpar()' style="margin: 20px !important;">
												<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
												<span><fmt:message key="citcorpore.comum.limpar" /></span>
											</button>
											<button type='button' name='btnRelatorio' class="light" onclick='imprimirRelatorio()' style="margin: 20px !important;">
												<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
												<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
											</button>
											<button type='button' name='btnRelatorio' class="light" onclick='imprimirRelatorioXls()' style="margin: 20px !important;">
												<img src="${ctx}/template_new/images/icons/small/util/excel.png">
												<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
											</button>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12">
											<div id="tblResumo" align="center" style='display: block; border:0px solid gray; overflow-x: auto'></div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>

	<% if (iframe == null) { %>
	<%@include file="/include/footer.jsp"%>
	<%} %>

	<script type="text/javascript" src="js/pesquisaSolicitacoesServicosPortal.js"></script>

	<div class="POPUP_barraFerramentasIncidentes" id="POPUP_menuAnexos"
		style='display: none'>
		<form name="formUpload" method="post" enctype="multipart/form-data">
			<cit:uploadControlList
				style="height:100px;width:50%;border:1px solid black" title="Anexos"
				id="uploadAnexos" form="document.formUpload"
				action="/pages/uploadList/uploadList.load" disabled="false" />
		</form>
	</div>

	<div id="POPUP_RESPONSAVEL"
		title="<fmt:message key="citcorpore.comum.pesquisaresponsavel" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaResponsavel' style="width: 540px">
							<cit:findField formName='formPesquisaResponsavel'
								lockupName='LOOKUP_USUARIO_EMPREGADO' id='LOOKUP_RESPONSAVEL'
								top='0' left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_RESPONSAVEL_ATUAL"
		title="<fmt:message key="citcorpore.comum.pesquisaresponsavel" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaResponsavelAtual' style="width: 540px">
							<cit:findField formName='formPesquisaResponsavelAtual'
								lockupName='LOOKUP_RESPONSAVEL_ATUAL'
								id='LOOKUP_RESPONSAVEL_ATUAL' top='0' left='0' len='550'
								heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_UNIDADE"
		title="<fmt:message key="citcorpore.comum.unidade" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUnidade' style="width: 540px">
							<cit:findField formName='formPesquisaUnidade'
								lockupName='LOOKUP_UNIDADE' id='LOOKUP_UNIDADE_SOLICITACAO'
								top='0' left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_LOCALIDADE"
		title="<fmt:message key="itemConfiguracaoTree.localidade" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaLocalidade' style="width: 540px">
							<cit:findField formName='formPesquisaLocalidade'
								lockupName='LOOKUP_LOCALIDADE_PESQUISA'
								id='LOOKUP_LOCALIDADE_SOLICITACAO' top='0' left='0' len='550'
								heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_SOLICITANTE"
		title="<fmt:message key="citcorpore.comum.pesquisacolaborador" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<cit:findField formName='formPesquisaUsuario'
								lockupName='LOOKUP_SOLICITANTE' id='LOOKUP_SOLICITANTE' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_SERVICO" title="<fmt:message key="servico.servico" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formServico' style="width: 540px">
							<cit:findField formName='formServico' lockupName='LOOKUP_SERVICO'
								id='LOOKUP_SERVICO' top='0' left='0' len='550' heigth='400'
								javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_ITEMCONFIG"
		title="<fmt:message key="citcorpore.comum.identificacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisa' style="width: 540px">
							<cit:findField formName='formPesquisa'
								lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO'
								id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0' left='0' len='550'
								heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>

		<div id="POPUP_OCORRENCIAS"
			title="<fmt:message key="citcorpore.comum.ocorrenciasolicitacao" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formOcorrencias' method="post" action='${ctx}/pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao'>
								<input type='hidden' name='idSolicitacaoOcorrencia' id="idSolicitacaoOcorrencia" />
								<div id='divRelacaoOcorrencias' style='width: 100%; height: 100%;'></div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="pag" style="text-align: center; display: block; width: 100%; margin-bottom: 5px;">
		<input id="btfirst" class="ui-widget ui-state-default botao corner-all" type="button"
			onclick="paginacao(0)" value='<fmt:message key="citcorpore.comum.primeiro" />'
			title='<fmt:message key="citcorpore.comum.primeiro" />' style="cursor: pointer" name="btfirst">
		<input id="btprevius"
			class="ui-widget ui-state-default botao corner-all" type="button"
			onclick="paginacao(-1)" value='<fmt:message key="citcorpore.comum.anterior" />'
			title='<fmt:message key="citcorpore.comum.anterior" />' style="cursor: pointer" name="btprevius">
		<label><fmt:message key="citcorpore.comum.mostrandoPagina" /></label>
		<label id='paginaAtual' style='width: 1% !important;'></label>
		<label><fmt:message key="citcorpore.comum.mostrandoPaginaDe" /></label>
		<label id='paginaTotal' style='width: 1% !important;'></label>
		<input id="btnext" class="ui-widget ui-state-default botao corner-all" type="button"
			onclick="paginacao(1)" value='<fmt:message key="citcorpore.comum.proximo" />'
			title='<fmt:message key="citcorpore.comum.proximo" />' style="cursor: pointer" name="btnext">
		<input id="btlast" class="ui-widget ui-state-default botao corner-all" type="button"
			onclick="paginacao(document.form.totalItens.value);" value='<fmt:message key="citcorpore.comum.ultimo" />'
			title='<fmt:message key="citcorpore.comum.ultimo" />' style="cursor: pointer" name="btlast">
	</div>

</body>
</html>
</compress:html>
