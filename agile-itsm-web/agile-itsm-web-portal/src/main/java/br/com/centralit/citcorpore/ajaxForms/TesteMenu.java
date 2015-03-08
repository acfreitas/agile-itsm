package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;

public class TesteMenu extends AjaxFormAction {

    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

    }

    @Override
    public Class getBeanClass() {
	return null;
    }
}
