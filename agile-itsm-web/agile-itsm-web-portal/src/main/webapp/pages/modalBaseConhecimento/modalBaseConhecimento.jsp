<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	
		<%
		    String id = request.getParameter("id");
		%>	
    	<script src="${ctx}/template_new/js/jquery/jquery.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/js/UploadUtils.js"></script>
    	<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>

		<%@include file="/include/security/security.jsp" %>
		<title><fmt:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<link type="text/css" rel="stylesheet" href="${ctx}/css/layout-default-latest.css"/>
		<link type="text/css" rel="stylesheet" href="${ctx}/css/jquery.ui.all.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/themeroller/Aristo.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jqueryTreeview/jquery.treeview.css"/>
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/js/jQueryGantt/css/style.css" />
	    <link rel="stylesheet" type="text/css" href="${ctx}/css/slick.grid.css"/>
		<link type="text/css" rel="stylesheet" class="include" href="${ctx}/pages/graficos/jquery.jqplot.min.css" />
		<link type="text/css" rel="stylesheet" href="${ctx}/pages/modalBaseConhecimento/css/modalBaseConhecimento.css"/>

		<script src="${ctx}/template_new/js/jquery/jquery-ui.min.js" type="text/javascript"></script>
		<script src="${ctx}/template_new/js/touchPunch/jquery.ui.touch-punch.min.js" type="text/javascript"></script>
		<script src="${ctx}/template_new/js/uitotop/js/jquery.ui.totop.js" type="text/javascript"></script>


		<script src='${ctx}/template_new/js/star-rating/jquery.MetaData.js' type="text/javascript" language="javascript"></script>
		<script src='${ctx}/template_new/js/star-rating/jquery.rating.js' type="text/javascript" language="javascript"></script>
		<link href='${ctx}/template_new/js/star-rating/jquery.rating.css' type="text/css" rel="stylesheet"/>



	</head>

	<body>
		<div class="clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" style="height: 40px; *height: 40px; ">

		<form name='formPesquisa' id='formPesquisa'>

			<div id="pesquisaBaseConhecimento">
				<%	if(request.getParameter("id") == null)	{		%>
	    			<iframe id="frameCadastroRapido" name="frameCadastroRapido"
	          		  src="${ctx}/pages/baseConhecimentoView/baseConhecimentoView.load" width="100%" height="100%"></iframe>
		    	<%
		    	} else	{	%>
					<iframe id="frameCadastroRapido" name="frameCadastroRapido"
					src="${ctx}/pages/baseConhecimentoView/baseConhecimentoView.load?id=<%=request.getParameter("id")%>&origem=portal" width="100%" height="100%"></iframe>				<%}%>

			</div>
		</form>
		</div>
		
		<script type="text/javascript">
			var id = "${id}";
		</script>
		
		<script type="text/javascript" src="${ctx}/pages/modalBaseConhecimento/js/modalBaseConhecimento.js"></script>
		
	</body>

</html>

