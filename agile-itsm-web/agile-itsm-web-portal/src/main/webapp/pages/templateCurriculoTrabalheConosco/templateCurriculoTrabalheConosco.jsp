<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/templateCurriculo.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css" />
	</head>
	<body>
		<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="<fmt:message key='citcorpore.comum.aguardeProcessando'/>" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>
		<div class="container-fluid fixed ">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar main hidden-print">
				<%String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>
				<!-- Wrapper -->
				<div class="wrapper clearfix">
					<div class="g-first header-logo-curriculo" id="header-logo">
			  			<h1>
			  				<% if(request.getAttribute("metodoAutenticacao") != null && request.getAttribute("metodoAutenticacao").equals("AD")) { %>
			  				<fmt:message key="rh.atualizarCurriculo"/>
			  				<% } else { %>
			  				<fmt:message key="trabalhe.conosco"/>
			  				<% } %>
			  			</h1>
					</div>


					<!-- Topo Right -->
					<form name='formInternacionaliza' id='formInternacionaliza' class="marginless clearfix" action='${ctx}/pages/internacionalizar/internacionalizar'>
					<input type="hidden" name="locale" id="locale"/>
					<ul class="navbar topnav pull-right">
						<!-- Language menu -->
						<li class="hidden-phone dropdown dd-1 dd-flags" id="lang_nav">
					    <% if (locale.equalsIgnoreCase("pt")) {%>
				    		<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
				    	<%} else {
				    		if (locale.equalsIgnoreCase("en")) {%>
				    			<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/us.png" alt="en"></a>
				    	  <%} else {
				    		    if (locale.equalsIgnoreCase("es")) {%>
				    				<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/es.png" alt="es"></a>
				    		  <%} else {%>
				    			    <a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
				    		  <%}
				    		}
				    	}%>
				    		<ul class="dropdown-menu pull-left">
					    		<li class="active"><a href="javascript:;" title="Portugues" onclick="internacionalizar('')" ><img src="../../novoLayout/common/theme/images/lang/br.png" alt="Portugues"> Português BR</a></li>
					      		<li><a href="javascript:;" title="English" onclick="internacionalizar('en')"><img src="../../novoLayout/common/theme/images/lang/us.png" alt="English"> English</a></li>
					      		<li><a href="javascript:;" title="Español" onclick="internacionalizar('es')"><img src="../../novoLayout/common/theme/images/lang/es.png" alt="Espanhol"> Español</a></li>
					    	</ul>
						</li>
						<!-- Language menu END -->


						<!-- Profile / Logout menu -->
						<li class="account dropdown dd-1">
								<a data-toggle="dropdown" href="#" class="glyphicons lock"><span class="hidden-phone">${nomeCandidatoAbrev}</span><i></i></a>
							<ul class="dropdown-menu pull-right">
								<li class="profile">
									<span>
										<span class="heading"><fmt:message key="citcorpore.comum.perfil"/> </span>
										<span class="img"></span>
										<span class="details">
											<p>${nomeCandidato}</p>
											<a href="mailto:" id='emailUsuario'>${emailCandidato}</a>
										</span>
										<span class="clearfix"></span>
									</span>
								</li>
								<li>
									<span>
										<% if(request.getAttribute("metodoAutenticacao") != null && request.getAttribute("metodoAutenticacao").equals("AD")) { %>
										<a class="btn btn-default btn-mini pull-right" href="/citsmart/pages/loginCandidato/loginCandidato.load?metodoAutenticacao=AD&amp;logout=yes"><fmt:message key="citcorpore.comum.sair"/></a>
										<% } else { %>
										<a class="btn btn-default btn-mini pull-right" href="/citsmart/pages/loginCandidato/loginCandidato.load?logout=yes"><fmt:message key="citcorpore.comum.sair"/></a>
										<% } %>
									</span>
								</li>
							</ul>
						</li>
						<!-- // Profile / Logout menu END -->

					</ul>
					</form>
					<!-- // Top Menu Right END -->

					<% if(request.getAttribute("ultimaAtualizacao") != null) { %>
					<div id="header-info-curriculo">
						<strong><fmt:message key="ocorrenciaProblema.dataHoraUltimaAtualizacao" /></strong> <%=request.getAttribute("ultimaAtualizacao")%><% if(request.getAttribute("metodoAutenticacao") != null && request.getAttribute("metodoAutenticacao").equals("AD")) { %>, <a target="_blank" href="${ctx}/pages/visualizarCurriculoTrabalheConosco/visualizarCurriculoTrabalheConosco.load?iframe=true"><fmt:message key="citcorpore.comum.visualizar"/></a><% } %>
					</div>
					<% } %>
				</div>
				<!-- // Wrapper END -->
			</div>

			<div id="wrapper">

				<!-- Inicio conteudo -->
				<div id="content">
					<%@include file="/novoLayout/common/include/templateCurriculoCentro.jsp" %>
				</div>
				<!--  Fim conteudo-->
				<!--  Fim conteudo-->
				<div class="clearfix"></div>
				<!-- Inicio Rodape  -->
				<div id="footer" class="hidden-print">

					<!--  Copyright Line -->
					<div class="copy">© 2014 - <a target="_blank" href="http://centralit.com.br/"><fmt:message key="centraiit.nome.empresa"/></a> - <fmt:message key="citcorpore.todosDireitosReservados"/>  </div>
					<!--  End Copyright Line -->

				</div>
				<%@include file="/novoLayout/common/include/libRodape.jsp" %>
				<%@include file="/novoLayout/common/include/templateCurriculoCentroScripts.jsp" %>

				<!-- Fim Rodape  -->
			</div>
		</div>
	</body>
