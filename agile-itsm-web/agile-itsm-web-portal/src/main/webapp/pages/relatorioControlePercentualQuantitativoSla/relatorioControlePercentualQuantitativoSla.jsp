<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<link rel="stylesheet" type="text/css" href="./css/relatorioControlePercentualQuantitativoSla.css" />

<script type="text/javascript" src="./js/relatorioControlePercentualQuantitativoSla.js"></script>
</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title=""
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>

	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="relatorioSlaPercentualQuantitativo.titulo" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div class="block" >
						<div id="parametros">
							<form name='form' action='${ctx}/pages/relatorioControlePercentualQuantitativoSla/relatorioControlePercentualQuantitativoSla'>
								<input type="hidden" id='idSolicitante' name='idSolicitante'>
								<input type="hidden" id='idServico' name='idServico'>
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset style="height: 64px">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tr>
														<td><input type='text' name='dataInicio'
															id='dataInicio' size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
														<td>&nbsp;-&nbsp;</td>
														<td><input type='text' name='dataFim' id='dataFim'
															size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 64px">
											<label><fmt:message key="contrato.contrato" /></label>
											<div>
												<select name="idContrato"  onchange=" document.form.fireEvent('carregaUnidade'); limparServico();"></select>
											</div>
										</fieldset>
									</div>

								<div class="col_25">
										<fieldset style="height: 64px">
											<label><fmt:message key="prioridade.prioridade" /></label>
											<div>
												<select name="idPrioridade"></select>
											</div>
										</fieldset>
									</div>



									<div class="col_25">
										<fieldset style="height: 64px">
											<label><fmt:message key="pesquisasolicitacao.gruposolucionador" /></label>
											<div>
												<select name="idGrupoAtual"></select>
											</div>
										</fieldset>
									</div>

									<div class = "col_25">
										<fieldset style="height: 64px">
										<label><fmt:message key="citcorpore.comum.unidade" /></label>

																			<div id="divUnidade">

								</div>
										</fieldset>
									</div>

									<div class = "col_25">
										<fieldset style="height: 64px">
										<label><fmt:message key="servico.servico" /></label>
											<div>
												<input type="text" id="addServicoContrato" name="nomeServico" readonly="readonly" />
											</div>
										</fieldset>
									</div>

									<div class="col_25">
										<fieldset  style="height: 64px !important;">
											<label><fmt:message key="tipoServico.tipoServico" /></label>
											<div>
												<select name="idTipoServico"></select>
											</div>
										</fieldset>
									</div>
								<div class = "col_25">
										<fieldset style="height: 64px">
										<label><fmt:message key="citcorpore.comum.situacao" /></label>
											<div>
												<select name="situacao"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_50">
									 <div class = "col_50">
										<fieldset style="height: 64px">
										<label><fmt:message key="citcorpore.comum.classificacao" /></label>
											<div>
												<select name='idTipoDemandaServico'></select>
											</div>
										</fieldset>
										</div>
										<div class = "col_50">
										<fieldset style="height: 64px">
										<label><fmt:message key="citcorpore.comum.origem" /></label>
											<div>
												<select name='idOrigem' class="Valid[Required] Description[<fmt:message key='origemAtendimento.origem' />]"></select>
											</div>
										</fieldset>
										</div>
									</div>

									</div>

								</div>
								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light"
											onclick='imprimirRelatorioQuantitativo();'
											style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/util/file_pdf.png" style="padding-left: 8px;">
											<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
										<button type='button' name='btnRelatorioXls' class="light"
											onclick="imprimirRelatorioQuantitativoXls()"
											style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/util/excel.png">
											<span><fmt:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"
											onclick='limpar()' style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar" /></span>
										</button>

									</fieldset>
								</div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
		<div id="POPUP_SERVICOCONTRATO" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style='width: 560px !important;height: 560px !important;' >
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section"  >
							<form name='formPesquisaLocalidade' style='width: 530px !important; ' >
								<cit:findField formName='formPesquisaLocalidade' lockupName='LOOKUP_SERVICOCONTRATO' id='LOOKUP_SERVICOCONTRATO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>


</html>

