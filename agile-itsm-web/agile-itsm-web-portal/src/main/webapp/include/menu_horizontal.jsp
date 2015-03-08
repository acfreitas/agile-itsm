<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
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
		$('.abalinguas').click(function() {

			if ($("#lang").is('.hide')) {
				$("#lang").removeClass('hide').addClass('show');
			} else {
				$("#lang").removeClass('show').addClass('hide');
			}
		});

		$('#gbg4').click(function() {
			if ($("#gbd4").is('.visibilityFalse')) {
				$('#gbd4').removeClass('visibilityFalse').addClass('visibilityTrue');
			} else {
				$('#gbd4').removeClass('visibilityTrue').addClass('visibilityFalse');
			}
		});
		function hidden(){
			$('#gbd4').removeClass('visibilityTrue').addClass('visibilityFalse')
		}

		$('body').click(function(e){
			if(!$(e.target).hasClass('TRUE')) {
				$('#gbd4').removeClass('visibilityTrue').addClass('visibilityFalse');
			}
		});


		var altura = $(window).height()-140;
		$("#main_container").css("height", altura);
	});

	function internacionalizar(parametro){
		document.getElementById('locale').value = parametro;
		document.formInternacionaliza.fireEvent('internacionaliza');
	}
</script>

<script type="text/javascript"	src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/lookup/jquery.ui.lookup.js"></script>

<div id='menusT' style="width: 100%; display: block; float: left; letter-spacing: 0px; margin: 0px 0px 10px;">
	<div class="navbar main hidden-print">
		<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
	</div>
</div>
