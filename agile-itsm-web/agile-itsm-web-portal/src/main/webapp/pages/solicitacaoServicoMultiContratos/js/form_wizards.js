/* ==========================================================
 * ErgoAdmin v1.2
 * form_wizards.js
 * 
 * http://www.mosaicpro.biz
 * Copyright MosaicPro
 *
 * Built exclusively for sale @Envato Marketplaces
 * ========================================================== */ 

$(function()
{
	var bWizardTabClass = '';
	$('.wizard').each(function()
	{
		if ($(this).is('#rootwizard'))
			bWizardTabClass = 'nav nav-tabs';
		else
			bWizardTabClass = '';

		var wiz = $('#rootwizard');
		
		$(this).bootstrapWizard(
		{
			onNext: function(tab, navigation, index) 
			{
				/*
				 * Somente exibe alertas de edição caso o usuário possa editar os campos
				 * @author thyen.chang
				 * 23/12/14
				 */
				if(document.getElementById("parametroEditar").value === "S"){
					if(index==1)
					{
						// Valida��o de contrato
						if(!wiz.find('#idContrato').val()) {
							alert(i18n_message("solicitacaoservico.validacao.contrato"));
							wiz.find('#idContrato').focus();
							return false;
						}
					} else if (index==2) { // Validando os dados do solicitante
						if(!wiz.find("#idOrigem").val()) {
							alert(i18n_message("citcorpore.comum.origem") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
							wiz.find('#idOrigem').focus();
							return false;
						} else if(!wiz.find("#solicitante").val()) {
							alert(i18n_message("solicitacaoServico.solicitante") + ": "	+ i18n_message("citcorpore.comum.campo_obrigatorio"));
							wiz.find('#solicitante').focus();
							return false;						
						} else if(!wiz.find("#emailcontato").val()) {
							alert(i18n_message("citcorpore.comum.email") + ": "	+ i18n_message("citcorpore.comum.campo_obrigatorio"));
							wiz.find('#emailcontato').focus();
							return false;
						} else if(wiz.find("#emailcontato").val()!=''){
							var email = wiz.find("#emailcontato").val();
							if (email != '') {
								if (!/\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}\b/.test(email) ) {
									alert(i18n_message("citcorpore.validacao.emailInvalido"));
									wiz.find('#emailcontato').focus();
									return false;
								}
							}						
						} else if(!wiz.find("#idUnidade").val()) {
							alert(i18n_message("unidade.unidade") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
							wiz.find('#idUnidade').focus();
							return false;						
						}
					} else if (index==3) {
						
						if(!wiz.find("#idTipoDemandaServico").val()) {
							alert(i18n_message("solicitacaoServico.tipo") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
							wiz.find('#idTipoDemandaServico').focus();
							return false;
						} else if(!wiz.find("#servicoBusca").val()) {
							alert(i18n_message("citcorpore.comum.servico") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
							wiz.find('#servicoBusca').focus();
							return false;						
						} else if(!wiz.find("#descricao").val()) {
							alert(i18n_message("solicitacaoServico.descricao") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
							wiz.find('#descricao').data("wysihtml5").editor.focus();
							return false;						
						} else if(!wiz.find("#urgencia").val()) {
							alert(i18n_message("solicitacaoServico.urgencia") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
							wiz.find('#urgencia').focus();
							return false;						
						}  else if(!wiz.find("#impacto").val()) {
							alert(i18n_message("solicitacaoServico.impacto") + ": "	+ i18n_message("citcorpore.comum.campo_obrigatorio"));
							wiz.find('#impacto').focus();
							return false;						
						}
						/*M�rio J�nior 03/12/2013
						Se existir template, validar antes de passar pro pr�ximo.*/
						var validaTemplate = validarInformacoesComplementares();
						 if (!validaTemplate) {
							 return false;
						 }
					}
				}
			}, 
			onLast: function(tab, navigation, index) 
			{
				// Make sure we entered the title
				/*if(!wiz.find('#inputTitle').val()) {
					alert('You must enter the product title');
					wiz.find('#inputTitle').focus();
					return false;
				}*/
			}, 
			/*onTabClick: function(tab, navigation, index) 
			{
				if(!wiz.find('#idContrato').val()) { // Valida��o de contrato
					alert(i18n_message("solicitacaoservico.validacao.contrato"));
					wiz.find('#idContrato').focus();
					return false;
				} else if(!wiz.find("#idOrigem").val()) { // Validando os dados do solicitante
					alert(i18n_message("citcorpore.comum.origem") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
					wiz.find('#idOrigem').focus();
					return false;
				} else if(!wiz.find("#solicitante").val()) {
					alert(i18n_message("solicitacaoServico.solicitante") + ": "	+ i18n_message("citcorpore.comum.campo_obrigatorio"));
					wiz.find('#solicitante').focus();
					return false;						
				} else if(!wiz.find("#emailcontato").val()) {
					alert(i18n_message("citcorpore.comum.email") + ": "	+ i18n_message("citcorpore.comum.campo_obrigatorio"));
					wiz.find('#emailcontato').focus();
					return false;						
				} else if(!wiz.find("#idUnidade").val()) {
					alert(i18n_message("unidade.unidade") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
					wiz.find('#idUnidade').focus();
					return false;						
				} else if(!wiz.find("#idTipoDemandaServico").val()) { // Validando os dados da solicita��o
					alert(i18n_message("solicitacaoServico.tipo") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
					wiz.find('#idTipoDemandaServico').focus();
					return false;
				} else if(!wiz.find("#servicoBusca").val()) {
					alert(i18n_message("citcorpore.comum.servico") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
					wiz.find('#servicoBusca').focus();
					return false;						
				} else if(!wiz.find("#descricao").val()) {
					alert(i18n_message("solicitacaoServico.descricao") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
					wiz.find('#descricao').focus();
					return false;						
				} else if(!wiz.find("#urgencia").val()) {
					alert(i18n_message("solicitacaoServico.urgencia") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
					wiz.find('#urgencia').focus();
					return false;						
				}  else if(!wiz.find("#impacto").val()) {
					alert(i18n_message("solicitacaoServico.impacto") + ": "	+ i18n_message("citcorpore.comum.campo_obrigatorio"));
					wiz.find('#impacto').focus();
					return false;						
				} 
			},*/
			onTabShow: function(tab, navigation, index) 
			{
				var $total = navigation.find('li:not(.status)').length;
				var $current = index+1;
				var $percent = ($current/$total) * 100;
				
				if (wiz.find('.bar').length)
				{
					wiz.find('.bar').css({width:$percent+'%'});
					wiz.find('.bar')
						.find('.step-current').html($current)
						.parent().find('.steps-total').html($total)
						.parent().find('.steps-percent').html(Math.round($percent) + "%");
				}
				
				// update status
				if (wiz.find('.step-current').length) wiz.find('.step-current').html($current);
				if (wiz.find('.steps-total').length) wiz.find('.steps-total').html($total);
				if (wiz.find('.steps-complete').length) wiz.find('.steps-complete').html(($current-1));
				
				// mark all previous tabs as complete
				navigation.find('li:not(.status)').removeClass('primary');
				navigation.find('li:not(.status):lt('+($current-1)+')').addClass('primary');
	
				// If it's the last tab then hide the last button and show the finish instead
				if($current >= $total) {
					wiz.find('.pagination .next').hide();
					wiz.find('.pagination .finish').show();
					wiz.find('.pagination .finish').removeClass('disabled');
				} else {
					wiz.find('.pagination .next').show();
					wiz.find('.pagination .finish').hide();
				}
				
				/*Elevando o scrool ao topo da p�gina*/
				$('html, body').animate({ 
					scrollTop: 0
				}, 500);
			},
			tabClass: bWizardTabClass,
			nextSelector: '.next', 
			previousSelector: '.previous',
			firstSelector: '.first', 
			lastSelector: '.last'
		});
	});
	
	$('#idContrato').on('change', function() {
		if($('#idContrato').val()) {
			$('.wizard').each(function()
			{
				if ($(this).is('#rootwizard'))
					bWizardTabClass = 'nav nav-tabs';
				else
					bWizardTabClass = '';

				var wiz = $(this);
				$(this).bootstrapWizard('show', 1);
			});
		}
	});
});