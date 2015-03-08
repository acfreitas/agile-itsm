package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.negocio.CategoriaOcorrenciaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author thiago.monteiro
 * 
 */
@SuppressWarnings("rawtypes")
public class CategoriaOcorrencia extends AjaxFormAction {
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.focusInFirstActivateField(null);
	}

	@Override
	public Class getBeanClass() {
		return CategoriaOcorrenciaDTO.class;
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Cria uma instância de CategoriaOcorrenciaDTO com os dados
		// provenientes do formulário
		// na página JSP.
		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) document.getBean();
		CategoriaOcorrenciaService categoriaOcorrenciaService = (CategoriaOcorrenciaService) ServiceLocator.getInstance().
				getService(CategoriaOcorrenciaService.class, null);		
		
		// Verifica se o DTO e o serviço existem
		if (categoriaOcorrenciaDTO != null && categoriaOcorrenciaService != null) {
			categoriaOcorrenciaDTO.setDataInicio(UtilDatas.getDataAtual() );			
			// Inserir		
			if (categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() == null) {
				categoriaOcorrenciaService.create(categoriaOcorrenciaDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG05") );
			} else {
				// Atualiar			
				categoriaOcorrenciaService.update(categoriaOcorrenciaDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG06") );
			}
			HTMLForm form = document.getForm("formCategoriaOcorrencia");
			form.clear();
		}
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) document.getBean();
		CategoriaOcorrenciaService categoriaOcorrenciaService = (CategoriaOcorrenciaService) ServiceLocator.getInstance().
				getService(CategoriaOcorrenciaService.class, WebUtil.getUsuarioSistema(request) );
		
		if (categoriaOcorrenciaDTO != null & categoriaOcorrenciaService != null) {
			if (categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != null && (categoriaOcorrenciaDTO.getIdCategoriaOcorrencia().intValue() > 0) ) {
				categoriaOcorrenciaService.deletarCategoriaOcorrencia(categoriaOcorrenciaDTO, document);
				HTMLForm form = document.getForm("formCategoriaOcorrencia");
				form.clear();
			}
		}
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Recupera o objeto que contém os dados preenchidos no formulário
		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) document.getBean();
		CategoriaOcorrenciaService categoriaOcorrenciaService = (CategoriaOcorrenciaService) ServiceLocator.getInstance().
				getService(CategoriaOcorrenciaService.class, null);
		
		if (categoriaOcorrenciaDTO != null && categoriaOcorrenciaService != null) {
			// Recupera dados a partir do banco de dados
			categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaService.restore(categoriaOcorrenciaDTO);		
			HTMLForm form = document.getForm("formCategoriaOcorrencia");
			form.clear();
			form.setValues(categoriaOcorrenciaDTO);
		}
	}
}