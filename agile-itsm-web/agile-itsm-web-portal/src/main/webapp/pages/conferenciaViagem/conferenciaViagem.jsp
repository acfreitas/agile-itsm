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
		<link type="text/css" rel="stylesheet" href="css/conferenciaViagem.css" />
		
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
	    <script type="text/javascript" src="${ctx}/cit/objects/PrestacaoContasViagemDTO.js"></script>
	</head>
	
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	
	<body>
		<div class="nowrapper">
			<!-- Inicio conteudo -->
			<div id="content">
					<form id="form" name="form" action='${ctx}/pages/conferenciaViagem/conferenciaViagem'>
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
					<input type='hidden' name='idEmpregado' id='idEmpregado' /> 
					<input type='hidden' name='idPrestacaoContasViagem' id='idPrestacaoContasViagem' /> 
					<input type='hidden' name='itensPrestacaoContasViagemSerialize' id="itensPrestacaoContasViagemSerialize"/>
					<input type='hidden' name='idEmpregado' id="idEmpregado"/>
					<input type='hidden' name='situacao' id='situacao'/>
					<input type='hidden' name='listItens' id='listItens'/>
					<input type='hidden' name='idResponsavel' id='idResponsavel'/>
					<input type='hidden' name='idAprovacao' id='idAprovacao'/>
					<input type='hidden' name='idContrato' id='idContrato'/>
					<input type='hidden' name='idTarefa' id='idTarefa'/>
					
					<div class="widget">
						<div class="widget-head">
							<h4 class="heading"><fmt:message key="prestacaoContas.aprovarPrestarConta"/></h4>
						</div><!-- .widget-head -->
						
					<div class="widget-body">
					<div id="infoViagem" class="widget row-fluid" data-toggle="collapse-widget" data-collapse-closed="true">
						<div class="widget-head">
							<h4 class="heading"><fmt:message key="requisicaoViagem.dadosGerais"/></h4>
						</div><!-- .widget-head -->
						
						<div class="widget-body collapse">
							<div class="row-fluid">
								<div class="span6">
									<label for="nomeCidadeOrigem" class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.origem"/></label>
									<input type="text" id="nomeCidadeOrigem" name="nomeCidadeOrigem" class="Valid[Required] Description[requisicaoViagem.cidadeOrigem] span12" disabled="disabled" size="10" maxlength="10" />
								</div><!-- .span6 -->
								<div class="span6">
									<label for="nomeCidadeDestino" class="strong campoObrigatorio"><fmt:message key="importmanager.destino"/></label>
									<input type="text" id="nomeCidadeDestino" name="nomeCidadeDestino" class="Valid[Required] Description[requisicaoViagem.cidadeDestino] span12" disabled="disabled" size="10" maxlength="10" />
								</div><!-- .span6 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid">
								<div class="span4">
									<label for="dataInicioViagem" class="strong campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.ida"/></label>
									<input id="dataInicioViagem" name="dataInicioViagem" size="10" maxlength="10" type="text" disabled="disabled" class="Format[Date] Description[citcorpore.comum.datainicio] Valid[Date] text datepicker span12" onchange="calcularQuantidadeDias()" />
								</div><!-- .span4 -->

								<div class="span4">
									<label for="dataFimViagem" class="strong campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.volta"/></label>
									<input id="dataFimViagem" name="dataFimViagem" size="10" maxlength="10" type="text" disabled="disabled" class="Format[Date] Description[citcorpore.comum.datafim] Valid[Date] text datepicker span12" onchange="calcularQuantidadeDias()" />
								</div><!-- .span4 -->

								<div class="span4">
									<label for="qtdeDias" class="strong campoObrigatorio"><fmt:message key="requisicaoViagem.quantidadeDias"/></label>
									<input id="qtdeDias" name="qtdeDias" size="10" maxlength="10" type="text" disabled="disabled" readonly="readonly" class="span12"/>
								</div><!-- .span4 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid">
								<div class="span4">
									<label for="idCentroCusto" class="strong campoObrigatorio"><fmt:message key="centroResultado"/></label>
									<select id='idCentroCusto' name='idCentroCusto' disabled="disabled" class="Valid[Required] Description[centroResultado.custo] span12"></select>
								</div><!-- .span4 -->

								<div class="span4">
									<label for="idProjeto" class="strong campoObrigatorio"><fmt:message key="requisicaoProduto.projeto"/></label>
									<select name='idProjeto' class="Valid[Required] Description[requisicaoProduto.projeto] span12" disabled="disabled"></select>
								</div><!-- .span4 -->

								<div class="span4">
									<label for="idMotivoViagem" class="strong campoObrigatorio"><fmt:message key="requisicaoViagem.justificativa"/></label>
									<select name='idMotivoViagem' class="Valid[Required] Description[requisicaoViagem.justificativa] span12" disabled="disabled"></select>
								</div><!-- .span4 -->
							</div><!-- .row-fluid -->
							
							<div class="row-fluid">
								<div class="span12">
									<label for="descricaoMotivo" class="strong campoObrigatorio"><fmt:message key="requisicaoViagem.motivo"/></label>
									<textarea name="descricaoMotivo" id="descricaoMotivo" cols='200' rows='5' maxlength = "2000" class="span12" disabled="disabled"></textarea>
								</div><!-- span12 -->
							</div><!-- .row-fluid -->
						</div><!-- widget-body -->
					</div><!-- #infoViagem -->
					
					<div id="infoPrestacaoContas" class="widget" data-toggle="collapse-widget">
						<div class="widget-head">
							<h4 class="heading" id="nomeIntegranteViagem"></h4>
						</div><!-- .widget-head -->
						
						<div class="widget-body">
						
							<div class="row-fluid">
								<div id="divCustoTotal" class="span12">
									<label id="valorTotalViagem" style="float: right; margin-right: 18px;"></label>
								</div>
							</div>	
								
                            <div class="row-fluid">
                                   <div class="span4">
                                         <label class="strong">Valor a prestar contas</label>
                                         <input type="text" name="totalPrestacaoContas" id="totalPrestacaoContas" readonly="true" class="Format[Moeda] span12" />
                                   </div><!-- .span4 -->
                                   
                                   <div class="span4">
                                         <label class="strong">Total dos lançamento</label>
                                         <input type="text" name="totalLancamentos" id="totalLancamentos" readonly="true" class="Format[Moeda] span12" />
                                   </div><!-- .span4 -->
                                   
                                   <div class="span4">
                                         <label class="strong">Diferença</label>
                                         <input type="text" name="valorDiferenca" id="valorDiferenca" readonly="true" class="Format[Moeda] span12" />
                                   </div><!-- .span4 -->
                            </div><!-- .row-fluid -->
                                  
                            
                            <div id="itensPrestacaoContas" class="widget widget-hide-button-collapse">   
                               <div class="widget-head">
                                     <h3 class="heading">Itens</h3>
                               </div><!-- .widget-head -->
                               
                               <div class="widget-body">
                               	  <div class="row-fluid">
                               	  	<div class="span12">
	                                  <table class="table table-hover table-bordered table-striped" id="tabelaItemPrestacaoContasViagem">
	                                    <tr>
	                                       <th style="width: 9%;"><fmt:message  key="itemPrestacaoContasViagem.numeroNota" /></th>
	                                       <th style="width: 7%;"><fmt:message  key="itemPrestacaoContasViagem.data" /></th>
	                                       <th style="width: 10%; text-align: center;"><fmt:message  key="itemPrestacaoContasViagem.nomeFornecedor" /></th>
	                                       <th style="width: 7%;"><fmt:message  key="citcorpore.comum.valor" /></th>
	                                       <th style="width: 31%;"><fmt:message  key="citcorpore.comum.descricao" /></th>
	                                    </tr>
	                                  </table>
                                  	</div>
                                  </div>
                              </div><!-- .widget-body -->
							</div><!-- #itensPrestacaoContas -->
							
						</div><!-- .widget-body -->
					</div><!-- #infoPrestacaoContas -->
					
					<div id="divAprovacao" class="widget" data-toggle="collapse-widget">
						<div class="widget-head">
							<h4 class="heading" id="integrante"><fmt:message key="prestacaoContas.aprovarPrestacaoConta" /></h4>
						</div><!-- .widget-head -->
						
						<div class="widget-body">
							<div class="row-fluid">
				               <div class="span2" >
			                       <label style='cursor:pointer' class='radio strong'>
	                       	   	      <input type='radio' name="aprovado" value="S" onclick='configuraJustificativa("S");'  checked="checked"><fmt:message key="citcorpore.comum.aprovada"/>
	                       	   	   </label>
			                       <label style='cursor:pointer' class='radio strong'>
			                       	  <input type='radio' name="aprovado" value="N"  onclick='configuraJustificativa("N");' ><fmt:message key="citcorpore.comum.naoAprovada"/>
			                       </label>
				               </div>
				               <div id="divJustificativa">
				                   <div class="span4">
				                        <fieldset>
				                            <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.justificativa" /></label>
				                            <div>
				                                <select id='idJustificativaAutorizacao' class="span12" name='idJustificativaAutorizacao'></select>
				                            </div>
				                        </fieldset>
				                   </div>
				                   <div class="span6">
				                       <fieldset>
				                           <label class="campoObrigatorio"><fmt:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
				                           <div>
				                                <textarea id="complemJustificativaAutorizacao" name="complemJustificativaAutorizacao" class="span12"></textarea>                               
				                           </div>
				                       </fieldset>
				                   </div>
				               </div>
				           </div> 
						</div><!-- .widget-body -->
					</div><!-- #divAprovacao -->
					
					</div><!-- #widget-body -->
				</div><!-- #widget -->
					
				</form>
	   		</div><!-- #content -->
	    </div><!-- #nowrapper -->
	    
	    <%@include file="/novoLayout/common/include/libRodape.jsp" %>
		<script src="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.js"></script>
		<script src="js/conferenciaViagem.js"></script>
	
	</body>
</html>
