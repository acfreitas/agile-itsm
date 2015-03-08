

		function TabelaExcecoes(_idTabelaExcecoes){
			var idTabelaExcecoes = _idTabelaExcecoes;
			var excecoes = [];
			var tabela = null;

			this.getExcecoes = function(){
				return excecoes;
			}

			this.setExcecoes = function(novasExcecoes){
				excecoes = novasExcecoes;
				montaTabela();
			}

			this.adicionarExcecao = function(excecao){
				excecoes.push(excecao);
				montaTabela();
			}

			this.limpaLista = function(){
				excecoes.length = 0;
				excecoes = null;
				excecoes = [];
				limpaTabela();
			}

			var limpaTabela = function(){
				while (getTabela().rows.length > 1){
					getTabela().deleteRow(1);
				}
			}

			var montaTabela = function(){
				limpaTabela();
				var excecao;
				for(var i = 0; i < excecoes.length; i++){
					excecao = excecoes[i];
					var linha = getTabela().insertRow(1);

					var celTipo = linha.insertCell(0);
					var celDataInicio = linha.insertCell(1);
					var celDataTermino = linha.insertCell(2);
					var celJornada = linha.insertCell(3);
					var celExcluir = linha.insertCell(4);

					celTipo.innerHTML = excecao.getTipo();
					celDataInicio.innerHTML = excecao.getDataInicio();
					celDataTermino.innerHTML = excecao.getDataTermino();
					celJornada.innerHTML = excecao.getDescJornada();

					var botaoExcluir = getBotao();
					botaoExcluir.setAttribute("id", i);
					celExcluir.appendChild(botaoExcluir);
					botaoExcluir.setAttribute("onclick", "tabExcecoes.removeExcecao(" + i + ")");
				}
			}

			this.removeExcecao = function(indice){
				removeExcecaoDaLista(indice);
				montaTabela();
			}

			/**
			 * Remove item e organiza lista
			 */
			var removeExcecaoDaLista = function(indice){
				excecoes[indice] = null;
				var novaLista = [];
				for(var i = 0 ; i < excecoes.length; i++){
					if(excecoes[i] != null){
						novaLista.push(excecoes[i]);
					}
				}
				excecoes = novaLista;
			}

			var getBotao = function(){
				var botao = new Image();
				botao.setAttribute("style", "cursor: pointer;");
				botao.src = ctx + "/imagens/delete.png";
				return botao;
			}

			var getTabela = function(){
				if(tabela == null){
					tabela = document.getElementById(idTabelaExcecoes);
				}
				return tabela;
			}
		}

		function Excecao(_tipo, _dataInicio, _dataFim, _descJornada, _idJornada){
			this.tipo = _tipo;
			this.dataInicio = _dataInicio;
			this.dataTermino = _dataFim;
			this.idJornada = _idJornada;
			this.descJornada = _descJornada;
			this.idExcecaoCalendario;

			this.getTipo = function(){
				return this.tipo;
			}

			this.getDataInicio = function(){
				return this.dataInicio;
			}

			this.getDataTermino = function(){
				return this.dataTermino;
			}

			this.getDescJornada = function(){
				return this.descJornada;
			}

			this.getIdJornada = function(){
				return this.idJornada;
			}
		}

		var tabExcecoes;
		addEvent(window, "load", load, false);
		function load() {
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}

			tabExcecoes = new TabelaExcecoes("tabelaExcecoes");
			$("#divAdicionarExcecao").dialog({
				title : i18n_message("calendario.title"),
				width : 500,
				height : 300,
				modal : true,
				autoOpen : false,
				resizable : true,
				show : "fade",
				hide : "fade"
			});

		}
	<!--			$('.tabs').tabs('select', 0);-->
			//configuraçães iniciais da popup
			function salvar(){
				//salvar excecoes
				document.form.listaExecoesSerializada.value = ObjectUtils.serializeObjects(tabExcecoes.getExcecoes());
				document.form.save();
			}

			function restaurarTabelaExcecoes(serializado){
				var lista = ObjectUtils.deserializeCollectionFromString(serializado);
				if(lista == null || lista == ""){
					return;
				}
				tabExcecoes.limpaLista();
				var novaLista = [];
				for(var i = 0 ; i < lista.length; i++){
					var opcoes = document.getElementById("idjornadaexcecao").options;
					var descricaoCalendario = "";
					for (var j = 0; j < opcoes.length; j++) {
						if (opcoes[j].value == lista[i].idJornada) {
							descricaoCalendario = opcoes[j].text;
							if (descricaoCalendario == "undefined"){
								descricaoCalendario = "";
							}
							break;
						}
					}
					var excecao = new Excecao(lista[i].tipo, lista[i].dataInicio, lista[i].dataTermino, descricaoCalendario, lista[i].idJornada);
					excecao.idExcecaoCalendario = lista[i].idExcecaoCalendario;
					novaLista[i] = excecao;
				}
				tabExcecoes.setExcecoes(novaLista);
			}

			function LOOKUP_CALENDARIO_select(id, desc) {
				document.form.restore({idCalendario : id});
			}

			function validaData(dataInicial, dataFinal){
				if(dataInicial.value == "" || dataFinal.value == "" ){
					alert(i18n_message("calendario.preenchaDatas"));
					return false;
				}
				if (document.getElementById("permiteDataInferiorHoje").value != 'S'){
					if(comparaComDataAtual(dataInicial) < 0 || comparaComDataAtual(dataFinal) < 0){
						alert(i18n_message("calendario.dataInferiorDataHoje"));
						return false;
					}
				}

				if(!verificaData(dataInicial.value, dataFinal.value)){
					return false;
				}
				return true;
			}

			/**
			* @author rodrigo.oliveira
			*/
			function verificaData(dataInicio, dataFim) {

				var dtInicio = new Date();
				var dtFim = new Date();

				dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
				dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;

				if (dtInicio > dtFim){
					alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
					return false;
				}else
					return true;
			}

			function adicionarExcecao(){
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataTermino").value;

				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
				 	return false;
				}

				if(DateTimeUtil.isValidDate(dataFim) == false){
					 alert(i18n_message("citcorpore.comum.validacao.datafim"));
					 document.getElementById("dataTermino").value = '';
					return false;
				}

				if(validaData(document.getElementById("dataInicio"), document.getElementById("dataTermino"))){
					var select = document.getElementById("idjornadaexcecao");

					var tipoExcecao = "F"
					var jornadaText = select.options[select.selectedIndex].text;
					var jornadaValue = select.options[select.selectedIndex].value;

					if ($("#tipoExcecaoTrabalho").is(':checked')){
						tipoExcecao = "T";
					} else{
						jornadaText = "";
						jornadaValue = null;
					}
					var excecao = new Excecao(tipoExcecao, document.getElementById("dataInicio").value, document.getElementById("dataTermino").value, jornadaText, jornadaValue);

					tabExcecoes.adicionarExcecao(excecao);

					document.formAdicionarExcecao.reset();

					$("#divAdicionarExcecao").dialog("close");
				}
			}

			function clearForm(){
				tabExcecoes.limpaLista();
				document.form.clear();
				setConsideraFeriados();
			}

			function clearTabExecoes(){
				tabExcecoes.limpaLista();
			}

			document.form.onClear = function(){
				window.setTimeout(setConsideraFeriados, 500);
			}
			function setConsideraFeriados(){
				document.form.consideraFeriados[0].checked = true;
			}

			function abrirAdicionarExcecao(){
				$("#divJornada").hide();
				$('#divAdicionarExcecao').dialog('open');
			}

			function hideExcecao(){
				$("#divJornada").hide();
			}

			function showExcecao(){
				$("#divJornada").show();
			}

			function excluir() {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("excluir");
				}
			}

	
