/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoRelacionadoDTO;
import br.com.centralit.citcorpore.bean.ComentariosDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoUsuarioDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoRelacionadoService;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.ComentariosService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.EventoMonitConhecimentoService;
import br.com.centralit.citcorpore.negocio.EventoMonitoramentoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ImportanciaConhecimentoGrupoService;
import br.com.centralit.citcorpore.negocio.ImportanciaConhecimentoUsuarioService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.NotificacaoGrupoService;
import br.com.centralit.citcorpore.negocio.NotificacaoService;
import br.com.centralit.citcorpore.negocio.NotificacaoUsuarioService;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoPastaService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.PermissaoAcessoPasta;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * Action de BaseConhecimento.
 * 
 * @author valdoilo.damasceno
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class BaseConhecimento extends AjaxFormAction {
	
	private BaseConhecimentoDTO baseConhecimentoBean;
	
	private Integer idPrimeiraPasta;
	
	private boolean aprovaBaseConhecimento;
	
	
	ProblemaDTO problemaDto ; 
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();
		
		baseConhecimentoDto.setErroConhecido(request.getParameter("erroConhecido"));
		
		if(request.getParameter("iframe") !=null && !request.getParameter("iframe").equalsIgnoreCase("")){
			baseConhecimentoDto.setIframe(request.getParameter("iframe"));
		}
		
		
		
		if(request.getParameter("idProblema") !=null&& !request.getParameter("idProblema").equalsIgnoreCase("")){
			baseConhecimentoDto.setIdProblema(Integer.parseInt(request.getParameter("idProblema")));
		}
		
		if (request.getParameter("idBaseConhecimento") != null && !request.getParameter("idBaseConhecimento").equalsIgnoreCase("") && !"sim".equals(request.getAttribute("limpar"))) {
			baseConhecimentoDto.setIdBaseConhecimento(Integer.parseInt(request.getParameter("idBaseConhecimento")));
		}
		request.removeAttribute("limpar");
		
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		this.alimentaComboPastasBaseConhecimento(document, request);
		this.preencherComboOrigem(document, request);
		this.preencherComboPrivacidade(document, request);
		this.preencherComboSituacao(document, request);

		EmpregadoDTO empregadoDto = new EmpregadoDTO();

		empregadoDto.setIdEmpregado(usuarioDto.getIdEmpregado());

		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);

		String autor = empregadoDto.getNome();

		baseConhecimentoDto.setAutor(autor);

		baseConhecimentoDto.setDataInicio(UtilDatas.getDataAtual());

		if (perfilAcessoPastaService.verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(usuarioDto, this.getIdPrimeiraPasta())) {
			document.executeScript("$('#publicacao').show()");
		} else {
			document.executeScript("$('#publicacao').hide()");
		}

		request.getSession(true).setAttribute("colUploadsGED", null);

		document.executeScript("uploadAnexos.clear()");
		
		if(baseConhecimentoDto.getIdProblema()!=null){
			problemaDto = this.getProblemaDto(baseConhecimentoDto.getIdProblema());
		}
		
		if(problemaDto !=null){
			baseConhecimentoDto.setConteudo("<strong>Causa raiz:</strong><br type='_moz' />"+problemaDto.getCausaRaiz()+"<br/><br/><strong>&nbsp;Solu&ccedil;&atilde;o contorno:</strong><br />"+problemaDto.getSolucaoContorno());
		}

		HTMLForm form = CITCorporeUtil.limparFormulario(document);

		form.setValues(baseConhecimentoDto);

		this.preencherComboNotificacao(document, request, response);
		HTMLElement notificacaoTitulo = document.getElementById("notificacaoTitulo");
		notificacaoTitulo.setValue("");
		
		document.executeScript("limpaTabelaEventoMonitoramento();");

		document.executeScript("$('#tabelaGrupoNotificacao').hide()");
		document.executeScript("$('#tabelaUsuarioNotificacao').hide()");
		document.executeScript("$('#btnGravarNotificacao').hide()");
		document.executeScript("$('#divSolicitacaoServico').hide()");
		document.executeScript("$('#divMudanca').hide()");
		document.executeScript("$('#btnGravarEventoMonitConhecimento').hide()");
		
		String iframe = "";
		if(baseConhecimentoDto.getIframe()!=null){
			iframe = baseConhecimentoDto.getIframe();
		}
		
		if(iframe!=null && !iframe.equals("")){
			
			if(baseConhecimentoDto.getErroConhecido()!=null && baseConhecimentoDto.getErroConhecido().equalsIgnoreCase("S")){
				document.executeScript("$('#erroConhecido').attr('checked',true)");
				document.executeScript("document.getElementById('comboOrigem').options[5].selected = 'selected'");
				document.executeScript("$('#tabs-2').hide()");
				
			}
			
			if(baseConhecimentoDto.getIdBaseConhecimento()!=null){
				
				this.restore(document, request, response);
			}
		}else{
			document.executeScript("validaDocumento()");
		}
		
		//fechando janela aguarde
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			
		
	}

	/**
	 * Inclui nova Base de Conhecimento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.executeScript("aguarde();");
		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));

		Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
		System.out.println("BaseConhecimento - A arquivosUpados esta vazia!" + arquivosUpados);

		baseConhecimentoDto.setListImportanciaConhecimentoUsuario((Collection<ImportanciaConhecimentoUsuarioDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				ImportanciaConhecimentoUsuarioDTO.class, "listImportanciaConhecimentoUsuarioSerializado", request));

		baseConhecimentoDto.setListImportanciaConhecimentoGrupo((Collection<ImportanciaConhecimentoGrupoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				ImportanciaConhecimentoGrupoDTO.class, "listImportanciaConhecimentoGrupoSerializado", request));

		baseConhecimentoDto.setListBaseConhecimentoRelacionado((Collection<BaseConhecimentoRelacionadoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
				BaseConhecimentoRelacionadoDTO.class, "listConhecimentoRelacionadoSerializado", request));

		baseConhecimentoDto.setListaDeUsuarioNotificacao((ArrayList<NotificacaoUsuarioDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoUsuarioDTO.class,
				"listUsuariosNotificacaoSerializados", request));

		baseConhecimentoDto.setListaDeGrupoNotificacao((ArrayList<NotificacaoGrupoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoGrupoDTO.class,
				"listGruposNotificacaoSerializados", request));

		baseConhecimentoDto.setListEventoMonitoramento((ArrayList<EventoMonitConhecimentoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EventoMonitConhecimentoDTO.class,
				"listEventoMonitoramentoSerializado", request));
		
		

		if (baseConhecimentoDto.getIdBaseConhecimento() == null) {

			if (baseConhecimentoDto.getStatus() == null) {
				baseConhecimentoDto.setStatus("N");
			}

			if (baseConhecimentoService.verificaBaseConhecimentoExistente(baseConhecimentoDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				document.executeScript("fechar_aguarde();");
				return;
			}

			baseConhecimentoService.create(baseConhecimentoDto, arquivosUpados, WebUtil.getIdEmpresa(request), usuarioDto);
			document.alert(UtilI18N.internacionaliza(request, "dinamicview.gravadocomsucesso"));

		} else {
			if (baseConhecimentoService.verificarSeBaseConhecimentoJaPossuiNovaVersao(baseConhecimentoDto)) {
				document.executeScript("fechar_aguarde();");
				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.versaorecente"));
			} else {
				if (baseConhecimentoDto.getIdBaseConhecimentoPai() == null && baseConhecimentoService.verificaBaseConhecimentoExistente(baseConhecimentoDto)) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
					document.executeScript("fechar_aguarde();");
					return;
				}

				baseConhecimentoService.update(baseConhecimentoDto, arquivosUpados, WebUtil.getIdEmpresa(request), usuarioDto);

				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
				document.executeScript("fechar_aguarde();");
				document.executeScript("limpar()");
			}
		}

		document.executeScript("limpar();");
		document.executeScript("limpar_LOOKUP_BASECONHECIMENTO()");
		document.executeScript("fechar_aguarde();");
		document.executeScript("fechar();");
	}

	/**
	 * Restaura Base de Conhecimento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		
		String iframe = "";

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();
		NotificacaoDTO notificacaoDto = new NotificacaoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		
		
		if(baseConhecimentoDto.getIframe()!=null){
			iframe = baseConhecimentoDto.getIframe();
		}

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {

			request.getSession(true).setAttribute("colUploadsGED", null);

			NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);
			NotificacaoGrupoService notificacaoGrupoService = (NotificacaoGrupoService) ServiceLocator.getInstance().getService(NotificacaoGrupoService.class, null);
			NotificacaoUsuarioService notificacaoUsuarioService = (NotificacaoUsuarioService) ServiceLocator.getInstance().getService(NotificacaoUsuarioService.class, null);
			PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);
			BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
			ComentariosService comentariosService = (ComentariosService) ServiceLocator.getInstance().getService(ComentariosService.class, null);
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
			ImportanciaConhecimentoUsuarioService importanciaConhecimentoUsuarioService = (ImportanciaConhecimentoUsuarioService) ServiceLocator.getInstance().getService(
					ImportanciaConhecimentoUsuarioService.class, null);
			ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance().getService(
					ImportanciaConhecimentoGrupoService.class, null);
			BaseConhecimentoRelacionadoService baseConhecimentoRelacionadoService = (BaseConhecimentoRelacionadoService) ServiceLocator.getInstance().getService(
					BaseConhecimentoRelacionadoService.class, null);
			EventoMonitConhecimentoService eventoMonitConhecimentoService = (EventoMonitConhecimentoService) ServiceLocator.getInstance().getService(EventoMonitConhecimentoService.class, null);
			EventoMonitoramentoService eventoMonitoramentoService = (EventoMonitoramentoService) ServiceLocator.getInstance().getService(EventoMonitoramentoService.class, null);

			baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);
			
			if(baseConhecimentoDto!=null){
				
			boolean isPossuiPermissao = this.aplicarPermissaoDeAcesso(document, request, baseConhecimentoDto, usuarioDto, true);

				
				if (isPossuiPermissao) {
					if(iframe==null || iframe.equals("")){
						document.executeScript("document.form.restore()");
					}
						
				} else {
					document.executeScript("limpar()");
					return;
				}

			if (baseConhecimentoDto.getOrigem() == null) {
				baseConhecimentoDto.setOrigem("1");
			} else if (baseConhecimentoDto.getOrigem().equals("")) {
				baseConhecimentoDto.setOrigem("1");
			}

			EmpregadoDTO empregadoAutor = new EmpregadoDTO();
			EmpregadoDTO empregadoAprovador = new EmpregadoDTO();

			UsuarioDTO usuarioAutor = new UsuarioDTO();
			UsuarioDTO usuarioAprovador = new UsuarioDTO();

			if (baseConhecimentoDto.getIdUsuarioAutor() != null) {

				usuarioAutor.setIdUsuario(baseConhecimentoDto.getIdUsuarioAutor());
				usuarioAutor = (UsuarioDTO) usuarioService.restore(usuarioAutor);
				if(usuarioAutor != null)
				{
				empregadoAutor = empregadoService.restoreByIdEmpregado(usuarioAutor.getIdEmpregado());
				baseConhecimentoDto.setAutor(empregadoAutor.getNome());
				}

			}

			if (baseConhecimentoDto.getIdUsuarioAprovador() != null) {

				usuarioAprovador.setIdUsuario(baseConhecimentoDto.getIdUsuarioAprovador());
				usuarioAprovador = (UsuarioDTO) usuarioService.restore(usuarioAprovador);
				 if (usuarioAprovador != null)
				 {
				empregadoAprovador = empregadoService.restoreByIdEmpregado(usuarioAprovador.getIdEmpregado());
				baseConhecimentoDto.setAprovador(empregadoAprovador.getNome());
			}

			}

			request.getSession().removeAttribute("idBaseConhecimento");
			request.getSession().setAttribute("idBaseConhecimento", baseConhecimentoDto.getIdBaseConhecimento());

			Double media = baseConhecimentoService.calcularNota(baseConhecimentoDto.getIdBaseConhecimento());
			if (media != null) {

				baseConhecimentoDto.setMedia(media.toString());
			}

			Long quantidadeVotos = baseConhecimentoService.contarVotos(baseConhecimentoDto.getIdBaseConhecimento());
			if (quantidadeVotos != null) {

				baseConhecimentoDto.setVotos(quantidadeVotos.toString());
			}

			document.executeScript("deleteAllRowsUsuarioNotificacao()");
			document.executeScript("deleteAllRowsGrupoNotificacao()");

			if (baseConhecimentoDto.getIdNotificacao() != null) {

				notificacaoDto.setIdNotificacao(baseConhecimentoDto.getIdNotificacao());

				notificacaoDto = (NotificacaoDTO) notificacaoService.restore(notificacaoDto);

				Collection<NotificacaoGrupoDTO> listaIdGrupo = notificacaoGrupoService.listaIdGrupo(notificacaoDto.getIdNotificacao());

				Collection<NotificacaoUsuarioDTO> listaIdUsuario = notificacaoUsuarioService.listaIdUsuario(notificacaoDto.getIdNotificacao());

				if (listaIdUsuario != null && !listaIdUsuario.isEmpty()) {
					for (NotificacaoUsuarioDTO notificacaoUsuarioDto : listaIdUsuario) {
						if (notificacaoUsuarioDto.getIdUsuario() != null) {
							UsuarioDTO usuarioDtoNotificacao = new UsuarioDTO();
							usuarioDtoNotificacao.setIdUsuario(notificacaoUsuarioDto.getIdUsuario());
							usuarioDtoNotificacao = (UsuarioDTO) usuarioService.restore(usuarioDtoNotificacao);
							document.executeScript("addLinhaTabelaUsuarioNotificacao(" + usuarioDtoNotificacao.getIdUsuario() + ", '" + usuarioDtoNotificacao.getNomeUsuario() + "', " + false + ");");
							document.executeScript("$('#tabelaUsuarioNotificacao').show()");
							document.executeScript("$('#gridUsuarioNotificacao').show()");
						}

					}
				}

				if (listaIdGrupo != null && !listaIdGrupo.isEmpty()) {
					for (NotificacaoGrupoDTO notificacaoGrupoDto : listaIdGrupo) {
						if (notificacaoGrupoDto.getIdGrupo() != null) {
							GrupoDTO grupoDtoNotificacao = new GrupoDTO();
							grupoDtoNotificacao.setIdGrupo(notificacaoGrupoDto.getIdGrupo());
							grupoDtoNotificacao = (GrupoDTO) grupoService.restore(grupoDtoNotificacao);
							document.executeScript("addLinhaTabelaGrupoNotificacao(" + grupoDtoNotificacao.getIdGrupo() + ", '" + grupoDtoNotificacao.getNome() + "', " + false + ");");
							document.executeScript("$('#tabelaGrupoNotificacao').show()");
							document.executeScript("$('#gridGrupoNotificacao').show()");
						}
					}
				}
				baseConhecimentoDto.setTituloNotificacao(notificacaoDto.getTitulo());
				baseConhecimentoDto.setTipoNotificacao(notificacaoDto.getTipoNotificacao());
			}
			
			

			HTMLForm form = CITCorporeUtil.limparFormulario(document);
			
			if (baseConhecimentoDto.getFaq() != null && StringUtils.equalsIgnoreCase(baseConhecimentoDto.getFaq().trim(), "S")) {
				document.executeScript("ckeckarFaq()");
				document.executeScript("habilitarPergunta()");
				document.executeScript("$('#documento').attr('checked',false);");
				document.executeScript("ocultarAnexos()");
			} else {
				if(baseConhecimentoDto.getErroConhecido()!=null && baseConhecimentoDto.getErroConhecido().equalsIgnoreCase("S")){
					document.executeScript("$('#erroConhecido').attr('checked',true);");
				}else{
					document.executeScript("$('#documento').attr('checked',true);");
				}
				document.executeScript("exibirAnexos()");
			}
			
			document.executeScript("uploadAnexos.clear()");
			document.executeScript("deleteAllRows()");

			this.restaurarAnexos(request, baseConhecimentoDto);

			//baseConhecimentoDto.setTitulo(StringEscapeUtils.unescapeJavaScript(baseConhecimentoDto.getTitulo()));
			
			if(request.getParameter("idProblema") !=null&& !request.getParameter("idProblema").equalsIgnoreCase("")){
				baseConhecimentoDto.setIdProblema(Integer.parseInt(request.getParameter("idProblema")));
			}
			
			form.setValues(baseConhecimentoDto);

			document.executeScript("setarValoresPopupNotificacao()");
			document.executeScript("deleteAllRowsUsuario()");
			document.executeScript("deleteAllRowsGrupo()");
			document.executeScript("deleteAllRowsConhecimento()");
			document.executeScript("deleteAllRowsEventoMonitoramento()");
			document.executeScript("$('#btnGravarNotificacao').show()");

			Collection<ImportanciaConhecimentoUsuarioDTO> listImportanciaConhecimentoUsuarioDto = importanciaConhecimentoUsuarioService.listByIdBaseConhecimento(baseConhecimentoDto
					.getIdBaseConhecimento());

			if (listImportanciaConhecimentoUsuarioDto != null && !listImportanciaConhecimentoUsuarioDto.isEmpty()) {

				for (ImportanciaConhecimentoUsuarioDTO importanciaConhecimentoUsuarioDTO : listImportanciaConhecimentoUsuarioDto) {

					if (importanciaConhecimentoUsuarioDTO.getIdUsuario() != null) {

						UsuarioDTO usuarioImportanciaConhecimento = new UsuarioDTO();
						usuarioImportanciaConhecimento.setIdUsuario(importanciaConhecimentoUsuarioDTO.getIdUsuario());
						usuarioImportanciaConhecimento = (UsuarioDTO) usuarioService.restore(usuarioImportanciaConhecimento);

						document.executeScript("addLinhaTabelaUsuario(" + usuarioImportanciaConhecimento.getIdUsuario() + ", '" + usuarioImportanciaConhecimento.getNomeUsuario() + "', " + false
								+ ");");
						document.executeScript("atribuirCheckedUsuario('" + importanciaConhecimentoUsuarioDTO.getGrauImportanciaUsuario() + "')");
						document.executeScript("exibirTabelaUsuario()");
					}
				}
			}

			Collection<ImportanciaConhecimentoGrupoDTO> listImportanciaConhecimentoGrupoDto = importanciaConhecimentoGrupoService.listByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());

			if (listImportanciaConhecimentoGrupoDto != null && !listImportanciaConhecimentoGrupoDto.isEmpty()) {

				for (ImportanciaConhecimentoGrupoDTO importanciaConhecimentoGrupoDTO : listImportanciaConhecimentoGrupoDto) {

					if (importanciaConhecimentoGrupoDTO.getIdGrupo() != null) {

						GrupoDTO grupoImportanciaConhecimento = new GrupoDTO();
						grupoImportanciaConhecimento.setIdGrupo(importanciaConhecimentoGrupoDTO.getIdGrupo());
						grupoImportanciaConhecimento = (GrupoDTO) grupoService.restore(grupoImportanciaConhecimento);

						document.executeScript("addLinhaTabelaGrupo(" + grupoImportanciaConhecimento.getIdGrupo() + ", '" + grupoImportanciaConhecimento.getNome() + "', " + false + ");");
						document.executeScript("atribuirCheckedGrupo('" + importanciaConhecimentoGrupoDTO.getGrauImportanciaGrupo() + "')");
					}

				}
			}

			Collection<BaseConhecimentoRelacionadoDTO> listBaseConhecimentoRelacionadoDto = baseConhecimentoRelacionadoService.listByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
			if (listBaseConhecimentoRelacionadoDto == null) {
				listBaseConhecimentoRelacionadoDto = new ArrayList<BaseConhecimentoRelacionadoDTO>();
			}
			if (listBaseConhecimentoRelacionadoDto != null && !listBaseConhecimentoRelacionadoDto.isEmpty()) {

				for (BaseConhecimentoRelacionadoDTO baseConhecimentoRelacionadoDto : listBaseConhecimentoRelacionadoDto) {

					if (baseConhecimentoRelacionadoDto.getIdBaseConhecimentoRelacionado() != null) {

						BaseConhecimentoDTO conhecimentoRelacionadoDto = new BaseConhecimentoDTO();
						conhecimentoRelacionadoDto.setIdBaseConhecimento(baseConhecimentoRelacionadoDto.getIdBaseConhecimentoRelacionado());
						conhecimentoRelacionadoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(conhecimentoRelacionadoDto);

						document.executeScript("addLinhaTabelaConhecimentoRelacionado(" + conhecimentoRelacionadoDto.getIdBaseConhecimento() + ",'" + conhecimentoRelacionadoDto.getTitulo() + "',"
								+ true + ");");
					}
				}
			}
			Collection<BaseConhecimentoRelacionadoDTO> listBaseConhecimentoRelacionadoAux = baseConhecimentoRelacionadoService.listByIdBaseConhecimentoRelacionado(baseConhecimentoDto
					.getIdBaseConhecimento());
			if (listBaseConhecimentoRelacionadoAux != null && !listBaseConhecimentoRelacionadoAux.isEmpty()) {

				for (BaseConhecimentoRelacionadoDTO baseConhecimentoRelacionadoDto : listBaseConhecimentoRelacionadoAux) {

					if (baseConhecimentoRelacionadoDto.getIdBaseConhecimentoRelacionado() != null) {

						BaseConhecimentoDTO conhecimentoRelacionadoDto = new BaseConhecimentoDTO();
						conhecimentoRelacionadoDto.setIdBaseConhecimento(baseConhecimentoRelacionadoDto.getIdBaseConhecimento());
						conhecimentoRelacionadoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(conhecimentoRelacionadoDto);
						if (conhecimentoRelacionadoDto != null) {
							document.executeScript("addLinhaTabelaConhecimentoRelacionado(" + conhecimentoRelacionadoDto.getIdBaseConhecimento() + ",'" + conhecimentoRelacionadoDto.getTitulo() + "',"
									+ true + ");");
						}
						
					}
				}
			}

			Collection<EventoMonitConhecimentoDTO> listEventoMonitConhecimento = eventoMonitConhecimentoService.listByIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());

			if (listEventoMonitConhecimento != null && !listEventoMonitConhecimento.isEmpty()) {

				for (EventoMonitConhecimentoDTO eventoMonitConhecimento : listEventoMonitConhecimento) {

					if (eventoMonitConhecimento.getIdEventoMonitoramento() != null) {

						EventoMonitoramentoDTO eventoMonitoramentoDto = new EventoMonitoramentoDTO();
						eventoMonitoramentoDto.setIdEventoMonitoramento(eventoMonitConhecimento.getIdEventoMonitoramento());
						eventoMonitoramentoDto = (EventoMonitoramentoDTO) eventoMonitoramentoService.restore(eventoMonitoramentoDto);

						String descricao = "";

						descricao = eventoMonitoramentoDto.getDetalhamento() + "-" + eventoMonitoramentoDto.getCriadoPor() + "-" + eventoMonitoramentoDto.getDataCriacao();

						document.executeScript("addLinhaTabelaEvento(" + eventoMonitoramentoDto.getIdEventoMonitoramento() + ",'" + descricao + "'," + true + ");");

					}
				}
			}

			document.executeScript("exibirBtnGravarImportancia()");
			document.executeScript("exibirBtnGravarConhecimentoRelacionado()");
			document.executeScript("uploadAnexos.refresh()");

			Collection<ComentariosDTO> comentarios = comentariosService.consultarComentarios(baseConhecimentoDto);

			if (comentarios != null && !comentarios.isEmpty()) {
				comentariosService.restaurarGridComentarios(document, comentarios);
			}

			if (baseConhecimentoDto.getStatus() != null && StringUtils.contains(baseConhecimentoDto.getStatus(), "S")) {
				document.executeScript("bloquearTitulo()");
			} else {
				if (baseConhecimentoDto.getStatus() != null && StringUtils.contains(baseConhecimentoDto.getStatus(), "N")
						&& baseConhecimentoService.verificarSeBaseConhecimentoPossuiVersaoAnterior(baseConhecimentoDto)) {
					document.executeScript("bloquearTitulo()");
				} else {
					document.executeScript("liberarTitulo()");
				}
			}

			if (baseConhecimentoDto.getArquivado() != null && StringUtils.equalsIgnoreCase(baseConhecimentoDto.getArquivado().trim(), "S")) {
				document.executeScript("exibirArquivado()");
				document.executeScript("exibirBotaoRestaurar()");
			}
			if(iframe==null || iframe.equals("")){
				document.executeScript("setDataEditor()");
			}
			
			document.executeScript("$('#divSolicitacaoServico').show()");
			document.executeScript("$('#divMudanca').show()");
			document.executeScript("$('#btnGravarEventoMonitConhecimento').show()");
			}
		}
	}

	/**
	 * Exclui Base conhecimento e todos os seus conteúdos.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void excluir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		boolean isAprovaBaseConhecimento = this.usuarioAprovaBaseConhecimento(usuarioDto, baseConhecimentoDto.getIdPasta());

		if (baseConhecimentoService.verificarSeBaseConhecimentoJaPossuiNovaVersao(baseConhecimentoDto)) {
			document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.versaorecente"));
			document.executeScript("fechar_aguarde();");
		} else {

			if (isAprovaBaseConhecimento) {
				baseConhecimentoService.excluir(baseConhecimentoDto, isAprovaBaseConhecimento);
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
				document.executeScript("fechar_aguarde();");
			} else {
				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
				document.executeScript("fechar_aguarde();");
			}
		}
		document.executeScript("fechar();");
		document.executeScript("limpar()");
		document.executeScript("fechar_aguarde();");
		
	}

	/**
	 * Grava Importância Conhecimento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void gravarImportanciaConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		PermissaoAcessoPasta permissao = this.obterPermissaoDeAcessoPasta(baseConhecimentoDto, usuarioDto);

		if (permissao != null) {

			if (PermissaoAcessoPasta.LEITURAGRAVACAO.equals(permissao)) {

				baseConhecimentoDto.setListImportanciaConhecimentoUsuario((Collection<ImportanciaConhecimentoUsuarioDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
						ImportanciaConhecimentoUsuarioDTO.class, "listImportanciaConhecimentoUsuarioSerializado", request));

				baseConhecimentoDto.setListImportanciaConhecimentoGrupo((Collection<ImportanciaConhecimentoGrupoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
						ImportanciaConhecimentoGrupoDTO.class, "listImportanciaConhecimentoGrupoSerializado", request));

				BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

				baseConhecimentoService.criarImportanciaConhecimentoUsuario(baseConhecimentoDto, null);

				baseConhecimentoService.criarImportanciaConhecimentoGrupo(baseConhecimentoDto, null);

				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.importanciaDefinida"));

			} else {
				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
			}

		} else {
			document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
		}
		document.executeScript("fecharPopupGrauDeImportancia()");
	}

	/**
	 * Grava Conhecimento Relacionado.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void gravarConhecimentoRelacionado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		PermissaoAcessoPasta permissao = this.obterPermissaoDeAcessoPasta(baseConhecimentoDto, usuarioDto);

		if (permissao != null) {

			if (PermissaoAcessoPasta.LEITURAGRAVACAO.equals(permissao)) {
				baseConhecimentoDto.setListBaseConhecimentoRelacionado((Collection<BaseConhecimentoRelacionadoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
						BaseConhecimentoRelacionadoDTO.class, "listConhecimentoRelacionadoSerializado", request));

				BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

				baseConhecimentoService.criarRelacionamentoEntreConhecimentos(baseConhecimentoDto, null);

				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.conhecimentoRelacionadoSucesso"));

			} else {
				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
			}

		} else {
			document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
		}
		document.executeScript("fecharPopupConhecimentoRelacionado()");
	}

	/**
	 * FireEvent para Gravar Eventos Monitoramento relacionado a Base de Conhecimento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void gravarEventoMonitConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		baseConhecimentoDto.setListEventoMonitoramento(((ArrayList<EventoMonitConhecimentoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EventoMonitConhecimentoDTO.class,
				"listEventoMonitoramentoSerializado", request)));

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		baseConhecimentoService.criarRelacionamentoEntreEventoMonitConhecimento(baseConhecimentoDto, null);
		document.alert(UtilI18N.internacionaliza(request, "MSG05"));
	}

	/**
	 * Restaura arquivos Anexos da Base de Conhecimento.
	 * 
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	protected void restaurarAnexos(HttpServletRequest request, BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, Exception {

		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);

		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_BASECONHECIMENTO, baseConhecimentoDto.getIdBaseConhecimento());

		Collection<UploadDTO> colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		if (colAnexosUploadDTO != null) {

			for (UploadDTO uploadDTO : colAnexosUploadDTO) {

				if (uploadDTO.getDescricao() == null) {
					uploadDTO.setDescricao("");
				}

			}
		}

		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
	}

	/**
	 * Limpa Fomulário e arquivos anexos.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void limpar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession(true).setAttribute("colUploadsGED", null);
		document.executeScript("uploadAnexos.clear()");
		request.setAttribute("limpar", "sim");
		this.load(document, request, response);
	}

	/**
	 * Verifica se Usuário Pode Aprovar Base de Conhecimento da pasta Selecionada.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public void verificarPermissoesDeAcesso(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);

		boolean usuarioAprovaBaseConhecimento = perfilAcessoPastaService.verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(usuarioDto, baseConhecimentoDto.getIdPasta());

		if (usuarioAprovaBaseConhecimento) {
			document.executeScript("$('#publicacao').show()");
			document.executeScript("marcaRadioButton()");
		} else {
			document.executeScript("$('#publicacao').hide()");
		}

		this.aplicarPermissaoDeAcesso(document, request, baseConhecimentoDto, usuarioDto, false);
	}

	/**
	 * Verifica se Usuário Pode Aprovar Base de Conhecimento da pasta Selecionada.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public void verificarPermissoesDeAcessoRestore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		boolean usuarioAprovaBaseConhecimento = perfilAcessoPastaService.verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(usuarioDto, baseConhecimentoDto.getIdPasta());

		if (usuarioAprovaBaseConhecimento) {
			document.executeScript("$('#publicacao').show()");
			document.executeScript("marcaRadioButton()");
		} else {
			document.executeScript("$('#publicacao').hide()");
		}

		boolean isRestore = this.aplicarPermissaoDeAcesso(document, request, baseConhecimentoDto, usuarioDto, true);

		if (isRestore) {
			HTMLForm form = CITCorporeUtil.limparFormulario(document);
			form.setValues(baseConhecimentoDto);
			document.executeScript("document.form.restore()");
		}
	}

	/**
	 * Oculta ou Exibe Botões Gravar, Limpar ou Excluir de Acordo com a permissão de Acesso do Usuário Logado.
	 * 
	 * @param document
	 * @param baseConhecimentoDto
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	private boolean aplicarPermissaoDeAcesso(DocumentHTML document, HttpServletRequest request, BaseConhecimentoDTO baseConhecimentoDto, UsuarioDTO usuarioDto, boolean isRestore) throws Exception {
		PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);

		PermissaoAcessoPasta permissao = obterPermissaoDeAcessoPasta(baseConhecimentoDto, usuarioDto);

		boolean usuarioAprovaBaseConhecimento = perfilAcessoPastaService.verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(usuarioDto, baseConhecimentoDto.getIdPasta());

		if (permissao != null) {

			if (PermissaoAcessoPasta.SEMPERMISSAO.equals(permissao)) {
				document.executeScript("ocultarBotoes()");
				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
				document.executeScript("ocultarBtnGravarImportancia()");
				document.executeScript("ocultarBtnGravarConhecimentoRelacionado()");
				if(request.getParameter("iframe").equalsIgnoreCase("true")){
					document.executeScript("desabilitaCamposFrame()");
				}
				return false;
			} else {

				if (PermissaoAcessoPasta.LEITURA.equals(permissao)) {
					document.executeScript("ocultarBotoes()");
					document.executeScript("ocultarDivPublicacao()");

					if (isRestore) {
						document.executeScript("desabilitarComboPasta()");
					}

					document.executeScript("ocultarBtnGravarImportancia()");
					document.executeScript("ocultarBtnGravarConhecimentoRelacionado()");

					document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.somenteLeitura"));
					if(request.getParameter("iframe") != null && request.getParameter("iframe").equalsIgnoreCase("true")){
						document.executeScript("desabilitaCamposFrame()");
					}
				} else {

					if (PermissaoAcessoPasta.LEITURAGRAVACAO.equals(permissao)) {
						document.executeScript("exibirBotoes()");
						document.executeScript("habilitarComboPasta()");

						if (usuarioAprovaBaseConhecimento) {
							document.executeScript("$('#publicacao').show()");
						} else {
							document.executeScript("$('#publicacao').hide()");
						}

						if (baseConhecimentoDto.getArquivado() != null && StringUtils.equalsIgnoreCase(baseConhecimentoDto.getArquivado().trim(), "S")) {
							document.executeScript("ocultarBotaoArquivar()");
							document.executeScript("exibirBotaoRestaurar()");
						} else {
							document.executeScript("ocultarArquivado()");
							document.executeScript("exibirBotaoArquivar()");
							document.executeScript("ocultarBotaoRestaurar()");
						}
					}
				}
				return true;
			}

		} else {
			document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
			return false;
		}
	}

	/**
	 * Retorna PermissaoAcessoPasta do Usuário à Pasta que está o conhecimento informado.
	 * 
	 * @param baseConhecimentoDto
	 * @param usuarioDto
	 * @return PermissaoAcessoPasta
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public PermissaoAcessoPasta obterPermissaoDeAcessoPasta(BaseConhecimentoDTO baseConhecimentoDto, UsuarioDTO usuarioDto) throws Exception {

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);

		PastaDTO pastaDto = new PastaDTO();

		if (baseConhecimentoDto.getIdPasta() != null) {
			pastaDto.setId(baseConhecimentoDto.getIdPasta());
			pastaDto = (PastaDTO) pastaService.restore(pastaDto);

			return perfilAcessoPastaService.verificarPermissaoDeAcessoPasta(usuarioDto, pastaDto);
		}
		return null;
	}

	/**
	 * Gera tree de subPastas.
	 * 
	 * @param sb
	 * @param listaDeSubPastas
	 * @param pasta
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @author Thays
	 */
	public void gerarSubpastas(StringBuilder sb, Collection<PastaDTO> listaDeSubPastas, PastaDTO pasta, HttpServletRequest request) throws ServiceException, Exception {

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		for (PastaDTO subPasta : listaDeSubPastas) {

			BaseConhecimentoDTO baseConhecimento = new BaseConhecimentoDTO();

			Collection<PastaDTO> listSubSubPasta = pastaService.listSubPastas(subPasta);

			String nomeSubPasta = subPasta.getNome();

			sb.append("<li class='collapsable'>");
			sb.append("<div class=\"hitarea closed-hitarea collapsable-hitarea\">");
			sb.append("</div>");

			if (listSubSubPasta != null && !listSubSubPasta.isEmpty()) {

				sb.append("<span class=\"folder\">");

				sb.append(this.obterStatusPermissao(request, subPasta, subPasta.getNome(), usuarioDto));

				sb.append("</span >");

				Collection<BaseConhecimentoDTO> listBaseconhecimentoDaSubpasta = baseConhecimentoService.listarBaseConhecimentoByPasta(subPasta);

				if (listBaseconhecimentoDaSubpasta != null && !listBaseconhecimentoDaSubpasta.isEmpty()) {

					sb.append("<ul>");
					for (BaseConhecimentoDTO base : listBaseconhecimentoDaSubpasta) {

						sb.append("<li>");
						sb.append("<span class=\"file\">");

						/*
						 * sb.append("<a  href='#'  onclick='tituloBaseConhecimentoView(" + base.getIdBaseConhecimento() + "); corTitulo(" + base.getIdBaseConhecimento() +
						 * ");incidentesAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento() + ")'id='idTitulo" + base.getIdBaseConhecimento() + "' >");
						 */

						sb.append("<a  href='#'  onclick='corTitulo(" + base.getIdBaseConhecimento() + ");itensConfiguracoesAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento()
								+ ");incidentesAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento() + ");problemasAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento()
								+ ");mudancasAbertasPorBaseConhecimnto(" + base.getIdBaseConhecimento() + ");comentariosAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento()
								+ ");verificarPermissaoDeAcesso(" + base.getIdPasta() + "," + base.getIdBaseConhecimento() + ");desabilitaDivPesquisa()'    id='idTitulo" + base.getIdBaseConhecimento() + "' >");

						// sb.append(this.obterStatusConfidencial(pasta, base.getTitulo(), usuarioDto));

						if (base.getPrivacidade() != null && !StringUtils.isEmpty(base.getPrivacidade())) {
							sb.append(" " + base.getTitulo() + " - " + this.getPrivacidade(request, base.getPrivacidade()) + " ");
						} else {
							sb.append(" " + base.getTitulo() + " ");
						}

						sb.append("</a>");
						sb.append("</span>");
						sb.append("</li>");
					}
					sb.append("</ul>");
				}
			} else {

				sb.append("<span class=\"folder\">");
				sb.append(this.obterStatusPermissao(request, subPasta, subPasta.getNome(), usuarioDto));
				sb.append("</span>");

				Collection<BaseConhecimentoDTO> listaBaseconhecimento = baseConhecimentoService.listarBaseConhecimentoByPasta(subPasta);

				if (listaBaseconhecimento != null && !listaBaseconhecimento.isEmpty()) {

					sb.append("<ul>");

					for (BaseConhecimentoDTO base : listaBaseconhecimento) {

						sb.append("<li>");
						sb.append("<span class=\"file\">");

						sb.append("<a  href='#'  onclick='corTitulo(" + base.getIdBaseConhecimento() + ");incidentesAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento()
								+ ");problemasAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento() + ");itensConfiguracoesAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento()
								+ ");mudancasAbertasPorBaseConhecimnto(" + base.getIdBaseConhecimento() + ");comentariosAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento()
								+ ");verificarPermissaoDeAcesso(" + base.getIdPasta() + "," + base.getIdBaseConhecimento() + ");desabilitaDivPesquisa()'    id='idTitulo" + base.getIdBaseConhecimento() + "' >");

						/*
						 * sb.append("<a  href='#'  onclick='tituloBaseConhecimentoView(" + base.getIdBaseConhecimento() + "); corTitulo(" + base.getIdBaseConhecimento() +
						 * ");incidentesAbertosPorBaseConhecimnto(" + base.getIdBaseConhecimento() + ")'id='idTitulo" + base.getIdBaseConhecimento() + "' >");
						 */

						// sb.append(this.obterStatusConfidencial(pasta, base.getTitulo(), usuarioDto));

						if (base.getPrivacidade() != null && !StringUtils.isEmpty(base.getPrivacidade())) {
							sb.append(" " + base.getTitulo() + " - " + this.getPrivacidade(request, base.getPrivacidade()) + " ");
						} else {
							sb.append(" " + base.getTitulo() + " ");
						}

						sb.append("</a>");
						sb.append("</span>");
						sb.append("</li>");

					}
					sb.append("</ul>");
				}
			}

			if (listSubSubPasta != null && !listSubSubPasta.isEmpty()) {
				sb.append("<ul id=\"subBios\"> ");
				this.gerarSubpastas(sb, listSubSubPasta, pasta, request);
				sb.append("</ul> ");
				sb.append("</li>");
			}
		}
	}

	/**
	 * Carrega combo de Pastas de Acordo com Perfil de Acesso do Usuário Logado.
	 * 
	 * @param document
	 * @param request
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void alimentaComboPastasBaseConhecimento(DocumentHTML document, HttpServletRequest request) throws Exception {

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		HTMLSelect combo = (HTMLSelect) document.getSelectById("comboPasta");
		inicializaCombo(combo, request);

		ArrayList<PastaDTO> listaPastaAux = (ArrayList<PastaDTO>) pastaService.listPastasESubpastas(usuarioDto);

		for (PastaDTO pasta : listaPastaAux) {
			if (pasta.getDataFim() == null) {
				combo.addOption(pasta.getId().toString(), pasta.getNomeNivel());
			}
		}
	}

	/**
	 * Carrega combo de Origem do conhecimento
	 * 
	 * @param document
	 * @param request
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public void preencherComboOrigem(DocumentHTML document, HttpServletRequest request) throws Exception {

		HTMLSelect combo = (HTMLSelect) document.getSelectById("comboOrigem");
		combo.removeAllOptions();

		combo.addOption(BaseConhecimentoDTO.CONHECIMENTO.toString(), UtilI18N.internacionaliza(request, "baseConhecimento.conhecimento"));
		combo.addOption(BaseConhecimentoDTO.EVENTO.toString(), UtilI18N.internacionaliza(request, "justificacaoFalhas.evento"));
		combo.addOption(BaseConhecimentoDTO.MUDANCA.toString(), UtilI18N.internacionaliza(request, "requisicaMudanca.mudanca"));
		combo.addOption(BaseConhecimentoDTO.INCIDENTE.toString(), UtilI18N.internacionaliza(request, "solicitacaoServico.incidente"));
		combo.addOption(BaseConhecimentoDTO.SERVICO.toString(), UtilI18N.internacionaliza(request, "servico.servico"));
		combo.addOption(BaseConhecimentoDTO.PROBLEMA.toString(), UtilI18N.internacionaliza(request, "problema.problema"));
	}

	/**
	 * Preenche Combo Privacidade.
	 * 
	 * @param document
	 * @param request
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void preencherComboPrivacidade(DocumentHTML document, HttpServletRequest request) throws Exception {

		HTMLSelect comboPrivacidade = (HTMLSelect) document.getSelectById("comboPrivacidade");

		comboPrivacidade.removeAllOptions();

		comboPrivacidade.addOption(BaseConhecimentoDTO.CONFIDENCIAL.toString(), UtilI18N.internacionaliza(request, "baseconhecimento.privacidade.confidencial"));
		comboPrivacidade.addOption(BaseConhecimentoDTO.PUBLICO.toString(), UtilI18N.internacionaliza(request, "baseconhecimento.privacidade.publico"));
		comboPrivacidade.addOption(BaseConhecimentoDTO.INTERNO.toString(), UtilI18N.internacionaliza(request, "baseconhecimento.privacidade.interno"));
	}

	/**
	 * Preenche combo situação.
	 * 
	 * @param document
	 * @param request
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void preencherComboSituacao(DocumentHTML document, HttpServletRequest request) throws Exception {

		HTMLSelect comboPrivacidade = (HTMLSelect) document.getSelectById("comboSituacao");

		comboPrivacidade.removeAllOptions();

		comboPrivacidade.addOption(BaseConhecimentoDTO.EMDESENHO.toString(), UtilI18N.internacionaliza(request, "baseconhecimento.emdesenho"));
		comboPrivacidade.addOption(BaseConhecimentoDTO.EMREVISAO.toString(), UtilI18N.internacionaliza(request, "baseconhecimento.emrevisao"));
		comboPrivacidade.addOption(BaseConhecimentoDTO.REVISADO.toString(), UtilI18N.internacionaliza(request, "baseconhecimento.revisado"));
		comboPrivacidade.addOption(BaseConhecimentoDTO.EMAVALIACAO.toString(), UtilI18N.internacionaliza(request, "baseconhecimento.emavaliacao"));
		comboPrivacidade.addOption(BaseConhecimentoDTO.AVALIADO.toString(), UtilI18N.internacionaliza(request, "baseconhecimento.avaliado"));
	}

	/**
	 * Verifica se usuário aprova Base Conhecimento na pasta selecionada.
	 * 
	 * @param usuarioDto
	 * @param idPasta
	 * @return true = aprova; false = não aprova.
	 * @throws ServiceException
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	private boolean usuarioAprovaBaseConhecimento(UsuarioDTO usuarioDto, Integer idPasta) throws ServiceException, Exception {

		boolean aprovaBaseConhecimento = false;

		PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);

		aprovaBaseConhecimento = perfilAcessoPastaService.verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(usuarioDto, idPasta);

		return aprovaBaseConhecimento;
	}

	public void verificarNotificacoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {

			baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);

			if (baseConhecimentoDto.getIdNotificacao() != null) {

				Notificacao notificacao = new Notificacao();

				NotificacaoDTO notificacaoDto = new NotificacaoDTO();

				notificacaoDto.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
				notificacaoDto.setIdNotificacao(baseConhecimentoDto.getIdNotificacao());

				DocumentHTML documentNotificacao = document;

				documentNotificacao.setBean(notificacaoDto);

				notificacao.restore(documentNotificacao, request, response);
			}
		}
	}

	/**
	 * Verifica se usuário possui acesso ao Conhecimento que está tentanto relacionar.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void validarRelacionamentoConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		if (baseConhecimentoDto.getIdConhecimentoRelacionado() != null) {

			baseConhecimentoDto.setIdBaseConhecimento(baseConhecimentoDto.getIdConhecimentoRelacionado());
			baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);

			PermissaoAcessoPasta permissao = this.obterPermissaoDeAcessoPasta(baseConhecimentoDto, usuarioDto);

			if (permissao != null && (permissao.equals(PermissaoAcessoPasta.LEITURA) || permissao.equals(PermissaoAcessoPasta.LEITURAGRAVACAO))) {
				document.executeScript("addLinhaTabelaConhecimentoRelacionado(" + baseConhecimentoDto.getIdBaseConhecimento() + ",'" + baseConhecimentoDto.getTitulo() + "'," + true + ");");
			} else {
				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
			}
		}
	}

	@Override
	public Class getBeanClass() {
		return BaseConhecimentoDTO.class;
	}

	/**
	 * @return valor do atributo idPrimeiraPasta.
	 */
	public Integer getIdPrimeiraPasta() {
		return idPrimeiraPasta;
	}

	/**
	 * Define valor do atributo idPrimeiraPasta.
	 * 
	 * @param idPrimeiraPasta
	 */
	public void setIdPrimeiraPasta(Integer idPrimeiraPasta) {
		this.idPrimeiraPasta = idPrimeiraPasta;
	}

	/**
	 * Preenche combo de Notificações.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Thays
	 */
	public void preencherComboNotificacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboTipoNotificacao = (HTMLSelect) document.getSelectById("tipo");
		inicializaCombo(comboTipoNotificacao, request);

		for (Enumerados.TipoNotificacao tipoNotificacao : Enumerados.TipoNotificacao.values()) {
			comboTipoNotificacao.addOption(tipoNotificacao.getTipoNotificacao(), UtilI18N.internacionaliza(request, tipoNotificacao.getDescricao()));
		}

		/*
		 * comboTipoNotificacao.addOption("T", "Tudo for alterado"); comboTipoNotificacao.addOption("C", "Novas nofiticações for adicionadas"); comboTipoNotificacao.addOption("A",
		 * "Notificações forem alteradas"); comboTipoNotificacao.addOption("E", "Notificações forem excluidas");
		 */

	}

	/**
	 * Inicializa combo.
	 * 
	 * @param componenteCombo
	 * @param request
	 * @author Vadoilo Damasceno
	 */
	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	/**
	 * Restorna String com a Label Confidencial caso o usuário não possua Permissão de Acesso a Pasta ou a Base de Conhecimento.
	 * 
	 * @param idPasta
	 * @param pastaBaseConhecimento
	 * @param usuarioDto
	 * @return String
	 * @throws ServiceException
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public String obterStatusPermissao(HttpServletRequest request, PastaDTO pastaDto, String pastaBaseConhecimento, UsuarioDTO usuarioDto) throws ServiceException, Exception {

		PerfilAcessoPastaService perfilAcessoPastaService = (PerfilAcessoPastaService) ServiceLocator.getInstance().getService(PerfilAcessoPastaService.class, null);

		PermissaoAcessoPasta permissao = null;

		permissao = perfilAcessoPastaService.verificarPermissaoDeAcessoPasta(usuarioDto, pastaDto);

		if (permissao != null) {
			if (PermissaoAcessoPasta.SEMPERMISSAO.equals(permissao)) {
				return "" + pastaBaseConhecimento + " - " + UtilI18N.internacionaliza(request, "baseconhecimento.permissao.sempermissao");
			} else {
				return "" + pastaBaseConhecimento + " ";
			}
		} else {
			return "" + pastaBaseConhecimento + " - " + UtilI18N.internacionaliza(request, "baseconhecimento.permissao.sempermissao");
		}
	}

	/**
	 * Cria Notificação.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Thays
	 */
	public void gravarNotificacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		NotificacaoDTO notificacaoDto = new NotificacaoDTO();

		baseConhecimentoDto.setListaDeUsuarioNotificacao((ArrayList<NotificacaoUsuarioDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoUsuarioDTO.class,
				"listUsuariosNotificacaoSerializados", request));

		baseConhecimentoDto.setListaDeGrupoNotificacao((ArrayList<NotificacaoGrupoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoGrupoDTO.class,
				"listGruposNotificacaoSerializados", request));

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

		PermissaoAcessoPasta permissao = this.obterPermissaoDeAcessoPasta(baseConhecimentoDto, usuarioDto);

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {

			if (permissao != null) {

				if (PermissaoAcessoPasta.LEITURAGRAVACAO.equals(permissao)) {

					if (baseConhecimentoDto.getIdNotificacao() != null) {

						if (baseConhecimentoDto.getTituloNotificacao() != null && !baseConhecimentoDto.getTituloNotificacao().equalsIgnoreCase("")) {
							notificacaoDto.setTitulo(baseConhecimentoDto.getTitulo());
						} else {
							document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.tituloObrigatorio"));
							return;
						}

						if (baseConhecimentoDto.getTipoNotificacao() != null && !baseConhecimentoDto.getTipoNotificacao().equalsIgnoreCase("")) {
							notificacaoDto.setTipoNotificacao(baseConhecimentoDto.getTipoNotificacao());
						} else {
							document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.tipoNotificacaoObrigatorio"));
							return;
						}

						baseConhecimentoService.criarNotificacao(baseConhecimentoDto, null);

						document.alert(UtilI18N.internacionaliza(request, "MSG06"));

						document.executeScript("limpar_LOOKUP_BASECONHECIMENTO()");
					} else {

						if (baseConhecimentoDto.getTituloNotificacao() != null && !baseConhecimentoDto.getTituloNotificacao().equalsIgnoreCase("")) {
							notificacaoDto.setTitulo(baseConhecimentoDto.getTitulo());
						} else {
							document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.tituloObrigatorio"));
							return;
						}

						if (baseConhecimentoDto.getTipoNotificacao() != null && !baseConhecimentoDto.getTipoNotificacao().equalsIgnoreCase("")) {
							notificacaoDto.setTipoNotificacao(baseConhecimentoDto.getTipoNotificacao());
						} else {
							document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.tipoNotificacaoObrigatorio"));
							return;
						}

						notificacaoDto = baseConhecimentoService.criarNotificacao(baseConhecimentoDto, null);

						baseConhecimentoDto.setIdNotificacao(notificacaoDto.getIdNotificacao());

						baseConhecimentoService.update(baseConhecimentoDto);

						document.alert(UtilI18N.internacionaliza(request, "MSG05"));

						document.executeScript("limpar_LOOKUP_BASECONHECIMENTO()");
					}

				} else {
					document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
				}

			} else {
				document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.usuarioSemPermissao"));
			}
		}
	}

	/**
	 * Arquiva Documento da Base de Conhecimento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void arquivarConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {
			baseConhecimentoService.arquivarConhecimento(baseConhecimentoDto);

			document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.documentoArquivado"));

			document.executeScript("limpar()");
			document.executeScript("ocultarBotaoArquivar()");
		}
	}

	/**
	 * Restaura Conhecimento Arquivado da Base de Conhecimento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void restaurarConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		if (baseConhecimentoDto.getIdBaseConhecimento() != null) {

			baseConhecimentoService.restaurarConhecimento(baseConhecimentoDto);

			document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.documentoRestaurado"));

			document.executeScript("limpar()");
			document.executeScript("ocultarBotaoRestaurar()");
			document.executeScript("exibirBotaoArquivar()");
		}
	}

	/**
	 * Retorna privacidade de Acordo com a sua abreviação.
	 * 
	 * @param abreviacaoPrivacidade
	 * @return Privacidade
	 * @author Vadoilo Damasceno
	 */
	public String getPrivacidade(HttpServletRequest request, String abreviacaoPrivacidade) {

		if (abreviacaoPrivacidade != null && !StringUtils.isEmpty(abreviacaoPrivacidade)) {
			if (StringUtils.equalsIgnoreCase(BaseConhecimentoDTO.CONFIDENCIAL, abreviacaoPrivacidade)) {
				return UtilI18N.internacionaliza(request, "baseconhecimento.privacidade.confidencial");
			} else {
				if (StringUtils.equalsIgnoreCase(BaseConhecimentoDTO.PUBLICO, abreviacaoPrivacidade)) {
					return UtilI18N.internacionaliza(request, "baseconhecimento.privacidade.publico");
				} else {
					if (StringUtils.equalsIgnoreCase(BaseConhecimentoDTO.INTERNO, abreviacaoPrivacidade)) {
						return UtilI18N.internacionaliza(request, "baseconhecimento.privacidade.interno");
					}
				}
			}
		}
		return "";
	}

	/**
	 * Retorna Grau de Importância.
	 * 
	 * @param request
	 * @param importancia
	 * @return String
	 * @author Vadoilo Damasceno
	 */
	public String getGrauImportancia(HttpServletRequest request, Integer importancia) {

		if (importancia != null) {
			if (importancia == 1) {
				return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": " + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.baixo");
			} else {
				if (importancia == 2) {
					return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": " + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.medio");
				} else {
					if (importancia == 3) {
						return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": " + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.alto");
					}
				}
			}
		}
		return "";
	}

	/**
	 * Restaura item configuração na Grid.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void restoreItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();

		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);

		itemConfiguracaoDTO.setIdItemConfiguracao(baseConhecimentoDto.getIdItemConfiguracao());
		itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO);

		HTMLTable tabelaItemConfiguracao = document.getTableById("tblIC");

		if (itemConfiguracaoDTO.getSequenciaIC() == null) {

			tabelaItemConfiguracao.addRow(itemConfiguracaoDTO, new String[] { "", "idItemConfiguracao", "identificacao", "" }, new String[] { "idItemConfiguracao" },
					UtilI18N.internacionaliza(request, "baseConhecimento.itemExiste"), new String[] { "exibeIconesIC" }, null, null);
		} else {

			tabelaItemConfiguracao.updateRow(itemConfiguracaoDTO, new String[] { "", "idItemConfiguracao", "identificacao", "" }, new String[] { "idItemConfiguracao" },
					UtilI18N.internacionaliza(request, "baseConhecimento.itemExiste"), new String[] { "exibeIconesIC" }, null, null, itemConfiguracaoDTO.getSequenciaIC());
		}

		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");
		document.executeScript("fecharPopupPesquisaItemCfg()");
	}

	/**
	 * Restura solicitação serviço na Grid.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void atualizaGridSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();
		ServicoDTO servicoDto = new ServicoDTO();

		solicitacaoServicoDto.setIdSolicitacaoServico(baseConhecimentoDto.getIdSolicitacaoServico());

		solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);

		servicoContratoDto.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());

		servicoContratoDto = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDto);

		servicoDto.setIdServico(servicoContratoDto.getIdServico());

		servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

		solicitacaoServicoDto.setNomeServico(servicoDto.getNomeServico());

		HTMLTable tblSolicitacao = document.getTableById("tblSolicitacao");

		if (solicitacaoServicoDto.getSequenciaSolicitacao() == null) {
			tblSolicitacao.addRow(solicitacaoServicoDto, new String[] { "", "", "idSolicitacaoServico", "nomeServico" }, new String[] { "idSolicitacaoServico" },
					UtilI18N.internacionaliza(request, "baseConhecimento.solicitacaoExiste"), new String[] { "exibeIconesSolicitacao" }, null, null);
		} else {
			tblSolicitacao.updateRow(solicitacaoServicoDto, new String[] { "", "", "idSolicitacaoServico", "nomeServico" }, new String[] { "idSolicitacaoServico" },
					UtilI18N.internacionaliza(request, "baseConhecimento.solicitacaoExiste"), new String[] { "exibeIconesSolicitacao" }, null, null, solicitacaoServicoDto.getSequenciaSolicitacao());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblSolicitacao', 'tblSolicitacao');");
		document.executeScript("fecharSolicitacaoServico();");
	}

	/**
	 * Restaura Problemas na Grid.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void atualizaGridProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		ProblemaDTO problemaDTO = new ProblemaDTO();

		problemaDTO.setIdProblema(baseConhecimentoDto.getIdProblema());

		problemaDTO = (ProblemaDTO) problemaService.restore(problemaDTO);
		HTMLTable tblProblema = document.getTableById("tblProblema");

		if (problemaDTO != null) {
			if (problemaDTO.getSequenciaProblema() == null) {
				tblProblema.addRow(problemaDTO, new String[] { "", "", "titulo", "status" }, new String[] { "idProblema" }, UtilI18N.internacionaliza(request, "baseConhecimento.problemaExiste"),
						new String[] { "exibeIconesProblema" }, null, null);
			} else {
				tblProblema.updateRow(problemaDTO, new String[] { "", "", "titulo", "status" }, new String[] { "idProblema" }, UtilI18N.internacionaliza(request, "baseConhecimento.problemaExiste"),
						new String[] { "exibeIconesProblema" }, null, null, problemaDTO.getSequenciaProblema());
			}
		}

		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
		document.executeScript("fecharProblema();");
	}

	/**
	 * Restaura Mudanças na Grid.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void atualizaGridMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();

		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();

		requisicaoMudancaDTO.setIdRequisicaoMudanca(baseConhecimentoDto.getIdRequisicaoMudanca());

		requisicaoMudancaDTO = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDTO);

		HTMLTable tblMudanca = document.getTableById("tblMudanca");

		if (requisicaoMudancaDTO.getSequenciaMudanca() == null) {
			tblMudanca.addRow(requisicaoMudancaDTO, new String[] { "", "", "titulo", "status" }, new String[] { "idRequisicaoMudanca" },
					UtilI18N.internacionaliza(request, "baseConhecimento.mudancaExiste"), new String[] { "exibeIconesMudanca" }, null, null);
		} else {
			tblMudanca.updateRow(requisicaoMudancaDTO, new String[] { "", "", "titulo", "status" }, new String[] { "idRequisicaoMudanca" },
					UtilI18N.internacionaliza(request, "baseConhecimento.mudancaExiste"), new String[] { "exibeIconesMudanca" }, null, null, requisicaoMudancaDTO.getSequenciaMudanca());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblMudanca', 'tblMudanca');");
		document.executeScript("fecharMudancaAtualizaGrid();");
	}
	/**
	 * Restaura Liberações na Grid.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author murilo.pacheco
	 */
	public void atualizaGridLiberacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		
		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
		
		RequisicaoLiberacaoDTO requisicaoLiberacaoDTO = new RequisicaoLiberacaoDTO();
		
		requisicaoLiberacaoDTO.setIdRequisicaoLiberacao(baseConhecimentoDto.getIdRequisicaoLiberacao());
		
		requisicaoLiberacaoDTO = (RequisicaoLiberacaoDTO) requisicaoLiberacaoService.restore(requisicaoLiberacaoDTO);
		
		HTMLTable tblMudanca = document.getTableById("tblLiberacao");
		
		if (requisicaoLiberacaoDTO.getSeqReabertura() == null) {
			tblMudanca.addRow(requisicaoLiberacaoDTO, new String[] { "", "", "titulo", "status" }, new String[] { "idRequisicaoLiberacao" },
					UtilI18N.internacionaliza(request, "baseConhecimento.liberacaoExiste"), new String[] { "exibeIconesLiberacao" }, null, null);
		} else {
			tblMudanca.updateRow(requisicaoLiberacaoDTO, new String[] { "", "", "titulo", "status" }, new String[] { "idRequisicaoLiberacao" },
					UtilI18N.internacionaliza(request, "baseConhecimento.liberacaoExiste"), new String[] { "exibeIconesLiberacao" }, null, null, requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblLiberacao', 'tblLiberacao');");
		document.executeScript("fecharLiberacao();");
	}

	/**
	 * Associa solicitações ao documento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void gravarSolicitacoesConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		List<SolicitacaoServicoDTO> colItensINC_Serialize = (List<SolicitacaoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(SolicitacaoServicoDTO.class,
				"colItensINC_Serialize", request);
		baseConhecimentoDto.setColItensIncidentes(colItensINC_Serialize);
		baseConhecimentoService.gravarSolicitacoesConhecimento(baseConhecimentoDto);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		document.alert(UtilI18N.internacionaliza(request, "MSG05"));
	}

	/**
	 * Associa problemas ao documento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void gravarProblemasConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		List<ProblemaDTO> colItensProblema_Serialize = (List<ProblemaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ProblemaDTO.class, "colItensProblema_Serialize", request);
		baseConhecimentoDto.setColItensProblema(colItensProblema_Serialize);
		baseConhecimentoService.gravarProblemasConhecimento(baseConhecimentoDto);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		document.alert(UtilI18N.internacionaliza(request, "MSG05"));
	}

	/**
	 * Associa mudanças ao documento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void gravarMudancaConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		List<RequisicaoMudancaDTO> colItensMudanca_Serialize = (List<RequisicaoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RequisicaoMudancaDTO.class,
				"colItensMudanca_Serialize", request);
		baseConhecimentoDto.setColItensMudanca(colItensMudanca_Serialize);
		baseConhecimentoService.gravarMudancaConhecimento(baseConhecimentoDto);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		document.alert(UtilI18N.internacionaliza(request, "MSG05"));
	}
	
	/**
	 * Associa Liberaçoes ao documento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author murilo.pacheco
	 */
	public void gravarLiberacaoConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		List<RequisicaoLiberacaoDTO> colItensLiberacao_Serialize = (List<RequisicaoLiberacaoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RequisicaoLiberacaoDTO.class,
				"colItensLiberacao_Serialize", request);
		baseConhecimentoDto.setColItensLiberacao(colItensLiberacao_Serialize);
		baseConhecimentoService.gravarLiberacaoConhecimento(baseConhecimentoDto);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		document.alert(UtilI18N.internacionaliza(request, "MSG05"));
	}

	/**
	 * Associa itens configuração ao documento.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void gravarICConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));
		BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) document.getBean();
		List<ItemConfiguracaoDTO> colItensIC = (List<ItemConfiguracaoDTO>) br.com.citframework.util.WebUtil
				.deserializeCollectionFromRequest(ItemConfiguracaoDTO.class, "colItensIC_Serialize", request);
		baseConhecimentoDto.setColItensICSerialize(colItensIC);
		baseConhecimentoService.gravarICConhecimento(baseConhecimentoDto);
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		document.alert(UtilI18N.internacionaliza(request, "MSG05"));
	}
	
    private ProblemaDTO getProblemaDto(Integer idProblema) throws Exception {
    	ProblemaService problemaService = (ProblemaService)ServiceLocator.getInstance().getService(ProblemaService.class,null);		
    	
		ProblemaDTO problemaDto = new ProblemaDTO();	
		
		if(idProblema!=null){
			problemaDto.setIdProblema(idProblema);
			problemaDto = (ProblemaDTO) problemaService.restore(problemaDto);
		}
		   
		return  problemaDto;
	}
}
