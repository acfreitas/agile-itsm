<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@page import="br.com.citframework.dto.Usuario" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoLiberacao" %>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho" %>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>

<%
	response.setCharacterEncoding("ISO-8859-1");

	String login = "";
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario != null) {
		login = usuario.getLogin();
	}

	pageContext.setAttribute("login", login);
	pageContext.setAttribute("idUsuario", usuario.getIdUsuario());

	pageContext.setAttribute("situacaoLiberacaoSuspensa", SituacaoRequisicaoLiberacao.Suspensa.name());
%>

<!DOCTYPE html>
<html>
<head>
	<%@include file="/include/header.jsp"%>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
	<link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />

	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

	<link type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css"/>

	<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.ui.all.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jQueryGantt/css/style.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/css/slick.grid.css"/>
	<link rel="stylesheet" type="text/css" class="include" href="${ctx}/pages/graficos/jquery.jqplot.min.css" />

	<link rel="stylesheet" type="text/css" href="${ctx}/pages/gerenciamentoLiberacao/css/gerenciamentoLiberacao.css" />

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
	<script type="text/javascript" class="include" src="${ctx}/pages/graficos/jquery.jqplot.min.js"></script>
	<script type="text/javascript" class="include" src="${ctx}/pages/graficos/plugins/jqplot.pieRenderer.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/json2.js"></script>
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
		var situacaoLiberacaoSuspensa = "${situacaoLiberacaoSuspensa}";

		function resize_iframe() {
			var height=window.innerWidth; //Firefox
			if (document.body.clientHeight) {
				height=document.body.clientHeight; //IE
			}
			document.getElementById("fraRequisicaoLiberacao").style.height=parseInt(height - document.getElementById("fraRequisicaoLiberacao").offsetTop-135)+"px";
		}
	</script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />

<body>
	<%@include file="/include/menu_horizontal_gerenciamento.jsp"%>

	<h2 class="loading"><fmt:message key="citcorpore.comum.aguardecarregando"/></h2>

	<div class="ui-layout-center hidden">
		<table width='100%' height='100%' style='width: 100%; height: 100%'>
			<tr>
				<td width='100%' style='width: 100%; height: 100%; vertical-align: top !important;'>
					<iframe id='fraRequisicaoLiberacao' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()"></iframe>
				</td>
			</tr>
		</table>
	</div>

	<div class="ui-layout-south hidden">

	<div class="flat_area grid_16">
		<h2><fmt:message key="gerenciamentoLiberacao.gerenciamentoLiberacao" /></h2>
	</div>

	<div class="box grid_16 tabs">

		<ul class="tab_header clearfix">
			<li>
				<a href="#tabs-1"><fmt:message key="gerenciamentoLiberacao.gerenciamentoLiberacao" /></a>
			</li>
			<li>
				<a href="#tabs-2" class="round_top"><fmt:message key="gerenciamentoLiberacao.gerenciamentoLiberacaoGraficos" /></a>
			</li>
		</ul>

		<div class="toggle_container">
			<div id="tabs-1" class="block">
				<div class="clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" style="min-height: 75px;">
					<form name='formPesquisa' id='formPesquisa' action='${ctx}/pages/gerenciamentoLiberacao/gerenciamentoLiberacao'>
						<table width="100%">
							<tr>
								<td style='vertical-align: top;'>
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td style='vertical-align: middle;'>
													<fmt:message key="requisicaoLiberacao.numeroLiberacao" />:
												</td>
												<td style='vertical-align: top;'>
													<input onkeypress="return disableEnterKey(event)" class='Format[Numero]' type='text' name="idRequisicaoSel" id="idRequisicaoSel" size="10" style="border:1px solid #B3B3B3;height:24px; width: 77px !important" />
												</td>
												<td style='vertical-align: middle;'>
													<a href='#' class='btn-action glyphicons search btn-default titulo'  title="<fmt:message key="citcorpore.comum.pesquisar"/>" onclick="atualizarListaTarefas()"><i></i></a>
												</td>
												<td style='vertical-align: middle;'>
													<fmt:message key="gerenciaservico.atribuidacompartilhada"/>
												</td>

												<td style='vertical-align: top;'>
													<select name='atribuidaCompartilhada' id='atribuidaCompartilhada' onchange="atualizarListaTarefas()"></select>
												</td>

												<td style='vertical-align: middle;'>
													<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title="<fmt:message key="citcorpore.comum.limpar" />"  onclick="document.formPesquisa.clear();atualizarListaTarefas()"><fmt:message key='citcorpore.ui.botao.rotulo.Limpar' /></span>
												</td>
											</tr>
										</table>
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<span class="btn btn-small btn-primary" title="<fmt:message key='requisicaoLiberacao.cadastrarLiberacao'/>"  onclick="abrirSolicitacao()">
										<fmt:message key="requisicaoLiberacao.cadastrarLiberacao"/>
									</span>
									<span  title="<fmt:message key='gerenciarequisicao.pesquisaliberacao'/>"  class="btn btn-small btn-primary" onclick="abrePopupPesquisa()">
										<fmt:message key="gerenciarequisicao.pesquisaliberacao"/>
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
					</form>
				</div>
				<div id='divConteudoLista' class="ui-layout-content" style="height: 380px;">
					<div id='divConteudoListaInterior' style='height: 100%; width: 100%'></div>
				</div>
			</div>
		</div>

		<div id="tabs-2" class="block">
			<table cellpadding='0' cellspacing='0'>
				<tr>
					<td id='tdAvisosSol' style='vertical-align: top; '>
					</td>
				</tr>
			</table>
		</div>

		<form name='form' id='form' action='${ctx}/pages/gerenciamentoLiberacao/gerenciamentoLiberacao'>
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
			<iframe id='fraVisao' src='about:blank' width="100%" height="100%"></iframe>
		</div>

		<div id="popupCadastroRapido">
			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/pages/gerenciamentoLiberacao/js/gerenciamentoLiberacao.js"></script>
</body>
</html>
