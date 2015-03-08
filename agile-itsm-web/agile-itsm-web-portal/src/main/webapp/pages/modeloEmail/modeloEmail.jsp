<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

    	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
    	<script type="text/javascript">
    		var ctx = "${ctx}"
    	</script>
		<script type="text/javascript" src="./js/modeloEmail.js"></script>

	</head>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2><fmt:message key="modeloemail.modeloemail"/></h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><fmt:message key="modeloemail.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><fmt:message key="modeloemail.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action='${ctx}/pages/modeloEmail/modeloEmail'>
									<div class="columns clearfix">
										<input type='hidden' name='idModeloEmail'/>

										<div class="columns clearfix">
											<div class="col_1000">
												<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio" ><fmt:message key="baseConhecimento.titulo"/></label>
														<div>
														  	<input type='text' name="titulo" maxlength="255" size="65" class="Valid[Required] Description[baseConhecimento.titulo]" />
														</div>
												</fieldset>
												</div>
												<div class="col_50">
												<fieldset style="height: 61px; !important">
													<label class="campoObrigatorio"><fmt:message key="solicitacaoServico.situacao"/></label>
														<div>
														  	<input id="situacao" value="A" checked="checked" class="Valid[Required] Description[solicitacaoServico.situacao]" type="radio" name="situacao" ><fmt:message key="citcorpore.comum.ativo"/>
				                                    		<input id="situacao" value="I" class="Valid[Required] Description[solicitacaoServico.situacao]" type="radio" name="situacao" ><fmt:message key="citcorpore.comum.inativo"/>
														</div>
												</fieldset>
												</div>
											</div>
										</div>

										<div class="columns clearfix">
											<div class="col_50">
												<fieldset>
													<label><fmt:message key="modeloemail.texto"/></label>
														<div>
														  	<textarea id="texto" name="texto" rows="3" cols="80" maxlength="2000" style="display: block;"></textarea>
														</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio" ><fmt:message key="citSmart.comum.identificador"/></label>
														<div>
														  	<input type='text' name="identificador" maxlength="20" size="20" class="Valid[Required] Description[citSmart.comum.identificador]" />
														</div>
												</fieldset>
											</div>
										</div>
									</div>

									<button type='button' name='btnGravar' class="light"  onclick='salvar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span><fmt:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
										<span><fmt:message key="citcorpore.comum.limpar"/></span>
									</button>
								</form>
							</div>
						</div>

						<div id="tabs-2" class="block">
							<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_MODELOEMAIL' id='LOOKUP_MODELOEMAIL'  top='0' left='0' len='550' heigth='400'
													javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>

