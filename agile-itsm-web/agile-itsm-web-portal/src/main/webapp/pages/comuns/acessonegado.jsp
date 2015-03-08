<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

<%String retorno = "${ctx}/pages/index/index.load";%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><fmt:message key="citcorpore.comum.acessoNegado"/></title>

<style type="text/css">

.style1 {font-family: Arial, Helvetica, sans-serif}
.style2 {
	color: #FFFFFF;
	font-weight: bold;
	font-family: Arial, Helvetica, sans-serif;
}
.style3 {color: #357AB1}
.style4 {
	color: #FF0000;
	font-family: Arial, Helvetica, sans-serif;
	font-weight: bold;
	width: 99%!important;
}

.style5{
	color:#848C97;
	font-family: Arial, Helvetica, sans-serif;

}

#tblAcessoNegado{
	box-shadow: 0 0 20px rgba(0, 0, 0, 0.3);
	width: 60%;
	min-height:60%;
	margin-top: 5%;

}
.tooltip_top{
	cursor: pointer;
	float: right;

}

</style>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script src="${ctx}/novoLayout/common/theme/scripts/plugins/system/jquery.min.js"></script>
<script type="text/javascript">

/**
 * Motivo: Pequena customização para adaptar o fechamento da popup quando for 'modal' ou 'dialog' dentro de um iframe
 * Autor: flavio.santana
 * Data/hora: 19/11/2013
 */
function voltar(e) {
<% if(request.getParameter("iframe") != null) { %>
	parent.$("iframe").each(function(iel, el) {
	  if(el.contentWindow === window) {
		  if($(this).parents('div').is('.modal'))
		  {
			  parent.$($(this).parents('div').find('.modal')).modal('hide');
		  }
		  else if($(this).parents('div').is('.ui-dialog-content'))
		  {
			  parent.$($(this).parents('div').find('.ui-dialog-content')).dialog('close');
		  }
	  }
	});
<% } else { %>
	window.location = '<%=retorno%>';
<% }  %>
}

function internacionalizar(parametro){
	  document.getElementById('locale').value = parametro;
	  document.formInternacionaliza.fireEvent('internacionaliza');
}
</script>
</head>

<body>

	<div id="linguas">
			<!-- <div class="abalinguas"><img title="Selecione o Idioma" class="tooltip_left" src="../../template_new/images/icons/small/white/globe_2.png"></div> -->
			<div id="lang" class="menulinguas hide">
		  	 <form name='formInternacionaliza' id='formInternacionaliza' action='${ctx}/pages/internacionalizar/internacionalizar'>
  				<input type="hidden" name="locale" id="locale"/>
		  		<img title="Português" onclick="internacionalizar('pt')" class="tooltip_top" src="${ctx}/template_new/images/brazil_flag.png">
		  		<img title="English" onclick="internacionalizar('en')" class="tooltip_top" src="${ctx}/template_new/images/united_states_flag.png">
		  		<img title="Español" onclick="internacionalizar('es')" class="tooltip_top" src="${ctx}/template_new/images/spain_flag.png">
		  	 </form>

		  	</div>
		</div>
<table width="100%" border="0" id="tblAcessoNegado" align="center" cellpadding="0" cellspacing="0" bordercolor="#D3E3F0">
  <tr>
    <td height="100px" bordercolor="#D3E3F0">
    	 <img border="0" src="${ctx}/imagens/logo/logo.png" />
    </td>
  </tr>
  <tr>
    <td height="400px" bgcolor="#F3F8FC" valign="top">
	<%-- <h2 align="left" class="style5">&nbsp;<fmt:message key="citcorpore.comum.cadastroInativos"/></h2> --%>
	<p align="left" class="style16 style17 style12 style13 style1 style3">&nbsp;</p>
	<p align="left" class="style16 style17 style12 style13 style1 style3">&nbsp;</p>
	<form name="form1" method="post" action="<%=retorno%>">
      <p>&nbsp;</p>
      <table width="500" align="center" cellpadding="0" cellspacing="0">
		<tr><td><div align="center" class="style4">
		  <p align="left"><h4><img border="0" src="${ctx}/imagens/smile_sad.png" /> <fmt:message key="citcorpore.comum.permissaoFuncionalidade"/></h4></p>
		  </div></td></tr>
      </table>
      <p align="center">
       <!--  <input type="submit" class="light img_icon has_text" name="Submit" value="Voltar"> -->
       <button id="botao" class="light img_icon has_text" title="<fmt:message key='citcorpore.comum.voltarprincipal' />" onclick="voltar(this)">
       	<%-- <img border="0" title="<fmt:message key='citcorpore.comum.voltarprincipal' />" src="${ctx}/imagens/back.png" /> --%><span style="padding-left: 0px !important;"><fmt:message key="citcorpore.comum.voltar" /></span>
       </button>
      </p>
	</form>
	</td>
  </tr>
</table>
</body>
</html>
