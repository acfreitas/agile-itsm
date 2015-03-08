<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/include/header.jsp"%>

    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<script type="text/javascript" src="./js/importanciaNegocio.js"></script>
</head>
<body>
<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>

		<div class="flat_area grid_16">
				<h2><fmt:message key="importanciaNegocio.importanciaNegocio"/></h2>
		</div>

  <div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><fmt:message key="importanciaNegocio.cadastroImportanciaDeNegocio"/></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><fmt:message key="importanciaNegocio.pesquisaImportanciaDeNegocio"/></a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	 <div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form' action='${ctx}/pages/importanciaNegocio/importanciaNegocio'>
								<div class="columns clearfix">
									<input type='hidden' name='idEmpresa'/>
									<input type='hidden' name='idImportanciaNegocio' />
									<input type='hidden' name='situacao' value="A" />

									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message
													key="importanciaNegocio.nomeImportanciaDeNegocio" />
											</label>
											<div>
												<input type='text' name="nomeImportanciaNegocio"
													maxlength="100"
													class="Valid[Required] Description[importanciaNegocio.nomeImportanciaDeNegocio]" />
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>

								<button type='button' name='btnGravar' class="light"
									onclick='document.form.save();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message
											key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type="button" name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message
											key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='excluir();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
									<span><fmt:message
											key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>

						</div>
					</div>
					<div id="tabs-2" class="block" >
							<div  class="section">
									<fmt:message key="citcorpore.comum.pesquisa"/>
									<form name='formPesquisa'>
										<cit:findField formName='formPesquisa' lockupName='LOOKUP_IMPORTANCIANEGOCIO' id='LOOKUP_IMPORTANCIANEGOCIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true'   />
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


