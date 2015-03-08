package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
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
import br.com.centralit.citcorpore.bean.RelatorioIncidentesNaoResolvidosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class RelatorioIncidentesNaoResolvidos extends AjaxFormAction {

	private UsuarioDTO usuario;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		this.preencherComboContrato(document, request, response);
		carregarComboTipoDemanda(document, request);
	}

	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		usuario = WebUtil.getUsuario(request);
		RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDto = (RelatorioIncidentesNaoResolvidosDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();

		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		
		calculaPeriododeReferencia(relatorioIncidentesNaoResolvidosDto);
		
		Collection<SolicitacaoServicoDTO> listaSolicitacoesNaoResolvidas = solicitacaoService.findSolicitacoesNaoResolvidasNoPrazoKPI(relatorioIncidentesNaoResolvidosDto);
		
		Collection<RelatorioIncidentesNaoResolvidosDTO> listaParaEnvio = new ArrayList<RelatorioIncidentesNaoResolvidosDTO>();

		calculaQtdDiasAtrasados(listaSolicitacoesNaoResolvidas,relatorioIncidentesNaoResolvidosDto);
		
		/*preenche a lista para envio*/
		if(listaSolicitacoesNaoResolvidas != null && !listaSolicitacoesNaoResolvidas.isEmpty()){
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaSolicitacoesNaoResolvidas) {
				RelatorioIncidentesNaoResolvidosDTO novo = new RelatorioIncidentesNaoResolvidosDTO();
				novo.setNumeroSolicitacao(solicitacaoServicoDTO.getIdSolicitacaoServico());
				novo.setNomeservico(solicitacaoServicoDTO.getNomeServico());
				novo.setTipoServico(solicitacaoServicoDTO.getNomeTipoDemandaServico());
				novo.setResponsavel(solicitacaoServicoDTO.getResponsavelAtual());
				novo.setSolicitante(solicitacaoServicoDTO.getNomeSolicitante());
				novo.setSituacao(solicitacaoServicoDTO.getSituacao());
				novo.setDataCriacao(solicitacaoServicoDTO.getDataHoraSolicitacaoStr());
				novo.setQtdDiasAtrasos(solicitacaoServicoDTO.getQtdDiasAberto());
				listaParaEnvio.add(novo);
			}
		}
		
		int QuantidadeDeSolicitacoesComMaisXDias = listaSolicitacoesNaoResolvidas == null ? 0 : listaSolicitacoesNaoResolvidas.size();
		/**
		 * Otimizar depois usando um count para o quantitativo de solicitações dentro do período informado de dias
		 */
		Collection<SolicitacaoServicoDTO> col = solicitacaoService.findSolicitacoesNaoResolvidasEntrePrazoKPI(relatorioIncidentesNaoResolvidosDto);
		int QuantidadeDeSolicitacoesDentroDoPeriodo = (col == null ? 0 : col.size()) ;
		
		if (listaParaEnvio != null && !listaParaEnvio.isEmpty()) {

			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioIncidentesNaoResolvidosXls.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioIncidentesNaoResolvidos.titulo"));

			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			if (usuario != null) {
				parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			}
			parametros.put("dataReferencia", relatorioIncidentesNaoResolvidosDto.getDataReferencia());
			if (relatorioIncidentesNaoResolvidosDto.getIdContrato() != null) {
				parametros.put("contrato", this.getContrato(relatorioIncidentesNaoResolvidosDto.getIdContrato()));
			}
			parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioIncidentesNaoResolvidosXls_subreport1.jasper");
			parametros.put("Logo", LogoRel.getFile());
			parametros.put("titulo", UtilI18N.internacionaliza(request, "citcorpore.comum.titulo"));
			parametros.put("qtdDiasAberto", relatorioIncidentesNaoResolvidosDto.getQtdDiasAbertos());
			parametros.put("TotalAberto",String.valueOf(QuantidadeDeSolicitacoesDentroDoPeriodo + QuantidadeDeSolicitacoesComMaisXDias));
			parametros.put("QuantidadeDeSolicitacoesComMaisXDias",String.valueOf((int)QuantidadeDeSolicitacoesComMaisXDias));
			parametros.put("QuantidadeDeSolicitacoesDentroDoPeriodo",String.valueOf((int)QuantidadeDeSolicitacoesDentroDoPeriodo));
			parametros.put("percentualExecutadas",String.format("%.2f",100.0*((float)QuantidadeDeSolicitacoesComMaisXDias/(float)(QuantidadeDeSolicitacoesDentroDoPeriodo + QuantidadeDeSolicitacoesComMaisXDias)))+"%");
			
			this.gerarRelatorioFormatoXls(listaParaEnvio, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);

		} else {
			document.executeScript("reportEmpty();");
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	@Override
	public Class getBeanClass() {
		return RelatorioIncidentesNaoResolvidosDTO.class;
	}

	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);
	}

	public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO = (RelatorioIncidentesNaoResolvidosDTO) document.getBean();
		HTMLSelect comboGrupo1 = document.getSelectById("primeiraListaGrupo");
		HTMLSelect comboGrupo2 = document.getSelectById("segundaListaGrupo");
		HTMLSelect servicos = document.getSelectById("listaServico");

		servicos.removeAllOptions();
		comboGrupo1.removeAllOptions();
		comboGrupo2.removeAllOptions();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupo = grupoService.listGrupoByIdContrato(relatorioIncidentesNaoResolvidosDTO.getIdContrato());
		comboGrupo1.addOptions(colGrupo, "idGrupo", "Nome", null);
	}
	
	private void carregarComboTipoDemanda(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception {

		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);

		HTMLSelect idTipoDemandaServico = (HTMLSelect) document.getSelectById("idTipoDemandaServico");

		idTipoDemandaServico.removeAllOptions();

		idTipoDemandaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		Collection<TipoDemandaServicoDTO> listTipoDemanda = tipoDemandaService.listSolicitacoes();

		if (listTipoDemanda != null) {
			idTipoDemandaServico.addOptions(listTipoDemanda, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
		}
	}

	public void gerarRelatorioFormatoPdf(Collection<SolicitacaoServicoDTO> listaRelatorioIncidentesNaoAtendidos, String caminhoJasper, Map<String, Object> parametros, String diretorioReceita,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorioIncidentesNaoAtendidos);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioEficaciaNasEstimativasDasRequisicaoDeServico" + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioEficaciaNasEstimativasDasRequisicaoDeServico" + "_" + usuario.getIdUsuario() + ".pdf')");
	}

	public void gerarRelatorioFormatoXls(Collection<RelatorioIncidentesNaoResolvidosDTO> listaIncidentesNaoResolvidos, Map<String, Object> parametros, String diretorioReceita, DocumentHTML document,
			String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaIncidentesNaoResolvidos);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioIncidentesNaoResolvidosXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioIncidentesNaoResolvidosXls" + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioIncidentesNaoResolvidosXls" + "_" + usuario.getIdUsuario() + ".xls')");

	}

	private String getContrato(Integer id) throws ServiceException, Exception {

		ContratoDTO contrato = new ContratoDTO();
		contrato.setIdContrato(id);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		contrato = (ContratoDTO) contratoService.restore(contrato);
		if (contrato != null) {
			return contrato.getNumero();
		}

		return null;
	}

	/*private String getDescricaoGrupo(int idGrupo) throws ServiceException, Exception {

		GrupoDTO dto = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		dto = grupoService.listGrupoById(idGrupo);
		return dto.getNome();

	}*/

