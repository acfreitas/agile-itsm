<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@include file="/include/security/security.jsp" %>
	<title>CIT Corpore</title>
	<%@include file="/include/header.jsp" %>
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
</script>
</head>
<body>
	<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>

		<div class="flat_area grid_16">
				<h2>Contrato</h2>
		</div>

		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1">Cadastro de Contrato</a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top">Pesquisa Contrato </a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	<div class="toggle_container">
	<div id="tabs-1" class="block">
	<div class="section">
	<form name='form' action='${ctx}/pages/contratos/contratos'>

		<div class="columns clearfix">
			<input type='hidden' name='idContrato'/>
			<div class="col_40">
				<fieldset>
					<label>N&uacute;mero</label>
						<div>
						 <input type='text' name="numero" maxlength="30" size="30" class="Valid[Required] Description[Número]" />
						</div>
				</fieldset>
			</div>
			<div class="col_40">
				<fieldset>
					<label>Cliente</label>
						<div>
						  <select name='idCliente' class="Valid[Required] Description[Cliente]">
											            	</select>
						</div>
				</fieldset>
			</div>
			<div class="col_20">
				<fieldset>
					<label><fmt:message key="visao.dataContrato" /></label>
						<div>
						  <input type='text' name="dataContrato" maxlength="10" size="10" class="Valid[Required] Description[visao.dataContrato] Format[Data] datepicker" />
						</div>
				</fieldset>
			</div>
		</div>
		<div class="columns clearfix">

			<div class="col_100">
				<fieldset>
					<label>Objeto</label>
						<div>
						  <textarea name="objeto" cols='70' rows='5' class="Valid[Required] Description[Objeto]"></textarea>
						</div>
				</fieldset>
			</div>
		</div>

		<div class="columns clearfix">
		  <div class="col_25">
				<fieldset>
					<label>Moeda</label>
						<div>
						  <select name='idMoeda' class="Valid[Required] Description[Moeda]">
											            	</select>
						</div>
				</fieldset>
			</div>
			<div class="col_25">
				<fieldset>
					<label>Valor Cota&ccedil;&atilde;o da Moeda</label>
						<div>
						 <input type='text' name='cotacaoMoeda' size="15" maxlength="15" class="Valid[Required] Description[Valor cotação moeda]"/>
						</div>
				</fieldset>
			</div>
			<div class="col_25">
				<fieldset>
					<label>Fluxo de Execu&ccedil;&atilde;o de Atividade</label>
						<div>
						  <select name='idFluxo' class="Valid[Required] Description[Cliente]">
											            	</select>
						</div>
				</fieldset>
			</div>
			<div class="col_25">
				<fieldset>
					<label>Situa&ccedil;&atilde;o</label>
						<div>
						 <select name='situacao' class="Valid[Required] Description[Situação]">
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
