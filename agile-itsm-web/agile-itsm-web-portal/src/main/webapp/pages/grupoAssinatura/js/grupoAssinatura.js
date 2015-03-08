function LOOKUP_GRUPOASSINATURA_select(idParam, desc) {
	$('.tabsbar a[href="#tab1-3"]').tab('show');
	document.form.restore({
		idGrupoAssinatura : idParam
	});
}

function serializaTblAssinaturas() {
	var itens = HTMLUtils.getObjectsByTableId('tblAssinaturas');
	document.form.tblAssinaturas_serialize.value = ObjectUtils.serializeObjects(itens);
}

function adicionarAssinatura(){
	if((document.getElementById('idAssinatura').value == "")||(document.getElementById('idAssinatura').value == "0")){
		alert(i18n_message("grupoAssinatura.alerta.informeAssinatura"));
		return;
	}else if((document.getElementById('ordem').value == "")||(document.getElementById('ordem').value == "0")){
		alert(i18n_message("grupoAssinatura.alerta.informeOrdem"));
		return;						
	}
    var obj = new CIT_ItemGrupoAssinaturaDTO();
    obj.idAssinatura = document.getElementById('idAssinatura').value;
    
    var texto = document.getElementById('idAssinatura').options[document.getElementById('idAssinatura').selectedIndex].text.split(" / ");
    obj.nomeResponsavel = texto[0].replace(/-*/, "");
    obj.papel = texto[1].replace(/-*/, "");
	obj.fase = texto[2].replace(/-*/, "");
	obj.ordem = document.getElementById('ordem').value;
	
	HTMLUtils.addRow('tblAssinaturas', document.form, '', obj, 
			['', 'nomeResponsavel', 'papel', 'fase', 'ordem'], ["idAssinatura","ordem"], i18n_message("grupoAssinatura.alerta.assinaturaJaAdicionada"), [exibeIconesItemAssinatura], null, null, false);
	
	document.getElementById("idAssinatura").value = "0";
	document.getElementById("ordem").value = "";
	document.getElementById("idAssinatura").focus();
}

exibeIconesItemAssinatura = function(row, obj){
    row.cells[0].innerHTML = '<img id="imgDelAss" style="cursor: pointer;" border="0" src="'+URL_INITIAL+'imagens/delete.png" onclick="excluiItemAssinatura(\'tblAssinaturas\', this.parentNode.parentNode.rowIndex);">'
}

excluiItemAssinatura = function(table, index) {
	if (index > 0 && confirm(i18n_message("grupoAssinatura.alerta.exclusaoAssinatura"))) {
		HTMLUtils.deleteRow(table, index);
	}
}

function alimentarDadosAssinatura(){
	document.form.fireEvent("alimentarDadosAssinatura");
}

function gravar(){
	serializaTblAssinaturas();
	document.form.save();
}

function limpar(){
	document.form.clear();
	HTMLUtils.deleteAllRows("tblAssinaturas");
}

function excluir() {
	if (confirm(i18n_message("citcorpore.comum.deleta"))) {
		document.form.fireEvent("excluir");
	}
}
