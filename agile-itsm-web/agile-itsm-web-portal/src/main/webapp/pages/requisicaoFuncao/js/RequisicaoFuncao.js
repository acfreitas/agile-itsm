addEvent(window, "load", load, false);
function load() {
	document.form.afterLoad = function() {
		parent.escondeJanelaAguarde();
		controleEtapas();
	}
}

function clearIdCargo(){
	$('#idCargo').val("");
	$('#cargo').val("");
}

jQuery(document).ready(function($) {
	$('#numeroPessoas').keypress(function(e) {
		var tecla = e.keyCode ? e.keyCode : e.which;
		if((tecla > 47 && tecla < 58) || (tecla > 34 && tecla < 41) || tecla == 8 || tecla == 46) {
			return true;
		}
		return false;
	});
	
});

function validaObrigatorio(id){
	if($("#"+id+"").prop('checked')){
		$("#"+id+"").val("S");
		serializarTabelas();
	}else{
		$("#"+id+"").val("N");
		serializarTabelas();
	}
}

function getObjetoSerializado() {
	var obj = new CIT_RequisicaoFuncaoDTO();
	HTMLUtils.setValuesObject(document.form, obj);
	return ObjectUtils.serializeObject(obj);
}

function serializarTabelas(){
	var tabela = HTMLUtils.getObjectsByTableId('tblPerspectivaComportamental');
	document.form.colPerspectivaComportamentalSerialize.value = ObjectUtils.serializeObjects(tabela);
	
	tabela = HTMLUtils.getObjectsByTableId('tblPerspectivaComplexidade');
	document.form.colPerspectivaComplexidadeSerialize.value = ObjectUtils.serializeObjects(tabela);
	
	tabela = HTMLUtils.getObjectsByTableId('tblFormacaoAcademica');
	document.form.colPerspectivaTecnicaFormacaoAcademicaSerialize.value = ObjectUtils.serializeObjects(tabela);
	
	tabela = HTMLUtils.getObjectsByTableId('tblCertificacao');
	document.form.colPerspectivaTecnicaCertificacaoSerialize.value = ObjectUtils.serializeObjects(tabela);
	
	tabela = HTMLUtils.getObjectsByTableId('tblCurso');
	document.form.colPerspectivaTecnicaCursoSerialize.value = ObjectUtils.serializeObjects(tabela);
	
	tabela = HTMLUtils.getObjectsByTableId('tblIdioma');
	document.form.colPerspectivaTecnicaIdiomaSerialize.value = ObjectUtils.serializeObjects(tabela);
	
	tabela = HTMLUtils.getObjectsByTableId('tblExperiencia');
	document.form.colPerspectivaTecnicaExperienciaSerialize.value = ObjectUtils.serializeObjects(tabela);
	
	tabela = HTMLUtils.getObjectsByTableId('tblCompetenciasTecnicas');
	document.form.colCompetenciasTecnicasSerialize.value = ObjectUtils.serializeObjects(tabela);
}

function ocultaDiv1(val){
	if(val == 'N'){
		$("#justificativa1").show();
	}
	if(val == 'S'){
		$("#complementoJustificativaValidacao").val("");
		$("#justificativaValidacao").val("");
		$("#justificativa1").hide();
	}
}

function ocultaDiv2(val){
	if(val == 'N'){
		$("#justificativa2").show();
	}
	if(val == 'S'){
		$("#justificativa2").hide();
	}
}

