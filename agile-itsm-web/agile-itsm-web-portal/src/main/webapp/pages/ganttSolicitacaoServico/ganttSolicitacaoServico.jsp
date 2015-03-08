<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<script type="text/javascript" src="./js/ganttSolicitacaoServico.js"></script>

	</head>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="gantt.gantt"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<form name="form" action='${ctx}/pages/ganttSolicitacaoServico/ganttSolicitacaoServico'>

						<div class="columns clearfix">
							<div class="col_25">
								<fieldset>
									<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="citcorpore.comum.periodo"/></label>
									<div>
										<table>
											<tr>
												<td>
													<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
												</td>
												<td>
													<fmt:message key="citcorpore.comum.a"/>
												</td>
												<td>
													<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
												</td>
											</tr>
										</table>
									</div>
								</fieldset>
							</div>
						</div>

						<div class="columns clearfix">
							<div class="col_25">
								<fieldset>
									<label style="margin-top: 5px;"><fmt:message key="solicitacaoServico.tipo"/></label>
										<div>
										  	<select id="comboTipoDemanda" name="tipoDemandaServico" style="margin-bottom: 3px;"></select>
										</div>
								</fieldset>
							</div>
							<div class="col_25">
								<fieldset>
									<label style="margin-top: 5px;"><fmt:message key="grupo.grupo"/></label>
										<div>
										  	<select id="comboGruposSeguranca" name="idGruposSeguranca" style="margin-bottom: 3px;"></select>
										</div>
								</fieldset>
							</div>
							<div class="col_25">
								<fieldset>
									<label style="margin-top: 5px;"><fmt:message key="citcorpore.comum.situacao"/></label>
									<div>
										<select name='situacao' style="margin-bottom: 3px;">
											<option value=''><fmt:message key="citcorpore.comum.todos"/></option>
											<OPTION value='Cancelada'><fmt:message key="citcorpore.comum.cancelada"/></OPTION>
								            <OPTION value='EmAndamento'><fmt:message key="citcorpore.comum.emandamento"/></OPTION>
								            <OPTION value='Fechada'><fmt:message key="citcorpore.comum.fechada"/></OPTION>
								            <OPTION value='Reaberta'><fmt:message key="citcorpore.comum.reaberta"/></OPTION>
								            <OPTION value='ReClassificada'><fmt:message key="citcorpore.comum.reclassificada"/></OPTION>
								            <OPTION value='Resolvida'><fmt:message key="citcorpore.comum.resolvida"/></OPTION>
								            <OPTION value='Suspensa'><fmt:message key="citcorpore.comum.suspensa"/></OPTION>
										</select>
									</div>
								</fieldset>
							</div>
						</div>
						<div class="columns clearfix">
							<div class="col_100">
								<fieldset>
									<button style="margin-top: 10px; margin-left: 22px; margin-bottom: 10px; width: 90px;" type='button' name='btnFiltrar' class="light"  onclick='filtrar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/download.png">
										<span><fmt:message key="gantt.filtrar"/></span>
									</button>
					                <button style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px; width: 100px;" type='button' name='btnLimpar' class="light" onclick='meulimpar();'>
                                        <img src="${ctx}/template_new/images/icons/small/grey/clear.png">
                                        <span><fmt:message key="citcorpore.comum.limpar"/></span>
                                    </button>
								</fieldset>
							</div>
						</div>
						<div class="gantt" style="width: 74%; float: left; margin-left: 20px;"></div>
					</form>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>

