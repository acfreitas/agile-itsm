<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.util.Constantes"%>

<!doctype html public "">
<html>
<head>
<%
    String iframe = "";
    iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" />
</title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="${ctx}/js/ValidacaoUtils.js"></script>
<script type="text/javascript" src="${ctx}/js/PopupManager.js"></script>
<script>
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
<!--			$('.tabs').tabs('select', 0);-->
		}
	}

	function LOOKUP_TIPOFLUXO_select(id,desc){
		try{
			parent.atribuiValor(id,desc);
		}catch(e){}
		document.form.restore({idTipoFluxo:id});
		$('.tabs').tabs('select', 0);
	}
</script>
<%
    //se for chamado por iframe deixa apenas a parte de cadastro da página
    if (iframe != null) {
%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>
<%
    }
%>
</head>
<body>
	<div id="wrapper">
		<%
		    if (iframe == null) {
		%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%
		    }
		%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%
			    if (iframe == null) {
			%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%
			    }
			%>
			<div class="flat_area grid_16">
				<h2>
					Tipo Fluxo
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1">Cadastro</a></li>
					<li><a href="#tabs-2" class="round_top">Pesquisa</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='${ctx}/pages/tipoFluxo/tipoFluxo'>
								<input type="hidden" name="locale" id="locale" value="" />
								<input type="hidden" name="idTipoFluxo" id="idTipoFluxo" />
								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio">Nome</label>
											<div>
												<input type='text' name="nomeFluxo" class="Valid[Required] Description[Nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label>Descrição</label>
											<div>
												<input type='text' name="descricao" class="Valid[Required] Description[Descrição]" />
											</div>
										</fieldset>
									</div>
								</div>


								<div class="col_100">
								<button type='button' name='btnGravar' class="light"
									onclick='document.form.save();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/pencil.png">
									<span><fmt:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear();'>
									<img
										src="${ctx}/template_new/images/icons/small/grey/clear.png">
									<span><fmt:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><fmt:message key="citcorpore.comum.pesquisa"/>
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_TIPOFLUXO' id='LOOKUP_TIPOFLUXO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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

