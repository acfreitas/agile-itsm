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
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
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

@SuppressWarnings({ "rawtypes", "unused" })
public class RelatorioServicoAprovar extends AjaxFormAction {
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

		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.list();
		document.getSelectById("idContrato").addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.todos") );
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);
		
		document.getSelectById("situacaoAprovacao").removeAllOptions();
		document.getSelectById("situacaoAprovacao").addOption("1", UtilI18N.internacionaliza(request, "citcorpore.comum.aprovados"));
		document.getSelectById("situacaoAprovacao").addOption("2",UtilI18N.internacionaliza(request, "citcorpore.comum.naoAprovados"));
		
		for(Enumerados.TopListEnum valor : Enumerados.TopListEnum.values())
			document.getSelectById("topList").addOption(valor.getValorTopList().toString(), UtilI18N.internacionaliza(request, valor.getNomeTopList()));
		
	}

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}
	
	/**
	 * Trata a internacionalização de acordo com a situação
	 * Foi verificado que existem todos as situações internacionalizadas em citcorpore.comum.{situacao}
	 * sendo (situacao} em minusculo
	 * @param request
	 * @param situacaoKey
	 * @return String
	 * @author uelen.pereira
	 */
	private String getSituacaoInternacionalizada(HttpServletRequest request, String situacaoKey) {
		
		return UtilI18N.internacionaliza(request, "citcorpore.comum." + situacaoKey.toLowerCase());
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
	public void imprimirRelatorioServicoAprovar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() == null) {
			if (solicitacaoServicoDto.getDataInicio() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
			if (solicitacaoServicoDto.getDataFim() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}

		}

		Collection<RelatorioQuantitativoSolicitacaoDTO> listDadosRelatorio = new ArrayList<RelatorioQuantitativoSolicitacaoDTO>();
		Collection<RelatorioQuantitativoSolicitacaoDTO> listDadosRelatorioTemp = solicitacaoService.listaServicosAbertosAprovados(solicitacaoServicoDto);
		
		if (listDadosRelatorioTemp != null) {

			Integer  i = 0;
			
			for (RelatorioQuantitativoSolicitacaoDTO porServico : listDadosRelatorioTemp) {
				
				i++;
				
				RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO();

				relatorioQuantitativoSolicitacaoDto.setIdServico(i);
				relatorioQuantitativoSolicitacaoDto.setIdSolicitacaoServico(porServico.getIdSolicitacaoServico());
				relatorioQuantitativoSolicitacaoDto.setNome(porServico.getNome().toUpperCase());
				relatorioQuantitativoSolicitacaoDto.setDataHoraSolicitacao(porServico.getDataHoraSolicitacao());
				relatorioQuantitativoSolicitacaoDto.setNomeServico(porServico.getNomeServico().toUpperCase());
				
				if (solicitacaoServicoDto.getIdContrato() == null ) {
					relatorioQuantitativoSolicitacaoDto.setNumero(UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
				} else{
					relatorioQuantitativoSolicitacaoDto.setNumero(porServico.getNumero());	
				}
				
				relatorioQuantitativoSolicitacaoDto.setSituacao(this.getSituacaoInternacionalizada(request, porServico.getSituacao()).toUpperCase());	
				
				listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto);
			}
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioServicoAprovar.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		if (solicitacaoServicoDto.getSituacaoAprovacao().equals("1")) {
			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioServicoAprovar.titulo.aprovados"));
		}
		if (solicitacaoServicoDto.getSituacaoAprovacao().equals("2")) {
			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioServicoAprovar.titulo.naoAprovados"));
		}
		
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDto.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDto.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		
		if (solicitacaoServicoDto.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDto.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
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
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioServicoAprovar" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioServicoAprovar" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
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
	public void imprimirRelatorioServicoAprovarXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() == null) {
			if (solicitacaoServicoDto.getDataInicio() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
			if (solicitacaoServicoDto.getDataFim() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}

		}

		Collection<RelatorioQuantitativoSolicitacaoDTO> listDadosRelatorio = new ArrayList<RelatorioQuantitativoSolicitacaoDTO>();
		Collection<RelatorioQuantitativoSolicitacaoDTO> listDadosRelatorioTemp = solicitacaoService.listaServicosAbertosAprovados(solicitacaoServicoDto);
		
		if (listDadosRelatorioTemp != null) {
			
			Integer  i = 0;
			
			for (RelatorioQuantitativoSolicitacaoDTO porServico : listDadosRelatorioTemp) {
				
				i++;
				
				RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO();
				relatorioQuantitativoSolicitacaoDto.setIdServico(i);
				relatorioQuantitativoSolicitacaoDto.setIdSolicitacaoServico(porServico.getIdSolicitacaoServico());
				relatorioQuantitativoSolicitacaoDto.setNome(porServico.getNome().toUpperCase());
				relatorioQuantitativoSolicitacaoDto.setDataHoraSolicitacao(porServico.getDataHoraSolicitacao());
				relatorioQuantitativoSolicitacaoDto.setNomeServico(porServico.getNomeServico().toUpperCase());
				
				if (solicitacaoServicoDto.getIdContrato() == null ) {
					relatorioQuantitativoSolicitacaoDto.setNumero("Todos");
				} else {
					relatorioQuantitativoSolicitacaoDto.setNumero(porServico.getNumero());	
				}
				
				relatorioQuantitativoSolicitacaoDto.setSituacao(this.getSituacaoInternacionalizada(request, porServico.getSituacao()).toUpperCase());
				
				listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto);
			}
		}
		
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioServicoAprovar.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		if (solicitacaoServicoDto.getSituacaoAprovacao().equals("1")) {
			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioServicoAprovar.titulo.aprovados"));
		}
		if (solicitacaoServicoDto.getSituacaoAprovacao().equals("2")) {
			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioServicoAprovar.titulo.naoAprovados"));
		}
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDto.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDto.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		if (solicitacaoServicoDto.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDto.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}
		
		
		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);
		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioServicoAprovarXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioServicoAprovarXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
				+ diretorioRelativoOS + "/RelatorioServicoAprovarXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

}
