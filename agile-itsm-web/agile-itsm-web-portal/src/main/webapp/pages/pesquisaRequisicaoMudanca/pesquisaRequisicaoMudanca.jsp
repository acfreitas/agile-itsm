<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>

<!doctype html public "">
<html>
<head>
<%
    String iframe = "";
    iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

<link rel="stylesheet" type="text/css" href="./css/pesquisaRequisicaoMudanca.css" />
<%if (iframe != null) {%>
	<link rel="stylesheet" type="text/css" href="./css/pesquisaRequisicaoMudancaIFrame.css">
<%}%>

<script type="text/javascript" src="./js/pesquisaRequisicaoMudanca.js"></script>

</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
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


		<%-- <%@include file="/include/menu_vertical.jsp"%> --%>
		<!-- Conteudo -->
		<!-- <div id="main_container" class="main_container container_16 clearfix"> -->
		<%-- 	<%@include file="/include/menu_horizontal.jsp"%> --%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="pesquisaRequisicaoMudanca.pesquisaRequisicaoMudanca"/></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div  class="toggle_container">
					<div  class="block">
						<div id="parametros">
							<form name='form' action='${ctx}/pages/pesquisaRequisicaoMudanca/pesquisaRequisicaoMudanca'>
								<input type="hidden" id='idSolicitante' name='idSolicitante'>
								<input type="hidden" id='idProprietario' name='idProprietario'>
								<input type="hidden" id='idItemConfiguracao' name='idItemConfiguracao'>
								<input type="hidden" id='idUnidade' name='idUnidade'>
								<input type="hidden" id='formatoArquivoRelatorio' name='formatoArquivoRelatorio'>

								<div class="columns clearfix">
									<div class="col_25">
										<fieldset style="height: 71px">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tr>
														<td>
															<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
														<td>
															<fmt:message key="citcorpore.comum.a" />
														</td>
														<td>
															<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 71px">
											<label><fmt:message key="citcorpore.comum.numero" /></label>
											<div>
												<input type="text" id="idRequisicaoMudanca" name="idRequisicaoMudanca" size="9" maxlength="9" class='Format[Numero]'/>
											</div>
										</fieldset>
									</div>
									<%-- <div class="col_25">
										<fieldset>
											<label><fmt:message key="contrato.contrato" /></label>
											<div>
												<select name='idContrato'>
												</select>
											</div>
										</fieldset>
									</div>	 --%>
									<div class="col_25">
										<fieldset style="height: 71px">
											<label><fmt:message key="citcorpore.comum.ordenacao" /></label>
											<div>
												<div>
												<select name='ordenacao' id="ordenacao">
													<option selected="selected" value='requisicaomudanca.idrequisicaomudanca'><fmt:message key="citcorpore.comum.numero" /></option>
													<option value="requisicaomudanca.datahorainicio"><fmt:message key="citcorpore.comum.data" /></option>
										            <OPTION value='ltrim(solicitante.nome)'><fmt:message key="solicitacaoServico.solicitante" /></OPTION>
										             <OPTION value='ltrim(proprietario.nome)'><fmt:message key="requisicaoMudanca.proprietario" /></OPTION>
										            <OPTION value='requisicaomudanca.status'><fmt:message key="requisicaMudanca.status" /></OPTION>
										            <OPTION value='requisicaomudanca.idgrupoatual'><fmt:message key="pesquisasolicitacao.gruposolucionador" /></OPTION>
												</select>
											</div>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="columns clearfix">
									<div class="col_25">
										<fieldset >
											<label><fmt:message key="solicitacaoServico.itemConfiguracao" /></label>
											<div>
												<input type="text" onfocus='abrePopupIC();' id="nomeItemConfiguracao" name="nomeItemConfiguracao" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset >
											<label><fmt:message key="solicitacaoServico.solicitante" /></label>
											<div>
												<input type="text" onclick="selecionarSolicitante();" id="nomeSolicitante" name="nomeSolicitante" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset >
											<label><fmt:message key="requisicaoMudanca.proprietario" /></label>
											<div>
											<input class="Valid[Required] Description[requisicaoMudanca.proprietario]" id="nomeProprietario" name="nomeProprietario" type="text" readonly="readonly" onclick="selecionarProprietario()"/>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 55px" >
											<label><fmt:message key="requisicaMudanca.status" /></label>
											<div>
												<select name='status'>
													<option value=''>--<fmt:message key="citcorpore.comum.todos" /> --</option>
													<OPTION value='Registrada'><fmt:message key="citcorpore.comum.registrada" /></OPTION>
													<OPTION value='Proposta'><fmt:message key="citcorpore.comum.proposta" /></OPTION>
										            <OPTION value='Aprovada'><fmt:message key="citcorpore.comum.aprovada" /></OPTION>
										            <OPTION value='Planejada'><fmt:message key="citcorpore.comum.planejada" /></OPTION>
										            <OPTION value='EmExecucao'><fmt:message key="citcorpore.comum.emExecucao" /></OPTION>
										            <OPTION value="Concluida" ><fmt:message key="citcorpore.comum.concluida" /></OPTION>
										            <OPTION value="Rejeitada" ><fmt:message key="citcorpore.comum.rejeitada" /></OPTION>
										            <OPTION value="Cancelada" ><fmt:message key="citcorpore.comum.cancelada" /></OPTION>
												</select>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="pesquisasolicitacao.gruposolucionador" /></label>
											<div>
												<select name='idGrupoAtual'>
												</select>
											</div>
										</fieldset>
									</div>
								<%-- 	<div class="col_25">
										<fieldset>
											<label><fmt:message key="pesquisasolicitacao.fase" /> </label>
											<div>
												<select name='idFaseAtual'>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.origem" /></label>
											<div>
												<select name='idOrigem'>
												</select>
											</div>
										</fieldset>
									</div>		 --%>

									<div class="col_25">
										<div>
											<fieldset>
												<label><fmt:message key="citcorpore.comum.tipo" /></label>
												<div>
													<select id="comboTipo" name='idTipoMudanca' onchange="mostrarCategoria();">
													</select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_25">
										<div id="div_categoria" style="display:none">
											<fieldset>
												<label><fmt:message key="pesquisaRequisicaoMudanca.categoriaMudanca" /></label>
												<div>
													<select  name='nomeCategoriaMudanca'>
													<option value=''>--<fmt:message key="citcorpore.comum.todos" /> --</option>
													<option value="Importante"><fmt:message key="pesquisaRequisicaoMudanca.categoriaMudancaImportante" /></option>
													<option value="Significativa"><fmt:message key="pesquisaRequisicaoMudanca.categoriaMudancaSignificativa" /></option>
													<option value="Pequena"><fmt:message key="pesquisaRequisicaoMudanca.categoriaMudancaPequena" /></option>
													</select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_25">
										<fieldset style="height: 52px">
											<label style="display: block; float: left;padding-top: 15px;"><input  type="checkbox" name="exibirCampoDescricao" id="exibirCampoDescricao" value="S" /><fmt:message key="pesquisasolicitacao.exibirCampoDescricaoRelatorios"/> </label>
										</fieldset>
									</div>
								</div>
								<%-- <div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.unidade" /></label>
											<div>
												<input type="text"  id="addUnidade" name="nomeUnidade" />
											</div>
										</fieldset>
									</div>
								</div> --%>

								<div class="col_100">
									<fieldset>
										<button type='button' name='btnPesquisar' class="light"  onclick='pesquisaRequisicaoMudanca();' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><fmt:message key="citcorpore.comum.pesquisar" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"  onclick='limpar()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar" /></span>
										</button>
									 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioRequisicaoMudanca()' style="margin: 20px !important;">
										<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
										<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
										 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioRequisicaoMudancaXls()' style="margin: 20px !important;">
										<img src="${ctx}/template_new/images/icons/small/util/excel.png">
										<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
									</fieldset>
								</div>

							</form>
						</div>


						<div class="col_100"  align="center" id="divTblRequisicaoMudanca" style='display: block; border:0px solid gray'>

						<table id='tblRequisicaoMudanca' class="table" cellpadding="0" cellspacing="0" width='100%'>
							<tr>
								<th width="10%" ><fmt:message key="requisicaoMudanca.NumeroMudanca" /></th>
								<th width="10%" ><fmt:message key="citcorpore.comum.tipo" /></th>
								<th width="10%" ><fmt:message key="requisicaMudanca.status" /></th>
								<th width="10%" ><fmt:message key="requisicaMudanca.titulo" /></th>
								<th width="10%" ><fmt:message key="citcorpore.comum.motivo" /></th>
								<th width="10%" ><fmt:message key="citcorpore.comum.descricao" /></th>
								<th width="10%"><fmt:message key="requisicaoMudanca.proprietario" /></th>
								<th width="10%" ><fmt:message key="solicitacaoServico.solicitante" /></th>
								<th width="10%" ><fmt:message key="citcorpore.comum.categoria" /></th>
								<th width="10%" ><fmt:message key="citcorpore.comum.dataHoraInicio" /></th>
								<th width="10%" ><fmt:message key="grupo.grupo" /></th>

							</tr>
						</table>

						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>

		<%
		    if (iframe == null) {
		%>
		<%@include file="/include/footer.jsp"%>
		<%} %>

