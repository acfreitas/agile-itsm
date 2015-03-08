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
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoRetornoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoRetornoListaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class RelatorioQuantitativoRetorno extends AjaxFormAction {
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
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		//Busca por grupos ATIVOS
		Collection colGrupo = grupoService.listaGruposAtivos();
		document.getSelectById("idGrupoAtual").removeAllOptions();
		document.getSelectById("idGrupoAtual").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		document.getSelectById("idGrupoAtual").addOptions(colGrupo, "idGrupo", "nome", null);
		/**
		 * Preenche combo TOP List
		 * 
		 * @author thyen.chang
		 */
		for(Enumerados.TopListEnum valor : Enumerados.TopListEnum.values())
			document.getSelectById("topList").addOption(valor.getValorTopList().toString(), UtilI18N.internacionaliza(request, valor.getNomeTopList()));
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

	public void imprimirCargaQuantitativoRetornoPDF(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		ContratoDTO contratoDto = new ContratoDTO();
		GrupoDTO grupoDTO = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

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
		
		String FILTRO_FLUXO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.FILTRO_FLUXO_NOME, "").trim();
		Collection<RelatorioQuantitativoRetornoListaDTO> listaPorPeriodo = trazListaRetornos(solicitacaoServicoDTO, WebUtil.getLanguage(request));
		
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoRetorno.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioRetorno.quantidadeSolicitacoesRetorno"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		parametros.put("NOME_FLUXO", UtilI18N.internacionaliza(request, "relatorioRetorno.nomeFluxo")+": ");
		parametros.put("nomeFluxoRetorno", FILTRO_FLUXO);
		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
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
		
		if (listaPorPeriodo.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		try
		{
		
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaPorPeriodo);
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioQuantitativoRetorno" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioQuantitativoRetorno" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
	
	/**
	 * Gera o relatório no formato XLS
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public void imprimirCargaQuantitativoRetornoXLS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		ContratoDTO contratoDto = new ContratoDTO();
		GrupoDTO grupoDTO = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

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
		
		String FILTRO_FLUXO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.FILTRO_FLUXO_NOME, "").trim();
		Collection<RelatorioQuantitativoRetornoListaDTO> listaPorPeriodo = trazListaRetornos(solicitacaoServicoDTO,  WebUtil.getLanguage(request));

		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioRetorno.quantidadeSolicitacoesRetorno"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		parametros.put("NOME_FLUXO", UtilI18N.internacionaliza(request, "relatorioRetorno.nomeFluxo")+": ");
		parametros.put("nomeFluxoRetorno", FILTRO_FLUXO);
		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
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
		
		if (listaPorPeriodo.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		try
		{
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaPorPeriodo);

			JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoRetorno.jrxml");

			JasperReport relatorio = JasperCompileManager.compileReport(desenho);

			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioQuantitativoRetorno" + "_" + usuario.getIdUsuario() + ".xls");

			exporter.exportReport();

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioQuantitativoRetorno" + "_" + usuario.getIdUsuario() + ".xls')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
	
	public Collection<RelatorioQuantitativoRetornoListaDTO> trazListaRetornos(SolicitacaoServicoDTO solicitacaoServicoDTO, String language) throws ServiceException, Exception{
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		String FILTRO_FLUXO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.FILTRO_FLUXO_NOME, "").trim();
		Collection<RelatorioQuantitativoRetornoListaDTO> listaPorPeriodo = new ArrayList<RelatorioQuantitativoRetornoListaDTO>();
		Collection<RelatorioQuantitativoRetornoDTO> dadosRelatorio = new ArrayList<RelatorioQuantitativoRetornoDTO>();
		Collection<RelatorioQuantitativoRetornoDTO> dadosRelatorioNome = new ArrayList<RelatorioQuantitativoRetornoDTO>();
		Collection<RelatorioQuantitativoRetornoDTO> listaServicosRetorno = solicitacaoServicoService.listaServicosRetorno(solicitacaoServicoDTO,FILTRO_FLUXO);
		if (listaServicosRetorno != null) {
			for (RelatorioQuantitativoRetornoDTO relatorio : listaServicosRetorno) {
							
				RelatorioQuantitativoRetornoDTO dadosRetorno = new RelatorioQuantitativoRetornoDTO();
					dadosRetorno = solicitacaoServicoService.servicoRetorno(relatorio);
					if (dadosRetorno != null) {
						relatorio.setIdOcorrencia(dadosRetorno.getIdOcorrencia());
						relatorio.setDataRegistro(dadosRetorno.getDataRegistro());
						relatorio.setHoraRegistro(dadosRetorno.getHoraRegistro());
					for (RelatorioQuantitativoRetornoDTO relatorioNome : solicitacaoServicoService.listaServicosRetornoNomeResponsavel(relatorio)) {
						if (relatorioNome.getNome() != null && !relatorioNome.getNome().equals("")) {
							relatorio.setNome(relatorioNome.getNome().toUpperCase());	
						}
						relatorio.setDataCompleta(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, relatorio.getDataRegistro(), language) +" " +relatorio.getHoraRegistro());
						dadosRelatorio.add(relatorio);
						
						if (solicitacaoServicoDTO.getIdSolicitante() != null && !solicitacaoServicoDTO.getIdSolicitante().equals("")) {
							if (solicitacaoServicoDTO.getSolicitante().equalsIgnoreCase(relatorioNome.getNome())) {
							relatorio.setDataCompleta(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, relatorio.getDataRegistro(), language) +" " +relatorio.getHoraRegistro());
								if (!dadosRelatorioNome.contains(relatorio)) {
									dadosRelatorioNome.add(relatorio);
								}
							}
						}
				}
			}
		}
	}
		Collection<RelatorioQuantitativoRetornoDTO> solicitacaoRetorno = new ArrayList<RelatorioQuantitativoRetornoDTO>();
		String FILTRO_FLUXO_ENCERRAMENTO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.FILTRO_FLUXO_ENCERRAMENTO, "").trim();

		for (RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO : dadosRelatorio) {
			if (solicitacaoServicoService.validaQuantidadeRetorno(relatorioQuantitativoRetornoDTO)) {
				RelatorioQuantitativoRetornoDTO relatorioId = solicitacaoServicoService.retornarIdEncerramento(FILTRO_FLUXO_ENCERRAMENTO, relatorioQuantitativoRetornoDTO);
				if(relatorioId != null){
					if (solicitacaoServicoService.confirmaEncerramento(relatorioQuantitativoRetornoDTO, relatorioId.getIdElemento())) {
						solicitacaoRetorno.add(relatorioQuantitativoRetornoDTO);
					}
				}
			}
		}
		
		if (solicitacaoRetorno != null && !solicitacaoRetorno.isEmpty()) {
			dadosRelatorio.removeAll(solicitacaoRetorno);
		}
		if (solicitacaoServicoDTO.getIdSolicitante() != null && !solicitacaoServicoDTO.getIdSolicitante().equals("")) {
			Collection<RelatorioQuantitativoRetornoDTO> dadosPesquisa = new ArrayList<RelatorioQuantitativoRetornoDTO>();
			for (RelatorioQuantitativoRetornoDTO pesquisaNome : dadosRelatorioNome) {
				for (RelatorioQuantitativoRetornoDTO principal : dadosRelatorio) {
					if (pesquisaNome.getIdSolicitacaoServico().equals(principal.getIdSolicitacaoServico())) {
						dadosPesquisa.add(principal);
					}
				}

			}
			dadosRelatorio = dadosPesquisa;
		}
		
		if (dadosRelatorio != null && !dadosRelatorio.isEmpty()) {
			RelatorioQuantitativoRetornoListaDTO relatorioQuantitativoRetornoListaDTO = new RelatorioQuantitativoRetornoListaDTO();
			relatorioQuantitativoRetornoListaDTO.setListaPorPeriodo(dadosRelatorio);
			listaPorPeriodo.add(relatorioQuantitativoRetornoListaDTO);
		}
		
		return listaPorPeriodo;
	}
}
