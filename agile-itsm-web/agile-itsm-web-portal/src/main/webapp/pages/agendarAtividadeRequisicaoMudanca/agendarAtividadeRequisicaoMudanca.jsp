<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String idRequisicaoMudanca = UtilStrings.nullToVazio((String)request.getParameter("idRequisicaoMudanca"));
	String nomeTarefa = UtilStrings.nullToVazio((String)request.getAttribute("nomeTarefa"));

%>
	<%@include file="/include/header.jsp"%>

    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>

</head>

<body>


<div id="wrapper">
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">

		<div class="flat_area grid_16">
				<h2><fmt:message key="requisicaoMudanca.requisicaoMudanca"/>&nbsp;Nº&nbsp;<%=idRequisicaoMudanca%></h2>
		</div>
  <div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key='gerenciaservico.agendaratividade.historicoagendamentos' /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key='gerenciaservico.agendaratividade.criaragendamento' /></a></li>
				</ul>
	 <div class="toggle_container">
	 					<div id="tabs-1" class="block">
	 						<div id='divAgendamentos' style='overflow: auto'>
	 						</div>
	 					</div>
						<div id="tabs-2" class="block">
							<form name='form' action='${ctx}/pages/agendarAtividadeRequisicaoMudanca/agendarAtividadeRequisicaoMudanca'>
								<div class="columns clearfix">
									<input type='hidden' name='idRequisicaoMudanca' id='idRequisicaoMudanca'/>
									<input type='hidden' name='idTarefa'/>

									<div class="col_100">
										<fieldset>
											<label style="cursor: pointer;"><fmt:message key='gerenciaservico.agendaratividade.crupoatividades' /></label>
											<div>
												<select name='idGrupoAtvPeriodica'>
												</select>
											</div>
										</fieldset>
										<fieldset id='fldOrientacao'>
											<label style="cursor: pointer;"><fmt:message key='gerenciaservico.agendaratividade.orientacaotecnica' /></label>
											<div>
												<textarea name="orientacaoTecnica" cols='90' rows='5'></textarea>
											</div>
										</fieldset>
										<fieldset id='fldIniciarEm'>
											<label style="cursor: pointer;"><fmt:message key='gerenciaservico.agendaratividade.agendarpara' /></label>
											<div>
												<table>
													<tr>
														<td>
															<input type='text' name='dataInicio' id='dataInicio' size="10" maxlength="10" class="Format[Date] Valid[Date] Description[gerenciaservico.agendaratividade.agendarpara] datepicker text"/>
												        </td>
												        <td>
										                    &nbsp;<b><fmt:message key='citcorpore.comum.as' /></b>&nbsp;
												        </td>
												        <td>
												        	<input type='text' name='horaInicio' id='horaInicio' size="5" maxlength="5" class='Format[Hora] Valid[Hora] Description[gerenciaservico.agendaratividade.agendarpara] text'/>
												        </td>
												    </tr>
												</table>
											</div>
										</fieldset>
										<fieldset id='fldDuracaoEstimada'>
											<label style="cursor: pointer;"><fmt:message key='gerenciaservico.agendaratividade.duracaoestimada' /> </label>
											<div>
												<table>
													<tr>
														<td>
															<input type='text' name='duracaoEstimada' id='duracaoEstimada' size="5" maxlength="5" class='Format[Numero] text'/>&nbsp;
														</td>
														<td>
															<b>&nbsp;<fmt:message key='gerenciaservico.agendaratividade.minuto' /></b>
														</td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>

								<div>
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name='btnCancelar' class="light" onclick='fechar();'>
										<img
											src="${ctx}/template_new/images/icons/small/grey/bended_arrow_left.png">
										<span><fmt:message key="citcorpore.comum.cancelar" />
										</span>
									</button>
							    </div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
	 </div>
  </div>
 </div>
<!-- Fim da Pagina de Conteudo -->

	<script src="js/agendarAtividadeRequisicaoMudanca.js"></script>
</body>

</html>
