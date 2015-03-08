package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.negocio.JustificativaRequisicaoFuncaoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class JustificativaRequisicaoFuncao extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);
		

	}

	@Override
	public Class getBeanClass() {
		return JustificativaRequisicaoFuncaoDTO.class;
	}

	/**
	 * Metodo de salvar
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exceptio
	 * @author thiago.borges
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JustificativaRequisicaoFuncaoDTO justificativaRequisicaoFuncaoDTO = (JustificativaRequisicaoFuncaoDTO) document.getBean();

		JustificativaRequisicaoFuncaoService justificativaRequisicaoFuncaoService = (JustificativaRequisicaoFuncaoService) ServiceLocator.getInstance().getService(JustificativaRequisicaoFuncaoService.class, null);
		if (justificativaRequisicaoFuncaoDTO.getIdJustificativa() == null || justificativaRequisicaoFuncaoDTO.getIdJustificativa() == 0) {
			if (justificativaRequisicaoFuncaoService.consultarJustificativasAtivas(justificativaRequisicaoFuncaoDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			justificativaRequisicaoFuncaoService.create(justificativaRequisicaoFuncaoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (justificativaRequisicaoFuncaoService.consultarJustificativasAtivas(justificativaRequisicaoFuncaoDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			justificativaRequisicaoFuncaoService.update(justificativaRequisicaoFuncaoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_JUSTIFICATIVAREQUISICAOFUNCAO()");
	}

	

	/**
	 * Metodo para restaura os campos.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JustificativaRequisicaoFuncaoDTO justificativaRequisicaoFuncaoDTO = (JustificativaRequisicaoFuncaoDTO) document.getBean();
		JustificativaRequisicaoFuncaoService justificativaRequisicaoFuncaoService = (JustificativaRequisicaoFuncaoService) ServiceLocator.getInstance().getService(JustificativaRequisicaoFuncaoService.class, null);
		justificativaRequisicaoFuncaoDTO = (JustificativaRequisicaoFuncaoDTO) justificativaRequisicaoFuncaoService.restore(justificativaRequisicaoFuncaoDTO);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(justificativaRequisicaoFuncaoDTO);
	}

}
