package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.TipoUnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.TipoUnidadeService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class TipoUnidade extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		/*
		 * if (!WebUtil.isUserInGroup(request, Constantes.getValue("GRUPO_DIRETORIA"))){ document.alert("Você não tem permissão para acessar esta funcionalidade!" );
		 * document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'"); return; }
		 */

		// document.focusInFirstActivateField(null);
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TipoUnidadeDTO tipoUnidade = (TipoUnidadeDTO) document.getBean();

		TipoUnidadeService tipoUnidadeService = (TipoUnidadeService) ServiceLocator.getInstance().getService(TipoUnidadeService.class, null);

		if (!tipoUnidadeService.jaExisteUnidadeComMesmoNome(tipoUnidade.getNomeTipoUnidade())) {
			if (tipoUnidade.getIdTipoUnidade() == null || tipoUnidade.getIdTipoUnidade().intValue() == 0) {
				tipoUnidade.setIdEmpresa(WebUtil.getIdEmpresa(request));

				tipoUnidadeService.create(tipoUnidade);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				tipoUnidadeService.update(tipoUnidade);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_TIPOUNIDADE()");

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TipoUnidadeDTO tipoUnidade = (TipoUnidadeDTO) document.getBean();
		TipoUnidadeService testeService = (TipoUnidadeService) ServiceLocator.getInstance().getService(TipoUnidadeService.class, null);

		tipoUnidade = (TipoUnidadeDTO) testeService.restore(tipoUnidade);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(tipoUnidade);

	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoUnidadeDTO tipoUnidade = (TipoUnidadeDTO) document.getBean();
		TipoUnidadeService tipoUnidadeService = (TipoUnidadeService) ServiceLocator.getInstance().getService(TipoUnidadeService.class, null);

		if (tipoUnidade.getIdTipoUnidade() != null && tipoUnidade.getIdTipoUnidade() != 0) {
			tipoUnidade.setDataFim(UtilDatas.getDataAtual());
			tipoUnidadeService.update(tipoUnidade);

			HTMLForm form = document.getForm("form");
			form.clear();
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

		document.executeScript("limpar_LOOKUP_TIPOUNIDADE()");
	}

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return TipoUnidadeDTO.class;
	}
}
