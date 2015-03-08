<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.util.FiltroSegurancaCITSmart" %>

<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css">
<link href="${ctx}/novoLayout/common/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />

<script type="text/javascript" src="${ctx}/template_new/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jqueryTreeview/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jquery/jquery.maskedinput.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/touchPunch/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/tiptip/jquery.tipTip.minified.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/colorpicker/js/colorpicker.js"></script>

<script type="text/javascript" src="${ctx}/template_new/js/isotope/jquery.isotope.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/custom/gallery.js"></script>

<% if(!FiltroSegurancaCITSmart.getHaVersoesSemValidacao()) { %>
<script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
<%} %>

<script type="text/javascript" src="${ctx}/dwr/util.js"></script>
<script type="text/javascript">
	function tratarEnter (field, event) {
		var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
		if (keyCode == 13) {
			var i;
			for (i = 0; i < field.form.elements.length; i++)
				if (field == field.form.elements[i])
					break;
			i = (i + 1) % field.form.elements.length;
			field.form.elements[i].focus();
			return false;
		}
		return true;
	}
	/*
	* Motivo: Corrigindo erros de scripts
	* Autor: flavio.santana
	* Data/Hora: 04/11/2013 16:19
	*/
	function resize_iframe(){}

	if (window.matchMedia("screen and (-ms-high-contrast: active), (-ms-high-contrast: none)").matches) {
		 document.documentElement.className += " " + "ie10";
	}
</script>

<div id="loading_overlay">
	<div class="loading_message round_bottom">
		<img src="${ctx}/template_new/images/loading.gif" alt="aguarde..." />
	</div>
</div>

<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/js/bootstrap.min.js"></script>

<%@ include file="/pages/ctrlAsterisk/ctrlAsterisk.jsp" %>
