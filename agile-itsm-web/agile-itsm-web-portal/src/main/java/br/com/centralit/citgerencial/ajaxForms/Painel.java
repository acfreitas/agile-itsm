package br.com.centralit.citgerencial.ajaxForms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.bean.GerencialGraphInformationDTO;
import br.com.centralit.citgerencial.bean.GerencialInfoGenerateDTO;
import br.com.centralit.citgerencial.bean.GerencialItemInformationDTO;
import br.com.centralit.citgerencial.bean.GerencialItemPainelDTO;
import br.com.centralit.citgerencial.bean.GerencialOptionDTO;
import br.com.centralit.citgerencial.bean.GerencialOptionsDTO;
import br.com.centralit.citgerencial.bean.GerencialPainelDTO;
import br.com.centralit.citgerencial.bean.GerencialParameterDTO;
import br.com.centralit.citgerencial.config.GerencialGrupoPainelConfig;
import br.com.centralit.citgerencial.config.GerencialItemInformationConfig;
import br.com.centralit.citgerencial.config.GerencialPainelConfig;
import br.com.centralit.citgerencial.negocio.GerencialGenerate;
import br.com.centralit.citgerencial.util.WebUtilGerencial;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilTratamentoArquivos;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class Painel extends AjaxFormAction {
	private static final Logger LOGGER = Logger.getLogger(Painel.class);

	public Class getBeanClass() {
		return GerencialPainelDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
		if (usrDto == null) {
			return;
		}
		HTMLElement divUser = document.getElementById("usuarioRel");
		divUser.setInnerHTML(usrDto.getNomeUsuario());

	}

	/*
	 * Faz o carregamento do menu no event de mudança da combo
	 */
	public void changeCombo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		LOGGER.debug(UtilI18N.internacionaliza(request, "painel.loadPainelGerencial"));

		GerencialPainelDTO gerencialPainel = (GerencialPainelDTO) document.getBean();

		Collection colPaineis = null;
		if (gerencialPainel.getFileNameGrupo() == null || gerencialPainel.getFileNameGrupo().trim().equalsIgnoreCase("")) {
			colPaineis = new ArrayList();
		} else {
			colPaineis = GerencialGrupoPainelConfig.getItensGrupo(gerencialPainel.getFileNameGrupo());
		}

		StringBuilder html = new StringBuilder();
		html.append("<div id='tblPaineis'>");
		if (!colPaineis.isEmpty()) {
			int i = 1;
			for (Iterator it = colPaineis.iterator(); it.hasNext();) {
				GerencialPainelDTO painel = (GerencialPainelDTO) it.next();
				html.append("<a class='sJcFJd' id='nav-0" + i + "' onclick='atualizaPainel(\"" + painel.getFileName() + "\", this.id)'" + " href='javascript:;'><div class='CpzCDd'>"
						+ UtilI18N.internacionaliza(request, painel.getDescription()) + "</div></a>");

				document.executeScript("atualizaPainel(" + painel.getFileName() + ", this)");
				i++;
			}
		}
		html.append("</div>");

		HTMLElement divUser = document.getElementById("divLista");
		divUser.setInnerHTML(html + "");
	}

	public void geraPainel(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario user = WebUtilGerencial.getUsuario(request);
		if (user == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}

		if (user.getIdUsuario() == null) {
			user.setIdUsuario("1");
		}

		GerencialPainelDTO gerencialDto = (GerencialPainelDTO) document.getBean();
		GerencialPainelDTO gerencialPainelDto = null;
		gerencialPainelDto = GerencialPainelConfig.getInstance(gerencialDto.getFileName());

		HashMap hashParametros = getParametrosInformados(request);

		String dataInicial = (String) hashParametros.get("PARAM.dataInicial");
		String dataFinal = (String) hashParametros.get("PARAM.dataFinal");

		/* Visualização da data no painel principal */
		if (dataInicial != null || dataFinal != null) {
			HTMLElement parametros = document.getElementById("parametros");
			parametros.setInnerHTML("<h2>" + UtilI18N.internacionaliza(request, "citcorpore.comum.datainicio") + " - " + UtilI18N.internacionaliza(request, "citcorpore.comum.datafim") + "</h2>");
		}

		if (!gerencialDto.isParametersPreenchidos() && gerencialPainelDto.getListParameters() != null && gerencialPainelDto.getListParameters().size() > 0) {
			HTMLElement divParametros = document.getElementById("divParametros");
			divParametros.setInnerHTML(geraParametrosPainel(gerencialPainelDto, hashParametros, user, true, request));
			document.executeScript("DEFINEALLPAGES_atribuiCaracteristicasCitAjax()");
			// document.executeScript("$('#POPUP_PARAM').attr('title','" + gerencialPainelDto.getDescription() + "')");
			document.executeScript("HTMLUtils.focusInFirstActivateField(document.formParametros)");
			document.executeScript("pageLoad();");
			document.executeScript("$('#POPUP_PARAM').dialog('open')");
			return;
		}

		// Limpando a estrutura
		document.executeScript("document.getElementById('divPainel').innerHTML = '';");

		String urlInicial = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "");
		String caminhoGraficos = request.getSession().getServletContext().getRealPath("/tempFiles");
		String caminhoPdfs = request.getSession().getServletContext().getRealPath("/tempFiles");
		
		
		
		if (gerencialPainelDto.getListItens() != null) {
			Collection<GerencialItemPainelDTO> colItens = gerencialPainelDto.getListItens();
			int i = 0;
//			for (Iterator it = gerencialPainelDto.getListItens().iterator(); it.hasNext();) {
//				GerencialItemPainelDTO gerencialItemPainelAuxDto = (GerencialItemPainelDTO) it.next();
			for (GerencialItemPainelDTO gerencialItemPainelAuxDto : colItens) {
				document.executeScript("var divPainel = document.getElementById('divPainel')");

				document.executeScript("var div" + i + " = document.createElement('div')");

				/* Ferramentas de opções */
				document.executeScript("var internaTools" + i + " = document.createElement('div')");
				document.executeScript("internaTools" + i + ".id = 'internaTools" + i + "'");
				document.executeScript("internaTools" + i + ".className = 'internaTools'");
				/* Titulo */
				document.executeScript("var internaTitle" + i + " = document.createElement('div')");
				document.executeScript("internaTitle" + i + ".id = 'internaTitle" + i + "'");
				document.executeScript("internaTitle" + i + ".className = 'internaTitle'");
				/* Conteudo */
				document.executeScript("var interna" + i + " = document.createElement('div')");
				document.executeScript("interna" + i + ".id = 'interna" + i + "'");
				document.executeScript("interna" + i + ".className = 'interna'");

				GerencialItemInformationDTO gerencialItemInfDto = null;

				gerencialItemInfDto = GerencialItemInformationConfig.getInstance(gerencialItemPainelAuxDto.getFile(), request);
				if (gerencialItemInfDto != null && gerencialItemInfDto.getType().equalsIgnoreCase("JSP")) {
					geraBotoesBarraFerramentasJSP(document, gerencialItemPainelAuxDto, request, "TABLE", i, gerencialItemInfDto, "");
					document.executeScript("submitJSP('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + gerencialItemInfDto.getClassExecute() + "')");
					return;
				}
				// document.executeScript("internaTools" + i + ".innerHTML = '" + geraBarraFerramentas(gerencialItemInfDto, gerencialItemPainelAuxDto) + "'");

				GerencialGenerate gerencialGenerateService = (GerencialGenerate) ServiceLocator.getInstance().getService(GerencialGenerate.class, WebUtil.getUsuarioSistema(request));

				GerencialInfoGenerateDTO infoGenerate = new GerencialInfoGenerateDTO();
				infoGenerate.setHashParametros(hashParametros);

				infoGenerate.setCaminhoArquivosGraficos(caminhoGraficos);
				infoGenerate.setCaminhoArquivosPdfs(caminhoPdfs);

				String tipoSaida = (String) request.getSession(true).getAttribute(gerencialItemPainelAuxDto.getFile());
				if (gerencialDto.getGerarExcel() != null && gerencialDto.getGerarExcel().equalsIgnoreCase("S")) {
					tipoSaida = "EXCEL";
				}

				String graphType = "";
				if (gerencialDto.getGerarExcel() == null || gerencialDto.getGerarExcel().equalsIgnoreCase("") || gerencialDto.getGerarExcel().equalsIgnoreCase("N")) {
					if (tipoSaida == null || tipoSaida.trim().equalsIgnoreCase("")) {
						if (gerencialItemInfDto.getDefaultVisualization().equalsIgnoreCase("T")) {
							tipoSaida = "TABLE";
						}
						if (gerencialItemInfDto.getDefaultVisualization().equalsIgnoreCase("P")) {
							tipoSaida = "PDF";
						} else if (gerencialItemInfDto.getDefaultVisualization().substring(0, 1).equalsIgnoreCase("G")) {
							tipoSaida = "GRAPH";
							try {
								graphType = gerencialItemInfDto.getDefaultVisualization().substring(2);
							} catch (Exception e) {
								//
							}
						}
					} else {
						if (tipoSaida.equalsIgnoreCase("T")) {
							tipoSaida = "TABLE";
						}
						if (tipoSaida.equalsIgnoreCase("P")) {
							tipoSaida = "PDF";
						} else if (tipoSaida.equalsIgnoreCase("G")) {
							try {
								graphType = gerencialItemInfDto.getDefaultVisualization().substring(2);
							} catch (Exception e) {
							}
							tipoSaida = "GRAPH";
						} else if (tipoSaida.substring(0, 1).equalsIgnoreCase("G")) {
							try {
								graphType = tipoSaida.substring(2);
							} catch (Exception e) {
							}
							tipoSaida = "GRAPH";
						}
					}
				}

				// ACRESCENTADO POR EMAURI - 02/12
				request.getSession(true).setAttribute("FILE_NAME_GERENCIAL_PDF", gerencialItemPainelAuxDto.getFile());
				// fim - ACRESCENTADO POR EMAURI - 02/12

				infoGenerate.setSaida(tipoSaida);
				if (tipoSaida.equalsIgnoreCase("GRAPH")) {
					if (gerencialItemInfDto.getDefaultVisualization().length() <= 1) {
						document.alert(UtilI18N.internacionaliza(request, "painel.estaConsultaNaoPodeterGraficoGerado"));
						tipoSaida = "TABLE";
						infoGenerate.setSaida(tipoSaida);

						request.getSession(true).setAttribute(gerencialDto.getFileNameItem(), tipoSaida);
					} else {
						if (graphType.equalsIgnoreCase("")) { // Se nao foi especificado o Grafico, entao pega do configuracao.
							infoGenerate.setGraphType(gerencialItemInfDto.getDefaultVisualization().substring(2));
						} else {
							infoGenerate.setGraphType(graphType);
						}
					}
				}

				String retorno = "";
				if (tipoSaida.equalsIgnoreCase("PDF")) {
					request.getSession(true).setAttribute("FILE_NAME_GERENCIAL_PDF", gerencialItemPainelAuxDto.getFile());
					geraPDF(document, request, response);
				} else if (tipoSaida.equalsIgnoreCase("EXCEL")) {
					geraExcel(document, request, response);
					document.executeScript("hideAguarde()");
					return;
				} else {
					retorno = (String) gerencialGenerateService.generate(gerencialItemInfDto, user, infoGenerate, gerencialItemPainelAuxDto, gerencialPainelDto, request);
				 if(retorno == null){
					 retorno = (String) gerencialGenerateService.geraTabelaVazia(infoGenerate, request);
				 }
				}

				geraBotoesBarraFerramentas(document, gerencialItemPainelAuxDto, request, tipoSaida, i, gerencialItemInfDto, retorno);

				i++;
			}
		}

		document.executeScript("document.getElementById('Throbber_2').style.visibility ='hidden';");
		document.executeScript("$('#POPUP_GRAFICO_OPC').dialog('close');");
		document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}

	public void geraBotoesBarraFerramentas(DocumentHTML document, GerencialItemPainelDTO gerencialItemPainelAuxDto, HttpServletRequest request, String tipoSaida, int i,
			GerencialItemInformationDTO gerencialItemInfDto, String retorno) throws Exception {
		int tamAMais = 0;
		int tamAMaisBarra = 0;
		if (i > 0) {
			tamAMais = 15;
			tamAMaisBarra = 19;
		}
		
		/* Desenvolvedor: Rodrigo Pecci - Data: 31/10/2013 - Horário: 11h07min - ID Citsmart: 120770
		 * Motivo/Comentário: A largura do gráfico gerado estava ultrapassando a largura da tabela. Foi corrigido para pegar a largura correta. 
		 */
		document.executeScript("interna" + i + ".style.cssText  = 'width:" + gerencialItemPainelAuxDto.getWidth() + "px !important;height:" + gerencialItemPainelAuxDto.getHeigth() + "px !important;'");
		//document.executeScript("interna" + i + ".style.width  = '" + gerencialItemPainelAuxDto.getWidth() + "px'");
		//document.executeScript("interna" + i + ".style.height = '" + gerencialItemPainelAuxDto.getHeigth() + "px'");

		if ("TABLE".equalsIgnoreCase(tipoSaida)) {
			document.executeScript("interna" + i + ".style.overflow = 'auto'");
		}

		document.executeScript("div" + i + ".appendChild ( internaTools" + i + " )");
		document.executeScript("div" + i + ".appendChild ( internaTitle" + i + " )");
		document.executeScript("div" + i + ".appendChild ( interna" + i + " )");
		document.executeScript("div" + i + ".id ='miniPainel'");

		document.executeScript("divPainel.appendChild ( div" + i + " )");

		HTMLElement internaTitle = document.getElementById("internaTitle" + i + "");
		internaTitle.setInnerHTML("<h2>" + UtilI18N.internacionaliza(request, gerencialItemInfDto.getTitle()) + "</h2>");

		HTMLElement internaTools = document.getElementById("internaTools" + i + "");
		String strBarraFerramentas = geraBarraFerramentas(gerencialItemInfDto, gerencialItemPainelAuxDto, tipoSaida, request);
		internaTools.setInnerHTML(strBarraFerramentas);

		HTMLElement interna = document.getElementById("interna" + i + "");
        if (!gerencialItemInfDto.getType().equalsIgnoreCase("SERVICE_BUFFER")) 
        	//interna.setInnerHTML(UtilHTML.encodeHTML(retorno));//Forma utilizada antes do uso da classe StringEscateUTils
        	interna.setInnerHTML(StringEscapeUtils.unescapeHtml(retorno));
        else
            interna.setInnerHTML(retorno);
	}

	public void geraBotoesBarraFerramentasJSP(DocumentHTML document, GerencialItemPainelDTO gerencialItemPainelAuxDto, HttpServletRequest request, String tipoSaida, int i,
			GerencialItemInformationDTO gerencialItemInfDto, String retorno) throws Exception {
		int tamAMais = 0;
		int tamAMaisBarra = 0;
		if (i > 0) {
			tamAMais = 15;
			tamAMaisBarra = 19;
		}

		document.executeScript("interna" + i + ".style.width  = '" + gerencialItemPainelAuxDto.getWidth() + "px'");
		document.executeScript("interna" + i + ".style.height = '" + gerencialItemPainelAuxDto.getHeigth() + "px'");

		if ("TABLE".equalsIgnoreCase(tipoSaida)) {
			document.executeScript("interna" + i + ".style.overflow = 'auto'");
		}

		document.executeScript("div" + i + ".appendChild ( internaTools" + i + " )");
		document.executeScript("div" + i + ".appendChild ( internaTitle" + i + " )");
		document.executeScript("div" + i + ".appendChild ( interna" + i + " )");
		document.executeScript("div" + i + ".id ='miniPainel'");

		document.executeScript("divPainel.appendChild ( div" + i + " )");

		HTMLElement internaTitle = document.getElementById("internaTitle" + i + "");
		internaTitle.setInnerHTML("<h2>" + gerencialItemInfDto.getTitle() + "</h2>");

		HTMLElement internaTools = document.getElementById("internaTools" + i + "");
		String strBarraFerramentas = geraBarraFerramentas(gerencialItemInfDto, gerencialItemPainelAuxDto, tipoSaida, request);
		internaTools.setInnerHTML(strBarraFerramentas);

		HTMLElement interna = document.getElementById("interna" + i + "");
        if (!gerencialItemInfDto.getType().equalsIgnoreCase("SERVICE_BUFFER")) 
            //interna.setInnerHTML(UtilHTML.encodeHTML(retorno));//Forma utilizada antes do uso da classe StringEscateUTils
        	interna.setInnerHTML(StringEscapeUtils.unescapeHtml(retorno));
        else
            interna.setInnerHTML(retorno);
	}

	public void geraPDF(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario user = WebUtilGerencial.getUsuario(request);
		if (user == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		GerencialPainelDTO gerencialDto = (GerencialPainelDTO) document.getBean();
		GerencialPainelDTO gerencialPainelDto = null;
		gerencialPainelDto = GerencialPainelConfig.getInstance(gerencialDto.getFileName());

		String fileNameItem = gerencialDto.getFileNameItem();
		if (fileNameItem == null || fileNameItem.trim().equalsIgnoreCase("")) {
			fileNameItem = (String) request.getSession(true).getAttribute("FILE_NAME_GERENCIAL_PDF");
		}

		GerencialItemInformationDTO gerencialItemInfDto = null;
		gerencialItemInfDto = GerencialItemInformationConfig.getInstance(fileNameItem, request);

		HashMap hashParametros = getParametrosInformados(request);

		GerencialGenerate gerencialGenerateService = (GerencialGenerate) ServiceLocator.getInstance().getService(GerencialGenerate.class, WebUtil.getUsuarioSistema(request));

		GerencialInfoGenerateDTO infoGenerate = new GerencialInfoGenerateDTO();
		infoGenerate.setTipoSaidaApresentada(gerencialDto.getTipoSaidaApresentada());
		infoGenerate.setSaida("PDF");

		String urlInicial = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "");
		String caminhoGraficos = request.getSession().getServletContext().getRealPath("/tempFiles");
		String caminhoPdfs = request.getSession().getServletContext().getRealPath("/tempFiles");

		infoGenerate.setCaminhoArquivosGraficos(caminhoGraficos);
		infoGenerate.setCaminhoArquivosPdfs(caminhoPdfs);

		if (gerencialDto.getTipoSaidaApresentada().equalsIgnoreCase("GRAPH")) {
			try {
				infoGenerate.setGraphType(gerencialItemInfDto.getDefaultVisualization().substring(2));
			} catch (Exception e) {
			}

			String tipoSaida = (String) request.getSession(true).getAttribute(gerencialDto.getFileNameItem());

			String graphType = "";
			if (tipoSaida != null && !tipoSaida.trim().equalsIgnoreCase("")) {
				if (tipoSaida.substring(0, 1).equalsIgnoreCase("G")) {
					try {
						graphType = tipoSaida.substring(2);
					} catch (Exception e) {
					}

					infoGenerate.setGraphType(graphType);
				}
			}
		}

		GerencialItemPainelDTO gerencialItemPainel = null;
		if (gerencialPainelDto.getListItens() != null) {
			// int i = 0;
			for (Iterator it = gerencialPainelDto.getListItens().iterator(); it.hasNext();) {
				GerencialItemPainelDTO gerencialItemPainelAuxDto = (GerencialItemPainelDTO) it.next();
				if (gerencialItemPainelAuxDto.getFile().equalsIgnoreCase(gerencialDto.getFileNameItem())) {
					gerencialItemPainel = gerencialItemPainelAuxDto;
				}
			}
		}

		infoGenerate.setHashParametros(hashParametros);

		// Internacionaliza os GerencialOptionDTO nos parâmentros
		for (Object parameter : gerencialPainelDto.getListParameters()) {
			if (parameter instanceof GerencialParameterDTO && ((GerencialParameterDTO) parameter).getColOptions() != null) {
				for (Object objeto : ((GerencialParameterDTO) parameter).getColOptions()) {
					if (objeto instanceof GerencialOptionDTO) {
						GerencialOptionDTO option = (GerencialOptionDTO) objeto;
						option.setText(UtilI18N.internacionaliza(request, option.getText()));
					}
				}
			}
		}

		String retorno = (String) gerencialGenerateService.generate(gerencialItemInfDto, user, infoGenerate, gerencialItemPainel, gerencialPainelDto, request);
		if(retorno!=null && retorno!= ""){
			document.executeScript("window.open('" + retorno + "')");
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide()");
	}

	public void getFileExcel(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GerencialPainelDTO gerencialDto = (GerencialPainelDTO) document.getBean();
		if (gerencialDto.getFileNameExcel() != null) {
			System.out.println(">>> Arquivo Excel: " + gerencialDto.getFileNameExcel());
			System.out.println(">>> Arquivo Excel (curto): " + gerencialDto.getFileNameExcelShort());
			File arquivo = new File(gerencialDto.getFileNameExcel());
			
			byte[] buffer = UtilTratamentoArquivos.getBytesFromFile(arquivo);
			response.setContentType("application/vnd.ms-excel");
			if (gerencialDto.getFileNameExcelShort() == null || gerencialDto.getFileNameExcelShort().trim().equalsIgnoreCase("")) {
				gerencialDto.setFileNameExcelShort("relatorio-excel.xls");
			}
			response.setHeader("Content-Disposition", "attachment; filename=" + gerencialDto.getFileNameExcelShort());
			ServletOutputStream outputStream = response.getOutputStream();
			if (buffer != null) {
				response.setContentLength(buffer.length);
			} else {
				System.out.println(">>> CITGERENCIAL -> Buffer null ");
				System.out.println(">>> Arquivo Excel: " + gerencialDto.getFileNameExcel());
				System.out.println(">>> Arquivo Excel (curto): " + gerencialDto.getFileNameExcelShort());
			}
			if(buffer != null){
				outputStream.write(buffer);
			}
			outputStream.flush();
			outputStream.close();
		}
	}

	public void geraExcel(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario user = WebUtilGerencial.getUsuario(request);
		if (user == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		GerencialPainelDTO gerencialDto = (GerencialPainelDTO) document.getBean();
		GerencialPainelDTO gerencialPainelDto = null;
		gerencialPainelDto = GerencialPainelConfig.getInstance(gerencialDto.getFileName());

		String fileNameItem = gerencialDto.getFileNameItem();
		if (fileNameItem == null || fileNameItem.trim().equalsIgnoreCase("")) {
			fileNameItem = (String) request.getSession(true).getAttribute("FILE_NAME_GERENCIAL_PDF");
		}

		GerencialItemInformationDTO gerencialItemInfDto = null;
		gerencialItemInfDto = GerencialItemInformationConfig.getInstance(fileNameItem, request);

		HashMap hashParametros = getParametrosInformados(request);

		GerencialGenerate gerencialGenerateService = (GerencialGenerate) ServiceLocator.getInstance().getService(GerencialGenerate.class, WebUtil.getUsuarioSistema(request));

		GerencialInfoGenerateDTO infoGenerate = new GerencialInfoGenerateDTO();
		infoGenerate.setTipoSaidaApresentada(gerencialDto.getTipoSaidaApresentada());
		infoGenerate.setSaida("TABLE");

		// String caminhoGraficos = request.getSession().getServletContext().getRealPath(Constantes.getValue("CAMINHO_TEMP_GRAFICOS"));
		// String caminhoPdfs = request.getSession().getServletContext().getRealPath(Constantes.getValue("CAMINHO_TEMP_PDFS"));
		String urlInicial = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "");
		String caminhoGraficos = request.getSession().getServletContext().getRealPath("/tempFiles");
		String caminhoPdfs = request.getSession().getServletContext().getRealPath("/tempFiles");

		infoGenerate.setCaminhoArquivosGraficos(caminhoGraficos);
		infoGenerate.setCaminhoArquivosPdfs(caminhoPdfs);

		if (gerencialDto.getTipoSaidaApresentada().equalsIgnoreCase("GRAPH")) {
			try {
				infoGenerate.setGraphType(gerencialItemInfDto.getDefaultVisualization().substring(2));
			} catch (Exception e) {
			}

			String tipoSaida = (String) request.getSession(true).getAttribute(gerencialDto.getFileNameItem());

			String graphType = "";
			if (tipoSaida != null && !tipoSaida.trim().equalsIgnoreCase("")) {
				if (tipoSaida.substring(0, 1).equalsIgnoreCase("G")) {
					try {
						graphType = tipoSaida.substring(2);
					} catch (Exception e) {
					}

					infoGenerate.setGraphType(graphType);
				}
			}
		}

		GerencialItemPainelDTO gerencialItemPainel = null;
		if (gerencialPainelDto.getListItens() != null) {
			// int i = 0;
			for (Iterator it = gerencialPainelDto.getListItens().iterator(); it.hasNext();) {
				GerencialItemPainelDTO gerencialItemPainelAuxDto = (GerencialItemPainelDTO) it.next();
				if (gerencialItemPainelAuxDto.getFile().equalsIgnoreCase(gerencialDto.getFileNameItem())) {
					gerencialItemPainel = gerencialItemPainelAuxDto;
				}
			}
		}

		infoGenerate.setHashParametros(hashParametros);

		String retorno = (String) gerencialGenerateService.generate(gerencialItemInfDto, user, infoGenerate, gerencialItemPainel, gerencialPainelDto, request);
		if(retorno != null && retorno != ""){
			String caminho = "";
			String caminhoRelativo = "";
			try {
				File arquivo = new File(caminhoGraficos);
				if (!arquivo.exists()) {
					arquivo.mkdirs();
				}
				File arquivoVer = new File(caminhoGraficos + "/" + user.getIdUsuario());
				if (!arquivoVer.exists()) {
				arquivoVer.mkdirs();
				}
				caminho = caminhoGraficos + "/" + user.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemInfDto.getDescription())) + ".xls";
				caminho = caminho.replaceAll("\\\\", "/");
				caminhoRelativo = urlInicial + "/tempFiles" + "/" + user.getIdUsuario() + "/" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemInfDto.getDescription()))
						+ ".xls";
				arquivo = new File(caminho);
				if (arquivo.exists()) {
					arquivo.delete();
				}
				
	
				//PrintWriter pw = new PrintWriter(new FileOutputStream(arquivo));
				
				BufferedWriter out = null;
				try {
					/**
					 * Alterado codificação do Writer para ANSI
					 * @author thyen.chang
					 * 17/12/2014
					 */
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo),"Cp1252"));
				} catch (Exception e) {
					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo)));
					e.printStackTrace();
				}
				
		        if (!gerencialItemInfDto.getType().equalsIgnoreCase("SERVICE_BUFFER")) {
					try {
						out.write(retorno);
						//pw.write(retorno);
					} catch (Exception e) {
						e.printStackTrace();
					}
		        } else {
					out.write(retorno);
					//pw.write(retorno);
		        }
		        out.flush();
		        out.close();
		        out = null;
