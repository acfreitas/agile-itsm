<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/security/security.jsp" %>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
	<title>CIT Corpore</title>
	<%@include file="/include/header.jsp" %>
</head>
<body>
	<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>

		<div class="flat_area grid_16">
				<h2>Funcionalidade</h2>
		</div>

		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1">Cadastro de Funcionalidade </a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top">Pesquisa Funcionalidade </a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	<div class="toggle_container">
	<div id="tabs-1" class="block">
	<div class="section">
	<form name='form' action='${ctx}/pages/funcionalidade/funcionalidade'>

		<div class="columns clearfix">
			<input type='hidden' name='idFuncionalidade'/>
			<div class="col_100">
				<fieldset class="label_side">
					<label>Nome</label>
						<div>
						 <input type='text' name="nome" maxlength="70"
									class="Valid[Required] Description[Nome]"
									title="Informe o nome da Funcionalidade." />
						</div>
				</fieldset>
			</div>`
			</div>
			<div class="columns clearfix">
			<div class="col_100">
				<fieldset class="label_side">
					<label>Descri&ccedil;&atilde;o</label>
						<div>
						  <textarea name="descricao"

										class="Valid[Required] Description[Descrição]"
										title="Informe a descrição da Funcionalidade.">
									</textarea>
						</div>
				</fieldset>
			</div>
		</div>


		<br><br>

<button type='button' name='btnGravar' class="light"  onclick='document.form.save();'>
<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gravar Dados</span>
</button>
<button type='button' name='btnLimpar' class="light" onclick='document.form.save();'>
<img src="${ctx}/template_new/images/icons/small/grey/trashcan.png">
<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Limpar Dados</span>
</button>




			</form>





	</div>
						</div>
<div id="tabs-2" class="block">
							<div class="section">


									Pesquisa
									<form name='formPesquisa'>
										<cit:findField formName='formPesquisa' lockupName='LOOKUP_CONTRATOS' id='LOOKUP_CONTRATOS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
