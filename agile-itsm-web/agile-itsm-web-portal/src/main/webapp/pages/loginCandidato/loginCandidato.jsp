<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.versao.Versao" %>
<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@ page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>
<%@ page import="br.com.citframework.service.ServiceLocator" %>
<%@ page import="br.com.centralit.citcorpore.negocio.ParametroCorporeService" %>
<%@ page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO" %>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@ page import="br.com.centralit.citcorpore.negocio.UsuarioService" %>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados" %>
<%@page import="br.com.citframework.util.UtilStrings"%>
<!DOCTYPE html>
<html>
<head>

<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
<link type="text/css" rel="stylesheet" href="${ctx}/pages/loginCandidato/css/loginCandidato.css"/>

<title>CITSMart</title>
<% boolean metodoAutenticacaoAtivo = (request.getParameter("metodoAutenticacao") != null && ((request.getParameter("metodoAutenticacao")).trim()).equals("AD")); %>
<% String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>
<%
/* Variveis que recebem os parametros de telefone surpote para, depois fazer a quebra e armazenar em array usando o metodo split usando o (ponto e virgula) como separador.*/
String telefoneSuporteTelaLogin = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CONFIGURACAO_TELEFONE_SUPORTE_TELA_LOGIN, ";" );
String arrayTelefoneSuporteTelaLogin[] = telefoneSuporteTelaLogin.split(";");

/* Variveis que recebem os parametros de email surpote para, depois fazer a quebra e armazenar em array usando o metodo split usando o (ponto e virgula) como separador.*/
String emailSuporteTelaLogin = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CONFIGURACAO_EMAIL_SUPORTE_TELA_LOGIN, ";" );
String arrayEmailSuporteTelaLogin[] = emailSuporteTelaLogin.split(";");
%>
</head>

<body class="login">

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="<fmt:message key='citcorpore.comum.aguardeProcessando'/>" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>

	<!-- Wrapper -->
