var countAtributo = 0;

function addLinhaTabelaAtributosLdap(id, atributoLdap, valorAtributoLdap){
	var tbl = document.getElementById('tabelaAtributosLdap');

	$('#tabelaAtributosLdap').show();

	var lastRow = tbl.rows.length;

	countAtributo++;

	var row = tbl.insertRow(lastRow);

	var coluna = row.insertCell(0);

	coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributo' + countAtributo + '" name="idAtributoLdap" value="' + id + '" />' + atributoLdap;

	coluna = row.insertCell(1);
	if(countAtributo == 6){
		coluna.innerHTML = '<input style="width: 100%;  border: 0 none;" type="password" onblur="validarQuantidade(' + countAtributo + ');" id="valorAtributoLdap' + countAtributo + '" name="valorAtributoLdap" value="' + valorAtributoLdap + '"/>'
		+'<input readonly="readonly" style="width: 100%;display: none;" id="quantidadeParametro' + countAtributo + '" name="quantidadeParametro" value="' + valorAtributoLdap.split(";").length + '" />';
	}else{
	coluna.innerHTML = '<input style="width: 100%;  border: 0 none;" type="text" onblur="validarQuantidade(' + countAtributo + ');" id="valorAtributoLdap' + countAtributo + '" name="valorAtributoLdap" value="' + valorAtributoLdap + '"/>'
	+'<input readonly="readonly" style="width: 100%;display: none;" id="quantidadeParametro' + countAtributo + '" name="quantidadeParametro" value="' + valorAtributoLdap.split(";").length + '" />';
	}
	}

function deleteAllRowsTabelaAtributosLdap() {
	var tabela = document.getElementById('tabelaAtributosLdap');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
}

function serializaAtributosLdap() {
	var tabela = document.getElementById('tabelaAtributosLdap');
	var count = countAtributo + 1;
	var listAtributos = [];

	for ( var i = 1; i < count; i++) {
		if (document.getElementById('idAtributo' + i) != "" && document.getElementById('idAtributo' + i) != null) {
			var idAtributo = document.getElementById('idAtributo' + i).value;

			var valorAtributo = $('#valorAtributoLdap' + i).val()

			var ldapDto = new LdapDTO(idAtributo, valorAtributo);

			listAtributos.push(ldapDto);
		}
	}
	document.form.listAtributoLdapSerializado.value = ObjectUtils.serializeObjects(listAtributos);
}

function LdapDTO(idAtributo, valorAtributo){
	this.idAtributoLdap = idAtributo;
		this.valorAtributoLdap = valorAtributo;
	}

function gravar(){
	 var campo1 = document.getElementById("valorAtributoLdap1").value.split(";").length;
	 var campo2 = document.getElementById("valorAtributoLdap2").value.split(";").length;
	 var campo3 = document.getElementById("valorAtributoLdap3").value.split("&").length;
	 var campo4 = document.getElementById("valorAtributoLdap4").value.split(";").length;
	 var campo5 = document.getElementById("valorAtributoLdap5").value.split(";").length;
	 var campo6 = document.getElementById("valorAtributoLdap6").value.split(";").length;
	 var campo7 = document.getElementById("valorAtributoLdap7").value.split(";").length;
	 var campo8 = document.getElementById("valorAtributoLdap8").value.split(";").length;
	 var campo9 = document.getElementById("valorAtributoLdap9").value.split(";").length;
	 var campo10 = document.getElementById("valorAtributoLdap10").value.split(";").length;
	 var campo11 = document.getElementById("valorAtributoLdap11").value.split(";").length;
	 var campo12 = document.getElementById("valorAtributoLdap12").value.split(";").length;

		if(campo1 == campo2 && campo1 == campo3 && campo1 == campo4 && campo1 == campo5 && campo1 == campo6 && campo1 == campo7  && campo1 == campo8  && campo1 == campo10  && campo1 == campo11 && campo1 == campo12){
			 //if(campo1 == campo2 && campo1 == campo3 && campo1 == campo4 && campo1 == campo5 ){
				 if((campo7 == 1 || campo1 == campo7) && (campo8 == 1 || campo1 == campo8) && (campo10 == 1 || campo1 == campo10) && (campo11 == 1 || campo1 == campo11) && (campo12 == 1 || campo1 == campo12)){
			 serializaAtributosLdap();
				document.form.save();
		 }
		 else{
			 alert(i18n_message("ldap.quantidadeparametros"));
		 }
	 }else{
		 alert(i18n_message("ldap.quantidadeparametros"));
	 }
}

function testarConexao() {
	JANELA_AGUARDE_MENU.show();
	document.form.fireEvent("testarConexao");
}

function atualizar(){
	document.form.fireEvent("load");
}
function sincronizaLDAP() {
	if(confirm(i18n_message("ldap.desejaSincronizarLDAP"))) {
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("sincronizaLDAP");
	}
}

 function validarQuantidade(nomeCampo) {
	 var campoInicial = document.getElementById("valorAtributoLdap1").value.split(";");
		 if(nomeCampo==3){
			 var quantidade = document.getElementById("valorAtributoLdap"+nomeCampo).value.split("&");
	 } else {
		var quantidade = document.getElementById("valorAtributoLdap"+nomeCampo).value.split(";");
	 }
	 var quantidade = document.getElementById("valorAtributoLdap"+nomeCampo).value.split(";");

	 var primeiroValor = campoInicial.length;
	 var segundoValor = quantidade.length;
	 if(primeiroValor == segundoValor){
		 document.getElementById("quantidadeParametro"+nomeCampo).style.color="black";
		 document.getElementById("valorAtributoLdap"+nomeCampo).style.color="black";
	 }else{
		 if(nomeCampo != 8  && nomeCampo != 12  && nomeCampo != 13){
			 document.getElementById("quantidadeParametro"+nomeCampo).style.color="red";
			 document.getElementById("valorAtributoLdap"+nomeCampo).style.color="red";
		 }
		 else{
			 var valorCampo = document.getElementById("valorAtributoLdap"+nomeCampo).value.trim();
			 if(valorCampo != "S" && valorCampo != "s" && valorCampo != "N" && valorCampo != "n" && valorCampo != ""){
				 document.getElementById("quantidadeParametro"+nomeCampo).style.color="red";
				 document.getElementById("valorAtributoLdap"+nomeCampo).style.color="red";
				 document.getElementById("quantidadeParametro"+nomeCampo).value="";
				 document.getElementById("valorAtributoLdap"+nomeCampo).value="";
				 alert("Parametro incorreto!");
			 }
			 document.getElementById("quantidadeParametro"+nomeCampo).style.color="black";
			 document.getElementById("valorAtributoLdap"+nomeCampo).style.color="black";
		 }
	 }
	 document.getElementById("quantidadeParametro"+nomeCampo).value = quantidade.length;
}


