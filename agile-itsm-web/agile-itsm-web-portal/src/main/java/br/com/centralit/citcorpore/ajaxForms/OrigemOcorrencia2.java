package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.negocio.OrigemOcorrenciaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author thiago.monteiro
 */

@SuppressWarnings("rawtypes")
public class OrigemOcorrencia2 extends AjaxFormAction {
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.focusInFirstActivateField(null);
	}

	@Override
	public Class getBeanClass() {
		return OrigemOcorrenciaDTO.class;
	}
	
	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrigemOcorrenciaDTO origemOcorrenciaDTO = (OrigemOcorrenciaDTO) document.getBean();
		OrigemOcorrenciaService origemOcorrenciaService = (OrigemOcorrenciaService) ServiceLocator.getInstance().getService(OrigemOcorrenciaService.class, null);
		
		// Verifica se o DTO e o servico existem.
		if (origemOcorrenciaDTO != null && origemOcorrenciaService != null) {
			origemOcorrenciaDTO.setDataInicio(UtilDatas.getDataAtual() );
			// Inserir
			if (origemOcorrenciaDTO.getIdOrigemOcorrencia() == null) {			
				origemOcorrenciaService.create(origemOcorrenciaDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05") );	
			} else {
				// Atualiar
				origemOcorrenciaService.update(origemOcorrenciaDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG06") );
			}
			HTMLForm form = document.getForm("formOrigemOcorrencia");
			form.clear();
		}
	}
	
	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrigemOcorrenciaDTO origemOcorrenciaDTO = (OrigemOcorrenciaDTO) document.getBean();
		OrigemOcorrenciaService origemOcorrenciaService = (OrigemOcorrenciaService) ServiceLocator.getInstance().
				getService(OrigemOcorrenciaService.class, WebUtil.getUsuarioSistema(request) );
		
		if (origemOcorrenciaDTO != null && origemOcorrenciaService != null) {
			if (origemOcorrenciaDTO.getIdOrigemOcorrencia() != null && (origemOcorrenciaDTO.getIdOrigemOcorrencia().intValue() > 0) ) {
				origemOcorrenciaService.deletarOrigemOcorrencia(origemOcorrenciaDTO, document);
				HTMLForm form = document.getForm("formOrigemOcorrencia");
				form.clear();
			}
		}
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrigemOcorrenciaDTO origemOcorrenciaDTO = (OrigemOcorrenciaDTO) document.getBean();
		OrigemOcorrenciaService origemOcorrenciaService = (OrigemOcorrenciaService) ServiceLocator.getInstance().
				getService(OrigemOcorrenciaService.class, null);
		
		if (origemOcorrenciaDTO != null && origemOcorrenciaService != null) {
			origemOcorrenciaDTO = (OrigemOcorrenciaDTO) origemOcorrenciaService.restore(origemOcorrenciaDTO);		
			HTMLForm form = document.getForm("formOrigemOcorrencia");
			form.clear();
			form.setValues(origemOcorrenciaDTO);
		}		
	}	
}
