<%@ page isErrorPage="true"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/security/security.jsp"%>
<html lang="en-us" class="no-js">
<!--<![endif]-->
<title>Erro 404 - CITSmart</title>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<style type="text/css">
	section {
		padding-left: 18px;
		margin-bottom: 24px;
		margin-top: 8px;
		max-width: 60%;
	}

	section > h3 {
		-webkit-margin-start: -18px;
	}
	.wrap {
		font-size: xx-large;
	}
</style>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<h1 class="wrap">Erro 404</h1>
					<section>
						<h3>Página não encontrada.</h3>
						<p>O endereço que você está tentado acessar não existe.</p>
					</section>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>
