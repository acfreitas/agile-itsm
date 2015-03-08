<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<link rel="stylesheet" type="text/css" href="./css/relatorioOrdemServicoUst.css" />

<script type="text/javascript" src="./js/relatorioOrdemServicoUst.js"></script>
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
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="relatorioUtilizacaoUSTs.relatorioUtilizacaoUSTs" /></a></li>
				</ul>
				<div class="toggle_container">
					<div class="block" >
						<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/relatorioOrdemServicoUst/relatorioOrdemServicoUst'>
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset style="height: 53px">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.ano" /></label>
											<div>
												<select class="Valid[Required] Description[ano]" id="ano"  name="ano"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 53px">
											<label class="campoObrigatorio"><fmt:message key="contrato.contrato" /></label>
											<div>
												<select class="Valid[Required] Description[contrato]" id="idContrato"  name="idContrato"></select   >
											</div>
										</fieldset>
									</div>
								</div>

								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioOrdemServicoUst()"
											style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
											<span><fmt:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioOrdemServicoUstXls()"
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
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>

