// ------------- variaveis globais -------------
var pagina = "1";
var proximaPagina = "";
/*bruno.aquino: foi comentado o trecho de c�digo abaixo e colocado em templateCurriculo.jsp pois n�o era possivel capturar o locale*/
/*jQuery(function($){
	$("#cep").mask("99.999-999");
	$("#cpf").mask("999.999.999-99");
	$("#telefone").mask("9999-9999");
	$("#dataNascimento").mask("99/99/9999");

 Desenvolvedor: Gilberto Nery - Data: 25/11/2013 - Hor�rio: 15:00 - ID Citsmart: 0
 *
 * Motivo/Coment�rio: Formato o campo Data de Nascimento para o padr�o brasileiro (dd/mm/yyyy)
 *
	$('#dataNascimento').datepicker({
		dateFormat: 'dd/mm/yy',
		language: 'pt-BR'
	});

});*/

// Validar saida do usuario da p�gina
saidaNaoEsperada = false;

$('body').on('keyup', function(e) {
	var key = (e.keyCode ? e.keyCode : e.which);
	if((key >= 48 && key <= 57) || (key >= 65 && key <= 90) || (key >= 97 && key <= 122) || (key == 32)) {
		saidaNaoEsperada = true;
	}
});

$(function() {

	pagina = 1;

	$('#PESQUISA_CURSOS').dialog({
		autoOpen: false,
		width: 600,
		height: 500,
		modal: true
	});

	/*document.form.afterRestore = function() {
		$('.tabs').tabs('select', 0);

		$('#telefone').unmask();
		$('#telefone').mask('(99) 9999-9999').val($('#telefone').val() );

		$('#fax').unmask();
		$('#fax').mask('(99) 9999-9999').val($('#fax').val() );

		$('#cep').unmask();
		$('#cep').mask('99999-999').val($('#cep').val() );

		$('#idNaturalidade option[value=' + $('#idPais').val() + ']').prop('selected', true);
		$('#enderecoIdUF option[value=' + $('#idUf').val() + ']').prop('selected', true);
		$('#enderecoNomeCidade option[value=' + $('#idCidade').val() + ']').prop('selected', true);
	};*/


	$('#pais').change(function() {
        $('#idPais').val($('#pais option:selected').val() );

        if ($('#idPais').val() == '') {
        	$('#hiddenIdUf').val('');
        	$('#idCidade').val('');
        	document.formPesquisaCurriculo.fireEvent('preencherComboCidades');
        }
        document.formPesquisaCurriculo.fireEvent('preencherComboUfs');
    });

	/* CONFIGURA O CAMPO ESCONDIDO idUf DE ACORDO COM A OPCAO SELECIONADA NA CAIXA DE SELECAO DE UFs*/
	$('#enderecoIdUF').change(function() {
		$('#hiddenIdUf').val($('#enderecoIdUF option:selected').val() );

		if ($('#hiddenIdUf').val() == '') {
			$('#idCidade').val('');
		}
		document.formPesquisaCurriculo.fireEvent('preencherComboCidades');
	});

	/* CONFIGURA O CAMPO ESCONDIDO idCidade DE ACORDO COM A OPCAO SELECIONADA NA CAIXA DE SELECAO DE CIDADES*/
	$('#enderecoIdCidade').change(function() {
		$('#idCidade').val($('#enderecoIdCidade option:selected').val() );
	});


	$('#idEstadoNatal').change(function() {
		$('#idUfNatal').val($('#idEstadoNatal option:selected').val() );

		if ($('#idUfNatal').val() == '') {
			$('#cidadeNatalValor').val('');
		}
		document.formPesquisaCurriculo.fireEvent('preencherComboCidadeNatal');
	});

	$('#idCidadeNatal').change(function() {
		$('#cidadeNatalValor').val($('#idCidadeNatal option:selected').val() );
	});

	/* 1 - CONFIGURANDO A MASCARA APROPRIADA PARA CADA CAMPO DO FORMULARIO */

	/* 1.2 - PARA OS CAMPOS TELEFONE E FAX */
/*	$('#telefone').mask('(99) 9999-9999');
	$('#fax').mask('(99) 9999-9999');

	 1.3 - CEP
	$('#cep').mask('99999-999');

	$('#email').focusout(function() {
		var email = $('#email').val();

		if (email != '') {
			// Avaliando a expressao regular para validacao do e-mail.
			if (!/\b[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}\b/.test(email) ) {
				alert(i18n_message("citcorpore.validacao.emailInvalido"));
			}
		}
	});*/
	$('body').on('change', '.diasAtuais', function(){
		if($(this).is(':checked')){
			$(this).parents('.widget-funcoes').find('.escondedatafinal').hide();
	    } else {
	    	$(this).parents('.widget-funcoes').find('.escondedatafinal').show();
	    }
	});
});

function pesquisarCurso() {
	$('#PESQUISA_CURSOS').dialog('open');
}

function LOOKUP_PESQUISA_CURSOS_select(id, desc) {
	$('#idCurso').val(id);
	$('#treinamento').val(desc);
	$('#PESQUISA_CURSOS').dialog('close');
}

function gerarImgDel(row, obj){
    row.cells[3].innerHTML = '<span onclick="excluirTelefone(this.parentNode.parentNode.rowIndex)"class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function gerarImgDelEmail(row, obj){
	row.cells[2].innerHTML = '<span  onclick="excluirEmail(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function gerarImagemDelEndereco(row, obj){
	if(obj.principal == "N"){
		row.cells[1].innerHTML = '<span  onclick="excluirEndereco(this.parentNode.parentNode.rowIndex, ' +  ' \'N\'' + ')" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
	}else{
		row.cells[1].innerHTML = '<span  onclick="excluirEndereco(this.parentNode.parentNode.rowIndex, ' +  ' \'S\'' + ')" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
	}
};

function gerarImagemDelFormacao(row, obj){
	row.cells[4].innerHTML = '<span onclick="excluirFormacao(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function gerarImagemDelExperiencia(row, obj){
	row.cells[4].innerHTML = '<span onclick="excluirExperiencia(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function gerarBotaoAdicionarFuncao(row, obj){
	if(obj.idExperienciaProfissional=='')
		obj.idExperienciaProfissional = 0 ;
	row.cells[3].innerHTML = '<span onclick="funcaoDescricao('+obj.idExperienciaProfissional+',this.parentNode.parentNode.rowIndex);" class="btn-action glyphicons eye_open btn-primary titulo"><i></i></span>';
};

function funcaoDescricao(idExperienciaProfissional,row){
		if(idExperienciaProfissional != null && idExperienciaProfissional != 0){
			var obj = HTMLUtils.getObjectByTableIndex('tblExperiencias', row);
			document.formExperienciaProfissional.idExperienciaProfissional.value = idExperienciaProfissional;
			document.formExperienciaProfissional.colFuncaoExperiencia_Serialize.value = obj.colFuncaoSerialize;
		}
		document.formExperienciaProfissional.fireEvent("restauraTabelaFuncao");
};

function gerarImagemDelExperienciaFuncao(row, obj){
	row.cells[2].innerHTML = '<span onclick="excluirExperienciaFuncao(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function gerarImagemDelCompetencia(row, obj){
	row.cells[2].innerHTML = '<span onclick="excluirCompetencia(this.parentNode.parentNode.rowIndex);" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function gerarImagemDelCertificacao(row, obj){
	row.cells[3].innerHTML = '<span onclick="excluirCertificacao(this.parentNode.parentNode.rowIndex);" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function gerarImagemDelTreinamento(row, obj){
	row.cells[2].innerHTML = '<span  onclick="excluirTreinamento(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function gerarImagemDelIdioma(row, obj){
	row.cells[4].innerHTML = '<span onclick="excluirIdioma(this.parentNode.parentNode.rowIndex);" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function manipulaDivDeficiencia(){

	if(document.formPesquisaCurriculo.portadorNecessidadeEspecial[0].checked){
		document.getElementById('idItemListaTipoDeficiencia').removeAttribute("disabled");
		$('#portadorNecessidadeEspecialS').parent('label').addClass('radio inline');
	}
	else{
		document.getElementById('idItemListaTipoDeficiencia').setAttribute("disabled", "disabled");
		document.formPesquisaCurriculo.idItemListaTipoDeficiencia.value = '';
	}
}


function limpar(){
	window.location = URL_INITIAL + 'requisicaoPessoal/requisicaoPessoal.load';
}

function desabilitarTela() {
   var f = document.formPesquisaCurriculo;
   for(i=0;i<f.length;i++){
       var el =  f.elements[i];
       if (el.type != 'hidden') {
           if (el.disabled != null && el.disabled != undefined) {
               el.disabled = 'disabled';
            }
        }
    }
}


addEvent(window, "load", load, false);
function load(){
      if(document.getElementById('auxEnderecoPrincipal').value == '' || document.getElementById('auxEnderecoPrincipal').value == 0){
    	  $('#divEnderecoPrincipal').show();
      }else{
    	  $('#divEnderecoPrincipal').hide();
      }
//      $('#tab2').show();

}
function validar() {
   }

function validaPrincipalEndereco(x){
	if(x == 0){
		$('#divEnderecoPrincipal').show();
	}else{
  	  	$('#divEnderecoPrincipal').hide();
	}
}

function validaPrincipalEmail(x){
	if(x == 0){
		$('#divPrincipal').show();
	}else{
  	  	$('#divPrincipal').hide();
	}
}

function getObjetoSerializado() {
       var obj = new CIT_RequisicaoPessoalDTO();
       HTMLUtils.setValuesObject(document.formPesquisaCurriculo, obj);
       return ObjectUtils.serializeObject(obj);
	}




//--------------------------------------- TELEFONE ---------------------------------------

function LOOKUP_CURRICULOS_select(id,desc){
	document.formPesquisaCurriculo.restore({idCurriculo:id});
}

function excluirTelefone(rowIndex){
	HTMLUtils.deleteRow('tblTelefones', rowIndex);
};


