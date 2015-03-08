<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citgerencial.bean.GerencialPainelDTO"%>

<%
	String fileNameGrupo = request.getParameter("fileNameGrupo");
	if (fileNameGrupo == null) fileNameGrupo = "";

	String groupName = request.getParameter("groupName");
	if (groupName == null) groupName = "";

	String showOptions = request.getParameter("showOptions");
	if (showOptions == null) showOptions = "";
	if (showOptions.equalsIgnoreCase("")) showOptions = "true";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/include/header.jsp"%>
	<title>CITSMart</title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>


	<link rel="stylesheet" type="text/css" href="./css/painel.css" />

	<script type="text/javascript" src="${ctx}/js/javaScriptComuns.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.printElement.js"></script>

	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/fonts.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/text.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/fonts.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css"/>
	<link rel="stylesheet" type="text/css"href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>


	<script type="text/javascript" src="${ctx}/pages/painel/jquery-1.8.0.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.core.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.widget.js"></script>

	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.datepicker.js"></script>
	<script src="/citsmart/js/jquery.ui.datepicker-locale.js"></script>
	<script src="${ctx}/js/jquery.ui.datepicker-init.js"></script>

	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.dialog.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.draggable.js"></script>
	<script type="text/javascript" src="${ctx}/pages/painel/jquery.ui.position.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/pages/portal/css/jquery-ui-1.8.21.custom.css" />
	<script type="text/javascript">
		var ctx = "${ctx}"
	</script>
	<script type="text/javascript" src="./js/painel.js">	</script>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body id="bodyNode" style="background-color: white;">

	<form name='formPainel' class="form-horizontal" style="margin: 0" method="post" action='<%=Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/painel/painel'>
		<input type='hidden' name='fileName'/>
		<input type='hidden' name='fileNameItem'/>
		<input type='hidden' name='tipoSaida'/>
		<input type='hidden' name='tipoSaidaApresentada'/>
		<input type='hidden' name='campo'/>
		<input type='hidden' name='parametersPreenchidos' id='parametersPreenchidos' value='false'/>
		<div id='divInfTransfPainel' style='display:none'></div>

		<div class="wj">
			<div class="He">
				<span id="usuarioRel" class="Xib"></span><a class="Ki" href="/citsmart/pages/login/login.load?logout=yes"><fmt:message key="citcorpore.comum.sair" /></a>
			</div>
			<div class="XF">
				<div title="CITSmart"></div>
			</div>

			<div id="TabPanel">
				<div class="pg">
					<div class="lFloat tf">
						<h1><fmt:message key="painel.relatoriosPadrao" /></h1>
					</div>
					<div class="lFloat n">
						<div class='row-fluid'>
							<div class='span12'>
								<div class='control-group'>
								  <label id='labelGrupoInf'><fmt:message key="painel.grupoInformacoes" />:</label>
								</div>
								<div class='controls'>
									<select id='fileNameGrupo' class='ui-state-default ui-combobox-input ui-autocomplete-input ui-widget ui-widget-content ui-corner-left' name='fileNameGrupo' style='width: 300px; text-align: left' onchange='submitGrupo()'>
										<option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
										<option value='grupoPaineis/incidentes/pnl1Incidentes.xml'><fmt:message key="painel.incidentesRequisicoes" /></option>
										<option value='grupoPaineis/itemcfg/pnl1BaseItemCfg.xml'><fmt:message key="painel.itemcfg" /></option>
										<option value='grupoPaineis/servicos/pnl1Servicos.xml'><fmt:message key="painel.servicos" /></option>
			                            <option value='grupoPaineis/mudancas/pnl1Mudancas.xml'><fmt:message key="requisicaoMudanca.mudancas" /></option>
			                            <option value='grupoPaineis/requisicaoMudancas/pnl1requisicaoMudanca.xml'><fmt:message key="requisicaoMudanca.requisicaoMudanca" /></option>
									</select>
									</div>
							</div>
						</div>
					</div>
					 <div class="rFloat">
						<button type="button" name='btnPesquisar' class="light"  onclick='voltar();' style="margin-top: 5px !important;"><span><fmt:message key="citcorpore.comum.voltar" /></span></button>
					</div>
				</div>
			</div>
		</div>

		<table width="100%">
			<tr>
				<td valign="top" class="w200">
					<div id='divLista' class="ug" >

					</div>
				</td>
				<td valign="top" class="aq"></td>
				<td valign="top" class="w100p">
					<div id="painelcontrole">
						<h1><fmt:message key="painel.painelControle" /></h1>
						<div id="parametros" title="<fmt:message key="painel.cliqueParaMudarParametros" />"></div>
						<div id="parametrosIcon"></div>
					</div>

					<div id='divPainel'></div>
				</td>
			</tr>
		</table>
	</form>


	<div id='divAguarde' style='position: absolute; display:none; CURSOR: wait; BACKGROUND-COLOR:gray; filter:alpha(opacity=20);-moz-opacity:.25;opacity:.25;'>
		<table width="100%" >
			<tr>
				<td>
					<font  size='15'><b><fmt:message key="citcorpore.comum.aguardeCarregando" /></b></font>
				</td>
			</tr>
		</table>
	</div>

		<form name='formGetExcel'>
			<input type='hidden' name='fileNameExcel'/>
			<input type='hidden' name='fileNameExcelShort'/>

		 	<input type='hidden' name='parmCount'/>
		 	<input type='hidden' name='parm1'/>
		 	<input type='hidden' name='parm2'/>
		 	<input type='hidden' name='parm3'/>
		</form>

	<div id='divGenerateExcel' style='display:none'>
		<iframe src='about:blank' name='frameGetExcel' id='frameGetExcel'></iframe>
	</div>

<div  style="background:white" id="POPUP_PARAM">
	<form name="formParametros" id="formParametros">
	<div id='divParametros' style='width: 98%; overflow: auto; padding: 7px;'></div>
		<table>
			<tr>
				<td align="center" valign="middle">
					<div id="Throbber" class="throbber"></div>
				</td>
				<td>

					<button type='button' name='btnOKParms' class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only light" onclick='gerarPainel();' style="margin: 20px !important;">
						<span><fmt:message key="citcorpore.comum.gerar" /></span>
					</button>

					<button type='button' name='btnFecharParms' class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only light" onclick='fecharTelaParm();limparForm();' style="margin: 20px !important;">
						<span><fmt:message key="citcorpore.comum.fechar" /></span>
					</button>
				</td>
			</tr>
		</table>
	</form>
</div>

<div style="background:white" title="Opções de Gráficos" id="POPUP_GRAFICO_OPC">
	<table>
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<div id='divOpcoesGraficos' style='height: 150px; background-color: white; width:348px;'>

				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div style='background-color: white;text-align:right;'>

					<div id="Throbber_2" class="throbber"></div>

					<input type='button' class='ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'
					id='btnFecharOptGrf' name='btnFecharOptGrf' value='Fechar' onclick="$('#POPUP_GRAFICO_OPC').dialog('close');"/>
				</div>
			</td>
		</tr>
	</table>
</div>

</body>


</html>