private void calculaPeriododeReferencia(RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDto) throws ServiceException, Exception {
		
		Double diasAberto = Double.parseDouble(relatorioIncidentesNaoResolvidosDto.getQtdDiasAbertos().toString());
		Double diasAbertosEmMilisegundos = diasAberto*24*60*60*1000;
		Double dataFinalDouble = relatorioIncidentesNaoResolvidosDto.getDataReferencia().getTime() - diasAbertosEmMilisegundos;
		
		long dataFinalLong = Math.round(dataFinalDouble);
		
		java.sql.Date dataFinalDate = new java.sql.Date(dataFinalLong);
		relatorioIncidentesNaoResolvidosDto.setPeriodoReferencia(dataFinalDate);
		
	}
	
	private void calculaQtdDiasAtrasados(Collection<SolicitacaoServicoDTO> col, RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDto) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		
		int diasAberto;
		if(col != null){
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : col) {
				diasAberto = 0;
				SolicitacaoServicoDTO novo = solicitacaoService.restoreAll(solicitacaoServicoDTO.getIdSolicitacaoServico());
				
				if(novo.getAtrasoSLA() > 0) {
					diasAberto = (int) ((novo.getAtrasoSLA() / 3600) / 24);
				}
				solicitacaoServicoDTO.setQtdDiasAberto(diasAberto);
			}
		}
	}
}
