jQuery(function($){
	/*
	 * Seta o datepicker para os inputs com as classes .citdatepicker e .datepicker
	 */
	$('.citdatepicker').datepicker();
	$('.datepicker').datepicker();
	$('.dataNascimento').datepicker();
	$('.dtpicker').datepicker();
	$('.date').datepicker();

	/*
	 * Passar por todos os inputs com as classes .citdatepicker e .datepicker e adicionar as classes de formatação e validação do framework se não existirem
	 */
	$('.citdatepicker, .datepicker, .dataNascimento, .dtpicker, .date').each(function(e) {
		if (!$(this).hasClass('Format[Date]')) $(this).addClass('Format[Date]');
		if (!$(this).hasClass('Valid[Date]')) $(this).addClass('Valid[Date]');
	});
});