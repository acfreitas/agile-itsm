jQuery(function($){
	$("#telefonecontato").mask("(999) 9999-9999");
});

function fecharJanelaAguarde(){
	JANELA_AGUARDE_MENU.hide();	
}

function ajustarTelaPadraoTemplate() {
	$('#menu').attr('style', 'display: none !important;');
	$('#content').css('width', '97%');
	parent.ajustarTemplate();
}

function ajustarTelaPadraoCitsmart(){
	$('#menu').attr('style', 'display: block !important;');
	$('#content').css('width', 'auto');
	parent.ajustarPadraoCitsmart();
}

function LOOKUP_SOLICITANTE_select(id, desc){
	document.form.solicitante.value = desc;
	document.form.idSolicitante.value = id;
	$('#modal_lookupSolicitante').modal('hide');
	document.form.fireEvent("restoreColaboradorSolicitante");
}

function desabilitaSituacao(){
	var radios = document.getElementsByName('situacao');
	for (var i=0, iLen=radios.length; i<iLen; i++) {
	  radios[i].disabled = true;
	}
	$("div.radio").each(function() {
		$(this).addClass("disabled");
	});					 
}

function validaExibicaoBaseConhecimento(paramAtivo) {
	if (paramAtivo == 'S' || paramAtivo == 's') {
		if($("input[name='situacao']:checked").val() != "Resolvida") {
			$('.solucaoRespostaBaseConhecimento').addClass('hide');
		} else {
			$('.solucaoRespostaBaseConhecimento').removeClass('hide');
		}
	}
}

function LOOKUP_PROBLEMA_select(id, desc){	
	document.form.idProblema.value = id;
	document.form.fireEvent('atualizaGridProblema');
}
function inserirProblemaNalista(id){
	document.form.idProblema.value = id;
	document.form.fireEvent('atualizaGridProblema');
}

function LOOKUP_MUDANCA_select(id, desc){
	document.form.idRequisicaoMudanca.value = id;
	document.form.fireEvent('atualizaGridMudanca');
}
function inserirMudancaNalista(id){
	document.form.idRequisicaoMudanca.value = id;
	document.form.fireEvent('atualizaGridMudanca');
}

function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {
	document.form.idItemConfiguracao.value = id;
	document.form.fireEvent('atualizaGridItemConfiguracao');
}

function fecharModalProblema(){
	$("#modal_lookupProblema").modal('hide');
	setQuantitativoProblema();
}

function fecharModalMudanca(){
	$("#modal_lookupMudanca").modal('hide');
	setQuantitativoMudanca();
}

function fecharModalItemConfiguracao(){
	$("#modal_lookupItemConfiguracao").modal('hide');
}

function fecharModalListaRelacionarIncidentes(){
	$('#modal_listaRelacionarIncidentes').modal('hide');
}

function abrirTodosCollapse(){
	$("#collapse1").find(".widget-body").each(function() {
		$(this).addClass("in");
		$("#collapse1").attr('data-collapse-closed',false);
		$(this).css("height", "auto");
	});
	
	$("#collapse2").find(".widget-body").each(function() {
		$(this).addClass("in");
		$("#collapse2").attr('data-collapse-closed',false);
		$(this).css("height", "auto");
	});
	
	$("#collapse3").find(".widget-body").each(function() {
		$(this).addClass("in");
		$("#collapse3").attr('data-collapse-closed',false);
		$(this).css("height", "auto");
	});
	
	$("#collapse4").find(".widget-body").each(function() {
		$(this).addClass("in");
		$("#collapse4").attr('data-collapse-closed',false); 
		$(this).css("height", "auto");
	});
}

/**
 * Desmonta a tela de cadastro de solicita??o mostrando apenas o collpase3
 * permitindo a reclassifica??o da solicita??o
**/
function visualizaCollapse3(){
	$(".menu-0, .menu-1").css('cssText', 'display: none;');
	$("#tab1-4").removeClass("active");
	$("#tab3-4").addClass("active");
	$("#divControleInformacaoComplementar2").addClass("inativo");
	/**
	 * Motivo: Comentado pois n?o permitia que quando se reclassificava um servi?o o SLA fosse calculado e mostrado
	 * @author flavio.santana
	 * Data/Hora: 04/12/2013 16:16
	 */
	//$("#fieldDescricao").addClass("inativo");
	//$("#fieldSla").addClass("inativo");
	var count = 1;
	$('.nav-tabs').find('li').each( function() {
		if ($(this).is('li')) {
			if(count==3) {
				$(this).addClass('active');
				count++;
			}else {
				$(this).removeClass('active').addClass("disabled");
				count++;
			}
		}
	});
}
 
/**Autocomplete **/
var completeServico;
var completeSolicitante;
var completeUnidade;

function montaParametrosAutocompleteUnidade(){
	idContrato =  document.form.idContrato.value;
	completeUnidade.setOptions({params: {idContrato: idContrato} });
}

function geraAutoComplete(){
	completeUnidade = $('#unidadeDes').autocomplete({ 
		serviceUrl: 'pages/autoCompleteUnidade/autoCompleteUnidade.load', 
		noCache: true,
		onSelect: function(value, data){ 
					$('#idUnidade').val(data);
					$('#unidadeDes').val(value.replace(/-*/, ""));
					document.form.fireEvent("preencherComboLocalidade");
				  } 
    });
}

$(document).ready(function() {
	$('#servicoBusca').on('click', function(){
		montaParametrosAutocompleteServico();
	});
	
	
	$(document).on('onfocus','#unidadeDes', function(){
		montaParametrosAutocompleteUnidade();
	});
	
	completeSolicitante = $('#solicitante').autocomplete({ 
		serviceUrl:'pages/autoCompleteSolicitante/autoCompleteSolicitante.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idSolicitante').val(data);
			$('#solicitante').val(value);
			$('#nomecontato').val(value);
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("restoreColaboradorSolicitante");
			document.form.fireEvent('renderizaHistoricoSolicitacoesEmAndamentoUsuario');
		}
	});
	
	completeServico = $('#servicoBusca').autocomplete({
		serviceUrl:'pages/autoCompleteServico/autoCompleteServico.load',
		noCache: true,
		onSelect: function(value, data){
			//document.form.clear();
			$('#idServico').val(data);
			$('#servicoBusca').val(value);
			document.form.fireEvent('verificaImpactoUrgencia');
			document.form.fireEvent('carregaBaseConhecimentoAssoc');
			document.form.fireEvent('verificaGrupoExecutor');
			calcularSLA();
			carregarInformacoesComplementares();
		}
	});
	
});

var tipoDemanda;
var contrato;
var categoria;

