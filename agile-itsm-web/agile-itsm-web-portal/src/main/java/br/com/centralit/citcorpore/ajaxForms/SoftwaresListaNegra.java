package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.SoftwaresListaNegraDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.SoftwaresListaNegraService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 * @author ronnie.lopes
 *
 */
public class SoftwaresListaNegra extends AjaxFormAction {
	
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SoftwaresListaNegraDTO softwaresListaNegraDTO = (SoftwaresListaNegraDTO) document.getBean();
		SoftwaresListaNegraService softwaresListaNegraService = (SoftwaresListaNegraService) ServiceLocator.getInstance().getService(SoftwaresListaNegraService.class, null);

		if (!softwaresListaNegraService.verficiarSoftwareListaNegraMesmoNome(softwaresListaNegraDTO.getNomeSoftwaresListaNegra())) {
			if (softwaresListaNegraDTO.getIdSoftwaresListaNegra() == null || softwaresListaNegraDTO.getIdSoftwaresListaNegra().intValue() == 0) {
				softwaresListaNegraDTO.setIdSoftwaresListaNegra(WebUtil.getIdEmpresa(request));
				softwaresListaNegraService.create(softwaresListaNegraDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				softwaresListaNegraService.update(softwaresListaNegraDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_SOFTWARESLISTANEGRA()");
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SoftwaresListaNegraDTO softwaresListaNegraDTO = (SoftwaresListaNegraDTO) document.getBean();
		SoftwaresListaNegraService softwaresListaNegraService = (SoftwaresListaNegraService) ServiceLocator.getInstance().getService(SoftwaresListaNegraService.class, null);

		softwaresListaNegraDTO = (SoftwaresListaNegraDTO) softwaresListaNegraService.restore(softwaresListaNegraDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(softwaresListaNegraDTO);
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SoftwaresListaNegraDTO softwaresListaNegraDTO = (SoftwaresListaNegraDTO) document.getBean();
		SoftwaresListaNegraService softwaresListaNegraService = (SoftwaresListaNegraService) ServiceLocator.getInstance().getService(SoftwaresListaNegraService.class, null);

		if (softwaresListaNegraDTO.getIdSoftwaresListaNegra() != null && softwaresListaNegraDTO.getIdSoftwaresListaNegra() != 0) {
			softwaresListaNegraService.delete(softwaresListaNegraDTO);

			HTMLForm form = document.getForm("form");
			form.clear();
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}
		document.executeScript("limpar_LOOKUP_SOFTWARESLISTANEGRA()");
	}

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return SoftwaresListaNegraDTO.class;
	}
}
