<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoServicoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoDTO"%>

<!doctype html public "">
<html>
<head>
<%
	String idContrato = request.getParameter("idContrato");
   	if (idContrato == null)
   	idContrato = "";
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>

<title><fmt:message key="citcorpore.comum.title"/></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="../../cit/objects/NotificacaoServicoDTO.js"></script>
<link type="text/css" rel="stylesheet" href="css/notificacaoServicoContrato.css"/>
	<script>


	</script>
</head>

<body>
	<div id="wrapper">
		<div id="main_container" class="main_container container_16 clearfix">
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="notificacao.cadastronotificacao" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/notificacaoServicoContrato/notificacaoServicoContrato'>
								<input type='hidden' name='idBaseConhecimento' id='idBaseConhecimento'/>
								<input type='hidden' name='idNotificacao' />
								<input type='hidden' name='idContrato' value="<%=idContrato%>" />
								<input type='hidden' name='dataInicio' id="dataInicio" />
								<input type='hidden' name='dataFim' id="dataFim" />
								<input type="hidden" name="usuariosSerializados">
								<input type="hidden" name="gruposSerializados">
								<input type='hidden' name='origemNotificacao' id='origemNotificacao'/>
								<input type='hidden' name='servicosLancados' id='servicosLancados'/>

								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="notificacao.titulo" /></label>
											<div>
												<input type='text' name="titulo" maxlength="256" class="Valid[Required] Description[notificacao.titulo]" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="notificacao.tipoNotificacao" /></label>
											<div>
												<select id="tipoNotificacao" name="tipoNotificacao" class='Valid[Required] Description[notificacao.tipoNotificacao]'></select>
											</div>
										</fieldset>
									</div>
								</div>
								<div  class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label id="addUsuario" style="cursor: pointer;">
												<fmt:message key="citcorpore.comum.usuario" />
												<img src="${ctx}/imagens/add2.png">
											</label>
											<div id="gridUsuario">
												<table class="table" id="tabelaUsuario"	style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 98%;"><fmt:message key="citcorpore.comum.usuario" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label id="addGrupo" style="cursor: pointer;">
												<fmt:message key="grupo.grupo" />
												<img src="${ctx}/imagens/add2.png">
											</label>
											<div id="gridGrupo">
												<table class="table" id="tabelaGrupo"  style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 98%;"><fmt:message key="grupo.grupo" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<div id='divServicos' style='height: 300px; overflow: auto;'></div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="js/notificacaoServicoContrato.js"></script>
	
	<div id="POPUP_USUARIO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<cit:findField formName='formPesquisaUsuario' lockupName='LOOKUP_USUARIO_NOTIFICACAO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_GRUPO" title="<fmt:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaGrupo' style="width: 540px">
							<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</body>


</html>
