<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html>
<html>
<head>
<%
	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
%>

<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp" %>
<title><fmt:message key="citcorpore.comum.title"/></title>
<link rel="stylesheet" type="text/css" href="./css/importManager.css">
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="${ctx}/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.position.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.autocomplete.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker-locale.js"></script>
<script src="${ctx}/js/jquery.ui.datepicker-init.js"></script>

<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/lookup/jquery.ui.lookup.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-easy.css">
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.ui.datepicker.css">
<link rel="stylesheet" type="text/css" href="${ctx}/pages/portal/css/jquery-ui-1.8.21.custom.css" />

<script type="text/javascript" src="${ctx}/js/locale/easyui-lang-pt_BR.js"></script>

<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/formparams/jquery.formparams.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.parser.js"></script>

<script type="text/javascript" src="./js/importManager.js"></script>

<%
//se for chamado por iframe deixa apenas a parte de cadastro da página
if(iframe != null){%>
<link rel="stylesheet" type="text/css" href="./css/importManagerIFrame.css">
<%}%>

</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body id='body' class="easyui-layout" style='background-color: white;'>
	<div data-options="region:'north',split:false" style="height: 125px;" class="dinamic-menu">
		<%@include file="/include/menu_horizontal.jsp"%>
	</div>
	<div data-options="region:'center'" title="Import Manager">
<!-- Conteudo -->
	<div id="tabs" class="easyui-tabs" data-options="tools:'#tab-tools'">
		<div id="tabs-1" data-options="tools:'#p-tools'" style="width:100% !important;" title="Import Manager">
			<form name='form' action='${ctx}/pages/importManager/importManager'>
				<input type='hidden' name='jsonMatriz'/>
				<input type='hidden' name='idImportConfig'/>
				<input type='hidden' name='auxData'/>
				<table>
					<tr>
						<td colspan="2">
							<table>
								<tr>
									<td>
										<fmt:message key="importmanager.tipo"/>:*
									</td>
									<td>
										<select name='tipo' id='tipo' onchange='validaTipo(this)'>
											<option value='J'><fmt:message key="importmanager.tipo.jdbc"/></option>
											<option value='C'><fmt:message key="importmanager.tipo.csvfile"/></option>
											<option value='T'><fmt:message key="importmanager.tipo.txtfile"/></option>
										</select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div id='divJdbc'>
								<table>
									<tr>
										<td colspan="2">
											<b><fmt:message key="importmanager.origem"/></b>
										</td>
									</tr>
									<tr>
										<td>
											<fmt:message key="importmanager.conexao"/>:*
										</td>
										<td>
											<select name='idExternalConnection' id='idExternalConnection' onchange='selecionaExternalConnection()'>
											</select>
										</td>
									</tr>
									<tr>
										<td>
											<fmt:message key="importmanager.tablename"/>:*
										</td>
										<td>
											<select name='tabelaOrigem' id='tabelaOrigem' onchange='selecionaOrigemDestinoDados()'>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<b><fmt:message key="importmanager.destino"/></b>
										</td>
									</tr>
									<tr>
										<td>
											<fmt:message key="importmanager.tablename"/>:*
										</td>
										<td>
											<select name='tabelaDestino' id='tabelaDestino' onchange='selecionaOrigemDestinoDados()'>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<b><fmt:message key="importmanager.nome"/></b>
										</td>
									</tr>
									<tr>
										<td>
											<fmt:message key="importmanager.nome"/>:*
										</td>
										<td>
											<input type='text' name='nome' size="100" maxlength="100"/>
										</td>
									</tr>
								</table>
								<div>
									<table id="tt" style="width:800px;height:400px"
											data-options="iconCls:'icon-edit',singleSelect:true,idField:'idorigem',url:'datagrid_data2.json'"
											title="<fmt:message key="importmanager.dadosimportacao"/>">
										<thead>
											<tr>
												<th data-options="field:'idorigem',width:200,
														editor:{
															type:'combobox',
															options:{
																valueField:'id',
																textField:'text',
																data:origem,
																required:true
															}
														}"><fmt:message key="importmanager.origem"/></th>
												<th data-options="field:'iddestino',width:200,
														editor:{
															type:'combobox',
															options:{
																valueField:'id',
																textField:'text',
																data:destino,
																required:true
															}
														}"><fmt:message key="importmanager.destino"/></th>
												<th data-options="field:'script',width:400,editor:'textarea'"><fmt:message key="importmanager.script"/></th>
											</tr>
										</thead>
									</table>
								</div>
								<div>
									<table>
										<tr>
											<td>
												<button type="button" onclick='gravar()'><fmt:message key="citcorpore.comum.gravar"/></button>
											</td>
											<td>
												<button type="button" onclick='carregarDados()'><fmt:message key="importmanager.executar"/></button>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
			</div>
			<div id="tabs-2" data-options="tools:'#p-tools'" style="width:100% !important;" title="<fmt:message key="citcorpore.comum.pesquisa" />">
						<div class="section">
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_IMPORTCONFIG' id='LOOKUP_IMPORTCONFIG' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
</body>
</html>