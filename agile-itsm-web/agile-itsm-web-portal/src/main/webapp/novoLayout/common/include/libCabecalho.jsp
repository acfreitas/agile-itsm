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

<!-- Menu -->
<link href="${ctx}/novoLayout/common/theme/css/menu.css" rel="stylesheet" />

<!-- -boottstrap -->
<link href="${ctx}/novoLayout/common/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/novoLayout/common/bootstrap/css/responsive.min.css" rel="stylesheet" />
<link href="${ctx}/novoLayout/common/bootstrap/css/bootstrap-modal.min.css" rel="stylesheet" />

<!-- Glyphicons Font Icons -->
<link href="${ctx}/novoLayout/common/theme/css/glyphicons.min.css" rel="stylesheet" />

<!-- Uniform Pretty Checkboxes -->
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/forms/pixelmatrix-uniform/css/uniform.default.min.css" rel="stylesheet" />

<!-- Bootstrap Extended -->
<link href="${ctx}/novoLayout/common/bootstrap/extend/jasny-bootstrap/css/jasny-bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/novoLayout/common/bootstrap/extend/jasny-bootstrap/css/jasny-bootstrap-responsive.min.css" rel="stylesheet">
<link href="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-wysihtml5/css/bootstrap-wysihtml5-0.0.2.css" rel="stylesheet">
<link href="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-select/bootstrap-select.css" rel="stylesheet" />
<link href="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-toggle-buttons/static/stylesheets/bootstrap-toggle-buttons.css" rel="stylesheet" />

<!-- Select2 Plugin -->
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/forms/select2/select2.min.css" rel="stylesheet" />
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/forms/select2/select2-bootstrap.css" rel="stylesheet" />

<!-- JQueryUI -->
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/system/jquery-ui/css/smoothness/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" />

<!-- MiniColors ColorPicker Plugin -->
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/color/jquery-miniColors/jquery.miniColors.css" rel="stylesheet" />

<!-- Notyfy Notifications Plugin -->
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/notifications/notyfy/jquery.notyfy.css" rel="stylesheet" />
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/notifications/notyfy/themes/default.css" rel="stylesheet" />

<!-- Gritter Notifications Plugin -->
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/notifications/Gritter/css/jquery.gritter.css" rel="stylesheet" />

<!-- Easy-pie Plugin -->
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/charts/easy-pie/jquery.easy-pie-chart.css" rel="stylesheet" />

<!-- Google Code Prettify Plugin -->
<link href="${ctx}/novoLayout/common/theme/scripts/plugins/other/google-code-prettify/prettify.css" rel="stylesheet" />

<!-- Bootstrap Image Gallery -->
<link href="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-image-gallery/css/bootstrap-image-gallery.min.css" rel="stylesheet" />

<!-- Main Theme Stylesheet :: CSS -->
<link href="${ctx}/novoLayout/common/theme/css/style-light.min.css" rel="stylesheet" />

<link rel="shortcut icon" href="${ctx}/novoLayout/common/include/images/favicon.ico" />

<script type="text/javascript" src="${ctx}/js/defines.js"></script>
<script type="text/javascript" src="${ctx}/js/Temporizador.js"></script>
<script type="text/javascript" src="${ctx}/js/tabber.js"></script>
<script type="text/javascript" src="${ctx}/js/LookupFind.js"></script>
<script type="text/javascript" src="${ctx}/js/ObjectUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/DateTimeUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/StringUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/NumberUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/AjaxUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/HTMLUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/FormatUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/Thread.js"></script>

<script type="text/javascript" src="${ctx}/js/i18n/messages_${locale}.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}/js/funcoesDeUsoComum.js"></script>

<!-- LESS.js Library -->
<script src="${ctx}/novoLayout/common/theme/scripts/plugins/system/less.min.js"></script>
<!--
Motido: Adaptação ao layout antigo
Autor: flavio.santana
 -->
<style>
	#sobre-container { display: -webkit-box;-webkit-box-orient: horizontal;display: -moz-box;-moz-box-orient: horizontal;display: box;box-orient: horizontal;margin-top: 10px;}
	#sobre-container h2, #versao-container h2 {font-size: 1.3em;margin-bottom: 0.4em;}
	#versao-container {margin-top: 30px;}
	#produto-descricao{margin-left: 10px;}
	#produto-container {line-height: 1.8em;margin-top: 100px;}
	#historico{display: none;}
</style>
