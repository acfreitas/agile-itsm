addEvent(window, "load", load, false);
function load() {
	$(".popup").dialog({
		width : document.body.offsetWidth/1.3,
		height : window.screen.height/1.3,
		modal : true,
		autoOpen : false,
		resizable : false,
		show : "fade",
		hide : "fade",
	});

	$("#loading_overlay").hide();
}

var listaFalhas = [];
function JustificacaoEventoHistorico(_ip, _idItemConfiguracao, _idBaseItemConfiguracao,
									 _identificacaoItemConfiguracao, _descricaoTentativa, _idEvento,
									 _nomeGrupo, _nomeUnidade, _idEmpregado, _idHistoricoTentativa){
	this.ip = _ip;
	this.idItemConfiguracao = _idItemConfiguracao;
	this.idBaseItemConfiguracao = _idBaseItemConfiguracao;
	this.identificacaoItemConfiguracao = _identificacaoItemConfiguracao;
	this.descricaoTentativa = _descricaoTentativa;
	this.idEvento = _idEvento;
	this.nomeGrupo = _nomeGrupo;
	this.nomeUnidade = _nomeUnidade;
	this.descricao;
	this.idEmpregado = _idEmpregado;
	this.idHistoricoTentativa = _idHistoricoTentativa;
}

function alimentaListaFalhas(){
	document.getElementById("qtdTotal").innerHTML = "";
	listaFalhas = [];
	var lista = ObjectUtils.deserializeCollectionFromString(document.getElementById("listaItensSerializado").value);
	var table = document.getElementById('tabelaFalhas');
	if(lista == ""){
		table.innerHTML = i18n_message("justificacaoFalhas.nenhumResultadoEncontrado");
		return;
	}
	table.innerHTML = "";
	var listaIpsJaPercorrido = [];

	var contador = 0;
	for(var i = 0 ; i < lista.length; i++){
		listaFalhas.push(new JustificacaoEventoHistorico(lista[i].ip, lista[i].idItemConfiguracao, lista[i].idBaseItemConfiguracao,
														 lista[i].identificacaoItemConfiguracao, lista[i].descricaoTentativa, lista[i].idEvento,
														 lista[i].nomeGrupo, lista[i].nomeUnidade, lista[i].idEmpregado, lista[i].idHistoricoTentativa));

		if(!estaNaLista(listaIpsJaPercorrido, lista[i].ip)){
				listaIpsJaPercorrido.push(lista[i].ip);

				var linkIp = document.createElement('a');
				linkIp.setAttribute('href', '#');
				linkIp.innerText = lista[i].ip + " (" + getQuantidadeItensIp(lista[i].ip, lista) + ")";
				linkIp.setAttribute("id", lista[i].ip);
				linkIp.addEventListener("click", abrePopupJustificacao, false);
				table.appendChild(linkIp);
				table.appendChild(document.createElement("br"));
		}
	}

	document.getElementById("qtdTotal").innerHTML = "Total: " + listaFalhas.length;
}

function getQuantidadeItensIp(ip, listaFalhasAux){
	var contador = 0;
	for(var i = 0; i < listaFalhasAux.length; i++){
		if(listaFalhasAux[i].ip == ip){
			contador++;
		}
	}
	return contador;
}

function estaNaLista(lista, valor){
	for(var i = 0 ; i < lista.length; i++){
		if(lista[i] == valor){
			return true;
		}
	}
	return false;
}

function abrePopupJustificacao(ev){
	var id = ev.target.getAttribute("id");

	//cabecalho
	for(var i = 0 ; i < listaFalhas.length; i++){
		if(listaFalhas[i].ip == id){
			document.getElementById("lbNomeGrupo").innerText = listaFalhas[i].nomeGrupo;
			document.getElementById("lbNomeUnidade").innerText = listaFalhas[i].nomeUnidade;
			document.getElementById("lbIP").innerText = listaFalhas[i].ip;
			break;
		}
	}

	var tabela = document.getElementById("descricaoFalhas");
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}

	for(var i = 0 ; i < listaFalhas.length; i++){
		if(listaFalhas[i].ip == id){
			var linha = tabela.insertRow(1);
			var coluna01 = linha.insertCell(0);
			var coluna02 = linha.insertCell(1);
			var coluna03 = linha.insertCell(2);
			var coluna04 = linha.insertCell(3);

			var chk = document.createElement("input");

			//descricao falha
			coluna01.innerHTML = listaFalhas[i].descricaoTentativa;

			//descricao item
			coluna02.innerHTML = listaFalhas[i].identificacaoItemConfiguracao;

			//descricao tipo
			coluna03.innerHTML = listaFalhas[i].idItemConfiguracao == "" ? i18n_message("justificacaoFalhas.instalacao") : i18n_message("justificacaoFalhas.desinstalacao");

			// checkbox
			chk.setAttribute("type", "checkbox");
			chk.setAttribute("id", "check-" + i);
			coluna04.appendChild(chk);
		}
	}

	$("#popupJustificacao").dialog("open");
}

function LOOKUP_EVENTOS_select(id, desc) {
	$("#POPUP_EVENTOS").dialog("close");
	document.form.idEvento.value = id;
	document.form.nomeEvento.value = desc.split("-")[0];
}

function LOOKUP_GRUPO_select(id, desc) {
	$("#POPUP_GRUPO_EMPREGADOS").dialog("close");
	document.form.idGrupo.value = id;
	document.form.nomeGrupo.value = desc.split("-")[0];
}

function LOOKUP_UNIDADE_select(id, desc) {
	$("#POPUP_UNIDADE_EMPREGADO").dialog("close");
	document.form.idUnidade.value = id;
	document.form.nomeUnidade.value = desc.split("-")[0];
}

function pesquisar(){
	document.form.fireEvent("pesquisar");
	$("#loading_overlay").show();
}

function checkAll(){
	var chkAll = document.getElementById("chkMarcarTodos");
	for(var i = 0 ; i < listaFalhas.length; i++){
		chk = document.getElementById("check-" + i);
		if(chk != null) {
			chk.checked = chkAll.checked;
		}
	}
}

function justificar(){
	var justificacao = document.getElementById("txtJustificacaoFalha").value;
	var chk;
	var listaAux = [];
	for(var i = 0 ; i < listaFalhas.length; i++){
		chk = document.getElementById("check-" + i);
		if(chk != null && chk.checked == 1){
			listaFalhas[i].descricao = justificacao;
			listaAux.push(listaFalhas[i]);
		}
	}
	var listaSerializada = ObjectUtils.serializeObjects(listaAux);
	document.getElementById("listaItensSerializado").value = listaSerializada;
	document.form.fireEvent("salvarJustificativa");
	document.getElementById('tabelaFalhas').innerHTML = "";
	$("#loading_overlay").show();
	$("#popupJustificacao").dialog("close");
}
