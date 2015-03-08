/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.SituacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.SituacaoServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class SituacaoServico extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	public void SituacaoServico_onsave(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SituacaoServicoDTO situacao = (SituacaoServicoDTO) document.getBean();
		SituacaoServicoService situacaoService = (SituacaoServicoService) ServiceLocator.getInstance().getService(SituacaoServicoService.class, null);

		if (situacao.getIdSituacaoServico() == null || situacao.getIdSituacaoServico().intValue() == 0) {
			if (!situacaoService.jaExisteSituacaoComMesmoNome(situacao.getNomeSituacaoServico())) {
				situacao.setIdEmpresa(WebUtil.getIdEmpresa(request));
				situacaoService.create(situacao);
				document.alert(UtilI18N.internacionaliza(request, "MSG05") );
			} else {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado") );
			}
		} else {
			situacaoService.update(situacao);
			document.alert(UtilI18N.internacionaliza(request, "MSG06") );
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.executeScript("limpar_LOOKUP_SITUACAOSERVICO()");
	}

	public void SituacaoServico_onrestore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SituacaoServicoDTO situacao = (SituacaoServicoDTO) document.getBean();
		SituacaoServicoService situacaoService = (SituacaoServicoService) ServiceLocator.getInstance().getService(SituacaoServicoService.class, null);
		situacao = (SituacaoServicoDTO) situacaoService.restore(situacao);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(situacao);
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SituacaoServicoDTO situacao = (SituacaoServicoDTO) document.getBean();
		SituacaoServicoService situacaoService = (SituacaoServicoService) ServiceLocator.getInstance().getService(SituacaoServicoService.class, null);
		situacao.setDataFim(Util.getSqlDataAtual());

		boolean existeServicoAssociado = situacaoService.existeServicoAssociado(situacao.getIdSituacaoServico());
		if(existeServicoAssociado){
			document.alert(UtilI18N.internacionaliza(request,"centroResultado.naoPodeSerExcluido.possuiRelacionamentos"));
			HTMLForm form = document.getForm("form");
			form.clear();
			return;
		}
		
		situacaoService.update(situacao);
		document.alert(UtilI18N.internacionaliza(request, "MSG07") );
		CITCorporeUtil.limparFormulario(document);
		
		document.executeScript("limpar_LOOKUP_SITUACAOSERVICO()");
	}

	public Class getBeanClass() {
		return SituacaoServicoDTO.class;
	}
}
