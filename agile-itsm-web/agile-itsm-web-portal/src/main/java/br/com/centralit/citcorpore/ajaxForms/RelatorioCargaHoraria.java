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
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.RelatorioCargaHorariaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.HistoricoSolicitacaoServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes","unused" })
public class RelatorioCargaHoraria extends AjaxFormAction {
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
		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.list();
		document.getSelectById("idContrato").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);
		document.getSelectById("idGrupoAtual").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
	}

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	/**
	 * Faz a impressão do relatório no formato pdf.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author cledson.junior
	 */

	public void imprimirCargaHorariaUsuarioPDF(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		HistoricoSolicitacaoServicoService historicoSolicitacaoServicoService = (HistoricoSolicitacaoServicoService) ServiceLocator.getInstance().getService(HistoricoSolicitacaoServicoService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		GrupoDTO grupoDTO = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (solicitacaoServicoDTO.getIdSolicitacaoServicoPesquisa() == null) {
			if (solicitacaoServicoDTO.getDataInicio() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
			if (solicitacaoServicoDTO.getDataFim() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}

		}

		Collection<RelatorioCargaHorariaDTO> listDadosRelatorio = new ArrayList<RelatorioCargaHorariaDTO>();
		if (historicoSolicitacaoServicoService.imprimirCargaHorariaUsuario(solicitacaoServicoDTO) != null) {
			Integer  i = 0;
			for (RelatorioCargaHorariaDTO relatorio : historicoSolicitacaoServicoService.imprimirCargaHorariaUsuario(solicitacaoServicoDTO)) {
				if (relatorio.getSoma() != null && relatorio.getSoma() != 0) {
					 i++;
					 RelatorioCargaHorariaDTO relatorioCargaHorariaDTO = new RelatorioCargaHorariaDTO();
					 relatorioCargaHorariaDTO.setIdServico(i);
					 relatorioCargaHorariaDTO.setNomeUsuario(relatorio.getNomeUsuario().toUpperCase().trim());
					 int horasAux = ((int)relatorio.getSoma().intValue()) / 60;
					 int minAux = ((int)relatorio.getSoma().intValue()) % 60;
					 relatorioCargaHorariaDTO.setTotal(horasAux +"h:"+minAux+"m");
					listDadosRelatorio.add(relatorioCargaHorariaDTO);
				}
				
			}
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioCargaHoraria.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioHorasTrabalhadas.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}
		if (solicitacaoServicoDTO.getIdGrupoAtual() != null) {
			grupoDTO.setIdGrupo(solicitacaoServicoDTO.getIdGrupoAtual());
			grupoDTO = (GrupoDTO) grupoService.restore(grupoDTO);
			parametros.put("grupo", grupoDTO.getNome().toUpperCase().trim());
		} else {
			parametros.put("grupo", UtilI18N.internacionaliza(request, "citcorpore.comum.todos").toUpperCase());
		}
		
		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		try
		{
		
			JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioCargaHoraria" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioCargaHoraria" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();


	}
	
	/**
	 * Gera o relatorio no formato XLS
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public void imprimirCargaHorariaUsuarioXLS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		HistoricoSolicitacaoServicoService historicoSolicitacaoServicoService = (HistoricoSolicitacaoServicoService) ServiceLocator.getInstance().getService(HistoricoSolicitacaoServicoService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		GrupoDTO grupoDTO = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (solicitacaoServicoDTO.getIdSolicitacaoServicoPesquisa() == null) {
			if (solicitacaoServicoDTO.getDataInicio() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
			if (solicitacaoServicoDTO.getDataFim() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}

		}

		Collection<RelatorioCargaHorariaDTO> listDadosRelatorio = new ArrayList<RelatorioCargaHorariaDTO>();
		if (historicoSolicitacaoServicoService.imprimirCargaHorariaUsuario(solicitacaoServicoDTO) != null) {
			Integer  i = 0;
			for (RelatorioCargaHorariaDTO relatorio : historicoSolicitacaoServicoService.imprimirCargaHorariaUsuario(solicitacaoServicoDTO)) {
				if (relatorio.getSoma() != null && relatorio.getSoma() != 0) {
					 i++;
					 RelatorioCargaHorariaDTO relatorioCargaHorariaDTO = new RelatorioCargaHorariaDTO();
					 relatorioCargaHorariaDTO.setIdServico(i);
					 relatorioCargaHorariaDTO.setNomeUsuario(relatorio.getNomeUsuario().toUpperCase().trim());
					 int horasAux = ((int)relatorio.getSoma().intValue()) / 60;
					 int minAux = ((int)relatorio.getSoma().intValue()) % 60;
					 relatorioCargaHorariaDTO.setTotal(horasAux +"h:"+minAux+"m");
					listDadosRelatorio.add(relatorioCargaHorariaDTO);
				}
				
			}
		}

		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioHorasTrabalhadas.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}
		if (solicitacaoServicoDTO.getIdGrupoAtual() != null) {
			grupoDTO.setIdGrupo(solicitacaoServicoDTO.getIdGrupoAtual());
			grupoDTO = (GrupoDTO) grupoService.restore(grupoDTO);
			parametros.put("grupo", grupoDTO.getNome().toUpperCase().trim());
		} else {
			parametros.put("grupo", UtilI18N.internacionaliza(request, "citcorpore.comum.todos").toUpperCase());
		}
		
		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		try
		{
			JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);

			JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioCargaHoraria.jrxml");

			JasperReport relatorio = JasperCompileManager.compileReport(desenho);

			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioCargaHoraria" + "_" + usuario.getIdUsuario() + ".xls");

			exporter.exportReport();

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioCargaHoraria" + "_" + usuario.getIdUsuario() + ".xls')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();


	}
	
	public void imprimirCargaHorariaGrupoPDF(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		HistoricoSolicitacaoServicoService historicoSolicitacaoServicoService = (HistoricoSolicitacaoServicoService) ServiceLocator.getInstance().getService(HistoricoSolicitacaoServicoService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		GrupoDTO grupoDTO = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (solicitacaoServicoDTO.getIdSolicitacaoServicoPesquisa() == null) {
			if (solicitacaoServicoDTO.getDataInicio() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
			if (solicitacaoServicoDTO.getDataFim() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}

		}
		Collection<RelatorioCargaHorariaDTO> listDadosRelatorio = new ArrayList<RelatorioCargaHorariaDTO>();
		if (historicoSolicitacaoServicoService.imprimirCargaHorariaGrupo(solicitacaoServicoDTO) != null) {
			Integer  i = 0;
			for (RelatorioCargaHorariaDTO relatorio : historicoSolicitacaoServicoService.imprimirCargaHorariaGrupo(solicitacaoServicoDTO)) {
				if (relatorio.getSoma() != null && relatorio.getSoma() != 0) {
				 i++;
				 RelatorioCargaHorariaDTO relatorioCargaHorariaDTO = new RelatorioCargaHorariaDTO();
				 relatorioCargaHorariaDTO.setIdServico(i);
				 relatorioCargaHorariaDTO.setNomeUsuario(relatorio.getNomeGrupo().toUpperCase().trim());
				 int horasAux = ((int)relatorio.getSoma().intValue()) / 60;
				 int minAux = ((int)relatorio.getSoma().intValue()) % 60;
				 relatorioCargaHorariaDTO.setTotal(horasAux +"h:"+minAux+"m");
				 relatorioCargaHorariaDTO.setNomeGrupo(relatorio.getNomeGrupo().toUpperCase().trim());
				listDadosRelatorio.add(relatorioCargaHorariaDTO);
				}
			}
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioCargaHorariaGrupo.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioHorasTrabalhadasGrupo.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}
		if (solicitacaoServicoDTO.getIdGrupoAtual() != null) {
			grupoDTO.setIdGrupo(solicitacaoServicoDTO.getIdGrupoAtual());
			grupoDTO = (GrupoDTO) grupoService.restore(grupoDTO);
			parametros.put("grupo", grupoDTO.getNome().toUpperCase());
		} else {
			parametros.put("grupo", UtilI18N.internacionaliza(request, "citcorpore.comum.todos").toUpperCase());
		}
		
		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		try
		{
		
			JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioCargaHorariaGrupo" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioCargaHorariaGrupo" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();


	}
	
	public void imprimirCargaHorariaGrupoXLS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		HistoricoSolicitacaoServicoService historicoSolicitacaoServicoService = (HistoricoSolicitacaoServicoService) ServiceLocator.getInstance().getService(HistoricoSolicitacaoServicoService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		GrupoDTO grupoDTO = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (solicitacaoServicoDTO.getIdSolicitacaoServicoPesquisa() == null) {
			if (solicitacaoServicoDTO.getDataInicio() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
			if (solicitacaoServicoDTO.getDataFim() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}

		}
		Collection<RelatorioCargaHorariaDTO> listDadosRelatorio = new ArrayList<RelatorioCargaHorariaDTO>();
		if (historicoSolicitacaoServicoService.imprimirCargaHorariaGrupo(solicitacaoServicoDTO) != null) {
			Integer  i = 0;
			for (RelatorioCargaHorariaDTO relatorio : historicoSolicitacaoServicoService.imprimirCargaHorariaGrupo(solicitacaoServicoDTO)) {
				if (relatorio.getSoma() != null && relatorio.getSoma() != 0) {
				 i++;
				 RelatorioCargaHorariaDTO relatorioCargaHorariaDTO = new RelatorioCargaHorariaDTO();
				 relatorioCargaHorariaDTO.setIdServico(i);
				 relatorioCargaHorariaDTO.setNomeUsuario(relatorio.getNomeGrupo().toUpperCase().trim());
				 int horasAux = ((int)relatorio.getSoma().intValue()) / 60;
				 int minAux = ((int)relatorio.getSoma().intValue()) % 60;
				 relatorioCargaHorariaDTO.setTotal(horasAux +"h:"+minAux+"m");
				 relatorioCargaHorariaDTO.setNomeGrupo(relatorio.getNomeGrupo().toUpperCase().trim());
				listDadosRelatorio.add(relatorioCargaHorariaDTO);
				}
			}
		}

		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioHorasTrabalhadasGrupo.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}
		if (solicitacaoServicoDTO.getIdGrupoAtual() != null) {
			grupoDTO.setIdGrupo(solicitacaoServicoDTO.getIdGrupoAtual());
			grupoDTO = (GrupoDTO) grupoService.restore(grupoDTO);
			parametros.put("grupo", grupoDTO.getNome().toUpperCase());
		} else {
			parametros.put("grupo", UtilI18N.internacionaliza(request, "citcorpore.comum.todos").toUpperCase());
		}
		
		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		try
		{
			JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);

			JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioCargaHorariaGrupo.jrxml");

			JasperReport relatorio = JasperCompileManager.compileReport(desenho);

			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioCargaHorariaGrupo" + "_" + usuario.getIdUsuario() + ".xls");

			exporter.exportReport();

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioCargaHorariaGrupo" + "_" + usuario.getIdUsuario() + ".xls')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();


	}
	
	public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		//Busca por ID do contrato por grupos ATIVOS
		Collection colGrupo = grupoService.listGrupoAtivosByIdContrato(solicitacaoServicoDTO.getIdContrato().intValue());
		document.getSelectById("idGrupoAtual").removeAllOptions();
		document.getSelectById("idGrupoAtual").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		document.getSelectById("idGrupoAtual").addOptions(colGrupo, "idGrupo", "nome", null);
	}
}
