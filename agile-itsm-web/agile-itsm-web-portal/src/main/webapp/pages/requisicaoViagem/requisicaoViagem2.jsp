<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.UtilStrings"%>

<!doctype html public "">
<html>
<head>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
	<%@include file="/novoLayout/common/include/titulo.jsp"%>
	<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>
	<link type="text/css" rel="stylesheet" href="css/RequisicaoViagem2.css"/>
	
	<%
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", -1);
	%>
</head>
<body>
	<div id="wrapper">
		<div id="content">
			<div id="criacao-viagem-container">
				<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true">
					<div class="widget-head">
						<h3 class="heading">Dados da solicitação da viagem</h3>
					</div><!-- .widget-head -->
					<div class="widget-body">
						<div class="widget-body-inner">
							<div class="row-fluid">
								<div class="span6">
									<label class="strong">Cidade origem</label>
									<input type="text" class="span12" id="cidade_origem" name="cidade_origem" />
								</div><!-- .span6 -->
								<div class="span6">
									<label class="strong">Cidade destino</label>
									<input type="text" class="span12" id="cidade_destino" name="cidade_destino" />
								</div><!-- .span6 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid">
								<div class="span4">
									<label class="strong">Data ínicio</label>
									<input type="text" class="span12" id="data_inicio" name="data_inicio" />
								</div><!-- .span4 -->
								<div class="span4">
									<label class="strong">Data fim</label>
									<input type="text" class="span12" id="data_fim" name="data_fim" />
								</div><!-- .span4 -->
								<div class="span4">
									<label class="strong">Total dias</label>
									<input type="text" class="span12" id="total_dias" name="total_dias" />
								</div><!-- .span4 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid">
								<div class="span4">
									<label class="strong">Centro de resultado</label>
									<select class="span12" id="centro_resultado" name="centro_resultado">
										<option>-- Selecione --</option>
									</select>
								</div><!-- .span4 -->
								<div class="span4">
									<label class="strong">Projeto</label>
									<select class="span12" id="centro_resultado" name="centro_resultado">
										<option>-- Selecione --</option>
									</select>
								</div><!-- .span4 -->
								<div class="span4">
									<label class="strong">Justificativa</label>
									<select class="span12" id="justificativa" name="justificativa">
										<option>-- Selecione --</option>
									</select>
								</div><!-- .span4 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid">
								<div class="span12">
									<label class="strong">Motivo</label>
									<textarea class="span12" id="motivo" name="motivo"></textarea>
								</div><!-- .span12 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid" id="integrantes-container">
								<div class="widget widget-hide-button-collapse">
									<div class="widget-head">
										<h3 class="heading">Integrante(s) da viagem</h3>
									</div><!-- .widget-head -->
									<div class="widget-body">
										<div class="row-fluid">
											<div class="span12">
												<label class="radio inline strong">
													<input type="radio" name="integrante" id="funcionario" class="radio-integrante" value="1" /> Funcionário
												</label>
												<label class="radio inline strong">
													<input type="radio" name="integrante" id="nao_funcionario" class="radio-integrante" value="0" /> Não funcionário
												</label>
											</div><!-- .span12 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid">
											<div class="span6">
												<label class="strong">Nome funcionário</label>
												<input type="text" class="span12" id="nome_funcionario" name="nome_funcionario" />
											</div><!-- .span6 -->
											<div class="span6">
												<label class="strong">Nome do responsável pela prestação de contas</label>
												<input type="text" class="span12" id="nome_responsavel_prest_contas" name="nome_responsavel_prest_contas" />
											</div><!-- .span6 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid" id="info-complementares-nao-funcionario-container">
											<div class="span12">
												<label class="strong">Informações complementares não funcionário</label>
												<textarea class="span12" id="info_complementares_nao_funcionario" name="info_complementares_nao_funcionario"></textarea>
											</div><!-- .span12 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid">
											<div class="span12">
												<button id="btn-add-integrante" type="button" class="btn btn-small btn-primary btn-icon glyphicons circle_plus">
													<i></i> Adicionar
												</button>
											</div><!-- .span12 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid" id="table-integrantes-container">
											<table class="table table-bordered table-striped table-hover" id="table_integrantes">
												<thead>
													<tr>
														<th>Integrante</th>
														<th>Funcinário</th>
														<th>Responsável</th>
														<th class="center">Excluir</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>Vânia Padilha</td>
														<td>Não</td>
														<td>Gilberto Tavares Nery</td>
														<td class="center"><a class="btn" href="javascript:;">&times;</a></td>
													</tr>
													
													<tr>
														<td>Gilberto Tavares Nery</td>
														<td>Sim</td>
														<td>Gilberto Tavares Nery</td>
														<td class="center"><a class="btn" href="javascript:;">&times;</a></td>
													</tr>
												</tbody>
											</table>
										</div><!-- .row-fluid -->
									</div><!-- .widget-body -->
								</div><!-- .widget -->
							</div><!-- #integrantes-container -->
						</div><!-- .widget-body-inner -->
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- #criacao-viagem-container -->
			
			<div id="execucao-viagem-container">
				<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true">
					<div class="widget-head">
						<h3 class="heading">Controle financeiro da viagem</h3>
					</div><!-- .widget-head -->
					<div class="widget-body">
						<div class="widget-body-inner">
							
							<div class="row-fluid">
								<div class="span12">
									<label class="strong">Moeda</label>
									<select id='moeda' name='moeda' class="span3">
										<option>Real</option>
									</select>
								</div>							
							</div>
							
							<div class="row-fluid" id="integrantes-itens-container">
								<div class="widget widget-hide-button-collapse">
									<div class="widget-head">
										<h3 class="heading">Integrante(s) da viagem</h3>
										<span onclick="abrirPopup();" id="add_itens" name='add_itens' class="lFloat btn btn-icon btn-primary">Adicionar itens</span>
									</div><!-- .widget-head -->
									
									<div class="row-fluid">
										<div class="span12">
											<ul class="filetree treeview browser">
											    <li>
											    	<span class='folder'>Gilberto Tavares Nery</span>
											        <ul>
											            <li>
											            	<div class='file'>
												            	<table class='table_integrante_controle'>
												            		<tr>
												            			<td width="5%"><span class="glyphicons airplane"><i></i>&nbsp;</span></td>
												            			<td width="50%">Passagem aerea</td>
												            			<td width="40%">200,00</td>
												            			<td width="5%">&times;</td>
												            		</tr>
												            		<tr>
												            			<td width="5%"><span class="glyphicons building"><i></i>&nbsp;</span></td>
												            			<td width="50%">Hospedagem</td>
												            			<td width="40%">400,00</td>
												            			<td width="3%">&times;</td>
												            		</tr>
												            		<tr>
												            			<td width="5%"></td>
												            			<td width="50%">Valor total</td>
												            			<td width="40%">600,00</td>
												            			<td width="3%"></td>
												            		</tr>
												            	</table>
											            	</div>
											            </li>
											        </ul>
											    </li>
											</ul>
										</div>							
									</div>
									
									<div class="row-fluid">
										<div class="span12">
											<ul class="filetree treeview browser">
											    <li>
											    	<span class='folder'>Vania Padilha (Não funcionario)</span>
											        <ul>
											            <li>
											            	<div class='file'>
												            	<table class='table_integrante_controle'>
												            		<tr>
												            			<td width="5%"><span class="glyphicons airplane"><i></i>&nbsp;</span></td>
												            			<td width="50%">Passagem aerea</td>
												            			<td width="40%">350,00</td>
												            			<td width="5%">&times;</td>
												            		</tr>
												            		<tr>
												            			<td width="5%"><span class="glyphicons building"><i></i>&nbsp;</span></td>
												            			<td width="50%">Hospedagem</td>
												            			<td width="40%">550,00</td>
												            			<td width="5%">&times;</td>
												            		</tr>
												            		<tr>
												            			<td width="5%"></td>
												            			<td width="50%">Valor total</td>
												            			<td width="40%">900,00</td>
												            			<td width="5%"></td>
												            		</tr>
												            	</table><!-- .table_integrante_controle -->
											            	</div>
											            </li>
											        </ul>
											    </li>
											</ul>
										</div>							
									</div>
																				
								</div><!-- .widget -->
							</div><!-- #integrantes-itens-container -->
						</div><!-- .widget-body-inner -->
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- #execucao-viagem-container -->
			
			<div id="POPUP_ITEMCONTROLEFINANCEIRO" title="<fmt:message key="itemControleFinanceiroViagem.itemControleFinanceiroViagem"/>">
				<div class='row-fluid'>
					<div class='span12'>
						
						<div class='row-fluid'>
							<div class="span6">
								<label class="campoObrigatorio">Tipo de momentação</label>
								<select name='classificacaoAux' id='classificacaoAux' onchange="tratarTipoMovimentacaoFinanceira(this);" class='span12' >
								</select>
							</div>
							
							<div class="span6">
								<label class="campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.fornecedor" /></label>
								<select onchange="recuperaNomeFornecedor();" id="idFornecedorAux" name="idFornecedorAux" class='span12'>
								</select>
							</div>
						</div>
								
						<div class='row-fluid'>
							<div class="span4">
								<label class="campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.formaPagamento" /></label>
								<select name='idFormaPagamentoAux' id='idFormaPagamentoAux' class='span12'>
								</select>
							</div>
							
							<div class="span4">
								<label class="campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.prazoCotacao" /></label>
								<input id="prazoCotacaoAux" name="prazoCotacaoAux" size="10" maxlength="10" type="text" class="Format[Data] span='12' text datepicker " />
							</div>
							
							<div class="span4">
								<label class="campoObrigatorio"><fmt:message key="requisicaoViagem.prazoCotacaoHora" /></label>
								<input id="horaCotacaoAux" name="horaCotacaoAux" size="10" class="span12" maxlength="10" type="text" onblur="validaHoras(this)"/>
							</div>	
						</div>
								
						<div class='row-fluid'>
							<div class="span4">
								<label><fmt:message key="itemControleFinanceiroViagem.valorUnitario" /></label>
								<input type='text' class="Format[Moeda] span12" id="valorUnitarioAux" name="valorUnitarioAux" maxlength="8" onblur="document.formItem.fireEvent('efetuarCalculo');" />
							</div>
							
							<div class="span4">
								<label><fmt:message key="itemControleFinanceiroViagem.quantidade" /></label> 
								<input type='text' id="quantidadeAux" name="quantidadeAux" class="Format[Numero] span12" maxlength="8" onblur="document.formItem.fireEvent('efetuarCalculo');" />
							</div>
							
							<div class="span4">
								<label id="nomeAdiantamento" style="display: none"><fmt:message key="itemControleFinanceiroViagem.adiantamento" /></label>
								<label id="nomeValorTotal"><fmt:message key="coletaPreco.preco" /></label>
								<input type='text' id="valorAdiantamentoAux" name="valorAdiantamentoAux" class="Format[Moeda] span12" maxlength="20" readonly="readonly" />
							</div>
						</div>
						
						<div class='row-fluid'>
							<div class="span4" id="div_assento">
								<label><fmt:message key="itemControleFinanceiroViagem.assento" /></label>
								<input type='text' class="span12" id="assentoAux" name="assentoAux" maxlength="20" />
							</div>

							<div class="span4" id="div_tipoPassagem">
								<label><fmt:message key="itemControleFinanceiroViagem.tipoPassagem" /></label>
								<select name='tipoPassagemAux' id='tipoPassagemAux' class="span12">
									<option><fmt:message key="itemControleFinanceiroViagem.ida" /></option>
									<option><fmt:message key="itemControleFinanceiroViagem.idaevolta" /></option>
									<option><fmt:message key="itemControleFinanceiroViagem.remarcacao" /></option>
									<option><fmt:message key="itemControleFinanceiroViagem.volta" /></option>
								</select>
							</div>
							
							<div class="span4" id="div_localizador">
								<label><fmt:message key="itemControleFinanceiroViagem.localizador" /></label>
								<input type='text' id="localizadorAux" name="localizadorAux" maxlength="50" class="span12"/>
							</div>
						</div>
						
						<div class='row-fluid'>
							<div class='span12'>
								<label><fmt:message key="avaliacaoFonecedor.observacao" /></label>
								<textarea id="observacao" name="observacao" class="span12" float: left;"></textarea>
							</div>	
						</div>
						
						<div class='row-fluid'>
							<div class="span12">
								<label class="strong">Atribuir ao(s) integrante(s):</label>
							</div>
						</div>
						
						<div class='row-fluid'>
							<div class="span12">
								<div>
									<label class="checkbox inline"><input type="checkbox"> Todos</label>
								</div>
								<div>
									<label class="checkbox inline"><input type="checkbox"> Gilberto Tavares Nery</label>
								</div>
								<div>
									<label class="checkbox inline"><input type="checkbox"> Vania Padilha (Não Funcionario)</label>
								</div>
								
							</div>
						</div>
						
						<br />
						
						<div class="row-fluid">
							<div class="span12">
								<button type='button' name='btnAdicionarItem' class="lFloat btn btn-icon btn-primary" onclick='gravarItens();'>
									<i></i><fmt:message key="citcorpore.comum.gravar" />
								</button>
								
								<button type='button' name='btnFechar' class="lFloat btn btn-icon btn-primary" onclick='fecharFrameItemControleFinanceiro();'>
									<i></i><fmt:message key="citcorpore.comum.fechar" />
								</button>
							</div>
						</div>
					</div>
				</div>
			</div><!-- #POPUP_ITEMCONTROLEFINANCEIRO -->
			
			<div id="autorizacao-viagem-container">
				<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true">
					<div class="widget-head">
						<h3 class="heading">Autorizar viagem</h3>
					</div><!-- .widget-head -->
					<div class="widget-body">
						<div class="widget-body-inner">
							<div class="row-fluid box-generic">
								<div class="span6">
									<strong>Moeda: </strong>Real
								</div><!-- .span6 -->
								<div class="span6 right">
									<strong>Valor total da viagem: </strong>R$ 1000,00
								</div><!-- .span6 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid" id="autorizacao-container">
								<div class="span2">
									<label class="strong">Autorização</label>
									<div>
										<label class="radio"><input type="radio" class="radio-autorizacao" name="autorizacao" id="autorizado" value="1" /> Autorizado</label>
									</div>
									<div>
										<label class="radio"><input type="radio" class="radio-autorizacao" name="autorizacao" id="nao_autorizado" value="0" /> Não autorizado</label>
									</div>
								</div><!-- .span2 -->
								
								<div class="span10 box-generic" id="autorizacao-justificativa-container">
									<div class="span4">
										<label class="strong">Justificativa</label>
										<select class="span12" name="justificativa_nao_autorizacao" id="justificativa_nao_autorizacao">
											<option>-- Selecione --</option>
										</select>
									</div><!-- .span4 -->
									
									<div class="span8">
										<label class="strong">Complemento justificativa</label>
										<textarea class="span12" name="complemento_justificativa_nao_autorizacao" id="complemento_justificativa_nao_autorizacao"></textarea>
									</div><!-- .span8 -->
								</div><!-- .span10 -->
							</div><!-- .row-fluid -->
						</div><!-- .widget-body-inner -->
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- #autorizacao-viagem-container -->
			
			<div id="controle-financeiro-viagem-container">
				<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true">
					<div class="widget-head">
						<h3 class="heading">Controle financeiro da viagem</h3>
					</div><!-- .widget-head -->
					<div class="widget-body">
						<div class="widget-body-inner">
							<div class="row-fluid">
								<div class="span12">
									<label class="strong">Moeda</label>
									<select class="span4">
										<option>Real</option>
									</select>
								</div><!-- .span12 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid" id="integrantes-list-container">
								<div class="widget widget-hide-button-collapse">
									<div class="widget-head">
										<h3 class="heading">Integrante(s) da viagem</h3>
									</div><!-- .widget-head -->
									<div class="widget-body">
										<div class="row-fluid">
											<div class="span12">
												<ul class="filetree treeview browser">
												    <li>
												    	<span class='folder'>Gilberto Tavares Nery</span>
												        <ul>
												            <li>
												            	<div class='file'>
													            	<table class='table_integrante_controle'>
													            		<tr>
													            			<td width="5%"><span class="glyphicons airplane"><i></i>&nbsp;</span></td>
													            			<td width="50%">Passagem aerea</td>
													            			<td width="40%">200,00</td>
													            		</tr>
													            		<tr>
													            			<td width="5%"><span class="glyphicons building"><i></i>&nbsp;</span></td>
													            			<td width="50%">Hospedagem</td>
													            			<td width="40%">400,00</td>
													            		</tr>
													            		<tr>
													            			<td width="5%"></td>
													            			<td width="50%">Valor total</td>
													            			<td width="40%">600,00</td>
													            		</tr>
													            	</table>
												            	</div><!-- .file -->
												            </li>
												        </ul>
												    </li>
												</ul>
											</div><!-- .span12 -->						
										</div><!-- .row-fluid -->
										
										<div class="row-fluid">
											<div class="span12">
												<ul class="filetree treeview browser">
												    <li>
												    	<span class='folder'>Vania Padilha (Não funcionario)</span>
												        <ul>
												            <li>
												            	<div class='file'>
													            	<table class='table_integrante_controle'>
													            		<tr>
													            			<td width="5%"><span class="glyphicons airplane"><i></i>&nbsp;</span></td>
													            			<td width="50%">Passagem aerea</td>
													            			<td width="40%">350,00</td>
													            		</tr>
													            		<tr>
													            			<td width="5%"><span class="glyphicons building"><i></i>&nbsp;</span></td>
													            			<td width="50%">Hospedagem</td>
													            			<td width="40%">550,00</td>
													            		</tr>
													            		<tr>
													            			<td width="5%"></td>
													            			<td width="50%">Valor total</td>
													            			<td width="40%">900,00</td>
													            		</tr>
													            	</table><!-- .table_integrante_controle -->
												            	</div><!-- .file -->
												            </li>
												        </ul>
												    </li>
												</ul>
											</div><!-- .span12 -->							
										</div><!-- .row-fluid -->
									</div><!-- .widget-body -->
								</div><!-- .widget -->
							</div><!-- #integrantes-list-container -->
						</div><!-- .widget-body-inner -->
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- #controle-financeiro-viagem-container -->
			
			<div id="execucao-compra-viagem-container">
				<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true">
					<div class="widget-head">
						<h3 class="heading">Execução de compra da viagem</h3>
					</div><!-- .widget-head -->
					<div class="widget-body">
						<div class="widget-body-inner">
							<div class="row-fluid">
								<div class="span12">
									<strong>Confirma compra dos itens</strong> 
									<label class="checkbox inline ckeckbox-label-inline">
										<input type="checkbox" name="confirma_compra_itens" id="confirma_compra_itens" value="S" /> Sim
									</label>
								</div><!-- .span12 --> 
							</div><!-- .row-fluid -->
						</div><!-- .widget-body-inner -->
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- #execucao-compra-viagem-container -->
			
			<div id="adiantamento-viagem-container">
				<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true">
					<div class="widget-head">
						<h3 class="heading">Adiantamento</h3>
					</div><!-- .widget-head -->
					<div class="widget-body">
						<div class="widget-body-inner">
							<div class="row-fluid">
								<div class="span12">
									<strong>Confirma adiantamento</strong> 
									<label class="checkbox inline ckeckbox-label-inline">
										<input type="checkbox" name="confirma_adiantamento" id="confirma_adiantamento" value="S" /> Sim
									</label>
								</div><!-- .span12 -->
							</div><!-- .row-fluid -->
						</div><!-- .widget-body-inner -->
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- #adiantamento-viagem-container -->
			
			<div id="prestacao-contas-viagem-container">
				<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true">
					<div class="widget-head">
						<h3 class="heading">Prestação de contas da viagem</h3>
					</div><!-- .widget-head -->
					<div class="widget-body">
						<div class="widget-body-inner">
							<div class="row-fluid">
								<div class="span6">
									<strong>GILBERTO TAVARES NERY</strong>
								</div><!-- .span6 -->
								<div class="span6 right">
									<strong>Valor total adiantado: R$ 1000,00</strong>
								</div><!-- .span6 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid" id="itens-prestacao-contas-container">
								<div class="widget widget-hide-button-collapse">
									<div class="widget-head">
										<h3 class="heading">Itens prestação contas viagem</h3>
									</div><!-- .widget-head -->
									<div class="widget-body">
										<div class="row-fluid">
											<div class="span6">
												<label class="strong">Fornecedor</label>
												<input type="text" class="span12" />
											</div><!-- .span6 -->
											<div class="span3">
												<label class="strong">Nº nota fiscal</label>
												<input type="text" class="span12" />
											</div><!-- .span3-->
											<div class="span3">
												<label class="strong">Data</label>
												<input type="text" class="span12" />
											</div><!-- .span3 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid">
											<div class="span3">
												<label class="strong">Valor</label>
												<input type="text" class="span12" />
											</div><!-- .span3 -->
											<div class="span9">
												<label class="strong">Fornecedor</label>
												<input type="text" class="span12" />
											</div><!-- .span9 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid">
											<div class="span12">
												<button type="button" class="btn btn-small btn-primary btn-icon glyphicons circle_plus">
													<i></i> Adicionar
												</button>
											</div><!-- .span12 -->
										</div><!-- .row-fluid -->
									</div><!-- .widget-body -->
								</div><!-- .widget -->
							</div><!-- #itens-prestacao-contas-container -->
							
							<div class="row-fluid" id="integrantes-container">
								<div class="widget widget-hide-button-collapse">
									<div class="widget-head">
										<h3 class="heading">Integrante(s) da viagem</h3>
									</div><!-- .widget-head -->
									<div class="widget-body">
										<div class="row-fluid">
											<div class="span12">
												<label class="radio inline strong">
													<input type="radio" name="integrante" id="funcionario" class="radio-integrante" value="1" /> Funcionário
												</label>
												<label class="radio inline strong">
													<input type="radio" name="integrante" id="nao_funcionario" class="radio-integrante" value="0" /> Não funcionário
												</label>
											</div><!-- .span12 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid">
											<div class="span6">
												<label class="strong">Nome funcionário</label>
												<input type="text" class="span12" id="nome_funcionario" name="nome_funcionario" />
											</div><!-- .span6 -->
											<div class="span6">
												<label class="strong">Nome do responsável pela prestação de contas</label>
												<input type="text" class="span12" id="nome_responsavel_prest_contas" name="nome_responsavel_prest_contas" />
											</div><!-- .span6 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid" id="info-complementares-nao-funcionario-container">
											<div class="span12">
												<label class="strong">Informações complementares não funcionário</label>
												<textarea class="span12" id="info_complementares_nao_funcionario" name="info_complementares_nao_funcionario"></textarea>
											</div><!-- .span12 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid">
											<div class="span12">
												<button id="btn-add-integrante" type="button" class="btn btn-small btn-primary btn-icon glyphicons circle_plus">
													<i></i> Adicionar
												</button>
											</div><!-- .span12 -->
										</div><!-- .row-fluid -->
										
										<div class="row-fluid" id="table-integrantes-container">
											<table class="table table-bordered table-striped table-hover" id="table_integrantes">
												<thead>
													<tr>
														<th>Fornecedor</th>
														<th>Fatura</th>
														<th>Data</th>
														<th>Valor</th>
														<th class="center">Excluir</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>Tam - S.A</td>
														<td>5216457745456</td>
														<td>22/05/2014</td>
														<td>720</td>
														<td class="center"><a class="btn" href="javascript:;">&times;</a></td>
													</tr>
													
													<tr>
														<td>Restaurante</td>
														<td>5216457745456</td>
														<td>22/05/2014</td>
														<td>500</td>
														<td class="center"><a class="btn" href="javascript:;">&times;</a></td>
													</tr>
												</tbody>
											</table>
										</div><!-- .row-fluid -->
									</div><!-- .widget-body -->
								</div><!-- .widget -->
							</div><!-- #integrantes-container -->
						</div><!-- .widget-body-inner -->
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- #prestacao-contas-viagem-container -->
			
			<div id="conferencia-viagem-container"> 
				<div class="widget" data-toggle="collapse-widget" data-collapse-closed="false">
					<div class="widget-head">
						<h3 class="heading">Conferência da viagem</h3>
					</div><!-- .widget-head -->
					<div class="widget-body">
						<div class="widget-body-inner">
							<div class="row-fluid" id="autorizacao-conferencia-container">
								<div class="span2">
									<label class="strong">Autorização</label>
									<div>
										<label class="radio"><input type="radio" class="radio-autorizacao-conferencia" name="autorizacao_conferencia" id="autorizado_conferencia" value="1" /> Autorizado</label>
									</div>
									<div>
										<label class="radio"><input type="radio" class="radio-autorizacao-conferencia" name="autorizacao_conferencia" id="nao_autorizado_conferencia" value="0" /> Não autorizado</label>
									</div>
								</div><!-- .span2 -->
								
								<div class="span10 box-generic" id="autorizacao-justificativa-conferencia-container">
									<div class="span4">
										<label class="strong">Justificativa</label>
										<select class="span12" name="justificativa_nao_autorizacao_conferencia" id="justificativa_nao_autorizacao_conferencia">
											<option>-- Selecione --</option>
										</select>
									</div><!-- .span4 -->
									
									<div class="span8">
										<label class="strong">Complemento justificativa</label>
										<textarea class="span12" name="complemento_justificativa_nao_autorizacao_conferencia" id="complemento_justificativa_nao_autorizacao_conferencia"></textarea>
									</div><!-- .span8 -->
								</div><!-- .span10 -->
							</div><!-- .row-fluid -->
						</div><!-- .widget-body-inner -->
					</div><!-- .widget-body -->
				</div><!-- .widget -->
			</div><!-- #conferencia-viagem-container -->
		</div><!-- #content -->
	</div><!-- #wrapper -->
	
	<%@include file="/novoLayout/common/include/libRodape.jsp" %>
	<script src="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.js"></script>
	<script src="${ctx}/template_new/js/jqueryTreeview/jquery.cookie.js"></script>
	<script type="text/javascript" src="${ctx}/js/dtree.js"></script>
	<script src="js/RequisicaoViagem2.js"></script>
</body>
</html>