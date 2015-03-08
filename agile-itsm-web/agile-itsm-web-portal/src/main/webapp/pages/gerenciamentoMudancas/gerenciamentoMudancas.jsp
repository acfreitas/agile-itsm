<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@page import="br.com.citframework.dto.Usuario" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoMudanca" %>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho" %>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>

<!-- Thiago Fernandes - 23/10/2013 16:50 - Sol. 121468 - Retirar barras de rolagens desnecesarias. Linhas 652, 653, 792, 1221. -->
<%
	response.setCharacterEncoding("ISO-8859-1");

	String login = "";
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario != null) {
		login = usuario.getLogin();
	}
	pageContext.setAttribute("login", login);

	pageContext.setAttribute("situacaoMudancaSuspensa", SituacaoRequisicaoMudanca.Suspensa.name());
%>
<!DOCTYPE html>
<html>
<head>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>

	<link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />
	<title><fmt:message key="citcorpore.comum.title" /></title>

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
	<link rel="stylesheet" type="text/css" class="include" href="${ctx}/pages/graficos/jquery.jqplot.min.css" />

	<link rel="stylesheet" type="text/css" href="css/gerenciamentoMudancas.css">

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

	<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

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

	<script type="text/javascript" src="${ctx}/js/highcharts.js"></script>
	<script type="text/javascript" src="${ctx}/js/exporting.js"></script>

	<script type="text/javascript">
		var login = "${login}";
		var idRequisicao = "${idRequisicao}";
		var situacaoMudancaSuspensa = "${situacaoMudancaSuspensa}";

		function resize_iframe() {
			var height=window.innerWidth;//Firefox

			if (document.body.clientHeight) {
				height=document.body.clientHeight;//IE
			}
			document.getElementById("fraRequisicaoMudanca").style.height=parseInt(height - document.getElementById("fraRequisicaoMudanca").offsetTop-135)+"px";
		}
	</script>
</head>

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
		<div class="flat_area grid_16">
			<h2><fmt:message key='gerenciamentoMudanca.gerenciamentoMudancas'/></h2>
		</div>

		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><fmt:message key='gerenciamentoMudanca.gerenciamentoMudancas'/></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><fmt:message key='citcorpore.comum.graficos'/></a>
				</li>
			</ul>

			<div class="toggle_container">
				<div id="tabs-1" class="block">
					<div class="clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" style="min-height: 75px; ">
						<form name='formPesquisa' id='formPesquisa' action='${ctx}/pages/gerenciamentoMudancas/gerenciamentoMudancas'>
							<div class="space" >
								<table width="100%" cellspacing="3" >
									<tr>
										<td style='vertical-align: top;'>
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td style='vertical-align: middle;'>
														<fmt:message key="requisicaoMudanca.NumeroMudanca" />:&nbsp;
													</td>
													<td>
														<input type="text" onkeypress="return disableEnterKey(event)" name="idRequisicaoSel" id="idRequisicaoSel" class="Format[Numero]" />
													</td>
													<td style='vertical-align: middle;'>
														<a href='#' class='btn-action glyphicons search btn-default titulo' title="<fmt:message key="citcorpore.comum.pesquisar"/>" onclick="atualizarListaTarefas()"><i></i></a>
													</td>
													<td style='vertical-align: middle;'>&nbsp;&nbsp;
														<fmt:message key="gerenciaservico.atribuidacompartilhada"/>&nbsp;
													</td>
													<td style='vertical-align: middle;'>
														<select name='atribuidaCompartilhada' id='atribuidaCompartilhada' onchange="atualizarListaTarefas()"></select>
													</td>
													<td style='vertical-align: middle;'>
														<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title="<fmt:message key="citcorpore.comum.limpar" />" onclick="document.formPesquisa.clear();atualizarListaTarefas()"><fmt:message key='citcorpore.ui.botao.rotulo.Limpar' /></span>
													</td>
												</tr>
											</table>
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											<span class="btn btn-small btn-primary" title="<fmt:message key='requisicaoMudanca.cadastrarMudanca'/>" onclick="abrirSolicitacao()">
												<fmt:message key="requisicaoMudanca.cadastrarMudanca"/>
											</span>

											<span title="<fmt:message key='gerenciarequisicao.pesquisaMudanca'/>" class="btn btn-small btn-primary" onclick="abrePopupPesquisa()">
												<fmt:message key="gerenciarequisicao.pesquisaMudanca"/>
											</span>
											<span class="btn btn-default btn-primary" type="button" onclick="atualizarListaTarefas();" id=""><i class="icon-white icon-refresh"></i></span>

											<div class="T-I J-J5-Ji ar7 nf L3 T-I-JO ">
												<label>
													<input type='checkbox' id='chkAtualiza' name='chkAtualiza' value='X'/>
													<fmt:message key="citcorpore.comum.atualizar" />&nbsp;<fmt:message key="citcorpore.comum.automaticamente" />
												</label>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</form>
					</div>

					<div id='divConteudoLista' class="ui-layout-content" style="height: 380px;">
						<div id='divConteudoListaInterior' style='height: 100%; width: 100%'></div>
					</div>
				</div>

				<div id="tabs-2" class="block">
					<table cellpadding='0' cellspacing='0'>
						<tr>
							<td id='tdAvisosSol' style='vertical-align: top; '></td>
						</tr>
					</table>
				</div>

				<form name='form' id='form' action='${ctx}/pages/gerenciamentoMudancas/gerenciamentoMudancas'>
					<input type='hidden' name='idFluxo'/>
					<input type='hidden' name='idVisao'/>
					<input type='hidden' name='idTarefa'/>
					<input type='hidden' name='acaoFluxo'/>
					<input type='hidden' name='idUsuarioDestino'/>
					<input type='hidden' name='numeroContratoSel'/>
					<input type='hidden' name='idRequisicaoSel'/>
					<input type='hidden' name='atribuidaCompartilhada'/>
					<input type='hidden' name='idRequisicao' id='idRequisicaoForm'/>
					<input type='hidden' name='erroGrid' id='erroGrid'/>
				</form>

				<div id="POPUP_VISAO">
					<iframe id='fraVisao' src='about:blank' width="98%" height="99%"></iframe>
				</div>

				<div id="POPUP_REUNIAO">
					<iframe id='fraReuniao' src='about:blank' width="100%" height="100%"></iframe>
				</div>

				<div id="popupCadastroRapido">
					<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="js/gerenciamentoMudancas.js"></script>
</body>
</html>
