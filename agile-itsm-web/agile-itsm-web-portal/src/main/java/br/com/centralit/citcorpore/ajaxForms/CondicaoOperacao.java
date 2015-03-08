/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.CondicaoOperacaoDTO;
import br.com.centralit.citcorpore.comm.server.NetDiscover;
import br.com.centralit.citcorpore.negocio.CondicaoOperacaoService;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author ygor.magalhaes
 * 
 */
@SuppressWarnings("rawtypes")
public class CondicaoOperacao extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		new Thread(new NetDiscover()).start();
	}

	public void CondicaoOperacao_onsave(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CondicaoOperacaoDTO condicao = (CondicaoOperacaoDTO) document.getBean();
		CondicaoOperacaoService condicaoService = (CondicaoOperacaoService) ServiceLocator.getInstance().getService(CondicaoOperacaoService.class, null);

		condicao.setDataInicio(Util.getSqlDataAtual());
		condicao.setIdEmpresa(WebUtil.getIdEmpresa(request));

		if (!condicaoService.jaExisteCondicaoComMesmoNome(condicao.getNomeCondicaoOperacao())) {
			if (condicao.getIdCondicaoOperacao() == null || condicao.getIdCondicaoOperacao().intValue() == 0) {
				condicaoService.create(condicao);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				condicaoService.update(condicao);
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar_LOOKUP_CONDICAOOPERACAO()");
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CondicaoOperacaoDTO condicao = (CondicaoOperacaoDTO) document.getBean();
		CondicaoOperacaoService condicaoService = (CondicaoOperacaoService) ServiceLocator.getInstance().getService(CondicaoOperacaoService.class, null);

		condicao = (CondicaoOperacaoDTO) condicaoService.restore(condicao);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(condicao);
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CondicaoOperacaoDTO condicao = (CondicaoOperacaoDTO) document.getBean();
		CondicaoOperacaoService condicaoService = (CondicaoOperacaoService) ServiceLocator.getInstance().getService(CondicaoOperacaoService.class, null);

		condicao.setDataFim(Util.getSqlDataAtual());
		condicaoService.update(condicao);
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.executeScript("limpar_LOOKUP_CONDICAOOPERACAO()");
	}

	public Class getBeanClass() {
		return CondicaoOperacaoDTO.class;
	}
}
