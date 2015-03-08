		function tamanhoCampo(obj, limit) {
	  		if (obj.value.length >= limit) {
	  			obj.value = obj.value.substring(0, limit-1);
	  		}
	  	}

		var objTab = null;

		
		$(window).load(function(){
			
			$("#POPUP_SERVICOCONTRATO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			$("#POPUP_FLUXOTRABALHO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			
			
		});
		
		

		function excluir() {
			if (document.getElementById("idServicoContrato").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}

		function closePopup(idServicoContrato) {
			parent.fecharVisaoPainel(idServicoContrato,"ServicoContrato");
		}

		function setaValorServicoContrato(){
			var servicoContrato = document.getElementById('idServico').value;
			document.getElementById('idServico').value = servicoContrato;
		}

		$(function() {
			$("#addServicoContrato").click(function() {
				$("#POPUP_SERVICOCONTRATO").dialog("open");
			});
		});

		function LOOKUP_SERVICO_select(id, desc){
			document.form.idServico.value = id;
			document.form.fireEvent("restoreServicoContrato");
		}
		function fecharPopup(){
			$("#POPUP_SERVICOCONTRATO").dialog("close");
		}

		function gravar(){
			if(serializa()){
				document.form.save();
			}
		}

		function ComparaDatas() {
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
		$(function() {
			$("#addFluxo").click(function() {
				$("#POPUP_FLUXOTRABALHO").dialog("open");
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
			coluna.innerHTML = '<img id="imgDelEmpregado' + contTipoFluxo + '" style="cursor: pointer;" title="'i18n_message("citcorpore.comum.excluir")+'" src="'+ctx+'/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaFluxo\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = tipoFluxoNome + '<input type="hidden" id="idTipoFluxo' + contTipoFluxo + '" name="' + idTipofluxo + '" value="' + tipoFluxoNome + '" />';
			coluna = row.insertCell(2);
			coluna.innerHTML = faseNome + '<input type="hidden" id="idFase' + contTipoFluxo + '" name="' + idFase + '" value="' + faseNome + '" />';
			coluna = row.insertCell(3);
			coluna.innerHTML = fluxoPrincipalNome + '<input type = "hidden" id = "principal' + contTipoFluxo + '" name = "' + idFluxoPrincipal + '" value ="' + fluxoPrincipalNome + '" />';

			$("#POPUP_FLUXOTRABALHO").dialog("close");
		}

		function removeLinhaTabela(idTabela, rowIndex) {
			 if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.idTipoFluxoExclusao.value = eval('document.getElementById("idTipoFluxo' + rowIndex + '").name');
				document.form.idFaseExclusao.value = eval('document.getElementById("idFase' + rowIndex + '").name');
				document.form.principalExclusao.value = eval('document.getElementById("principal' + rowIndex + '").name');
				/* document.form.fireEvent("deleteFluxo"); */
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.emailsSerializados.value = eval('document.getElementById("idTipoFluxo' + rowIndex + '").name');
				contTipoFluxo = 0;
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

			 return true;

			 }

		 function getFluxo(seq) {
			 var fluxoServicoDTO = new CIT_FluxoServicoDTO();
			 fluxoServicoDTO.sequencia = seq;
			 fluxoServicoDTO.idTipoFluxo = document.getElementById('idTipoFluxo' + seq).name;
			 fluxoServicoDTO.idFase = document.getElementById('idFase' + seq).name;
			 fluxoServicoDTO.principal = document.getElementById('principal' + seq).name;
			 return fluxoServicoDTO;
		 }

		 function exibirGrid(){
			document.getElementById('gridFluxos').style.display = 'block';
		}