package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.servico.FluxoService;
import br.com.centralit.bpm.servico.TipoFluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.CondicaoOperacaoDTO;
import br.com.centralit.citcorpore.bean.FaseServicoDTO;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.CalendarioService;
import br.com.centralit.citcorpore.negocio.CondicaoOperacaoService;
import br.com.centralit.citcorpore.negocio.FaseServicoService;
import br.com.centralit.citcorpore.negocio.FluxoServicoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ModeloEmailService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author Cledson.junior
 *
 */
public class ServicoContrato extends AjaxFormAction {
	
	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.preencherComboCondicaoOperacao(document, request, response);
		this.preencherComboModelosEmailsAbertura(document, request, response);
		this.preencherComboModelosEmailsFinalizacao(document, request, response);
		this.preencherComboModelosEmailsDemais(document, request, response);
		this.preencherComboGrupoAprovador(document, request, response);
		this.preencherComboGrupoEscalacao(document, request, response);
		this.preencherComboGrupoExecutor(document, request, response);
		this.preencherCalendario(document, request, response);
		this.preencherComboFluxo(document, request, response);
		this.preencherComboFase(document, request, response);
		
		String idServicoContrato = request.getParameter("idServicoContrato");
		if(idServicoContrato!= null && idServicoContrato != ""){
			recupera(document, request, response);
		}
	}

	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public void recupera(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) document.getBean();
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		HTMLForm form = document.getForm("form");
		if(servicoContratoDTO.getIdServicoContrato() != null) {
			servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
			ServicoDTO servicoBean = new ServicoDTO();
			FaseServicoDTO faseServicoDTO = new FaseServicoDTO();
			TipoFluxoDTO tipoFluxoDTO = new TipoFluxoDTO();
			ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
			servicoBean.setIdServico(servicoContratoDTO.getIdServico());
			servicoBean = (ServicoDTO) servicoService.restore(servicoBean);
			servicoContratoDTO.setNomeServico(servicoBean.getNomeServico());
			FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
			TipoFluxoService tipoFluxoService = (TipoFluxoService) ServiceLocator.getInstance().getService(TipoFluxoService.class, null);

			FluxoServicoService fluxoServicoService = (FluxoServicoService) ServiceLocator.getInstance().getService(FluxoServicoService.class, WebUtil.getUsuarioSistema(request));
			
			Collection<FluxoServicoDTO> grupoFluxo = (Collection<FluxoServicoDTO>) fluxoServicoService.findByIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
			
			if (grupoFluxo != null && !grupoFluxo.isEmpty()) {
				for (FluxoServicoDTO fluxoServicoDTO : grupoFluxo) {
					faseServicoDTO.setIdFase(fluxoServicoDTO.getIdFase());
					faseServicoDTO = (FaseServicoDTO) faseServicoService.restore(faseServicoDTO);
					tipoFluxoDTO.setIdTipoFluxo(fluxoServicoDTO.getIdTipoFluxo());
					tipoFluxoDTO = (TipoFluxoDTO) tipoFluxoService.restore(tipoFluxoDTO);
					
					/* Desenvolvedor: Euler.Ramos Data: 11/11/2013 Horário: 10h18min ID Citsmart: 123627 Motivo/Comentário: Evitar null pointer!*/
					String nomeFase;
					if ((faseServicoDTO!=null)&&(faseServicoDTO.getNomeFase()!=null)) {
						nomeFase = faseServicoDTO.getNomeFase();
					} else {
						nomeFase = "";
					}
					String descricaoTipoFluxo;
					if ((tipoFluxoDTO!=null)&&(tipoFluxoDTO.getDescricao()!=null)) {
						descricaoTipoFluxo = tipoFluxoDTO.getDescricao();
					} else {
						descricaoTipoFluxo = "";
					}
					
					document.executeScript("addLinhaTabelaFluxoTrabalho(" + fluxoServicoDTO.getIdFase() + ",'" + fluxoServicoDTO.getIdTipoFluxo() + "','" + fluxoServicoDTO.getPrincipal() + "','"+ nomeFase+"','"+ descricaoTipoFluxo +"','"+ fluxoServicoDTO.getPrincipal()+"');");
				}
				document.executeScript("exibirGrid();");
			}
			form.setValues(servicoContratoDTO);
		}
	}
	
	public void deleteFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FluxoServicoDTO fluxoServicoDTO = new FluxoServicoDTO();
		FluxoServicoService fluxoServicoService = (FluxoServicoService) ServiceLocator.getInstance().getService(FluxoServicoService.class, WebUtil.getUsuarioSistema(request));
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) document.getBean();

		String idTipoFluxoExclusao = request.getParameter("idTipoFluxoExclusao");
		String idFaseExclusao = request.getParameter("idFaseExclusao");
		String principalExclusao = request.getParameter("principalExclusao");
		
		fluxoServicoDTO.setDeleted("y");
		fluxoServicoDTO.setIdFase(Integer.parseInt(idFaseExclusao));
		fluxoServicoDTO.setIdTipoFluxo(Integer.parseInt(idTipoFluxoExclusao));
		fluxoServicoDTO.setPrincipal(principalExclusao);
		fluxoServicoDTO.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
		if (fluxoServicoDTO != null && fluxoServicoDTO.getIdServicoContrato() != null) {
			fluxoServicoService.update(fluxoServicoDTO);
		}

		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}
	
	public void restoreServicoContrato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) document.getBean();
		ServicoDTO servicoBean = new ServicoDTO();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		servicoBean.setIdServico(servicoContratoDTO.getIdServico());
		servicoBean = (ServicoDTO) servicoService.restore(servicoBean);
		servicoContratoDTO.setNomeServico(servicoBean.getNomeServico());
		servicoContratoDTO.setIdServico(servicoBean.getIdServico());
		HTMLForm form = document.getForm("form");
		form.setValues(servicoContratoDTO);
		document.executeScript("fecharPopup()");
	}
	
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
				ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) document.getBean();
				ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
				servicoContratoService.deletarByIdServicoContrato(servicoContratoDTO, document);
				document.executeScript("closePopup();");
				document.executeScript("showServicosContrato('" + servicoContratoDTO.getIdContrato() + "');");
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
	
	public Class<ServicoContratoDTO> getBeanClass() {
		return ServicoContratoDTO.class;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherComboCondicaoOperacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    CondicaoOperacaoService condicaoOperacaoService = (CondicaoOperacaoService) ServiceLocator.getInstance().getService(CondicaoOperacaoService.class, null);
		HTMLSelect idCondicaoOperacao = (HTMLSelect) document.getSelectById("idCondicaoOperacao");
		idCondicaoOperacao.removeAllOptions();
		ArrayList<CondicaoOperacaoDTO> cols = (ArrayList) condicaoOperacaoService.list();
		ArrayList<CondicaoOperacaoDTO> popular = new ArrayList<CondicaoOperacaoDTO>();

		if (cols != null) {
			for (CondicaoOperacaoDTO condicaoOperacaoDTO : cols)
				if (condicaoOperacaoDTO.getDataFim() == null)
					popular.add(condicaoOperacaoDTO);
			
			idCondicaoOperacao.addOptions(popular, "idCondicaoOperacao", "nomeCondicaoOperacao", null);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherComboModelosEmailsAbertura(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, null);
		HTMLSelect idModelosEmails = (HTMLSelect) document.getSelectById("idModeloEmailCriacao");
		idModelosEmails.removeAllOptions();
		ArrayList<ModeloEmailDTO> cols = (ArrayList) modeloEmailService.getAtivos();
		ArrayList<ModeloEmailDTO> popular = new ArrayList<ModeloEmailDTO>();

		idModelosEmails.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
		if (cols != null) {
			for (ModeloEmailDTO modeloEmailDTO : cols)
					popular.add(modeloEmailDTO);
			idModelosEmails.addOptions(popular, "idModeloEmail", "titulo", null);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherComboModelosEmailsFinalizacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, null);
		HTMLSelect idModelosEmails = (HTMLSelect) document.getSelectById("idModeloEmailFinalizacao");
		idModelosEmails.removeAllOptions();
		ArrayList<ModeloEmailDTO> cols = (ArrayList) modeloEmailService.getAtivos();
		ArrayList<ModeloEmailDTO> popular = new ArrayList<ModeloEmailDTO>();

		idModelosEmails.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
		if (cols != null) {
			for (ModeloEmailDTO modeloEmailDTO : cols)
					popular.add(modeloEmailDTO);
			idModelosEmails.addOptions(popular, "idModeloEmail", "titulo", null);
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherComboModelosEmailsDemais(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, null);
		HTMLSelect idModelosEmails = (HTMLSelect) document.getSelectById("idModeloEmailAcoes");
		idModelosEmails.removeAllOptions();
		ArrayList<ModeloEmailDTO> cols = (ArrayList) modeloEmailService.getAtivos();
		ArrayList<ModeloEmailDTO> popular = new ArrayList<ModeloEmailDTO>();

		idModelosEmails.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
		if (cols != null) {
			for (ModeloEmailDTO modeloEmailDTO : cols)
					popular.add(modeloEmailDTO);
			idModelosEmails.addOptions(popular, "idModeloEmail", "titulo", null);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherComboGrupoEscalacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		HTMLSelect idGrupoEscalacao = (HTMLSelect) document.getSelectById("idGrupoNivel1");
		idGrupoEscalacao.removeAllOptions();
		ArrayList<GrupoDTO> cols = (ArrayList) grupoService.listaGruposAtivos();
		ArrayList<GrupoDTO> popular = new ArrayList<GrupoDTO>();

		idGrupoEscalacao.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
		if (cols != null) {
			for (GrupoDTO grupoDTO : cols)
					popular.add(grupoDTO);
			idGrupoEscalacao.addOptions(popular, "idGrupo", "nome", null);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherComboGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		HTMLSelect idGrupoEscalacao = (HTMLSelect) document.getSelectById("idGrupoExecutor");
		idGrupoEscalacao.removeAllOptions();
		ArrayList<GrupoDTO> cols = (ArrayList) grupoService.listaGruposAtivos();
		ArrayList<GrupoDTO> popular = new ArrayList<GrupoDTO>();

		idGrupoEscalacao.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
		if (cols != null) {
			for (GrupoDTO grupoDTO : cols)
					popular.add(grupoDTO);
			idGrupoEscalacao.addOptions(popular, "idGrupo", "nome", null);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherComboGrupoAprovador(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		HTMLSelect idGrupoEscalacao = (HTMLSelect) document.getSelectById("idGrupoAprovador");
		idGrupoEscalacao.removeAllOptions();
		ArrayList<GrupoDTO> cols = (ArrayList) grupoService.listaGruposAtivos();
		ArrayList<GrupoDTO> popular = new ArrayList<GrupoDTO>();

		idGrupoEscalacao.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
		if (cols != null) {
			for (GrupoDTO grupoDTO : cols)
					popular.add(grupoDTO);
			idGrupoEscalacao.addOptions(popular, "idGrupo", "nome", null);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherCalendario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CalendarioService calendarioService = (CalendarioService) ServiceLocator.getInstance().getService(CalendarioService.class, null);
		HTMLSelect idCalendario = (HTMLSelect) document.getSelectById("idCalendario");
		idCalendario.removeAllOptions();
		ArrayList<CalendarioDTO> cols = (ArrayList) calendarioService.list();
		ArrayList<CalendarioDTO> popular = new ArrayList<CalendarioDTO>();

		idCalendario.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
		if (cols != null) {
			for (CalendarioDTO calendarioDTO : cols)
					popular.add(calendarioDTO);
			idCalendario.addOptions(popular, "idCalendario", "descricao", null);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherComboFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
		HTMLSelect idtipofluxo = (HTMLSelect) document.getSelectById("idtipofluxo");
		idtipofluxo.removeAllOptions();
		ArrayList<FluxoDTO> cols = (ArrayList) fluxoService.list();
		ArrayList<FluxoDTO> popular = new ArrayList<FluxoDTO>();

		idtipofluxo.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
		if (cols != null) {
			for (FluxoDTO fluxoDTO : cols)
					popular.add(fluxoDTO);
			idtipofluxo.addOptions(popular, "idtipofluxo", "descricao", null);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void preencherComboFase(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
		HTMLSelect idfase = (HTMLSelect) document.getSelectById("idfase");
		idfase.removeAllOptions();
		ArrayList<FaseServicoDTO> cols = (ArrayList) faseServicoService.list();
		ArrayList<FaseServicoDTO> popular = new ArrayList<FaseServicoDTO>();

		idfase.addOption("", UtilI18N.internacionaliza(request, "requisitosla.selecione"));
		if (cols != null) {
			for (FaseServicoDTO faseServicoDTO : cols)
					popular.add(faseServicoDTO);
			idfase.addOptions(popular, "idfase", "nomefase", null);
		}
	}
	
}
