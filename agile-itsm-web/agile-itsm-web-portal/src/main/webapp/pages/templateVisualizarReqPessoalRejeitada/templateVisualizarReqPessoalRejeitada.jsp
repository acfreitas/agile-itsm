<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    <%
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        String id = request.getParameter("id");
    %>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>  

    <style type="text/css">
	    .campoObrigatorio:before {
			color: #FF0000;
			content: "*";
		}
      /*   .destacado {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
            font-size: 14px;
        }
        .table {
            border-left:1px solid #ddd;
            width: 100%;
        }
        
        .table th {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
        }
        
        .table td {
            border:1px solid #ddd;
            padding:4px 10px;
            border-top:none;
            border-left:none;
        }
        
        .table1 {
        }
        
        .table1 th {
            border:1px solid #ddd;
            padding:4px 10px;
            background:#eee;
        }
        
        .table1 td {
        }   
             
         div#main_container {
            margin: 0px 0px 0px 0px;
        } 
                
        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;
            
            letter-spacing: -4px;
        } */
    </style>

    <script>
	    function limpar(){
	   		window.location = '${ctx}/templateVisualizarReqPessoalRejeitada/templateVisualizarReqPessoalRejeitada.load';
	   	} 
		    
	    var contSelecao = 0;
		function exibirSelecao(objeto) {
			contSelecao = 0;
			HTMLUtils.deleteAllRows('tblSelecao');
			var nome = objeto.toUpperCase().substring(0,1) + objeto.substring(1,objeto.length);
			document.formSelecao.objeto.value = nome;
			document.formSelecao.action = '${ctx}/' + objeto + '/' + objeto;
	 		document.formSelecao.fireEvent('exibeSelecao');
			$("#modal_selecao").modal("show");
		}
		
		function incluirOpcaoSelecao(id, desc, detalhe) {
			contSelecao ++;
			var tbl = document.getElementById('tblSelecao');
			var ultimaLinha = tbl.rows.length;
			var linha = tbl.insertRow(ultimaLinha);
			
			var coluna = linha.insertCell(0);
			coluna.className = "celulaEsquerda";
		    coluna.innerHTML = '<input style="cursor: pointer" type="checkbox" name="id" id="id' + contSelecao + '" value="'+id+'">';
		    
			coluna = linha.insertCell(1);
			coluna.className = "celulaEsquerda";
		    coluna.innerHTML = '<b>' +desc + '</b><input type="hidden" id="desc' + contSelecao + '" name="desc" value="' + desc + '" />';
		    
		    coluna = linha.insertCell(2);
			coluna.className = "celulaEsquerda";
			coluna.innerHTML = detalhe.substring(0,11) +'<span style="cursor: pointer;" title = "'+ detalhe +'"> ... </span> <input type="hidden" id="detalhe' + contSelecao + '" name="detalhe" value="' + detalhe + '" />';
		}
		
		function atribuirSelecao() {
			if (document.formSelecao.id.length) {
				var tbl = document.getElementById('tblSelecao');
				var ultimaLinha = tbl.rows.length;
	            for(var i = 1; i < document.formSelecao.id.length; i++){
	            	if (document.formSelecao.id[i].checked) {
	            		var desc = document.getElementById('desc'+i).value;
						if (!validarLinhaSelecionada(document.formSelecao.objeto.value, ultimaLinha, document.formSelecao.id[i].value, desc))
							return;
					}
				}
	            for(var i = 1; i < document.formSelecao.id.length; i++){
	            	if (document.formSelecao.id[i].checked) {
	            		var desc = document.getElementById('desc'+i).value;
	            		var detalhe = document.getElementById('detalhe'+i).value;
	            		adicionarLinhaSelecionada(document.formSelecao.objeto.value, document.formSelecao.id[i].value, desc, "N", detalhe);
					}
				}
			}
			$("#modal_selecao").modal("hide");
		}
		
		function configuraPainelAcesso(){
		       if (document.formDescricao.acessoAAlteracoes[0].checked) {
		           $('divPainelAcesso').style.display='block';
		       }else{
		           $('divPainelAcesso').style.display='none';
		       }       
		}
		
	   function configuraPainelSalario(chk){
	       if (chk.checked) 
	           document.getElementById('divPainelSalario').style.display = 'none';
	       else
	       	document.getElementById('divPainelSalario').style.display = 'block';
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
	        	if (document.form.idSolicitacaoServico != '') {
	        		configuraPainelSalario(document.form.salarioACombinar);
	        	}
	            if (document.form.editar.value != '' && document.form.editar.value != 'S')
	                desabilitarTela();
	            parent.escondeJanelaAguarde();                    
	        }    
	    }
	
	    function validar() {
	        return document.form.validate();
	    }
	
		function restoreFuncao() {
		   document.form.fireEvent("restoreFuncao");
		}
		      
		function abilitarCampoSalario() {
	    	if (document.getElementById('salarioACombinar').checked == true) {
	    		document.getElementById('salario').value = '';
	    		document.getElementById('salario').disabled = true;
	    	}else{
	    		document.getElementById('salario').disabled = false;
			}
	    }
	
	</script>
