		function tamanhoCampo(obj, limit) {
	  		if (obj.value.length >= limit) {
	  			obj.value = obj.value.substring(0, limit-1);
	  		}
	  	}

		
		$(window).load(function(){
			
			setTimeout(function(){
				if (idServicoContrato != '' && idServicoContrato != '') {
					document.form.fireEvent("recupera");
				}
			}, 500);

			$("#POPUP_SERVICOCONTRATO").dialog({
				autoOpen : false,
				width : 625,
				height : 400,
				modal : true
			});

			$("#POPUP_FLUXOTRABALHO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			document.formPesquisaServicoAtivo.pesqLockupLOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO_IDCONTRATO.value = idContrato;
			
		});	

		

		function closePopup() {
			parent.fecharVisao();
		}

		function setaValorServicoContrato(){
			var servicoContrato = document.getElementById('idServico').value;
			document.getElementById('idServico').value = servicoContrato;
		}

		$(function() {
			$("#btnLimpar").click(function() {

				document.form.clear();

				limparTabelaServicos();

			});

			$("#addServicoContrato").click(function() {
				$("#POPUP_SERVICOCONTRATO").dialog("open");
			});
		});

		 function LOOKUP_SERVICOATIVOS_DIFERENTE_CONTRATO_select(id, desc){
			addLinhaTabelaServico(id, desc);
		}

		function fecharPopup(){
			$("#POPUP_SERVICOCONTRATO").dialog("close");
		}

		function gravar(){
			JANELA_AGUARDE_MENU.show();
			serializa();
			serializaServicos();
			document.form.save();
			contServico = 0;
		}

		function ComparaDatas(){
			var data1 = document.getElementById("dataInicio").value;
			var data2 = document.getElementById("dataFim").value;

			var nova_data1 = parseInt(data1.split("/")[2].toString() + data1.split("/")[1].toString() + data1.split("/")[0].toString());
			var nova_data2 = parseInt(data2.split("/")[2].toString() + data2.split("/")[1].toString() + data2.split("/")[0].toString());

			 if (nova_data2 < nova_data1){
				 alert(i18n_message("contrato.dataFimMenorDataInicio"));
				 document.getElementById("dataFim").value = "";
			 }
		}

		var contTipoFluxo = 0;
		var contServico = 0;
		$(function() {
			$("#addFluxo").click(function() {
				$("#POPUP_FLUXOTRABALHO").dialog("open");
			});
		});

		$(function() {
			$("#addServico").click(function() {
				$("#POPUP_SERVICOCONTRATO").dialog("open");
			});

			$("#addTodosOsServicos").click(function() {
				JANELA_AGUARDE_MENU.show();
				limparTabelaServicos();
				document.form.fireEvent("adicionarTodosOsServicos");
			});
		});

		function gravarFluxo(){
			var fase = document.getElementById("idfase");
			var faseNome = fase.options[fase.selectedIndex].text;
			var idFase = document.getElementById("idfase").value;

			var tipofluxo = document.getElementById("idtipofluxo");
			var tipoFluxoNome = tipofluxo.options[tipofluxo.selectedIndex].text;
			var idTipofluxo = document.getElementById("idtipofluxo").value;

			var fluxoPrincipal = document.getElementById("fluxoPrincipal");
			var fluxoPrincipalNome = fluxoPrincipal.options[fluxoPrincipal.selectedIndex].text;
			var idFluxoPrincipal = document.getElementById("fluxoPrincipal").value;

			if (idFase == "") {
				 alert(i18n_message("contrato.faseCampoObrigatorio"));
				return;
			}
			else if (idTipofluxo == ""){
				 alert(i18n_message("contrato.fluxoCampoObrigatorio"));
				return;
			}
			else{
				addLinhaTabelaFluxoTrabalho(idFase,idTipofluxo,idFluxoPrincipal,faseNome,tipoFluxoNome,fluxoPrincipalNome);
			}
		}

		function addLinhaTabelaFluxoTrabalho(idFase,idTipofluxo,idFluxoPrincipal,faseNome,tipoFluxoNome,fluxoPrincipalNome){
			var tbl = document.getElementById('tabelaFluxo');
			var lastRow = tbl.rows.length;
			var row = tbl.insertRow(lastRow);
			var coluna = row.insertCell(0);
			contTipoFluxo++;
			coluna.innerHTML = '<img id="imgDelEmpregado' + contTipoFluxo + '" style="cursor: pointer;" title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaFluxo\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = tipoFluxoNome + '<input type="hidden" id="idTipoFluxo' + contTipoFluxo + '" name="' + idTipofluxo + '" value="' + tipoFluxoNome + '" />';
			coluna = row.insertCell(2);
			coluna.innerHTML = faseNome + '<input type="hidden" id="idFase' + contTipoFluxo + '" name="' + idFase + '" value="' + faseNome + '" />';
			coluna = row.insertCell(3);
			coluna.innerHTML = fluxoPrincipalNome + '<input type = "hidden" id = "principal' + contTipoFluxo + '" name = "' + idFluxoPrincipal + '" value ="' + fluxoPrincipalNome + '" />';

			$("#POPUP_FLUXOTRABALHO").dialog("close");
		}

		function addLinhaTabelaServico(idServico, nomedDoServico){
			var tbl = document.getElementById('tabelaServico');
			var lastRow = tbl.rows.length;
			var row = tbl.insertRow(lastRow);
			var coluna = row.insertCell(0);
			contServico++;
			coluna.innerHTML = '<img id="imgDelEmpregado' + contServico + '" style="cursor: pointer;" title="'+i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabelaServico(\'tabelaServico\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = idServico + '<input type="hidden" id="idServico' + contServico + '" name="' + idServico + '" value="' + idServico + '" />';
			coluna = row.insertCell(2);
			coluna.innerHTML = nomedDoServico + '<input type="hidden" id="nomeServico' + contServico + '" name="' + idServico + '"  />';
			serializaServicos();

			$("#POPUP_SERVICOCONTRATO").dialog("close");


		}



		function removeLinhaTabela(idTabela, rowIndex) {
			 if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.idTipoFluxoExclusao.value = eval('document.getElementById("idTipoFluxo' + rowIndex + '").name');
				document.form.idFaseExclusao.value = eval('document.getElementById("idFase' + rowIndex + '").name');
				document.form.principalExclusao.value = eval('document.getElementById("principal' + rowIndex + '").name');
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.emailsSerializados.value = eval('document.getElementById("idTipoFluxo' + rowIndex + '").name');
				contTipoFluxo = 0;
		 	}
		 }

		function removeLinhaTabelaServico(idTabela, rowIndex) {
			 if (confirm(i18n_message("citcorpore.comum.deleta"))) {
// 				document.form.idServicoExclusao.value = eval('document.getElementById("idServico' + contServico + '").name');
				/* document.form.fireEvent("deleteFluxo"); */
				HTMLUtils.deleteRow(idTabela, rowIndex);
				contServico = 0;
		 	}
		 }

		 function serializa(){
			 var tabela = document.getElementById('tabelaFluxo');
			 var count = tabela.rows.length;
			 var contadorAux = 0;
			 var prioridadeUnidade = new Array();

			 for ( var i = 1; i <= count; i++) {
				 var trObj = document.getElementById('idTipoFluxo' + i);
				 if (!trObj) {
				 continue;
				 }
				 prioridadeUnidade[contadorAux] = getFluxo(i);
				 contadorAux = contadorAux + 1;
			 }

			 var prioridadeUnidadeSerializada = ObjectUtils.serializeObjects(prioridadeUnidade);

			 document.form.fluxosSerializados.value = prioridadeUnidadeSerializada;
		 }

		 function serializaServicos(){
			 var tabela = document.getElementById('tabelaServico');
			 var count = contServico + 1;
			 var servicoArray = [];

			 for ( var i = 1; i <= count; i++) {
				 var trObj = document.getElementById('idServico' + i);

				 if (trObj != null){
					 var servico = new ServicoDTO(trObj.value);
					 servicoArray.push(servico);
				 }
			 }

			 var listServico = ObjectUtils.serializeObjects(servicoArray);

			 document.form.servicosSerializados.value = listServico;
		 }

		 function getFluxo(seq) {
			 var fluxoServicoDTO = new CIT_FluxoServicoDTO();
			 fluxoServicoDTO.sequencia = seq;
			 fluxoServicoDTO.idTipoFluxo = document.getElementById('idTipoFluxo' + seq).name;
			 fluxoServicoDTO.idFase = document.getElementById('idFase' + seq).name;
			 fluxoServicoDTO.principal = document.getElementById('principal' + seq).name;
			 return fluxoServicoDTO;
		 }

		 function ServicoDTO(idServ){
			 this.idServico = idServ;
		 }

		 function exibirGrid(){
			document.getElementById('gridFluxos').style.display = 'block';
		}

		 function limparTabelaServicos(){
			contServico = 0;
			var tabela = document.getElementById('tabelaServico');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			}
		}

		 ValidacaoUtils.validaRequired = function(field, label){
			var bTexto = false;

			if (HTMLUtils._isHTMLElement(field, "input")){
				if (field.type == "text"){
					bTexto = true;
				}
			}

			if (bTexto){
				if(StringUtils.isBlank(field.value)){
					alert(label + i18n_message("citcorpore.comum.campo_obrigatorio"));
					try{
						field.focus();
						JANELA_AGUARDE_MENU.hide();
					}catch(e){
					}
				    return false;
				}
			}else{
				if(StringUtils.isBlank(HTMLUtils.getValue(field.id, field.form))){
					alert(label + i18n_message("citcorpore.comum.campo_obrigatorio"));
					JANELA_AGUARDE_MENU.hide();
					try{
						field.focus();
					}catch(e){
					}
				    return false;
				}
			}
			return true;
		}