function disableEtapa1(){
	$("#nomeFuncao").prop('disabled',true);
	$("#numeroPessoas").prop('disabled', true);
	$("#possuiSubordinados1").prop('disabled', true);
	$("#possuiSubordinados2").prop('disabled', true);
	$("#justificativaFuncao").prop('disabled', true);
	$("#resumoAtividades").prop('disabled', true);
}
function disableEtapa2(){
	$("#requisicaoValida1").prop('disabled',true);
	$("#requisicaoValida2").prop('disabled',true);
	$("#justificativaValidacao").prop('disabled', true);
	$("#complementoJustificativaValidacao").prop('disabled', true);
}
function disableEtapa3(){
	$("#cargo").prop('disabled',true);
	$("#funcao").prop('disabled', true);
	$("#resumoFuncao").prop('disabled', true);
	$("#descricaoPerspectivaComplexidade").prop('disabled',true);
	$("#nivelPerspectivaComplexidade").prop('disabled', true);
	$("#descricaoFormacaoAcademica").prop('disabled', true);
	$("#descricaoCertificacao").prop('disabled',true);
	$("#descricaoCurso").prop('disabled', true);
	$("#descricaoIdioma").prop('disabled', true);
	$("#descricaoExperiencia").prop('disabled', true);
	$("#descricaoCompetenciasTecnicas").prop('disabled', true);
	$("#nivelCompetenciasTecnicas").prop('disabled',true);
	$("#descricaoPerspectivaComportamental").prop('disabled', true);
	$("#detalhePerspectivaComportamental").prop('disabled', true);
}

function controleEtapas(){
	
	if ($("#fase").val() == "etapa1"){
		$("#etapa2").show();
		$("#etapa3").hide();
		$("#etapa4").hide();
		
//		Disabilita campos Div Etapa1
		disableEtapa1();
		
	}
	if ($("#fase").val() == "etapa2"){
		$("#etapa2").show();
		$("#etapa3").show();
		$("#etapa4").hide();
		
//		Habilita Botões
//		$("#addPerspectivaComplexidade").attr("disabled", false);
//		$("#addFormacaoAcademica").attr("disabled", false);
//		$("#addCertificacao").attr("disabled", false);
//		$("#addCurso").attr("disabled", false);
//		$("#addIdioma").attr("disabled", false);
//		$("#addExperiencia").attr("disabled", false);
//		$("#addCompetenciasTecnicas").attr("disabled", false);
//		$("#addPerspectivaComportamental").attr("disabled", false);
		
		$("#obrigatorioFormacao").attr("disabled", false);
		$("#obrigatorioCertificacao").attr("disabled", false);
		$("#obrigatorioCurso").attr("disabled", false);
		$("#obrigatorioIdioma").attr("disabled", false);
		$("#obrigatorioExperiencia").attr("disabled", false);
		
//		Disabilita campos Div Etapa1
		disableEtapa1();
//		Disabilita campos Div Etapa2
		disableEtapa2();
	}
	if ($("#fase").val() == "etapa3"){
		$("#etapa2").show();
		$("#etapa3").show();
		$("#etapa4").show();
		
//		Disabilita Botões
//		$("#addPerspectivaComplexidade").attr("disabled", true);
//		$("#addFormacaoAcademica").attr("disabled", true);
//		$("#addCertificacao").attr("disabled", true);
//		$("#addCurso").attr("disabled", true);
//		$("#addIdioma").attr("disabled", true);
//		$("#addExperiencia").attr("disabled", true);
//		$("#addCompetenciasTecnicas").attr("disabled", true);
//		$("#addPerspectivaComportamental").attr("disabled", true);
		
		$("#obrigatorioFormacao").attr("disabled", true);
		$("#obrigatorioCertificacao").attr("disabled", true);
		$("#obrigatorioCurso").attr("disabled", true);
		$("#obrigatorioIdioma").attr("disabled", true);
		$("#obrigatorioExperiencia").attr("disabled", true);
		
		
//		Disabilita campos Div Etapa1
		disableEtapa1();
//		Disabilita campos Div Etapa2
		disableEtapa2();
//		Disabilita campos Div Etapa3
		disableEtapa3();
	}
}

var seletorEtapa1 = 0;

function controle_etapa1() {
	if (seletorEtapa1 == 1) {
		$("#etapa1").find(".widget-body").each(function() {
			$(this).addClass("in");
			$("#etapa1").attr('data-collapse-closed', false);
			$(this).css("height", "auto");
		});
		seletorEtapa1 = 0;
	} else {
		$("#etapa1").find(".widget-body").each(function() {
			$(this).removeClass("in");
			$(this).css("height", 0);
			$("#etapa1").attr('data-collapse-closed', true)
		});
		seletorEtapa1 = 1;
	}
}

