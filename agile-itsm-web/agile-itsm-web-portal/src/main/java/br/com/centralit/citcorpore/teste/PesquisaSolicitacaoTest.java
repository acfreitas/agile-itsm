package br.com.centralit.citcorpore.teste;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.PermissoesFluxoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTest;

public class PesquisaSolicitacaoTest {
	public String testPesquisaSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		try {
			SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
			SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

			solicitacaoServicoDto.setDataFim(UtilDatas.getDataAtual());
			solicitacaoServicoDto.setDataInicio(new java.sql.Date(212, 02, 11));

			Integer idIc = -1;
			if (request.getParameter("idItemConfiguracao") != null && !request.getParameter("idItemConfiguracao").equals("")) {
				idIc = Integer.parseInt(request.getParameter("idItemConfiguracao"));
			}
			Integer idSolicitante = -1;
			if (request.getParameter("idSolicitante") != null && !request.getParameter("idSolicitante").equals("")) {
				idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
			}

			Integer idResponsavel = -1;
			if (request.getParameter("idResponsavel") != null && !request.getParameter("idResponsavel").equals("")) {
				idResponsavel = Integer.parseInt(request.getParameter("idResponsavel"));
			}
			Integer idUnidade = -1;
			if (request.getParameter("idUnidade") != null && !request.getParameter("idUnidade").equals("")) {
				idUnidade = Integer.parseInt(request.getParameter("idUnidade"));
			}
			Integer idServico1 = -1;
			if (request.getParameter("idServico") != null && !request.getParameter("idServico").equals("")) {
				idServico1 = Integer.parseInt(request.getParameter("idServico"));
			}

			if (solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() == null) {
				solicitacaoServicoDto.setIdSolicitacaoServicoPesquisa(-1);
			}
			if (solicitacaoServicoDto.getDataInicio() == null) {
				solicitacaoServicoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
			}
			if (solicitacaoServicoDto.getDataFim() == null) {
				solicitacaoServicoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
			}
			if (solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() == null) {
				solicitacaoServicoDto.setIdSolicitacaoServicoPesquisa(-1);
			}
			if (solicitacaoServicoDto.getDataInicio() == null) {
				solicitacaoServicoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
			}
			if (solicitacaoServicoDto.getDataFim() == null) {
				solicitacaoServicoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
			}

			Collection colCriterios = new ArrayList();
			colCriterios.add(new Condition("idSolicitacaoServico", "", solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa()));
			colCriterios.add(new Condition("idsolicitante", "", idSolicitante));
			colCriterios.add(new Condition("iditemconfiguracao", "", idIc));
			colCriterios.add(new Condition("situacao", "", solicitacaoServicoDto.getSituacao()));
			colCriterios.add(new Condition("dataInicial", "", solicitacaoServicoDto.getDataInicio()));
			colCriterios.add(new Condition("dataFinal", "", UtilDatas.strToTimestamp(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, solicitacaoServicoDto.getDataFim(), WebUtil.getLanguage(request)) + " 23:59:59")));
			colCriterios.add(new Condition("idPrioridade", "", solicitacaoServicoDto.getIdPrioridade()));
			colCriterios.add(new Condition("idOrigem", "", solicitacaoServicoDto.getIdOrigem()));
			colCriterios.add(new Condition("idUnidade", "", idUnidade));
			colCriterios.add(new Condition("idFaseAtual", "", solicitacaoServicoDto.getIdFaseAtual()));
			colCriterios.add(new Condition("idGrupoAtual", "", solicitacaoServicoDto.getIdGrupoAtual()));
			colCriterios.add(new Condition("idServico", "", new Integer(-1)));
			colCriterios.add(new Condition("classificacao", "", new String("*")));
			colCriterios.add(new Condition("idTipoDemandaServico", "", solicitacaoServicoDto.getIdTipoDemandaServico()));
			colCriterios.add(new Condition("idContrato", "", solicitacaoServicoDto.getIdContrato()));
			colCriterios.add(new Condition("ordenacao", "", solicitacaoServicoDto.getOrdenacao()));
			colCriterios.add(new Condition("idResponsavel", "", idResponsavel));
			colCriterios.add(new Condition("idServico1", "", idServico1));
			colCriterios.add(new Condition("palavraChave", "", solicitacaoServicoDto.getPalavraChave()));

			ArrayList<SolicitacaoServicoDTO> resumo = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService.listSolicitacaoServicoByCriterios(colCriterios);

