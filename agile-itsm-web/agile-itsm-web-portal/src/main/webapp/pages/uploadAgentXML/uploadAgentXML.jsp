<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title>
	<fmt:message key="citcorpore.comum.title" />
</title>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

</head>
	<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
	<script type="text/javascript">
		function gravar(){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("enviar");
		}

		fechar_aguarde = function() {
	    	JANELA_AGUARDE_MENU.hide();
		}
	</script>
<body>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<div id="wrapper">

		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<fmt:message key="uploadAgente.upload_agente" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1">
							<fmt:message key="uploadAgente.upload" />
						</a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form id='form' name='form' action='${ctx}/pages/UploadAgentXML/UploadAgentXML'>
							</form>
							<form name="formUpload" method="post" enctype="multipart/form-data">
								<label style="margin-top: 5px; margin-bottom: 5px;"><fmt:message key="citcorpore.comum.arquivo" /></label>
								<cit:uploadControl style="height:100px;width:50%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>
							</form>
						</div>
						<br />
						<button type='button' name='btnGravar' class="light" onclick='gravar();'>
							<span>
								<fmt:message key="citSmart.comum.enviar" />
							</span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>