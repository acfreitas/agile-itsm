<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
	<head>	
		<%
			String iframe = "";
			iframe = request.getParameter("iframe");
			String URL_SISTEMA = "";
			URL_SISTEMA = CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+'/';
		%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
		
		<%-- <script src="${ctx}/novoLayout/common/include/js/templatePesquisaCurriculo.js"></script> --%>
		    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/CurriculoDTO.js"></script>
    <style type="text/css">
	#modal_curriculo{
	width: 1640px!important;
	margin-left: -49%; 
	top: 40%!important;
	}
	#modal_curriculo .modal-body{
		max-height: 610px;
		overflow: auto!important;
	}
    
    </style>
	</head>
	<%
	
	String linguaAtual =(String)request.getSession().getAttribute("locale");
	
	%>
	 <script>
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
	        	dataAtual();                   
	          }  
	      }
	      
	     $(function() {
	        $("#POPUP_EMPREGADOS").dialog({
	            autoOpen : false,
	            width : 1000,
	            height : 600,
	            modal : true
	        });
	    }); 
	     function abrePopup() {
	 		$("#POPUP_EMPREGADOS").dialog("open");
	 	}

	 	function LOOKUP_EMPREGADOS_select(id,desc){
	 		document.form.restore({idEmpregado:id});
	 		$("#POPUP_EMPREGADOS").dialog("close");
	 	}  
	     
	    $(function() {
	        $("#POPUP_CURRICULO").dialog({
	            autoOpen : false,
	            width : 1000,
	            height : 600,
	            modal : true
	        });
	    }); 
	    
	    function manipulaDivsAdmissao() {
		    		document.getElementById('divAperfeicoamento').style.display = 'none';
		    		document.getElementById('divEficacia').style.display = 'none';
		    		document.getElementById('divIntegridade').style.display = 'none';
		    		document.getElementById('divComprometimento').style.display = 'none';
		    		document.getElementById('divIniciativa').style.display = 'none';
		    		document.getElementById('divDataInicio').style.display = 'block';
		    		document.getElementById('divRequisitosObrigatorios').style.display = 'block';
	    }
	    
	    function manipulaDivsAlteracao() {
		    		document.getElementById('divAperfeicoamento').style.display = 'block';
		    		document.getElementById('divEficacia').style.display = 'block';
		    		document.getElementById('divIntegridade').style.display = 'block';
		    		document.getElementById('divComprometimento').style.display = 'block';
		    		document.getElementById('divIniciativa').style.display = 'block';
		    		document.getElementById('divDataInicio').style.display = 'none';
		    		document.getElementById('divRequisitosObrigatorios').style.display = 'none';
	    }
	    
	    function validar() {
        }
        
	    function getObjetoSerializado() {
            var obj = new CIT_RequisicaoPessoalDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            return ObjectUtils.serializeObject(obj);
     	} 
	    
	    function dataAtual() {
	    	hoje = new Date();
	    	dia = hoje.getDate();
	    	mes = hoje.getMonth();
	    	ano = hoje.getFullYear();
	    	if (dia < 10)
	    	 dia = "0" + dia;
	    	if (mes < 10)
		     mes = "0" + (mes+1);
	    	if (ano < 2000)
	    	 ano = "19" + ano;
	    	if('<%=request.getSession().getAttribute("locale")%>'=="en")
	    		document.getElementById('data').value = (mes+"/"+ dia +"/"+ano);
	    	else
		    	document.getElementById('data').value = (dia+"/"+ mes +"/"+ano);

	    }
	    
	    function restoreCargo() {
	 	   document.form.fireEvent("restoreCargo");
	 	}

	    function gerarRelatorio() {
	    	JANELA_AGUARDE_MENU.show();
	 		document.form.fireEvent("imprimirRelatorioMovimentacaoColaborador");
		}

	    function gerarRelatorioDetalhado() {
			if(document.form.tipoMovimentacao[1].checked){
				JANELA_AGUARDE_MENU.show();
		 		document.form.fireEvent("imprimirRelatorioMovimentacaoColaboradorDetalhado");
	    	}else{
	    		alert("Campo Alteração Obrigatorio");
	    		i18n_message("rh.campoAlteracao");
	    	}
		}
     	
     	     
