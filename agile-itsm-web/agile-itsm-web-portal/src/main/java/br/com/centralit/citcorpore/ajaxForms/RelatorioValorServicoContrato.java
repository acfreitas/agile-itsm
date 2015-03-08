package br.com.centralit.citcorpore.ajaxForms;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.RelatorioValorServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValoresServicoContratoDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.ValoresServicoContratoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class RelatorioValorServicoContrato extends AjaxFormAction {
	UsuarioDTO usuario;

	private  String localeSession = null;
	
	
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		// Preenchendo a combobox de contratos.
		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.list();
		document.getSelectById("idContrato").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos") );
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);
		
		/*
		 * A partir desse ponto, a rotina vai pegar o primeiro contrato (que será o primeiro do combox, já selecionado)
		 * e irá carregar os seus serviços.
		 */
		Integer primeiroIdContrato = null;
		
		for (ContratoDTO contratoDto : (Collection<ContratoDTO>) colContrato) {
			
			primeiroIdContrato = contratoDto.getIdContrato();
			
			break;
		}
		
		document.getSelectById("idServico").removeAllOptions();		
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);		
		document.getSelectById("idServico").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos") );		
		document.getSelectById("idServico").addOptions(this.carregaServicosDoContrato(primeiroIdContrato), "idServico", "nomeServico", null);
	}
	
	/**
	 * Utilizado para retornar um ArrayList dos serviços de um contrato
	 * @param idContrato
	 * @return ArrayList
	 * @throws ServiceException
	 * @throws Exception
	 */
	public ArrayList<ServicoDTO> carregaServicosDoContrato(Integer idContrato) throws ServiceException, Exception {
		
		Collection servicos = new ArrayList<ServicoDTO>();
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		
		if (idContrato != null && servicoContratoService != null) {
			
			Collection<ServicoContratoDTO> servicoContrato = servicoContratoService.findByIdContrato(idContrato);
			
			if (servicoContrato != null) {
				ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
				
				if (servicoService != null) {
					for (ServicoContratoDTO sc : servicoContrato) {
						if (sc.getIdServico() != null) {
							ServicoDTO s = new ServicoDTO();
							s.setIdServico(sc.getIdServico() );
							Collection<ServicoDTO> s1 = new ArrayList();
							s1 = (Collection<ServicoDTO>) servicoService.find(s);
							if(s1 != null){
								s =  s1.iterator().next();
								servicos.add(s);
							}
							
						}
					}
				}				
			}
		}
		
		return (ArrayList<ServicoDTO>) servicos;
	}
	
	public void preencherComboboxServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ValoresServicoContratoDTO valoresServicoContratoDTO = (ValoresServicoContratoDTO) document.getBean();
		Collection servicos = new ArrayList<ServicoDTO>();
		
		if (valoresServicoContratoDTO != null && valoresServicoContratoDTO.getIdContrato() != null) {
			servicos = this.carregaServicosDoContrato(valoresServicoContratoDTO.getIdContrato());
		}

		// Preenchendo a combobox de serviços.
		
		document.getSelectById("idServico").removeAllOptions();		
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);		
		document.getSelectById("idServico").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos") );		
		document.getSelectById("idServico").addOptions(servicos, "idServico", "nomeServico", null);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	@Override
	public Class getBeanClass() {
		return ValoresServicoContratoDTO.class;
	}

	/**
	 * Faz a impressão do relatório no formato pdf.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Flavio.santana
	 */
	public void imprimirRelatorioValorServicoContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		ValoresServicoContratoDTO valoresServicoContratoDTO = (ValoresServicoContratoDTO) document.getBean();		
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		ValoresServicoContratoService valoresServicoContratoService = (ValoresServicoContratoService) ServiceLocator.getInstance().getService(ValoresServicoContratoService.class, null);
		ServicoDTO servicoDto = new ServicoDTO();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		
		usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (valoresServicoContratoDTO.getIdValorServicoContrato() == null) {
			
			if (valoresServicoContratoDTO.getDataInicio() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio") );
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
			
			if (valoresServicoContratoDTO.getDataFim() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim") );
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}

		}
		
		BigDecimal valorTotalGeral = new BigDecimal(0.0);
		BigDecimal valorTotal = new BigDecimal(0.0);
		Integer qtde = new Integer(0);
		
		RelatorioValorServicoContratoDTO relatorioQuantitativoSolicitacaoDto = null;
		
		Collection<RelatorioValorServicoContratoDTO> listDadosRelatorio = new ArrayList<RelatorioValorServicoContratoDTO>();
		
		List<RelatorioValorServicoContratoDTO> list = (List<RelatorioValorServicoContratoDTO>) valoresServicoContratoService.listaValoresServicoContrato(valoresServicoContratoDTO) ;
		
		if (list != null) {					
			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			
			for (RelatorioValorServicoContratoDTO porServico : list) {				
				SolicitacaoServicoDTO sol= null;
				
				sol = solicitacaoServicoService.obterQuantidadeSolicitacoesServico(porServico.getIdServicoContrato(), porServico.getDataInicio(), (porServico.getDataFim() == null ? UtilDatas.getDataAtual() : porServico.getDataFim() ) );
				
				if (sol != null && sol.getQuantidade() > 0) {
					relatorioQuantitativoSolicitacaoDto = new RelatorioValorServicoContratoDTO();
					valorTotal = (porServico.getValorServico().multiply(new BigDecimal(sol.getQuantidade() ) ) );
					valorTotalGeral = valorTotalGeral.add(valorTotal);
					relatorioQuantitativoSolicitacaoDto.setNomeServico(porServico.getNomeServico() );
					relatorioQuantitativoSolicitacaoDto.setQuantidade(sol.getQuantidade() );
					relatorioQuantitativoSolicitacaoDto.setTotalValorServico(valorTotal);
					relatorioQuantitativoSolicitacaoDto.setValorServico(porServico.getValorServico() );
					relatorioQuantitativoSolicitacaoDto.setTotalGeral(valorTotalGeral);
					listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto);
				}
			}
		}
		
		Date dt = new Date();
		
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioValorServicoContrato.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioValorServicoContrato.titulo") );
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade") );
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual() );
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario() );
		parametros.put("dataInicio", valoresServicoContratoDTO.getDataInicio() );
		parametros.put("dataFim", valoresServicoContratoDTO.getDataFim() );
		parametros.put("Logo", LogoRel.getFile());
		
		if(valoresServicoContratoDTO.getIdContrato()!=null){
			contratoDto.setIdContrato(valoresServicoContratoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		}


		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
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
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioValorServicoContrato" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioValorServicoContrato" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		} 
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	/**
	 * Faz a impressão do relatório no formato xls.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Flavio.santana
	 */
	public void imprimirRelatorioValorServicoContratoXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {		
		ValoresServicoContratoDTO valoresServicoContratoDTO = (ValoresServicoContratoDTO) document.getBean();
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		ValoresServicoContratoService valoresServicoContratoService = (ValoresServicoContratoService) ServiceLocator.getInstance().getService(ValoresServicoContratoService.class, null);
		ServicoDTO servicoDto = new ServicoDTO();
		ServicoService ServicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		if (valoresServicoContratoDTO.getIdValorServicoContrato() == null) {
			if (valoresServicoContratoDTO.getDataInicio() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio") );
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
			
			if (valoresServicoContratoDTO.getDataFim() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim") );
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
		}
		
		BigDecimal valorTotalGeral = new BigDecimal(0.0);
		BigDecimal valorTotal = new BigDecimal(0.0);
		Integer qtde = new Integer(0);
		
		RelatorioValorServicoContratoDTO relatorioQuantitativoSolicitacaoDto = null;
		
		Collection<RelatorioValorServicoContratoDTO> listDadosRelatorio = new ArrayList<RelatorioValorServicoContratoDTO>();
		
		List<RelatorioValorServicoContratoDTO> list = (List<RelatorioValorServicoContratoDTO>) valoresServicoContratoService.listaValoresServicoContrato(valoresServicoContratoDTO) ;
		
		if (list != null) {					
			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			
			for (RelatorioValorServicoContratoDTO porServico : list) {				
				SolicitacaoServicoDTO sol= null;
				
				sol = solicitacaoServicoService.obterQuantidadeSolicitacoesServico(porServico.getIdServicoContrato(), 
						porServico.getDataInicio(), (porServico.getDataFim() == null ? UtilDatas.getDataAtual() : porServico.getDataFim() ) );
				
				if (sol != null) {
					if(sol.getQuantidade()!=0){
						relatorioQuantitativoSolicitacaoDto = new RelatorioValorServicoContratoDTO();
						valorTotal = (porServico.getValorServico().multiply(new BigDecimal(sol.getQuantidade() ) ) );
						valorTotalGeral = valorTotalGeral.add(valorTotal);
						relatorioQuantitativoSolicitacaoDto.setNomeServico(porServico.getNomeServico() );
						relatorioQuantitativoSolicitacaoDto.setQuantidade(sol.getQuantidade() );
						relatorioQuantitativoSolicitacaoDto.setTotalValorServico(valorTotal);
						relatorioQuantitativoSolicitacaoDto.setValorServico(porServico.getValorServico() );
						relatorioQuantitativoSolicitacaoDto.setTotalGeral(valorTotalGeral);
						listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto);
					}
					
				}
			}
		}
		
		

		Date dt = new Date();
		
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioValorServicoContratoXls.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioValorServicoContrato.titulo") );
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade") );
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual() );
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario() );
		parametros.put("dataInicio", valoresServicoContratoDTO.getDataInicio() );
		parametros.put("dataFim", valoresServicoContratoDTO.getDataFim() );
		parametros.put("Logo", LogoRel.getFile());
		
		if(valoresServicoContratoDTO.getIdContrato()!=null){
			contratoDto.setIdContrato(valoresServicoContratoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		}


		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);
		
		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioValorServicoContratoXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioValorServicoContratoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
				+ diretorioRelativoOS + "/RelatorioValorServicoContratoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
}
