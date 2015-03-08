
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		window.setTimeout('processsa()',1000);
	}
	function processsa() {
		document.form.fireEvent('mostraInfo');
		window.setTimeout('processsa()',5000);
	}

	function LOOKUP_COMANDO_select(id, desc) {
		document.form.restore({
			id :id});
	}
	function submeteIP(){
		document.form.fireEvent('submeteIP');
	}
	function forcarLacoInv(){
		document.form.fireEvent('forcarLacoInv');
	}
	function inventarioAgora(ip){
		document.form.ip.value = ip;
		document.form.fireEvent('inventarioAgora');
	}
	function refreshIPs(){
		document.getElementById('divInfo').innerHTML = 'Aguarde...';
		document.form.fireEvent('refreshIPs');
	}
	function gerarFaixaIPs(){
		$("#faixaIps").modal("show");
	}
	function submeteGerarFaixaIPs(){
		document.getElementById('divResultado').innerHTML = 'Aguarde...';
		document.formGerarFaixa.fireEvent('gerarFaixaIPs');
	}
	function adicionarIPSAtivosLista(){
		document.form.ip.value = document.formGerarFaixa.txtIPSAtivos.value;
		document.form.fireEvent('submeteIP');
	}
	function adicionarTodosIPSLista(){
		document.form.ip.value = document.formGerarFaixa.txtIPSTodos.value;
		document.form.fireEvent('submeteIP');
	}
	function fazPing(ip){
		document.form.ip.value = ip;
		document.form.fireEvent('fazPing');
	}
	function refresh(){
		window.location = ctx+'/pages/mostraStatusInventario/mostraStatusInventario.load';
	}
	function alterarThreadInv(){
		document.formAlteraValor.tipoDado.value = 'INV';
		$("#alteraValor").modal("show");
	}
	function alterarThreadDisc(){
		document.formAlteraValor.tipoDado.value = 'DIS';
		$("#alteraValor").modal("show");
	}
	function alterarPingTimeout(){
		document.formAlteraValor.tipoDado.value = 'PING';
		$("#alteraValor").modal("show");
	}
	function submeteAlteracaoValor(){
		document.formAlteraValor.fireEvent('alteraValor');
	}


