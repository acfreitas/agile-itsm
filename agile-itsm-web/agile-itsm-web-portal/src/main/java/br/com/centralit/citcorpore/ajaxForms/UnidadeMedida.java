package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.centralit.citcorpore.negocio.UnidadeMedidaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class UnidadeMedida extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {

		return UnidadeMedidaDTO.class;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UnidadeMedidaDTO unidadeMedida = (UnidadeMedidaDTO) document.getBean();
		
		if(unidadeMedida.getSituacao()== null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}

		UnidadeMedidaService unidadeMedidaService = (UnidadeMedidaService) ServiceLocator.getInstance().getService(UnidadeMedidaService.class, null);
		if (unidadeMedida.getIdUnidadeMedida() == null || unidadeMedida.getIdUnidadeMedida() == 0) {
			if (unidadeMedidaService.consultarUnidadesMedidas(unidadeMedida)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			unidadeMedidaService.create(unidadeMedida);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (unidadeMedidaService.consultarUnidadesMedidas(unidadeMedida)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			unidadeMedidaService.update(unidadeMedida);
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
	 *             Metodo colocar status Inativo quando for solicitado a exclusão do usuario.
	 */

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UnidadeMedidaDTO unidadeMedida = (UnidadeMedidaDTO) document.getBean();

		UnidadeMedidaService unidadeMedidaService = (UnidadeMedidaService) ServiceLocator.getInstance().getService(UnidadeMedidaService.class, WebUtil.getUsuarioSistema(request));

		if (unidadeMedida.getIdUnidadeMedida().intValue() > 0) {
			unidadeMedidaService.delete(unidadeMedida);
		}
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

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
		UnidadeMedidaDTO unidadeMedidaDTO = (UnidadeMedidaDTO) document.getBean();

		UnidadeMedidaService unidadeMedidaService = (UnidadeMedidaService) ServiceLocator.getInstance().getService(UnidadeMedidaService.class, null);

		unidadeMedidaDTO = (UnidadeMedidaDTO) unidadeMedidaService.restore(unidadeMedidaDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(unidadeMedidaDTO);

	}

}
