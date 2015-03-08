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
		<link type="text/css" rel="stylesheet" href="css/compraViagem.css" />
		
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
	</head>
	
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	
	<body>
		<div class="nowrapper">
			<!-- Inicio conteudo -->
			<div id="content">
   				<form name='form' action='${ctx}/pages/compraViagem/compraViagem'>
                        <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
						<input type='hidden' name='estado'/>
						<input type='hidden' name='idCidadeOrigem'/>
						<input type='hidden' name='idCidadeDestino'/>
						<input type='hidden' name='confirma'/>
						
						<div class="widget">
							<div class="widget-head">
		                    	<h2 class="heading"><fmt:message key="compraViagem.confirmarCompra" /></h2>
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
												<input id="qtdeDias" name="qtdeDias" size="10" maxlength="10" type="text" readonly="readonly" class="span12"/>
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
											</div><!-- span12 -->
										</div><!-- .row-fluid -->
									</div><!-- widget-body -->
								</div><!-- #infoViagem -->
								
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
							</div><!-- #infoCtrlViagem -->
						
							<div id="confirmaCompra" class="widget" data-toggle="collapse-widget">
							<div class="widget-head">
								<h4 class="heading"><fmt:message key="compraViagem.confirmacaoCompra" /></h4>
							</div><!-- .widget-head -->
							
							<div class="row-fluid">
								<div class="widget-body">
									<div class="span12" >
		                        		<label for="confirmaExec" style='cursor:pointer; margin-top: 1%; padding-left: 10px; font-weight: bold;'><fmt:message key="requisicaoViagem.confimarCompra" />&nbsp
		                                <input type='checkbox' class="inline checkbox" style="font-weight: bold;font-size: medium;" id='confirmaExec' name='confirmaExec' onclick="confirmaExecucao();"/>&nbsp<fmt:message key="citcorpore.comum.sim" /></label>
		
<%-- 		                        		<label for="cancelarRequisicao" style='cursor:pointer; margin-top: 1%; padding-left: 10px; font-weight: bold;'><fmt:message key="requisicaoViagem.cancelarRequisicao"/>&nbsp --%>
<%-- 		                              	<input type="checkbox" class="radio" name="cancelarRequisicao" value="S" id="cancelarRequisicao"/><fmt:message key="citcorpore.comum.sim" /></label> --%>
		                       		</div>
	                       		</div><!-- .widget-body -->
                       		</div><!-- .row-fluid -->
						</div><!-- #confirmaCompra -->
                       	
                       	</div>
                   	</div>                                              
   				</form>
    		</div>
	    </div>
	    <%@include file="/novoLayout/common/include/libRodape.jsp" %>
		<script src="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.js"></script>
		<script src="js/compraViagem.js"></script>
	</body>
</html>
