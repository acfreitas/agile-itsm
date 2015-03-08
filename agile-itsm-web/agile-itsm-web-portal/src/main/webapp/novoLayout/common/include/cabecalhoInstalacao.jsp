<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>

<!-- Wrapper -->
<div class="wrapper">
<!-- //Desenvolvedor: Thiago Matias - Data: 06/11/2013 - Horário: 10:50 - ID Citsmart: 123357 - Motivo/Comentário: quando estiver no portal ao clicar na logo redicionará para o portal, quando estiver no sistema ao clicar na logo redicionará para o sistema   -->

	<div class="g-first" id="header-logo">
		<a href="javascript:;">
			<img alt="CITSMart" id="logo" src="/citsmart/imagens/logo/logo.png"/>
		</a>
	</div>

	<!-- Topo Right -->
	<form name='formInternacionaliza' id='formInternacionaliza' class="marginless" action='${ctx}/pages/start/start'>
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
					<li class="active"><a href="javascript:;" onclick="internacionalizar('')" title="Portugues"><img src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="Portugues"> Português BR</a></li>
					<li><a href="javascript:;" onclick="internacionalizar('en')" title="English"><img src="${ctx}/novoLayout/common/theme/images/lang/us.png" alt="English"> English</a></li>
					<li><a href="javascript:;" onclick="internacionalizar('es')" title="Español"><img src="${ctx}/novoLayout/common/theme/images/lang/es.png" alt="Espanhol"> Español</a></li>
				</ul>
			</li>
			<!-- Language menu END -->

			<!-- Dropdown -->
			<li class="dropdown dd-1 visible-desktop">
				<a href="#" data-toggle="dropdown" class="glyphicons shield"><i></i><fmt:message key="citcorpore.comum.ajuda"/> <span class="caret"></span></a>
				<ul class="dropdown-menu pull-right">
					<li class="dropdown ">
						<a href="/cithelp/index.html" target="blank" class="dropdown-toggle glyphicons adress_book" data-toggle=""><i></i><fmt:message key="citcorpore.comum.guiaUsuario"/></a>
					</li>
					<li><a href="#modal_sobreCitsmart" data-toggle="modal" data-target="#modal_sobreCitsmart" class="glyphicons circle_info"><fmt:message key="citcorpore.comum.sobre"/><i></i></a></li>
				</ul>
			</li>
			<!-- // Dropdown END -->
		</ul>
	</form>
	<!-- // Top Menu Right END -->
	<ul class="topnav pull-right rimless">
		<!--
		* Procedimento para corrigir problema de quebra do cabeçalho
		* @autor luiz.borges
		* 26/11/2013 16:54
		 -->
		<li class="dropdown dd-1 visible-desktop">
			<a href="#" data-toggle="dropdown" class="glyphicons beach_umbrella"><i></i><fmt:message key="citcorpore.comum.suporte"/> <span class="caret"></span></a>
			<ul class="dropdown-menu pull-right">
				<% if (locale.equalsIgnoreCase("en")) {%>
				<li class="profile">
					<span>
						<a href="javascript:;" data-toggle="" class="glyphicons phone"><i></i><span> Call us +1 415 508 4002 </span></a>
					</span>
				</li>
				<%} else {%>
				<li class="profile">
					<span>
						<a href="javascript:;" data-toggle="" class="glyphicons phone"><i></i><span> +55 (61) 3966 - 4349 (Brasília)</span></a>
					</span>
				</li>
				<li class="profile">
					<span>
						<a href="javascript:;" data-toggle="" class="glyphicons phone"><i></i><span> 0800 500 3030 (Demais Localidades)</span></a>
					</span>
				</li>
				<%}%>
				<li class="profile">
					<span>
						<a href="mailto:suporte.citsmart@citsmart.com.br?Subject=[<fmt:message key="citcorpore.comum.suporte"/>]" target="top" data-toggle="" class="glyphicons envelope"><i></i> <span class=""><fmt:message key="citcorpore.comum.email_suporte"/></span></a>
					</span>
				</li>
			</ul>
		</li>
		<!-- // Dropdown END -->
	</ul>
	<div class="clearfix"></div>
</div>
<!-- // Wrapper END -->

<!-- Modal sobre Citsmart -->
<div class="modal hide fade in" id="modal_sobreCitsmart" data-width="800">
	<!-- Modal heading -->
	<div class="modal-header">
		 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3><fmt:message key="citcorpore.comum.sobre"/></h3>
	</div>
	<!-- // Modal heading END -->
	<!-- Modal body -->
	<div class="modal-body">
		<cit:sobreCitsmart />
	</div>
	<!-- // Modal body END -->
	<!-- Modal footer -->
	<div class="modal-footer">
		<div style="margin: 0;" class="form-actions">
			<a href="#" class="btn " onclick="preencherComboOrigem();" data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
		</div>
	<!-- // Modal footer END -->
	</div>
</div>
