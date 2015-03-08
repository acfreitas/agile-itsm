<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">

<html>
<head>
<%
	String retorno = "${ctx}/pages/index/index.load";

	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/security/security.jsp" %>
<title><fmt:message key="citcorpore.comum.title"/></title>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<link rel="stylesheet" type="text/css" href="./css/galeriaImagens.css" />
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>


<script>
	var retorno = "${retorno}";
</script>


<script type="text/javascript" src="./js/galeriaImagens.js"></script>
<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>
		<div class="flat_area grid_16">
				<h2>
					<fmt:message key="menu.nome.galeriaImagens" />
				</h2>
			</div>
<!-- 	Desenvolvedor: Pedro Lino - Data: 289/10/2013 - Horário: 17:00 - ID Citsmart: 120948 -
		* Motivo/Comentário: Alterado todo layout para div, no novo padrão
		* Adicionado div Janela aguarde ao carregar as imagens -->
<div class="box grid_16 tabs">
	<div id="areautil">
		<div id="formularioIndex">
       		<div id=conteudo>
				<table width="100%">
					<tr>
						<td width="100%">
							<div id='areaUtilAplicacao'>
								<!-- ## AREA DA APLICACAO ## -->
							 	<form name='form' action='${ctx}/galeriaImagens/galeriaImagens'>
							 	<input type='hidden' name='idImagem' id='idImagem' />
									<div class='row-fluid'>
										<div class="span6">
											<label ><strong><fmt:message key="citcorpore.comum.categoria" /></strong></label>
										  	<select name='idCategoriaGaleriaImagem' onchange='selecionaCategoriaGaleriaImagem()' ></select>
										</div>
										<div class="span6">
											<label >&nbsp;</label>
											<button type='button' name='btnAddImagem' onclick='adicionarImagem()'><fmt:message key="menu.nome.adicionarImagem" /></button>
										</div>

									</div>
									<div class='row-fluid'>
										<div class='box-generic'>
											<div id='divImagens'></div>
										</div>

									</div>
								</form>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>

<div id="POPUP_ADD_IMAGEM" title="" >
		<iframe name='frameUpload' id='frameUpload' src='about:blank' height="0" width="0" style='display:none'/></iframe>
	<form name='formAddImagem' method="post" ENCTYPE="multipart/form-data" action='${ctx}/pages/galeriaImagens/galeriaImagens.load'>
		<input type='hidden' name='idCategoriaGaleriaImagem' id='idCategoriaGaleriaImagemAdd'/>
		<div class='innerAll'>
			<div class='row-fluid'>

					<div class="span6">
							<label ><strong><fmt:message key="menu.nome.arquivoImagem" /></strong></label>
						  	<input type='file' name='arquivo' size="60"/>
						</div>
						<div class="span6">
							<label ><strong><fmt:message key="menu.nome.descricaoImagem" /></strong></label>
							<input type="text" name='descricaoImagem' size="70" maxlength="70">
						</div>
				</div>
				<div class='row-fluid'>
					<div class="span12">
						<label ><strong><fmt:message key="citcorpore.ui.tabela.coluna.Detalhamento" /></strong></label>
						<textarea rows="5" cols="60" name="detalhamento" style='border:1px solid black'></textarea>
					</div>
				</div>
				<div class='row-fluid'>
					<div class="span12">
						<button type='button' name='btnEnviarImagem' value="" onclick='submitFormAnexo();'><fmt:message key="menu.nome.enviarImagem" /></button>
						<button type='button' name='btnFecharEnviarImagem' value="" onclick="fecharPopUpAdicionarImagem();"><fmt:message key="menu.nome.fecharImagem" /></button>
					</div>
			</div>
		</div>
	</form>
</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>

</div>
</body>
</html>

