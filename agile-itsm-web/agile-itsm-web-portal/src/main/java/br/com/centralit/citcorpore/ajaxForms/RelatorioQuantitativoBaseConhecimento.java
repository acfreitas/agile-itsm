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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class RelatorioQuantitativoBaseConhecimento extends AjaxFormAction {
	
	private UsuarioDTO usuario;
	private String localeSession;
	private BaseConhecimentoService baseConhecimentoService;
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		setUsuario(WebUtil.getUsuario(request));
		
		if (getUsuario() == null) {
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
		
	}
	
	@Override
	public Class getBeanClass() {
		return BaseConhecimentoDTO.class;
	}
	
	public void imprimirRelatorioQuantitativoBaseConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		BaseConhecimentoDTO baseConhecimentoDTO = (BaseConhecimentoDTO) document.getBean();
		
		setUsuario(WebUtil.getUsuario(request));
		
		if (getUsuario() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		Integer totalConhecimentoCriado = 0;
		Integer totalConhecimentoAprovado = 0;
		
		Collection<RelatorioQuantitativoBaseConhecimentoDTO> listaDadosRelatorio = new ArrayList<RelatorioQuantitativoBaseConhecimentoDTO>();
		Collection<BaseConhecimentoDTO> listaQuantidadeBaseConhecimento =  getBaseConhecimentoService().quantidadeBaseConhecimentoPorPeriodo(baseConhecimentoDTO);
		Collection<ComentariosDTO> listaComentarios = getBaseConhecimentoService().consultaConhecimentosAvaliados(baseConhecimentoDTO);
		Collection<BaseConhecimentoDTO> listaAutores = getBaseConhecimentoService().consultaConhecimentosPorAutores(baseConhecimentoDTO);
		Collection<BaseConhecimentoDTO> listaAprovadores = getBaseConhecimentoService().consultaConhecimentosPorAprovadores(baseConhecimentoDTO);
		Collection<BaseConhecimentoDTO> listaPublicadosPorOrigem = getBaseConhecimentoService().consultaConhecimentosPublicadosPorOrigem(baseConhecimentoDTO);
		Collection<BaseConhecimentoDTO> listaNaoPublicadosPorOrigem = getBaseConhecimentoService().consultaConhecimentosNaoPublicadosPorOrigem(baseConhecimentoDTO);
		
		RelatorioQuantitativoBaseConhecimentoDTO relatorioQuantitativoBaseConhecimentoDTO = new RelatorioQuantitativoBaseConhecimentoDTO();
		
		if (listaQuantidadeBaseConhecimento != null) {
			relatorioQuantitativoBaseConhecimentoDTO.setListaBaseConhecimento(listaQuantidadeBaseConhecimento);
		}
		
		if(listaComentarios != null){
			relatorioQuantitativoBaseConhecimentoDTO.setListaComentarios(listaComentarios);
		}
		
		if(listaAutores != null){
			relatorioQuantitativoBaseConhecimentoDTO.setListaAutores(listaAutores);
			for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaAutores) {
				totalConhecimentoCriado += baseConhecimentoDTO2.getQtdConhecimentoPorUsuario();
			}
		}
		
		if(listaAprovadores != null){
			relatorioQuantitativoBaseConhecimentoDTO.setListaAprovadores(listaAprovadores);
			for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaAprovadores) {
				totalConhecimentoAprovado += baseConhecimentoDTO2.getQtdConhecimentoPorAprovador();
			}
		}
		
		if(listaPublicadosPorOrigem != null){
			for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaPublicadosPorOrigem) {
				if(baseConhecimentoDTO2.getOrigem() != null && !baseConhecimentoDTO2.getOrigem().equalsIgnoreCase("")){
					String nomeOrigem = Enumerados.OrigemBaseConhecimento.getDescOrigemByOrigem(Integer.parseInt(baseConhecimentoDTO2.getOrigem()));
					baseConhecimentoDTO2.setNomeOrigem(nomeOrigem);
				}else{
					baseConhecimentoDTO2.setNomeOrigem("-");
				}
			}
			relatorioQuantitativoBaseConhecimentoDTO.setListaPublicadosPorOrigem(listaPublicadosPorOrigem);
		}
		
		if(listaNaoPublicadosPorOrigem != null){
			for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaNaoPublicadosPorOrigem) {
				if(baseConhecimentoDTO2.getOrigem() != null && !baseConhecimentoDTO2.getOrigem().trim().equalsIgnoreCase("")){
					String nomeOrigem = Enumerados.OrigemBaseConhecimento.getDescOrigemByOrigem(Integer.parseInt(baseConhecimentoDTO2.getOrigem()));
					baseConhecimentoDTO2.setNomeOrigem(nomeOrigem);
				}else{
					baseConhecimentoDTO2.setNomeOrigem("-");
				}
			}
			relatorioQuantitativoBaseConhecimentoDTO.setListaNaoPublicadosPorOrigem(listaNaoPublicadosPorOrigem);
		}
		
		if(relatorioQuantitativoBaseConhecimentoDTO != null){
			listaDadosRelatorio.add(relatorioQuantitativoBaseConhecimentoDTO);
		}
		
		Date dt = new Date();
		
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoBaseConhecimento.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO",UtilI18N.internacionaliza(request, "relatorioQuantitativoBaseConhecimento.relatorioQuantitativoBaseConhecimento") );
		parametros.put("CIDADE",UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade") );
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual() );
		parametros.put("NOME_USUARIO", getUsuario().getNomeUsuario() );
		parametros.put("dataInicio", baseConhecimentoDTO.getDataInicio() );
		parametros.put("dataFim", baseConhecimentoDTO.getDataFim() );
		parametros.put("TOTAL_CONHECIMENTO_CRIADO", totalConhecimentoCriado);
		parametros.put("TOTAL_CONHECIMENTO_APROVADO", totalConhecimentoAprovado);
		parametros.put("Logo", LogoRel.getFile());
		
		if (listaDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		try{
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaDadosRelatorio);
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioQuantitativoBaseConhecimento" + strCompl + "_" + getUsuario().getIdUsuario() + ".pdf");
			
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioQuantitativoBaseConhecimento" + strCompl + "_" + getUsuario().getIdUsuario() + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		
	}
	
	public void imprimirRelatorioQuantitativoBaseConhecimentoXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		BaseConhecimentoDTO baseConhecimentoDTO = (BaseConhecimentoDTO) document.getBean();
		
		setUsuario(WebUtil.getUsuario(request));
		
		if (getUsuario() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		Integer totalConhecimentoCriado = 0;
		Integer totalConhecimentoAprovado = 0;
		
		Collection<RelatorioQuantitativoBaseConhecimentoDTO> listaDadosRelatorio = new ArrayList<RelatorioQuantitativoBaseConhecimentoDTO>();
		Collection<BaseConhecimentoDTO> listaQuantidadeBaseConhecimento =  getBaseConhecimentoService().quantidadeBaseConhecimentoPorPeriodo(baseConhecimentoDTO);
		Collection<ComentariosDTO> listaComentarios = getBaseConhecimentoService().consultaConhecimentosAvaliados(baseConhecimentoDTO);
		Collection<BaseConhecimentoDTO> listaAutores = getBaseConhecimentoService().consultaConhecimentosPorAutores(baseConhecimentoDTO);
		Collection<BaseConhecimentoDTO> listaAprovadores = getBaseConhecimentoService().consultaConhecimentosPorAprovadores(baseConhecimentoDTO);
		Collection<BaseConhecimentoDTO> listaPublicadosPorOrigem = getBaseConhecimentoService().consultaConhecimentosPublicadosPorOrigem(baseConhecimentoDTO);
		Collection<BaseConhecimentoDTO> listaNaoPublicadosPorOrigem = getBaseConhecimentoService().consultaConhecimentosNaoPublicadosPorOrigem(baseConhecimentoDTO);
		
		RelatorioQuantitativoBaseConhecimentoDTO relatorioQuantitativoBaseConhecimentoDTO = new RelatorioQuantitativoBaseConhecimentoDTO();
		
		if (listaQuantidadeBaseConhecimento != null) {
			relatorioQuantitativoBaseConhecimentoDTO.setListaBaseConhecimento(listaQuantidadeBaseConhecimento);
		}
		
		if(listaComentarios != null){
			relatorioQuantitativoBaseConhecimentoDTO.setListaComentarios(listaComentarios);
		}
		
		if(listaAutores != null){
			relatorioQuantitativoBaseConhecimentoDTO.setListaAutores(listaAutores);
			for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaAutores) {
				totalConhecimentoCriado += baseConhecimentoDTO2.getQtdConhecimentoPorUsuario();
			}
		}
		
		if(listaAprovadores != null){
			relatorioQuantitativoBaseConhecimentoDTO.setListaAprovadores(listaAprovadores);
			for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaAprovadores) {
				totalConhecimentoAprovado += baseConhecimentoDTO2.getQtdConhecimentoPorAprovador();
			}
		}
		
		if(listaPublicadosPorOrigem != null){
			for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaPublicadosPorOrigem) {
				if(baseConhecimentoDTO2.getOrigem() != null && !baseConhecimentoDTO2.getOrigem().equalsIgnoreCase("")){
					String nomeOrigem = Enumerados.OrigemBaseConhecimento.getDescOrigemByOrigem(Integer.parseInt(baseConhecimentoDTO2.getOrigem()));
					baseConhecimentoDTO2.setNomeOrigem(nomeOrigem);
				}else{
					baseConhecimentoDTO2.setNomeOrigem("-");
				}
			}
			relatorioQuantitativoBaseConhecimentoDTO.setListaPublicadosPorOrigem(listaPublicadosPorOrigem);
		}
		
		if(listaNaoPublicadosPorOrigem != null){
			for (BaseConhecimentoDTO baseConhecimentoDTO2 : listaNaoPublicadosPorOrigem) {
				if(baseConhecimentoDTO2.getOrigem() != null && !baseConhecimentoDTO2.getOrigem().equalsIgnoreCase("")){
					String nomeOrigem = Enumerados.OrigemBaseConhecimento.getDescOrigemByOrigem(Integer.parseInt(baseConhecimentoDTO2.getOrigem()));
					baseConhecimentoDTO2.setNomeOrigem(nomeOrigem);
				}else{
					baseConhecimentoDTO2.setNomeOrigem("-");
				}
			}
			relatorioQuantitativoBaseConhecimentoDTO.setListaNaoPublicadosPorOrigem(listaNaoPublicadosPorOrigem);
		}
		
		if(relatorioQuantitativoBaseConhecimentoDTO != null){
			listaDadosRelatorio.add(relatorioQuantitativoBaseConhecimentoDTO);
		}
		
		Date dt = new Date();
		
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoBaseConhecimentoXls.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO",UtilI18N.internacionaliza(request, "relatorioQuantitativoBaseConhecimento.relatorioQuantitativoBaseConhecimento") );
		parametros.put("CIDADE",UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade") );
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual() );
		parametros.put("NOME_USUARIO", getUsuario().getNomeUsuario() );
		parametros.put("dataInicio", baseConhecimentoDTO.getDataInicio() );
		parametros.put("dataFim", baseConhecimentoDTO.getDataFim() );
		parametros.put("TOTAL_CONHECIMENTO_CRIADO", totalConhecimentoCriado);
		parametros.put("TOTAL_CONHECIMENTO_APROVADO", totalConhecimentoAprovado);
		parametros.put("Logo", LogoRel.getFile());
		
		if (listaDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		try{
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaDadosRelatorio);
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioQuantitativoBaseConhecimentoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");
			
			exporter.exportReport();
			
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioQuantitativoBaseConhecimentoXls" + strCompl + "_" + getUsuario().getIdUsuario() + ".xls')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		
	}
	
	public String getLocaleSession() {
		return localeSession;
	}

	public void setLocaleSession(String localeSession) {
		this.localeSession = localeSession;
	}

	public BaseConhecimentoService getBaseConhecimentoService() throws ServiceException, Exception {
		return (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

}
