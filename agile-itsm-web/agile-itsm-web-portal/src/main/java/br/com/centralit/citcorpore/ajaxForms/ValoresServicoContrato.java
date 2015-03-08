package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ValoresServicoContratoDTO;
import br.com.centralit.citcorpore.negocio.ValoresServicoContratoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author flávio.santana
 *
 */
public class ValoresServicoContrato extends AjaxFormAction {
	
	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}
	
	/**
	 * Inclui registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValoresServicoContratoDTO valoresServicoContrato = (ValoresServicoContratoDTO) document.getBean();
		ValoresServicoContratoService valorServicoContrato = (ValoresServicoContratoService) ServiceLocator.getInstance().getService(ValoresServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		
		if (valoresServicoContrato.getIdValorServicoContrato() == null || valoresServicoContrato.getIdValorServicoContrato().intValue() == 0) {
			valorServicoContrato.create(valoresServicoContrato);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			valorServicoContrato.update(valoresServicoContrato);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("closePopup(" + valoresServicoContrato.getIdServicoContrato() + ");");
	}
	
	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValoresServicoContratoDTO valoresServicoContrato = (ValoresServicoContratoDTO) document.getBean();
		ValoresServicoContratoService valorServicoContrato = (ValoresServicoContratoService) ServiceLocator.getInstance().getService(ValoresServicoContratoService.class, null);
		
		valoresServicoContrato = (ValoresServicoContratoDTO) valorServicoContrato.restore(valoresServicoContrato);
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(valoresServicoContrato);
	}
	
	/**
	 * recupera os dados ao carregar página
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void recupera(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValoresServicoContratoDTO valoresServicoContrato = (ValoresServicoContratoDTO) document.getBean();
		ValoresServicoContratoService valorServicoContrato = (ValoresServicoContratoService) ServiceLocator.getInstance().getService(ValoresServicoContratoService.class, null);
		
		if(valoresServicoContrato.getIdValorServicoContrato()!=null) {
			valoresServicoContrato = (ValoresServicoContratoDTO) valorServicoContrato.restore(valoresServicoContrato);
			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(valoresServicoContrato);
		}
	}
	
	
	public Class<ValoresServicoContratoDTO> getBeanClass() {
		return ValoresServicoContratoDTO.class;
	}
	
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValoresServicoContratoDTO valoresServicoContrato = (ValoresServicoContratoDTO) document.getBean();
		ValoresServicoContratoService valorServicoContrato = (ValoresServicoContratoService) ServiceLocator.getInstance().getService(ValoresServicoContratoService.class, null);

		if (valoresServicoContrato.getIdValorServicoContrato() != null && valoresServicoContrato.getIdValorServicoContrato()!= 0) {
			valorServicoContrato.delete(valoresServicoContrato);

			HTMLForm form = document.getForm("form");
			form.clear();
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}
		document.executeScript("closePopup(" + valoresServicoContrato.getIdServicoContrato() + ");");
	}

}
