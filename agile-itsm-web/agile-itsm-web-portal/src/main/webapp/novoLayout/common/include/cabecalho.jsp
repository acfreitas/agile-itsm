<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<!-- Compatibilidade para simular telas do chrome no IE -->
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<%String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>
<%	String nameUser = null;
	String emailUser = null;
	StringBuilder finalNameUser = new StringBuilder();
	String acessoCitsmart = null;

	if (WebUtil.getUsuario(request) != null){
		nameUser = WebUtil.getUsuario(request).getNomeUsuario();
		emailUser = WebUtil.getUsuario(request).getEmail();
		acessoCitsmart = WebUtil.getUsuario(request).getAcessoCitsmart();


		/**
		* Procedimento para Abreviar o Nome do Usuário
		* @autor luiz.borges
		* 26/11/2013 09:47
		*/
		if(nameUser != null){
			String[] array = new String[15];
			int index;

			if(nameUser.contains(" ")){
				int cont = 0;

				nameUser = nameUser.trim();
				array = nameUser.split(" ");
				index = array.length;

				for(String nome : array){
					if(cont == 0){
						finalNameUser.append(nome.toUpperCase() + " ");
						cont++;
					}else{
						if(cont == index-1){
							finalNameUser.append(" " + nome.toUpperCase());
						}else{
							if(nome.length() > 3){
								finalNameUser.append(nome.substring(0, 1).toUpperCase() + ". ");
							}
							cont++;
						}
					}
				}
			}else{
				finalNameUser.append(nameUser.toUpperCase());
			}
		}

	}
	/* @autor edu.braz
	 *  05/02/2014 */
	/* Variveis que recebem os parametros de telefone surpote para, depois fazer a quebra e armazenar em array usando o metodo split usando o (ponto e virgula) como separador.*/
	String telefoneSuporte = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CONFIGURACAO_TELEFONE_SUPORTE, ";");
	String arrayTelefoneSuporte[] = telefoneSuporte.split(";");
	/* @autor edu.braz
	 *  05/02/2014  */
	/* Variveis que recebem os parametros de email surpote para, depois fazer a quebra e armazenar em array usando o metodo split usando o (ponto e virgula) como separador.*/
	String emailSuporte = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.CONFIGURACAO_EMAIL_SUPORTE, ";");
	String arrayEmailSuporte[] = emailSuporte.split(";");

	String urlAtual = request.getServletPath();
%>
<% String asteriskAtivaBotao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKATIVAR, "N"); %>

<% String portalTelaInicio = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LOGIN_PORTAL, "N"); %>

