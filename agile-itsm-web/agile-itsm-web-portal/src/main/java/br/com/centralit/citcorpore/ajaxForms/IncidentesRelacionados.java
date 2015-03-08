package br.com.centralit.citcorpore.ajaxForms;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.IncidentesRelacionadosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class IncidentesRelacionados extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		IncidentesRelacionadosDTO incidenteARelacionar = (IncidentesRelacionadosDTO) document.getBean();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		Collection<SolicitacaoServicoDTO> solicitacoesRelacionadas = solicitacaoServicoService.listSolicitacaoServicoRelacionada(incidenteARelacionar.getIdSolicitacaoIncRel());

		StringBuilder script = new StringBuilder();

		String html = this.montaHTMLResumoSolicitacoes(solicitacoesRelacionadas, script, request);

		document.getElementById("tabelaIncidentesRelacionados").setInnerHTML(html);

		document.executeScript(script.toString());

		document.executeScript("temporizadorRel1.init()");

		document.executeScript("$('#POPUP_menuIncidentesRelacionados').dialog('open');");
		
		/**
		 * Quantitativo de Incidentes Relacionados
		 * @author thays.araujo
		 */
		Integer quantidadeIncidentesRelacionados;
		String quantidadeIncidentesRelacionadosStr = "0";
		if(solicitacoesRelacionadas!=null){
			quantidadeIncidentesRelacionados = solicitacoesRelacionadas.size();
			quantidadeIncidentesRelacionadosStr = String.valueOf(quantidadeIncidentesRelacionados);
			document.getElementById("quantidadeIncidentesRelacionados").setValue(quantidadeIncidentesRelacionadosStr);
		}

	}

	public void listarSolicitacoesServicoEmAndamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		IncidentesRelacionadosDTO incidenteARelacionar = (IncidentesRelacionadosDTO) document.getBean();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoEmAndamento = solicitacaoServicoService.listSolicitacaoServicoEmAndamento(incidenteARelacionar.getIdSolicitacaoIncRel());

		StringBuilder script = new StringBuilder();

		String html = montaHTMLSolicitacoesServicoEmAndamento(listSolicitacaoServicoEmAndamento, script, request);

		document.getElementById("divConteudoIncRel").setInnerHTML(html);

		document.executeScript(script.toString());

		document.executeScript("temporizadorRel2.init()");
	}

	public void relacionarIncidentes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		IncidentesRelacionadosDTO incidenteRelacionadoDto = (IncidentesRelacionadosDTO) document.getBean();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		boolean aux = false;
		if (incidenteRelacionadoDto != null && incidenteRelacionadoDto.getFilhasAdicionar() != null) {

			for (int i = 0; i < incidenteRelacionadoDto.getFilhasAdicionar().length; i++) {

				System.out.println("Update: " + incidenteRelacionadoDto.getFilhasAdicionar()[i]);

				solicitacaoServicoService.updateSolicitacaoPai(incidenteRelacionadoDto.getIdSolicitacaoIncRel(), incidenteRelacionadoDto.getFilhasAdicionar()[i]);

				aux = true;
			}

		}

		document.executeScript("$('#divSolicitacoesFilhas').dialog('close')");
		document.executeScript("fecharModalListaRelacionarIncidentes()");
		document.executeScript("document.getElementById('divConteudoIncRel').value = ''");

		this.restore(document, request, response);

		if (aux) {
			document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.incidentesrelacionados"));
		}
	}

	private String montaHTMLResumoSolicitacoes(Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada, StringBuilder script, HttpServletRequest request) {
		StringBuilder html = new StringBuilder();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		html.append("<table class='table table-white table-bordered table-vertical-center table-pricing fontSize13' width='100%'");
		html.append("<tr>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resposta") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.horalimite") + "</th>");
		html.append("<th class='center'>");
		html.append("<span class='innerLR'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.temporestante")+"</span>");
		html.append("<span class='glyphicons standard clock'><i></i></span>");
		html.append("</th>");
		html.append("</tr>");

		if (listSolicitacaoServicoRelacionada != null && !listSolicitacaoServicoRelacionada.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoServicoRelacionada : listSolicitacaoServicoRelacionada) {
				html.append("<tr>");
				html.append("<hidden id='idSolicitante' value='" + solicitacaoServicoRelacionada.getIdSolicitante() + "'/>");
				html.append("<hidden id='idResponsavel' value='" + solicitacaoServicoRelacionada.getIdResponsavel() + "'/>");
				html.append("<td>" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "</td>");
				if (solicitacaoServicoRelacionada.getDataHoraSolicitacao() != null) {
					String horaLimitSTR = format.format(solicitacaoServicoRelacionada.getDataHoraSolicitacao());
					html.append("<td id='dataHoraSolicitacao'>" + horaLimitSTR + "</td>");
				}
				html.append("<td>" + solicitacaoServicoRelacionada.getPrazoHH() + ":" + solicitacaoServicoRelacionada.getPrazoMM() + "</td>");
				html.append("<td>" + solicitacaoServicoRelacionada.getDescricao() + "</td>");
				if (solicitacaoServicoRelacionada.getResposta() != null && !solicitacaoServicoRelacionada.getResposta().equals("")) {
					html.append("<td>" + solicitacaoServicoRelacionada.getResposta() + "</td>");
				}else{
					html.append("<td>" +  "-" + "</td>");
				}
				
				if(solicitacaoServicoRelacionada.getSituacao().equalsIgnoreCase("EmAndamento")){
					html.append("<td>"+ UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "</td>");
				} else if(solicitacaoServicoRelacionada.getSituacao().equalsIgnoreCase("Cancelada")){
					html.append("<td>"  + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada") + "</td>");
				} else if(solicitacaoServicoRelacionada.getSituacao().equalsIgnoreCase("Suspensa")){
					html.append("<td>"  + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa") +"</td>");
				} else{
					html.append("<td>" + solicitacaoServicoRelacionada.getSituacao() + "</td>");
				}
				if (solicitacaoServicoRelacionada.getDataHoraLimite() != null && !solicitacaoServicoRelacionada.getDataHoraLimite().equals("")) {
					String horaLimitSTR = format.format(solicitacaoServicoRelacionada.getDataHoraLimite());
					html.append("<td>" + horaLimitSTR + "</td>");
					script.append("temporizadorRel1.addOuvinte(new Solicitacao('tempoRestanteRel1" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "', " + "'barraProgressoRel1"
							+ solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "', " + "'" + solicitacaoServicoRelacionada.getDataHoraSolicitacao() + "', '"
							+ solicitacaoServicoRelacionada.getDataHoraLimite() + "'));");
					html.append("<td><label id='tempoRestanteRel1" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "'></label>");
					html.append("<div id='barraProgressoRel1" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "'></div></td>");
				} else {
					html.append("<td>&nbsp;</td>");
					html.append("<td>&nbsp;</td>");
				}

				html.append("</tr>");
			}
		}
		html.append("</table>");
		return html.toString();
	}

	private String montaHTMLSolicitacoesServicoEmAndamento(Collection<SolicitacaoServicoDTO> resumo, StringBuilder script, HttpServletRequest request) {
		StringBuilder html = new StringBuilder();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String horaSolicitacaoTR = "";
		String horaLimitSTR = "";

		html.append("<div class='row-fluid innerB'>");
		html.append("	<div class='span12'>");
		html.append("		<button type='button' class='btn btn-primary' onclick=\"document.formIncidentesRelacionados.fireEvent('relacionarIncidentes');\">" + UtilI18N.internacionaliza(request, "citcorpore.comum.relacionar")	+ "</button>");
		html.append("	</div>");
		html.append("</div>");
		
		html.append("<div class='row-fluid'>");
		html.append("	<div class='span12'>");
		
		html.append("<table class='table table-white table-bordered table-vertical-center table-pricing fontSize13' width='100%'");
		html.append("<tr>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.adicionar") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resposta") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.situacao") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.horalimite") + "</th>");
		html.append("<th class='center'>");
		html.append("<span class='innerLR'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.temporestante")+"</span>");
		html.append("<span class='glyphicons standard clock'><i></i></span>");
		html.append("</th>");
		html.append("</tr>");
		/*
		 * Quando o idServico já estiver sendo alimentado no restore de solicitacoes, incluir validação para que o serviço carregado não seja mostrado na lista de incidentes orfãos.
		 */
		if (resumo != null) {
			for (SolicitacaoServicoDTO r : resumo) {
				html.append("<tr>");
				html.append("<hidden id='idSolicitante' value='" + r.getIdSolicitante() + "'/>");
				html.append("<hidden id='idResponsavel' value='" + r.getIdResponsavel() + "'/>");
				html.append("<td class='center'><input type='checkbox' id='filhasAdicionar' name='filhasAdicionar' value='" + r.getIdSolicitacaoServico() + "' /> </td>");
				html.append("<td>" + r.getIdSolicitacaoServico() + "</td>");
				if (r.getDataHoraSolicitacao() != null && !r.getDataHoraSolicitacao().equals("")) {
					horaSolicitacaoTR = format.format(r.getDataHoraSolicitacao());
					html.append("<td id='dataHoraSolicitacao'>" + horaSolicitacaoTR + "</td>");
				}else{
					html.append("<td>&nbsp;</td>");
				}
				html.append("<td>" + r.getPrazoHH() + ":" + r.getPrazoMM() + "</td>");
				html.append("<td>" + r.getDescricao() + "</td>");
				html.append("<td>" + (r.getResposta() != null ? r.getResposta() : "-") + "</td>");
				if(r.getSituacao().equalsIgnoreCase("EmAndamento")){
					html.append("<td>"+ UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "</td>");
				} else if(r.getSituacao().equalsIgnoreCase("Cancelada")){
					html.append("<td>"  + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada") + "</td>");
				} else if(r.getSituacao().equalsIgnoreCase("Suspensa")){
					html.append("<td>"  + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa") +"</td>");
				} else{
					html.append("<td>" + r.getSituacao() + "</td>");
				}
				if (r.getDataHoraLimite() != null && !r.getDataHoraLimite().equals("")) {
					horaLimitSTR = format.format(r.getDataHoraLimite());
					html.append("<td id='dataHoraSolicitacao'>" + horaLimitSTR + "</td>");
				}else{
					html.append("<td>&nbsp;</td>");
				}

				script.append("temporizadorRel2.addOuvinte(new Solicitacao('tempoRestanteRel2" + r.getIdSolicitacaoServico() + "', " + "'barraProgressoRel2" + r.getIdSolicitacaoServico() + "', "
						+ "'" + r.getDataHoraSolicitacao() + "', '" + r.getDataHoraLimite() + "'));");

				html.append("<td><label id='tempoRestanteRel2" + r.getIdSolicitacaoServico() + "'></label>");
				html.append("<div id='barraProgressoRel2" + r.getIdSolicitacaoServico() + "'></div></td>");
				html.append("</tr>");
			}
		}
		html.append("</table>");
		html.append("	</div>");
		html.append("</div>");
		
		return html.toString();
	}

	public void abrirListaDeSubSolicitacoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		IncidentesRelacionadosDTO incidenteARelacionar = (IncidentesRelacionadosDTO) document.getBean();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		Collection<SolicitacaoServicoDTO> solicitacoesRelacionadas = solicitacaoServicoService.listSolicitacaoServicoRelacionada(incidenteARelacionar.getIdSolicitacaoIncRel());

		StringBuilder script = new StringBuilder();

		String html = this.gerarHtmlComListaSubSolicitacoes(solicitacoesRelacionadas, script, request);

		document.getElementById("tabelaIncidentesRelacionados").setInnerHTML(html);

		document.executeScript(script.toString());

		document.executeScript("temporizadorRel1.init()");

		document.executeScript("$('#formIncidentesRelacionados').dialog('open');");
	}

	private String gerarHtmlComListaSubSolicitacoes(Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada, StringBuilder script, HttpServletRequest request) {
		StringBuilder html = new StringBuilder();
		html.append("<table class='table table-white table-bordered table-vertical-center table-pricing fontSize13 width='100%'");
		html.append("<tr>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resposta") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
		html.append("<th class='center'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.horalimite") + "</th>");
		html.append("<th class='center'>");
		html.append("<span class='innerLR'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.temporestante")+"</span>");
		html.append("<span class='glyphicons standard clock'><i></i></span>");
		html.append("</th>");
		html.append("</tr>");

		if (listSolicitacaoServicoRelacionada != null && !listSolicitacaoServicoRelacionada.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoServicoRelacionada : listSolicitacaoServicoRelacionada) {
				html.append("<tr>");
				html.append("<hidden id='idSolicitante' value='" + solicitacaoServicoRelacionada.getIdSolicitante() + "'/>");
				html.append("<hidden id='idResponsavel' value='" + solicitacaoServicoRelacionada.getIdResponsavel() + "'/>");
				html.append("<td style='text-align: center;'>" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "</td>");
				html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, solicitacaoServicoRelacionada.getDataHoraSolicitacao(), WebUtil.getLanguage(request)) + "</td>");
				html.append("<td>" + solicitacaoServicoRelacionada.getPrazoHH() + ":" + solicitacaoServicoRelacionada.getPrazoMM() + "</td>");
				html.append("<td>" + solicitacaoServicoRelacionada.getDescricao() + "</td>");
				html.append("<td>" + (solicitacaoServicoRelacionada.getResposta() != null ? solicitacaoServicoRelacionada.getResposta() : "-") + "</td>");
				if(solicitacaoServicoRelacionada.getSituacao().equalsIgnoreCase("EmAndamento")){
					html.append("<td>"+ UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "</td>");
				} else if(solicitacaoServicoRelacionada.getSituacao().equalsIgnoreCase("Cancelada")){
					html.append("<td>"  + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada") + "</td>");
				} else if(solicitacaoServicoRelacionada.getSituacao().equalsIgnoreCase("Suspensa")){
					html.append("<td>"  + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa") +"</td>");
				} else{
					html.append("<td>" + solicitacaoServicoRelacionada.getSituacao() + "</td>");
				}
				if (solicitacaoServicoRelacionada.getDataHoraLimite() != null) {
					html.append("<td>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, solicitacaoServicoRelacionada.getDataHoraLimite(), WebUtil.getLanguage(request)) + "</td>");
					script.append("temporizadorRel1.addOuvinte(new Solicitacao('tempoRestanteRel1" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "', " + "'barraProgressoRel1"
							+ solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "', " + "'" + solicitacaoServicoRelacionada.getDataHoraSolicitacao() + "', '"
							+ solicitacaoServicoRelacionada.getDataHoraLimite() + "'));");
					html.append("<td><label id='tempoRestanteRel1" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "'></label>");
					html.append("<div id='barraProgressoRel1" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "'></div></td>");
				} else
					html.append("<td>&nbsp;</td>");

				html.append("</tr>");
			}
		}
		html.append("</table>");
		return html.toString();
	}

	@Override
	public Class getBeanClass() {
		return IncidentesRelacionadosDTO.class;
	}

}
