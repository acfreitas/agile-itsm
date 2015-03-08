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
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class UploadAjax extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return UploadDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		br.com.centralit.citcorpore.util.Upload upload = new br.com.centralit.citcorpore.util.Upload();
		HashMap hshRetorno[] = upload.doUploadAll(request);

		Collection fileItems = hshRetorno[1].values();
		HashMap formItems = hshRetorno[0];

		String descUploadFile = (String) formItems.get("DESCUPLOADFILE_UPLOADANEXOS");
		if (descUploadFile != null) {
			descUploadFile = UtilStrings.decodeCaracteresEspeciais(descUploadFile);
			descUploadFile = descUploadFile.replaceAll("#10##13#", "\n"); // codificacao do CITFramework
		}

		Collection colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGEDAjax");
		if (colUploadsGED == null) {
			colUploadsGED = new ArrayList();
		}

		File f = new File(CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload");
		if (!f.exists()) {
			f.mkdirs();
		}

		// String fileName = "";
		FileItem fi;
		if (!fileItems.isEmpty()) {
			File arquivo;
			// List filesDel = new ArrayList();
			Iterator it = fileItems.iterator();
			while (it.hasNext()) {
				fi = (FileItem) it.next();

				if (fi.getSize() > 0 && fi.getFieldName() != null) {
					UploadDTO uploadDTO = new UploadDTO();
					uploadDTO.setDescricao(descUploadFile);
					uploadDTO.setNameFile(CITCorporeUtil.getNameFile(fi.getName()));
					uploadDTO.setSituacao("Temporário");
					uploadDTO.setTemporario("S");
					uploadDTO.setPath(CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload/" + uploadDTO.getNameFile());

					arquivo = new File(uploadDTO.getPath());
					fi.write(arquivo);

					colUploadsGED.add(uploadDTO);
				}
			}
		}
		request.getSession(true).setAttribute("colUploadsGEDAjax", colUploadsGED);
	}

}
