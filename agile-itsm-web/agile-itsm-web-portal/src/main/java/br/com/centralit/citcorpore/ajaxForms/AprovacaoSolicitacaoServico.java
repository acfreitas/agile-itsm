package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AprovacaoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.AprovacaoSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({ "rawtypes" })
public class AprovacaoSolicitacaoServico extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		JustificativaSolicitacaoService justificativaService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class,
				WebUtil.getUsuarioSistema(request));

		AprovacaoSolicitacaoServicoService aprovacaoSolicitacaoServicoService = (AprovacaoSolicitacaoServicoService) ServiceLocator.getInstance().getService(AprovacaoSolicitacaoServicoService.class,
				null);

		HTMLSelect idJustificativa = (HTMLSelect) document.getSelectById("idJustificativa");
		idJustificativa.removeAllOptions();
		idJustificativa.addOption("", "---");
		Collection colJustificativas = justificativaService.listAtivasParaAprovacao();
		if (colJustificativas != null && !colJustificativas.isEmpty())
			idJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);

		AprovacaoSolicitacaoServicoDTO aprovacaoSolicitacaoServicoDto = aprovacaoSolicitacaoServicoService.findNaoAprovadaBySolicitacaoServico(solicitacaoServicoDto);

		if (aprovacaoSolicitacaoServicoDto != null) {

			HTMLForm form = document.getForm("form");
			form.clear();

			form.setValues(aprovacaoSolicitacaoServicoDto);

			document.executeScript("configuraJustificativa('N')");
		}

	}

}
