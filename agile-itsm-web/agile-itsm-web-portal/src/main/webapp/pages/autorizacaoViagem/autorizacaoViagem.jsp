<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!DOCTYPE html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
		<%@include file="/novoLayout/common/include/titulo.jsp"%>
		
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
		<link type="text/css" rel="stylesheet" href="css/AutorizacaoViagem.css" />
		
		<%
	        response.setHeader("Cache-Control", "no-cache"); 
	        response.setHeader("Pragma", "no-cache");
	        response.setDateHeader("Expires", -1);
    	%>
	       
	    <script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	    <script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/RequisicaoViagemDTO.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/DespesaViagemDTO.js"></script>
	    <script type="text/javascript" src="${ctx}/cit/objects/IntegranteViagemDTO.js"></script>
	</head>
	
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	
	<body>
		<div class="nowrapper">
			<!-- Inicio conteudo -->
			<div id="content">
				<div id="tabs-2" class="box-generic" style="overflow: hidden;">
   				<form name='form' action='${ctx}/pages/autorizacaoViagem/autorizacaoViagem'>
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                    <input type='hidden' name='editar' id='editar' /> 
                    <input type='hidden' name='acao' id='acao'/> 
                    <input type='hidden' name='detalhes' id='detalhes'/>
                    <input type='hidden' name='itemIndex' id='itemIndex'/>
                    <input type='hidden' name='integranteViagemSerialize' id='integranteViagemSerialize'/>
                    <input type='hidden' name='estado' id='estado'/>
                    <input type='hidden' name='idCidadeOrigem' id='idCidadeOrigem'/>
                    <input type='hidden' name='idCidadeDestino' id='idCidadeDestino'/>
                    <input type='hidden' name='idEmpregado' id='idEmpregado'/>
                    <input type='hidden' name='autorizado' id='autorizado'/>
                    <input type='hidden' name='idContrato' id='idContrato'/>
                    
                    <div class="widget">
                    	<div class="widget-head">
                        	<h2 class="heading"><fmt:message key="autorizacaoViagem.aprovarSolicitacaoViagens" /></h2>
                        </div><!-- .widget-head -->
                        
                        <div class="widget-body">
                        	<div id="infoViagem" class="widget row-fluid" data-toggle="collapse-widget" data-collapse-closed="true">
                        		<div class="widget-head">
			                    	<h4 class="heading"><fmt:message key="requisicaoViagem.dadosGerais" /></h4>
			                    </div><!-- .widget-head -->
			                    
			                    <div class="widget-body collapse">
				                   	<div class="row-fluid">
				                       	<div class="span6" id='divNomeDaCidadeOrigem'>
				                      			<label class="strong campoObrigatorio"><fmt:message key="lookup.origem" /></label>	
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
			                       	 	<input id="dataInicioViagem" name="dataInicioViagem" maxlength="10" type="text" class="Format[Date] Description[Data Inicio] Valid[Date] text datepicker span12" onchange="calcularQuantidadeDias()" />
			                       	 </div><!-- .span4 -->
			                       	 <div class="span4">
			                       	 	<label for="dataFimViagem" class="strong campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.volta"/></label>
			                       	 	<input id="dataFimViagem" name="dataFimViagem" maxlength="10" type="text" class="Format[Date] Description[Data Fim] Valid[Date] text datepicker span12" onchange="calcularQuantidadeDias()" />
			                       	 </div><!-- .span4 -->
			                       	 <div class="span4">
			                       	 	<label for="qtdeDias" class="strong"><fmt:message key="requisicaoViagem.quantidadeDias"/></label>
			                       	 	<input id="qtdeDias" name="qtdeDias" maxlength="10" type="text" readonly="readonly" class="span12"/>
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
			                       		<textarea name="descricaoMotivo" id="descricaoMotivo" cols='200' rows='5' maxlength = "2000" class="span12"></textarea>
			                       	</div><!-- .span12 -->
			                       </div><!-- .row-fluid -->
                      			</div><!-- .widget-body -->	
                        	</div><!-- #infoViagem-->
                        	
                        	<div id="infoCtrlViagem" class="widget" data-toggle="collapse-widget">
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
                        	</div><!-- #infoCtrlViagem-->
                        	
                        	<div id="confirmaAprovacao" class="widget" data-toggle="collapse-widget">
                        		<div class="widget-head">
									<h2 class="heading"><fmt:message key="autorizacaoViagem" /></h2>
								</div><!-- .widget-head -->
								
								<div class="widget-body">
									<div class="row-fluid">		
										<div class="span2" >
			                           		<label class="strong campoObrigatorio"><fmt:message key="itemRequisicaoProduto.parecer"/></label>
					                        <label class="radio" style='cursor:pointer'><input type='radio' name="autorizar" id="autorizarS" value="S" onclick='configuraJustificativa("S");' ><fmt:message key="itemRequisicaoProduto.autorizado"/></label>
					                        <label class="radio" style='cursor:pointer'><input type='radio' name="autorizar" id="autorizarN" value="N" onclick='configuraJustificativa("N");' ><fmt:message key="itemRequisicaoProduto.naoAutorizado"/></label>
			                            </div>	
			                            
			                            <div id="divJustificativa" class="span10">
			                           		<div class="span4">
			                           			<label for="idJustificativaAutorizacao" class="strong campoObrigatorio"><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
									        	<select id='idJustificativaAutorizacao'  name='idJustificativaAutorizacao' class="span12"></select>
			                           		</div>
			                           		<div class="span8">
			                           			<label for="complemJustificativaAutorizacao" class="strong campoObrigatorio"><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
									        	<textarea id="complemJustificativaAutorizacao" name="complemJustificativaAutorizacao"  class="span12"></textarea>
			                           		</div>
			                           	</div>
				 							<!--<div class="row-fluid">
					                            <div class="span12" >
					                        		<label for="cancelarRequisicao" style='cursor:pointer; margin-top: 1%; padding-left: 10px; font-weight: bold;'><fmt:message key="requisicaoViagem.cancelarRequisicao"/>&nbsp
					                              	<input type="checkbox" class="checkbox" name="cancelarRequisicao" value="S" id="cancelarRequisicao"/><fmt:message key="citcorpore.comum.sim" /></label>
					                        	</div>    
			                       			</div>-->
		                       		</div><!-- .row-fluid -->	
		                   		</div><!-- .widget-body -->
                        	</div><!-- #confirmaAprovacao-->
                        </div><!-- .widget-->
                    </div><!-- .widget-->
                </form>
    		</div>
	    </div>
	    <%@include file="/novoLayout/common/include/libRodape.jsp" %>
		<script src="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.js"></script>
		<script src="js/AutorizacaoViagem.js"></script>
	</body>
</html>
