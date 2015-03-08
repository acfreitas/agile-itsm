<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@ page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO" %>

<!doctype html public "">
<html>
<head>
	<%@include file="/include/header.jsp"%>

	<%@ include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title" /></title>
	<%@ include file="/include/menu/menuConfig.jsp" %>

	<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<link rel="stylesheet" type="text/css" href="./css/relatorioSolicitacaoPorExecutante.css" />

	<script type="text/javascript" src="./js/relatorioSolicitacaoPorExecutante.js"></script>
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2><fmt:message key="relatorioSolicitacaoPorExecutante.relatorioSolicitacaoPorExecutante"/></h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="relatorioSolicitacaoPorExecutante.relatorioSolicitacaoPorExecutante" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div class="block" >
						<div id="parametros">
							<form name="form" action="${ctx}/pages/relatorioSolicitacaoPorExecutante/relatorioSolicitacaoPorExecutante">
								<input type="hidden" id='formatoArquivoRelatorio' name='formatoArquivoRelatorio'>
								<input type="hidden" id='idResponsavelAtual' name='idResponsavelAtual'>


								<div class="columns clearfix">
									<div class="col_20">
										<fieldset style="height: 65px">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tr>
														<td><input type='text' name='dataInicio'
															id='dataInicio' size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
														<td><fmt:message key="citcorpore.comum.a"/></td>
														<td><input type='text' name='dataFim' id='dataFim'
															size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset style="height: 65px">
											<label><fmt:message key="citcorpore.comum.numero"/></label>
											<div>
												<input type="text" id="idSolicitacaoServico" name="idSolicitacaoServico" size="9" maxlength="9" class='Format[Numero]'/>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset  style="height: 65px">
											<label><fmt:message key="relatorioSolicitacaoPorExecutante.executadoPor" /> </label>
											<div>
												<input type="text" onfocus='abrePopupResponsavel();' id="nomeResponsavelAtual" name="nomeResponsavelAtual" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 65px">
											<label><fmt:message key="contrato.contrato" /></label>
											<div>
												<select name="idContrato"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_10">
										<fieldset style="height: 65px">
											<label><fmt:message key="citcorpore.comum.topList" /></label>
											<div>
												<select name="topList"></select>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<fieldset>
										<button type='button' name='btnRelatorio' class="light" onclick='imprimirRelatorioPdf();' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png" style="padding-left: 8px;">
											<span><fmt:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnRelatorio' class="light"	onclick='imprimirRelatorioXls();' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/util/excel.png"  style="padding-left: 8px;">
											<span><fmt:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light" onclick='limpar()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
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
	</div>
	<%@include file="/include/footer.jsp"%>
</body>

	<div id="POPUP_RESPONSAVEL" title="<fmt:message key="citcorpore.comum.pesquisaresponsavel" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaResponsavel' style="width: 540px">
							<cit:findField formName='formPesquisaResponsavel'
								lockupName='LOOKUP_USUARIO_EMPREGADO' id='LOOKUP_RESPONSAVEL' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</html>

