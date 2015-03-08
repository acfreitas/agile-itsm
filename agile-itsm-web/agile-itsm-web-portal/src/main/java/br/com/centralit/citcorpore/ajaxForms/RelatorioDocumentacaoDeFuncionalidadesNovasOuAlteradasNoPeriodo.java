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
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
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
public class RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo extends AjaxFormAction {

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
		RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO = (RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		SolicitacaoServicoService solcitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		ArrayList<ServicoDTO> listaServicos = new ArrayList<ServicoDTO>();
		Collection<RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO> listaParaEnvio = new ArrayList<RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO>();
		Collection<RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO> listaAux = new ArrayList<RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO>();
		// Restaura o usuário selecionado
		if (relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO.getListaServicos() != null) {
			String[] listaServicosTela;
			listaServicosTela = relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO.getListaServicos().split(";");
			if (listaServicosTela.length != 0) {
				for (String i : listaServicosTela) {
					if (!i.equals("")) {
						ServicoDTO servico = servicoService.findById(Integer.valueOf(i));
						if (servico != null) {
							listaServicos.add(servico);
						}
					}
				}
			}
		}

		if(listaServicos != null && !listaServicos.isEmpty()){
			listaAux = solcitacaoService.listaQtdSolicitacoesCanceladasFinalizadasporServicoNoPeriodo(relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO.getDataInicio(), relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO.getDataFim(), listaServicos);
		}

		if (listaAux != null && !listaAux.isEmpty()) {

			int totalAbertoTotal = 0;
			int totalQtdeSoliciatacoesCanceladasFinalizadas = 0;

			for (RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO dto : listaAux) {
				RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO novoDto = new RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO();
				novoDto.setNomeServico(dto.getNomeServico());
				novoDto.setTotalAberto(dto.getTotalAberto()==null?0:dto.getTotalAberto());
				novoDto.setQtdeSoliciatacoesCanceladasFinalizadas(dto.getQtdeSoliciatacoesCanceladasFinalizadas()==null?0:dto.getQtdeSoliciatacoesCanceladasFinalizadas());
				if(dto.getTotalAberto()==null || dto.getQtdeSoliciatacoesCanceladasFinalizadas()==null){
					novoDto.setPorcentagemExecutada("0.00%");
				}else{
					novoDto.setPorcentagemExecutada(String.format("%.2f",100 *((double)dto.getQtdeSoliciatacoesCanceladasFinalizadas()/dto.getTotalAberto()))+"%");
				}
				listaParaEnvio.add(novoDto);
				totalAbertoTotal += dto.getTotalAberto();
				totalQtdeSoliciatacoesCanceladasFinalizadas += dto.getQtdeSoliciatacoesCanceladasFinalizadas()==null?0:dto.getQtdeSoliciatacoesCanceladasFinalizadas();
			}

			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo.title"));

			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			parametros.put("dataInicio", relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO.getDataInicio());
			parametros.put("dataFim", relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO.getDataFim());
			parametros.put("Logo", LogoRel.getFile());
			parametros.put("titulo", UtilI18N.internacionaliza(request, "citcorpore.comum.titulo"));
			parametros.put("totalAbertoTotal",String.valueOf(totalAbertoTotal));
			parametros.put("totalQtdeSoliciatacoesCanceladasFinalizadas",String.valueOf(totalQtdeSoliciatacoesCanceladasFinalizadas));
			if(totalAbertoTotal!=0 || totalQtdeSoliciatacoesCanceladasFinalizadas!=0){
				parametros.put("porcentagemTotal",String.format("%.2f",100 *((double)totalQtdeSoliciatacoesCanceladasFinalizadas/totalAbertoTotal))+"%");
			}else{
				parametros.put("porcentagemTotal","0.00%");
			}


			if (relatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {

				this.gerarRelatorioFormatoPdf(listaParaEnvio, caminhoJasper, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);

			} else {

				this.gerarRelatorioFormatoXls(listaParaEnvio, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);

			}
		} else {
			document.executeScript("reportEmpty();");
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	@Override
	public Class getBeanClass() {
		return RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO.class;
	}

	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);
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


	public void gerarRelatorioFormatoPdf(Collection<RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO> listaRelatorioMudancaItemConfiguracao, String caminhoJasper,
			Map<String, Object> parametros, String diretorioReceita, DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {


		parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo_subreport1.jasper");

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorioMudancaItemConfiguracao);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo" + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo" + "_" + usuario.getIdUsuario() + ".pdf')");
	}

	public void gerarRelatorioFormatoXls(Collection<RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO> listaRelatorioMudancaItemConfiguracao, Map<String, Object> parametros,
			String diretorioReceita, DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorioMudancaItemConfiguracao);

		parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoXls_subreport1.jasper");

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoXls" + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoXls" + "_" + usuario.getIdUsuario() + ".xls')");

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

	private String getDescricaoGrupo(int idGrupo) throws ServiceException, Exception {

		GrupoDTO dto = new GrupoDTO();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		dto = grupoService.listGrupoById(idGrupo);
		return dto.getNome();

	}

}