var seletorEtapa2 = 0;

function controle_etapa2() {
	if (seletorEtapa2 == 1) {
		$("#etapa2").find(".widget-body").each(function() {
			$(this).addClass("in");
			$("#etapa2").attr('data-collapse-closed', false);
			$(this).css("height", "auto");
		});
		seletorEtapa2 = 0;
	} else {
		$("#etapa2").find(".widget-body").each(function() {
			$(this).removeClass("in");
			$(this).css("height", 0);
			$("#etapa2").attr('data-collapse-closed', true)
		});
		seletorEtapa2 = 1;
	}
}

var seletorEtapa3_1 = 0;

function controle_etapa3_1() {
	if (seletorEtapa3_1 == 1) {
		$("#etapa3_1").find(".widget-body").each(function() {
			$(this).addClass("in");
			$("#etapa3_1").attr('data-collapse-closed', false);
			$(this).css("height", "auto");
		});
		seletorEtapa3_1 = 0;
	} else {
		$("#etapa3_1").find(".widget-body").each(function() {
			$(this).removeClass("in");
			$(this).css("height", 0);
			$("#etapa3_1").attr('data-collapse-closed', true)
		});
		seletorEtapa3_1 = 1;
	}
}

var seletorEtapa3_2 = 0;

function controle_etapa3_2() {
	if (seletorEtapa3_2 == 1) {
		$("#etapa3_2").find(".widget-body").each(function() {
			$(this).addClass("in");
			$("#etapa3_2").attr('data-collapse-closed', false);
			$(this).css("height", "auto");
		});
		seletorEtapa3_2 = 0;
	} else {
		$("#etapa3_2").find(".widget-body").each(function() {
			$(this).removeClass("in");
			$(this).css("height", 0);
			$("#etapa3_2").attr('data-collapse-closed', true)
		});
		seletorEtapa3_2 = 1;
	}
}

var seletorEtapa3_3 = 0;

function controle_etapa3_3() {
	if (seletorEtapa3_3 == 1) {
		$("#etapa3_3").find(".widget-body").each(function() {
			$(this).addClass("in");
			$("#etapa3_3").attr('data-collapse-closed', false);
			$(this).css("height", "auto");
		});
		seletorEtapa3_3 = 0;
	} else {
		$("#etapa3_3").find(".widget-body").each(function() {
			$(this).removeClass("in");
			$(this).css("height", 0);
			$("#etapa3_3").attr('data-collapse-closed', true)
		});
		seletorEtapa3_3 = 1;
	}
}

var seletorEtapa3_4 = 0;

function controle_etapa3_4() {
	if (seletorEtapa3_4 == 1) {
		$("#etapa3_4").find(".widget-body").each(function() {
			$(this).addClass("in");
			$("#etapa3_4").attr('data-collapse-closed', false);
			$(this).css("height", "auto");
		});
		seletorEtapa3_4 = 0;
	} else {
		$("#etapa3_4").find(".widget-body").each(function() {
			$(this).removeClass("in");
			$(this).css("height", 0);
			$("#etapa3_4").attr('data-collapse-closed', true)
		});
		seletorEtapa3_4 = 1;
	}
}

var seletorEtapa3_5 = 0;

function controle_etapa3_5() {
	if (seletorEtapa3_5 == 1) {
		$("#etapa3_5").find(".widget-body").each(function() {
			$(this).addClass("in");
			$("#etapa3_5").attr('data-collapse-closed', false);
			$(this).css("height", "auto");
		});
		seletorEtapa3_5 = 0;
	} else {
		$("#etapa3_5").find(".widget-body").each(function() {
			$(this).removeClass("in");
			$(this).css("height", 0);
			$("#etapa3_5").attr('data-collapse-closed', true)
		});
		seletorEtapa3_5 = 1;
	}
}

var seletorEtapa4 = 0;

