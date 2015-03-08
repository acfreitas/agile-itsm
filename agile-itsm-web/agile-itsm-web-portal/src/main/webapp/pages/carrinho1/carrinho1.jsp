<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<html>
	<head>	
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="../carrinho1/carrinho1.css"/>
	</head>
	<body>
		<div class="container-fluid fixed ">
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar main hidden-print">
			
				<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
				
				<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>	
				
			</div>
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<div id="content">
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
		<table class="table table-bordered table-primary table-striped table-vertical-center checkboxs js-table-sortable">
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
		
	</div>
	<!-- // Shopping cart END -->
				</div>
				<!--  Fim conteudo-->
				
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
				
			</div>
		</div>
	</body>
</html>