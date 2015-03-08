<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/include/header.jsp"%>
		<%@include file="/include/security/security.jsp"%>
				<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<%
			String iframe = "";
			iframe = request.getParameter("iframe");

			String idServicoContrato = request.getParameter("idServicoContrato").toString();
			pageContext.setAttribute("idServicoContrato", idServicoContrato);
		%>

		<%//se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {%>
			<link rel="stylesheet" type="text/css" href="./css/atividadesSer.css"/>
		<%}%>
		
		<html lang="en-us" class="no-js">
		<title><fmt:message key="citcorpore.comum.title"/></title>
		
		<script type="text/javascript" src="../../cit/objects/ServicoContratoDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/ServicoDTO.js"></script>
		<script  charset="ISO-8859-1" type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>

	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="wrapper" style="padding: 0px !important;" >
			<%if (iframe == null) {%>
			<%@include file="/include/menu_vertical.jsp"%>
			<%}%>
			<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
				<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>
				<div class="flat_area grid_16"></div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="grupovisao.atividades_servico_conforme" /></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='formInterno' id="formInterno" action='${ctx}/pages/atividadesServicoContrato/atividadesServicoContrato'>
									<input type='hidden' name='idServicoContrato' value="<%=request.getParameter("idServicoContrato").toString()%>"/>
									<input type='hidden' name='idContrato' value="<%=request.getParameter("idContrato").toString()%>"/>
									<input type='hidden' name='idFormulaOs'/>
									<input type='hidden' name='estruturaFormulaOs'/>
									<input type='hidden' name='formula'/>
									<input type='hidden' name='formulaCalculoFinal'/>
									<input type='hidden' name='complexidade'/>
									<input type='hidden' name='idAtividadeServicoContrato' value="<%=request.getParameter("idAtividadeServicoContrato").toString()%>" />
										<div class="col_100">
											<fieldset>
												<label class="campoObrigatorio" ><fmt:message key="visao.descricaoAtividade"/> </label>
												<div>
													<textarea id="descricaoAtividade" name="descricaoAtividade" maxlength="4000" rows="2" cols="10" style="display: block;" class="Valid[Required] Description[visao.descricaoAtividade]" ></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_100">
											<fieldset>
												<label><fmt:message key="visao.observacao"/> </label>
												<div>
													<textarea id="obsAtividade" name="obsAtividade" maxlength="4000" rows="2" cols="10" style="display: block;" maxlength="1000"></textarea>
												</div>
											</fieldset>
										</div>
									<div class="col_100">
										<fieldset style="padding-top: 5px; padding-bottom: 10px;">
											<label><fmt:message key="visao.contabilizar"/></label>
											<select name="CONTABILIZAR" id="CONTABILIZAR" class=" Description[visao.contabilizar]" style="left-padding: 15px; width: 150px;margin-left:20px;"  onchange="avaliaContabil();">
												<option value="N"><fmt:message key="citcorpore.comum.nao"/></option>
												<option value="S"><fmt:message key="citcorpore.comum.sim"/></option>
											</select>
										</fieldset>
									</div>
									<div class="col_100">
										<fieldset style="padding-bottom: 10px;">
											<br/>
											<label class="campoObrigatorio"><fmt:message key="visao.tipoCusto"/></label>
												<input type='hidden' id='idServicoContratoContabil' name='idServicoContratoContabil'/>
											<select id="tipoCusto" name="tipoCusto" style="width: 150px;margin-left:20px;" class=" Valid[Required] Description[grupo.serviceDesk]" onchange="avaliaTipoCusto();mudarComplexidade();">
												<option value='C'><fmt:message key="requisitosla.custo_total" /></option>
												<option value='F'><fmt:message key="requisitosla.formula" /></option>
											</select>
										</fieldset>
										</div>
										<br/>

										<div id='divByCustoTotal'>
											<fieldset>
												<div>
													<label class="campoObrigatorio" style="margin-left: -5px !important;"><b><fmt:message key="requisitosla.custo_total"/>:</b></label>
													<input type='TEXT' id="custoAtividade"	name='custoAtividade' size='9' maxlength='9' class='Format[Money]' style="width: 150px !important;" />
												</div>
												<div>
													<label class="campoObrigatorio" style="margin-left: -5px !important;" ><b><fmt:message key="matrizvisao.complexidade"/>:</b></label>
													<select id="complexidadeCustoTotal" name="complexidadeCustoTotal" class="noClearCITAjax" style="width: 150px !important;">
														<option value="B"><fmt:message key="citcorpore.comum.complexidadeBaixa" /></option>
														<option value="I"><fmt:message key="citcorpore.comum.complexidadeIntermediaria" /></option>
														<option value="M"><fmt:message key="citcorpore.comum.complexidadeMediana" /></option>
														<option value="A"><fmt:message key="citcorpore.comum.complexidadeAlta" /></option>
														<option value="E"><fmt:message key="citcorpore.comum.complexidadeEspecialista" /></option>
													</select>
												</div>
											</fieldset>
										</div>
										<div id='divByCustoFormula' style='display: none'>
											<fieldset>
												<div>
													<label class="campoObrigatorio" style="margin-left: -5px !important;" ><b><fmt:message key="requisitosla.formula" />:</b></label>
													<select id="Formulas" name="Formulas" class="noClearCITAjax" style="width: 50% !important;" onchange="mostrarFormula();">
													</select>
												</div>

											</fieldset>
										</div>
										<div id='divByCustoFormula2' style='display: none'>
										<br>
										</div>
										 <div id='divByCustoFormula3' style='display: none'>
										 <br>
										</div>
										<div id="divComboServicoContrato" style="display: none;">
											<br>
											<fieldset>
												<label><fmt:message key="contrato.servicos_contrato" /></label>
												<div>
													<input id="addServicoContrato" type='text' readonly="readonly" name="nomeServico" maxlength="80" />
												</div>
											</fieldset>
										</div>
									<br>
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar" /></span>
									</button>
									<button type='reset' name='btnLimpar' class="light" onclick="limpar();">
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar" /></span>
									</button>
									<button type='button' name='btnExcluir' id="btnExcluir"	class="light" onclick='excluir();'>
										<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
										<span><fmt:message key="citcorpore.comum.excluir" /></span>
									</button>
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
	<div id="POPUP_SERVICOCONTRATO" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
		<div class="box grid_16 tabs" style='width: 560px !important;height: 560px !important;' >
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section"  >
						<form name='formPesquisaLocalidade' style='width: 530px !important; ' >
							<cit:findField formName='formPesquisaLocalidade' lockupName='LOOKUP_SERVICOCONTRATO' id='LOOKUP_SERVICOCONTRATO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var properties = { idServicoContrato: "${idServicoContrato}"};
	</script>
	<script type="text/javascript" src="./js/atividadesServico.js"></script>
</html>
