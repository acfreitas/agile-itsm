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
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.PesquisaSatisfacaoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class RelatorioPesquisaSatisfacao extends AjaxFormAction {
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
		document.getSelectById("idContrato").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);
		HTMLSelect comboNota = (HTMLSelect) document.getSelectById("comboNotas");
		comboNota.removeAllOptions();

		document.getSelectById("comboNotas").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		for (Enumerados.Nota nota : Enumerados.Nota.values()) {
			comboNota.addOption(nota.getNota().toString(), UtilI18N.internacionaliza(request,nota.getChaveInternacionalizacao()));
		}

		/**
		 * Preenche a combo do Top List
		 * 
		 * @author thyen.chang
		 */
		document.getSelectById("topList").removeAllOptions();
		for (Enumerados.TopListEnum valor : Enumerados.TopListEnum.values()) {
			document.getSelectById("topList").addOption(valor.getValorTopList().toString(), UtilI18N.internacionaliza(request,valor.getNomeTopList()));
	}

	}

	@Override
	public Class getBeanClass() {
		return PesquisaSatisfacaoDTO.class;
	}

	/**
	 * Faz a impressão do relatório no formato pdf.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Thays.araujo
	 */

	public void imprimirRelatorioPesquisaSatisfacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PesquisaSatisfacaoDTO pesquisaSatisfacaoDTO = (PesquisaSatisfacaoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		Collection<PesquisaSatisfacaoDTO> listPesquisa = new ArrayList();
		PesquisaSatisfacaoService pesquisaSatisfacaoService = (PesquisaSatisfacaoService) ServiceLocator.getInstance().getService(PesquisaSatisfacaoService.class, null);
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		listPesquisa = pesquisaSatisfacaoService.relatorioPesquisaSatisfacao(pesquisaSatisfacaoDTO);
		if (listPesquisa != null) {
			for (PesquisaSatisfacaoDTO pesquisa : listPesquisa) {
				for (Enumerados.Nota nota : Enumerados.Nota.values()) {

					if (nota.getNota().toString().equals(pesquisa.getNota().toString())) {
						pesquisa.setValorNota(nota.getDescricao());
					}

				}
				if(pesquisa.getIdSolicitacaoServico() != null){
					PesquisaSatisfacaoDTO pesquisaSatisfacaoAux = new PesquisaSatisfacaoDTO();
					pesquisaSatisfacaoAux = pesquisaSatisfacaoService.findByIdSolicitacaoServico(pesquisa.getIdSolicitacaoServico());
					if(pesquisaSatisfacaoAux != null && pesquisaSatisfacaoAux.getIdResponsavelAtual()!= null && pesquisaSatisfacaoAux.getNomeResponsavelAtual()!= null){
						pesquisa.setIdResponsavelAtual(pesquisaSatisfacaoAux.getIdResponsavelAtual());
						pesquisa.setNomeResponsavelAtual(pesquisaSatisfacaoAux.getNomeResponsavelAtual());
					} else{
						pesquisa.setNomeResponsavelAtual(pesquisa.getOperador());
					}
				}
			}
		}
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		int i = 0;

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaSatisfacao.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatoriopesquisasatisfacao.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("numero", pesquisaSatisfacaoDTO.getIdSolicitacaoServico());
		parametros.put("solicitante", pesquisaSatisfacaoDTO.getNomeSolicitante());
		parametros.put("dataInicio", pesquisaSatisfacaoDTO.getDataInicio());
		parametros.put("dataFim", pesquisaSatisfacaoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		if (pesquisaSatisfacaoDTO.getNota() != null) {
			for (Enumerados.Nota nota : Enumerados.Nota.values()) {
				if (nota.getNota().toString().equals(pesquisaSatisfacaoDTO.getNota().toString())) {
					parametros.put("nota", nota.getDescricao());
				}
			}
		}

		if (pesquisaSatisfacaoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(pesquisaSatisfacaoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}

		if (listPesquisa.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		try {
			
			if (pesquisaSatisfacaoDTO.getTipoRelatorio().equalsIgnoreCase("pdf")) {
				this.gerarRelatorioFormatoPdf(listPesquisa, caminhoJasper, parametros, diretorioReceita, document, diretorioRelativoOS, usuario, strCompl);
			} else {
				parametros.put("tituloSolicitante", UtilI18N.internacionaliza(request, "colaborador.solicitante"));
				this.gerarRelatorioFormatoXls(listPesquisa, parametros, diretorioReceita, document, diretorioRelativoOS, usuario, strCompl);
			}
			
			/*			
 			JRDataSource dataSource = new JRBeanCollectionDataSource(listPesquisa);

			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			// JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioPesquisaSatisfacao" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioPesquisaSatisfacao" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
			*/
			
		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void gerarRelatorioFormatoPdf(Collection<PesquisaSatisfacaoDTO> listPesquisa, String caminhoJasper, Map<String, Object> parametros,
			String diretorioReceita, DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario, String strCompl) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listPesquisa);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioPesquisaSatisfacao" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioPesquisaSatisfacao" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

	}

	public void gerarRelatorioFormatoXls(Collection<PesquisaSatisfacaoDTO> listPesquisa, Map<String, Object> parametros, String diretorioReceita,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario, String strCompl) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listPesquisa);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaSatisfacaoXls.jrxml");

		desenho.setLanguage("java");
		
		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioPesquisaSatisfacaoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioPesquisaSatisfacaoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");

	}
}