/**Monta os parametros para a buscas do autocomplete**/
function montaParametrosAutocompleteServico(){
	tipoDemanda = $("#idTipoDemandaServico").val();
 	contrato =  $("#idContrato").val() ;
 	categoria = $("#idCategoriaServico").val();
 	if($("#utilizaCategoriaServico").is(":checked")){
 		completeServico.setOptions({params: {contrato: contrato, tipoDemanda: tipoDemanda, categoria: categoria } });
 	} else {
 		completeServico.setOptions({params: {contrato: contrato, tipoDemanda: tipoDemanda} });
 	}
 	completeSolicitante.setOptions({params: {contrato: contrato} });
}

function listarSolicitacoesServicoEmAndamento(){
	if(document.form.idSolicitacaoServico.value != ""){
		document.formIncidentesRelacionados.idSolicitacaoIncRel.value = document.form.idSolicitacaoServico.value; 
		document.formIncidentesRelacionados.fireEvent("listarSolicitacoesServicoEmAndamento");
	}
}

function restaurarIncidentesRelacionados(){
	if(document.form.idSolicitacaoServico.value != ""){
		document.formIncidentesRelacionados.idSolicitacaoIncRel.value = document.form.idSolicitacaoServico.value; 
		document.formIncidentesRelacionados.fireEvent("restore");
	}
}

function abreModalNovoColaborador(){
	contrato =  $("#idContrato").val();
	document.getElementById('frameCadastroNovoColaborador').src = URL_SISTEMA+'pages/empregado/empregado.load?iframe=true&idContrato='+contrato;
	$('#modal_novoColaborador').modal('show');
}

function startLoading() {
	document.getElementById('tdResultadoSLAPrevisto').style.display = 'none'
		var servicoBusca = document.form.servicoBusca.value;
		if(servicoBusca!= ''){
			document.getElementById('divMini_loading').style.display = 'block';
		} else {
			document.getElementById('divMini_loading').style.display = 'none';
		}
}

function stopLoading() {
	document.getElementById('divMini_loading').style.display = 'none';
	document.getElementById('tdResultadoSLAPrevisto').style.display = 'block'
}

function stopSLAPrevisto(){
	document.getElementById('tdResultadoSLAPrevisto').style.display = 'none';
}

function calcularSLA() {
	/**
	 * Motivo: Adicionado valida??o de reclassifica??o pois n?o permitia que quando se alterava um servi?o o SLA fosse calculado e mostrado
	 * @author flavio.santana
	 * Data/Hora: 04/12/2013 16:16
	 */
	if (document.form.reclassicarSolicitacao.value == 'S' || document.form.idSolicitacaoServico.value == null || document.form.idSolicitacaoServico.value == '' || document.form.idSolicitacaoServico.value == 0 ) {
		startLoading();
		var temp = 'var statusDisabledUrgencia = document.form.urgencia.disabled;';
		temp += 'var statusDisabledImpacto = document.form.impacto.disabled;';
		temp += 'document.form.urgencia.disabled = false;';
		temp += 'document.form.impacto.disabled = false;';
		temp += 'document.form.fireEvent("calculaSLA");';
		temp += 'document.form.urgencia.disabled = statusDisabledUrgencia;';
		temp += 'document.form.impacto.disabled = statusDisabledImpacto;';
		temp += 'document.getElementById("img_carregando").style.display = "none";';
		setTimeout(temp, 1500);
	}
}

function limparCampoBusca(){
	document.form.servicoBusca.value = '';
	document.getElementById('tdResultadoSLAPrevisto').style.display = 'none';
}

function setaValorLookup(obj){
	document.form.idSolicitante.value = '';
	document.form.solicitante.value = '';
	document.form.emailcontato.value = '';
	document.form.telefonecontato.value = '';
	document.form.observacao.value = '';
	document.form.ramal.value = '';
	document.getElementById('idLocalidade').options.length = 0;
	document.form.servicoBusca.value = '';
	document.getElementById('idTipoDemandaServico').options[0].selected = 'selected';
	$("#idOrigem option:first").attr('selected','selected');
}

function limparEmails(){
    $("#emails").val("");	        
}

function chamaFuncoesContrato(){
	setaValorLookup(this);
	document.form.fireEvent('verificaGrupoExecutor');
	document.form.fireEvent('verificaImpactoUrgencia'); 
	document.form.fireEvent('carregaServicosMulti');
	
	document.getElementById("idUnidade").value= "0";
	if (document.getElementById("unidadeDes")!=null){
		document.getElementById("unidadeDes").value= "";
	}
	
	document.form.fireEvent('carregaUnidade');
	limparEmails();
	document.form.fireEvent('preencherComboLocalidade');
	adicionarIdContratoNaLookup(document.form.idContrato.value);
}

function adicionarIdContratoNaLookup(id){
	document.getElementById('pesqLockupLOOKUP_SOLICITANTE_IDCONTRATO').value = id;
}

function abreModalOcorrencia(editarVisualizar) {
	document.getElementById('frameCadastroOcorrenciaSolicitacao').src = URL_SISTEMA+'pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao.load?iframe=true&idSolicitacaoServico='+document.form.idSolicitacaoServico.value+'&visualizar='+editarVisualizar;
	$('#modal_ocorrencia').modal('show');

}

function limparCampoServiceBusca() {
	document.form.servicoBusca.value = '';
}

function carregaScript(){
	document.form.idServico.disabled = false;
	document.getElementById("divScript").innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
	document.form.fireEvent('carregaBaseConhecimentoAssoc');
} 

function carregaSolicitacoesAbertasParaMesmoSolicitante(){
	document.form.fireEvent("renderizaHistoricoSolicitacoesEmAndamentoUsuario");
}

function carregaFlagGerenciamento(){
	document.form.fireEvent("carregaFlagGerenciamento");
}

function pesquisaSolicitacoesAbertasParaMesmoSolicitante(){
	document.form.situacaoFiltroSolicitante.value = document.getElementById('situacaoTblResumo2').value
	document.form.buscaFiltroSolicitante.value = document.getElementById('campoBuscaTblResumo2').value
	document.form.fireEvent('renderizaHistoricoSolicitacoesEmAndamentoUsuario')
}

