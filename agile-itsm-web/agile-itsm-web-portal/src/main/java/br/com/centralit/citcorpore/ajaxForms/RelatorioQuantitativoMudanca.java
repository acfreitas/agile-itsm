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
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unused" })
public class RelatorioQuantitativoMudanca extends AjaxFormAction {
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

	}

	@Override
	public Class getBeanClass() {
		return RequisicaoMudancaDTO.class;
	}

	@SuppressWarnings("unchecked")
	public void imprimirRelatorioQuantitativo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();

		RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) document.getBean();

		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);

		usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (requisicaoMudancaDTO.getDataInicio() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (requisicaoMudancaDTO.getDataFim() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		RelatorioQuantitativoMudancaDTO relatorioQuantitativoMudancaDTO = new RelatorioQuantitativoMudancaDTO();

		Collection<RelatorioQuantitativoMudancaDTO> listDadosRelatorio = new ArrayList<RelatorioQuantitativoMudancaDTO>();
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorPeriodo = requisicaoMudancaService.listaQuantidadeMudancaPorPeriodo(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorStatus = requisicaoMudancaService.listaQuantidadeMudancaPorStatus(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorProprietario = requisicaoMudancaService.listaQuantidadeMudancaPorProprietario(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorSolicitante = requisicaoMudancaService.listaQuantidadeMudancaPorSolicitante(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorImpacto = requisicaoMudancaService.listaQuantidadeMudancaPorImpacto(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorUrgencia = requisicaoMudancaService.listaQuantidadeMudancaPorUrgencia(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeSemAprovacaoPorPeriodo = requisicaoMudancaService.listaQuantidadeSemAprovacaoPorPeriodo(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeERelacionamentos = requisicaoMudancaService.listaQuantidadeERelacionamentos(request, requisicaoMudancaDTO);

		if (listQuantidadeMudancaPorPeriodo != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorPeriodo(listQuantidadeMudancaPorPeriodo);
		}

		if (listQuantidadeMudancaPorStatus != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorStatus(listQuantidadeMudancaPorStatus);
		}

		if (listQuantidadeMudancaPorProprietario != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorProprietario(listQuantidadeMudancaPorProprietario);
		}

		if (listQuantidadeMudancaPorSolicitante != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorSolicitante(listQuantidadeMudancaPorSolicitante);
		}

		if (listQuantidadeMudancaPorImpacto != null) {
			for (RequisicaoMudancaDTO impacto : listQuantidadeMudancaPorImpacto) {
				if (impacto.getNivelImpacto() != null) {
					if (impacto.getNivelImpacto().equalsIgnoreCase("B")) {
						impacto.setNivelImpacto(UtilI18N.internacionaliza(request, "citcorpore.comum.baixo"));
					} else if (impacto.getNivelImpacto().equalsIgnoreCase("M")) {
						impacto.setNivelImpacto(UtilI18N.internacionaliza(request, "citcorpore.comum.medio"));
					} else if (impacto.getNivelImpacto().equalsIgnoreCase("A")) {
						impacto.setNivelImpacto(UtilI18N.internacionaliza(request, "citcorpore.comum.alto"));
					}
				}
			}
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorImpacto(listQuantidadeMudancaPorImpacto);
		}

		if (listQuantidadeMudancaPorUrgencia != null) {
			for (RequisicaoMudancaDTO urgencia : listQuantidadeMudancaPorUrgencia) {
				if (urgencia.getNivelUrgencia() != null) {
					if (urgencia.getNivelUrgencia().equalsIgnoreCase("B")) {
						urgencia.setNivelUrgencia(UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
					} else if (urgencia.getNivelUrgencia().equalsIgnoreCase("M")) {
						urgencia.setNivelUrgencia(UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
					} else if (urgencia.getNivelUrgencia().equalsIgnoreCase("A")) {
						urgencia.setNivelUrgencia(UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));
					}
				}
			}
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorUrgencia(listQuantidadeMudancaPorUrgencia);
		}

		if (listQuantidadeSemAprovacaoPorPeriodo != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadeSemAprovacaoPorPeriodo(listQuantidadeSemAprovacaoPorPeriodo);
		}

		if (listQuantidadeERelacionamentos != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadeERelacionamentos(listQuantidadeERelacionamentos);
		}

		listDadosRelatorio.add(relatorioQuantitativoMudancaDTO);

		Date dt = new Date();

		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoMudanca.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioQuantitativo.relatorioQuantitativoMudanca"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", requisicaoMudancaDTO.getDataInicio());
		parametros.put("dataFim", requisicaoMudancaDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());

		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);

			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			// JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioQuantitativoMudanca" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioQuantitativoMudanca" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	@SuppressWarnings("unchecked")
	public void imprimirRelatorioQuantitativoXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();

		RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) document.getBean();

		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);

		usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (requisicaoMudancaDTO.getDataInicio() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (requisicaoMudancaDTO.getDataFim() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		RelatorioQuantitativoMudancaDTO relatorioQuantitativoMudancaDTO = new RelatorioQuantitativoMudancaDTO();

		Collection<RelatorioQuantitativoMudancaDTO> listDadosRelatorio = new ArrayList<RelatorioQuantitativoMudancaDTO>();
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorPeriodo = requisicaoMudancaService.listaQuantidadeMudancaPorPeriodo(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorStatus = requisicaoMudancaService.listaQuantidadeMudancaPorStatus(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorProprietario = requisicaoMudancaService.listaQuantidadeMudancaPorProprietario(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorSolicitante = requisicaoMudancaService.listaQuantidadeMudancaPorSolicitante(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorImpacto = requisicaoMudancaService.listaQuantidadeMudancaPorImpacto(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeMudancaPorUrgencia = requisicaoMudancaService.listaQuantidadeMudancaPorUrgencia(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeSemAprovacaoPorPeriodo = requisicaoMudancaService.listaQuantidadeSemAprovacaoPorPeriodo(requisicaoMudancaDTO);
		Collection<RequisicaoMudancaDTO> listQuantidadeERelacionamentos = requisicaoMudancaService.listaQuantidadeERelacionamentos(request, requisicaoMudancaDTO);

		if (listQuantidadeMudancaPorPeriodo != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorPeriodo(listQuantidadeMudancaPorPeriodo);
		}

		if (listQuantidadeMudancaPorStatus != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorStatus(listQuantidadeMudancaPorStatus);
		}

		if (listQuantidadeMudancaPorProprietario != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorProprietario(listQuantidadeMudancaPorProprietario);
		}

		if (listQuantidadeMudancaPorSolicitante != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorSolicitante(listQuantidadeMudancaPorSolicitante);
		}

		if (listQuantidadeMudancaPorImpacto != null) {
			for (RequisicaoMudancaDTO impacto : listQuantidadeMudancaPorImpacto) {
				if (impacto.getNivelImpacto() != null) {
					if (impacto.getNivelImpacto().equalsIgnoreCase("B")) {
						impacto.setNivelImpacto(UtilI18N.internacionaliza(request, "citcorpore.comum.baixo"));
					} else if (impacto.getNivelImpacto().equalsIgnoreCase("M")) {
						impacto.setNivelImpacto(UtilI18N.internacionaliza(request, "citcorpore.comum.medio"));
					} else if (impacto.getNivelImpacto().equalsIgnoreCase("A")) {
						impacto.setNivelImpacto(UtilI18N.internacionaliza(request, "citcorpore.comum.alto"));
					}
				}
			}
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorImpacto(listQuantidadeMudancaPorImpacto);
		}

		if (listQuantidadeMudancaPorUrgencia != null) {
			for (RequisicaoMudancaDTO urgencia : listQuantidadeMudancaPorUrgencia) {
				if (urgencia.getNivelUrgencia() != null) {
					if (urgencia.getNivelUrgencia().equalsIgnoreCase("B")) {
						urgencia.setNivelUrgencia(UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
					} else if (urgencia.getNivelUrgencia().equalsIgnoreCase("M")) {
						urgencia.setNivelUrgencia(UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
					} else if (urgencia.getNivelUrgencia().equalsIgnoreCase("A")) {
						urgencia.setNivelUrgencia(UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));
					}
				}
			}
			relatorioQuantitativoMudancaDTO.setListaQuantidadePorUrgencia(listQuantidadeMudancaPorUrgencia);
		}

		if (listQuantidadeSemAprovacaoPorPeriodo != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadeSemAprovacaoPorPeriodo(listQuantidadeSemAprovacaoPorPeriodo);
		}

		if (listQuantidadeERelacionamentos != null) {
			relatorioQuantitativoMudancaDTO.setListaQuantidadeERelacionamentos(listQuantidadeERelacionamentos);
		}

		listDadosRelatorio.add(relatorioQuantitativoMudancaDTO);

		Date dt = new Date();

		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoMudancaXLS.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioQuantitativo.relatorioQuantitativoMudanca"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", requisicaoMudancaDTO.getDataInicio());
		parametros.put("dataFim", requisicaoMudancaDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());

		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}


		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);

			JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoMudancaXls.jrxml");

			JasperReport relatorio = JasperCompileManager.compileReport(desenho);

			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioQuantitativoMudancaXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

			exporter.exportReport();

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioQuantitativoMudancaXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");

		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

}