</script>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
	<body>
		<div class="container-fluid fixed ">
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<%if (iframe == null) {%>
				<div class="navbar main hidden-print">
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				</div>
			<%}%>
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<div id="content">
					<form name='form' action='${ctx}/pages/movimentacaoColaborador/movimentacaoColaborador'>
					<input type="hidden" id='colecaoCurriculo' name='colecaoCurriculo'>
					<input type="hidden" id='curriculos_serialize' name='curriculos_serialize'>
						<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key='FichaMovimentacaoColaborador.FichaMovimentacaoColaborador'/></h4>
								</div>
						</div>
						<div class="widget-body collapse in">
						
								<!-- Column -->
									<div class="span6">
									<div class="tab-content">
										
										<!-- Palavra chave -->
											<div class="row-fluid">
												<label class="strong"><fmt:message key='movimentacaoColaborador.numeroSolicitacaoPessoal'/>:</label>
												<div>
													<input type="text" class="span3 " readonly="readonly" id="idSolicitacao" name="idSolicitacao">
												</div>
											</div>
										<!-- // Fim Palavra chave -->
										
										
										<!-- Palavra chave -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='citcorporeRelatorio.comum.gestor'/>:</label>
													<div class="controls">
														<input type="text" class="span12" readonly="readonly" value="" id="gestor" name="gestor" maxlength="80">
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
											<!-- Palavra chave -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='colaborador.colaborador'/>:</label>
													<div class="controls">
														<input type="text" class="span12" value="" readonly="readonly" id="nomeCandidato" name="nomeCandidato" maxlength="80">
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
									</div>
								</div>	
								
								<!-- Fim column -->	
								
								<!-- Column -->
									<div class="span6">
										<div class="tab-content">
											
											<!-- Palavra chave -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='eventoItemConfiguracao.data'/>:</label>
													<div id="" class="input-append">													
														<input type="text" class="span6 citdatepicker" id="data" name="data">
														<span class="add-on glyphicons calendar"><i></i></span>
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
											<!-- Cidade -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='citcorporeRelatorio.comum.indicacao'/>:</label>
													<div class="controls">
														<input type="text" class="span12" value="" id="indicacao" name="indicacao" maxlength="80">
													</div>
												</div>
											<!-- // Fim Cidade -->
											
											<!-- Palavra chave -->
											<div class="row-fluid">
												<label class="strong"><fmt:message key='lookup.cargo'/>:</label>
												<div>
													<input type="text" class="span12" readonly="readonly" id="cargo" name="cargo">
												</div>
											</div>
										<!-- // Fim Palavra chave -->
											
										</div>
									</div>
								<!-- Fim column -->	
						</div>
						<div>
							<div class="row-fluid">
								<div class="widget-body uniformjs collapse in">
									<label class="radio">
										<div class="radio" id="admissao"><span class="checked"><input type="radio" checked="checked" value="1" name="tipoMovimentacao" class="radio" style="opacity: 0;"  onclick="manipulaDivsAdmissao()"></span></div>
										 <fmt:message key='movimentacaoColaborador.umAdmissao'/>
									</label>
									<label class="radio">
										<div class="radio" id="alteracao"><span class="checked"><input type="radio" value="0" name="tipoMovimentacao" class="radio" style="opacity: 0;" onclick="manipulaDivsAlteracao()"></span></div>
										 <fmt:message key='movimentacaoColaborador.doisAlteracao'/>
									</label>
								</div>
							</div>
						</div>
						<div class="widget-body collapse in">
						
								<!-- Column -->
									<div class="span6">
									<div class="tab-content">
										
										<!-- Palavra chave -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='citcorporeRelatorio.comum.lotacao'/>:</label>
													<div class="controls">
														<input type="text" class="span12" value="" id="lotacao" name="lotacao" maxlength="80">
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
											<!-- Palavra chave -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='menu.nome.horarioTrabalho'/>:</label>
													<div class="controls">
														<input type="text" class="span12" value="" id="horarioTrabalho" name="horarioTrabalho" maxlength="80">
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											<!-- Palavra chave -->
												<div class="row-fluid" id="divDataInicio" Style="display: block">
													<label class="strong"><fmt:message key='visao.dataDeInicio'/>:</label>
													<div id="datetimepicker2" class="input-append">
														<input type="text" class="span6 citdatepicker" id="dataInicio" name="dataInicio">
														<span class="add-on glyphicons calendar"><i></i></span>
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
											<!-- Cidade -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='citcorporeRelatorio.comum.planoExcelencia'/>:</label>
													<div class="controls">
														<input type="text" class="span12" value="" id="excelencia" name="excelencia" maxlength="80">
													</div>
												</div>
											<!-- // Fim Cidade -->
											
											<!-- Palavra chave -->
												<div class="row-fluid" id="divAperfeicoamento" Style="display: none">
													<div class="span4">
														<label class="strong"><fmt:message key='citcorporeRelatorio.comum.aperfeicoamento'/></label>
														<textarea id="aperfeicoamento" class="span4" rows="4" name="aperfeicoamento" style="width: 309px; height: 111px"></textarea>
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
											<!-- Palavra chave -->
												<div class="row-fluid" id="divEficacia" Style="display: none">
													<div class="span4">
														<label class="strong"><fmt:message key='citcorporeRelatorio.comum.eficacia'/></label>
														<textarea id="eficacia" class="span4" rows="4" name="eficacia" style="width: 309px; height: 111px"></textarea>
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
											<!-- Palavra chave -->
												<div class="row-fluid" id="divIntegridade" Style="display: none">
													<div class="span4">
														<label class="strong"><fmt:message key='citcorporeRelatorio.comum.integridade'/></label>
														<textarea id="integridade" class="span4" rows="4" name="integridade" style="width: 309px; height: 111px"></textarea>
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
									</div>
								</div>	
								
								<!-- Fim column -->	
								
								<!-- Column -->
									<div class="span6">
										<div class="tab-content">
											
											<!-- Palavra chave -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='lookup.centroResultado'/>:</label>
													<div class="controls">
														<input type="text" class="span12" value="" id="resultado" name="resultado" maxlength="80">
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
											<!-- Cidade -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='lookup.projeto'/>:</label>
													<div class="controls">
														<input type="text" class="span12" value="" id="projeto" name="projeto" maxlength="80">
													</div>
												</div>
											<!-- // Fim Cidade -->
											<!-- Cidade -->
												<div class="row-fluid">
													<label class="strong"><fmt:message key='requisicaoPessoal.salario'/>:</label>
													<div class="controls">
														<input type="text" class="span6" value="" id="salario" name="salario" maxlength="80">
													</div>
												</div>
											<!-- // Fim Cidade -->
											
											<!-- Palavra chave -->
												<div class="row-fluid" id="divRequisitosObrigatorios" Style="display: block">
													<div class="span10">
														<label class="strong"><fmt:message key='movimentacaoColaborador.requisitosObrigatoriosAdmissao'/>:</label>
														<textarea id="requisitos" class="span4" rows="4" name="requisitos" style="width: 309px; height: 111px"></textarea>
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
											<!-- Palavra chave -->
												<div class="row-fluid" id="divComprometimento" Style="display: none">
													<div class="span4">
														<label class="strong"><fmt:message key='citcorporeRelatorio.comum.comprometimento'/></label>
														<textarea id="comprometimento" class="span4" rows="4" name="comprometimento" style="width: 309px; height: 111px"></textarea>
													</div>
												</div>
											<!-- // Fim Palavra chave -->
											
											<!-- Palavra chave -->
												<div class="row-fluid" id="divIniciativa" Style="display: none">
													<div class="span4">
														<label class="strong"><fmt:message key='citcorporeRelatorio.comum.iniciativa'/></label>
														<textarea id="iniciativa" class="span4" rows="4" name="iniciativa" style="width: 309px; height: 111px"></textarea>
													</div>
												</div>
											<!-- // Fim Palavra chave -->
										</div>
											
									</div>
								<!-- Fim column -->	
						</div>
						<div align="left" class="form-actions">
							<button class="btn btn-icon btn-primary glyphicons search" type="button" onclick='gerarRelatorio();'><i></i><fmt:message key='movimentacaoColaborador.gerarRelatorioSemJustificativa'/></button>
							<button class="btn btn-icon btn-primary glyphicons search" type="button" onclick='gerarRelatorioDetalhado();'><i></i><fmt:message key='movimentacaoColaborador.gerarRelatorioComJustificativa'/></button>
						</div>
						</div>
						</form>
						</div>
						<div class="modal hide fade in" id="modal_curriculo" aria-hidden="false">
								<!-- Modal heading -->
								<div class="modal-header">
									 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
									<h3></h3>
								</div>
								<!-- // Modal heading END -->
								<!-- Modal body -->
								<div class="modal-body">
									<iframe id='frameVisualizacaoCurriculo' src='' width="1600" height="560" border="0" ></iframe>
								</div>
								<!-- // Modal body END -->
								<!-- Modal footer -->
								<div class="modal-footer">
									<div style="margin: 0;" class="form-actions">
										<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a> 
									</div>
								<!-- // Modal footer END -->
							</div>
						</div>
						<!-- Fim tabela resultados  -->
					</div>
				<!--  Fim conteudo-->
			
				<%@include file="/novoLayout/common/include/libRodape.jsp" %> 
			</div>
		</div>
	</body>
</html>