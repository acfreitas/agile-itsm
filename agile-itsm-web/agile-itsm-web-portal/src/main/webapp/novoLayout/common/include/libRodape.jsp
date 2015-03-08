<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<!-- JQuery -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/system/jquery.min.js"></script>

<!-- JQueryUI -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/system/jquery-ui/js/jquery-ui-1.9.2.custom.min.js"></script>

<!-- JQueryUI Touch Punch -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/system/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>

<!-- Modernizr -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/system/modernizr.js"></script>

<!-- Bootstrap -->
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/js/bootstrap.js"></script>

<!-- SlimScroll Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/other/jquery-slimScroll/jquery.slimscroll.min.js"></script>

<!-- Common Demo Script -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/demo/common.js?1374758914"></script>

<!-- Holder Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/other/holder/holder.js"></script>

<!-- Uniform Forms Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/forms/pixelmatrix-uniform/jquery.uniform.min.js"></script>

<!-- Mask  -->
<script type="text/javascript" src="${ctx}/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>

<!-- Global -->
<!--[if !IE]><!-->
<script type="text/javascript">
if (/*@cc_on!@*/false) {
	document.documentElement.className+=' ie10';
}
var basePath = '${ctx}/novoLayout/common/';
</script>
<!--<![endif]-->

<!-- Bootstrap Extended -->
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-select/bootstrap-select.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-toggle-buttons/static/js/jquery.toggle.buttons.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/extend/jasny-bootstrap/js/jasny-bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/extend/jasny-bootstrap/js/bootstrap-fileupload.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/extend/bootbox.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-wysihtml5/js/wysihtml5-0.3.0_rc2.min.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/extend/bootstrap-wysihtml5/js/bootstrap-wysihtml5-0.0.2.js"></script>

<!-- Google Code Prettify -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/other/google-code-prettify/prettify.js"></script>

<!-- Gritter Notifications Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/notifications/Gritter/js/jquery.gritter.min.js"></script>

<!-- Notyfy Notifications Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/notifications/notyfy/jquery.notyfy.js"></script>

<!-- MiniColors Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/color/jquery-miniColors/jquery.miniColors.js"></script>

<!-- Cookie Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/system/jquery.cookie.js"></script>

<!-- Ba-Resize Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/other/jquery.ba-resize.js"></script>

<!--  Flot Charts Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.pie.min.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.tooltip.min.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.selection.min.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.resize.min.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.orderBars.js"></script>

<!-- Autocomplete -->
<script type="text/javascript" src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>

<!-- Select2 Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/forms/select2/select2.js"></script>

<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/js/bootstrap-modalmanager.js"></script>
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/js/bootstrap-modal.js"></script>

<!-- Datepicker + Internacionalização + Inicialização -->
<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker-locale.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ui.datepicker-init.js"></script>

<script type="text/javascript">
	$(function() {
		/*
		Motivo: Funções de atualização do citsmart sobre
		Autor: flavio.santana
		31/10/2013 15:16
		*/
		$('#divRelease').slimScroll({ scrollTo: '0', height: '150px' });

		$('#historico').hide();

		$('.openHistorico').click(function(e){
			$('#produto').css('margin-top','20px');
			$('#historico').show();
		});

		$('.titulo').tooltip({placement: "auto"});

		//Faz o focus no primeiro elemento do dropdown que foi clicado.
		$('[data-toggle="dropdown"]').on('click', function () {
			var re = $(this).attr('re');
			if (re != '') {
				setTimeout(function(){ $('.' + re).find('input[type="text"], select, textarea').first().focus(); },0);
			}
		});

		var primaryColor = '#8ec657',
		dangerColor = '#b55151',
		successColor = '#609450',
		warningColor = '#ab7a4b',
		inverseColor = '#45484d';

	});

	//imprime no console qualquer erro de javascript no sistema
	onerror=handleErr;
	function handleErr(msg,url,l)
	{
		var txt;
		txt+=" Erro: " + msg + " - ";
		txt+="URL: " + url + " - ";
		txt+="Linha: " + l;
		console.log(txt);
		return true;
	}

	/*
	 * Rodrigo Pecci Acorse - 31/03/2014 15h20
	 * Marca como selected todos os options do select informado.
	 * Utilizado para select multiplos que precisam de todos os options selecionados ao fazer o post.
	 * Neste caso, todos os itens serão enviados para o DTO se ele estiver esperando um array.
	 *
	 */
	function markAllOptionsSelectedForSelectMultiple(element) {
		$("#" + element + " option").prop('selected', true);
	}
</script>

<!-- Bootstrap Form Wizard Plugin -->
<script type="text/javascript" src="${ctx}/novoLayout/common/bootstrap/extend/twitter-bootstrap-wizard/jquery.bootstrap.wizard.js"></script>

<!--[if IE]><!--><script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/other/excanvas/excanvas.compiled.js"></script><!--<![endif]-->
<!--[if lt IE 8]><!--><script type="text/javascript" src="${ctx}/novoLayout/common/theme/scripts/plugins/other/json2.compiled.js"></script><!--<![endif]-->
