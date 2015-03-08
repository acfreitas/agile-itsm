package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class Cargos extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);
		

	}

	@Override
	public Class getBeanClass() {
		return CargosDTO.class;
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
		CargosDTO cargos = (CargosDTO) document.getBean();

		CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
		if (cargos.getIdCargo() == null || cargos.getIdCargo() == 0) {
			if (cargosService.consultarCargosAtivos(cargos)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			cargos.setDataInicio(UtilDatas.getDataAtual());
			cargosService.create(cargos);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (cargosService.consultarCargosAtivos(cargos)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			cargosService.update(cargos);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_CARGOS()");
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
		CargosDTO cargos = (CargosDTO) document.getBean();

		CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, WebUtil.getUsuarioSistema(request));

		if (cargos.getIdCargo().intValue() > 0) {
			cargosService.deletarCargo(cargos, document);
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_CARGOS()");
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
		CargosDTO cargos = (CargosDTO) document.getBean();
		CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
		cargos = (CargosDTO) cargosService.restore(cargos);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(cargos);
	}

}
