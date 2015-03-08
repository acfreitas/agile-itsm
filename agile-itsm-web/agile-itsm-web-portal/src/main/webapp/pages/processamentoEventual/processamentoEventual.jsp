<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<!doctype html public "">
<html>
	<head>
		<%@include file="/include/header.jsp"%>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>

		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<script type="text/javascript">
			var objTab = null;
			function pesqSatisf(){
				document.form.fireEvent('executaSolSemPesqSatisf');
			}
		</script>
	</head>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>

				<div class="flat_area grid_16">
					<h2>Processamento Eventual</h2>
				</div>

				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">Processamento</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='${ctx}/pages/processamentoEventual/processamentoEventual'>
									<button type='button' name='btnGravar' class="light"  onclick='save();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span>Executar</span>
									</button>


									<button type='button' name='btnGravar' class="light"  onclick='pesqSatisf();'>
										<img src="${ctx}/template_new/images/icons/small/grey/pencil.png">
										<span>Executar Processamento de Pesquisas de Satisfação</span>
									</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>
