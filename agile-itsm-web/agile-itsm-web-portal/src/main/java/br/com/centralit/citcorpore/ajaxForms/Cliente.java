package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class Cliente extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		/*
		 * if (!WebUtil.isUserInGroup(request, Constantes.getValue("GRUPO_DIRETORIA"))){ document.alert("Você não tem permissão para acessar esta funcionalidade!");
		 * document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'"); return; }
		 */
		HTMLSelect comboIdSituacaoFuncional = (HTMLSelect) document.getSelectById("situacao");

		comboIdSituacaoFuncional.addOption("", "-- Selecione --");
		comboIdSituacaoFuncional.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.ativo"));
		comboIdSituacaoFuncional.addOption("I", UtilI18N.internacionaliza(request, "citcorpore.comum.inativo"));

		document.focusInFirstActivateField(null);
	}

	public void Cliente_onsave(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ClienteDTO cliente = (ClienteDTO) document.getBean();
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);

		if (cliente.getIdCliente() == null || cliente.getIdCliente().intValue() == 0) {
			clienteService.create(cliente);
		} else {
			clienteService.update(cliente);
		}
		HTMLForm form = document.getForm("form");
		form.clear();

		document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		document.executeScript("limpar_LOOKUP_CLIENTE()");
	}

	public void Cliente_onrestore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ClienteDTO cliente = (ClienteDTO) document.getBean();
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);

		cliente = (ClienteDTO) clienteService.restore(cliente);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(cliente);

	}

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return ClienteDTO.class;
	}
}
