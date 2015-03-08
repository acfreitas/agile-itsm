<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>

<!doctype html public "">
<html>
<head>
<%
	String idSolicitacaoServico = UtilStrings.nullToVazio((String)request.getParameter("idSolicitacaoServico"));
	String dataHoraSolicitacao = UtilStrings.nullToVazio((String)request.getAttribute("dataHoraSolicitacao"));

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
				<h2><fmt:message key="solicitacaoServico.solicitacao"/>&nbsp;N&deg; &nbsp;<%=idSolicitacaoServico%>&nbsp;-&nbsp;<fmt:message key="solicitacaoServico.dataHoraCriacao"/>&nbsp;<%=dataHoraSolicitacao%></h2>
		</div>
  <div class="box grid_16 tabs">

	 <div class="toggle_container">
						<div class="">
							<form name='form' action='${ctx}/pages/consultaSolicitacaoServico/consultaSolicitacaoServico'>
								<div class="columns clearfix">
									<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/>

									<div class="col_66">
										<div class="col_50" >
											<fieldset>
												<label style="cursor: pointer;"><fmt:message key="origemAtendimento.origem"/></label>
												<div>
													<input type='text' name="origem" readonly="readonly"/>
												</div>
											</fieldset>
										</div>
										<div class="col_50" >
											<fieldset>
												<label style="cursor: pointer;"><fmt:message key="solicitacaoServico.solicitante"/></label>
												<div>
													<input type='text' name="solicitante"  readonly="readonly"/>
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_66">
										<div class="col_50" >
											<fieldset>
												<label style="cursor: pointer;"><fmt:message key="solicitacaoServico.tipo"/></label>
												<div>
													<input type='text' name="demanda" readonly="readonly"/>
												</div>
												<label style="cursor: pointer;"><fmt:message key="servico.servico"/></label>
												<div>
													<input type='text' name="servico"  readonly="readonly"/>
												</div>
											</fieldset>
										</div>
										<div class="col_50" >
											<fieldset>
												<label><fmt:message key="solicitacaoServico.descricao"/></label>
													<div>
									       				<textarea name="descricao" cols='70' rows='5' readonly="readonly"></textarea>
													</div>
											</fieldset>
										</div>
									</div>
									<div class="col_66">
										<div class="col_50" >
											<fieldset>
												<label><fmt:message key="contrato.contrato" /></label>
													<div>
														<input type='text' name="contrato" size='50' readonly="readonly"/>
													</div>
												<label><fmt:message key="solicitacaoServico.situacao" /></label>
													<div>
														<input type='text' name="situacao" size='50' readonly="readonly"/>
													</div>
											</fieldset>
										</div>
										<div class="col_50" >
											<fieldset>
												<label><fmt:message key="solicitacaoServico.prioridade" /></label>
													<div>
														<input type='text' name="prioridade" size='10' readonly="readonly"/>
													</div>
												<label><fmt:message key="solicitacaoServico.prazoLimite" /></label>
													<div>
														<input type='text' name="dataHoraLimiteStr" size='30' readonly="readonly"/>
													</div>
											</fieldset>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
	 </div>
  </div>
 </div>
<!-- Fim da Pagina de Conteudo -->

<script type="text/javascript" src="js/consultaSolicitacaoServico.js"></script>

</body>

</html>
