<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
	<head>	
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="css/portal2_1.css"/>
		<script type="text/javascript" src="js/portal2_1.js"></script>
		<link type="text/css" rel="stylesheet" href="../templateGerenciamento/css/templateGerenciamento.css"/>
		<script type="text/javascript" src="../templateGerenciamento/js/templateGerenciamento.js"></script>
		<script type="text/javascript" src="../../cit/objects/InfoCatalogoServicoDTO.js"></script>
	</head>
	<body>
		<div class="container-fluid fixed ">
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar main hidden-print">
			
				<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
				<input type="hidden" id="rowIndex" name="rowIndex"/>
			</div>
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<div id="content">				
					<div class="separator top"></div>	
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget" data-toggle="collapse-widget">
								<div class="widget-head">
									<h4 class="heading">Solicitações</h4>
								</div>
								<div class="widget-body collapse in">								
									<div class="span12 filtro">		
										<div class="row-fluid" >									
											<div class="span6">				
												<div class="input-append">
												  	<input class="span11" id="" type="text" placeholder="Buscar">
											  		<button class="btn btn-default" type="button"><i class="icon-search"></i></button>
												</div>
											</div>
											<div class="span6">											
												<div id="acoes" class="btn-group btn-block span3">
												   <div class="leadcontainer">
														<button class="btn dropdown-lead btn-default">Filtros de busca</button>
													</div>
													<a class="btn btn-default dropdown-toggle filtro-toogle" href="javascript:;" id="dropdownFiltro" ><span class="caret"></span> </a>
													<ul class="dropdownFiltro pull-right">
														<li>
															<!-- Inicio Filtro -->
															<form method="post" action="">
																<div class="row-fluid">
																	<div class="span12">																			
																		<div class="row-fluid">
																			<div class="span3">
																				<label>Número da Solicitação</label>
																				<input type="text" class="input-block-level">
																			</div>
																			<div class="span9">
																				<label>Contrato</label>
																				<select class="selectpicker span12" >
																					<option>Selecione</option>
																				</select>
																			</div>																					
																		</div>
																		<div class="row-fluid">
																			<div class="span3">
																				<label>Tipo de Solicitação</label>
																				<select class="selectpicker span12" >
																					<option>Selecione</option>
																				</select>
																			</div>
																			<div class="span9">
																				<label>Solicitante</label>
																				<select class="selectpicker span12" >
																					<option>Selecione</option>
																				</select>
																			</div>																					
																		</div>
																		<div class="row-fluid">
																			<div class="span9">
																				<label>Responsável</label>
																				<select class="selectpicker span12" >
																					<option>Selecione</option>
																				</select>
																			</div>
																			<div class="span3">
																				<label>Grupo Executor</label>
																				<select class="selectpicker span12" >
																					<option>Selecione</option>
																				</select>
																			</div>
																		</div>
																		<div class="row-fluid">
																			<div class="span3">
																				<label>Status</label>
																				<select class="selectpicker span12" >
																					<option>Selecione</option>
																				</select>
																			</div>
																			<div class="span9">
																				<label>Palavras Chave</label>
																				<input type="text" class="palavrasChave span12">
																			</div>
																		</div>
																		<div class="row-fluid">
																			<div class="span3">
																				<button type="submit" class="btn btn-icon btn-primary glyphicons search"><i></i>Buscar</button>
																			</div>
																		</div>
																	</div>
																</div>
															</form>		
															<!-- Fim Filtro -->											
														</li>
													</ul>
												</div>
											</div>
										</div>
									</div>
									<div class="row-fluid" >									
										<!-- Inicio Solicitações abertas -->
										<div class="span12">										
											<cit:gerenciamentoField tipoLista="1" classeExecutora="br.com.centralit.citcorpore.ajaxForms.GerenciamentoServicosImpl" paginacao="true"></cit:gerenciamentoField>																			
										</div>
										<!-- Fim Solicitações abertas -->
									</div>
									</div>
								</div>
								<div class="widget" data-toggle="collapse-widget">
									<div class="widget-head">
										<h4 class="heading"><fmt:message key="portal.carrinho.servico" /></h4>
									</div>
									<div class="widget-body collapse in">								
										<div class="span12 filtro">		
											<div class="row-fluid" >									
												<div class="span6">				
													<div class="input-append">
													  	<input class="span11" id="" type="text" placeholder="Buscar">
												  		<button class="btn btn-default" type="button"><i class="icon-search"></i></button>
													</div>
												</div>
											</div>
										</div>
										<div class="row-fluid" >									
											<div class="span12">										
												<div id="titulo" >
													<div class="row-fluid inicio">
														<div class="span12">
															<div class="pagination pagination-right margin-none">
																<ul>
																	<li class="disabled"><a href="#">&laquo;</a></li>
																	<li class="active"><a href="#">1</a></li>
																	<li><a href="#">2</a></li>
																	<li><a href="#">3</a></li>
																	<li><a href="#">&raquo;</a></li>
																</ul>
															</div>													
														</div>
													</div>
												</div>
											</div>
											<!-- Tabs -->
											<div class="tabsbar tabsbar-2">
												<ul class="row-fluid row-merge">
													<li class="span3 glyphicons cargo active"><a href="#tab1-4" data-toggle="tab"><i></i>Listagem de Serviços</a></li>
													<li class="span3 glyphicons cart_in"><a href="#tab2-4" data-toggle="tab"><i></i> <span>Carrinho de Serviços</span></a></li>
													<li class="span3 glyphicons pencil"><a href="#tab2-4" data-toggle="tab"><i></i> <span>Gerenciamento - Meu Catálogo</span></a></li>
												</ul>
											</div>
											<!-- Fim tabs -->
											<form name='form' action='${ctx}/pages/portal2_1/portal2_1'>
												<input type="hidden" id="idCatalogoServico" name="idCatalogoServico"/>
												<input type='hidden' name='servicosLancados' id='servicosLancados'/>
												<div class="tab-content">
													<div class="tab-pane active" id="tab1-4"> <!-- conteudo da tab Listagem de Serviços -->
														<!-- Início listagem de itens -->
														<div class="shop-client-products list" id="listaServicos">
														</div>
													<!-- 	<table class="table table-bordered" id="listaServicos"> -->
													    	<%-- <tr><th class="span5"><fmt:message key="servico.nome" /></th><th class="span3"><fmt:message key="portal.carrinho.descricao" /></th><th class="span4"><fmt:message key="portal.carrinho.adicionar" /></th></tr> --%>
													    	<!-- <tr><td class="span4">BANCO DE DADOS ORACLE</td><td class="span1 center"><a type="button" class="btn btn-small" href="#descricao_modal" data-toggle="modal">Saiba mais</a></td><td class="span1 center"><a type="button" class="btn btn-small btn-primary" href="#addcarrinho_modal" data-toggle="modal">Adicionar ao carrinho</a></td></tr> -->
													   <!--  </table> --><!-- // Fim listagem de itens -->
														<!-- Inicio Modal Descrição -->

														<!-- Inicio Modal adicionar ao Carrinho -->
														<div class="modal fade" id="addcarrinho_modal" data-toggle="modal">
															<div class="modal-header">
														    	<a class="close" data-dismiss="modal">&times;</a>
														    	<h3>Adicionar Serviços</h3>
														  	</div>
														  	<div class="modal-body">
														    	<table class="table table-bordered" id="tblAddServico">
															    	<tr><th class="span1">#</th><th class="span2">Nome do Serviço</th><th class="span3">Preço</th></tr>
															    	<tr><td class="celulaGrid span1"><img id="imgDelInfo" style="cursor: pointer;" title="Excluir Dados" src="/citsmart/imagens/delete.png" onclick="deleteLinha('tblAddServico', this.parentNode.parentNode.rowIndex);"></td><td class="span4">HARDWARE.IMPRESSORA.LASER.APAGAR FILA DE IMPRESSAO.</td><td>R$ 20,00</td></tr>
															    	<tr><td class="celulaGrid span1"><img id="imgDelInfo" style="cursor: pointer;" title="Excluir Dados" src="/citsmart/imagens/delete.png" onclick="deleteLinha('tblAddServico', this.parentNode.parentNode.rowIndex);"></td><td class="span4">HARDWARE.IMPRESSORA.LASER.APAGAR FILA DE IMPRESSAO.</td><td>R$ 15,00</td></tr>
															   	</table>
														  	</div>
														 	<div class="modal-footer">
																<a href="#" class="btn" data-dismiss="modal">Fechar</a>
																<a href="#" class="btn btn-primary">Salvar</a>
															</div>
														</div>
														<!-- Fim Modal adicionar ao Carrinho -->
													</div>
													<div class="tab-pane" id="tab2-4">
														<!-- Shopping cart -->
														<div class="shop-client-products cart">
															<!-- With selected actions -->
															<div class="checkboxs_actions hide form-inline small pull-right">
																<label class="strong">With selected:</label>
																<select class="selectpicker" data-style="btn-default btn-small">
																	<option>Action</option>
																	<option>Action</option>
																	<option>Action</option>
																</select>
																<div class="separator bottom"></div>
															</div>
															<div class="clearfix"></div>
															<!-- // With selected actions END -->
															<!-- Cart table -->
															<table id="carrinho" class="table table-bordered table-primary table-striped table-vertical-center checkboxs js-table-sortable">
																<thead>
																	<tr>
																		<!-- <th style="width: 1%;" class="uniformjs"><input type="checkbox" /></th> -->
																		<th style="width: 1%;" class="center"><fmt:message key="carrinho.numero"/></th>
																		<th></th>
																		<th style="width: 80px;"><fmt:message key="carrinho.categoria"/></th>
																		<th style="width: 50px;"><fmt:message key="carrinho.quantidade"/></th>
																		<th style="width: 80px;"><fmt:message key="carrinho.preço"/></th>
																		<th style="width: 80px;"><fmt:message key="carrinho.subtotal"/></th>
																		<th style="width: 80px;"><fmt:message key="carrinho.excluir"/></th>
																	</tr>
																</thead>
																<tbody>
																	<!-- Cart item -->
																	<tr class="selectable">
																		<!-- <td class="center uniformjs"><input type="checkbox" /></td> -->
																		<td class="center">1</td>
																		<td class="product">
																			<div class="media">
																				<!-- <a href="shop_client_product.htmllang=en&amp;layout_type=fluid&amp;menu_position=menu-left&amp;style=style-light" class="media-object pull-left"><img class="thumb" src="theme/images/gallery-2/1.jpg" width="80" alt=""></a> -->
																				<div id="produto" class="media-body">
																					<h5 style="color: #333333">Product name goes here</h5><span>Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma bandeja de tipo.</span>
																					<!-- <span class="col">
																						Size:<br/>
																						<span class="label">3-4 Years</span>
																					</span> -->
																					<!-- <span class="col">
																						Color:<br/>
																						<span class="label">Blue Navy</span>
																					</span> -->
																					<div class="clearfix"></div>
																				</div>
																			</div>
																		</td>
																		<td class="center">categoria</td>
																		<td class="center"><input type="text" value="1" class="input-block-level" style="margin: 0;" /></td>
																		<td class="center">&dollar;1,000.00</td>
																		<td class="center">subtotal</td>
																		<td class="center"><a href="#" class="btn-action glyphicons remove_2 btn-danger"><i></i></a></td>
																	</tr>
																	<!-- // Cart item END -->
																</tbody>
															</table>
															<!-- // Cart table END -->
															<div class="separator bottom"></div>
															<!-- Row -->
															<div class="row-fluid">
															<!-- Column -->
																<div class="span5">
																	<!-- <div class="box-generic center">
																		<strong>Discount Code:</strong>
																		<div class="separator bottom"></div>
																		<input type="text" value="723-WTX31" class="span12" />
																		<button class="btn btn-inverse">Apply code</button>
																	</div> -->
																</div>
																<!-- Column END -->
																<!-- Column -->
																<div class="span4 offset3">
																	<table class="table table-borderless table-condensed cart_total">
																		<tbody>
																			<%-- <tr>
																				<td class="right"><fmt:message key="Subtotal:"/></td>
																				<td class="right strong">&dollar;1,000.00</td>
																			</tr>
																			<tr>
																				<td class="right"><fmt:message key="Delivery:"/></td>
																				<td class="right strong">&dollar;5.00</td>
																			</tr> --%>
																			<!--  <tr>
																				<td class="right">VAT:</td>
																				<td class="right strong">&dollar;119.00</td>
																			</tr>  -->
																			<tr>
																				<td colspan="2">
																					<div class="separator bottom"></div>
																					<span class="label center label-block large"><fmt:message key="carrinho.total"/> &dollar;3,000.00</span>
																				</td>
																			</tr>
																			<tr>
																				<td colspan="2"><button type="submit" class="btn btn-block btn-primary btn-icon glyphicons right_arrow"><i></i><fmt:message key="carrinho.Concluir"/></span></td>
																			</tr>
																		</tbody>
																	</table>
																</div>
																<!-- // Column END -->
															</div>
															<!-- // Row END -->
														</div>	<!-- // Shopping cart END -->
													</div>
												<!-- <div class="tab-pane" id="tab2-4">
													<h4>Third tab</h4>
													<p>Lorem ipsum dolor sit amet ...</p>
												</div>
												<div class="tab-pane" id="tab4-4">
													<h4>Fourth tab</h4>
													<p>Lorem ipsum dolor sit amet ...</p>
												</div> -->
												</div>
											</form>
										</div>
										<div class="separator top"></div>
										<div class="row-fluid">
											<div class="span12">										
												<div id="titulo" >
												<!-- 	<div class="row-fluid inicio">
														<div class="span12">
															<div class="pagination pagination-right margin-none">
																<ul>
																	<li class="disabled"><a href="#">&laquo;</a></li>
																	<li class="active"><a href="#">1</a></li>
																	<li><a href="#">2</a></li>
																	<li><a href="#">3</a></li>
																	<li><a href="#">&raquo;</a></li>
																</ul>
															</div>													
														</div>
													</div> -->
												</div>
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
	</body>
</html>