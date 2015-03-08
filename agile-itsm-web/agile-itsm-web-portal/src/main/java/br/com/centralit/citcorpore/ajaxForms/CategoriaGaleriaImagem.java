package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CategoriaGaleriaImagemDTO;
import br.com.centralit.citcorpore.negocio.CategoriaGaleriaImagemService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class CategoriaGaleriaImagem extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);

	}

	@Override
	public Class getBeanClass() {
		return CategoriaGaleriaImagemDTO.class;
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
		CategoriaGaleriaImagemDTO categoriaGaleriaImagem = (CategoriaGaleriaImagemDTO) document.getBean();

		CategoriaGaleriaImagemService categoriaGaleriaImagemService = (CategoriaGaleriaImagemService) ServiceLocator.getInstance().getService(CategoriaGaleriaImagemService.class, null);
		if (categoriaGaleriaImagem.getIdCategoriaGaleriaImagem() == null || categoriaGaleriaImagem.getIdCategoriaGaleriaImagem() == 0) {
			if (categoriaGaleriaImagemService.consultarCategoriaImagemAtivos(categoriaGaleriaImagem)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			categoriaGaleriaImagem.setDataInicio(UtilDatas.getDataAtual());
			categoriaGaleriaImagemService.create(categoriaGaleriaImagem);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (categoriaGaleriaImagemService.consultarCategoriaImagemAtivos(categoriaGaleriaImagem)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			categoriaGaleriaImagemService.update(categoriaGaleriaImagem);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_CATEGORIAGALERIAIMAGEM()");
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
		CategoriaGaleriaImagemDTO categoriaGaleriaImagem = (CategoriaGaleriaImagemDTO) document.getBean();

		CategoriaGaleriaImagemService categoriaGaleriaImagemService = (CategoriaGaleriaImagemService) ServiceLocator.getInstance().getService(CategoriaGaleriaImagemService.class, WebUtil.getUsuarioSistema(request));

		if (categoriaGaleriaImagem.getIdCategoriaGaleriaImagem().intValue() > 0) {
			categoriaGaleriaImagemService.deletarCategoriaImagem(categoriaGaleriaImagem, document);
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_CATEGORIAGALERIAIMAGEM()");
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
		CategoriaGaleriaImagemDTO categoriaGaleriaImagemDTO = (CategoriaGaleriaImagemDTO) document.getBean();
		CategoriaGaleriaImagemService categoriaGaleriaImagemService = (CategoriaGaleriaImagemService) ServiceLocator.getInstance().getService(CategoriaGaleriaImagemService.class, null);
		categoriaGaleriaImagemDTO = (CategoriaGaleriaImagemDTO) categoriaGaleriaImagemService.restore(categoriaGaleriaImagemDTO);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(categoriaGaleriaImagemDTO);
	}

}