<div id="login">

	<div class="container">

		<div class="wrapper">

			<!-- Box -->
			<div class="widget">
				<% if(metodoAutenticacaoAtivo) { %>
				<div class="widget-head">
					<h3 class="heading"><fmt:message key="rh.acessoAoCurriculo"/></h3>
				</div>
				<div class="widget-body">
					<form name='formInternacionaliza' id='formInternacionaliza' class="marginless" action='${ctx}/pages/internacionalizar/internacionalizar'>
						<input type="hidden" name="locale" id="locale"/>
							<div class="navbar main hidden-print">
							<ul class="topnav pull-right">
								<!-- Language menu -->
								<li class="hidden-phone dropdown dd-1 dd-flags" id="lang_nav">

								<% if (locale.equalsIgnoreCase("pt")) {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
    							 <%} else {
    								if (locale.equalsIgnoreCase("en")) {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/us.png" alt="br"></a>
    							  <%} else {
    								  if (locale.equalsIgnoreCase("es")) {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/es.png" alt="br"></a>
    							    <%} else {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
    								<%}
    							    }
    							  }%>

							    	<ul class="dropdown-menu pull-left">
							    		<li class="active"><a href="javascript:;" onclick="internacionalizar('')" title="Portugues" ><img src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="Portugues"> Português BR</a></li>
							      		<li><a href="javascript:;" onclick="internacionalizar('en')" title="English"><img src="${ctx}/novoLayout/common/theme/images/lang/us.png" alt="English"> English</a></li>
							      		<li><a href="javascript:;" onclick="internacionalizar('es')" title="Español"><img src="${ctx}/novoLayout/common/theme/images/lang/es.png" alt="Espanhol"> Español</a></li>
							    	</ul>
								</li>
							</ul>
						</div>
					</form>

					<!-- Form -->
					<form name="form" onkeydown="if ( event.keyCode == 13 ) validar();" id="formlogin" action="${ctx}/pages/loginCandidato/loginCandidato">
						<input type="hidden" name="metodoAutenticacao" id="metodoAutenticacao" value="${metodoAutenticacao}" />
						<label><fmt:message key="login.nomeusuario"/></label>
						<input type="text" class="input-block-level" id="user" name="user" maxlength="256" placeholder="<fmt:message key='login.placeholderUsuario'/>"/>
						<label><fmt:message key="login.senha"/></label>
						<input type="password" class="input-block-level margin-none" maxlength="100" id="senha" name="senha" placeholder="<fmt:message key='login.placeholderSenha'/>" />
						<div class="separator bottom"></div>
						<div class="row-fluid">
							<div class="span8">
								<%
								String metodoAutenticacaoProprio = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.METODO_AUTENTICACAO_Pasta, "1");
								if (metodoAutenticacaoProprio != null && metodoAutenticacaoProprio.trim().equalsIgnoreCase("1") ) {
								%>
								<label>
									<a class="" href="#modal_alteraSenha" data-toggle="modal" id='modals-bootbox-confirm'><fmt:message key="recuperacaoSenha.esqueceuSuaSenha" /></a>
								</label>
								<%
								}
								%>
							</div>
							<div class="span4 center">
								<button class="btn btn-block btn-primary" onclick='validar();' type="button"><fmt:message key="login.entrar"/></button>
							</div>
						</div>
					</form><!-- // Form END -->
				</div>
				<div class="widget-footer">
					<p class="glyphicons restart"><i></i><fmt:message key="login.usuarioSenha"/></p>
				</div><!-- .widget-footer -->

				<!-- INICIO MODAL REDEFINIR SENHA -->
				<div class="modal hide fade in" id="modal_alteraSenha"  aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><fmt:message key="login.esqueceu" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name="form1" action="${ctx}/pages/login/login" method="post">
							<label for="login"><fmt:message key="recuperacaoSenha.loginOuEmail" /></label>
							<div class='row-fluid'>
								<div class='span12'>
									<input type="text" class="" id="" name="login" placeholder="<fmt:message key="recuperacaoSenha.dica.loginOuEmail" />" />
								</div>
							</div>
						</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn btn-default" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
						<a href="#" data-dismiss="modal" class="btn btn-primary" onclick="document.form1.fireEvent('redefinirSenha');"><fmt:message key="citcorpore.comum.gravar" /></a>
					</div>
					<!-- // Modal footer END -->
				</div>
				<% } else { %>
				<div class="widget-head">
					<h3 class="heading"><fmt:message key="trabalhe.conosco"/></h3>
				</div>
				<div class="widget-body">
					<form name='formInternacionaliza' id='formInternacionaliza' class="marginless" action='${ctx}/pages/internacionalizar/internacionalizar'>
						<input type="hidden" name="locale" id="locale"/>
							<div class="navbar main hidden-print">
							<ul class="topnav pull-right">
								<!-- Language menu -->
								<li class="hidden-phone dropdown dd-1 dd-flags" id="lang_nav">

								<% if (locale.equalsIgnoreCase("pt")) {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
    							 <%} else {
    								if (locale.equalsIgnoreCase("en")) {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/us.png" alt="br"></a>
    							  <%} else {
    								  if (locale.equalsIgnoreCase("es")) {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/es.png" alt="br"></a>
    							    <%} else {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
    								<%}
    							    }
    							  }%>

							    	<ul class="dropdown-menu pull-left">
							    		<li class="active"><a href="javascript:;" onclick="internacionalizar('')" title="Portugues" ><img src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="Portugues"> Português BR</a></li>
							      		<li><a href="javascript:;" onclick="internacionalizar('en')" title="English"><img src="${ctx}/novoLayout/common/theme/images/lang/us.png" alt="English"> English</a></li>
							      		<li><a href="javascript:;" onclick="internacionalizar('es')" title="Español"><img src="${ctx}/novoLayout/common/theme/images/lang/es.png" alt="Espanhol"> Español</a></li>
							    	</ul>
								</li>
							</ul>
						</div>
					</form>

					<!-- Form -->
					<form name="form" onkeydown="if ( event.keyCode == 13 ) validar();" id="formlogin" action="${ctx}/pages/loginCandidato/loginCandidato">
						<input type="hidden" name="metodoAutenticacao" id="metodoAutenticacao" value="${metodoAutenticacao}" />
						<label><fmt:message key="candidato.candidato"/></label>
						<input type="text" class="input-block-level" maxlength="14" id="cpf" name="cpf" placeholder="<fmt:message key='rh.cpf'/>"/>
						<label><fmt:message key="login.senha"/></label>
						<input type="password" class="input-block-level margin-none" maxlength="100" id="senha" name="senha" placeholder="<fmt:message key='login.placeholderSenha'/>" />
						<div class="separator bottom"></div>
						<div class="row-fluid">
							<div class="span8">
								<label>
									<a class="" href="${ctx}/pages/recuperaSenhaCandidato/recuperaSenhaCandidato.load" data-toggle="modal" id='modals-bootbox-confirm'><fmt:message key="recuperacaoSenha.esqueceuSuaSenha" /></a>
								</label>
								<label>
									<a class="" href="${ctx}/pages/candidatoTrabalheConosco/candidatoTrabalheConosco.load" data-toggle="modal" id='modals-bootbox-confirm'><fmt:message key="usuario.novo" /></a>
								</label>
							</div>
							<div class="span4 center">
								<button class="btn btn-block btn-primary" onclick='validar();' type="button"><fmt:message key="login.entrar"/></button>
							</div>
						</div>
					</form><!-- // Form END -->
				</div>
				<% } %>
			</div><!-- // Box END -->
		</div>

	</div>

</div>
<% if(metodoAutenticacaoAtivo) { %>
<div class="innerAll center" style="width: 90%;">
	<p><i></i><fmt:message key="login.problema"/></p>
	<table align="center">
	  <td align="left">
	        <%/* @autor edu.braz
	      	 *  05/02/2014
	           * Percorre todo o array de telefone surpote para depois apresentalos na tela de forma separada. */
	            int i = 0;
	            while(i<arrayTelefoneSuporteTelaLogin.length){ %>
		<span class="glyphicons phone" data-toggle="notyfy" data-layout="topRight" data-type="primary"><i></i><b><fmt:message key="citcorpore.comum.ligue_nos"/></b><span class="">&nbsp;<%= arrayTelefoneSuporteTelaLogin[i] %></span></span><br>
		    <%i++;
	            } %>
	              </td><td align="left">
		<%/* @autor edu.braz
	      	   *  05/02/2014
	             * Percorre todo o array de E-mail surpote para depois apresentalos na tela de forma separada. */
	              int j = 0;
	              while(j<arrayEmailSuporteTelaLogin.length){ %>
		&nbsp;&nbsp;<a href="mailto:<%=arrayEmailSuporteTelaLogin[j]%>?Subject=[<fmt:message key="citcorpore.comum.suporte"/>]" target="top" data-toggle="" class="glyphicons envelope"><i></i><%=arrayEmailSuporteTelaLogin[j] %><span class=""></span></a><br>
		     <%j++;
	              } %>
	      </td>
	</table>
</div>
<% } %>
<%@include file="/novoLayout/common/include/libRodape.jsp" %>
<script type="text/javascript" src="${ctx}/pages/loginCandidato/js/loginCandidato.js"></script>
</body>
</html>