function controle_etapa4() {
	if (seletorEtapa4 == 1) {
		$("#etapa4").find(".widget-body").each(function() {
			$(this).addClass("in");
			$("#etapa4").attr('data-collapse-closed', false);
			$(this).css("height", "auto");
		});
		seletorEtapa4 = 0;
	} else {
		$("#etapa4").find(".widget-body").each(function() {
			$(this).removeClass("in");
			$(this).css("height", 0);
			$("#etapa4").attr('data-collapse-closed', true)
		});
		seletorEtapa4 = 1;
	}
}

function addPerspectivaComplexidade(){
	var obj = new CIT_PerspectivaComplexidadeDTO;
	
	//Valida as Informacoes digitadas do Telefone
	if (StringUtils.isBlank(document.getElementById('descricaoPerspectivaComplexidade').value)){
		alert(i18n_message("requisicaoFuncao.descricaoObrigatorio"));
		document.getElementById('descricaoPerspectivaComplexidade').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('nivelPerspectivaComplexidade').value)){
		alert(i18n_message("requisicaoFuncao.nivelObrigatorio"));
		document.getElementById('nivelPerspectivaComplexidade').focus();
		return;
	}
	
	obj.descricaoPerspectivaComplexidade = document.getElementById('descricaoPerspectivaComplexidade').value;
	obj.nivelPerspectivaComplexidade = document.getElementById('nivelPerspectivaComplexidade').value;
	HTMLUtils.addRow('tblPerspectivaComplexidade', document.form, 'perspectivaComplexidade', obj, ['descricaoPerspectivaComplexidade', 'nivelPerspectivaComplexidade',''], null, '', [gerarImgDelPerspectivaComplexidade], null, null, null, false);
	
	document.getElementById('descricaoPerspectivaComplexidade').value = '';
	document.getElementById('nivelPerspectivaComplexidade').value = '';
	
	serializarTabelas();
};

function gerarImgDelPerspectivaComplexidade(row, obj){
	row.cells[2].innerHTML = '<span  onclick="excluirgerarImgDelPerspectivaComplexidade(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function excluirgerarImgDelPerspectivaComplexidade(rowIndex){
	var tbl = document.getElementById('tblPerspectivaComplexidade');
		var iRowFimAux = tbl.rows.length;
		var obj = new CIT_PerspectivaComplexidadeDTO();
		var b = false;
			try{
				HTMLUtils.deleteRow('tblPerspectivaComplexidade', rowIndex);
			}catch(ex){
			}
			document.getElementById('descricaoPerspectivaComplexidade').value = '';
			document.getElementById('nivelPerspectivaComplexidade').value = '';
			serializarTabelas();
};	


function addFormacaoAcademica(){
	if(document.getElementById('descricaoFormacaoAcademica').value == '' || document.getElementById('descricaoFormacaoAcademica').value == null){
		return;
	}
	if(document.getElementById('idFormacaoAcademica').value == '' || document.getElementById('idFormacaoAcademica').value == null){
		alert(i18n_message("requisicaoFuncao.formacaoNaoCadastrada"));
		document.getElementById('descricaoFormacaoAcademica').value = '';
		return;
	}
	document.form.fireEvent('buscaFormacaoAcademica');
	serializarTabelas();
};

function gerarImgDelFormacaoAcademica(row, obj){
	row.cells[3].innerHTML = '<span  onclick="excluirgerarImgDelFormacaoAcademica(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
//    row.cells[0].innerHTML = '<input type="checkbox" name="obrigatorioFormacao" id="id'+obj.controleId+'" value="N" onclick=validaObrigatorio("id'+obj.controleId+'");>';
};

function excluirgerarImgDelFormacaoAcademica(rowIndex){
	var tbl = document.getElementById('tblFormacaoAcademica');
		var iRowFimAux = tbl.rows.length;
		var obj = new CIT_FormacaoAcademicaDTO();
		var b = false;
			try{
				HTMLUtils.deleteRow('tblFormacaoAcademica', rowIndex);
			}catch(ex){
			}
			document.getElementById('descricaoFormacaoAcademica').value = '';
			serializarTabelas();
};	

