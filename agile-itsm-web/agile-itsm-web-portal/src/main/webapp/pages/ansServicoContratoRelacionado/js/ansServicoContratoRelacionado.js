
function lancarServicos() {
	checkboxServico = document.getElementsByName('idServicoContrato');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var baselines = new Array();

	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idServicoContrato' + i);
		if (!trObj) {
			continue;
		}
		baselines[contadorAux] = getServico(i);
		contadorAux = contadorAux + 1;
	}
	serializaServico();
}

serializaServico = function() {
	var checkboxServico = document.getElementsByName('idServicoContrato');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var servicos = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idServicoContrato' + i);
		if (!trObj) {
			continue;
		}else if(trObj.checked){
			servicos[contadorAux] = getServico(i);
			contadorAux = contadorAux + 1;
			continue;
		}
	}
	var servicosSerializadas = ObjectUtils.serializeObjects(servicos);
	document.form.servicosSerializados.value = servicosSerializadas;
	return true;
}

getServico = function(seq) {
	var acordoServicoContratoDTO = new CIT_AcordoServicoContratoDTO();
	acordoServicoContratoDTO.sequencia = seq;
	acordoServicoContratoDTO.idServicoContrato = eval('document.form.idServicoContrato' + seq + '.value');
	return acordoServicoContratoDTO;
}

//@author ronnie.lopes
//Checka o checkbox de acordo com o filtro
function check(campoBusca) {
	var tabela = document.getElementById('tblServicoContrato');
	var count = tabela.rows.length;
	var campoBuscaFormatada = campoBusca.value.toLowerCase();

	//verifica se existe algo digitado no campoBusca(Filtro)
	if (campoBuscaFormatada == "" || campoBuscaFormatada == null) {
		if ($('#todos').is(":checked")) { //verifica se o checkbox 'todos' está marcado
			for ( var i = 1; i < count; i++) {
				$('#idServicoContrato' + i).attr('checked', true); //marca todos elementos
			}
		}else { //se o checkbox 'todos' estiver desmarcado, desmarca todos os elementos
			for ( var i = 1; i < count; i++) {
				$('#idServicoContrato' + i).attr('checked', false);
			}
		}
	}else {
		if ($('#todos').is(":checked")) {
			for ( var i = 1; i < count; i++) {
				var elemento = tabela.rows[i]; //recebe o elemento
				if(elemento.className == 'ativo') { //verifica se o elemento possui Class "ativo"
					$('#idServicoContrato' + i).attr('checked', true); //checka o checkbox do elemento
				}
			}
		}else {
			for ( var i = 1; i < count; i++) {
				var elemento = tabela.rows[i]; //recebe o elemento
				if(elemento.className == 'ativo') { //verifica se o elemento possui Class "ativo"
					$('#idServicoContrato' + i).attr('checked', false); //retira o check do checkbox do elemento
				}
			}
		}
	}
}

//@author ronnie.lopes
//Filtra a Tabela pelo Nome do Serviço de acordo com o que digita no campo busca
function filtroTableAcNivelServ(campoBusca, table) {
	// Recupera valor do campo de busca
	var term = campoBusca.value.toLowerCase();
	if (term != "") {
		// Mostra os TR's que contem o value digitado no campoBusca
		if (table != "") {
			$("#" + table + " tbody>tr").hide();
			$("#" + table + " td").filter(function() {
				return $(this).text().toLowerCase().indexOf(term) > -1
			}).parent("tr").show().addClass("ativo"); //adiciona Class "ativo" nos elementos filtrados
		}
	} else {
		// Quando não há nada digitado, mostra a tabela com todos os dados
		$("#" + table + " tbody>tr").show().removeClass("ativo"); //remove Class "ativo" de todos elementos quando não filtrados
	}
}

function fechar() {
	parent.fecharVisao();
}

function gravar() {
	JANELA_AGUARDE_MENU.show();
	lancarServicos();
	document.form.save();
}

function salvar() {
	if ($('#habilitado').is(":checked")) {
		if (confirm(i18n_message('sla.confirmaVinculo'))) {
			gravar();
		}
	} else {
		gravar();
	}
}

function listaServicosRelacionados() {
	$('#habilitado').attr("checked", false);
	document.form.fireEvent("listaServicosRelacionados");
	JANELA_AGUARDE_MENU.show();
	deleteAllRows();
}

$(function() {
	JANELA_AGUARDE_MENU.show();
});

function deleteAllRows() {
	var tabela = document.getElementById('tblServicoContrato');
	if (tabela != undefined) {
		var count = tabela.rows.length;
		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
	}
}

function mostra() {
	$("#HAB").css("display", "block");
}
function oculta() {
	$("#HAB").css("display", "none");
}