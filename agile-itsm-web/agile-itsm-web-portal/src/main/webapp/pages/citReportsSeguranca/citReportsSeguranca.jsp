<%@page import="br.com.centralit.citcorpore.bean.GrupoDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<%
	String fileNameGrupo = request.getParameter("fileNameGrupo");
	if (fileNameGrupo == null) fileNameGrupo = "";

	String groupName = request.getParameter("groupName");
	if (groupName == null) groupName = "";

	String showOptions = request.getParameter("showOptions");
	if (showOptions == null) showOptions = "";
	if (showOptions.equalsIgnoreCase("")) showOptions = "true";

	Usuario user = br.com.citframework.util.WebUtil.getUsuario(request);
	String usuario = "";
	if (user != null){
		usuario = user.getNomeUsuario();
	}
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><fmt:message key="citcorpore.comum.title" /></title>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		<link rel="stylesheet" type="text/css" href="${ctx}/pages/dashBoardBuilder/css/byrei-dyndiv_0.5.css">
		<script type="text/javascript" src="${ctx}/pages/dashBoardBuilder/js/byrei-dyndiv_1.0rc1.js"></script>

		<script  type="text/javascript" src="${ctx}/js/CollectionUtils.js"></script>

		<script type="text/javascript"	src="${ctx}/js/jquery.easyui.min.js"></script>

		<link rel="stylesheet" type="text/css" href="${ctx}/pages/portal/css/jquery-ui-1.8.21.custom.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/main.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/theme_base.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/template_new/css/ie.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/gray/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery-easy.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.ui.datepicker.css">

	<script>
		var posPainel;
		function atualizaPainel(fileName, idPainel, objTd){
			clearAllCheckBox();
			emiteAguarde();
			document.formPainel.fireEvent('mostraSeguranca');
		}
		function clearAllCheckBox(){
	        if(document.formPainel.perfilSelecionado != undefined && document.formPainel.perfilSelecionado.length > 0){
	            for(i = 0; i < document.formPainel.perfilSelecionado.length; i++){
	                document.formPainel.perfilSelecionado[i].checked = false;
	            }
	        }
	    }
	    function selectCheckBoxByValue(value){
	        if(document.formPainel.perfilSelecionado != undefined && document.formPainel.perfilSelecionado.length > 0){
	            for(i = 0; i < document.formPainel.perfilSelecionado.length; i++){
	                if(document.formPainel.perfilSelecionado[i].value == value){
	                    document.formPainel.perfilSelecionado[i].checked = true;
	                }
	            }
	        }
	    }
		function emiteAguarde(){
			posPainel = HTMLUtils.getPosElement('divPainel');

			document.getElementById('divAguarde').style.top = posPainel.y + 'px';
			document.getElementById('divAguarde').style.left = posPainel.x + 'px';
			document.getElementById('divAguarde').style.width = document.getElementById('divPainel').style.width;
			document.getElementById('divAguarde').style.height = document.getElementById('divPainel').style.height;

			document.getElementById('divAguarde').style.display = 'block';
		}
		function hideAguarde(){
			document.getElementById('divAguarde').style.display = 'none';
		}

		function gravarPerfsSeg(){
			if (document.formPainel.idConsulta.value == '' || document.formPainel.idConsulta.value == undefined){
				alert('Selecione a consulta/relatório que deseja aplicar segurança!');
				return;
			}
			document.formPainel.fireEvent('gravarSeguranca');
		}
		function abrirPopup(id, text){
			clearAllCheckBox();
			if (id != null){
				var init = id.charAt(0);
				var idConsulta = id.substring(1,id.length);
				emiteAguarde();
				document.formPainel.idConsulta.value = StringUtils.onlyNumbers(idConsulta);
				document.formPainel.fireEvent('mostraSeguranca');
			}
		}
	</script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body id="bodyNode">
 <div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
			<%
			    if (iframe == null) {
			%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%
			    }
			%>
	<form name='formPainel' method="POST" action='<%=Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/citReportsSeguranca/citReportsSeguranca'>
		<input type='hidden' name='idConsulta'/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<div id='divInfTransfPainel' style='display:none'></div>
		<table>
			<tr>
				<td>
					<div id='divLista' style='height: 600px; width: 250px; border: 1px solid black; overflow: auto'>
						<div id="infoDiv" style="font-weight: bold;"><fmt:message key="listagemConsultas.consultas"/></div>
						<br>
						<ul id="tt" class="easyui-tree" data-options="url:'${ctx}/pages/listagemConsultasObjects/listagemConsultasObjects.load?seg=N',animate: true, onClick: function(node){
									abrirPopup(node.id, node.text);
								}">
				        </ul>
					</div>
				</td>
				<td>
					<div id='divPainel' style='height: 520px; width: 801px; '>
                       <table border="1" cellpadding="0" cellspacing="0" style="font-size:11px;color:black;font-family:Arial,Tahoma;" width="80%">
                         <tr>
                             <td class="campoEsquerda"><b><fmt:message key="desenhoFluxo.grupos" /></b></td>
                         </tr>
                           <%
                              Collection perfil = (Collection)request.getAttribute("perfil");
                              if(perfil != null && !perfil.isEmpty()){
                                  GrupoDTO perfilSeguranca = null;
                                  for(Iterator it = perfil.iterator(); it.hasNext();){
                                      perfilSeguranca = (GrupoDTO)it.next();
                              %>
                         <tr>
                               <td>
                                   <input type="checkbox" name="perfilSelecionado" value="<%= perfilSeguranca.getIdGrupo()%>"><%=perfilSeguranca.getNome()%>
                               </td>
                         </tr>
                            <%
                                  }
                              }
                              %>
	                         <tr>
	                             <td>
	                             	<button type='button' onclick='gravarPerfsSeg()'><fmt:message key="citcorpore.comum.gravar"/></button>
	                             </td>
	                         </tr>
                       </table>
					</div>
				</td>
			</tr>
		</table>
	</form>

	<div id='divAguarde' style='position: absolute; display:none; CURSOR: wait; BACKGROUND-COLOR:gray; filter:alpha(opacity=20);-moz-opacity:.25;opacity:.25;'>
		<table width="100%" heigth="100%">
			<tr>
				<td>
					<font size='72'><b><fmt:message key="citcorpore.comum.aguardecarregando"/></b></font>
				</td>
			</tr>
		</table>
	</div>
 </div>
<%@include file="/include/footer.jsp"%>

</body>

</html>