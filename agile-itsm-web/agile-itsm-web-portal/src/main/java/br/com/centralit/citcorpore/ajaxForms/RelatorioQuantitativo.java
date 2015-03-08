package br.com.centralit.citcorpore.ajaxForms;

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
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unused" })
public class RelatorioQuantitativo extends AjaxFormAction {
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
		Collection colContrato = contratoService.list();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);

		// Preenchendo a combobox de situacoes.
		HTMLSelect comboSituacao = document.getSelectById("situacao");
		comboSituacao.removeAllOptions();
		comboSituacao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.todas"));
		for (SituacaoSolicitacaoServico situacao : SituacaoSolicitacaoServico.values()) {
			comboSituacao.addOption(situacao.name(), UtilI18N.internacionaliza(request, "solicitacaoServico.situacao." + situacao.name()));
		}

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
	 * @author Thays.araujo
	 */
	public void imprimirRelatorioQuantitativo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		ContratoDTO contratoDto = new ContratoDTO();

		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
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
		
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorSolicitante = solicitacaoService.listaQuantidadeSolicitacaoPorSolicitante(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorGrupo = solicitacaoService.listaQuantidadeSolicitacaoPorGrupo(request, solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorItemConfiguracao = solicitacaoService.listaQuantidadeSolicitacaoPorItemConfiguracao(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorSituacao = null;
		boolean filtroContemSituacao = solicitacaoServicoDto.getSituacao() != null && !solicitacaoServicoDto.getSituacao().isEmpty();
		if (!filtroContemSituacao) {
			listPorSituacao = solicitacaoService.listaQuantidadeSolicitacaoPorSituacao(solicitacaoServicoDto);
		}
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorTipo = solicitacaoService.listaQuantidadeSolicitacaoPorTipo(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorOrigem = solicitacaoService.listaQuantidadeSolicitacaoPorOrigem(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorPrioridade = solicitacaoService.listaQuantidadeSolicitacaoPorPrioridade(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorFase = solicitacaoService.listaQuantidadeSolicitacaoPorFase(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorTipoServico = solicitacaoService.listaQuantidadeSolicitacaoPorTipoServico(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorResponsavel = solicitacaoService.listaQuantidadeSolicitacaoPorResponsavel(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorHoraAbertura = solicitacaoService.listaQuantidadeSolicitacaoPorHoraAbertura(solicitacaoServicoDto);
		Collection<SolicitacaoServicoDTO> listPorSituacaoSLA = solicitacaoServicoService.relatorioControleSla(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorPesquisaSatisfacao = solicitacaoService.listaQuantidadeSolicitacaoPorPesquisaSatisfacao(request, solicitacaoServicoDto);

		if (listPorSolicitante != null) {
			listDadosRelatorio.addAll(listPorSolicitante);
			/*
			 * for (RelatorioQuantitativoSolicitacaoDTO porSolicitante : solicitacaoService.listaQuantidadeSolicitacaoPorSolicitante(solicitacaoServicoDto) ) { RelatorioQuantitativoSolicitacaoDTO
			 * relatorioQuantitativoSolictitacaoDto = new RelatorioQuantitativoSolicitacaoDTO(); relatorioQuantitativoSolictitacaoDto.setSolicitante(porSolicitante.getSolicitante() );
			 * relatorioQuantitativoSolictitacaoDto.setQuantidadeSolicitante(porSolicitante.getQuantidadeSolicitante() ); listDadosRelatorio.add(relatorioQuantitativoSolictitacaoDto); }
			 */
		}

		if (listPorGrupo != null) {
			listDadosRelatorio.addAll(listPorGrupo);
			/*
			 * for (RelatorioQuantitativoSolicitacaoDTO porGrupo : solicitacaoService.listaQuantidadeSolicitacaoPorGrupo(solicitacaoServicoDto) ) { RelatorioQuantitativoSolicitacaoDTO
			 * relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO(); relatorioQuantitativoSolicitacaoDto.setGrupo(porGrupo.getGrupo() );
			 * relatorioQuantitativoSolicitacaoDto.setQuantidadeGrupo(porGrupo.getQuantidadeGrupo() ); listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto); }
			 */
		}

		if (listPorItemConfiguracao != null) {
			listDadosRelatorio.addAll(listPorItemConfiguracao);
			/*
			 * for (RelatorioQuantitativoSolicitacaoDTO porItemConfiguracao : solicitacaoService.listaQuantidadeSolicitacaoPorItemConfiguracao(solicitacaoServicoDto) ) {
			 * RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO();
			 * relatorioQuantitativoSolicitacaoDto.setItemConfiguracao(porItemConfiguracao.getItemConfiguracao() );
			 * relatorioQuantitativoSolicitacaoDto.setQuantidadeItemConfiguracao(porItemConfiguracao.getQuantidadeItemConfiguracao() ); listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto); }
			 */
		}

		if (listPorSituacao != null) {
			listDadosRelatorio.addAll(listPorSituacao);
			/*
			 * for (RelatorioQuantitativoSolicitacaoDTO porSituacao : solicitacaoService.listaQuantidadeSolicitacaoPorSituacao(solicitacaoServicoDto) ) { if
			 * (StringUtils.contains(StringUtils.upperCase(porSituacao.getSituacao()), StringUtils.upperCase("EmAndamento") ) ) { porSituacao.setSituacao(UtilI18N.internacionaliza(request,
			 * "citcorpore.comum.emandamento") ); } RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO();
			 * relatorioQuantitativoSolicitacaoDto.setSituacao(porSituacao.getSituacao() ); relatorioQuantitativoSolicitacaoDto.setQuantidadeSituacao(porSituacao.getQuantidadeSituacao() );
			 * listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto); }
			 */
		}

		if (listPorTipo != null) {
			listDadosRelatorio.addAll(listPorTipo);
			/*
			 * for (RelatorioQuantitativoSolicitacaoDTO porTipo : solicitacaoService.listaQuantidadeSolicitacaoPorTipo(solicitacaoServicoDto) ) { RelatorioQuantitativoSolicitacaoDTO
			 * relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO(); relatorioQuantitativoSolicitacaoDto.setTipo(porTipo.getTipo() );
			 * relatorioQuantitativoSolicitacaoDto.setQuantidadeTipo(porTipo.getQuantidadeTipo() ); listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto); }
			 */
		}

		if (listPorOrigem != null) {
			listDadosRelatorio.addAll(listPorOrigem);
			/*
			 * for (RelatorioQuantitativoSolicitacaoDTO porOrigem : solicitacaoService.listaQuantidadeSolicitacaoPorOrigem(solicitacaoServicoDto) ) { RelatorioQuantitativoSolicitacaoDTO
			 * relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO(); relatorioQuantitativoSolicitacaoDto.setOrigem(porOrigem.getOrigem() );
			 * relatorioQuantitativoSolicitacaoDto.setQuantidadeOrigem(porOrigem.getQuantidadeOrigem() ); listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto); }
			 */
		}

		if (listPorPrioridade != null) {
			listDadosRelatorio.addAll(listPorPrioridade);
			/*
			 * for (RelatorioQuantitativoSolicitacaoDTO porPrioridade : solicitacaoService.listaQuantidadeSolicitacaoPorPrioridade(solicitacaoServicoDto) ) { RelatorioQuantitativoSolicitacaoDTO
			 * relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO(); relatorioQuantitativoSolicitacaoDto.setPrioridade(porPrioridade.getPrioridade() );
			 * relatorioQuantitativoSolicitacaoDto.setQuantidadePrioridade(porPrioridade.getQuantidadePrioridade() ); listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto); }
			 */
		}

		if (listPorFase != null) {
			listDadosRelatorio.addAll(listPorFase);
			/*
			 * for (RelatorioQuantitativoSolicitacaoDTO porFase : solicitacaoService.listaQuantidadeSolicitacaoPorFase(solicitacaoServicoDto) ) { RelatorioQuantitativoSolicitacaoDTO
			 * relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO(); relatorioQuantitativoSolicitacaoDto.setFase(porFase.getFase() );
			 * relatorioQuantitativoSolicitacaoDto.setQuantidadeFase(porFase.getQuantidadeFase() ); listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto); }
			 */
		}

		if (listPorTipoServico != null) {
			listDadosRelatorio.addAll(listPorTipoServico);
			/*
			 * for (RelatorioQuantitativoSolicitacaoDTO porTipoServico : solicitacaoService.listaQuantidadeSolicitacaoPorTipoServico(solicitacaoServicoDto) ) { RelatorioQuantitativoSolicitacaoDTO
			 * relatorioQuantitativoSolicitacaoDto = new RelatorioQuantitativoSolicitacaoDTO(); relatorioQuantitativoSolicitacaoDto.setTipoServico(porTipoServico.getTipoServico() );
			 * relatorioQuantitativoSolicitacaoDto.setQuantidadeTipoServico(porTipoServico.getQuantidadeTipoServico() ); listDadosRelatorio.add(relatorioQuantitativoSolicitacaoDto); }
			 */
		}

		if (listPorResponsavel != null) {
			listDadosRelatorio.addAll(listPorResponsavel);
		}

		if (listPorHoraAbertura != null) {
			listDadosRelatorio.addAll(listPorHoraAbertura);
		}

		if (listPorSituacaoSLA != null && !listPorSituacaoSLA.isEmpty() ) {
			int qtdePrazo = 0;
			int qtdeForaPrazo = 0;
			if (listPorSituacaoSLA != null) {
				List<RelatorioQuantitativoSolicitacaoDTO> listSituacao = new ArrayList<RelatorioQuantitativoSolicitacaoDTO>();
				for (SolicitacaoServicoDTO relatorioQuantitativoSolicitacaoDTO2 : listPorSituacaoSLA) {
					
					relatorioQuantitativoSolicitacaoDTO2 = solicitacaoServicoService.verificaSituacaoSLA(relatorioQuantitativoSolicitacaoDTO2);		
					
					if(relatorioQuantitativoSolicitacaoDTO2.getAtrasoSLAStr() != null){
						if ((relatorioQuantitativoSolicitacaoDTO2.getAtrasoSLAStr() != null && relatorioQuantitativoSolicitacaoDTO2.getAtrasoSLAStr().equalsIgnoreCase("S")) || relatorioQuantitativoSolicitacaoDTO2.getAtrasoSLA() > 0) {
							qtdePrazo++;
						} else {
							qtdeForaPrazo++;
						}
					}
				}
				RelatorioQuantitativoSolicitacaoDTO relatorioComAtraso = new RelatorioQuantitativoSolicitacaoDTO();
				relatorioComAtraso.setSituacaoSLA(UtilI18N.internacionaliza(request, "citcorpore.comum.comAtraso"));
				relatorioComAtraso.setQuantidadeSituacaoSLA(qtdePrazo);
				listSituacao.add(relatorioComAtraso);
				RelatorioQuantitativoSolicitacaoDTO relatorioSemAtraso = new RelatorioQuantitativoSolicitacaoDTO();
				relatorioSemAtraso.setSituacaoSLA(UtilI18N.internacionaliza(request, "citcorpore.comum.semAtraso"));
				relatorioSemAtraso.setQuantidadeSituacaoSLA(qtdeForaPrazo);
				listSituacao.add(relatorioSemAtraso);
				listDadosRelatorio.addAll(listSituacao);
			}
		}

		if (listPorPesquisaSatisfacao != null) {
			listDadosRelatorio.addAll(listPorPesquisaSatisfacao);
		}

		Date dt = new Date();

		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativo.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioQuantitativo.relatorioQuantitativo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDto.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDto.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		if (solicitacaoServicoDto.getSituacao() != null && !solicitacaoServicoDto.getSituacao().isEmpty()) {
			parametros.put("situacao", UtilI18N.internacionaliza(request, "solicitacaoServico.situacao." + solicitacaoServicoDto.getSituacao()));
		}
		if (solicitacaoServicoDto.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDto.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		}
		parametros.put("contrato", contratoDto.getNumero());
		if (solicitacaoServicoDto.getTipoUsuario() != null && !solicitacaoServicoDto.getTipoUsuario().isEmpty()) {
			parametros.put("tipoUsuario", UtilI18N.internacionaliza(request, "citcorpore.comum." + solicitacaoServicoDto.getTipoUsuario()));
		}

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

			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioQuantitativoIncidentesSolicitacoes" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioQuantitativoIncidentesSolicitacoes" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		} catch (OutOfMemoryError e) {
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
	 * @author Thays.araujo
	 */
	public void imprimirRelatorioQuantitativoXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
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

		JRDataSource listaPorGrupoDataSource = null;

		JRDataSource listaPorItemConfiguracaoDataSource = null;

		JRDataSource listaPorSituacaoDataSource = null;

		JRDataSource listaPorTipoDataSource = null;

		JRDataSource listaPorOrigemDataSource = null;

		JRDataSource listaPorPrioridadeDataSource = null;

		JRDataSource listaPorFaseDataSource = null;

		JRDataSource listaPorTipoServicoDataSource = null;

		JRDataSource listaPorResponsavelDataSource = null;

		JRDataSource listaPorHoraAberturaDataSource = null;

		JRDataSource listaPorSituacaoSLADataSource = null;

		JRDataSource listaPorPesquisaSatisfacaoDataSource = null;

		Collection<RelatorioQuantitativoSolicitacaoDTO> listDadosRelatorio = new ArrayList<RelatorioQuantitativoSolicitacaoDTO>();
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorGrupo = solicitacaoService.listaQuantidadeSolicitacaoPorGrupo(request, solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorItemConfiguracao = solicitacaoService.listaQuantidadeSolicitacaoPorItemConfiguracao(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorSituacao = solicitacaoService.listaQuantidadeSolicitacaoPorSituacao(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorTipo = solicitacaoService.listaQuantidadeSolicitacaoPorTipo(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorOrigem = solicitacaoService.listaQuantidadeSolicitacaoPorOrigem(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorPrioridade = solicitacaoService.listaQuantidadeSolicitacaoPorPrioridade(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorFase = solicitacaoService.listaQuantidadeSolicitacaoPorFase(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorTipoServico = solicitacaoService.listaQuantidadeSolicitacaoPorTipoServico(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorResponsavel = solicitacaoService.listaQuantidadeSolicitacaoPorResponsavel(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorHoraAbertura = solicitacaoService.listaQuantidadeSolicitacaoPorHoraAbertura(solicitacaoServicoDto);
		Collection<SolicitacaoServicoDTO> listPorSituacaoSLA = solicitacaoService.relatorioControleSla(solicitacaoServicoDto);
		Collection<RelatorioQuantitativoSolicitacaoDTO> listPorPesquisaSatisfacao = solicitacaoService.listaQuantidadeSolicitacaoPorPesquisaSatisfacao(request, solicitacaoServicoDto);

		if (listPorGrupo != null) {
			listaPorGrupoDataSource = new JRBeanCollectionDataSource(listPorGrupo);
		}
		if (listPorItemConfiguracao != null) {
			listaPorItemConfiguracaoDataSource = new JRBeanCollectionDataSource(listPorItemConfiguracao);
		}
		if (listPorSituacao != null) {
			listaPorSituacaoDataSource = new JRBeanCollectionDataSource(listPorSituacao);
		}
		if (listPorTipo != null) {
			listaPorTipoDataSource = new JRBeanCollectionDataSource(listPorTipo);
		}
		if (listPorOrigem != null) {
			listaPorOrigemDataSource = new JRBeanCollectionDataSource(listPorOrigem);
		}
		if (listPorPrioridade != null) {
			listaPorPrioridadeDataSource = new JRBeanCollectionDataSource(listPorPrioridade);
		}
		if (listPorFase != null) {
			listaPorFaseDataSource = new JRBeanCollectionDataSource(listPorFase);
		}
		if (listPorTipoServico != null) {
			listaPorTipoServicoDataSource = new JRBeanCollectionDataSource(listPorTipoServico);
		}
		if (listPorResponsavel != null) {
			listaPorResponsavelDataSource = new JRBeanCollectionDataSource(listPorResponsavel);
		}
		if (listPorHoraAbertura != null) {
			listaPorHoraAberturaDataSource = new JRBeanCollectionDataSource(listPorHoraAbertura);
		}
			if (listPorSituacaoSLA != null) {
				int qtdePrazo = 0;
				int qtdeForaPrazo = 0;
				if (listPorSituacaoSLA != null) {
					List<RelatorioQuantitativoSolicitacaoDTO> listSituacao = new ArrayList<RelatorioQuantitativoSolicitacaoDTO>();
					for (SolicitacaoServicoDTO relatorioQuantitativoSolicitacaoDTO2 : listPorSituacaoSLA) {
						if (relatorioQuantitativoSolicitacaoDTO2.getAtrasoSLAStr().equalsIgnoreCase("S")) {
							qtdePrazo++;
						} else {
							qtdeForaPrazo++;
						}
					}
					RelatorioQuantitativoSolicitacaoDTO relatorioComAtraso = new RelatorioQuantitativoSolicitacaoDTO();
					relatorioComAtraso.setSituacaoSLA(UtilI18N.internacionaliza(request, "citcorpore.comum.comAtraso"));
					relatorioComAtraso.setQuantidadeSituacaoSLA(qtdePrazo);
					listSituacao.add(relatorioComAtraso);
					RelatorioQuantitativoSolicitacaoDTO relatorioSemAtraso = new RelatorioQuantitativoSolicitacaoDTO();
					relatorioSemAtraso.setSituacaoSLA(UtilI18N.internacionaliza(request, "citcorpore.comum.semAtraso"));
					relatorioSemAtraso.setQuantidadeSituacaoSLA(qtdeForaPrazo);
					listSituacao.add(relatorioSemAtraso);
					listaPorSituacaoSLADataSource = new JRBeanCollectionDataSource(listSituacao);
				}
			}
			
			
		if (listPorPesquisaSatisfacao != null) {
			listaPorPesquisaSatisfacaoDataSource = new JRBeanCollectionDataSource(listPorPesquisaSatisfacao);
		}
		if (solicitacaoService.listaQuantidadeSolicitacaoPorSolicitante(solicitacaoServicoDto) != null) {
			for (RelatorioQuantitativoSolicitacaoDTO porSolicitante : solicitacaoService.listaQuantidadeSolicitacaoPorSolicitante(solicitacaoServicoDto)) {
				RelatorioQuantitativoSolicitacaoDTO relatorioQuantitativoSolictitacaoDto = new RelatorioQuantitativoSolicitacaoDTO();
				relatorioQuantitativoSolictitacaoDto.setSolicitante(porSolicitante.getSolicitante());
				relatorioQuantitativoSolictitacaoDto.setQuantidadeSolicitante(porSolicitante.getQuantidadeSolicitante());
				if (listaPorGrupoDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorGrupo(listaPorGrupoDataSource);
				}
				if (listaPorItemConfiguracaoDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorItemConfiguracao(listaPorItemConfiguracaoDataSource);
				}
				if (listaPorSituacaoDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorSituacao(listaPorSituacaoDataSource);
				}
				if (listaPorTipoDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorTipo(listaPorTipoDataSource);
				}
				if (listaPorOrigemDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorOrigem(listaPorOrigemDataSource);
				}
				if (listaPorPrioridadeDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorPrioridade(listaPorPrioridadeDataSource);
				}
				if (listaPorFaseDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorFase(listaPorFaseDataSource);
				}
				if (listaPorTipoServicoDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorTipoServico(listaPorTipoServicoDataSource);
				}
				if (listaPorResponsavelDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorResponsavel(listaPorResponsavelDataSource);
				}
				if (listaPorHoraAberturaDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorHoraAbertura(listaPorHoraAberturaDataSource);
				}
				if (listaPorSituacaoSLADataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorSituacaoSLA(listaPorSituacaoSLADataSource);
				}
				if (listaPorPesquisaSatisfacaoDataSource != null) {
					relatorioQuantitativoSolictitacaoDto.setListaPorPesquisaSatisfacao(listaPorPesquisaSatisfacaoDataSource);
				}

				listDadosRelatorio.add(relatorioQuantitativoSolictitacaoDto);
			}
		}
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativo.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
		String caminhoSubRelatorioJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioQuantitativo.relatorioQuantitativo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", solicitacaoServicoDto.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDto.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		parametros.put("SUBREPORT_DIR", caminhoSubRelatorioJasper);
		if (solicitacaoServicoDto.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDto.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
		}
		parametros.put("contrato", contratoDto.getNumero());
		if (solicitacaoServicoDto.getSituacao() != null && !solicitacaoServicoDto.getSituacao().isEmpty()) {
			parametros.put("situacao", UtilI18N.internacionaliza(request, "solicitacaoServico.situacao." + solicitacaoServicoDto.getSituacao()));
		}
		if (solicitacaoServicoDto.getTipoUsuario() != null && !solicitacaoServicoDto.getTipoUsuario().isEmpty()) {
			parametros.put("tipoUsuario", UtilI18N.internacionaliza(request, "citcorpore.comum." + solicitacaoServicoDto.getTipoUsuario()));
		}

		if (listDadosRelatorio.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}


		JRDataSource dataSource = new JRBeanCollectionDataSource(listDadosRelatorio);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQuantitativoXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioQuantitativoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioQuantitativoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

}
