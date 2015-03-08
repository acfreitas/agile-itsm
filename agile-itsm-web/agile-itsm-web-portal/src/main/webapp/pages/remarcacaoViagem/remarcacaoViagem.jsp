<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

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
		<link type="text/css" rel="stylesheet" href="css/remarcacaoViagem.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
		<script type="text/javascript" src="${ctx}/cit/objects/DespesaViagemDTO.js"></script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
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
								<h4 class="heading strong"><fmt:message key="menu.nome.RemarcacaoViagem"/></h4>
							</div>
							<div class="widget-body collapse in">	
								<div class="tabsbar">
									<ul>
										<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="baseConhecimento.pesquisar"/></a></li>
										<li><a href="#tab2-3" id="tab2" data-toggle="tab" ><fmt:message key="requisicaoViagem.remarcacao"/></a></li>
									</ul>
								</div>	
								
								<div class="tab-content">
									<div class="tab-pane active" id="tab1-3">
										<form name='form' id='form' action='${ctx}/pages/remarcacaoViagem/remarcacaoViagem'>
			                                <input type='hidden' name='itemSerialiados' id='itemSerialiados' />
			                                <input type='hidden' name='idDespesaViagem' id='idDespesaViagem' /> 
			                                <input type='hidden' name='colDespesaViagemSerialize' id='colDespesaViagemSerialize'/>
			                                <input type='hidden' name='idResponsavel' id='idResponsavel'/>
											<input type='hidden' name='idEmpregado' id='idEmpregado' />
											<input type='hidden' name='idIntegranteViagem' id='idIntegranteViagem' />
											<input type='hidden' name='idSolicitacao' id='idSolicitacao' />
											<input type='hidden' name='rowIndexItem' id='rowIndexItem' />
											<input type='hidden' name='remarcarRoteiro' id='remarcarRoteiro'/>
											<input type='hidden' name='idCidadeOrigemAux' id='idCidadeOrigemAux' />
											<input type='hidden' name='idCidadeDestinoAux' id='idCidadeDestinoAux' />
											<input type='hidden' name='origem' id='origem' />
											<input type='hidden' name='destino' id='destino' />
											<input type='hidden' name='idaAux' id='idaAux' />
											<input type='hidden' name='voltaAux' id='voltaAux' />
											<input type='hidden' name='idFornecedor' id='idFornecedor' />
											<input type='hidden' name='idTipo' id='idTipo' />
											<input type='hidden' name='idRoteiro' id='idRoteiro' />
											<input type='hidden' name='rowTable' id='rowTable' />
											<input type='hidden' name='idMoeda' id='idMoeda' />
											<input type='hidden' name='nomeMoeda' id='nomeMoeda' />
								                               
		                                	<div class='row-fluid'>
												<div class='span12'>
												
												 <div class="widget">
													<div class="widget-head">
														<h4 class="heading strong"><fmt:message key='citcorpore.comum.filtro'/></h4>
													</div>
													
													<!-- .widget-head -->
													<div class="widget-body">
													
										             	<div class='span4'>
												            <label class='strong'><fmt:message key="gerenciamentoservico.NSolicitacao" /></label>
												            <input type='text' name="idSolicitacaoServico" id="idSolicitacaoServico" maxlength="10" class="span12 Format[Numero]" />
										                </div>
									                     	
										                <div class='span8'>
												            <label class='strong'><fmt:message key="itemControleFinanceiroViagem.nome" /></label>
												            <input type='text' name="nomeEmpregado" id="nomeEmpregado" class='span12' onchange="limpaIntegrante()"/>
									                    </div>	
							                     	
								                     	<div class='row-fluid'>
										             		 <div class='span12'>
										             		 
										             		 <div class="widget">
																	<div class="widget-head">
																		<h4 class="heading strong"><fmt:message key='requisicaoViagem.periodoDaViagem'/></h4>
																	</div><!-- .widget-head -->
																	
																	<div class="widget-body">
																		<div class='row-fluid'>
													             		 	<div class='span4'>
														             		 	<label class='strong'><fmt:message key="itemControleFinanceiroViagem.iniciandoEntre" /></label>
														             		 	<div class='row-fluid'>
															             		 	<div class='span5'>
														                            	<input type='text' name="dataInicio" id="dataInicio" maxlength="10" class="span12 Format[Data] datepicker" onblur="fctValidaData(this)"/>
														                            </div>
														                            <div class='span2' style="text-align: center !important;">
														                            	<fmt:message key="citcorpore.comum.a" />
														                            </div>
														                            <div class='span5'>
														                            	<input type='text' name="dataInicioAux" id="dataInicioAux" maxlength="10" class="span12 Format[Data] datepicker" onblur="fctValidaData(this)"/>
														                            </div>
													                            </div>
													                        </div>
													                     	   
													                        <div class='span4' style="text-transform: lowercase; margin-top: 26px; text-align: center;">
														                        <label class="radio inline strong">
																					<input type="radio" id="e" name="eOu" value="and" checked="checked"/><fmt:message key="citSmart.comum.e" />
																				</label>
																				<label class="radio inline strong">	
																					<input type="radio" id="ou" name="eOu" value="or" /> <fmt:message key="citSmart.comum.ou" />
																				</label>	
													                        </div>
													                     
													                        <div class='span4'>
														             		 	<label class='strong'><fmt:message key="itemControleFinanceiroViagem.terminandoEntre" /></label>
														             		 	<div class='row-fluid'>
															             		 	<div class='span5'>
														                            	<input type='text' name="dataFim" id="dataFim" maxlength="10" class="span12 Format[Data] datepicker" onblur="fctValidaData(this)"/>
														                            </div>
														                            <div class='span2' style="text-align: center !important;">
														                            	<fmt:message key="citcorpore.comum.a" />
														                            </div>
														                            <div class='span5'>
														                            	<input type='text' name="dataFimAux" id="dataFimAux" maxlength="10" class="span12 Format[Data] datepicker" onblur="fctValidaData(this)"/>
														                            </div>
													                            </div>
													                        </div>
												                        </div>
																		
																	</div>
																</div>		
										             		</div>
										             	</div>
									             	</div>
								             	</div>
								                     
								                     <div class='row-fluid'>
														<div class='span12'>
															<button type='button' name='btnPesquisarRequisicao' class="lFloat btn btn-icon btn-primary" onclick='pesquisarRequisicoes();'>
															<i></i><fmt:message key="citcorpore.comum.pesquisar" />
															</button>
															
															<button type='button' name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='limparFormulario();'>
														<i></i><fmt:message key="citcorpore.comum.limpar" />
														</button>
								                     	</div>
								                     </div>	
								                     
								               </div>
								         </div>
								         
								         <div class="row-fluid">&nbsp;</div>
								         
								         <div class="row-fluid">
											<div class="widget">
												<div class="widget-head" style="height: height: 30px!important">
													<h4 class="heading strong"><fmt:message key='controleFinanceiroViagemImprevisto.requisicoesViagem'/></h4>
												</div>
												<div class="widget-body">							
													<!-- Table  -->
													<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
														<div class="row-fluid"></div>
														<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblRequisicoesViagem" aria-describedby="DataTables_Table_0_info">
															<!-- Table heading -->
															<thead>
																<tr>
										                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 5%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="citcorpore.comum.numero" /></th>
										                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="citcorpore.comum.datainicio" /></th>
										                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="citcorpore.comum.datafim" /></th>
										                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 21%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="itemControleFinanceiroViagem.nome" /></th>
										                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 19%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="requisicaoViagem.cidadeOrigem" /></th>
										                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 19%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="requisicaoViagem.cidadeDestino" /></th>
										                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 15 %;" aria-label="Browser: activate to sort column ascending"><fmt:message key="visaoAdm.situacao" /></th>
										                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 5%;" aria-label="Browser: activate to sort column ascending"></th>
										                       </tr>
															</thead>
															<!-- // Table heading END -->
																			
															<!-- Table body -->											
															<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
															<!-- // Table body END -->
														</table>
														<!-- // Table END -->
													</div>
												</div>
											</div>
										</div>
										
									</div>	
									
									
									<div class="tab-pane" id="tab2-3">
										<div class="section">
											<div class='row-fluid'>
												<div class='row-fluid'>
													
													<div class='row-fluid'>
														<div class='span12'>
															<input type='text' name="nomeIntegrante" id="nomeIntegrante" maxlength="120" readonly="readonly" class="span12" />
														</div> 	
													</div>
													
													<div class="row-fluid">
														<div class="widget" data-toggle="collapse-widget" data-collapse-closed="false">
															<div class="widget-head" style="height: height: 30px!important">
																<h4 class="heading"><fmt:message key='requisicaoViagem.roteiroViagem'/><h4>
															</div>
															<div class="widget-body">	
																<div class="row-fluid">
									                            	<div class="span6" id='divNomeDaCidadeOrigem'>
									                           			<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.origem" /></label>	
									                            		<input id="nomeCidadeOrigem" name="nomeCidadeOrigem" class="span12" type="text" required="required" />
									                            	</div><!-- .span6 -->
									                            	<div class="span6">
									                            		<label class='strong campoObrigatorio'><fmt:message key="importmanager.destino" /></label>
									                            		<input id="nomeCidadeDestino" name="nomeCidadeDestino" class="span12" type="text" required="required" />
									                            	</div><!-- .span6 -->
									                           	</div><!-- .row-fluid -->
									                           
									                            <div class="row-fluid">
									                            	 <div class="span4">
									                            	 	<label for="dataInicioViagem" class="strong campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.ida"/></label>
									                            	 	<input id="ida" name="ida" maxlength="10" type="text" class="Format[Date] Description[Data Inicio] Valid[Date] text datepicker span12" onblur="fctValidaData(this)" onchange="calcularQuantidadeDias()" />
									                            	 </div><!-- .span4 -->
									                            	 <div class="span4">
									                            	 	<label for="dataFimViagem" class="strong campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.volta"/></label>
									                            	 	<input id="volta" name="volta" maxlength="10" type="text" class="Format[Date] Description[Data Fim] Valid[Date] text datepicker span12" onblur="fctValidaData(this)" onchange="calcularQuantidadeDias()" />
									                            	 </div><!-- .span4 -->
									                            	 <div class="span4">
									                            	 	<label for="qtdeDias" class="strong campoObrigatorio"><fmt:message key="requisicaoViagem.quantidadeDias"/></label>
									                            	 	<input id="qtdeDias" name="qtdeDias" maxlength="10" type="text" readonly="readonly" class="span12"/>
									                            	 </div><!-- .span4 -->
									                            </div><!-- .row-fluid -->
															</div>
														</div>
													</div>		
													
														
													</div>
													
													<div class="row-fluid">
														<div class="widget">
															<div class="widget-head" style="height: height: 30px!important">
																<h4 class="heading strong"><fmt:message key="remarcaoViagem.despesaRemarcaoViagem" /></h4>
																<button type='button' name='btnAddItens' class="lFloat btn btn-icon btn-primary" style="float: right; margin-top: 2px;" onclick='abrePopupItens();'>
																	<i></i><fmt:message key="requisicaoViagem.addItemDaDespesa" />
																</button>
															</div>
															<div class="widget-body">							
																<!-- Table  -->
																<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
																	<div class="row-fluid"></div>
																	<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblItemRemarcacaoViagem" aria-describedby="DataTables_Table_0_info">
																		<!-- Table heading -->
																		<thead>
																			<tr>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="remarcaoViagem.dataRegistro" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 17%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="citcorpore.comum.tipo" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 30%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="coletaPreco.fornecedor" /></th>
																				 <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="coletaPreco.quantidade" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 10%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="itemPedidoCompra.valorTotal" /></th>
																				<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 10%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="formaPagamento.formaPagamento" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="menu.nome.Moeda" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 12%;" aria-label="Browser: activate to sort column ascending"></th>
													                       </tr>
																		</thead>
																		<!-- // Table heading END -->
																						
																		<!-- Table body -->											
																		<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
																		<!-- // Table body END -->
																	</table>
																	<!-- // Table END -->
																</div>
															</div>
														</div>
													</div>
													
													<div class="row-fluid">
														<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true">
															<div class="widget-head" style="height: height: 30px!important">
																<h4 class="heading strong"><fmt:message key="remarcaoViagem.historicoRemarcacoes" /></h4>
															</div>
															<div class="widget-body">							
																<!-- Table  -->
																<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
																	<div class="row-fluid"></div>
																	<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblDespesaHist" aria-describedby="DataTables_Table_0_info">
																		<!-- Table heading -->
																		<thead>
																			<tr>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="remarcaoViagem.dataRegistro" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 17%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="citcorpore.comum.tipo" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 36%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="coletaPreco.fornecedor" /></th>
																				<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="coletaPreco.quantidade" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 10%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="itemPedidoCompra.valorTotal" /></th>
																				<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 10%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="formaPagamento.formaPagamento" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="menu.nome.Moeda" /></th>
													                       </tr>
																		</thead>
																		<!-- // Table heading END -->
																						
																		<!-- Table body -->											
																		<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
																		<!-- // Table body END -->
																	</table>
																	<!-- // Table END -->
																</div>
															</div>
														</div>
													</div>
													
													<div class="row-fluid"> 
														<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true">
															<div class="widget-head" style="height: height: 30px!important">
																<h4 class="heading strong"><fmt:message key="requisicaoViagem.marcacaoOriginal" /></h4>
															</div>
															<div class="widget-body">							
																<!-- Table  -->
																<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
																	<div class="row-fluid"></div>
																	<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblItemDespesaOriginal" aria-describedby="DataTables_Table_0_info">
																		<!-- Table heading -->
																		<thead>
																			<tr>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="remarcaoViagem.dataRegistro" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 17%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="citcorpore.comum.tipo" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 36%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="coletaPreco.fornecedor" /></th>
																				<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="coletaPreco.quantidade" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 10%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="itemPedidoCompra.valorTotal" /></th>
																				<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 10%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="formaPagamento.formaPagamento" /></th>
													                           <th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 8%;" aria-label="Browser: activate to sort column ascending"><fmt:message key="menu.nome.Moeda" /></th>
													                       </tr>
																		</thead>
																		<!-- // Table heading END -->
																						
																		<!-- Table body -->											
																		<tbody role="alert" aria-live="polite" aria-relevant="all"></tbody>
																		<!-- // Table body END -->
																	</table>
																	<!-- // Table END -->
																</div>
															</div>
														</div>
													</div>																																							
													
													<br>
													
													<div class="row-fluid">
														<div class="span12">
															<button type='button' name='btnAdicionarItem' class="lFloat btn btn-icon btn-primary" onclick='remarcarViagem();'>
																<i></i><fmt:message key="requisicaoViagem.remarcarViagem" />
															</button>
															
															<button type='button' name='btnCancelar' class="lFloat btn btn-icon btn-primary" onclick='cancelar();'>
																<i></i><fmt:message key="citcorpore.comum.cancelar" />
															</button>
															
														</div>
													</div>
												</div>
												</form>	
												<div id="POPUP_ITEMDESPESA" name="POPUP_ITEMDESPESA"  style="overflow: hidden;" title="<fmt:message key="requisicaoViagem.despesaViagem"/>">
													<form name='formItem' id='formItem' action='${ctx}/pages/remarcacaoViagem/remarcacaoViagem'>
														<input type='hidden' name='idSolicitacao' id='idSolicitacao' />
											
														<div id="form-itens-container">
															<div class='row-fluid'>
																<div class="span4">
																	<label class="campoObrigatorio strong"><fmt:message key="requisicaoViagem.tipoDespesa" /></label>
																	<select name='tipoDespesa' id='tipoDespesa' class='span12' onchange="tratarValoresTipoMovimentacao();"></select>
																</div><!-- .span3 -->
																
																<div class="span8">
																	<label class="campoObrigatorio strong"><fmt:message key="itemControleFinanceiroViagem.fornecedor" /></label>
																	<input type='text' id="nomeFornecedor" name="nomeFornecedor" maxlength="100" class='span12' />
																</div><!-- .span6 -->
															</div><!-- .row-fluid -->
															
															<div class='row-fluid'>
																<div class="span3">
																	<label for="moeda" class="strong campoObrigatorio"><fmt:message key="moeda.moeda"/></label>
																	<select id="moeda" name='moeda' class="Valid[Required] Description[moeda.moeda] span12"></select>
																</div><!-- span3 -->
																
																<div class="span5">
																	<label class="campoObrigatorio strong"><fmt:message key="itemControleFinanceiroViagem.formaPagamento" /></label>
																	<select name='idFormaPagamento' id='idFormaPagamento' class='span12'></select>
																</div><!-- .span5 -->
																
																<div class="span2">
																	<label id="labelPrazoCotacao" class="campoObrigatorio strong"><fmt:message key="despesaViagem.dataCotacaoExpira" /></label>
																	<input id="prazoCotacao" name="prazoCotacao" size="10" maxlength="10" type="text" onchange="" onblur="ValidacaoUtils.validaData(this, '');" class="Format[Data] span='12' datepicker " />
																</div><!-- .span2 -->
																
																<div class="span2">
																	<label id="labelHoraCotacao" class="campoObrigatorio strong"><fmt:message key="despesaViagem.horaCotacaoExpira" /></label>
																	<input id="horaCotacao" name="horaCotacao" size="10" class="span12" maxlength="10" type="text" onblur="ValidacaoUtils.validaHora(this, '')"/>
																</div><!-- .span2 -->
															</div><!-- .row-fluid -->
															
															<div class='row-fluid'>
																<div class="span4">
																	<label class="campoObrigatorio strong"><fmt:message key="itemControleFinanceiroViagem.valorUnitario" /></label>
																	<input type='text' class="format-money span12" id="valor" name="valor" maxlength="8" onblur="document.formItem.fireEvent('calcularTotal');" />
																</div><!-- .span4 -->
																
																<div class="span4">
																	<label class="campoObrigatorio strong"><fmt:message key="itemControleFinanceiroViagem.quantidade" /></label> 
																	<input type='text' id="quantidade" name="quantidade" class="Format[Numero] span12" maxlength="8" onblur="document.formItem.fireEvent('calcularTotal');" />
																</div><!-- .span4 -->
																
																<div class="span4">
																	<label class="strong"><fmt:message key="despesaViagem.totalDespesa" /></label>
																	<input type='text' id="valorAdiantamento" name="valorAdiantamento" class="Format[Moeda] span12" maxlength="20" disabled="disabled" />
																</div><!-- .span4 -->
															</div><!-- .row-fluid -->
															
															<div class='row-fluid'>
																<div class='span12'>
																	<label class="strong"><fmt:message key="avaliacaoFonecedor.observacao" /></label>
																	<textarea id="observacoes" name="observacoes" class="span12" float: left;"></textarea>
																</div><!-- .span12 -->
															</div><!-- .row-fluid -->
														</div><!-- #form-itens-container -->
														
														<div class='row-fluid'>
															<div class="span12">
																<button type='button' name='btnAdicionarItem' class="lFloat btn btn-icon btn-primary" style="float: right;" onclick='adicionarItem();'>
																<i></i><fmt:message key="requisicaoProduto.adicionarItem" />
																</button>
															</div>
														</div>
													</form>
												</div>
											</div>
										</div>
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
		<script type="text/javascript" src="js/remarcacaoViagem.js"></script>
		<script src="js/jquery.maskMoney.js"></script>
		<script src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>
	</body>
</html>