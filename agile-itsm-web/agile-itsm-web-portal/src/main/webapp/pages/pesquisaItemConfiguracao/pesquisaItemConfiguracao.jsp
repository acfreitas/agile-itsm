<%@page import="br.com.citframework.tld.I18N"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.PesquisaItemConfiguracaoDTO"%>

<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor"%>

<!DOCTYPE html>
<compress:html
	enabled="true"
	jsCompressor="closure"
	compressCss="true"
	compressJavaScript="true"
	removeComments="true"
	removeMultiSpaces="true">
<html>
<head>
<%
    String iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>

<%
    //se for chamado por iframe deixa apenas a parte de cadastro da página
    if (iframe != null) {
%>

<link type="text/css" rel="stylesheet" href="./css/pesquisaItemConfiguracao.css"/>

<%
    }
%>
</head>
<body>
<div id="wrapper">
	<%
	    if (iframe == null) {
	%>
	<%@include file="/include/menu_vertical.jsp"%>
	<%
	    }
	%>
		<form name='form2' action='${ctx}/pages/solicitacaoServico/solicitacaoServico'>
			<input type="hidden" id="idItemConfiguracao" name="idItemConfiguracao">
		</form>
		<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
		<%
		    if (iframe == null) {
		%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%
		    }
		%>
			<div class="flat_area grid_16">
				<h2><fmt:message key="pesquisa.pesquisa" /></h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="citcorpore.comum.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div style="height: 400px; overflow-y: auto;" class="block">
						<div  class="">
							<form name='form'
								action='${ctx}/pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao'>
								<div class="columns clearfixs">
									<div class="columns clearfix">
									<input type='hidden' name='idPesquisaItemConfiguracao' id='idPesquisaItemConfiguracao'/>
									<input type='hidden' name='idItemConfiguracao' id='idItemConfiguracao'/>
									<input type='hidden' name='idGrupoItemConfiguracao' id ="idGrupoItemConfiguracao" />
									<input type="hidden" name="idItemConfiguracaoFilho" id="idItemConfiguracaoFilho"/>

										<div class="col_60">
											<fieldset>
												<label><fmt:message key="itemConfiguracao.itemConfiguracao" /></label>
												<div>
													<input style="width: 90% !important;" type='text' id="ip" name="ip" readonly="readonly"
														maxlength="70" size="70" class="Valid[Required] Description[itemConfiguracao.itemConfiguracao]" />
													<img  id="addip"  style=" cursor: pointer; vertical-align: middle;"
														src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
												</div>
											</fieldset>
										</div>
									  	<% if (iframe != null) {%>
											<div class="col_100">
												<div class="col_40">
													<fieldset>
														<div style="height:50px" class="inline clearfix" >
															<label>
																<input type="checkbox" id="itemRelacionado" onclick="divItemFilho(this)" name="itemRelacionado" value="S" /><fmt:message key="pesquisa.pesquisarItemRelacionado" />
															</label>
														</div>

													</fieldset>
												</div>
												<div id="divItemFilho" style="display: none;" class="col_60">
													<div>
														<label>
															<fmt:message key="pesquisa.identificacaoItemRelacionado" />
														</label>
														<input style="width: 90% !important;" type='text' id="identificacaoFilho" name="identificacaoFilho" readonly="readonly"
															maxlength="70" size="70" class="Valid[Required] Description[itemConfiguracao.itemConfiguracao]" />
														<img id="addipFilho"  style=" cursor: pointer; vertical-align: middle;"
															src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
													</div>
												</div>
											</div>
										<% } %>
										<div class="col_33">
											<fieldset>
												<label>
													<fmt:message key="pesquisa.grupo" />:
												</label>
												<div>
													<input onclick="consultarGrupoItemConfiguracao()" readonly="readonly" style="width: 90% !important;" type='text' name="nomeGrupoItemConfiguracao" maxlength="70" size="70"  />
													<img onclick="consultarGrupoItemConfiguracao()" style=" vertical-align: middle;"
														src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
												</div>
											</fieldset>
										</div>
										<div  class="col_33">
											<fieldset>
												<label>
													<fmt:message key="pesquisa.tipoExecucao" />
												</label>
												<div style="height:35px" class="inline clearfix">
													<label>
														<input type="checkbox" id="instalacao" name="instalacao" value="I" /><fmt:message key="pesquisa.instalacao" />
													</label>
													<label>
														<input type="checkbox" id="desinstalacao" name=desinstalacao value="D" /><fmt:message key="pesquisa.desinstalacao" />
													</label>
													<label>
														<input type="checkbox" checked="checked" id="inventario" name=inventario value="inventario" /><fmt:message key="pesquisa.inventario" />
													</label>
												</div>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset>
												<label>
													<fmt:message key="pesquisa.datainicio" />
												</label>
												<div>
													<input id="dataInicio" type="text" name="dataInicio" maxlength="10"
														class="Valid[Data] Description[pesquisa.datainicio] Format[Data] datepicker" />
												</div>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset>
												<label>
													<fmt:message key="pesquisa.datafim" />
												</label>
												<div>
													<input id="dataFim" type="text" name="dataFim" maxlength="10"
														class="Valid[Data] Description[pesquisa.datafim] Format[Data] datepicker" />
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_100">
										<div  style="display: block; float: right; margin-top: 7px;">
											<button type="button" name="btnlimpar" class="light" onclick="document.form.clear();limparGrid();checkedTipoExecucao();">
												<img src="${ctx}/template_new/images/icons/small/grey/clear.png" />
												<span><fmt:message key="citcorpore.comum.limpar" /></span>
											</button>
										</div>
										<div style="display: block; float: right; margin-top: 7px;">
											<button type="button" name="btnpesquisa" class="light" onclick="pesquisa();">
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png" />
												<span><fmt:message key="citcorpore.comum.pesquisa" /></span>
											</button>
											<button type="button" name="btnverificarExpiracao" class="light" onclick="verificarExpiracao();">
												<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
												<span><fmt:message key="pesquisa.verificarExpiracao" /></span>
											</button>
										</div>
									</div>
									<div id="divPesquisaItemConfiguracao"></div>
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

	<div id="POPUP_ITEMCONFIG" title="<fmt:message key="citcorpore.comum.identificacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisa' style="width: 540px">
							<cit:findField formName='formPesquisa'
	 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO_NAO_DESATIVADOS' id='LOOKUP_PESQUISAITEMCONFIGURACAO_NAO_DESATIVADOS' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div id="POPUP_ITEMCONFIGFILHO" title="<fmt:message key="citcorpore.comum.identificacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaFilho' style="width: 540px">
							<cit:findField formName='formPesquisaFilho'
	 							lockupName='LOOKUP_ITENSCONFIGURACAORELACIONADOS_NAO_DESATIVADOS' id='LOOKUP_ITENSCONFIGURACAORELACIONADOS_NAO_DESATIVADOS' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>



	<div id="POPUP_GRUPOITEMCONFIGURACAO" title="<fmt:message key="pesquisa.grupo" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaGrupo' style="width: 540px">
							<cit:findField formName='formPesquisaGrupo'
								lockupName='LOOKUP_GRUPOITEMCONFIGURACAO' id='LOOKUP_GRUPOITEMCONFIGURACAO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_ATIVOS" title="<fmt:message key="pesquisa.listaAtivosDaMaquina" />" style="overflow: hidden;">
		<div class="box grid_16 tabs" >
			<div class="toggle_container" >
				<div id="tabs-2" class="block" style="overflow: hidden;">
					<div class="section" style="overflow: hidden;">
						<iframe id="iframeAtivos" style="display: block; margin-left: -20px;" name="iframeAtivos" width="970" height="480" >
						</iframe>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		var iframe = "${iframe}";
	</script>

	<script type="text/javascript" src="js/pesquisaItemConfiguracao.js"></script>
</body>

</html>
</compress:html>
