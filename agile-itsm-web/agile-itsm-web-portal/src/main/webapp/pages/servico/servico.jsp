<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp" %>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="pt-br" class="no-js"> <!--<![endif]-->

	<title>CIT Corpore</title>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			document.getElementById('tabTela').tabber.tabShow(0);
		}
	}

	function LOOKUP_CONTRATOS_select(id,desc){
		document.form.restore({idContrato:id});
	}

	$(function() {
		   $('.datepicker').datepicker();
		  });
</script>
</head>
<body>
	<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>

		<div class="flat_area grid_16">
				<h2>Serviço</h2>
		</div>

		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1">Cadastro de Serviço</a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top">Pesquisa Serviço</a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	<div class="toggle_container">
	<div id="tabs-1" class="block">
	<div class="section">
	<form name='form' action='${ctx}/pages/servico/servico'>
		<div class="columns clearfix">
			<input type='hidden' name='idContrato'/>
			<div class="col_66">
				<fieldset style="height: 61px">
					<label>Categoria</label>
						<div>
						  <select name='idCliente' class="Valid[Required] Description[Cliente]">
											            	</select>
						</div>
				</fieldset>
			</div>
			<div class="col_33">
				<fieldset>
					<label>Data de Início</label>
						<div>
						  <input type='text' name="dataInicio" maxlength="10" size="10" class="Valid[Required,Date] Format[Data] datepicker Description[Data de Início]" />
						</div>
				</fieldset>
			</div>
		</div>
		<div class="columns clearfix">

			<div class="col_100">
				<fieldset>
					<label>Detalhamento do Serviço</label>
						<div>
						  <textarea name="objeto" cols='70' rows='5' class="Valid[Required] Description[Objeto]"></textarea>
						</div>
				</fieldset>
			</div>
		</div>

		<div class="columns clearfix">
		  <div class="col_50">
				<fieldset>
					<label>Tipo de Serviço</label>
						<div>
						  <select name='idMoeda' class="Valid[Required] Description[Moeda]">
											            	</select>
						</div>
				</fieldset>
			</div>
			<div class="col_50">
				<fieldset>
					<label>Importância ao Negócio</label>
						<div>
						  <select name='idFluxo' class="Valid[Required] Description[Cliente]">
											            	</select>
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
