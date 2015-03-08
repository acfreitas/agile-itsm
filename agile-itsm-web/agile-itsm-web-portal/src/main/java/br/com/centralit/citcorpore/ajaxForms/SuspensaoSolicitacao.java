package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class SuspensaoSolicitacao extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		JustificativaSolicitacaoService justificativaService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		solicitacaoServicoDto = solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
		request.setAttribute("dataHoraSolicitacao", solicitacaoServicoDto.getDataHoraSolicitacaoStr());

		Collection colJustificativas = justificativaService.listAtivasParaSuspensao();
		document.getSelectById("idJustificativa").removeAllOptions();
		document.getSelectById("idJustificativa").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colJustificativas != null) {
			document.getSelectById("idJustificativa").addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
		form.setValues(solicitacaoServicoDto);
	}

	/**
	 * Salva a Suspensão da Solicitação.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno Data: 25/10/2013 - Horário: 16:00. Chamada parent.pesquisarItensFiltro()
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		SolicitacaoServicoDTO solicitacaoAuxDto = (SolicitacaoServicoDTO) document.getBean();
		if (solicitacaoAuxDto.getIdSolicitacaoServico() == null)
			return;

		if (solicitacaoAuxDto.getIdJustificativa() == null) {
			document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.suspensaosolicitacao.validacao.justificanaoinformada"));
			return;
		}

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		SolicitacaoServicoDTO solicitacaoServicoDto = solicitacaoServicoService.restoreAll(solicitacaoAuxDto.getIdSolicitacaoServico());
		solicitacaoServicoDto.setIdJustificativa(solicitacaoAuxDto.getIdJustificativa());
		solicitacaoServicoDto.setComplementoJustificativa(solicitacaoAuxDto.getComplementoJustificativa());
		solicitacaoServicoService.suspende(usuario, solicitacaoServicoDto);
		document.executeScript("parent.refreshTelaGerenciamento();");
	}
}