</body>
	<div class="POPUP_barraFerramentasMudancas" id="POPUP_menuAnexos" style='display:none'>
		<form name="formUpload" method="post" enctype="multipart/form-data">
			<cit:uploadControlList style="height:100px;width:50%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/uploadList/uploadList.load" disabled="false"/>
		</form>
	</div>

	<div id="POPUP_UNIDADE" title="<fmt:message key="citcorpore.comum.unidade" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaUnidade' style="width: 540px">
						<cit:findField formName='formPesquisaUnidade'
							lockupName='LOOKUP_UNIDADE' id='LOOKUP_UNIDADE_SOLICITACAO' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

	<div id="POPUP_EMPREGADO" title="<fmt:message key="citcorpore.comum.pesquisa" />" >
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaColaborador' style="width: 540px">
							<cit:findField formName='formPesquisaColaborador'
								lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div id="POPUP_ITEMCONFIG" title="<fmt:message key="citcorpore.comum.identificacao" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisa' style="width: 540px">
						<cit:findField formName='formPesquisa'
 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_OCORRENCIAS" title="<fmt:message key="citcorpore.comum.ocorrenciasolicitacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formOcorrencias' method="post" action='${ctx}/pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao'>
							<input type='hidden' name='idSolicitacaoOcorrencia' />
							<div id='divRelacaoOcorrencias' style='width: 100%; height: 100%;'>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
</html>

