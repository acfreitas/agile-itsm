<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.util.Constantes"%>

<!doctype html public "">
<html>
<head>
<%
    String iframe = "";
    iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>


<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
<script>
	addEvent(window, "load", load, false);
	var popup;
	addEvent(window, "load", load, false);
	function load() {
		popup = new PopupManager(800, 600, "${ctx}/pages/");
		popup.titulo = "<fmt:message key='citcorpore.comum.pesquisarapida' />";
		document.form.afterRestore = function() {
<!--			$('.tabs').tabs('select', 0);-->
		}
		$("#POPUP_TIPOFLUXO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}
	function atribuiValor(id, desc){
		document.form.idTipoFluxo.value = id;
		document.form.nomeFluxo.value = desc.split("-")[0];
		//$("#popupCadastroRapido").dialog("close");
	}

	$(function() {
		$("#addTipoFluxo").click(function() {
			$("#POPUP_TIPOFLUXO").dialog("open");
		});
	});

	function LOOKUP_TIPOFLUXO_select(id, desc){
		document.form.idTipoFluxo.value = id;
		document.form.nomeFluxo.value = desc.split("-")[0];
		$("#POPUP_TIPOFLUXO").dialog("close");
	}

	function LOOKUP_FLUXO_select(id,desc){
		document.form.restore({idFluxo : id});
	}

	/**
	 * @param dataInicial Componente que está armazenando a data inicial.
	 * @param dataFinal Componente que está armazenando a data final.
	 */
	function validaData(dataInicial, dataFinal){
		if(dataInicial.value == ""){
			alert(i18n_message("calendario.preenchaDatas"));
			return false;
		}

		if(dataFinal.value != "" && converteData(dataInicial.value) > converteData(dataFinal.value)){
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			return false;
		}
		return true;
	}

	function salvar(){
		if(validaData(document.getElementById("dataInicio"),
		   			  document.getElementById("dataFim"))){
			document.form.save();
		}
	}
</script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					Fluxo
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1">Cadastro</a></li>
					<li><a href="#tabs-2" class="round_top">Pesquisa</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/fluxo/fluxo'>
								<input type="hidden" name="locale" id="locale" value="" />
								<input type="hidden" name="idTipoFluxo" id="idTipoFluxo" />
								<input type="hidden" name="idFluxo" id="idFluxo" />
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio">
												Tipo
												<img src="${ctx}/imagens/add.png"
													 onclick="popup.abrePopup('tipoFluxo', '()')">
											</label>
											<div>
												<input id="addTipoFluxo" type='text' readonly="readonly" name="nomeFluxo" maxlength="80" class="Valid[Required] Description[Tipo de Fluxo]" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label>Versão</label>
											<div>
												<input type='text' readonly="readonly" name="versao" class="" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio">Data início</label>
											<div>
												<input type='text' name="dataInicio" id="dataInicio" maxlength="10" size="10"
													   class="Valid[Required,Data] Description[Data início] Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label>Data fim</label>
											<div>
												<input type='text' name="dataFim" id="dataFim" maxlength="10" size="10"
													   class="Format[Data]" readonly="readonly" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label>XML</label>
											<div>
												<textarea name="conteudoXml" rows="15" cols="100"></textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
								<button type='button' name='btnGravar' class="light"
									onclick='salvar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear();'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='update();'>
									<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir" />
									</span>
								</button>
								</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_FLUXO' id='LOOKUP_FLUXO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				<!-- ## FIM - AREA DA APLICACAO ## -->
			</div>
		</div>
	</div>
	<div id="popupCadastroRapido">
		<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%">
		</iframe>
	</div>
	<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
	<div id="POPUP_TIPOFLUXO" title="Pesquisa Tipo de Fluxo">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaTipoFluxo' style="width: 540px">
							<cit:findField formName='formPesquisaTipoFluxo'
								lockupName='LOOKUP_TIPOFLUXO' id='LOOKUP_TIPOFLUXO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</html>