function detalheSolicitacao(parametro){
	var dadosSolicitacao;
	var divDetalhe;
	
	dadosSolicitacao = parametro.split("#");

	divDetalhe = '<div class="span4" id="informacoesUsuario">';
	divDetalhe += '<div class="well margin-none">';
	divDetalhe += '<address class="margin-none">';
	divDetalhe += '<h2>'+dadosSolicitacao[1]+'</h2>';
	divDetalhe += '<abbr title="Work email">'+i18n_message("visao.contrato")+':</abbr> '+dadosSolicitacao[0]+'<br> ';
	divDetalhe += '<abbr title="Work email">Email:</abbr> <a href="mailto:'+dadosSolicitacao[2]+'"> '+dadosSolicitacao[2]+'</a><br> ';
	divDetalhe += '<abbr title="Work Phone">'+i18n_message("lookup.telefone")+':</abbr> '+dadosSolicitacao[3]+'<br>';
	if(dadosSolicitacao[4]=='Requisi??o'){	
	divDetalhe += '<abbr title="Work Fax">'+i18n_message("portal.carrinho.tipoSolicitacao")+':</abbr>'+i18n_message("requisicaoProduto.requisicao")+'<br>';
	} else if (dadosSolicitacao[4]=='Incidente'){
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("portal.carrinho.tipoSolicitacao")+':</abbr> '+i18n_message("requisitosla.incidente")+'<br>';		
	} else{
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("portal.carrinho.tipoSolicitacao")+':</abbr> '+dadosSolicitacao[4]+'<br>';	
	}
	divDetalhe += '<abbr title="Work Fax">'+i18n_message("problema.servico")+':</abbr> '+dadosSolicitacao[5]+'<br>';		
	if(dadosSolicitacao[6] == 'EmAndamento'){
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("projeto.situacao")+':</abbr> '+i18n_message("solicitacaoServico.situacao.EmAndamento")+'<br>';
	} else if (dadosSolicitacao[6]=='Fechada'){
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("projeto.situacao")+':</abbr> '+i18n_message("solicitacaoServico.situacao.Fechada")+'<br>';	
	} else {
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("projeto.situacao")+':</abbr> '+dadosSolicitacao[6]+'<br>';	
	}
	divDetalhe += '</div>';
		
	document.getElementById('detalheSolicitacaoServico').innerHTML = divDetalhe;
	$("#modal_detalheSolicitacaoServico").modal("show");
}

//INICIALIZA O TEMPLATE - INFORMA??ES COMPLEMENTARES
function carregarInformacoesComplementares() {
    try{
    	$('#divControleInformacaoComplementar1').switchClass( "inativo", "ativo", null );
    	$('#divControleInformacaoComplementar2').switchClass( "inativo", "ativo", null );
    	$('#divInformacoesComplementares').switchClass( "ativo", "inativo", null );
    	$('#fraInformacoesComplementares').switchClass( "ativo", "inativo", null );
    	
        window.frames["fraInformacoesComplementares"].document.write("");
        window.frames["fraInformacoesComplementares"].document.write("<font color='red'><b>" + i18n_message("citcorpore.comum.aguardecarregando") + "</b></font>");
    }catch (e) {
    }       
    document.form.fireEvent('carregaInformacoesComplementares');
}
function exibirInformacoesComplementares(url) {
    if (url != '') {
        JANELA_AGUARDE_MENU.show();
        $('#divControleInformacaoComplementar1').switchClass( "ativo", "inativo", null );
        $('#divControleInformacaoComplementar2').switchClass( "ativo", "inativo", null );
        $('#divInformacoesComplementares').switchClass( "inativo", "ativo", null );
    	$('#fraInformacoesComplementares').switchClass( "inativo", "ativo", null );
        document.getElementById('fraInformacoesComplementares').src = url;
    }else{
        try{
            window.frames["fraInformacoesComplementares"].document.write("");
        }catch (e) {
        }       
        document.getElementById('divInformacoesComplementares').style.display = 'none';
    } 
}   

function validarInformacoesComplementares() {
	if (window.frames["fraInformacoesComplementares"]){
		try{
    		return window.frames["fraInformacoesComplementares"].validar();
		}catch(e){
			return true;
		}
	}else{
		return true;
	}
}   

function escondeJanelaAguarde() {
    JANELA_AGUARDE_MENU.hide();
}

function destaqueScript(){
	$('#divMenuScript').addClass('ui-state-highlight');
}

function destaqueSolicitacaoMesmoUsuario(){
	$('#divMenuSolicitacao').addClass('ui-state-error');
}

function adicionarRegistroExecucao(){
	if(document.getElementById('controleRegistroExecucao').style.display == 'none'){
		$('#btnAdicionarRegistroExecucao').switchClass("circle_plus", "circle_minus", null);
		document.getElementById('controleRegistroExecucao').style.display = 'block'
	}else{
		$('#btnAdicionarRegistroExecucao').switchClass("circle_minus", "circle_plus", null);
		document.getElementById('controleRegistroExecucao').style.display = 'none'
	}
}

function executa_miniLoading(){
	document.getElementById('divMini_loading').style.display = 'block';
	
}
function finaliza_miniLoading(){
	document.getElementById("divMini_loading").style.display = 'none';

}

function fecharAddSolicitante(){
	$('#modal_novoColaborador').modal('hide');
}

function abrirModalBaseConhecimento(){
	document.getElementById('frameBaseConhecimento').src = URL_SISTEMA+'baseConhecimentoView/baseConhecimentoView.load?iframe=true';
	$('#modal_baseConhecimento').modal('show');
}

function abrirModalPesquisaItemConfiguracao(){
	document.getElementById('framePesquisaItemConfiguracao').src = URL_SISTEMA+'pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load?iframe=true';
	$('#modal_pesquisaItemConfiguracao').modal('show');
}

function abrirModalAgenda(){
	document.getElementById('frameAgendaAtvPeriodicas').src = URL_SISTEMA+'pages/agendaAtvPeriodicas/agendaAtvPeriodicas.load?noVoltar=true';
	$('#modal_agenda').modal('show');
}

function abrirModalProblema(){
	$('#conteudoiframeEditarCadastrarProblema').html('<iframe src="about:blank" width="99%" id="iframeEditarCadastrarProblema" height="550" class="iframeSemBorda"></iframe>');
}

function abrirModalMudanca(){
	$('#conteudoiframeEditarCadastrarMudanca').html('<iframe src="about:blank" width="99%" id="iframeEditarCadastrarMudanca" height="550" class="iframeSemBorda"></iframe>');
}

function abrirModalItemConfiguracao(){
	 $('#conteudoiframeInformacaoItemConfiguracao').html('<iframe src="about:blank" width="99%" id="iframeInformacaoItemConfiguracao" height="530" class="iframeSemBorda"></iframe>'); 

}

function modalCadastroSolicitacaoServico(){
	document.form.fireEvent('abrirListaDeSubSolicitacoes');
	$('#modal_solicitacaofilha').modal('show');
}


$(function() {
	var offset = $("#menu").offset();
	var topPadding = 15;
	$(window).scroll(function() {
		if ($(window).scrollTop() > offset.top) {
			$("#menu").stop().animate({
				marginTop: $(window).scrollTop() - offset.top + topPadding
			});
		} else {
			$("#menu").stop().animate({
				marginTop: 0
			});
		};
	});
});

$("#idContrato").focus(function() {
	var idContrato = $("#idContrato").val();
	var idServico = $("#idServico").val();
	if(idContrato && idServico && !this.value) {
		this.value = idContrato + "." + idServico;
	}
});

var newsletter = $("#newsletter");
var inital = newsletter.is(":checked");
var topics = $("#newsletter_topics")[inital ? "removeClass" : "addClass"]("gray");
var topicInputs = topics.find("input").attr("disabled", !inital);

