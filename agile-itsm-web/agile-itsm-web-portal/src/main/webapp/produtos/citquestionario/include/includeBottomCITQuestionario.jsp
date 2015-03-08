<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>

<form name='formArquivosMultimidia' method="post" action='${ctx}/pages/uploadArquivoMultimidia/uploadArquivoMultimidia'>
	<input type='hidden' name='idDIV'/>
</form>

<cit:janelaPopup style="display:none;top:1350px;width:600px;left:50px;height:200px;position:absolute;" title="Upload Anexo" id="POPUP_UPLOAD_MULTIMIDIA">
	<iframe name='frameUploadAnexo' id='frameUploadAnexo' src='about:blank' height="0" width="0"/></iframe>
	<form name='formAnexo' method="post" ENCTYPE="multipart/form-data" action='${ctx}/pages/uploadArquivoMultimidia/uploadArquivoMultimidia.load'>
		<input type='hidden' name='idPessoa'/>
		<input type='hidden' name='idQuestaoQuest'/>
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

<cit:janelaPopup style="display:none;top:1350px;width:600px;left:50px;height:200px;position:absolute;" title="Modelos Textuais" id="POPUP_MODELOS_TEXTUAIS">
	<form name='formModeloTextual' method="post" action='${ctx}/pages/modeloTextual/modeloTextual'>
		<input type='hidden' name='campoSelecaoModeloTextual' id='campoSelecaoModeloTextual'/>
		<input type='hidden' name='idModeloTextual' id='idModeloTextual'/>
		<input type='hidden' name='tipoCampo' id='tipoCampo'/>
	</form>
</cit:janelaPopup>

<cit:janelaPopup style="display:none;top:1350px;width:800px;left:50px;height:600px;position:absolute;" title="Galeria de Imagens" id="POPUP_GALERIA_IMAGENS">
	<form name='formGaleriaImagens' method="post" target="_blank" action='${ctx}/uploadArquivoMultimidia/uploadArquivoMultimidia'>
		<input type='hidden' name='idQuestaoParm' id='idQuestaoParm'/>
		<input type='hidden' name='idImagemParm' id='idImagemParm'/>
		<input type='hidden' name='caminhoImagemParm' id='caminhoImagemParm'/>
		<input type='hidden' name='campoValue' id='campoValue'/>
		<input type='hidden' name='funcaoRecebeConteudo' id='funcaoRecebeConteudo'/>
		<input type='hidden' name='frame' id='frame'/>
		<input type='hidden' name='imagemFundo' id='imagemFundo'/>
		<input type='hidden' name='conteudoImagem' id='conteudoImagem'/>
		<input type='hidden' name='selecaoImagemEdicao' id='selecaoImagemEdicao' value='chamaEditorImagens(document.formGaleriaImagens.idQuestaoParm.value,document.formGaleriaImagens.idImagemParm.value,document.formGaleriaImagens.caminhoImagemParm.value, document.formGaleriaImagens.campoValue.value);'/>
<a name="ancoraGaleriaImagens"></a>
		<table cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td>
					Categoria:
				</td>
				<td>
					<select name='idCategoriaGaleriaImagem' onchange='selecionaCategoriaGaleriaImagem()'></select>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<div id='divImagens'></div>
				</td>
			</tr>
		</table>
	</form>
</cit:janelaPopup>