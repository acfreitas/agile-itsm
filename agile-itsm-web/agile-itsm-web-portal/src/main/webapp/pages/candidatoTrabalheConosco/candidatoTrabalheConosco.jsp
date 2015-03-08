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
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">

			<% if (request.getAttribute("idCandidato") != null)  { %>
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar main hidden-print">
				<%String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>
				<!-- Wrapper -->
				<div class="wrapper">
					<div class="g-first" id="header-logo">
			  			<h3><fmt:message key="trabalhe.conosco"/></h3>
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
			<% } %>
			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">

				<!-- Inicio conteudo -->
				<div id="content">
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><fmt:message key="trabalhe.conosco"/></h4>
								</div>
								<div class="widget-body collapse in">
									<form name='form' action='${ctx}/pages/candidatoTrabalheConosco/candidatoTrabalheConosco'>
										<input type='hidden' name='idCandidato' id='idCandidato' value="${idCandidato}" />
										<input type='hidden' name='dataFim' id='dataFim' value="${dataFim}"/>
										<input type='hidden' name='situacao' id='situacao' value="${situacao}"/>

										<div class="row-fluid">

											<%@include file="/novoLayout/common/include/candidatoCentro.jsp" %>

											<div class='row-fluid'>
												<div class='span12'>
													<button type='button' name='btnGravar' class="lFloat btn btn-icon btn-primary" onclick='gravar();'>
														<i></i><fmt:message key="citcorpore.comum.gravar" />
													</button>
													<button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='window.location="${ctx}/pages/trabalheConosco/trabalheConosco.load"'>
														<i></i><fmt:message key="citcorpore.comum.voltar" />
													</button>
												</div>
										   </div>
										</div>

									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
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
		<script type="text/javascript" src="${ctx}/pages/candidatoTrabalheConosco/js/candidato.js"></script>
	</body>
</html>