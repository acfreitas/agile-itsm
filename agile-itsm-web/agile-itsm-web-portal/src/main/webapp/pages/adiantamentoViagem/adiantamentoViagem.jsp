<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ProdutoDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="java.util.Collection"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
		<%@include file="/novoLayout/common/include/titulo.jsp"%>
		
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
		<link type="text/css" rel="stylesheet" href="css/AdiantamentoViagem.css" />

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
	</head>
	<body>
		<div class="nowrapper">
			<!-- Inicio conteudo -->
			<div id="content">
				<form name='form' action='${ctx}/pages/adiantamentoViagem/adiantamentoViagem'>
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
					<input type='hidden' name='estado'/>
					<input type='hidden' name='idCidadeOrigem'/>
					<input type='hidden' name='idCidadeDestino'/>
					<input type='hidden' name="confirma" id="confirma" />
					
					<div class="widget">
						<div class="widget-head">
	                    	<h2 class="heading"><fmt:message key="adiantamentoViagem.confirmarAdiantamentoDiarias" /></h2>
	                    </div><!-- .widget-head -->
	                    
	                    <div class="widget-body">
	                    	<div id="infoViagem" class="widget row-fluid" data-toggle="collapse-widget" data-collapse-closed="true">
	                    		<div class="widget-head">
	                    			<h4 class="heading"><fmt:message key="requisicaoViagem.dadosGerais"/></h4>
	                    		</div><!-- .widget-head -->
	                    		
	                    		<div class="widget-body">
	                    			<div class="row-fluid">
	                    				<div class="span6">
		                            		<label for="nomeCidadeOrigem" class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.origem"/></label>
		                            		<input type="text" id="nomeCidadeOrigem" name="nomeCidadeOrigem" class="Valid[Required] Description[requisicaoViagem.cidadeOrigem] span12" size="10" maxlength="10" />
		                            	</div><!-- .span6 -->
		                            	<div class="span6">
		                            		<label for="nomeCidadeDestino" class="strong campoObrigatorio"><fmt:message key="importmanager.destino"/></label>
		                            		<input type="text" id="nomeCidadeDestino" name="nomeCidadeDestino" class="Valid[Required] Description[requisicaoViagem.cidadeDestino] span12" size="10" maxlength="10" />
		                            	</div><!-- .span6 -->
	                    			</div><!-- .row-fluid -->
	                    			
	                    			<div class="row-fluid">
	                    				<div class="span4">
		                            	 	<label for="dataInicioViagem" class="strong campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.ida"/></label>
		                            	 	<input id="dataInicioViagem" name="dataInicioViagem" size="10" maxlength="10" type="text" class="Format[Date] Description[citcorpore.comum.datainicio] Valid[Date] text datepicker span12" onchange="calcularQuantidadeDias()" />
		                            	 </div><!-- .span4 -->
		                            	 <div class="span4">
		                            	 	<label for="dataFimViagem" class="strong campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.volta"/></label>
		                            	 	<input id="dataFimViagem" name="dataFimViagem" size="10" maxlength="10" type="text" class="Format[Date] Description[citcorpore.comum.datafim] Valid[Date] text datepicker span12" onchange="calcularQuantidadeDias()" />
		                            	 </div><!-- .span4 -->
		                            	 <div class="span4">
		                            	 	<label for="qtdeDias" class="strong"><fmt:message key="requisicaoViagem.quantidadeDias"/></label>
		                            	 	<input id="qtdeDias" class="span12" name="qtdeDias" size="10" maxlength="10" type="text" readonly="readonly"/>
		                            	 </div><!-- .span4 -->
	                    			</div><!-- .row-fluid -->
	                    			
	                    			<div class="row-fluid">
	                    				<div class="span4">
		                            		<label for="idCentroCusto" class="strong campoObrigatorio"><fmt:message key="centroResultado"/></label>
		                            	 	<select id='idCentroCusto' name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo] span12"></select>
		                            	</div><!-- .span4 -->
		                            	<div class="span4">
		                            		<label for="idProjeto" class="strong campoObrigatorio"><fmt:message key="requisicaoProduto.projeto"/></label>
		                            	 	<select name='idProjeto' class="Valid[Required] Description[requisicaoProduto.projeto] span12"></select>
		                            	</div><!-- .span4 -->
		                            	<div class="span4">
		                            		<label for="idMotivoViagem" class="strong campoObrigatorio"><fmt:message key="requisicaoViagem.justificativa"/></label>
		                            	 	<select name='idMotivoViagem' class="Valid[Required] Description[requisicaoViagem.justificativa] span12"></select>
		                            	</div><!-- .span4 -->
	                    			</div><!-- .row-fluid -->
	                    			
	                    			<div class="row-fluid">
		                            	<div class="span12">
		                            		<label for="descricaoMotivo" class="strong campoObrigatorio"><fmt:message key="requisicaoViagem.motivo"/></label>
		                            		<textarea class="span12" name="descricaoMotivo" id="descricaoMotivo" cols='200' rows='5' maxlength = "2000"></textarea>
		                            	</div><!-- .span11 -->
		                            </div><!-- .row-fluid -->
	                    		</div><!-- .widget-body -->
	                    	</div><!-- #infoViagem -->
	                    	
	                    	<div id="widgetIntegrantes" class="widget row-fluid" data-toggle="collapse-widget">
	                    		<div class="widget-head">
	                    			<h4 class="heading"><fmt:message key="autorizacaoViagem.custo" /></h4>
	                    		</div><!-- .widget-head -->
	                    		
	                    		<div class="widget-body">
									<div class="row-fluid">
										<div id="divBotoesAcao" class="span6">
											<a href="javascript:;" onclick="expandirItemDespesa();">+ Expandir todos</a> | <a href="javascript:;" onclick="retrairItemDespesa();">- Retrair todos</a>
										</div>
										
										<div id="divCustoTotal" class="span6">
											<label id="valorTotalViagem" style="float: right; margin-right: 18px;"></label>
										</div>
									</div>	
									<div class="row-fluid">
										<div id="despesa-viagem-items-container">
										</div><!-- .widget-body -->
									</div><!-- .row-fluid -->
								</div><!-- .widget-body -->
	                    		
	                    	</div><!-- #widgetIntegrantes -->
	                       	
							<div class="widget row-fluid" data-toggle="collapse-widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="requisicaoViagem.confirmacaoAdiantamento"/></h4>
								</div><!-- .widget-head -->
								
								<div class="widget-body">
									<div class="row-fluid">
										<div class="span12 strong" style="display: none;">
			                        		<fmt:message key="requisicaoViagem.cancelarRequisicao"/>
			                        		<label id="cancelarRequisicaoLabel" class="checkbox inline strong">
			                        			<input type="checkbox" class="checkbox" name="cancelarRequisicao" value="S" id="cancelarRequisicao"/><fmt:message key="citcorpore.comum.sim" />
			                        		</label>
			                        	</div><!-- .span12 -->
			                        </div><!-- .row-fluid -->
			                        
			                        <div class="row-fluid">
			                        	<div class="span12 strong">
			                        	<fmt:message key="requisicaoViagem.confimarAdiantamento"/>?
			                        		<label id="confirmarRequisicaoLabel" class="checkbox inline strong">
			                        			<input type="checkbox" class="checkbox" name="confirmaExec" value="S" id="confirmaExec" onclick="confirmaExecucao();"/><fmt:message key="citcorpore.comum.sim" />
			                        		</label>
			                        	</div><!-- .span12 -->
			                        </div><!-- .row-fluid -->
			                    </div><!-- .widget-body -->
		                    </div><!-- .widget -->
	                    </div><!-- .widget-body -->
					</div><!-- .widget -->
				</form>
			</div><!-- #content -->
		</div><!-- .nowrapper -->
		
		<%@include file="/novoLayout/common/include/libRodape.jsp" %>
		<script src="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.js"></script>
		<script src="js/AdiantamentoViagem.js"></script>
	</body>
</html>