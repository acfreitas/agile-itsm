package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.RelatorioCausaSolucaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CategoriaSolucaoService;
import br.com.centralit.citcorpore.negocio.CausaIncidenteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class RelatorioCausaSolucao extends SolicitacaoServicoMultiContratos{
	UsuarioDTO usuario;
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

		document.getSelectById("idTipoDemandaServico").removeAllOptions();
		TipoDemandaServicoService tipoServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		Collection colTipos = tipoServicoService.listSolicitacoes();
		document.getSelectById("idTipoDemandaServico").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idTipoDemandaServico").addOptions(colTipos, "idTipoDemandaServico", "nomeTipoDemandaServico", null);

		HTMLSelect comboSituacao = document.getSelectById("situacao");
		comboSituacao.removeAllOptions();
		comboSituacao.addOption("",  "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		for (SituacaoSolicitacaoServico situacao : SituacaoSolicitacaoServico.values()) {
			if (situacao.name().equalsIgnoreCase("cancelada") || situacao.name().equalsIgnoreCase("fechada")) {
				comboSituacao.addOption(situacao.name(), UtilI18N.internacionaliza(request, "solicitacaoServico.situacao." + situacao.name()));
			}
		}

		HTMLSelect comboGrupo1 = document.getSelectById("primeiraListaGrupo");
		comboGrupo1.removeAllOptions();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupo = grupoService.listaGruposAtivos();
		comboGrupo1.addOptions(colGrupo, "idGrupo", "Nome", null);

		HTMLSelect comboCausa1 = document.getSelectById("primeiraListaCausa");
		comboCausa1.removeAllOptions();
		CausaIncidenteService causaService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);
		Collection colCausas = causaService.listaCausasAtivas();
		comboCausa1.addOptions(colCausas, "idCausaIncidente", "DescricaoCausa", null);

		HTMLSelect comboSolucao1 = document.getSelectById("primeiraListaSolucao");
		comboSolucao1.removeAllOptions();
		CategoriaSolucaoService solucaoService = (CategoriaSolucaoService) ServiceLocator.getInstance().getService(CategoriaSolucaoService.class, null);
		Collection colSolucao = solucaoService.listaCategoriasSolucaoAtivas();
		comboSolucao1.addOptions(colSolucao, "idCategoriaSolucao", "DescricaoCategoriaSolucao", null);
	}

	@Override
	public Class getBeanClass() {
		return RelatorioCausaSolucaoDTO.class;
	}

	public void gerarCausaSolucaoGraficoBarras(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);

		RelatorioCausaSolucaoDTO relatorioCausaSolucaoDto = (RelatorioCausaSolucaoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService)ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		Collection<RelatorioCausaSolucaoDTO> listaCausaSolicitacao = solicitacaoServicoService.listaCausaSolicitacao(relatorioCausaSolucaoDto);
		Collection<RelatorioCausaSolucaoDTO> listaSolucaoSolicitacao = solicitacaoServicoService.listaSolucaoSolicitacao(relatorioCausaSolucaoDto);

		if(listaCausaSolicitacao == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		DefaultPieDataset datasetCausa = new DefaultPieDataset();
		Double totalCausas = 0.0;
		Double percentCausa = 0.0;

		for (RelatorioCausaSolucaoDTO lstCausaSol : listaCausaSolicitacao) {
			if (lstCausaSol != null) totalCausas += lstCausaSol.getNumeroSolicitacoes();
		}
		for (RelatorioCausaSolucaoDTO lstCausaSol : listaCausaSolicitacao) {
			if (lstCausaSol != null) {
				percentCausa = (double) Math.round((lstCausaSol.getNumeroSolicitacoes()/totalCausas)*100);
				if (lstCausaSol.getDescricaoCausa() != null && !lstCausaSol.getDescricaoCausa().equals("")) {
					datasetCausa.setValue(lstCausaSol.getDescricaoCausa() + " (" + lstCausaSol.getNumeroSolicitacoes() + " - " + percentCausa + "%)", percentCausa);
				} else {
					datasetCausa.setValue(UtilI18N.internacionaliza(request, "relatorioCausaSolucao.semCausa") + " (" + lstCausaSol.getNumeroSolicitacoes() + " - " + percentCausa + "%)", percentCausa);
				}
			}
		}

		DefaultPieDataset datasetSolucao = new DefaultPieDataset();
		Double totalSolucoes = 0.0;
		Double percentSolucoes = 0.0;

		for (RelatorioCausaSolucaoDTO lstSolucaoSol : listaSolucaoSolicitacao) {
			if (lstSolucaoSol != null) totalSolucoes += lstSolucaoSol.getNumeroSolicitacoes();
		}
		for (RelatorioCausaSolucaoDTO lstSolucaoSol : listaSolucaoSolicitacao) {
			if (lstSolucaoSol != null) {
				percentSolucoes = (double) Math.round((lstSolucaoSol.getNumeroSolicitacoes()/totalSolucoes)*100);
				if (lstSolucaoSol.getDescricaoCategoriaSolucao() != null && !lstSolucaoSol.getDescricaoCategoriaSolucao().equals("")) {
					datasetSolucao.setValue(lstSolucaoSol.getDescricaoCategoriaSolucao() + " (" + lstSolucaoSol.getNumeroSolicitacoes() + " - " + percentSolucoes + "%)", percentSolucoes);
				} else {
					datasetSolucao.setValue(UtilI18N.internacionaliza(request, "relatorioCausaSolucao.semSolucao") + " (" + lstSolucaoSol.getNumeroSolicitacoes() + " - " + percentSolucoes + "%)", percentSolucoes);
				}
			}
		}

		if (datasetCausa.getItemCount() == 0 && datasetSolucao.getItemCount() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		JFreeChart pieChartCausa = ChartFactory.createPieChart(UtilI18N.internacionaliza(request, "relatorioCausaSolucao.causa"), datasetCausa, true, true, false);
		JFreeChart pieChartSolucao = ChartFactory.createPieChart(UtilI18N.internacionaliza(request, "relatorioCausaSolucao.solucao"), datasetSolucao, true, true, false);

		File fileCausa = null;
		File fileSolucao = null;
		try {
			String diretorioRelativoOSCausa = CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles" + "/ChartCausa.png";
			fileCausa = new File(diretorioRelativoOSCausa);
			ChartUtilities.saveChartAsPNG(fileCausa, pieChartCausa, 520, 675);

			String diretorioRelativoOSSolucao = CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles" + "/ChartSolucao.png";
			fileSolucao = new File(diretorioRelativoOSSolucao);
			ChartUtilities.saveChartAsPNG(fileSolucao, pieChartSolucao, 520, 675);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}

		Map<String, Object> parametros = new HashMap<String, Object>();

		HttpSession session = request.getSession(true);
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioCausaSolucao.titulo"));
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("dataInicio", relatorioCausaSolucaoDto.getDataInicio());
		parametros.put("dataFim", relatorioCausaSolucaoDto.getDataFim());
		parametros.put("Logo", LogoRel.getFile());

		Date dt = new Date();
		String strCompl = "" + dt.getTime();

		parametros.put("ChartCausa", fileCausa);
		parametros.put("ChartSolucao", fileSolucao);

		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioCausaSolucaoGraficoBarras.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		if (relatorioCausaSolucaoDto != null && relatorioCausaSolucaoDto.getGenerationType() != null) {
			if (relatorioCausaSolucaoDto.getGenerationType().equalsIgnoreCase("xls")) {
				JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioCausaSolucaoGraficoBarras.jrxml");
				JasperReport relatorio = JasperCompileManager.compileReport(desenho);
				JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros);

				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioCausaSolucaoGraficoBarras" + strCompl + ".xls");

				exporter.exportReport();

				document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
						+ "/RelatorioCausaSolucaoGraficoBarras" + strCompl + ".xls')");

			} else {
				JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros);
				JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioCausaSolucaoGraficoBarras" + strCompl + ".pdf");

				document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
						+ "/RelatorioCausaSolucaoGraficoBarras" + strCompl + ".pdf')");
			}
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void gerarCausaSolucaoAnalitico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);

		RelatorioCausaSolucaoDTO relatorioCausaSolucaoDto = (RelatorioCausaSolucaoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService)ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		Collection<RelatorioCausaSolucaoDTO> listaCausaSolucaoSolicitacao = solicitacaoServicoService.listaCausaSolucaoAnalitico(relatorioCausaSolucaoDto);

		Collection<RelatorioCausaSolucaoDTO> listDadosRelatorio = new ArrayList<RelatorioCausaSolucaoDTO>();

		if (listaCausaSolucaoSolicitacao != null) {
			for (RelatorioCausaSolucaoDTO porSolicitacao : listaCausaSolucaoSolicitacao) {
				if (porSolicitacao.getDescricaoCausa() == null || porSolicitacao.getDescricaoCausa().equalsIgnoreCase("")) {
					porSolicitacao.setDescricaoCausa(UtilI18N.internacionaliza(request, "relatorioCausaSolucao.semCausa"));
				}
				if (porSolicitacao.getDescricaoCategoriaSolucao() == null || porSolicitacao.getDescricaoCategoriaSolucao().equalsIgnoreCase("")) {
					porSolicitacao.setDescricaoCategoriaSolucao(UtilI18N.internacionaliza(request, "relatorioCausaSolucao.semSolucao"));
				}

				listDadosRelatorio.add(porSolicitacao);
			}
		}

		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		Map<String, Object> parametros = new HashMap<String, Object>();

		HttpSession session = request.getSession(true);
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioCausaSolucao.titulo"));
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("dataInicio", relatorioCausaSolucaoDto.getDataInicio());
		parametros.put("dataFim", relatorioCausaSolucaoDto.getDataFim());
		parametros.put("Logo", LogoRel.getFile());

		Date dt = new Date();
		String strCompl = "" + dt.getTime();

		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioCausaSolucaoAnalitico.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);

		if (relatorioCausaSolucaoDto != null && relatorioCausaSolucaoDto.getGenerationType() != null) {
			if (relatorioCausaSolucaoDto.getGenerationType().equalsIgnoreCase("xls")) {
				JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioCausaSolucaoAnalitico.jrxml");
				JasperReport relatorio = JasperCompileManager.compileReport(desenho);
				JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioCausaSolucaoAnalitico" + strCompl + ".xls");

				exporter.exportReport();

				document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
						+ diretorioRelativoOS + "/RelatorioCausaSolucaoAnalitico" + strCompl + ".xls')");
			} else {
				try
				{
					JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
					JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
					parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
					JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
					//JasperViewer.viewReport(print,false);

					JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioCausaSolucaoAnalitico" + strCompl + ".pdf");

					document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
							+ diretorioRelativoOS + "/RelatorioCausaSolucaoAnalitico" + strCompl + ".pdf')");
				} catch(OutOfMemoryError e) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
				}
			}
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RelatorioCausaSolucaoDTO relatorioCausaSolucaoDto = (RelatorioCausaSolucaoDTO) document.getBean();

		HTMLSelect comboGrupo1 = document.getSelectById("primeiraListaGrupo");
		HTMLSelect comboGrupo2 = document.getSelectById("segundaListaGrupo");
		HTMLSelect servicos = document.getSelectById("listaServico");

		servicos.removeAllOptions();
		comboGrupo1.removeAllOptions();
		comboGrupo2.removeAllOptions();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupo;

		if (relatorioCausaSolucaoDto != null && relatorioCausaSolucaoDto.getIdContrato() != null) {
			colGrupo = grupoService.listGrupoByIdContrato(relatorioCausaSolucaoDto.getIdContrato());
		} else {
			colGrupo = grupoService.listaGruposAtivos();
		}

		comboGrupo1.addOptions(colGrupo, "idGrupo", "Nome", null);
	}

}

