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
		<link type="text/css" rel="stylesheet" href="css/DespesaViagem.css" />
		
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
			<div id="content">
				<form name='form' action='${ctx}/pages/despesaViagem/despesaViagem'>
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
					<input type='hidden' name='idTarefa' id='idTarefa' />
					<input type='hidden' name='estado'/>
					<input type='hidden' name='idCidadeOrigem'/>
					<input type='hidden' name='idCidadeDestino'/>
					<input type='hidden' name='colIntegrantesViagem_Serialize' id='colIntegrantesViagem_Serialize' />
					
					<div class="widget">
                    	<div class="widget-head">
                        	<h2 class="heading" id="titulo"></h2>
                        </div><!-- .widget-head -->
                        
                        <div class="widget-body">
							<div id="divNaoAprovada" class="widget row-fluid" data-toggle="collapse-widget" data-collapse-closed="false">
								<div class="widget-head">
									<h3 class="heading"><fmt:message key="requisicaoViagem.naoAprovada"/></h3>
								</div><!-- .widget-head -->
								
								<div class="widget-body">
									<div id="naoAprovada" class="span12">
									</div>
								</div>
							</div>
                        	
							<div id="infoViagem" class="widget row-fluid" data-toggle="collapse-widget" data-collapse-closed="true">
								<div class="widget-head">
									<h3 class="heading"><fmt:message key="requisicaoViagem.dadosGerais"/></h3>
								</div><!-- .widget-head -->
								
								<div class="widget-body collapse">
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
											<input id="dataInicioViagem" name="dataInicioViagem" size="10" maxlength="10" type="text" class="Format[Date] Description[citcorpore.comum.datainicio] Valid[Date] datepicker span12" onchange="calcularQuantidadeDias()" />
										</div><!-- .span4 -->
		
										<div class="span4">
											<label for="dataFimViagem" class="strong campoObrigatorio"><fmt:message key="itemControleFinanceiroViagem.volta"/></label>
											<input id="dataFimViagem" name="dataFimViagem" size="10" maxlength="10" type="text" class="Format[Date] Description[citcorpore.comum.datafim] Valid[Date] datepicker span12" onchange="calcularQuantidadeDias()" />
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
									<h3 class="heading"><fmt:message key="requisicaoViagem.integrantes" /></h3>
								</div><!-- .widget-head -->
								
								<div class="widget-body">
									<div class="row-fluid">
										<div class="widget widget-hide-button-collapse">
											<div class="widget-head">
												<h3 class="heading"><fmt:message key="despesaViagem.viagensIntegrante" /></h3>
												<span onclick="abrirPopupItemControleFinanceiro();" id="add_itens" name='add_itens' class="lFloat btn btn-icon btn-primary"><fmt:message key="requisicaoViagem.adicionarItens" /></span>
											</div><!-- .widget-head -->
											
											<div class="widget-body">
												<div class="row-fluid">
													<div id="divBotoesAcao" class="span6">
														<a href="javascript:;" onclick="expandirItemDespesa();">+ Expandir todos</a> | <a href="javascript:;" onclick="retrairItemDespesa();">- Retrair todos</a>
													</div>
												</div>
												
												<div class="row-fluid">
													<div id="despesa-viagem-items-container"></div>
												</div>
											</div><!-- .widget-body -->
										</div><!-- .widget -->
									</div><!-- .row-fluid -->
								</div><!-- .widget-body -->
							</div><!-- #infoCtrlViagem -->
							
							<%--
							<div class="row-fluid">
								<div class="span12 strong">
									<fmt:message key="requisicaoViagem.cancelarRequisicao"/>&nbsp;
									
									<label for="cancelarRequisicao" class="checkbox inline strong">
										<input type="checkbox" class="checkbox" name="cancelarRequisicao" value="S" id="cancelarRequisicao"/> <fmt:message key="citcorpore.comum.sim" />
									</label>
								</div><!-- .span12 -->
							</div><!-- .row-fluid --> 
							 --%>
						</div><!-- .widget-body -->
					</div><!-- .widget -->
				</form>
			</div><!-- #content -->
		</div><!-- #nowrapper -->
		
		<div id="POPUP_ITEMCONTROLEFINANCEIRO" name="POPUP_ITEMCONTROLEFINANCEIRO" title="<fmt:message key="requisicaoViagem.despesaViagem"/>">
			<form name='formItem' id='formItem' action='${ctx}/pages/despesaViagem/despesaViagem'>
				<input type='hidden' name='idDespesaViagem' id='idDespesaViagem' />
				<input type='hidden' name='idSolicitacaoServicoAux' id='idSolicitacaoServicoAux' />
				<input type='hidden' name='idFornecedor' id='idFornecedor' />
				<input type='hidden' name='idTipo' id='idTipo' />
				<input type='hidden' name='dataInicio' id='dataInicio' />
				<input type='hidden' name='idRoteiro' id='idRoteiro' />
				<input type='hidden' name='original' id='original' />
				<input type='hidden' name='idMoeda' id='idMoeda' />
				
				<input type='hidden' name='colIntegrantesViagem_SerializeAux' id='colIntegrantesViagem_SerializeAux' />
				
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
							<label for="idMoedaAux" class="strong campoObrigatorio"><fmt:message key="moeda.moeda"/></label>
							<select id="idMoedaAux" name='idMoedaAux' class="Valid[Required] Description[moeda.moeda] span12"></select>
						</div><!-- span3 -->
						
						<div class="span5">
							<label class="campoObrigatorio strong"><fmt:message key="itemControleFinanceiroViagem.formaPagamento" /></label>
							<select name='idFormaPagamento' id='idFormaPagamento' class='span12'></select>
						</div><!-- .span5 -->
						
						<div class="span2">
							<label id="labelPrazoCotacao" class="campoObrigatorio strong"><fmt:message key="despesaViagem.dataCotacaoExpira" /></label>
							<input id="prazoCotacaoAux" name="prazoCotacaoAux" size="10" maxlength="10" type="text" onchange="" onblur="ValidacaoUtils.validaData(this, '');" class="Format[Data] span12 datepicker " />
						</div><!-- .span2 -->
						
						<div class="span2">
							<label id="labelHoraCotacao" class="campoObrigatorio strong"><fmt:message key="despesaViagem.horaCotacaoExpira" /></label>
							<input id="horaCotacaoAux" name="horaCotacaoAux" size="10" class="span12" maxlength="10" type="text" onblur="ValidacaoUtils.validaHora(this, '')"/>
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
							<input type='text' id="valorAdiantamento" name="valorAdiantamento" class="span12" maxlength="20" readonly="readonly" />
						</div><!-- .span4 -->
					</div><!-- .row-fluid -->
					
					<div class='row-fluid'>
						<div class='span12'>
							<label class="strong"><fmt:message key="avaliacaoFonecedor.observacao" /></label>
							<textarea id="observacoes" name="observacoes" class="span12" float: left;"></textarea>
						</div><!-- .span12 -->
					</div><!-- .row-fluid -->
				</div><!-- #form-itens-container -->
				
				<div id="integrantes-viagem-container" class="widget">
					<div class="widget-head">
						<h3 id="integrantes-viagem-heading" class="heading"><fmt:message key="requisicaoViagem.atribuirIntegrante" /></h3>
					</div><!-- .widget-head -->
					<div class="widget-body">
						<ul id="integrantes-itens" class="unstyled">
						</ul>
					</div><!-- .widget-body -->
				</div><!-- .widget -->
				
				<div class='row-fluid'>
					<div class="span12">
						<button type='button' name='btnFechar' class="rFloat btn btn-icon" onclick='fecharPopupItemControleFinanceiro()'>
							<i></i> <fmt:message key="Fechar" />
						</button>
						<button type='button' name='btnAdicionarItem' class="rFloat btn btn-icon btn-primary despesa-viagem-buttom-confirma" onclick='adicionarDespesaViagem();'>
							<i></i> <fmt:message key="citcorpore.comum.confirmar" />
						</button>
					</div><!-- .span12 -->
				</div><!-- .row-fluid -->
			</form>
		</div><!-- #POPUP_ITEMCONTROLEFINANCEIRO -->
		
		<%@include file="/novoLayout/common/include/libRodape.jsp" %>
		<script src="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.js"></script>
		<script src="js/jquery.maskMoney.js"></script>
		<script src="js/DespesaViagem.js"></script>
	</body>
</html>