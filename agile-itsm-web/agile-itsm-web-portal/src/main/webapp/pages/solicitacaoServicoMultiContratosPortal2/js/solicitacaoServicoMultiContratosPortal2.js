function cancelar(){
	//parent.fecharModalNovaSolicitacao();
	parent.$('#modal_novaSolicitacao').modal('hide');
	JANELA_AGUARDE_MENU.hide();		 
}
function abrir(){
	parent.abrirModalNovaSolicitacao();
}
function mostraMensagemInsercao(param) {
	bootbox.alert(param, function(result) {
		parent.fecharModalNovaSolicitacao();
	});
}
function validarCampoDescricao() {
    if (/^\s*$/g.test($('#descricao').val())) {
        return false;
    }
    return true;
};
function salvar() {
	JANELA_AGUARDE_MENU.show();
	if(validarCampoDescricao()) {
		document.form.save();
	}else {
		JANELA_AGUARDE_MENU.hide();		 
		alert(i18n_message("solicitacaoServico.informedescricao"));
		var editor = new wysihtml5.Editor("descricao");
		editor.focus(true);
	}
}
