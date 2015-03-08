<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>
<!doctype html public "">
<html>
	<head>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<link type="text/css" rel="stylesheet" href="${ctx}/pages/trabalheConosco/css/trabalheConosco.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="container-fluid fixed">

			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar main hidden-print">
				<%String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>
				<!-- Wrapper -->
				<div class="wrapper">
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
					<form name='formInternacionaliza' id='formInternacionaliza' class="marginless" action='${ctx}/pages/internacionalizar/internacionalizar'>
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
										<span class="heading"><fmt:message key="citcorpore.comum.perfil"/></span>
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

					<div class="clearfix"></div>
				</div>
				<!-- // Wrapper END -->
			</div>

			<div id="wrapper" class="nowrapper">

				<!-- Inicio conteudo -->
				<div id="content">
					<div class="row-fluid">
						<div class="innerAll">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"></h4>
								</div>
								<div class="widget-body collapse in">
									<div class="row-fluid center">
										<div class="span12">
											<div class="row-fluid">
												<div class="span6">
													<% if(request.getAttribute("metodoAutenticacao") != null && request.getAttribute("metodoAutenticacao").equals("AD")) { %>
													<a target="_blank" href="${ctx}/pages/visualizarCurriculoTrabalheConosco/visualizarCurriculoTrabalheConosco.load?iframe=true"><fmt:message key="rh.visualizarCurriculo"/></a>
													<% } else { %>
													<a href="${ctx}/pages/candidatoTrabalheConosco/candidatoTrabalheConosco.load"><fmt:message key="alterar.dados.acesso"/></a>
													<% } %>
												</div>
												<div class="span6">
													<a href="${ctx}/pages/templateCurriculoTrabalheConosco/templateCurriculoTrabalheConosco.load"><fmt:message key="rh.atualizarCurriculo"/></a>
												</div>
											</div>
										</div>
									</div>
									<div class="row-fluid">
										<div class="span12 center">
											<img alt="" src="http://centralit.com.br/images/logo_central.png">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->
				<div class="clearfix"></div>
				<!-- Inicio Rodape  -->
				<div id="footer" class="hidden-print">

					<!--  Copyright Line -->
					<div class="copy">© 2014 - <a target="_blank" href="http://centralit.com.br/"><fmt:message key="centraiit.nome.empresa"/></a> - <fmt:message key="citcorpore.todosDireitosReservados"/>  </div>
					<!--  End Copyright Line -->

				</div>
				<%@include file="/novoLayout/common/include/libRodape.jsp" %>
				<!-- Fim Rodape  -->
			</div>
		</div>
		<script src="${ctx}/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
		<script type="text/javascript" src="js/candidato.js"></script>
	</body>
</html>