newsletter.click(function() {
	topics[this.checked ? "removeClass" : "addClass"]("gray");
	topicInputs.attr("disabled", !this.checked);
});

exibeIconesProblema = function(row, obj){
	var id = obj.idProblema;
    obj.sequenciaOS = row.rowIndex; 
    row.cells[2].innerHTML = '<a class="btn-action glyphicons pencil btn-success" onclick="carregarProblema('+ row.rowIndex + ', '+id+')"><i></i></a>  ';
    row.cells[2].innerHTML += '<a class="btn-action glyphicons remove_2 btn-danger" onclick="excluiProblema(this.parentNode.parentNode.rowIndex,this)"><i></i></a>';
}

exibeIconesMudanca = function(row, obj){
	var id = obj.idRequisicaoMudanca;
    obj.sequenciaOS = row.rowIndex; 
      row.cells[2].innerHTML = '<a class="btn-action glyphicons pencil btn-success" onclick="carregarMudanca('+ row.rowIndex + ', '+id+')"><i></i></a>  '
      row.cells[2].innerHTML += '<a class="btn-action glyphicons remove_2 btn-danger" onclick="excluiMudanca(this.parentNode.parentNode.rowIndex,this)"><i></i></a>';
}

exibeIconesBaseConhecimento = function(row, obj){
	var id = obj.idBaseConhecimento;
    obj.sequenciaOS = row.rowIndex; 
    row.cells[2].innerHTML = '<a  class="btn-action glyphicons remove_2 btn-danger" onclick="excluiBaseConhecimento(this.parentNode.parentNode.rowIndex,this)"><i></i></a>';
}

exibeIconesIC = function(row, obj){
	var id = obj.idItemConfiguracao;
    obj.sequenciaIC = row.rowIndex; 
    row.cells[3].innerHTML = '<a  class="btn-action glyphicons remove_2 btn-danger" onclick="excluiIC(this.parentNode.parentNode.rowIndex,this)"><i></i></a>';
    
	if(obj.idItemConfiguracaoPai == ""){
		row.cells[2].innerHTML = '<a class="btn-action glyphicons circle_info btn-default" onclick="popupAtivos( '+ id + ')"><i></i></a>';
	}
}

excluiBaseConhecimento = function(indice) {
	if (indice > 0 && confirm('Confirma exclus?o?')) {
		HTMLUtils.deleteRow('tblBaseConhecimento', indice);
		setQuantitativoBaseConhecimento();
	}
}
excluiProblema = function(indice) {
	if (indice > 0 && confirm('Confirma exclus?o?')) {
		HTMLUtils.deleteRow('tblProblema', indice);
		setQuantitativoProblema();
	}
}

excluiMudanca = function(indice) {
	if (indice > 0 && confirm('Confirma exclus?o?')) {
		HTMLUtils.deleteRow('tblMudanca', indice);
		setQuantitativoMudanca();
	}
}
excluiLiberacao = function(indice) {
	if (indice > 0 && confirm('Confirma exclus?o?')) {
		HTMLUtils.deleteRow('tblLiberacao', indice);
	}
}

excluiIC = function(indice) {
	if (indice > 0 && confirm('Confirma exclus?o?')) {
		HTMLUtils.deleteRow('tblIC', indice);
		setQuantitativoItemConfiguracao();
	}
}

excluiSolicitacao = function(indice) {
	if (indice > 0 && confirm('Confirma exclus?o?')) {
		HTMLUtils.deleteRow('tblSolicitacao', indice);
	}
}
function buscaProblema(row, object){
	carregarProblema(row, object);
}
function buscaMudanca(row, object){
	var obj = object.idRequisicaoMudanca;
	carregarMudanca(row, obj);
}
function carregarProblema(row, obj){
	var idProblema = obj;
	document.getElementById('iframeEditarCadastrarProblema').src = URL_SISTEMA+"pages/problema/problema.load?iframe=true&chamarTelaProblema=S&acaoFluxo=E&idProblema="+idProblema;
	$("#modal_editarCadastrarProblema").modal("show");
}


/**
Funcao que faz referencia ao bot?o fechar da tela de problema ap?s fechar um problema ira fechar modal em solicitacao servico.
* 
* @author maycon.fernandes
* @since 30/10/2013 15:35
*/
function fecharProblema(){
	$("#modal_editarCadastrarProblema").modal("hide");
}

function fecharFrameProblema(){
	$("#modal_editarCadastrarProblema").modal("hide");
}

/**
Funcao que faz referencia ao bot?o fechar da tela de mudanca ap?s fechar um mudanca ira fechar modal em solicitacao servico.
* 
* @author maycon.fernandes
* @since 30/10/2013 15:35
*/
function fecharMudanca(){
	$("#modal_editarCadastrarMudanca").modal("hide");
}

/**
Alterado para apenas visualizar, a rotina anterior estava permitindo alterar com isso ele nao estava andando junto com o fluxo.
* 
* @author maycon.fernandes
* @since 30/10/2013 15:35
*/
function carregarMudanca(row, obj){
	var idMudanca = obj;
	document.getElementById('iframeEditarCadastrarMudanca').src = URL_SISTEMA+"pages/requisicaoMudanca/requisicaoMudanca.load?iframe=true&idRequisicaoMudanca="+idMudanca+"&escalar=N&alterarSituacao=N&editar=N";
	$("#modal_editarCadastrarMudanca").modal("show");
}

function chamaPopupCadastroSolicitacaoServico(){
	var idItem = document.getElementById("idSolicitacaoServico").value;
	var idContrato = document.getElementById("idContrato").value;	
	document.getElementById('frameCadastroNovaSolicitacaoFilho').src = URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?iframe=true&idSolicitacaoRelacionada='+idItem+'&idContrato='+idContrato;
	$("#modal_editarCadastrarSolicitacaoFilha").modal("show");
}

function cadastrarProblema(){
	document.form.fireEvent("setarDescricaoNaSessaoCadastrarProblema");
}

function cadastrarProblemaAbrirFame(){
	document.getElementById('iframeEditarCadastrarProblema').src = URL_SISTEMA+"pages/problema/problema.load?iframe=true&solicitacaoServico=true";
	$("#modal_editarCadastrarProblema").modal("show");
}

function cadastrarMudanca(){
	document.form.fireEvent("setarDescricaoNaSessaoCadastrarMudanca");
}

function cadastrarMudancaAbrirFame(){
	document.getElementById('iframeEditarCadastrarMudanca').src = URL_SISTEMA+"pages/requisicaoMudanca/requisicaoMudanca.load?iframe=true&solicitacaoServico=true";
	$("#modal_editarCadastrarMudanca").modal("show");
}

function popupAtivos(id){
	var idItem = id;
		document.getElementById('iframeInformacaoItemConfiguracao').src = URL_SISTEMA+'pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
		$("#modal_informacaoItemConfiguracao").modal("show");
		calcularSLA();
}