</head>

<body> 
	<div class="container-fluid fixed" '>
        <div class="wrapper">
            <div id="tabs-2" class="box-generic" style="overflow: hidden;">
                        <form name='form' action='${ctx}/pages/templateVisualizarReqPessoalRejeitada/templateVisualizarReqPessoalRejeitada'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                                <input type='hidden' name='editar' id='editar' /> 
                                <input type='hidden' name='acao' id='acao'/> 
									
							<div class="row-fluid">
								<div class='span4'>
									<label for='idFuncao' class='strong campoObrigatorio'><fmt:message key="rh.funcao" /></label>
									<select name='idFuncao' id='idFuncao' class="Valid[Required] Description[rh.funcao] span10"  onchange="document.form.fireEvent('restoreFuncao');" disabled="disabled"></select>
								</div>
								<div class='span4'>
									<label for='vagas' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.numeroVagas" /></label>
									<input type='text' name='vagas' class="Valid[Required] Description[requisicaoPessoal.numeroVagas] Format[Numero] span10"   maxlength="9"  class='span6' disabled="disabled"/>
								</div>
								<div class='span4'>
									<label for='confidencial' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.vagaConfidencial" /></label>
									<select name='confidencial' class="Valid[Required] Description[requisicaoPessoal.vagaConfidencial]" disabled="disabled">
	                                   <option value=" "><fmt:message key="citcorpore.comum.selecione" /></option>
	                                   <option value="S"><fmt:message key="citcorpore.comum.sim"/></option>
	                                   <option value="N"><fmt:message key="citcorpore.comum.nao"/></option>
	                                </select>
								</div>
							</div>
						<div class="row-fluid">
							<div class='span4'>
								<label for='tipoContratacao' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.tipoContratacao" /></label>
								<select name='tipoContratacao' class="Valid[Required] Description[requisicaoPessoal.tipoContratacao]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label for='motivoContratacao' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.motivoContratacao" /></label>
								<select name='motivoContratacao' class="Valid[Required] Description[requisicaoPessoal.motivoContratacao]" disabled="disabled">
                               	      <option value=''><fmt:message key="citcorpore.comum.selecione" /></option>
                               	      <option value='N'><fmt:message key="rh.novoCargo"/> </option>
                               	      <option value='D'><fmt:message key="rh.demissaoPessoal" /></option>
                               	      <option value='A'><fmt:message key="rh.aumentoDemanda" /></option>
                               	      <option value='R'><fmt:message key="rh.requisicaoCliente" /></option>
                                </select>
							</div>
							<div class='span4'>
							<label class='strong'><fmt:message key="requisicaoPessoal.salario" /></label>
								<div class='row-fluid'>
									<div class='span6'>
										<input type="checkbox" onclick="abilitarCampoSalario();" name="salarioACombinar" value="S" disabled="disabled">
		                                 <fmt:message key="requisicaoPessoal.salarioACombinar" />
	                                 </div>
	                                 <div class='span6'>
		                                 <div id='divPainelSalario' style='display:block'>
		                                      <div class="input-prepend input-append">
													<span id="spanSlario" class="add-on"><fmt:message key="citcorpore.comum.simboloMonetario" /></span>
													<input class="Format[Moeda] span10" id="salario" name='salario' size='10' maxlength="100" disabled="disabled"> 
											  </div>
			                         	</div>
		                         	</div>
	                         	</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class='span4'>
								<label for='idPais' class='strong'><fmt:message key="unidade.pais" /></label>
								<select name='idPais' id="idPais"  onchange="document.form.fireEvent('preencherComboUfs');" class="Description[unidade.pais]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label for='idUf' class='strong'><fmt:message key="localidade.uf" /></label>
								<select name='idUf' id="idUf" onchange="document.form.fireEvent('preencherComboCidade');" class="Description[uf]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label for='idCidade' class='strong'><fmt:message key="localidade.cidade" /></label>
								<select id="idCidade" name='idCidade'  class="Description[Cidade]" disabled="disabled"></select>
							</div>
						</div>
						
						<div class="row-fluid">
						<!-- 
							<div class='span4'>
								<label for='idUnidade' class='strong campoObrigatorio'><fmt:message key="citcorporeRelatorio.comum.lotacao" /></label>
								<select name='idUnidade' class="Valid[Required] Description[citcorporeRelatorio.comum.lotacao]" disabled="disabled"></select>
							</div>
							 -->
							 <div class='span4'>
								<label for='idLotacao' class='strong campoObrigatorio'><fmt:message key="citcorporeRelatorio.comum.lotacao" /></label>
								<select name='idLotacao' class="Valid[Required] Description[citcorporeRelatorio.comum.lotacao]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label for='idJornada' class='strong'><fmt:message key="requisicaoPessoal.horarios" /></label>
								<select name='idJornada' id='idJornada' disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label for='beneficios' class='strong'><fmt:message key="requisicaoPessoal.beneficios" /></label>
								<textarea name='beneficios' rows="4"  class='span8' maxlength="100" disabled="disabled"></textarea>
							</div>
						</div>
						<div class="row-fluid">
							<div class='span4'>
								<label for='idCentroCusto' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.centroCusto" /></label>
								<select name='idCentroCusto' class="Valid[Required] Description[requisicaoPessoal.centroCusto]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label for='idProjeto' class='strong campoObrigatorio'><fmt:message key="requisicaoPessoal.projeto" /></label>
								<select name='idProjeto' class="Valid[Required] Description[requisicaoPessoal.projeto]" disabled="disabled"></select>
							</div>
							<div class='span4'>
								<label for='folgas' class='strong'><fmt:message key="requisicaoPessoal.folgas" /></label>
								<input type='text' name='folgas' size='10' maxlength="100" disabled="disabled"/>
							</div>
						</div>
					<div class="row-fluid">	
						<div class="span12">
							<label for='observacoes' class="section"><fmt:message key="solicitacaoCargo.complementos"/></label>
						</div>
					</div>
					<div class="row-fluid">	
						<div class="span12">				
							<div>
								<textarea rows="5" cols="122" id='observacoes' name='observacoes' maxlength="500" class='span12' disabled="disabled"></textarea>
							</div>
						</div>
					</div>
					<div class='widget'>
						<div class='box-generic'>
							<div class="row-fluid">
								<div class="span12" >
									<div>
										<input id="rejeitada" name="rejeitada" class="checkbox" type="checkbox" value="S" style="cursor: pointer" onclick="configuraJustificativaRejeicao()" disabled="disabled">
	                     				<fmt:message key="requisicaoPessoal.rejeitarRequisicao"/>
									</div>
									<div id='divJustificativaRejeicao' style='display:block;' class='span12'>
					                    <label><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
					                    <div>
					                        <textarea class="span11" id="justificativaRejeicao" name="justificativaRejeicao" cols='60' rows='3' disabled="disabled"></textarea>                               
					                    </div>
                                    </div>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<%@include file="/novoLayout/common/include/libRodape.jsp"%>
		<script type="text/javascript"
			src="${ctx}/js/ValidacaoUtils.js"></script>
		<script type="text/javascript"
			src="${ctx}/js/PopupManager.js"></script>
		<script type="text/javascript"
			src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
	</div>
</body>

</html>
