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
		<link type="text/css" rel="stylesheet" href="css/cadastroConexaoBI.css"/>
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
							<form class="form-horizontal" id='form' name='form' action='${ctx}/pages/cadastroConexaoBI/cadastroConexaoBI'>		
									<input type="hidden" id="idConexaoBI" name="idConexaoBI" />
									<input type="hidden" id="status" name="status" value="" />
									<input type="hidden" id="caminhoPastaLog" name="caminhoPastaLog" />
									<input type='hidden' id='idProcessamentoBatchEspecifico' name='idProcessamentoBatchEspecifico' />
									<input type='hidden' id='idProcessamentoBatchExcecao' name='idProcessamentoBatchExcecao' />
									<div class='row-fluid' id="nomeCliente">
											<div class="span10">
												<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.nome"/></label>
												<input type="text" class="Valid[Required] Description[Nome] span9" name="nome" id="nome" maxlength="50" autofocus="autofocus"/>
											</div>
									</div>
									<div id="divConexao">
										<h5><fmt:message key="bi.painelControle.conexao.conexaoBI"/></h5>
									 	<div class='row-fluid'>	
									 			<div class="span6">
													<label class="strong"><fmt:message key="Citsmart.comum.link"/></label>
													<input type="text" class="span9" name="link" id="link" maxlength="50"/>
												</div>			
												<div class="span6">
													<label class="strong"><fmt:message key="bi.painelControle.conexao.login"/></label>
													<input type="text" class="span9" name="login" id="login" maxlength="30" />
												</div>
										</div>		
										<div class='row-fluid'>	
												<div class="span6">
													<label class="strong"><fmt:message key="bi.painelControle.conexao.senha"/></label>
													<input type="password" class="span6" name="senha" id="senha" maxlength="30"/>
												</div>
										</div>	
									</div>			
										<div class='row-fluid'>		
												<div class="span6">
													<label class="strong"><fmt:message key="bi.painelControle.conexao.emailNotificacao"/></label>
													<input type="text" class="span8" name="emailNotificacao" id="emailNotificacao" maxlength="70"/>
												</div>
												<div class="span6">
													<label class="strong campoObrigatorio"><fmt:message key="bi.painelControle.conexao.tipoIntegracao"/></label>
													<label class='importacaoLabel'><div class='importacao' id='importacao'><input class='radio' type='radio' value='A' name='tipoImportacao' id='tipoImportacaoAuto' checked='checked'></div><fmt:message key="citcorpore.comum.automatica"/></label>
													<label class='importacaoLabel'><div class='importacao' id='importacao'><input class='radio ' type='radio' value='M' name='tipoImportacao' id='tipoImportacaoManual' ></div><fmt:message key="citcorpore.comum.manual"/></label>
												</div> 
										</div>
									
							</form>									
						</div>
						<div id="divBotoes">
							<button type="button" data-dismiss="modal" class="btn  btn-primary required " onclick='salvar();' id="btGravar"><fmt:message key="citcorpore.comum.gravar" /></button>
							<button type="button"  class="btn" onclick="cancelar()" data-dismiss="modal"><fmt:message key="citcorpore.comum.cancelar" /></button> 
						<%--<button type="button" name="btnLimpar" class="btn" onclick="document.form.clear();">
								<span><fmt:message key="citcorpore.comum.limpar"/></span>
							</button> --%>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->
				<script type="text/javascript" src="js/cadastroConexaoBI.js"></script>
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
	</body>
</html>