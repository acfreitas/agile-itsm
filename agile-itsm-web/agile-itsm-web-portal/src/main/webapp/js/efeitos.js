/* Este arquivo contém funções para efeitos gráficos jqueryui */

// Função responsável por dar efeito tab no elemento tab do jqueryui
// Para funcionar as tabs devem estar dentro de um container com
// id="effect"
// Exemplo em http://jqueryui.com/demos/tabs/

$(function() {
	$( "#tab_container" ).tabs();
});


// Função responsável por estilizar botões e links segundo o tema jqueryui
// Para funcionar os elements devem estar dentro de um container com
// class = "demo"
// Exemplo em http://jqueryui.com/demos/button/
$(function() {
	$( "input:button, input:submit, input:reset, button", ".demo" ).button();
	// alguns estilos css são definidos aqui pois a função button
	// sobrescreve alguns estilos como color, font-size, ...
	$( "input:button, input:submit, input:reset, button", ".demo" ).css({
		'padding' : '3px 10px',
		'vertical-align' : 'middle',
		'height' : '35px',
		'color' : 'black',
		'font-size' : '12px'
	});
});

// Função que aumenta o wrap para o tamanho da tela caso ele seja menor que a mesma
$(function(){
	var window_height = $(window).height();
	var wrap_height = $("#wrap").height();
	var height = window_height > wrap_height ? window_height : wrap_height;
	
	$("#wrap").css({
		'height' : height
	});
});

// Função que permite o efeito accordion
// Exemplo em http://jqueryui.com/demos/accordion/

$(function() {
	$("#accordion").accordion();
});