function addCertificacao(){
	if(document.getElementById('descricaoCertificacao').value == '' || document.getElementById('descricaoCertificacao').value == null){
		return;
	}
	if(document.getElementById('idCertificacao').value == '' || document.getElementById('idCertificacao').value == null){
		alert(i18n_message("requisicaoFuncao.certificacaoNaoCadastrada"));
		document.getElementById('descricaoCertificacao').value = '';
		return;
	}
	document.form.fireEvent('buscaCertificacao');
	serializarTabelas();
};

function gerarImgDelCertificacao(row, obj){
	row.cells[3].innerHTML = '<span  onclick="excluirgerarImgDelCertificacao(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
//    row.cells[0].innerHTML = '<input style="cursor: pointer" type="checkbox" name="id" id="id'+ obj.idCertificacao +'" value="'+obj.idCertificacao+'">';
};

function excluirgerarImgDelCertificacao(rowIndex){
	var tbl = document.getElementById('tblCertificacao');
		var iRowFimAux = tbl.rows.length;
		var obj = new CIT_CertificacaoDTO();
		var b = false;
			try{
				HTMLUtils.deleteRow('tblCertificacao', rowIndex);
			}catch(ex){
			}
			document.getElementById('descricaoCertificacao').value = '';
			serializarTabelas();
};	

function addCurso(){
	if(document.getElementById('descricaoCurso').value == '' || document.getElementById('descricaoCurso').value == null){
		return;
	}
	if(document.getElementById('idCurso').value == '' || document.getElementById('idCurso').value == null){
		alert(i18n_message("requisicaoFuncao.cursoNaoCadastrado"));
		document.getElementById('descricaoCurso').value = '';
		return;
	}
	document.form.fireEvent('buscaCurso');
	serializarTabelas();
};

function gerarImgDelCurso(row, obj){
	row.cells[3].innerHTML = '<span  onclick="excluirgerarImgDelCurso(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
//    row.cells[0].innerHTML = '<input style="cursor: pointer" type="checkbox" name="id" id="id'+ obj.idCurso +'" value="'+obj.idCurso+'">';
};

function excluirgerarImgDelCurso(rowIndex){
	var tbl = document.getElementById('tblCurso');
		var iRowFimAux = tbl.rows.length;
		var obj = new CIT_CursoDTO();
		var b = false;
			try{
				HTMLUtils.deleteRow('tblCurso', rowIndex);
			}catch(ex){
			}
			document.getElementById('descricaoCurso').value = '';
			serializarTabelas();
};	

function addIdioma(){
	if(document.getElementById('descricaoIdioma').value == '' || document.getElementById('descricaoIdioma').value == null){
		return;
	}
	if(document.getElementById('idIdioma').value == '' || document.getElementById('idIdioma').value == null){
		alert(i18n_message("requisicaoFuncao.IdiomaNaoCadastrado"));
		document.getElementById('descricaoIdioma').value = '';
		return;
	}
	document.form.fireEvent('buscaIdioma');
	serializarTabelas();
};

function gerarImgDelIdioma(row, obj){
	row.cells[3].innerHTML = '<span  onclick="excluirgerarImgDelIdioma(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
//    row.cells[0].innerHTML = '<input style="cursor: pointer" type="checkbox" name="id" id="id'+ obj.idIdioma +'" value="'+obj.idIdioma+'">';
};

function excluirgerarImgDelIdioma(rowIndex){
	var tbl = document.getElementById('tblIdioma');
		var iRowFimAux = tbl.rows.length;
		var obj = new CIT_IdiomaDTO();
		var b = false;
			try{
				HTMLUtils.deleteRow('tblIdioma', rowIndex);
			}catch(ex){
			}
			document.getElementById('descricaoIdioma').value = '';
			serializarTabelas();
};	

