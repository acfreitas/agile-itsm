package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.SituacaoLiberacaoMudancaDTO;
import br.com.centralit.citcorpore.negocio.SituacaoLiberacaoMudancaService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
@SuppressWarnings("rawtypes")


/**
 * 
 * @author geber.costa
 *
 */
public class SituacaoLiberacaoMudanca extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);
		
	}

	@Override
	public Class getBeanClass() {
		return SituacaoLiberacaoMudancaDTO.class;
	}

	/**
	 * Metodo de salvar
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SituacaoLiberacaoMudancaDTO situacaoLiberacaoMudancaDto = (SituacaoLiberacaoMudancaDTO) document.getBean();

		SituacaoLiberacaoMudancaService situacaoLiberacaoMudancaService = (SituacaoLiberacaoMudancaService) ServiceLocator.getInstance().getService(SituacaoLiberacaoMudancaService.class, null);
		
		if (situacaoLiberacaoMudancaDto.getIdSituacaoLiberacaoMudanca() == null || situacaoLiberacaoMudancaDto.getIdSituacaoLiberacaoMudanca() == 0) {
			if (situacaoLiberacaoMudancaService.consultaExistenciaSituacao(situacaoLiberacaoMudancaDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			//situacaoLiberacaoMudancaDto.setSituacao(situacao)
			situacaoLiberacaoMudancaService.create(situacaoLiberacaoMudancaDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if (situacaoLiberacaoMudancaService.consultaExistenciaSituacao(situacaoLiberacaoMudancaDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			situacaoLiberacaoMudancaService.update(situacaoLiberacaoMudancaDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		/*document.executeScript("limpar_LOOKUP_MUDANCA_CADASTRAR_SITUACAO()");*/
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
		
		SituacaoLiberacaoMudancaDTO situacaoDto = (SituacaoLiberacaoMudancaDTO) document.getBean();
		SituacaoLiberacaoMudancaService situacaoService = (SituacaoLiberacaoMudancaService) ServiceLocator.getInstance().getService(SituacaoLiberacaoMudancaService.class, null);
		

		if (situacaoDto.getIdSituacaoLiberacaoMudanca().intValue() > 0) {
			situacaoService.deletarSituacao(situacaoDto, document,request);
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		/*document.executeScript("limpar_LOOKUP_MUDANCA_CADASTRAR_SITUACAO()");*/
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
		SituacaoLiberacaoMudancaDTO situacaoLiberacaoMudancaDto = (SituacaoLiberacaoMudancaDTO) document.getBean();
		SituacaoLiberacaoMudancaService situacaoService = (SituacaoLiberacaoMudancaService) ServiceLocator.getInstance().getService(SituacaoLiberacaoMudancaService.class, null);
		situacaoLiberacaoMudancaDto = (SituacaoLiberacaoMudancaDTO) situacaoService.restore(situacaoLiberacaoMudancaDto);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(situacaoLiberacaoMudancaDto);
	}

}
