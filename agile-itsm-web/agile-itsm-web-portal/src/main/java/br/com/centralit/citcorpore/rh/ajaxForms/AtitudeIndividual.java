package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.centralit.citcorpore.rh.negocio.AtitudeIndividualService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AtitudeIndividual extends AjaxFormAction {

	public Class getBeanClass() {
		return AtitudeIndividualDTO.class;
	}

	private boolean validaSessao(DocumentHTML document, HttpServletRequest request) {

		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {

			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");

			return false;
		}

		return true;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!this.validaSessao(document, request)) {

			return;
		}

		AtitudeIndividualDTO atitudesIndividuaisDto = new AtitudeIndividualDTO();

		HTMLForm form = document.getForm("form");
		form.setValues(atitudesIndividuaisDto);
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!this.validaSessao(document, request)) {

			return;
		}

		AtitudeIndividualDTO atitudesIndividuaisDto = (AtitudeIndividualDTO) document.getBean();
		AtitudeIndividualService atitudesIndividuaisService = (AtitudeIndividualService) ServiceLocator.getInstance().getService(AtitudeIndividualService.class, null);

		Collection<AtitudeIndividualDTO> colAtitudeIndividualDTOs = atitudesIndividuaisService.findByNome(atitudesIndividuaisDto.getDescricao());

		if (atitudesIndividuaisDto.getIdAtitudeIndividual() != null) {
			atitudesIndividuaisService.update(atitudesIndividuaisDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		} else {
			 if(colAtitudeIndividualDTOs == null || colAtitudeIndividualDTOs.size() < 1){
				atitudesIndividuaisService.create(atitudesIndividuaisDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			 }else{
           	  document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
             }
		}

		document.executeScript("limpar()");
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!this.validaSessao(document, request)) {

			return;
		}

		AtitudeIndividualDTO atitudesIndividuaisDto = (AtitudeIndividualDTO) document.getBean();

		AtitudeIndividualService atitudesIndividuaisService = (AtitudeIndividualService) ServiceLocator.getInstance().getService(AtitudeIndividualService.class, null);
		atitudesIndividuaisDto = (AtitudeIndividualDTO) atitudesIndividuaisService.restore(atitudesIndividuaisDto);

		atitudesIndividuaisService.delete(atitudesIndividuaisDto);
		document.executeScript("limpar()");

		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!this.validaSessao(document, request)) {

			return;
		}

		AtitudeIndividualDTO atitudesIndividuaisDto = (AtitudeIndividualDTO) document.getBean();
		if (atitudesIndividuaisDto.getIdAtitudeIndividual() == null)
			return;

		AtitudeIndividualService atitudesIndividuaisService = (AtitudeIndividualService) ServiceLocator.getInstance().getService(AtitudeIndividualService.class, null);
		atitudesIndividuaisDto = (AtitudeIndividualDTO) atitudesIndividuaisService.restore(atitudesIndividuaisDto);

		HTMLForm form = document.getForm("form");
		form.setValues(atitudesIndividuaisDto);
	}

	public void exibeSelecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		AtitudeIndividualService atitudesIndividuaisService = (AtitudeIndividualService) ServiceLocator.getInstance().getService(AtitudeIndividualService.class, null);

		Collection<AtitudeIndividualDTO> colAtitudeIndividual = atitudesIndividuaisService.list();

		if (colAtitudeIndividual != null) {
			for (AtitudeIndividualDTO atitudesIndividuaisDto : colAtitudeIndividual) {
				document.executeScript("incluirOpcaoSelecao(\"" + atitudesIndividuaisDto.getIdAtitudeIndividual() + "\",\"" + atitudesIndividuaisDto.getDescricao().trim() + "\",\""
						+ atitudesIndividuaisDto.getDetalhe().trim() + "\");");
			}
		}
	}
}