function selectedItemConfiguracao(idItemCfg){
	document.form.idItemConfiguracao.value = idItemCfg;
	serializaTabelaIcParaImpactoUrgencia();
	document.form.fireEvent("restoreItemConfiguracao");
}

function serializaTabelaIcParaImpactoUrgencia(){
	var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
	if (objsIC != null) {
		document.form.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);
	}
}

function gravarSolucaoRespostaEmBaseConhecimento(){
	if($("#gravaSolucaoRespostaBaseConhecimento").is(":checked")){
    		$('#divTituloSolucaoRespostaBaseConhecimento').switchClass( "inativo", "ativo", null );
    		$("#tituloSolucaoRespostaBaseConhecimento").attr('required',true);
		}
	else{
			$('#divTituloSolucaoRespostaBaseConhecimento').switchClass( "ativo", "inativo", null );
			$("#tituloSolucaoRespostaBaseConhecimento").attr('required',false); 
			$("#tituloSolucaoRespostaBaseConhecimento").val("");
		 }
}

function pesquisarSolucao(){
	document.form.fireEvent('pesquisaBaseConhecimento');
}

function LOOKUP_BASECONHECIMENTO_PUBLICADOS_select(id, desc){
	document.form.idItemBaseConhecimento.value = id;
	document.form.fireEvent('atualizaGridBaseConhecimento'); 
	$('#modal_lookupBaseConhecimento').modal('hide')
}

function chamaPopupCadastroOrigem(){
	if (document.form.idContrato.value == ''){
		alert(i18n_message("solicitacaoservico.validacao.contrato"));
		return;
	}
	var idContrato = 0;
	try{
		idContrato = document.form.idContrato.value;
	}catch(e){
	}
	document.getElementById('frameExibirOrigem').src = URL_SISTEMA+'pages/origemAtendimento/origemAtendimento.load?iframe=true&idContrato='+idContrato;
	$('#modal_origem').modal('show');
}

function setValorTextArea(id, texto, isWysi) {
	if (isWysi == "S") {
		$(id).data("wysihtml5").editor.setValue(texto);
	} else {
		$(id).html(texto);
	}
}

function desabilitaTextAreaWysi(id) {
	$(id).data("wysihtml5").editor.composer.element.setAttribute('contenteditable', false);
	
	$(id +"-wysihtml5-toolbar").remove();
}

//M?rio J?nior -  23/10/2013 -  16:27 - Inseri tarefa na grid
function informaNumeroSolicitacao(numero, responsavel, tarefa){
	document.getElementById('tituloSolicitacao').innerHTML = '<label class="strong">Nº</label><p>&nbsp;' + numero + '</p><label class="strong">' + i18n_message("solicitacaoServico.responsavelatual.desc") + '</label><p>'+i18n_message("solicitacaoServico.solicitacaonumero") + '&nbsp;' + responsavel + '</p><label  class="strong">'+i18n_message("solicitacaoServico.tarefaatual.desc")+'</label><p>'+i18n_message("solicitacaoServico.tarefaatual.desc") + '&nbsp;' + tarefa + '</p>';   
}

function bloqueiaBotoesVisualizacao(){
	$('.btn').attr('disabled', 'disabled').addClass('disabled');
	$('#btnPesquisaSolUsuario').removeAttr( "disabled" ).removeClass('disabled');
	$('#tabCadastroOcorrencia').addClass('inativo');
	document.getElementById('divBtIncidentesRelacionados').style.display = 'none';
	document.getElementById('btnAdduploadAnexos').style.display = 'none';
	//retira disabled dos botoes fechar das Modais
	$('.modal-footer').find('a').each( function() {
		$('a').removeAttr( "disabled" ).removeClass('disabled');
	});

}

function preencherComboOrigem() {
	document.form.fireEvent('chamaComboOrigem');
}

function abreVISBASECONHECIMENTO(id){
	JANELA_AGUARDE_MENU.show();
	document.getElementById('visualizaProblemaBaseConhecimento').src = URL_SISTEMA + 'baseConhecimentoView/baseConhecimentoView.load?iframe=true&idBaseConhecimento='+id;
	$('#modal_visualizaProblemaBaseConhecimento').modal('show');
	
}

function contadorClicks(idBaseConhecimento){
	document.form.idBaseConhecimento.value = idBaseConhecimento;
	document.form.fireEvent('contadorDeClicks');	
}	

function validaCampoExecutanteNullparaVazio(){
	$('#solicitante').val("");
}

function mostrarPassoQuatroExecucaoTarefa(){
}

/*
 * Alterado por
 * desenvolvedor: rcs (Rafael César Soyer)
 * data: 09/01/2015
*/ 
function mostrarComboServico(){
	var idTipoDemandaServico = $("#idTipoDemandaServico").val();
	var idCategoriaServico = $("#idCategoriaServico").val();
	if (idTipoDemandaServico != '') {
		JANELA_AGUARDE_MENU.show();
		if($("#utilizaCategoriaServico").is(":checked")){
			document.form.idCategoriaServico.value = idCategoriaServico;
		} else{
			document.form.idCategoriaServico.value = '';
		}
		$('#filtroTableServicos').val("");
		$('#filtroTableServicos').focus();
		document.form.fireEvent('listarServicosPorContratoDemandaCategoria');
	}
	else{
		alert(i18n_message("citcorpore.comum.informeTipoSolicitacao"));
		$('#ulWizard li:eq(2) a').tab('show');
		document.form.idTipoDemandaServico.focus();
	}
}

function marcarChecksEmail(){
	document.form.fireEvent('marcarChecksEmail')
}

function selecionarServico(row, obj){
	JANELA_AGUARDE_MENU.show()
	$('#idServico').val(obj.idServico);
	$('#servicoBusca').val(obj.nomeServico);
	document.form.fireEvent('verificaImpactoUrgencia');
	document.form.fireEvent('carregaBaseConhecimentoAssoc');
	document.form.fireEvent('verificaGrupoExecutor');
	calcularSLA();
	carregarInformacoesComplementares();
	JANELA_AGUARDE_MENU.hide()
	$('#modal_infoServicos').modal('hide');
	
}

function filtroTableJs(campoBusca, table){
	// Recupera value do campo de busca
    var term=campoBusca.value.toLowerCase();
	if( term != ""){
		// Mostra os TR's que contem o value digitado no campoBusca
		if(table != ""){
			$("#"+table+" tbody>tr").hide();
            $("#"+table+" td").filter(function(){
                   return $(this).text().toLowerCase().indexOf(term ) >-1
            }).parent("tr").show();
		}
	}else{
		// Quando n?o h? nada digitado, mostra a tabela com todos os dados
		$("#"+table+" tbody>tr").show();
	}
}

function somenteNumero(e){
    var tecla=(window.event)?event.keyCode:e.which;   
    if((tecla>47 && tecla<58)) return true;
    else{
    	if (tecla==8 || tecla==0) return true;
	else  return false;
    }
}

