package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.RelatorioKpiProdutividadeDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
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
public class RelatorioKpiProdutividade extends AjaxFormAction {

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
	}

	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		usuario = WebUtil.getUsuario(request);
		RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDTO = (RelatorioKpiProdutividadeDTO) document.getBean();
		String situacao = relatorioKpiProdutividadeDTO.getSituacao();
		HttpSession session = ((HttpServletRequest) request).getSession();
		RelatorioKpiProdutividadeDTO dadosDaTela = new RelatorioKpiProdutividadeDTO();
		dadosDaTela = relatorioKpiProdutividadeDTO;
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		ArrayList<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
		Collection<RelatorioKpiProdutividadeDTO> listaParaEnvio = new ArrayList<RelatorioKpiProdutividadeDTO>();
		// Restaura o usuário selecionado
		if (relatorioKpiProdutividadeDTO.getListaUsuarios() != null) {
			String[] listaUsuariosTela;
			listaUsuariosTela = relatorioKpiProdutividadeDTO.getListaUsuarios().split(";");
			if (listaUsuariosTela.length != 0) {
				for (String i : listaUsuariosTela) {
					if (!i.equals("")) {
						UsuarioDTO usuario = usuarioService.restoreByIdEmpregado(Integer.valueOf(i));
						if (usuario != null) {
							listaUsuarios.add(usuario);
						}
					}
				}
			}
		}
		Collections.sort(listaUsuarios);
		// ----------------------------------------------------
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ArrayList<RelatorioKpiProdutividadeDTO> subLista = new ArrayList<RelatorioKpiProdutividadeDTO>();
		RelatorioKpiProdutividadeDTO dtoAux = new RelatorioKpiProdutividadeDTO();
		double countEstouradasTotalGrupo = 0;
		double countExecutadasTotalGrupo = 0;
		double countExecutadasTotal = 0;
		double countEstouradasTotal = 0;
		
		boolean mostrarIncidentes = relatorioKpiProdutividadeDTO.getCheckMostrarIncidentes() != null ? true : false;
		boolean mostrarRequisicoes = relatorioKpiProdutividadeDTO.getCheckMostrarRequisicoes() != null ? true : false;

		if (listaUsuarios != null) {
			// Busca todas as solicitações feitas pelo usuário
			for (UsuarioDTO usuarios : listaUsuarios) {
				RelatorioKpiProdutividadeDTO novoFuncionario = new RelatorioKpiProdutividadeDTO();
				novoFuncionario.setFuncionario(Integer.toString(usuarios.getIdUsuario()));
				novoFuncionario.setCheckMostrarIncidentes(relatorioKpiProdutividadeDTO.getCheckMostrarIncidentes());
				novoFuncionario.setCheckMostrarRequisicoes(relatorioKpiProdutividadeDTO.getCheckMostrarRequisicoes());
				novoFuncionario.setDataInicio(relatorioKpiProdutividadeDTO.getDataInicio());
				novoFuncionario.setDataFim(relatorioKpiProdutividadeDTO.getDataFim());
				novoFuncionario.setContrato(String.valueOf(relatorioKpiProdutividadeDTO.getIdContrato()));
				novoFuncionario.setGrupo(relatorioKpiProdutividadeDTO.getGrupo());
				novoFuncionario.setFormatoArquivoRelatorio(relatorioKpiProdutividadeDTO.getFormatoArquivoRelatorio());

				double countExecutadasPorSolicitacao = 0;
				double countEstouradasPorSolicitacao = 0;
				
				Collection<SolicitacaoServicoDTO> listaSolicitacoesUsuario = solicitacaoService.listaServicosPorResponsavelNoPeriodo(novoFuncionario.getDataInicio(), novoFuncionario.getDataFim(),
						Integer.valueOf(novoFuncionario.getFuncionario()), mostrarIncidentes, mostrarRequisicoes, situacao);
				novoFuncionario.setFuncionario(usuarios.getNomeUsuario());
				if (listaSolicitacoesUsuario != null && !listaSolicitacoesUsuario.isEmpty()) {

					// verifica a quantidade de solicitações

					for (SolicitacaoServicoDTO solicitacaoServicoDTOAux : listaSolicitacoesUsuario) {
						countExecutadasPorSolicitacao++;
						solicitacaoServicoDTOAux = solicitacaoService.restoreAll(solicitacaoServicoDTOAux.getIdSolicitacaoServico());
						if (solicitacaoServicoDTOAux.getAtrasoSLA() > 0) {
									countEstouradasPorSolicitacao++;
								}
						//countExecutadasTotal += countExecutadasPorSolicitacao;
						//countEstouradasTotal += countEstouradasPorSolicitacao;
					}
					dtoAux = new RelatorioKpiProdutividadeDTO();
					dtoAux.setFuncionario(usuarios.getNomeUsuario());
					dtoAux.setTotalPorExecutante((int) countExecutadasPorSolicitacao);
					dtoAux.setTotalPorExecutanteEstouradas(((int) countExecutadasPorSolicitacao - (int) countEstouradasPorSolicitacao));
					dtoAux.setTotalPorExecutanteEstouradasPorcentagem(String.format("%.2f", 100 * (countEstouradasPorSolicitacao / countExecutadasPorSolicitacao)) + "%");
					dtoAux.setTotalPorExecutantePorcentagem(String.format("%.2f", 100 - (100 * (countEstouradasPorSolicitacao / countExecutadasPorSolicitacao))) + "%");
					subLista.add(dtoAux);

					novoFuncionario.setListaGeral(subLista);
					novoFuncionario.setTotalPorExecutante((int) countExecutadasPorSolicitacao);
					novoFuncionario.setTotalPorExecutanteEstouradas(((int) countExecutadasPorSolicitacao - (int) countEstouradasPorSolicitacao));
					novoFuncionario.setTotalPorExecutanteEstouradasPorcentagem(String.format("%.2f", 100 * (countEstouradasPorSolicitacao / countExecutadasPorSolicitacao)) + "%");
					novoFuncionario.setTotalPorExecutantePorcentagem(String.format("%.2f", 100 - (100 * (countEstouradasPorSolicitacao / countExecutadasPorSolicitacao))) + "%");
					listaParaEnvio.add(novoFuncionario);
					countExecutadasTotalGrupo += countExecutadasPorSolicitacao;
					countEstouradasTotalGrupo += countEstouradasPorSolicitacao;
				} else {
					dtoAux.setTotalPorExecutantePorcentagem("00,00%");
					dtoAux.setTotalPorExecutanteEstouradasPorcentagem("100,00%");
					subLista.add(dtoAux);

					novoFuncionario.setListaGeral(subLista);
					novoFuncionario.setTotalPorExecutante(0);
					novoFuncionario.setTotalPorExecutanteEstouradas(0);
					novoFuncionario.setTotalPorExecutanteEstouradasPorcentagem("100,00%");
					novoFuncionario.setTotalPorExecutantePorcentagem("100,00%");
					listaParaEnvio.add(novoFuncionario);
					countExecutadasTotalGrupo += countExecutadasTotal;
					countEstouradasTotalGrupo += countEstouradasPorSolicitacao;

				}
			}

		}

		if (listaParaEnvio != null && !listaParaEnvio.isEmpty()) {

			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioKpiProdutividade.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioKpi.titulo"));

			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			parametros.put("dataInicio", dadosDaTela.getDataInicio());
			parametros.put("dataFim", dadosDaTela.getDataFim());
			parametros.put("contrato", this.getContrato(dadosDaTela.getIdContrato()));
			// parametros.put("grupo", this.getDescricaoGrupo(Integer.parseInt(relatorioKpiProdutividadeDTO.getGrupo())));
			parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioKpiProdutividade_subreport1.jasper");
			parametros.put("Logo", LogoRel.getFile());
			parametros.put("titulo", UtilI18N.internacionaliza(request, "citcorpore.comum.titulo"));
			parametros.put("incidentes",
					dadosDaTela.getCheckMostrarIncidentes() != null ? UtilI18N.internacionaliza(request, "citcorpore.comum.sim") : UtilI18N.internacionaliza(request, "citcorpore.comum.nao"));
			parametros.put("solicitacoes",
					dadosDaTela.getCheckMostrarRequisicoes() != null ? UtilI18N.internacionaliza(request, "citcorpore.comum.sim") : UtilI18N.internacionaliza(request, "citcorpore.comum.nao"));
			parametros.put("internacionaliza_NumeroSolicitacao", UtilI18N.internacionaliza(request, "gerenciamentoservico.numeroSolicitacao"));
			parametros.put("internacionaliza_NomeServico", UtilI18N.internacionaliza(request, "servico.nome"));
			parametros.put("internacionaliza_QuatidadeExecutada", UtilI18N.internacionaliza(request, "relatorioKpi.QuantidadeExecutada"));
			parametros.put("internacionaliza_QuantiudadeEstourada", UtilI18N.internacionaliza(request, "relatorioKpi.QuantidadeEstourada"));
			parametros.put("internacionaliza_TotalExecutado", UtilI18N.internacionaliza(request, "relatorioKpi.TotalExecutado"));
			parametros.put("internacionaliza_TotalEstourado", UtilI18N.internacionaliza(request, "relatorioKpi.TotalEstourado"));
			parametros.put("internacionaliza_TotalPorGrupo", UtilI18N.internacionaliza(request, "relatorioKpi.TotalPorGrupo"));
			parametros.put("internacionaliza_NaoEstrapolada", UtilI18N.internacionaliza(request, "relatorioKpi.internacionaliza_NaoEstrapolada"));
			parametros.put("ExecutadasTotalPorGrupo", (int) countExecutadasTotalGrupo + "");
			parametros.put("EstouradasTotalPorGrupo", (int) (countExecutadasTotalGrupo - countEstouradasTotalGrupo) + "");
			parametros.put("EstouradasTotalPorGrupoPorcentagem", String.format("%.2f", 100 * (countEstouradasTotalGrupo / countExecutadasTotalGrupo)) + "%");
			parametros.put("ExecutadasTotalPorGrupoPorcentagem", String.format("%.2f", 100 - (100 * (countEstouradasTotalGrupo / countExecutadasTotalGrupo))) + "%");

			if (relatorioKpiProdutividadeDTO.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {

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
		return RelatorioKpiProdutividadeDTO.class;
	}

	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);
	}

	public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, int idContrato) throws Exception {

		HTMLSelect comboGrupo1 = document.getSelectById("primeiraListaGrupo");
		HTMLSelect comboGrupo2 = document.getSelectById("segundaListaGrupo");

		comboGrupo1.removeAllOptions();
		comboGrupo2.removeAllOptions();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupo = grupoService.listGrupoByIdContrato(idContrato);
		comboGrupo1.addOptions(colGrupo, "idGrupo", "Nome", null);
	}

	public void preencherComboUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, int idContrato) throws Exception {

		HTMLSelect comboUnidade1 = document.getSelectById("primeiraListaUnidade");
		HTMLSelect comboUnidade2 = document.getSelectById("segundaListaUnidade");

		comboUnidade1.removeAllOptions();
		comboUnidade2.removeAllOptions();

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		Collection colGrupo = unidadeService.listUnidadePorContrato(idContrato);
		comboUnidade1.addOptions(colGrupo, "idUnidade", "Nome", null);
	}

	@SuppressWarnings("unchecked")
	public void preencherGrupoUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HTMLSelect comboGrupo1 = document.getSelectById("primeiraListaGrupo");
		HTMLSelect comboGrupo2 = document.getSelectById("segundaListaGrupo");

		HTMLSelect comboUnidade1 = document.getSelectById("primeiraListaUnidade");
		HTMLSelect comboUnidade2 = document.getSelectById("segundaListaUnidade");

		HTMLSelect comboUsuario = document.getSelectById("primeiraLista");
		HTMLSelect combousuario2 = document.getSelectById("segundaLista");

		comboGrupo1.removeAllOptions();
		comboGrupo2.removeAllOptions();

		comboUnidade1.removeAllOptions();
		comboUnidade2.removeAllOptions();

		comboUsuario.removeAllOptions();
		combousuario2.removeAllOptions();

		RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDTO = (RelatorioKpiProdutividadeDTO) document.getBean();
		if (relatorioKpiProdutividadeDTO.getIdContrato() != null) {
			String UnidadeGrupo = relatorioKpiProdutividadeDTO.getSelecionarGrupoUnidade();
			int idContrato = relatorioKpiProdutividadeDTO.getIdContrato();

			if (relatorioKpiProdutividadeDTO.getSelecionarGrupoUnidade() != null && relatorioKpiProdutividadeDTO.getSelecionarGrupoUnidade().equalsIgnoreCase("grupo")) {
				this.preencherComboGrupo(document, request, response, idContrato);
			} else {
				preencherComboUnidade(document, request, response, idContrato);
			}
		} else {
			comboGrupo1.removeAllOptions();
			comboGrupo2.removeAllOptions();

			comboUnidade1.removeAllOptions();
			comboUnidade2.removeAllOptions();
		}

		/*
		 * if(relatorioKpiProdutividadeDTO.getIdContrato()!=null && !relatorioKpiProdutividadeDTO.getIdContrato().equals("")){ EmpregadoService funcionarioService = (EmpregadoService)
		 * ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		 * 
		 * 
		 * comboUsuarios1.removeAllOptions(); comboUsuarios2.removeAllOptions(); comboGrupo.removeAllOptions();
		 * 
		 * this.preencherComboGrupo(document, request, response, idContrato); Collection<EmpregadoDTO> colUsuario = funcionarioService.listEmpregadoContrato(idContrato); ArrayList<EmpregadoDTO>
		 * colUsuario2 = new ArrayList<EmpregadoDTO>();
		 * 
		 * if (colUsuario != null) { for (EmpregadoDTO object : colUsuario) { colUsuario2.add(funcionarioService.restoreByIdEmpregado(object.getIdEmpregado())); } }
		 * comboUsuarios1.addOptions(colUsuario2, "idEmpregado", "nome", null); }else{ comboUsuarios1.removeAllOptions(); comboUsuarios2.removeAllOptions(); comboGrupo.removeAllOptions();
		 * comboGrupo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione")); }
		 */

	}

	public void preencherComboUsuarios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDTO = (RelatorioKpiProdutividadeDTO) document.getBean();
		HTMLSelect comboUsuarios1 = document.getSelectById("primeiraLista");
		HTMLSelect comboUsuarios = document.getSelectById("segundaLista");

		comboUsuarios1.removeAllOptions();
		comboUsuarios.removeAllOptions();
		// UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		ArrayList<Integer> listaGruposUnidades = new ArrayList<Integer>();
		String tipoAgrupamento = relatorioKpiProdutividadeDTO.getSelecionarGrupoUnidade();
		// Restaura o Grupo/Unidade selecionado
		if (relatorioKpiProdutividadeDTO.getListaUsuarios() != null) {
			String[] listaGruposUnidadesTela;
			listaGruposUnidadesTela = relatorioKpiProdutividadeDTO.getListaGrupoUnidade().split(";");
			if (listaGruposUnidadesTela.length != 0) {
				for (String i : listaGruposUnidadesTela) {
					if (!i.equals("")) {
						listaGruposUnidades.add(Integer.parseInt(i));
					}
				}
			}
		}
		if (listaGruposUnidades != null && !listaGruposUnidades.isEmpty()) {

			EmpregadoService funcionarioService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

			ArrayList<EmpregadoDTO> colUsuario2 = new ArrayList<EmpregadoDTO>();
			Set<EmpregadoDTO> EmpregadosNaoRepetidos = new LinkedHashSet<EmpregadoDTO>();

			for (Integer gruposUnidades : listaGruposUnidades) {
				if (tipoAgrupamento.equalsIgnoreCase("grupo")) {
					Collection<EmpregadoDTO> colUsuario = funcionarioService.listEmpregadosByIdGrupo(gruposUnidades);
					for (EmpregadoDTO empregadoDTO : colUsuario) {
						EmpregadosNaoRepetidos.add(empregadoDTO);
					}
				} else {
					Collection<EmpregadoDTO> colUsuario = funcionarioService.listEmpregadosByIdUnidade(gruposUnidades);
					for (EmpregadoDTO empregadoDTO : colUsuario) {
						EmpregadosNaoRepetidos.add(empregadoDTO);
					}
				}
			}
			if (EmpregadosNaoRepetidos != null && !EmpregadosNaoRepetidos.isEmpty()) {
				for (EmpregadoDTO object : EmpregadosNaoRepetidos) {
					colUsuario2.add(funcionarioService.restoreByIdEmpregado(object.getIdEmpregado()));
				}
			}
			comboUsuarios.addOptions(colUsuario2, "idEmpregado", "nome", null);
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	public void gerarRelatorioFormatoPdf(Collection<RelatorioKpiProdutividadeDTO> listaRelatorioMudancaItemConfiguracao, String caminhoJasper, Map<String, Object> parametros, String diretorioReceita,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorioMudancaItemConfiguracao);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioKpiProdutividade" + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioKpiProdutividade" + "_" + usuario.getIdUsuario() + ".pdf')");
	}

	public void gerarRelatorioFormatoXls(Collection<RelatorioKpiProdutividadeDTO> listaRelatorioMudancaItemConfiguracao, Map<String, Object> parametros, String diretorioReceita,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorioMudancaItemConfiguracao);

		parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioKpiProdutividadeXls_subreport1.jasper");

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioKpiProdutividadeXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioKpiProdutividadeXls" + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioKpiProdutividadeXls" + "_" + usuario.getIdUsuario() + ".xls')");

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
