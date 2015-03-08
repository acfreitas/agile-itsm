<%@ page isErrorPage="true"%>  
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CITSmart - ITSM</title>
<link rel="stylesheet"
	href="${ctx}/pages/error/css/default.css" />
<script
	src="${ctx}/template_new/js/jquery/jquery.min.js"
	type="text/javascript"></script>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
</head>
<body>
	<!-- INICIO HEADER -->
	<div class="compact" id="header">
		<div class="g-section g-tpl-160 g-split">
			<div class="g-unit g-first" id="header-logo">
				<a
					href="${ctx}/pages/index/index.load">
					<img alt="CITSmart" src="/citsmart/imagens/logo/logo.png" />
				</a>
			</div>
		</div>
	</div>
	<!-- FIM HEADER -->

	<!-- INICIO CONTENT -->
	<div class="browser-features" id="main">
		<div
			class="compact marquee-side marquee-divider g-section g-tpl-nest g-split"
			id="marquee">
			<div class="g-unit g-first marquee-copy g-col-8">
				<div class="g-content" id="step">
					<!-- STEP 1 -->
					<div class="g-content-inner">
						<h1 class="wrap">Erro 500</h1>
						<section>
							<h3>Ocorreu um erro</h3>
							<p><%=exception%></p>
						</section>
					</div>

				</div>
			</div>
		</div>
	</div>
	<!-- FIM CONTENT -->

</body>
</html>
