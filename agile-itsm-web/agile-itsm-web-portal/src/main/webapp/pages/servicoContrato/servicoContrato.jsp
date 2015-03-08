<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title"/></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<%}%>

    <link type="text/css" rel="stylesheet" href="css/servicoContrato.css"/>
    
    
	<script type="text/javascript" src="../../cit/objects/ServicoContratoDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/FluxoServicoDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/ServicoDTO.js"></script>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1">
					<fmt:message key="visao.servicoContrato" /></a></li>
				</ul>
					<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='${ctx}/pages/servicoContratoUnico/servicoContratoUnico'>
								<input type='hidden' name='idServicoContrato' value="<%=request.getParameter("idServicoContrato").toString()%>"/>
								<input type='hidden' id='idServico' name='idServico'/>
								<input type="hidden" id="fluxosSerializados" name="fluxosSerializados" />
								<input type="hidden" id="idTipoFluxoExclusao" name="idTipoFluxoExclusao" />
								<input type="hidden" id="idFaseExclusao" name="idFaseExclusao" />
								<input type="hidden" id="principalExclusao" name="principalExclusao" />
								<input type="hidden" id="idContrato" name="idContrato" value="<%=request.getParameter("idContrato").toString()%>" />

								<div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="contrato.servicos_contrato" /></label>
										<div>
											<input id="addServicoContrato" type='text'  name="nomeServico" maxlength="80" class="Valid[Required] Description[contrato.servicos_contrato]"/>
										</div>
									</fieldset>
								</div>

								<div class="col_100">
									<div class="col_33">
										<fieldset style="height: 55px;">
											<label class="campoObrigatorio"><fmt:message key="visao.condicaoOperacao" /></label>
											<div>
												<select style="width: 90%;" id='idCondicaoOperacao' name='idCondicaoOperacao' class="Valid[Required] Description[visao.condicaoOperacao]"></select>
											</div>
										</fieldset>
									</div>

									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="visao.dataDeInicio"/></label>
											<div>
											  	<input  type='text'  id="dataInicio" style="width: 90% !important;"  name="dataInicio" maxlength="10"  size="10"  class="Valid[Required,Data] Description[visao.dataDeInicio] Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>

									<div class="col_33">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.datafim"/></label>
											<div>
										  		<input  type='text'  id="dataFim" style="width: 90% !important;" onchange="ComparaDatas()" name="dataFim" maxlength="10"  size="10"  class="Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>
								</div>

								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.observacao"/> </label>
											<div>
												<textarea id="observacao" name="observacao" rows="2" cols="10" style="display: block;" onKeyDown="tamanhoCampo(this, 1000);" onKeyUp="tamanhoCampo(this, 1000);"></textarea>
											</div>
										</fieldset>
									</div>

									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.restricoesPressuposto"/> </label>
											<div>
												<textarea id="restricoesPressup" name="restricoesPressup" rows="2" cols="10" style="display: block;" onKeyDown="tamanhoCampo(this, 65000);" onKeyUp="tamanhoCampo(this, 65000);"></textarea>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.objetivo"/> </label>
											<div>
												<textarea id="objetivo" name="objetivo" rows="2" cols="10" style="display: block;" onKeyDown="tamanhoCampo(this, 1000);" onKeyUp="tamanhoCampo(this, 1000);"></textarea>
											</div>
										</fieldset>
									</div>

									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.descricaoProcesso"/> </label>
											<div>
												<textarea id="descricaoProcesso" name="descricaoProcesso" rows="2" cols="10" style="display: block;" onKeyDown="tamanhoCampo(this, 100);" onKeyUp="tamanhoCampo(this, 100);"></textarea>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.linkProcesso" /></label>
											<div>
												<input id="linkProcesso" type='text' name="linkProcesso" maxlength="500" />
											</div>
										</fieldset>
									</div>

									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.areaRequisitante" /></label>
											<div>
												<input id="areaRequisitante" type='text' name="areaRequisitante" maxlength="500" />
											</div>
										</fieldset>
									</div>
								</div>

								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="visao.modEmailAberturaIncidente" /></label>
											<div>
												<select style="width: 100%;" id='idModeloEmailCriacao' name='idModeloEmailCriacao' class="Valid[Required] Description[visao.modEmailAberturaIncidente]"></select>
											</div>
										</fieldset>
									</div>

									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.modEmailFinalizacaoIncidente" /></label>
											<div>
												<select style="width: 100%;" id='idModeloEmailFinalizacoes' name='idModeloEmailFinalizacao'></select>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.modEmailDemaisAcoes" /></label>
											<div>
												<select style="width: 100%;" id='idModeloEmailAcoes' name='idModeloEmailAcoes' ></select>
											</div>
										</fieldset>
									</div>

									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.grupoEscalacaoNivel1" /></label>
											<div>
												<select style="width: 100%;" id='idGrupoNivel1' name='idGrupoNivel1'></select>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.grupoExecutor" /></label>
											<div>
												<select style="width: 100%;" id='idGrupoExecutor' name='idGrupoExecutor'></select>
											</div>
										</fieldset>
									</div>

									<div class="col_50">
										<fieldset>
											<label><fmt:message key="visao.grupoAprovador" /></label>
											<div>
												<select style="width: 100%;" id='idGrupoAprovador' name='idGrupoAprovador'></select>
											</div>
										</fieldset>
									</div>
								</div>

							<div class="col_100">
								<div class="col_50">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="visao.calendario" /></label>
										<div>
											<select style="width: 100%;" id='idCalendario' name='idCalendario' class="Valid[Required] Description[visao.calendario]"></select>
										</div>
									</fieldset>
								</div>
								<div class="col_50">
									<fieldset>
										<label><fmt:message key="servicoContrato.expandirTela" /></label>
										<div>
											<input type="radio"   id='expandirS' name='expandir' value="S"/><fmt:message key="citcorpore.comum.sim" />
											<input type="radio"   id='expandirN' name='expandir' value="N"/><fmt:message key="citcorpore.comum.nao" />
										</div>
									</fieldset>
								</div>
								</div>

								<div class="col_100">
									<br><br>
									<fieldset>
										<label id="addFluxo" style="cursor: pointer;"title="<fmt:message  key="citcorpore.comum.cliqueParaAdicinar" />"><fmt:message  key="visao.fluxoServico" /><img	src="${ctx}/imagens/add.png"></label>
										<div  id="gridFluxos">
											<table class="tableLess" id="tabelaFluxo" width="100%">
												<thead>
													<tr class="th">
														<th></th>
														<th><fmt:message  key="visao.fluxo" /></th>
														<th><fmt:message  key="visao.fase" /></th>
														<th><fmt:message  key="visao.fluxoPrincipal" />	</th>
													</tr>
												</thead>
												<tbody></tbody>
											</table>
										</div>
									</fieldset>
									<br>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light" onclick='gravar();'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir" class="light" onclick='excluir();'>
									<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%@include file="/include/footer.jsp"%>
	
	<script type="text/javascript" src="js/servicoContrato.js"></script>
	
	
	<div id="POPUP_FLUXOTRABALHO" title="<fmt:message  key="visao.fluxoServico" />">
		<div class="section">
			<div class="col_100">
				<fieldset>
					<label  class="campoObrigatorio"><fmt:message key="visao.fluxo" /></label>
					<div>
						<select id='idtipofluxo' name='idtipofluxo' class="Valid[Required] Description[visao.modEmailAberturaIncidente]"></select>
					</div>
				</fieldset>
			</div>
			<div class="col_100">
				<fieldset>
					<label class="campoObrigatorio"><fmt:message key="visao.fase" /></label>
					<div>
						<select id='idfase' name='idfase' class="Valid[Required] Description[visao.modEmailAberturaIncidente]"></select>
					</div>
				</fieldset>
			</div>

			<div class="col_100">
				<fieldset>
					<label class="campoObrigatorio"><fmt:message key="visao.fluxoPrincipal"/></label>
					<div>
					<select name="fluxoPrincipal" id="fluxoPrincipal">
						<option value="N"><fmt:message key="citcorpore.comum.nao"/></option>
						<option value="S"><fmt:message key="citcorpore.comum.sim"/></option>
					</select>
					</div>
				</fieldset>
			</div>

			<button type='button' name='btnGravar' class="light" onclick='gravarFluxo();'>
				<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
				<span><fmt:message key="citcorpore.comum.gravar" />
				</span>
			</button>
		</div>
	</div>

	<div id="POPUP_SERVICOCONTRATO" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
		<div class="box grid_16 tabs" style='width: 560px !important;height: 560px !important;' >
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section"  >
						<form name='formPesquisaServicoAtivo' style='width: 530px !important; ' >
							<cit:findField formName='formPesquisaServicoAtivo' lockupName='LOOKUP_SERVICO' id='LOOKUP_SERVICO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>	
	
</body>

</html>
