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
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
		<link type="text/css" rel="stylesheet" href="css/grupoAssinatura.css"/>
		
		<script src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>
		<script type="text/javascript" src="${ctx}/cit/objects/ItemGrupoAssinaturaDTO.js"></script>
		
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
									<h4 class="heading"><fmt:message key="grupoAssinatura.titulo"/></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="grupoAssinatura.cadastroGrupoAssinatura"/></a></li>
											<li><a href="#tab2-3" data-toggle="tab" ><fmt:message key="grupoAssinatura.pesquisaGrupoAssinatura"/></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form name='form' action='${ctx}/pages/grupoAssinatura/grupoAssinatura'>
												
												<input type='hidden' name='idGrupoAssinatura'/>
												<input type='hidden' name='dataInicio'/>
												<input type="hidden" name="tblAssinaturas_serialize" id="tblAssinaturas_serialize" />
												
												<div class='row-fluid'>
													<div class='span8'>
														<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.titulo" /></label>
														<input type='text' name='titulo' id='titulo' class="span12 Valid[Required] Description[citcorpore.comum.titulo]" />
													</div>
												</div>
												
												<div class="separator bottom"></div>
												
												<div class='row-fluid'>
													<div class='span12'>
														
														<div class='row-fluid'>
															<div class='spar12'>
																<div class='span5'>
																	<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.assinatura" /></label>
																	<select  id="idAssinatura" name="idAssinatura" class="span12" onchange="alimentarDadosAssinatura();"></select>
																</div>
																<div class='span1'>
																	<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.ordenacao" /></label>
																	<input type='text' name='ordem' id='ordem' class="span12 Format[Numero] text" />
																</div>
																<div class='span1'>
																	<button type='button' name='btnAdd' class="lFloat btn btn-icon btn-primary" onclick='adicionarAssinatura();' style='margin-top: 24px;'>
																		<i></i><fmt:message key="citcorpore.comum.adicionar" />
																	</button>
																</div>
															</div>
														</div>
														
														<table id="tblAssinaturas" name="tblAssinaturas" class="dynamicTable table table-striped table-bordered table-condensed dataTable">
															<tr>
																<th height="10px" width="2%"></th>
																<th height="10px" width="40%"><fmt:message	key="citcorpore.comum.responsavel" /></th>
																<th height="10px" width="25%"><fmt:message	key="citcorpore.comum.papel" /></th>
																<th height="10px" width="25%"><fmt:message	key="citcorpore.comum.fase" /></th>
																<th height="10px" width="8%"><fmt:message	key="citcorpore.comum.ordenacao" /></th>
															</tr>
														</table>
													</div>
												</div>
												
												<div class="separator bottom"></div>
												
												<div class='row-fluid'>
													<div class='span12'>
														<button type='button' name='btnGravar' class="lFloat btn btn-icon btn-primary" onclick='gravar();'>
															<i></i><fmt:message key="citcorpore.comum.gravar" />
														</button>
														<button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='limpar();'>
															<i></i><fmt:message key="citcorpore.comum.limpar" />
														</button>
														<button type='button' name='btnExcluir' class="lFloat btn btn-icon btn-primary" onclick='excluir();'>
															<i></i><fmt:message	key="citcorpore.comum.excluir" />
														</button>
													</div>
											   </div>
											</form>
										</div>
										<div class="tab-pane" id="tab2-3">
											<div class="section"><fmt:message key="citcorpore.comum.pesquisa" />
												<form name='formPesquisa'>
													<cit:findField formName='formPesquisa' lockupName='LOOKUP_GRUPOASSINATURA' id='LOOKUP_GRUPOASSINATURA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
		<script type="text/javascript" src="js/grupoAssinatura.js"></script>
	</body>
</html>