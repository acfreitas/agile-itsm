package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.TipoServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RelatorioControlePercentualQuantitativoSla extends AjaxFormAction{
	UsuarioDTO usuario;
	private  String localeSession = null;
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.list();
		document.getSelectById("idContrato").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);

		document.getSelectById("idGrupoAtual").removeAllOptions();
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupo = grupoService.listarGruposAtivos();
		document.getSelectById("idGrupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idGrupoAtual").addOptions(colGrupo, "idGrupo", "nome", null);

		document.getSelectById("idPrioridade").removeAllOptions();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		Collection colPrioridade = prioridadeService.list();
		document.getSelectById("idPrioridade").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idPrioridade").addOptions(colPrioridade, "idPrioridade", "nomePrioridade", null);

		document.getSelectById("idTipoServico").removeAllOptions();
		TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);
		Collection colTipos = tipoServicoService.list();
		document.getSelectById("idTipoServico").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idTipoServico").addOptions(colTipos, "idTipoServico", "nomeTipoServico", null);
		
		HTMLSelect comboSituacao = document.getSelectById("situacao");
		comboSituacao.removeAllOptions();
		comboSituacao.addOption("",  "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		for (SituacaoSolicitacaoServico situacao : SituacaoSolicitacaoServico.values()) {
			comboSituacao.addOption(situacao.name(), UtilI18N.internacionaliza(request, "solicitacaoServico.situacao." + situacao.name()));
		}
		
		String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
		StringBuilder objeto;
		if ((UNIDADE_AUTOCOMPLETE!=null)&&(UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S"))){
			objeto = new StringBuilder();
			objeto.append("<input type='text' name='unidadeDes' id='unidadeDes' style='width: 100%;' onkeypress='onkeypressUnidadeDes();'>");
			objeto.append("<input type='hidden' name='idUnidade' id='idUnidade' value='0'/>");
			document.getElementById("divUnidade").setInnerHTML(objeto.toString());
			document.executeScript("montaParametrosAutocompleteUnidade()");
		} else {
			objeto = new StringBuilder();
			objeto.append("<select name='idUnidade' id = 'idUnidade' onclick='verificaContrato();' class='Valid[Required] Description[colaborador.cadastroUnidade]'></select>");
			document.getElementById("divUnidade").setInnerHTML(objeto.toString());
		}
		
		this.preencherComboOrigem(document, request, response);
		
		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		HTMLSelect idTipoDemandaServico = (HTMLSelect) document.getSelectById("idTipoDemandaServico");
		idTipoDemandaServico.removeAllOptions();
		idTipoDemandaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		Collection col = tipoDemandaService.listSolicitacoes();
		if (col != null)
			idTipoDemandaServico.addOptions(col, "idTipoDemandaServico", "nomeTipoDemandaServico", null);

		
		document.getSelectById("sla").removeAllOptions();
		document.getSelectById("sla").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		ArrayList<SolicitacaoServicoDTO> colSLA = (ArrayList<SolicitacaoServicoDTO>) solicitacaoServicoService.listarSLA();
			if (colSLA != null) {
			for (SolicitacaoServicoDTO solicitacaoServico : colSLA)
				if (solicitacaoServico.getPrazoHH() < 10 && solicitacaoServico.getPrazoMM() < 10) {
					document.getSelectById("sla").addOption(solicitacaoServico.getPrazoHH()+":"+solicitacaoServico.getPrazoMM(),"0"+solicitacaoServico.getPrazoHH()+"h "+"0"+solicitacaoServico.getPrazoMM()+"m");
				}
				else if(solicitacaoServico.getPrazoHH() < 10 && solicitacaoServico.getPrazoMM() > 10) {
					document.getSelectById("sla").addOption(solicitacaoServico.getPrazoHH()+":"+solicitacaoServico.getPrazoMM(),"0"+solicitacaoServico.getPrazoHH()+"h "+solicitacaoServico.getPrazoMM()+"m");
				}
				else if(solicitacaoServico.getPrazoHH() > 9 && solicitacaoServico.getPrazoMM() < 10) {
					document.getSelectById("sla").addOption(solicitacaoServico.getPrazoHH()+":"+solicitacaoServico.getPrazoMM(),solicitacaoServico.getPrazoHH()+"h "+"0"+solicitacaoServico.getPrazoMM()+"m");
				}
				else{
					document.getSelectById("sla").addOption(solicitacaoServico.getPrazoHH()+":"+solicitacaoServico.getPrazoMM(),solicitacaoServico.getPrazoHH()+"h "+solicitacaoServico.getPrazoMM()+"m");
				}
		}
	}

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	public void imprimirRelatorioControleSla(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		TipoServicoDTO tipoServicoDTO = new TipoServicoDTO();
		TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);

		PrioridadeDTO prioridadeDto = new PrioridadeDTO();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);

		GrupoDTO grupoDto = new GrupoDTO();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		
		UnidadeDTO unidadeDTO = new UnidadeDTO();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		
		OrigemAtendimentoDTO origemAtendimentoDTO = new OrigemAtendimentoDTO();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		
		ServicoDTO servicoDto = new ServicoDTO();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		
		TipoDemandaServicoService  tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		TipoDemandaServicoDTO tipoDemandaDto = new TipoDemandaServicoDTO();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoBean = new SolicitacaoServicoDTO();	
		solicitacaoServicoBean = solicitacaoServicoService.relatorioControlePercentualQuantitativoSla(solicitacaoServicoDTO);

		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControlePercentualQuantitativoSla.jasper";
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		
		/* Desenvolvedor: Rodrigo Pecci - Data: 02/11/2013 - Horário: 15h02min - ID Citsmart: 120770
		 * Motivo/Comentário: A internacionalização estava sendo feita antes do get das porcentagens, isso fazia perder as traduções.
		 */
		parametros = solicitacaoServicoBean.getMapPorcentagemSla();
		
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);		
		
		if (solicitacaoServicoBean == null || solicitacaoServicoBean.getMapPorcentagemSla() == null ||  solicitacaoServicoBean.getMapPorcentagemSla().size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioSla.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("numero", solicitacaoServicoDTO.getIdSolicitacaoServico());
		parametros.put("solicitante", solicitacaoServicoDTO.getNomeSolicitante());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());

		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}

		if (solicitacaoServicoDTO.getIdPrioridade() != null && !solicitacaoServicoDTO.getIdPrioridade().equals(-1)) {
			prioridadeDto.setIdPrioridade(solicitacaoServicoDTO.getIdPrioridade());
			prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
			solicitacaoServicoDTO.setPrioridade(prioridadeDto.getNomePrioridade());
			parametros.put("prioridade", solicitacaoServicoDTO.getPrioridade());
		} else {
			parametros.put("prioridade", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}

		if (solicitacaoServicoDTO.getIdGrupoAtual() != null && !solicitacaoServicoDTO.getIdGrupoAtual().equals(-1)) {
			grupoDto.setIdGrupo(solicitacaoServicoDTO.getIdGrupoAtual());
			grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
			solicitacaoServicoDTO.setGrupoAtual(grupoDto.getSigla());
			parametros.put("grupoSolucionador", solicitacaoServicoDTO.getGrupoAtual());
		} else {
			parametros.put("grupoSolucionador", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getIdUnidade() != null && !solicitacaoServicoDTO.getIdUnidade().equals(-1) && solicitacaoServicoDTO.getIdUnidade() != 0) {
			unidadeDTO.setIdUnidade(solicitacaoServicoDTO.getIdUnidade());
			 unidadeDTO = (UnidadeDTO) unidadeService.restore(unidadeDTO);			
			solicitacaoServicoDTO.setNomeUnidadeSolicitante(unidadeDTO.getNomeNivel());
			parametros.put("unidade", solicitacaoServicoDTO.getNomeUnidadeSolicitante());
		} else {
			parametros.put("unidade", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getIdOrigem() != null && !solicitacaoServicoDTO.getIdOrigem().equals(-1)) {
			origemAtendimentoDTO.setIdOrigem(solicitacaoServicoDTO.getIdOrigem());
			origemAtendimentoDTO = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemAtendimentoDTO);			
			solicitacaoServicoDTO.setOrigem(origemAtendimentoDTO.getDescricao());;
			parametros.put("origem", solicitacaoServicoDTO.getOrigem());
		} else {
			parametros.put("origem", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getIdServico() != null && !solicitacaoServicoDTO.getIdServico().equals(-1)) {
			servicoDto.setIdServico(solicitacaoServicoDTO.getIdServico());
			servicoDto = (ServicoDTO) servicoService.restore(servicoDto);			
			solicitacaoServicoDTO.setServico(servicoDto.getNomeServico());
			parametros.put("servico", solicitacaoServicoDTO.getServico());
		} else {
			parametros.put("servico", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
	
		if (solicitacaoServicoDTO.getIdTipoServico() != null && !solicitacaoServicoDTO.getIdTipoServico().equals(-1)) {
			tipoServicoDTO.setIdTipoServico(solicitacaoServicoDTO.getIdTipoServico());
			tipoServicoDTO = (TipoServicoDTO) tipoServicoService.restore(tipoServicoDTO);
			solicitacaoServicoDTO.setNomeTipoServico(tipoServicoDTO.getNomeTipoServico());
			parametros.put("tipoServico", solicitacaoServicoDTO.getNomeTipoServico());
		} else {
			parametros.put("tipoServico", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getIdTipoDemandaServico() != null && !solicitacaoServicoDTO.getIdTipoDemandaServico().equals(-1)) {
			tipoDemandaDto.setIdTipoDemandaServico(solicitacaoServicoDTO.getIdTipoDemandaServico());
			tipoDemandaDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaDto);
			solicitacaoServicoDTO.setNomeTipoDemandaServico(tipoDemandaDto.getNomeTipoDemandaServico());
			parametros.put("classificao", solicitacaoServicoDTO.getNomeTipoDemandaServico());
		} else {
			parametros.put("classificao", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getSituacao()!= null && !solicitacaoServicoDTO.getSituacao().trim().equalsIgnoreCase("")) {
			parametros.put("situacao", UtilI18N.internacionaliza(request, "solicitacaoServico.situacao." + Enumerados.SituacaoSolicitacaoServico.valueOf(solicitacaoServicoDTO.getSituacao())));
		} else {
			parametros.put("situacao", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}

		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros);
		JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioControlePercentualQuantitativoSla" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioControlePercentualQuantitativoSla" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void imprimirRelatorioControleSlaXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		TipoServicoDTO tipoServicoDTO = new TipoServicoDTO();
		TipoServicoService tipoServicoService = (TipoServicoService) ServiceLocator.getInstance().getService(TipoServicoService.class, null);

		PrioridadeDTO prioridadeDto = new PrioridadeDTO();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);

		GrupoDTO grupoDto = new GrupoDTO();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		
		UnidadeDTO unidadeDTO = new UnidadeDTO();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		
		OrigemAtendimentoDTO origemAtendimentoDTO = new OrigemAtendimentoDTO();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		
		ServicoDTO servicoDto = new ServicoDTO();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		
		TipoDemandaServicoService  tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		TipoDemandaServicoDTO tipoDemandaDto = new TipoDemandaServicoDTO();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoBean = new SolicitacaoServicoDTO();	
		solicitacaoServicoBean = solicitacaoServicoService.relatorioControlePercentualQuantitativoSla(solicitacaoServicoDTO);

		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		Map<String, Object> parametros = new HashMap<String, Object>();
		
		/* Desenvolvedor: Rodrigo Pecci - Data: 13/11/2013 - Horário: 09h59min - ID Citsmart: 124212
		 * Motivo/Comentário: A internacionalização estava sendo feita antes do get das porcentagens, isso fazia perder as traduções.
		 */
		parametros = solicitacaoServicoBean.getMapPorcentagemSla();
		
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		if (solicitacaoServicoBean == null || solicitacaoServicoBean.getMapPorcentagemSla() == null ||  solicitacaoServicoBean.getMapPorcentagemSla().size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioSla.titulo"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("numero", solicitacaoServicoDTO.getIdSolicitacaoServico());
		parametros.put("solicitante", solicitacaoServicoDTO.getNomeSolicitante());
		parametros.put("dataInicio", solicitacaoServicoDTO.getDataInicio());
		parametros.put("dataFim", solicitacaoServicoDTO.getDataFim());
		parametros.put("Logo", LogoRel.getFile());

		if (solicitacaoServicoDTO.getIdContrato() != null) {
			contratoDto.setIdContrato(solicitacaoServicoDTO.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}

		if (solicitacaoServicoDTO.getIdPrioridade() != null && !solicitacaoServicoDTO.getIdPrioridade().equals(-1)) {
			prioridadeDto.setIdPrioridade(solicitacaoServicoDTO.getIdPrioridade());
			prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
			solicitacaoServicoDTO.setPrioridade(prioridadeDto.getNomePrioridade());
			parametros.put("prioridade", solicitacaoServicoDTO.getPrioridade());
		} else {
			parametros.put("prioridade", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}

		if (solicitacaoServicoDTO.getIdGrupoAtual() != null && !solicitacaoServicoDTO.getIdGrupoAtual().equals(-1)) {
			grupoDto.setIdGrupo(solicitacaoServicoDTO.getIdGrupoAtual());
			grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
			solicitacaoServicoDTO.setGrupoAtual(grupoDto.getSigla());
			parametros.put("grupoSolucionador", solicitacaoServicoDTO.getGrupoAtual());
		} else {
			parametros.put("grupoSolucionador", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getIdUnidade() != null && !solicitacaoServicoDTO.getIdUnidade().equals(-1) && solicitacaoServicoDTO.getIdUnidade()!= 0) {
			unidadeDTO.setIdUnidade(solicitacaoServicoDTO.getIdUnidade());
			 unidadeDTO = (UnidadeDTO) unidadeService.restore(unidadeDTO);			
			solicitacaoServicoDTO.setNomeUnidadeSolicitante(unidadeDTO.getNomeNivel());
			parametros.put("unidade", solicitacaoServicoDTO.getNomeUnidadeSolicitante());
		} else {
			parametros.put("unidade", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getIdOrigem() != null && !solicitacaoServicoDTO.getIdOrigem().equals(-1)) {
			origemAtendimentoDTO.setIdOrigem(solicitacaoServicoDTO.getIdOrigem());
			origemAtendimentoDTO = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemAtendimentoDTO);			
			solicitacaoServicoDTO.setOrigem(origemAtendimentoDTO.getDescricao());;
			parametros.put("origem", solicitacaoServicoDTO.getOrigem());
		} else {
			parametros.put("origem", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getIdServico() != null && !solicitacaoServicoDTO.getIdServico().equals(-1)) {
			servicoDto.setIdServico(solicitacaoServicoDTO.getIdServico());
			servicoDto = (ServicoDTO) servicoService.restore(servicoDto);			
			solicitacaoServicoDTO.setServico(servicoDto.getNomeServico());
			parametros.put("servico", solicitacaoServicoDTO.getServico());
		} else {
			parametros.put("servico", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
	
		if (solicitacaoServicoDTO.getIdTipoServico() != null && !solicitacaoServicoDTO.getIdTipoServico().equals(-1)) {
			tipoServicoDTO.setIdTipoServico(solicitacaoServicoDTO.getIdTipoServico());
			tipoServicoDTO = (TipoServicoDTO) tipoServicoService.restore(tipoServicoDTO);
			solicitacaoServicoDTO.setNomeTipoServico(tipoServicoDTO.getNomeTipoServico());
			parametros.put("tipoServico", solicitacaoServicoDTO.getNomeTipoServico());
		} else {
			parametros.put("tipoServico", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getIdTipoDemandaServico() != null && !solicitacaoServicoDTO.getIdTipoDemandaServico().equals(-1)) {
			tipoDemandaDto.setIdTipoDemandaServico(solicitacaoServicoDTO.getIdTipoDemandaServico());
			tipoDemandaDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaDto);
			solicitacaoServicoDTO.setNomeTipoDemandaServico(tipoDemandaDto.getNomeTipoDemandaServico());
			parametros.put("classificao", solicitacaoServicoDTO.getNomeTipoDemandaServico());
		} else {
			parametros.put("classificao", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		if (solicitacaoServicoDTO.getSituacao()!= null && !solicitacaoServicoDTO.getSituacao().trim().equalsIgnoreCase("")) {
			parametros.put("situacao", UtilI18N.internacionaliza(request, "solicitacaoServico.situacao." + Enumerados.SituacaoSolicitacaoServico.valueOf(solicitacaoServicoDTO.getSituacao())));
		} else {
			parametros.put("situacao", UtilI18N.internacionaliza(request, "alcada.limite.todos"));
		}
		
		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
		
		
		JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioControlePercentualQuantitativoSlaXls.jrxml");

		JasperReport relatorio = JasperCompileManager.compileReport(desenho);

		JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros);

		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioControlePercentualQuantitativoSlaXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

		exporter.exportReport();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
				+ "/RelatorioControlePercentualQuantitativoSlaXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}
	
	public void preencherComboOrigem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		HTMLSelect selectOrigem = (HTMLSelect) document.getSelectById("idOrigem");
		selectOrigem.removeAllOptions();
		ArrayList<OrigemAtendimentoDTO> todasOrigens = (ArrayList) origemAtendimentoService.list();
		ArrayList<OrigemAtendimentoDTO> origensNaoExcluidas = new ArrayList<OrigemAtendimentoDTO>();
		String origemPadrao = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO, "");

		selectOrigem.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (todasOrigens != null) {
			for (OrigemAtendimentoDTO origemAtendimento : todasOrigens) {
				if (origemAtendimento.getDataFim() == null) {
					origensNaoExcluidas.add(origemAtendimento);
				}
			}
			selectOrigem.addOptions(origensNaoExcluidas, "idOrigem", "descricao", origemPadrao);
		}
	}
	
public void carregaUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
		if ((UNIDADE_AUTOCOMPLETE!=null)&&(!UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S"))){
			String parametroUnidadeVinculadoAContratos = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
	
			SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();
	
			UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
	
			HTMLSelect comboUnidadeMultContratos = (HTMLSelect) document.getSelectById("idUnidade");
	
			comboUnidadeMultContratos.removeAllOptions();
			comboUnidadeMultContratos.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	
			if (parametroUnidadeVinculadoAContratos.trim().equalsIgnoreCase("S")) {
	
				Integer idContrato = solicitacaoServicoDTO.getIdContrato();
	
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
			} else {
				ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();
				if (unidades != null) {
					for (UnidadeDTO unidade : unidades) {
						if (unidade.getDataFim() == null) {
							comboUnidadeMultContratos.addOption(Util.tratarAspasSimples(unidade.getIdUnidade().toString()), Util.tratarAspasSimples(unidade.getNomeNivel()));
						}
					}
				}
			}
		}

	}
}
