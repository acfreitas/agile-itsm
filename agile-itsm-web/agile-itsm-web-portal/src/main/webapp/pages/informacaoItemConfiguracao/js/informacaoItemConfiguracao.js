function informa() {
	document.form.fireEvent("informacao");
}

function tree(id) {
	$(id).treeview();
	$('#loading_overlay').show();
}

function restaurarValoresBios() {
	document.form.fireEvent("prepararHtmlBios");
	$('#loading_overlay').show();
}

function restaurarValoresHardware() {
	document.form.fireEvent("prepararHtmlHardware");
	$('#loading_overlay').show();
}

function restaurarValoresSoftware() {
	document.form.fireEvent("prepararHtmlSoftware");
	$('#loading_overlay').show();
}

function verificaImpactos(id){
	document.form.fireEvent('verificaImpactos');
}