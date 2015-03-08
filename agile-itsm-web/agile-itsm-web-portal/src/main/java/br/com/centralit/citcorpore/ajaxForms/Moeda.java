package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.MoedaDTO;
import br.com.centralit.citcorpore.negocio.MoedaService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class Moeda extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.focusInFirstActivateField(null);
	}

	@Override
	public Class getBeanClass() {
		return MoedaDTO.class;
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exceptio
	 * Metodo de salvar
	 * @author flavio.santana
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MoedaDTO moedaDTO = (MoedaDTO) document.getBean();

		MoedaService moedaDTOService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
		if (moedaDTO.getIdMoeda() == null || moedaDTO.getIdMoeda() == 0) {
			if (moedaDTOService.verificaSeCadastrado(moedaDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			moedaDTO.setDataInicio(UtilDatas.getDataAtual());
			moedaDTOService.create(moedaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			moedaDTOService.update(moedaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *          
	 */

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MoedaDTO moedaDTO = (MoedaDTO) document.getBean();
		MoedaService moedaService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
		
		if(!moedaService.verificaRelacionamento(moedaDTO)) {
			moedaDTO.setDataFim(UtilDatas.getDataAtual());
			moedaService.updateNotNull(moedaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}else {
			document.alert(UtilI18N.internacionaliza(request, "moeda.possui_relacionamento"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *             Metodo para restaura os campos.
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MoedaDTO moedaDTO = (MoedaDTO) document.getBean();
		MoedaService moedaDTOService = (MoedaService) ServiceLocator.getInstance().getService(MoedaService.class, null);
		moedaDTO = (MoedaDTO) moedaDTOService.restore(moedaDTO);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(moedaDTO);

	}

}
