<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.citframework.util.UtilI18N"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<%@include file="/include/security/security.jsp"%>
	<title>CITSmart</title>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<script type="text/javascript" src="../../cit/objects/AcordoServicoContratoDTO.js"></script>
	<script type="text/javascript" src="${ctx}/pages/portal/js/default.js"></script>

	<link href="./css/ansServicoContratoRelacionado.css" rel="stylesheet" />
	
</head>
<body>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<form name='form' id='form' action='${ctx}/pages/ansServicoContratoRelacionado/ansServicoContratoRelacionado'>
		<input type="hidden" name="servicosSerializados" id="servicosSerializados"/>
		<input type="hidden" id="idContrato" name="idContrato" value="<%=request.getAttribute("idContrato")%>" />
		<section>
			<section>
			  <h3><fmt:message key="sla.avaliacao.acordo" /></h3>
				<div>
					<select id="idAcordoNivelServico" name="idAcordoNivelServico" onchange="listaServicosRelacionados();"></select>
				</div>
			</section>
			<section id="HAB" style='display:none;'>
			  <h4><input type="checkbox" id="habilitado" name="habilitado" value="S"/><fmt:message key="sla.avaliacao.habilitado" /></h4>
			</section>
			<section style="padding-bottom: 25px !important;">
				<button type='button' name='btnGravar' class="light" onclick='salvar();'> <span><fmt:message key="citcorpore.comum.gravar" /></span></button>
				<button type='button' name='btnFechar' class="light" onclick='fechar();'><span><fmt:message key="citcorpore.comum.fechar" /></span></button>
				<div id="buscaServico" style="float: right;"></div>
			</section>
			<section>
				<div id="relacionarServicos" style="clear: both;"></div>
			</section>
		</section>
	</form>
</body>
<script type="text/javascript" src="./js/ansServicoContratoRelacionado.js"></script>
</html>