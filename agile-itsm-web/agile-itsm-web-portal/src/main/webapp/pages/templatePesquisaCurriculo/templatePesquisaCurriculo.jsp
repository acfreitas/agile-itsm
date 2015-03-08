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
		<script src="${ctx}/novoLayout/common/include/js/templatePesquisaCurriculo.js"></script>
		    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>
    <script type="text/javascript" src="${ctx}/cit/objects/CurriculoDTO.js"></script>
    <style type="text/css">
	#modal_curriculo{
	width: 99%!important;
	margin-left: -49%;
	top: 40%!important;
	}
	#modal_curriculo .modal-body{
		max-height: 610px;
		overflow: auto!important;
	}

	#modal_listaNegra{
	}
	#modal_listaNegra .modal-body{
		max-height: 500px;
		overflow: auto!important;
	}

    </style>
	</head>

	<script type="text/javascript">
		function gerarSelecaoCurriculo(row, obj){
	        obj.selecionado = 'N';
	        row.cells[0].innerHTML = "<input type='checkbox' name='chkSel_"+obj.idCurriculo+"' id='chkSel_"+obj.idCurriculo+"' onclick='marcarDesmarcar(this,"+row.rowIndex+",\"tblCurriculos\")' />";
	    }

	</script>
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
					<form name='formSugestaoCurriculos' action='${ctx}/pages/triagemRequisicaoPessoal/triagemRequisicaoPessoal'>
					<input type="hidden" id='colecaoCurriculo' name='colecaoCurriculo'>
					<input type="hidden" id='curriculos_serialize' name='curriculos_serialize'>
						<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="rh.pesquisaCurriculo" /></h4>
								</div>
						</div>
						<div class="widget-body collapse in">

								<!-- Column -->
									<div class="span6">
									<div class="tab-content">

										<!-- Palavra chave -->
											<div class="control-group">
												<label class="strong"><fmt:message key="rh.palavraChave" />:</label>
												<div class="controls">
													<input type="text" onkeypress="chamarFuncaoAbrirTriagemManual(event);" class="span4" value="" id="chave" name="chave" maxlength="80">
												</div>
											</div>
										<!-- // Fim Palavra chave -->


										<!-- Palavra chave -->
												<div class="control-group">
													<label class="strong"><fmt:message key="rh.formacao" />:</label>
													<div class="controls">
														<input type="text" onkeypress="chamarFuncaoAbrirTriagemManual(event);" class="span4" value="" id="formacao" name="formacao" maxlength="80">
													</div>
												</div>
											<!-- // Fim Palavra chave -->

											<!-- Palavra chave -->
												<div class="control-group">
													<label class="strong"><fmt:message key="rh.certificacoesm" />:</label>
													<div class="controls">
														<input type="text" onkeypress="chamarFuncaoAbrirTriagemManual(event);" class="span4" value="" id="certificacao" name="certificacao" maxlength="80">
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
												<div class="control-group">
													<label class="strong"><fmt:message key="rh.idiomas" />:</label>
													<div class="controls">
														<input type="text" onkeypress="chamarFuncaoAbrirTriagemManual(event);" class="span4" value="" id="idiomas" name="idiomas" maxlength="80">
													</div>
												</div>
											<!-- // Fim Palavra chave -->

											<!-- Cidade -->
												<div class="control-group">
													<label class="strong"><fmt:message key="rh.cidade" />:</label>
													<div class="controls">
														<input type="text" onkeypress="chamarFuncaoAbrirTriagemManual(event);" class="span4" value="" id="cidade" name="cidade" maxlength="80">
													</div>
												</div>
											<!-- // Fim Cidade -->

										</div>
									</div>
								<!-- Fim column -->
						</div>
						<div class='span9 filtro filtrobar main'>
						<div class="span4">
							<button style="float: left;" class="btn btn-icon btn-primary" type="button" onclick="parent.abrirTriagemManual();"><i></i><fmt:message key="citcorpore.comum.pesquisar" /></button>
							<!-- <button class="btn btn-icon btn-primary" type="button" onclick="parent.sugereCurriculos();"><i></i>Pesquisa Automática</button>	 -->
							<button style="float: left;" class="btn btn-icon btn-default glyphicons circle_remove" type="button" onclick="document.formSugestaoCurriculos.clear();"><i></i><fmt:message key="rh.limpar" /></button>
						</div>
							<div style="float: left; width: 230px" class='span4 topfiltro'>
							<ul>
								<li id='acoes' class='btn-group btn-block span3'>
									<div class='leadcontainer'>
									<button type='button' class='btn dropdown-lead btn-default' onclick="parent.sugereCurriculos();"><fmt:message key="rh.pesquisaAutomatica" /></button>
									</div>
									<a class='btn btn-default dropdown-toggle filtro-toogle' href='#' data-toggle='dropdown' re='dropdownFiltro' ><span class='caret'></span> </a>
									<ul class="dropdownFiltro dropdown-menu pull-right">
										<li class="dropdownFiltroPesquisa">
											<div class="span4">
						                       <input type='checkbox' name='chkCertificacao' value="C"/><fmt:message key="triagem.pesquisaPorCertificacao" />
					                 	 	</div>
										</li>
										<li>
											<div class="span3">
						                       <input type='checkbox' name='chkIdioma' value="I"/><fmt:message key="triagem.pesquisaPorIdioma" />
					                 	 	</div>
										</li>
									</ul>
								</li>
								</ul>
							</div>
							<div style="clear: both;"></div>
						</div>
						<br>
						<br>
						<br>
						<br>
						<br>
						<div class="widget">
							<div class="widget-head">
								<h4 class="heading"><fmt:message key="rh.resultadoBuscaCurriculos" /></h4>
								</div>
							<!-- Tabela de resultados -->
							<div class="widget-body">

								<!-- Table  -->
								<div >
									<div class="row-fluid">
									</div>
										<table class="table table-condensed" id="tblCurriculos" >

											<!-- Table heading -->
												<tr >
													<th width="23%"><fmt:message key="citcorpore.comum.nome" /></th>
													<th width="12%"><fmt:message key="citcorpore.comum.dataNascimento" /></th>
													<th width="21%"><fmt:message key="rh.cpf" /></th>
													<th width="1%"><fmt:message key="citcorpore.comum.sexo" /></th>
													<th width="27,5%" align="right"><fmt:message key="rh.acoes" /></th>
												</tr>
											<!-- // Table heading END -->

											<!-- Table body -->

											<!-- // Table body END -->
									</table>
								</div>
								<!-- // Table END -->
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
									<iframe id='frameVisualizacaoCurriculo' src='' width="99%" height="560" border="0" ></iframe>
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

						<div class="modal hide fade in" id="modal_listaNegra" aria-hidden="false">
								<!-- Modal heading -->
								<div class="modal-header">
									 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
									<h3><fmt:message key="rh.listaNegraCurriculo" /></h3>
								</div>
								<!-- // Modal heading END -->
								<!-- Modal body -->
								<div class="modal-body">
									<iframe id='frameListaNegra' src='' width="99%" height="430" border="0" ></iframe>
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

				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
	</body>
</html>