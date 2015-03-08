
		var popup;
		addEvent(window, "load", load, false);
		function load() {
			popup = new PopupManager(800, 600, "${ctx}/pages/");
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
			$("#POPUP_UNIDADE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			$("#POPUP_USUARIO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			$("#POPUP_REQUISITOSLA").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

		}
		$(function () {
			$( "#tabs" ).tabs();

			$("#addUnidade").click(function() {
				$("#POPUP_UNIDADE").dialog("open");
			});

			$("#addUsuario").click(function() {
				$("#POPUP_USUARIO").dialog("open");
			});

			$("#addRequisitoSLA").click(function() {
				$("#POPUP_REQUISITOSLA").dialog("open");
			});

		});

		function validaData(dataInicio, dataFim, avaliarEm) {
			if (typeof(locale) === "undefined") locale = '';

			var dtInicio = new Date();
			var dtFim = new Date();
			var avEm = new Date();

			var dtInicioConvert = '';
			var dtFimConvert = '';
			var avEmConvert = '';
			var dtInicioSplit = dataInicio.split("/");
			var dtFimSplit = dataFim.split("/");
			var avEmSplit = avaliarEm.split("/");

			if (locale == 'en') {
				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
				avEmConvert = avEmSplit[2] + "/" + avEmSplit[0] + "/" + avEmSplit[1];
			} else {
				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
				avEmConvert = avEmSplit[2] + "/" + avEmSplit[1] + "/" + avEmSplit[0];
			}

			dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
			dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;
			avEm.setTime(Date.parse(avEmConvert)).setFullYear;

			if (dtInicio > avEm){
				alert(i18n_message("solicitacaoservico.validacao.dataavaliarincorreta"));
				return false;
			}else if (dtInicio > dtFim){
				alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
				return false;
			}else
				return true;
		}

		function abreFechaMaisMenos(obj,idObj){
			var n = obj.src.indexOf(ctx + '/imagens/mais.jpg');
			if (n > -1){
				document.getElementById(idObj).style.display='block';
				document.getElementById('img_' + idObj).src = ctx + '/imagens/menos.jpg';
			}else{
				document.getElementById(idObj).style.display='none';
				document.getElementById('img_' + idObj).src = ctx + '/imagens/mais.jpg';
			}
		}

		function LOOKUP_ACORDONIVELSERVICOGLOBAL_select(id, desc) {
			JANELA_AGUARDE_MENU.show();
			document.form.restore({idAcordoNivelServico:id});
		}

		function LOOKUP_UNIDADE_select(id, desc) {

			var table = document.getElementById('tabelaPrioridadeUnidade');
			var tableSize = table.rows.length;
			var contadorAux = 0;
			if(tableSize >= 2){
				for (var i = 1; i < tableSize; i++) {
					var trObj = document.getElementById('idUnidade' + i);
					if (!trObj) {
						continue;
					}
					if(trObj.value == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return;
					}
				}
			}

			var valor = desc.split(' - ');

			document.form.idUnidadePrioridade.value = id;
			document.form.addUnidade.value = valor[0];

			$("#POPUP_UNIDADE").dialog("close");

		}

		function LOOKUP_SOLICITANTE_select(id, desc) {

			var table = document.getElementById('tabelaPrioridadeUsuario');
			var tableSize = table.rows.length;
			var contadorAux = 0;
			if(tableSize >= 2){
				for (var i = 1; i < tableSize; i++) {
					var trObj = document.getElementById('idUsuario' + i);
					if (!trObj) {
						continue;
					}
					if(trObj.value == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return;
					}
				}
			}

			var valor = desc.split(' - ');

			document.form.idUsuarioPrioridade.value = id;
			document.form.addUsuario.value = valor[0];

			$("#POPUP_USUARIO").dialog("close");

		}

		function LOOKUP_REQUISITOSLA_select(id, desc) {

			var table = document.getElementById('tabelaRequisitoSLA');
			var tableSize = table.rows.length;
			var contadorAux = 0;
			if(tableSize >= 2){
				for (var i = 1; i < tableSize; i++) {
					var trObj = document.getElementById('idRequisitoSLA' + i);
					if (!trObj) {
						continue;
					}
					if(trObj.value == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return;
					}
				}
			}

			var valor = desc.split(' - ');

			document.form.idRequisitoSLAVinc.value = id;
			document.form.addRequisitoSLA.value = valor[0];

			$("#POPUP_REQUISITOSLA").dialog("close");

		}

		function avaliaTipoSLA(){
			document.getElementById('divByDisponibilidade').style.display = 'none';
			document.getElementById('divByTempos').style.display = 'none';
			document.getElementById('divByDiversos').style.display = 'none';

			var value = document.getElementById("tipo").value;

			if (value == 'D'){
				document.getElementById('divByDisponibilidade').style.display = 'block';
			}
			if (value == 'T'){
				document.getElementById('divByTempos').style.display = 'block';
			}
			if (value == 'V'){
				document.getElementById('divByDiversos').style.display = 'block';
			}
		}

		function addUnidadeRow(){

			var idUnidade = document.getElementById("idUnidadePrioridade").value;
			var unidadeNome = document.getElementById("addUnidade").value;
			var prioridadeValor = document.getElementById("prioridadeUnidade").value;

			if(unidadeNome == '' || prioridadeValor == ''){
				alert(i18n_message("acordoNivelServico.informarCampos"));
			}else{
				insereRowUnidade(idUnidade, unidadeNome, prioridadeValor);
			}

		}

		function addUsuarioRow(){

			var idUsuario = document.getElementById("idUsuarioPrioridade").value;
			var usuarioNome = document.getElementById("addUsuario").value;
			var prioridadeValor = document.getElementById("prioridadeUsuario").value;

			if(usuarioNome == '' || prioridadeValor == ''){
				alert(i18n_message("acordoNivelServico.informarCampos"));
			}else{
				insereRowUsuario(idUsuario, usuarioNome, prioridadeValor);
			}

		}

		function addRequisitoSLARow(){

			var idRequisitoSLA = document.getElementById("idRequisitoSLAVinc").value;
			var assuntoRequisito = document.getElementById("addRequisitoSLA").value;
			var dataVinculacao = document.getElementById("dataVinculacaoSLA").value;

			if(assuntoRequisito == '' || dataVinculacao == ''){
				alert(i18n_message("acordoNivelServico.informarCampos"));
			}else{
				insereRowRequisitoSLA(idRequisitoSLA, assuntoRequisito, dataVinculacao);
			}

		}

		function addRevisarSLARow(){

			var dataRevisar = document.getElementById("dataRevisarSLA").value;
			var detalhes = document.getElementById("detalhesSLA").value;
			var observacao = document.getElementById("observacaoSLA").value;

			if(dataRevisar == '' || detalhes == ''){
				alert(i18n_message("acordoNivelServico.informarCampos"));
			}else{
				insereRevisarSLARow(dataRevisar, detalhes, observacao);
			}

		}

		function gravar(){

			var value = document.getElementById("tipo").value;
			if(value == ''){
				alert(i18n_message("acordoNivelServico.tipoAcordoSelecione"));
				return;
			}

			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;
			var avaliarEm = document.getElementById("avaliarEm").value;

			document.getElementById("disponibilidade").value = document.getElementById("disponibilidade").value.replace(",", ".");

			if(!validaData(dataInicio, dataFim, avaliarEm)){
				return;
			}

			limpaCamposTelaNaoUsada();

			aguarde();
			serializaPrioridadeUnidade();
			serializaPrioridadeUsuario();
			serializaRequisitoSLA();
			serializaRevisarSLA();
			document.form.save();
			fechar_aguarde();
		}

		function excluir(){
			if(document.getElementById("idAcordoNivelServico").value != ""){
				if (confirm(i18n_message("dinamicview.confirmaexclusao"))){
					document.form.fireEvent('excluir');
				}
			}
		}

		function limpar(){
			document.form.clear();
			deleteAllRowsPrioridadeUnidade();
			deleteAllRowsPrioridadeUsuario();
			deleteAllRowsRequisitoSLA();
			deleteAllRowsRevisarSLA();
			document.form.fireEvent("limpar");
		}

		function limpaCamposTelaNaoUsada(){

			var tipo = document.getElementById("tipo").value;

			if(tipo == "D"){
				deleteAllRowsPrioridadeUnidade();
				deleteAllRowsPrioridadeUsuario();
				document.getElementById("IDPRIORIDADEAUTO1").value = "";
				document.getElementById("IDGRUPO1").value = "";
				document.getElementById("TEMPOAUTO").value = "";
				document.getElementById("valorLimite").value = "";
				document.getElementById("unidadeValorLimite").value = "";
				document.getElementById("detalheGlosa").value = "";
				document.getElementById("detalheLimiteGlosa").value = "";
				zeraValores();
			}

			if(tipo == "T"){
				document.getElementById("disponibilidade").value = "";
				document.getElementById("valorLimite").value = "";
				document.getElementById("unidadeValorLimite").value = "";
				document.getElementById("detalheGlosa").value = "";
				document.getElementById("detalheLimiteGlosa").value = "";
			}

			if(tipo == "V"){
				deleteAllRowsPrioridadeUnidade();
				deleteAllRowsPrioridadeUsuario();
				document.getElementById("disponibilidade").value = "";
				document.getElementById("IDPRIORIDADEAUTO1").value = "";
				document.getElementById("IDGRUPO1").value = "";
				document.getElementById("TEMPOAUTO").value = "";
				zeraValores();
			}
		}

		function zeraValores(){
			for(var i = 1; i <= 5; i++){
				document.getElementById("HH-1-" + i).value = "0";
				document.getElementById("HH-2-" + i).value = "0";
				document.getElementById("MM-1-" + i).value = "0";
				document.getElementById("MM-2-" + i).value = "0";
			}
		}

		function aguarde(){
			JANELA_AGUARDE_MENU.show();
		}

		function fechar_aguarde(){
	    	JANELA_AGUARDE_MENU.hide();
		}

		/* @autor edu.braz
		 *  14/03/2014 */
		//funçao para alterar o tipo sla de forma que os campos retornen limpos
		function alterarTipoSLA(){
			document.getElementById('divByDisponibilidade').style.display = 'none';
			document.getElementById('divByTempos').style.display = 'none';
			document.getElementById('divByDiversos').style.display = 'none';

			var value = document.getElementById("tipo").value;

			if (value == 'D'){
				document.getElementById('divByDisponibilidade').style.display = 'block';
				deleteAllRowsRequisitoSLA();
				deleteAllRowsRevisarSLA();
				document.getElementById("disponibilidade").value = "";
			}
			if (value == 'T'){
				document.getElementById('divByTempos').style.display = 'block';
				deleteAllRowsPrioridadeUnidade();
				deleteAllRowsPrioridadeUsuario();
				document.getElementById("IDPRIORIDADEAUTO1").value = "";
				document.getElementById("IDGRUPO1").value = "";
				document.getElementById("TEMPOAUTO").value = "";
				document.getElementById("idEmail").value = "";
				document.getElementById("addUnidade").value = "";
				document.getElementById("addUsuario").value = "";
				document.getElementById("prioridadeUnidade").value = "";
				document.getElementById("prioridadeUsuario").value = "";
				zeraValores();
			}
			if (value == 'V'){
				document.getElementById('divByDiversos').style.display = 'block';
				deleteAllRowsRequisitoSLA();
				deleteAllRowsRevisarSLA();
				document.getElementById("valorLimite").value = "";
				document.getElementById("unidadeValorLimite").value = "";
				document.getElementById("detalheGlosa").value = "";
				document.getElementById("detalheLimiteGlosa").value = "";
			}
		}


		// Cristian: a propriedade maxlength não existe para o objeto TEXTAREA. Mas com esta função, você pode atribuir esta característica a este objeto.
		window.onload = function() {
			  var txts = document.getElementsByTagName('TEXTAREA')

			  for(var i = 0, l = txts.length; i < l; i++) {
			    if(/^[0-9]+$/.test(txts[i].getAttribute("maxlength"))) {
			      var func = function() {
			        var len = parseInt(this.getAttribute("maxlength"), 10);

			        if(this.value.length > len) {
			          alert('Tamanho máximo excedido: ' + len);
			          this.value = this.value.substr(0, len);
			          return false;
			        }
			      }

			      txts[i].onkeyup = func;
			      txts[i].onblur = func;
			    }
			  }
			}


		
