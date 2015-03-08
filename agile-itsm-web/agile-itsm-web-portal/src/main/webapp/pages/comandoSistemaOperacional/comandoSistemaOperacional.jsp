<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.Constantes"%>

<!doctype html public "">
<html>
<head>

<%@include file="/include/security/security.jsp" %>
<%@include file="/include/header.jsp"%>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
	
</head>
<body>
	<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
	<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">
		<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
					<h2><fmt:message key="comandoso.comandoso"/></h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1"><fmt:message key="comandoso.cadastro"/></a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top"><fmt:message key="comandoso.pesquisa"/></a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
				<div id="tabs-1" class="block">
					<div class="section">
						<form name='form' action='${ctx}/pages/comandoSistemaOperacional/comandoSistemaOperacional'>
							<input type='hidden' name='id' />
							<div class="columns clearfix">
								<div  class="col_66">
									<fieldset style="height: 65px">
										<label class="campoObrigatorio" onclick="alerta();"><fmt:message key="comandoso.comandoso"/></label>
											<div>
												<input type="text" id="comando" name="comando" maxlength="80" class="Valid[Required] Description[comandoso.comandoso]" />
											</div>
									</fieldset>
								</div>
								<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="comandoso.sistema"/>
										<img src="${ctx}/imagens/add.png"
													onclick="abrePopup('sistemaOperacional', 'load')">
										</label>
											<div>
												<select name='idSistemaOperacional' id='idSistemaOperacional' class="Valid[Required] Description[comandoso.sistema]"></select>
											</div>
									</fieldset>
								</div>
							</div>
							<div class="columns clearfix">
								<div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><fmt:message key="comandoso.funcao"/>
											<img src="${ctx}/imagens/add.png" onclick="abrePopup('comando', 'load')">
										</label>
											<div>
												<select name='idComando' id='idComando' class="Valid[Required] Description[comandoso.funcao]"></select>
											</div>
									</fieldset>
								</div>
							</div>

							<div id="popupCadastroRapido">
								<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%">
								</iframe>
							</div>
							<br><br>


							<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
							<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
							<span><fmt:message key="citcorpore.comum.gravar"/></span>
							</button>


							<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
							<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
							<span><fmt:message key="citcorpore.comum.limpar"/></span>
							</button>
						</form>
				</div>
				</div>

				<div id="tabs-2" class="block">
					<div class="section">
							<fmt:message key="citcorpore.comum.pesquisa"/>
							<!-- EDITAR QUANDO COLOCAR FUNCIONALIDADE -->
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_COMANDOSISTEMAOPEARCIONAL' id='LOOKUP_COMANDOSISTEMAOPEARCIONAL' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
		
		<script type="text/javascript" src="js/comandoSistemaOperacional.js"></script>
	</body>
</html>
