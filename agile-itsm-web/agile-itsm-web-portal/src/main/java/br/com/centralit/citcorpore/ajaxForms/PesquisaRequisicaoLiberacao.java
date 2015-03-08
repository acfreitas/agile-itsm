/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FaseServicoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContatoRequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FaseServicoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PermissoesFluxoService;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class PesquisaRequisicaoLiberacao extends AjaxFormAction {

	UsuarioDTO usuario;
	private String localeSession = null;

	@Override
	public Class getBeanClass() {
		return PesquisaRequisicaoLiberacaoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.list();
		document.getSelectById("idContrato").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);

		document.getSelectById("idPrioridade").removeAllOptions();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		Collection col = prioridadeService.list();
		document.getSelectById("idPrioridade").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idPrioridade").addOptions(col, "idPrioridade", "nomePrioridade", null);

		document.getSelectById("idGrupoAtual").removeAllOptions();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupos = grupoSegurancaService.findGruposAtivos();
		document.getSelectById("idGrupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idGrupoAtual").addOptions(colGrupos, "idGrupo", "nome", null);

		document.getSelectById("idFaseAtual").removeAllOptions();
		FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
		Collection colFases = faseServicoService.list();
		document.getSelectById("idFaseAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idFaseAtual").addOptions(colFases, "idFase", "nomeFase", null);

		document.getSelectById("idOrigem").removeAllOptions();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		Collection colOrigem = origemAtendimentoService.list();
		document.getSelectById("idOrigem").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idOrigem").addOptions(colOrigem, "idOrigem", "descricao", null);

		document.getSelectById("idTipoDemandaServico").removeAllOptions();
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		Collection colTiposDemanda = tipoDemandaServicoService.list();
		document.getSelectById("idTipoDemandaServico").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idTipoDemandaServico").addOptions(colTiposDemanda, "idTipoDemandaServico", "nomeTipoDemandaServico", null);

	}

	/*
	 * public void preencheSolicitacoesRelacionadas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception { usuario =
	 * WebUtil.getUsuario(request); if (usuario == null) { document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada")); document.executeScript("window.location = '" +
	 * Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'"); document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide(); return; } SolicitacaoServicoDTO solicitacaoServicoDto =
	 * (SolicitacaoServicoDTO) document.getBean(); SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class,
	 * null);
	 * 
	 * if (solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() == null) { if (solicitacaoServicoDto.getDataInicio() == null) { document.alert(UtilI18N.internacionaliza(request,
	 * "citcorpore.comum.validacao.datainicio")); document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide(); return; } if (solicitacaoServicoDto.getDataFim() == null) {
	 * document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim")); document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide(); return; }
	 * 
	 * } Integer idIc = -1; if (request.getParameter("idItemConfiguracao") != null && !request.getParameter("idItemConfiguracao").equals("")) { idIc =
	 * Integer.parseInt(request.getParameter("idItemConfiguracao")); } Integer idSolicitante = -1; if (request.getParameter("idSolicitante") != null &&
	 * !request.getParameter("idSolicitante").equals("")) { idSolicitante = Integer.parseInt(request.getParameter("idSolicitante")); }
	 * 
	 * Integer idResponsavel = -1; if (request.getParameter("idResponsavel") != null && !request.getParameter("idResponsavel").equals("")) { idResponsavel =
	 * Integer.parseInt(request.getParameter("idResponsavel")); } Integer idUnidade = -1; if (request.getParameter("idUnidade") != null && !request.getParameter("idUnidade").equals("")) { idUnidade =
	 * Integer.parseInt(request.getParameter("idUnidade")); } Integer idServico1 = -1; if (request.getParameter("idServico") != null && !request.getParameter("idServico").equals("")) { idServico1 =
	 * Integer.parseInt(request.getParameter("idServico")); }
	 * 
	 * if (solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() == null) { solicitacaoServicoDto.setIdSolicitacaoServicoPesquisa(-1); } if (solicitacaoServicoDto.getDataInicio() == null) {
	 * solicitacaoServicoDto.setDataInicio (UtilDatas.strToSQLDate("01/01/1970")); } if (solicitacaoServicoDto.getDataFim() == null) { solicitacaoServicoDto.setDataFim(new
	 * java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime())); }
	 * 
	 * if (solicitacaoServicoDto.getDataInicioFechamento() == null) { solicitacaoServicoDto .setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970")); } if
	 * (solicitacaoServicoDto.getDataFimFechamento() == null) { solicitacaoServicoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365,
	 * Calendar.DAY_OF_YEAR).getTime())); }
	 * 
	 * Collection colCriterios = new ArrayList(); colCriterios.add(new Condition("idSolicitacaoServico", "", solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa())); colCriterios.add(new
	 * Condition("idsolicitante", "", idSolicitante)); colCriterios.add(new Condition("iditemconfiguracao", "", idIc)); colCriterios.add(new Condition("situacao", "",
	 * solicitacaoServicoDto.getSituacao())); colCriterios.add(new Condition("dataInicial", "", solicitacaoServicoDto.getDataInicio())); colCriterios.add(new Condition("dataFinal", "",
	 * UtilDatas.strToTimestamp(UtilDatas .dateToSTR(solicitacaoServicoDto.getDataFim()) + " 23:59:59"))); colCriterios.add(new Condition("idPrioridade", "", solicitacaoServicoDto.getIdPrioridade()));
	 * colCriterios.add(new Condition("idOrigem", "", solicitacaoServicoDto.getIdOrigem())); colCriterios.add(new Condition("idUnidade", "", idUnidade)); colCriterios.add(new Condition("idFaseAtual",
	 * "", solicitacaoServicoDto.getIdFaseAtual())); colCriterios.add(new Condition("idGrupoAtual", "", solicitacaoServicoDto.getIdGrupoAtual())); colCriterios.add(new Condition("idServico", "", new
	 * Integer(-1))); colCriterios.add(new Condition("classificacao", "", new String("*"))); colCriterios.add(new Condition("idTipoDemandaServico", "",
	 * solicitacaoServicoDto.getIdTipoDemandaServico())); colCriterios.add(new Condition("idContrato", "", solicitacaoServicoDto.getIdContrato())); colCriterios.add(new Condition("ordenacao", "",
	 * solicitacaoServicoDto.getOrdenacao())); colCriterios.add(new Condition("idResponsavel", "", idResponsavel)); colCriterios.add(new Condition("idServico1", "", idServico1)); colCriterios.add(new
	 * Condition("palavraChave", "", solicitacaoServicoDto.getPalavraChave())); colCriterios.add(new Condition("dataInicioFechamento", "", solicitacaoServicoDto.getDataInicioFechamento()));
	 * colCriterios.add(new Condition("dataFinalFechamento", "", UtilDatas.strToTimestamp(UtilDatas.dateToSTR (solicitacaoServicoDto.getDataFimFechamento()) + " 23:59:59")));
	 * 
	 * ArrayList<SolicitacaoServicoDTO> resumo = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService.listSolicitacaoServicoByCriterios(colCriterios);
	 * 
	 * StringBuilder script = new StringBuilder(); if (resumo != null) { document.getElementById ("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request, response)); } else {
	 * document.getElementById("tblResumo").setInnerHTML (UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.criterioinformado")); } document.executeScript(script.toString());
	 * document.executeScript("temporizador.init()"); document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	 * 
	 * }
	 */

	public void preencheSolicitacoesRelacionadas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		PesquisaRequisicaoLiberacaoDTO pesquisaRequisicaoLiberacaoDto = (PesquisaRequisicaoLiberacaoDTO) document.getBean();
		
		RequisicaoLiberacaoService liberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
		List<RequisicaoLiberacaoDTO> listaRequisicaoLiberacaoPorCriterios = (List<RequisicaoLiberacaoDTO>) liberacaoService.listaRequisicaoLiberacaoPorCriterios(pesquisaRequisicaoLiberacaoDto);
		

		if (pesquisaRequisicaoLiberacaoDto.getDataInicio() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataFim() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataInicioFechamento() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970"));
		} else {
			document.executeScript("$('#situacao').attr('disabled', 'true')");
			pesquisaRequisicaoLiberacaoDto.setSituacao("Concluida");
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataFimFechamento() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		} else {
			document.executeScript("$('#situacao').attr('disabled', 'true')");
			pesquisaRequisicaoLiberacaoDto.setSituacao("Concluida");
		}
		
