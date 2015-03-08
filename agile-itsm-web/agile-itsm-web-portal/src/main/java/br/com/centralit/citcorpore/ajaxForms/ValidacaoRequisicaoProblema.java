package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ValidacaoRequisicaoProblemaDTO;
import br.com.centralit.citcorpore.negocio.ValidacaoRequisicaoProblemaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author geber.costa
 * 
 */

public class ValidacaoRequisicaoProblema extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) document.getBean();
		
		ValidacaoRequisicaoProblemaService validacaoRequisicaoProblemaService = (ValidacaoRequisicaoProblemaService) ServiceLocator.getInstance()
				.getService(ValidacaoRequisicaoProblemaService.class, null);
		
		if(validacaoRequisicaoProblemaDto.getIdProblema()!=null){
			validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) validacaoRequisicaoProblemaService.findByIdProblema(validacaoRequisicaoProblemaDto.getIdProblema());
			if(validacaoRequisicaoProblemaDto !=null)
			if(validacaoRequisicaoProblemaDto.getIdValidacaoRequisicaoProblema()!=null){
				this.restoreValidacaoRequisicaoProblema(document, request, response, validacaoRequisicaoProblemaDto);
			}
			
		}
		
	    }

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) document.getBean();
		ValidacaoRequisicaoProblemaService validacaoRequisicaoProblemaService = (ValidacaoRequisicaoProblemaService) ServiceLocator.getInstance().getService(ValidacaoRequisicaoProblemaService.class, null);

		if (validacaoRequisicaoProblemaDto.getObservacaoProblema() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}

		if (validacaoRequisicaoProblemaDto.getDataInicio() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}
		if (validacaoRequisicaoProblemaDto.getDataFim() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}

		if (validacaoRequisicaoProblemaDto.getIdValidacaoRequisicaoProblema() == null || validacaoRequisicaoProblemaDto.getIdValidacaoRequisicaoProblema().intValue() == 0) {
			validacaoRequisicaoProblemaService.create(validacaoRequisicaoProblemaDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			validacaoRequisicaoProblemaService.update(validacaoRequisicaoProblemaDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
	}
	
	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void restoreValidacaoRequisicaoProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response , ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto) throws Exception {
		
		
		ValidacaoRequisicaoProblemaService validacaoRequisicaoProblemaService = (ValidacaoRequisicaoProblemaService) ServiceLocator.getInstance().getService(ValidacaoRequisicaoProblemaService.class, null);
		
		if(validacaoRequisicaoProblemaDto.getIdValidacaoRequisicaoProblema()!=null){
			validacaoRequisicaoProblemaDto = 	(ValidacaoRequisicaoProblemaDTO) validacaoRequisicaoProblemaService.restore(validacaoRequisicaoProblemaDto);
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(validacaoRequisicaoProblemaDto);
		
		validacaoRequisicaoProblemaDto = null;
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
		
		ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) document.getBean();
		
		ValidacaoRequisicaoProblemaService validacaoRequisicaoProblemaService = (ValidacaoRequisicaoProblemaService) ServiceLocator.getInstance()
				.getService(ValidacaoRequisicaoProblemaService.class, null);
	
		validacaoRequisicaoProblemaDto = (ValidacaoRequisicaoProblemaDTO) validacaoRequisicaoProblemaService.restore(validacaoRequisicaoProblemaDto);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(validacaoRequisicaoProblemaDto);
	}
	
	public Class<ValidacaoRequisicaoProblemaDTO> getBeanClass() {
		return ValidacaoRequisicaoProblemaDTO.class;
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ValidacaoRequisicaoProblemaDTO validacaoRequisicaoProblemaDTO = (ValidacaoRequisicaoProblemaDTO) document.getBean();

		ValidacaoRequisicaoProblemaService validacaoRequisicaoProblemaService = (ValidacaoRequisicaoProblemaService) ServiceLocator.getInstance().getService(ValidacaoRequisicaoProblemaService.class,
				WebUtil.getUsuarioSistema(request));

		if (validacaoRequisicaoProblemaDTO.getIdValidacaoRequisicaoProblema().intValue() > 0) {
			validacaoRequisicaoProblemaService.delete(validacaoRequisicaoProblemaDTO);
		}
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

		HTMLForm form = document.getForm("form");
		form.clear();
	}

}
