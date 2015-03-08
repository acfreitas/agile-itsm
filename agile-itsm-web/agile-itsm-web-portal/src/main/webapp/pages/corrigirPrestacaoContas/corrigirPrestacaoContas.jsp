<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
		<%@include file="/novoLayout/common/include/titulo.jsp"%>
		
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="css/CorrigirPrestacaoContas.css"/>
		
		<%
	        response.setHeader("Cache-Control", "no-cache"); 
	        response.setHeader("Pragma", "no-cache");
	        response.setDateHeader("Expires", -1);
    	%>
    	
	    <script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/EmpregadoDTO.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoViagemDTO.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/DespesaViagemDTO.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/IntegranteViagemDTO.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/PrestacaoContasViagemDTO.js"></script>
    	<script type="text/javascript" src="${ctx}/cit/objects/ItemPrestacaoContasViagemDTO.js"></script>
	</head>
	
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	
	<body>
		<div class="nowwrapper">
			<div id="content" class="content">
				<form name='form' action='${ctx}/pages/prestacaoContasViagem/prestacaoContasViagem'>
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
					<input type='hidden' name='idPrestacaoContasViagem' id='idPrestacaoContasViagem' /> 
					<input type='hidden' name='itensPrestacaoContasViagemSerialize' id="itensPrestacaoContasViagemSerialize"/>
					<input type='hidden' name='idIntegrante' id='idIntegrante'/>
					<input type='hidden' name='idEmpregado' id="idEmpregado"/>
					<input type='hidden' name='idRespPrestacaoContas' id="idRespPrestacaoContas"/>
					<input type='hidden' name='situacao' id='situacao'/>
					<input type='hidden' name='listItens' id='listItens'/>
					<input type='hidden' name='idResponsavel' id='idResponsavel'/>
					<input type='hidden' name='idAprovacao' id='idAprovacao'/>
					<input type='hidden' name='idItemPrestContasViagem' id="idItemPrestContasViagem"/>
					
					<input type='hidden' name='idItemDespesaViagem' id="idItemDespesaViagem"/>
					<input type='hidden' name='idFornecedor' id="idFornecedor"/>

					<input type='hidden' name='integranteSerialize' id="integranteSerialize"/>
					
					<input type='hidden' name='integranteFuncionario' id="integranteFuncionario"/>
					<input type='hidden' name='nomeNaoFuncionario' id="nomeNaoFuncionario"/>
					
					<input type='hidden' name="totalPrestacaoContasAux" id="totalPrestacaoContasAux" />
					<input type='hidden' name="totalLancamentosAux" id="totalLancamentosAux" />
					<input type='hidden' name='valorDiferencaAux' id="valorDiferencaAux"/>
					<input type='hidden' name='retirarValor' id="retirarValor"/>
					
					<input type='hidden' name='rowIndexItemPrestacaoContas' id='rowIndexItemPrestacaoContas' />
					
					<div class="widget">
						<div class="widget-head">
                       		<h2 class="heading"><fmt:message key="prestacaoContasViagem.prestacaoContasViagem" /></h2>
                       	</div><!-- .widget-head -->
                       	
                       	<div class="widget-body">
                       		<div class="widget" data-toggle="collapse-widget">
                            	<div class="widget-head">
                            		<h2 class="heading"><fmt:message key="requisicaoViagem.motivoCorrecao"/></h2>
                            	</div><!-- .widget-head -->
                            	
                            	<div class="widget-body">
                            		<div class="row-fluid">
                            			<strong id="nomeEmpregado" class="span12"></strong>
                            		</div><!-- .row-fluid -->
                            		
                            		<div class="row-fluid">
                            			<textarea id="corrigir" name="corrigir" class="span12" rows="4" cols="200" readonly="readonly"></textarea>
                            		</div><!-- .row-fluid -->
                            	</div><!-- .widget-body -->
                            </div><!-- .widget -->
                            
                            <div id="divPrestacaoContas" class="widget" data-toggle="collapse-widget">
                            	<div class="widget-head">
                            		<h2 class="heading" id="nomeIntegranteViagem"></h2>
                            	</div><!-- .widget-head -->
                            	
                            	<div class="widget-body">
                            		<div class="divCadastro">
                            			<div class="row-fluid">
                            				<div class="span3">
	                            				<label class="campoObrigatorio strong"><fmt:message key="itemPrestacaoContasViagem.numeroDocumento" /></label>
												<input type="text" name="numeroDocumento" id="numeroDocumento" maxlength="50" class="Valid[Required] Description[itemPrestacaoContasViagem.numeroDocumento] span12" />
	                            			</div><!-- .span3 -->
	                            			
	                            			<div class="span3">
	                            				<label class="campoObrigatorio strong"><fmt:message key="citcorpore.comum.data"/></label>
												<input type="text" class="Valid[Required] Format[Date] Valid[Date] datepicker span12" maxlength="0" id="data" name="data" />
	                            			</div><!-- .span3 -->
	                            			
	                            			<div class="span6">
	                            				<label class="campoObrigatorio strong"><fmt:message key="itemPrestacaoContasViagem.nomeFornecedor"/></label>
												<input type='text' name="nomeFornecedor" id="nomeFornecedor" class="span12" maxlength="100" size="100"  />
	                            			</div><!-- .span6 -->
	                            		</div><!-- .row-fluid -->
	                            		
	                            		<div class="row-fluid">
	                            			<div class="span3">
	                            				<label class="campoObrigatorio strong"><fmt:message key="citcorpore.comum.valor" /></label>
												<input  type="text" name="valor" id="valor" maxlength="8" class="Valid[Required] Description[citcorpore.comum.valor] format-money span12"/>
												<input type='hidden' name='valorAux' id="valorAux"/>
	                            			</div><!-- .span3 -->
	                            			
	                            			<div class="span9">
	                            				<label class="campoObrigatorio strong"><fmt:message key="citcorpore.comum.descricao"/></label>
												<input type='text' name="descricao" id="descricao" maxlength="200" class="Description[citcorpore.comum.descricao] span12" />
	                            			</div><!-- .span9 -->
	                            		</div><!-- .row-fluid -->
	                            		
	                            		<div class="row-fluid">
	                            			<div class="span12">
	                            				<button type='button' name='btnGravar' class="light btn btn-primary" onclick="document.form.fireEvent('adicionarItem');">
													<fmt:message key="citcorpore.comum.adicionar" />
												</button>
	                            			</div><!-- .span12 -->
	                            		</div><!-- .row-fluid -->
                            		</div><!-- #divCadastro -->
                            	</div><!-- .widget-body -->
                            </div><!-- #divPrestacaoContas.widget -->
                            
                            <div class="box-generic">
	                        	<div class="row-fluid">
	                        		<div class="span4">
	                        			<label class="strong"><fmt:message key="requisicaoViagem.valorPrestarContas" /></label>
	                        			<input type="text" name="totalPrestacaoContas" id="totalPrestacaoContas" readonly="true" class="Format[Moeda] span12" />
	                        		</div><!-- .span4 -->
	                        		
	                        		<div class="span4">
	                        			<label class="strong"><fmt:message key="requisicaoViagem.totalLancamento" /></label>
	                        			<input type="text" name="totalLancamentos" id="totalLancamentos" readonly="true" class="Format[Moeda] span12" />
	                        		</div><!-- .span4 -->
	                        		
	                        		<div class="span4">
	                        			<label class="strong"><fmt:message key="requisicaoViagem.diferenca" /></label>
	                        			<input type="text" name="valorDiferenca" id="valorDiferenca" readonly="true" class="span12" />
	                        		</div><!-- .span4 -->
	                        	</div><!-- .row-fluid -->
	                        </div><!-- .box-generic -->
                            
                            <div id="widget-itens-prestacao-contas" class="widget" data-toggle="collapse-widget">
	                        	<div class="widget-head">
	                        		<h3 class="heading"><fmt:message key="pedidoCompra.itens" /></h3>
	                        	</div><!-- .widget-head -->
	                        	
	                        	<div class="widget-body">
	                        		<table class="table table-hover table-bordered table-striped" id="tabelaItemPrestacaoContasViagem">
										<tr>
											<th style="width: 9%;"><fmt:message  key="itemPrestacaoContasViagem.numeroNota" /></th>
											<th style="width: 5%;"><fmt:message  key="itemPrestacaoContasViagem.data" /></th>
											<th style="width: 10%; text-align: center;"><fmt:message  key="itemPrestacaoContasViagem.nomeFornecedor" /></th>
											<th style="width: 5%;"><fmt:message  key="citcorpore.comum.valor" /></th>
											<th style="width: 25%;"><fmt:message  key="citcorpore.comum.descricao" /></th>
											<th style="width: 10%;"><fmt:message  key="rh.acoes" /></th>
										</tr>
									</table>
	                        	</div><!-- .widget-body -->
	                        </div><!-- #widget-itens-prestacao-contas.widget -->
                       	</div><!-- .widget-body -->
					</div><!-- .widget -->
				</form>
			</div><!-- #content -->
		</div><!-- .nowwrapper -->
		
		<%@include file="/novoLayout/common/include/libRodape.jsp" %>
		<script src="js/jquery.maskMoney.js"></script>
		<script src="js/CorrigirPrestacaoContas.js"></script>
	</body>
</html>