/* Alterado por
* desenvolvedor: rcs (Rafael César Soyer)
* data: 09/01/2015
*/
function limparServico(){
	$('#servicoBusca').val('');
	$( "#idServico" ).val( '' );
}

/* Desenvolvedor: Riubbe Oliveira - Data: 23/10/2013 - Hor?rio: 10:46 - ID Citsmart: 121539 
 * 
 * Motivo/Coment?rio: Fun??o para ocultar divInformacoesComplementares caso seja um questionario 
 * isso se faz necess?rio porque ao salvar o question?rio, a fun??o getObjetoSeriarizado
 * da um submit e um reload dentro da div mostrando a pagina inicial do citsmart   
 * */
function ocultaInfoComplSeQuestionario(link){
	var str = new String(link);
	var res = str.search("visualizacaoQuestionario.load");
	if(res != -1){
		$('#divInformacoesComplementares').css('cssText','display:none !important');
	}
}

function incluiInfoComplSeQuestionario(link){
	var str = new String(link);
	var res = str.search("visualizacaoQuestionario.load");
	if(res != -1){
		$('#divInformacoesComplementares').css('cssText','display:block !important');
	}
}

function validaForm(){
	var idContrato = $("#idContrato").val();
	if (idContrato == '') {
		alert(i18n_message("contrato.contrato") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard a:first').tab('show');
		document.form.idContrato.focus();
		return;
	}
	var idOrigem = $("#idOrigem").val();
	if (idOrigem == '') {
		alert(i18n_message("citcorpore.comum.origem") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(1) a').tab('show');
		document.form.idOrigem.focus();
		return;
	}
	/*
	 * Rodrigo Pecci Acorse - 19/03/2014 16h30 - #137856
	 * A valida??o estava verificando somente o nome e n?o olhava para o id do solicitante.
	 */
	var solicitanteBusca = $("#solicitante").val();
	var idSolicitante = $("#idSolicitante").val();
	if (solicitanteBusca == '' || idSolicitante == '') {
		alert(i18n_message("solicitacaoServico.solicitante") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(1) a').tab('show');
		document.form.solicitante.focus();
		return;
	}
	var emailcontato = $("#emailcontato").val();
	if (emailcontato == '') {
		alert(i18n_message("citcorpore.comum.email") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(1) a').tab('show');
		document.form.emailcontato.focus();
		return;
	}
	if (emailcontato != ''){ 
		if (!/\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}\b/.test(emailcontato) ) {
			alert(i18n_message("citcorpore.validacao.emailInvalido"));
			$('#ulWizard li:eq(1) a').tab('show');
			document.form.emailcontato.focus();
			return;
		}
	}
	var idUnidade = $("#idUnidade").val();
	if (idUnidade == '' || idUnidade == '0') {
		alert(i18n_message("unidade.unidade") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(1) a').tab('show');
		document.form.idUnidade.focus();
		return;
	}
	var idTipoDemandaServico = $("#idTipoDemandaServico").val();
	if (idTipoDemandaServico == '') {
		alert(i18n_message("solicitacaoServico.tipo") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(2) a').tab('show');
		document.form.idTipoDemandaServico.focus();
		return;
	}
	var servicoBusca = $("#servicoBusca").val();
	var idServico = $("#idServico").val();
	if (servicoBusca == '' || idServico == '') {
		alert(i18n_message("citcorpore.comum.servico") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(2) a').tab('show');
		document.form.servicoBusca.focus();
		return;
	}
	var descricao = $("#descricao").val();
	if (descricao == '') {
		alert(i18n_message("solicitacaoServico.descricao") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(2) a').tab('show');
		return;
	}	
	if($("#divUrgencia").is(":visible")){
		var urgencia = $("#urgencia").val();
		if (urgencia == '') {
			alert(i18n_message("solicitacaoServico.urgencia") + ": "
					+ i18n_message("citcorpore.comum.campo_obrigatorio"));
			$('#ulWizard li:eq(2) a').tab('show');
			document.form.urgencia.focus();
			return;
		}
	}
	if($("#divImpacto").is(":visible")){
		var impacto = $("#impacto").val();
		if (impacto == '') {
			alert(i18n_message("solicitacaoServico.impacto") + ": "
					+ i18n_message("citcorpore.comum.campo_obrigatorio"));
			$('#ulWizard li:eq(2) a').tab('show');
			document.form.impacto.focus();
			return;
		}			
	}
	
	return true;
}

function gravarSemEnter(evt) {
	var key_code = evt.keyCode  ? evt.keyCode  : evt.charCode ? evt.charCode : evt.which ? evt.which : void 0;
	if (key_code == 13) {
		evento = key_code;
		fecharModalNovaSolicitacao();
		return;
	}else{
		gravar();
	}
}
function scrolls() {	
	var alturaDiv = $('#divInformacoesComplementares').height();
	var altura = alturaDiv / 2;
	$('html,body').animate({scrollTop: altura},'slow');
}



function atualizarLista(){
	parent.atualizarLista();
}
/* Desenvolvedor: Riubbe Oliveira - Data: 23/10/2013 - Hor?rio: 11:59 - ID Citsmart: 121539 
 * 
 * Motivo/Coment?rio: Fun??o que serializa as informa??es dos templates e questionarios.
 * foi incluido a chamada ao metodo ocultaInfoComplSeQuestionario(link) para verificar
 * e tratar a div quando for um question?rio.
 * */
function informacoesComplementaresSerialize(){
	var informacoesComplementares_serialize = '';
	var link = $('#fraInformacoesComplementares').attr('src');
		try {
			informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
		} catch (e) {
	}
	document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;
	ocultaInfoComplSeQuestionario(link);	
}

function habilitaEmail(){
	if (document.form.enviaEmailCriacao.disabled == true) {
		document.form.enviaEmailCriacao.disabled = false;
	}
	if (document.form.enviaEmailFinalizacao.disabled == true) {
		document.form.enviaEmailFinalizacao.disabled = false;
	}
	if (document.form.enviaEmailAcoes.disabled == true) {
		document.form.enviaEmailAcoes.disabled = false;
	}
}

function gravarfg() {
	seTodosCamposObrigatorioPreenchidos();
}
function fechaModalAnexo(){
	document.form.fireEvent("flagGerenciamentoClose");
}

function carregaCategoriaServico(){	
	if($("#utilizaCategoriaServico").is(":checked")){
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("criarComboCategoriaServico");
	} else {
		document.getElementById("divCategoriaServico").style.display = 'none';
	}
}

function fecharComEnter(evt){
	var key_code = evt.keyCode  ? evt.keyCode  : evt.charCode ? evt.charCode : evt.which ? evt.which : void 0;
	if (key_code == 13) {
		evento = key_code;
		fecharModalNovaSolicitacao();
		return;
	}
}

/*Criado para quando usar as setas na busca de sevi?o, n?o alterar o load do SLA*/
function eventoStartLoading(evt){
	if( evt.keyCode != 37 && evt.keyCode != 38 && evt.keyCode != 39 && evt.keyCode != 40){
		startLoading();
	}
}

function setQuantitativoProblema(){
	count = $("#tblProblema").find("tr").length-1;
	$("#quantidadeProblema").text(count);
	
}

function setQuantitativoMudanca(){
	count = $("#tblMudanca").find("tr").length-1;
	$("#quantidadeMudanca").text(count);
}

function setquantitativoItemConfiguracao(){
	count = $("#tblIC").find("tr").length-1;
	$("#quantidadeItemConfiguracao").text(count);
}

function setQuantitativoBaseConhecimento(){
	count = $("#tblBaseConhecimento").find("tr").length-1;
	$("#quantidadeBaseConhecimento").text(count);
}


/**
 * @author gilberto.nery
 * Data: 17/12/2013 - 17:20 - ID Citsmart: 127265
 * 
 * Motivo/Coment?rio: Function que sera chamada durante execu??o de automa??o de teste
 * na ferramenta Selenium
 */
function preencherCampoDescricao(){
	$('#descricao').data("wysihtml5").editor.setValue("Teste Automatizado Selenium");
}

function renderizaInformacoesSolicitante() {
	document.form.fireEvent("restoreColaboradorSolicitante");
	document.form.fireEvent('renderizaHistoricoSolicitacoesEmAndamentoUsuario');
}

function setEmail(){
	if (document.form.idContrato.value == ''){
		alert('Informe o contrato!');
		return;
	}
	
	document.formEmail.idContrato.value = document.form.idContrato.value;
	JANELA_AGUARDE_MENU.show();
	
	document.formEmail.fireEvent('loadMails');
}

function setDescricao(descricao){
	descricao = stripHtml(descricao);
	descricao = descricao.replace(/<!--[\s\S]*?-->/g, "");
	descricao = descricao.trim().replace(/\n|\r|\r\n/g, '<br/>');
	
	document.form.descricao.value = descricao;
	$('#descricao').data('wysihtml5').editor.setValue(descricao);
}

function copiaEmail(messageNumber){
	JANELA_AGUARDE_MENU.show();
	document.formEmail.emailMessageId.value = document.getElementById('emailMessageId' + messageNumber).value;
	document.formEmail.fireEvent('readMail');
}

function fechaModalLeituraEmail(){
	$('#modal_leituraEmails').modal('hide');
}

function toggleClass(classe, type){
	if (type == 'show') {
		$('.' + classe).show();
	} else if (type == 'hide') {
		$('.' + classe).hide();
	}
}

function stripHtml(html) {
   var tmp = document.createElement("DIV");
   tmp.innerHTML = html;
   return tmp.textContent || tmp.innerText || "";
}

function htmlDecode(input){
	var e = document.createElement('div');
	e.innerHTML = input;
	return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
}

function onkeypressUnidadeDes(){
	document.getElementById("idUnidade").value= "0";
}

function desabilitarSituacao() {
    $("input[name='situacao']").each(function(i) {
        $(this).attr('disabled', 'disabled').addClass('disabled');
        $(this).parents("div[class='radio']").addClass('disabled');
    });
}

function habilitarSituacao() {
    $("input[name='situacao']").each(function(i) {
        $(this).removeAttr('disabled');
    });
}


/* ============================================================
	ATENÇÃO

NO JSP DEVE CONTER APENAS SCRIPTS QUE NECESSITAM DE SCRIPTLETS

================================================================*/

/* 	ronnie.lopes
Inicializa escondido Campo Título do Gravar Base Conhecimento no Load da Página */
$(function() {
 if (mostraGravarBaseConhec=="S") { 
	 document.getElementById("divTituloSolucaoRespostaBaseConhecimento").style.display='none';

		$("input[name='situacao']").change(function() {
			if($(this).attr('value') != "Resolvida") {
				$('.solucaoRespostaBaseConhecimento').addClass('hide');
			} else {
				$('.solucaoRespostaBaseConhecimento').removeClass('hide');
			}
		});
 } 
});

var evento;

if (isVersionFree==false) {
	excluiProblema = function(indice) {
	if (indice > 0 && confirm('Confirma exclusão')) {
		HTMLUtils.deleteRow('tblProblema', indice);
		count = $("#tblProblema").find("tr").length-1;
		$("#quantidadeProblema").text(count);
	
	}
	}
	
	excluiMudanca = function(indice) {
		if (indice > 0 && confirm('Confirma exclusão')) {
			HTMLUtils.deleteRow('tblMudanca', indice);
			count = $("#tblMudanca").find("tr").length-1;
			$("#quantidadeMudanca").text(count);
		}
	}
	
	excluiIC = function(indice) {
		if (indice > 0 && confirm('Confirma exclusão')) {
			HTMLUtils.deleteRow('tblIC', indice);
			count = $("#tblIC").find("tr").length-1;
			$("#quantidadeItemConfiguracao").text(count);
		}
	}
}


function gravar() {
if (evento == 13) {
return;
}
JANELA_AGUARDE_MENU.show();
if(!validaForm()){
JANELA_AGUARDE_MENU.hide();
habilitaBotaoGravar();
return;
}

serializaMudanca();
serializaProblema();
serializaIC();
serializaBaseConhecimento();

/*  ronnie.lopes
Campo Título Obrigatório caso Situação seje igual a Resolvida */
if($("input[name='situacao']:checked").val() == "Resolvida") {
	if (mostraGravarBaseConhec=="S") {
		if($("#gravaSolucaoRespostaBaseConhecimento").is(":checked")){
			if($("#tituloBaseConhecimento").val() == "" || $("#tituloBaseConhecimento").val == null) {
				alert(i18n_message("solicitacaoServico.tituloObrigatorio"));
				habilitaBotaoGravar();
				JANELA_AGUARDE_MENU.hide();
				return;
			}
		}
	} 
}

var todosPreenchidos = validarInformacoesComplementares();
if (!todosPreenchidos) {
	habilitaBotaoGravar();
	JANELA_AGUARDE_MENU.hide();
	$('#ulWizard li:eq(2) a').tab('show');
	scrolls();
	return;
}

document.form.urgencia.disabled = false;
document.form.impacto.disabled = false;

if(document.getElementById('flagGrupo').value == 0){
	informacoesComplementaresSerialize();
	document.form.idServico.disabled = false;
	document.form.idContrato.disabled = false;
	habilitaEmail();
	habilitarSituacao();
	document.form.save();
}else{
	if (document.getElementById("idGrupoAtual").value == ''){
		if (confirm(i18n_message("solicitacaoServico.grupoAtualVazio"))){
			habilitaEmail();
			habilitarSituacao();
			informacoesComplementaresSerialize();
			document.form.save();
		}else{
			habilitaBotaoGravar();
			JANELA_AGUARDE_MENU.hide();
			return;
		}
	}else{
		habilitaEmail();
		habilitarSituacao();
		informacoesComplementaresSerialize();
		document.form.save();
	}
}

}

function desabilitaBotaoGravar(){
	var solicitacaoNova = false;
	if (tarefaAssociada=="N") {
		solicitacaoNova = true;
	}

	if(solicitacaoNova){
		document.getElementById('btnGravar').disabled = true;
	}else{
		document.getElementById('btnGravar').disabled = true;
		document.getElementById('btnGravarEContinuar').disabled = true;
	}
}

function habilitaBotaoGravar(){
	var solicitacaoNova = false;
	if (tarefaAssociada=="N") {
	solicitacaoNova = true;
	}

	if(solicitacaoNova){
		document.getElementById('btnGravar').disabled = false
	}else{
		document.getElementById('btnGravar').disabled = false;
		document.getElementById('btnGravarEContinuar').disabled = false;
	}
}

function gravarEContinuar() {
	desabilitaBotaoGravar();
	document.form.acaoFluxo.value = acaoIniciar;
	gravar();
}

function gravarEFinalizar() {
	desabilitaBotaoGravar();
	document.form.acaoFluxo.value = acaoExecutar;
	gravar();
}

function mostraMensagemInsercao(msg){
	document.getElementById('divInsercao').innerHTML = msg;
	$("#mensagem_insercao").modal("show");

	$('#mensagem_insercao').on('hide', function() {
		$('#tmensagem_insercao').modal('hide');
		fecharSolicitacaoModal();
	});
	
	document.getElementById('mensagem_insercao').focus();
}

function fecharModalNovaSolicitacao() {
	fecharSolicitacaoModal();
}

function fecharSolicitacaoModal(){
	/**
	* Motivo: Verifica se o parametro modalAsterisk é true e chama a função fecharModalSolicitacaoAsterisk
	* Autor: flavio.santana
	* Data/Hora: 11/12/2013 10:15
	*/
	var modalAsterisk = parametroAdicionalAsterisk;
	if(iframe){
		if(modalAsterisk === true) {
			parent.fecharModalSolicitacaoAsterisk();
		} else {
			parent.fecharModalFilha();
		}
	}else{
		parent.pesquisarItensFiltro();
	}
}

function fecharModalFilha(){
	$('#modal_editarCadastrarSolicitacaoFilha').modal('hide');
	document.form.fireEvent('abrirListaDeSubSolicitacoes');
}

function fecharModalSubSolicitacao(){
	var modalAsterisk = parametroAdicionalAsterisk;
	if(iframe){
		if(modalAsterisk) {
			parent.fecharModalSolicitacaoAsterisk();
		} else {
			parent.fecharModalFilha();
		}
	}else{
		parent.fecharModalNovaSolicitacao();
	}
}

function cancelar(){
	bootbox.confirm(i18n_message("solicitacaoServico.cancelarOperacao"), function(result) {
	if(result == true){
		fecharModalSubSolicitacao()
			$.gritter.add({
				title: 'CITSMART',
				text: i18n_message("MSG16"),
				class_name: 'gritter-primary',
				time: 1300
			});
	
	}
	});
}

function serializaMudanca(){
	var mudancas = HTMLUtils.getObjectsByTableId('tblMudanca');
	document.form.colItensMudanca_Serialize.value =  ObjectUtils.serializeObjects(mudancas);
}

function serializaProblema(){
	var problemas = HTMLUtils.getObjectsByTableId('tblProblema');
	document.form.colItensProblema_Serialize.value =  ObjectUtils.serializeObjects(problemas);
}

function serializaIC(){
	var ics = HTMLUtils.getObjectsByTableId('tblIC');
	document.form.colItensIC_Serialize.value =  ObjectUtils.serializeObjects(ics);
}

function serializaBaseConhecimento(){
	var baseConhecimento = HTMLUtils.getObjectsByTableId('tblBaseConhecimento');
	document.form.colItensBaseConhecimento_Serialize.value =  ObjectUtils.serializeObjects(baseConhecimento);
}

/*
Mário Júnior
02/12/2013 - Sol. 123057
- Criado para fechar popup quando der enter na mensagem de inserção
*/
$('#mensagem_insercao').on('show', function() {
	$(window).keydown(function(event){
	if(event.keyCode == 13){
		$("#mensagem_insercao").modal("hide");
		fecharSolicitacaoModal();
	}
	});
});


/*
Pedro Lino
23/10/2013 - Sol. 120948
- Alterado height de iframeEditarCadastrarProblema e iframeEditarCadastrarMudanca para 550 para retirar barra de rolagem via iframe
*/
/*
Rodrigo Pecci Acorse
07/11/2013 - Sol. 123390
- Os iframs que possuiam src definido aqui no load da página foram removidos e adicionados somente na ação do item. Os frames serão carregados somente quando necessário.
*/
$(window).load(function(){
$('#divInformacoesComplementares').html('<iframe id="fraInformacoesComplementares" name="fraInformacoesComplementares" src="about:blank" class="inativo iframeSemBorda" width="100%" height="100%" style="width: 99%; height: 100%; border: none; overflow: auto"></iframe>');
$('#conteudoCadastroNovoColaborador').html('<iframe id="frameCadastroNovoColaborador" src="about:blank" width="99%" height="545" class="iframeSemBorda"></iframe>');
$('#conteudoframeBaseConhecimento').html('<iframe id="frameBaseConhecimento" src="about:blank" width="99%" height="460" class="iframeSemBorda"></iframe>');
$('#conteudoframeExibirOrigem').html('<iframe id="frameExibirOrigem" src="about:blank" width="99%" height="400" class="iframeSemBorda"></iframe>');
$('#conteudovisualizaProblemaBaseConhecimento').html('<iframe id="visualizaProblemaBaseConhecimento" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
$('#conteudoPesquisaItemConfiguracao').html('<iframe id="framePesquisaItemConfiguracao" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
$('#conteudoAgendaAtvPeriodicas').html('<iframe id="frameAgendaAtvPeriodicas" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
$('#conteudoCadastroOcorrenciaSolicitacao').html('<iframe id="frameCadastroOcorrenciaSolicitacao" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
$('#conteudoframeCadastroNovaSolicitacaoFilho').html('<iframe id="frameCadastroNovaSolicitacaoFilho" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
$('#conteudoiframeInformacaoItemConfiguracao').html('<iframe id="iframeInformacaoItemConfiguracao" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
$('#conteudoiframeEditarCadastrarProblema').html('<iframe id="iframeEditarCadastrarProblema" src="about:blank" width="99%" height="550" class="iframeSemBorda"></iframe>');
$('#conteudoiframeEditarCadastrarMudanca').html('<iframe id="iframeEditarCadastrarMudanca" src="about:blank" width="99%" height="550" class="iframeSemBorda"></iframe>');
});