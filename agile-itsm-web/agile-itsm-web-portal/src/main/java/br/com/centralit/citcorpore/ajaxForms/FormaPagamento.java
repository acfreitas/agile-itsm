package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.FormaPagamentoDTO;
import br.com.centralit.citcorpore.negocio.FormaPagamentoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class FormaPagamento extends AjaxFormAction{

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return FormaPagamentoDTO.class;
	}

	/**
	 * Metodo de salvar
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FormaPagamentoDTO formaPagamento = (FormaPagamentoDTO) document.getBean();
		FormaPagamentoService formaPagamentoService = (FormaPagamentoService) ServiceLocator.getInstance().getService(FormaPagamentoService.class, null);
		
		if (formaPagamento.getIdFormaPagamento() == null || formaPagamento.getIdFormaPagamento() == 0) {
			if (formaPagamentoService.consultarFormaPagamento(formaPagamento)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			formaPagamento.setSituacao("A");
			formaPagamentoService.create(formaPagamento);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (formaPagamentoService.consultarFormaPagamento(formaPagamento)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			formaPagamento.setSituacao("A");
			formaPagamentoService.update(formaPagamento);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_FORMAPAGAMENTO()");
	}

	/**
	 * Metodo colocar status Inativo quando for solicitado a exclusão do usuario.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FormaPagamentoDTO formaPagamentoDto = (FormaPagamentoDTO) document.getBean();
		FormaPagamentoService formaPagamentoService = (FormaPagamentoService) ServiceLocator.getInstance().getService(FormaPagamentoService.class, null);
		

		if (formaPagamentoDto.getIdFormaPagamento().intValue() > 0) {
			formaPagamentoService.deletarFormaPagamento(formaPagamentoDto, document,request);
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_FORMAPAGAMENTO()");
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
		FormaPagamentoDTO formaPagamentoDto = (FormaPagamentoDTO) document.getBean();
		FormaPagamentoService formaPagamentoService = (FormaPagamentoService) ServiceLocator.getInstance().getService(FormaPagamentoService.class, null);
		formaPagamentoDto = (FormaPagamentoDTO) formaPagamentoService.restore(formaPagamentoDto);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(formaPagamentoDto);
	}
}