function addExperiencia(){
	if(document.getElementById('descricaoExperiencia').value == '' || document.getElementById('descricaoExperiencia').value == null){
		return;
	}
	if(document.getElementById('idConhecimento').value == '' || document.getElementById('idConhecimento').value == null){
		alert(i18n_message("requisicaoFuncao.experienciaNaoCadastrada"));
		document.getElementById('descricaoExperiencia').value = '';
		return;
	}
	document.form.fireEvent('buscaExperiencia');
	serializarTabelas();
};

function gerarImgDelExperiencia(row, obj){
	row.cells[3].innerHTML = '<span  onclick="excluirgerarImgDelExperiencia(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
//    row.cells[0].innerHTML = '<input style="cursor: pointer" type="checkbox" name="id" id="id'+ obj.idConhecimento +'" value="'+obj.idConhecimento+'">';
};

function excluirgerarImgDelExperiencia(rowIndex){
	var tbl = document.getElementById('tblExperiencia');
		var iRowFimAux = tbl.rows.length;
		var obj = new CIT_ConhecimentoDTO();
		var b = false;
			try{
				HTMLUtils.deleteRow('tblExperiencia', rowIndex);
			}catch(ex){
			}
			document.getElementById('descricaoExperiencia').value = '';
			serializarTabelas();
};	

function addCompetenciasTecnicas(){
	var obj = new CIT_CompetenciasTecnicasDTO();
	
	//Valida as Informacoes digitadas do Telefone
	if (StringUtils.isBlank(document.getElementById('descricaoCompetenciasTecnicas').value)){
		alert(i18n_message("requisicaoFuncao.descricaoObrigatorio"));
		document.getElementById('descricaoCompetenciasTecnicas').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('nivelCompetenciasTecnicas').value)){
		alert(i18n_message("requisicaoFuncao.nivelObrigatorio"));
		document.getElementById('nivelCompetenciasTecnicas').focus();
		return;
	}
	
	obj.descricaoCompetenciasTecnicas = document.getElementById('descricaoCompetenciasTecnicas').value;
	obj.nivelCompetenciasTecnicas = document.getElementById('nivelCompetenciasTecnicas').value;
	HTMLUtils.addRow('tblCompetenciasTecnicas', document.form, 'competenciasTecnicas', obj, ['descricaoCompetenciasTecnicas', 'nivelCompetenciasTecnicas',''], null, '', [gerarImgDelCompetenciasTecnicas], null, null, null, false);
	
	document.getElementById('descricaoCompetenciasTecnicas').value = '';
	document.getElementById('nivelCompetenciasTecnicas').value = '';
	serializarTabelas();
};

function gerarImgDelCompetenciasTecnicas(row, obj){
	row.cells[2].innerHTML = '<span  onclick="excluirgerarImgDelPerspectivaTecnica(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function excluirgerarImgDelPerspectivaTecnica(rowIndex){
	var tbl = document.getElementById('tblCompetenciasTecnicas');
		var iRowFimAux = tbl.rows.length;
		var obj = new CIT_PerspectivaTecnicaDTO();
		var b = false;
			try{
				HTMLUtils.deleteRow('tblCompetenciasTecnicas', rowIndex);
			}catch(ex){
			}
			document.getElementById('descricaoCompetenciasTecnicas').value = '';
			document.getElementById('nivelCompetenciasTecnicas').value = '';
			serializarTabelas();	
};	


