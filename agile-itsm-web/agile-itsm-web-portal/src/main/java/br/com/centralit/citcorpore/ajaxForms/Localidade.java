package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.centralit.citcorpore.negocio.LocalidadeUnidadeService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class Localidade extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LocalidadeDTO localidadeDto = (LocalidadeDTO) document.getBean();
		LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);

		if (localidadeDto.getIdLocalidade() == null || localidadeDto.getIdLocalidade() == 0) {

			if (localidadeService.verificarLocalidadeAtiva(localidadeDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			localidadeDto.setDataInicio(UtilDatas.getDataAtual());
			localidadeService.create(localidadeDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {

			if (localidadeService.verificarLocalidadeAtiva(localidadeDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}

			localidadeService.update(localidadeDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_LOCALIDADE()");

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
		LocalidadeDTO localidadeDto = (LocalidadeDTO) document.getBean();
		LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);
		localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(localidadeDto);

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
		LocalidadeDTO localidadeDto = (LocalidadeDTO) document.getBean();

		LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);
		LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);

		if (localidadeDto.getIdLocalidade().intValue() > 0) {
			if (localidadeUnidadeService.verificarExistenciaDeLocalidadeEmUnidade(localidadeDto.getIdLocalidade())) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
				HTMLForm form = document.getForm("form");
				form.clear();
				return;
			}
			localidadeDto.setDataFim(UtilDatas.getDataAtual());
			localidadeService.update(localidadeDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_LOCALIDADE()");
	}

	@Override
	public Class getBeanClass() {
		return LocalidadeDTO.class;
	}

}
