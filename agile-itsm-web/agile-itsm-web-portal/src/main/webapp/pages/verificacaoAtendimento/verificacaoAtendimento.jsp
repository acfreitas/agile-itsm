<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.Constantes"%>

<!doctype html public "">
<html>
<head>

<%@include file="/include/security/security.jsp" %>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en-us" class="no-js"> <!--<![endif]-->
	<title>CIT Corpore</title>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript"
			src="${ctx}/js/ValidacaoUtils.js"></script>
<script>

	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
	}

	function fechar() {
		parent.fecharSolicitacao();
	}

	//funcoes de tratamento da popup de cadastro rápido

</script>
</head>
<body>
	<div id="wrapper">
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">

		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1">Cadastro</a>
				</li>
			</ul>
	<a href="#" class="toggle">&nbsp;</a>
	<div class="toggle_container">
	<div id="tabs-1" class="block">
	<div class="section">


		<form name='form' action='${ctx}/pages/verificacaoAtendimento/verificacaoAtendimento'>
			<input type='hidden' name='idSolicitacaoServico'/>
			<input type='hidden' name='idTarefa'/>

			<div class="col_33">
				<fieldset>
					<label>Serviço</label>
						<div>
							<input type='text' name='idServicoContrato' value='1'/>
						</div>
				</fieldset>
			</div>
			<div class="col_33">
				<fieldset>
					<label>Solicitante</label>
						<div>
							<input type='text' name='idSolicitante' value='1'/>
						</div>
				</fieldset>
			</div>
			<div class="col_33">
				<fieldset>
					<label>Origem</label>
						<div>
							<input type='text' name='idOrigem' value='1'/>
						</div>
				</fieldset>
			</div>
		    <div class="col_50">
				<fieldset>
					<label>Descrição</label>
						<div>
		       				<textarea name="descricao" cols='70' rows='5'></textarea>
						</div>
				</fieldset>
			</div>
		    <div class="col_50">
				<fieldset>
					<label>Grupo</label>
						<div>
							<select name='idGrupoAtual'></select>
						</div>
				</fieldset>
			</div>
		    <div class="col_50">
				<fieldset>
					<label>Situação</label>
						<div>
							<label><input type='radio' name="situacao" value="EmAndamento" class=" Description[Situação]" />Em andamento</label>
							<label><input type='radio' name="situacao" value="Resolvida" class=" Description[Situação]" />Resolvida</label>
							<label><input type='radio' name="situacao" value="Cancelada" class=" Description[Situação]" />Cancelada</label>
						</div>
				</fieldset>
			</div>
		<br><br>


<button type='button' name='btnGravar' class="light"  onclick='document.form.save();'>
<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
<span>Gravar Dados</span>
</button>


<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
<img src="${ctx}/template_new/images/icons/small/grey/clear.png">
<span>Limpar Dados</span>
</button>

<button type='button' name='btnCancelar' class="light"
	onclick='fechar();'><img
	src="${ctx}/template_new/images/icons/small/grey/bended_arrow_left.png">
<span>Cancelar</span></button>



			</form>





	</div>
						</div>

	</div>
							<!-- ## FIM - AREA DA APLICACAO ## -->


	</div>

	</div>

		</div>
<!-- Fim da Pagina de Conteudo -->
	</body>
</html>
