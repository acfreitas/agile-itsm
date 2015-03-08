package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Upload;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;

public class UploadFile extends AjaxFormAction {

	public Class getBeanClass() {
		return UploadDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
           /* document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;*/
        	usuario = new UsuarioDTO();
        	usuario.setIdEmpresa(1);
        	
        }
        
		Upload upload = new Upload();
		HashMap hshRetorno[] = upload.doUploadAll(request);

		Collection fileItems = hshRetorno[1].values();

		request.getSession().setAttribute("ARQUIVOS_UPLOAD", null);

		Collection colNomesArquivosUpload = new ArrayList();

		String fileName = "";
		FileItem fi;
		if (!fileItems.isEmpty()) {
			Iterator it = fileItems.iterator();
			File arquivo;
			while (it.hasNext()) {
				fi = (FileItem) it.next();

				String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP
						+ "/tempUpload";

				File fileDir = new File(strTempUpload);
				if (!fileDir.exists()) {
					fileDir.mkdir();
				}
				strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
				fileDir = new File(strTempUpload);
				if (!fileDir.exists()) {
					fileDir.mkdir();
				}
				fileName = "FILE_"
						+ br.com.citframework.util.Util.geraSenhaAleatoria(6)
						+ "_" + Util.getNameFile(fi.getName());
				arquivo = new File(strTempUpload + "/" + fileName);
				try {
					fi.write(arquivo);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(e);
				}
				UploadDTO uploadItem = new UploadDTO();
				uploadItem.setNameFile(fileName);
				uploadItem.setPath(strTempUpload + "/" + fileName);
				uploadItem.setCaminhoRelativo(Constantes.getValue("CONTEXTO_APLICACAO")
								+ "/tempUpload/"
								+ usuario.getIdEmpresa()
								+ "/"
								+ fileName);

				colNomesArquivosUpload.add(uploadItem);
			}
		}

		request.getSession().setAttribute("ARQUIVOS_UPLOAD", colNomesArquivosUpload);
}
}
