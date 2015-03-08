fechar = function(){
	parent.fecharModalDelegacaoTarefa();
}

gravar = function() {
	if (StringUtils.isBlank(document.form.idGrupoDestino.value) && StringUtils.isBlank(document.form.idUsuarioDestino.value)){
		alert(i18n_message("gerenciaservico.delegartarefa.validacao.informeprazo"));
		document.form.IdGrupoDestino.focus();
		return;
	}
	if (window.confirm(i18n_message("gerenciaservico.delegartarefa.confirm.delegaratividade"))) 
		document.form.save();
}	