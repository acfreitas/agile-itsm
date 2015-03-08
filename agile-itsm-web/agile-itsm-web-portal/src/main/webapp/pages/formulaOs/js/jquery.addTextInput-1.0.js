/**
 * @author: renato.jesus Adiciona o texto(text) definido ao elemento alvo
 *          (target)
 */
(function($) {
	$.fn.addTextInput = function(options) {
		var settings = {
			target : '',
			text : 'DEFINIR O TEXTO'
		};

		settings = $.extend(settings, options);

		return this.each(function() {
					var $this = $(this), target = settings.target, text = settings.text;

					$this
							.on(
									'click',
									function() {
										var posCursor = 0;
										var textTarget = target.val();

										if (target.prop('selectionStart') != null
												&& target
														.prop('selectionStart') != '') {
											posCursor = target
													.prop('selectionStart');
										} else if (document.selection) {
											target.focus();
											var sel = document.selection
													.createRange();
											var selLength = document.selection
													.createRange().text.length;
											sel.moveStart('character', -(target
													.val()).length);
											posCursor = sel.text.length
													- selLength;
										}

										target.val(textTarget.substring(0,
												posCursor)
												+ text
												+ textTarget
														.substring(posCursor));
									});
				});
	};
	
	
})(jQuery);
