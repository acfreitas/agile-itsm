$(function() {
	var users = [
		"Andressa",
		"Andréia",
		"Ademar",
		"Anderson",
		"Bourne",
		"Carlos",
		"Douglas",
		"João Paulo",
		"Luciana"
	];
	$( "#tags" ).autocomplete({
		source: users
	});
});

function carregarAtividade(){
	
}

/* A partir de uma linha <tr> e um formulário <form>,
 * itera pelas células da linha <tr> atribuindo aos campos de entrada
 * do formulário <input> de mesmo nome que as células, os valores das células.	
 */
function add(tr_id, form_id){
	
	var cells = document.getElementById(tr_id).cells; // recupera as células
	var inputs = document.getElementById(form_id).getElementsByTagName("INPUT"); // recupera os inputs
	
	alert(inputs.length);
	
	for(var i = 0; i < cells.length; i++) // itera pelas células da linha <tr>
	{
		for(var j = 0; j < inputs.length; j++) // itera pelos campos do formulário <input>
		{ 
			if(cells[i].name == inputs[j].name) // se tiverem o mesmo nome
			{ 
				inputs[j].value = cells[i].innerHTML; // atribui ao input o valor da célula
				break; // passa para a próxima célula
			}
		}
	}
}
 
 // exibe uma janela interativa
 function showInterativeWindow(){
	 
	 var window_width = $(window).width();
	 
	 var interative_window_width = 160;
	 var interative_window_height = 20;
	 
	 var interative_window = document.createElement("span");
	 
	 var left = (window_width - interative_window_width) / 2; 
	 
	 var style = "position:absolute;" 
	 + "top:0; " 
	 + "left:" + left + "px; "
	 + "height: " + interative_window_height + "px; "
	 + "width:" + interative_window_width + "px; "
	 + "background:#eee; text-align:center;";
	 
	 interative_window.setAttribute('style', style);
	 interative_window.setAttribute('id', 'interative_window');
	 
	 var content = document.createTextNode("Processando...");
	 
	 interative_window.appendChild(content);
	 
	 document.body.appendChild(interative_window);
 };
 
 function hideInterativeWindow(){
	 var window;
	 if(window = document.getElementById('interarive_window'))
		 window.style.display = 'none';
 }


// carrega uma atividade para cadastro
function carregar(source){

	// janela interativa
	//showInterativeWindow();
	
	// recupera a linha que contém o registro a ser carregado
	var row = source.parentNode.parentNode;
	
	document.getElementById("descricao").value = row.cells[0].innerHTML;
	
	// fecha a tab atual e abre a tab realização atividade
	document.getElementById("container_realizacao_atividade").click();
	
	document.getElementById("tags").focus();
}
