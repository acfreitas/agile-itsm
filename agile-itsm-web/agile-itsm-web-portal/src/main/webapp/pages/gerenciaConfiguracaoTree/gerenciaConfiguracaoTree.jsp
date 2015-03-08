<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>
<%@page import="javax.servlet.http.HttpSession" %>

<!doctype html public "">

<html>

	<head>

		<%
			String id = request.getParameter("idBaseConhecimento");
		    String iframeProblema = "";
		    iframeProblema = request.getParameter("iframeProblema");
		    if (iframeProblema == null) {
		    	iframeProblema = "false";
			}
		%>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<link type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css"/>

		 <!-- Imports do layout -->
		<script type="text/javascript" src="${ctx}/js/jquery-latest.js"></script>
   		<script type="text/javascript" src="${ctx}/js/jquery-ui-latest.js"></script>
    	<script type="text/javascript" src="${ctx}/js/jquery.layout-latest.js"></script>
    	<script type="text/javascript" src="${ctx}/js/debug.js"></script>
		<!-- Imports da jsTree -->
		<script src="${ctx}/template_new/js/jstree/_lib/jquery.cookie.js"  type="text/javascript"></script>
		<script src="${ctx}/template_new/js/jstree/_lib/jquery.hotkeys.js"  type="text/javascript"></script>
		<script src="${ctx}/template_new/js/jstree/jquery.jstree.js"  type="text/javascript"></script>



		<!-- Fim Imports da jsTree -->

    	<%--<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery.ui.all.css"/> --%>
		<link rel="stylesheet" type="text/css" href="./css/gerenciaConfiguracaoTree.css">
		<script type="text/javascript">
			var ctx = "${ctx}";
		</script>
		<script type="text/javascript" src="./js/gerenciaConfiguracaoTree.js"></script>
	</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title=""
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

	<body>

	<div class="ui-layout-north">
		<div id="divLogo">
			<table cellpadding='0' cellspacing='0'>
				<tr>
					<td>
						<img border="0" src="${ctx}/imagens/logo/CMDB2.png" />
					</td>
				</tr>
			</table>
		</div>

		<div id="divControleLayout" style="position: fixed;top:1%;right: 2%;z-index: 100000;float: right;display: block;">
				<table cellpadding='0' cellspacing='0'>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td style='vertical-align: top;'>
							<%-- <label style="margin-left: 20%; font-size: large;"><b><fmt:message key="gerenciaConfiguracaoTree.CMDBExplorer" /></b></label> --%>
							<%
							if (iframeProblema.equalsIgnoreCase("false")){
							%>
							<button  type="button" class="light img_icon has_text" style='text-align: right; margin-left: 99%; float: right; display: block;' onclick="voltar()" title="<fmt:message key="citcorpore.comum.voltarprincipal" />">
								<img border="0" title="<fmt:message key='citcorpore.comum.voltarprincipal' />" src="${ctx}/imagens/back.png" /><span style="padding-left: 0px !important;"><fmt:message key="citcorpore.comum.voltar" /></span>
							</button>
							<%
							}
							%>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="ui-layout-west " id="west" style="overflow: hidden;">
			<!--
			adicionado div superior
			@autor flavio.santana
			25/10/2013 10:50
			 -->
			<div id="calcTamanho">
				<form action="javascript:;" onsubmit="filtar()">
					<div class="row-fluid">
						<div class="span12">
							<label class="content-row">
								<div><fmt:message key="citcorpore.comum.identificacao" /></div>
								<span><input name="identificador" id="identificador" type="text" class="text"/></span>
							</label>
						</div>
					</div>
					<div id="filtro">
						<div class="row-fluid">
							<div class="span12">
								<label class="content-row">
									<div><fmt:message key="citcorpore.comum.criticidade" /></div>
									<span><select id="cboCriticidade" name="cboCriticidade"></select></span>
								</label>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span12">
								<label class="content-row">
									<div><fmt:message key="itemConfiguracaoTree.status" /></div>
									<span><select id="comboStatus" name="comboStatus"></select></span>
								</label>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span12 right">
							<div class="content-row">
								<div class="row-fluid">
									<div class="span6">
										<span id='spanPesq' class='manipulaDiv' style='cursor: pointer'><fmt:message key="gerenciaConfiguracaoTree.outrosFiltros" /> &nbsp;<img src="${ctx}/imagens/search.png" /></span>
									</div>
									<div class="span6">
										<button id="buttonFiltro" type="button" title="" class="light" onclick="filtar()" style="float: right !important;">
											<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
											<span style="padding-left: 0px!important;"><fmt:message key="gantt.filtrar" /></span>
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>

				</form>
			</div>
			<div id="treeTop">
				<div id="jsTreeIC"></div>
			</div>
		</div>

		<div class="ui-layout-center " style="overflow: hidden;" >
			<div id="FRAME_OPCOES" style="overflow: hidden;" >
				<iframe id="iframeOpcoes" name="iframeOpcoes" width="100%" height="100%"></iframe>
			</div>
		</div>

		<!-- <div class="ui-layout-east hidden"></div> -->

		<!-- <div class="ui-layout-south hidden"></div> -->


		<form name="formTree" action="${ctx}/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree">
			<input type='hidden' id="idItemConfiguracaoExport" name='idItemConfiguracaoExport'/>
			<input type='hidden' id="idItemConfiguracao" name='idItemConfiguracao'/>

			<%if(id!=null){ %>
				<input type='hidden' name='idBaseConhecimento' id='idBaseConhecimento' value='<%=id %>' />
			<%} else {%>
				<input type='hidden' name='idBaseConhecimento' id='idBaseConhecimento' value='' />
			<%}%>

			<input type='hidden' id="idItemConfiguracaoPai" name='idItemConfiguracaoPai'/>
			<input type='hidden' id="idBrowserName" name='idBrowserName'/>
			<input type='hidden' id="idGrupoItemConfiguracao" name='idGrupoItemConfiguracao'/>
			<input type='hidden' id="idGrupoItemConfiguracaoPai" name='idGrupoItemConfiguracaoPai'/>
			<input type='hidden' id="nomeGrupoItemConfiguracao" name='nomeGrupoItemConfiguracao'/>
			<input type='hidden' id="identificacao" name='identificacao'/>
			<input type='hidden' id="criticidade" name='criticidade'/>
			<input type='hidden' id="status" name='status'/>
			<input type='hidden' name='cboCriticidade'/>
			<input type='hidden'  name='comboStatus'/>

		</form>

		<div id="POPUP_PESQUISA" title='<fmt:message key="gerenciaConfiguracaoTree.consultaItemRelacionado"/>' style="display: none;">
			<form name='formPesquisaItemConfiguracaoRelacionado'>

				<cit:findField formName='formPesquisaItemConfiguracaoRelacionado'
				lockupName='LOOKUP_ITENSCONFIGURACAORELACIONADOS'
				id='LOOKUP_ITENSCONFIGURACAORELACIONADOS' top='0' left='0' len='550' heigth='400'
				javascriptCode='true'
				htmlCode='true' />
			</form>
		</div>

		<div id="FRAME_ATIVOS" style="overflow: hidden;" >
			<iframe id="iframeAtivos" name="iframeAtivos" width="100%" height="100%"></iframe>
		</div>

		<div id="POPUP_LEGENDA" title='<fmt:message key="gerenciaConfiguracaoTree.legenda.titulo"/>' style="display: none;">
			<table class="leg" class="margin10">
				<tr>
					<td width="10%"><div class='bAtivado wh20'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.ativado"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='bDesativado wh20'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.desativado"/></td>
				</tr>
				<tr>
					<td width="10%"><div class='bEmManutencao wh20'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.emManutencao"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='bImplantacao wh20'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.implantacao"/></td>
				</tr>
				<tr>
					<td width="10%"><div class='bHomologacao wh20'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.homologacao"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='bEmDesenvolvimento wh20'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.emDesenvolvimento"/></td>
				</tr>
				<tr>
					<td width="10%"><div class='bArquivado wh20'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.arquivado"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='bValidarItem wh20'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.validaItem"/></td>
				</tr>
				<tr>
					<td width="10%"><div class='wind'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.windows"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='linux'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.linux"/></td>
				</tr>
				<tr>
					<td width="10%"><div class='ic_crit'></div></td>
					<td width="25%"><fmt:message key="gerenciaConfiguracaoTree.legenda.icsCriticos"/></td>
					<td width="25%"></td>
					<td width="10%"></td>
					<td width="10%"></td>

				</tr>
			</table>
		</div>


		<%-- <div id="POPUP_GRUPO" title="Grupo Item Configuração" style="display: none;">
			<form name='formPesquisaGrupoItemConfiguracao'>

				<cit:findField formName='formPesquisaGrupoItemConfiguracao'
				lockupName='LOOKUP_GRUPOITENSCONFIGURACAO'
				id='LOOKUP_GRUPOITENSCONFIGURACAO' top='0' left='0' len='550' heigth='400'
				javascriptCode='true'
				htmlCode='true' />
			</form>
		</div> --%>

	</body>

</html>