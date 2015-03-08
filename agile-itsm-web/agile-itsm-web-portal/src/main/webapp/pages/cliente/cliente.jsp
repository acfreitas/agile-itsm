<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
	<%@include file="/include/security/security.jsp" %>
	<%@include file="/include/header.jsp"%>
	<title><fmt:message key="citcorpore.comum.title" /></title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<script>

	addEvent(window, "load", load, false);
	function load(){
	document.form.afterRestore = function () {
	$('.tabs').tabs('select', 0);
	}
	}

	function LOOKUP_CLIENTE_select(id,desc){
		document.form.restore({idCliente:id});
	}
</script>
</head>
<body>
	<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>

		<div class="flat_area grid_16">
				<h2>Cliente</h2>
		</div>

		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1">Cadastro de Cliente </a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top">Pesquisa Cliente </a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	<div class="toggle_container">
	<div id="tabs-1" class="block">
	<div class="section">
		<form name='form' action='${ctx}/pages/cliente/cliente'>

		<div class="columns clearfix">
			<input type='hidden' name='idCliente'/>
			<div class="col_50">
				<fieldset>
					<label>Raz&atilde;o Social</label>
						<div>
						  <input type='text' name="nomeRazaoSocial" maxlength="70" size="70" class="Valid[Required] Description[Razão Social]" />
						</div>
				</fieldset>
			</div>
			<div class="col_50">
				<fieldset>
					<label>Nome Fantasia</label>
						<div>
						  <input type='text' name="nomeFantasia" maxlength="70" size="70" class="Valid[Required] Description[Nome Fantasia]"/>
						</div>
				</fieldset>
			</div>
		</div>
		<div class="columns clearfix">
			<div class="col_50">
				<fieldset>
					<label><span class="campoEsquerda">CPF/CNPJ</span></label>
						<div>
						  <input type='text' name="cpfCnpj" maxlength="14" size="14" />
						</div>
				</fieldset>
			</div>
			<div class="col_50">
				<fieldset>
					<label><span class="campoEsquerda">Situa&ccedil;&atilde;o</span></label>
						<div>
						  <select name='situacao' class="Valid[Required] Description[Situação]"></select>
						</div>
				</fieldset>
			</div>
		</div>

		<div class="columns clearfix">
		  <div class="col_100">
				<fieldset>
					<label><span class="campoEsquerda">Observa&ccedil;&otilde;es</span></label>
						<div>
						  <textarea name="observacoes" cols='70' rows='5'></textarea>
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
										<cit:findField formName='formPesquisa' lockupName='LOOKUP_CLIENTE' id='LOOKUP_CLIENTE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
