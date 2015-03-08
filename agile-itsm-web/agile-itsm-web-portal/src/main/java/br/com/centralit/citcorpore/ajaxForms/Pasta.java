/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoPastaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.NotificacaoGrupoService;
import br.com.centralit.citcorpore.negocio.NotificacaoService;
import br.com.centralit.citcorpore.negocio.NotificacaoUsuarioService;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * Action de Pasta.
 *
 * @author valdoilo.damasceno
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class Pasta extends AjaxFormAction {

	private PastaDTO pastaBean;

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		PastaDTO pastaDto = new PastaDTO();

		UsuarioDTO usuarioDto = new UsuarioDTO();

		usuarioDto = WebUtil.getUsuario(request);

		this.carregarComboDePastas(document, request);

		document.executeScript("deleteAllRows()");

		if (pastaDto == null || pastaDto.getHerdaPermissoes() == null || !StringUtils.contains(pastaDto.getHerdaPermissoes().toLowerCase().trim(), "S")) {

			HTMLForm formulario = document.getForm("form");

			this.gerarGridPerfilAcesso(document, null);

			document.executeScript("exibirGridPerfilAcesso()");

			formulario.setValues(pastaDto);
		}

		this.preencherComboNotificacao(document, request, response);
		document.executeScript("$('#btnGravarNotificacao').hide();");

	}

	/**
	 * Salva Nova Pasta no CITSmart.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {


		document.executeScript("aguarde();");
		PastaDTO pastaDto = new PastaDTO();

		pastaDto = (PastaDTO) document.getBean();

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		pastaDto.setPerfisDeAcesso(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(PerfilAcessoPastaDTO.class, "perfisSerializados", request));


		pastaDto.setListaDeUsuario((ArrayList<NotificacaoUsuarioDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoUsuarioDTO.class,
				"usuariosSerializados", request));

		pastaDto.setListaDeGrupo((ArrayList<NotificacaoGrupoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoGrupoDTO.class,
				"gruposSerializados", request));


		if (pastaDto.getId() == null) {

			if (pastaService.verificaSeExistePasta(pastaDto)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				document.executeScript("fechar_aguarde();");
				return;
			}
			/*if(pastaDto.getTitulo() == null && pastaDto.getTitulo().equalsIgnoreCase("")){
				document.alert("Titulo : campo obrigatorio");
				return;
			}
			if(pastaDto.getTipoNotificacao()==null && pastaDto.getTipoNotificacao().equalsIgnoreCase("")){
				document.alert("Tipo Notificação : campo obrigatorio");
				return;
			}*/

			pastaService.create(pastaDto);

			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			document.executeScript("fechar_aguarde();");
			document.executeScript("limpar();");

		} else {

			if (pastaService.verificaSeExistePasta(pastaDto)) {

				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				document.executeScript("fechar_aguarde();");
				return;
			}

			if(existeCruzamentoDeHeranca(pastaDto)) {
				document.alert(UtilI18N.internacionaliza(request, "pasta.naoEPossivelRelacionarAPastaSuperior"));
				document.executeScript("fechar_aguarde();");
				return;
			}

			pastaService.update(pastaDto);

			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			document.executeScript("fechar_aguarde();");
			document.executeScript("limpar();");
		}

		CITCorporeUtil.limparFormulario(document);
		document.executeScript("ocultarGridPerfilAcesso()");

		load(document, request, response);
	}

	/**
	 * Restaura a pasta e seus Perfis de Acesso.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws LogicException
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		PastaDTO pastaDto = new PastaDTO();

		NotificacaoDTO notificacaoDto = new NotificacaoDTO();

		pastaDto = (PastaDTO) document.getBean();

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		PerfilAcessoService perfilAcessoService = (PerfilAcessoService) ServiceLocator.getInstance().getService(PerfilAcessoService.class, null);

		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);


		NotificacaoGrupoService notificacaoGrupoService = (NotificacaoGrupoService) ServiceLocator.getInstance().getService(NotificacaoGrupoService.class, null);

		NotificacaoUsuarioService notificacaoUsuarioService = (NotificacaoUsuarioService) ServiceLocator.getInstance().getService(NotificacaoUsuarioService.class, null);

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		pastaDto = (PastaDTO) pastaService.restore(pastaDto);

		document.executeScript("deleteAllRowsUsuario()");
		document.executeScript("deleteAllRowsGrupo()");

		if(pastaDto.getIdNotificacao()!=null){

			notificacaoDto.setIdNotificacao(pastaDto.getIdNotificacao());

			notificacaoDto = (NotificacaoDTO) notificacaoService.restore(notificacaoDto);

			Collection<NotificacaoGrupoDTO> listaIdGrupo = notificacaoGrupoService.listaIdGrupo(notificacaoDto.getIdNotificacao());

			Collection<NotificacaoUsuarioDTO> listaIdUsuario = notificacaoUsuarioService.listaIdUsuario(notificacaoDto.getIdNotificacao());

			if (listaIdUsuario != null && !listaIdUsuario.isEmpty()) {
				for (NotificacaoUsuarioDTO notificacaoUsuarioDto : listaIdUsuario) {
					if (notificacaoUsuarioDto.getIdUsuario() != null) {
						UsuarioDTO usuarioDto = new UsuarioDTO();
						usuarioDto.setIdUsuario(notificacaoUsuarioDto.getIdUsuario());
						usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
						document.executeScript("addLinhaTabelaUsuario(" + usuarioDto.getIdUsuario() + ", '" + usuarioDto.getNomeUsuario() + "', " + false + ");");
						document.executeScript("exibirTabelaUsuario()");
						document.executeScript("$('#gridUsuario').show()");
					}

				}
			}

			if (listaIdGrupo != null && !listaIdGrupo.isEmpty()) {
				for (NotificacaoGrupoDTO notificacaoGrupoDto : listaIdGrupo) {
					if (notificacaoGrupoDto.getIdGrupo() != null) {
						GrupoDTO grupoDto = new GrupoDTO();
						grupoDto.setIdGrupo(notificacaoGrupoDto.getIdGrupo());
						grupoDto = (GrupoDTO) grupoService.restore(grupoDto);
						document.executeScript("addLinhaTabelaGrupo(" + grupoDto.getIdGrupo() + ", '" + grupoDto.getNome() + "', " + false + ");");
						document.executeScript("exibirTabelaGrupo()");
						document.executeScript("$('#gridGrupo').show()");
					}

				}
			}

			pastaDto.setTitulo(notificacaoDto.getTitulo());

			pastaDto.setTipoNotificacao(notificacaoDto.getTipoNotificacao());

		}

		document.executeScript("deleteAllRows()");


		HTMLForm form = CITCorporeUtil.limparFormulario(document);

		carregarComboPastaSuperiorNoRestore(document, request, pastaDto);

		form.setValues(pastaDto);

		document.executeScript("setarValoresPopupNotificacao();");

		document.executeScript("$('#btnGravarNotificacao').show();");

		Collection<PerfilAcessoDTO> listPerfilAcesso = perfilAcessoService.consultarPerfisDeAcesso(pastaDto);

		gerarGridPerfilAcesso(document, listPerfilAcesso);

		if (pastaDto.getIdPastaPai() != null) {

			document.executeScript("exibirHerdaPermissao()");

			if (pastaDto.getHerdaPermissoes() != null && StringUtils.contains(pastaDto.getHerdaPermissoes().toUpperCase().trim(), "S")) {

				document.executeScript("ocultarGridPerfilAcesso()");

			} else {

				document.executeScript("exibirGridPerfilAcesso()");
			}

		} else {

			document.executeScript("ocultarHerdaPermissao()");

		}
	}

	/**
	 * Restaura Grid de Perfil de Acesso Pasta.
	 *
	 * @param document
	 * @param perfisDeAcessoDaPasta
	 * @throws Exception
	 */
	public void gerarGridPerfilAcesso(DocumentHTML document, Collection<PerfilAcessoDTO> perfisDeAcessoDaPasta) throws Exception {

		PerfilAcessoService perfilAcessoService = (PerfilAcessoService) ServiceLocator.getInstance().getService(PerfilAcessoService.class, null);

		Collection<PerfilAcessoDTO> todosOsPerfisDeAcesso = perfilAcessoService.consultarPerfisDeAcessoAtivos();

		if (todosOsPerfisDeAcesso != null && !todosOsPerfisDeAcesso.isEmpty()) {

			int count = 0;
			document.executeScript("count = 0");
			for (PerfilAcessoDTO perfil : todosOsPerfisDeAcesso) {
				count++;

				document.executeScript("restoreRow()");
				document.executeScript("seqSelecionada = " + count);

				String perfilAcesso = (perfil.getNomePerfilAcesso() != null ? perfil.getNomePerfilAcesso() : "");
				String aprovaBaseConhecimento = (perfil.getAprovaBaseConhecimento() != null ? perfil.getAprovaBaseConhecimento() : "N");

				if (perfilAcesso != null) {
					perfilAcesso = perfilAcesso.replaceAll("'", "");
				}

				document.executeScript("setRestorePerfilAcesso('" + perfil.getIdPerfilAcesso() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(StringEscapeUtils.escapeJavaScript(perfilAcesso)) + "'," + "'"
						+ br.com.citframework.util.WebUtil.codificaEnter(aprovaBaseConhecimento) + "')");

				if (perfisDeAcessoDaPasta != null && !perfisDeAcessoDaPasta.isEmpty()) {

					for (PerfilAcessoDTO perfilAcessoPasta : perfisDeAcessoDaPasta) {

						String aprovaBase = br.com.citframework.util.WebUtil.codificaEnter((perfilAcessoPasta.getAprovaBaseConhecimento() != null ? perfilAcessoPasta.getAprovaBaseConhecimento() : ""));
						String permiteLeitura = br.com.citframework.util.WebUtil.codificaEnter((perfilAcessoPasta.getPermiteLeitura() != null ? perfilAcessoPasta.getPermiteLeitura() : ""));
						String permiteLeituraGravacao = br.com.citframework.util.WebUtil.codificaEnter((perfilAcessoPasta.getPermiteLeituraGravacao() != null ? perfilAcessoPasta.getPermiteLeituraGravacao() : ""));

						document.executeScript("atribuirChecked('" + perfilAcessoPasta.getIdPerfilAcesso() + "','" + aprovaBase + "','" + permiteLeitura + "','" + permiteLeituraGravacao + "')");

					}
				}
			}

		}
	}

	/**
	 * Cara combo de pastas.
	 *
	 * @param document
	 * @param idPasta
	 * @throws Exception
	 * @throws LogicException
	 * @throws ServiceException
	 */
	private void carregarComboPastaSuperiorNoRestore(DocumentHTML document, HttpServletRequest request, PastaDTO pastaDto) throws Exception, LogicException, ServiceException {

		PerfilAcessoGrupoDTO perfilAcessoGrupo = new PerfilAcessoGrupoDTO();

		UsuarioDTO usuarioDto = new UsuarioDTO();

		usuarioDto = WebUtil.getUsuario(request);

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);

		HTMLSelect comboPasta = (HTMLSelect) document.getSelectById("comboPastaPai");
		comboPasta.removeAllOptions();
		comboPasta.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		Collection<PastaDTO> pastasAtivas = pastaService.consultarPastasAtivas();

		ArrayList<PastaDTO> listaPastaAux = (ArrayList<PastaDTO>) pastaService.listPastasESubpastas();

		for (PastaDTO pasta : listaPastaAux) {

			if (pasta.getDataFim() == null) {

				if (!pasta.getId().equals(pastaDto.getId())) {

					comboPasta.addOption(pasta.getId().toString(), StringEscapeUtils.escapeJavaScript(pasta.getNomeNivel()));

				}
			}

		}

	}

	/**
	 * Exclui Pasta.
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public void excluirPasta(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		PastaDTO pastaDto = new PastaDTO();

		pastaDto = (PastaDTO) document.getBean();

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		if (pastaService.excluirPasta(pastaDto)) {

			CITCorporeUtil.limparFormulario(document);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			document.executeScript("fechar_aguarde();");

		} else {


			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
			document.executeScript("fechar_aguarde();");
		}
	}

	/**
	 * Carrega combo de Pastas.
	 *
	 * @param document
	 * @throws Exception
	 */
	private void carregarComboDePastas(DocumentHTML document, HttpServletRequest request) throws Exception {

		HTMLSelect comboPasta = (HTMLSelect) document.getSelectById("comboPastaPai");
		comboPasta.removeAllOptions();
		comboPasta.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		Collection<PastaDTO> listPastasESubpastas = pastaService.listPastasESubpastas();

		if (listPastasESubpastas != null && !listPastasESubpastas.isEmpty()) {

			for (PastaDTO pasta : listPastasESubpastas) {

				if (pasta.getDataFim() == null) {

					comboPasta.addOption(pasta.getId().toString(), StringEscapeUtils.escapeJavaScript(pasta.getNomeNivel()));

				}
			}
		}
	}

	/**
	 * Ativa Checkbox "Herdar Permissões" caso a pasta
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void ativarHerdarPemissoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		PastaDTO pastaDto = (PastaDTO) document.getBean();

		PastaDTO pastaSelecionada = new PastaDTO();

		if (pastaDto.getIdPastaPai() != null) {

			document.executeScript("ativarCheckHerdarPermissoes()");

		} else {

			document.executeScript("desativarCheckHerdarPermissoes()");

		}
	}

	public void preencherComboNotificacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HTMLSelect comboTipoNotificacao = (HTMLSelect) document.getSelectById("tipo");

		inicializaCombo(comboTipoNotificacao, request);

		for (Enumerados.TipoNotificacao tipoNotificacao : Enumerados.TipoNotificacao.values()) {
			comboTipoNotificacao.addOption(tipoNotificacao.getTipoNotificacao(), UtilI18N.internacionaliza(request, tipoNotificacao.getDescricao()));
		}

	}

	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void gravarNotificacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{

		PastaDTO pastaDto = (PastaDTO) document.getBean();

		NotificacaoDTO notificacaoDto = new NotificacaoDTO();

		pastaDto.setPerfisDeAcesso(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(PerfilAcessoPastaDTO.class, "perfisSerializados", request));

		pastaDto.setListaDeUsuario((ArrayList<NotificacaoUsuarioDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoUsuarioDTO.class,
				"usuariosSerializados", request));

		pastaDto.setListaDeGrupo((ArrayList<NotificacaoGrupoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(NotificacaoGrupoDTO.class,
				"gruposSerializados", request));

		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);


		if(pastaDto.getId()!=null){

			if(pastaDto.getIdNotificacao()!=null ){

				if(pastaDto.getTitulo()!=null && !pastaDto.getTitulo().equalsIgnoreCase("")){
					notificacaoDto.setTitulo(pastaDto.getTitulo());
				}else{
					document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.tituloObrigatorio"));
					return;
				}

				if(pastaDto.getTipoNotificacao()!=null && !pastaDto.getTipoNotificacao().equalsIgnoreCase("")){
					notificacaoDto.setTipoNotificacao(pastaDto.getTipoNotificacao());
				}else{
					document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.tipoNotificacaoObrigatorio"));
					return;
				}

			 pastaService.criarNotificacao(pastaDto, null);
			 document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}else{

				if(pastaDto.getTitulo()!=null && !pastaDto.getTitulo().equalsIgnoreCase("")){
					notificacaoDto.setTitulo(pastaDto.getTitulo());
				}else{
					document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.tituloObrigatorio"));
					return;
				}

				if(pastaDto.getTipoNotificacao()!=null && !pastaDto.getTipoNotificacao().equalsIgnoreCase("")){
					notificacaoDto.setTipoNotificacao(pastaDto.getTipoNotificacao());
				}else{
					document.alert(UtilI18N.internacionaliza(request, "baseConhecimento.tipoNotificacaoObrigatorio"));
					return;
				}

				notificacaoDto =  pastaService.criarNotificacao(pastaDto, null);

				pastaDto.setIdNotificacao(notificacaoDto.getIdNotificacao());

				pastaService.update(pastaDto);
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));


			}


		}



	}

	/**
	 *
	 * @param document
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void validarHerancadePermissao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PastaDTO pastaDto = (PastaDTO) document.getBean();

		if(pastaDto.getId() != null){
			if(existeCruzamentoDeHeranca(pastaDto)) {
				document.alert(UtilI18N.internacionaliza(request, "pasta.naoEPossivelRelacionarAPastaSuperior"));
			}
		}

	}

	public boolean existeCruzamentoDeHeranca(PastaDTO pastaDTO) throws ServiceException, Exception {
		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		if(pastaDTO.getIdPastaPai() != null) {
			PastaDTO pastaPai = (PastaDTO) pastaService.restore(new PastaDTO(pastaDTO.getIdPastaPai()));
			if(pastaPai != null && pastaPai.getIdPastaPai() != null) {
				if(pastaPai.getIdPastaPai().equals(pastaDTO.getId()))
					return true;
			}
		}
		return false;
	}

	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return PastaDTO.class;
	}

}
