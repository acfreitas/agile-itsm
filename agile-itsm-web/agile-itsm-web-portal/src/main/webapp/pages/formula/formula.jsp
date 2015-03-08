<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
	<%@include file="/include/header.jsp"%>

    <%@include file="/include/security/security.jsp" %>
	<title><fmt:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<script type="text/javascript" src="./js/formula.js"></script>
</head>
<body>
<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>

		<div class="flat_area grid_16">
				<h2><fmt:message key="formula.formula"/></h2>
		</div>

  <div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><fmt:message key="formula.cadastro"/></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><fmt:message key="formula.pesquisa"/></a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	 <div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form' action='${ctx}/pages/formula/formula'>
								<div class="columns clearfix">
									<input type='hidden' name='idFormula'/>

									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message
													key="formula.nome" />
											</label>
											<div>
												<input type='text' name="nome"
													maxlength="120" size="120"
													class="Valid[Required] Description[formula.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message
													key="formula.identificador" />
											</label>
											<div>
												<input type='text' name="identificador"
													maxlength="120" size="120"
													class="Valid[Required] Description[formula.identificador]" />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio"><fmt:message
													key="formula.conteudo" />
											</label>
											<div>
												<textarea name="conteudo" rows="20" cols="80" maxLength="10000" style='font-family: courier new; font-size: 11px' class="Valid[Required] Description[formula.conteudo]"></textarea>
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
							</form>

						</div>
					</div>
					<div id="tabs-2" class="block" >
							<div  class="section">
									<fmt:message key="citcorpore.comum.pesquisa"/>
									<form name='formPesquisa'>
										<cit:findField formName='formPesquisa' lockupName='LOOKUP_FORMULA' id='LOOKUP_FORMULA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true'   />
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


