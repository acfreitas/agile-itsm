
			var pilha_abertura=0;
			var pilha_fechamento=0;
			var formulaValidada = "";

					function LOOKUP_FORMULAOS_select(id,desc){
						$('.tabsbar a[href="#tab1-4"]').tab('show');
						document.form.restore({idFormulaOs:id});
					}

					function excluir(){
						if(document.getElementById("idFormulaOs").value != ""){
							if(confirm(i18n_message("gerenciaConfiguracaoTree.desejaExcluir")+"")){
								document.form.fireEvent("delete");
							}
						}
					}

					function limpar(){
						$("#formulaMontada").empty();
						$("#resultadoFormula").empty();
						document.form.clear();
					}

					function validar(){
						if($("#descricao").val()!="" &&  $("#formula").val()!="" &&$("#situacao").val()!=""){
							if(verificarOperador())
								document.form.fireEvent("save");
						}else{
							alert(i18n_message("problema.preencha_campos_obrigatorios"))
						}

					}

					function replaceAll(find, replace, str) {
						  return str.replace(new RegExp(find, 'g'), replace);
					}

					//var chaves = new RegExp("\{[0-9|a-z|A-Z]+\:\}", "g");
					var chaves = new RegExp("\{[0-9|a-z|A-Z]+}", "g");

					function verificarOperador(){
						pilha_abertura = 0;
						pilha_fechamento= 0;
						var espressao = $("#formula").val();
						espressao = replaceAll(" ","",espressao);
						$("#formula").val(espressao);
						formulaValidada = espressao;
						espressao = espressao.replace(chaves, "");//remover labels
						espressao = verificarOqueNaoENUmero(espressao);
						return verificar_parenteses(espressao);
					}

					function verificarOqueNaoENUmero(string){
						if(string.indexOf("vValor")!=-1){
							string = replaceAll("vValor","1",string);

						}if(string.indexOf("vDiasUteis")!=-1){
							string = replaceAll("vDiasUteis","2",string);

						}if(string.indexOf("vDiasCorridos")!=-1){
							string = replaceAll("vDiasCorridos","3",string);

						}if(string.indexOf("vNumeroUsuarios")!=-1){
							string = replaceAll("vNumeroUsuarios","4",string);

						}if(string.indexOf("vComplexidade")!=-1){
							string = replaceAll("vComplexidade","5",string);
						}
						return string;
					}

					function parenteses_bem_formados(  )
					{
						if(pilha_abertura==pilha_fechamento) return true;
						else return false;
					}

					function verificar_parenteses( formula )
					{
						for( var i=0; i<formula.length; i++)
						{
							if( (formula[i]=='(' || formula[i]==')') )
							{
								if(empilhar_parenteses( formula[i]))
									console.log("formando...<br>");
								else
								{
									console.log("mal formado<br>");
									break;
								}
							}
						}

						if( parenteses_bem_formados(  ) )
						{
							console.log("bem formado<br>");
							return verificar_operacoes(formula);
						}else
						{
							alert(i18n_message("formula.FormulaInvalida"));
							return false;
						}

					}
					function empilhar_parenteses( valor )
					{
						if(valor=='(')
						{
							pilha_abertura++;
						}
						else if(valor==')')
						{
							pilha_fechamento++;
						}

						if(pilha_fechamento>pilha_abertura) return false;
						else return true;
					}

					var numero = new RegExp(/[0-9]/);

					function verificar_operacoes(formula)
					{
						var resp = true;
						for( var i=0; i<formula.length; i++)
						{
							if(  formula[i]=='*'   ||  formula[i]=='/' )
							{
								if( (formula[i-1]==')' || numero.test(formula[i-1])) && ( formula[i+1]=='(' || numero.test(formula[i+1]) || formula[i+1]=='+' || formula[i+1]=='-')  )
								{
									console.log("operado...<br>");
								}
								else
								{
									resp=false;
									break;
								}
							}
							else if( formula[i]=='+'  ||  formula[i]=='-'  )
							{
								if( ( formula[i-1]==null || formula[i-1]=='/' || formula[i-1]=='*' || formula[i-1]=='(' || formula[i-1]==')' || numero.test(formula[i-1])) && ( formula[i+1]=='(' || numero.test(formula[i+1]))  )
								{
									console.log("operado...<br>");
								}
								else
								{
									resp=false;
									break;
								}
							}
						}

						if(resp){
							return true;
						}
						else{
							alert(i18n_message("formula.FormulaInvalida"));
							return false
						}

					}

					function MostrarFormula(){
						formula = $("#formula").val();
						if(formula!=""){
						  if(verificarOperador()){
							var formula = formulaValidada;
							$("#formulaMontada").empty();
							var html = "<b id='idB'>"
								html+=formula;
								if(html.indexOf("vValor")!=-1){
									html = replaceAll("vValor","<input type='text' class='vValor span12 Format[numeric]' style='width: 35px;' id=Valor name='Valor'  required='required'>",html);
								}
								if(html.indexOf("vDiasUteis")!=-1){
									html = replaceAll("vDiasUteis","<b>"+i18n_message("formula.DiasUteis")+"</b>",html);

								}if(html.indexOf("vDiasCorridos")!=-1){
									html = replaceAll("vDiasCorridos","<b>"+i18n_message("citcorpore.comum.diasCorridos")+"</b>",html);

								}if(html.indexOf("vNumeroUsuarios")!=-1){
									html = replaceAll("vNumeroUsuarios","<b>"+i18n_message("formula.NumeroDeUsuarios")+"</b>",html);

								}if(html.indexOf("vComplexidade")!=-1){
									html = replaceAll("vComplexidade","<select id='RetComplexidade' name='RetComplexidade' style='width: 130px;' class='vComplexidade span12 Valid[Required]'>"+
									"<option value='1'>Baixa</option>"+
									"<option value='2'>Intermediaria</option>"+
									"<option value='3'>Media</option>"+
									"<option value='4'>Alta</option>"+
									"<option value='5'>Especialista</option></select>",html);
								}
								html += "</b>";
								html += "<button class='btn btn-default btn-primary' type='button' onclick='visualizarValorFinal();' id='calcularValorFinal' style='margin-top: -9px; margin-left: 15px;' name='add'>"+i18n_message("formula.Simular")+"</button>";
									while(html.indexOf("{")!=-1 || html.indexOf("}")!=-1){
										html = html.replace("{", "  ");
										html = html.replace("}", "  ");
									}
								$("#formulaMontada").append(html);
						   }else{
							   $("#formulaMontada").empty();
						   }
						}
					}

					function visualizarValorFinal(){
						var estrututaFormula = document.getElementById('formula').value;
						var totalValor = $(".vValor").length;
						var totalComplexidade = $(".vComplexidade").length;
						var formulaFinal = "";
						var formulaAuxiliar = estrututaFormula;

						var arrayValores = new Array();
						if(totalValor!=0){
							for(i = 0;i<totalValor;i++){
								var obj = $(".vValor")[i];
								if(obj.value==""){
									alert(i18n_message("formula.ExistemCamposEmBranco"));
									return false;
								}else{
									arrayValores[i] = obj.value;
								}

							}
						}

						if(arrayValores.length!=0){
							var aux1 = "";
							var aux2 = "";//variavel que irá armazenar a formula final sem os input
							var aux3 = formulaAuxiliar;

							for(i = 0;i<arrayValores.length;i++){
								aux1 = "";
								for(j = 0; j<aux3.length;j++){
									aux1 += aux3[j];
									if(aux1.indexOf("vValor")!=-1)
										break;
								}
								aux3 = aux3.replace(aux1,"");
								aux2 += aux1.replace("vValor",arrayValores[i]);
								if((i+1==arrayValores.length) && aux3!=""){
									aux2 += aux3;
								}
							}
							formulaAuxiliar = aux2;
						}


						var arrayComplexidade = new Array();
						if(totalComplexidade!=0){
							for(i = 0;i<totalComplexidade;i++){
								var obj = $(".vComplexidade")[i];
								if(obj.value==""){
									alert(i18n_message("formula.ExistemCamposEmBranco"));
									return false;
								}else{
									arrayComplexidade[i] = obj.value;
								}
							}
						}


						if(arrayComplexidade.length!=0){
							var aux1 = "";
							var aux2 = "";//variavel que irá armazenar a formula final sem os input
							var aux3 = formulaAuxiliar;

							for(i = 0;i<arrayComplexidade.length;i++){
								aux1 = "";
								for(j = 0; j<aux3.length;j++){
									aux1 += aux3[j];
									if(aux1.indexOf("vComplexidade")!=-1)
										break;
								}
								aux3 = aux3.replace(aux1,"");
								aux2 += aux1.replace("vComplexidade",arrayComplexidade[i]);
								if((i+1==arrayComplexidade.length) && aux3!=""){
									aux2 += aux3;
								}
							}
							formulaAuxiliar = aux2;
						}

						var strAux = formulaAuxiliar;
						var index1 = 0;
						var index2 = 0;
						var strPedaco = "";

						for(i = 0;i<formulaAuxiliar.length;i++){
								strPedaco = "";
								if(formulaAuxiliar[i].indexOf("{")!=-1){
									index1 = i;
									for(j = i;j<formulaAuxiliar.length;j++){
										if(formulaAuxiliar[j].indexOf("}")!=-1){
											index2 = j+1;
											break;
										}
									}
									for(j = index1;j<index2;j++){
										strPedaco += formulaAuxiliar[j];
									}

									strAux = strAux.replace(strPedaco,"");
								}
						}
						formulaFinal = strAux;



						document.getElementById("formulaSimulada").value = formulaFinal;
						document.form.fireEvent("simularCalculo");
					}

					function setarResultado(resultado){
						$("#resultadoFormula").empty();
						$("#resultadoFormula").append(resultado);
					}

					function addOperador(operador){
						var montarFormula = $("#formula").val();
						/* Validação para gravar o tamanho do da formula do mesmo tamanho do banco de dados
						o valor deve ser menor para não tenha conflito entre digitar a formula e usar os botoes de opções.*/
						if(montarFormula.length<230){
						montarFormula += operador.value;
						$("#formula").val(montarFormula);
						}
					}
			
