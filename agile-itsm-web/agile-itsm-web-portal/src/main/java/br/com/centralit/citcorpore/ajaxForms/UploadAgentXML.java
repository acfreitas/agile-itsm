package br.com.centralit.citcorpore.ajaxForms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.comm.server.Servidor;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class UploadAgentXML extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		request.getSession(true).setAttribute("colUploadsGED", null);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
	}

	public void enviar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");

		FileReader reader = null;
		String resultado = "";
		String extensoesErradas = "";
		String extensoesCorretas = "";

		if (arquivosUpados != null && arquivosUpados.size() > 0) {

			for (UploadDTO uploadDTO : arquivosUpados) {
				String path = uploadDTO.getPath();

				String ext[] = null;
				if(path != null){
					ext = path.split("\\.");
				}
				
				int i = 0;
				if(ext != null){
					i = ext.length;
				}

				if (i > 0) {
					if (ext[i - 1].equalsIgnoreCase("ocs") || ext[i - 1].equalsIgnoreCase("xml")) {

						if (path != null && !path.isEmpty()) {
							try {
								reader = new FileReader(path);
								BufferedReader lerArq = new BufferedReader(reader);
								String linha = lerArq.readLine();

								while (linha != null) {
									resultado += linha;
									linha = lerArq.readLine();
								}

								new Servidor().getGravarItemConfiguracao(resultado);
								reader.close();
								extensoesCorretas += "\n " + uploadDTO.getNameFile() + ";";
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					} else {
						extensoesErradas += "\n" + uploadDTO.getNameFile() + ";";
					}
				} else {
					document.alert(UtilI18N.internacionaliza(request, "uploadAgente.arquivos_formato_xml"));
					return;
				}
			}

			document.executeScript("fechar_aguarde();");

			if (extensoesErradas != null && !extensoesErradas.equalsIgnoreCase("")) {
				document.alert(UtilI18N.internacionaliza(request, "uploadAgente.arquivos_nao_registrados") + "\n" + UtilI18N.internacionaliza(request, "uploadAgente.arquivos_nao_formato")
						+ extensoesErradas);
			}

			if (extensoesCorretas != null && !extensoesCorretas.equalsIgnoreCase("")) {
				document.alert(UtilI18N.internacionaliza(request, "uploadAgente.arquivos_registrados_sucesso") + extensoesCorretas);
			}

			HTMLForm formUpload = document.getForm("formUpload");
			formUpload.clear();
			request.getSession(true).setAttribute("colUploadsGED", null);
		} else {
			document.executeScript("fechar_aguarde();");
			document.alert(UtilI18N.internacionaliza(request, "uploadAgente.nenhum_arquivo_selecionado"));
		}
	}

	@Override
	public Class getBeanClass() {
		return CategoriaProdutoDTO.class;
	}
}