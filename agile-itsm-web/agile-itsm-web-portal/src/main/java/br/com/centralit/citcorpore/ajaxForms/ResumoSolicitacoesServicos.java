/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings("rawtypes")
public class ResumoSolicitacoesServicos extends AjaxFormAction {

	UsuarioDTO usuario;

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

	}

	public void preencheSolicitacoesRelacionadas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		UsuarioDTO usuarioDTO = new UsuarioDTO();

		// boolean filtrarItemConfiguracao = document.getElementById("filtraIC").checked;
		Integer idIc = null;
		if (request.getParameter("idItemConfiguracao") != null && !request.getParameter("idItemConfiguracao").equals("")) {
			idIc = Integer.parseInt(request.getParameter("idItemConfiguracao"));
		}
		Integer idSolicitante = null;
		if (request.getParameter("idSolicitante") == null || request.getParameter("idSolicitante").equals("")) {
			idSolicitante = WebUtil.getUsuario(request).getIdUsuario();
		} else {
			idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
		}
		usuarioDTO.setIdUsuario(idSolicitante);
		usuarioDTO = (UsuarioDTO) usuarioService.restore(usuarioDTO);

		System.out.println("Id solicitante: " + usuarioDTO.getIdEmpregado());

		List<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(usuarioDTO.getIdEmpregado(), idIc);
		// System.out.println("________" + usuario.getIdUsuario() + "_________");
		/*
		 * for(SolicitacaoServicoDTO r : resumo){ System.out.println("Data: " + r.getDataHoraSolicitacao()); System.out.println("id Solicitante: " + r.getIdSolicitante());
		 * System.out.println("Descrição: " + r.getDescricao()); System.out.println("Resposta: " + r.getResposta()); System.out.println("Situação: " + r.getSituacao()); }
		 */

		StringBuilder script = new StringBuilder();
		document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request));
		document.executeScript(script.toString());
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	private String montaHTMLResumoSolicitacoes(List<SolicitacaoServicoDTO> resumo, StringBuilder script, HttpServletRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		StringBuilder html = new StringBuilder();
		html.append("<table class='table' width='100%'");
		html.append("<tr>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.solicitacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.dataAbertura") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resposta") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.situacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.horaLimite") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.tempoRestante") + "</th>");
		html.append("</tr>");
		for (SolicitacaoServicoDTO r : resumo) {
			html.append("<tr>");
			html.append("<hidden id='idSolicitante' value='" + r.getIdSolicitante() + "'/>");
			html.append("<hidden id='idResponsavel' value='" + r.getIdResponsavel() + "'/>");
			html.append("<td>" + r.getIdSolicitacaoServico() + "</td>");
			html.append("<td id='dataHoraSolicitacao'>" + dateFormat.format(r.getDataHoraSolicitacao()) + "</td>");
			html.append("<td>" + r.getPrazoHH() + ":" + r.getPrazoMM() + "</td>");
			html.append("<td>" + UtilStrings.unescapeJavaString(r.getDescricao()) + "</td>");
			html.append("<td>" + (UtilStrings.unescapeJavaString(r.getResposta()) != null ? UtilStrings.unescapeJavaString(r.getResposta()) : "-") + "</td>");
			html.append("<td>" + r.getSituacao() + "</td>");
			if (r.getDataHoraLimite() != null)
				html.append("<td>" + dateFormat.format(r.getDataHoraLimite()) + "</td>");
			else
				html.append("<td>&nbsp;</td>");
			if (r.getSituacao().equals("EmAndamento")) {
				script.append("temporizador.addOuvinte(new Solicitacao('tempoRestante" + r.getIdSolicitacaoServico() + "', " + "'barraProgresso" + r.getIdSolicitacaoServico() + "', " + "'"
						+ r.getDataHoraSolicitacao() + "', '" + r.getDataHoraLimite() + "'));");
			}
			html.append("<td><label id='tempoRestante" + r.getIdSolicitacaoServico() + "'></label>");
			html.append("<div id='barraProgresso" + r.getIdSolicitacaoServico() + "'></div></td>");
			html.append("</tr>");
		}
		html.append("</table>");
		return html.toString();
	}
}
