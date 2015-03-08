package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
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
import br.com.centralit.citcorpore.bean.CausaIncidenteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.RelatorioKpiProdutividadeDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CausaIncidenteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
@SuppressWarnings("rawtypes")
public class RelatorioQI03QI04ProgramacaoDefensivaEficacia extends AjaxFormAction{

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

	
	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return RelatorioKpiProdutividadeDTO.class;
	}
	
	/**
	 * Preenche combo de contrato
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception { HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);
	}
	
	/**
	 * Preencher combo de grupo
	 * @param document
	 * @param request
	 * @param response
	 * @param idContrato
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, int idContrato) throws Exception {

		HTMLSelect comboGrupo1 = document.getSelectById("primeiraListaGrupo");
		HTMLSelect comboGrupo2 = document.getSelectById("segundaListaGrupo");

		comboGrupo1.removeAllOptions();
		comboGrupo2.removeAllOptions();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupo = grupoService.listGrupoByIdContrato(idContrato);
		comboGrupo1.addOptions(colGrupo, "idGrupo", "Nome", null);
	}
	
	/**
	 *Preenche combo de grupo por  unidade
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	@SuppressWarnings("unused")
	public void preencherGrupoUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDto = (RelatorioKpiProdutividadeDTO) document.getBean();

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
		
		
		if(relatorioKpiProdutividadeDto.getIdContrato()!=null){
			String UnidadeGrupo = relatorioKpiProdutividadeDto.getSelecionarGrupoUnidade();
			int idContrato = relatorioKpiProdutividadeDto.getIdContrato();

			if (relatorioKpiProdutividadeDto.getSelecionarGrupoUnidade() != null && relatorioKpiProdutividadeDto.getSelecionarGrupoUnidade().equalsIgnoreCase("grupo")) {
				this.preencherComboGrupo(document, request, response, idContrato);
			} else {
				preencherComboUnidade(document, request, response, idContrato);
			}
		}else{
			comboGrupo1.removeAllOptions();
			comboGrupo2.removeAllOptions();
			
			comboUnidade1.removeAllOptions();
			comboUnidade2.removeAllOptions();
		}
		
	

	}
	
	/**Preencher combo de unidade
	 * @param document
	 * @param request
	 * @param response
	 * @param idContrato
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, int idContrato) throws Exception {

		HTMLSelect comboUnidade1 = document.getSelectById("primeiraListaUnidade");
		HTMLSelect comboUnidade2 = document.getSelectById("segundaListaUnidade");

		comboUnidade1.removeAllOptions();
		comboUnidade2.removeAllOptions();

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		Collection colGrupo = unidadeService.listUnidadePorContrato(idContrato);
		comboUnidade1.addOptions(colGrupo, "idUnidade", "Nome", null);
	}
	
	
	/**
	 * Preenche combo de usuario
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboUsuarios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDTO = (RelatorioKpiProdutividadeDTO) document.getBean();
		HTMLSelect comboUsuarios1 = document.getSelectById("primeiraLista");
		HTMLSelect comboUsuarios = document.getSelectById("segundaLista");

		comboUsuarios1.removeAllOptions();
		comboUsuarios.removeAllOptions();
		//UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
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
		if(listaGruposUnidades!=null && !listaGruposUnidades.isEmpty()){
			
			EmpregadoService funcionarioService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			
			
			ArrayList<EmpregadoDTO> colUsuario2 = new ArrayList<EmpregadoDTO>();
			Set<EmpregadoDTO> EmpregadosNaoRepetidos = new LinkedHashSet<EmpregadoDTO>();
			
			for (Integer gruposUnidades : listaGruposUnidades) {
				if(tipoAgrupamento.equalsIgnoreCase("grupo")){
					Collection<EmpregadoDTO> colUsuario = funcionarioService.listEmpregadosByIdGrupo(gruposUnidades);
					for (EmpregadoDTO empregadoDTO : colUsuario) {
						EmpregadosNaoRepetidos.add(empregadoDTO);
					}
				}else{
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
	
	/**
	 * Preencher Combo de Tipo Demanda Serviço.
	 * 
	 * @param document
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 * 
	 */
	public void preencherComboTipoDemanda(DocumentHTML document, HttpServletRequest request,HttpServletResponse response) throws ServiceException, Exception {

		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);

		HTMLSelect idTipoDemandaServico = (HTMLSelect) document.getSelectById("idTipoDemandaServico");

		idTipoDemandaServico.removeAllOptions();

		idTipoDemandaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		Collection<TipoDemandaServicoDTO> listTipoDemanda = tipoDemandaService.listSolicitacoes();

		if (listTipoDemanda != null) {
			idTipoDemandaServico.addOptions(listTipoDemanda, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
		}
	}
	
	
	/**
	 * Metodo para iniciar a impressão do relatorio
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		usuario = WebUtil.getUsuario(request);
		RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDTO = (RelatorioKpiProdutividadeDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		SolicitacaoServicoService solcitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		CausaIncidenteService causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		
		ArrayList<EmpregadoDTO> listaEmpregados = new ArrayList<EmpregadoDTO>();
		
		ArrayList<ServicoDTO> listaServicos = new ArrayList<ServicoDTO>();
		
		ArrayList<CausaIncidenteDTO> listaCausaIncidentes = new ArrayList<CausaIncidenteDTO>();
		
		Collection<RelatorioKpiProdutividadeDTO> listaRelatorio = new ArrayList<RelatorioKpiProdutividadeDTO>();
		
		if (relatorioKpiProdutividadeDTO.getListaServicosString() != null) {
			String[] listaServicosTela;
			listaServicosTela = relatorioKpiProdutividadeDTO.getListaServicosString().split(";");
			if (listaServicosTela.length != 0) {
				for (String servico : listaServicosTela) {
					if (!servico.equals("")) {
						ServicoDTO servicoDto = servicoService.findById(Integer.valueOf(servico));
						if (servicoDto != null) {
							listaServicos.add(servicoDto);
						}
					}
				}
				relatorioKpiProdutividadeDTO.setListaServicos(listaServicos);
			}
		}
		if (relatorioKpiProdutividadeDTO.getListaUsuarios() != null) {
			String[] listaUsuariosTela;
			listaUsuariosTela = relatorioKpiProdutividadeDTO.getListaUsuarios().split(";");
			if (listaUsuariosTela.length != 0) {
				for (String empregado : listaUsuariosTela) {
					if (!empregado.equals("")) {
						EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(Integer.valueOf(empregado));
						if (empregadoDto != null) {
							listaEmpregados.add(empregadoDto);
						}
					}
				}
				relatorioKpiProdutividadeDTO.setListaEmpregado(listaEmpregados);
			}
		}
		
		if (relatorioKpiProdutividadeDTO.getListaCausaString() != null) {
			String[] listaCAusasTela;
			listaCAusasTela = relatorioKpiProdutividadeDTO.getListaCausaString().split(";");
			if (listaCAusasTela.length != 0) {
				for (String causaIncidente : listaCAusasTela) {
					if (!causaIncidente.equals("")) {
						CausaIncidenteDTO causaIncidenteDto = new CausaIncidenteDTO();
						causaIncidenteDto.setIdCausaIncidente(Integer.valueOf(causaIncidente));
						 causaIncidenteDto = (CausaIncidenteDTO) causaIncidenteService.restore(causaIncidenteDto);
						if (causaIncidenteDto != null) {
							listaCausaIncidentes.add(causaIncidenteDto);
						}
					}
				}
				relatorioKpiProdutividadeDTO.setListaCausaIncidentes(listaCausaIncidentes);
			}
		}
		
		listaRelatorio = solcitacaoService.listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito(relatorioKpiProdutividadeDTO);
		
		if (listaRelatorio != null && !listaRelatorio.isEmpty()) {

			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioQI03QI04ProgramacaoDefensivaEficaciaXls.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioQI03QI04ProgramacaoDefensivaEficacia"));

			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			parametros.put("dataInicio", relatorioKpiProdutividadeDTO.getDataInicio());
			parametros.put("dataFim", relatorioKpiProdutividadeDTO.getDataFim());
			parametros.put("Logo", LogoRel.getFile());
			parametros.put("titulo", UtilI18N.internacionaliza(request, "citcorpore.comum.titulo"));			
			
			if(relatorioKpiProdutividadeDTO.getIdContrato() != null && relatorioKpiProdutividadeDTO.getIdContrato().intValue() > 0){
				parametros.put("contrato", this.getContrato(relatorioKpiProdutividadeDTO.getIdContrato()));
			}

			if (relatorioKpiProdutividadeDTO.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {

				this.gerarRelatorioFormatoPdf(listaRelatorio, caminhoJasper, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);

			} else {

				this.gerarRelatorioFormatoXls(listaRelatorio, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);

			}
		} else {
			document.executeScript("reportEmpty();");
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	/**
	 * Metodo para imprimir o relatorio no formato .pdf
	 * @param listaRelatorio
	 * @param caminhoJasper
	 * @param parametros
	 * @param diretorioReceita
	 * @param document
	 * @param diretorioRelativoOS
	 * @param usuario
	 * @throws Exception
	 */
	public void gerarRelatorioFormatoPdf(Collection<RelatorioKpiProdutividadeDTO> listaRelatorio, String caminhoJasper, Map<String, Object> parametros, String diretorioReceita,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		parametros.put("SUBREPORT_DIR", CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS")+ "RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo_subreport1.jasper");

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorio);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo" + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodo" + "_" + usuario.getIdUsuario() + ".pdf')");
	}

	/**
	 * Metodo para imprimir relatorio no formato .xls
	 * @param listaRelatorio
	 * @param parametros
	 * @param diretorioReceita
	 * @param document
	 * @param diretorioRelativoOS
	 * @param usuario
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void gerarRelatorioFormatoXls(Collection<RelatorioKpiProdutividadeDTO> listaRelatorio, Map<String, Object> parametros, String diretorioReceita, DocumentHTML document,
			String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorio);


		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS")+ "RelatorioQI03QI04ProgramacaoDefensivaEficaciaXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioQI03QI04ProgramacaoDefensivaEficaciaXls" + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS+ "/RelatorioQI03QI04ProgramacaoDefensivaEficaciaXls" + "_" + usuario.getIdUsuario() + ".xls')");

	}
	
	private String getContrato(Integer id) throws ServiceException, Exception{
		ContratoDTO contrato = new ContratoDTO();
		contrato.setIdContrato(id);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		contrato = (ContratoDTO) contratoService.restore(contrato);
		if(contrato != null){
			return contrato.getNumero();
		}
		return null;
	}
}
