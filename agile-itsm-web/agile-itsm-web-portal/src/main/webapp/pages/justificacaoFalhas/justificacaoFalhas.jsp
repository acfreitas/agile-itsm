<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.GrupoDTO"%>

<!doctype html public>
<html>
<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/informacao.css">
<link rel="stylesheet" type="text/css" href="css/justificacaoFalhas.css">
</head>

<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="justificacaoFalhas.JustificacaoFalhas" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1"><fmt:message key="justificacaoFalhas.consulta" /></a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/justificacaoFalhas/justificacaoFalhas'>
								<input type="hidden" id="listaItensSerializado" name="listaItensSerializado"/>
								<%
									if(!br.com.citframework.util.Util.isVersionFree(request)){
								%>
								<div class="columns clearfix">
									<div class="col_66">
										<fieldset>
										<label><fmt:message key="justificacaoFalhas.evento" /></label>
										<div>
											<input type="hidden" id="idEvento" name="idEvento" />
											<input type="text" readonly="readonly" id="nomeEvento" size="100%" name="nomeEvento" onclick="$('#POPUP_EVENTOS').dialog('open')"/>
										</div>
										</fieldset>
									</div>
								</div>
								<%
									}
								%>

								<div class="columns clearfix">
									<div class="col_33">
										<fieldset>
											<label><fmt:message key="justificacaoFalhas.grupoEmpregados" /></label>
											<div>
												<input type="hidden" id="idGrupo" name="idGrupo" />
												<input type="text" readonly="readonly" id="nomeGrupo" name="nomeGrupo" onclick="$('#POPUP_GRUPO_EMPREGADOS').dialog('open')"/>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
										<label><fmt:message key="justificacaoFalhas.unidadeEvento" /></label>
										<div>
											<input type="hidden" id="idUnidade" name="idUnidade" />
											<input type="text" readonly="readonly" id="nomeUnidade" size="45" name="nomeUnidade" onclick="$('#POPUP_UNIDADE_EMPREGADO').dialog('open')"/>
										</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_33">
										<fieldset>
											<label><fmt:message key="pesquisa.datainicio" /></label>
											<div>
												<input type="text" class="datepicker" id="dataInicial" name="dataInicial">
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><fmt:message key="pesquisa.datafim" /></label>
											<div>
												<input type="text" class="datepicker" id="dataFinal" name="dataFinal">
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_66">
									<button type='button' name='btnPesquisar' class="light" onclick="pesquisar();">
										<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
										<span><fmt:message key="citcorpore.comum.pesquisa"/></span>
									</button>
								</div>
								<div class="col_33" id="qtdTotal" align="center">

								</div>
								<div class="col_100" style="height: 300px">
									<input type="hidden" name="listaSerializada" id="listaSerializada">
									<div style="border: 1px solid  #DDDDDD; text-align: center;" id="tabelaFalhas">

									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!--  JUSTIFICACAO -->
		<div id="popupJustificacao" class="popup">
			<div class="columns clearfix">
				<div class="col_33">
					<fieldset>
						<label><fmt:message key="pesquisa.grupo" />:</label>
						<div>
							<label id="lbNomeGrupo"></label>
						</div>
					</fieldset>
				</div>
				<div class="col_33">
					<fieldset>
						<label><fmt:message key="pesquisa.unidade" />:</label>
						<div>
							<label id="lbNomeUnidade"></label>
						</div>
					</fieldset>
				</div>
				<div class="col_33">
					<fieldset>
						<label><fmt:message key="inventario.ip" />:</label>
						<div>
							<label id="lbIP"></label>
						</div>
					</fieldset>
				</div>
			</div>
			<div class="columns clearfix">
				<table id="descricaoFalhas" class="table">
					<tr>
						<th><fmt:message key="justificacaoFalhas.falha" /></th>
						<th><fmt:message key="justificacaoFalhas.item" /></th>
						<th><fmt:message key="justificacaoFalhas.tipo" /></th>
						<th>
							<input type="checkbox" onclick="checkAll();" id="chkMarcarTodos" /><fmt:message key="justificacaoFalhas.marcarTodos" />
						</th>
					</tr>
				</table>
			</div>
			<div class="col_100" style="background-color: #cccccc">
				<fieldset>
					<label><fmt:message key="justificacaoFalhas.justifique" /></label>
					<div>
						<textarea rows="3" cols="100" id="txtJustificacaoFalha"></textarea>
						<button type='button' id="btSalvar" name="btSalvar" class="light" onclick="justificar();">
							<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
							<span> <fmt:message key="citcorpore.comum.gravar" /> </span>
						</button>
					</div>
				</fieldset>
			</div>
		</div>
	</div>

	<!-- EVENTOS -->
	<div id="POPUP_EVENTOS" title="<fmt:message key="citcorpore.comum.pesquisa"/> " class="popup">
			<table style="width: 100%">
			<tr>
				<td>
					<h3 align="center"><fmt:message key="justificacaoFalhas.evento" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaEvento' style="width: 90%;" >
			<cit:findField formName='formPesquisaEvento' lockupName='LOOKUP_EVENTOS' id='LOOKUP_EVENTOS' top='0' left='0' len='550'
							heigth='400' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<!-- GRUPO EVENTOS -->
	<div id="POPUP_GRUPO_EMPREGADOS" title="<fmt:message key="citcorpore.comum.pesquisa"/>" class="popup">
			<table style="width: 100%">
			<tr>
				<td>
					<h3 align="center"><fmt:message key="justificacaoFalhas.grupoEmpregados" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaGrupo' style="width: 85%;">
			<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550'
							heigth='400' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<!-- UNIDADE EVENTOS -->
	<div id="POPUP_UNIDADE_EMPREGADO" title="<fmt:message key="citcorpore.comum.pesquisa"/>" class="popup">
			<table style="width: 100%">
			<tr>
				<td>
					<h3 align="center"><fmt:message key="justificacaoFalhas.unidadeEvento" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaUnidade' style="width: 85%;">
			<cit:findField formName='formPesquisaUnidade' lockupName='LOOKUP_UNIDADE' id='LOOKUP_UNIDADE' top='0' left='0' len='550'
							heigth='400' javascriptCode='true' htmlCode='true' />
		</form>
	</div>

	<%@include file="/include/footer.jsp"%>
	<script type="text/javascript" src='js/justificacaoFalhas.js'></script>
</body>
</html>
