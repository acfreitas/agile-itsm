function limpar(){
	window.location = URL_INITIAL + 'requisicaoPessoal/requisicaoPessoal.load';
} 

      function desabilitarTela() {
      var f = document.form;
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
      document.form.afterLoad = function () {
      	  parent.escondeJanelaAguarde();                   
      }    
  }
  
 $(function() {
    $("#POPUP_FICHA").dialog({
        autoOpen : false,
        width : 1024,
        height : 750,
        modal : true
    });
}); 

$(function() {
    $("#POPUP_CURRICULO").dialog({
        autoOpen : false,
        width : 1024,
        height : 600,
        modal : true
    });
}); 
$(function() {
	$("#POPUP_HISTORICOCANDIDATO").dialog({
		autoOpen : false,
		width : 1024,
		height : 600,
		modal : true
	});
}); 

function validar() {
	return true;
}

function getObjetoSerializado() {
    var obj = new CIT_RequisicaoPessoalDTO();
    HTMLUtils.setValuesObject(document.form, obj);
    return ObjectUtils.serializeObject(obj);
} 

function fechaPopupEntrevista() {
	$("#modal_ficha").modal("hide");
	document.form.fireEvent('exibeTriagens');
}




/**
 *Recebe a coleção enviada de triagemRequisicaoPessoal e adiciona na tblCurriculos
 **/
	function incluirCurriculo(curriculoStr) {
		var curriculo = new CIT_CurriculoDTO();
		curriculo = ObjectUtils.deserializeObject(curriculoStr);
	    var triagem = new CIT_TriagemRequisicaoPessoalDTO();
	    triagem.idCurriculo = curriculo.idCurriculo;
	    triagem.strDetalhamento = '<div class="row-fluid">';
	    triagem.strDetalhamento += '<h3>'+curriculo.nome+'</h3>';
	    triagem.strDetalhamento += '<label><b>CPF:</b>&nbsp;' +curriculo.cpfFormatado+ '</label>';
	    triagem.strDetalhamento += '<label><b>' + i18n_message('citcorpore.comum.dataNascimento') + ':</b>&nbsp;' +curriculo.dataNascimentoStr+ '</label>';
	    triagem.strDetalhamento += '<label><b>' + i18n_message('citcorpore.comum.estadoCivil') + ':</b>&nbsp;' +curriculo.estadoCivilExtenso+ '</label>';
	    triagem.strDetalhamento += '</div>';
	    triagem.caminhoFoto = curriculo.caminhoFoto;
	    triagem.idTriagem = curriculo.idTriagem;
	    triagem.tipoEntrevista = curriculo.tipoEntrevista;
	    
	    HTMLUtils.addRow('tblCurriculos', null, '', triagem, 
	               ["","strDetalhamento","",""], ["idCurriculo"], '', [gerarImgExclusaoCurriculo], null, null, false);
	}

	function limparDadostableCurriculo(){

		HTMLUtils.deleteAllRows('tblCurriculos');

	}
	
	excluirLinhaTable = function(indice, table) {
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
			HTMLUtils.deleteRow(table, indice);
		}
	}
    function abrirModalInformacoesCurriculo(row, idCurriculo){

	    document.getElementById('frameVisualizacaoCurriculo').src = URL_SISTEMA+'templateCurriculo/templateCurriculo.load?iframe=true&idCurriculoPesquisa='+idCurriculo;
	    $('#modal_curriculo').modal('show');

	
	}
    
    function adicionarCurriculoNaTriagem(index, idCurriculo){  
		 HTMLUtils.deleteRow('tblCurriculos', index);
	      var curriculos = [];
	      var curriculoDto = new CIT_CurriculoDTO();
	      curriculoDto.idCurriculo = idCurriculo;
	      curriculos.push(curriculoDto);  
	      document.formSugestaoCurriculos.curriculos_serialize.value = ObjectUtils.serializeObjects(curriculos);
	     
	      document.formSugestaoCurriculos.fireEvent("adicionaCurriculos");
	 }
      
    function configuraJustificativaRejeicao(){
		if(document.form.rejeitada.checked)
				document.getElementById('divJustificativaRejeicao').style.display = 'block'; 
			else
				document.getElementById('divJustificativaRejeicao').style.display = 'none';
   }