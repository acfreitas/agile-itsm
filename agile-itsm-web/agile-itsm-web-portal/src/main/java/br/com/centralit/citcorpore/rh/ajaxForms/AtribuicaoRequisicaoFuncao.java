package br.com.centralit.citcorpore.rh.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.rh.bean.AtribuicaoRequisicaoFuncaoDTO;
import br.com.centralit.citcorpore.rh.negocio.AtribuicaoRequisicaoFuncaoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class AtribuicaoRequisicaoFuncao extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.focusInFirstActivateField(null);
	}

	@Override
	public Class getBeanClass() {
		return AtribuicaoRequisicaoFuncaoDTO.class;
	}

	/**
	 * Metodo de salvar
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exceptio
	 * @author thiago.borges
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtribuicaoRequisicaoFuncaoDTO atribuicaoRequisicaoFuncaoDTO = (AtribuicaoRequisicaoFuncaoDTO) document.getBean();

		AtribuicaoRequisicaoFuncaoService atribuicaoRequisicaoFuncaoService = (AtribuicaoRequisicaoFuncaoService) ServiceLocator.getInstance().getService(AtribuicaoRequisicaoFuncaoService.class, null);
		if (atribuicaoRequisicaoFuncaoDTO.getIdAtribuicao() == null || atribuicaoRequisicaoFuncaoDTO.getIdAtribuicao() == 0) {
			if (atribuicaoRequisicaoFuncaoService.consultarAtribuicoesAtivas(atribuicaoRequisicaoFuncaoDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			atribuicaoRequisicaoFuncaoDTO.setDataInicio(UtilDatas.getDataAtual());
			atribuicaoRequisicaoFuncaoService.create(atribuicaoRequisicaoFuncaoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} 
		else {
			if (atribuicaoRequisicaoFuncaoService.consultarAtribuicoesAtivas(atribuicaoRequisicaoFuncaoDTO)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			atribuicaoRequisicaoFuncaoService.update(atribuicaoRequisicaoFuncaoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_ATRIBUICAOREQUISICAOFUNCAO()");
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
		AtribuicaoRequisicaoFuncaoDTO atribuicaoRequisicaoFuncaoDTO = (AtribuicaoRequisicaoFuncaoDTO) document.getBean();
		AtribuicaoRequisicaoFuncaoService atribuicaoRequisicaoFuncaoService = (AtribuicaoRequisicaoFuncaoService) ServiceLocator.getInstance().getService(AtribuicaoRequisicaoFuncaoService.class, null);
		atribuicaoRequisicaoFuncaoDTO = (AtribuicaoRequisicaoFuncaoDTO) atribuicaoRequisicaoFuncaoService.restore(atribuicaoRequisicaoFuncaoDTO);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(atribuicaoRequisicaoFuncaoDTO);
	}
	
	/**
	 * Exclui Tipo Item Configuração e suas características.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Thiago.borges
	 */
	public void update(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtribuicaoRequisicaoFuncaoDTO atribuicaoRequisicaoFuncaoDTO = (AtribuicaoRequisicaoFuncaoDTO) document.getBean();
		AtribuicaoRequisicaoFuncaoService atribuicaoRequisicaoFuncaoService = (AtribuicaoRequisicaoFuncaoService) ServiceLocator.getInstance().getService(AtribuicaoRequisicaoFuncaoService.class, null);

		
		if (atribuicaoRequisicaoFuncaoDTO.getIdAtribuicao() != null && atribuicaoRequisicaoFuncaoDTO.getIdAtribuicao() != 0) {
			atribuicaoRequisicaoFuncaoDTO.setDataFim(UtilDatas.getDataAtual());
			atribuicaoRequisicaoFuncaoService.update(atribuicaoRequisicaoFuncaoDTO);
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}

}
