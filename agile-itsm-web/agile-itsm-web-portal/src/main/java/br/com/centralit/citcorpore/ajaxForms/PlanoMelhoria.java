package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.AcaoPlanoMelhoriaDTO;
import br.com.centralit.citcorpore.bean.ObjetivoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.ObjetivoPlanoMelhoriaDTO;
import br.com.centralit.citcorpore.bean.PlanoMelhoriaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AcaoPlanoMelhoriaService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ObjetivoMonitoramentoService;
import br.com.centralit.citcorpore.negocio.ObjetivoPlanoMelhoriaService;
import br.com.centralit.citcorpore.negocio.PlanoMelhoriaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class PlanoMelhoria extends AjaxFormAction {

	UsuarioDTO usuario;
	private  String localeSession = null;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		Collection colForns = fornecedorService.list();
		if (colForns != null) {
			document.getSelectById("idFornecedor").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById("idFornecedor").addOptions(colForns, "idFornecedor", "razaoSocial", null);
		}
	}

	@Override
	public Class getBeanClass() {
		return PlanoMelhoriaDTO.class;
	}

	public void carregaContratos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContratos = contratoService.findByIdFornecedor(planoMelhoriaDTO.getIdFornecedor());
		document.getSelectById("idContrato").removeAllOptions();
		if (colContratos != null) {
			document.getSelectById("idContrato").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById("idContrato").addOptions(colContratos, "idContrato", "numero", null);
		}
	}

	public void gravarPlano(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}			
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		PlanoMelhoriaService planoMelhoriaService = (PlanoMelhoriaService) ServiceLocator.getInstance().getService(PlanoMelhoriaService.class, null);

		planoMelhoriaDTO.setModificadoPor(usuarioDto.getNomeUsuario());
		planoMelhoriaDTO.setUltModificacao(UtilDatas.getDataAtual());
		if (planoMelhoriaDTO.getIdPlanoMelhoria() == null || planoMelhoriaDTO.getIdPlanoMelhoria().intValue() == 0) {
			planoMelhoriaDTO.setCriadoPor(usuarioDto.getNomeUsuario());
			planoMelhoriaDTO.setDataCriacao(UtilDatas.getDataAtual());
			planoMelhoriaService.create(planoMelhoriaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			planoMelhoriaService.update(planoMelhoriaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("location.reload();");
	}

	public void gravarObjetivo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		ObjetivoPlanoMelhoriaService objetivoPlanoMelhoriaService = (ObjetivoPlanoMelhoriaService) ServiceLocator.getInstance().getService(ObjetivoPlanoMelhoriaService.class, null);

		ObjetivoPlanoMelhoriaDTO objetivoPlanoMelhoriaDTO = new ObjetivoPlanoMelhoriaDTO();
		Reflexao.copyPropertyValues(planoMelhoriaDTO, objetivoPlanoMelhoriaDTO);

		objetivoPlanoMelhoriaDTO.setIdPlanoMelhoria(planoMelhoriaDTO.getIdPlanoMelhoriaAux1());
		objetivoPlanoMelhoriaDTO.setModificadoPor(usuarioDto.getNomeUsuario());
		objetivoPlanoMelhoriaDTO.setUltModificacao(UtilDatas.getDataAtual());
		if (objetivoPlanoMelhoriaDTO.getIdObjetivoPlanoMelhoria() == null || objetivoPlanoMelhoriaDTO.getIdObjetivoPlanoMelhoria().intValue() == 0) {
			objetivoPlanoMelhoriaDTO.setCriadoPor(usuarioDto.getNomeUsuario());
			objetivoPlanoMelhoriaDTO.setDataCriacao(UtilDatas.getDataAtual());
			objetivoPlanoMelhoriaService.create(objetivoPlanoMelhoriaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			objetivoPlanoMelhoriaService.update(objetivoPlanoMelhoriaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("formObj");
		form.clear();
	}

	public void gravarAcao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		AcaoPlanoMelhoriaService acaoPlanoMelhoriaService = (AcaoPlanoMelhoriaService) ServiceLocator.getInstance().getService(AcaoPlanoMelhoriaService.class, null);
		ObjetivoPlanoMelhoriaService objetivoPlanoMelhoriaService = (ObjetivoPlanoMelhoriaService) ServiceLocator.getInstance().getService(ObjetivoPlanoMelhoriaService.class, null);

		AcaoPlanoMelhoriaDTO acaoPlanoMelhoriaDTO = new AcaoPlanoMelhoriaDTO();
		Reflexao.copyPropertyValues(planoMelhoriaDTO, acaoPlanoMelhoriaDTO);

		acaoPlanoMelhoriaDTO.setIdObjetivoPlanoMelhoria(planoMelhoriaDTO.getIdObjetivoPlanoMelhoria());

		ObjetivoPlanoMelhoriaDTO objetivoPlanoMelhoriaDTO = new ObjetivoPlanoMelhoriaDTO();
		objetivoPlanoMelhoriaDTO.setIdObjetivoPlanoMelhoria(planoMelhoriaDTO.getIdObjetivoPlanoMelhoria());
		objetivoPlanoMelhoriaDTO = (ObjetivoPlanoMelhoriaDTO) objetivoPlanoMelhoriaService.restore(objetivoPlanoMelhoriaDTO);
		if (objetivoPlanoMelhoriaDTO != null) {
			acaoPlanoMelhoriaDTO.setIdPlanoMelhoria(objetivoPlanoMelhoriaDTO.getIdPlanoMelhoria());
		}
		acaoPlanoMelhoriaDTO.setModificadoPor(usuarioDto.getNomeUsuario());
		acaoPlanoMelhoriaDTO.setUltModificacao(UtilDatas.getDataAtual());		
		if (acaoPlanoMelhoriaDTO.getIdAcaoPlanoMelhoria() == null || acaoPlanoMelhoriaDTO.getIdAcaoPlanoMelhoria().intValue() == 0) {
			acaoPlanoMelhoriaDTO.setCriadoPor(usuarioDto.getNomeUsuario());
			acaoPlanoMelhoriaDTO.setDataCriacao(UtilDatas.getDataAtual());
			acaoPlanoMelhoriaService.create(acaoPlanoMelhoriaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			acaoPlanoMelhoriaService.update(acaoPlanoMelhoriaDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("formObj");
		form.clear();
	}

	public void gravarMonitoramento(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		ObjetivoMonitoramentoService objetivoMonitoramentoService = (ObjetivoMonitoramentoService) ServiceLocator.getInstance().getService(
				ObjetivoMonitoramentoService.class, null);
		
		ObjetivoMonitoramentoDTO objetivoMonitoramentoDTO = new ObjetivoMonitoramentoDTO();
		Reflexao.copyPropertyValues(planoMelhoriaDTO, objetivoMonitoramentoDTO);

		objetivoMonitoramentoDTO.setIdObjetivoPlanoMelhoria(planoMelhoriaDTO.getIdObjetivoPlanoMelhoria());
		
		objetivoMonitoramentoDTO.setModificadoPor(usuarioDto.getNomeUsuario());
		objetivoMonitoramentoDTO.setUltModificacao(UtilDatas.getDataAtual());		
		if (objetivoMonitoramentoDTO.getIdObjetivoMonitoramento() == null || objetivoMonitoramentoDTO.getIdObjetivoMonitoramento().intValue() == 0) {
			objetivoMonitoramentoDTO.setCriadoPor(usuarioDto.getNomeUsuario());
			objetivoMonitoramentoDTO.setDataCriacao(UtilDatas.getDataAtual());			
			objetivoMonitoramentoService.create(objetivoMonitoramentoDTO);
		    document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			objetivoMonitoramentoService.update(objetivoMonitoramentoDTO);
		    document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("formMonitoramento");
		form.clear();		
	}	
	public void editaPlano(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		PlanoMelhoriaService planoMelhoriaService = (PlanoMelhoriaService) ServiceLocator.getInstance().getService(PlanoMelhoriaService.class, null);
		if (planoMelhoriaDTO.getIdPlanoMelhoria() == null || planoMelhoriaDTO.getIdPlanoMelhoria().intValue() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "planoMelhoria.informevazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		carregaContratos(document, request, response);
		planoMelhoriaDTO = (PlanoMelhoriaDTO) planoMelhoriaService.restore(planoMelhoriaDTO);
		document.getForm("form").clear();
		document.getForm("form").setValues(planoMelhoriaDTO);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void editaObjetivo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		ObjetivoPlanoMelhoriaService objetivoPlanoMelhoriaService = (ObjetivoPlanoMelhoriaService) ServiceLocator.getInstance().getService(ObjetivoPlanoMelhoriaService.class, null);
		if (planoMelhoriaDTO.getIdObjetivoPlanoMelhoria() == null || planoMelhoriaDTO.getIdObjetivoPlanoMelhoria().intValue() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "planoMelhoria.objetivo.informevazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		ObjetivoPlanoMelhoriaDTO objetivoPlanoMelhoriaDTO = new ObjetivoPlanoMelhoriaDTO();
		objetivoPlanoMelhoriaDTO.setIdObjetivoPlanoMelhoria(planoMelhoriaDTO.getIdObjetivoPlanoMelhoria());
		objetivoPlanoMelhoriaDTO = (ObjetivoPlanoMelhoriaDTO) objetivoPlanoMelhoriaService.restore(objetivoPlanoMelhoriaDTO);
		objetivoPlanoMelhoriaDTO.setIdPlanoMelhoriaAux1(objetivoPlanoMelhoriaDTO.getIdPlanoMelhoria());
		document.getForm("formObj").clear();
		document.getForm("formObj").setValues(objetivoPlanoMelhoriaDTO);
		document.getElementById("idPlanoMelhoriaAux1").setValue("" + objetivoPlanoMelhoriaDTO.getIdPlanoMelhoria());
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void editaAcao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		AcaoPlanoMelhoriaService acaoPlanoMelhoriaService = (AcaoPlanoMelhoriaService) ServiceLocator.getInstance().getService(AcaoPlanoMelhoriaService.class, null);
		if (planoMelhoriaDTO.getIdAcaoPlanoMelhoria() == null || planoMelhoriaDTO.getIdAcaoPlanoMelhoria().intValue() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "planoMelhoria.objetivo.informevazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		AcaoPlanoMelhoriaDTO acaoPlanoMelhoriaDTO = new AcaoPlanoMelhoriaDTO();
		acaoPlanoMelhoriaDTO.setIdAcaoPlanoMelhoria(planoMelhoriaDTO.getIdAcaoPlanoMelhoria());
		acaoPlanoMelhoriaDTO = (AcaoPlanoMelhoriaDTO) acaoPlanoMelhoriaService.restore(acaoPlanoMelhoriaDTO);
		document.getForm("formAcao").setValues(acaoPlanoMelhoriaDTO);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
	public void editaMonitoramento(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		ObjetivoMonitoramentoService objetivoMonitoramentoService = (ObjetivoMonitoramentoService) ServiceLocator.getInstance().getService(
				ObjetivoMonitoramentoService.class, null);
		if (planoMelhoriaDTO.getIdObjetivoMonitoramento() == null || planoMelhoriaDTO.getIdObjetivoMonitoramento().intValue() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "planoMelhoria.monitoramento.informevazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		ObjetivoMonitoramentoDTO objetivoMonitoramentoDTO = new ObjetivoMonitoramentoDTO();
		objetivoMonitoramentoDTO.setIdObjetivoMonitoramento(planoMelhoriaDTO.getIdObjetivoMonitoramento());
		objetivoMonitoramentoDTO = (ObjetivoMonitoramentoDTO) objetivoMonitoramentoService.restore(objetivoMonitoramentoDTO);
		document.getForm("formMonitoramento").setValues(objetivoMonitoramentoDTO);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
	/**
	 * Faz a impressão do relatório no formato pdf.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Thays.araujo
	 */

	public void imprimirDocumentoPlanoDeMelhoria(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		PlanoMelhoriaDTO planoMelhoriaDTO = (PlanoMelhoriaDTO) document.getBean();
		
		if(planoMelhoriaDTO.getIdPlanoMelhoria()==null){
			document.alert(UtilI18N.internacionaliza(request, "planoMelhoria.informevazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		ObjetivoPlanoMelhoriaDTO objetivoPlanoMelhoriaDto = new ObjetivoPlanoMelhoriaDTO();

		AcaoPlanoMelhoriaDTO acaoPlanoMelhoriaDto = new AcaoPlanoMelhoriaDTO();
		
		ObjetivoMonitoramentoDTO objetivoMonitoramentoDto = new ObjetivoMonitoramentoDTO();

		PlanoMelhoriaService planoMelhoriaService = (PlanoMelhoriaService) ServiceLocator.getInstance().getService(PlanoMelhoriaService.class, null);

		AcaoPlanoMelhoriaService acaoPlanoMelhoriaService = (AcaoPlanoMelhoriaService) ServiceLocator.getInstance().getService(AcaoPlanoMelhoriaService.class, null);

		ObjetivoPlanoMelhoriaService objetivoPlanoMelhoriaService = (ObjetivoPlanoMelhoriaService) ServiceLocator.getInstance().getService(ObjetivoPlanoMelhoriaService.class, null);
		
		ObjetivoMonitoramentoService objetivoMonitoramentoService = (ObjetivoMonitoramentoService) ServiceLocator.getInstance().getService(ObjetivoMonitoramentoService.class, null);

		Collection<ObjetivoPlanoMelhoriaDTO> listObjetivosPlanoMelhoriaPrincipal = null;
		
		Collection<ObjetivoMonitoramentoDTO> listObjetivosMonitoramento = null;

		Collection<AcaoPlanoMelhoriaDTO> listAcaoPlanoMelhoria = null;

		Collection<ObjetivoPlanoMelhoriaDTO> listDeTituloDeObjetoPlanoMelhoria = new ArrayList<ObjetivoPlanoMelhoriaDTO>();
		
		Collection<ObjetivoMonitoramentoDTO> listGeralDeObjetivoMelhoria = new ArrayList<ObjetivoMonitoramentoDTO>();

		Integer contObjetivo = 0;
		Integer contObjetivoMonitoramento = 0;
		Integer contAcao = 0;

		if (planoMelhoriaDTO.getIdPlanoMelhoria() != null) {
			planoMelhoriaDTO = (PlanoMelhoriaDTO) planoMelhoriaService.restore(planoMelhoriaDTO);
			objetivoPlanoMelhoriaDto.setIdPlanoMelhoria(planoMelhoriaDTO.getIdPlanoMelhoria());
			acaoPlanoMelhoriaDto.setIdPlanoMelhoria(planoMelhoriaDTO.getIdPlanoMelhoria());
		}
		if (objetivoPlanoMelhoriaDto.getIdPlanoMelhoria() != null) {
			listObjetivosPlanoMelhoriaPrincipal = objetivoPlanoMelhoriaService.listObjetivosPlanoMelhoria(objetivoPlanoMelhoriaDto);

		}

		if (listObjetivosPlanoMelhoriaPrincipal != null) {
			for (ObjetivoPlanoMelhoriaDTO objPlanoMelhoria : listObjetivosPlanoMelhoriaPrincipal) {
				contAcao = 0;
				ObjetivoPlanoMelhoriaDTO obj =  new ObjetivoPlanoMelhoriaDTO();
				
				contObjetivo++;
				
				obj.setTituloObjetivo(objPlanoMelhoria.getTituloObjetivo());
				
				obj.setSequencialObjetivo(contObjetivo);
				
				objPlanoMelhoria.setSequencialObjetivo(contObjetivo);
				
				planoMelhoriaDTO.setResponsavel(objPlanoMelhoria.getResponsavel());

				if (objPlanoMelhoria.getIdObjetivoPlanoMelhoria() != null) {
					acaoPlanoMelhoriaDto.setIdObjetivoPlanoMelhoria(objPlanoMelhoria.getIdObjetivoPlanoMelhoria());
					listAcaoPlanoMelhoria = acaoPlanoMelhoriaService.listAcaoPlanoMelhoria(acaoPlanoMelhoriaDto);
					objetivoMonitoramentoDto.setIdObjetivoPlanoMelhoria(objPlanoMelhoria.getIdObjetivoPlanoMelhoria());
					listObjetivosMonitoramento = objetivoMonitoramentoService.listObjetivosMonitoramento(objetivoMonitoramentoDto);
				}

				if (listAcaoPlanoMelhoria != null) {
					for (AcaoPlanoMelhoriaDTO acao : listAcaoPlanoMelhoria) {
						contAcao++;
						acao.setSequencialObjetivo(objPlanoMelhoria.getSequencialObjetivo());
						acao.setSequencialAcao(contAcao);
						acao.setDetalhamentoAcao(acao.getDetalhamentoAcao());
						acao.setDataInicio(acao.getDataInicio());
						acao.setDataFim(acao.getDataInicio());
						acao.setResponsavel(acao.getResponsavel());
						acao.setResultadoEsperadoPlanoMelhoria(objPlanoMelhoria.getResultadoEsperado());
						acao.setMedicaoPlanoMelhoria(objPlanoMelhoria.getMedicao());
					}
				}
				
				if(listObjetivosMonitoramento!=null){
					for(ObjetivoMonitoramentoDTO objetivoMonitoramento : listObjetivosMonitoramento){
						contObjetivoMonitoramento++;
						objetivoMonitoramento.setSequecialObjetivoMonitoramento(contObjetivoMonitoramento);
						objetivoMonitoramento.setTituloObjetivoPlanoMelhoria(objPlanoMelhoria.getTituloObjetivo());
					}
					
					listGeralDeObjetivoMelhoria.addAll(listObjetivosMonitoramento);
					
				}
				
				
				listDeTituloDeObjetoPlanoMelhoria.add(obj);
				objPlanoMelhoria.setListObjetivosPlanoMelhoria(listDeTituloDeObjetoPlanoMelhoria);
				objPlanoMelhoria.setListAcaoPlanoMelhoria(listAcaoPlanoMelhoria);
				objPlanoMelhoria.setListObjetivosMonitoramento(listObjetivosMonitoramento);

			}
			
		}
		
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "PlanoMelhoriaServicos.jasper";
		String caminhoSubRelatorioJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS");
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		
		usuario = WebUtil.getUsuario(request);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("SUBREPORT_DIR", caminhoSubRelatorioJasper);
		parametros.put("purpose", planoMelhoriaDTO.getObjetivo());
		parametros.put("overview", planoMelhoriaDTO.getVisaoGeral());
		parametros.put("scope", planoMelhoriaDTO.getEscopo());
		parametros.put("vision", planoMelhoriaDTO.getVisao());
		parametros.put("mission", planoMelhoriaDTO.getMissao());
		parametros.put("serviceImprovementPlan", planoMelhoriaDTO.getTitulo());
		parametros.put("startDate", planoMelhoriaDTO.getDataInicio());
		parametros.put("endDate", planoMelhoriaDTO.getDataFim());
		parametros.put("responsible", planoMelhoriaDTO.getResponsavel());
		parametros.put("listObjetivosMonitoramento", listGeralDeObjetivoMelhoria);
		parametros.put("caminhoLogo", CITCorporeUtil.CAMINHO_REAL_APP +"//imagens//logo//logo.png");
		

		JRDataSource dataSource = null;

		if (listObjetivosPlanoMelhoriaPrincipal != null) {

			dataSource = new JRBeanCollectionDataSource(listObjetivosPlanoMelhoriaPrincipal);
		}

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/PlanoMelhoriaServicos" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS + "/PlanoMelhoriaServicos"
				+ strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		 document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}
}