//				pw.flush();
//				pw.close();
//				pw = null;
				arquivo = null;
	
				// document.executeScript("window.open('" + caminhoRelativo + "')");
				document.executeScript("getFileExcel('" + caminho + "','" + UtilStrings.generateNomeBusca(UtilStrings.removeCaracteresEspeciais(gerencialItemInfDto.getDescription())) + ".xls')");
				document.executeScript("JANELA_AGUARDE_MENU.hide()");
			} catch (Exception e) {
				e.printStackTrace();
				document.executeScript("hideAguarde()");
				// handle exception
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.executeScript("JANELA_AGUARDE_MENU.hide()");
		}
	}

	private String geraBarraFerramentas(GerencialItemInformationDTO gerencialItemInfDto, GerencialItemPainelDTO gerencialItemPainelAuxDto, String tipoSaidaApresentada, HttpServletRequest request) {
		String strOpcoes = "";

		strOpcoes += "<table>";
		strOpcoes += "<tr>";
		if (gerencialItemInfDto.isReport()) {
			strOpcoes += "<td>";
			strOpcoes += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
					+ "/produtos/citgerencial/imagens/grid.gif\" style=\"cursor:pointer\" title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.tabela") + "' onclick='setaTipoSaida(\""
					+ gerencialItemPainelAuxDto.getFile() + "\", \"T\")' />";
			strOpcoes += "</td>";
		}
		if (gerencialItemInfDto.isGraph()) {
			String graficosTipos = "";
			if (gerencialItemInfDto.getListGraphs() != null) {
				for (Iterator it = gerencialItemInfDto.getListGraphs().iterator(); it.hasNext();) {
					GerencialGraphInformationDTO gerencialGraph = (GerencialGraphInformationDTO) it.next();
					if (!graficosTipos.equalsIgnoreCase(""))
						graficosTipos += ";";
					graficosTipos += gerencialGraph.getType();
				}
			}

			strOpcoes += "<td>";
			// strOpcoes += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") +
			// "/produtos/citgerencial/imagens/grafico.gif\" style=\"cursor:pointer\" onclick='setaTipoSaida(\"" + gerencialItemPainelAuxDto.getFile() + "\", \"G\")' />";
			strOpcoes += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
					+ "/produtos/citgerencial/imagens/grafico.gif\" style=\"cursor:pointer\" title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.graficos")
					+ "'  onclick='setaTipoGrafico(\"" + gerencialItemPainelAuxDto.getFile() + "\", \"G\", \"" + graficosTipos + "\", \""
					+ UtilI18N.internacionaliza(request, gerencialItemInfDto.getTitle()) + "\")' />";
			strOpcoes += "</td>";
		}
		if (gerencialItemInfDto.isReport()) {
			strOpcoes += "<td>";
			strOpcoes += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
					+ "/produtos/citgerencial/imagens/documents.gif\" style=\"cursor:pointer\" title='" + UtilI18N.internacionaliza(request, "painel.downloadDocumentoPDF") + "' onclick='geraPDF(\""
					+ gerencialItemPainelAuxDto.getFile() + "\", \"" + tipoSaidaApresentada + "\")' />";
			strOpcoes += "</td>";
			strOpcoes += "<td>";
			strOpcoes += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
					+ "/produtos/citgerencial/imagens/excel.gif\" style=\"cursor:pointer\" title='" + UtilI18N.internacionaliza(request, "painel.downloadDocumentoXLS") + "' onclick='geraExcel(\""
					+ gerencialItemPainelAuxDto.getFile() + "\", \"" + tipoSaidaApresentada + "\")' />";
			strOpcoes += "</td>";
		}
		strOpcoes += "</tr>";
		strOpcoes += "<table>";

		return strOpcoes;
	}

	private String geraBarraFerramentasJSP(GerencialItemInformationDTO gerencialItemInfDto, GerencialItemPainelDTO gerencialItemPainelAuxDto, String tipoSaidaApresentada, HttpServletRequest request) {
		String strOpcoes = "";

		strOpcoes += "<table>";
		strOpcoes += "<tr>";
		if (gerencialItemInfDto.isReport()) {
			strOpcoes += "<td>";
			strOpcoes += "<img src=\"" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")
					+ "/produtos/citgerencial/imagens/grid.gif\" style=\"cursor:pointer\" title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.tabela") + "' onclick='geraSaidaImpHTML(\""
					+ gerencialItemPainelAuxDto.getFile() + "\", \"T\")' />";
			strOpcoes += "</td>";
		}
		strOpcoes += "</tr>";
		strOpcoes += "<table>";

		return strOpcoes;
	}

	private String geraParametrosPainel(GerencialPainelDTO gerencialPainelDto, HashMap hashParametros, Usuario user, boolean reload, HttpServletRequest request) throws ServiceException, Exception {
		String strRetorno = "<table>";
		for (Iterator it = gerencialPainelDto.getListParameters().iterator(); it.hasNext();) {
			GerencialParameterDTO gerencialParameterDto = (GerencialParameterDTO) it.next();

			strRetorno += "<tr>";
			strRetorno += "<td>";
			strRetorno += UtilI18N.internacionaliza(request, gerencialParameterDto.getDescription());
			if (gerencialParameterDto.isMandatory()) {
				strRetorno += "*";
			}
			strRetorno += "</td>";
			strRetorno += "<td>";
			strRetorno += geraCampo(gerencialParameterDto, gerencialPainelDto, hashParametros, user, reload, request);
			strRetorno += "</td>";
			strRetorno += "</tr>";
		}
		strRetorno += "</table>";

		return strRetorno;
	}

	private String geraCampo(GerencialParameterDTO gerencialParameterDto, GerencialPainelDTO gerencialPainelDto, HashMap hashParametros, Usuario user, boolean reload, HttpServletRequest request)
			throws ServiceException, Exception {
		String strRetorno = "";
		String strValid = "";

		if (gerencialParameterDto.isMandatory()) {
			strValid = "Required";
		}

		// Verifica se o campo tem o tipo HTML como select.
		if (gerencialParameterDto.getTypeHTML() != null && gerencialParameterDto.getTypeHTML().equalsIgnoreCase("select")) {
			String strValidCompleta = "";

			if (!strValid.trim().equalsIgnoreCase("")) {
				strValidCompleta = " Valid[" + strValid + "]";
			}
			strRetorno += "<select size='" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName() + "' id='PARAM." + gerencialParameterDto.getName()
					+ "' class='Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

			if (gerencialParameterDto.isReload()) {
				// strRetorno += " onchange='recarrega(this)'";
			}

			String value = "#$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%"; // Valor absurdo!!!! So sera usado se nao foi informado o parametro.
			if (reload) {
				value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				if (value == null) {
					value = "#$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%";
				}
			}

			strRetorno += ">";
			if (gerencialParameterDto.getColOptions() != null) {
				for (Iterator it = gerencialParameterDto.getColOptions().iterator(); it.hasNext();) {
					Object obj = it.next();
					if (GerencialOptionDTO.class.isInstance(obj)) {
						GerencialOptionDTO option = (GerencialOptionDTO) obj;
						strRetorno += "<option value='" + option.getValue() + "'";
						if (reload) {
							if (option.getValue().equalsIgnoreCase(value)) {
								strRetorno += " selected ";
							}
						}
						strRetorno += ">" + UtilI18N.internacionaliza(request, option.getText()) + "</option>";
					}
					if (GerencialOptionsDTO.class.isInstance(obj)) {
						GerencialOptionsDTO options = (GerencialOptionsDTO) obj;
						if (options.isOnload() || reload) {
							GerencialGenerate gerencialGenerateService = (GerencialGenerate) ServiceLocator.getInstance().getService(GerencialGenerate.class, WebUtil.getUsuarioSistema(request));
							Collection col = gerencialGenerateService.executaSQLOptions(options, gerencialPainelDto, hashParametros, user);
							for (Iterator itOptions = col.iterator(); itOptions.hasNext();) {
								GerencialOptionDTO option = (GerencialOptionDTO) itOptions.next();
								strRetorno += "<option value='" + option.getValue() + "'";
								if (reload) {
									if (option.getValue().equalsIgnoreCase(value)) {
										strRetorno += " selected ";
									}
								}
								strRetorno += ">" + option.getText() + "</option>";
							}
						}
					}
				}
			}
			strRetorno += "</select>";
		}

		// Verifica se o campo tem o tipo HTML como checkbox.
		if (gerencialParameterDto.getTypeHTML() != null && gerencialParameterDto.getTypeHTML().equalsIgnoreCase("checkbox")) {
			String strValidCompleta = "";

			if (!strValid.trim().equalsIgnoreCase("")) {
				strValidCompleta = " Valid[" + strValid + "]";
			}

			String value = "#$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%"; // Valor absurdo!!!! So sera usado se nao foi informado o parametro.
			if (reload) {
				value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				if (value == null) {
					value = "#$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%$#%";
				}
			}

			if (gerencialParameterDto.getColOptions() != null) {
				String strRetornoAux = "";
				int qtdeOpcoes = 0;
				for (Iterator it = gerencialParameterDto.getColOptions().iterator(); it.hasNext();) {
					Object obj = it.next();
					if (GerencialOptionDTO.class.isInstance(obj)) {
						qtdeOpcoes++;
						GerencialOptionDTO option = (GerencialOptionDTO) obj;
						strRetornoAux += "<input type='checkbox' name='PARAM." + gerencialParameterDto.getName() + "' id='PARAM." + gerencialParameterDto.getName() + "' class='Description["
								+ gerencialParameterDto.getDescription() + "] " + strValidCompleta + "' value='" + option.getValue() + "'";
						if (reload) {
							if (option.getValue().equalsIgnoreCase(value)) {
								strRetornoAux += " checked='checked' ";
							}
						}
						if (gerencialParameterDto.isReload()) {
							// strRetornoAux += " onclick='recarrega(this)'";
						}
						strRetornoAux += "/>" + option.getText() + "<br/>";
					}
					if (GerencialOptionsDTO.class.isInstance(obj)) {
						GerencialOptionsDTO options = (GerencialOptionsDTO) obj;
						if (options.isOnload() || reload) {
							GerencialGenerate gerencialGenerateService = (GerencialGenerate) ServiceLocator.getInstance().getService(GerencialGenerate.class, WebUtil.getUsuarioSistema(request));
							Collection col = gerencialGenerateService.executaSQLOptions(options, gerencialPainelDto, hashParametros, user);
							for (Iterator itOptions = col.iterator(); itOptions.hasNext();) {
								qtdeOpcoes++;
								GerencialOptionDTO option = (GerencialOptionDTO) itOptions.next();
								strRetornoAux += "<input type='checkbox' name='PARAM." + gerencialParameterDto.getName() + "' id='PARAM." + gerencialParameterDto.getName() + "' class='Description["
										+ gerencialParameterDto.getDescription() + "] " + strValidCompleta + "' value='" + option.getValue() + "'";
								if (reload) {
									if (option.getValue().equalsIgnoreCase(value)) {
										strRetornoAux += " checked='checked' ";
									}
								}
								if (gerencialParameterDto.isReload()) {
									// strRetornoAux += " onclick='recarrega(this)'";
								}
								strRetornoAux += "/>" + option.getText() + "<br/>";
							}
						}
					}
				}
				if (!strRetornoAux.equalsIgnoreCase("")) {
					if (qtdeOpcoes > 5) {
						strRetorno += "<div style='height:100px; overflow:auto; border: 1px solid black'>" + strRetornoAux + "</div>";
					} else {
						strRetorno += strRetornoAux;
					}
				}
			}
		}

		// Campo Date
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.sql.Date")) {
			strValid += ",Date";

			strRetorno += "<input type='text' size='" + gerencialParameterDto.getSize() + "' maxlength='" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName()
					+ "' id='PARAM." + gerencialParameterDto.getName() + "'";
			if (!reload) {
				strRetorno += " value='" + gerencialParameterDto.getDefaultValue() + "' ";
			}
			strRetorno += " class='Format[Date] Description[" + gerencialParameterDto.getDescription() + "] Valid[" + strValid + "] datepicker' ";

			if (gerencialParameterDto.isReload()) {
				// strRetorno += " onblur='recarrega(this)'";
			}

			if (reload) {
				String value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				if (value != null) {
					strRetorno += " value='" + value + "'";
				}
			}

			strRetorno += "/>";
		}

		// Campo Inteiro - Nao ha casas decimais
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.lang.Integer")) {
			if (gerencialParameterDto.getTypeHTML() == null || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("") || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("text")) {
				String strValidCompleta = "";

				if (!strValid.trim().equalsIgnoreCase("")) {
					strValidCompleta = " Valid[" + strValid + "]";
				}
				strRetorno += "<input type='text' size='" + gerencialParameterDto.getSize() + "' maxlength='" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName()
						+ "' id='PARAM." + gerencialParameterDto.getName() + "'";
				if (!reload) {
					strRetorno += " value='" + gerencialParameterDto.getDefaultValue() + "' ";
				}
				strRetorno += " class='Format[Numero] Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

				if (gerencialParameterDto.isReload()) {
					// strRetorno += " onblur='recarrega(this)'";
				}

				if (reload) {
					String value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
					if (value != null) {
						strRetorno += " value='" + value + "'";
					}
				}

				strRetorno += "/>";
			}
		}

		// Campo Duplo - Com casas decimais
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.lang.Double")) {
			String strValidCompleta = "";

			if (!strValid.trim().equalsIgnoreCase("")) {
				strValidCompleta = " Valid[" + strValid + "]";
			}
			strRetorno += "<input type='text' size='" + gerencialParameterDto.getSize() + "' maxlength='" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName()
					+ "' id='PARAM." + gerencialParameterDto.getName() + "'";
			if (!reload) {
				strRetorno += " value='" + gerencialParameterDto.getDefaultValue() + "' ";
			}
			strRetorno += " class='Format[Money] Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

			if (gerencialParameterDto.isReload()) {
				// strRetorno += " onblur='recarrega(this)'";
			}

			if (reload) {
				String value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				if (value != null) {
					strRetorno += " value='" + value + "'";
				}
			}

			strRetorno += "/>";
		}

		// Campo String
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.lang.String")) {
			if (gerencialParameterDto.getTypeHTML() == null || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("") || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("text")) {
				String strValidCompleta = "";

				if (!strValid.trim().equalsIgnoreCase("")) {
					strValidCompleta = " Valid[" + strValid + "]";
				}
				strRetorno += "<input type='text' size='" + gerencialParameterDto.getSize() + "' maxlength='" + gerencialParameterDto.getSize() + "' name='PARAM." + gerencialParameterDto.getName()
						+ "' id='PARAM." + gerencialParameterDto.getName() + "'";
				if (!reload) {
					strRetorno += " value='" + gerencialParameterDto.getDefaultValue() + "' ";
				}
				strRetorno += " class='Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

				if (gerencialParameterDto.isReload()) {
					// strRetorno += " onblur='recarrega(this)'";
				}

				if (reload) {
					String value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
					if (value != null) {
						strRetorno += " value='" + value + "'";
					}
				}

				strRetorno += "/>";
			}
		}
		// Campo StringBuilder
		if (gerencialParameterDto.getType().equalsIgnoreCase("java.lang.StringBuilder")) {
			if (gerencialParameterDto.getTypeHTML() == null || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("") || gerencialParameterDto.getTypeHTML().equalsIgnoreCase("text")
					|| gerencialParameterDto.getTypeHTML().equalsIgnoreCase("textarea")) {
				String strValidCompleta = "";

				if (!strValid.trim().equalsIgnoreCase("")) {
					strValidCompleta = " Valid[" + strValid + "]";
				}
				strRetorno += "<textarea rows='" + gerencialParameterDto.getSize() + "' cols='70' name='PARAM." + gerencialParameterDto.getName() + "' id='PARAM." + gerencialParameterDto.getName()
						+ "'";
				strRetorno += " class='Description[" + gerencialParameterDto.getDescription() + "]" + strValidCompleta + "'";

				if (gerencialParameterDto.isReload()) {
					// strRetorno += " onblur='recarrega(this)'";
				}

				String value = "";
				if (reload) {
					value = (String) hashParametros.get("PARAM." + gerencialParameterDto.getName());
				} else {
					value = gerencialParameterDto.getDefaultValue();
				}

				strRetorno += ">";
				if (!reload) {
					if (value == null) {
						value = "";
					}
					strRetorno += value;
				}
				strRetorno += "</textarea>";
			}
		}

		return strRetorno;
	}

	public HashMap getParametrosInformados(HttpServletRequest request) {
		Enumeration x = request.getParameterNames();
		HashMap hashRetorno = new HashMap();
		String[] aux;
		while (x.hasMoreElements()) {
			String nameElement = (String) x.nextElement();

			// System.out.println("Parametro vindo no request: " + nameElement + "    ---> Valor: " + request.getParameter(nameElement));

			if (nameElement.startsWith("PARAM.")) {
				String[] strValores = request.getParameterValues(nameElement);
				if (strValores.length == 0 || strValores.length == 1) {
					String value = request.getParameter(nameElement);
					hashRetorno.put(nameElement, value);
				} else {
					aux = new String[strValores.length];
					for (int i = 0; i < strValores.length; i++) {
						aux[i] = strValores[i];
					}
					hashRetorno.put(nameElement, aux);
				}
			}
		}

		Usuario user = WebUtilGerencial.getUsuario(request);
		hashRetorno.put("USER", user);
		hashRetorno.put("citcorpore.comum.emissao", UtilI18N.internacionaliza(request, "citcorpore.comum.emissao"));
		hashRetorno.put("citcorpore.comum.pagina", UtilI18N.internacionaliza(request, "citcorpore.comum.pagina"));
		hashRetorno.put("grupovisao.contratos", UtilI18N.internacionaliza(request, "grupovisao.contratos"));
		hashRetorno.put("citcorpore.comum.temSLA", UtilI18N.internacionaliza(request, "citcorpore.comum.temSLA"));
		hashRetorno.put("citcorpore.comum.naoTemSLA", UtilI18N.internacionaliza(request, "citcorpore.comum.naoTemSLA"));
		hashRetorno.put("citcorpore.comum.numeroSolicitacoesIncidentes", UtilI18N.internacionaliza(request, "citcorpore.comum.numeroSolicitacoesIncidentes"));
		hashRetorno.put("citcorpore.comum.quantidadeDeOrigens", UtilI18N.internacionaliza(request, "citcorpore.comum.quantidadeDeOrigens"));
		hashRetorno.put("citcorpore.comum.quantidadeRegistros", UtilI18N.internacionaliza(request, "citcorpore.comum.quantidadeRegistros"));
		hashRetorno.put("citcorpore.comum.naoInformado", UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
		hashRetorno.put("citcorpore.comum.todos", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		hashRetorno.put("citcorpore.comum.selecione", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		return hashRetorno;
	}

	public void atualizaTipoSaida(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario user = WebUtilGerencial.getUsuario(request);
		if (user == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		GerencialPainelDTO gerencialDto = (GerencialPainelDTO) document.getBean();

		request.getSession(true).setAttribute(gerencialDto.getFileNameItem(), gerencialDto.getTipoSaida());

		document.executeScript("atualizaPainel(null,null)");
	}

	public void reloadParameters(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Usuario user = WebUtilGerencial.getUsuario(request);
		if (user == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}
		GerencialPainelDTO gerencialDto = (GerencialPainelDTO) document.getBean();
		GerencialPainelDTO gerencialPainelDto = null;
		gerencialPainelDto = GerencialPainelConfig.getInstance(gerencialDto.getFileName());

		HashMap hashParametros = getParametrosInformados(request);

		if (!gerencialDto.isParametersPreenchidos() && gerencialPainelDto.getListParameters() != null && gerencialPainelDto.getListParameters().size() > 0) {
			HTMLElement divParametros = document.getElementById("divParametros");
			divParametros.setInnerHTML(geraParametrosPainel(gerencialPainelDto, hashParametros, user, true, request));
			document.executeScript("DEFINEALLPAGES_atribuiCaracteristicasCitAjax()");
			document.executeScript("window.setTimeout(\"document.formParametros['" + gerencialDto.getCampo() + "'].focus()\", 3000)");

			return;
		}
	}
}
