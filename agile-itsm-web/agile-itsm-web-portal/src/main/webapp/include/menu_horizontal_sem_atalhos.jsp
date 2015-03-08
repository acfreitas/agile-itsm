<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO"%>
<%@page import="br.com.centralit.citcorpore.negocio.ParametroCorporeService"%>
<%@page import="br.com.centralit.citcorpore.negocio.UsuarioService"%>
<%@page import="br.com.citframework.service.ServiceLocator"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@page import="br.com.centralit.citcorpore.integracao.ad.LDAPUtils" %>
<%@page import="br.com.centralit.citcorpore.bean.LoginDTO" %>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<link rel="stylesheet" type="text/css"	href="${ctx}/js/themes/gray/easyui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-easy.css" />
<script type="text/javascript">
	$(document).ready(function() {

		$(".menulinguas").hide();
		$('.abalinguas').click(function() {
			$(".menulinguas").animate({
				width : 'toggle'
			});
		});
		var altura = $(window).height()-140;
		$("#main_container").css("height", altura);
	});
</script>
<script type="text/javascript"	src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/lookup/jquery.ui.lookup.js"></script>
<div class="panel-header" style="letter-spacing: 0;">
	<div class="panel-title">
		<table>
			<tbody>
				<tr>
					<td><img src="/citsmart/imagens/homeicon.png"></td>
					<td>Menu Principal</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<div id='menusT' style="width: 100%; display: block; float: left; letter-spacing: 0px; margin: 0px 0px 10px;">
	<div id='menuTopo' data-options="region:'north',split:true" title="" class="easyui-layout ui-corner-all">
		<m:menu rapido="N" />
	</div>

	<div id="linguas">
		<div class="abalinguas"><img title="Selecione o Idioma" class="tooltip_left" src="${ctx}/template_new/images/icons/small/white/globe_2.png"></div>
		<div class="menulinguas">
			<img title="Português" class="tooltip_top" src="${ctx}/template_new/images/brazil_flag.png">
			<img title="English" class="tooltip_top" src="${ctx}/template_new/images/united_states_flag.png">
		</div>
	</div>
</div>