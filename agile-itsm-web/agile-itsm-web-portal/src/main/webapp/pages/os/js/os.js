		var objTab = null;

		
		$(window).load(function(){
			
			document.form.afterRestore = function () {
				document.getElementById('tabTela').tabber.tabShow(0);
			}
			
			document.form.onClear = function(){
				GRID_ITENS.deleteAllRows();
			};
			
		});		
		

		function LOOKUP_OS_select(id,desc){
			document.form.restore({idOS:id});
		}

		function gravarForm(){

			document.getElementById("dataInicio").disabled = false;
			document.getElementById("dataFim").disabled = false;

			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;
			if(!validaData(dataInicio, dataFim)){
				return;
			}
			var count = GRID_ITENS.getMaxIndex();
			var existeErro = false;
			var contadorAux = 0;
			var objs = new Array();
			for (var i = 1; i <= count; i++){
				var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
				if (!trObj){
					continue;
				}
				var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
				var complexidadeObj = document.getElementById('complexidade' + NumberUtil.zerosAEsquerda(i,5));
				var demandaObj = document.getElementById('demanda' + NumberUtil.zerosAEsquerda(i,5));
				var objObj = document.getElementById('obs' + NumberUtil.zerosAEsquerda(i,5));
				var formulaObj = document.getElementById('formula' + NumberUtil.zerosAEsquerda(i,5));
				var idAtividadeServicoContrato = document.getElementById('idAtividadeServicoContrato' + NumberUtil.zerosAEsquerda(i,5));
				var contabilizarObj = document.getElementById('contabilizar' + NumberUtil.zerosAEsquerda(i,5));
				var idServicoContratoContabilObj = document.getElementById('idServicoContratoContabil' + NumberUtil.zerosAEsquerda(i,5));
				trObj.bgColor = 'white';
				complexidadeObj.style.backgroundColor = 'white';
				quantidadeObj.style.backgroundColor = 'white';
				demandaObj.style.backgroundColor = 'white';
				objObj.style.backgroundColor = 'white';
				if (complexidadeObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';
					objObj.style.backgroundColor = 'orange';
					alert('Informe a complexidade! Linha: ' + i);
					return;
				}
				if (demandaObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';
					objObj.style.backgroundColor = 'orange';
					alert('Informe a demanda! Linha: ' + i);
					return;
				}
				if (quantidadeObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';
					objObj.style.backgroundColor = 'orange';
					alert('Informe o custo! Linha: ' + i);
					return;
				}
				var objItem = new CIT_DemandaDTO();
				objItem.complexidade = complexidadeObj.value;
				objItem.custoAtividade = quantidadeObj.value;
				objItem.descricaoAtividade = demandaObj.value;
				objItem.obsAtividade = objObj.value;
				objItem.formula = formulaObj.value;
				objItem.idAtividadeServicoContrato = idAtividadeServicoContrato.value;
				objItem.contabilizar = contabilizarObj.value;
				objItem.idServicoContratoContabil = idServicoContratoContabilObj.value;
				objs[contadorAux] = objItem;
				contadorAux = contadorAux + 1;

				if (permiteValorZeroAtv!="S"){
					if (quantidadeObj.value == '' || quantidadeObj.value == '0,00' || quantidadeObj.value == '0'){
						trObj.bgColor = 'orange';
						complexidadeObj.style.backgroundColor = 'orange';
						quantidadeObj.style.backgroundColor = 'orange';
						demandaObj.style.backgroundColor = 'orange';
						objObj.style.backgroundColor = 'orange';
						alert('Falta definir custo da atividade ! Linha: ' + i);
						existeErro = true;
					}
				}
			}
			if(existeErro){
				return;
			}
			document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
			document.form.save();
		}
		var seqSelecionada = '';
		function setaRestoreItem(complex, det, obs, custo,  formula, idAtividadeServicoContrato, contabilizar, idServicoContratoContabil){
			if (seqSelecionada != ''){
				eval('HTMLUtils.setValue(\'complexidade' + seqSelecionada + '\', \'' + complex + '\')');
				eval('document.form.demanda' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
				eval('document.form.obs' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + obs + '\')');
				eval('document.form.formula' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + formula + '\')');
				eval('document.form.quantidade' + seqSelecionada + '.value = "' + custo + '"');
				eval('document.form.idAtividadeServicoContrato' + seqSelecionada + '.value = "' + idAtividadeServicoContrato + '"');
				eval('document.form.contabilizar' + seqSelecionada + '.value = "' + contabilizar + '"');
				eval('document.form.idServicoContratoContabil' + seqSelecionada + '.value = "' + idServicoContratoContabil + '"');
			}
		}

		function confirmaCancelamento(){
			if(confirm(i18n_message("os.cancelarOS"))){
				document.form.fireEvent('cancelaOSeRAs');
			}else{
				return false;
			}
		}

		/**
		* @author rodrigo.oliveira
		*/
		function validaData(dataInicio, dataFim) {
			if (typeof(locale) === "undefined") locale = '';

			var dtInicio = new Date();
			var dtFim = new Date();

			var dtInicioConvert = '';
			var dtFimConvert = '';
			var dtInicioSplit = dataInicio.split("/");
			var dtFimSplit = dataFim.split("/");

			if (locale == 'en') {
				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
			} else {
				dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
				dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
			}

			dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
			dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

			if (dtInicio > dtFim){
				alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
				return false;
			}else
				return true;
		}

		function selecionaServicoContrato(){
			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;

			if(dataInicio == "" || dataFim == ""){
				alert(i18n_message("os.InformePeriodoSelecionarServico"));
				document.getElementById("idServicoContrato").value = "";
				return;
			}

			if (dataInicio != ""){
				if(!DateTimeUtil.isValidDate(document.form.dataInicio.value)){
					alert(i18n_message("citcorpore.comum.datainvalida"));
					document.form.dataInicio.focus();
					return;
				}
			}
			if (dataFim != ""){
				if(!DateTimeUtil.isValidDate(document.form.dataFim.value)){
					alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
					document.form.dataFim.focus();
					return;
				}
			}

		}

		function restoreInfoServios(){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent('restoreInfoServicoContrato');
		}

		function atualizaInfoServios(){
			if (!document.form.idServicoContrato.disabled){
				if (confirm('Atenção! Esta ação pode atualizar as atividades da OS, conforme registradas no serviço. Confirma ')){
					restoreInfoServios();
					alert('Atenção! É necessário rever os RAs. Se houver RAs gerados para esta OS, verifique cada um!');
				}
			}else{
				alert('Não é possível atualizar a lista de tarefas. A OS está bloqueada para esta ação!');
			}
		}

		function GRID_ITENS_onDeleteRowByImgRef(objImg){
			var situacaoOs = document.getElementById("situacaoOS").value;
			if(situacaoOs != 1){
				alert(i18n_message("os.naoPodeExcluirFaseCriacao"));
				return false;
			}
			//$("#yourdropdownid option:selected").text();

			if(confirm(i18n_message('dinamicview.desejaexcluirlinha'))){
				var indice = objImg.parentNode.parentNode.rowIndex;
				HTMLUtils.deleteRow("GRID_ITENS_tblItens", indice);
				recalculacusto();
				preencheNumeracaoItens();
				return false;
			}else{
				return false;
			}
		}

		function calculaCusto(){
			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;

			if (dataInicio != ""){
				if(!DateTimeUtil.isValidDate(document.form.dataInicio.value)){
					alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
					alert(i18n_message("citcorpore.comum.datainvalida"));
					document.form.dataInicio.focus();
					return;
				}
			}
			if (dataFim != ""){
				if(!DateTimeUtil.isValidDate(document.form.dataFim.value)){
					alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
					alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
					document.form.dataFim.focus();
					return;
				}
			}
			document.form.fireEvent('restoreInfoServicoContrato');
		}

		/**
		* Recalcula custo das atividades ao excluir
		* @author euler.ramos
		*/
		function recalculacusto(){
			var count = GRID_ITENS.getMaxIndex();
			var contadorAux = 0;
			var objs = new Array();
			for (var i = 1; i <= count; i++){
				var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
				if (!trObj){
					continue;
				}
				var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
				var complexidadeObj = document.getElementById('complexidade' + NumberUtil.zerosAEsquerda(i,5));
				var demandaObj = document.getElementById('demanda' + NumberUtil.zerosAEsquerda(i,5));
				var objObj = document.getElementById('obs' + NumberUtil.zerosAEsquerda(i,5));
				var formulaObj = document.getElementById('formula' + NumberUtil.zerosAEsquerda(i,5));
				var idAtividadeServicoContrato = document.getElementById('idAtividadeServicoContrato' + NumberUtil.zerosAEsquerda(i,5));
				var contabilizarObj = document.getElementById('contabilizar' + NumberUtil.zerosAEsquerda(i,5));
				var idServicoContratoContabilObj = document.getElementById('idServicoContratoContabil' + NumberUtil.zerosAEsquerda(i,5));
				var objItem = new CIT_DemandaDTO();
				objItem.complexidade = complexidadeObj.value;
				objItem.custoAtividade = quantidadeObj.value;
				objItem.descricaoAtividade = demandaObj.value;
				objItem.obsAtividade = objObj.value;
				objItem.formula = formulaObj.value;
				objItem.idAtividadeServicoContrato = idAtividadeServicoContrato.value;
				objItem.contabilizar = contabilizarObj.value;
				objItem.idServicoContratoContabil = idServicoContratoContabilObj.value;
				objs[contadorAux] = objItem;
				contadorAux = contadorAux + 1;
			}
			document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
			document.form.fireEvent('reCalculaCusto');
		}

		/**
		* Preenche numeração
		* @author rodrigo.oliveira
		*/
		function preencheNumeracaoItens(){
			var count = GRID_ITENS.getMaxIndex();
			flag = false;
			for (var i = 1; i <= count; i++){
				if(!flag){
					var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i,5));
					if(!item){
						flag = true;
						var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i+1,5));
						if(!item){
							continue;
						}
					}
					(item.innerHTML == i < 10) ?  NumberUtil.zerosAEsquerda(i,2) : i;
				}else{
					var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i+1,5));
					if(!item){
						continue;
					}
					(item.innerHTML == i < 10) ?  NumberUtil.zerosAEsquerda(i,2) : i;
				}
			}
		}


		/**
		* Converte número em valor monetário
		* @author rodrigo.oliveira
		*/
		function moeda(num){
			  x = 0;

			  if(num < 0) { num = Math.abs(num);   x = 1; }

			  if(isNaN(num)) num = "0";
			  cents = Math.floor((num*100+0.5)%100);
			    num = Math.floor((num*100+0.5)/100).toString();

			  if(cents < 10) cents = "0" + cents;
			    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
			  num = num.substring(0,num.length-(4*i+3))+'.'+num.substring(num.length-(4*i+3));

			  ret = num + ',' + cents;
			  if (x == 1)
			  ret = '-' + ret;

			  return ret;
		}

		$(function() {
			$('.dtpicker').datepicker();
		});