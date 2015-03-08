/* Este arquivo cont�m fun��es para efeitos gr�ficos jqueryui */

// Fun��o respons�vel por dar efeito tab no elemento tab do jqueryui
// Para funcionar as tabs devem estar dentro de um container com
// id="effect"
// Exemplo em http://jqueryui.com/demos/tabs/

$(function() {
	$( "#tab_container" ).tabs();
});


// Fun��o respons�vel por estilizar bot�es e links segundo o tema jqueryui
// Para funcionar os elements devem estar dentro de um container com
// class = "demo"
// Exemplo em http://jqueryui.com/demos/button/
$(function() {
	$( "input:button, input:submit, input:reset, button", ".demo" ).button();
	// alguns estilos css s�o definidos aqui pois a fun��o button
	// sobrescreve alguns estilos como color, font-size, ...
	$( "input:button, input:submit, input:reset, button", ".demo" ).css({
		'padding' : '3px 10px',
		'vertical-align' : 'middle',
		'height' : '35px',
		'color' : 'black',
		'font-size' : '12px'
	});
});

// Fun��o que aumenta o wrap para o tamanho da tela caso ele seja menor que a mesma
$(function(){
	var window_height = $(window).height();
	var wrap_height = $("#wrap").height();
	var height = window_height > wrap_height ? window_height : wrap_height;
	
	$("#wrap").css({
		'height' : height
	});
});

// Fun��o que permite o efeito accordion
// Exemplo em http://jqueryui.com/demos/accordion/

$(function() {
	$("#accordion").accordion();
});
