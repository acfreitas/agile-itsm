package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.TipoServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RelatorioControleSla extends AjaxFormAction {
	UsuarioDTO usuario;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.list();
		document.getSelectById("idContrato").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);

		document.getSelectById("idGrupoAtual").removeAllOptions();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupo = grupoService.listarGruposAtivos();
		document.getSelectById("idGrupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idGrupoAtual").addOptions(colGrupo, "idGrupo", "nome", null);

		document.getSelectById("idPrioridade").removeAllOptions();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		Collection colPrioridade = prioridadeService.list();
		document.getSelectById("idPrioridade").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idPrioridade").addOptions(colPrioridade, "idPrioridade", "nomePrioridade", null);

		document.getSelectById("idTipoServico").removeAllOptions();
		TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);
		Collection colTipos = tipoServicoService.list();
		document.getSelectById("idTipoServico").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idTipoServico").addOptions(colTipos, "idTipoServico", "nomeTipoServico", null);

		document.getSelectById("prazo").removeAllOptions();
		document.getSelectById("prazo").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("prazo").addOption("S", UtilI18N.internacionaliza(request, "relatorioSla.semAtraso"));
		document.getSelectById("prazo").addOption("N", UtilI18N.internacionaliza(request, "relatorioSla.comAtraso"));

		document.getSelectById("sla").removeAllOptions();
		document.getSelectById("sla").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		ArrayList<SolicitacaoServicoDTO> colSLA = (ArrayList<SolicitacaoServicoDTO>) solicitacaoServicoService.listarSLA();
		if (colSLA != null) {
			for (SolicitacaoServicoDTO solicitacaoServico : colSLA)
				if (solicitacaoServico.getPrazoHH() < 10 && solicitacaoServico.getPrazoMM() < 10) {
					document.getSelectById("sla").addOption(solicitacaoServico.getPrazoHH() + ":" + solicitacaoServico.getPrazoMM(),
							"0" + solicitacaoServico.getPrazoHH() + "h " + "0" + solicitacaoServico.getPrazoMM() + "m");
				} else if (solicitacaoServico.getPrazoHH() < 10 && solicitacaoServico.getPrazoMM() > 10) {
					document.getSelectById("sla").addOption(solicitacaoServico.getPrazoHH() + ":" + solicitacaoServico.getPrazoMM(),
							"0" + solicitacaoServico.getPrazoHH() + "h " + solicitacaoServico.getPrazoMM() + "m");
				} else if (solicitacaoServico.getPrazoHH() > 9 && solicitacaoServico.getPrazoMM() < 10) {
					document.getSelectById("sla").addOption(solicitacaoServico.getPrazoHH() + ":" + solicitacaoServico.getPrazoMM(),
							solicitacaoServico.getPrazoHH() + "h " + "0" + solicitacaoServico.getPrazoMM() + "m");
				} else {
					document.getSelectById("sla").addOption(solicitacaoServico.getPrazoHH() + ":" + solicitacaoServico.getPrazoMM(),
							solicitacaoServico.getPrazoHH() + "h " + solicitacaoServico.getPrazoMM() + "m");
				}
		}

	}

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	/**
	 * FireEvent responsável por gerar o Relatório XML de Controle SLA.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void imprimirRelatorioControleSla(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();

		HttpSession session = ((HttpServletRequest) request).getSession();

		TipoServicoDTO tipoServicoDTO = new TipoServicoDTO();
		TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);

		PrioridadeDTO prioridadeDto = new PrioridadeDTO();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);

		GrupoDTO grupoDto = new GrupoDTO();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoControleSla = new ArrayList();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		listSolicitacaoServicoControleSla = solicitacaoServicoService.relatorioControleSla(solicitacaoServicoDTO);

		listSolicitacaoServicoControleSla = this.tratarListSolicitacaoServicoRelatorioControleSla(request, solicitacaoServicoDTO, listSolicitacaoServicoControleSla);

		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControleSLA.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioSla.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("numero", solicitacaoServicoDTO.getIdSolicitacaoServico());
		parametros.put("solicitante", solicitacaoServicoDTO.getNomeSolicitante());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());

		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", "Todos");
		}

		if (solicitacaoServicoDTO.getIdTipoDemandaServico() != null && !solicitacaoServicoDTO.getIdTipoDemandaServico().equals(-1)) {
			tipoServicoDTO.setIdTipoServico(solicitacaoServicoDTO.getIdTipoServico());
			tipoServicoDTO = (TipoServicoDTO) tipoServicoService.restore(tipoServicoDTO);
			solicitacaoServicoDTO.setNomeTipoDemandaServico(tipoServicoDTO.getNomeTipoServico());
			parametros.put("tipo", solicitacaoServicoDTO.getNomeTipoDemandaServico());
		} else {
			parametros.put("tipo", "Todos");
		}

		if (solicitacaoServicoDTO.getIdPrioridade() != null && !solicitacaoServicoDTO.getIdPrioridade().equals(-1)) {
			prioridadeDto.setIdPrioridade(solicitacaoServicoDTO.getIdPrioridade());
			prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
			solicitacaoServicoDTO.setPrioridade(prioridadeDto.getNomePrioridade());
			parametros.put("prioridade", solicitacaoServicoDTO.getPrioridade());
		} else {
			parametros.put("prioridade", "Todos");
		}

		if (solicitacaoServicoDTO.getIdGrupoAtual() != null && !solicitacaoServicoDTO.getIdGrupoAtual().equals(-1)) {
			grupoDto.setIdGrupo(solicitacaoServicoDTO.getIdGrupoAtual());
			grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
			solicitacaoServicoDTO.setGrupoAtual(grupoDto.getSigla());
			parametros.put("grupoSolucionador", solicitacaoServicoDTO.getGrupoAtual());
		} else {
			parametros.put("grupoSolucionador", "Todos");
		}

		if (listSolicitacaoServicoControleSla.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		JRDataSource dataSource = new JRBeanCollectionDataSource(listSolicitacaoServicoControleSla);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioControleSLA" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioControleSLA" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	/**
	 * FireEvent responsável por gerar o Relatório XML de Controle SLA.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void imprimirRelatorioControleSlaXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();

		HttpSession session = ((HttpServletRequest) request).getSession();

		TipoServicoDTO tipoServicoDTO = new TipoServicoDTO();
		TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);

		PrioridadeDTO prioridadeDto = new PrioridadeDTO();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);

		GrupoDTO grupoDto = new GrupoDTO();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection<SolicitacaoServicoDTO> listSolicitacaoServicoControleSla = new ArrayList();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		listSolicitacaoServicoControleSla = solicitacaoServicoService.relatorioControleSla(solicitacaoServicoDTO);

		listSolicitacaoServicoControleSla = this.tratarListSolicitacaoServicoRelatorioControleSla(request, solicitacaoServicoDTO, listSolicitacaoServicoControleSla);

		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioSla.titulo"));
		parametros.put("numero", solicitacaoServicoDTO.getIdSolicitacaoServico());
		parametros.put("solicitante", solicitacaoServicoDTO.getNomeSolicitante());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		parametros.put("sla", solicitacaoServicoDTO.getPrazoHH());

		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", "Todos");
		}

		if (solicitacaoServicoDTO.getIdTipoDemandaServico() != null && !solicitacaoServicoDTO.getIdTipoDemandaServico().equals(-1)) {
			tipoServicoDTO.setIdTipoServico(solicitacaoServicoDTO.getIdTipoServico());
			tipoServicoDTO = (TipoServicoDTO) tipoServicoService.restore(tipoServicoDTO);
			solicitacaoServicoDTO.setNomeTipoDemandaServico(tipoServicoDTO.getNomeTipoServico());
			parametros.put("tipo", solicitacaoServicoDTO.getNomeTipoDemandaServico());
		} else {
			parametros.put("tipo", "Todos");
		}

		if (solicitacaoServicoDTO.getIdPrioridade() != null && !solicitacaoServicoDTO.getIdPrioridade().equals(-1)) {
			prioridadeDto.setIdPrioridade(solicitacaoServicoDTO.getIdPrioridade());
			prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
			solicitacaoServicoDTO.setPrioridade(prioridadeDto.getNomePrioridade());
			parametros.put("prioridade", solicitacaoServicoDTO.getPrioridade());
		} else {
			parametros.put("prioridade", "Todos");
		}

		if (solicitacaoServicoDTO.getIdGrupoAtual() != null && !solicitacaoServicoDTO.getIdGrupoAtual().equals(-1)) {
			grupoDto.setIdGrupo(solicitacaoServicoDTO.getIdGrupoAtual());
			grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
			solicitacaoServicoDTO.setGrupoAtual(grupoDto.getSigla());
			parametros.put("grupoSolucionador", solicitacaoServicoDTO.getGrupoAtual());
		} else {
			parametros.put("grupoSolucionador", "Todos");
		}

		if (listSolicitacaoServicoControleSla.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		JRDataSource dataSource = new JRBeanCollectionDataSource(listSolicitacaoServicoControleSla);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControleSLAXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioControleSLAXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioControleSLAXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	/**
	 * Trata nos ítens da lista a Situação, Prazo e Atraso do SLA.
	 * 
	 * @param request
	 *            - HttpServletRequest
	 * @param solicitacaoServicoDTO
	 *            - SolicitacaoServicoDTO
	 * @param listSolicitacaoServicoControleSla
	 *            - Collection<SolicitacaoServicoDTO>
	 * @return Collection<SolicitacaoServicoDTO>
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	private Collection<SolicitacaoServicoDTO> tratarListSolicitacaoServicoRelatorioControleSla(HttpServletRequest request, SolicitacaoServicoDTO solicitacaoServicoDTO,
			Collection<SolicitacaoServicoDTO> listSolicitacaoServicoControleSla) throws Exception {

		if (listSolicitacaoServicoControleSla != null) {

			SolicitacaoServicoServiceEjb solicitacaoServicoServiceEjb = new SolicitacaoServicoServiceEjb();

			Collection<SolicitacaoServicoDTO> listSolicitacaoServicoControleSlaAux = new ArrayList();

			for (SolicitacaoServicoDTO solicitacaoServicoControleSlaDto : listSolicitacaoServicoControleSla) {
				
				/* Desenvolvedor: Rodrigo Pecci - Data: 01/11/2013 - Horário: 18h30min - ID Citsmart: 120770
				 * Motivo/Comentário: O método anterior estava calculando o atraso da SLA mesmo se ela fosse Suspensa ou Cancelada. Foi alterado o método.
				 */
				solicitacaoServicoControleSlaDto = solicitacaoServicoServiceEjb.verificaSituacaoSLA(solicitacaoServicoControleSlaDto, null);			

				if (StringUtils.contains(StringUtils.upperCase(solicitacaoServicoControleSlaDto.getSituacao()), StringUtils.upperCase("EmAndamento"))) {
					solicitacaoServicoControleSlaDto.setSituacao(UtilI18N.internacionaliza(request, "citcorpore.comum.emandamento"));
				}

				//solicitacaoServicoControleSlaDto.setDataHoraLimiteStr("--");
				solicitacaoServicoControleSlaDto.setDataHoraLimiteStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, solicitacaoServicoControleSlaDto.getDataHoraLimite(), WebUtil.getLanguage(request)));
				solicitacaoServicoControleSlaDto.setDataHoraSolicitacaoStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, solicitacaoServicoControleSlaDto.getDataHoraSolicitacao(), WebUtil.getLanguage(request)));
				solicitacaoServicoControleSlaDto.setDataHoraFimStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, solicitacaoServicoControleSlaDto.getDataHoraFim(), WebUtil.getLanguage(request)));

				//solicitacaoServicoControleSlaDto.setDataHoraFimStr(UtilDatas.formatTimestamp(solicitacaoServicoControleSlaDto.getDataHoraFim()));

				if (solicitacaoServicoControleSlaDto.getAtrasoSLA() > 0) {
					solicitacaoServicoControleSlaDto.setPrazo(UtilI18N.internacionaliza(request, "citcorpore.comum.nao"));

					if (solicitacaoServicoDTO.getPrazo() != null && StringUtils.isNotBlank(solicitacaoServicoDTO.getPrazo()) && solicitacaoServicoDTO.getPrazo().trim().equalsIgnoreCase("S")) {
						continue;
					}
				} else {
					solicitacaoServicoControleSlaDto.setPrazo(UtilI18N.internacionaliza(request, "citcorpore.comum.sim"));

					if (solicitacaoServicoDTO.getPrazo() != null && StringUtils.isNotBlank(solicitacaoServicoDTO.getPrazo()) && solicitacaoServicoDTO.getPrazo().trim().equalsIgnoreCase("N")) {
						continue;
					}
				}

				listSolicitacaoServicoControleSlaAux.add(solicitacaoServicoControleSlaDto);
			}

			listSolicitacaoServicoControleSla = listSolicitacaoServicoControleSlaAux;
		}

		return listSolicitacaoServicoControleSla;
	}

}
