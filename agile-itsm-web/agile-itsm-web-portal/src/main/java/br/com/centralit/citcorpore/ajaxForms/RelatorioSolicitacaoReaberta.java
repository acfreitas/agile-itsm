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
import net.sf.jasperreports.engine.JRException;
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

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.RelatorioSolicitacaoReabertaDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.RelatorioSolicitacaoReabertaService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
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
 * @author euler.ramos
 *
 */
public class RelatorioSolicitacaoReaberta extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		this.preencherComboContrato(document, request, response);
		this.preencherComboGrupo(document, request, response);
		this.preencherComboTipoDemandaServico(document, request, response);
		this.preencherComboSituacao(document, request, response);

		/**
		 * Preenche combo TOP List
		 *
		 * @author thyen.chang
		 */
		for(Enumerados.TopListEnum valor : Enumerados.TopListEnum.values())
			document.getSelectById("topList").addOption(valor.getValorTopList().toString(), UtilI18N.internacionaliza(request, valor.getNomeTopList()));

	}

	public void abreRelatorioPDF(JRDataSource dataSource, Map<String, Object> parametros, String diretorioTemp, String caminhoJasper,String jasperArqRel, String diretorioRelativo, String arquivoRelatorio, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try
		{
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioTemp, 4096, 25);
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper+jasperArqRel+".jasper", parametros, dataSource);
			//JasperViewer.viewReport(print,false);

			JasperExportManager.exportReportToPdfFile(print, diretorioTemp + arquivoRelatorio + ".pdf");

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
					+ diretorioRelativo + arquivoRelatorio + ".pdf')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void abreRelatorioXLS(JRDataSource dataSource, Map<String, Object> parametros, String diretorioTemp, String caminhoJasper, String jasperArqRel, String diretorioRelativo, String arquivoRelatorio, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try
		{
			JasperDesign desenho = JRXmlLoader.load(caminhoJasper + jasperArqRel +".jrxml");
			JasperReport relatorio = JasperCompileManager.compileReport(desenho);
			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioTemp + arquivoRelatorio + ".xls");
			exporter.exportReport();
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativo + arquivoRelatorio + ".xls')");
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	public void geraRelatorioSolicitacaoReaberta(RelatorioSolicitacaoReabertaDTO relatorioSolicitacaoReabertaDTO, UsuarioDTO usuario, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try {

			JRDataSource dataSource;
			//Obtendo informações
			RelatorioSolicitacaoReabertaService relatorioSolicitacaoReabertaService = (RelatorioSolicitacaoReabertaService) ServiceLocator.getInstance().getService(RelatorioSolicitacaoReabertaService.class, WebUtil.getUsuarioSistema(request));
			ArrayList<RelatorioSolicitacaoReabertaDTO> listaSolicitacoesReabertas = relatorioSolicitacaoReabertaService.listSolicitacaoReaberta(relatorioSolicitacaoReabertaDTO);
			ArrayList<RelatorioSolicitacaoReabertaDTO> listaSolicitacoesReabertasFormatadas = new ArrayList<>();
			if (listaSolicitacoesReabertas.size() == 0) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
				return;
			}else {
				//metodo para retirar formatação do campo descrição.
				for (RelatorioSolicitacaoReabertaDTO relatorioSolicitacaoReaberta : listaSolicitacoesReabertas ){
					if (!relatorioSolicitacaoReaberta.getDescricaoServico().equals("") && relatorioSolicitacaoReaberta.getDescricaoServico() != null ){
						relatorioSolicitacaoReaberta.setDescricaoServico(relatorioSolicitacaoReaberta.getDescricaoServicoSemFormatacao());
						listaSolicitacoesReabertasFormatadas.add(relatorioSolicitacaoReaberta);
					}
				}
			}

			listaSolicitacoesReabertas.clear();
			listaSolicitacoesReabertas.addAll(listaSolicitacoesReabertasFormatadas);

			dataSource = new JRBeanCollectionDataSource(listaSolicitacoesReabertas);
			// quantidade de Solicitações encontradas
			relatorioSolicitacaoReabertaDTO.setQuantidade(listaSolicitacoesReabertas.size());
			//Alimentando os parâmetros de filtragem para serem mostrados no relatório
			Map<String, Object> parametros = this.alimentaParametros(relatorioSolicitacaoReabertaDTO, usuario, UtilI18N.internacionaliza(request, "relatorio.solicitacaoReaberta.titulo"), document, request, response);

			//Configurando dados para geração do Relatório
			StringBuilder jasperArqRel = new StringBuilder();
			jasperArqRel.append("RelatorioSolicitacaoReaberta");
			Date dt = new Date();
			String strMiliSegundos = Long.toString(dt.getTime());
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
			String diretorioTemp = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			String arquivoRelatorio = "/"+ jasperArqRel + strMiliSegundos + "_" + usuario.getIdUsuario();

			//Chamando o relatório
			if (relatorioSolicitacaoReabertaDTO.getFormato().equalsIgnoreCase("PDF")){
				abreRelatorioPDF(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			} else {
				abreRelatorioXLS(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString()+"Xls", diretorioRelativo, arquivoRelatorio, document, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que carrega na página o resultado da Pesquisa da Solicitações Reabertas de  acordo com o filtro
	 *
	 * @param relatorioSolicitacaoReabertaDTO
	 * @param usuario
	 * @param document
	 * @param request
	 * @param response
	 */



	public void gerarRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try{
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}

				RelatorioSolicitacaoReabertaDTO relatorioSolicitacaoReabertaDTO = (RelatorioSolicitacaoReabertaDTO) document.getBean();
				geraRelatorioSolicitacaoReaberta(relatorioSolicitacaoReabertaDTO, usuario, document, request, response);
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, Object> alimentaParametros(RelatorioSolicitacaoReabertaDTO relatorioSolicitacaoReabertaDTO, UsuarioDTO usuario, String titulo, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		HttpSession session = ((HttpServletRequest) request).getSession();
		Map<String, Object> parametros = new HashMap<String, Object>();
		// setando o nome do Grupo para que seja apresentado no cabeçalho
		GrupoService grupoService =  (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, WebUtil.getUsuarioSistema(request));
		if (relatorioSolicitacaoReabertaDTO.getIdGrupo() != 0){
			GrupoDTO grupoDTO = (GrupoDTO) grupoService.listGrupoById(relatorioSolicitacaoReabertaDTO.getIdGrupo());
			relatorioSolicitacaoReabertaDTO.setGrupo(grupoDTO.getNome());
		}else{
			relatorioSolicitacaoReabertaDTO.setGrupo(UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		parametros.put("TITULO_RELATORIO", titulo);
		parametros.put("dataInicialReabertura", relatorioSolicitacaoReabertaDTO.getDataInicialReabertura());
		parametros.put("dataFinalReabertura", relatorioSolicitacaoReabertaDTO.getDataFinalReabertura());
		parametros.put("grupo", relatorioSolicitacaoReabertaDTO.getGrupo());
		parametros.put("nomeTipoDemandaServico", relatorioSolicitacaoReabertaDTO.getNomeTipoDemandaServico());
		parametros.put("situacao", relatorioSolicitacaoReabertaDTO.getSituacao());
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("quantidade", relatorioSolicitacaoReabertaDTO.getQuantidade());

		if (usuario!=null){
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		} else {
			parametros.put("NOME_USUARIO", "-");
		}

		//Tratamento para internacionalização do intervalo de datas
		StringBuilder intervaloDasDatas = new StringBuilder();
		String pattern;
		if (usuario != null && StringUtils.isNotBlank(usuario.getLocale()) && usuario.getLocale().toString().equals("en_US")){
			pattern = "MM/dd/yyyy";
		} else {
			pattern = "dd/MM/yyyy";
		}
		intervaloDasDatas.append(UtilDatas.dateToSTR(relatorioSolicitacaoReabertaDTO.getDataInicialReabertura(), pattern)+" "+UtilI18N.internacionaliza(request,"citcorpore.comum.a")+" "+UtilDatas.dateToSTR(relatorioSolicitacaoReabertaDTO.getDataFinalReabertura(), pattern));
		parametros.put("Periodo", intervaloDasDatas.toString());

		parametros.put("Logo", LogoRel.getFile());

		if ((relatorioSolicitacaoReabertaDTO.getIdContrato()!=null)&&(relatorioSolicitacaoReabertaDTO.getIdContrato().intValue()>0)){
			ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
			ContratoDTO contratoDTO = new ContratoDTO();
			contratoDTO.setIdContrato(relatorioSolicitacaoReabertaDTO.getIdContrato());
			contratoDTO = (ContratoDTO) contratoService.restore(contratoDTO);
			parametros.put("contrato", contratoDTO.getNumero());
		} else {
			parametros.put("contrato", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		if ((relatorioSolicitacaoReabertaDTO.getIdTipoDemandaServico()!=null)&&(relatorioSolicitacaoReabertaDTO.getIdTipoDemandaServico().intValue()>0)){
			TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
			TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();
			tipoDemandaServicoDTO.setIdTipoDemandaServico(relatorioSolicitacaoReabertaDTO.getIdTipoDemandaServico());
			tipoDemandaServicoDTO = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDTO);
			parametros.put("nomeTipoDemandaServico", tipoDemandaServicoDTO.getNomeTipoDemandaServico());
		} else {
			parametros.put("nomeTipoDemandaServico", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		if (relatorioSolicitacaoReabertaDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.EmAndamento.toString())) {
			parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.emandamento"));
		} else {
			if (relatorioSolicitacaoReabertaDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Suspensa.toString())) {
				parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.suspensa"));
			} else {
				if (relatorioSolicitacaoReabertaDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.toString())) {
					parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.cancelada"));
				} else {
					if (relatorioSolicitacaoReabertaDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Resolvida.toString())) {
						parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.resolvida"));
					} else {
						if (relatorioSolicitacaoReabertaDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Reaberta.toString())) {
							parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.reaberta"));
						} else {
							if (relatorioSolicitacaoReabertaDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Fechada.toString())) {
								parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.fechada"));
							} else {
								if (relatorioSolicitacaoReabertaDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.ReClassificada.toString())) {
									parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.reclassificada"));
								} else {
									parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
								}
							}
						}
					}
				}
			}
		}

		return parametros;
	}

	private void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		RelatorioSolicitacaoReabertaDTO relatorioSolicitacaoReabertaDTO = (RelatorioSolicitacaoReabertaDTO) document.getBean();
		HTMLSelect comboContrato;
		try {
			comboContrato = document.getSelectById("idContrato");
			if (comboContrato!=null){
				comboContrato.removeAllOptions();
				ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
				Collection colContrato = contratoService.listAtivos();
				if (colContrato!=null){
					if (colContrato.size()>0){
						if (colContrato.size()>1){
							comboContrato.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
						}
						comboContrato.addOptions(colContrato, "idContrato", "numero", null);
						if (colContrato.size()<2){
							List<ContratoDTO> lista = (List<ContratoDTO>) colContrato;
							relatorioSolicitacaoReabertaDTO.setIdContrato(lista.get(0).getIdContrato());
							document.setBean(relatorioSolicitacaoReabertaDTO);
						}
					} else {
						comboContrato.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
					}
				} else {
					comboContrato.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		RelatorioSolicitacaoReabertaDTO relatorioSolicitacaoReabertaDTO = (RelatorioSolicitacaoReabertaDTO) document.getBean();
		HTMLSelect comboGrupo;
		try {
			comboGrupo = document.getSelectById("idGrupo");
			if (comboGrupo!=null){
				comboGrupo.removeAllOptions();
				GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
				Collection colGrupo = grupoService.listaGruposAtivos();
				if (colGrupo!=null){
					if (colGrupo.size()>0){
						if (colGrupo.size()>1){
							comboGrupo.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
						}
						comboGrupo.addOptions(colGrupo, "idGrupo", "nome", null);
						if (colGrupo.size()<2){
							List<GrupoDTO> lista = (List<GrupoDTO>) colGrupo;
							relatorioSolicitacaoReabertaDTO.setIdGrupo(lista.get(0).getIdGrupo());
							document.setBean(relatorioSolicitacaoReabertaDTO);
						}
					} else {
						comboGrupo.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
					}
				} else {
					comboGrupo.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void preencherComboTipoDemandaServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		HTMLSelect comboTipoDemandaServico;
		try {
			comboTipoDemandaServico = document.getSelectById("idTipoDemandaServico");
			if (comboTipoDemandaServico!=null){
				comboTipoDemandaServico.removeAllOptions();
				TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
				Collection colTipoDemandaServico = tipoDemandaServicoService.listSolicitacoes();
				comboTipoDemandaServico.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
				if ((colTipoDemandaServico!=null)&&(colTipoDemandaServico.size()>0)){
					comboTipoDemandaServico.addOptions(colTipoDemandaServico, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void preencherComboSituacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		HTMLSelect comboSituacao;
		try {
			comboSituacao = document.getSelectById("situacao");
			if (comboSituacao!=null){
				comboSituacao.removeAllOptions();
				comboSituacao.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
				comboSituacao.addOption(Enumerados.SituacaoSolicitacaoServico.EmAndamento.toString(), UtilI18N.internacionaliza(request, "citcorpore.comum.emandamento"));
				comboSituacao.addOption(Enumerados.SituacaoSolicitacaoServico.Suspensa.toString(), UtilI18N.internacionaliza(request, "citcorpore.comum.suspensa"));
				comboSituacao.addOption(Enumerados.SituacaoSolicitacaoServico.Cancelada.toString(), UtilI18N.internacionaliza(request, "citcorpore.comum.cancelada"));
				comboSituacao.addOption(Enumerados.SituacaoSolicitacaoServico.Resolvida.toString(), UtilI18N.internacionaliza(request, "citcorpore.comum.resolvida"));
				comboSituacao.addOption(Enumerados.SituacaoSolicitacaoServico.Reaberta.toString(), UtilI18N.internacionaliza(request, "citcorpore.comum.reaberta"));
				comboSituacao.addOption(Enumerados.SituacaoSolicitacaoServico.Fechada.toString(), UtilI18N.internacionaliza(request, "citcorpore.comum.fechada"));
				comboSituacao.addOption(Enumerados.SituacaoSolicitacaoServico.ReClassificada.toString(), UtilI18N.internacionaliza(request, "citcorpore.comum.reclassificada"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	public Class getBeanClass() {
		return RelatorioSolicitacaoReabertaDTO.class;
	}

}