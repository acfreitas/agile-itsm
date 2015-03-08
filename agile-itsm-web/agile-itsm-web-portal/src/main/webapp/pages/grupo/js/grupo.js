
	var contEmpregado = 0;
	var contEmail = 0;
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function validaEmail(mail) {
		var prim = mail.value.indexOf("@");
		if(prim < 2) {
			return false;
		}
		if(mail.value.indexOf("@",prim + 1) != -1) {
		}
		if(mail.value.indexOf(".") < 1) {
			return false;
		}
		if(mail.value.indexOf(" ") != -1) {
			return false;
		}
		if(mail.value.indexOf("zipmeil.com") > 0) {
			return false;
		}
		if(mail.value.indexOf("hotmeil.com") > 0) {
			return false;
		}
		if(mail.value.indexOf(".@") > 0) {
			return false;
		}
		if(mail.value.indexOf("@.") > 0) {
			return false;
		}
		if(mail.value.indexOf(".com.br.") > 0) {
			return false;
		}
		if(mail.value.indexOf("/") > 0) {
			return false;
		}
		if(mail.value.indexOf("[") > 0) {
			return false;
		}
		if(mail.value.indexOf("]") > 0) {
			return false;
		}
		if(mail.value.indexOf("(") > 0) {
			return false;
		}
		if(mail.value.indexOf(")") > 0) {
			return false;
		}
		if(mail.value.indexOf("..") > 0) {
			return false;
		}

		return true;
}

	function LOOKUP_EMPREGADO_select(id, desc){
		document.form.iddEmpregado.value = id;
		document.form.descEmpregado.value = desc;
		document.form.fireEvent("manipulaEmpregado");
	}

	function LOOKUP_EMPREGADO_EMAIL_select(id, desc) {

		document.formEmail.idEmpregado.value = id;
		document.formEmail.fireEvent("restoreEmpregadoEmail");
	}

	function complementoRestore(){
		var nome = document.formEmail.nomeEmail.value
		var email = document.formEmail.emailExtra.value
		if(!validaEmail(document.formEmail.emailExtra)){
			alert (i18n_message("grupo.emailInvalidoColaborador"));
			document.formEmail.clear();
		return;
		}
		else{
		var id = document.formEmail.idEmpregado.value
		addLinhaTabelaEmail(id,nome,email, true );
		}
		document.formEmail.clear();
	}

	function LOOKUP_GRUPO_select(id, desc) {
		JANELA_AGUARDE_MENU.show();
		contColuna=1;
		document.form.restore({idGrupo : id});
		document.getElementById('paginas').style.display = "block";
	}
	function excluir() {
		if (document.getElementById("idGrupo").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	function gravar(){
		document.form.nome.value = document.form.nome.value.replace(/'/g,"");
		/* if (document.getElementById("serviceDesk").value == "-- Selecione --"){
			alert("Por favor, selecione se o grupo é do Service Desk");
		} */
		if (document.getElementById("serviceDesk").value == i18n_message("citcorpore.comum.selecione") ){
			alert(i18n_message("grupo.serviceDeskObrigatorio"));
		}
		else if (document.getElementById("idPerfilAcessoGrupo").value != ""){
			JANELA_AGUARDE_MENU.show();
			//so serealiza a tabela empregado quando o idGrupo não existe, por causa da paginação
			if(document.getElementById("idGrupo").value ==""){
			    serializa();
			}else{
				//Trata os elementos que estao na pagina;
				serializaAux();
				document.form.fireEvent("gravarEmail");
			}
			serializa2();

			document.form.save();
			contEmpregado = 0;
			contEmail = 0;
		}else{
			alert(i18n_message("grupo.perfilAcessoObrigatorio"))
		}
	}

	function gravarEmail(){
			var nome = document.formEmail.nomeEmail.value
			var email = document.formEmail.emailExtra.value
			if (nome == "") {
				alert (i18n_message("grupo.nomeCampoObrigatorio"));
				return;
			}
			else if (email == ""){
				alert (i18n_message("grupo.emailCampoObrigatorio"));
				return;
			}
			else if(!validaEmail(document.formEmail.emailExtra)){
				alert (i18n_message("grupo.emailInvalido"));
			document.formEmail.emailExtra.value = "";
			return;
			}
			else{
				var id = document.formEmail.idEmpregado.value
				addLinhaTabelaEmail(id,nome,email, true );
			 document.formEmail.clear();
			}

	}

	var contColuna = 1;


    /*desenvolvedor: Rafael César Soyer; data: 11/12/2014 */
	function reiniciaVarContColuna (){
		contColuna = 1;
	}

	function addLinhaTabelaEmpregado(id, desc, valida, checked) {
		var tbl = document.getElementById('tabelaEmpregado');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
		if (valida){
			if (!validaAddLinhaTabelaEmpregado(lastRow, id)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contEmpregado++;
		coluna.innerHTML = '<input class="check" type="checkbox" id="idEmpregado' + contColuna + '" name="empregadoSelecionado" value="'+id+'" style="cursor: pointer;" onclick="concatenarValoresCheckados(this);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = desc;
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input  type="checkbox" ' + (checked ? 'checked="true"' : '') + ' id="enviaEmail' + contColuna + '"  name="enviaEmail" value= "S" />';
		contColuna++;
		//+ '<input type="hidden" id="idEmpregado' + contColuna + '" name="idEmpregado" value="' + id + '" />'
		$("#POPUP_EMPREGADO").dialog("close");

	}


	function addLinhaTabelaEmail(id, descNome, descEmail, valida){
		var tbl = document.getElementById('tabelaEmail');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
	 	if (valida){
			if (!validaAddLinhaTabelaEmail(lastRow, descEmail)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contEmail++;
		coluna.innerHTML = '<img id="imgDelEmpregado' + contEmail + '" style="cursor: pointer;" title="' + i18n_message("citcorpore.comum.excluir") + '" src="' + ctx + '/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaEmail\', this.parentNode.parentNode.rowIndex,this);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = descNome + '<input type="hidden" id="idNomeEmail' + contEmail + '" name="idNomeEmail" value="' + descNome + '" />';
		coluna = row.insertCell(2);
		coluna.innerHTML = descEmail + '<input type="hidden" id="idEmail' + contEmail + '"  name="idEmail" value="' + descEmail + '" />';
		coluna = row.insertCell(3);
		if (id != "" || id != 0){
			coluna.innerHTML = i18n_message("grupo.colaborador") + '<input type = "hidden" id = "idEmpregadoEmail' + contEmail + '" name = "idEmpregadoEmail" value ="' + id + '" />';
		}
		else{
			coluna.innerHTML = i18n_message("grupo.externo") + '<input type = "hidden" id = "idEmpregadoEmail' + contEmail + '" name = "idEmpregadoEmail" value ="' + id + '" />';
		}


		$("#POPUP_EMAIL").dialog("close");
	}

	function validaAddLinhaTabelaEmpregado(lastRow, id){
		if (lastRow > 2){
			var arrayIdEmpregado = document.form.empregadoSelecionado;
			for (var i = 0; i < arrayIdEmpregado.length; i++){
				if (arrayIdEmpregado[i].value == id){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}

			}
		} else if (lastRow == 2){
			var idEmpregado = document.form.empregadoSelecionado;
			if (idEmpregado.value == id){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		return true;
	}

	function validaAddLinhaTabelaEmail(lastRow, email){
		if (lastRow > 2){
			var arrayEmail = document.form.idEmail;
			for (var i = 0; i < arrayEmail.length; i++){
				if (arrayEmail[i].value == email){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}

			}
		} else if (lastRow == 2){
			var idEmail = document.form.idEmail;
			if (idEmail.value == email){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		return true;
	}


 	function GrupoEmpregadoDTO(_id, i){
 		if  ($('#enviaEmail' + i).is(":checked")){
			this.enviaEmail = "S";
		}else{
			this.enviaEmail = "N";
		}
 		this.idEmpregado = _id;
 	}

 	function GrupoEmailDTO(_id,i){
			this.email = _id;
			this.nome = document.getElementById('idNomeEmail' + i).value;
			this.idEmpregado = document.getElementById('idEmpregadoEmail' + i).value;
 	}

 	function serializa(){
 		var tabela = document.getElementById('tabelaEmpregado');
 		var count = contEmpregado + 1;
 		var listaDeEmpregados = [];
 		for(var i = 1; i < count ; i++){
 			if (document.getElementById('idEmpregado' + i) != "" && document.getElementById('idEmpregado' + i) != null){
 			var trObj = document.getElementById('idEmpregado' + i).value;
 			var empregado = new GrupoEmpregadoDTO(trObj, i);
 			listaDeEmpregados.push(empregado);
 			}
 		}
 		var ser = ObjectUtils.serializeObjects(listaDeEmpregados);
		document.form.empregadosSerializados.value = ser;
 	}

 	function serializaAux(){
 		var tabela = document.getElementById('tabelaEmpregado');
 		var count = contEmpregado + 1;
 		var listaDeEmpregados = [];
 		for(var i = 1; i < count ; i++){
 			if (document.getElementById('idEmpregado' + i) != "" && document.getElementById('idEmpregado' + i) != null){
 			var trObj = document.getElementById('idEmpregado' + i).value;
 			var empregado = new GrupoEmpregadoDTO(trObj, i);
 			listaDeEmpregados.push(empregado);
 			}
 		}
 		var ser = ObjectUtils.serializeObjects(listaDeEmpregados);
		document.form.empregadosSerializadosAux.value = ser;
 	}

 	 function serializa2(){
		var tabela = document.getElementById('tabelaEmail');
		var count = contEmail + 1;
		var listaDeEmails = [];
		for(var i = 1; i < count ; i++){
			try{
				if (document.getElementById('idEmail' + i) != "" && document.getElementById('idEmail' + i) != null){

					var trObj = document.getElementById('idEmail' + i).value;
					var email = new GrupoEmailDTO(trObj, i);
					listaDeEmails.push(email);
				}
			}catch(e){}
		}
		var ser = ObjectUtils.serializeObjects(listaDeEmails);
		document.form.emailsSerializados.value = ser;
	}


	function removeLinhaTabela(idTabela, rowIndex, objBotao) {
		 if(idTabela == "tabelaEmpregado"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					//document.form.empregadoGrupos.value = eval('document.form.idEmpregado' + rowIndex + '.value');
					var ItemDatabela = objBotao.parentNode.parentNode.children[1].childNodes[1].defaultValue;
					document.form.empregadoGrupos.value = ItemDatabela;
					contColuna = 1;
					document.form.fireEvent("deleteEmpregado");
					HTMLUtils.deleteRow(idTabela, rowIndex);
					document.form.empregadoGrupos.value = '';
					//document.form.empregadosSerializadosAux.value = eval('document.form.idEmpregado' + rowIndex + '.value');
				}
		 }
		 else{
			 if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				var ItemDatabela = objBotao.parentNode.parentNode.children[2].childNodes[1].defaultValue;
 				//document.form.emailGrupos.value = eval('document.getElementById("idEmail' + rowIndex + '").value');
 				document.form.emailGrupos.value = ItemDatabela;
				document.form.fireEvent("deleteEmail");
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.emailsSerializados.value = eval('document.getElementById("idEmail' + rowIndex + '").value');
				contEmail = 0;
		 	}
		 }
	}

	function deleteAllRows() {
		var tabela = document.getElementById('tabelaEmpregado');
		var tabela1 = document.getElementById('tabelaEmail');
		var count = tabela.rows.length;
		var count1 = tabela1.rows.length

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
			while (count1 > 1) {
				tabela1.deleteRow(count1 - 1);
				count1--;
		}
		document.getElementById('tabelaEmpregado').style.display = "none";
		document.getElementById('tabelaEmail').style.display = "none";
	}

	function visualizarPermissoes() {
		document.getElementById('permissoes').style.display = 'block';
		document.getElementById('tipoPermissao').style.display = 'block';
	}

	function ocultarPermissoes() {
		document.getElementById('permissoes').style.display = 'none';
		document.getElementById('tipoPermissao').style.display = 'none';
	}

	$(function() {
		$("#POPUP_EMPREGADO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

	$(function() {
		$("#POPUP_EMAIL").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

	$(function() {
		$("#addEmpregado").click(function() {
			$("#POPUP_EMPREGADO").dialog("open");
		});
	});

	$(function() {
		$("#addEmail").click(function() {
			$("#POPUP_EMAIL").dialog("open");
		});
	});

	function limpar() {
		deleteAllRows();
		document.form.clear();
		document.getElementById('paginas').style.display = "none";
		contColuna = 1;
	}

	function limparEmail(){
		document.formEmail.clear();
	}

	function exibirGrid(){
		document.getElementById('gridEmpregados').style.display = 'block';
	}

	function exibirGrid1(){
		document.getElementById('gridEmails').style.display = 'block';
	}

	function addEmpregado(){
			$("#POPUP_EMPREGADO").dialog("open");
	}

	function check() {
		var tabela = document.getElementById('tabelaEmpregado');
		var count = tabela.rows.length;

		if ($('#emailTodos').is(":checked")) {
			for ( var i = 1; i < count; i++) {
				$('#enviaEmail' + i).attr('checked', true);
			}
		} else {
			for ( var i = 1; i < count; i++) {
				$('#enviaEmail' + i).attr('checked', false);
			}
		}
	}

	/*desenvolvedor: Rafael César Soyer; data: 11/12/2014 */
	function uncheckEmailTodos (){
		$('#emailTodos').attr('checked', false);
	}

	function marcarTodos(){
		classe = 'perm';
		if ($("#checkboxCheckAll").is(':checked')) {
			$("." + classe).each(function() {
				$(this).attr("checked", true);
			});
		}else {
			$("." + classe).each(function() {
					$(this).attr("checked", false);
			});
		}
	}

	ValidacaoUtils.validaRequired = function(field, label){
		var bTexto = false;

		if (HTMLUtils._isHTMLElement(field, "input")){
			if (field.type == "text"){
				bTexto = true;
			}
		}

		if (bTexto){
			if(StringUtils.isBlank(field.value)){
				alert(label + i18n_message("citcorpore.comum.campo_obrigatorio"));
				try{
					field.focus();
					JANELA_AGUARDE_MENU.hide();
				}catch(e){
				}
			    return false;
			}
		}else{
			if(StringUtils.isBlank(HTMLUtils.getValue(field.id, field.form))){
				alert(label + i18n_message("citcorpore.comum.campo_obrigatorio"));
				JANELA_AGUARDE_MENU.hide();
				try{
					field.focus();
				}catch(e){
				}
			    return false;
			}
		}
		return true;
	}

	//Mudança na paginação
	function paginarItens(paginaSelecionadaColaborador) {
		//Página selecionada
		document.form.paginaSelecionadaColaborador.value = paginaSelecionadaColaborador;
		//Tratando a página que mudou
		serializaAux();
		document.form.fireEvent("gravarEmail");
		//Preenche a tabela com a próxima página
		document.form.fireEvent("preencheTabelaColaborador");
		contColuna = 1;
		contEmpregado = 0;
	}

	function deleteEmpregadoRows() {
		var tabela = document.getElementById('tabelaEmpregado');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		document.getElementById('tabelaEmpregado').style.display = "block";
	}


	function deleteEmpregadoRow() {
		var tabela = document.getElementById('tabelaEmpregado');
		var linhas = tabela.rows.length;
		var i = 1;

		while (i <= linhas) {
			var elemento = $("#tabelaEmpregado TR").eq(i)[0];
			if(elemento.firstElementChild.firstChild.checked)
				tabela.deleteRow(i);
			i++;
		}
		document.getElementById('tabelaEmpregado').style.display = "block";
	}

    function checkboxAllEmpregados(selecionado) {
    	var classe = 'check';
    	if (!$(selecionado).is(':checked')) {
    		$('.' + classe).each(function() {
    			$(this).attr('checked', false);
    			concatenarValoresCheckados(this);
    		});
    	}else {
    		$('.' + classe).each(function() {
    			$(this).attr('checked', true);
    			concatenarValoresCheckados(this);
    		});
    		};
    }

  //função que concatena e desconcatena os checkbox que marcados ou desmarcados
	var empregadosCheckados = '' ;
	function concatenarValoresCheckados(elemento) {
		if (!$(elemento).is(':checked')) {
			checkadosArray = empregadosCheckados.split(";");
			empregadosCheckados = '';
			for(var i = 0; i < checkadosArray.length; i++){
				if (checkadosArray[i] == elemento.value){
					checkadosArray[i] = '';
				}
			}
			for (var i = 0; i < checkadosArray.length; i++){
				if(checkadosArray[i] != ''){
					empregadosCheckados +=checkadosArray[i]+';';
				}
			}
		}else {
				empregadosCheckados += elemento.value + ';';
				}
		document.form.AllEmpregadosCheckados.value = empregadosCheckados;
	}

	function memoriaEmpregadosCheckados(){
		table = document.getElementById('tabelaEmpregado');
		itens = document.getElementsByName('empregadoSelecionado');
		checkTodosPagina = document.getElementsByName('selecionarTodosDaPagina');
		count = 0;
		var ids = empregadosCheckados.split(';');
		for(i=0; i< itens.length; i++){
			for (y=0; y< ids.length; y++){
				if(itens[i].value == ids[y]){
					itens[i].checked = true;
					count += 1;
					break;
				}
			}
		}
		if (itens.length == count ? $(checkTodosPagina).attr('checked', true) : $(checkTodosPagina).attr('checked', false));
	}

	function removerEmpregadosSelecionados(){
		if($('#idGrupo').val()!=""){
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.fireEvent("deleteEmpregado");
				reiniciaVarContColuna();
				uncheckEmailTodos();
			}
		}else{
			deleteEmpregadoRow();
		}

	}

	function removerTodosEmpregados(){
		if (confirm(i18n_message("citcorpore.comum.deleta"))){
			document.form.fireEvent("deleteTodosEmpregados");
			reiniciaVarContColuna();
			uncheckEmailTodos();
		}
	}