function addTelefone(){

	//Valida as Informacoes digitadas do Telefone
	if (StringUtils.isBlank(document.getElementById('telefone#idTipoTelefone').value)){
		alert(i18n_message("rh.informeTipoTelefone"));
		document.getElementById('telefone#idTipoTelefone').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('telefone#ddd').value)){
		alert(i18n_message("rh.informeDDD"));
		document.getElementById('telefone#ddd').focus();
		return;
	}else{
		var dddTelefone =  document.getElementById('telefone#ddd').value;
		if (dddTelefone.length <= 1) {
			alert(i18n_message("rh.DDDInvalido"));
			document.getElementById('telefone#ddd').focus();
			return;
		}
	}
	if (StringUtils.isBlank(document.getElementById('telefone').value)){
		alert(i18n_message("rh.informeNumeroTelefone"));
		document.getElementById('telefone').focus();
		return;
	}
	if(StringUtils.trim(document.getElementById('telefone').value).length < 8) {
		alert(i18n_message("rh.numeroTelefoneInvalido"));
		document.getElementById('telefone').focus();
		return;
	}
	if(document.getElementById('rowIndexTelefone').value == null ||  document.getElementById('rowIndexTelefone').value == undefined || document.getElementById('rowIndexTelefone').value < 1) {
		var obj = new CIT_TelefoneCurriculoDTO();

		obj.idTipoTelefone = document.getElementById('telefone#idTipoTelefone').value;
		obj.descricaoTipoTelefone = document.getElementById('telefone#idTipoTelefone').options[document.getElementById('telefone#idTipoTelefone').selectedIndex].text;
		obj.numeroTelefone = document.getElementById('telefone').value;
		obj.ddd = document.getElementById('telefone#ddd').value;

		HTMLUtils.addRow('tblTelefones', document.formPesquisaCurriculo, 'telefone#idTipoTelefone', obj, ['ddd', 'numeroTelefone', 'descricaoTipoTelefone',''], null, '', [gerarImgDel], funcaoClickRowTelefone, null, false);
	} else {
		var obj = HTMLUtils.getObjectByTableIndex('tblTelefones', document.getElementById('rowIndexTelefone').value);

		obj.idTipoTelefone = document.getElementById('telefone#idTipoTelefone').value;
		obj.descricaoTipoTelefone = document.getElementById('telefone#idTipoTelefone').options[document.getElementById('telefone#idTipoTelefone').selectedIndex].text;
		obj.numeroTelefone = document.getElementById('telefone').value;
		obj.ddd = document.getElementById('telefone#ddd').value;

		HTMLUtils.updateRow('tblTelefones', document.formPesquisaCurriculo, 'telefone#idTipoTelefone', obj, ['ddd', 'numeroTelefone', 'descricaoTipoTelefone',''], null, '', [gerarImgDel], funcaoClickRowTelefone, null, document.getElementById('rowIndexTelefone').value, false);

		document.getElementById('rowIndexTelefone').value = null;
	}

	document.getElementById('telefone').value = '';
	document.getElementById('telefone#ddd').value = '';
	document.getElementById('telefone#idTipoTelefone').value = '';
	document.getElementById('telefone#idTipoTelefone').focus();
};

function funcaoClickRowTelefone(row, obj) {
	if(obj == null) {
		document.getElementById('rowIndexTelefone').value = null;
	} else {
		rowIndex = $('#'+obj.idControleCITFramework).index();

		document.getElementById("rowIndexTelefone").value = rowIndex;

		$('[id="telefone#idTipoTelefone"]').find('option:contains("' + obj.descricaoTipoTelefone + '")').attr("selected", true);
		document.getElementById("telefone#ddd").value = obj.ddd;
		document.getElementById("telefone").value = obj.numeroTelefone;
	}
};


//--------------------------------------- EMAIL ---------------------------------------

function validaEmail(email) {
	var existe = false;
	// Verifica se o E-mail ja esta cadatrado para um outro candidato
	$.ajax({
		url: '/citsmart/pages/templateCurriculoTrabalheConosco/templateCurriculoTrabalheConosco.event',
		async: false,
		data: {
			'method': 'execute',
			'parmCount': '',
			'parm1': 'templateCurriculoTrabalheConosco',
			'parm2': '',
			'parm3': 'verificaEmailPrincipalJaCadastrado',
			'nocache': new Date(),
			'email': StringUtils.trim(email),
			'idCurriculo': StringUtils.trim(document.getElementById('idCurriculo').value)
		},
		type: 'POST',
		dataType: 'json',
		success: function(data) {
			existe = data.existe;
		},
		error: function() {
			console.log("Error!");
		}
	});

	if(existe == "true" || existe == true) {
		return true;
	}

	return false;
}

function mostrarEmailPrincipal(row, obj){
	if (obj.principal == 'S'){
		row.cells[1].innerHTML = '<img src="'+URL_SISTEMA+'"/imagens/ok.png" border="0"/>';
	}else{
		row.cells[1].innerHTML = '&nbsp;';
	}
	validaPrincipal();
};

function excluirEmail(rowIndex){
	var obj = HTMLUtils.getObjectByTableIndex('tblEmail', rowIndex);

	HTMLUtils.deleteRow('tblEmail', rowIndex);

	if(obj.principal == 'S' || obj.imagemEmailprincipal == 'S') {
		$('#divPrincipal').show();
		document.getElementById('auxEmailPrincipal').value = '';
		document.getElementById('email#principal').checked = false;
	}

	if(rowIndex < document.getElementById('auxEmailPrincipal').value) {
		document.getElementById('auxEmailPrincipal').value = document.getElementById('auxEmailPrincipal').value - 1;
	}
};

function addEmail(){
	//Valida as Informacoes digitadas do E-mail
	if (StringUtils.isBlank(document.getElementById('email#descricaoEmail').value)){
		alert(i18n_message("rh.informeEmail"));
		document.getElementById('email#descricaoEmail').focus();
		return;
	}
	// Se o e-mail for principal
	if(document.getElementById('email#principal').checked) {
		if(validaEmail(document.getElementById('email#descricaoEmail').value)) {
			alert(i18n_message("rh.emailPrincipalJaCadastrado"));
			document.getElementById('email#descricaoEmail').focus();
			return;
		}
	}

	var resultado = ValidacaoUtils.validaEmail(document.getElementById('email#descricaoEmail'),'');
	if (resultado == false) {
		return;
	}

	if(document.getElementById('rowIndexEmail').value == null ||  document.getElementById('rowIndexEmail').value == undefined || document.getElementById('rowIndexEmail').value < 1) {
		var obj = new CIT_EmailCurriculoDTO();

		if (document.getElementById('email#principal').checked){
			var tbl = document.getElementById('tblEmail');
			obj.imagemEmailprincipal = 'S';
			obj.principal = 'S';
			document.getElementById('auxEmailPrincipal').value = tbl.rows.length;
			$('#divPrincipal').hide();
		} else {
			obj.imagemEmailprincipal = 'N';
			obj.principal = 'N';
		}

		obj.descricaoEmail = document.getElementById('email#descricaoEmail').value;

		HTMLUtils.addRow('tblEmail', document.formPesquisaCurriculo, 'email', obj, ['descricaoEmail', 'imagemEmailprincipal', ''], null, '', [gerarImgDelEmail], funcaoClickRowEmail, null, false);
	} else {
		var obj = HTMLUtils.getObjectByTableIndex('tblEmail', document.getElementById('rowIndexEmail').value);

		if (document.getElementById('email#principal').checked){
			var tbl = document.getElementById('tblEmail');
			obj.imagemEmailprincipal = 'S';
			obj.principal = 'S';
			document.getElementById('auxEmailPrincipal').value = document.getElementById('rowIndexEmail').value;
			$('#divPrincipal').hide();
		} else {
			if(document.getElementById('rowIndexEmail').value == document.getElementById('auxEmailPrincipal').value) {
				document.getElementById('auxEmailPrincipal').value = '';
			}
			obj.imagemEmailprincipal = 'N';
			obj.principal = 'N';
		}

		obj.descricaoEmail = document.getElementById('email#descricaoEmail').value;

		HTMLUtils.updateRow('tblEmail', document.formPesquisaCurriculo, 'email', obj, ['descricaoEmail', 'imagemEmailprincipal',''], null, '', [gerarImgDelEmail], funcaoClickRowEmail, null, document.getElementById('rowIndexEmail').value, false);

		document.getElementById('rowIndexEmail').value = null;
	}

	document.getElementById('email#descricaoEmail').value = '';
	document.getElementById('email#principal').checked = false;

	HTMLUtils.applyStyleClassInAllCells('tblEmail', 'celulaGrid');
};

function funcaoClickRowEmail(row, obj) {
	if(obj == null) {
		document.getElementById('rowIndexEmail').value = null;
	} else {
		rowIndex = $('#'+obj.idControleCITFramework).index();
		obj = HTMLUtils.getObjectByTableIndex('tblEmail', rowIndex);
		document.getElementById("rowIndexEmail").value = rowIndex;
		document.getElementById("email#descricaoEmail").value = obj.descricaoEmail;

		if(obj.principal == 'S' || obj.imagemEmailprincipal == 'S') {
			$("#divPrincipal").show();
			document.getElementById('email#principal').checked = true;
		} else if(document.getElementById('auxEmailPrincipal').value != '' && document.getElementById('auxEmailPrincipal').value > 0) {
			$("#divPrincipal").hide();
			document.getElementById('email#principal').checked = false;
		}
	}
};

function validaPrincipal() {
	var objEmail = HTMLUtils.getObjectsByTableId('tblEmail');

		for(var i = 0; i < objEmail.length; i++){
			var obj = objEmail[i];
			if (obj.principal == 'S') {
				document.getElementById('divPrincipal').style.display = 'none';
				return;
			}
		}
		document.getElementById('divPrincipal').style.display = 'block';
}

//--------------------------------------- FORMACAO ---------------------------------------

function excluirFormacao(rowIndex){
	HTMLUtils.deleteRow('tblFormacao', rowIndex);
	limpaFormacao
};

function addFormacao(){
	//Valida as Informacoes digitadas do Formacao
	if (StringUtils.isBlank(document.getElementById('formacao#idTipoFormacao').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("requisicaoMudanca.tipo")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('formacao#idTipoFormacao').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('formacao#idSituacao').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("requisitosla.situacao")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('formacao#idSituacao').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('formacao#instituicao').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.instituicaoEnsino")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('formacao#instituicao').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('formacaoDescricao').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.nomeDoCurso")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('formacaoDescricaoaddIdioma()').focus();
		return;
	}

	if(document.getElementById('rowIndexFormacao').value == null ||  document.getElementById('rowIndexFormacao').value == undefined || document.getElementById('rowIndexFormacao').value < 1) {
		var obj = new CIT_FormacaoCurriculoDTO();

	obj.descricaoTipoFormacao = document.getElementById('formacao#idTipoFormacao').options[document.getElementById('formacao#idTipoFormacao').selectedIndex].text;
	obj.descricaoSituacao = document.getElementById('formacao#idSituacao').options[document.getElementById('formacao#idSituacao').selectedIndex].text;
	obj.descricao = document.getElementById('formacaoDescricao').value;
	obj.instituicao = document.getElementById('formacao#instituicao').value;

		HTMLUtils.addRow('tblFormacao', document.formPesquisaCurriculo, 'formacao', obj, ['descricaoTipoFormacao', 'descricaoSituacao', 'instituicao', 'descricao',''], null, '', [gerarImagemDelFormacao], funcaoClickRowFormacao, null, false);
	} else {
		var obj = HTMLUtils.getObjectByTableIndex('tblFormacao', document.getElementById('rowIndexFormacao').value);

		obj.descricaoTipoFormacao = document.getElementById('formacao#idTipoFormacao').options[document.getElementById('formacao#idTipoFormacao').selectedIndex].text;
		obj.descricaoSituacao = document.getElementById('formacao#idSituacao').options[document.getElementById('formacao#idSituacao').selectedIndex].text;
		obj.descricao = document.getElementById('formacaoDescricao').value;
		obj.instituicao = document.getElementById('formacao#instituicao').value;

		HTMLUtils.updateRow('tblFormacao', document.formPesquisaCurriculo, 'formacao', obj, ['descricaoTipoFormacao' , 'descricaoSituacao', 'instituicao', 'descricao',''], null, '', [gerarImagemDelFormacao], funcaoClickRowFormacao, null, document.getElementById('rowIndexFormacao').value, false);

		document.getElementById('rowIndexFormacao').value = null;
	}

	HTMLUtils.applyStyleClassInAllCells('tblFormacao', 'celulaGrid');
	limpaFormacao();
};

