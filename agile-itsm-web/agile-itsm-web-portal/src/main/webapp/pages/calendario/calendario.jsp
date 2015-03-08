<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.util.Constantes"%>

<!doctype html public "">
<html>
<head>
<%
    String iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" />
</title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" charset="ISO-8859-1"src="${ctx}/js/ValidacaoUtils.js"></script>
<link rel="stylesheet" type="text/css" href="./css/calendario.css" />
</head>
<body>
	<div id="wrapper">

		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="calendario.calendario" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1"><fmt:message key="calendario.cadastro" /></a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top"><fmt:message key="calendario.pesquisa" /></a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/calendario/calendario'>
								<input type="hidden" id="listaExecoesSerializada" name="listaExecoesSerializada" />
								<input type="hidden" id="idCalendario" name="idCalendario" />
								<input type="hidden" id="permiteDataInferiorHoje" name="permiteDataInferiorHoje" />
								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="calendario.descricao" /></label>
											<div>
												<input type='text' name="descricao" maxlength="70"
													   class="Valid[Required] Description[calendario.descricao]" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset style="height: 60px;">
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="calendario.consideraFeriados" />:</label>
											<div style="margin-top: 5px;">
												<input type="radio" checked="checked" name="consideraFeriados" class="Description[calendario.consideraFeriados]" value="S"/><fmt:message key="citcorpore.comum.sim" />
												<input type="radio" name="consideraFeriados" class="Description[calendario.consideraFeriados]" value="N"/><fmt:message key="citcorpore.comum.nao" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<div>
												<table class="table" width="50%">
													<tr>
														<th colspan="2"><fmt:message key="calendario.jornadas" /></th>
													</tr>
													<tr>
														<td><fmt:message key="calendario.segunda" /></td>
														<td>  <select name="idJornadaSeg"></select>  </td>
													</tr>
													<tr>
														<td><fmt:message key="calendario.terca" /></td>
														<td>  <select name="idJornadaTer"></select>  </td>
													</tr>
													<tr>
														<td><fmt:message key="calendario.quarta" /></td>
														<td>  <select name="idJornadaQua"></select>  </td>
													</tr>
													<tr>
														<td><fmt:message key="calendario.quinta" /></td>
														<td>  <select name="idJornadaQui"></select>  </td>
													</tr>
													<tr>
														<td><fmt:message key="calendario.sexta" /></td>
														<td>  <select name="idJornadaSex"></select>  </td>
													</tr>
													<tr>
														<td><fmt:message key="calendario.sabado" /></td>
														<td>  <select name="idJornadaSab"></select>  </td>
													</tr>
													<tr>
														<td><fmt:message key="calendario.domingo" /></td>
														<td>  <select name="idJornadaDom"></select>  </td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label>
												<fmt:message key="calendario.excecoes" />
												<img alt="" onclick="abrirAdicionarExcecao()"
													 src="${ctx}/imagens/add.png" />
											</label>
											<div>
												<table class="table" width="50%" id="tabelaExcecoes" style="margin-top: 5px;">
													<tr>
														<th><fmt:message key="citcorpore.comum.tipo" /></th>
														<th><fmt:message key="citcorpore.comum.datainicio" /></th>
														<th><fmt:message key="citcorpore.comum.datafim" /></th>
														<th><fmt:message key="calendario.jornada" /></th>
														<th><fmt:message key="calendario.excluir" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
							</div>
							<div style="margin-top: 10px;">
								<button type='button' name='btnGravar' class="light"
										onclick='salvar()'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='clearForm();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
									<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" /></span>
								</button>
							</div>
						</div>
					</form>
					</div>
				</div> <!--  end tab -->
				<div id="tabs-2" class="block">
					<div class="section">
						<fmt:message key="citcorpore.comum.pesquisa"/>
						<form name='formPesquisa'>
							<cit:findField formName='formPesquisa' lockupName='LOOKUP_CALENDARIO' id='LOOKUP_CALENDARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
				<!-- ## FIM - AREA DA APLICACAO ## -->
			</div>
		</div>
	</div>
	<!-- Fim da Pagina de Conteudo -->

		<div id="divAdicionarExcecao" style="left: 10px; top: 10px; padding: 7px;">
			<form name="formAdicionarExcecao" action="">
				<div class="columns clearfix">
					<div class="col_100">
						<input type="radio" checked="checked" name="tipoExcecao" id="tipoExcecaoFolga" class="Description[Considera Feriados]" value="F" onclick="hideExcecao()"/><fmt:message key="calendario.folga"/>
						<input type="radio" name="tipoExcecao" id="tipoExcecaoTrabalho" class="Description[Considera Feriados]" value="T" onclick="showExcecao()"/><fmt:message key="calendario.trabalho"/>
					</div>
				</div>
				<div class="columns clearfix">
					<div class="" style="display: block; float: left; width: 230px; margin-right: 10px;">
						<label><fmt:message key="citcorpore.comum.datainicio"/>:</label>
						<div>
							<input type='text' id="dataInicio" name="dataInicio" maxlength="10" size="10"
								class="Valid[Required,Date] Description[citcorpore.comum.datainicio] Format[Date] datepicker" />
						</div>
					</div>
					<div class=""  style="display: block; float: left; width: 230px;">
						<label><fmt:message key="citcorpore.comum.datafim"/>:</label>
						<div>
							<input type='text' id="dataTermino" name="dataTermino" maxlength="10" size="10"
								class="Valid[Required,Date] Description[citcorpore.comum.datafim] Format[Date] datepicker" />
						</div>
					</div>
					<div class="c"  style="display: block; float: left; width: 470px; margin-bottom: 10px;" id="divJornada">
						<label><fmt:message key="calendario.jornada"/>:</label>
						<div>
							<select id="idjornadaexcecao"></select>
						</div>
					</div>
				</div>

				<button type='button' name='btnGravar' class="light" onclick='adicionarExcecao()'>
					<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
					<span>
						<fmt:message key="citcorpore.comum.gravar"/>
					</span>
				</button>
			</form>
		</div>
	</div>

	<%@include file="/include/footer.jsp"%>
	<script type="text/javascript">
		var ctx = "${ctx}"
	</script>
	<script type="text/javascript" src="./js/calendario.js"></script>
</body>
</html>


