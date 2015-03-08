<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO"%>
<%@page import="br.com.centralit.citcorpore.free.Free" %>

<!doctype html>
<html>
<head>
<%
	String iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctx}/pages/pesquisaRequisicaoLiberacao/css/pesquisaRequisicaoLiberacao.css" />
<%if (iframe != null) {%>
	<link rel="stylesheet" type="text/css" href="${ctx}/pages/pesquisaRequisicaoLiberacao/css/pesquisaRequisicaoLiberacaoIFrame.css" />
<%}%>

<script type="text/javascript" src="${ctx}/pages/pesquisaRequisicaoLiberacao/js/pesquisaRequisicaoLiberacao.js"></script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;" />

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

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="pesquisarequisicao.pesquisarequisicao"/></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div class="block" style="overflow: inherit;">
						<div id="parametros">
							<form name='form' action='${ctx}/pages/pesquisaRequisicaoLiberacao/pesquisaRequisicaoLiberacao'>
								<input type="hidden" id='idSolicitante' name='idSolicitante'>
								<input type="hidden" id='idResponsavel' name='idResponsavel'>
								<input type="hidden" id='idRequisicaoLiberacao' name='idRequisicaoLiberacao'>
								<input type="hidden" id='idUnidade' name='idUnidade'>
								<input type="hidden" id='idContato' name='idContato'>

								<div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="pesquisarequisicao.periodoAbertura" /></label>
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
										<fieldset>
											<label><fmt:message key="pesquisarequisicao.periodoEncerramento"/></label>
											<div>
												<table>
													<tr>
														<td>
															<input type='text' name='dataInicioFechamento' id='dataInicioFechamento' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
														<td>
															<fmt:message key="citcorpore.comum.a" />
														</td>
														<td>
															<input type='text' name='dataFimFechamento' id='dataFimFechamento' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 65px">
											<label><fmt:message key="citcorpore.comum.numero" /></label>
											<div>
												<input type="text" id="idRequisicaoLiberacaoPesquisa" name="idRequisicaoLiberacaoPesquisa" size="9" maxlength="9" class='Format[Numero]'/>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 65px">
											<label><fmt:message key="contrato.contrato" /></label>
											<div>
												<select name='idContrato'>
												</select>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="columns clearfix">
									<div class="col_25">
										<fieldset style="height: 65px">
											<label><fmt:message key="citcorpore.comum.ordenacao" /></label>
											<div>
												<select name='ordenacao' id="ordenacao">
													<option selected="selected" value='l.idliberacao'><fmt:message key="citcorpore.comum.numero" /></option>
													<option value="l.datahoracaptura"><fmt:message key="citcorpore.comum.data" /></option>
													<option value='ltrim(e.nome)'><fmt:message key="solicitacaoServico.solicitante" /></option>
													<option value='l.situacao'><fmt:message key="solicitacaoServico.situacao" /></option>
													<option value='l.prioridade'><fmt:message key="solicitacaoServico.prioridade" /></option>
													<option value='ltrim(g.sigla)'><fmt:message key="pesquisasolicitacao.gruposolucionador" /></option>
												</select>
											</div>
										</fieldset>
									</div>

									<div class="col_25">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.unidade" /></label>
											<div>
												<input type="text" id="addUnidade" name="nomeUnidade" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="solicitacaoServico.solicitante" /></label>
											<div>
												<input type="text" onfocus='abrePopupUsuario();' id="nomeSolicitante" name="nomeSolicitante" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 61px !important;">
											<label><fmt:message key="solicitacaoServico.situacao" /></label>
											<div>
												<select id="situacao" name='situacao'>
													<option value=''>--<fmt:message key="citcorpore.comum.todos" /> --</option>
													<option value='Registrada'><fmt:message key="citcorpore.comum.registrada"/></option>
													<option value='EmExecucao'><fmt:message key="liberacao.emExecucao"/></option>
													<option value='Concluida'><fmt:message key="liberacao.finalizada"/></option>
													<option value='NaoResolvida'><fmt:message key="requisicaoLiberacao.naoResolvida"/></option>
													<option value='Cancelada'><fmt:message key="liberacao.cancelada"/></option>
												</select>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset style="height: 61px !important;">
											<label><fmt:message key="solicitacaoServico.prioridade" /></label>
											<div>
												<select name='idPrioridade'>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><fmt:message key="pesquisasolicitacao.gruposolucionador" /></label>
											<div>
												<select name='idGrupoAtual'>
												</select>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_25">
									<fieldset style="height: 55px">
										<label style="display: block; float: left;padding-top: 15px;"><input type="checkbox" name="exibirCampoDescricao" id="exibirCampoDescricao" /><fmt:message key="pesquisasolicitacao.exibirCampoDescricaoRelatorios"/></label>
									</fieldset>
								</div>

								<div class="col_100">
									<fieldset>
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
									</fieldset>
								</div>
								<div class="col_100" id="tblResumo" align="center" style='display: block; border:0px solid gray'>
							</div>
							</form>
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

	<div class="POPUP_barraFerramentasIncidentes" id="POPUP_menuAnexos" style='display:none'>
		<form name="formUpload" method="post" enctype="multipart/form-data">
			<cit:uploadControlList style="height:100px;width:50%;border:1px solid black" title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/uploadList/uploadList.load" disabled="false"/>
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

	<div id="POPUP_SOLICITANTE" title="<fmt:message key="citcorpore.comum.pesquisacolaborador" />">
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
							<cit:findField formName='formServico'
								lockupName='LOOKUP_SERVICO' id='LOOKUP_SERVICO' top='0'
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
	</div>

	<div id="POPUP_OCORRENCIAS" title="<fmt:message key="citcorpore.comum.ocorrencialiberacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formOcorrencias' method="post" action='${ctx}/pages/ocorrenciaLiberacao/ocorrenciaLiberacao'>
							<input type='hidden' name='idOcorrencia' />
							<div id='divRelacaoOcorrencias' style='width: 100%; height: 100%;'>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_CONTATO" title="<fmt:message key="pesquisarequisicao.contato" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaContato' style="width: 540px">
							<cit:findField formName='formPesquisaContato'
								lockupName='LOOKUP_CONTATOLIBERACAO' id='LOOKUP_CONTATOLIBERACAO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