//		GerenciamentoLiberacaoDTO gerenciamentoBean = (GerenciamentoLiberacaoDTO) document.getBean();
//		ExecucaoLiberacaoService execucaoLiberacaoService = (ExecucaoLiberacaoService) ServiceLocator.getInstance().getService(ExecucaoLiberacaoService.class, null);
//		List<TarefaFluxoDTO> colTarefas = execucaoLiberacaoService.recuperaTarefasComFiltro(usuario.getLogin(),pesquisaRequisicaoLiberacaoDto);

		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
		
//		ArrayList<RequisicaoLiberacaoDTO> listaSolicitacaoServicoPorCriterios = (ArrayList<RequisicaoLiberacaoDTO>) requisicaoLiberacaoService.listaRequisicaoLiberacaoPorCriterios(pesquisaRequisicaoLiberacaoDto);

		StringBuilder script = new StringBuilder();
		if (listaRequisicaoLiberacaoPorCriterios != null) {
			document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(listaRequisicaoLiberacaoPorCriterios, script, request, response));
		} else {
			document.getElementById("tblResumo").setInnerHTML(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.criterioinformado"));
		}
		document.executeScript(script.toString());
		document.executeScript("temporizador.init()");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	private String montaHTMLResumoSolicitacoes(List<RequisicaoLiberacaoDTO> resumo, StringBuilder script, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		usuario = WebUtil.getUsuario(request);
		StringBuilder html = new StringBuilder();
		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class,  WebUtil.getUsuarioSistema(request));
		PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		
		html.append("<table class='table' id='tbRetorno' width='100%' style = 'font-size: 10px' >");
		html.append("<tr>");
		html.append("<th>&nbsp;</th>");
		html.append("<th>&nbsp;</th>");
		html.append("<th>&nbsp;</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.numero")  + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.solicitante") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.dataCriacao") + "</th>"); 
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.prioridade") + "</th>");
//		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.prazo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.situacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.datainicial") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.datalimite") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.grupoexecutor") + "</th>");
//		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicaoliberacao.responsavel") + "</th>");
//		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoralimite") + "</th>");
//		html.append("<th>" + UtilI18N.internacionaliza(request, "unidade.grupo") + "</th>");
//		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoraencerramento") + "</th>");
//		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisarequisicao.temporestante"));
//		html.append("<img width='20' height='20'");
//		html.append("alt='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativaotemporizador") + "' id='imgAtivaTimer' style='opacity:0.5' ");
//		html.append("title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativadestemporizador") + "'");
//		html.append("src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/cronometro.png'/>");
//		html.append("</th>");
		html.append("</tr>");
		HashMap<String, PermissoesFluxoDTO> mapPermissoes = new HashMap();
		for (RequisicaoLiberacaoDTO r : resumo) {
			
//			RequisicaoLiberacaoDTO r = (RequisicaoLiberacaoDTO)a.getRequisicaoLiberacaoDto();
            RequisicaoLiberacaoDTO reqDto = new RequisicaoLiberacaoDTO();
            reqDto.setIdRequisicaoLiberacao(r.getIdRequisicaoLiberacao());
//            reqDto.setIdRequisicaoLiberacao(r.getIdRequisicaoLiberacao());
            
            FluxoDTO fluxoDto = requisicaoLiberacaoService.recuperaFluxo(reqDto);
            if (fluxoDto == null)
                continue;
            
            html.append("<tr>");
			html.append("<hidden id='idSolicitante' value='" + r.getIdSolicitante() + "'/>");
			html.append("<hidden id='idResponsavel' value='" + r.getIdResponsavel() + "'/>");

			PermissoesFluxoDTO permFluxoDto = mapPermissoes.get("" + fluxoDto.getIdFluxo());
			if (permFluxoDto == null) {
				permFluxoDto = permissoesFluxoService.findByUsuarioAndFluxo(usuario, fluxoDto);
				if (permFluxoDto != null)
					mapPermissoes.put("" + fluxoDto.getIdFluxo(), permFluxoDto);
			}
			html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/search.png' border='0' title='"
					+ UtilI18N.internacionaliza(request, "pesquisasolicitacao.consultasolicitacaoincidente") + "' onclick='consultarOcorrencias(\"" + r.getIdRequisicaoLiberacao() + "\")' style='cursor:pointer'/></td>");
			
			if (permFluxoDto != null && permFluxoDto.getReabrir() != null && permFluxoDto.getReabrir().equalsIgnoreCase("S")) {
				if (r.encerrada()) {
					html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
							+ "/imagens/reabrir.jpg' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.reabrirsol") + "' onclick='reabrir(\"" + r.getIdRequisicaoLiberacao()
							+ "\")' style='cursor:pointer'/></td>");
				} else {
					html.append("<td>&nbsp;</td>");
				}
			} else {
				html.append("<td>&nbsp;</td>");
			}
			ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
			Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, r.getIdRequisicaoLiberacao());

			if (colAnexos != null && !colAnexos.isEmpty()) {
				html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
						+ "/imagens/Paperclip4-black-32.png' width='16' height='16' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.visualizaranexos") + "' id='btAnexos' onclick='anexos(\""
						+ r.getIdRequisicaoLiberacao() + "\")' style='cursor:pointer'/></td>");
			} else {
				html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
						+ "/imagens/file.png' width='16' height='16' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.visualizaranexos") + "' id='btAnexos' onclick='anexos(\""
						+ r.getIdRequisicaoLiberacao() + "\")' style='cursor:pointer'/></td>");
			}
			r.setNomeSolicitante(((EmpregadoDTO) empregadoService.restoreByIdEmpregado(r.getIdSolicitante())).getNome());
			html.append("<td>" + r.getIdRequisicaoLiberacao() + "</td>");
			html.append("<td>" + r.getNomeSolicitante() + "</td>");
			html.append("<td>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraCaptura(), WebUtil.getLanguage(request)) + "</td>");
			html.append("<td>" + r.getPrioridade() + "</td>");
//			if (r.getSeqReabertura() == null || r.getSeqReabertura().intValue() == 0) {
//				html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.formatTimestamp(r.getDataHoraSolicitacao()) + "</td>");
//			} else {
//				html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.formatTimestamp(r.getDataHoraSolicitacao()) + "<br><br>" + UtilI18N.internacionaliza(request, "solicitacaoServico.seqreabertura")
//						+ ": <span style='color:red'><b>" + r.getSeqReabertura() + "</b></span></td>");
//			}
//			html.append("<td>" + r.getPrazoHH() + ":" + r.getPrazoMM() + "</td>");
//			html.append("<td>" + UtilStrings.nullToVazio(r.getResposta()) + "</td>");
//			html.append("<td>" + r.getNomeServico().replace(".", ". ") + "</td>");
			if(r.getStatus().equalsIgnoreCase("emexecucao"))
				html.append("<td>" + "Em Execução" + "</td>");
			else if(r.getStatus().equalsIgnoreCase("naoresolvida"))
				html.append("<td>" + "Não Resolvida" + "</td>");
			else
				html.append("<td>" + r.getStatus() + "</td>");
			
			if (r.getDataHoraInicioAgendada() != null)
			    html.append("<td>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraInicioAgendada(), WebUtil.getLanguage(request)) + "</td>");
			else
			    html.append("<td>&nbsp;</td>");
			if(r.getDataHoraTerminoAgendada() != null)
				html.append("<td>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraTerminoAgendada(), WebUtil.getLanguage(request))+ "</td>");
			else
				html.append("<td>&nbsp;</td>");
			
			GrupoDTO grupoDTO = new GrupoDTO();
			grupoDTO.setIdGrupo(r.getIdGrupoAtual());
			
			String grupoAtual = ((GrupoDTO) grupoService.restore(grupoDTO)).getNome(); 
//			((EmpregadoDTO) empregadoService.restoreByIdEmpregado(r.getIdSolicitante()).getNome());
			html.append("<td>" + grupoAtual + "</td>");
//			String d = "";
//			if (r.getDataHoraConclusao() != null) {
//				d = UtilDatas.formatTimestamp(r.getDataHoraConclusao());
//			}
			
//			html.append("<td id='dataHoraFimSolicitacao'>" + d + "</td>");
//			UsuarioDTO usuarioDTO = new UsuarioDTO();
//			usuarioDTO.setIdUsuario(r.getIdResponsavel());
			
			
//			UsuarioDTO usuarioDTO = usuarioService.restoreByID(r.getIdResponsavel());
//			UsuarioDTO usuarioDTO = null;
//			if(usuarioDTO != null)
//				html.append("<td>" + usuarioDTO.getLogin()+ "</td>");
//			else
//				html.append("<td>&nbsp;</td>");
//			html.append("<td>"+a.getElementoFluxoDto().getDocumentacao()+"</td>");
//			if (r.getStatus().equals("EmAndamento")) {
//			script.append("temporizador.addOuvinte(new Solicitacao('tempoRestante" + r.getIdRequisicaoLiberacao() + "', " + "'barraProgresso" + r.getIdRequisicaoLiberacao() + "', " + "'" + r.getDataHoraSolicitacao()
//					+ "', '" + r.getDataHoraTerminoAgendada() + "'));");
//			}
//			html.append("<td><label id='tempoRestante" + r.getIdRequisicaoLiberacao() + "'></label>");
//			html.append("<div id='barraProgresso" + r.getIdRequisicaoLiberacao() + "'></div></td>");
			html.append("</tr>");
		}
		html.append("</table>");
		return html.toString();
	}

	public void reabre(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		PesquisaRequisicaoLiberacaoDTO pesquisaRequisicaoLiberacaoDto = (PesquisaRequisicaoLiberacaoDTO) document.getBean();
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = new RequisicaoLiberacaoDTO();
		RequisicaoLiberacaoService liberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
		if (pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacao() == null) {
			document.alert(UtilI18N.internacionaliza(request, "pesquisasolicitacao.informeReabrir"));
			return;
		} else {
			requisicaoLiberacaoDto.setIdRequisicaoLiberacao(pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacao());
			requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO) liberacaoService.restore(requisicaoLiberacaoDto);
			liberacaoService.reabre(usuario, requisicaoLiberacaoDto);
		}

		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.reaberta"));
		document.executeScript("filtrar()");
	}

	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		PesquisaRequisicaoLiberacaoDTO pesquisaRequisicaoLiberacaoDto = (PesquisaRequisicaoLiberacaoDTO) document.getBean();
		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
		TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		GrupoDTO grupoDto = new GrupoDTO();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		OrigemAtendimentoDTO origemDto = new OrigemAtendimentoDTO();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		FaseServicoDTO faseDto = new FaseServicoDTO();
		FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
		PrioridadeDTO prioridadeDto = new PrioridadeDTO();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		UsuarioDTO usuarioDto = new UsuarioDTO();
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		UnidadeDTO unidadeDto = new UnidadeDTO();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		ContatoRequisicaoLiberacaoService contatoRequisicaoLiberacaoService = (ContatoRequisicaoLiberacaoService) ServiceLocator.getInstance().getService(ContatoRequisicaoLiberacaoService.class, null);
		
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataInicio() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataFim() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataInicioFechamento() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970"));
		} else {
			document.executeScript("$('#situacao').attr('disabled', 'true')");
			pesquisaRequisicaoLiberacaoDto.setSituacao("Concluida");
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataFimFechamento() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		} else {
			document.executeScript("$('#situacao').attr('disabled', 'true')");
			pesquisaRequisicaoLiberacaoDto.setSituacao("Concluida");
		}

		ArrayList<RequisicaoLiberacaoDTO> listaRequisicaoLiberacaoPorCriterios = (ArrayList<RequisicaoLiberacaoDTO>) requisicaoLiberacaoService.listaRequisicaoLiberacaoPorCriterios(pesquisaRequisicaoLiberacaoDto);

		if (listaRequisicaoLiberacaoPorCriterios == null || listaRequisicaoLiberacaoPorCriterios.size() == 0) {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			return;
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaRequisicaoLiberacao.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.relatorioRequisicoesLiberacoes"));
		parametros.put("CIDADE", "Brasília,");
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicial", pesquisaRequisicaoLiberacaoDto.getDataInicio());
		parametros.put("dataFinal", pesquisaRequisicaoLiberacaoDto.getDataFim());
		parametros.put("exibirCampoDescricao", pesquisaRequisicaoLiberacaoDto.getExibirCampoDescricao());
		parametros.put("quantidade", listaRequisicaoLiberacaoPorCriterios.size());
		if (pesquisaRequisicaoLiberacaoDto.getNomeItemConfiguracao() != null && !pesquisaRequisicaoLiberacaoDto.getNomeItemConfiguracao().equalsIgnoreCase("")) {
			parametros.put("nomeItemConfiguracao", pesquisaRequisicaoLiberacaoDto.getNomeItemConfiguracao());
		} else {
			parametros.put("nomeItemConfiguracao", null);
		}
		if (!pesquisaRequisicaoLiberacaoDto.getNomeSolicitante().equalsIgnoreCase("")) {
			parametros.put("nomeSolicitante", pesquisaRequisicaoLiberacaoDto.getNomeSolicitante());
		} else {

			parametros.put("nomeSolicitante", null);
		}

//		parametros.put("dataFim", pesquisaRequisicaoLiberacaoDto.getDataFim());
		if (pesquisaRequisicaoLiberacaoDto.getIdTipoDemandaServico() != null) {
			tipoDemandaServicoDto.setIdTipoDemandaServico(pesquisaRequisicaoLiberacaoDto.getIdTipoDemandaServico());
			tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
			pesquisaRequisicaoLiberacaoDto.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
			parametros.put("tipo", pesquisaRequisicaoLiberacaoDto.getNomeTipoDemandaServico());
		} else {
			parametros.put("tipo", pesquisaRequisicaoLiberacaoDto.getNomeTipoDemandaServico());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacaoPesquisa() != null) {
			parametros.put("numero", pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacaoPesquisa());
		} else {
			parametros.put("numero", pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacaoPesquisa());
		}
		if (pesquisaRequisicaoLiberacaoDto.getSituacao() != null && !pesquisaRequisicaoLiberacaoDto.getSituacao().equals("")) {
			if (StringUtils.contains(StringUtils.upperCase(pesquisaRequisicaoLiberacaoDto.getSituacao()), StringUtils.upperCase("EmAndamento"))) {
				pesquisaRequisicaoLiberacaoDto.setSituacao("Em Andamento");
			}
			parametros.put("situacao", pesquisaRequisicaoLiberacaoDto.getSituacao());
		} else {
			parametros.put("situacao", null);
		}

		if (pesquisaRequisicaoLiberacaoDto.getIdGrupoAtual() != null) {
			grupoDto.setIdGrupo(pesquisaRequisicaoLiberacaoDto.getIdGrupoAtual());
			grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
			pesquisaRequisicaoLiberacaoDto.setGrupoAtual(grupoDto.getSigla());
			parametros.put("grupoSolucionador", pesquisaRequisicaoLiberacaoDto.getGrupoAtual());
		} else {
			parametros.put("grupoSolucionador", pesquisaRequisicaoLiberacaoDto.getGrupoAtual());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdOrigem() != null) {
			origemDto.setIdOrigem(pesquisaRequisicaoLiberacaoDto.getIdOrigem());
			origemDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemDto);
			pesquisaRequisicaoLiberacaoDto.setOrigem(origemDto.getDescricao());
			parametros.put("origem", pesquisaRequisicaoLiberacaoDto.getOrigem());
		} else {
			parametros.put("origem", pesquisaRequisicaoLiberacaoDto.getOrigem());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdFaseAtual() != null) {
			faseDto.setIdFase(pesquisaRequisicaoLiberacaoDto.getIdFaseAtual());
			faseDto = (FaseServicoDTO) faseServicoService.restore(faseDto);
			pesquisaRequisicaoLiberacaoDto.setFaseAtual(faseDto.getNomeFase());
			parametros.put("fase", pesquisaRequisicaoLiberacaoDto.getFaseAtual());
		} else {
			parametros.put("fase", pesquisaRequisicaoLiberacaoDto.getFaseAtual());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdPrioridade() != null) {
			prioridadeDto.setIdPrioridade(pesquisaRequisicaoLiberacaoDto.getIdPrioridade());
			prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
			pesquisaRequisicaoLiberacaoDto.setPrioridade(prioridadeDto.getNomePrioridade());
			parametros.put("prioridade", pesquisaRequisicaoLiberacaoDto.getPrioridade());
		} else {
			parametros.put("prioridade", pesquisaRequisicaoLiberacaoDto.getPrioridade());
		}

		if (pesquisaRequisicaoLiberacaoDto.getIdContrato() != null) {
			contratoDto.setIdContrato(pesquisaRequisicaoLiberacaoDto.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}

		if (pesquisaRequisicaoLiberacaoDto.getNomeContato() != null) {

			parametros.put("nomeContato", pesquisaRequisicaoLiberacaoDto.getNomeContato());
		} else {
			parametros.put("nomeContato", null);
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdUnidade() != null) {
			unidadeDto.setIdUnidade(pesquisaRequisicaoLiberacaoDto.getIdUnidade());
			unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);
			parametros.put("unidade", unidadeDto.getNome());
		} else {
			parametros.put("unidade", null);
		}
		
		
		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaRequisicaoLiberacaoPorCriterios);

			// Instancia o arquivo de swap, informando:
			// Diretorio,
			// Tamanho de cada bloco (4kb)
			// Numero mínimo de blocos que o swap será aumentado sempre que
			// estiver cheio
			// JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096,
			// 25);

			// Instancia o virtualizador
			// JRAbstractLRUVirtualizer virtualizer = new
			// JRSwapFileVirtualizer(25, arquivoSwap, true);

			JRAbstractLRUVirtualizer virtualizer = new JRGzipVirtualizer(500);

			// Seta o parametro REPORT_VIRTUALIZER com a instância da
			// virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

			// Preenche o relatório e exibe numa GUI
			Timestamp ts1 = UtilDatas.getDataHoraAtual();
			JasperPrint jp = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			Timestamp ts2 = UtilDatas.getDataHoraAtual();
			double tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(ts2, ts1);
			System.out.println("########## Tempo fillReport: " + tempo);

			JasperExportManager.exportReportToPdfFile(jp, diretorioReceita + "/RelatorioRequisicaoLiberacao" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioRequisicaoLiberacao" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
			// JasperViewer.viewReport(jp,false);

		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		/*
		 * JRDataSource dataSource = new JRBeanCollectionDataSource(listaSolicitacaoServicoPorCriterios);
		 * 
		 * JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource); JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioSolicitacaoServico" +
		 * strCompl + "_" + usuario.getIdUsuario() + ".pdf");
		 * 
		 * document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS +
		 * "/RelatorioSolicitacaoServico" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		 */
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void imprimirRelatorioXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		PesquisaRequisicaoLiberacaoDTO pesquisaRequisicaoLiberacaoDto = (PesquisaRequisicaoLiberacaoDTO) document.getBean();
		RequisicaoLiberacaoService liberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
		TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		GrupoDTO grupoDto = new GrupoDTO();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		OrigemAtendimentoDTO origemDto = new OrigemAtendimentoDTO();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		FaseServicoDTO faseDto = new FaseServicoDTO();
		FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
		PrioridadeDTO prioridadeDto = new PrioridadeDTO();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		UsuarioDTO usuarioDto = new UsuarioDTO();
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		UnidadeDTO unidadeDto = new UnidadeDTO();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataInicio() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataFim() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataInicioFechamento() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970"));
		} else {
			pesquisaRequisicaoLiberacaoDto.setSituacao("Fechada");
		}

		if (pesquisaRequisicaoLiberacaoDto.getDataFimFechamento() == null) {
			pesquisaRequisicaoLiberacaoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		} else {
			pesquisaRequisicaoLiberacaoDto.setSituacao("Fechada");
		}

		ArrayList<RequisicaoLiberacaoDTO> listaRequisicaoLiberacaoPorCriterios = (ArrayList<RequisicaoLiberacaoDTO>) liberacaoService.listaRequisicaoLiberacaoPorCriterios(pesquisaRequisicaoLiberacaoDto);

		if (listaRequisicaoLiberacaoPorCriterios == null || listaRequisicaoLiberacaoPorCriterios.size() == 0) {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			return;
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaRequisicaoLiberacaoXls.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", "Relatório Incidentes / Solicitações de Serviços");
		parametros.put("CIDADE", "Brasília,");
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", pesquisaRequisicaoLiberacaoDto.getDataInicio());
		parametros.put("dataFim", pesquisaRequisicaoLiberacaoDto.getDataFim());
		parametros.put("exibirCampoDescricao", pesquisaRequisicaoLiberacaoDto.getExibirCampoDescricao());
		parametros.put("quantidade", listaRequisicaoLiberacaoPorCriterios.size());
		parametros.put("nomeItemConfiguracao", null);
		if (!pesquisaRequisicaoLiberacaoDto.getNomeSolicitante().equalsIgnoreCase("")) {
			parametros.put("nomeSolicitante", pesquisaRequisicaoLiberacaoDto.getNomeSolicitante());
		} else {

			parametros.put("nomeSolicitante", null);
		}

		parametros.put("dataFim", pesquisaRequisicaoLiberacaoDto.getDataFim());
		if (pesquisaRequisicaoLiberacaoDto.getIdTipoDemandaServico() != null) {
			tipoDemandaServicoDto.setIdTipoDemandaServico(pesquisaRequisicaoLiberacaoDto.getIdTipoDemandaServico());
			tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
			pesquisaRequisicaoLiberacaoDto.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
			parametros.put("tipo", pesquisaRequisicaoLiberacaoDto.getNomeTipoDemandaServico());
		} else {
			parametros.put("tipo", pesquisaRequisicaoLiberacaoDto.getNomeTipoDemandaServico());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacaoPesquisa() != null) {
			parametros.put("numero", pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacaoPesquisa());
		} else {
			parametros.put("numero", pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacaoPesquisa());
		}
		if (pesquisaRequisicaoLiberacaoDto.getSituacao() != null && !pesquisaRequisicaoLiberacaoDto.getSituacao().equals("")) {
			if (StringUtils.contains(StringUtils.upperCase(pesquisaRequisicaoLiberacaoDto.getSituacao()), StringUtils.upperCase("EmAndamento"))) {
				pesquisaRequisicaoLiberacaoDto.setSituacao("Em Andamento");
			}
			parametros.put("situacao", pesquisaRequisicaoLiberacaoDto.getSituacao());
		} else {
			parametros.put("situacao", null);
		}

		if (pesquisaRequisicaoLiberacaoDto.getIdGrupoAtual() != null) {
			grupoDto.setIdGrupo(pesquisaRequisicaoLiberacaoDto.getIdGrupoAtual());
			grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
			pesquisaRequisicaoLiberacaoDto.setGrupoAtual(grupoDto.getSigla());
			parametros.put("grupoSolucionador", pesquisaRequisicaoLiberacaoDto.getGrupoAtual());
		} else {
			parametros.put("grupoSolucionador", pesquisaRequisicaoLiberacaoDto.getGrupoAtual());
		}
		if (pesquisaRequisicaoLiberacaoDto.getNomeContato() != null) {
			parametros.put("nomeContato", pesquisaRequisicaoLiberacaoDto.getNomeContato());
		} else {
			parametros.put("nomeContato", null);
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdOrigem() != null) {
			origemDto.setIdOrigem(pesquisaRequisicaoLiberacaoDto.getIdOrigem());
			origemDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemDto);
			pesquisaRequisicaoLiberacaoDto.setOrigem(origemDto.getDescricao());
			parametros.put("origem", pesquisaRequisicaoLiberacaoDto.getOrigem());
		} else {
			parametros.put("origem", pesquisaRequisicaoLiberacaoDto.getOrigem());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdFaseAtual() != null) {
			faseDto.setIdFase(pesquisaRequisicaoLiberacaoDto.getIdFaseAtual());
			faseDto = (FaseServicoDTO) faseServicoService.restore(faseDto);
			pesquisaRequisicaoLiberacaoDto.setFaseAtual(faseDto.getNomeFase());
			parametros.put("fase", pesquisaRequisicaoLiberacaoDto.getFaseAtual());
		} else {
			parametros.put("fase", pesquisaRequisicaoLiberacaoDto.getFaseAtual());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdPrioridade() != null) {
			prioridadeDto.setIdPrioridade(pesquisaRequisicaoLiberacaoDto.getIdPrioridade());
			prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
			pesquisaRequisicaoLiberacaoDto.setPrioridade(prioridadeDto.getNomePrioridade());
			parametros.put("prioridade", pesquisaRequisicaoLiberacaoDto.getPrioridade());
		} else {
			parametros.put("prioridade", pesquisaRequisicaoLiberacaoDto.getPrioridade());
		}

		if (pesquisaRequisicaoLiberacaoDto.getIdContrato() != null) {
			contratoDto.setIdContrato(pesquisaRequisicaoLiberacaoDto.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}

		if (pesquisaRequisicaoLiberacaoDto.getIdResponsavel() != null) {
			usuarioDto.setIdUsuario(pesquisaRequisicaoLiberacaoDto.getIdResponsavel());
			usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
			parametros.put("responsavel", usuarioDto.getNomeUsuario());
		} else {
			parametros.put("responsavel", null);
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdUnidade() != null) {
			unidadeDto.setIdUnidade(pesquisaRequisicaoLiberacaoDto.getIdUnidade());
			unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);
			parametros.put("unidade", unidadeDto.getNome());
		} else {
			parametros.put("unidade", null);
		}

		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaRequisicaoLiberacaoPorCriterios);

			JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaRequisicaoLiberacaoXls.jrxml");

			JasperReport relatorio = JasperCompileManager.compileReport(desenho);

			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioPesquisaSolicitacaoServicoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

			exporter.exportReport();

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioPesquisaSolicitacaoServicoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void restoreUpload(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		request.getSession(true).setAttribute("colUploadsGED", null);
		/* Realida o refresh do iframe */
		document.executeScript("document.getElementById('fraUpload_uploadAnexos').contentWindow.location.reload(true)");

		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		PesquisaRequisicaoLiberacaoDTO pesquisaRequisicaoLiberacaoDto = (PesquisaRequisicaoLiberacaoDTO) document.getBean();

		if (pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacao() == null) {
			return;
		}
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO, pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacao());
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
		document.executeScript("$('#POPUP_menuAnexos').dialog('open');");
	}
	
}