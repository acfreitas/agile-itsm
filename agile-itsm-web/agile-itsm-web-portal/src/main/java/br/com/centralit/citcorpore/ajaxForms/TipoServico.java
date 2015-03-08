package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.TipoServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 * @author leandro.viana
 *
 */
@SuppressWarnings("rawtypes")
public class TipoServico extends AjaxFormAction {

	/**
	 * Inicializa dados ao carregar a tela.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		document.focusInFirstActivateField(null);
	}

	/**
	 * Inclui ou Atualiza um registro.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoServicoDTO tipoServicoDTO = (TipoServicoDTO) document.getBean();
		TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);

		if (tipoServicoDTO.getIdTipoServico() == null || tipoServicoDTO.getIdTipoServico().intValue() == 0) {
			tipoServicoDTO.setIdEmpresa(WebUtil.getIdEmpresa(request));

			if (!tipoServicoService.verificarSeTipoServicoExiste(tipoServicoDTO)) {
				tipoServicoDTO.setSituacao("A");
				tipoServicoService.create(tipoServicoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
			}
		} else {
			if (!tipoServicoService.verificarSeTipoServicoExiste(tipoServicoDTO)) {
				tipoServicoService.update(tipoServicoDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			} else {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
			}
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar_LOOKUP_TIPOSERVICO()");
	}

	/**
	 * Restaura os dados ao clicar em um registro na tela.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoServicoDTO tipoServicoDTO = (TipoServicoDTO) document.getBean();
		TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);

		tipoServicoDTO = (TipoServicoDTO) tipoServicoService.restore(tipoServicoDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(tipoServicoDTO);
	}

	/**
	 * Altera a situação da prioridade para Inativo ao confirmar a exclusão.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void alterarSituacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TipoServicoDTO tipoServicoDTO = (TipoServicoDTO) document.getBean();

		TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);

		if (!tipoServicoService.existeVinculadoCadastroServico(tipoServicoDTO.getIdTipoServico())){

			if (tipoServicoDTO.getIdTipoServico() != null && tipoServicoDTO.getIdTipoServico().intValue() != 0) {
				tipoServicoDTO.setSituacao("I");
				tipoServicoService.update(tipoServicoDTO);
				HTMLForm form = document.getForm("form");
				form.clear();
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
				document.executeScript("limpar_LOOKUP_TIPOSERVICO()");
			}

		}else{

			document.alert(UtilI18N.internacionaliza(request, "exclusao.comun.cadastroServico"));
		}
	}


	public Class getBeanClass() {
		return TipoServicoDTO.class;
	}

}
