<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!doctype html public "">
<html>
	<head>
		<%
			String descricao = (String)request.getAttribute("descricao");
			String resposta = (String)request.getAttribute("resposta");
			if (descricao == null){
			    descricao= "";
			}
			descricao = descricao.replaceAll("\n", "<br>");
			if (resposta == null){
			    resposta= "";
			}
			resposta = resposta.replaceAll("\n", "<br>");
		%>
		<%String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>

		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<script type="text/javascript">

			function enviar(){
				if(document.form.comboNotas.value == ""){
					alert(i18n_message("citcorpore.comum.selecioneAvaliacao"))
					return;
					}
				document.form.fireEvent("save");
			}

			addEvent(window, "load", load, false);
			function load() {
				JANELA_AGUARDE_MENU.show();
				document.form.restore();
				document.form.afterRestore = function() {

				}
				$(window).resize(function () {
					$("#corpo").css("width", $(window).width());
				});
			}
			function fecharPopup(){
				window.parent.$('#popupCadastroRapido').dialog('close');
			}
			function hidden(id){
				var element = $("#"+id);
				if (element.is('.showing')) {
					element.removeClass('showing').addClass('hidden');
				}
			}
			function showing(id) {
				var element = $("#"+id);
				if (element.is('.hidden')) {
					element.removeClass('hidden').addClass('showing');
				}
			}
			function closeW() {
			  window.open('','_self','');
	          window.close();
			}
			function internacionalizar(parametro){
				$('#locale').removeAttr('disabled');
				document.getElementById('locale').value = parametro;
				document.form.fireEvent("internacionaliza");
			}

		</script>
		<style type="text/css">
			.hidden{
				display: none;
			}
			.showing{
				display: block;
			}
			body{
				padding: 5px;
				text-align: center;
				width: 100%;
			}
			#corpo{
				margin: 0 auto;
				width: 700px;
				padding: 7px;
				margin-top: 20px;
				height: auto;
			}
			#page{
				display: block;
				height: auto;
			}
			.camp{
				display: block;
				float: left;
				width: 99.5%;
				padding: 2px;
				margin-bottom: 5px;
				text-align: left;
			}
			.titulo{
				height: 35px;
				line-height: 35px;
				border: 1px dotted #B3B3B3;
				margin-bottom: 10px;
			}
			.titulo h2{
				letter-spacing: 2px;
				font-size: 20px;
				height: 35px;
				line-height: 35px\0/;
			}
			.titLab{
				font-size: 13px;
				font-weight: bold;
			}
			#comentario{
				background-color: #F3F3F3;
				border: solid #B3B3B3 1px;
				width: 100%;
			}
			#comboNotas{
				background-color: #F3F3F3;
				border: solid #B3B3B3 1px;
			}
			.campoObrigatorio{
				display: block;
				width: 210px;
				float: right;
				font-size: 9px;
				font-weight: normal;
			}
			.result{
				display: block;
				float: left;
				width: 99.5%;
				padding: 2px;
			}
			.menu {
				list-style: none;
				float: right;
				width: 320px;
			}
			.menu li {
				float:left;
				margin-right: 5px;
				padding: 8px;
			}
			.menu li.active {
				background-color: #F5F5F5;
			}
			.menu li a {
				text-decoration: none;
			}
			.clear {clear:both;}
		</style>
	</head>

	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>

	<body>
	<div id="corpo" >
		<div id="page" class="hidden">
			<form id="form" name='form' action='${ctx}/pages/pesquisaSatisfacao/pesquisaSatisfacao'>
				<input type="hidden" id="idSolicitacaoServico" name="idSolicitacaoServico">
				<input type="hidden" id="frame" name="frame">
				<input type="hidden" name="locale" id="locale"/>

				<ul class="menu">
		    		<li <% if (locale.equalsIgnoreCase("")) { %> class="active" <% } %> onclick="internacionalizar('pt');return false;"><a href="" title="Portugues" ><img onclick="internacionalizar('pt');return false;" src="../../novoLayout/common/theme/images/lang/br.png" alt="Portugues"> Português BR</a></li>
		      		<li <% if (locale.equalsIgnoreCase("en")) { %> class="active" <% } %> onclick="internacionalizar('en');return false;"><a href="" title="English"><img onclick="internacionalizar('en');return false;" src="../../novoLayout/common/theme/images/lang/us.png" alt="English"> English</a></li>
		      		<li <% if (locale.equalsIgnoreCase("es")) { %> class="active" <% } %> onclick="internacionalizar('es');return false;"><a href="" title="Español"><img onclick="internacionalizar('es');return false;" src="../../novoLayout/common/theme/images/lang/es.png" alt="Espanhol"> Español</a></li>
	      		</ul>

	      		<div class="clear"></div>

				<div class='titulo'>
					<h2>CITSMart - <fmt:message key="pesquisasatisfacao.pesquisasatisfacao"/></h2>
				</div>

				<div class="camp">
					<label class="titLab">
						<fmt:message key="pesquisa.codigodataabertura"/>:
					</label>
					<label id="divId" class="result"></label>
				</div>
				<div class="camp">
					<label class="titLab">
						<fmt:message key="pesquisa.descricao"/>:
					</label>
					<label id="" class="result">
						<%=descricao%>
					</label>
				</div>
				<div class="camp">
					<label class="titLab">
						<fmt:message key="pesquisa.resposta"/>:
					</label>
					<label id="" class="result">
					<%=resposta%>
					</label>
				</div>
				<div class="camp">
					<label class="titLab">
						<fmt:message key="comentarios.avaliacao"/>:
					</label>
					<label id="" class="result">
						<select id="comboNotas" name="nota" ></select>
					</label>
				</div>
				<div class="camp">
					<label class="titLab">
						<fmt:message key="pesquisa.melhoria"/>:
					</label>
					<label id="" class="result">
						<textarea id="comentario" name="comentario" rows="10" cols="50" style="display: block;"></textarea>
					</label>
				</div>
				<div class="camp">

					<button type='button' name='btnEnviar' class="light"  onclick='enviar();' >
						<img src="${ctx}/template_new/images/icons/small/grey/user_comment.png">
						<span><fmt:message key="citSmart.comum.enviar"/></span>
					</button>
				</div>
			</form>
		</div>
		<div id="pagemsg" class="hidden">
			<h1 style="text-align: center;float: left;line-height: 35px !important;"><fmt:message key="pesquisasatisfacao.sucesso"/></h1>
			<button type="button"  class="light"  onclick="closeW();" style="margin: 6px 0 0 20px;float: right;margin-left: 30px;"><fmt:message key="citcorpore.comum.sair"/></button>
		</div>
	</div>
<%@include file="/include/header.jsp" %>
<script type="text/javascript" src="${ctx}/template_new/js/isotope/jquery.isotope.min.js"></script>
<script type="text/javascript" src="${ctx}/template_new/js/fancybox/jquery.fancybox-1.3.4.js"></script>

<script type="text/javascript" src="${ctx}/template_new/js/custom/gallery.js"></script>

		<div id="loading_overlay">
			<div class="loading_message round_bottom">
				<img src="${ctx}/template_new/images/loading.gif" alt="aguarde..." />
			</div>
		</div>
	</body>
</html>