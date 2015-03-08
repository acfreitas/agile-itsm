<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");

%>
<%@include file="/include/header.jsp" %>
<%@include file="/include/security/security.jsp" %>
<title><fmt:message key="citcorpore.comum.title"/></title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<link type="text/css" rel="stylesheet" href="${ctx}/novoLayout/common/include/css/template.css"/>
<script type="text/javascript">
	function validaNav()
	{
		if ($.browser.msie)
			document.getElementById("ie").value = "true";
		else
			document.getElementById("ie").value = "false";
	}

	addEvent(window, "load", load, false);

</script>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;" />
</head>

<body>

<!-- Area de JavaScripts -->
<script>
    function agendar(){
    	document.formAgendamento.fireEvent("save");
    }
</script>
		<div class="widget">
			<div class="widget-head">
				<h4></h4>
			</div>

			<div class="widget-body center">
				<!-- Inicio conteudo -->
				<div id="content <%=(iframe == null) ? "contentframe" : "" %>">
					<div class="separator top"></div>
					<div class="row-fluid">
						<div class="innerLR">
						 	<form id='formAgendamento' name='formAgendamento' action='${ctx}/agendamentoExecucaoBI/agendamentoExecucaoBI'>
							 		<input id="idProcessamentoBatch" name="idProcessamentoBatch" type="hidden" />
							 		<input id="ie" name="ie" type="hidden"></input>
							 		<input id="tipo" name="tipo" type="hidden"/>
							 		<input id="conteudo" name="conteudo" type="hidden" />
							 		<input id="descricao" name="descricao" type="hidden" />
							 		<input id="idConexaoBI" name="idConexaoBI" type="hidden" />
							 		<input id="abriuAgendamentoExcecao" name="abriuAgendamentoExcecao" type="hidden" />
							 		<div class="row-fluid">
								 		<div class="span2" >
									 		<fieldset>
												<label class="campoObrigatorio"><fmt:message key="processamentoBatch.segundos"/></label>
												<div>
													<select name='segundos' id='segundos' class="Valid[Required] Description[processamentoBatch.segundos]"></select>
												</div>
											</fieldset>
										</div>
								 		<div class="span2" >
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="processamentoBatch.minutos"/></label>
												<div>
													<select name='minutos' id='minutos' class="Valid[Required] Description[processamentoBatch.minutos]"></select>
												</div>
											</fieldset>
										</div>
								 		<div class="span2" >
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="processamentoBatch.horas"/></label>
												<div>
													<select name='horas' id='horas' class="Valid[Required] Description[processamentoBatch.horas]"></select>
												</div>
											</fieldset>
										</div>
								 		<div class="span2" >
											<fieldset>
												<label><fmt:message key="processamentoBatch.diaDoMes"/></label>
												<div>
													<select name='diaDoMes' id='diaDoMes' class="Valid[Required] Description[processamentoBatch.diaDoMes]"></select>
												</div>
											</fieldset>
								 		</div>
								 		<div class="span2" >
											<fieldset>
												<label class="campoObrigatorio"><fmt:message key="processamentoBatch.mes"/></label>
												<div>
													<select name='mes' id='mes' class="Valid[Required] Description[processamentoBatch.mes]"></select>
												</div>
											</fieldset>
										</div>
								 		<div class="span2" >
											<fieldset>
												<label><fmt:message key="processamentoBatch.diaDaSemana"/></label>
												<div>
													<select name='diaDaSemana' id='diaDaSemana' class="Valid[Required] Description[processamentoBatch.diaDaSemana]"></select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="row-fluid">
								 		<div class="span2" >
								 			<fieldset>
												<label><fmt:message key="processamentoBatch.ano"/></label>
												<div>
													<select name='ano' id='ano' class="Description[processamentoBatch.ano]"></select>
												</div>
											</fieldset>
								 		</div>
								 		<div class="span2" >
								 			<fieldset>
												<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.situacao"/></label>
												<div>
													<select name='situacao' id='situacao' class="Valid[Required] Description[citcorpore.comum.situacao]"></select>
												</div>
											</fieldset>
								 		</div>
							 		</div>
						 		<br>
								<button type='button' name='btnGravar' class="light"  onclick='save();'>
									<span><fmt:message key="citcorpore.comum.gravar"/></span>
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	<%@include file="/include/footer.jsp"%>
</body>
</html>