<%@page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@page import="br.com.centralit.citcorpore.bean.GrupoDTO" %>
<%@page import="br.com.centralit.citcorpore.bean.ImagemItemConfiguracaoRelacaoDTO" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<link rel="stylesheet" type="text/css" href="css/estiloMapeamentoServico.css">

<%
	String retorno = "${ctx}/pages/index/index.load";
	pageContext.setAttribute("retorno", retorno);
%>

</head>
<body>
	<div id="carregando" class="carregando" style="display: none;">
		<label>Carregando...</label>
	</div>
	<div id="divLogo" style="overflow: hidden!important;">
		<table cellpadding='0' cellspacing='0'>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<img border="0" src="${ctx}/imagens/logo/logo.png" />
				</td>
			</tr>
		</table>
	</div>
	<div id="divControleLayout" style="position: fixed;top:1%;right: 2%;z-index: 100000;float: right;display: block;">
		<table cellpadding='0' cellspacing='0'>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td>
					<button type="button" id='btnVoltar' class="light img_icon has_text" style='text-align: right; margin-left: 99%; float: right; display: block;' onclick="voltar()" title="<fmt:message key='citcorpore.comum.voltarprincipal' />">
						<img border="0" title="<fmt:message key='citcorpore.comum.voltarprincipal' />" src="${ctx}/imagens/back.png" /><span style="padding-left: 0px !important;"><fmt:message key="citcorpore.comum.voltar" /></span>
					</button>
				</td>
			</tr>
		</table>
	</div>

	<form name='form' action='${ctx}/pages/mapaDesenhoServico/mapaDesenhoServico'>
		<input type="hidden" name="idServico"  />
		<input type="hidden" name="idItemConfiguracao" />
		<input type="hidden" name="idImagemItemConfiguracao" />
		<input type="hidden" name="listaItensConfiguracaoSerializada" />
		<div id="menu">
			<menu id="itens">
				<li>
					<label class="tooltip_bottom" style="height: 40px;" title="<fmt:message key="mapaDesenhoServico.software"/>">
						<img src="imagens/software.png" id="1" draggable="true" class="itemInMenu icone" ondragstart="dragstart(event)" />
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;" title="<fmt:message key="mapaDesenhoServico.servidor"/>">
					<img src="imagens/servidor.png" id="2" draggable="true" class="itemInMenu icone" ondragstart="dragstart(event)" />
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;" title="<fmt:message key="mapaDesenhoServico.desktop"/>">
						<img src="imagens/desktop.png" id="3" draggable="true" class="itemInMenu icone" ondragstart="dragstart(event)"  />
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;" title="<fmt:message key="mapaDesenhoServico.linux"/>">
					<img src="imagens/linux.png" id="4" draggable="true" class="itemInMenu icone" ondragstart="dragstart(event)" />
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;" title="<fmt:message key="mapaDesenhoServico.windows"/>">
						<img src="imagens/windows.png" id="5" draggable="true" class="itemInMenu icone" ondragstart="dragstart(event)" />
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;" title="<fmt:message key="mapaDesenhoServico.switch"/>">
						<img src="imagens/switch.png" id="6" draggable="true" class="itemInMenu icone" ondragstart="dragstart(event)" />
					</label>
				</li>
			</menu>
			<ul id="opcoes">
				<li>
					<label id="hoverServico" class="tooltip_bottom" style="height: 18px;" title="<fmt:message key='mapaDesenhoServico.selecionarServico'/>">
						<img src="${ctx}/template_new/images/icons/large/grey/magnifying_glass.png" class="icone"  id="" onclick="togglePopup('POPUP_SERVICO', 'open');"></img>
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 18px;" title="<fmt:message key="citcorpore.comum.gravar"/>">
						<img src="${ctx}/template_new/images/icons/large/grey/cassette.png"
							 class="icone"  id="disquete"
							 onclick="mapa.salvarServico();"></img>
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 18px;" title="<fmt:message key="citcorpore.comum.gerarImpressao"/>">
						<img src="${ctx}/template_new/images/icons/large/grey/printer.png"  class="icone"  id="imprimir" onclick="mapa.imprimirDesenhoMapaServicos()"></img>
					</label>
				</li>
			</ul>
		</div>
		<div id="divNomeServico">
			<h2 id="nomeServico"></h2>
		</div>
		<div id="editarItemConfiguracao" style="width: 95%;" class="popup">
			<div id="formItemConfiguracao">
				<table border="1">
				<tr>
					<td>
						<h3 align="left"><fmt:message key='itemConfiguracao.itemConfiguracao'/></h3>
					</td>
				</tr>
					<tr>
						<td>
							<fmt:message key='itemConfiguracao.itemConfiguracao'/>:
						</td>
					</tr>
					<tr>
						<td>
							<input type="text" id="identificacao" name="identificacao" disabled="disabled" size="100%">
						</td>
						<td>
							<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" onclick="togglePopupFrame('POPUPITEMCONFIGURACAO', '${ctx}/pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load', 'open')"></img>
						</td>
					</tr>
					<tr>
						<td><fmt:message key='citcorpore.comum.descricao'/>:</td>
					</tr>
					<tr>
						<td>
							<textarea rows="3" cols="103" id="txtDescricao" name="txtDescricao" maxlength="250">
							</textarea>
						</td>
					</tr>
					<tr>
						<td><label class="msg_erro" id="msg_erro_form_item"></label></td>
					</tr>
					<tr>
						<td colspan="3">
							<button type='button' id="btSalvar" name="btSalvar" class="light">
								<img src="${ctx}/template_new/images/icons/small/grey/pencil.png" />
								<span>
									<fmt:message key="citcorpore.comum.gravar" />
								</span>
							</button>
							<button type='button' onclick="limpaFormItemConfiguracao();" class="light">
								<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png" />
								<span>
									<fmt:message key="citcorpore.comum.limpar" />
								</span>
							</button>
							<button type='button' id="btExcluir" name='btExcluir' class="light" style="display: none">
							<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
								<span>
									<fmt:message key="citcorpore.comum.excluir" />
								</span>
							</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>

	<div id="POPUP_SERVICO" style="" title="<fmt:message key="citcorpore.comum.pesquisa" />" class="popup">
		<table>
			<tr>
				<td>
					<h3 align="center"><fmt:message key="servico.servico" /></h3>
				</td>
			</tr>
		</table>

		<form name='formPesquisa' style="width: 95%;">
			<cit:findField formName='formPesquisa' lockupName='LOOKUP_SERVICO_DESENHO' id='LOOKUP_SERVICO_DESENHO' top='0' left='0' len='550' heigth='300' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<div id="POPUPITEMCONFIGURACAO" style="" title="<fmt:message key="citcorpore.comum.pesquisa" />" class="popup section">
		<iframe id="popupitemc" name="popupitemc" width="100%" height="98%"></iframe>
	</div>
	<div id="imprimir">
		<canvas id="mapaServicos" class=''></canvas>
	</div>
</body>

	<script>
		var retorno = "${retorno}";
		var iframe = "${iframe}";
	</script>

	<script type="text/javascript" src="js/jquery.growl.js"></script>
	<script type="text/javascript" src="js/mapaDesenho.js"></script>
	<script type="text/javascript" src="js/MapaDesenhoServico.js"></script>
</html>
