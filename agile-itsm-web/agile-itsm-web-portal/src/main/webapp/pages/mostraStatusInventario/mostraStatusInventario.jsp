<%@page import="br.com.centralit.citcorpore.comm.server.IPAddress"%>
<%@page import="br.com.centralit.citcorpore.batch.ThreadValidaFaixaIP"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.batch.MonitoraAtivosDiscovery"%>
<%@page import="br.com.centralit.citcorpore.util.CITCorporeUtil"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%
			//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			String iframe = "";
			iframe = request.getParameter("iframe");
			
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title" /></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script>
	var contextoAplicacao = "${contextoAplicacao}";
</script>

<script type="text/javascript" src="./js/mostraStatusInventario.js"></script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<link rel="stylesheet" type="text/css" href="./css/mostraStatusInventario.css" />
<%}%>
</head>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="mostrarStatusInventario.titulo"/>
				</h2>
				<form name='form' method="post" action='${ctx}/pages/mostraStatusInventario/mostraStatusInventario'>
					<table width="100%">
						<%if (!CITCorporeUtil.START_MODE_DISCOVERY){%>
						<tr>
							<td colspan="4">
								<font color='red'><fmt:message key="mostrarStatusInventario.atencaOProcesso"/><u><fmt:message key="mostrarStatusInventario.discovery"/></u><fmt:message key="mostrarStatusInventario.estaDesativado"/></font>
							</td>
						</tr>
						<%}%>
						<%if (!CITCorporeUtil.START_MODE_INVENTORY){%>
						<tr>
							<td colspan="4">
								<font color='red'><fmt:message key="mostrarStatusInventario.atencaOProcesso"/><u><fmt:message key="mostrarStatusInventario.inventario"/></u><fmt:message key="mostrarStatusInventario.estaDesativado"/></font>
							</td>
						</tr>
						<%}%>
						<tr>
							<td colspan="4">
								<fmt:message key="mostrarStatusInventario.informeOsIpsAbaixo"/>
							</td>
						</tr>
						<tr>
							<td>
								<textarea rows="5" cols="50" name='ip'></textarea>
							</td>
							<td>
								<div>
									<button onclick='submeteIP()' type='button'><fmt:message key="mostrarStatusInventario.realizarInvetarioDosIps"/></button><br>
									<button onclick='forcarLacoInv()' type='button'><fmt:message key="mostrarStatusInventario.forcarNovaRodadaDoInventario"/></button><br>
								</div>
							</td>
							<td>
								<div>
									<button onclick='refreshIPs()' type='button'><fmt:message key="mostrarStatusInventario.refreshDaListDeIps"/></button><br>
									<button onclick='gerarFaixaIPs()' type='button'><fmt:message key="mostrarStatusInventario.GerarFaixaDeIps"/></button><br>
								</div>
							</td>
							<td>
								<div id="divInfo2">
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<fmt:message key="mostrarStatusInventario.numeroThreadsParaProcessoInvetario"/><b><%=MonitoraAtivosDiscovery.NUMERO_THREADS%></b>
										</td>
										<td>
											<button type='button' onclick='alterarThreadInv()'><fmt:message key="mostrarStatusInventario.alterar"/></button>
										</td>
										<td>
											<fmt:message key="mostrarStatusInventario.numeroThreadsDiscovery"/><b><%=ThreadValidaFaixaIP.NUMERO_THREADS%></b>
										</td>
										<td>
											<button type='button' onclick='alterarThreadDisc()'><fmt:message key="mostrarStatusInventario.alterar"/></button>
										</td>
										<td>
											<fmt:message key="mostrarStatusInventario.icmpTimeout"/><b><%=IPAddress.PING_TIMEOUT%></b>
										</td>
										<td>
											<button type='button' onclick='alterarPingTimeout()'><fmt:message key="mostrarStatusInventario.alterar"/></button>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div id="divInfo">
									<%=MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO_COMPL%>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>

<div class="modal hide fade in" id="faixaIps" aria-hidden="false" data-backdrop="static" data-keyboard="false">
	<!-- Modal heading -->
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
	</div>
	<!-- // Modal heading END -->
	<!-- Modal body -->
	<div class="modal-body" >
		<form name='formGerarFaixa' method="post" action='${ctx}/pages/mostraStatusInventario/mostraStatusInventario'>
		<div id="divParametros">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="4">
						<fmt:message key="mostrarStatusInventario.informeAsFaixasAbaixo"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea rows="2" cols="50" name='ipFaixaGerar'></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<input name='validarIP' type='checkbox' value='S'/><fmt:message key="mostrarStatusInventario.realizarChecknoIp"/>
					</td>
					<td>
						<input name='nativePing' type='checkbox' value='S'/><fmt:message key="mostrarStatusInventario.realizarPingNativo"/>
					</td>
					<td>
						<fmt:message key="mostrarStatusInventario.threads"/>
					</td>
					<td>
						<input name='numThreads' type='text' value='10'/>
					</td>
					<td>
						<button type='button' onclick='submeteGerarFaixaIPs()'><fmt:message key="mostrarStatusInventario.gerar"/></button>
					</td>
				</tr>
			</table>
		</div>
		<div id="divResultado" style='height: 150px;'>

		</div>
		</form>
	</div>
	<!-- // Modal body END -->
	<!-- Modal footer -->
	<div class="modal-footer">
		<a id="btFechar" href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
	</div>
</div>

<div class="modal hide fade in" id="alteraValor" aria-hidden="false" data-backdrop="static" data-keyboard="false">
	<!-- Modal heading -->
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
	</div>
	<!-- // Modal heading END -->
	<!-- Modal body -->
	<div class="modal-body" >
		<form name='formAlteraValor' method="post" action='${ctx}/pages/mostraStatusInventario/mostraStatusInventario'>
		<input type='hidden' name='tipoDado'/>
		<div id="divParametros">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<fmt:message key="mostrarStatusInventario.valor"/>
					</td>
				</tr>
				<tr>
					<td>
						<input type='text' name='valor' maxlength="5" size="5"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button type='button' onclick='submeteAlteracaoValor()'><fmt:message key="mostrarStatusInventario.alterar"/></button>
					</td>
				</tr>
			</table>
		</div>
		</form>
	</div>
	<!-- // Modal body END -->
	<!-- Modal footer -->
	<div class="modal-footer">
		<a id="btFechar" href="#" class="btn " data-dismiss="modal"><fmt:message key="citcorpore.comum.fechar" /></a>
	</div>
</div>

	<%@include file="/include/footer.jsp"%>
	
	
</body>
</html>