			StringBuilder script = new StringBuilder();
			if (resumo != null) {
				document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request, response));
			} else {
				document.getElementById("tblResumo").setInnerHTML(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.criterioinformado"));
			}
			document.executeScript(script.toString());
			document.executeScript("temporizador.init()");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

			return new UtilTest().testNotNull(resumo);
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	private String montaHTMLResumoSolicitacoes(ArrayList<SolicitacaoServicoDTO> resumo, StringBuilder script, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		StringBuilder html = new StringBuilder();
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);
		html.append("<table class='table' id='tbRetorno' width='100%' >");
		html.append("<tr>");
		html.append("<th>&nbsp;</th>");
		html.append("<th>&nbsp;</th>");
		html.append("<th>&nbsp;</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacao") + "/<br>" + UtilI18N.internacionaliza(request, "solicitacaoServico.incidente") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitante") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.responsavel") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.tipo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoraabertura") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "gerenciaservico.sla") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.descricao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solucaoResposta") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "servico.servico") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoralimite") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "unidade.grupo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoraencerramento") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.temporestante"));
		html.append("<img width='20' height='20'");
		html.append("alt='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativaotemporizador") + "' id='imgAtivaTimer' style='opacity:0.5' ");
		html.append("title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativadestemporizador") + "'");
		html.append("src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/cronometro.png'/>");
		html.append("</th>");
		html.append("</tr>");
		HashMap<String, PermissoesFluxoDTO> mapPermissoes = new HashMap();
		for (SolicitacaoServicoDTO r : resumo) {
			html.append("<tr>");
			html.append("<hidden id='idSolicitante' value='" + r.getIdSolicitante() + "'/>");
			html.append("<hidden id='idResponsavel' value='" + r.getIdResponsavel() + "'/>");

			SolicitacaoServicoDTO solDto = new SolicitacaoServicoDTO();
			solDto.setIdSolicitacaoServico(r.getIdSolicitacaoServico());
			FluxoDTO fluxoDto = solicitacaoService.recuperaFluxo(solDto);

			PermissoesFluxoDTO permFluxoDto = mapPermissoes.get(""+fluxoDto.getIdFluxo());
			if (permFluxoDto == null) {
			    permFluxoDto = permissoesFluxoService.findByUsuarioAndFluxo(usuario, fluxoDto);
			    if (permFluxoDto != null)
			        mapPermissoes.put(""+fluxoDto.getIdFluxo(), permFluxoDto);
			}

			html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/search.png' border='0' title='"
					+ UtilI18N.internacionaliza(request, "pesquisasolicitacao.consultasolicitacaoincidente") + "' onclick='consultarOcorrencias(\"" + r.getIdSolicitacaoServico() + "\")' style='cursor:pointer'/></td>");
			if (permFluxoDto != null && permFluxoDto.getReabrir() != null && permFluxoDto.getReabrir().equalsIgnoreCase("S")) {
				if (r.encerrada()) {
					html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/reabrir.jpg' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.reabrirsol")
							+ "' onclick='reabrir(\"" + r.getIdSolicitacaoServico() + "\")' style='cursor:pointer'/></td>");
				} else {
					html.append("<td>&nbsp;</td>");
				}
			} else {
				html.append("<td>&nbsp;</td>");
			}
			ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
			Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, r.getIdSolicitacaoServico());

			if(colAnexos!=null && !colAnexos.isEmpty()) {
				html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/Paperclip4-black-32.png' width='16' height='16' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.visualizaranexos") + "' id='btAnexos' onclick='anexos(\"" + r.getIdSolicitacaoServico()
					+ "\")' style='cursor:pointer'/></td>");
			}else {
				html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/file.png' width='16' height='16' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.visualizaranexos") + "' id='btAnexos' onclick='anexos(\"" + r.getIdSolicitacaoServico()
						+ "\")' style='cursor:pointer'/></td>");
			}
			html.append("<td>" + r.getIdSolicitacaoServico() + "</td>");
			html.append("<td>" + r.getNomeSolicitante() + "</td>");
			html.append("<td>" + r.getResponsavel() + "</td>");
			html.append("<td>" + r.getNomeTipoDemandaServico() + "</td>");
			if (r.getSeqReabertura() == null || r.getSeqReabertura().intValue() == 0) {
				html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraSolicitacao(), WebUtil.getLanguage(request)) + "</td>");
			} else {
				html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraSolicitacao(), WebUtil.getLanguage(request)) + "<br><br>" + UtilI18N.internacionaliza(request, "solicitacaoServico.seqreabertura") + ": <span style='color:red'><b>" + r.getSeqReabertura() + "</b></span></td>");
			}
			html.append("<td>" + r.getPrazoHH() + ":" + r.getPrazoMM() + "</td>");
			html.append("<td>" + r.getDescricao() + "</td>");
			html.append("<td>" + UtilStrings.nullToVazio(r.getResposta()) + "</td>");
			html.append("<td>" + r.getNomeServico() + "</td>");
			html.append("<td>" + r.getSituacao() + "</td>");
			html.append("<td>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraLimite(), WebUtil.getLanguage(request)) + "</td>");
			html.append("<td>" + UtilStrings.nullToVazio(r.getSiglaGrupo()) + "</td>");
			String d = "";
			if (r.getDataHoraFim() != null) {
				d = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraFim(), WebUtil.getLanguage(request));
			}
			html.append("<td id='dataHoraFimSolicitacao'>" + d + "</td>");
			if (r.getSituacao().equals("EmAndamento")) {
				script.append("temporizador.addOuvinte(new Solicitacao('tempoRestante" + r.getIdSolicitacaoServico() + "', " + "'barraProgresso" + r.getIdSolicitacaoServico() + "', " + "'" + r.getDataHoraSolicitacao() + "', '" + r.getDataHoraLimite() + "'));");
			}
			html.append("<td><label id='tempoRestante" + r.getIdSolicitacaoServico() + "'></label>");
			html.append("<div id='barraProgresso" + r.getIdSolicitacaoServico() + "'></div></td>");
			html.append("</tr>");
		}
		html.append("</table>");
		return html.toString();
	}

}
