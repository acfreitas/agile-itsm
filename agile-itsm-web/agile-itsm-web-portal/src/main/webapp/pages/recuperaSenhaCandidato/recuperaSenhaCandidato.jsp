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
<link type="text/css" rel="stylesheet" href="${ctx}/pages/recuperaSenhaCandidato/css/recuperaSenhaCandidato.css"/>

<title>CITSMart</title>
</head>

<body class="login">

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="<fmt:message key='citcorpore.comum.aguardeProcessando'/>" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>
	
	<!-- Wrapper -->
<div id="login">

	<div class="container">
	
		<div class="wrapper">
				
			<!-- Box -->
			<div class="widget">
				
				<div class="widget-head">
					<h3 class="heading"><fmt:message key="recuperar.senha.candidato"/></h3>
				</div>
				<div class="widget-body">
				
					
					
					<!-- Form -->
					<form name="formRecuperaSenhaCandidato" id="formRecuperaSenhaCandidato" action="${ctx}/pages/recuperaSenhaCandidato/recuperaSenhaCandidato.load">
						<div class="row-fluid">
							<div class="span12">
								<label for="login"><fmt:message key="citcorpore.comum.email" /></label>
								<div class='row-fluid'>
									<div class='span12'>
										<input type="text" class="" id="email" name="email" maxlength="255" placeholder="<fmt:message key="baseConhecimento.informemail" />" />
									</div>
								</div>
							</div>
							<div>
								<button type="button" class="lFloat btn btn-icon btn-primary" onclick='validar();'><fmt:message key="citSmart.comum.enviar"/></button>
							
								<button type="button" name='btnLimpar' class="lFloat btn btn-icon btn-primary" onclick='window.location="${ctx}/pages/loginCandidato/loginCandidato.load"'>
								<i></i><fmt:message key="citcorpore.comum.voltar" />
							</button>
						</div>
						</div>
						
					</form>
					<!-- // Form END -->
							
				</div>
			</div>
			<!-- // Box END -->
			
		</div>
		
	</div>
	
</div>

<%@include file="/novoLayout/common/include/libRodape.jsp" %>
<script type="text/javascript" src="${ctx}/pages/recuperaSenhaCandidato/js/recuperaSenhaCandidato.js"></script>
</body>
</html>