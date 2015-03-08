$(function() {
	 $('#datepicker').datepicker();
});

/*
 * 	Esta fun��o recebe 4 parametros.
 * 	O primeiro � o nome da linha onde a tabela ser� preenchida, a 
 * 	linha originalmente estar� invis�vel para n�o ficar espa�o 
 * 	vazio enquanto o usu�rio n�o preencher a tabela.
 *  O segundo � o nome a tabela que ser� preenchida
 *  O terceiro � a primeira c�lula que ser� preenchida 
 *  O quarto � a segunda c�lula que ser� preenchida formando 
 *  duas colunas.
 * */
var contador = 0;
function adicionar_item(linha, table_id, cell1, cell2) {
	var value1 = document.getElementById(cell1).value;
	if (value1 != ""){
		var table = document.getElementById(table_id);
		document.getElementById(linha).style.display = "table-row";
		var newRow = table.insertRow(0);
		var newCell1 = newRow.insertCell(0);
		var newCell2 = newRow.insertCell(1);

		newRow.id = contador;
	if (document.getElementById(cell1)){
		newCell1.innerHTML = value1;
		document.getElementById(cell1).value = "";
	}
	else{
		alert("o campo est� vazio");
	}

	if (document.getElementById(cell2)){
		newCell2.innerHTML = document.getElementById(cell2).value;
		document.getElementById(cell2).value = "";
	}
	else{
		newCell2.style.width = "20px";
		newCell2.innerHTML = 
			'<a href=javascript:remover_item('+
			'"'+
			linha+
			'"'+
			','+
			'"'+
			contador+
			'"'+
			')>'+
			'<img src="../../imagens/delete.png"/> </a>';
		contador = contador + 1;
		}
	}
}

/*
 * A fun��o remover_item � chamada para se remover um item adicionado numa tabela 
 * atrav�s do m�todo "adicionar_item". Ele recebe o id_linha_fora que � o id da linha
 * onde toda tabela est� inserida. E tamb�m recebe o id_linha_dentro que � o
 * id da linha que deve ser removida da tabela. O id da linha onde toda a tabela
 * est� inserida serve para que quando houver uma exclusao seja verificado se
 * ainda existe alguma linha nesta tabela, caso n�o exista � necess�rio que toda a linha
 * desapare�a para que n�o fique ocupando espa�o e destruindo o layout.
 * */
function remover_item(id_linha_fora, id_linha_dentro) {
	var row = document.getElementById(id_linha_dentro);
    var table = row.parentNode;
    while ( table && table.tagName != 'TABLE' )
        table = table.parentNode;
    if ( !table )
        return;
    table.deleteRow(row.rowIndex);
    contador = contador - 1;
    if (contador == 0){
    	document.getElementById(id_linha_fora).style.display = "none";
    }
}

/*
 * A fun��o abaixo gera um autocomplete em todos os componentes da p�gina que 
 * pertencerem � classe autocomplete_usuario. O automplete � preenchido pela 
 * vari�vel "availableTags" declarada na fun��o. No futuro essa vari�vel precisa
 * ser alimentada com um retorno de consulta do banco.  
 * */
$(function() {
	var availableTags = [ 
	                    "ALEXANDRE SOSTAG FERREIRA", 
	                    "CESAR ANTONIO SERRA MIRANDA", 
	                    "CRISTIANO ESTACIO DE SOUZA CARVALHO", 
	                    "DEOCLECIANO DE CARVALHO SANTANA", 
	                    "DIOGENES FIDEL MARCAL", 
	                    "FRANCIELLY DENISE CARNEIRO DOS SANTOS",
	                    "FELIPE FIGUEIREDO PALMIER", 
	                    "GLEYCE KELLY DE ALMEIDA MOTA", 
	                    "JANDERSON RAPHAEL SANTOS MOURA", 
	                    "JESSICA JULIANA CACHALI", 
	                    "KAIO CESAR DE SOUTO MEDEIROS",
	                    "KHATLLYEN GONCALVES DAVI", 
	                    "LUIS GUSTAVO LINO", 
	                    "LORENA GALVAO MORAIS", 
                        "MICHELLY VAZ ALVES", 
                        "MARIANE PATRICIA PEREIRA DA SILVA",
                        "NUBIA LUIZA CORREA CARVALHO", 
                        "NELSON DE PAULA SOUZA JUNIOR",
						"ODILON RODRIGUES TRIGUEIRO JUNIOR", 
						"RAFAEL CARNEIRO NAVES", 
						"ROGERIO JORGE DE ASSIS", 
						"THAIS DE LIMA SILVA"]; 
	$(".autocomplete_usuarios").autocomplete({
		source : availableTags
	});
});

$(function() {
	var availableTags = [ 
	                      "Afuncionalidade", 
	                      "Afuncionalidade", 
	                      "Afuncionalidade", 
	                      "Bfuncionalidade",
	                      "C++funcionalidade", 
	                      "Cfuncionalidade", 
	                      "Cfuncionalidade", 
	                      "Cfuncionalidade", 
	                      "Cfuncionalidade", 
	                      "Efuncionalidade",
	                      "Ffuncionalidade", 
	                      "Gfuncionalidade", 
	                      "Hfuncionalidade", 
	                      "Jfuncionalidade", 
	                      "Jfuncionalidade", 
	                      "Lfuncionalidade",
	                      "Pfuncionalidade", 
	                      "Pfuncionalidade", 
	                      "Pfuncionalidade", 
	                      "Rfuncionalidade", 
	                      "Sfuncionalidade", 
	                      "Sfuncionalidade" ];
	$(".autocomplete_funcionalidades").autocomplete({
		source : availableTags
	});
});

$(function() {
	var availableTags = [ 
	                      "Agrupo", 
	                      "Bgrupo", 
	                      "Cgrupo", 
	                      "C++grupo", 
	                      "Dgrupo",
	                      "Egrupo", 
	                      "Fgrupo", 
	                      "Ggrupo", 
	                      "Hgrupo", 
	                      "Igrupo",
	                      "Jgrupo", 
	                      "Kgrupo", 
	                      "Lgrupo", 
	                      "Mgrupo", 
	                      "Ngrupo", 
	                      "Ogrupo",
	                      "Pgrupo", 
	                      "Qgrupo", 
	                      "Rgrupo", 
	                      "Sgrupo", 
	                      "Tgrupo", 
	                      "Ugrupo" ];
	$(".autocomplete_grupos").autocomplete({
		source : availableTags
	});
});