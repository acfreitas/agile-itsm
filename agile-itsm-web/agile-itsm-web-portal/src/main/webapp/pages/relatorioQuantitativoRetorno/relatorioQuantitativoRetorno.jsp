<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<style type="text/css">
.table {
	border-left: 1px solid #ddd;
}

.table th {
	border: 1px solid #ddd;
	padding: 4px 10px;
	border-left: none;
	background: #eee;
}

.table td {
	border: 1px solid #ddd;
	padding: 4px 10px;
	border-top: none;
	border-left: none;
}
	div.main_container .box {
	margin-top: -0.3em!important;

}
</style>

<script><!--
	var temporizador;
	addEvent(window, "load", load, false);
	function load() {
		$("#POPUP_USUARIO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	}
	$(function() {
		$("#addSolicitante").click(function() {
			if (document.form.dataInicio.value == '' || document.form.dataFim.value == ''){
				alert('Informe a data!');
				return;
			}

			$("#POPUP_USUARIO").dialog("open");
		});
	});

	function LOOKUP_USUARIO_EMPREGADO_select(id, desc){
		document.getElementById("idSolicitante").value = id;
		document.getElementById("addSolicitante").value = desc;
		$("#POPUP_USUARIO").dialog("close");
	}

	function imprimirRelatorioQuantitativo(formatoRelatorio){
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
			}

			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("citcorpore.comum.validacao.datafim"));
					 document.getElementById("dataFim").value = '';
					return false;
				}
			}
		}
		else {
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
					return false;
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("citcorpore.comum.validacao.datafim"));
					document.getElementById("dataFim").value = '';
					return false;
				}
		}
		if(validaData(dataInicio,dataFim)){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirCargaQuantitativoRetorno" + formatoRelatorio);
		}
	}

	function abrePopupUsuario(){
		$("#POPUP_USUARIO").dialog("open");
	}

	function validaData(dataInicio, dataFim) {
		if (typeof(locale) === "undefined") locale = '';

		var dtInicio = new Date();
		var dtFim = new Date();

		var dtInicioConvert = '';
		var dtFimConvert = '';
		var dtInicioSplit = dataInicio.split("/");
		var dtFimSplit = dataFim.split("/");

		if (locale == 'en') {
			dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[0] + "/" + dtInicioSplit[1];
			dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[0] + "/" + dtFimSplit[1];
		} else {
			dtInicioConvert = dtInicioSplit[2] + "/" + dtInicioSplit[1] + "/" + dtInicioSplit[0];
			dtFimConvert = dtFimSplit[2] + "/" + dtFimSplit[1] + "/" + dtFimSplit[0];
		}

		dtInicio.setTime(Date.parse(dtInicioConvert)).setFullYear;
		dtFim.setTime(Date.parse(dtFimConvert)).setFullYear;

		if (dtInicio > dtFim){
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			return false;
		}else
			return true;
	}

	function limpar(){
		document.form.clear();
	}

--></script>
</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>

	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="relatorioRetorno.quantidadeSolicitacoesRetorno" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div class="block" >
						<div id="parametros">
							<form name='form'
								action='${ctx}/pages/relatorioQuantitativoRetorno/relatorioQuantitativoRetorno'>
								<input type="hidden" id='idSolicitante' name='idSolicitante'>
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset style="height: 64px">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tr>
														<td><input type='text' name='dataInicio'
															id='dataInicio' size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
														<td>&nbsp;-&nbsp;</td>
														<td><input type='text' name='dataFim' id='dataFim'
															size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
									<fieldset style="height: 64px">
										<label><fmt:message key="grupo.grupo" /></label>
										<div>
											<select name="idGrupoAtual"></select>
										</div>
									</fieldset>
									</div>
									<div class="col_25">
									<fieldset style="height: 64px">
										<label>
										<fmt:message key="solicitacaoServico.solicitante" />
										<img id="btHistoricoSolicitante" style="cursor: pointer; display: none;" src="${ctx}/template_new/images/icons/small/grey/month_calendar.png" />
										</label>
										<div>
											<input id="addSolicitante" id="solicitante" type='text' readonly="readonly" name='solicitante' maxlength="80"/>
											</div>
									</fieldset>
									</div>
									<div class="col_10">
									<fieldset>
										<label><fmt:message key="citcorpore.comum.topList" /></label>
										<div>
											<select name="topList"></select>
										</div>
									</fieldset>
									</div>
								</div>
								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light"
											onclick='imprimirRelatorioQuantitativo("PDF");'
											style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/util/file_pdf.png" style="padding-left: 8px;">
											<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
									<button type='button' name='btnRelatorioXls' class="light"
											onclick='imprimirRelatorioQuantitativo("XLS");'
											style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/util/excel.png" style="padding-left: 8px;">
											<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"
											onclick='limpar()' style="margin: 20px !important;">
											<img
												src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar" /></span>
										</button>

									</fieldset>
								</div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
	<div id="POPUP_USUARIO" title="<fmt:message key="citcorpore.comum.pesquisacolaborador" />" style="height:40%;">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div  align="right">
						</div>
						<form name='formPesquisaColaborador' style="width: 540px">
							<cit:findField formName='formPesquisaColaborador' lockupName='LOOKUP_USUARIO_EMPREGADO' id='LOOKUP_USUARIO_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</html>