function funcaoClickRowFormacao(row, obj) {
	if(obj == null) {
		document.getElementById('rowIndexFormacao').value = null;
	} else {
		document.getElementById("rowIndexFormacao").value = $('#'+obj.idControleCITFramework).index();

		$('[id="formacao#idTipoFormacao"]').find('option[value="' + obj.idTipoFormacao + '"]').attr("selected", true);
		$('[id="formacao#idSituacao"]').find('option[value="' + obj.idSituacao + '"]').attr("selected", true);
		document.getElementById("formacao#instituicao").value = obj.instituicao;
		document.getElementById("formacaoDescricao").value = obj.descricao;
	}
};


//--------------------------------------- CERTIFICACAO ---------------------------------------
function marcaCertificacao(row, obj){
}

function excluirCertificacao(rowIndex){
	HTMLUtils.deleteRow('tblCertificacao', rowIndex);
	limpaCertificacao();
};

function addCertificacao(){
	//Valida as Informacoes digitadas do Telefone
	if (StringUtils.isBlank(document.getElementById('certificacao#descricao').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("ManualFuncao.Certificacao")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('certificacao#descricao').focus();
		return;
	}

	if (!StringUtils.isBlank(document.getElementById('certificacao#validade').value)){
		var  validade = document.getElementById('certificacao#validade').value;
		if (validade.length <= 3) {
			alert(i18n_message("rh.validadeCertificacaoInvalida"));
			document.getElementById('certificacao#validade').focus();
			return;
		}
	}

	if(document.getElementById('rowIndexCertificacao').value == null ||  document.getElementById('rowIndexCertificacao').value == undefined || document.getElementById('rowIndexCertificacao').value < 1) {
		var obj = new CIT_CertificacaoCurriculoDTO();

	obj.descricao = document.getElementById('certificacao#descricao').value;
	obj.versao = document.getElementById('certificacao#versao').value;
	obj.validade = document.getElementById('certificacao#validade').value;

		HTMLUtils.addRow('tblCertificacao', document.formPesquisaCurriculo, 'certificacao', obj, ['descricao' , 'versao' , 'validade', '' ], null, '', [gerarImagemDelCertificacao], funcaoClickRowCertificacao, null, false);
	} else {
		var obj = HTMLUtils.getObjectByTableIndex('tblCertificacao', document.getElementById('rowIndexCertificacao').value);

		obj.descricao = document.getElementById('certificacao#descricao').value;
		obj.versao = document.getElementById('certificacao#versao').value;
		obj.validade = document.getElementById('certificacao#validade').value;

		HTMLUtils.updateRow('tblCertificacao', document.formPesquisaCurriculo, 'certificacao', obj, ['descricao' , 'versao' , 'validade', '' ], null, '', [gerarImagemDelCertificacao], funcaoClickRowCertificacao, null, document.getElementById('rowIndexCertificacao').value, false);

		document.getElementById('rowIndexCertificacao').value = null;
	}

	limpaCertificacao();
};

function funcaoClickRowCertificacao(row, obj) {
	if(obj == null) {
		document.getElementById('rowIndexCertificacao').value = null;
	} else {
		document.getElementById('rowIndexCertificacao').value = $('#'+obj.idControleCITFramework).index();

		document.getElementById('certificacao#descricao').value = obj.descricao;
		document.getElementById('certificacao#versao').value = obj.versao;
		document.getElementById('certificacao#validade').value = obj.validade;
	}
};

//--------------------------------------- Treinamento ---------------------------------------
function marcaCertificacao(row, obj){
}

function excluirTreinamento(rowIndex){
	HTMLUtils.deleteRow('tblTreinamento', rowIndex);
//	limpaTreinamento();
};




function addTreinamento(){

	//Valida as Informacoes digitadas do Telefone
	if (StringUtils.isBlank(document.getElementById('treinamento').value)){
		alert(i18n_message("rh.informeTreinamento"));
		document.getElementById('treinamento').value = "";
		document.getElementById('treinamento').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('idCurso').value)){
		alert(i18n_message("rh.TreinamentoInvalido"));
		document.getElementById('treinamento').value = "";
		document.getElementById('treinamento').focus();
		return;
	}

	document.formPesquisaCurriculo.fireEvent('buscaTreinamento');

};

//--------------------------------------- IDIOMA ---------------------------------------
function addIdioma(){
	// Variaveis
	var rowIndexIdioma = document.getElementById("rowIndexIdioma").value;

	//Valida as Informacoes digitadas do Idioma

	// Verifica se eh uma atualizacao
	if(rowIndexIdioma != "" || rowIndexIdioma != null) {
		// Se for atualizacao entra aqui
		// Testa se trocou o idioma
		if(!$('#tblIdioma tr:eq(' + rowIndexIdioma + '):contains(' + StringUtils.trim(document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].text) + ')').length) {
			// se trocou verifica se eh repetido
			if($("#tblIdioma:contains('" + StringUtils.trim(document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].text) + "')").length > 0) {
				alert(i18n_message("rh.idiomaJaAdicionado"));
				return ;
			}
		}
	} else if($("#tblIdioma:contains('" + StringUtils.trim(document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].text) + "')").length > 0) {
		alert(i18n_message("rh.idiomaJaAdicionado"));
		return ;
	}

	if (StringUtils.isBlank(document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("lingua.lingua")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('idioma#idIdioma').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.escrita")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('idioma#idNivelEscrita').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.leitura")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('idioma#idNivelLeitura').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.conversacao")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('idioma#idNivelConversa').focus();
		return;
	}

	if(rowIndexIdioma == null ||  rowIndexIdioma == undefined || rowIndexIdioma < 1) {
		var obj = new CIT_IdiomaCurriculoDTO();

		obj.descricaoIdioma = document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].text;
		obj.nivelEscrita = document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].text;
		obj.nivelLeitura = document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].text;
		obj.nivelConversa = document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].text;
		obj.descNivelEscrita = document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].text;
		obj.descNivelLeitura = document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].text;
		obj.descNivelConversa = document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].text;

		var strNivel = '<table width="100%"><tr><td class="celulaGrid"><b>'+i18n_message("rh.escrita")+': </b>' + obj.nivelEscrita + '</td></tr>';
		strNivel = strNivel + '<tr><td class="celulaGrid"><b>'+i18n_message("rh.leitura")+': </b>' + obj.nivelLeitura + '</td></tr>';
		strNivel = strNivel + '<tr><td class="celulaGrid"><b>'+i18n_message("rh.conversacao")+': </b>' + obj.nivelConversa + '</td></tr>';
		strNivel = strNivel + '</table>';

		obj.detalhamentoNivel = strNivel;

		HTMLUtils.addRow('tblIdioma', document.formPesquisaCurriculo, 'idioma', obj, ['descricaoIdioma', 'descNivelEscrita','descNivelLeitura', 'descNivelConversa',''], null, '', [gerarImagemDelIdioma], funcaoClickRowIdioma, null, false);
	} else {
		var obj = HTMLUtils.getObjectByTableIndex('tblIdioma', document.getElementById('rowIndexIdioma').value);

		obj.descricaoIdioma = document.getElementById('idioma#idIdioma').options[document.getElementById('idioma#idIdioma').selectedIndex].text;
		obj.nivelEscrita = document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].text;
		obj.nivelLeitura = document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].text;
		obj.nivelConversa = document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].text;
		obj.descNivelEscrita = document.getElementById('idioma#idNivelEscrita').options[document.getElementById('idioma#idNivelEscrita').selectedIndex].text;
		obj.descNivelLeitura = document.getElementById('idioma#idNivelLeitura').options[document.getElementById('idioma#idNivelLeitura').selectedIndex].text;
		obj.descNivelConversa = document.getElementById('idioma#idNivelConversa').options[document.getElementById('idioma#idNivelConversa').selectedIndex].text;

		var strNivel = '<table width="100%"><tr><td class="celulaGrid"><b>'+i18n_message("rh.escrita")+': </b>' + obj.nivelEscrita + '</td></tr>';
		strNivel = strNivel + '<tr><td class="celulaGrid"><b>'+i18n_message("rh.leitura")+': </b>' + obj.nivelLeitura + '</td></tr>';
		strNivel = strNivel + '<tr><td class="celulaGrid"><b>'+i18n_message("rh.conversacao")+': </b>' + obj.nivelConversa + '</td></tr>';
		strNivel = strNivel + '</table>';

		obj.detalhamentoNivel = strNivel;

		HTMLUtils.updateRow('tblIdioma', document.formPesquisaCurriculo, 'idioma', obj, ['descricaoIdioma', 'descNivelEscrita','descNivelLeitura', 'descNivelConversa',''], null, '', [gerarImagemDelIdioma], funcaoClickRowIdioma, null, document.getElementById("rowIndexIdioma").value, false);

		document.getElementById("rowIndexIdioma").value = null;
	}

	limpaIdioma();
};

function funcaoClickRowIdioma(row, obj) {
	if(obj == null) {
		document.getElementById('rowIndexIdioma').value = null;
	} else {
		document.getElementById("rowIndexIdioma").value = $('#'+obj.idControleCITFramework).index();

		$('[id="idioma#idIdioma"]').find('option[value="' + obj.idIdioma + '"]').attr("selected", true);
		$('[id="idioma#idNivelEscrita"]').find('option[value="' + obj.idNivelEscrita + '"]').attr("selected", true);
		$('[id="idioma#idNivelLeitura"]').find('option[value="' + obj.idNivelLeitura + '"]').attr("selected", true);
		$('[id="idioma#idNivelConversa"]').find('option[value="' + obj.idNivelConversa + '"]').attr("selected", true);
	}
};

function MascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal, e){
	var sep = 0;
    var key = '';
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;
    var maxLength = objTextBox.getAttribute("maxlength") ? objTextBox.getAttribute("maxlength") : '';
    if(maxLength && (objTextBox.value).length == maxLength) {
    	return false;
    }

    if (whichCode == 13) return true;
    key = String.fromCharCode(whichCode); // Valor para o codigo da Chave
    if (strCheck.indexOf(key) == -1) return false; // Chave invalida
    len = objTextBox.value.length;
    for(i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) aux += objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) objTextBox.value = '';
    if (len == 1) objTextBox.value = '0'+ SeparadorDecimal + '0' + aux;
    if (len == 2) objTextBox.value = '0'+ SeparadorDecimal + aux;
    if (len > 2) {
        aux2 = '';
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = '';
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
        objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
    }
    return false;
}

function excluirIdioma(rowIndex){
	HTMLUtils.deleteRow('tblIdioma', rowIndex);
	limpaIdioma();
};




