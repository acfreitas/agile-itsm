package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UploadDTO;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class UploadExcluir extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return UploadDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UploadDTO uploadDTO = (UploadDTO) document.getBean();
		// ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		// ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
		String flagGerenciamento = (String)request.getSession(true).getAttribute("flagGerenciamento");
		Collection colUploadsGED = null;
        if(flagGerenciamento!= null && flagGerenciamento.equalsIgnoreCase("S")){
        colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGED2");	
        }else {
		colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGED");
        }
		if (colUploadsGED == null) {
			colUploadsGED = new ArrayList();
		}
		Collection col2 = new ArrayList();
		for (Iterator it = colUploadsGED.iterator(); it.hasNext();) {
			UploadDTO uploadAux = (UploadDTO) it.next();
			if (!uploadAux.getPath().equalsIgnoreCase(uploadDTO.getPath().replace("\\", "\\\\"))) {
				col2.add(uploadAux);
			}/*
			 * else{ uploadAux.getNameFile(); uploadAux.getPath(); controleGEDDTO.setId(new Integer(uploadAux.getId())); }
			 */
		}
		if(flagGerenciamento!= null && flagGerenciamento.equalsIgnoreCase("S")){
			request.getSession(true).setAttribute("colUploadsGED2", col2);	
		} else {
			request.getSession(true).setAttribute("colUploadsGED", col2);
		}		
	}
}
