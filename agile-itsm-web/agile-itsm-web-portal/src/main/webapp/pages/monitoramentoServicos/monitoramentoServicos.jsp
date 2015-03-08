<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@page import="br.com.citframework.dto.Usuario" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico" %>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho" %>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>

<%
	response.setCharacterEncoding("ISO-8859-1");

	String login = "";
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario != null) {
		login = usuario.getLogin();
	}

	SituacaoSolicitacaoServico situacaoSolicitacaoServico_Suspensa = SituacaoSolicitacaoServico.Suspensa;
	pageContext.setAttribute("situacaoSolicitacaoServico_Suspensa", situacaoSolicitacaoServico_Suspensa);
%>

<!DOCTYPE html>
<html>
<head>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
	<link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />

	<script>
		var idRequisicao = "${idRequisicao}";
		var situacaoSolicitacaoServico_Suspensa = "${situacaoSolicitacaoServico_Suspensa}";
		var login = "${login}";
	</script>

	<title>CITSMart</title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

	<link type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css"/>

	<!-- theme is last so will override defaults -->
	<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery.ui.all.css"/>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jQueryGantt/css/style.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/css/slick.grid.css"/>
	<link type="text/css" rel="stylesheet" class="include" href="${ctx}/pages/graficos/jquery.jqplot.min.css" />
	<!-- Easy-pie Plugin -->
	<link href="${ctx}/novoLayout/common/theme/scripts/plugins/charts/easy-pie/jquery.easy-pie-chart.css" rel="stylesheet" />

	<link rel="stylesheet" type="text/css" href="css/monitoramentoServicos.css"/>

	<script type="text/javascript" src="${ctx}/js/jquery-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-ui-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.layout-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/debug.js"></script>

	<script type="text/javascript" src="${ctx}/template_new/js/uniform/jquery.uniform.min.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/autogrow/jquery.autogrowtextarea.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/multiselect/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/selectbox/jquery.selectBox.min.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/timepicker/jquery.timepicker.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/colorpicker/js/colorpicker.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/tiptip/jquery.tipTip.minified.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/validation/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/uitotop/js/jquery.ui.totop.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/custom/ui.js"></script>
	<script type="text/javascript" src="${ctx}/template_new/js/custom/forms.js"></script>

	<script class="include" type="text/javascript" src="${ctx}/pages/graficos/jquery.jqplot.min.js"></script>

	<script class="include" type="text/javascript" src="${ctx}/pages/graficos/plugins/jqplot.pieRenderer.min.js"></script>

	<script type="text/javascript" src="${ctx}/js/json2.js"></script>

	<!-- SlickGrid and its dependancies (not sure what they're for) -->
	<script type="text/javascript" src="${ctx}/js/jquery.rule-1.0.1.1-min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.event.drag.custom.js"></script>

	<script type="text/javascript" src="${ctx}/js/slick.core.js"></script>
	<script type="text/javascript" src="${ctx}/js/slick.editors.js"></script>
	<script type="text/javascript" src="${ctx}/js/slick.grid.js"></script>

	<script type="text/javascript" src="${ctx}/js/CollectionUtils.js"></script>
	<!-- Flot Charts Plugin -->
	<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.js"></script>
	<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.pie.min.js"></script>
	<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.tooltip.min.js"></script>
	<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.selection.min.js"></script>
	<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.resize.min.js"></script>
	<script src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.orderBars.js"></script>

	<script type="text/javascript" src="js/monitoramentoServicos.js"></script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />

<body>
	<%@include file="/include/menu_horizontal_gerenciamento.jsp"%>
	<h2 class="loading"><fmt:message key="citcorpore.comum.aguardecarregando"/></h2>

	<div id="botao_voltar">
		<a href="#" onclick="voltar()">
			<fmt:message key="citcorpore.comum.voltar" />
		</a>
	</div>

	<div class="ui-layout-center hidden ">
		<table width='100%' height='100%' style='width: 100%; height: 100%; '>
			 <tr>
				<td width='100%' style='width: 100%; vertical-align: top !important; '>
					<iframe id='fraRequisicaoMudanca' src='about:blank' width="100%" style='width: 100%; border:none;' onload="resize_iframe()"></iframe>
				</td>
			</tr>
		</table>
	</div>

	<div class="ui-layout-south hidden ">
		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1" onclick="pesquisaServico();"><fmt:message key="painel.monitoramentoIncidente" /></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top" onclick="atualizaGraficoAba();"><fmt:message key='citcorpore.comum.graficos'/></a>
				</li>
			</ul>

			<div class="toggle_container">
				<div id="tabs-1" class="block">
					<div class="clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" style="min-height: 75px; ">
						<form name='formPesquisa' id='formPesquisa' action='${ctx}/pages/gerenciamentoMudancas/gerenciamentoMudancas'>
							<input type="hidden" name="nomeCampoOrdenacao" id="nomeCampoOrdenacao" />
							<input type="hidden" name="ordenacaoAsc" id="ordenacaoAsc" />

							<div class="space">
								<div class='row-fluid'>
									<div class='span12' style='vertical-align: center;'>
										<div class='span1'>
											<span class="btn btn-default btn-primary margin_top" type="button" onclick="window.location.reload();" id=""><i class="icon-white icon-refresh"></i>&nbsp;<fmt:message key="citcorpore.comum.atualizar" /></span>
										</div>
										<div class="span2 margin_top">
											<label>
												<input type='checkbox' id='chkAtualiza' name='chkAtualiza' value='X' checked="checked"/>
												<fmt:message key="citcorpore.comum.atualizar" />&nbsp;<fmt:message key="citcorpore.comum.automaticamente" />
											</label>
										</div>
										<div class='span3'>
											<label><fmt:message key="contrato.contrato" /></label>
											<input type="text" id="addContrato" name="nomeContrato" />
										</div>
										<div class='span2'>
											<label><fmt:message key="citcorpore.comum.grupoExecutor" /></label>
											<input type="text" name='nomeGrupo' id="addGrupo"/>
										</div>
										<div class='span1'>
											<span class="btn btn-default btn-primary margin_top" type="button" onclick="limparCampos();" id=""><fmt:message key="citcorpore.ui.botao.rotulo.Limpar" /></span>
										</div>
										<div class='span1'>
											<span class="btn btn-default btn-primary margin_top" type="button" onclick="pesquisaServico();" id=""><i class="icon-white icon-refresh"></i>&nbsp;<fmt:message key="citcorpore.comum.pesquisar" /></span>
										</div>
									</div>
									<div style="color:red">*<fmt:message key="monitoramento.listaApenasSolicitacoes" /></div>
								</div>
							</div>
						</form>
					</div>
					<div id='divConteudoLista' class="ui-layout-content" style="height: 500px;">
						<div id='divConteudoListaInterior' style='height: 100%; width: 100%'></div>
					</div>
				</div>

				<div id="tabs-2" class="block">
					<div style='vertical-align: top; '>
						<div class='col_100' id='tdAvisosSol'></div>
					</div>
				</div>

				<form name='form' id='form' action='${ctx}/pages/monitoramentoServicos/monitoramentoServicos'>
					<input type='hidden' name='idFluxo'/>
					<input type='hidden' name='idVisao'/>
					<input type='hidden' name='idTarefa'/>
					<input type='hidden' name='acaoFluxo'/>
					<input type='hidden' name='idUsuarioDestino'/>
					<input type='hidden' name='numeroContratoSel'/>
					<input type='hidden' name='idProblemaSel'/>
					<input type='hidden' name='atribuidaCompartilhada'/>
					<input type='hidden' name='idProblema' id='idProblemaForm'/>
					<input type="hidden" name="nomeCampoOrdenacao" id="nomeCampoOrdenacao2" />
					<input type="hidden" name="ordenacaoAsc" id="ordenacaoAsc2" />
					<input type="hidden" name="idContrato" id="idContrato" />
					<input type="hidden" name="numeroContratoSel" id="numeroContratoSel" />
					<input type="hidden" name="idGrupoAtual" id="idGrupoAtual" />
				</form>

				<div id="POPUP_VISAO">
					<iframe id='fraVisao' src='about:blank' width="100%" height="100%"></iframe>
				</div>

				<div id="popupCadastroRapido">
					<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_CONTRATO" title="<fmt:message key="grupovisao.contratos" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaLo' style="width: 540px">
							<cit:findField formName='formPesquisaLo'
								lockupName='LOOKUP_CONTRATOS' id='LOOKUP_CONTRATOS' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_GRUPO" title="<fmt:message key="lookup.grupo" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaGrupo' style="width: 540px">
							<cit:findField formName='formPesquisaGrupo'
								lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0'
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