package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class OrigemAtendimento2 extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);

	}

	@Override
	public Class getBeanClass() {
		return OrigemAtendimentoDTO.class;
	}

	/**
	 * Metodo de salvar
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exceptio
	 * @author thays.araujo
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrigemAtendimentoDTO origemAtendimento = (OrigemAtendimentoDTO) document.getBean();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, WebUtil.getUsuarioSistema(request));
		if (origemAtendimento.getIdOrigem() == null || origemAtendimento.getIdOrigem() == 0) {
			if (origemAtendimentoService.consultarOrigemAtendimentoAtivos(origemAtendimento)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			origemAtendimento.setDataInicio(UtilDatas.getDataAtual());
			origemAtendimentoService.create(origemAtendimento);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (origemAtendimentoService.consultarOrigemAtendimentoAtivos(origemAtendimento)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			origemAtendimentoService.update(origemAtendimento);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar_LOOKUP_ORIGEMATENDIMENTO()");
		
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
		OrigemAtendimentoDTO origemAtendimento = (OrigemAtendimentoDTO) document.getBean();

		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, WebUtil.getUsuarioSistema(request));

		if (origemAtendimento.getIdOrigem().intValue() > 0) {
			origemAtendimentoService.deletarOrigemAtendimento(origemAtendimento, document);
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_ORIGEMATENDIMENTO()");
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
		OrigemAtendimentoDTO origemAtendimento = (OrigemAtendimentoDTO) document.getBean();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		origemAtendimento = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemAtendimento);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(origemAtendimento);
	}

}