//--------------------------------------- ENDERECO  ---------------------------------------
function addEndereco(){
	var tbl = document.getElementById('tblEnderecos');

	//Valida as Informacoes digitadas do Endereco
	if (StringUtils.isBlank(document.getElementById('endereco#idTipoEndereco').options[document.getElementById('endereco#idTipoEndereco').selectedIndex].value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("visao.tipoEndereco")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('endereco#idTipoEndereco').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('cep').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("visao.cep")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('cep').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('endereco#logradouro').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("visao.logradouro")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('endereco#logradouro').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('enderecoIdUF').options[document.getElementById('enderecoIdUF').selectedIndex].value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.UF")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('enderecoIdUF').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('enderecoIdCidade').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("visao.cidade")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('enderecoIdCidade').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('endereco#nomeBairro').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("localidade.bairro")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('endereco#nomeBairro').focus();
		return;
	}



	if(document.getElementById('rowIndexEndereco').value == null ||  document.getElementById('rowIndexEndereco').value == undefined || document.getElementById('rowIndexEndereco').value < 1) {
		var obj = new CIT_EnderecoCurriculoDTO();

	obj.descricaoTipoEndereco = document.getElementById('endereco#idTipoEndereco').options[document.getElementById('endereco#idTipoEndereco').selectedIndex].text;

	if (document.getElementById('principal').checked){
		obj.principal = 'S';
	}else{
		obj.principal = 'N';
	}

	var strDetalhamento = '';
	if(document.getElementById('principal').checked){
		strDetalhamento = '<i>'+i18n_message("visao.correspondencia")+'</i>';
	}

	if(document.getElementById('endereco#idTipoEndereco').value == "1"){
		strDetalhamento = strDetalhamento +' <i>('+i18n_message("rh.residencial")+')</i><br>';
	}else{
		strDetalhamento = strDetalhamento +' <i>('+i18n_message("rh.comercial")+')</i><br>';
	}

	strDetalhamento = strDetalhamento + document.getElementById('endereco#logradouro').value + ', ' + document.getElementById('endereco#complemento').value +', '+ document.getElementById('endereco#nomeBairro').value + '<br>';
	strDetalhamento = strDetalhamento + document.getElementById('cep').value + ', ' + document.getElementById('enderecoIdCidade').options[document.getElementById('enderecoIdCidade').selectedIndex].text;
	strDetalhamento = strDetalhamento + ', ' + document.getElementById('enderecoIdUF').options[document.getElementById('enderecoIdUF').selectedIndex].text;
	strDetalhamento = strDetalhamento + ', ' + document.getElementById('pais').options[document.getElementById('pais').selectedIndex].text;

	obj.detalhamentoEndereco = strDetalhamento;
	obj.nomePais = document.getElementById('pais').options[document.getElementById('pais').selectedIndex].text;
	obj.logradouro = document.getElementById('endereco#logradouro').value;
	obj.nomeCidade = document.getElementById('enderecoIdCidade').options[document.getElementById('enderecoIdCidade').selectedIndex].text;
	obj.idCidade = document.getElementById('enderecoIdCidade').value;
	obj.complemento = document.getElementById('endereco#complemento').value;
	obj.cep = document.getElementById('cep').value;
	obj.nomeUF = document.getElementById('enderecoIdUF').options[document.getElementById('enderecoIdUF').selectedIndex].text;
	obj.idUf = document.getElementById('enderecoIdUF').value;
	obj.nomeBairro = document.getElementById('endereco#nomeBairro').value;

		if(obj.principal == 'S'){
			document.getElementById('auxEnderecoPrincipal').value = tbl.rows.length;
			$('#divEnderecoPrincipal').hide();
		}

		HTMLUtils.addRow('tblEnderecos', document.formPesquisaCurriculo, 'endereco', obj, ['detalhamentoEndereco' ,''], null, '', [gerarImagemDelEndereco], funcaoClickRowEndereco, null, false);
	} else {
		var obj = HTMLUtils.getObjectByTableIndex('tblEnderecos', document.getElementById('rowIndexEndereco').value);

		obj.descricaoTipoEndereco = document.getElementById('endereco#idTipoEndereco').options[document.getElementById('endereco#idTipoEndereco').selectedIndex].text;

		if (document.getElementById('principal').checked){
			obj.principal = 'S';
		}else{
			obj.principal = 'N';
		}

		var strDetalhamento = '';
		if(document.getElementById('principal').checked){
			strDetalhamento = '<i>'+i18n_message("visao.correspondencia")+'</i>';
		}

		if(document.getElementById('endereco#idTipoEndereco').value == "1"){
			strDetalhamento = strDetalhamento +' <i>('+i18n_message("rh.residencial")+')</i><br>';
		}else{
			strDetalhamento = strDetalhamento +' <i>('+i18n_message("rh.comercial")+')</i><br>';
		}

		strDetalhamento = strDetalhamento + document.getElementById('endereco#logradouro').value + ', ' + document.getElementById('endereco#complemento').value +', '+ document.getElementById('endereco#nomeBairro').value + '<br>';
		strDetalhamento = strDetalhamento + document.getElementById('cep').value + ', ' + document.getElementById('enderecoIdCidade').options[document.getElementById('enderecoIdCidade').selectedIndex].text;
		strDetalhamento = strDetalhamento + ', ' + document.getElementById('enderecoIdUF').options[document.getElementById('enderecoIdUF').selectedIndex].text;
		strDetalhamento = strDetalhamento + ', ' + document.getElementById('pais').options[document.getElementById('pais').selectedIndex].text;

		obj.detalhamentoEndereco = strDetalhamento;
		obj.nomePais = document.getElementById('pais').options[document.getElementById('pais').selectedIndex].text;
		obj.logradouro = document.getElementById('endereco#logradouro').value;
		obj.nomeCidade = document.getElementById('enderecoIdCidade').options[document.getElementById('enderecoIdCidade').selectedIndex].text;
		obj.idCidade = document.getElementById('enderecoIdCidade').value;
		obj.complemento = document.getElementById('endereco#complemento').value;
		obj.cep = document.getElementById('cep').value;
		obj.nomeUF = document.getElementById('enderecoIdUF').options[document.getElementById('enderecoIdUF').selectedIndex].text;
		obj.idUf = document.getElementById('enderecoIdUF').value;
		obj.nomeBairro = document.getElementById('endereco#nomeBairro').value;

	if(obj.principal == 'S'){
			document.getElementById('auxEnderecoPrincipal').value = document.getElementById('rowIndexEndereco').value;
		$('#divEnderecoPrincipal').hide();
	}

		HTMLUtils.updateRow('tblEnderecos', document.formPesquisaCurriculo, 'endereco', obj, ['detalhamentoEndereco' ,''], null, '', [gerarImagemDelEndereco], funcaoClickRowEndereco, null, document.getElementById('rowIndexEndereco').value, false);
		document.getElementById('rowIndexEndereco').value = null;
	}

	limpaEndereco();
	document.getElementById('endereco#idTipoEndereco').focus();
	document.formPesquisaCurriculo.fireEvent('preencherComboUfs');
	document.getElementById('principal').checked = false;
};

function funcaoClickRowEndereco(row, obj) {
	if(obj == null) {
		document.getElementById('rowIndexEmail').value = null;
	} else {
		rowIndex = $('#'+obj.idControleCITFramework).index();
		obj = HTMLUtils.getObjectByTableIndex('tblEnderecos', rowIndex);
		document.getElementById("rowIndexEndereco").value = rowIndex;

		$("[id='endereco#idTipoEndereco']").find("option[value='" + obj.idTipoEndereco + "']").attr("selected", "selected");
		document.getElementById("cep").value = obj.cep;
		$("[id='pais']").find("option:contains('" + obj.nomePais + "')").attr("selected", "selected");
		$.ajax({
			url: window.location.protocol + "//" + window.location.host + (window.location.pathname).replace(".load", ".event"),
			data: {'method': 'execute', 'parmCount': '', 'pais': $("[id='pais']").val(), 'parm1': 'templateCurriculoTrabalheConosco', 'parm2': '', 'parm3': 'getUFsJSON', 'nocache': new Date()},
			type: 'POST',
			dataType: 'json',
			success: function(data) {
				HTMLUtils.removeAllOptions("enderecoIdUF");
				HTMLUtils.addOption("enderecoIdUF", i18n_message("citcorpore.comum.selecione"));
				$.map(data, function(item) {
					HTMLUtils.addOption("enderecoIdUF", item.nomeUf, item.idUf);
				});
			},
			error: function() {
				console.log('error!');
			}
		}).done(function() {
			$("[id='enderecoIdUF']").find("option:contains('" + obj.nomeUF + "')").attr("selected", "selected");
		});

		$.ajax({
			url: window.location.protocol + "//" + window.location.host + (window.location.pathname).replace(".load", ".event"),
			data: {'method': 'execute', 'parmCount': '', 'hiddenIdUf': obj.idUf, 'parm1': 'templateCurriculoTrabalheConosco', 'parm2': '', 'parm3': 'getCidadesJSON', 'nocache': new Date()},
			type: 'POST',
			dataType: 'json',
			success: function(data) {
				HTMLUtils.removeAllOptions("enderecoIdCidade");
				HTMLUtils.addOption("enderecoIdCidade", i18n_message("citcorpore.comum.selecione"));
				$.map(data, function(item) {
					HTMLUtils.addOption("enderecoIdCidade", item.nomeCidade, item.idCidade);
				});
			},
			error: function() {
				console.log('error!');
			}
		}).done(function() {
			$("[id='enderecoIdCidade']").find("option[value='" + obj.idCidade + "']").attr("selected", "selected");
		});

		document.getElementById("endereco#logradouro").value = obj.logradouro;
		document.getElementById("endereco#complemento").value = obj.complemento;
		document.getElementById("endereco#nomeBairro").value = obj.nomeBairro;

		if($(row).siblings('tr').find('td:first-child:contains("'+i18n_message("visao.correspondencia")+'")').length > 0) {
			$('#divEnderecoPrincipal').hide();
			document.getElementById('principal').checked = false;
		} else {
			$('#divEnderecoPrincipal').show();
		}

		if(obj.principal == 'S') {
			document.getElementById('principal').checked = true;
		}
	}
};

function atuEndereco(){
};

function novoEndereco(){
	limpaEndereco();
	document.getElementById('divAdd').style.display = 'block';
	document.getElementById('divAltera').style.display = 'none';
	HTMLUtils.setValueColumnTable('tblEnderecos', '&nbsp;', 1, null, 0);
	document.getElementById('endereco#idTipoEndereco').focus();
};

function limpaEndereco(){
	document.getElementById('endereco#logradouro').value='';
	document.getElementById('cep').value='';
	document.getElementById('pais').value='';
	document.getElementById('endereco#complemento').value='';
	document.getElementById('endereco#idTipoEndereco').value='';
	document.getElementById('principal').value='';
	document.getElementById('enderecoIdCidade').value='';
	document.getElementById('endereco#nomeBairro').value='';
	document.getElementById('enderecoIdUF').value='';

//	HTMLUtils.removeAllOptions('enderecoIdUF');
	HTMLUtils.removeAllOptions('enderecoIdCidade');

};

function limpaFormacao(){
	document.getElementById('formacao#idTipoFormacao').value='';
	document.getElementById('formacao#idSituacao').value='';
	document.getElementById('formacao#instituicao').value='';
	document.getElementById('formacaoDescricao').value='';
};

 function limpaCertificacao(){
	document.getElementById('certificacao#descricao').value = '';
	document.getElementById('certificacao#versao').value = '';
	document.getElementById('certificacao#validade').value = '';
	document.getElementById('certificacao#descricao').focus();
};

function limpaTreinamento(){
	document.getElementById('Treinamento').value = '';
};

function limpaExperiencia(){
	document.getElementById('experiencia#empresa').value='';
	document.getElementById('experiencia#localidade').value='';
	document.getElementById('experiencia#descricaoFuncao').value = '';
	document.getElementById('experiencia#idMesInicio').value='';
	document.getElementById('experiencia#anoInicio').value='';
	document.getElementById('experiencia#anoInicio').value='';
	document.getElementById('experiencia#idMesFim').value='';
	document.getElementById('experiencia#anoFim').value='';
	document.formPesquisaCurriculo.indexExp.value = '';
};

function limpaExperienciaFuncao(){
	document.getElementById('experiencia#descricaoFuncao').value='';
	document.getElementById('experiencia#nomeFuncao').value='';
	document.formPesquisaCurriculo.indexDesFunc.value = '';
};

function limpaTabelaFuncao(){
	HTMLUtils.deleteAllRows('tblDescFuncao');
};

 function limpaIdioma(){
	document.getElementById('idioma#idNivelEscrita').value = '';
	document.getElementById('idioma#idNivelLeitura').value = '';
	document.getElementById('idioma#idNivelConversa').value = '';

	document.getElementById('idioma#idIdioma').value = '';
};

function limpaCompetencia(){
	document.getElementById('competenciaDescricao').value='';
	document.getElementById('competenciaNivel').value='';
};

function marcaEndereco(row, obj){
}

excluirEndereco = function(rowIndex,  principal){
	if(principal == "S"){
		document.getElementById('auxEnderecoPrincipal').value = '';
		 $('#divEnderecoPrincipal').show();
	}
	HTMLUtils.deleteRow('tblEnderecos', rowIndex);
	limpaEndereco();
	document.getElementById('divAdd').style.display = 'block';
	document.getElementById('divAltera').style.display = 'none';

};


function habilitaDivGraduacao() {
	document.getElementById('divPos').style.display = 'none';
	document.getElementById('divGraduacao').style.display = 'block';
}
function habilitaDivPos() {
	document.getElementById('divPos').style.display = 'block';
	document.getElementById('divGraduacao').style.display = 'none';
}
function desabilitaDiv() {
	document.getElementById('divPos').style.display = 'none';
	document.getElementById('divGraduacao').style.display = 'none';
}
function calculaIdade(){
	document.getElementById('spnIdade').innerHTML = '';
	if (!StringUtils.isBlank(document.getElementById('dataNascimento').value)){
		document.formPesquisaCurriculo.fireEvent('calculaIdade');
	}
};


//--------------------------------------- Experiencias ---------------------------------------

function templateExperienciasHTML() {
	var html = '';
	html += '<div class="widget widget-experiencias">';
	html += '	<span class="close close-experiencias">&times;</span>';
	html += '	<div class="widget-head">';
	html += '		<h4 class="heading">' + i18n_message("rh.empresa") + ' <strong class="widget-experiencias-count">' + ($('.widget-experiencias').length + 1) + '</strong></h4>';
	html += '	</div><!-- .widget-head -->';
	html += '	<div class="widget-body">';
	html += '		<div class="row-fluid">';
	html += '			<div class="span6">';
	html += '				<!-- nome da empresa -->';
	html += '				<label class="strong campoObrigatorio">' + i18n_message("lookup.nomeEmpresa") + '</label>';
	html += '				<div class="controls">';
	html += '					<input type="text" class="span12 experiencia-empresa" value="" name="experiencia-empresa[]" maxlength="80" />';
	html += '				</div><!-- nome da empresa -->';
	html += '			</div><!-- .span6 -->';
	html += '			<div class="span6">';
	html += '				<!-- localidade -->';
	html += '				<label class="strong campoObrigatorio">' + i18n_message("menu.nome.localidade") + '</label>';
	html += '				<div class="controls">';
	html += '					<input type="text" class="span12 experiencia-localidade" value="" name="experiencia-localidade[]" maxlength="80" />';
	html += '				</div><!-- localidade -->';
	html += '			</div><!-- .span6 -->';
	html += '		</div><!-- .row-fluid -->';
	html += '		<div class="widget-funcoes-container">';
	html += '			' + templateFuncoesHTML(true);
	html += '		</div><!-- .widget-funcoes-container -->';
	html += '		<button id="add-funcoes-item" type="button" class="btn btn-small btn-primary btn-icon glyphicons circle_plus"><i></i>' + i18n_message("rh.adicionarFuncao") + '</button>';
	html += '	</div><!-- .widget-body -->';
	html += '</div><!-- .widget-experiencias -->';

	return html;
}

function templateFuncoesHTML(botaoClose, parent) {
	var html = '';
	html += '<div class="widget widget-funcoes">';
	if(botaoClose) {
		html += '	<span class="close close-funcoes">&times;</span>';
	}
	html += '	<div class="widget-head">';
	if(parent) {
	html += '		<h4 class="heading">' + i18n_message("rh.funcao") + ' <strong class="widget-funcoes-count">' + (parent.find('.widget-funcoes').length + 1) + '</strong></h4>';
	} else {
	html += '		<h4 class="heading"><strong class="widget-funcoes-count">1</strong> - ' + i18n_message("rh.funcao") + '</h4>';
	}
	html += '	</div><!-- .widget-head -->';
	html += '	<div class="widget-body">';
	html += '		<div class="row-fluid">';
	html += '			<div class="">';
	html += '				<label class="strong campoObrigatorio">' + i18n_message("rh.funcao") + '</label>';
	html += '				<div class="controls">';
	html += '					<input type="text" class="span12 experiencia-nomeFuncao" value="" name="experiencia-nomeFuncao[]" maxlength="80">';
	html += '				</div><!-- .controls -->';
	html += '			</div>';
	html += '			<div class="">';
	html += '				<label class="strong campoObrigatorio">' + i18n_message("curriculo.descricaoFuncao") + '</label>';
	html += '				<div class="controls">';
	html += '					<textarea rows="" cols="" class="span12 experiencia-descricaoFuncao" value="" name="experiencia-descricaoFuncao[]" maxlength="600"></textarea>';
	html += '				</div><!-- .controls -->';
	html += '			</div>';
	html += '		</div><!-- .row-fluid -->';
	html += '		<div class="row-fluid">';
	html += '			<div class="row-fluid">';
	html += '				<div class="span6">';
	html += '					<label class="strong campoObrigatorio">' + i18n_message("citcorpore.texto.periodo") + '</label>';
	html += '				</div><!-- .span6 -->';
	html += '				<div class="span6">';
	html += '					<label class="checkbox inline diasAtuais-label">';
	html += '						<input type="checkbox" name="diasAtuais" class="diasAtuais send_left" value="s" />&nbsp;' + i18n_message("rh.dias.atuais");
	html += '					</label>';
	html += '				</div><!-- .span6 -->';
	html += '			</div><!-- .row-fluid -->';
	html += '			<div class="row-fluid">';
	html += '				<div class="span3">';
	html += '					<label for="experiencia-mesInicio" class="strong campoObrigatorio">' + i18n_message("rh.mesInicio") + '</label>';
	html += '					<select name="experiencia-mesInicio[]" class="span12 experiencia-mesInicio">';
	html += '						<option value="">' + i18n_message("citcorpore.comum.selecione") + '</option>';
	html += '						<option value="1">' + i18n_message("citcorpore.texto.mes.janeiro") + '</option>';
	html += '						<option value="2">' + i18n_message("citcorpore.texto.mes.fevereiro") + '</option>';
	html += '						<option value="3">' + i18n_message("citcorpore.texto.mes.marco") + '</option>';
	html += '						<option value="4">' + i18n_message("citcorpore.texto.mes.abril") + '</option>';
	html += '						<option value="5">' + i18n_message("citcorpore.texto.mes.maio") + '</option>';
	html += '						<option value="6">' + i18n_message("citcorpore.texto.mes.junho") + '</option>';
	html += '						<option value="7">' + i18n_message("citcorpore.texto.mes.julho") + '</option>';
	html += '						<option value="8">' + i18n_message("citcorpore.texto.mes.agosto") + '</option>';
	html += '						<option value="9">' + i18n_message("citcorpore.texto.mes.setembro") + '</option>';
	html += '						<option value="10">' + i18n_message("citcorpore.texto.mes.outubro") + '</option>';
	html += '						<option value="11">' + i18n_message("citcorpore.texto.mes.novembro") + '</option>';
	html += '						<option value="12">' + i18n_message("citcorpore.texto.mes.dezembro") + '</option>';
	html += '					</select>';
	html += '				</div><!-- .span3 -->';
	html += '				<div class="span2">';
	html += '					<label for="experiencia-anoInicio" class="strong campoObrigatorio">' + i18n_message("rh.anoInicio") + '</label>';
	html += '					<input type="text" value="" onkeypress="return somenteNumero(event);" class="span12 experiencia-anoInicio" name="experiencia-anoInicio[]" maxlength="4" />';
	html += '				</div><!-- .span12 -->';
	html += '				<div class="escondedatafinal">';
	html += '					<span></span><!-- So foi adicionado para alinhar o elemento .diasAtuais -->';
	html += '					<div class="span1 div-a-container">';
	html += '						<label for="div_a" class="span12"></label>';
	html += '						<div class="span12" name="div_a" style="text-align: center;">a</div>';
	html += '					</div><!-- .span1 -->';
	html += '					<div class="span3">';
	html += '						<label class="strong campoObrigatorio">' + i18n_message("rh.mesFim") + '</label>';
	html += '						<select name="experiencia-mesFim[]" class="span12 experiencia-mesFim">';
	html += '							<option value="">' + i18n_message("citcorpore.comum.selecione") + '</option>';
	html += '							<option value="1">' + i18n_message("citcorpore.texto.mes.janeiro") + '</option>';
	html += '							<option value="2">' + i18n_message("citcorpore.texto.mes.fevereiro") + '</option>';
	html += '							<option value="3">' + i18n_message("citcorpore.texto.mes.marco") + '</option>';
	html += '							<option value="4">' + i18n_message("citcorpore.texto.mes.abril") + '</option>';
	html += '							<option value="5">' + i18n_message("citcorpore.texto.mes.maio") + '</option>';
	html += '							<option value="6">' + i18n_message("citcorpore.texto.mes.junho") + '</option>';
	html += '							<option value="7">' + i18n_message("citcorpore.texto.mes.julho") + '</option>';
	html += '							<option value="8">' + i18n_message("citcorpore.texto.mes.agosto") + '</option>';
	html += '							<option value="9">' + i18n_message("citcorpore.texto.mes.setembro") + '</option>';
	html += '							<option value="10">' + i18n_message("citcorpore.texto.mes.outubro") + '</option>';
	html += '							<option value="11">' + i18n_message("citcorpore.texto.mes.novembro") + '</option>';
	html += '							<option value="12">' + i18n_message("citcorpore.texto.mes.dezembro") + '</option>';
	html += '						</select>';
	html += '					</div><!-- .span3 -->';
	html += '					<div class="span2">';
	html += '						<label for="experiencia-anoFim" class="strong campoObrigatorio">' + i18n_message("rh.anoFim") + '</label>';
	html += '						<input type="text" value="" class="span12 experiencia-anoFim"  onkeypress="return somenteNumero(event);" name="experiencia-anoFim[]" maxlength="4" />';
	html += '					</div><!-- .span12 -->';
	html += '				</div><!-- .escondedatafinal -->';
	html += '			</div><!-- .row-fluid -->';
	html += '		</div><!-- .row-fluid -->';
	html += '	</div><!-- .widget-body -->';
	html += '</div><!-- .widget -->';
	return html;
}

function getObjectExperiencias() {
	var experiencias = new Array();
	if(!$("#experiencia-nao-possui").is(":checked")) {
		$('.widget-experiencias').each(function(i) {
			var widgetExperiencias = $(this);

			var experiencia = new Object();

			experiencia.descricaoEmpresa = widgetExperiencias.find('.experiencia-empresa').val();
			experiencia.localidade = widgetExperiencias.find('.experiencia-localidade').val();

			var funcoes = new Array();

			widgetExperiencias.find('.widget-funcoes').each(function(j) {
				widgetFuncoes = $(this);
				var funcao = new Object();
				funcao.nomeFuncao = widgetFuncoes.find('.experiencia-nomeFuncao').val();
				funcao.descricaoFuncao = widgetFuncoes.find('.experiencia-descricaoFuncao').val();

				var inicioFuncao = new Date(widgetFuncoes.find('.experiencia-anoInicio').val(), (widgetFuncoes.find('.experiencia-mesInicio').val() - 1));
				funcao.inicioFuncao = DateTimeUtil.formatDate(inicioFuncao, "yyyy-MM-dd");

				if(!widgetFuncoes.find('.diasAtuais').is(':checked')) {
					var fimFuncao = new Date(widgetFuncoes.find('.experiencia-anoFim').val(), widgetFuncoes.find('.experiencia-mesFim').val() - 1);
					funcao.fimFuncao = DateTimeUtil.formatDate(fimFuncao, "yyyy-MM-dd");
				} else {
					funcao.fimFuncao = "";
				}

				funcoes.push(funcao);
			});

			experiencia.colFuncaoSerialize = ObjectUtils.serializeObjects(funcoes);

			experiencias.push(experiencia);
		});
	}

	return experiencias;
}

// Nao possui experiencia
$('body').on('change', '#experiencia-nao-possui', function() {
	if($(this).is(':checked')){
		$('#experiencia-container').addClass('disable');
	} else {
		$('#experiencia-container').removeClass('disable');
	}
});

$("body").on("focus", ".widget-experiencias", function() {
	$(this).removeClass("error");
});

$("body").on("focus", ".widget-funcoes", function() {
	$(this).removeClass("error");
});

// Adicionar empresa
$('body').on('click', '#add-experiencias-item', function() {
	if($('.widget-experiencias').length == 10) {
		alert("Voc� j� adicionou 10 empresas!\nEste � o limite!");
		return false;
	}

	$(this).before(templateExperienciasHTML());

	$('.widget-experiencias').fadeIn(1000);
});

//Remove uma empresa
$('body').on('click', '.close-experiencias', function() {
	$(this).parents('.widget-experiencias').fadeOut(500, function() {
		$(this).remove();

		for(i = 0; i < $('.widget-experiencias').length; i++) {
			$('.widget-experiencias:eq(' + i + ')').find('.widget-experiencias-count').empty().append(i+1);
		}
	});
});

// Adiciona uma fun��o
$('body').on('click', '#add-funcoes-item', function() {
	if($('.widget-funcoes').length == 10) {
		alert("Voc� j� adicionou 10 fun��es!\nEste � o limite!");
		return false;
	}

	$(this).siblings(".widget-funcoes-container").append(templateFuncoesHTML(true, $(this).siblings(".widget-funcoes-container")));

	$('.widget-funcoes').fadeIn(1000);
});

// Remove uma fun��o
$('body').on('click', '.close-funcoes', function() {
	parent = $(this).parents('.widget-funcoes-container');

	$(this).parents('.widget-funcoes').fadeOut(500, function() {
		$(this).remove();

		count = 1;
		parent.find('.widget-funcoes').each(function() {
			console.log("WTF!");
			$(this).find('.widget-funcoes-count').empty().append(count++);
		});
	});
});

//--------------------------------------- Experiencias ---------------------------------------

function excluirCompetencia(rowIndex){
	HTMLUtils.deleteRow('tblCompetencia', rowIndex);
	limpaCompetencia();
};

function addCompetencia(){
	if (StringUtils.isBlank(document.getElementById('competenciaDescricao').value)){
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.competencia")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('competenciaDescricao').focus();
		return;
	}

	if (StringUtils.isBlank(document.getElementById('competenciaNivel').value)){
		alert(i18n_message("rh.alertOCampo")+" [N�vel] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.getElementById('competenciaNivel').focus();
		return;
	}

	if(document.getElementById('rowIndexCompetencia').value == null ||  document.getElementById('rowIndexCompetencia').value == undefined || document.getElementById('rowIndexCompetencia').value < 1) {
		var obj = new CIT_CompetenciaCurriculoDTO();

		obj.descricaoCompetencia = document.getElementById('competenciaDescricao').value;
		obj.nivelCompetencia = document.getElementById('competenciaNivel').value;
		obj.nivelCompetenciaDesc = document.getElementById('competenciaNivel').options[document.getElementById('competenciaNivel').selectedIndex].text;

		HTMLUtils.addRow('tblCompetencia', document.formPesquisaCurriculo, 'competencia', obj, ['descricaoCompetencia', 'nivelCompetenciaDesc',''], null, '', [gerarImagemDelCompetencia], funcaoClickRowCompetencia, null, false);
	} else {
		var obj = HTMLUtils.getObjectByTableIndex('tblCompetencia', document.getElementById('rowIndexCompetencia').value);

		obj.descricaoCompetencia = document.getElementById('competenciaDescricao').value;
		obj.nivelCompetencia = document.getElementById('competenciaNivel').value;
		obj.nivelCompetenciaDesc = document.getElementById('competenciaNivel').options[document.getElementById('competenciaNivel').selectedIndex].text;

		HTMLUtils.updateRow('tblCompetencia', document.formPesquisaCurriculo, 'competencia', obj, ['descricaoCompetencia', 'nivelCompetenciaDesc',''], null, '', [gerarImagemDelCompetencia], funcaoClickRowCompetencia, null, document.getElementById("rowIndexCompetencia").value, false);

		document.getElementById("rowIndexCompetencia").value = null;
	}

	limpaCompetencia();
};

function funcaoClickRowCompetencia(row, obj) {
	if(obj == null) {
		document.getElementById('rowIndexCompetencia').value = null;
	} else {
		document.getElementById("rowIndexCompetencia").value = $('#'+obj.idControleCITFramework).index();
		document.getElementById("competenciaDescricao").value = obj.descricaoCompetencia;
		$("#competenciaNivel").find('option[value="' + obj.nivelCompetencia + '"]').attr("selected", true);
	}
};

// -------------- fotos ---------------------------------------
function submitFormFoto(){
	addEvent(document.getElementById("frameUploadFoto"),"load", carregouIFrame);

    document.formFoto.setAttribute("target","frameUploadFoto");
    document.formFoto.setAttribute("method","post");
    document.formFoto.setAttribute("enctype","multipart/form-data");
    document.formFoto.setAttribute("encoding","multipart/form-data");

    //submetendo
    document.formFoto.submit();
}

function carregouIFrame() {
	HTMLUtils.removeEvent(document.getElementById("frameUploadFoto"),"load", carregouIFrame);

	document.formPesquisaCurriculo.fireEvent('setaFoto');

	$("#POPUP_FICHA").dialog("close");
}

// ------------- autocomplete formacao ------------------

$(document).ready(function() {

	$('#formacaoDescricao').autocomplete({
		serviceUrl:'pages/autoCompleteFormacaoAcademica/autoCompleteFormacaoAcademica.load',
		noCache: true,
		onSelect: function(value, data){
			//document.form.clear();
			$('#idFormacaoAcademica').val(data);
			$('#formacaoDescricao').val(value);

		}
	});

	/*
	$('#treinamento').autocomplete({
		serviceUrl:'pages/autoCompleteCurso/autoCompleteCurso.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCurso').val(data);
			$('#descricaoCurso').val(value);
		}
	});*/
});

function limita(){
	var tamanho = (document.getElementById('formacaoDescricao').value).length;
	var tex=document.getElementById('formacaoDescricao').value;
	if (tamanho>=1000) {
		document.getElementById('formacaoDescricao').value=tex.substring(0,1000);
	}
	return true;
}

function habilitaDivGraduacao() {
	document.getElementById('divGraduacao').style.display = 'block';
}

function desabilitaDiv() {
	document.getElementById('divGraduacao').style.display = 'none';
}

//--------------------------- funcoes dos botoes de navegacao ----------------


function paginacao1(){
	pagina = 1;
	proximaPagina = 2;
	abrirAbaPagina1();
	document.getElementById('numeroPagina').value = pagina;
	$('#liPaginacao4').css("display" ,"block");
	$('#liPaginacao5').css("display" ,"none");
	$("#liPaginacao1").removeClass("disabled");
	$("#liPaginacao1").addClass("disabled");
	$("#liPaginacao2").addClass("disabled");
	$('#liPaginacao6').css("display" ,"block");

}

function paginacao2(){
	var todosPreenchidos = validarCamposPagina1();
	if (todosPreenchidos == true) {
		pagina = 2;
		proximaPagina = 3;
		abrirAbaPagina2();
		document.getElementById('numeroPagina').value = pagina;
		$('#liPaginacao4').css("display" ,"block");
		$('#liPaginacao5').css("display" ,"none");
		$("#liPaginacao1").removeClass("disabled");
		$("#liPaginacao2").removeClass("disabled");
		$('#liPaginacao6').css("display" ,"block");
		return true;
	}else{
		return false;
	}
}

function paginacao3(){
	var todosPreenchidos1 = validarCamposPagina1();
	if (todosPreenchidos1 == true) {
		var todosPreenchidos2 = validarCamposPagina2();
		if (todosPreenchidos2 == true) {
			pagina = 3;
			proximaPagina = 4;
			abrirAbaPagina3();
			document.getElementById('numeroPagina').value = pagina;
			$('#liPaginacao4').css("display" ,"block");
			$('#liPaginacao5').css("display" ,"none");
			$("#liPaginacao1").removeClass("disabled");
			$("#liPaginacao2").removeClass("disabled");
			$('#liPaginacao6').css("display" ,"block");
			return true;
		}else{
			$('#ulWizard li:eq(1) a').tab('show');
		}
	}else{
		$('#ulWizard a:first').tab('show');
	}
	return false;
}


function paginacao4(){
	var todosPreenchidos1 = validarCamposPagina1();
	if (todosPreenchidos1 == true) {
		var todosPreenchidos2 = validarCamposPagina2();
		if (todosPreenchidos2 == true) {
			var todosPreenchidos3 = validarCamposPagina3();
			if (todosPreenchidos3 == true) {
				pagina = 4;
				proximaPagina = 5;
				abrirAbaPagina4();
				document.getElementById('numeroPagina').value = pagina;
				$('#liPaginacao4').css("display" ,"block");
				$('#liPaginacao5').css("display" ,"none");
				$("#liPaginacao1").removeClass("disabled");
				$("#liPaginacao2").removeClass("disabled");
				$('#liPaginacao6').css("display" ,"block");
				return true;
			}else{
				$('#ulWizard li:eq(2) a').tab('show');
			}
		}else{
			$('#ulWizard li:eq(1) a').tab('show');
		}
	}else{
		$('#ulWizard a:first').tab('show');
	}
	return false;
}

function paginacao5(){

	var todosPreenchidos1 = validarCamposPagina1();

	if (todosPreenchidos1 == true) {

		var todosPreenchidos2 = validarCamposPagina2();
		if (todosPreenchidos2 == true) {

			var todosPreenchidos3 = validarCamposPagina3();
			if (todosPreenchidos3 == true) {

				var todosPreenchidos4 = validarCamposPagina4();
				if (todosPreenchidos4 == true) {

					pagina = 5;
					proximaPagina = 0;
					abrirAbaPagina5();
					document.getElementById('numeroPagina').value = pagina;
					$('#liPaginacao4').css("display" ,"none");
					$('#liPaginacao5').css("display" ,"block");
					$("#liPaginacao1").removeClass("disabled");
					$("#liPaginacao2").removeClass("disabled");
					$('#liPaginacao6').css("display" ,"block");
					return true;

				}else{
					pagina = 4;
					$('#ulWizard li:eq(3) a').tab('show');
					desabilitarBT();
				}
			}else{
				pagina = 3;
				$('#ulWizard li:eq(2) a').tab('show');
				desabilitarBT();
			}
		}else{
			pagina = 2;
			$('#ulWizard li:eq(1) a').tab('show');
			desabilitarBT();
		}
	}else{
		pagina = 1;
		$('#ulWizard a:first').tab('show');
	}

	return false;

}

function avancar(){

	itensEmail = HTMLUtils.getObjectsByTableId('tblEmail');
	console.log(itensEmail);

	for(i = 0; i < itensEmail.length; i++) {
		if(itensEmail[i].principal == "S" || itensEmail[i].imagemEmailprincipal == "S") {
			if(validaEmail(itensEmail[i].descricaoEmail)) {
				alert(i18n_message("rh.emailPrincipalJaCadastrado"));
				document.getElementById('email#descricaoEmail').focus();
				return;
			}
		}
	}

	if(pagina == 1){

		var todosPreenchidos2 = paginacao2();
		if (todosPreenchidos2 == true) {
			$("#wizardCurriculo").find("#li1").each(function() {
				$("#li1").removeClass("active");
				$("#li2").addClass("active");
				$("#tab1").removeClass("active");
				$('#tab2').addClass("active");
			});
			return;
		}
	}

	if(pagina == 2){

		var todosPreenchidos3 = paginacao3();

		if (todosPreenchidos3 == true) {
			$("#wizardCurriculo").find("#li2").each(function() {
				$("#li2").removeClass("active");
				$("#li3").addClass("active");
				$("#tab2").removeClass("active");
				$('#tab3').addClass("active");
			});
			return;
		}
	}

	if(pagina == 3){

	    var todosPreenchidos4 = paginacao4();

	    if (todosPreenchidos4 == true) {
			$("#wizardCurriculo").find("#li2").each(function() {
				$("#li3").removeClass("active");
				$("#li4").addClass("active");
				$("#tab3").removeClass("active");
				$('#tab4').addClass("active");
			});
			return;
	    }
	}

	if(pagina == 4){

		var todosPreenchidos5 = paginacao5();

		if (todosPreenchidos5 == true) {

			$("#wizardCurriculo").find("#li2").each(function() {

				$("#li4").removeClass("active");
				$("#li5").addClass("active");
				$("#tab4").removeClass("active");
				$('#tab5').addClass("active");
				$('#liPaginacao4').css("display" ,"none");
				$('#liPaginacao5').css("display" ,"block");
				$('#liPaginacao3').addClass("disabled");
				$('#liPaginacao5').removeClass("disabled");
			});

			return;
		}
	}

	if (pagina == 5) {

		$('#liPaginacao3').css("display" ,"none");
		$("#liPaginacao3").removeClass("disabled");

		$('#liPaginacao5').css("display" ,"block");
		$("#liPaginacao5").addClass("enabled");

		pagina = 1;
	}else{
		$('#liPaginacao3').css("display" ,"block");
		$('#liPaginacao5').css("display" ,"none");
		$("#liPaginacao5").addClass("disabled");
		$("#liPaginacao3").removeClass("disabled");
	}

}


function retroceder(){
	$("#liPaginacao3").removeClass("disabled");
	if(pagina == 5){
		$("#wizardCurriculo").find("#li5").each(function() {
			$("#li4").addClass("active");
			$("#li5").removeClass("active");
			$("#tab4").addClass("active");
			$('#tab5').removeClass("active");
		});
		paginacao4();
		return;
	}
	if(pagina == 4){
		$("#wizardCurriculo").find("#li4").each(function() {
			$("#li4").removeClass("active");
			$("#li3").addClass("active");
			$("#tab4").removeClass("active");
			$('#tab3').addClass("active");
		});
		paginacao3();
		return;
	}
	if(pagina == 3){
		$("#wizardCurriculo").find("#li2").each(function() {
			$("#li3").removeClass("active");
			$("#li2").addClass("active");
			$("#tab3").removeClass("active");
			$('#tab2').addClass("active");
		});
		paginacao2();
		return;
	}
	if(pagina == 2){
		$("#wizardCurriculo").find("#li1").each(function() {
			$("#li2").removeClass("active");
			$("#li1").addClass("active");
			$("#tab2").removeClass("active");
			$('#tab1').addClass("active");
			$('#liPaginacao4').css("display" ,"none");
			$('#liPaginacao5').css("display" ,"block");

		});
		paginacao1();
		return;
	}
}
	function avancarUltimo(){

		itensEmail = HTMLUtils.getObjectsByTableId('tblEmail');
		console.log(itensEmail);

		for(i = 0; i < itensEmail.length; i++) {
			if(itensEmail[i].principal == "S" || itensEmail[i].imagemEmailprincipal == "S") {
				if(validaEmail(itensEmail[i].descricaoEmail)) {
					alert(i18n_message("rh.emailPrincipalJaCadastrado"));
					document.getElementById('email#descricaoEmail').focus();
					return;
				}
			}
		}

		var todosPreenchidos5 = paginacao5();
		if (todosPreenchidos5 == true) {
			$("#li" + pagina).removeClass("active");
			$("#li5").addClass("active");
			$("#tab" + pagina).removeClass("active");
			$('#tab5').addClass("active");
			$('#liPaginacao4').css("display" ,"none");
			$('#liPaginacao5').css("display" ,"block");
			$("#liPaginacao1").removeClass("disabled");
			$("#liPaginacao3").addClass("disabled");
			$('#liPaginacao5').removeClass("disabled");
		}

	}

	function desabilitarBT() {
		$('#liPaginacao1').removeClass("disabled");
		$("#liPaginacao2").removeClass("disabled");
	}

	function primeiro(){
		$('#ulWizard a:first').tab('show');
		$('#liPaginacao1').addClass("disabled");
		$("#liPaginacao2").addClass("disabled");
		$("#liPaginacao3").removeClass("disabled");
		$("#liPaginacao4").removeClass("disabled");
		$("#liPaginacao5").addClass("disabled");
		$('#liPaginacao4').css("display" ,"block");
		$('#liPaginacao5').css("display" ,"none");
	}

	// ---------------------- gravar -------------------

	function gravar(){

        var itens = HTMLUtils.getObjectsByTableId('tblTelefones');
        document.formPesquisaCurriculo.colTelefones_Serialize.value = ObjectUtils.serializeObjects(itens);

        itens = HTMLUtils.getObjectsByTableId('tblEmail');
        document.formPesquisaCurriculo.colEmail_Serialize.value = ObjectUtils.serializeObjects(itens);

        itens = HTMLUtils.getObjectsByTableId('tblFormacao');
        document.formPesquisaCurriculo.colFormacao_Serialize.value = ObjectUtils.serializeObjects(itens);

        itens = HTMLUtils.getObjectsByTableId('tblCertificacao');
        document.formPesquisaCurriculo.colCertificacao_Serialize.value = ObjectUtils.serializeObjects(itens);

        itens = HTMLUtils.getObjectsByTableId('tblIdioma');
        document.formPesquisaCurriculo.colIdioma_Serialize.value = ObjectUtils.serializeObjects(itens);

        var itens2 = HTMLUtils.getObjectsByTableId('tblEnderecos');
        document.formPesquisaCurriculo.colEnderecos_Serialize.value = ObjectUtils.serializeObjects(itens2);

        itens = getObjectExperiencias();
        document.formPesquisaCurriculo.colExperienciaProfissional_Serialize.value = ObjectUtils.serializeObjects(itens);

        itens = HTMLUtils.getObjectsByTableId('tblCompetencia');
        document.formPesquisaCurriculo.colCompetencias_Serialize.value = ObjectUtils.serializeObjects(itens);

        itens = HTMLUtils.getObjectsByTableId('tblTreinamento');
        document.formPesquisaCurriculo.colTreinamento_Serialize.value = ObjectUtils.serializeObjects(itens);

        saidaNaoEsperada = false;

        mostrar_aguarde();

		document.formPesquisaCurriculo.save();
	}

function voltar(){

	window.location = URL_INITIAL + 'pages/trabalheConosco/trabalheConosco.load';

}

function restaurar(){
	document.formPesquisaCurriculo.fireEvent('restore');
}

function setarImgFoto(src){
	$("#divImgFoto").attr("src",src);
}


function delImgFoto(){

	document.getElementById("divImgFoto").innerHTML="";
	HTMLUtils.clearForm(document.formFoto);

	document.formPesquisaCurriculo.fireEvent('apagarFoto');

}


function validarCamposPagina1() {
	if (document.formPesquisaCurriculo.nome.value == '') {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("candidato.nomeCompleto")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.nome.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.sexo.value == '') {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("colaborador.sexo")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.sexo.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.estadoCivil.value == '0') {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.estadoCivil")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.estadoCivil.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.cpf.value == '') {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("lookup.cpf")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.cpf.focus();
		return false;
	}else{
		var resultado = validaCPFCurriculo(document.getElementById('cpf'),'');
		if (resultado == false) {
			document.formPesquisaCurriculo.cpf.focus();
			return;
		}
	}
	if (document.formPesquisaCurriculo.dataNascimento.value == '') {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("citcorpore.comum.dataNascimento")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.dataNascimento.focus();
		return false;
	}else{
		var resultado = ValidacaoUtils.validaData(document.getElementById('dataNascimento'),'');
		if (resultado == false) {
			return;
		}
	}
	if (document.formPesquisaCurriculo.nacionalidade.value == '') {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.nacionalidade")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.nacionalidade.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.filhos.value == '') {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.possuiFilhos")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.filhos.focus();
		return false;
	}
	if(document.getElementById("filhosS").checked) {
		if(StringUtils.isBlank(StringUtils.trim(document.formPesquisaCurriculo.qtdeFilhos.value))) {
			alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.quantosFilhos")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
			document.formPesquisaCurriculo.qtdeFilhos.focus();
			return ;
		}
	}

	if ($("[name=portadorNecessidadeEspecial]:checked").length <= 0) {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.informePne")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.portadorNecessidadeEspecial.focus();
		return false;
	}else{
		if (document.formPesquisaCurriculo.portadorNecessidadeEspecialS.checked) {
			if (document.formPesquisaCurriculo.idItemListaTipoDeficiencia.value == '') {
				alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("curriculo.pne")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
				document.formPesquisaCurriculo.idItemListaTipoDeficiencia.focus();
				return false;
			}
		}else{
			document.formPesquisaCurriculo.idItemListaTipoDeficiencia.value = '';
		}
	}

	if (document.formPesquisaCurriculo.idCidadeNatal.value == '') {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.cidadeNatal")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.idCidadeNatal.focus();
		return false;
	}
	if (document.formPesquisaCurriculo.idEstadoNatal.value == '') {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.UF")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		document.formPesquisaCurriculo.idEstadoNatal.focus();
		return false;
	}



	var qtdLinhasTabelaTelefone = $("#tblTelefones tr").length;
	if (qtdLinhasTabelaTelefone <= 1) {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.telefones")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		//document.formPesquisaCurriculo.telefone#ddd.focus();
		return false;
	}
	var qtdLinhasTabelaEmail = $("#tblEmail tr").length;
	if (qtdLinhasTabelaEmail <= 1) {
		alert(i18n_message("rh.alertOCampo")+" [Email] "+i18n_message("rh.alertEObrigatorio")+"!");
		//document.formPesquisaCurriculo.email#descricaoEmail.focus();
		return false;
	}
	var qtdLinhasTabelaEndereco = $("#tblEnderecos tr").length;
	if (qtdLinhasTabelaEndereco <= 1) {
		alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("visao.enderecos")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
		//document.formPesquisaCurriculo.endereco#idTipoEndereco.focus();
		return false;
	}
	return true;
}

function validarCamposPagina2() {
	var qtdLinhasTabelaFormcao = $("#tblFormacao tr").length;
	if (qtdLinhasTabelaFormcao <= 1) {
		alert(i18n_message("rh.informeFormacao"));
		return false;
	}
	return true;
}

function validarCamposPagina3() {
	if(!$("#experiencia-nao-possui").is(":checked")) {
		if($('.widget-experiencias').length > 0) {
			var sentinela = true;
			$('.widget-experiencias').each(function(i) {
				var widgetExperiencias = $(this);

				if(StringUtils.trim(widgetExperiencias.find('.experiencia-empresa').val()) == "") {
					alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("lookup.nomeEmpresa")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
					widgetExperiencias.addClass('error');
					sentinela = false;
					return false;
				} else if(StringUtils.trim(widgetExperiencias.find('.experiencia-localidade').val()) == "") {
					alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("menu.nome.localidade")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
					widgetExperiencias.addClass('error');
					sentinela = false;
					return false;
				} else {
					if(widgetExperiencias.find('.widget-funcoes').length > 0) {
						widgetExperiencias.find('.widget-funcoes').each(function(j) {
							widgetFuncoes = $(this);
							if(StringUtils.trim(widgetFuncoes.find('.experiencia-nomeFuncao').val()) == "") {
								alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.funcao")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
								widgetFuncoes.addClass('error');
								sentinela = false;
								return false;
							} else if(StringUtils.trim(widgetFuncoes.find('.experiencia-descricaoFuncao').val()) == "") {
								alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("curriculo.descricaoFuncao")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
								widgetFuncoes.addClass('error');
								sentinela = false;
								return false;
							} else if(StringUtils.trim(widgetFuncoes.find('.experiencia-mesInicio').val()) == "") {
								alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.mesInicio")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
								widgetFuncoes.addClass('error');
								sentinela = false;
								return false;
							} else if(StringUtils.trim(widgetFuncoes.find('.experiencia-anoInicio').val()) == "") {
								alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.anoInicio")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
								widgetFuncoes.addClass('error');
								sentinela = false;
								return false;
							} else if((StringUtils.trim(widgetFuncoes.find('.experiencia-anoInicio').val())).length < 4) {
								alert(i18n_message("rh.anoInvalido"));
								widgetFuncoes.addClass('error');
								sentinela = false;
								return false;
							} else if((StringUtils.trim(widgetFuncoes.find('.experiencia-mesFim').val()) == "") && (!widgetFuncoes.find('.diasAtuais').is(':checked'))) {
								alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.mesFim")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
								widgetFuncoes.addClass('error');
								sentinela = false;
								return false;
							} else if((StringUtils.trim(widgetFuncoes.find('.experiencia-anoFim').val()) == "") && (!widgetFuncoes.find('.diasAtuais').is(':checked'))) {
								alert(i18n_message("rh.alertOCampo")+" ["+i18n_message("rh.anoFim")+"] "+i18n_message("rh.alertEObrigatorio")+"!");
								widgetFuncoes.addClass('error');
								sentinela = false;
								return false;
							} else if((StringUtils.trim(widgetFuncoes.find('.experiencia-anoFim').val()).length < 4) && (!widgetFuncoes.find('.diasAtuais').is(':checked'))) {
								alert(i18n_message("rh.anoInvalido"));
								widgetFuncoes.addClass('error');
								sentinela = false;
								return false;
							} else if(((new Date(widgetFuncoes.find('.experiencia-anoInicio').val(), widgetFuncoes.find('.experiencia-mesInicio').val())).getTime() > (new Date(widgetFuncoes.find('.experiencia-anoFim').val(), widgetFuncoes.find('.experiencia-mesFim').val())).getTime()) && (!widgetFuncoes.find('.diasAtuais').is(':checked'))) {
								alert(i18n_message("rh.dataInicioMaior"));
								widgetFuncoes.addClass('error');
								sentinela = false;
								return false;
							}
						});
					} else {
						alert(i18n_message("rh.naoAdicionouFuncoes"));
						widgetFuncoes.addClass('error');
						sentinela = false;
						return false;
					}
				}
			});

			return sentinela;
		} else {
			alert(i18n_message("rh.naoAdicionouEmpresas"));
			return false;
		}
	} else {
		return true;
	}
}

function validarCamposPagina4() {
	/*
	 * renato.jesus
	 * Comentado para que informar competencia nao seja obrigatorio.
	 *
	var qtdLinhasTabelaCompetencia = $("#tblCompetencia tr").length;
	if (qtdLinhasTabelaCompetencia <= 1) {
		alert(i18n_message("rh.informeCompetencia"));
		//document.formPesquisaCurriculo.email#descricaoEmail.focus();
		return false;
	}*/

	/*var qtdLinhasTabelaCertificacoes = $("#tblCertificacao tr").length;
	if (qtdLinhasTabelaCertificacoes <= 1) {
		alert(i18n_message("rh.informeCertificacoes"));
		//document.formPesquisaCurriculo.email#descricaoEmail.focus();
		return false;
	}*/
	return true;
}

function abrirAbaPagina1() {
	$('#ulWizard a:first').tab('show');
}

function abrirAbaPagina2() {
	$("#li1").addClass("active");
	$("#li2").removeClass("active");

	$('#ulWizard li:eq(1) a').tab('show');

}

function abrirAbaPagina3() {
	$("#li2").addClass("active");
	$("#li3").removeClass("active");

	$('#ulWizard li:eq(2) a').tab('show');

}

function abrirAbaPagina4() {
	$("#li3").addClass("active");
	$("#li4").removeClass("active");

	$('#ulWizard li:eq(3) a').tab('show');
}

function abrirAbaPagina5() {
	$("#li4").addClass("active");
	$("#li5").removeClass("active");

	$('#ulWizard li:eq(4) a').tab('show');
}

function validaCPFCurriculo(field, label) {
	var cpf = field.value;

	if(StringUtils.isBlank(cpf)){
	     return true;
	}

	cpf = cpf.replace(".","");
	cpf = cpf.replace(".","");
	cpf = cpf.replace("-","");
	var erro = new String;
	if (cpf.length < 11) erro += label+i18n_message("rh.saoNecessariosDigitosVerificacaoCPF")+"\n\n";
	var nonNumbers = /\D/;
	if (nonNumbers.test(cpf)) erro += label+i18n_message("rh.verificacaoCPFSuportaApenasNumeros")+" \n\n";
	if (cpf == "00000000000" || cpf == "11111111111" || cpf == "22222222222" || cpf == "33333333333" || cpf == "44444444444" || cpf == "55555555555" || cpf == "66666666666" || cpf == "77777777777" || cpf == "88888888888" || cpf == "99999999999"){
		erro += label+i18n_message('citcorpore.validacao.numeroCPFInvalido');
	}
	var a = [];
	var b = new Number;
	var c = 11;
	for (i=0; i<11; i++){
		a[i] = cpf.charAt(i);
	    if (i < 9) b += (a[i] * --c);
	}
	if ((x = b % 11) < 2) { a[9] = 0 } else { a[9] = 11-x }
	b = 0;
	c = 11;
	for (y=0; y<10; y++) b += (a[y] * c--);
	if ((x = b % 11) < 2) { a[10] = 0; } else { a[10] = 11-x; }
	if ((cpf.charAt(9) != a[9]) || (cpf.charAt(10) != a[10])){
		erro +=label+i18n_message("citcorpore.validacao.numeroCPFInvalido");
	}
	if (erro.length > 0){
		alert(erro);
		field.focus();
		return false;
	}
	return true;
};

function somenteNumero(e){
	 var tecla=(window.event)?event.keyCode:e.which;
	 if((tecla>47 && tecla<58))
		 return true;
	 else{
		 if (tecla==8 || tecla==0) return true;
		 	else
		 		return false;
	 }
}

function limparGrids(){

	HTMLUtils.deleteAllRows("tblEmail");
	HTMLUtils.deleteAllRows("tblEnderecos");
	HTMLUtils.deleteAllRows("tblTelefones");
	HTMLUtils.deleteAllRows("tblFormacao");
	HTMLUtils.deleteAllRows("tblIdioma");
//	HTMLUtils.deleteAllRows("tblExperiencias");
	HTMLUtils.deleteAllRows("tblCompetencia");
	HTMLUtils.deleteAllRows("tblCertificacao");

	delImgFoto();

}

function LOOKUP_CANDIDATO_select(id, desc){
	document.formPesquisaCurriculo.idCandidato.value = id;
	document.formPesquisaCurriculo.nome = desc;
	var nome, cpf;
	desc = desc.split('-');
	nome = desc[0];
	cpf = desc[1];
	if(desc[2] != undefined)
	cpf += '-' + desc[2];
	$('#nome').val(nome);
	$('#cpf').val(cpf);
	$('#modal_candidato').modal('hide');
}

/*
 * Verifica se o parametro de upload esta configurado corretamente
 */
$("#adicionar_foto").click(function() {
	document.formPesquisaCurriculo.fireEvent("verificarParametroAnexos");
});

function abrirModalFoto(){
	$('#modal_foto').modal('show');
}

function manipulaInputQtdeFilhos() {
	if(document.formPesquisaCurriculo.filhos[0].checked){
		document.getElementById('qtdeFilhos').removeAttribute("disabled");
		$('#filhosS').parent("label").addClass('radio inline');
	}
	else{
		document.getElementById('qtdeFilhos').setAttribute("disabled", "disabled");
		document.formPesquisaCurriculo.qtdeFilhos.value = '';
	}
}

/*
 * Valida��o de CPF j� existente na tabela rh_curriculo
 * renato.jesus
 */
$('#cpf').on('blur', function() {
	$this = $(this);

	$.ajax({
		url: '/citsmart/pages/templateCurriculoTrabalheConosco/templateCurriculoTrabalheConosco.event',
		async: false,
		data: {
			'method': 'execute',
			'parmCount': '',
			'parm1': 'templateCurriculoTrabalheConosco',
			'parm2': '',
			'parm3': 'verificaCPFJaCadastrado',
			'nocache': new Date(),
			'cpf': StringUtils.trim($this.val()),
			'idCurriculo': StringUtils.trim(document.getElementById('idCurriculo').value)
		},
		type: 'POST',
		dataType: 'json',
		success: function(data) {
			existe = data.existe;
		},
		error: function() {
			console.log("Error!");
		}
	});

	if(existe == "true" || existe == true) {
		alert(i18n_message("rh.cpfJaCadastrado"));
		$this.focus();
		return;
	}
});

/*
 * Valida a saida da pagina caso clique no botao voltar ou tente atualizar a pagina
 * renato.jesus
 */
window.onbeforeunload = function(e) {
	if(saidaNaoEsperada) {
		return "Deseja realmente sair da p�gina?\nAs informa��es feitas ser�o perdidas!";
	}
}

mostrar_aguarde = function() {
	JANELA_AGUARDE_MENU.show();
}

fechar_aguarde = function() {
	JANELA_AGUARDE_MENU.hide();
}