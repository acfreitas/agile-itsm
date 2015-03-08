package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.EmailAlteracaoSenhaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.InternacionalizarDTO;
import br.com.centralit.citcorpore.bean.LoginDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.InstalacaoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.VersaoService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.PersistenceEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.XmlReadLookup;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Login extends AjaxFormAction {

	public Class getBeanClass() {
		return LoginDTO.class;
	}

	public static String recarregaPagina = "S";

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		InstalacaoService instalacaoService = (InstalacaoService) ServiceLocator.getInstance().getService(InstalacaoService.class, null);
		if (!instalacaoService.isSucessoInstalacao() && request.getSession().getAttribute("passoInstalacao") == null) {
			document.executeScript("mostraMensagemInsercao('" + "<label>" + UtilI18N.internacionaliza(request, "instalacao.mensagemInsucesso1") + "</label>" + "<label>"
					+ UtilI18N.internacionaliza(request, "instalacao.mensagemInsucesso2") + "</label>" + "<label>" + UtilI18N.internacionaliza(request, "instalacao.mensagemInsucesso3") + "</label>"
					+ "<label>" + UtilI18N.internacionaliza(request, "instalacao.mensagemInsucessoAviso1") + "</label>" + "<label>"
					+ UtilI18N.internacionaliza(request, "instalacao.mensagemInsucessoAviso2") + "</label>" + "<label>" + UtilI18N.internacionaliza(request, "instalacao.mensagemInsucessoAviso3")
					+ "</label>" + "<label>" + UtilI18N.internacionaliza(request, "instalacao.mensagemInsucessoAviso4") + "</label>" + "<label>"
					+ UtilI18N.internacionaliza(request, "instalacao.mensagemInsucessoAvisoManual") + "</label>" + "')");
		}

		HTMLForm form = document.getForm("form");

		document.focusInFirstActivateField(form);
		if (request.getParameter("logout") != null && "yes".equalsIgnoreCase(request.getParameter("logout"))) {
			request.getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE", null);
			request.getSession().setAttribute("acessosUsuario", null);
			request.getSession().setAttribute("menu", null);
			request.getSession().removeAttribute("menuPadrao");
			Login.recarregaPagina = "S";

			document.executeScript("window.location.href = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/login/login.load'");
		}
		// limpa cache das sessoes.
		ServletContext context = request.getSession().getServletContext();
		if (context.getAttribute("instalacao") == null && request.getSession().getAttribute("passoInstalacao") == null
				&& (request.getSession().getAttribute("locale") == null || request.getSession().getAttribute("locale").equals("")) && request.getSession().getAttribute("abrePortal") == null) {
			request.getSession().invalidate();
		}

		String IDIOMAPADRAO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.IDIOMAPADRAO, "");
		if (IDIOMAPADRAO != null && IDIOMAPADRAO.equalsIgnoreCase("EN") && recarregaPagina.equalsIgnoreCase("S")) {
			WebUtil.setLocale(IDIOMAPADRAO, request);
			XmlReadLookup.getInstance(new Locale(IDIOMAPADRAO));
			recarregaPagina = "N";
		}

	}

	public void Login_onsave(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		InstalacaoService instalacaoService = (InstalacaoService) ServiceLocator.getInstance().getService(InstalacaoService.class, null);
		if (!instalacaoService.isSucessoInstalacao() && request.getSession().getAttribute("passoInstalacao") == null) {
			this.load(document, request, response);
			document.executeScript("fechar_aguarde();");
			return;
		}

		LoginDTO login = (LoginDTO) document.getBean();
		boolean isAdmin = false;
		if (login != null) {
			/**
			 * Motivo: Inersão da validação de login Autor: Flávio.santana Data/Hora: 05/11/2013 17:30
			 */
			if (login.getUser() == null || login.getUser().trim().equalsIgnoreCase("")) {
				document.executeScript("fechar_aguarde();");
				document.alert(UtilI18N.internacionaliza(request, "login.digite_login"));
				return;
			}
			if (login.getSenha() == null || login.getSenha().trim().equalsIgnoreCase("")) {
				document.alert(UtilI18N.internacionaliza(request, "login.digite_senha"));
				document.executeScript("fechar_aguarde();");
				return;
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "login.nao_confere"));
			document.executeScript("fechar_aguarde();");
			return;
		}

		UsuarioDTO usrDto = new UsuarioDTO();
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		String metodoAutenticacao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.METODO_AUTENTICACAO_Pasta, "2");

		isAdmin = ("admin".equalsIgnoreCase(login.getUser()) || "consultor".equalsIgnoreCase(login.getUser()));

		if (metodoAutenticacao != null && metodoAutenticacao.trim().equalsIgnoreCase("2")) {
			if (!isAdmin) {
				if (request.getSession().getAttribute("passoInstalacao") != null) {
					document.executeScript("fechar_aguarde();");
					document.alert(UtilI18N.internacionaliza(request, "usuario.permissaoInstalacao"));
					return;
				} else {
					usuarioService.sincronizaUsuarioAD(LDAPUtils.autenticacaoAD(login.getUser(), login.getSenha()), login, false);
				}
			}
		} else {
			System.out.println(UtilI18N.internacionaliza(request, "login.configNaoSincronizarComAD"));
		}

		boolean veririficaVazio = usuarioService.listSeVazio();

		String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
		if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {

			algoritmo = "SHA-1";
		}

		login.setSenha(CriptoUtils.generateHash(login.getSenha(), algoritmo));

		if (!veririficaVazio && "admin".equalsIgnoreCase(login.getUser())) {

			usrDto.setDataInicio(UtilDatas.getDataAtual());
			usrDto.setLogin(login.getUser());
			usrDto.setSenha(login.getSenha());
			usrDto.setNomeUsuario("Administrador");
			usrDto.setStatus("A");

			usuarioService.createFirs(usrDto);
		}
		UsuarioDTO usuarioBean = usuarioService.restoreByLogin(login.getUser(), login.getSenha());
		if (usuarioBean == null) {
			document.executeScript("fechar_aguarde();");
			document.alert(UtilI18N.internacionaliza(request, "login.nao_confere"));
			return;
		}

		if (metodoAutenticacao == null || metodoAutenticacao.trim().equalsIgnoreCase("")) {
			document.executeScript("fechar_aguarde();");
			document.alert(UtilI18N.internacionaliza(request, "login.metodoAutenticaoNaoConfigurado"));
			return;
		}

		if (metodoAutenticacao != null) {
			if (usuarioBean.getStatus().equalsIgnoreCase("A") && login.getSenha().equals(usuarioBean.getSenha())) {
				if (usuarioBean.getIdEmpresa() == null) {
					usuarioBean.setIdEmpresa(1);
				}
				Usuario usuarioFramework = new Usuario();
				UsuarioDTO usr = new UsuarioDTO();
				usr.setIdUsuario(usuarioBean.getIdUsuario());
				usr.setNomeUsuario(usuarioBean.getNomeUsuario());
				usr.setIdGrupo(usuarioBean.getIdGrupo());
				usr.setIdEmpresa(usuarioBean.getIdEmpresa());
				usr.setIdEmpregado(usuarioBean.getIdEmpregado());
				usr.setLogin(usuarioBean.getLogin());
				usr.setStatus(usuarioBean.getStatus());
				usr.setIdPerfilAcessoUsuario(getIdPerfilAcessoUsuario(usuarioBean.getIdUsuario()));
				usr.setAcessoCitsmart(getAcessoCitsmart(usr.getIdPerfilAcessoUsuario(), usr.getIdUsuario()));
				usr.setEmail(usuarioBean.getEmail());
				// utilizado para log
				PersistenceEngine.setUsuarioSessao(usuarioBean);
				Reflexao.copyPropertyValues(usr, usuarioFramework);
				br.com.citframework.util.WebUtil.setUsuario(usuarioFramework, request);
				GrupoService grupoSegService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
				Collection<GrupoDTO> colGrupos = grupoSegService.getGruposByPessoa(usuarioBean.getIdEmpregado());
				GrupoDTO grpSeg;

				String[] grupos = null;
				if (colGrupos != null) {
					grupos = new String[colGrupos.size()];
					for (int i = 0; i < colGrupos.size(); i++) {
						grpSeg = (GrupoDTO) ((List) colGrupos).get(i);
						grupos[i] = grpSeg.getSigla();
					}
				} else {
					grupos = new String[1];
					grupos[0] = "";
				}

				usr.setGrupos(grupos);
				usr.setColGrupos(colGrupos);

				WebUtil.setUsuario(usr, request);

				if (usr != null) {

					Problema problema = new Problema();

					problema.notificarPrazoSolucionarProblemaExpirou(document, request, response, usr);

				}

				// Verificação de instalação
				String sessao = (String) request.getSession().getAttribute("passoInstalacao");

				if (sessao != null) {
					document.executeScript("window.location = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/start/start.load';");
				} else {

					VersaoService versaoService = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, null);
					String idPerfilAcessoAdministrador = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_PERFIL_ACESSO_ADMINISTRADOR, "1");
					boolean usuarioTemPerfilDeAdministrador = usr.getIdPerfilAcessoUsuario() != null && usr.getIdPerfilAcessoUsuario().toString().trim().equals(idPerfilAcessoAdministrador.trim());
					if (versaoService.haVersoesSemValidacao() && !usuarioTemPerfilDeAdministrador) {
						document.executeScript("fechar_aguarde();");
						document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.citsmartAtualizado"));
						return;
					}

					document.executeScript("logar()");
				}
				
				/**
				 * Caso tenha ficado alguma carga de menu no session, remove a referência para pegá-la novamente ao carregar o menu
				 * 
				 * @author thyen.chang
				 * @since 12/02/2015
				 */
				if(request.getSession(true).getAttribute("menuPadrao") != null){
					request.getSession(true).removeAttribute("menuPadrao");
				}
				
				/*
				 * Desenvolvedor: Thiago Matias - Data: 05/11/2013 - Horário: 16:00 - ID Citsmart: 123357 - Motivo/Comentário: redirecionando para o portal quando o parametro do mesmo estiver
				 * habilitado.
				 */
				String abrePortal = (String)request.getSession().getAttribute("abrePortal");
				String parametroPortal = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LOGIN_PORTAL, "N");
				if (parametroPortal.equalsIgnoreCase("S")) {
					document.executeScript("window.location = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/portal/portal.load';");
				} else if(abrePortal!= null && abrePortal.equalsIgnoreCase("S")){
					document.executeScript("window.location = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/portal/portal.load';");
					request.getSession().setAttribute("abrePortal", null);
				}

			} else {
				document.executeScript("fechar_aguarde();");
				document.alert(UtilI18N.internacionaliza(request, "login.nao_confere"));
				request.getSession().invalidate();
				return;
			}
		}
	}

	/**
	 * Adicionado tratamento para quando usuário não possui perfil de acesso
	 * 30/12/2014 - 10:18
	 * @author thyen.chang
	 * @param idPerfilAcessoUsuario
	 * @param idUsuario
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	private String getAcessoCitsmart(Integer idPerfilAcessoUsuario, Integer idUsuario)  throws ServiceException, Exception{

		PerfilAcessoService perfilAcessoService = (PerfilAcessoService) ServiceLocator.getInstance().getService(PerfilAcessoService.class, null);

		PerfilAcessoDTO perfilAcessoDTO = new PerfilAcessoDTO();

		perfilAcessoDTO.setIdPerfilAcesso(idPerfilAcessoUsuario);

		perfilAcessoDTO = perfilAcessoService.findByIdPerfilAcesso(perfilAcessoDTO);

		if(perfilAcessoDTO != null && perfilAcessoDTO.getAcessoSistemaCitsmart() != null && !perfilAcessoDTO.getAcessoSistemaCitsmart().isEmpty()
				&& perfilAcessoDTO.getAcessoSistemaCitsmart().equals("S"))
			return perfilAcessoDTO.getAcessoSistemaCitsmart();
		else
			return perfilAcessoService.getAcessoCitsmartByUsuario(idUsuario);
	}

	private Integer getIdPerfilAcessoUsuario(Integer idUsuario) throws ServiceException, Exception {
		PerfilAcessoUsuarioService perfilAcessoService = (PerfilAcessoUsuarioService) ServiceLocator.getInstance().getService(PerfilAcessoUsuarioService.class, null);
		PerfilAcessoUsuarioDTO perfilAcessoDTO = new PerfilAcessoUsuarioDTO();
		perfilAcessoDTO.setIdUsuario(idUsuario);
		perfilAcessoDTO = perfilAcessoService.listByIdUsuario(perfilAcessoDTO);
		if (perfilAcessoDTO == null) {
			return null;
		} else {
			return perfilAcessoDTO.getIdPerfilAcesso();
		}
	}

	public void redefinirSenha(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Obtendo o login ou e-mail informado na popup de recuperação de senha.
		LoginDTO loginDTO = (LoginDTO) document.getBean();

		if (loginDTO != null) {

			// Obtendo serviços para consultar informações de usuário e
			// empregado.
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

			try {

				if (usuarioService != null) {

					UsuarioDTO usuarioDTO = null;

					EmpregadoDTO empregadoDTO = null;

					// Verificando se foi informado um valor.
					if (loginDTO.getLogin() != null) {

						if (loginDTO.getLogin().trim().equals("")) {

							document.alert(UtilI18N.internacionaliza(request, "login.necessarioInformarEmailOuLogin"));

							document.executeScript("$('#login').focus()");

							return;
						}

						// Verificando se o valor informado é um e-mail.
						if (loginDTO.getLogin().matches("\\b[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}\\b")) {

							// Obtendo o empregado a partir o e-mail.
							empregadoDTO = empregadoService.restoreByEmail(loginDTO.getLogin());

							usuarioDTO = usuarioService.restoreByIdEmpregado(empregadoDTO.getIdEmpregado());

						} else { // caso não seja, assume que é login.

							// Restaurando o usuário através do login.
							usuarioDTO = usuarioService.restoreByLogin(loginDTO.getLogin());

							empregadoDTO = empregadoService.restoreByEmail(String.format("%s@%s", loginDTO.getLogin(), "centralit.com.br"));

							// Obtendo o empregado.
							empregadoDTO = empregadoService.restoreEmpregadosAtivosById(empregadoDTO.getIdEmpregado());
						}

						if (empregadoDTO.getIdEmpregado() != null && empregadoDTO.getIdEmpregado() > 0) {

							String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");

							if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {

								algoritmo = "SHA-1";
							}

							EmailAlteracaoSenhaDTO emailAlteracaoSenhaDTO = new EmailAlteracaoSenhaDTO();

							emailAlteracaoSenhaDTO.setLogin(usuarioDTO.getLogin());

							emailAlteracaoSenhaDTO.setNomeEmpregado(empregadoDTO.getNome());

							UUID uuid = UUID.randomUUID();

							String novaSenha = uuid.toString();

							novaSenha = novaSenha.replaceAll("-", "");

							novaSenha = novaSenha.substring(0, 7);

							emailAlteracaoSenhaDTO.setNovaSenha(novaSenha);

							usuarioDTO.setSenha(CriptoUtils.generateHash(novaSenha, algoritmo));

							usuarioService.updateNotNull(usuarioDTO);

							emailAlteracaoSenhaDTO.setLink(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.URL_Sistema, "33"));

							String ID_MODELO_EMAIL_ALTERACAO_SENHA = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_ALTERACAO_SENHA, "18");

							MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL_ALTERACAO_SENHA.trim()), new IDto[] { emailAlteracaoSenhaDTO });

							mensagem.envia(empregadoDTO.getEmail(), "", ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, "10"));

							document.alert(String.format(UtilI18N.internacionaliza(request, "login.alteracaoSenha.notificacaoEnvioEmail"), empregadoDTO.getNome(), empregadoDTO.getEmail()));
						}
					}
				}
			} catch (Exception e) {

				document.alert(UtilI18N.internacionaliza(request, "login.alteracaoSenha.cancelamento"));

				e.printStackTrace();
			}
		}
	}

	public void internacionaliza(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InternacionalizarDTO bean = (InternacionalizarDTO) document.getBean();

		String IDIOMAPADRAO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.IDIOMAPADRAO, " ");

		if (IDIOMAPADRAO == null) {
			IDIOMAPADRAO = "";
		}

		request.getSession(true).setAttribute("menu", null);
		request.getSession(true).setAttribute("menuPadrao", null);

		if (bean != null) {

			if (bean.getLocale() != null) {
				WebUtil.setLocale(bean.getLocale().trim(), request);
				XmlReadLookup.getInstance(new Locale(bean.getLocale().trim()));
			} else {
				WebUtil.setLocale(IDIOMAPADRAO, request);
				XmlReadLookup.getInstance(new Locale(IDIOMAPADRAO));
			}
		} else {
			WebUtil.setLocale(IDIOMAPADRAO, request);
			XmlReadLookup.getInstance(new Locale(IDIOMAPADRAO));
		}
		document.executeScript("window.location.reload()");
	}

}