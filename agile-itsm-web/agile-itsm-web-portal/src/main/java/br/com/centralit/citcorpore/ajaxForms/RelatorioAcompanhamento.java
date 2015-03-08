package br.com.centralit.citcorpore.ajaxForms;

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
import br.com.centralit.citcorpore.bean.RelatorioAcompanhamentoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unused" })
public class RelatorioAcompanhamento extends AjaxFormAction {
	private UsuarioDTO usuario;
	private  String localeSession = null;
	
	

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
		document.getSelectById("idContrato").addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);

	}

	public void imprimirRelatorioDeAcompanhamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		
		Double mediaProximoMeses = 0.0;
		Integer mesesVazio = 0;
		Double totalCustoAtividade = 0.0;
		Double valorEstimadoContrato = 0.0;
		Double valorInovacoes = 0.0;
		Double valorAdtivo = 0.0;
		Double total = 0.0;
		usuario = WebUtil.getUsuario(request);
		RelatorioAcompanhamentoDTO relatorioAcompanhamentoDTO = (RelatorioAcompanhamentoDTO) document.getBean();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, WebUtil.getUsuarioSistema(request));

		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		contratoDto.setIdContrato(relatorioAcompanhamentoDTO.getIdContrato());
		contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

		Collection<RelatorioAcompanhamentoDTO> listaAcompanhamentoPorPeriodoDoContrato = osService.listaAcompanhamentoPorPeriodoDoContrato(relatorioAcompanhamentoDTO);

		if (listaAcompanhamentoPorPeriodoDoContrato != null) {

			for (RelatorioAcompanhamentoDTO relatorioAcompanhamentoDto : listaAcompanhamentoPorPeriodoDoContrato) {

				mesesVazio = 0;
				mediaProximoMeses = 0.0;

				if (relatorioAcompanhamentoDto.getCustoAtividade() != null) {
					totalCustoAtividade = totalCustoAtividade + relatorioAcompanhamentoDto.getCustoAtividade();
				}

				if (relatorioAcompanhamentoDto.getValorEstimadoContrato() != null) {
					valorEstimadoContrato = relatorioAcompanhamentoDto.getValorEstimadoContrato();
				}
				if (relatorioAcompanhamentoDto.getQuantidadePeriodoRealizado() != null) {
					mesesVazio = relatorioAcompanhamentoDto.getQuantidadePeriodoRealizado() - relatorioAcompanhamentoDto.getPeridoVigenciaContrato();

				}
				if (relatorioAcompanhamentoDto.getValorEstimadoContrato() != null && totalCustoAtividade != null && mesesVazio != null) {
					mediaProximoMeses = (totalCustoAtividade - relatorioAcompanhamentoDto.getValorEstimadoContrato()) / mesesVazio;
				}
				if (relatorioAcompanhamentoDto.getValorEstimadoContrato() != null && relatorioAcompanhamentoDto.getPeridoVigenciaContrato() != null) {
					relatorioAcompanhamentoDto.setValorPorRata(relatorioAcompanhamentoDto.getValorEstimadoContrato() / relatorioAcompanhamentoDto.getPeridoVigenciaContrato());
				}
				if (relatorioAcompanhamentoDto.getValorPorRata() != null && relatorioAcompanhamentoDto.getCustoAtividade() != null) {
					relatorioAcompanhamentoDto.setSaldo(relatorioAcompanhamentoDto.getValorPorRata() - relatorioAcompanhamentoDto.getCustoAtividade());
				}
				if (relatorioAcompanhamentoDto.getQuantidadePeriodoRealizado() != null && mediaProximoMeses != null) {
					valorInovacoes = mediaProximoMeses - (totalCustoAtividade / relatorioAcompanhamentoDto.getQuantidadePeriodoRealizado());
				}
				if (relatorioAcompanhamentoDto.getValorEstimadoContrato() != null && valorAdtivo != null) {
					total = relatorioAcompanhamentoDto.getValorEstimadoContrato() + valorAdtivo;
				}

			}
		}

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO",UtilI18N.internacionaliza(request, "relatorioAcompanhamento.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("mediaProximosMeses", mediaProximoMeses);
		parametros.put("valorEstimadocontrato", contratoDto.getValorEstimado());
		parametros.put("valorInovacoes", valorInovacoes);
		parametros.put("totalCustoAtividade", totalCustoAtividade);
		parametros.put("dataInicio", contratoDto.getDataContrato());
		parametros.put("dataFim", contratoDto.getDataFimContrato());
		parametros.put("valorAdtivo", valorAdtivo);
		parametros.put("total", total);
		parametros.put("Logo", LogoRel.getFile());
		
		if (relatorioAcompanhamentoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(relatorioAcompanhamentoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", null);
		}
		
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioAcompanhamentoUst.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		if (listaAcompanhamentoPorPeriodoDoContrato != null && listaAcompanhamentoPorPeriodoDoContrato.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		try
		{
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaAcompanhamentoPorPeriodoDoContrato);
	
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioAcompanhamentoUst" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioAcompanhamentoUst" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void imprimirRelatorioDeAcompanhamentoXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		Double mediaProximoMeses = 0.0;
		Integer mesesVazio = 0;
		Double totalCustoAtividade = 0.0;
		Double valorEstimadoContrato = 0.0;
		Double valorInovacoes = 0.0;
		Double valorAdtivo = 0.0;
		Double total = 0.0;
		usuario = WebUtil.getUsuario(request);
		RelatorioAcompanhamentoDTO relatorioAcompanhamentoDTO = (RelatorioAcompanhamentoDTO) document.getBean();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class,  WebUtil.getUsuarioSistema(request));

		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		contratoDto.setIdContrato(relatorioAcompanhamentoDTO.getIdContrato());
		contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		Collection<RelatorioAcompanhamentoDTO> listaAcompanhamentoPorPeriodoDoContrato = osService.listaAcompanhamentoPorPeriodoDoContrato(relatorioAcompanhamentoDTO);

		if (listaAcompanhamentoPorPeriodoDoContrato != null) {

			for (RelatorioAcompanhamentoDTO relatorioAcompanhamentoDto : listaAcompanhamentoPorPeriodoDoContrato) {

				mesesVazio = 0;
				mediaProximoMeses = 0.0;

				if (relatorioAcompanhamentoDto.getCustoAtividade() != null) {
					totalCustoAtividade = totalCustoAtividade + relatorioAcompanhamentoDto.getCustoAtividade();
				}

				if (relatorioAcompanhamentoDto.getValorEstimadoContrato() != null) {
					valorEstimadoContrato = relatorioAcompanhamentoDto.getValorEstimadoContrato();
				}
				if (relatorioAcompanhamentoDto.getQuantidadePeriodoRealizado() != null) {
					mesesVazio = relatorioAcompanhamentoDto.getQuantidadePeriodoRealizado() - relatorioAcompanhamentoDto.getPeridoVigenciaContrato();

				}
				if (relatorioAcompanhamentoDto.getValorEstimadoContrato() != null && totalCustoAtividade != null && mesesVazio != null) {
					mediaProximoMeses = (totalCustoAtividade - relatorioAcompanhamentoDto.getValorEstimadoContrato()) / mesesVazio;
				}
				if (relatorioAcompanhamentoDto.getValorEstimadoContrato() != null && relatorioAcompanhamentoDto.getPeridoVigenciaContrato() != null) {
					relatorioAcompanhamentoDto.setValorPorRata(relatorioAcompanhamentoDto.getValorEstimadoContrato() / relatorioAcompanhamentoDto.getPeridoVigenciaContrato());
				}
				if (relatorioAcompanhamentoDto.getValorPorRata() != null && relatorioAcompanhamentoDto.getCustoAtividade() != null) {
					relatorioAcompanhamentoDto.setSaldo(relatorioAcompanhamentoDto.getValorPorRata() - relatorioAcompanhamentoDto.getCustoAtividade());
				}
				if (relatorioAcompanhamentoDto.getQuantidadePeriodoRealizado() != null && mediaProximoMeses != null) {
					valorInovacoes = mediaProximoMeses - (totalCustoAtividade / relatorioAcompanhamentoDto.getQuantidadePeriodoRealizado()) ;
				}
				if (relatorioAcompanhamentoDto.getValorEstimadoContrato() != null && valorAdtivo != null) {
					total = relatorioAcompanhamentoDto.getValorEstimadoContrato() + valorAdtivo;
				}

			}
		}

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO",UtilI18N.internacionaliza(request, "relatorioAcompanhamento.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("mediaProximosMeses", mediaProximoMeses);
		parametros.put("valorEstimadocontrato", contratoDto.getValorEstimado());
		parametros.put("valorInovacoes", valorInovacoes);
		parametros.put("totalCustoAtividade", totalCustoAtividade);
		parametros.put("dataInicio", contratoDto.getDataContrato());
		parametros.put("dataFim", contratoDto.getDataFimContrato());
		parametros.put("valorAdtivo", valorAdtivo);
		parametros.put("total", total);
		parametros.put("Logo", LogoRel.getFile());
		
		if (relatorioAcompanhamentoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(relatorioAcompanhamentoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", null);
		}
		

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioAcompanhamentoUstXls.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		if (listaAcompanhamentoPorPeriodoDoContrato != null && listaAcompanhamentoPorPeriodoDoContrato.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaAcompanhamentoPorPeriodoDoContrato);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioAcompanhamentoUstXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioAcompanhamentoUstXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
				+ diretorioRelativoOS + "/RelatorioAcompanhamentoUstXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	@Override
	public Class getBeanClass() {
		return RelatorioAcompanhamentoDTO.class;
	}

}
