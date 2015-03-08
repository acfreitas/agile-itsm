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
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.RelatorioTop10IncidentesRequisicoesDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.Top10IncidentesRequisicoesDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.Top10IncidentesRequisicoesService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
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
public class RelatorioTop10IncidentesRequisicoes extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		this.preencherComboRelatorio(document, request, response);
		this.preencherComboTopList(document, request, response);
		this.preencherComboContrato(document, request, response);
		this.preencherComboPrioridade(document, request, response);
		this.preencherComboUnidade(document, request, response);
		this.preencherComboTipoDemandaServico(document, request, response);
		this.preencherComboSituacao(document, request, response);
		this.preencherComboOrigem(document, request, response);
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

	public void geraTop10SolicitantesMaisAbriramReqInc(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO, UsuarioDTO usuario, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try {
			JRDataSource dataSource;
			//Obtendo informações
			Top10IncidentesRequisicoesService top10IncidentesRequisicoesService = (Top10IncidentesRequisicoesService) ServiceLocator.getInstance().getService(Top10IncidentesRequisicoesService.class, WebUtil.getUsuarioSistema(request));
			ArrayList<Top10IncidentesRequisicoesDTO> listaSolicitantes = top10IncidentesRequisicoesService.listSolicitantesMaisAbriramIncSol(relatorioTop10IncidentesRequisicoesDTO);
			if (listaSolicitantes.size() == 0) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
				return;
			} else {
				if (!relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
					Collection<SolicitacaoServicoDTO> listaDetalhe;
					for (Top10IncidentesRequisicoesDTO top10IncidentesRequisicoesDTO : listaSolicitantes) {
						listaDetalhe = top10IncidentesRequisicoesService.listDetalheSolicitanteMaisAbriuIncSol(top10IncidentesRequisicoesDTO.getId(),relatorioTop10IncidentesRequisicoesDTO);
						dataSource = new JRBeanCollectionDataSource(listaDetalhe);
						top10IncidentesRequisicoesDTO.setListaDetalhe(dataSource);
					}
				}
			}

			dataSource = new JRBeanCollectionDataSource(listaSolicitantes);

			//Alimentando os parâmetros de filtragem para serem mostrados no relatório
			Map<String, Object> parametros = this.alimentaParametros(relatorioTop10IncidentesRequisicoesDTO, usuario, UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relSolicitantesMaisAbriramReqInc.titulo"), document, request, response);

			//Configurando dados para geração do Relatório
			StringBuilder jasperArqRel = new StringBuilder();
			jasperArqRel.append("relTop10SolicitantesMaisAbriramReqInc");
			if (relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
				jasperArqRel.append("Resumido");
			} else {
				jasperArqRel.append("Detalhado");
			}
			Date dt = new Date();
			String strMiliSegundos = Long.toString(dt.getTime());
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
			String diretorioTemp = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			String arquivoRelatorio = "/"+ jasperArqRel + strMiliSegundos + "_" + usuario.getIdUsuario();

			//Chamando o relatório
			if (relatorioTop10IncidentesRequisicoesDTO.getFormato().equalsIgnoreCase("PDF")){
				abreRelatorioPDF(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			} else {
				abreRelatorioXLS(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void geraTop10GruposMaisResolveramReqInc(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO, UsuarioDTO usuario, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try {
			JRDataSource dataSource;
			//Obtendo informações
			Top10IncidentesRequisicoesService top10IncidentesRequisicoesService = (Top10IncidentesRequisicoesService) ServiceLocator.getInstance().getService(Top10IncidentesRequisicoesService.class, WebUtil.getUsuarioSistema(request));
			ArrayList<Top10IncidentesRequisicoesDTO> listaSolicitantes = top10IncidentesRequisicoesService.listGruposMaisResolveramIncSol(relatorioTop10IncidentesRequisicoesDTO);
			if (listaSolicitantes.size() == 0) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
				return;
			} else {
				if (!relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
					Collection<SolicitacaoServicoDTO> listaDetalhe;
					for (Top10IncidentesRequisicoesDTO top10IncidentesRequisicoesDTO : listaSolicitantes) {
						listaDetalhe = top10IncidentesRequisicoesService.listDetalheGruposMaisResolveramIncSol(top10IncidentesRequisicoesDTO.getId(),relatorioTop10IncidentesRequisicoesDTO);
						dataSource = new JRBeanCollectionDataSource(listaDetalhe);
						top10IncidentesRequisicoesDTO.setListaDetalhe(dataSource);
					}
				}
			}

			dataSource = new JRBeanCollectionDataSource(listaSolicitantes);

			//Alimentando os parâmetros de filtragem para serem mostrados no relatório
			Map<String, Object> parametros = this.alimentaParametros(relatorioTop10IncidentesRequisicoesDTO, usuario, UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relGruposMaisResolveramReqInc.titulo"), document, request, response);

			//Configurando dados para geração do Relatório
			StringBuilder jasperArqRel = new StringBuilder();
			jasperArqRel.append("relTop10GruposMaisResolveramReqInc");
			if (relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
				jasperArqRel.append("Resumido");
			} else {
				jasperArqRel.append("Detalhado");
			}
			Date dt = new Date();
			String strMiliSegundos = Long.toString(dt.getTime());
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
			String diretorioTemp = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			String arquivoRelatorio = "/"+ jasperArqRel + strMiliSegundos + "_" + usuario.getIdUsuario();

			//Chamando o relatório
			if (relatorioTop10IncidentesRequisicoesDTO.getFormato().equalsIgnoreCase("PDF")){
				abreRelatorioPDF(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			} else {
				abreRelatorioXLS(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void geraTop10ReqIncMaisSolicitados(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO, UsuarioDTO usuario, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try {
			JRDataSource dataSource;
			//Obtendo informações
			Top10IncidentesRequisicoesService top10IncidentesRequisicoesService = (Top10IncidentesRequisicoesService) ServiceLocator.getInstance().getService(Top10IncidentesRequisicoesService.class, WebUtil.getUsuarioSistema(request));
			ArrayList<Top10IncidentesRequisicoesDTO> listaSolicitantes = top10IncidentesRequisicoesService.listReqIncMaisSolicitados(relatorioTop10IncidentesRequisicoesDTO);
			if (listaSolicitantes.size() == 0) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
				return;
			} else {
				if (!relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
					Collection<SolicitacaoServicoDTO> listaDetalhe;
					for (Top10IncidentesRequisicoesDTO top10IncidentesRequisicoesDTO : listaSolicitantes) {
						listaDetalhe = top10IncidentesRequisicoesService.listDetalheReqIncMaisSolicitados(top10IncidentesRequisicoesDTO.getId(),top10IncidentesRequisicoesDTO.getIdServico(),relatorioTop10IncidentesRequisicoesDTO);
						dataSource = new JRBeanCollectionDataSource(listaDetalhe);
						top10IncidentesRequisicoesDTO.setListaDetalhe(dataSource);
					}
				}
			}

			dataSource = new JRBeanCollectionDataSource(listaSolicitantes);

			//Alimentando os parâmetros de filtragem para serem mostrados no relatório
			Map<String, Object> parametros = this.alimentaParametros(relatorioTop10IncidentesRequisicoesDTO, usuario, UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relReqIncMaisSolicitados.titulo"), document, request, response);

			//Configurando dados para geração do Relatório
			StringBuilder jasperArqRel = new StringBuilder();
			jasperArqRel.append("relTop10ReqIncMaisSolicitados");
			if (relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
				jasperArqRel.append("Resumido");
			} else {
				jasperArqRel.append("Detalhado");
			}
			Date dt = new Date();
			String strMiliSegundos = Long.toString(dt.getTime());
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
			String diretorioTemp = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			String arquivoRelatorio = "/"+ jasperArqRel + strMiliSegundos + "_" + usuario.getIdUsuario();

			//Chamando o relatório
			if (relatorioTop10IncidentesRequisicoesDTO.getFormato().equalsIgnoreCase("PDF")){
				abreRelatorioPDF(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			} else {
				abreRelatorioXLS(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void geraTop10UnidadesMaisAbriramReqInc(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO, UsuarioDTO usuario, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try {
			JRDataSource dataSource;
			//Obtendo informações
			Top10IncidentesRequisicoesService top10IncidentesRequisicoesService = (Top10IncidentesRequisicoesService) ServiceLocator.getInstance().getService(Top10IncidentesRequisicoesService.class, WebUtil.getUsuarioSistema(request));
			ArrayList<Top10IncidentesRequisicoesDTO> listaSolicitantes = top10IncidentesRequisicoesService.listUnidadesMaisAbriramReqInc(relatorioTop10IncidentesRequisicoesDTO);
			if (listaSolicitantes.size() == 0) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
				return;
			} else {
				if (!relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
					Collection<SolicitacaoServicoDTO> listaDetalhe;
					for (Top10IncidentesRequisicoesDTO top10IncidentesRequisicoesDTO : listaSolicitantes) {
						listaDetalhe = top10IncidentesRequisicoesService.listDetalheUnidadesMaisAbriramReqInc(top10IncidentesRequisicoesDTO.getId(),relatorioTop10IncidentesRequisicoesDTO);
						dataSource = new JRBeanCollectionDataSource(listaDetalhe);
						top10IncidentesRequisicoesDTO.setListaDetalhe(dataSource);
					}
				}
			}

			dataSource = new JRBeanCollectionDataSource(listaSolicitantes);

			//Alimentando os parâmetros de filtragem para serem mostrados no relatório
			Map<String, Object> parametros = this.alimentaParametros(relatorioTop10IncidentesRequisicoesDTO, usuario, UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relUnidadesMaisAbriramReqInc.titulo"), document, request, response);

			//Configurando dados para geração do Relatório
			StringBuilder jasperArqRel = new StringBuilder();
			jasperArqRel.append("relTop10UnidadesMaisAbriramReqInc");
			if (relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
				jasperArqRel.append("Resumido");
			} else {
				jasperArqRel.append("Detalhado");
			}
			Date dt = new Date();
			String strMiliSegundos = Long.toString(dt.getTime());
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
			String diretorioTemp = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			String arquivoRelatorio = "/"+ jasperArqRel + strMiliSegundos + "_" + usuario.getIdUsuario();

			//Chamando o relatório
			if (relatorioTop10IncidentesRequisicoesDTO.getFormato().equalsIgnoreCase("PDF")){
				abreRelatorioPDF(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			} else {
				abreRelatorioXLS(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void geraTop10LocMaisAbriramReqInc(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO, UsuarioDTO usuario, DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		try {
			JRDataSource dataSource;
			//Obtendo informações
			Top10IncidentesRequisicoesService top10IncidentesRequisicoesService = (Top10IncidentesRequisicoesService) ServiceLocator.getInstance().getService(Top10IncidentesRequisicoesService.class, WebUtil.getUsuarioSistema(request));
			ArrayList<Top10IncidentesRequisicoesDTO> listaSolicitantes = top10IncidentesRequisicoesService.listLocMaisAbriramReqInc(relatorioTop10IncidentesRequisicoesDTO);
			if (listaSolicitantes.size() == 0) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio") );
				return;
			} else {
				if (!relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
					Collection<SolicitacaoServicoDTO> listaDetalhe;
					for (Top10IncidentesRequisicoesDTO top10IncidentesRequisicoesDTO : listaSolicitantes) {
						listaDetalhe = top10IncidentesRequisicoesService.listDetalheLocMaisAbriramReqInc(top10IncidentesRequisicoesDTO.getId(),relatorioTop10IncidentesRequisicoesDTO);
						dataSource = new JRBeanCollectionDataSource(listaDetalhe);
						top10IncidentesRequisicoesDTO.setListaDetalhe(dataSource);
					}
				}
			}

			dataSource = new JRBeanCollectionDataSource(listaSolicitantes);

			//Alimentando os parâmetros de filtragem para serem mostrados no relatório
			Map<String, Object> parametros = this.alimentaParametros(relatorioTop10IncidentesRequisicoesDTO, usuario, UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relLocMaisAbriramReqInc.titulo"), document, request, response);

			//Configurando dados para geração do Relatório
			StringBuilder jasperArqRel = new StringBuilder();
			jasperArqRel.append("relTop10LocMaisAbriramReqInc");
			if (relatorioTop10IncidentesRequisicoesDTO.getVisualizacao().equalsIgnoreCase("R")){
				jasperArqRel.append("Resumido");
			} else {
				jasperArqRel.append("Detalhado");
			}
			Date dt = new Date();
			String strMiliSegundos = Long.toString(dt.getTime());
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
			String diretorioTemp = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativo = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			String arquivoRelatorio = "/"+ jasperArqRel + strMiliSegundos + "_" + usuario.getIdUsuario();

			//Chamando o relatório
			if (relatorioTop10IncidentesRequisicoesDTO.getFormato().equalsIgnoreCase("PDF")){
				abreRelatorioPDF(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			} else {
				abreRelatorioXLS(dataSource, parametros, diretorioTemp, caminhoJasper, jasperArqRel.toString(), diretorioRelativo, arquivoRelatorio, document, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gerarRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		try {
			RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO = (RelatorioTop10IncidentesRequisicoesDTO) document.getBean();
			switch (relatorioTop10IncidentesRequisicoesDTO.getIdRelatorio()) {
			case 1:
				geraTop10SolicitantesMaisAbriramReqInc(relatorioTop10IncidentesRequisicoesDTO, usuario, document, request, response);
				break;
			case 2:
				geraTop10GruposMaisResolveramReqInc(relatorioTop10IncidentesRequisicoesDTO, usuario, document, request, response);
				break;
			case 3:
				geraTop10ReqIncMaisSolicitados(relatorioTop10IncidentesRequisicoesDTO, usuario, document, request, response);
				break;
			case 4:
				geraTop10UnidadesMaisAbriramReqInc(relatorioTop10IncidentesRequisicoesDTO, usuario, document, request, response);
				break;
			case 5:
				geraTop10LocMaisAbriramReqInc(relatorioTop10IncidentesRequisicoesDTO, usuario, document, request, response);
				break;
			}
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, Object> alimentaParametros(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO, UsuarioDTO usuario, String titulo, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception{
		HttpSession session = ((HttpServletRequest) request).getSession();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		parametros.put("TITULO_RELATORIO", titulo);
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual() );

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
		intervaloDasDatas.append(UtilDatas.dateToSTRWithFormat(relatorioTop10IncidentesRequisicoesDTO.getDataInicial(), pattern)+" "+UtilI18N.internacionaliza(request,"citcorpore.comum.a")+" "+UtilDatas.dateToSTRWithFormat(relatorioTop10IncidentesRequisicoesDTO.getDataFinal(), pattern));
		parametros.put("Periodo", intervaloDasDatas.toString());

		parametros.put("Logo", LogoRel.getFile());

		if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
			ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
			ContratoDTO contratoDTO = new ContratoDTO();
			contratoDTO.setIdContrato(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
			contratoDTO = (ContratoDTO) contratoService.restore(contratoDTO);
			parametros.put("contrato", contratoDTO.getNumero());
		} else {
			parametros.put("contrato", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
			PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
			PrioridadeDTO prioridadeDTO = new PrioridadeDTO();
			prioridadeDTO.setIdPrioridade(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			prioridadeDTO = (PrioridadeDTO) prioridadeService.restore(prioridadeDTO);
			parametros.put("prioridade", prioridadeDTO.getNomePrioridade());
		} else {
			parametros.put("prioridade", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
			UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
			UnidadeDTO unidadeDTO = new UnidadeDTO();
			unidadeDTO.setIdUnidade(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
			unidadeDTO = (UnidadeDTO) unidadeService.restore(unidadeDTO);
			parametros.put("unidade", unidadeDTO.getNome());
		} else {
			parametros.put("unidade", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
			ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
			ServicoDTO servicoDTO = new ServicoDTO();
			servicoDTO.setIdServico(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
			servicoDTO = (ServicoDTO) servicoService.restore(servicoDTO);
			parametros.put("servico", servicoDTO.getNomeServico());
		} else {
			parametros.put("servico", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
			TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
			TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();
			tipoDemandaServicoDTO.setIdTipoDemandaServico(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
			tipoDemandaServicoDTO = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDTO);
			parametros.put("tipoDemandaServico", tipoDemandaServicoDTO.getNomeTipoDemandaServico());
		} else {
			parametros.put("tipoDemandaServico", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
			OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
			OrigemAtendimentoDTO origemAtendimentoDTO = new OrigemAtendimentoDTO();
			origemAtendimentoDTO.setIdOrigem(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			origemAtendimentoDTO = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemAtendimentoDTO);
			parametros.put("origem", origemAtendimentoDTO.getDescricao());
		} else {
			parametros.put("origem", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		if (relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.EmAndamento.toString())) {
			parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.emandamento"));
		} else {
			if (relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Suspensa.toString())) {
				parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.suspensa"));
			} else {
				if (relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Cancelada.toString())) {
					parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.cancelada"));
				} else {
					if (relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Resolvida.toString())) {
						parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.resolvida"));
					} else {
						if (relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Reaberta.toString())) {
							parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.reaberta"));
						} else {
							if (relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Fechada.toString())) {
								parametros.put("situacao", UtilI18N.internacionaliza(request, "citcorpore.comum.fechada"));
							} else {
								if (relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.ReClassificada.toString())) {
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

		if ((relatorioTop10IncidentesRequisicoesDTO.getTopList()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getTopList().intValue()>0)){
			parametros.put("topList", relatorioTop10IncidentesRequisicoesDTO.getTopList().toString());
		} else {
			parametros.put("topList", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		if ((relatorioTop10IncidentesRequisicoesDTO.getIdSolicitante()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdSolicitante().intValue()>0)){
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDTO = new EmpregadoDTO();
			empregadoDTO.setIdEmpregado(relatorioTop10IncidentesRequisicoesDTO.getIdSolicitante());
			empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
			parametros.put("solicitante", empregadoDTO.getNome().toString());
		} else {
			parametros.put("solicitante", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		}

		parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS"));

		return parametros;
	}

	private void preencherComboRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		HTMLSelect comboRelatorio;
		try {
			comboRelatorio = document.getSelectById("idRelatorio");
			if (comboRelatorio!=null){
				comboRelatorio.removeAllOptions();
				comboRelatorio.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
				comboRelatorio.addOption("1", UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relSolicitantesMaisAbriramReqInc.titulo"));
				comboRelatorio.addOption("2", UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relGruposMaisResolveramReqInc.titulo"));
				comboRelatorio.addOption("3", UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relReqIncMaisSolicitados.titulo"));
				comboRelatorio.addOption("4", UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relUnidadesMaisAbriramReqInc.titulo"));
				comboRelatorio.addOption("5", UtilI18N.internacionaliza(request, "relatorioTop10IncidentesRequisicoes.relLocMaisAbriramReqInc.titulo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void preencherComboTopList(DocumentHTML document, HttpServletRequest request, HttpServletResponse response){
		HTMLSelect comboTopList;
		try {
			comboTopList = document.getSelectById("topList");
			if (comboTopList!=null){
				comboTopList.removeAllOptions();
				comboTopList.addOption("10", UtilI18N.internacionaliza(request, "citcorpore.comum.dezprimeiros"));
				comboTopList.addOption("20", UtilI18N.internacionaliza(request, "citcorpore.comum.vinteprimeiros"));
				comboTopList.addOption("40", UtilI18N.internacionaliza(request, "citcorpore.comum.quarentaprimeiros"));
				comboTopList.addOption("80", UtilI18N.internacionaliza(request, "citcorpore.comum.oitentaprimeiros"));
				comboTopList.addOption("100", UtilI18N.internacionaliza(request, "citcorpore.comum.cemprimeiros"));
				comboTopList.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO = (RelatorioTop10IncidentesRequisicoesDTO) document.getBean();
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
							relatorioTop10IncidentesRequisicoesDTO.setIdContrato(lista.get(0).getIdContrato());
							document.setBean(relatorioTop10IncidentesRequisicoesDTO);
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

	private void preencherComboPrioridade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		HTMLSelect comboPrioridade;
		try {
			comboPrioridade = document.getSelectById("idPrioridade");
			if (comboPrioridade!=null){
				comboPrioridade.removeAllOptions();
				PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
				Collection colPrioridade = prioridadeService.prioridadesAtivas();
				comboPrioridade.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
				if ((colPrioridade!=null)&&(colPrioridade.size()>0)){
					comboPrioridade.addOptions(colPrioridade, "idPrioridade", "nomeprioridade", null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void preencherComboUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO = (RelatorioTop10IncidentesRequisicoesDTO) document.getBean();
		Integer idContrato = ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0))?relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue():0;
		HTMLSelect comboUnidade;
		try {
			comboUnidade = document.getSelectById("idUnidade");
			if (comboUnidade!=null){
				comboUnidade.removeAllOptions();
				UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
				Collection colUnidade = unidadeService.listarAtivasPorContrato(idContrato);
				comboUnidade.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
				if ((colUnidade!=null)&&(colUnidade.size()>0)){
					comboUnidade.addOptions(colUnidade, "idUnidade", "nome", null);
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

	private void preencherComboOrigem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		HTMLSelect comboOrigem;
		try {
			comboOrigem = document.getSelectById("idOrigem");
			if (comboOrigem!=null){
				comboOrigem.removeAllOptions();
				OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
				Collection colOrigemAtendimento = origemAtendimentoService.recuperaAtivos();
				comboOrigem.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
				if ((colOrigemAtendimento!=null)&&(colOrigemAtendimento.size()>0)){
					comboOrigem.addOptions(colOrigemAtendimento, "idOrigem", "descricao", null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void configuraObjetos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		try {
		HTMLElement imputSolicitante;
		HTMLElement imputServico;
		HTMLSelect selectUnidade;
		imputSolicitante = document.getElementById("solicitante");
		imputServico = document.getElementById("nomeServico");
		selectUnidade = document.getSelectById("idUnidade");

		RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO = (RelatorioTop10IncidentesRequisicoesDTO) document.getBean();
		switch (relatorioTop10IncidentesRequisicoesDTO.getIdRelatorio()) {
			case 1:
				//Solicitante
				imputSolicitante.setReadonly(true);
				imputSolicitante.setValue("");
				imputServico.setReadonly(false);
				selectUnidade.setDisabled(false);
				break;
			case 2:
				//Grupo
				imputSolicitante.setReadonly(true);
				imputSolicitante.setValue("");
				imputServico.setReadonly(false);
				selectUnidade.setDisabled(false);
				break;
			case 3:
				//Incidente - Requisição
				imputSolicitante.setReadonly(false);
				imputServico.setReadonly(true);
				imputServico.setValue("");
				selectUnidade.setDisabled(false);
				break;
			case 4:
				//Unidade
				imputSolicitante.setReadonly(true);
				imputSolicitante.setValue("");
				imputServico.setReadonly(false);
				selectUnidade.setSelectedIndex(0);
				selectUnidade.setDisabled(true);
				break;
			case 5:
				//Localidade
				imputSolicitante.setReadonly(true);
				imputSolicitante.setValue("");
				imputServico.setReadonly(false);
				selectUnidade.setDisabled(false);
				break;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Class getBeanClass() {
		return RelatorioTop10IncidentesRequisicoesDTO.class;
	}

}