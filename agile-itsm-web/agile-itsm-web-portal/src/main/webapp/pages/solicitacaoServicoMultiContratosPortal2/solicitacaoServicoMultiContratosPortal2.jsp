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
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="css/solicitacaoServicoMultiContratosPortal2.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">
			
				<% if(iframe == null) { %>
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
				<% } %>
				
			</div>
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<div id="content <%=(iframe == null) ? "contentframe" : "" %>">				
					<div class="separator top"></div>	
					<div class="row-fluid">
						<div class="innerLR">							
							<form class="form-horizontal" id='form' name='form' action='${ctx}/pages/solicitacaoServicoMultiContratosPortal2/solicitacaoServicoMultiContratosPortal2'>		
								<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
								<input type='hidden' name='idSolicitante' id='idSolicitante' />
								<input type='hidden' name='impacto' id='impacto' />	
								<input type='hidden' name='urgencia' id='urgencia' />		
								<input type='hidden' name='idTipoDemandaServico' id='idTipoDemandaServico' />
								<input type='hidden' name='idUnidade' id='idUnidade' />
								<input type='hidden' name='idServico' id='idServico' />
								<input type='hidden' name='idContrato' id='idContrato' />
								<input type='hidden' name='situacao' id='situacao' value='EmAndamento'/>	
								<input type='hidden' name='registroexecucao' id='registroexecucao' value=''/>
								<input type='hidden' name='nomecontato' id='nomecontato' value=''/>
								<input type='hidden' name='emailcontato' id='emailcontato' value=''/>
								<input type='hidden' name='telefonecontato' id='telefonecontato' value=''/>
								<input type='hidden' name='idOrigem' id='idOrigem' />
								
								<div class='row-fluid'>
									<div class='span6'>
										<div class="control-group">
											<label class="control-label"><fmt:message key="contrato.contrato"/></label>
											<div class="controls">
												<div id="nomeContrato"></div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><fmt:message key="solicitacaoServico.solicitante"/></label>
											<div class="controls">
												<div id="solicitante"></div>
											</div>
										</div>
										<div class="control-group row-fluid">
											<label class="control-label"><fmt:message key="solicitacaoServico.telefoneDoContato"/></label>
											<div class="controls">
												<div id="telefonecontatotxt"></div>
											</div>
										</div>
									</div>
								
									<div class='span6'>
										<div class="control-group">
											<label class="control-label"><fmt:message key="origemAtendimento.origem"/></label>
											<div class="controls">
												<div id="origem"></div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><fmt:message key="solicitacaoServico.emailContato"/></label>
											<div class="controls">
												<div id="emailcontatotxt"></div>
											</div>
										</div>
									</div>
								</div>
								
								<div class="separator line bottom"></div>
								
								<div class="control-group row-fluid">
									<label class="control-label"><fmt:message key="solicitacaoServico.tipo"/> </label>
									<div class="controls">
										<div id="nomeTipoDemandaServico"></div>
									</div>
								</div>
								<div class='row-fluid'>
									<div class="span6">
										<label class="control-label"><fmt:message key="servico.nome"/></label>
										<div class="controls">
											<div id="nomeServico"></div>
										</div>
									</div>
								</div>				
								
								<div class="separator line bottom"></div>
																								
								<div class="control-group row-fluid">
									<label class='control-label'><fmt:message key="solicitacaoServico.descricao"/></label>
									<div class="controls">
										<textarea required="required"  class="wysihtml5 span12" rows="5" id="descricao" name="descricao"></textarea>
									</div>
								</div>
							</form>									
						</div>
						<div id='divBotoes'>
							<button type="button"  class="btn " onclick="cancelar()" data-dismiss="modal"><fmt:message key="citcorpore.comum.cancelar" /></button> 
							<button type="button" data-dismiss="modal" class="btn  btn-primary required " onclick='salvar();' id="btGravar"><fmt:message key="citcorpore.comum.gravar" /></button>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->
				<script type="text/javascript" src="js/solicitacaoServicoMultiContratosPortal2.js"></script>
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
	</body>
</html>