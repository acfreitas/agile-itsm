<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.LdapDTO"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@include file="/include/header.jsp"%>
		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<link rel="stylesheet" type="text/css" href="./css/ldap.css" />

		<script type="text/javascript" src="./js/ldap.js"></script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="ldap.configuracao"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="ldap.parametrosldap"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/ldap/ldap' enctype="multipart/form-data">
									<input type="hidden" name="listAtributoLdapSerializado" id="idListAtributoLdapSerializado">
									<div class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<h2><fmt:message key="ldap.atributos"/></h2>
													<table class="table" id="tabelaAtributosLdap" style="width: 100%">
														<tr>
															<th style="width: 40%;"><fmt:message key="ldap.atributo"/></th>
															<th style="width: 100%;"><fmt:message key="ldap.valor"/></th>
															<th style="display: none;">Quantidade de Parametros</th>
														</tr>
													</table>
											</fieldset>
										</div>
										<br>
									</div>

									<button id="btnGravar" type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button id="btnTestarConexao" type='button' name='btnTestarConexao' class="light" onclick='testarConexao();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="ldap.testarconexao"/></span>
									</button>
									<button id="btnSincronizaLDAP" type='button' name='btnSincronizaLDAP' class="light" onclick='sincronizaLDAP();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="ldap.sincronizaLDAP"/></span>
									</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>

