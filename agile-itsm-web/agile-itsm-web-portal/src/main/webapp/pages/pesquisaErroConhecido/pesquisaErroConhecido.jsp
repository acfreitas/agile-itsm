<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!DOCTYPE html>
<html>
<head>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<title><fmt:message key="citcorpore.comum.title"/></title>

	<link type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery.ui.all.css"/>
	<link type="text/css" rel="stylesheet" href="${ctx}/pages/pesquisaErroConhecido/css/pesquisaErroConhecido.css" />

	<script type="text/javascript" src="${ctx}/js/dtree.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-ui-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.layout-latest.js"></script>
	<script type="text/javascript" src="${ctx}/js/debug.js"></script>
	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>

	<c:set var="iframe" value="false" />
	<c:if test="${not empty param.iframe}">
		<c:set var="iframe" value="${param.iframe}" />
	</c:if>

	<script type="text/javascript">
		var retorno = "${ctx}/pages/index/index.load";
	</script>

	<script type="text/javascript" src="${ctx}/pages/pesquisaErroConhecido/js/pesquisaErroConhecido.js"></script>
</head>

<body>
	<c:if test="${not iframe}">
		<div class="ui-layout-north">
			<div id="divLogo" style="overflow: hidden!important;">
				<table cellpadding='0' cellspacing='0'>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td>
							<img border="0" src="${ctx}/imagens/logo/logo.png" />
						</td>
					</tr>
				</table>
			</div>

			<div id="divControleLayout" style="position: fixed;top:1%;right: 2%;z-index: 100000;float: right;display: block; ">
				<table cellpadding='0' cellspacing='0' width="100" style="width: 100%;">
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td width="100" style="display: block; float: left;">
							<button type="button" class="light img_icon has_text" style='text-align: right; margin-left: 99%; float: right; display: block;' onclick="voltar()" title="<fmt:message key="citcorpore.comum.voltarprincipal" />">
								<img border="0" title="<fmt:message key='citcorpore.comum.voltarprincipal' />" src="${ctx}/imagens/back.png" /><span style="padding-left: 0px !important;"><fmt:message key="citcorpore.comum.voltar" /></span>
							</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</c:if>

	<!-- <div id="wrapper" class="principal"> -->
	<div class="ui-layout-west">
		<div class="pastas">
			<form name='form2' onsubmit='javascript:return false;' action='${ctx}/pages/pesquisaErroConhecido/pesquisaErroConhecido'>
				<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento'/>
				<input type='hidden' id='idPasta' name='idPasta'/>

				<div>
					<div style="" id="principalBaseConhecimento"></div>
				</div>
			</form>
		</div>
	</div>
	<div class="ui-layout-center">
		<div id="baseconhecimento" class="baseconhecimento container_16 clearfix" style=" padding: 0px !important;">
			<div class="flat_area grid_16" style="padding-bottom: 1px !important;margin-top: -1px;"></div>

			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<span class='manipulaDiv' style='cursor: pointer'><span id='spanPesq' style='cursor: pointer'><fmt:message key="baseConhecimento.esconderPesquisa" /></span> &nbsp;<img src="${ctx}/imagens/search.png" /></span>
						<div id="divpesquisa" class="section">
							<form name='formPesquisa' id='formPesquisa' onsubmit='javascript:return false;' action='${ctx}/pages/pesquisaErroConhecido/pesquisaErroConhecido'>
								<div class="flat_area grid_16"><h2><fmt:message key="baseConhecimento.erroConhecido" /></h2></div>
									<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento'/>

									<div class="columns clearfix">
										<div id='divBotaoFechar' style='top: 0px; left: 220px; z-index: 10000;'>
											<fieldset>
												<div class="col_66">
													<label style="font:bold 13px arial,serif;"><fmt:message key="baseConhecimento.pesquisar" /></label>
													<div>
														<input onkeydown="if ( event.keyCode == 13 || event.which == 13 ) pesquisarErroConhecido();" type='text' name='termoPesquisa' size='40' maxlength="200" />
													</div>
												</div>
												<div class="col_15">
													<button title='Pesquisa' type='button' id="btnPesquisar" name='btnPesquisar' style=" margin-top: 17px;margin-left: -30px;" class="light" onclick='pesquisarErroConhecido()'>
														<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
														<span><fmt:message key="baseConhecimento.pesquisar"/></span>
													</button>
												</div>
											</fieldset>
											<table>
												<tr>
													<td>
														<div id='resultPesquisaPai' style='display: none; border: 1px solid black; background-color: white; height: 400px; width: 650px; overflow: auto'>
															<table>
																<tr>
																	<td>
																		<div style="margin: 10px !important;" id='resultPesquisa'></div>
																	</td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</table>
										</div>
									</div>
								</form>
							</div>

							<div class="section" id="conhecimento">
								<form id="form" name='form' onsubmit='javascript:return false;' action='${ctx}/pages/pesquisaErroConhecido/pesquisaErroConhecido'>
									<div class="flat_area grid_16">
										<input type='text' id="tituloconhecimento" name="titulo"/>
									</div>

									<div class="columns clearfix">
										<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento'/>
										<input type='hidden' name='idBaseConhecimentoPai'/>
										<input type='hidden' name='dataInicio'/>

										<div class="columns clearfix">
											<div class="col_25">
												<fieldset style="height: 58px">
													<label><fmt:message key="pasta.pasta"/></label>
													<div>
														<input type='text' id="nomePasta" name="nomePasta"/>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="columns clearfix">
											<div class="col_50">
												<fieldset>
													<label><fmt:message key="baseConhecimento.conteudo"/></label>
													<div id="conteudo"></div>
												</fieldset>
											</div>
										</div>
									</div>
								</form>
							</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
