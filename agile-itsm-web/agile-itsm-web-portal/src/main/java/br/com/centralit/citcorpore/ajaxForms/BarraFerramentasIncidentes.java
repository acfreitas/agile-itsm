/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BarraFerramentasIncidentesDTO;

@SuppressWarnings("rawtypes")
public class BarraFerramentasIncidentes extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return BarraFerramentasIncidentesDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

}
