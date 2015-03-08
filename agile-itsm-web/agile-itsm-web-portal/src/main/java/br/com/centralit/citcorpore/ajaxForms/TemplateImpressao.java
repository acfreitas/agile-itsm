package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.TemplateImpressaoDTO;
import br.com.centralit.citcorpore.negocio.TemplateImpressaoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class TemplateImpressao extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TemplateImpressaoDTO templateImpressaoDTO = (TemplateImpressaoDTO) document.getBean();
		TemplateImpressaoService templateImpressaoService = (TemplateImpressaoService) ServiceLocator.getInstance().getService(TemplateImpressaoService.class, WebUtil.getUsuarioSistema(request) );
		if (templateImpressaoDTO.getIdTemplateImpressao() == null){
			templateImpressaoService.create(templateImpressaoDTO);
		}else{
			templateImpressaoService.update(templateImpressaoDTO);
		}
		
		document.alert(UtilI18N.internacionaliza(request, "dinamicview.gravadocomsucesso"));
		
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.executeScript("limpar()");		
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TemplateImpressaoDTO templateImpressaoDTO = (TemplateImpressaoDTO) document.getBean();
		TemplateImpressaoService templateImpressaoService = (TemplateImpressaoService) ServiceLocator.getInstance().getService(TemplateImpressaoService.class, WebUtil.getUsuarioSistema(request) );
		templateImpressaoDTO = (TemplateImpressaoDTO) templateImpressaoService.restore(templateImpressaoDTO);

		HTMLForm form = document.getForm("form");
		form.clear();		
		form.setValues(templateImpressaoDTO);

		document.executeScript("setDataEditor()");
	}	

	@Override
	public Class getBeanClass() {
		return TemplateImpressaoDTO.class;
	}

}