<style type="text/css">
#emailUsuario{
font-size: 11px!important;
 text-overflow: ellipsis!important;
}
#sobre-container { display: -webkit-box;-webkit-box-orient: horizontal;display: -moz-box;-moz-box-orient: horizontal;display: box;box-orient: horizontal;margin-top: 10px;}
#sobre-container h2, #versao-container h2 {font-size: 1.3em;margin-bottom: 0.4em;}
#versao-container {margin-top: 30px;}
#produto-descricao{margin-left: 10px;}
#produto-container {line-height: 1.8em;margin-top: 100px;}
#historico{display: none}
.navbar.main .topnav > li.open .dropdown-menu{background:#363432;border:none;box-shadow:none;-webkit-box-shadow:none;-moz-box-shadow:none;right:0;width:283px;}
</style>

<!-- Wrapper -->
<div class="wrapper">
<!-- //Desenvolvedor: Thiago Matias - Data: 06/11/2013 - Horário: 10:50 - ID Citsmart: 123357 -
	 //*Motivo/Comentário: quando estiver no portal ao clicar na logo redicionará para o portal, quando estiver no sistema ao clicar na logo redicionará para o sistema   -->
	<% if (urlAtual.equalsIgnoreCase("/pages/portal/portal.jsp")){ %>
	<div class="g-first" id="header-logo">
		<a href="${ctx}/pages/portal/portal.load">
			<img alt="CITSMart" id="logo" src="${ctx}/imagens/logo/logo.png"/>
		</a>
	</div>
	<%} else if (urlAtual.equalsIgnoreCase("/pages/index/index.jsp") && "S".equalsIgnoreCase(portalTelaInicio)) { %>
	<div class="g-first" id="header-logo">
		<a href="${ctx}/pages/portal/portal.load">
			<img alt="CITSMart" id="logo" src="${ctx}/imagens/logo/logo.png"/>
		</a>
	</div>

	<%}else{%>
	<div class="g-first" id="header-logo">
		<a href="${ctx}/pages/index/index.load">
			<img alt="CITSMart" id="logo" src="${ctx}/imagens/logo/logo.png"/>
		</a>
	</div>
	<%} %>

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
					if (locale.equalsIgnoreCase("es")) { %>
				<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/es.png" alt="es"></a>
			<% 		} else {%>
				<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
			<%		}
				}
			} %>
				<ul class="dropdown-menu pull-left">
					<li class="active"><a href="javascript:;" title="Portugues" onclick="internacionalizar('pt')" ><img src="${ctx}/novoLayout/common/theme/images/lang/br.png" alt="Portugues"> Português BR</a></li>
					<li><a href="javascript:;" title="English" onclick="internacionalizar('en')"><img src="${ctx}/novoLayout/common/theme/images/lang/us.png" alt="English"> English</a></li>
					<li><a href="javascript:;" title="Español" onclick="internacionalizar('es')"><img src="${ctx}/novoLayout/common/theme/images/lang/es.png" alt="Espanhol"> Español</a></li>
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

			<!-- Profile / Logout menu -->
			<li class="account dropdown dd-1">
					<a data-toggle="dropdown" href="#" class="glyphicons lock"><span class="hidden-phone"><%=finalNameUser%></span><i></i></a>
				<ul class="dropdown-menu pull-right">
					<li><a href="${ctx}/pages/empregado/empregado.load" class="glyphicons cogwheel"><fmt:message key="citcorpore.comum.configuracoes"/><i></i></a></li>
					<li class="profile">
						<span>
							<span class="heading"><fmt:message key="citcorpore.comum.perfil"/> <a href="${ctx}/pages/alterarSenha/alterarSenha.load" class="pull-right"><fmt:message key="usuario.alterarSenha"/></a></span>
							<span class="img"></span>
							<span class="details">
								<p><%=UtilStrings.nullToVazio(nameUser)%></p>
								<a href="mailto:<%=UtilStrings.nullToVazio(emailUser)%>" id='emailUsuario'><%=UtilStrings.nullToVazio(emailUser)%></a>
							</span>
							<span class="clearfix"></span>
						</span>
					</li>
					<li>
						<span>
							<a class="btn btn-default btn-mini pull-right" href="/citsmart/pages/login/login.load?logout=yes"><fmt:message key="citcorpore.comum.sair"/></a>
						</span>
					</li>
				</ul>
			</li>
			<!-- // Profile / Logout menu END -->
		</ul>
	</form>
	<!-- // Top Menu Right END -->
	<ul class="topnav pull-right rimless" >
	<!-- //Desenvolvedor: Thiago Matias - Data: 06/11/2013 - Horário: 10:50 - ID Citsmart: 123357 -
		 //*Motivo/Comentário: quando estiver no portal será exibidido um botao escrito Acessar o Sistema que redirecionará para o sistema, quando estiver no sistema o botão muda de icone e label  -->
		<% if (urlAtual.equalsIgnoreCase("/pages/portal/portal.jsp") && "S".equalsIgnoreCase(acessoCitsmart)){ %>
		<li class="rimless" >
			<a href="${ctx}/pages/index/index.load" data-toggle="" class="glyphicons book_open" ><i></i><b><fmt:message key="portal.acessarSistema"/></b><span class=""> </span></a>
		</li>
		<%} if (urlAtual.equalsIgnoreCase("/pages/index/index.jsp") && "S".equalsIgnoreCase(portalTelaInicio)) { %>

		<li class="rimless" >
			<a href="${ctx}/pages/portal/portal.load" data-toggle="" class="glyphicons home"><i></i><b><fmt:message key="cronograma.inicio"/></b><span class=""> </span></a>
		</li>
		<%} else if (!urlAtual.equalsIgnoreCase("/pages/portal/portal.jsp") && "S".equalsIgnoreCase(portalTelaInicio)){%>
		<li class="rimless" >
			<a href="${ctx}/pages/portal/portal.load" data-toggle="" id="btn3-inicio" class="glyphicons home"><i></i><b><fmt:message key="cronograma.inicio"/></b><span class=""> </span></a>
		</li>
		<%} else if (!urlAtual.equalsIgnoreCase("/pages/portal/portal.jsp") && "N".equalsIgnoreCase(portalTelaInicio)){%>
		<li class="rimless" >
			<a href="${ctx}/pages/index/index.load" data-toggle="" id="btn3-inicio" class="glyphicons home"><i></i><b><fmt:message key="cronograma.inicio"/></b><span class=""> </span></a>
		</li>
		<%}%>
		<!-- verificando se o parâmetro está habilitado ou não, caso esteja exibe botão atendimento -->
		<% if (asteriskAtivaBotao.equals("S")){%>
		<!-- botão atendimento -->
		<li class="rimless" style="display: block;">
			<a href="javascript:abreRamalTelefone();" data-toggle="" class="glyphicons headset"><i></i><b><fmt:message key="asterisk.atendimento"/></b><span class=""> </span></a>
		</li>
		<% } %>

		<!--
		* Procedimento para corrigir problema de quebra do cabeçalho
		* @autor luiz.borges
		* 26/11/2013 16:54
		 -->
		<li class="dropdown dd-1 visible-desktop">
			<a href="#" data-toggle="dropdown" class="glyphicons beach_umbrella"><i></i><fmt:message key="citcorpore.comum.suporte"/> <span class="caret"></span></a>
			<ul class="dropdown-menu pull-right">
				<% /* @autor edu.braz
					*   05/02/2014 */
					/* Percorre todo o array de telefone surpote para depois apresentalos na tela de forma separada. */
					int i = 0;
					while(i<arrayTelefoneSuporte.length){ %>
				<li class="profile">
					<span>
						<a href="javascript:;" data-toggle="" class="glyphicons phone"><i></i><span class=""><%=arrayTelefoneSuporte[i] %></span></a>
					</span>
				</li>
				<%i++;
					} %>
				<%/* @autor edu.braz
					 *  05/02/2014 */
					/* Percorre todo o array de E-mail surpote para depois apresentalos na tela de forma separada. */
					int j = 0;
					while(j<arrayEmailSuporte.length){ %>
				<li class="profile">
					<span>
						<a href="mailto:<%=arrayEmailSuporte[j] %>?Subject=[<fmt:message key="citcorpore.comum.suporte"/>]" target="top" data-toggle="" class="glyphicons envelope"><i></i>
						<span class=""><%=arrayEmailSuporte[j] %></span></a>
					</span>
				</li>
				<%		j++;
					}%>
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
		<cit:sobreCitsmart></cit:sobreCitsmart>
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
