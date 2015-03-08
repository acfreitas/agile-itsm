<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String iframe = request.getParameter("iframe");
%>
<!doctype html>
<html>
<head>
	<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
	<%@include file="/novoLayout/common/include/titulo.jsp" %>
	<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/jqueryautocomplete.css"/>
	<link type="text/css" rel="stylesheet" href="css/assinatura.css"/>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;" />

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
								<h4 class="heading"><fmt:message key="assinatura.titulo"/></h4>
							</div>
							<div class="widget-body collapse in">
								<div class="tabsbar">
									<ul>
										<li class="active"><a href="#tab1-3" data-toggle="tab"><fmt:message key="assinatura.cadastroAssinatura"/></a></li>
										<li><a href="#tab2-3" data-toggle="tab"><fmt:message key="assinatura.pesquisaAssinatura"/></a></li>
									</ul>
								</div>
								<div class="tab-content">
									<div class="tab-pane active" id="tab1-3">
										<form name='form' action='${ctx}/pages/assinatura/assinatura'>
											<input type='hidden' name='idAssinatura'/>
											<input type='hidden' name='dataInicio'/>

											<div class='row-fluid'>
												<div class='span8'>
													<label><fmt:message key="citcorpore.comum.responsavel" /></label>
													<input type='text' name='nomeResponsavel' id='nomeResponsavel' class="span12" onkeypress='onkeypressNomeResponsavel();'/>
													<input type='hidden' name='idEmpregado' id='idEmpregado' value='0'/>
												</div>
											</div>

											<div class='row-fluid'>
												<div class='span4'>
													<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.fase" /> (<fmt:message key="assinatura.fase.escolhaOuDigite" />)</label>
													<input type='text' id="fase" name="fase" class="span12 Valid[Required] Description[citcorpore.comum.fase]" list=listaFases>
													<datalist id=listaFases>
													</datalist>
												</div>

												<div class='span4'>
													<label class="strong campoObrigatorio"><fmt:message key="citcorpore.comum.papel" /> (<fmt:message key="assinatura.papel.escolhaOuDigite" />)</label>
													<input type='text' id="papel" name="papel" class="span12 Valid[Required] Description[citcorpore.comum.papel]" list=listaPapeis>
													<datalist id=listaPapeis>
													</datalist>
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
												<cit:findField formName='formPesquisa' lockupName='LOOKUP_ASSINATURA' id='LOOKUP_ASSINATURA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
		</div>
	</div>

	<%@include file="/novoLayout/common/include/rodape.jsp" %>

	<script src="${ctx}/novoLayout/common/include/js/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="js/assinatura.js"></script>
</body>
</html>
