<!DOCTYPE html>
<!--[if lt IE 7]> <html class="ie lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>    <html class="ie lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>    <html class="ie lt-ie9"> <![endif]-->
<!--[if gt IE 8]> <html class="ie gt-ie8"> <![endif]-->
<!--[if !IE]><!--><html><!-- <![endif]-->
<head>
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
<%String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>

<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
<script type="text/javascript" src="${ctx}/js/login2.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/login2.css"/>
<script type="text/javascript">
function logar() {
	document.form.submit();
	window.location = '${ctx}/pages/index/index.load';
}

function encaminhaAosErrosDeScript() {
	document.form.submit();
	window.location = '${ctx}/pages/scripts/scripts.load?upgrade=sim';
}

function alterarSenha() {
	document.form.submit();
	window.location = '${ctx}/pages/alterarSenha/alterarSenha.load';
}

function alterarSenha() {
	document.form.submit();
	window.location = '${ctx}/pages/alterarSenha/alterarSenha.load';
}

</script>

<title>CITSMart</title>
</head>

<body class="login">

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>

	<!-- Wrapper -->
<div id="login">

	<div class="container">

		<div class="wrapper">

			<h1 class="glyphicons lock"><img alt="CITSMart" id="logo" src="/citsmart/imagens/logo/logo.png"/><i></i></h1>

			<!-- Box -->
			<div class="widget">

				<div class="widget-head">
					<h3 class="heading"><fmt:message key="login.area"/></h3>
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
							    		<li class="active" onclick="internacionalizar('')"><a href="" title="Portugues" ><img onclick="internacionalizar('')" src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="Portugues"> Português BR</a></li>
							      		<li onclick="internacionalizar('en')"><a href="" title="English"><img onclick="internacionalizar('en')" src="${ctx}/novoLayout/common/theme/images/lang/us.png" alt="English"> English</a></li>
							      		<li onclick="internacionalizar('es')"><a href="" title="Español"><img onclick="internacionalizar('es')" src="${ctx}/novoLayout/common/theme/images/lang/es.png" alt="Espanhol"> Español</a></li>
							    	</ul>
								</li>
							</ul>
							</div>
					</form>
					<!-- Form -->
					<form name="form" onkeydown="if ( event.keyCode == 13 ) validar();" id="formlogin" action="${ctx}/pages/login/login">
					<!-- <input type="hidden" name="locale" id="locale" /> -->
						<label><fmt:message key="login.login"/></label>
						<input type="text" class="input-block-level" id="user" name="user"/>
						<label><fmt:message key="login.senha"/>
							<% UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

											boolean usuarioIsAd = usuarioService.usuarioIsAD(WebUtil.getUsuario(request) );

											String metodoAutenticacaoProprio = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.METODO_AUTENTICACAO_Pasta, "1");

											if (metodoAutenticacaoProprio != null && metodoAutenticacaoProprio.trim().equalsIgnoreCase("1") ) {

												if (!usuarioIsAd) {
							%>
								<a class="password" href="#modal_alteraSenha" data-toggle="modal" id='modals-bootbox-confirm'><fmt:message key="recuperacaoSenha.esqueceuSuaSenha" /></a>
							<%
								}
							}
							%>
						</label>
						<input type="password" class="input-block-level margin-none" id="senha" name="senha" />
						<div class="separator bottom"></div>
						<div class="row-fluid">
							<div class="span8">
								<div class="">&nbsp;</div>
							</div>
							<div class="span4 center">
								<button class="btn btn-block btn-primary" onclick='validar();' type="button"><fmt:message key="login.login"/></button>
							</div>
						</div>
					</form>
					<!-- // Form END -->

				</div>
				<div class="widget-footer">
					<p class="glyphicons restart"><i></i><fmt:message key="login.usuarioSenha"/></p>

				</div>
			</div>
			<!-- // Box END -->


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
							<div class="row-fluid">
								<div class="span12">
									<label  for="login"><fmt:message key="recuperacaoSenha.loginOuEmail" /></label>
									<input type="text" class="span12 margin-none" id="login_" name="login" placeholder="<fmt:message key="recuperacaoSenha.dica.loginOuEmail" />" />
								</div>
							</div>

<%-- 							<fieldset>
							<label for="login"><fmt:message key="recuperacaoSenha.loginOuEmail" /></label>
							<div>
								<input type="text" class="input-block-level margin-none" id="login" name="login" placeholder="<fmt:message key="recuperacaoSenha.dica.loginOuEmail" />" />
							</div>
						</fieldset> --%>
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

		</div>
		<div class="innerAll center">
		<p><i></i><fmt:message key="login.problema"/></p>
			<span class="glyphicons phone" data-toggle="notyfy" data-layout="topRight" data-type="primary"><i></i><b><fmt:message key="citcorpore.comum.suporte"/></b><span class=""> 0800 6363363</span></span>&nbsp; &nbsp;
			<a href="mailto:suporte.citsmart@centralit.com.brSubject=[<fmt:message key="citcorpore.comum.suporte"/>]" target="top" data-toggle="" class="glyphicons envelope"><i></i>suporte.citsmart@centralit.com.br <span class=""></span></a>

		</div>
		<div class="innerAll center">
			<p><i></i><fmt:message key="login.certificado"/></p>
				<a href="http://www.pinkelephant.com/PinkVERIFY/PinkVERIFY_2011_Toolsets.htm" target="_blank" class="">
					<img alt="" src="${ctx}/imagens/logo/PinkVERIFY_9.PNG" >
					<i></i>
					<span></span>
					<span class="strong"></span>
				</a>

		</div>

	</div>

</div>
<!-- // Wrapper END -->

<!-- Themer -->
<!-- <div id="themer" class="collapse">
	<div class="wrapper">
		<span class="close2">&times; close</span>
		<h4>Themer <span>color options</span></h4>
		<ul>
			<li>Theme: <select id="themer-theme" class="pull-right"></select><div class="clearfix"></div></li>
			<li>Primary Color: <input type="text" data-type="minicolors" data-default="#ffffff" data-slider="hue" data-textfield="false" data-position="left" id="themer-primary-cp" /><div class="clearfix"></div></li>
			<li>
				<span class="link" id="themer-custom-reset">reset theme</span>
				<span class="pull-right"><label>advanced <input type="checkbox" value="1" id="themer-advanced-toggle" /></label></span>
			</li>
		</ul>
		<div id="themer-getcode" class="hide">
			<hr class="separator" />
			<button class="btn btn-primary btn-small pull-right btn-icon glyphicons download" id="themer-getcode-less"><i></i>Get LESS</button>
			<button class="btn btn-inverse btn-small pull-right btn-icon glyphicons download" id="themer-getcode-css"><i></i>Get CSS</button>
			<div class="clearfix"></div>
		</div>
	</div>
</div> -->
<!-- // Themer END -->
<%@include file="/novoLayout/common/include/libRodape.jsp" %>

</body>
</html>