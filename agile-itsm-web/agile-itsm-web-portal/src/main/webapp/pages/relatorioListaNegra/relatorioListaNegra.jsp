<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
<!doctype html public "">
<html>
<head>
<%@ include file="/include/header.jsp"%>

<%
    String iframe = "";
    iframe = request.getParameter("iframe");
%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

<style type="text/css">
	.table {
		border-left:1px solid #ddd;
		clear: both;
	}

	.table th {
		border:1px solid #ddd;
		padding:4px 10px;
		border-left:none;
		background:#eee;
	}

	.table td {
		border:1px solid #ddd !important;
		padding:4px 10px !important;
		border-top:none !important;
		border-left:none !important;
	}
<%if (iframe != null) {%>

	div#main_container {
		margin: 10px 10px 10px 10px;

	}
<%}%>
</style>
<script><!--
	addEvent(window, "load", load, false);
	function load() {

		$("#POPUP_SOFTWARESLISTANEGRA").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});

		$("#POPUP_GRUPO_ITEMCONFIGURACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});


	}


	function LOOKUP_SOFTWARESLISTANEGRA_select(id, desc) {
		document.form.idSoftwaresListaNegra.value = id;
		document.form.nomeSoftwaresListaNegra.value = desc.split(" - ")[0];
		$("#POPUP_SOFTWARESLISTANEGRA").dialog("close");
	}

	function LOOKUP_GRUPOITEMCONFIGURACAO_select(id, desc){
		document.form.idGrupoItemConfiguracao.value = id;
		document.form.nomeGrupoItemConfiguracao.value = desc.split(" - ")[0];
		$("#POPUP_GRUPO_ITEMCONFIGURACAO").dialog("close");
	}

	function imprimirRelatorioListaNegra(){

		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;

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
		if(validaData(dataInicio,dataFim)){
			JANELA_AGUARDE_MENU.show();
			document.form.formatoArquivoRelatorio.value = 'pdf';
			document.form.fireEvent("imprimirRelatorioListaNegra");

		}
	}

	function imprimirRelatorioListaNegraXls(){

		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
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
		if(validaData(dataInicio,dataFim)){
			JANELA_AGUARDE_MENU.show();
			document.form.formatoArquivoRelatorio.value = 'xls';
			document.form.fireEvent("imprimirRelatorioListaNegra");
		}
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

	function pageLoad(){
		$(function()
		{
			$('input.datepicker').datepicker();
		});
	}

	function limpar() {
		document.form.clear();
	}


	 $(function() {
		$("#addListaNegra").click(function() {
			$("#POPUP_SOFTWARESLISTANEGRA").dialog("open");
		});
	});
	 $(function() {
			$("#addGrupoItemConfiguracao").click(function() {
				$("#POPUP_GRUPO_ITEMCONFIGURACAO").dialog("open");
			});
		});

--></script>

</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>

	<div id="wrapper">
			<%
		    if (iframe == null) {
		%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%
		    }
		%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%
			    if (iframe == null) {
			%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%
			    }
			%>


		<%-- <%@include file="/include/menu_vertical.jsp"%> --%>
		<!-- Conteudo -->
		<!-- <div id="main_container" class="main_container container_16 clearfix"> -->
		<%-- 	<%@include file="/include/menu_horizontal.jsp"%> --%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="citcorporeRelatorio.softwaresListaNegra"/></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div  class="toggle_container">
					<div  class="block" >
						<div id="parametros" >
							<form name='form' action='${ctx}/pages/relatorioListaNegra/relatorioListaNegra'>
								<input type="hidden" id='idSoftwaresListaNegra' name='idSoftwaresListaNegra'>
								<input type="hidden" id='idGrupoItemConfiguracao' name='idGrupoItemConfiguracao'>
								<input type="hidden" id='formatoArquivoRelatorio' name='formatoArquivoRelatorio'>

								<div class="columns clearfix">
									<div class="col_15">
										<fieldset style="height: 71px">
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tr>
														<td>
															<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
														<td>
															<fmt:message key="citcorpore.comum.a" />
														</td>
														<td>
															<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 71px">
											<label><fmt:message key="menu.nome.softwareListaNegra" /></label>
											<div>
												<input type='text' name="nomeSoftwaresListaNegra" id="addListaNegra" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
								   </div>
							       <div class="col_25">
										<fieldset style="height: 71px">
											<label><fmt:message key="menu.nome.grupoItemConfiguracao" /></label>
											<div>
												<input type='text' name="nomeGrupoItemConfiguracao" id="addGrupoItemConfiguracao" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
								   </div>
								   <div class="col_25">
								       <fieldset style="height: 71px">
													<label><fmt:message key="localidadeFisica.localidadeFisica"/></label>
														<div>
														  <input type='text' name="localidade" maxlength="40" size="70" class="Description[citcorpore.comum.nome]" />
												       </div>
										</fieldset>
								  </div>

								</div>

								<div class="col_100">
									<fieldset>
									    <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioListaNegra()' style="margin: 20px !important;">
										<img src="${ctx}/template_new/images/icons/small/util/file_pdf.png">
										<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
										 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioListaNegraXls()' style="margin: 20px !important;">
										<img src="${ctx}/template_new/images/icons/small/util/excel.png">
										<span><fmt:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"  onclick='limpar()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
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
		<%
		    if (iframe == null) {
		%>
		<%@include file="/include/footer.jsp"%>
		<% } %>

</body>
 <div id="POPUP_SOFTWARESLISTANEGRA" title="<fmt:message key="menu.nome.softwareListaNegra" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaListaNegra' style="width: 540px">
						<cit:findField formName='formPesquisaListaNegra'
							lockupName='LOOKUP_SOFTWARESLISTANEGRA' id='LOOKUP_SOFTWARESLISTANEGRA' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
 </div>

 <div id="POPUP_GRUPO_ITEMCONFIGURACAO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
						<div class="section">
							<form name='formGrupoItemConfiguracao' style="width: 540px">
								<cit:findField formName='formGrupoItemConfiguracao'
									lockupName='LOOKUP_GRUPOITEMCONFIGURACAO' id='LOOKUP_GRUPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
				</div>
			</div>
 </div>

</html>