function addPerspectivaComportamental(){
	var obj = new CIT_PerspectivaComportamentalFuncaoDTO;
	
	//Valida as Informacoes digitadas do perspectiva comportamental
	if (document.getElementById('idAtitudeIndividual').value == '' || document.getElementById('idAtitudeIndividual').value == null){
		alert(i18n_message("rh.comportamentoNaoCadastrado"));
		document.getElementById('descricaoPerspectivaComportamental').value = '';
		document.getElementById('descricaoPerspectivaComportamental').focus();
		return;
	}
	
	if (StringUtils.isBlank(document.getElementById('descricaoPerspectivaComportamental').value)){
		alert(i18n_message("requisicaoFuncao.descricaoObrigatorio"));
		document.getElementById('descricaoPerspectivaComportamental').focus();
		return;
	}
	if (StringUtils.isBlank(document.getElementById('detalhePerspectivaComportamental').value)){
		alert(i18n_message("requisicaoFuncao.detalheObrigatorio"));
		document.getElementById('detalhePerspectivaComportamental').focus();
		return;
	}
	
	obj.idAtitudeIndividual = document.getElementById('idAtitudeIndividual').value;
	obj.descricaoPerspectivaComportamental = document.getElementById('descricaoPerspectivaComportamental').value;
	obj.detalhePerspectivaComportamental = document.getElementById('detalhePerspectivaComportamental').value;
	HTMLUtils.addRow('tblPerspectivaComportamental', document.form, 'PerspectivaComportamental', obj, ['descricaoPerspectivaComportamental', 'detalhePerspectivaComportamental',''], null, '', [gerarImgDelPerspectivaComportamental], null, null, null, false);
	
	document.getElementById('idAtitudeIndividual').value = '';
	document.getElementById('descricaoPerspectivaComportamental').value = '';
	document.getElementById('detalhePerspectivaComportamental').value = '';
	
	serializarTabelas();

};

function gerarImgDelPerspectivaComportamental(row, obj){
	row.cells[2].innerHTML = '<span  onclick="excluirgerarImgDelPerspectivaComportamental(this.parentNode.parentNode.rowIndex)" class="btn-action glyphicons remove_2 btn-danger titulo"><i></i></span>';
};

function excluirgerarImgDelPerspectivaComportamental(rowIndex){
	var tbl = document.getElementById('tblPerspectivaComportamental');
		var iRowFimAux = tbl.rows.length;
		var obj = new CIT_AtitudeIndividualDTO;
		var b = false;
			try{
				HTMLUtils.deleteRow('tblPerspectivaComportamental', rowIndex);
			}catch(ex){
			}
			document.getElementById('descricaoPerspectivaComportamental').value = '';
			document.getElementById('detalhePerspectivaComportamental').value = '';
			serializarTabelas();
};	

//------------- autocompletes ------------------

$(document).ready(function() {

	$('#etapa2').hide();
	$('#etapa3').hide();
	$('#etapa4').hide();
	
	$('#funcao').prop('disabled',true)
	$('#resumoFuncao').prop('disabled',true)
	
	$('#cargo').autocomplete({ 
		serviceUrl:'pages/autoCompleteFuncao/autoCompleteFuncao.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCargo').val(data);
			$('#cargo').val(value);
		}
	});

	$('#descricaoFormacaoAcademica').autocomplete({ 
		serviceUrl:'pages/autoCompleteFormacaoAcademica/autoCompleteFormacaoAcademica.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idFormacaoAcademica').val(data);
			$('#descricaoFormacaoAcademica').val(value);
			
		}
	});
	
	$('#descricaoCertificacao').autocomplete({ 
		serviceUrl:'pages/autoCompleteCertificacao/autoCompleteCertificacao.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCertificacao').val(data);
			$('#descricaoCertificacao').val(value);
		}
	});
	
	$('#descricaoCurso').autocomplete({ 
		serviceUrl:'pages/autoCompleteCurso/autoCompleteCurso.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idCurso').val(data);
			$('#descricaoCurso').val(value);
		}
	});
	
	$('#descricaoIdioma').autocomplete({ 
		serviceUrl:'pages/autoCompleteIdioma/autoCompleteIdioma.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idIdioma').val(data);
			$('#descricaoIdioma').val(value);
		}
	});
	
	$('#descricaoExperiencia').autocomplete({ 
		serviceUrl:'pages/autoCompleteExperiencia/autoCompleteExperiencia.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idConhecimento').val(data);
			$('#descricaoExperiencia').val(value);
		}
	});
	
	$('#descricaoPerspectivaComportamental').autocomplete({ 
		serviceUrl:'pages/autoCompletePerspectivaComportamental/autoCompletePerspectivaComportamental.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idAtitudeIndividual').val(data);
			$('#descricaoPerspectivaComportamental').val(value);
		}
	});
});

