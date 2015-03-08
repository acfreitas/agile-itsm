package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UploadDTO;


public class UploadDocsLegaisExcluir extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return UploadDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UploadDTO uploadDTO = (UploadDTO) document.getBean();
		Collection colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGEDdocsLegais");
		if (colUploadsGED == null) {
			colUploadsGED = new ArrayList();
		}
		Collection col2 = new ArrayList();
		for (Iterator it = colUploadsGED.iterator(); it.hasNext();) {
			UploadDTO uploadAux = (UploadDTO) it.next();
			if (!uploadAux.getPath().equalsIgnoreCase(uploadDTO.getPath().replace("\\", "\\\\"))) {
				col2.add(uploadAux);
			}
		}
		request.getSession(true).setAttribute("colUploadsGEDdocsLegais", col2);
	}
}
