<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
<%@include file="/include/menu/menuConfig.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<link rel="stylesheet" type="text/css" href="./css/resumoSolicitacoesServicos.css" />

<script type="text/javascript" src="./js/resumoSolicitacoesServicos.js"></script>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>

	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2><fmt:message key="resumoSolicitacoesServicos.Solicitacoes/IncidentesSolicitadosPorMim" /></h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><fmt:message key="resumoSolicitacoesServicos.Solicitacoes/IncidentesSolicitadosPorMim" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div style="height: 500px; overflow: scroll;"  class="block" >
						<div id="parametros">
							<form name='form' action='${ctx}/pages/resumoSolicitacoesServicos/resumoSolicitacoesServicos'>
								<input type="hidden" id='idSolicitante' name='idSolicitante'>
								<input type="hidden" id='idItemConfiguracao' name='idItemConfiguracao'>
								<div class="columns clearfix">
									<div class="col_33">
										<fieldset>
											<label><fmt:message key="itemConfiguracao.itemConfiguracao" /></label>
											<div>
												<input type="text" onfocus='abrePopupIC();' id="nomeItemConfiguracao" name="nomeItemConfiguracao" />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
									<fieldset>
										<button type='button' name='btnPesquisar' class="light"  onclick="filtrar()" style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><fmt:message key="citcorpore.comum.pesquisar" /></span>
										</button>
									<button type='button' name='btnLimpar' class="light"  onclick='limpar()' style="margin: 20px !important;">
											<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
											<span><fmt:message key="citcorpore.comum.limpar" /></span>
										</button>
									</fieldset>
									</div>
									<div  align="right">
												<img width="20" height="20" alt="Ativa o temporizador" id="imgAtivaTimer" style="opacity:0.5;cursor: pointer;" title="<fmt:message key="resumoSolicitacoesServicos.ativarDesativarTemporizador"/>"
													 src="${ctx}/template_new/images/cronometro.png">
										<!--		<input type='checkbox' onchange="ativarDesativarTimer()"/>-->
									</div>
								</div>
							</form>
						</div>

						<div id="tblResumo" align="center">
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
	<div id="POPUP_SOLICITANTE" title="<fmt:message key="citcorpore.comum.pesquisa"/>">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<cit:findField formName='formPesquisaUsuario'
								lockupName='LOOKUP_USUARIO' id='LOOKUP_SOLICITANTE' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_ITEMCONFIG" title="<fmt:message key="citcorpore.comum.identificacao" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisa' style="width: 540px">
						<cit:findField formName='formPesquisa'
 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</html>

