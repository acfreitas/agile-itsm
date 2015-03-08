<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.rh.ajaxForms.PesquisaCurriculo"%>

<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>

	    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
    	<script type="text/javascript" src="${ctx}/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>
    	<script type="text/javascript" src="${ctx}/cit/objects/CurriculoDTO.js"></script>

		<link rel="stylesheet" type="text/css" href="./css/pesquisaCurriculo.css" />
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"	title="" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
	</cit:janelaAguarde>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">

				<% if(iframe == null) { %>
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				<% } %>

			</div>

			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">

				<!-- Inicio conteudo -->
				<div id="content">
					<div class="separator top"></div>
					<div class="row-fluid">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="rh.pesquisaCurriculo"/></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='formSugestaoCurriculos' action='${ctx}/pages/triagemRequisicaoPessoal/triagemRequisicaoPessoal'>
												<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
												<input type="hidden" id='idCurriculo' name='idCurriculo'>
												<input type="hidden" id='idHistorico' name='idHistorico'>
												<input type="hidden" id='idCandidato' name='idCandidato'>
												<input type="hidden" id='idIdioma' name='idIdioma'>
												<input type="hidden" id='idFormacaoAcademica' name='idFormacaoAcademica'>
												<input type="hidden" id='idCertificacao' name='idCertificacao'>
												<input type="hidden" id='idCidade' name='idCidade'>
												<input type="hidden" id='colecaoCurriculo' name='colecaoCurriculo'>
												<input type="hidden" id='curriculos_serialize' name='curriculos_serialize'>
												<input type="hidden" id='paginaSelecionada' name='paginaSelecionada'>

				                                <input type='hidden' name='contFormacaoAcademica' id='contFormacaoAcademica' value='0'/>
				                                <input type='hidden' name='contCertificacao' id='contCertificacao' value='0'/>
				                                <input type='hidden' name='contCurso' id='contCurso' value='0'/>
				                                <input type='hidden' name='contExperienciaInformatica' id='contExperienciaInformatica' value='0'/>
				                                <input type='hidden' name='contExperienciaAnterior' id='contExperienciaAnterior' value='0'/>
				                                <input type='hidden' name='contIdioma' id='contIdioma' value='0'/>
				                                <input type='hidden' name='contConhecimento' id='contConhecimento' value='0'/>
				                                <input type='hidden' name='contHabilidade' id='contHabilidade' value='0'/>
				                                <input type='hidden' name='contAtitudeIndividual' id='contAtitudeIndividual' value='0'/>

												<div class="row-fluid">
													<div class="row-fluid">
														<div class="span12">
														<!-- Palavra chave -->
															<label class="strong"><fmt:message key="rh.palavraChave" />:</label>
															<input class="span12" type="text" id="pesquisa_chave" name="pesquisa_chave" maxlength="80">
														<!-- // Fim Palavra chave -->
														</div>
													</div>
													<div class="row-fluid">
														<!-- Formacao -->
														<div class="span6">
															<label class="strong"><fmt:message key="rh.formacao" />:</label>
															<input class="span12" type="text" id="pesquisa_formacao" name="pesquisa_formacao" maxlength="80">
														</div>
														<!-- // Fim Formacao -->
														<!-- Certificacao -->
														<div class="span6">
															<label class="strong"><fmt:message key="menu.nome.certificacao" />:</label>
															<input class="span12" type="text" id="pesquisa_certificacao" name="pesquisa_certificacao" maxlength="80">
														</div>
														<!-- // Fim Certificacao -->
													</div>
												</div>
												<div class="row-fluid">
													<div class="row-fluid">
														<!-- Idiomas -->
														<div class="span6">
															<label class="strong"><fmt:message key="rh.idiomas" />:</label>
															<input class="span12" type="text" id="pesquisa_idiomas" name="pesquisa_idiomas" maxlength="80">
														</div>
														<!-- // Fim Idiomas -->
														<!-- País/Uf/Cidade -->
														<div class="span6">
														<!-- Cidade -->
															<label class="strong"><fmt:message key="rh.cidade" />:</label>
															<input class="span12" type="text" id="pesquisa_cidade" name="pesquisa_cidade" maxlength="80">
														<!-- // Fim Cidade -->
														</div>
														<!-- // Fim País/Uf/Cidade -->
													</div>
												</div>

												<!-- Essa div contem mais um filtro de pesquisa de curriculo que será implementado futuramente (funcao) -->
												<div class="span4" style="display: none;">
													<label class="strong"><fmt:message key="rh.funcao" />:</label>
													<input type="text" class="span4" value="" id="funcao" name="funcao" maxlength="80">
												</div>

												<div class='filtro filtrobar main'>
													<div class="span4">
														<button style="float: left;" class="btn btn-icon btn-primary" type="button" onclick="JANELA_AGUARDE_MENU.show();document.formSugestaoCurriculos.fireEvent('triagemManual');"><i></i><fmt:message key="citcorpore.comum.pesquisar" /></button>
														<button style="float: left;" class="btn btn-icon btn-default glyphicons circle_remove" type="button" onclick="limpar();"><i></i><fmt:message key="rh.limpar" /></button>
													</div>
													<div style="float: left;" class='span4 topfiltro'>
														<ul>
															<li id='acoes' class='btn-group btn-block'>
																<div class='leadcontainer'>
																<button type='button' class='btn dropdown-lead btn-default' onclick="sugereCurriculos();"><fmt:message key="rh.pesquisaAutomatica" /></button>
																</div>
																<a class='btn btn-default dropdown-toggle filtro-toogle' href='#' data-toggle='dropdown' re='dropdownFiltro' ><span class='caret'></span> </a>
																<ul class="dropdownFiltro dropdown-menu pull-right">
																	<li>
																		<div>
													                       <input type='checkbox' name='chkFormacao' id='chkFormacao' value="F"/>&nbsp;<fmt:message key="triagem.pesquisaPorFormacao" />
												                 	 	</div>
																	</li>
																	<li class="dropdownFiltroPesquisa">
																		<div>
													                       <input type='checkbox' name='chkCertificacao' id='chkCertificacao' value="C"/>&nbsp;<fmt:message key="triagem.pesquisaPorCertificacao" />
												                 	 	</div>
																	</li>
																	<li>
																		<div>
													                       <input type='checkbox' name='chkIdioma' id='chkIdioma' value="I"/>&nbsp;<fmt:message key="triagem.pesquisaPorIdioma" />
												                 	 	</div>
																	</li>
																</ul>
															</li>
														</ul>
													</div>
													<!-- div class="span4">
														<button style="float: left;" class="btn btn-icon btn-default" type="button" onclick=""><i></i><fmt:message key="Lista de exigências" /></button>
													</div-->
													<div style="clear: both;"></div>
												</div>

												<div style="clear: both;"></div>

				                                <div class=''>
				                                	<div class="widget row-fluid" data-toggle="collapse-widget" id='collapse1' data-collapse-closed="true">

														<!-- Widget heading -->
														<div class="widget-head">
															<h4 class="heading"><fmt:message key="requisicaoPessoal.perfilCargo"/></h4>
															<!-- <span class="collapse-toggle"></span> -->
														</div>
														<!-- // Widget heading END -->

														<div class="widget-body collapse">
				                                			<div id='divPerfilCargo' style='display:block!important' class='span12'>
								                                <div class="span12">
																	<label for='atividades' class="campoObrigatorio"><fmt:message key="solicitacaoCargo.atividades"/></label>
																	<div>
																		<textarea rows="5" cols="122" class='span12' name='atividades' id='atividades' disabled="disabled"></textarea>
																	</div>
																</div>
																<div class="span12">
																	<h4 id="perfilProfissional" class=""><fmt:message key="solicitacaoCargo.perfilProfissional"/></h4>
																</div>
																<div class="row-fluid">
																	<div class="span6 divFormacaoAcademica hide">
																		<label class="campoObrigatorio strong"><fmt:message key="solicitacaoCargo.formacaoAcademica"/></label>
																		<div  id="gridFormacaoAcademica">
																			<table id="tblFormacaoAcademica" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none">
																				<tr>
																					<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
																					<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
																					<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
																				</tr>
																			</table>
																		</div>
																	</div>
																	<div class="span6 divCertificacao hide">
																		<label class=' strong'><fmt:message key="solicitacaoCargo.certificacoes"/></label>
																		<div  id="gridCertificacao">
																			<table id="tblCertificacao" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
																				<tr>
																					<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
																					<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
																					<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
																				</tr>
																			</table>
																		</div>
																	</div>
																</div>
																<div class="row-fluid">
																	<div class='span6 divCurso hide'>
																		<label class=' strong'><fmt:message key="solicitacaoCargo.treinamentos"/></label>
																		<div  id="gridCurso">
																			<table id="tblCurso" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
																				<tr>
																					<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
																					<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
																					<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
																				</tr>
																			</table>
																		</div>
																	</div>
													  				<div class='span6 divExperienciaInformatica hide'>
																		<label class=' strong'><fmt:message key="solicitacaoCargo.experienciaInformatica"/></label>
																		<div  id="gridExperienciaInformatica">
																			<table id="tblExperienciaInformatica" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
																				<tr>
																					<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
																					<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
																					<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
																				</tr>
																			</table>
																		</div>
													  				</div>
																</div>
																<div class="row-fluid">
													  				<div class='span6 divIdioma hide'>
																		<label class=' strong'><fmt:message key="solicitacaoCargo.idiomas"/></label>
																		<div  id="gridIdioma">
																			<table id="tblIdioma" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
																				<tr>
																					<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
																					<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
																					<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
																				</tr>
																			</table>
																		</div>
													  				</div>
													  				<div class='span6 divExperienciaAnterior hide'>
																		<label class=' strong'><fmt:message key="solicitacaoCargo.experienciaAnterior"/></label>
																		<div  id="gridExperienciaAnterior">
																			<table id="tblExperienciaAnterior" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
																				<tr>
																					<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
																					<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
																					<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
																				</tr>
																			</table>
																		</div>
													  				</div>
																</div>
																<div class="span12" style="margin-top:10px;">
												  					<h4 id="perfilCompetencia" class="section"><fmt:message key="solicitacaoCargo.perfilCompetencia"/></h4>
												  				</div>
												  				<div class='row-fluid'>
													  				<div class='span6 divConhecimento hide'>
																		<label class="strong"><fmt:message key="solicitacaoCargo.conhecimentos"/></label>
																		<div  id="gridConhecimento">
																			<table id="tblConhecimento" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
																				<tr>
																					<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
																					<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
																					<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
																				</tr>
																			</table>
																		</div>
													  				</div>
													  				<div class='span6 divHabilidade hide'>
																		<label class="strong"><fmt:message key="solicitacaoCargo.habilidades"/></label>
																		<div  id="gridHabilidade">
																			<table id="tblHabilidade" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
																				<tr>
																					<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
																					<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
																					<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
																				</tr>
																			</table>
																		</div>
													  				</div>
																</div>
																<div class="row-fluid">
												  					<div class='span6 divAtitudeIndividual hide'>
																		<label class="campoObrigatorio strong"><fmt:message key="solicitacaoCargo.atitudes"/></label>
																		<div  id="gridAtitudeIndividual">
																			<table id="tblAtitudeIndividual" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
																				<tr>
																					<th style="font-size:10px;" ><fmt:message key="rh.descricao"/></th>
																					<th style="width: 40%;font-size:10px;" ><fmt:message key="rh.detalhes"/></th>
																					<th style="width: 10%;font-size:10px;" ><fmt:message key="rh.obrigatoria"/></th>
																				</tr>
																			</table>
																		</div>
												  					</div>
																</div>
															</div>
														</div>
													</div>
												</div>

												<div style="clear: both;"></div>

												<div class="widget">
													<div class="widget-head" style="height:40px;">
														<h4 class="heading"><fmt:message key="rh.resultadoBuscaCurriculos" /></h4>
														<div class="" style="margin-top: 5px;">
														<button id="limparDadosCurriculo" style="float: right;" class="btn btn-icon btn-default" type="button" onclick="limparDadostableCurriculo()"><i></i><fmt:message key="citcorpore.comum.limpar" /></button>
														</div>
													</div>
													<!-- Tabela de resultados -->
													<div class="widget-body">

														<!-- Table  -->
														<div >
															<div class="row-fluid">
															</div>
															<!--CLASS para colocar bordas na tabela (table-bordered) -->
																<table class="dynamicTable table table-striped table-condensed dataTable" id="tblCurriculos" >
																	<!-- Table heading -->
																		<tr class="">
																			<th id="coluna1" style=" width:8%; overflow:auto;"><fmt:message key="rh.foto" /></th>
																			<th id="coluna2" style=" width:80%; overflow:auto;"><fmt:message key="citcorpore.comum.nome" /></th>
																			<th id="coluna3" style=" width:12%; overflow:auto;"><fmt:message key="rh.acoes" /></th>
																		</tr>
																	<!-- // Table heading END -->

																	<!-- Table body -->

																	<!-- // Table body END -->
															</table>
														</div>
														<!-- // Table END -->
													</div>
												</div>

												<div id="paginas" class="span12 pagination margin-bottom-none pull-right" style="text-align: center;" ></div>

												<!-- MODAL ITEM DE CONFIGURACAO ... -->
												<div class="modal hide fade in" id="modal_curriculo" aria-hidden="false">
													<!-- Modal heading -->
														<div class="modal-header">
															<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
														</div>
														<!-- // Modal heading END -->
														<!-- Modal body -->
														<div id="modalCurriculo" class="modal_curriculo">

														</div>
														<!-- // Modal body END -->
														<!-- Modal footer -->
														<div class="modal-footer">
															<a href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
														</div>
													<!-- // Modal footer END -->
												</div>

												<!--MODAL VISUALIZAR HISTORICO -->
												<div class="modal hide fade in" id="modal_visualizarHistorico" aria-hidden="false">
												<!-- Modal heading -->
												<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
												</div>
												<!-- // Modal heading END -->
												<!-- Modal body -->
												<div class="modal-body">
												<div id="conteudoVisualizarHistorico">
												</div>
												</div>
												<!-- // Modal body END -->
												</div>
											</form>
										</div>
									</div>
								</div>
							</div>

					</div>

				</div>

				<!--  Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
				<script src="${ctx}/pages/pesquisaCurriculo/js/pesquisaCurriculo.js"></script>
				<script type="text/javascript" src="${ctx}/cit/objects/RequisicaoPessoalDTO.js"></script>
			    <script type="text/javascript" src="${ctx}/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>
	   			<script type="text/javascript" src="${ctx}/cit/objects/CurriculoDTO.js"></script>
			</div>
		</div>
	</body>
</html>
