<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>	
<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/novoLayout/common/include/titulo.jsp" %>
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
<link rel="stylesheet" type="text/css" href="./css/tipoMovimFinanceiraViagem.css" />
<script type="text/javascript" src="./js/tipoMovimFinanceiraViagem.js"></script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="tipoMovimFinanceiraViagem.TipoMovimFinanceiraViagem" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="tipoMovimFinanceiraViagem.cadastroTipoMovimFinanceiraViagem" /></a></li>
					<li><a href="#tabs-2" class="round_top"><fmt:message key="tipoMovimFinanceiraViagem.pesquisaTipoMovimFinanceiraViagem" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/tipoMovimFinanceiraViagem/tipoMovimFinanceiraViagem'>
								<div class="columns clearfix">
									<input type='hidden' name='idtipoMovimFinanceiraViagem' /> 
									<div class="col_40">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="citcorpore.comum.nome" /></label>
											<div>
												<input type='text' name="nome" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="citcorpore.comum.classificacao"/></label>
											<div>
												<select name="classificacao" id ="classificacao" class="Valid[Required] Description[citcorpore.comum.classificacao]"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="citcorpore.comum.tipo"/></label>
											<div>
												<select name="tipo" id ="tipo" class="Valid[Required] Description[citcorpore.comum.tipo]"></select>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_15">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="tipoMovimFinanceiraViagem.exigePrestacaoContas"/></label>
											<div class="inline clearfix">
												<label><input type="radio" name="exigePrestacaoConta" value="S" checked="checked" class="Valid[Required] Description[tipoMovimFinanceiraViagem.exigePrestacaoContas]"/><fmt:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" name="exigePrestacaoConta" value="N" class="Valid[Required] Description[tipoMovimFinanceiraViagem.exigePrestacaoContas]"/><fmt:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="tipoMovimFinanceira.exigeDataHoraCotacao"/></label>
											<div class="inline clearfix">
												<label><input type="radio" name="exigeDataHoraCotacao" value="S" checked="checked" class="Valid[Required] Description[tipoMovimFinanceira.exigeDataHoraCotacao]"/><fmt:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" name="exigeDataHoraCotacao" value="N" class="Valid[Required] Description[tipoMovimFinanceira.exigeDataHoraCotacao]"/><fmt:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="tipoMovimFinanceiraViagem.permiteAdiantamento"/></label>
											<div class="inline clearfix">
												<label><input type="radio" name="permiteAdiantamento" value="S" checked="checked" class="Valid[Required] Description[tipoMovimFinanceiraViagem.permiteAdiantamento]"/><fmt:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" name="permiteAdiantamento" value="N" class="Valid[Required] Description[tipoMovimFinanceiraViagem.permiteAdiantamento]"/><fmt:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><fmt:message key="citcorpore.comum.situacao" /></label>
											<div  class="inline clearfix">
											<label><input type="radio" name="situacao" value="A" checked="checked" class="Valid[Required] Description[citcorpore.comum.situacao]"/><fmt:message key="citcorpore.comum.ativo" /></label>
											<label><input type="radio" name="situacao" value="I" class="Valid[Required] Description[citcorpore.comum.situacao]"/><fmt:message key="citcorpore.comum.inativo" /></label>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label style="margin-top: 5px;"><fmt:message key="tipoMovimFinanceiraViagem.valorPadrao" /></label>
											<div>
												<input type='text' name="valorPadrao" maxlength="6" style="width: 235px !important;" class="Format[Moeda] " />
											</div>
										</fieldset>
									</div>	
								</div>
								<div class="columns clearfix">
									<div class="col_80">
										<fieldset>
											<label><fmt:message key="citcorpore.comum.descricao" />
											</label>
											<div>
												<textarea name="descricao" cols='200' rows='5' maxlength="2000" ></textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_80">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message key="menu.imagem" /></label>
											<div id="docs_icons" class="uniformjs">
												<a href="#" class="glyphicons no-js airplane"><i></i>
												<label class="checkbox">
													<input class="Valid[Required] Description[menu.imagem]" type="radio" name="imagem" value="airplane" />
												</label>
												</a>
												<a href="#" class="glyphicons no-js coins"><i></i>
													<label class="checkbox">
														<input class="Valid[Required] Description[menu.imagem]" type="radio" name="imagem" value="coins" />
													</label>
												</a>
												<a href="#" class="glyphicons no-js building"><i></i>
													<label class="checkbox">
														<input class="Valid[Required] Description[menu.imagem]" type="radio" name="imagem" value="building" />
													</label>
												</a>
												<a href="#" class="glyphicons no-js car"><i></i>
													<label class="checkbox">
														<input class="Valid[Required] Description[menu.imagem]" type="radio" name="imagem" value="car" />
													</label>
												</a>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<button type='button' name='btnGravar' class="light" onclick='document.form.save();'>
									<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnExcluir' class="light" onclick='excluir();'>
									<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message key="citcorpore.comum.excluir" /></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_TIPOMOVIMFINANCEIRAVIAGEM' id='LOOKUP_TIPOMOVIMFINANCEIRAVIAGEM' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
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
</html>


