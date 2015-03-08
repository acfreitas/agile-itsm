package br.com.centralit.citcorpore.ajaxForms;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.negocio.TipoMovimFinanceiraViagemService;
import br.com.centralit.citcorpore.util.Enumerados.ClassificacaoMovFinViagem;
import br.com.centralit.citcorpore.util.Enumerados.TipoMovFinViagem;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author ronnie.lopes
 * 
 */
@SuppressWarnings("rawtypes")
public class TipoMovimFinanceiraViagem extends AjaxFormAction {
	
	public Class getBeanClass() {
		return TipoMovimFinanceiraViagemDTO.class;
	}

	/**
	 * Inicializa os dados ao carregar a tela.
	 * @author ronnie.lopes
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.preencherComboClassificacao(document, request, response);	
		this.preencherComboTipo(document, request, response);
	}

	/**
	 * Inclui registro.
	 * @author ronnie.lopes
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraViagemDto = (TipoMovimFinanceiraViagemDTO) document.getBean();
		TipoMovimFinanceiraViagemService tipoMovimFinanceiraViagemService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, WebUtil.getUsuarioSistema(request));
		if (tipoMovimFinanceiraViagemDto.getIdtipoMovimFinanceiraViagem() == null || tipoMovimFinanceiraViagemDto.getIdtipoMovimFinanceiraViagem().intValue() == 0) {
			tipoMovimFinanceiraViagemService.create(tipoMovimFinanceiraViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			tipoMovimFinanceiraViagemService.update(tipoMovimFinanceiraViagemDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * Restaura os dados ao clicar em um registro.
	 * @author ronnie.lopes
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoMovimFinanceiraViagemDTO tipoMovimFinanceiraViagemDto = (TipoMovimFinanceiraViagemDTO) document.getBean();
		TipoMovimFinanceiraViagemService tipoMovimFinanceiraViagemService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);

		tipoMovimFinanceiraViagemDto = (TipoMovimFinanceiraViagemDTO) tipoMovimFinanceiraViagemService.restore(tipoMovimFinanceiraViagemDto);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(tipoMovimFinanceiraViagemDto);

	}
	
	/**
	 * Deleta o dado quando for solicitado a sua exclusão.
	 * @author ronnie.lopes
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception   
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TipoMovimFinanceiraViagemDTO tipoMoimFinanceiraViagemDto = (TipoMovimFinanceiraViagemDTO) document.getBean();
		TipoMovimFinanceiraViagemService tipoMovimFinanceiraViagemService = (TipoMovimFinanceiraViagemService) ServiceLocator.getInstance().getService(TipoMovimFinanceiraViagemService.class, null);

		if (tipoMoimFinanceiraViagemDto.getIdtipoMovimFinanceiraViagem().intValue() > 0) {
			tipoMovimFinanceiraViagemService.delete((tipoMoimFinanceiraViagemDto));
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

	}
	
	/**
	 * Preenche o combo da classificação com o enumerado ClassificacaoMovFinViagem
	 * @author ronnie.lopes
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void preencherComboClassificacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
        HTMLSelect classificacoes = (HTMLSelect) document.getSelectById("classificacao");
        classificacoes.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        for (ClassificacaoMovFinViagem object : ClassificacaoMovFinViagem.values()) {
        	classificacoes.addOption(object.getDescricao(), object.getDescricao());
        } 
	}
	
	/**
	 * Preenche o combo do tipo com o enumerado TipoMovFinViagem
	 * @author ronnie.lopes
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void preencherComboTipo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
        HTMLSelect tipos = (HTMLSelect) document.getSelectById("tipo");
        tipos.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        for (TipoMovFinViagem object : TipoMovFinViagem.values()) {
        	tipos.addOption(object.name(), UtilI18N.internacionaliza(request, object.getDescricao()));
        } 
	}
	
}
