<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<c:set var="locale" value="${fn.toLowerCase(sessionScope.locale)}" scope="request" />

<c:if test="${empty locale}">
	<c:set var="locale" value="pt" scope="request" />
</c:if>

<c:set var="waitingWindowMessage">
	<fmt:message key="citcorpore.comum.aguardeProcessando" />
</c:set>

<script type="text/javascript">
	var URL_INITIAL = "${ctx}/";
	var URL_SISTEMA = "${ctx}/";
	var LOCALE_SISTEMA = "${locale}";
	var ctx ="${ctx}";
	var locale = "${locale}";
</script>


<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/reset.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/fonts.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/fancybox/jquery.fancybox-1.3.4.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/tinyeditor/style.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/slidernav/slidernav.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/syntax_highlighter/styles/shCore.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/syntax_highlighter/styles/shThemeDefault.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/uitotop/css/ui.totop.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/fullcalendar/fullcalendar.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/isotope/isotope.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/elfinder/css/elfinder.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/tiptip/tipTip.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/uniform/css/uniform.aristo.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/multiselect/css/ui.multiselect.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/selectbox/jquery.selectBox.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/colorpicker/css/colorpicker.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/text.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/grid.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css">
<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jQueryGantt/css/style.css" />

<link href="${ctx}/novoLayout/common/theme/css/menu.css" rel="stylesheet" />

<!-- Bootstrap -->
<link href="${ctx}/novoLayout/common/bootstrap/css/bootstrap.css" rel="stylesheet" />

<link href="${ctx}/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />

<!-- Glyphicons Font Icons -->
<link href="${ctx}/novoLayout/common/theme/css/glyphicons.css" rel="stylesheet" />

<script type="text/javascript" src="${ctx}/template_new/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jqueryTreeview/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/touchPunch/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/uitotop/js/jquery.ui.totop.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/uniform/jquery.uniform.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/autogrow/jquery.autogrowtextarea.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/multiselect/js/ui.multiselect.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/selectbox/jquery.selectBox.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/colorpicker/js/colorpicker.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/tiptip/jquery.tipTip.minified.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/custom/ui.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/custom/forms.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jQueryGantt/js/jquery.fn.gantt.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/jquery/jquery.maskedinput.js"></script>

<script type="text/javascript" src="${ctx}/js/i18n/messages_${locale}.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/js/funcoesDeUsoComum.js"></script>

<!-- Datepicker + Internacionalização + Inicialização -->
<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker-locale.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker-init.js"></script>

<script type="text/javascript">
$('.openHistorico').click(function(e){
	$('#produto').css('margin-top','20px');
	$('#historico').show();
});

function buscaHistoricoPorVersao() {
	document.formSobre.fireEvent("buscaHistoricoPorVersao");
}

(function(){
	/*
	 * Rodrigo Pecci Acorse - 02/12/2013 09h00 - #125898
	 * Adiciona uma mensagem informando que o editor de texto não pode ser renderizado se o usuário estiver utilizando Internet Explorer.
	 */
	if ( typeof oFCKeditor !== 'undefined' && !oFCKeditor._IsCompatibleBrowser() ) {
		var instance = oFCKeditor.InstanceName;
		$('#' + instance).after('<div style="padding:5px;background-color:#EEDD82;">' + i18n_message("citcorpore.comum.editorNaoRenderizado") + '</div>');
	}
})();
</script>

<%
if(request.getSession(true).getAttribute("permissaoBotao") != null) {
	out.print(request.getSession(true).getAttribute("permissaoBotao").toString());
}
%>

<link rel="shortcut icon" href="${ctx}/novoLayout/common/include/images/favicon.ico" />
