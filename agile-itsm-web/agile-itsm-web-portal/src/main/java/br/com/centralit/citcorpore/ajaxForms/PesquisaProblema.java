package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Date;
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
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.PesquisaProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CategoriaProblemaService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked","unused" })
public class PesquisaProblema extends AjaxFormAction {

	UsuarioDTO usuario;
	private String localeSession = null;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.executeScript("$('#divTblProblema').hide();");

		this.preencherComboCidade(document, request, response);

		this.preencherComboCategoriaProblema(document, request, response);

	}

	/**
	 * @author geber.costa
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	
	public void pesquisaProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		PesquisaProblemaDTO pesquisaProblemaDTO = (PesquisaProblemaDTO) document.getBean();

		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);

		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		UsuarioDTO usuarioDto = new UsuarioDTO();

		if (pesquisaProblemaDTO.getIdProprietario() != null) {
			usuarioDto = usuarioService.restoreByIdEmpregadosDeUsuarios(pesquisaProblemaDTO.getIdProprietario());
			if (usuarioDto.getIdUsuario() != null) {
				pesquisaProblemaDTO.setIdProprietario(usuarioDto.getIdUsuario());
			}
		}

		Collection<PesquisaProblemaDTO> listaProblema = problemaService.listProblemaByCriterios(pesquisaProblemaDTO);

		if (listaProblema != null) {
			HTMLTable tblProblema = document.getTableById("tblProblema");
			tblProblema.deleteAllRows();

			int id = 0;
			boolean verificaId = false;
			for (PesquisaProblemaDTO problema : listaProblema) {

				// faz a validação do tamanho do campo título
				if (problema.getTitulo().length() >= 200) {
					StringBuilder sb = new StringBuilder();
					sb.append(problema.getTitulo().substring(0, 49) + "\n");
					sb.append(problema.getTitulo().substring(50, 99) + "\n");
					sb.append(problema.getTitulo().substring(100, 149) + "\n");
					sb.append(problema.getTitulo().substring(150, problema.getTitulo().length()));
					problema.setTitulo(sb.toString());

				} else if (problema.getTitulo().length() >= 100 && problema.getTitulo().length() < 200) {
					StringBuilder sb = new StringBuilder();
					sb.append(problema.getTitulo().substring(0, 99) + "\n");
					sb.append(problema.getTitulo().substring(100, problema.getTitulo().length()) + "\n");
					problema.setTitulo(sb.toString());

				} else if (problema.getTitulo().length() >= 50 && problema.getTitulo().length() < 200) {
					StringBuilder sb = new StringBuilder();
					sb.append(problema.getTitulo().substring(0, 49) + "\n");
					sb.append(problema.getTitulo().substring(50, problema.getTitulo().length()));
					problema.setTitulo(sb.toString());
				} else {

				}
				// faz a validação do tamanho do campo descrição
				if (problema.getDescricao().length() > 800) {
					StringBuilder sb = new StringBuilder();
					sb.append(problema.getDescricao().substring(0, 199) + "\n");
					sb.append(problema.getDescricao().substring(200, 399) + "\n");
					sb.append(problema.getDescricao().substring(400, 599) + "\n");
					sb.append(problema.getDescricao().substring(600, 799) + "\n");
					sb.append(problema.getDescricao().substring(800, problema.getDescricao().length()));
					problema.setDescricao(sb.toString());
				}

				else if (problema.getDescricao().length() > 600) {
					StringBuilder sb = new StringBuilder();
					sb.append(problema.getDescricao().substring(0, 199) + "\n");
					sb.append(problema.getDescricao().substring(200, 399) + "\n");
					sb.append(problema.getDescricao().substring(400, 599) + "\n");
					sb.append(problema.getDescricao().substring(600, problema.getDescricao().length()));
					problema.setDescricao(sb.toString());
				}

				else if (problema.getDescricao().length() > 400) {
					StringBuilder sb = new StringBuilder();
					sb.append(problema.getDescricao().substring(0, 199) + "\n");
					sb.append(problema.getDescricao().substring(200, 399) + "\n");
					sb.append(problema.getDescricao().substring(400, problema.getDescricao().length()));
					problema.setDescricao(sb.toString());
				}

				else if (problema.getDescricao().length() > 200) {
					StringBuilder sb = new StringBuilder();
					sb.append(problema.getDescricao().substring(0, 199) + "\n");
					sb.append(problema.getDescricao().substring(200, problema.getDescricao().length()));
					problema.setDescricao(sb.toString());
				} else {
					//
				}
				
				if(problema.getDataHoraCaptura()!=null){
					problema.setDataHoraInicioStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, problema.getDataHoraCaptura(), WebUtil.getLanguage(request)));
				}
				
				if(problema.getDataHoraFim()!=null){
					problema.setDataHoraConclusaoStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, problema.getDataHoraFim(), WebUtil.getLanguage(request)));
				}

				tblProblema.addRow(problema, new String[] { "idProblema", "categoria","status","nomeProprietario", "nomeSolicitante", "dataHoraInicioStr","dataHoraConclusaoStr", "nomeGrupoAtual",  "titulo", "descricao"},
						null, null, null, null, null);

				document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");

			}

			document.executeScript("$('#divTblProblema').show();");

		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.resultado"));
			document.executeScript("$('#divTblProblema').hide();");
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	/**
	 * fireEvent para impressão dos relatorio.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */
	public void imprimirRelatorioProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = ((HttpServletRequest) request).getSession();

		PesquisaProblemaDTO pesquisaProblemaDto = (PesquisaProblemaDTO) document.getBean();

		GrupoDTO grupoDto = new GrupoDTO();

		UsuarioDTO usuarioDto = new UsuarioDTO();

		CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		usuario = WebUtil.getUsuario(request);

		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);

		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, null);

		if (pesquisaProblemaDto != null && pesquisaProblemaDto.getIdProprietario() != null) {
			usuarioDto = usuarioService.restoreByIdEmpregadosDeUsuarios(pesquisaProblemaDto.getIdProprietario());
			if (usuarioDto.getIdUsuario() != null) {
				pesquisaProblemaDto.setIdProprietario(usuarioDto.getIdUsuario());
			}
		}

		Collection<PesquisaProblemaDTO> listProblemaByCriterios = problemaService.listProblemaByCriterios(pesquisaProblemaDto);

		if (listProblemaByCriterios != null) {
			if (pesquisaProblemaDto != null) {

				Date dt = new Date();

				String strCompl = "" + dt.getTime();
				String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaProblema.jasper";
				String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
				String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

				parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioProblema.titulo"));
				parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
				parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
				parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
				parametros.put("dataInicio", pesquisaProblemaDto.getDataInicio());
				parametros.put("dataFim", pesquisaProblemaDto.getDataFim());
				parametros.put("Logo", LogoRel.getFile());
				parametros.put("totalProblema", listProblemaByCriterios.size());
				parametros.put("numero", pesquisaProblemaDto.getIdProblema());
				parametros.put("status", pesquisaProblemaDto.getStatus());
				parametros.put("nomeSolicitante", pesquisaProblemaDto.getNomeSolicitante());
				parametros.put("proprietario", pesquisaProblemaDto.getNomeProprietario());
				parametros.put("exibirCampoDescricao", pesquisaProblemaDto.getExibirCampoDescricao());
				parametros.put("nomeItemConfiguracao", pesquisaProblemaDto.getNomeItemConfiguracao());

				if (pesquisaProblemaDto.getIdCategoriaProblema() != null) {
					categoriaProblemaDto.setIdCategoriaProblema(pesquisaProblemaDto.getIdCategoriaProblema());
					categoriaProblemaDto = (CategoriaProblemaDTO) categoriaProblemaService.restore(categoriaProblemaDto);
					parametros.put("categoria", categoriaProblemaDto.getNomeCategoria());
				} else {
					parametros.put("categoria", null);
				}

				if (pesquisaProblemaDto.getIdGrupoAtual() != null) {
					grupoDto.setIdGrupo(pesquisaProblemaDto.getIdGrupoAtual());
					grupoDto = (GrupoDTO) grupoService.restore(grupoDto);
					parametros.put("grupoSolucionador", grupoDto.getNome());
				} else {
					parametros.put("grupoSolucionador", "");
				}

				/*
				 * if (pesquisaRequisicaoMudancaDto.getIdCategoriaMudanca() != null) { categoriaMudancaDto.setIdCategoriaMudanca(pesquisaRequisicaoMudancaDto.getIdCategoriaMudanca());
				 * categoriaMudancaDto = (CategoriaMudancaDTO) categoriaMudancaService.restore(categoriaMudancaDto); parametros.put("categoria", categoriaMudancaDto.getNomeCategoria()); }else{
				 * parametros.put("categoria", ""); }
				 */

				if (pesquisaProblemaDto.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {

					this.gerarRelatorioFormatoPdf(listProblemaByCriterios, caminhoJasper, parametros, diretorioReceita, strCompl, document, diretorioRelativoOS, usuario);

				} else {

					this.gerarRelatorioFormatoXls(listProblemaByCriterios, parametros, diretorioReceita, strCompl, document, diretorioRelativoOS, usuario);

				}

			} else {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				return;
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	/**
	 * Gerar relatório no formato .pdf
	 * 
	 * @param listProblemaByCriterios
	 * @param caminhoJasper
	 * @param parametros
	 * @param diretorioReceita
	 * @param strCompl
	 * @param document
	 * @param diretorioRelativoOS
	 * @param usuario
	 * @throws Exception
	 * @author geber.costa
	 */
	public void gerarRelatorioFormatoPdf(Collection<PesquisaProblemaDTO> listProblemaByCriterios, String caminhoJasper, Map<String, Object> parametros, String diretorioReceita, String strCompl,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listProblemaByCriterios);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioPesquisaProblema" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioPesquisaProblema" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");

	}

	/**
	 * Gerar relatório no formato .xls
	 * 
	 * @param listProblemaByCriterios
	 * @param parametros
	 * @param diretorioReceita
	 * @param strCompl
	 * @param document
	 * @param diretorioRelativoOS
	 * @param usuario
	 * @throws Exception
	 * @author geber.costa
	 */
	public void gerarRelatorioFormatoXls(Collection<PesquisaProblemaDTO> listProblemaByCriterios, Map<String, Object> parametros, String diretorioReceita, String strCompl, DocumentHTML document,
			String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listProblemaByCriterios);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaProblemaXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioPesquisaProblema" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioPesquisaProblema" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");

	}

	public void preencherComboCidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getSelectById("idGrupoAtual").removeAllOptions();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection colGrupos = grupoService.findGruposAtivos();

		document.getSelectById("idGrupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");

		document.getSelectById("idGrupoAtual").addOptions(colGrupos, "idGrupo", "nome", null);
	}

	public void preencherComboCategoriaProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getSelectById("comboCategoriaProblema").removeAllOptions();

		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, null);
		Collection<CategoriaProblema> listaCategoriaProblema = categoriaProblemaService.getAtivos();

		document.getSelectById("comboCategoriaProblema").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("comboCategoriaProblema").addOptions(listaCategoriaProblema, "idCategoriaProblema", "nomeCategoria", null);
	}

	// public void preencherComboStatus(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
	//
	// ProblemaDTO problemaDto = (ProblemaDTO) document.getBean();
	//
	// document.getSelectById("status").removeAllOptions();
	//
	// ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
	// Collection <Problema> listaProblema = problemaService.findByIdProblema(problemaDto.getIdProblema());
	//
	// document.getSelectById("status").addOption("", "-- "+ UtilI18N.internacionaliza(request, "citcorpore.comum.todos")+ " --");
	// document.getSelectById("status").addOptions(listaProblema, "idProblema", "status", null);
	//
	// }

	public Class getBeanClass() {
		return PesquisaProblemaDTO.class;
	}

	public void validacaoCategoriaProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		PesquisaProblemaDTO pesquisaProblemDto = (PesquisaProblemaDTO) document.getBean();

		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, null);

		CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();

		if (pesquisaProblemDto.getIdTipoProblema() != null) {

			categoriaProblemaDto.setIdCategoriaProblema(pesquisaProblemDto.getIdTipoProblema());

			categoriaProblemaDto = (CategoriaProblemaDTO) categoriaProblemaService.restore(categoriaProblemaDto);

			if (categoriaProblemaDto.getNomeCategoria() != null && categoriaProblemaDto.getNomeCategoria().equalsIgnoreCase("Normal")) {
				document.executeScript("$('#nomeCategoriaProblema').attr('disabled', " + false + ");");
				document.executeScript("$('#div_categoria').show();");
			} else {
				document.executeScript("$('#div_categoria').hide();");
				document.executeScript("$('#nomeCategoriaProblema').attr('disabled', " + true + ");");
			}

		}

	}
}
