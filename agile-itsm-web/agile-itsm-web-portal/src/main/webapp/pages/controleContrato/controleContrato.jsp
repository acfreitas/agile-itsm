<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@include file="/include/header.jsp"%>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<title><fmt:message key="citcorpore.comum.title" /></title>

		<link rel="stylesheet" type="text/css" href="./css/controleContrato.css" />

    	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>

		<script type="text/javascript" src="./js/controleContrato.js"></script>


	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>


		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
				<div class="flat_area grid_16">
					<h2>
						<fmt:message key="citcorpore.controleContrato.controleContrato" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li><a href="#tabs-1"><fmt:message	key="citcorpore.controleContrato.cadastroControleContrato" /></a></li>
						<li><a href="#tabs-2" class="round_top"><fmt:message key="citcorpore.controleContrato.pesquisaControleContrato" /></a></li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="${ctx}/pages/controleContrato/controleContrato">
									<input type="hidden" id="idControleContrato" name="idControleContrato"/>
									<input type="hidden" id="idContrato" name="idContrato"/>
									<input type="hidden" id="pagamentoSerialize" name="pagamentoSerialize"/>
									<input type="hidden" id="treinamentoServicoSerialize" name="treinamentoSerialize"/>
									<input type="hidden" id="ocorrenciaSerialize" name="ocorrenciaSerialize"/>
									<input type="hidden" id="versaoSerialize" name="versaoSerialize"/>
									<input type="hidden" id="lstModulos" name="lstModulos"/>
									<input type="hidden" id="rowIndex" name="rowIndex"/>

									<div class="columns clearfix">
										<!-- Início dados Gerais -->
										<div class="col_60">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="contrato.contrato" />
												</label>
												<div>
													<input readonly="readonly" style="width: 90% !important;" type='text' name="nomeContrato" id="nomeContrato" onclick="abrePopupContrato()" maxlength="50" size="50" class="Valid[Required] Description[contrato.contrato]" />
												    <img style="vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.numeroSubscricao" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="numeroSubscricao"  maxlength="100" class="Valid[Required] Description[citcorpore.comum.numeroSubscricao]" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.comum.nome" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="cliente"  maxlength="200" class="Valid[Required] Description[citcorpore.comum.nome]" />
												</div>
											</fieldset>
										</div>
										<div class="col_60">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.endereco" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="endereco" maxlength="200" />
												</div>
											</fieldset>
										</div>
										<div class="col_30">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.contato" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="contato" maxlength="200" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.email" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="email"  maxlength="200"  />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.telefone1" />
												</label>
												<div style="height: 35px;">
													<input id="telefone1" type="text" name="telefone1"  maxlength="13" class="" />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.telefone2" />
												</label>
												<div style="height: 35px;">
													<input id="telefone2" type="text" name="telefone2"  maxlength="13" class="" />
												</div>
											</fieldset>
										</div>
										<div class="col_30">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.tipoSubscricao" />
												</label>
												<div style="height: 35px;">
													<select id="tipoSubscricao" name="tipoSubscricao"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.url" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="url"  maxlength="200" />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.login" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="login"  maxlength="200" />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<fmt:message key="citcorpore.controleContrato.senha" />
												</label>
												<div style="height: 35px;">
													<input type='password' name="senha"  maxlength="200" />
												</div>
											</fieldset>
										</div>

										<!-- VERSAO -->
										<div class="col_50">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><fmt:message key="citcorpore.controleContrato.versao" /></b>
											</div>
											<div class="col_100">
												<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
													<label  class="campoObrigatorio" style="margin-bottom: 3px; margin-top: 10px; float: left;">
														<fmt:message key="citcorpore.controleContrato.versao" />
													</label>
													<div style="float: left; width: 300px; padding-left: 0px;">
														<input type="hidden" id="idVersao" name="idVersao"/>
														<input type='text' id="nomeVersaoText" name="nomeVersaoText" maxlength="50" />
													</div>
													<img title="Adicionar" src="/citsmart/imagens/add.png" onclick="addGridVersao();" border="0" style="cursor:pointer; padding-top: 10px;">
												</fieldset>
											</div>
											<table class="table" id="tblVersao" style="width: 500px; margin-left: 15px;">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 90%;"><fmt:message key="citcorpore.controleContrato.versao" /></th>
												</tr>
											</table>
										</div>

										<!-- PAGAMENTOS -->
										<div class="col_50">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><fmt:message key="citcorpore.controleContrato.pagamento" /></b>
											</div>
											<div class="col_100">
												<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
													<div class="col_15" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="citcorpore.comum.data" />
															</label>
															<input type='text' id="dataPagamentoText" name="dataPagamentoText"  maxlength="200" class="datepicker" />
														</fieldset>
													</div>
													<div class="col_40" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="citcorpore.controleContrato.parcela" />
															</label>
															<input type='text' class="Format[Numero]" id="parcelaText" name="parcelaText" maxlength="200"  />
														</fieldset>
													</div>
													<div class="col_25" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="citcorpore.controleContrato.atraso" />
															</label>
															<input type='text' name="dataAtrasoText"  maxlength="200"  class="datepicker" />
														</fieldset>
													</div>
													<img title="Adicionar" src="/citsmart/imagens/add.png" onclick="addGridPagamento();" border="0" style="cursor:pointer; padding-top: 30px;">
												</fieldset>
											</div>
											<table class="table" id="tblPagamento" style="width: 850px; margin-left: 15px;">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.data" /></th>
													<th style="width: 35%;"><fmt:message key="citcorpore.controleContrato.parcela" /></th>
													<th style="width: 35%;"><fmt:message key="citcorpore.controleContrato.atraso" /></th>
												</tr>
											</table>
										</div>

										<!-- MODULOS ATIVOS -->
										<div class="col_100" style="border-top: 1px solid #ddd">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><fmt:message key="citcorpore.controleContrato.modulosAtivos" /></b>
											</div>
											<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
												<div id="divModulosAtivos">
													<table class="table" id="tblModulosAtivos" style="width: 850px; margin-left: 15px;">
														<tr>
															<th style="width: 16px !important;"></th>
															<th style="width: 90%;"><fmt:message key="citcorpore.controleContrato.modulos" /></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>

										<!-- TREINAMENTO -->
										<div class="col_50" style="border-top: 1px solid #ddd">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><fmt:message key="citcorpore.controleContrato.treinamento" /></b>
											</div>
											<div class="col_100">
												<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
													<div class="col_15" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="citcorpore.comum.data" />
															</label>
															<input type='text' name="dataTreinamentoText"  maxlength="200" class=" datepicker" />
														</fieldset>
													</div>
													<div class="col_40" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="citcorpore.controleContrato.treinamento" />
															</label>
															<input type='text' name="nomeTreinamentoText" maxlength="200" />
														</fieldset>
													</div>
													<div class="col_30" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="citcorpore.controleContrato.instrutor" />
															</label>
															<input type='hidden' name="idEmpregadoTreinamento" id="idEmpregadoTreinamento"  maxlength="200"  />
															<input type='text' name="nomeInstrutorTreinamentoText" onclick="abrePopupInstrutor()"  maxlength="200" style="width: 90% !important;" />
															<img style="vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
														</fieldset>
													</div>
													<img title="Adicionar" src="/citsmart/imagens/add.png" onclick="addGridTreinamento();" border="0" style="cursor:pointer; padding-top: 30px;">
												</fieldset>
											</div>
											<table class="table" id="tblTreinamento" style="width: 850px; margin-left: 15px;">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.data" /></th>
													<th style="width: 35%;"><fmt:message key="citcorpore.controleContrato.treinamento" /></th>
													<th style="width: 35%;"><fmt:message key="citcorpore.controleContrato.instrutor" /></th>
												</tr>
											</table>
										</div>

										<!-- OCORRENCIAS -->
										<div class="col_50" style="border-top: 1px solid #ddd">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><fmt:message key="citcorpore.controleContrato.ocorrencia" /></b>
											</div>
											<div class="col_100">
												<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
													<div class="col_15" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="citcorpore.comum.data" />
															</label>
															<input type='text' id="dataOcorrenciaText" name="dataOcorrencia"  maxlength="200" class="datepicker" />
														</fieldset>
													</div>
													<div class="col_40" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="citcorpore.controleContrato.assunto" />
															</label>
															<input type='text' id="assuntoOcorrenciaText" name="assuntoOcorrencia"  maxlength="200"  />
														</fieldset>
													</div>
													<div class="col_30" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<fmt:message key="citcorpore.comum.usuario" />
															</label>
															<input type='hidden' id="idEmpregadoOcorrencia" name="idEmpregadoOcorrencia" maxlength="200" />
															<input type='text' id="usuarioOcorrenciaText" name="usuarioOcorrenciaText" onclick="abrePopupUsuarioOcorrencia()" maxlength="200" style="width: 90% !important;" />
															<img style="vertical-align: middle;" src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
														</fieldset>
													</div>
													<img title="Adicionar" src="/citsmart/imagens/add.png" onclick="addGridOcorrencia();" border="0" style="cursor:pointer; padding-top: 30px;">
												</fieldset>
											</div>
											<table class="table" id="tblOcorrencia" style="width: 850px; margin-left: 15px;">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 20%;"><fmt:message key="citcorpore.comum.data" /></th>
													<th style="width: 35%;"><fmt:message key="citcorpore.controleContrato.assunto" /></th>
													<th style="width: 35%;"><fmt:message key="citcorpore.comum.usuario" /></th>
												</tr>
											</table>
										</div>
										<!--  Trecho de código comentado pois não estava claro a sua utilização na tela em questão
										<div class="col_100" style="border-top: 1px solid #ddd">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><fmt:message key="citcorpore.controleContrato.incidentes" /></b>
											</div>
											<fieldset>
												<div class="col_100" id="divIncidentesContrato" style="height: 200px;width: 65%; overflow: auto; padding-top: 10px;">
													<table class="table" id="tblIncidente" style="width: 65%">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 5%;"><fmt:message key="citcorpore.comum.solicitacao" /></th>
															<th style="width: 10%;"><fmt:message key="citcorpore.comum.servico" /></th>
															<th style="width: 5%;"><fmt:message key="contrato.contrato" /></th>
															<th style="width: 5%;"><fmt:message key="citcorpore.comum.usuario" /></th>
															<th style="width: 5%;"><fmt:message key="citcorpore.comum.responsavel" /></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>	-->
										<div class="col_100" style="padding-top: 30px; padding-bottom: 30px;">
											<button type='button' name='btnGravar' class="light"
												onclick='gravar();'>
												<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
												<span><fmt:message key="citcorpore.comum.gravar" />
												</span>
											</button>
											<button type='button' name='btnLimpar' class="light"
												onclick='limpar();document.form.fireEvent("load");'>
												<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
												<span><fmt:message key="citcorpore.comum.limpar" />
												</span>
											</button>
											<button type='button' name='btnExcluir' class="light"
												onclick='excluir();'>
												<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
												<span><fmt:message key="citcorpore.comum.excluir" />
												</span>
											</button>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section" style="padding-bottom: 10px;">
								<fmt:message key="citcorpore.comum.pesquisa" />
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_CONTROLECONTRATO' id='LOOKUP_CONTROLECONTRATO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->

	<%-- 	 <div id="POPUP_SERVICOCONTRATO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: auto !important">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px; width: 100% !important;">
							<form name='formServicoContrato'>
								<cit:findField formName='formServicoContrato'
								lockupName='LOOKUP_CATALOGOSERVICOCONTRATO'
								id='LOOKUP_CATALOGOSERVICOCONTRATO' top='0' left='0' len='550' heigth='400'
								javascriptCode='true'
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div> --%>
		<div id="POPUP_CONTRATO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div style="width: auto !important">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px; ">
							<form name='formContrato'style="width: 100% !important;">
								<cit:findField formName='formContrato'
								lockupName='LOOKUP_CONTRATOS'
								id='LOOKUP_CONTRATOS' top='0' left='0' len='550' heigth='400'
								javascriptCode='true'
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_INSTRUTOR" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="tabs" style="width: auto !important">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px; ">
							<form name='formTreinamento'style="width: 100% !important;">
								<cit:findField formName='formTreinamento'
								lockupName='LOOKUP_SOLICITANTE'
								id='LOOKUP_SOLICITANTE' top='0' left='0' len='550' heigth='400'
								javascriptCode='true'
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_USUARIOOCORRENCIA" title="<fmt:message key="citcorpore.comum.pesquisa" />">
			<div class="tabs" style="width: auto !important">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px; ">
							<form name='formUsuarioOcorrencia'style="width: 100% !important;">
								<cit:findField formName='formUsuarioOcorrencia'
								lockupName='LOOKUP_SOLICITANTE'
								id='LOOKUP_SOLICITANTEOCORRENCIA' top='0' left='0' len='550' heigth='400'
								javascriptCode='true'
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
		</div>
	</body>
</html>


