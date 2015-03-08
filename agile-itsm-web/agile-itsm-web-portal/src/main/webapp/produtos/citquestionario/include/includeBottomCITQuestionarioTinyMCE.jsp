<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

<form name='formArquivosMultimidia' method="post" action='${ctx}/pages/uploadArquivoMultimidia/uploadArquivoMultimidia'>
	<input type='hidden' name='idDIV'/>
</form>

<cit:janelaPopup style="display:none;top:1350px;width:600px;left:50px;height:200px;position:absolute;" title="Upload Anexo" id="POPUP_UPLOAD_MULTIMIDIA">
	<iframe name='frameUploadAnexo' id='frameUploadAnexo' src='about:blank' height="0" width="0"/></iframe>
	<form name='formAnexo' method="post" ENCTYPE="multipart/form-data" action='${ctx}/pages/uploadArquivoMultimidia/uploadArquivoMultimidia'>
		<input type='hidden' name='idPessoa'/>
		<table>
			<tr>
				<td>
					Arquivo Multímidia (Docs, Planilhas, Videos, PDFs, etc.):
				</td>
				<td>
					<input type='file' name='arquivo' size="60"/>
				</td>
			</tr>
			<tr>
				<td>
					Descrição/Observação do Arquivo:
				</td>
				<td>
					<textarea rows="5" cols="60" name="observacao"></textarea>
				</td>
			</tr>			
			<tr>
				<td>
					<input type='button' name='btnEnviarImagem' value='Enviar' onclick='submitFormAnexo()'/>
				</td>
			</tr>
		</table>
	</form>
</cit:janelaPopup>

	<script type="text/javascript">
		<%
		Collection lstFields = (Collection) request.getAttribute("LST_CAMPOS_EDITOR");
		if (lstFields == null)
			lstFields = new ArrayList();
		
		for(Iterator it = lstFields.iterator(); it.hasNext();){
			String fieldName = (String)it.next();
		%>
			tinyMCE.execCommand('mceAddControl', false, '<%=fieldName%>');
		<%
		}
		%>
	</script>
