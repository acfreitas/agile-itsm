<!-- Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Criação de Upload para requisição mudança para evitar conflitos com outras telas do sistema que usão upload.  -->
<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<%@ include file="/WEB-INF/templates/taglibs.jsp"%>

%>
<html>
<head>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>

	<style type="text/css">
	body {
	background-color: white;
	background-image: url("");
	}
	.table {
		border-left:1px solid #ddd;
	}

	.table th {
		border:1px solid #ddd;
		padding:4px 10px;
		border-left:none;
		background:#eee;
	}

	.table td {
		border:1px solid #ddd;
		padding:4px 10px;
		border-top:none;
		border-left:none;
	}

	.tableLess {
	  font-family: arial, helvetica !important;
	  font-size: 10pt !important;
	  cursor: default !important;
	  margin: 0 !important;
	  background: white !important;
	  border-spacing: 0  !important;
	  width: 100%  !important;
	}

	.tableLess tbody {
	  background: transparent  !important;
	}

	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}

	.tableLess thead th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}

	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}

	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9!important ;
	  cursor: pointer;
	}

	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}

	.tableLess td{
	  border: 1px solid #BBB  !important;
	  padding: 6px 10px  !important;
	}
	a{
		font-size: 11px!important;

	}
	a:HOVER {
		font-size: 11px!important;
		font-weight: bold!important;
}
td{
font-size: 11px!important;

}


	</style>

</head>
<body>
<script>


	function excluirImagemUpload(path){
		if (confirm(i18n_message("citcorpore.comum.confirme.desejaexcluiranexo"))){
			window.location = '${ctx}/pages/uploadExcluirRequisicaoProblema/uploadExcluirRequisicaoProblema.load?path=' + path;
		}
	}
	function obtemArquivoTemporario(path){
		window.location = '${ctx}/pages/visualizarUploadTemp/visualizarUploadTemp.load?path=' + path;
	}
	function excluirAnexo(id, nomeArquivo){
		if(confirm(i18n_message("citcorpore.comum.confirme.exclusaoanexo"))){
			window.location = '${ctx}/pages/uploadExcluirRequisicaoProblema/excluirAnexo.donameFile=' + nomeArquivo + '&id='+id;
		}
	}
	<%
		if(request.getAttribute("acaoRetorno") != null){
	%>
		alert(i18n_message("citcorpore.comum.validacao.anexoexcluido"));
	<%
		}
	%>
</script>
<table width="100%" class='tableLess'>
	<thead>
		<tr>
		  <th>&nbsp;</th>
          <th ><fmt:message key="citcorpore.comum.arquivo" /></th>
          <th><fmt:message key="citcorpore.comum.descricao" /></th>
          <th><fmt:message key="citcorpore.comum.situacao" /></th>
		</tr>
	</thead>
        <%boolean branco = true;%>
      	<%String cor = "#ffffff";%>
      	<%
		Collection colUploadsGED = (Collection)request.getSession(true).getAttribute("colUploadRequisicaoProblemaGED");
		if (colUploadsGED == null){
			colUploadsGED = new ArrayList();
		}
      	%>
      	<%for(Iterator it = colUploadsGED.iterator(); it.hasNext();){ %>
      		<%
      		UploadDTO uploadDTO = (UploadDTO)it.next();
      		%>
		<TR>
			<%
			if(branco){
				cor ="#ffffff";
			}else{
				cor="#E5EBF6";
			}
			%>
			<td >
					<img src='${ctx}/imagens/delete.png' style='cursor:pointer' onclick='excluirImagemUpload("<%=uploadDTO.getPath()%>")' title='<fmt:message key="citcorpore.ged.msg.exluiranexo" /> '/>
			</td>
			<td >
				<a href="#" onclick='obtemArquivoTemporario("<%=uploadDTO.getPath()%>")'><%=uploadDTO.getNameFile()%></a>
			</td>
			<td >
				<%=uploadDTO.getDescricao()%>
			</td>
			<td  title='<fmt:message key="citcorpore.ged.situacao" />'>
				<%=uploadDTO.getSituacao()%>
			</td>
        </TR>
		<%}%>
</table>
</body>

</html>