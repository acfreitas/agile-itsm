package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.AnaliseTendenciasDTO;
import br.com.centralit.citcorpore.bean.CausaIncidenteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TendenciaDTO;
import br.com.centralit.citcorpore.bean.TendenciaGanttDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AnaliseTendenciasService;
import br.com.centralit.citcorpore.negocio.CausaIncidenteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ImpactoService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.UrgenciaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class AnaliseTendencias extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		this.carregarComboContratos(document);

		this.carregarComboTipoDemandaServico(document, request);

		this.carregarComboUrgencia(document, request);

		this.carregarComboImpacto(document, request);

		this.carregarComboCausaIncidente(document, request);

		this.carregarComboGrupoExecutor(document, request);
	}

	/**
	 * Carrega Combo Grupo Executor.
	 *
	 * @param document
	 * @param request
	 * @throws Exception
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 * @since 04.06.2014
	 */
	private void carregarComboGrupoExecutor(DocumentHTML document, HttpServletRequest request) throws Exception, ServiceException {
		document.getSelectById("idGrupoExecutor").removeAllOptions();

		GrupoService grupoServoce = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection<GrupoDTO> listGrupoServiceDesk = grupoServoce.listGruposServiceDesk();

		document.getSelectById("idGrupoExecutor").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");

		document.getSelectById("idGrupoExecutor").addOptions(listGrupoServiceDesk, "idGrupo", "nome", null);
	}

	/**
	 * Carrega Combo Causa.
	 *
	 * @param document
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 04.06.2014
	 */
	private void carregarComboCausaIncidente(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception {

		document.getSelectById("idCausaIncidente").removeAllOptions();

		CausaIncidenteService causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);

		Collection colCausas = causaIncidenteService.listHierarquia();

		HTMLSelect idCausaIncidente = (HTMLSelect) document.getSelectById("idCausaIncidente");

		idCausaIncidente.removeAllOptions();

		idCausaIncidente.addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todas") + " --");

		if (colCausas != null && !colCausas.isEmpty()) {
			idCausaIncidente.addOptions(colCausas, "idCausaIncidente", "descricaoCausaNivel", null);
		}
	}

	/**
	 * Carrega Combo Impacto.
	 *
	 * @param document
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @throws LogicException
	 * @author valdoilo.damasceno
	 * @since 04.06.2014
	 */
	private void carregarComboImpacto(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception, LogicException {

		document.getSelectById("impacto").removeAllOptions();

		ImpactoService impactoService = (ImpactoService) ServiceLocator.getInstance().getService(ImpactoService.class, null);

		Collection<ImpactoDTO> listImpacto = impactoService.list();

		HTMLSelect comboImpacto = document.getSelectById("impacto");

		document.getSelectById("impacto").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");

		comboImpacto.addOptions(listImpacto, "idImpacto", "nivelImpacto", null);
	}

	/**
	 * Carrega Combo Urgência.
	 *
	 * @param document
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 04.06.2014
	 */
	private void carregarComboUrgencia(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception, LogicException {

		document.getSelectById("urgencia").removeAllOptions();

		UrgenciaService urgenciaService = (UrgenciaService) ServiceLocator.getInstance().getService(UrgenciaService.class, null);

		Collection<UrgenciaDTO> listUrgencia = urgenciaService.list();

		HTMLSelect comboUrgencia = document.getSelectById("urgencia");

		document.getSelectById("urgencia").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todas") + " --");

		comboUrgencia.addOptions(listUrgencia, "idUrgencia", "nivelUrgencia", null);
	}

	/**
	 * Carrega Combo Tipo Demanda Serviço.
	 *
	 * @param document
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 04.06.2014
	 */
	private void carregarComboTipoDemandaServico(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception {

		document.getSelectById("idTipoDemandaServico").removeAllOptions();

		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);

		Collection<TipoDemandaServicoDTO> listTipoDemandaServicoDto = tipoDemandaServicoService.listSolicitacoes();

		HTMLSelect comboTipoDemandaServico = document.getSelectById("idTipoDemandaServico");

		document.getSelectById("idTipoDemandaServico").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");

		comboTipoDemandaServico.addOptions(listTipoDemandaServicoDto, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
	}

	/**
	 * Carrega Combo de Contratos.
	 *
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 04.06.2014
	 */
	private void carregarComboContratos(DocumentHTML document) throws ServiceException, Exception {

		document.getSelectById("idContrato").removeAllOptions();

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		Collection<ContratoDTO> listContratoDto = contratoService.listAtivosWithNomeRazaoSocialCliente();

		HTMLSelect comboContrato = document.getSelectById("idContrato");

		comboContrato.addOptions(listContratoDto, "idContrato", "numeroContratoComNomeRazaoSocial", null);
	}

	public void buscarTendencia (DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		AnaliseTendenciasDTO analiseTendenciasDTO = (AnaliseTendenciasDTO) document.getBean();
		AnaliseTendenciasService analiseTendenciasService = (AnaliseTendenciasService) ServiceLocator.getInstance().getService(AnaliseTendenciasService.class, null);

		Collection<TendenciaDTO> colServico = analiseTendenciasService.buscarTendenciasServico(analiseTendenciasDTO);

		HTMLTable tblServicos = document.getTableById("tblServicos");
		tblServicos.deleteAllRows();
		if(colServico != null && colServico.size() >0){
			if (colServico != null && !colServico.isEmpty() && colServico.size() > 0) {
				tblServicos.addRowsByCollection(colServico, new String[] {"id", "descricao", "qtdeCritica",""}, null, "", new String[] { "exibeTendenciaServico" }, null, null);
			}
		}

		Collection<TendenciaDTO> colCausa = analiseTendenciasService.buscarTendenciasCausa(analiseTendenciasDTO);

		HTMLTable tblCausa = document.getTableById("tblCausa");
		tblCausa.deleteAllRows();
		if(colCausa != null && colCausa.size() >0){
			if (colCausa != null && !colCausa.isEmpty() && colCausa.size() > 0) {
				tblCausa.addRowsByCollection(colCausa, new String[] {"id", "descricao", "qtdeCritica",""}, null, "", new String[] { "exibeTendenciaCausa" }, null, null);
			}
		}

		Collection<TendenciaDTO> colItemConfiguracao = analiseTendenciasService.buscarTendenciasItemConfiguracao(analiseTendenciasDTO);

		HTMLTable tblItemConfiguracao = document.getTableById("tblItemConfiguracao");
		tblItemConfiguracao.deleteAllRows();
		if(colItemConfiguracao != null && colItemConfiguracao.size() >0){
			if (colItemConfiguracao != null && !colItemConfiguracao.isEmpty() && colItemConfiguracao.size() > 0) {
				tblItemConfiguracao.addRowsByCollection(colItemConfiguracao, new String[] {"id", "descricao", "qtdeCritica",""}, null, "", new String[] { "exibeTendenciaItemConfig" }, null, null);
			}
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
		document.executeScript("showResult();");
	}

	public void gerarRelatorio (DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AnaliseTendenciasDTO analiseTendenciasDTO = (AnaliseTendenciasDTO) document.getBean();
		List<TendenciaGanttDTO> listTendenciasDto = new ArrayList<TendenciaGanttDTO>();

		String relatorioTitulo = "";
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		AnaliseTendenciasService analiseTendenciaService = (AnaliseTendenciasService) ServiceLocator.getInstance().getService(AnaliseTendenciasService.class, null);

		if (analiseTendenciasDTO.getTipoRelatorio().equalsIgnoreCase("servico")) {
			listTendenciasDto = analiseTendenciaService.listarGraficoGanttServico(analiseTendenciasDTO, analiseTendenciasDTO.getIdRelatorio());

			ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
			ServicoDTO servicoDto = servicoService.findById(analiseTendenciasDTO.getIdRelatorio());

			if (servicoDto != null) {
				relatorioTitulo = UtilI18N.internacionaliza(request, "citcorpore.comum.servico") + ": " + servicoDto.getNomeServico();
			}
		} else if (analiseTendenciasDTO.getTipoRelatorio().equalsIgnoreCase("causa")) {
			listTendenciasDto = analiseTendenciaService.listarGraficoGanttCausa(analiseTendenciasDTO, analiseTendenciasDTO.getIdRelatorio());

			CausaIncidenteService causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);
			CausaIncidenteDTO causaIncidenteDto = new CausaIncidenteDTO();
			causaIncidenteDto.setIdCausaIncidente(analiseTendenciasDTO.getIdRelatorio());
			causaIncidenteDto = (CausaIncidenteDTO) causaIncidenteService.restore(causaIncidenteDto);

			if (causaIncidenteDto != null) {
				relatorioTitulo = UtilI18N.internacionaliza(request, "problema.causa") + ": " + causaIncidenteDto.getDescricaoCausa();
			}
		} else if (analiseTendenciasDTO.getTipoRelatorio().equalsIgnoreCase("itemConfiguracao")) {
			listTendenciasDto = analiseTendenciaService.listarGraficoGanttItemConfiguracao(analiseTendenciasDTO, analiseTendenciasDTO.getIdRelatorio());

			ItemConfiguracaoService causaIncidenteService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			ItemConfiguracaoDTO itemConfiguracaoDto = causaIncidenteService.restoreByIdItemConfiguracao(analiseTendenciasDTO.getIdRelatorio());

			if (itemConfiguracaoDto != null) {
				relatorioTitulo = UtilI18N.internacionaliza(request, "itemConfiguracao.itemConfiguracao") + ": " + itemConfiguracaoDto.getIdentificacao();
			}
		}

		if (listTendenciasDto != null && !listTendenciasDto.isEmpty()) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			for (TendenciaGanttDTO tendencia : listTendenciasDto) {
				if (tendencia != null) {
					String data = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, tendencia.getData(), WebUtil.getLanguage(request));
					dataset.addValue(tendencia.getQtde(), relatorioTitulo, data);
				}
			}

			JFreeChart chart = ChartFactory.createBarChart(
					"",         																					// chart title
					UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.data"),               			// domain axis label
					UtilI18N.internacionaliza(request, "visao.quantidade"),                  						// range axis label
		            dataset,                  																		// dataset
		            PlotOrientation.VERTICAL, 																		// orientation
		            false,                     																		// include legend
		            true,                     																		// tooltips?
		            false                     																		// URLs?
		    );

			CategoryPlot plot = chart.getCategoryPlot();
			CategoryAxis axis = (CategoryAxis)plot.getDomainAxis();
			axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

			File file = null;
			try {
				String diretorioRelativo = CITCorporeUtil.CAMINHO_REAL_APP + "tempFiles" + "/ChartAnaliseTendencias.png";
				file = new File(diretorioRelativo);
				ChartUtilities.saveChartAsPNG(file, chart, 1200, 650);
			} catch (IOException e) {
				System.err.println("Problem occurred creating chart.");
			}

			Map<String, Object> parametros = new HashMap<String, Object>();

			HttpSession session = request.getSession(true);
			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "problema.analiseTendencias.titulo"));
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			parametros.put("Logo", LogoRel.getFile());
			parametros.put("TipoRelatorio", relatorioTitulo);

			Date dt = new Date();
			String strCompl = "" + dt.getTime();

			parametros.put("ChartAnaliseTendencias", file);

			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioAnaliseTendencias.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros);
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioAnaliseTendencias" + strCompl + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioAnaliseTendencias" + strCompl + ".pdf')");
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			return;
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	@Override
	public Class getBeanClass() {
		return AnaliseTendenciasDTO.class;
	}

}
