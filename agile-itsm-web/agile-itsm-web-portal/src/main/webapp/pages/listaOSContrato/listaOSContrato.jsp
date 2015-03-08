<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.citframework.util.UtilDatas"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<html>
	<head></head>
<body>
	<table style="margin-top: 2px">
		<tr>
			<td  style="padding: 2px; cursor: pointer">
				<button type='button' name='btnPesquisar' class="light" onclick='adicionaOS();' >
					<span><fmt:message key="citcorpore.comum.criarOS"/></span>
				</button>
			</td>
			<td>
			 <td  class="">
				<table>
					<tr>
						<td>
							<input type='text' name='dataInicioOS' id='dataInicioOS' size='10' maxlength="10" class='Format[Date] Valid[Date] text datepicker'/>
						</td>
						<td>&nbsp;
							<b><fmt:message key="citcorpore.comum.a"/></b>
						&nbsp;</td>
						<td>
							<input type='text' name='dataFimOS' id='dataFimOS' size='10' maxlength="10" class='Format[Date] Valid[Date] text datepicker'/>
						</td>
					</tr>
				</table>
			</td>	
			<td>
				<div class="menubar">
					<ul>
						<li id='tdTodas'  onclick='listarOS("0", this)'><a><fmt:message key="citcorpore.comum.todas"/></a></li>
						<li class="divider"></li>
						
						<li id='tdEmCriacao'  onclick='listarOS("1", this)'><a><fmt:message key="perfil.criacao"/></a></li>
						<li class="divider"></li>
						
						<li  id='tdSolicitada'  onclick='listarOS("2", this)'><a><fmt:message key="perfil.solicitada"/></a></li>
						<li class="divider"></li>
						
						<li id='tdAutorizada'  onclick='listarOS("3", this)'><a><fmt:message key="perfil.autorizada"/></a></li>
						<li class="divider"></li>
						
						<li id='tdAprovada'  onclick='listarOS("4", this)'><a><fmt:message key="citcorpore.comum.aprovadas"/></a></li>
						<li class="divider"></li>
						
						<li id='tdEmExecucao'  onclick='listarOS("5", this)'><a><fmt:message key="citcorpore.comum.emExecucao"/></a></li>
						<li class="divider"></li>
						
						<li  id='tdExecutada'  onclick='listarOS("6", this)'><a><fmt:message key="perfil.executada"/></a></li>
						<li class="divider"></li>
						
						<li id='tdCancelada'  onclick='listarOS("7", this)'><a><fmt:message key="citcorpore.comum.canceladas"/></a></li>
						
					</ul>
			</div>
		  </td>
		</tr>
	</table>
	<div id='divListaOS'>
		<table cellpadding="0" cellspacing="0" width="100%" style='width: 98%' class="table table-bordered table-striped">
			<tr>
				<td  >
					&nbsp;
				</td>
				<td >
					<fmt:message key="citcorpore.comum.servico"/>
				</td>
				<td >
					<fmt:message key="citcorpore.comum.datainicio"/>
				</td>
				<td >
					<fmt:message key="citcorpore.comum.datafim"/>
				</td>				
			</tr>
		</table>
	</div>
</body>
</html>