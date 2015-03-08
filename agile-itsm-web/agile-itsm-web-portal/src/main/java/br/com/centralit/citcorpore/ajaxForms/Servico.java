package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.citframework.service.ServiceLocator;
@SuppressWarnings("rawtypes")
public class Servico extends AjaxFormAction {

  
    @Override
    public Class getBeanClass() {
	return ServicoDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

    }

    public void save(DocumentHTML document, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	HTMLForm form = document.getForm("form");
	form.clear();

	// document.alert("Registro gravado com sucesso!");
    }

    public void restore(DocumentHTML document, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	ServicoDTO servico = (ServicoDTO) document.getBean();
	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(
		ServicoService.class, null);

	servico = (ServicoDTO) servicoService.restore(servico);
	System.out.println("Restored");

	document.executeScript("alert('teste');");
	
	//HTMLForm form = document.getForm("form");

	//form.clear();
	//form.setValues(servico);
    }

}
