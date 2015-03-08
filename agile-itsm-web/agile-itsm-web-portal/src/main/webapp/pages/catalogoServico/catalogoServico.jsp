<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/include/header.jsp"%>

	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<script type="text/javascript" src="../../cit/objects/InfoCatalogoServicoDTO.js"></script>


	<title><fmt:message key="citcorpore.comum.title" /></title>

	<link rel="stylesheet" type="text/css" href="./css/catalogoServico.css" />

	<script type="text/javascript" src="./js/catalogoServico.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="catalogoServico.catalogoNegocio" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message	key="catalogoServico.cadastroCatalogoNegocio" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="catalogoServico.pesquisaCatalogoNegocio" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/catalogoServico/catalogoServico'>
								<div class="columns clearfix">
									<input type="hidden" id="idCatalogoServico" name="idCatalogoServico"/>
									<input type="hidden" id="idServicoContrato" name="idServicoContrato"/>
									<input type="hidden" id="idContrato" name="idContrato"/>
									<input type="hidden" id="servicoSerialize" name="servicoSerialize"/>
									<input type="hidden" id="infoCatalogoServicoSerialize" name="infoCatalogoServicoSerialize"/>
									<input type="hidden" id="rowIndex" name="rowIndex"/>
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome" /></label>
											<div>
												<input type='text' name="tituloCatalogoServico"  maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
											<label class="campoObrigatorio"><fmt:message key="contrato.contrato" /></label>
											<div>
												<div>
													<input  readonly="readonly" style="width: 90% !important;"
														type='text' name="nomeContrato" id="nomeContrato" onclick="abrePopupContrato()" maxlength="50" size="50" class="Valid[Required] Description[contrato.contrato]" />
													<img  style=" vertical-align: middle;"
														src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
												</div>
											</div>
										</fieldset>
									</div>
									<div class="col_66">
									<br>
										<fieldset>
											<legend>
												<fmt:message key="catalogoServico.informacoes_servico" />
											</legend>
											<label><fmt:message key="servico.servico" /></label>
											<div>
												<input  readonly="readonly" style="width: 90% !important;"
													type='text' name="nomeServicoContrato" id="nomeServicoContrato" onclick="abrePopupServico()" maxlength="50" size="50" class="" />
												<img  style=" vertical-align: middle;"
													src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
											</div>
											<label>
												<fmt:message key="citcorpore.comum.nome" />
											</label>
											<div>
												<input type='text' id="nomeCatalogoServico" name="nomeCatalogoServico" maxlength="100" />
												<button id="buttonAddLimpar" type="button" title="Limpa os dados de informações de serviço" class="light" onclick="limpaDadosTableInfo()" style="float: right !important;">
													<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
													<span>
														<fmt:message key="citcorpore.comum.limpar" />
													</span>
												</button>
												<button id="buttonAddInfoServico" type="button" class="light" onclick="addItemInfo()" style="float: right !important;">
													<img src="${ctx}/template_new/images/icons/small/grey/list.png">
													<span>
														<fmt:message key="citcorpore.comum.adicionar" />
													</span>
												</button>
											</div>
											<label id="infoComplementar"><fmt:message key="citcorpore.comum.descricao" /></label>
											<div>
												<div>
													<!-- <textarea id="descCatalogoServico" name="descCatalogoServico" onkeyDown="contCaracteres(this.form.descCatalogoServico,this.form.contDin,255);" onKeyUp="textCounter(this.form.descCatalogoServico,this.form.contDin,255);"  rows="10" cols="100"></textarea> -->
												<textarea id="descCatalogoServico" name="descCatalogoServico" onkeyup="contCaracteres(this.value)" onkeydown="contCaracteres(this.value)" onFocus="contCaracteres(this.value)" rows="10" cols="100"></textarea>
												</div>
 												(<fmt:message key="citcorpore.comum.caracteresrestantes"/>: <span id="cont">255</span>)<br>

											</div>
										</fieldset>
									</div>

									<div class="col_66">
										<fieldset>
											<div>
												<table class="table tabelaFixa" id="tblInfoCatalogoServico" >
													<tr>
														<th style="width: 3%;"></th>
														<th style="width: 8%;"><fmt:message key="#" /></th>
														<th style="width: 30%;"><fmt:message key="servico.servico" /></th>
														<th style="width: 20%;"><fmt:message key="citcorpore.comum.nome" /></th>
														<th style="width: 40%;"><fmt:message key="citcorpore.comum.descricao" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
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
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_CATALOGOSERVICO' id='LOOKUP_CATALOGOSERVICO'
										top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_SERVICOCONTRATO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<%-- <div class="box grid_16 tabs" style="width: auto !important">
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
		</div> --%>
	</div>
	<div id="POPUP_CONTRATO" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs" style="width: auto !important">
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
	<div id="POPUP_DETALHES" title="<fmt:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs" style="width: auto !important">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px; width: 100% !important;">
						<form name='formDetalhe'>
							<cit:findField formName='formDetalhe'
							lockupName='LOOKUP_CATALOGOSERVICOCONTRATO'
							id='LOOKUP_CATALOGOSERVICOCONTRATO' top='0' left='0' len='550' heigth='400'
							javascriptCode='true'
							htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>

</html>
