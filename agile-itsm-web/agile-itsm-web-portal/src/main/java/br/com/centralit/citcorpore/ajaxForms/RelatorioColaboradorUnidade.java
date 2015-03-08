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
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.RelatorioColaboradorUnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RelatorioColaboradorUnidade extends AjaxFormAction {

	private UsuarioDTO usuario;

	@Override
	public Class getBeanClass() {
		return RelatorioColaboradorUnidadeDTO.class;
	}
	
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
		RelatorioColaboradorUnidadeDTO relatorioColaboradorUnidadeDto = (RelatorioColaboradorUnidadeDTO) document.getBean();
		HttpSession session = ((HttpServletRequest) request).getSession();

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		Collection<EmpregadoDTO> empregados = empregadoService.listEmpregadoByContratoAndUnidadeAndEmpregados(
				relatorioColaboradorUnidadeDto.getIdContrato(), relatorioColaboradorUnidadeDto.getIdUnidade(), 
				relatorioColaboradorUnidadeDto.getIdColaborador(), WebUtil.getUsuario(request),
				listUnidadeContrato(document, request, response));
		
		Collection<RelatorioColaboradorUnidadeDTO> listaParaEnvio = new ArrayList<RelatorioColaboradorUnidadeDTO>();
		
		if (empregados != null) {
			for (EmpregadoDTO empregado : empregados) {
				RelatorioColaboradorUnidadeDTO novoFuncionario = new RelatorioColaboradorUnidadeDTO();
				
				if (empregado.getIdUnidade() != null) {
					UnidadeDTO unidadeDto = new UnidadeDTO();
					unidadeDto.setIdUnidade(empregado.getIdUnidade());
					unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);
					
					novoFuncionario.setNomeUnidade(unidadeDto.getNome());					
				}

				novoFuncionario.setNomeColaborador(empregado.getNome());
				novoFuncionario.setEmailColaborador(empregado.getEmail());
				novoFuncionario.setTelefoneColaborador(empregado.getTelefone());
				
				listaParaEnvio.add(novoFuncionario);
			}
		}
		
		if (listaParaEnvio != null && !listaParaEnvio.isEmpty()) {
			String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioColaboradorUnidade.jasper";
			String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

			parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioUnidadeColaborador.titulo"));

			parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
			parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
			if(usuario != null){
				parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
			}

			parametros.put("contrato", this.getContrato(relatorioColaboradorUnidadeDto.getIdContrato()));
			if (relatorioColaboradorUnidadeDto.getIdUnidade() != null) {
				parametros.put("unidade", this.getUnidade(relatorioColaboradorUnidadeDto.getIdUnidade()));
			}else{
				 parametros.put("unidade",  UtilI18N.internacionaliza(request, "citcorpore.comum.todas") );
			}
			
			parametros.put("Logo", LogoRel.getFile());
			parametros.put("titulo", UtilI18N.internacionaliza(request, "citcorpore.comum.titulo"));
			
    	    if (relatorioColaboradorUnidadeDto.getIdColaborador() == null){
    	        parametros.put("colaborador",  UtilI18N.internacionaliza(request, "citcorpore.comum.todos") );
    	    }
    	    else{
    	        parametros.put("colaborador",  UtilI18N.internacionaliza(request, "citcorpore.comum.filtrados") );
    	    }

			if (relatorioColaboradorUnidadeDto.getFormatoArquivoRelatorio().equalsIgnoreCase("pdf")) {
				this.gerarRelatorioFormatoPdf(listaParaEnvio, caminhoJasper, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);
			} else {
				this.gerarRelatorioFormatoXls(listaParaEnvio, parametros, diretorioReceita, document, diretorioRelativoOS, usuario);
			}
		} else {
			document.executeScript("reportEmpty();");
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	public void preencherComboContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		comboContrato.removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.listAtivos();
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboContrato.addOptions(colContrato, "idContrato", "numero", null);
	}
	
	public void carregaUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RelatorioColaboradorUnidadeDTO relatorioColaboradorUnidadeDto = (RelatorioColaboradorUnidadeDTO) document.getBean();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

		HTMLSelect comboUnidadeMultContratos = (HTMLSelect) document.getSelectById("idUnidade");
		inicializarCombo(comboUnidadeMultContratos, request);

		Integer idContrato = relatorioColaboradorUnidadeDto.getIdContrato();

		if (idContrato != null) {
			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);
			if (unidades != null) {
				for (UnidadeDTO unidade : unidades) {
					if (unidade.getDataFim() == null) {
						comboUnidadeMultContratos.addOption(Util.tratarAspasSimples(unidade.getIdUnidade().toString()), Util.tratarAspasSimples(unidade.getNomeNivel()));
					}
				}
			}
		}
	}
	
	public ArrayList<UnidadeDTO> listUnidadeContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RelatorioColaboradorUnidadeDTO relatorioColaboradorUnidadeDto = (RelatorioColaboradorUnidadeDTO) document.getBean();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

		ArrayList<UnidadeDTO> list = new ArrayList<>();

		Integer idContrato = relatorioColaboradorUnidadeDto.getIdContrato();

		if (idContrato != null) {
			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);
			if (unidades != null) {
				for (UnidadeDTO unidade : unidades) {
					if (unidade.getDataFim() == null) {
						list.add(unidade);
					}
				}
			}
		}
		return list;
	}
	
	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void gerarRelatorioFormatoPdf(Collection<RelatorioColaboradorUnidadeDTO> listaRelatorio, String caminhoJasper, Map<String, Object> parametros, String diretorioReceita,
			DocumentHTML document, String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		Integer numeroControle = WebUtil.getRandomNumber();

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorio);

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioColaboradorUnidade" + "_" + numeroControle + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioColaboradorUnidade" + "_" + numeroControle + ".pdf')");
	}

	public void gerarRelatorioFormatoXls(Collection<RelatorioColaboradorUnidadeDTO> listaRelatorio, Map<String, Object> parametros, String diretorioReceita, DocumentHTML document,
			String diretorioRelativoOS, UsuarioDTO usuario) throws Exception {

		Integer numeroControle = WebUtil.getRandomNumber();

		JRDataSource dataSource = new JRBeanCollectionDataSource(listaRelatorio);

		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioColaboradorUnidade.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioColaboradorUnidade" + "_" + numeroControle + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioColaboradorUnidade" + "_" + numeroControle + ".xls')");

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

	private String getUnidade(Integer id) throws ServiceException, Exception {

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		
		UnidadeDTO unidadeDto = new UnidadeDTO();
		unidadeDto.setIdUnidade(id);
		unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);

		if (unidadeDto != null) {
			return unidadeDto.getNome();
		}

		return null;
	}
	
}
