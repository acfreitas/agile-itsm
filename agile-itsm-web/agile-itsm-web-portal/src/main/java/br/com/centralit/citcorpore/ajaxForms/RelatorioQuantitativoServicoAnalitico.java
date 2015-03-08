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
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoServicoAnaliticoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes"})
public class RelatorioQuantitativoServicoAnalitico extends AjaxFormAction {
	UsuarioDTO usuario;
	private String localeSession = null;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		// Preenchendo a combobox de contratos.
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);

		// Preenchendo a combobox de tipo de servico.
		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		HTMLSelect comboTipoDemandaServico = document.getSelectById("idTipoDemandaServico");
		comboTipoDemandaServico.removeAllOptions();
		comboTipoDemandaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		Collection col = tipoDemandaService.listSolicitacoes();
		if (col != null) {
			comboTipoDemandaServico.addOptions(col, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
		}
		
		/**
		 * Preencher combo TOP List
		 * 
		 * @author thyen.chang
		 */
		for(Enumerados.TopListEnum valor : Enumerados.TopListEnum.values())
			document.getSelectById("topList").addOption(valor.getValorTopList().toString(), UtilI18N.internacionaliza(request, valor.getNomeTopList()));
		

	}

	@Override
	public Class getBeanClass() {
		return ServicoDTO.class;
	}

	public void imprimirRelatorioQuantitativo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		int contadorRegistros = 0;

		ServicoDTO servicoDTO = (ServicoDTO) document.getBean();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (servicoDTO.getDataInicio() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (servicoDTO.getDataFim() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		Collection<RelatorioQuantitativoServicoAnaliticoDTO> listaDadosRelatorio = new ArrayList<RelatorioQuantitativoServicoAnaliticoDTO>();
		Collection<ServicoDTO> listaQuantidadeServicoAnalitico = servicoService.listaQuantidadeServicoAnalitico(servicoDTO);

		if(listaQuantidadeServicoAnalitico != null){
			for (ServicoDTO servicoDTO2 : listaQuantidadeServicoAnalitico) {
				contadorRegistros ++;
				if (servicoDTO2.getTempoAtendimentoHH() == null && servicoDTO2.getTempoAtendimentoMM() == null) {
					if (servicoDTO2.getSituacao().equalsIgnoreCase("EmAndamento")) {
						servicoDTO2.setTempoDecorrido(servicoDTO2.getSituacao());
					}else{
						servicoDTO2.setTempoDecorrido(UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.semTempoAtendimento"));
					}
				}else{
					servicoDTO2.setTempoDecorrido(servicoDTO2.getTempoAtendimentoHH() + "h " + servicoDTO2.getTempoAtendimentoMM() + "m");
				}
			}
		}
		if (listaQuantidadeServicoAnalitico != null) {
			RelatorioQuantitativoServicoAnaliticoDTO relatorioQuantitativoServicoAnaliticoDTO = new RelatorioQuantitativoServicoAnaliticoDTO();
			relatorioQuantitativoServicoAnaliticoDTO.setListaPorPeriodo(listaQuantidadeServicoAnalitico);
			listaDadosRelatorio.add(relatorioQuantitativoServicoAnaliticoDTO);
		}

		Date dt = new Date();

		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoServicoAnalitico.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioQuantitativo.relatorioQuantitativoServicoConcluidosAnalitico"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", servicoDTO.getDataInicio());
		parametros.put("dataFim", servicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		parametros.put("TotalRegistros", contadorRegistros);

		ContratoDTO contratoDto = new ContratoDTO();
		if (servicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(servicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		}
		parametros.put("contrato", contratoDto.getNumero());

		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
		if (servicoDTO.getIdTipoDemandaServico() != null) {
			tipoDemandaServicoDto.setIdTipoDemandaServico(servicoDTO.getIdTipoDemandaServico());
			tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaService.restore(tipoDemandaServicoDto);
		}
		parametros.put("nomeTipoDemandaServico", tipoDemandaServicoDto.getNomeTipoDemandaServico());

		if (listaDadosRelatorio.size() == 0 || listaQuantidadeServicoAnalitico.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaDadosRelatorio);

			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			// JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioQuantitativoServicoAnalitico" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioQuantitativoServicoAnalitico" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void imprimirRelatorioQuantitativoXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		ServicoDTO servicoDTO = (ServicoDTO) document.getBean();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		int contadorRegistros = 0;
		
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (servicoDTO.getDataInicio() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (servicoDTO.getDataFim() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		Collection<RelatorioQuantitativoServicoAnaliticoDTO> listaDadosRelatorio = new ArrayList<RelatorioQuantitativoServicoAnaliticoDTO>();
		Collection<ServicoDTO> listaQuantidadeServicoAnalitico = servicoService.listaQuantidadeServicoAnalitico(servicoDTO);

		if(listaQuantidadeServicoAnalitico != null){
			for (ServicoDTO servicoDTO2 : listaQuantidadeServicoAnalitico) {
				contadorRegistros ++;
				if (servicoDTO2.getTempoAtendimentoHH() == null && servicoDTO2.getTempoAtendimentoMM() == null) {
					if (servicoDTO2.getSituacao().equalsIgnoreCase("EmAndamento")) {
						servicoDTO2.setTempoDecorrido(servicoDTO2.getSituacao());
					}else{
						servicoDTO2.setTempoDecorrido(UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.semTempoAtendimento"));
					}
				}else{
					servicoDTO2.setTempoDecorrido(servicoDTO2.getTempoAtendimentoHH() + "h " + servicoDTO2.getTempoAtendimentoMM() + "m");
				}
			}
		}
		if (listaQuantidadeServicoAnalitico != null && !listaQuantidadeServicoAnalitico.isEmpty()) {
			RelatorioQuantitativoServicoAnaliticoDTO relatorioQuantitativoServicoAnaliticoDTO = new RelatorioQuantitativoServicoAnaliticoDTO();
			relatorioQuantitativoServicoAnaliticoDTO.setListaPorPeriodo(listaQuantidadeServicoAnalitico);
			listaDadosRelatorio.add(relatorioQuantitativoServicoAnaliticoDTO);
		}
		
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
		String caminhoSubRelatorioJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioQuantitativo.relatorioQuantitativoServicoConcluidosAnalitico"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", servicoDTO.getDataInicio());
		parametros.put("dataFim", servicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		parametros.put("SUBREPORT_DIR", caminhoSubRelatorioJasper);
		parametros.put("TotalRegistros", contadorRegistros);

		ContratoDTO contratoDto = new ContratoDTO();
		if (servicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(servicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		}
		parametros.put("contrato", contratoDto.getNumero());

		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
		if (servicoDTO.getIdTipoDemandaServico() != null) {
			tipoDemandaServicoDto.setIdTipoDemandaServico(servicoDTO.getIdTipoDemandaServico());
			tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaService.restore(tipoDemandaServicoDto);
		}
		parametros.put("nomeTipoDemandaServico", tipoDemandaServicoDto.getNomeTipoDemandaServico());

		if (listaDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}


		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaDadosRelatorio);
			JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoServicoAnaliticoXls.jrxml");

			JasperReport relatorio = JasperCompileManager.compileReport(desenho);

			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioQuantitativoServicoAnaliticoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

			exporter.exportReport();

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioQuantitativoServicoAnaliticoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}

	}

}
