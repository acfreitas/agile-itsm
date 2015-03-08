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
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.HistoricoSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings({ "rawtypes" })
public class RelatorioQuantitativoEncaminhada extends AjaxFormAction {
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

	public void imprimirCargaQuantitativoEncaminhadoPDF(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		HistoricoSolicitacaoServicoService historicoSolicitacaoServicoService = (HistoricoSolicitacaoServicoService) ServiceLocator.getInstance().getService(HistoricoSolicitacaoServicoService.class, null);
		OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
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
		Collection<SolicitacaoServicoDTO> listDadosRelatorio = new ArrayList<SolicitacaoServicoDTO>();
		Collection<SolicitacaoServicoDTO> solicitacaoServicoRel = historicoSolicitacaoServicoService.imprimirSolicitacaoEncaminhada(solicitacaoServicoDTO);
		if (solicitacaoServicoRel != null) {
			Integer  i = 0;
			for (SolicitacaoServicoDTO relatorio : solicitacaoServicoRel) {
					i++;
					relatorio.setIdServico(i);
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("EmAndamento")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Cancelada")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Fechada")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Fechada").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Reaberta")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Reaberta").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Reclassificada")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Reclassificada").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Resolvida")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Resolvida").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Suspensa")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Registrada/Em andamento")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacaoRegistrada").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Resolvida")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacaoResolvida").toUpperCase());
					}	
					relatorio.setListaSolicitacaoServico(historicoSolicitacaoServicoService.imprimirSolicitacaoEncaminhadaFilhas(relatorio));
					for (SolicitacaoServicoDTO solicitacaoServicoDTO2 : relatorio.getListaSolicitacaoServico()) {
						OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoAux = new OcorrenciaSolicitacaoDTO();
						ocorrenciaSolicitacaoAux = ocorrenciaSolicitacaoService.findByIdOcorrencia(solicitacaoServicoDTO2.getIdOcorrencia());
						String dadosSolicitacao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getDadosSolicitacao() );
						
						SolicitacaoServicoDTO solicitacaoDto = null;
						if (dadosSolicitacao.length() > 0) {					
							try {
								solicitacaoDto = new Gson().fromJson(dadosSolicitacao,SolicitacaoServicoDTO.class);
								
								if (solicitacaoDto != null)
									dadosSolicitacao = solicitacaoDto.getSituacaoFinal();
							} catch (Exception e) {
								dadosSolicitacao = "";
							}
						}
						if (dadosSolicitacao != null) {
							solicitacaoServicoDTO2.setSituacaoAtual(dadosSolicitacao.toUpperCase());
						}else{
							solicitacaoServicoDTO2.setSituacaoAtual(dadosSolicitacao);
						}
						
						solicitacaoServicoDTO2.setDataCompleta(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, solicitacaoServicoDTO2.getDataRegistro(), WebUtil.getLanguage(request)) +" " +solicitacaoServicoDTO2.getHoraRegistro());
					}
					listDadosRelatorio.add(relatorio);
				}
		}
		 
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoEncaminhada.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioExecucaoSolicitacao.titulo.execucaoSolicitacao"));
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
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioQuantitativoEncaminhada" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativoOS + "/RelatorioQuantitativoEncaminhada" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();


	}
	
	public void imprimirCargaQuantitativoEncaminhadoXLS(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		HistoricoSolicitacaoServicoService historicoSolicitacaoServicoService = (HistoricoSolicitacaoServicoService) ServiceLocator.getInstance().getService(HistoricoSolicitacaoServicoService.class, null);
		OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
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
		Collection<SolicitacaoServicoDTO> listDadosRelatorio = new ArrayList<SolicitacaoServicoDTO>();
		Collection<SolicitacaoServicoDTO> solicitacaoServicoRel = historicoSolicitacaoServicoService.imprimirSolicitacaoEncaminhada(solicitacaoServicoDTO);
		if (solicitacaoServicoRel != null) {
			Integer  i = 0;
			for (SolicitacaoServicoDTO relatorio : solicitacaoServicoRel) {
					i++;
					relatorio.setIdServico(i);
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("EmAndamento")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Cancelada")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Fechada")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Fechada").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Reaberta")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Reaberta").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Reclassificada")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Reclassificada").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Resolvida")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Resolvida").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Suspensa")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Registrada/Em andamento")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacaoRegistrada").toUpperCase());
					}
					if (relatorio.getSituacao() != null && relatorio.getSituacao().equalsIgnoreCase("Resolvida")) {
						relatorio.setSituacao(UtilI18N.internacionaliza(request, "solicitacaoServico.situacaoResolvida").toUpperCase());
					}	
					relatorio.setListaSolicitacaoServico(historicoSolicitacaoServicoService.imprimirSolicitacaoEncaminhadaFilhas(relatorio));
					for (SolicitacaoServicoDTO solicitacaoServicoDTO2 : relatorio.getListaSolicitacaoServico()) {
						OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoAux = new OcorrenciaSolicitacaoDTO();
						ocorrenciaSolicitacaoAux = ocorrenciaSolicitacaoService.findByIdOcorrencia(solicitacaoServicoDTO2.getIdOcorrencia());
						String dadosSolicitacao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getDadosSolicitacao() );
						
						SolicitacaoServicoDTO solicitacaoDto = null;
						if (dadosSolicitacao.length() > 0) {					
							try {
								solicitacaoDto = new Gson().fromJson(dadosSolicitacao,SolicitacaoServicoDTO.class);
								
								if (solicitacaoDto != null)
									dadosSolicitacao = solicitacaoDto.getSituacaoFinal();
							} catch (Exception e) {
								dadosSolicitacao = "";
							}
						}
						if (dadosSolicitacao != null) {
							solicitacaoServicoDTO2.setSituacaoAtual(dadosSolicitacao.toUpperCase());
						}else{
							solicitacaoServicoDTO2.setSituacaoAtual(dadosSolicitacao);
						}
						
						solicitacaoServicoDTO2.setDataCompleta(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, solicitacaoServicoDTO2.getDataRegistro(), WebUtil.getLanguage(request)) +" " +solicitacaoServicoDTO2.getHoraRegistro());
					}
					listDadosRelatorio.add(relatorio);
				}
		}

		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioExecucaoSolicitacao.titulo.execucaoSolicitacao"));
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

			JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoEncaminhada.jrxml");

			JasperReport relatorio = JasperCompileManager.compileReport(desenho);

			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);
			Date dt = new Date();
			String strCompl = "" + dt.getTime();
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioQuantitativoEncaminhada" + "_" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

			exporter.exportReport();

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioQuantitativoEncaminhada" + "_" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();


	}

	public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		//Busca por ID do contrato por grupos ATIVOS
		
		if (solicitacaoServicoDTO.getIdContrato()!=null) {
			
		Collection colGrupo = grupoService.listGrupoAtivosByIdContrato(solicitacaoServicoDTO.getIdContrato().intValue());
		document.getSelectById("idGrupoAtual").removeAllOptions();
		document.getSelectById("idGrupoAtual").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		document.getSelectById("idGrupoAtual").addOptions(colGrupo, "idGrupo", "nome", null);
		} else {
			document.getSelectById("idGrupoAtual").removeAllOptions();
			document.getSelectById("idGrupoAtual").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
	}
		
}
}
