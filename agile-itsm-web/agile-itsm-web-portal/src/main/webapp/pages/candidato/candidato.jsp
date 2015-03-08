<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
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
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<style type="text/css">
		
		</style>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">
			
				<% if(iframe == null) { %>
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				<% } %>
				
			</div>
	
			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">
					
				<!-- Inicio conteudo -->
				<div id="content">				
					<div class="separator top"></div>	
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="candidato.candidato"/></h4>
								</div>
								<div class="widget-body collapse in">		
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="candidato.cadastro"/></a></li>
											<li><a href="#tab2-3" data-toggle="tab" ><fmt:message key="candidato.pesquisa"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/candidato/candidato'>
												<input type='hidden' name='idCandidato' id='idCandidato' /> 
												<input type='hidden' name='dataInicio' id='dataInicio' /> 
												<input type='hidden' name='dataFim' id='dataFim' /> 
												<input type='hidden' name='situacao' id='situacao'/> 
												
												<div class="row-fluid">
													<div class="span12">
														<div class="span5">
															<label class="campoObrigatorio strong"><fmt:message key="candidato.nomeCompleto" /></label>
															<input id="nome" name="nome" style="width: 100% !important;" type='text' maxlength="150" class="Valid[Required] Description[citcorpore.comum.nome]" />
														</div>
													
														<div class="span3">
															<label class="campoObrigatorio strong"><fmt:message key="colaborador.cpf" /></label>
															<input id="cpf" type="text" name="cpf" style="width: 100% !important;" maxlength="14" class="Valid[Required] Description[colaborador.cpf]"  onclick="cpf()" onblur="validaCpf()"/>
														</div>
														
														<div class="span4">
																<label class="campoObrigatorio strong"><fmt:message key="citcorpore.comum.tipo" /></label>
																	<select name="tipo" id="tipo" class="Valid[Required] Description[citcorpore.comum.tipo]">
																</select>
														</div>
													</div>
												</div>
												<div class="row-fluid">
													<div class="span12">
															<div class="span5">
																<label class="campoObrigatorio strong"><fmt:message key="avaliacaoFonecedor.email" /></label>
																<input id="email" name="email" style="width: 100% !important;" type='text' maxlength="150" class="Valid[Required] Description[avaliacaoFonecedor.email]" onblur="validaEmail()"/>
															</div>
													</div>
												</div>
												<div id="recebeDiv">
													<div id="divSenha">
														<div class="row-fluid">
															<div class="span12">
																	<div class="span2">
																		<label class="campoObrigatorio strong"><fmt:message key="eventoItemConfiguracao.senha" /></label>
																		<input id="senha" name="senha" type="password" maxlength="20" onblur="testaSenha()" />
																	</div>
															
																	<div class="span2">
																		<label class="campoObrigatorio strong"><fmt:message key="alterarSenha.senhaNovamente" /></label>
																		<input id="senha2" name="senha2"  type="password" maxlength="20" onblur="testaSenha()"/>
																	</div>
															</div>
														</div>
													</div>
												</div>
												<div id="divAlterarSenha" style="display: none;">
													<fieldset>
														<label onclick="alterarSenha()" style="cursor: pointer; margin-top: 5px; margin-bottom: 5px;"><img alt="" src="${ctx}/template_new/images/icons/small/util/alterarsenha.png"><fmt:message key="usuario.alterarSenha"/></label>
													</fieldset>
												</div>
												<div class='row-fluid'>
													<div class='span12'>
														<button type='button' name='btnGravar' class="lFloat btn btn-icon btn-primary" onclick='gravar();'>
															<i></i><fmt:message key="citcorpore.comum.gravar" />
														</button>
														<button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='limpar();'>
															<i></i><fmt:message key="citcorpore.comum.limpar" />
														</button>
														<button type='button' name='btnUpDate' class="lFloat btn btn-icon btn-primary" onclick='excluir();'>
															<i></i><fmt:message	key="citcorpore.comum.excluir" />
														</button>
													</div>
											   </div>
											</form>
										</div>
										<div class="tab-pane" id="tab2-3">
											<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
												<form name='formPesquisa'>
													<cit:findField formName='formPesquisa' lockupName='LOOKUP_CANDIDATO' id='LOOKUP_CANDIDATO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
				</div>
				<!--  Fim conteudo-->
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
			</div>
		</div>
		<script src="${ctx}/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
		<script type="text/javascript" src="js/candidato.js"></script>
	</body>
</html>